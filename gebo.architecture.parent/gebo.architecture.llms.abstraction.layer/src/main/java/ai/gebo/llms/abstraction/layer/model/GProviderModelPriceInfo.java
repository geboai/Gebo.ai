package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * One model of a provider under one of its deals, with its prices: the one the
 * deal gives it, which the configurations using it pay, and the one the provider's
 * API gave with the model, if any, as a suggestion.
 */
@Data
public class GProviderModelPriceInfo {
	private String providerId = null;
	/** The deal; null for configurations whose API key no deal covers. */
	private String dealId = null;
	private String dealDescription = null;
	/** The model's code at the provider, {@code IGConfigurableModel.safeGetModelCode()}. */
	private String modelCode = null;
	/** The configurations running the model with the deal's API keys; empty when none does. */
	private List<GModelConfigRef> configurations = new ArrayList<>();
	/** The pricing the provider's API gave with the model, if any. */
	private GModelPricingConditions providerApiPricing = null;
	/** The deal's price of the model: what the configurations pay. */
	private GModelPricingConditions dealPricing = null;
	/** True when the deal's price was imported from the provider's API, false when set by the admin. */
	private Boolean dealPricingAutoImported = null;
	private Date dealPricingDate = null;
}
