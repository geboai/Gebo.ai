package ai.gebo.llms.abstraction.layer.controllers.model;

import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import lombok.Data;

/** The flat conditions to set on a provider deal, as a request body. */
@Data
public class ProviderDealFlatConditionsRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** The flat conditions; null clears them, making the deal pay per use. */
	private GProviderFlatConditions flatConditions = null;
}
