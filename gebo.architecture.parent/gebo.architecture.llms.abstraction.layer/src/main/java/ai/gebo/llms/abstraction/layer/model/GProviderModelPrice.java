package ai.gebo.llms.abstraction.layer.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The price a {@link GProviderDeal} gives to one model configuration, identified by
 * its configuration code: the price the configuration's
 * {@code IGConfigurableModel.getPricingConditions()} returns while the deal covers
 * the API key it runs with. It moves with that key when the key moves to another
 * deal. Stored as a list entry, not a map, since codes may contain dots that
 * MongoDB field names cannot.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GProviderModelPrice {
	/** Code of the model configuration the price applies to: the key of the entry. */
	private String configCode = null;
	/**
	 * The model's code at the provider when the price was set, as
	 * {@code IGConfigurableModel.safeGetModelCode()} returned it; informative.
	 */
	private String modelCode = null;
	private GModelPricingConditions pricingConditions = null;
	private Date dateModified = null;
}
