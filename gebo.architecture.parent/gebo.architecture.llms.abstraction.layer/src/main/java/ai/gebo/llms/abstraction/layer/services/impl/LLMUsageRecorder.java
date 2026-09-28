package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.llms.abstraction.layer.services.StackSamplingUtils;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;

/**
 * Writes the single usage record of one model call, whatever the model type. The
 * chat usage advisor and the usage recording wrappers of the other model types all
 * go through here, so the user, latency and caller stack of a record are derived
 * the same way for every type.
 */
@Service
@AllArgsConstructor
public class LLMUsageRecorder {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMUsageRecorder.class);
	/** Number of distinct packages sampled from the caller's stack. */
	public static final int CALLER_PACKAGES = 3;
	private final ILLMSUsageCrudService usageCrudService;

	/**
	 * Samples the caller's stack. Must be called on the thread that issued the call,
	 * before any asynchronous hop, or the sample describes the scheduler instead.
	 */
	public static String sampleCaller() {
		return StackSamplingUtils.sampleCallerPackages(CALLER_PACKAGES);
	}

	/**
	 * The user the call is made for. Like {@link #sampleCaller()} it must be read on
	 * the calling thread: a streamed call completes on a Reactor thread whose
	 * security context is usually empty.
	 */
	public static String currentUsername() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getName).orElse("anonymous");
	}

	/**
	 * Records one call. Always writes, even with zero tokens: model types that have
	 * no token accounting (speech, transcription, images, ranking) and providers that
	 * return no usage are still worth counting and timing.
	 *
	 * @param modelType the type of the model actually called, stated by the recording
	 *                  point: it is not inferred from the configuration's class, which
	 *                  yields no type for a configuration class it does not know.
	 */
	public void record(GBaseModelConfig config, ModelType modelType, String username, String callerStack,
			long startNanos, long inputToken, long outputToken, long totalToken, LLMCallOutcome outcome) {
		long latencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
		LLMUsageDetailDto detail = LLMUsageDetailDto.of(config);
		if (modelType != null) {
			if (detail.getModelType() != null && detail.getModelType() != modelType && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Usage model type stated by the caller=" + modelType
						+ " differs from the one inferred from the configuration=" + detail.getModelType()
						+ ", keeping the stated one");
			}
			detail.setModelType(modelType);
		}
		detail.setInputToken(inputToken);
		detail.setOutputToken(outputToken);
		detail.setTotalToken(totalToken);
		detail.setUsername(username);
		detail.setCallerStack(callerStack);
		detail.setLatency(latencyMs);
		detail.setOutcome(outcome);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Recording usage modelType=" + detail.getModelType() + " provider=" + detail.getProviderId()
					+ " model=" + detail.getModel() + " user=" + username + " outcome=" + outcome + " latency="
					+ latencyMs + "ms tokens=" + inputToken + "/" + outputToken + "/" + totalToken);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<USAGE_CALLER_STACK>" + callerStack + "</USAGE_CALLER_STACK>");
		}
		this.usageCrudService.enqueueUsage(detail);
	}

	private static long toLong(Integer value) {
		return value != null ? value.longValue() : 0L;
	}

	/**
	 * Starts the accounting of one call to a model of the given type, capturing on the
	 * calling thread everything that cannot be read once the call has completed.
	 */
	public Call begin(GBaseModelConfig config, ModelType modelType) {
		return new Call(config, modelType, currentUsername(), sampleCaller(), System.nanoTime());
	}

	/**
	 * One call being accounted: ended exactly once, with its outcome.
	 */
	@AllArgsConstructor
	public final class Call {
		private final GBaseModelConfig config;
		private final ModelType modelType;
		private final String username;
		private final String callerStack;
		private final long startNanos;

		public void success(long inputToken, long outputToken, long totalToken) {
			record(config, modelType, username, callerStack, startNanos, inputToken, outputToken, totalToken,
					LLMCallOutcome.SUCCESS);
		}

		public void success() {
			success(0, 0, 0);
		}

		/** Ends the call with the token counts of a provider {@link Usage}, when present. */
		public void success(Usage usage) {
			if (usage == null) {
				success();
				return;
			}
			success(toLong(usage.getPromptTokens()), toLong(usage.getCompletionTokens()),
					toLong(usage.getTotalTokens()));
		}

		public void failure() {
			record(config, modelType, username, callerStack, startNanos, 0, 0, 0, LLMCallOutcome.ERROR);
		}
	}
}
