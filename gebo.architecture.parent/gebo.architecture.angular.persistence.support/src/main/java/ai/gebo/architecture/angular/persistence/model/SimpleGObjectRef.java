package ai.gebo.architecture.angular.persistence.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleGObjectRef {
	@NotNull
	private String simpleClassName;
	@NotNull
	private String code;

}
