package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GProviderKeyLimit;

/**
 * Reads, through a provider's API, the spending limit of one of its API keys.
 * Implemented for the providers whose API exposes it to the key itself; the
 * provider deals import their limits through these readers.
 */
public interface IGProviderKeyLimitsReader {

	/** The real provider this reader serves, as its model types declare it. */
	public String getProviderId();

	/**
	 * Reads the spending limit of an API key.
	 *
	 * @param clearApiKey the API key, in clear
	 * @return the key's limit, with a null amount when the key has no limit
	 * @throws RuntimeException when the provider cannot be read
	 */
	public GProviderKeyLimit readLimit(String clearApiKey);
}
