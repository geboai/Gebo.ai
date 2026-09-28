package ai.gebo.llms.abstraction.layer.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The price a {@link GProviderDeal} gives to one of its provider's models: the
 * admin's override, or completion, of the price found by the models lookup while
 * configuring the model. Stored as a list entry, not a map, since model codes
 * contain dots (e.g. "gpt-4.1") that MongoDB field names cannot.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GProviderModelPrice {
	/** The model's code at the provider, as {@code IGConfigurableModel.safeGetModelCode()} returns it. */
	private String modelCode = null;
	private GModelPricingConditions pricingConditions = null;
	private Date dateModified = null;
}
