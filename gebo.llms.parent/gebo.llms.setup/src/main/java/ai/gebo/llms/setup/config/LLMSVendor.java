package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMSVendor {
	@NotNull
	private LLMSVendorInfo vendorInfo = null;
	@NotNull
	@NotEmpty
	private List<LLMSModelsPresets> presets = new ArrayList<LLMSModelsPresets>();
}
