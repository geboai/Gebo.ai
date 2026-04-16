package ai.gebo.architecture.ai.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPromptUseInfo  {
	private List<GPromptPlaceholderInfo> placeholders = new ArrayList<GPromptPlaceholderInfo>();
	@NotNull
	private String code=null;
	@NotNull
	private String description=null;
	@NotNull
	private String module = null;
	public GPromptUseInfo() {

	}

}
