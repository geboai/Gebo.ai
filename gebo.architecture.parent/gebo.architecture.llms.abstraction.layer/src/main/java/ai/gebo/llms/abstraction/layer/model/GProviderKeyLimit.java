package ai.gebo.llms.abstraction.layer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The spending limit of one API key, as its provider's API reports it.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GProviderKeyLimit {
	public static enum Period {
		DAILY, WEEKLY, MONTHLY,
		/** A limit that never resets. */
		TOTAL
	}

	/** ISO 4217 currency of {@link #amount}. */
	private String currencyCode = null;
	/** The limit; null when the key has no spending limit. */
	private Double amount = null;
	/** The period the limit resets on; meaningless without an amount. */
	private Period period = null;
}
