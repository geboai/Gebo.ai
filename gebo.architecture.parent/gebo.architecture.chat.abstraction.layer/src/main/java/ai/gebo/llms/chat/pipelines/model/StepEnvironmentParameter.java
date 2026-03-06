package ai.gebo.llms.chat.pipelines.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class StepEnvironmentParameter {
	public static enum StepEnvironmentType {
		STRING, STRING_LIST
	}

	@NotNull
	private final String paramName;
	@NotNull
	private final StepEnvironmentType paramType;
}
