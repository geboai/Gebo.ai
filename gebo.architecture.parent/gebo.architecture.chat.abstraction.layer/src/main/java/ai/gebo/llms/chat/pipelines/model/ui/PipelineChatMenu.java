package ai.gebo.llms.chat.pipelines.model.ui;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PipelineChatMenu implements Cloneable {
	@NotNull
	private String menuId = null;
	private String pipelineId = null;
	@NotNull
	private String description = null;
	private String icon=null;
	@NotNull
	List<PipelineChatMenuItem> items = new ArrayList<PipelineChatMenuItem>();
	@NotNull
	private Integer order = null;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Cloneable problem", e);
		}
	}
}
