package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GProviderCurrency;

/**
 * The admin's settings of the real LLM providers, over what their model types
 * declare.
 */
public interface IGProviderSettingsService {
	/** The currency when neither the admin nor the provider's model types set one. */
	public static final String FALLBACK_CURRENCY = "USD";

	/**
	 * The default currency of a provider's prices: the admin's, else the one its
	 * model types declare, else {@link #FALLBACK_CURRENCY}.
	 */
	public GProviderCurrency getProviderCurrency(String providerId);

	/**
	 * Sets the default currency of a provider's prices, which must be a known
	 * currency; null goes back to the declared one.
	 */
	public GProviderCurrency updateProviderCurrency(String providerId, String currencyCode);
}
