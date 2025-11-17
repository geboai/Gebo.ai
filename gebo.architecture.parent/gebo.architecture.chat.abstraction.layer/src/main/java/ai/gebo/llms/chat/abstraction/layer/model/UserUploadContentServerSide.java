package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.UUID;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUploadContentServerSide extends UserUploadedContent {
	public UserUploadContentServerSide() {
		this.setCode(UUID.randomUUID().toString());
	}
	@NotNull
	protected String relativeFilePath = null;
	protected String geboDocumentCode = null;
	protected GObjectRef<GProjectEndpoint> publishedEndpoint = null;
	protected Boolean published = null;
	
	
}
