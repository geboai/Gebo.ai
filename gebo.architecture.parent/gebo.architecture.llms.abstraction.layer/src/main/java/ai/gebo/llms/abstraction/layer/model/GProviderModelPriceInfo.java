package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * One model of a provider, under one of its deals, with its prices: the one
 * configured with the model (as the models lookup found it, or as set in the model
 * configuration) and the one the deal gives it, which wins when set.
 */
@Data
public class GProviderModelPriceInfo {
	private String providerId = null;
	/** The deal covering the API keys the model runs with; null when none covers them. */
	private String dealId = null;
	private String dealDescription = null;
	/** The model's code at the provider, {@code IGConfigurableModel.safeGetModelCode()}. */
	private String modelCode = null;
	/** The families the model is configured as (a model may serve as chat and embedding). */
	private List<ModelType> modelTypes = new ArrayList<>();
	/** Codes of the model configurations running the model; empty for a deal price of a model no longer configured. */
	private List<String> modelConfigCodes = new ArrayList<>();
	/** Secret codes of the API keys the model runs with. */
	private List<String> secretCodes = new ArrayList<>();
	/** The price saved with the model configuration, if any. */
	private GModelPricingConditions configuredPricing = null;
	/** The deal's price of the model, if any: it wins over the configured one. */
	private GModelPricingConditions dealPricing = null;
}
