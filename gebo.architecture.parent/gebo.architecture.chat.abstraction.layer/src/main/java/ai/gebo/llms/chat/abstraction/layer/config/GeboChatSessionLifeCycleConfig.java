package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatsession")
@Data
public class GeboChatSessionLifeCycleConfig {
	private double maximumContextWindowFullFillCoeff = 0.7;
	private double sessionShrinkResizeContextWindowCoeff = 0.4;
	private Integer maximumContextWindowTokenUsed = null;
	private Integer minimumShrinkResizeTargetTokens = null;
	private TimedOutMessageReceiverFactoryConfig sessionShrinkerReceiverConfig = new TimedOutMessageReceiverFactoryConfig();
	public GeboChatSessionLifeCycleConfig() {
		this.sessionShrinkerReceiverConfig.setTimeout(10000l);
		this.sessionShrinkerReceiverConfig.setPoolCardinality(1);
		this.sessionShrinkerReceiverConfig.setUseSenderThread(false);
		this.sessionShrinkerReceiverConfig.setFlushThreshold(10);
	}
}
