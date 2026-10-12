package ai.gebo.llms.abstraction.layer.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The price a {@link GProviderDeal} gives to one of its provider's models: what
 * every model configuration running that model with an API key the deal covers
 * pays. Stored as a list entry, not a map, since model codes contain dots (e.g.
 * "gpt-4.1") that MongoDB field names cannot.
 * <p>
 * The price is either imported from the provider's API, when the provider exposes
 * it ({@link #autoImported} true, refreshed while the model is configured), or set
 * by the admin ({@link #autoImported} false, never overwritten by an import).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GProviderModelPrice {
	/** The model's code at the provider, as {@code IGConfigurableModel.safeGetModelCode()} returns it. */
	private String modelCode = null;
	private GModelPricingConditions pricingConditions = null;
	private Date dateModified = null;
	/** True when imported from the provider's API, false when set by the admin. */
	private Boolean autoImported = null;
}
