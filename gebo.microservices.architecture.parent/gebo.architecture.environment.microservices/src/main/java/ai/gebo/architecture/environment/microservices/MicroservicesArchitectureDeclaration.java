package ai.gebo.architecture.environment.microservices;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;

@Configuration
// GeboClientsTopologyProperties is bound here rather than being a scanned
// @Component: this module is what declares "this deployable IS the microservices
// architecture", so the clients-topology overrides come into existence exactly
// where GClientsTopologyProviderImpl - their only consumer - does, and a
// deployable that has neither has no orphan @ConfigurationProperties bean.
@EnableConfigurationProperties(GeboClientsTopologyProperties.class)
public class MicroservicesArchitectureDeclaration {
	@Bean
	@Scope("singleton")
	public GeboApplicationArchitecture microservicesArchitecture() {
		return new GeboApplicationArchitecture(ArchitectureType.MICROSERVICES);
	}

}
