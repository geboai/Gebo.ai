package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

@Data
public class GModelPricingConditions {
	public static enum PricingModelType {
		FLAT, MTOKEN
	}
	PricingModelType pricingType = null;
	Double trafficLimits = null;
	Double mtokenPrice = null;
	String currencyCode = null;
}
