package ai.gebo.architecture.neo4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.neo4j")
@Data
public class GeboNeo4jModuleConfig {
	boolean enabled = false;

}
