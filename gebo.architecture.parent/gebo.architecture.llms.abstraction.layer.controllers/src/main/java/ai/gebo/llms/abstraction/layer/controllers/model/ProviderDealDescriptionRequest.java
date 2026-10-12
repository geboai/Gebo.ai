package ai.gebo.llms.abstraction.layer.controllers.model;

import lombok.Data;

/** The new description of a provider deal, as a request body. */
@Data
public class ProviderDealDescriptionRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** The new description; must not be blank. */
	private String description = null;
}
