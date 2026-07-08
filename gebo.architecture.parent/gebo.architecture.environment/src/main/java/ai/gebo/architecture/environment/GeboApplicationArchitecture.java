package ai.gebo.architecture.environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeboApplicationArchitecture {
	public static enum ArchitectureType {
		MONOLITHIC, MICROSERVICES
	}

	private final ArchitectureType architecture;
}
