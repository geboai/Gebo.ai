package ai.gebo.llms.chat.pipelines.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.service.impl.DefaultInputChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.impl.DefaultRoutingChatPipelineStepServiceImpl;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatpipes")
@Data
public class ChatPipelinesConfiguration {
	public ChatPipelinesConfiguration() {
		ChatPipelineConfiguration defaultPipeline = new ChatPipelineConfiguration();
		defaultPipeline.setCode("default-pipeline");
		defaultPipeline.setDefaultPipeline(true);
		defaultPipeline.setStepInputId(DefaultInputChatPipelineStepServiceImpl.DEFAULT_INPUT_STEP);
		defaultPipeline.setStepRouterId(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP);
		this.pipelines.add(defaultPipeline);
	}

	private List<ChatPipelineConfiguration> pipelines = new ArrayList<ChatPipelineConfiguration>();
}
