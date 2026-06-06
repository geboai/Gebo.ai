package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GAgentRole extends GBaseObject {
	@NotNull
	private String longExplanation = null;
	private List<String> agentServiceIds = null;
}
