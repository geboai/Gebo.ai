package ai.gebo.llms.setup.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMAutoconfigureCreationData {
	@NotNull
	private String vendorId = null;
	private String secretId = null;
	private String newApiSecret = null;
	private String newUserName = null;
	private String defaultChatModel = null;
	private String internalServicesModel = null;
	private String embeddingModel = null;
	private String rankerModel = null;
	private String transcriptModel = null;
	private String ttsModel = null;
	private String imagesModel = null;
}