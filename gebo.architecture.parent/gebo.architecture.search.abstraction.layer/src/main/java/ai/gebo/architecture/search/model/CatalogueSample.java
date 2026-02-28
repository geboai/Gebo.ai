package ai.gebo.architecture.search.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueSample {
	@NotNull
	String code = null;
	@NotNull
	String description = null;
	
}
