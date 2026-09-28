package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * The deal Gebo has with a real LLM provider (e.g. "openai", "regolo.ai"): the
 * economic relationship shared by every model of that provider, whatever its type
 * (chat, embedding, ...), and by the API keys it applies to.
 * <p>
 * A deal maps the set of API keys, referenced by their secret code, it covers. It
 * is maintained automatically while models are configured: the API key chosen for
 * a model is associated with a deal of the model's provider, creating the deal
 * when the provider has none yet (see {@code IGProviderDealService}).
 */
@Data
@Document
public class GProviderDeal {
	/**
	 * Generated identifier, the provider id followed by a UUID (see
	 * {@link #newId(String)}): a provider may have several deals, so the provider
	 * alone is not a key.
	 */
	@Id
	private String id = null;
	/**
	 * The real provider this deal is with, as its model types declare it
	 * ({@link GModelType#getProviderId()}): not specific to any model type, and not
	 * unique, a provider possibly having several deals.
	 */
	@Indexed
	private String providerId = null;
	/**
	 * Human readable description, given by the admin or, for a deal created
	 * automatically, a default one (see {@link #defaultDescription(String, boolean)}).
	 */
	private String description = null;
	/** Secret codes of the API keys this deal covers, each listed once. */
	@Indexed
	private List<String> secretCodes = new ArrayList<>();
	/**
	 * The deal's flat conditions, if any: monthly price and traffic limits shared by
	 * its API keys. Null for a pay per use deal.
	 */
	private GProviderFlatConditions flatConditions = null;
	/**
	 * The deal's spending limits, imported from the provider's API
	 * ({@link GProviderSpendingLimits#getAutoImported()} true) or set by an admin.
	 * Null when unknown.
	 */
	private GProviderSpendingLimits spendingLimits = null;
	/**
	 * The prices of the provider's models under this deal, one entry per model code,
	 * set by the admin. They win over the prices configured with the models (see
	 * {@code IGConfigurableModel.getPricingConditions()}).
	 */
	private List<GProviderModelPrice> modelPrices = new ArrayList<>();
	private Date dateCreated = null;
	private Date dateModified = null;

	/** The price this deal gives to a model, or null. */
	public GModelPricingConditions modelPricing(String modelCode) {
		if (modelPrices == null || modelCode == null) {
			return null;
		}
		for (GProviderModelPrice price : modelPrices) {
			if (modelCode.equals(price.getModelCode())) {
				return price.getPricingConditions();
			}
		}
		return null;
	}

	/**
	 * The description given to a deal created without one, e.g. "regolo.ai deal
	 * (created automatically on 2026-09-28)".
	 *
	 * @param automatic whether the deal is created by the model configuration flow
	 *                  rather than by an admin
	 */
	public static String defaultDescription(String providerId, boolean automatic) {
		return providerId + " deal (" + (automatic ? "created automatically" : "created") + " on "
				+ java.time.LocalDate.now() + ")";
	}

	/** A new deal identifier for a provider: its id followed by a UUID. */
	public static String newId(String providerId) {
		return providerId + "-" + UUID.randomUUID();
	}
}
