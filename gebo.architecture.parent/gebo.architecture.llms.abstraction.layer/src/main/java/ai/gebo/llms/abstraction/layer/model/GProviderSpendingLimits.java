package ai.gebo.llms.abstraction.layer.model;

import java.util.Date;

import lombok.Data;

/**
 * The spending limits of a {@link GProviderDeal}: how much its API keys may spend,
 * per period, in {@link #currencyCode}. A limit not set means no limit for that
 * period.
 * <p>
 * The limits are either imported automatically from the provider, reading the
 * limits of the deal's API keys through the provider's API
 * ({@link #autoImported} true, refreshed whenever the deal's keys change), or set
 * by an admin ({@link #autoImported} false, never overwritten by an import).
 */
@Data
public class GProviderSpendingLimits {
	/** ISO 4217 code of the currency of the limits, e.g. USD. */
	private String currencyCode = null;
	private Double dailySpendingLimit = null;
	private Double weeklySpendingLimit = null;
	private Double monthlySpendingLimit = null;
	/** A limit that never resets, over the whole life of the keys. */
	private Double totalSpendingLimit = null;
	/** True when imported from the provider's API, false when set by an admin. */
	private Boolean autoImported = null;
	/** When the limits were last imported; null for limits set by an admin. */
	private Date importDate = null;
}
