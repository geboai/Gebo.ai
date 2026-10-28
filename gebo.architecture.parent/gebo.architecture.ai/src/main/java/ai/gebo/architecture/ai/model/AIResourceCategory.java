package ai.gebo.architecture.ai.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.ai.model.AIResourceType.ConsentActions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class AIResourceCategory {
	@NotNull
	private String id, description;
	private Map<String, Object> metaData = new HashMap<>();
}
