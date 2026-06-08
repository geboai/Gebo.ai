package ai.gebo.architecture.agents.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.roles")
@Data
@PropertySource(value = "classpath:/agent-roles-library/agent-roles.yml", factory = GeboYamlPropertySourceFactory.class)
public class AgentRolesConfig {
	private List<GAgentRole> library = null;
}
