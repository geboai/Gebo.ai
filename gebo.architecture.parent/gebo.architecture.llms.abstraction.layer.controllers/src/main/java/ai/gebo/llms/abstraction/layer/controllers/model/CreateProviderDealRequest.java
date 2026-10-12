package ai.gebo.llms.abstraction.layer.controllers.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/** A new provider deal, as a request body. */
@Data
public class CreateProviderDealRequest {
	/** The real provider of the deal. */
	private String providerId = null;
	/** Description of the deal; when blank a default one is given. */
	private String description = null;
	/**
	 * API keys the new deal covers, moved from the provider's other deals covering
	 * them; may be empty.
	 */
	private List<String> secretCodes = new ArrayList<>();
}
