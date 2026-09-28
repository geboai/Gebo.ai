package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GProviderDeal;

/**
 * Maintains the {@link GProviderDeal}s: which API keys each real provider's deal
 * covers.
 */
public interface IGProviderDealService {

	/**
	 * Makes sure an API key is covered by a deal of its provider:
	 * <ul>
	 * <li>a deal of the provider already covering the key is returned as is;</li>
	 * <li>otherwise the key is added to the provider's existing deal;</li>
	 * <li>otherwise a deal is created for the provider, covering the key.</li>
	 * </ul>
	 *
	 * @param providerId the real provider, as its model types declare it
	 * @param secretCode the secret code of the API key
	 * @return the deal now covering the key, or null when the application has no
	 *         deals storage
	 */
	public GProviderDeal ensureDeal(String providerId, String secretCode);
}
