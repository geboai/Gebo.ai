package ai.gebo.multilanguage.support.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UIExistingText {
	public static final String SEPARATOR = "-§-";

	@Id
	public String getId() {
		return moduleId.toLowerCase() + SEPARATOR + entityId.toLowerCase() + SEPARATOR + componentId.toLowerCase()
				+ SEPARATOR + fieldId.toLowerCase();
	}

	public void setId(String id) {

	}

	@NotNull
	private String moduleId = null;
	@NotNull
	private String entityId = null;
	@NotNull
	private String componentId = null;
	private String key = null;
	@NotNull
	private String fieldId = null;
	@NotNull
	private String text = null;
}
