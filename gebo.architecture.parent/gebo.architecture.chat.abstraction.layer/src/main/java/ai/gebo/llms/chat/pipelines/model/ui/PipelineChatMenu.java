package ai.gebo.llms.chat.pipelines.model.ui;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineChatMenu {
	@NotNull
	private String menuId = null;
	private String pipelineId = null;
	@NotNull
	private String description = null;
	@NotNull
	List<PipelineUserChatMenuItem> items = new ArrayList<PipelineUserChatMenuItem>();
	@NotNull
	private Integer order = null;
}
