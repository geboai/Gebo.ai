package ai.gebo.architecture.ai.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AIToolsAndResourcesLibraryType {
	@NotNull
	private String id, description;
	private Map<String, Object> metaData = new HashMap<>();
	@NotNull
	private List<AIResourceType> resourcesTypes = null;
	@NotNull
	private List<ToolReference> tools = null;
	/**
	 * What an agent's model must be told when a run holds this library, for the rules
	 * of its prompt that the tools change; null when there is nothing to tell. It is
	 * for the model: the API describing the library to the admin leaves it out.
	 */
	@JsonIgnore
	private String agentRules = null;
	/**
	 * The files a user may upload to a chat for this library's tools to work on, by
	 * their extension with the dot (.pptx, .png). The chats whose agents hold the
	 * library accept them even when no content handler reads them: such an upload is
	 * kept for the tools, which find it among the conversation's uploads, and is not
	 * read into the chat - the models get no picture or sound of it. Empty when the
	 * library works on no uploaded file.
	 */
	private List<String> uploadFileTypes = new ArrayList<>();
	/**
	 * Whether the library reaches an agent only when the agent names it
	 * ({@code GAgentConfig.libraryTypeIds}), instead of being mounted on every agent
	 * that mounts every library ({@code GAgentConfig.subscribeAllLibraries}), as all
	 * but a few are. A library answers true when its tools belong to some agents and
	 * to nobody else, as the requests a person answers as a process: on a chat agent
	 * they would compete with what the chat already does. It is the library's own
	 * nature, not an installation's choice: an agent leaves other libraries out with
	 * {@code GAgentConfig.excludedLibraryTypeIds}.
	 */
	private boolean mountedOnlyWhenAsked = false;
}
