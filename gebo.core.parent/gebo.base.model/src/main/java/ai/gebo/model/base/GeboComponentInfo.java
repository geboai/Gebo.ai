package ai.gebo.model.base;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeboComponentInfo {
	public GeboComponentInfo() {
	}

	// Identifier for the messaging system
	@NotNull
	private String messagingModuleId = null;
	@NotNull
	private String messagingComponentId = null;

	public String getCompleteComponentId() {

		return messagingModuleId + "." + messagingComponentId;
	}
}
