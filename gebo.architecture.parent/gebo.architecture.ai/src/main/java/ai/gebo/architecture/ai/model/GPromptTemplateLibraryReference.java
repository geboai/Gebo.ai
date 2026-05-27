package ai.gebo.architecture.ai.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPromptTemplateLibraryReference {
	@NotNull
	private String promptUse = null;
	@NotNull
	private ContextContentRequired chatHistory = null;
	@NotNull
	private ContextContentRequired contextDocuments = null;
	@NotNull
	private ContextContentRequired toolsCalling = null;
	private String langCode = null;
	private String modelProvider = null;
	private String modelCode = null;
	@NotNull
	private String systemReference = null;
	@NotNull
	private String userReference = null;

}
