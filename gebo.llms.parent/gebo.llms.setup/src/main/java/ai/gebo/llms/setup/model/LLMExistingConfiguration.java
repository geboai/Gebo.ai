package ai.gebo.llms.setup.model;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.secrets.model.SecretInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMExistingConfiguration {

	@NotNull
	GObjectRef<GBaseModelConfig> existingModelConfig = null;
	@NotNull
	ModelType modelType = null;
	SecretInfo secretInfo = null;

}
