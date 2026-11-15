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
	/**
	 * Whether a resource of this type can be handed over as a file - a working
	 * directory as a zip, a document as itself - through
	 * {@code IGAIToolsAndResourcesLibrarySource#download}. What the file is and how
	 * it is made is the source's business; that it can be asked for is declared
	 * here, so that a user interface offers the download without knowing the type.
	 */
	private boolean downloadable = false;
}
