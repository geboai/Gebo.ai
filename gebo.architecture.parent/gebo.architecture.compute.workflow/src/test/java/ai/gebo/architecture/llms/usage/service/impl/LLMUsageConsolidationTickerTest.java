package ai.gebo.architecture.llms.usage.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.architecture.llms.usage.service.ILLMUsageDailyAggregationService;

/**
 * Pins which consolidation the ticker runs: the default alone, a replacement over the
 * default whatever the registration order, never two replacements.
 */
class LLMUsageConsolidationTickerTest {

	private static LLMUsageDailyAggregationServiceImpl defaultService() {
		return new LLMUsageDailyAggregationServiceImpl(mock(LLMUsageDetailRepository.class),
				mock(LLMDailyUsageDetailRepository.class));
	}

	/** A replacement made by extending the default. */
	static class ExtendedService extends LLMUsageDailyAggregationServiceImpl {
		ExtendedService() {
			super(mock(LLMUsageDetailRepository.class), mock(LLMDailyUsageDetailRepository.class));
		}
	}

	@Test
	void theDefaultRunsWhenAlone() {
		LLMUsageDailyAggregationServiceImpl only = defaultService();
		assertSame(only, LLMUsageConsolidationTicker.choose(List.of(only)));
	}

	@Test
	void aReplacementWinsWhateverTheOrder() {
		LLMUsageDailyAggregationServiceImpl standard = defaultService();
		ILLMUsageDailyAggregationService replacement = mock(ILLMUsageDailyAggregationService.class);
		assertSame(replacement, LLMUsageConsolidationTicker.choose(List.of(standard, replacement)));
		assertSame(replacement, LLMUsageConsolidationTicker.choose(List.of(replacement, standard)));
	}

	@Test
	void anExtensionOfTheDefaultCountsAsReplacement() {
		LLMUsageDailyAggregationServiceImpl standard = defaultService();
		ExtendedService extended = new ExtendedService();
		assertSame(extended, LLMUsageConsolidationTicker.choose(List.of(standard, extended)));
	}

	@Test
	void twoReplacementsAreRefused() {
		assertThrows(IllegalStateException.class,
				() -> LLMUsageConsolidationTicker.choose(List.of(defaultService(),
						mock(ILLMUsageDailyAggregationService.class), new ExtendedService())));
	}

	@Test
	void aFailedRunDoesNotStopTheSchedule() {
		ILLMUsageDailyAggregationService failing = mock(ILLMUsageDailyAggregationService.class);
		doThrow(new IllegalStateException("mongo down")).when(failing).consolidate();
		LLMUsageConsolidationTicker ticker = new LLMUsageConsolidationTicker(List.of(failing));

		ticker.tick();
		ticker.tick();

		verify(failing, times(2)).consolidate();
	}
}
