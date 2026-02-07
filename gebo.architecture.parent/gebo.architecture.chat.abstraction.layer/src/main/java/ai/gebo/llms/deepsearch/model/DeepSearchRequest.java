package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.drew.lang.annotations.NotNull;

import ai.gebo.llms.chat.abstraction.layer.model.session.GUserChatSession;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchRequest extends GBaseObject {
	@NotNull
	String username = null;
	@NotNull
	String query = null;
	@NotNull

	List<String> knowledgeBases = new ArrayList<String>();
	@GObjectReference(referencedType = GUserChatSession.class)
	String userChatContextCode = null;
	String chatRequestCode = null;
	List<String> deepSearchDataSources = null;

	public DeepSearchRequest() {
		this.setCode(UUID.randomUUID().toString());
	}
}
