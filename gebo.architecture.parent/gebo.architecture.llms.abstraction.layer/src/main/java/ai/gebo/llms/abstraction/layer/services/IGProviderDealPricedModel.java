package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.LoggerFactory;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;

/**
 * A runtime model priced by the provider deals: its
 * {@link IGConfigurableModel#getPricingConditions()} returns the price the deal
 * covering its API key gives to its model code, else the one the provider's API
 * gave with the model.
 * Implemented by the abstract base implementations of every model family; the
 * runtime DAOs attach the deal service to each model they register, as models are
 * not Spring beans.
 */
public interface IGProviderDealPricedModel {

	/** Attaches the deal service; null leaves the model priced by the provider API only. */
	public void setProviderDealService(IGProviderDealService providerDealService);

	/**
	 * The pricing of a model: the one of its provider deal, when the deal gives its
	 * model a price, else the one the provider's API gave with the model.
	 */
	public static GModelPricingConditions dealOrProviderApiPricing(IGProviderDealService providerDealService,
			IGConfigurableModel<?, ?> model) {
		// Pricing is best effort: a failure prices the call with what is left, else not at
		// all, and never reaches the model call being priced.
		GModelPricingConditions dealPricing = null;
		try {
			dealPricing = providerDealService != null ? providerDealService.findModelPricing(model) : null;
		} catch (Throwable e) {
			LoggerFactory.getLogger(IGProviderDealPricedModel.class)
					.error("Cannot read the deal pricing of model code=" + safeCode(model)
							+ ", the provider API's one applies", e);
		}
		if (dealPricing != null) {
			return dealPricing;
		}
		try {
			return model.getProviderApiPricingConditions();
		} catch (Throwable e) {
			LoggerFactory.getLogger(IGProviderDealPricedModel.class)
					.error("Cannot read the provider API pricing of model code=" + safeCode(model)
							+ ", the call is left unpriced", e);
			return null;
		}
	}

	private static String safeCode(IGConfigurableModel<?, ?> model) {
		try {
			return model != null ? model.getCode() : null;
		} catch (Throwable e) {
			return null;
		}
	}
}
