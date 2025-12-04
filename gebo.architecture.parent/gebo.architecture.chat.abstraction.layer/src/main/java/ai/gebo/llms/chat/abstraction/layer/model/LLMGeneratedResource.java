package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMGeneratedResource  extends GBaseObject {
	@NotNull
	protected String fileName = null;
	protected String extension = null;
	protected String contentType = null;
	protected Long fileSize = null;
	protected Long tokensCount = null;
	@NotNull
	protected String userContextCode = null;
}
