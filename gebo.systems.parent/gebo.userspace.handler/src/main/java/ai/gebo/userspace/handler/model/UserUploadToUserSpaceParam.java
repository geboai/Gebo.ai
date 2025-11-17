package ai.gebo.userspace.handler.model;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUploadToUserSpaceParam {
	@NotNull
	@Valid
	List<UserUploadedContent> userUploadContent = null;
	GObjectRef<GUserspaceProjectEndpoint> userSpaceTransferTo = null;
	Boolean transferToUserDefaultSpace = null;
}