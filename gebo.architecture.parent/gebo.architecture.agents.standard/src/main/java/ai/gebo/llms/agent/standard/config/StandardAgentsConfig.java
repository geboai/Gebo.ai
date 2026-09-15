package ai.gebo.llms.agent.standard.config;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.standard")
@Data
public class StandardAgentsConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardAgentsConfig.class);

	private boolean enabled = false;

	/**
	 * Hard cap on the number of chunks kept per source document by the standard
	 * document-search agents, bounding the candidate pool fed to the ranker. Set via
	 * {@code ai.gebo.agents.standard.max-chunks-per-document} in application.yml.
	 */
	private int maxChunksPerDocument = 10;

	@PostConstruct
	public void logResolvedConfiguration() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Standard agents configuration resolved: enabled:" + enabled + " maxChunksPerDocument:"
					+ maxChunksPerDocument);
		}
	}
}
