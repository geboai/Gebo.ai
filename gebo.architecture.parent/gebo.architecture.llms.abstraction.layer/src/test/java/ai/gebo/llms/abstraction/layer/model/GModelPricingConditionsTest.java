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
	void perTokenProviderPricesBecomePerMillion() {
		// regolo.ai gpt-oss-20b, as published by its LiteLLM model_info
		GModelPricingConditions pricing = GModelPricingConditions.fromPerTokenPrices(1e-7, 4.2e-7, null, "USD");
		assertEquals(GModelPricingConditions.PricingModelType.MTOKEN, pricing.getPricingType());
		assertEquals("USD", pricing.getCurrencyCode());
		assertEquals(0.10, pricing.getInputMtokenPrice(), 1e-12);
		assertEquals(0.42, pricing.getOutputMtokenPrice(), 1e-12);
		assertNull(pricing.getRequestPrice());
	}

	@Test
	void aModelPricedPerRequestCostsItsRequestPrice() {
		// regolo.ai Qwen3-Embedding-8B: token prices 0, 0.001 per request
		GModelPricingConditions embedding = GModelPricingConditions.fromPerTokenPrices(0d, 0d, 0.001, "USD");
		assertEquals(0.001, embedding.callCost(50_000, 0), 1e-12);
		// tokens and requests both priced add up
		GModelPricingConditions both = GModelPricingConditions.fromPerTokenPrices(1e-6, 2e-6, 0.01, "USD");
		assertEquals(1.0 + 2.0 + 0.01, both.callCost(1_000_000, 1_000_000), 1e-9);
	}

	@Test
	void unknownOrDynamicPricesLeaveThePricingUnset() {
		assertNull(GModelPricingConditions.fromPerTokenPrices(null, null, null, "USD"));
		// OpenRouter marks a dynamically priced model with -1
		assertNull(GModelPricingConditions.fromPerTokenPrices(-1d, -1d, null, "USD"));
		assertNull(new GModelPricingConditions().callCost(1000, 1000));
	}

	@Test
	void noInputPriceMeansNoTokenCost() {
		assertNull(mtoken(null, 2.00).tokenCost(1000, 1000));
		assertNull(new GModelPricingConditions().tokenCost(1000, 1000));
	}
}
