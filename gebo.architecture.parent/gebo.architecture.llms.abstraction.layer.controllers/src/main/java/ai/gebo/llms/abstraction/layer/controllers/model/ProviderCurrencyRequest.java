package ai.gebo.llms.abstraction.layer.controllers.model;

import lombok.Data;

/** The default currency to set for a provider's prices, as a request body. */
@Data
public class ProviderCurrencyRequest {
	private String providerId = null;
	/** ISO 4217 code; null goes back to the currency the provider declares. */
	private String currencyCode = null;
}
