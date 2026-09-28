package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GProviderModelPriceInfo;

/**
 * Lists the models of a provider with their prices, for the admin to override or
 * complete them per deal (see {@link IGProviderDealService#updateModelPricing}).
 */
public interface IGProviderModelPricesService {

	/**
	 * The models of a provider, one entry per deal and model code: the models running
	 * in the runtime DAOs whose type is of the provider, grouped by the deal covering
	 * their API key, plus the deal prices of models no longer configured.
	 */
	public List<GProviderModelPriceInfo> getProviderModelPrices(String providerId);
}
