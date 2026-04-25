package ai.gebo.llms.abstraction.layer.model;

import org.springframework.security.web.util.UrlUtils;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import lombok.Data;

@Data
public class GBaseRankerModelConfig<ModelChoiceType extends GBaseRankerModelChoice>
		extends GBaseModelConfig<ModelChoiceType> {
	private Integer maxDocumentsPerRequest = null;
	private Integer maxDocumentTokens = null;
	private Integer responseReserveTokens = null;
	private String fullServiceUrl = null;
	private String relativeServiceUrl = null;

	public String generateEndpointUrl() throws LLMConfigException {
		if (fullServiceUrl != null)
			return fullServiceUrl;
		if (baseUrl != null) {
			if (relativeServiceUrl != null) {
				return baseUrl + relativeServiceUrl;
			}
			return baseUrl + IGRankerModelConfigurationSupportService.STANDARD_RERANK_RELATIVE_URL;
		}
		throw new LLMConfigException("Cannot create full url for the ranker service");
	}
}
