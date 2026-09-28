package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/**
 * The price of using a model, set on its configuration or on the model choice it
 * uses (the configuration wins, see {@code IGConfigurableModel.getPricingConditions()}).
 * All amounts are in {@link #currencyCode}.
 * <p>
 * Two pricing models are supported:
 * <ul>
 * <li>{@link PricingModelType#MTOKEN}: pay per use, priced per million tokens. Input
 * (prompt) and output (completion) tokens are priced separately, as most providers
 * do; a model priced on its input only, like an embedding model, sets the input
 * price alone and its output is priced the same.</li>
 * <li>{@link PricingModelType#FLAT}: a fixed {@link #monthlyFlatCost} per month,
 * optionally covering a traffic allowance of {@link #monthlyTrafficLimits} million
 * tokens per month, and often capped by the provider at {@link #dailyTrafficLimits}
 * million tokens per day. Tokens beyond the allowance are priced at the per million
 * token prices, when those are set.</li>
 * </ul>
 */
@Data
public class GModelPricingConditions {
	public static enum PricingModelType {
		/** A fixed cost per month, optionally with a monthly token allowance. */
		FLAT,
		/** Pay per use, priced per million tokens. */
		MTOKEN
	}

	private PricingModelType pricingType = null;
	/** ISO 4217 code of the currency every amount is expressed in, e.g. EUR, USD. */
	private String currencyCode = null;

	/** Price per million input (prompt) tokens. */
	private Double inputMtokenPrice = null;
	/**
	 * Price per million output (completion) tokens; when not set, output tokens are
	 * priced as input tokens.
	 */
	private Double outputMtokenPrice = null;

	/** {@link PricingModelType#FLAT} only: the fixed cost charged every month. */
	private Double monthlyFlatCost = null;
	/**
	 * {@link PricingModelType#FLAT} only: the traffic the monthly cost covers, in
	 * millions of tokens per month; not set means unlimited.
	 */
	private Double monthlyTrafficLimits = null;
	/**
	 * {@link PricingModelType#FLAT} only: the daily traffic cap, in millions of tokens
	 * per day, that providers often apply on top of a flat monthly fee; not set means
	 * no daily cap.
	 */
	private Double dailyTrafficLimits = null;

	/**
	 * The effective price per million output tokens: the output price, or the input
	 * price when no output price is set.
	 */
	public Double effectiveOutputMtokenPrice() {
		return outputMtokenPrice != null ? outputMtokenPrice : inputMtokenPrice;
	}

	/**
	 * The pay per use cost of the given tokens at the per million token prices, or
	 * null when the model is not priced per token or no input price is set. For a
	 * {@link PricingModelType#FLAT} model this is the price of tokens beyond the
	 * allowance, not of the ones the fee covers.
	 */
	public Double tokenCost(long inputTokens, long outputTokens) {
		if (inputMtokenPrice == null) {
			return null;
		}
		double output = effectiveOutputMtokenPrice();
		return (inputTokens * inputMtokenPrice + outputTokens * output) / 1_000_000d;
	}
}
