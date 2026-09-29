package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;

/**
 * A runtime model priced by the provider deals: its
 * {@link IGConfigurableModel#getPricingConditions()} returns the price the deal
 * covering its API key gives to its configuration code, else its configured one.
 * Implemented by the abstract base implementations of every model family; the
 * runtime DAOs attach the deal service to each model they register, as models are
 * not Spring beans.
 */
public interface IGProviderDealPricedModel {

	/** Attaches the deal service; null leaves the model priced by its configuration. */
	public void setProviderDealService(IGProviderDealService providerDealService);

	/**
	 * The pricing of a model: the one of its provider deal, when the deal gives its
	 * configuration a price, else its configured one.
	 */
	public static GModelPricingConditions dealOrConfiguredPricing(IGProviderDealService providerDealService,
			IGConfigurableModel<?, ?> model) {
		GModelPricingConditions dealPricing = providerDealService != null
				? providerDealService.findModelPricing(model)
				: null;
		return dealPricing != null ? dealPricing : model.getConfiguredPricingConditions();
	}
}
