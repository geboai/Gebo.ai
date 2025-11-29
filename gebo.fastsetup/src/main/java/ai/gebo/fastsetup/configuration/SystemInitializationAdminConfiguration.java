package ai.gebo.fastsetup.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.sysinit.admin.config")
@Data
public class SystemInitializationAdminConfiguration {
	String adminUsername = null;
	String adminPassword = null;
}
