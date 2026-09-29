package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * One model configuration of a provider, under the deal covering the API key it
 * runs with, with its prices: the pricing saved in the configuration (set by the
 * user, or retrieved from the provider's API) and the one the deal gives it, which
 * wins when set. Also lists the deal prices no longer applied, of configurations
 * deleted or running with a key the deal no longer covers.
 */
@Data
public class GProviderModelPriceInfo {
	public static enum PricingSource {
		/** Set by the user in the model configuration. */
		CONFIGURATION,
		/** Retrieved from the provider's API by the models lookup, not edited. */
		PROVIDER_API
	}

	private String providerId = null;
	/** The deal covering the configuration's API key; null when none covers it. */
	private String dealId = null;
	private String dealDescription = null;
	private String configCode = null;
	private String configDescription = null;
	/** The model's code at the provider, {@code IGConfigurableModel.safeGetModelCode()}. */
	private String modelCode = null;
	private ModelType modelType = null;
	/** Secret code of the API key the configuration runs with. */
	private String secretCode = null;
	/** The pricing saved in the configuration, to confirm as the deal's price. */
	private GModelPricingConditions configuredPricing = null;
	/** Where {@link #configuredPricing} comes from; null without pricing. */
	private PricingSource configuredPricingSource = null;
	/** The deal's price of the configuration, if any: it wins over the configured one. */
	private GModelPricingConditions dealPricing = null;
	/**
	 * True for a deal price no longer applied: its configuration was deleted, or runs
	 * with an API key the deal no longer covers.
	 */
	private boolean stale = false;
}
