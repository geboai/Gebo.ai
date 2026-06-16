package ai.gebo.llms.agent.standard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.standard")
@Data
public class StandardAgentsConfig {
	private boolean enabled = false;

}
