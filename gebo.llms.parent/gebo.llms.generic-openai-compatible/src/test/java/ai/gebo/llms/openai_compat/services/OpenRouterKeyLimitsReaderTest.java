package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GProviderKeyLimit;
import ai.gebo.openrouter.client.OpenRouterAiClient;
import ai.gebo.openrouter.client.model.OpenRouterKeyInfo;

class OpenRouterKeyLimitsReaderTest {

	private static OpenRouterKeyLimitsReader readerAnswering(OpenRouterKeyInfo info) {
		OpenRouterAiClient client = mock(OpenRouterAiClient.class);
		when(client.getCurrentKey()).thenReturn(info);
		return new OpenRouterKeyLimitsReader() {
			@Override
			protected OpenRouterAiClient newClient(String clearApiKey) {
				return client;
			}
		};
	}

	private static OpenRouterKeyInfo key(Double limit, String limitReset) {
		OpenRouterKeyInfo info = new OpenRouterKeyInfo();
		info.setLimit(limit);
		info.setLimitReset(limitReset);
		return info;
	}

	@Test
	void theKeyLimitIsReadInUsdWithItsResetPeriod() {
		GProviderKeyLimit limit = readerAnswering(key(25d, "monthly")).readLimit("sk-or-1");
		assertEquals(new GProviderKeyLimit("USD", 25d, GProviderKeyLimit.Period.MONTHLY), limit);
		assertEquals(GProviderKeyLimit.Period.DAILY, readerAnswering(key(1d, "daily")).readLimit("k").getPeriod());
		assertEquals(GProviderKeyLimit.Period.WEEKLY, readerAnswering(key(1d, "weekly")).readLimit("k").getPeriod());
	}

	@Test
	void aLimitThatNeverResetsIsATotalOne() {
		assertEquals(GProviderKeyLimit.Period.TOTAL, readerAnswering(key(5d, null)).readLimit("k").getPeriod());
	}

	@Test
	void anUnlimitedKeyHasNoAmount() {
		assertNull(readerAnswering(key(null, null)).readLimit("k").getAmount());
	}

	@Test
	void anUnknownAnswerIsAnError() {
		assertThrows(IllegalStateException.class, () -> readerAnswering(key(5d, "yearly")).readLimit("k"));
		assertThrows(IllegalStateException.class, () -> readerAnswering(null).readLimit("k"));
	}
}
