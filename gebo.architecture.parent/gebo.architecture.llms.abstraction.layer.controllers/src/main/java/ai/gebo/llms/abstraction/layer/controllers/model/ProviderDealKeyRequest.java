package ai.gebo.llms.abstraction.layer.controllers.model;

import lombok.Data;

/** An API key of a provider deal, as a request body. */
@Data
public class ProviderDealKeyRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** Secret code of the API key. */
	private String secretCode = null;
}
