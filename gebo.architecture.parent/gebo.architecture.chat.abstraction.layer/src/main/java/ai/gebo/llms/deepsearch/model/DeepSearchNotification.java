package ai.gebo.llms.deepsearch.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepSearchNotification {
	@NotNull
	String content = null;
	String dataSourceDescription = null;
}
