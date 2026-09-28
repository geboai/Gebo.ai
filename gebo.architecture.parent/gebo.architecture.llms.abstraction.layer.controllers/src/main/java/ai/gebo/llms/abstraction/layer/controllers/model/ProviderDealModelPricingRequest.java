package ai.gebo.llms.abstraction.layer.controllers.model;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import lombok.Data;

/** The price a provider deal gives to one of its provider's models, as a request body. */
@Data
public class ProviderDealModelPricingRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** The model's code at the provider. */
	private String modelCode = null;
	/** The price; null removes it, the model going back to its configured price. */
	private GModelPricingConditions pricingConditions = null;
}
