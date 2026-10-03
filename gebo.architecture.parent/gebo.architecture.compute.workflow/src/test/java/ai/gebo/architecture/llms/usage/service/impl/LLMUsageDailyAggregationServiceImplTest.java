package ai.gebo.architecture.llms.usage.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

	private static LLMUsageDetail raw(long timestamp, long tokens, long responseTime) {
		LLMUsageDetail detail = new LLMUsageDetail();
		detail.setProviderId("openai");
		detail.setModelTypeCode("chatgpt-OpenAI");
		detail.setUsername("user");
		detail.setModel("gpt");
		detail.setCallerStack("stack");
		detail.setModelType(ModelType.CHAT);
		detail.setOutcome(LLMCallOutcome.SUCCESS);
		detail.setInputToken(tokens);
		detail.setOutputToken(tokens);
		detail.setTotalToken(2 * tokens);
		detail.setResponseTime(responseTime);
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
		when(dailyRepo.findByModelTypeCodeAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt()))
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
		assertEquals(100, daily.getResponseTimeMin());
		assertEquals(300, daily.getResponseTimeMax());
		assertEquals(200, daily.getResponseTimeAvg());
		assertEquals("openai", daily.getProviderId());
		assertEquals("chatgpt-OpenAI", daily.getModelTypeCode());
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
		when(dailyRepo.findByModelTypeCodeAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.empty());
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
	void timeToFirstTokenIsAggregatedOverTheCallsThatMeasuredIt() {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		long now = System.currentTimeMillis();
		LLMUsageDetail streamedFast = raw(now, 10, 1000);
		streamedFast.setTimeToFirstToken(200L);
		LLMUsageDetail streamedSlow = raw(now, 10, 3000);
		streamedSlow.setTimeToFirstToken(600L);
		LLMUsageDetail blocking = raw(now, 10, 2000);
		List<LLMUsageDetail> rows = List.of(streamedFast, streamedSlow, blocking);
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> new ArrayList<>(rows).stream());
		AtomicReference<LLMDailyUsageDetail> stored = new AtomicReference<>();
		when(dailyRepo.findByModelTypeCodeAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt()))
				.thenAnswer(invocation -> Optional.ofNullable(stored.get()));
		when(dailyRepo.save(any())).thenAnswer(invocation -> {
			stored.set(invocation.getArgument(0));
			return invocation.getArgument(0);
		});

		new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo).consolidate();

		LLMDailyUsageDetail daily = stored.get();
		// The response time covers every call, the time to first token only the two
		// streamed ones: the blocking call must not drag its average toward zero.
		assertEquals(3, daily.getNrRequests());
		assertEquals(2000, daily.getResponseTimeAvg());
		assertEquals(2, daily.getTimeToFirstTokenSamples());
		assertEquals(200L, daily.getTimeToFirstTokenMin());
		assertEquals(600L, daily.getTimeToFirstTokenMax());
		assertEquals(400L, daily.getTimeToFirstTokenAvg());
	}

	private LLMDailyUsageDetail consolidateOne(List<LLMUsageDetail> rows) {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> new ArrayList<>(rows).stream());
		when(dailyRepo.findByModelTypeCodeAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.empty());
		AtomicReference<LLMDailyUsageDetail> stored = new AtomicReference<>();
		when(dailyRepo.save(any())).thenAnswer(invocation -> {
			stored.set(invocation.getArgument(0));
			return invocation.getArgument(0);
		});
		new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo).consolidate();
		return stored.get();
	}

	@Test
	void costIsSummedOverThePricedCalls() {
		long now = System.currentTimeMillis();
		LLMUsageDetail a = raw(now, 10, 100);
		a.setCost(0.25);
		a.setCurrencyCode("USD");
		LLMUsageDetail b = raw(now, 10, 100);
		b.setCost(0.50);
		b.setCurrencyCode("USD");
		LLMUsageDetail unpriced = raw(now, 10, 100);

		LLMDailyUsageDetail daily = consolidateOne(List.of(a, b, unpriced));

		assertEquals(3, daily.getNrRequests());
		assertEquals(2, daily.getCostSamples());
		assertEquals(0.75, daily.getCost(), 1e-12);
		assertEquals("USD", daily.getCurrencyCode());
	}

	@Test
	void costsInSeveralCurrenciesAreNotSummed() {
		long now = System.currentTimeMillis();
		LLMUsageDetail usd = raw(now, 10, 100);
		usd.setCost(0.25);
		usd.setCurrencyCode("USD");
		LLMUsageDetail eur = raw(now, 10, 100);
		eur.setCost(0.25);
		eur.setCurrencyCode("EUR");

		LLMDailyUsageDetail daily = consolidateOne(List.of(usd, eur));

		assertEquals(2, daily.getCostSamples());
		assertNull(daily.getCost());
		assertNull(daily.getCurrencyCode());
	}

	@Test
	void noStreamedCallLeavesTheTimeToFirstTokenUnset() {
		LLMUsageDetailRepository usageRepo = mock(LLMUsageDetailRepository.class);
		LLMDailyUsageDetailRepository dailyRepo = mock(LLMDailyUsageDetailRepository.class);
		long now = System.currentTimeMillis();
		when(usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(anyLong(), anyLong()))
				.thenAnswer(invocation -> List.of(raw(now, 10, 1000)).stream());
		AtomicReference<LLMDailyUsageDetail> stored = new AtomicReference<>();
		when(dailyRepo.findByModelTypeCodeAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndApiSecretCodeAndYearAndMonthAndDay(
				any(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.empty());
		when(dailyRepo.save(any())).thenAnswer(invocation -> {
			stored.set(invocation.getArgument(0));
			return invocation.getArgument(0);
		});

		new LLMUsageDailyAggregationServiceImpl(usageRepo, dailyRepo).consolidate();

		LLMDailyUsageDetail daily = stored.get();
		assertEquals(0, daily.getTimeToFirstTokenSamples());
		assertNull(daily.getTimeToFirstTokenMin());
		assertNull(daily.getTimeToFirstTokenAvg());
		assertNull(daily.getTimeToFirstTokenMax());
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
