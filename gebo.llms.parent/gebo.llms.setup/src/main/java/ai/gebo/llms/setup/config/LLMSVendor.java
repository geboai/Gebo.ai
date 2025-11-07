package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.secrets.model.GeboSecretType;
import lombok.Data;

@Data
public class LLMSVendor {
	private LLMSVendorInfo vendorInfo = null;
	private List<LLMSModelsPresets> presets = new ArrayList<LLMSModelsPresets>();
}
