package ai.gebo.llms.chat.pipelines.model.ui;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class PipelineChatMenuItemParameter {
	@NotNull
	private String parameterName = null;
	@NotNull
	private Object parameterValue = null;

}
