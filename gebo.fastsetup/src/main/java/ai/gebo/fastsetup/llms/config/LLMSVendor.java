package ai.gebo.fastsetup.llms.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class LLMSVendor {
	private String vendorId=null;
	private boolean requiresCustomUrl = false;
	private String defaultCustomUrl = null;
	private boolean requiresApiKey = false;
	private String description = null;
	private String name=null;
	private String webSite = null;
	private String acquireKeyUrl=null;
	private List<LLMSModelsPresets> presets=new ArrayList<LLMSModelsPresets>();
	
}
