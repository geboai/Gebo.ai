package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chat.prompts.cache")
@Data
public class GeboPromptsParametersCacheConfig {
	/**
	 * Minutes an entry survives without being hit before the scheduled eviction
	 * removes it.
	 */
	private long idleTtlMinutes = 30;

	/**
	 * Milliseconds a cached entry stays fresh: an older entry has its prompts
	 * parameters recomputed on the next lookup.
	 */
	private long parametersTtlMillis = 120000;
}
