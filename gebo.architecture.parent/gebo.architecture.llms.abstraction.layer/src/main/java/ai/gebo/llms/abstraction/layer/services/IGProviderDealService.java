package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GProviderApiKey;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import ai.gebo.llms.abstraction.layer.model.GProviderSpendingLimits;

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
	 * from the provider's other deals covering them: from then on the configurations
	 * running with them pay the new deal's model prices.
	 *
	 * @param secretCodes the keys of the new deal; may be empty
	 * @param description the deal's description; when blank a default one is given
	 */
	public GProviderDeal createDeal(String providerId, List<String> secretCodes, String description);

	/** Changes the description of a deal; a blank one is refused. */
	public GProviderDeal updateDescription(String dealId, String description);

	/**
	 * Assigns an API key of the deal's provider to the deal, moving it from another
	 * deal of the same provider covering it: from then on the configurations running
	 * with it pay this deal's model prices. {@link GProviderDeal#NO_API_KEY} stands
	 * for the provider's configurations running without an API key.
	 */
	public GProviderDeal assignApiKey(String dealId, String secretCode);

	/** Removes an API key from a deal, leaving it covered by no deal. */
	public GProviderDeal removeApiKey(String dealId, String secretCode);

	/**
	 * Deletes a deal covering no API key; the last deal of a provider cannot be
	 * deleted while the provider has configured models.
	 */
	public void deleteDeal(String dealId);

	/**
	 * Sets the flat conditions of a deal, or clears them when null, making the deal
	 * pay per use. Set conditions need a currency and a non negative monthly cost;
	 * traffic limits, when set, must be positive.
	 */
	public GProviderDeal updateFlatConditions(String dealId, GProviderFlatConditions flatConditions);

	/**
	 * Imports the deal's spending limits from its provider's API, through the
	 * provider's {@link IGProviderKeyLimitsReader}: the limits of all the deal's API
	 * keys, summed per period. Does nothing when the provider has no reader, or when
	 * an admin set the deal's limits. Best effort: a key that cannot be read leaves
	 * the limits unchanged and is logged. Also run automatically whenever the deal's
	 * keys change.
	 */
	public GProviderDeal refreshImportedLimits(String dealId);

	/**
	 * Sets the deal's spending limits by hand ({@code autoImported} false), which the
	 * imports then leave untouched; null clears them and imports them again.
	 */
	public GProviderDeal updateSpendingLimits(String dealId, GProviderSpendingLimits spendingLimits);

	/**
	 * Sets the price the deal gives to one of its provider's models, which every
	 * configuration running the model with an API key the deal covers pays; null
	 * removes it, the model going back to the price of the provider's API, if any.
	 * The price is marked as set by the admin, which imports never overwrite. Set
	 * prices need a currency and no negative amount.
	 */
	public GProviderDeal updateModelPricing(String dealId, String modelCode, GModelPricingConditions pricing);

	/**
	 * Imports the price the provider's API gives to a model into the deal covering
	 * the API key: set, flagged as imported, unless the admin set that model's price.
	 * Best effort: never fails.
	 *
	 * @param secretCode the API key the model runs with; null or blank for none
	 */
	public void importModelPricing(String providerId, String secretCode, String modelCode,
			GModelPricingConditions pricing);

	/**
	 * The price a model gets from the provider's deal covering an API key: the
	 * coordinates API key code -> provider + model code. Read on every model call, so
	 * served from an in-memory snapshot of the deals' prices, rebuilt after each
	 * change and refreshed in the background: no call to the store, never fails.
	 *
	 * @param secretCode the API key the model runs with; null or blank for none
	 * @return the deal's price of the model, or null when the deal covering the key
	 *         gives it none
	 */
	public GModelPricingConditions findModelPricing(String providerId, String secretCode, String modelCode);

	/**
	 * {@link #findModelPricing(String, String, String)} at a runtime model's
	 * coordinates: its type's provider, its configuration's API key and
	 * {@link IGConfigurableModel#safeGetModelCode()}.
	 */
	public GModelPricingConditions findModelPricing(IGConfigurableModel<?, ?> model);

	/**
	 * Rebuilds the in-memory snapshot of the deals' prices from the store, to pick up
	 * the changes made by other instances of a cluster.
	 */
	public void refreshPricesSnapshot();
}
