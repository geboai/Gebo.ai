package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GProviderApiKey;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;

/**
 * Maintains the {@link GProviderDeal}s: which API keys each real provider's deal
 * covers.
 * <p>
 * The maintenance operations keep these rules: an API key is covered by at most
 * one deal; a key can be assigned only to a deal of a provider whose secrets list
 * it under the provider's context code; assigning a key another deal of the same
 * provider covers moves it; a deal can be deleted only once it covers no key. A
 * rule violation raises {@link IllegalArgumentException} or
 * {@link IllegalStateException}.
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

	/** The deals of a provider, or every deal when providerId is null. */
	public List<GProviderDeal> findDeals(String providerId);

	/** The deal with the given id, or null. */
	public GProviderDeal findDeal(String dealId);

	/**
	 * The API keys of a provider: the secrets listed under the provider's context
	 * code, each with the deal covering it.
	 */
	public List<GProviderApiKey> getProviderApiKeys(String providerId);

	/**
	 * Creates a new deal for a provider, covering the given API keys, which are moved
	 * from the provider's other deals covering them.
	 *
	 * @param secretCodes the keys of the new deal; may be empty
	 * @param description the deal's description; when blank a default one is given
	 */
	public GProviderDeal createDeal(String providerId, List<String> secretCodes, String description);

	/** Changes the description of a deal; a blank one is refused. */
	public GProviderDeal updateDescription(String dealId, String description);

	/**
	 * Assigns an API key of the deal's provider to the deal, moving it from another
	 * deal of the same provider covering it.
	 */
	public GProviderDeal assignApiKey(String dealId, String secretCode);

	/** Removes an API key from a deal, leaving it covered by no deal. */
	public GProviderDeal removeApiKey(String dealId, String secretCode);

	/** Deletes a deal covering no API key. */
	public void deleteDeal(String dealId);

	/**
	 * Sets the flat conditions of a deal, or clears them when null, making the deal
	 * pay per use. Set conditions need a currency and a non negative monthly cost;
	 * traffic limits, when set, must be positive.
	 */
	public GProviderDeal updateFlatConditions(String dealId, GProviderFlatConditions flatConditions);
}
