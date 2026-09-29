package ai.gebo.llms.abstraction.layer.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * The admin's settings of a real LLM provider, overriding what its model types
 * declare: one document per provider, present only once something is set.
 */
@Data
@Document
public class GProviderSettings {
	/** The provider, as its model types declare it ({@link GModelType#getProviderId()}). */
	@Id
	private String providerId = null;
	/**
	 * ISO 4217 code of the currency the provider's prices are expressed in, replacing
	 * the one its model types declare ({@link GModelType#getDefaultCurrencyCode()}).
	 */
	private String currencyCode = null;
	private Date dateModified = null;
}
