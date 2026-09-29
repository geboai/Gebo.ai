package ai.gebo.llms.abstraction.layer.controllers.model;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import lombok.Data;

/** The price a provider deal gives to one model configuration, as a request body. */
@Data
public class ProviderDealModelPricingRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** Code of the model configuration the price applies to. */
	private String configCode = null;
	/** The price; null removes it, the configuration going back to its own pricing. */
	private GModelPricingConditions pricingConditions = null;
}
