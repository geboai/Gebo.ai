package ai.gebo.model.base;

import jakarta.validation.constraints.NotNull;

public interface IGComponentOriginatedData {
		@NotNull
		public GeboComponentInfo getOriginComponent();
}
