package ai.gebo.architecture.environment.microservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;

@Configuration
public class MicroservicesArchitectureDeclaration {
	@Bean
	@Scope("singleton")
	public GeboApplicationArchitecture microservicesArchitecture() {
		return new GeboApplicationArchitecture(ArchitectureType.MICROSERVICES);
	}

}
