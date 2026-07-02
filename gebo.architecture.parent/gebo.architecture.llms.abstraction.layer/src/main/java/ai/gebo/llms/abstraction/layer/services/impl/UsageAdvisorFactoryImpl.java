package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.LLMUsageDetail;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisor;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.llms.abstraction.layer.services.StackSamplingUtils;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class UsageAdvisorFactoryImpl implements IChatModelUsageAdvisorFactory {
	private final ILLMSUsageCrudService usageCrudService;

	@AllArgsConstructor
	public static final class GeboChatModelUsageAdvisor implements IChatModelUsageAdvisor {

		private final GBaseChatModelConfig config;
		private final ILLMSUsageCrudService usageCrudService;
		private String callStack = null;

		@Override
		public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
			this.callStack = StackSamplingUtils.sampleCallerPackages(3);
			long start = System.nanoTime();

			ChatClientResponse response = chain.nextCall(request);

			collectUsage(request, response, start);

			return response;
		}

		@Override
		public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {

			long start = System.nanoTime();
			this.callStack = StackSamplingUtils.sampleCallerPackages(3);
			return chain.nextStream(request).doOnNext(response -> {
				Usage usage = response.chatResponse().getMetadata().getUsage();

				if (usage != null) {
					collectUsage(request, response, start);
				}
			});
		}

		@Override
		public String getName() {
			return "gebo-chatmodel-usage-advisor";
		}

		@Override
		public int getOrder() {
			return Ordered.LOWEST_PRECEDENCE - 100;
		}

		private void collectUsage(ChatClientRequest request, ChatClientResponse response, long startNanos) {
			Usage usage = response.chatResponse().getMetadata().getUsage();
			if (usage == null) {
				return;
			}
			String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
					.filter(Authentication::isAuthenticated).map(Authentication::getName).orElse("anonymous");
			long latencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
			Integer inputTokens = usage.getPromptTokens();
			Integer outputTokens = usage.getCompletionTokens();
			Integer totalTokens = usage.getTotalTokens();
			LLMUsageDetail detail = LLMUsageDetail.of(config);
			detail.setInputToken(inputTokens != null ? inputTokens.longValue() : 0l);
			detail.setOutputToken(outputTokens != null ? outputTokens.longValue() : 0l);
			detail.setTotalToken(totalTokens != null ? totalTokens.longValue() : 0l);
			detail.setUsername(username);
			detail.setCallerStack(callStack);
			this.usageCrudService.enqueueUsage(detail);
		}
	}

	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config) {

		return new GeboChatModelUsageAdvisor(config, usageCrudService, null);
	}

}
