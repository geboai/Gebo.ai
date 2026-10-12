package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/**
 * One API key of a provider, as the secrets store lists it under the provider's
 * context code, with the {@link GProviderDeal} currently covering it, if any.
 */
@Data
public class GProviderApiKey {
	/** Secret code of the API key. */
	private String secretCode = null;
	/** Description of the secret. */
	private String description = null;
	/** Type of the secret, e.g. a token. */
	private String secretType = null;
	/** Whether the secret is declared read only (e.g. in the configuration). */
	private Boolean readOnly = null;
	/** Id of the deal covering the key; null when no deal covers it. */
	private String dealId = null;
}
