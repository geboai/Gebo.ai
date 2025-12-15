package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.drew.lang.annotations.NotNull;

import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeepSearchRequest extends GBaseObject {
	@NotNull
	String username = null;
	@NotNull
	String query = null;
	@NotNull
	@NotEmpty
	List<String> knowledgeBases = new ArrayList<String>();
	@GObjectReference(referencedType = GUserChatContext.class)
	String userChatContextCode = null;
	String chatRequestCode = null;

	public DeepSearchRequest() {
		this.setCode(UUID.randomUUID().toString());
	}
}
