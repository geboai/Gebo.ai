package ai.gebo.architecture.ai.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AIResourceType {
	public static enum ConsentActions {
		READ_INFORMATIONS, EXECUTE_CODE, WRITE_INFORMATIONS
	}
	@NotNull
	private String id, description;
	@NotNull
	private List<ConsentActions> consented = null;
	private Map<String, Object> metaData = new HashMap<>();
	@NotNull
	private AIResourceCategory category = null;
}
