package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.model.ModelType;
import lombok.Data;

/** A model configuration using a priced model, as the setup UI lists it. */
@Data
public class GModelConfigRef {
	private String configCode = null;
	private String description = null;
	private ModelType modelType = null;
	/** Secret code of the API key the configuration runs with; null without one. */
	private String secretCode = null;
}
