package ai.gebo.llms.abstraction.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class GModelPricingConditionsTest {

	private static GModelPricingConditions mtoken(Double input, Double output) {
		GModelPricingConditions pricing = new GModelPricingConditions();
		pricing.setPricingType(GModelPricingConditions.PricingModelType.MTOKEN);
		pricing.setCurrencyCode("EUR");
		pricing.setInputMtokenPrice(input);
		pricing.setOutputMtokenPrice(output);
		return pricing;
	}

	@Test
	void inputAndOutputTokensArePricedSeparately() {
		// 1.2M input at 0.50 + 0.3M output at 2.00 = 0.60 + 0.60
		assertEquals(1.20, mtoken(0.50, 2.00).tokenCost(1_200_000, 300_000), 1e-9);
	}

	@Test
	void outputIsPricedAsInputWhenNoOutputPriceIsSet() {
		GModelPricingConditions embedding = mtoken(0.10, null);
		assertEquals(0.10, embedding.effectiveOutputMtokenPrice());
		assertEquals(0.02, embedding.tokenCost(150_000, 50_000), 1e-9);
	}

	@Test
	void noInputPriceMeansNoTokenCost() {
		assertNull(mtoken(null, 2.00).tokenCost(1000, 1000));
		assertNull(new GModelPricingConditions().tokenCost(1000, 1000));
	}
}
