package ai.gebo.llms.chat.abstraction.layer.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPromptLibraryReference {
	@NotNull
	private String promptUse = null;
	private String langCode = null;
	private String modelProvider = null;
	private String modelCode = null;
	@NotNull
	private String reference = null;

}
