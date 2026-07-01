package ai.gebo.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
@Data
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.apikeygen.config")
public class GeneratedApiKeyConfig {
	private boolean userApiKeyGenEnabled=true;
	private boolean adminApiKeyGenEnabled=true;
}
