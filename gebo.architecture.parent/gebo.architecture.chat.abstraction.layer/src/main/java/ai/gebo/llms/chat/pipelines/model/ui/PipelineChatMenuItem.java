package ai.gebo.llms.chat.pipelines.model.ui;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineChatMenuItem implements Cloneable {
	@NotNull
	private String optionId = null;
	private String icon = null;
	@NotNull
	private String description = null;
	private boolean defaultOption = false;
	private String routeOption = null;
	private String pipelineId = null;
	private String productId = null;
	private List<PipelineChatMenuItemParameter> parameters = new ArrayList<PipelineChatMenuItemParameter>();

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Cloneable problem", e);
		}
	}
}
