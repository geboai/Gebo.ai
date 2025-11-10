package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.secrets.model.GeboSecretType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMSVendor {
	@NotNull
	private LLMSVendorInfo vendorInfo = null;
	@NotNull
	private List<LLMSModelsPresets> presets = new ArrayList<LLMSModelsPresets>();
}
