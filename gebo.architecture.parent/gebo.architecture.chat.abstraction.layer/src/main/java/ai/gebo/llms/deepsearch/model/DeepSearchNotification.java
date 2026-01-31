package ai.gebo.llms.deepsearch.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchNotification {
	@NotNull
	String content = null;
}
