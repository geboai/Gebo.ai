package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUploadedContent extends GBaseObject {
	public UserUploadedContent() {
	}

	public UserUploadedContent(UserUploadedContent uc) {
		super(uc);
		fileName = uc.fileName;
		extension = uc.extension;
		contentType = uc.contentType;
		fileName = uc.fileName;
		fileSize = uc.fileSize;
		tokensCount = uc.tokensCount;
		userContextCode = uc.userContextCode;
	}

	@NotNull
	protected String fileName = null;
	@NotNull
	protected String extension = null;
	@NotNull
	protected String contentType = null;
	@NotNull
	protected Long fileSize = null;
	@NotNull
	protected Long tokensCount = null;
	@NotNull
	protected String userContextCode = null;

}
