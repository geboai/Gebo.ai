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
	// Below MongoDB's 16 MB document limit: beyond it the oldest interactions' documents are dropped
	// from the full state (their summaries stay in the compact one).
	private int maximumFullStateBytes = 12 * 1024 * 1024;
	private TimedOutMessageReceiverFactoryConfig sessionShrinkerReceiverConfig = new TimedOutMessageReceiverFactoryConfig();
	public GeboChatSessionLifeCycleConfig() {
		this.sessionShrinkerReceiverConfig.setTimeout(10000l);
		this.sessionShrinkerReceiverConfig.setPoolCardinality(1);
		this.sessionShrinkerReceiverConfig.setUseSenderThread(false);
		this.sessionShrinkerReceiverConfig.setFlushThreshold(10);
	}
}
