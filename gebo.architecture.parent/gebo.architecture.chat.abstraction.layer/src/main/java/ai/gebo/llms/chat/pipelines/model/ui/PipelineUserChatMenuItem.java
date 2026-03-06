package ai.gebo.llms.chat.pipelines.model.ui;

import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineUserChatMenuItem {
	@NotNull
	String optionId = null;
	@NotNull
	String description = null;
	boolean defaultOption = false;
	RespondingWith routeOption = null;
	@NotNull
	String pipelineId = null;
}
