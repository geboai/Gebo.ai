package ai.gebo.llms.abstraction.layer.controllers.model;

import ai.gebo.llms.abstraction.layer.model.GProviderSpendingLimits;
import lombok.Data;

/** The spending limits to set by hand on a provider deal, as a request body. */
@Data
public class ProviderDealSpendingLimitsRequest {
	/** Id of the deal. */
	private String dealId = null;
	/** The limits; null clears them, importing them again from the provider. */
	private GProviderSpendingLimits spendingLimits = null;
}
