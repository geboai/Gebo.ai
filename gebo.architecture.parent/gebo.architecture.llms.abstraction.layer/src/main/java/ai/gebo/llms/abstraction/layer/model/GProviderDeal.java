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
	/** Secret codes of the API keys this deal covers, each listed once. */
	@Indexed
	private List<String> secretCodes = new ArrayList<>();
	private Date dateCreated = null;
	private Date dateModified = null;

	/** A new deal identifier for a provider: its id followed by a UUID. */
	public static String newId(String providerId) {
		return providerId + "-" + UUID.randomUUID();
	}
}
