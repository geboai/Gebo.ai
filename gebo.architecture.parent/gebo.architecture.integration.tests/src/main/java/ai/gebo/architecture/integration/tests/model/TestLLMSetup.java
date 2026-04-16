package ai.gebo.architecture.integration.tests.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestLLMSetup {
	public static enum ModelRole {
		DEFAULT_EMBEDDING, DEFAULT_CHAT, INTERNAL_SERVICES
	}	
	@NotNull
	String modelCode = null;
	@NotNull
	ModelRole role = null;
}
