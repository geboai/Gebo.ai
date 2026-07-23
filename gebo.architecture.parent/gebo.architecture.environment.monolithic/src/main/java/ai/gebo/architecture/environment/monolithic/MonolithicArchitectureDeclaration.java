package ai.gebo.architecture.environment.monolithic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.GeboApplicationArchitecture.ArchitectureType;

@Configuration
public class MonolithicArchitectureDeclaration {
	@Bean
	@Scope("singleton")
	public GeboApplicationArchitecture monolithicArchitecture() {
		return new GeboApplicationArchitecture(ArchitectureType.MONOLITHIC);
	}

}
