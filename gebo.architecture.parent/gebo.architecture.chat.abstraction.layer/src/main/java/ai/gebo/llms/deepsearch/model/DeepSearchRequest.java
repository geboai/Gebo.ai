package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchRequest extends GBaseObject {
	@NotNull
	private String username = null;
	@NotNull
	private String query = null;
	private List<String> knowledgeBases = new ArrayList<String>();
	@GObjectReference(referencedType = GUserChatSession.class)
	private String userChatContextCode = null;
	private String chatRequestCode = null;
	private List<String> deepSearchDataSources = null;
	private DeliverableIntent userIntent = DeliverableIntent.UNKNOWN;
	public DeepSearchRequest() {
		this.setCode(UUID.randomUUID().toString());
	}
}
