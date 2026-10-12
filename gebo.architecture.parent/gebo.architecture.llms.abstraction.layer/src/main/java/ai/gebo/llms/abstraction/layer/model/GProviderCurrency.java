package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/** The default currency of a provider's prices, and where it comes from. */
@Data
public class GProviderCurrency {
	private String providerId = null;
	/** The currency in effect: the admin's, else the declared one. */
	private String currencyCode = null;
	/** The currency the provider's model types declare, else USD. */
	private String declaredCurrencyCode = null;
	/** True when the admin replaced the declared currency. */
	private boolean overridden = false;
}
