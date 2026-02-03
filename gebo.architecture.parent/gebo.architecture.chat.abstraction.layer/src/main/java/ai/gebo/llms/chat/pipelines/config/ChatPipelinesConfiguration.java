package ai.gebo.llms.chat.pipelines.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGStaticPromptsProvider;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultInputChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatpipes")
@Data
public class ChatPipelinesConfiguration {
	public static final String DEFAULT_PIPELINE = "default-pipeline";

	public ChatPipelinesConfiguration() {
		ChatPipelineConfiguration defaultPipeline = new ChatPipelineConfiguration();
		defaultPipeline.setCode(DEFAULT_PIPELINE);
		defaultPipeline.setDefaultPipeline(true);
		defaultPipeline.setStepInputId(DefaultInputChatPipelineStepServiceImpl.DEFAULT_INPUT_STEP);
		defaultPipeline.setStepRouterId(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP);
		this.pipelines.add(defaultPipeline);

	}

	private List<ChatPipelineConfiguration> pipelines = new ArrayList<ChatPipelineConfiguration>();

	private int maxRoutingDecisionDocumentsTokenBudget = 12000;
	private int globalRagTopK = 20;
	private double rewriteThreashold = 0.67;
}
