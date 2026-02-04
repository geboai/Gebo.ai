package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatsession")
@Data
public class GeboChatSessionLifeCycleConfig {
	private double maximumContextWindowFullFillCoeff = 0.7;
	private double sessionShrinkResizeContextWindowCoeff = 0.4;
	private Integer maximumContextWindowTokenUsed = null;
	private Integer minimumShrinkResizeTargetTokens = null;
}
