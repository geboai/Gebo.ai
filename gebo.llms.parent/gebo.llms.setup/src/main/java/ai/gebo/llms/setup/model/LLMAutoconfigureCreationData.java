package ai.gebo.llms.setup.model;

import lombok.Data;

@Data
public class LLMAutoconfigureCreationData {
	private String serviceHandler = null;
	private String secretId = null;
}