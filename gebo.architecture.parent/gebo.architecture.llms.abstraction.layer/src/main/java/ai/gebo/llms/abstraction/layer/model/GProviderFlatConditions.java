package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/**
 * The flat conditions of a {@link GProviderDeal}: a fixed monthly price, covering
 * the traffic of the deal's API keys up to the limits the provider sets, per month
 * and, often, per day. Traffic limits are in millions of tokens.
 */
@Data
public class GProviderFlatConditions {
	/** ISO 4217 code of the currency of {@link #monthlyFlatCost}, e.g. EUR, USD. */
	private String currencyCode = null;
	/** The fixed price paid every month. */
	private Double monthlyFlatCost = null;
	/** Traffic the monthly price covers, in millions of tokens; not set means unlimited. */
	private Double monthlyTrafficLimits = null;
	/**
	 * Daily traffic cap the provider applies, in millions of tokens; not set means no
	 * daily cap.
	 */
	private Double dailyTrafficLimits = null;
}
