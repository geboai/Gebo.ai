package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class GPromptPlaceholderInfo {
	private String placeholder = null, description = null;

}
