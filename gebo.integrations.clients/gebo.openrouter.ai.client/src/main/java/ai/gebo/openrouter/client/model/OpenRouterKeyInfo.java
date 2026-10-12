package ai.gebo.openrouter.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The information OpenRouter returns about the API key making the request
 * ({@code GET /key}). Amounts are in USD credits.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterKeyInfo {
	/** Human readable label of the key. */
	private String label;
	/** Spending limit of the key, in USD; null when the key has no limit. */
	private Double limit;
	/** Remaining spending before the limit, in USD; null without a limit. */
	@JsonProperty("limit_remaining")
	private Double limitRemaining;
	/**
	 * How often the limit resets: "daily", "weekly", "monthly", or null when it is a
	 * lifetime limit.
	 */
	@JsonProperty("limit_reset")
	private String limitReset;
	/** Total credits used by the key, in USD. */
	private Double usage;
	@JsonProperty("usage_daily")
	private Double usageDaily;
	@JsonProperty("usage_weekly")
	private Double usageWeekly;
	@JsonProperty("usage_monthly")
	private Double usageMonthly;
	@JsonProperty("is_free_tier")
	private Boolean freeTier;
}
