package ai.gebo.architecture.llms.usage.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.model.ModelType;

/**
 * Pins that the daily consolidation rebuilds the daily documents from the raw rows
 * instead of adding to them, so running it on every tick does not multiply the
 * counts.
 */
class LLMUsageDailyAggregationServiceImplTest {

	private static LLMUsageDetail raw(long timestamp, long tokens, long latency) {
		LLMUsageDetail detail = new LLMUsageDetail();
		detail.setProviderId("openai");
		detail.setUsername("user");
		detail.setModel("gpt");
		detail.setCallerStack("stack");
		detail.setModelType(ModelType.CHAT);
		detail.setOutcome(LLMCallOutcome.SUCCESS);
		detail.setInputToken(tokens);
		detail.setOutputToken(tokens);
		detail.setTotalToken(2 * tokens);
		detail.setLatency(latency);
		detail.setTimestamp(timestamp);
		return detail;
	}

	@Test
	void repeatedTicksRebuildTheSameDailyDocument() {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		long now = System.currentTimeMillis();
		List<LLMUsageDetail> rows = List.of(raw(now, 10, 100), raw(now, 30, 300));
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> new ArrayList<>(rows).stream());
		// A one document store: what the last save wrote is what the next lookup finds.
		AtomicReference<LLMDailyUsageDetail> stored = new AtomicReference<>();
		when(dailyRepo.findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt()))
				.thenAnswer(invocation -> Optional.ofNullable(stored.get()));
		when(dailyRepo.save(any())).thenAnswer(invocation -> {
			stored.set(invocation.getArgument(0));
			return invocation.getArgument(0);
		});
		LLMUsageDailyAggregationServiceImpl service = new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo);

		service.consolidate();
		service.consolidate();
		service.consolidate();

		LLMDailyUsageDetail daily = stored.get();
		assertEquals(2, daily.getNrRequests());
		assertEquals(40, daily.getInputToken());
		assertEquals(40, daily.getOutputToken());
		assertEquals(80, daily.getTotalToken());
		assertEquals(100, daily.getLatencyMin());
		assertEquals(300, daily.getLatencyMax());
		assertEquals(200, daily.getLatencyAvg());
	}

	@Test
	void eachModelTypeGetsItsOwnDailyDocumentAndUntypedRowsCountAsChat() {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		long now = System.currentTimeMillis();
		LLMUsageDetail chat = raw(now, 10, 100);
		LLMUsageDetail legacy = raw(now, 5, 50);
		legacy.setModelType(null);
		LLMUsageDetail embedding = raw(now, 7, 20);
		embedding.setModelType(ModelType.EMBEDDING);
		List<LLMUsageDetail> rows = List.of(chat, legacy, embedding);
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> new ArrayList<>(rows).stream());
		when(dailyRepo.findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.empty());
		List<LLMDailyUsageDetail> saved = new ArrayList<>();
		when(dailyRepo.save(any())).thenAnswer(invocation -> {
			saved.add(invocation.getArgument(0));
			return invocation.getArgument(0);
		});
		LLMUsageDailyAggregationServiceImpl service = new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo);

		service.consolidate();

		assertEquals(2, saved.size());
		LLMDailyUsageDetail chatDaily = saved.stream().filter(d -> d.getModelType() == ModelType.CHAT).findFirst()
				.orElseThrow();
		LLMDailyUsageDetail embeddingDaily = saved.stream().filter(d -> d.getModelType() == ModelType.EMBEDDING)
				.findFirst().orElseThrow();
		assertEquals(2, chatDaily.getNrRequests());
		assertEquals(15, chatDaily.getInputToken());
		assertEquals(1, embeddingDaily.getNrRequests());
		assertEquals(7, embeddingDaily.getInputToken());
	}

	@Test
	void rawRowsOfYesterdayAndTodayAreKept() {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> new ArrayList<LLMUsageDetail>().stream());
		LLMUsageDailyAggregationServiceImpl service = new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo);

		service.consolidate();

		ZoneId zone = ZoneId.systemDefault();
		long yesterdayStart = LocalDate.now(zone).minusDays(1).atStartOfDay(zone).toInstant().toEpochMilli();
		long todayEnd = LocalDate.now(zone).plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1;
		verify(usageRepo).findByTimestampGreaterThanEqualAndTimestampLessThanEqual(yesterdayStart, todayEnd);
		verify(usageRepo).deleteByTimestampLessThanEqual(yesterdayStart - 1);
	}
}
