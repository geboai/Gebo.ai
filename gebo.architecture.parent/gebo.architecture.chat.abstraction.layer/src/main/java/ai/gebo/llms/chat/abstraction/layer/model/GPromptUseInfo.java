package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPromptUseInfo extends GBaseObject {
	private List<GPromptPlaceholderInfo> placeholders = new ArrayList<GPromptPlaceholderInfo>();
	@NotNull
	private String module = null;
	public GPromptUseInfo() {

	}

}
