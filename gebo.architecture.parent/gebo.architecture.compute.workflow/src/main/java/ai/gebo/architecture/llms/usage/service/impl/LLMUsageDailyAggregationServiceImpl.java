package ai.gebo.architecture.llms.usage.service.impl;

import ai.gebo.core.messages.LLMCallOutcome;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.architecture.llms.usage.service.ILLMUsageDailyAggregationService;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;

/**
 * The default {@link ILLMUsageDailyAggregationService}: folds raw
 * {@link LLMUsageDetail} rows written by {@link LLMUsageConcentratorReceiverFactory}
 * into {@link LLMDailyUsageDetail} daily aggregates. It holds no schedule of its
 * own: {@link LLMUsageConsolidationTicker} invokes it. Extend it and override the
 * protected hooks ({@link #zone()}, {@link #resolveOutcome(LLMUsageDetail)},
 * {@link #resolveModelType(LLMUsageDetail)}) to adjust how records are bucketed.
 * <p>
 * Every run (tick) <b>rebuilds</b> the daily aggregates of yesterday and today from the
 * raw rows and overwrites them, it never adds to them. The raw rows of both days
 * are kept until the day after tomorrow, so the rebuild always sees every row of
 * the days it writes, which makes the tick idempotent: running it again produces
 * the same documents. Adding to the existing documents instead counted every raw
 * row of today once per tick (about 144 times a day), and lost the rows written
 * after the last tick before midnight.
 * <p>
 * Yesterday is rebuilt as well as today so that the rows written between the last
 * tick of a day and midnight are consolidated by the first tick of the next day.
 */
@Component
@AllArgsConstructor
public class LLMUsageDailyAggregationServiceImpl implements ILLMUsageDailyAggregationService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * The model type given to a raw record that carries none. Until every model type
	 * was accounted only chat calls were recorded, so a record without a type is a
	 * chat call.
	 */
	public static final ModelType LEGACY_MODEL_TYPE = ModelType.CHAT;
	protected final LLMUsageDetailRepository usageRepo;
	protected final LLMDailyUsageDetailRepository consolidatedRepo;

	/**
	 * The time zone the calendar days of the aggregates are cut in. Defaults to the
	 * JVM's zone.
	 */
	protected ZoneId zone() {
		return ZoneId.systemDefault();
	}

	/**
	 * The outcome a raw record is aggregated under. Records written before the
	 * outcome existed carry null: they are folded into SUCCESS rather than creating a
	 * third, meaningless bucket.
	 */
	protected LLMCallOutcome resolveOutcome(LLMUsageDetail detail) {
		return detail.getOutcome() != null ? detail.getOutcome() : LLMCallOutcome.SUCCESS;
	}

	/**
	 * The model type a raw record is aggregated under: its own, or
	 * {@link #LEGACY_MODEL_TYPE} for a record that carries none.
	 */
	protected ModelType resolveModelType(LLMUsageDetail detail) {
		return detail.getModelType() != null ? detail.getModelType() : LEGACY_MODEL_TYPE;
	}

	@Override
	public void consolidate() {
		ZoneId zone = zone();
		LocalDate today = LocalDate.now(zone);
		long yesterdayFirstMillisecond = today.minusDays(1).atStartOfDay(zone).toInstant().toEpochMilli();
		long todayLastMillisecond = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin consolidate() rebuilding the daily usage of " + today.minusDays(1) + " and "
					+ today + " window=[" + yesterdayFirstMillisecond + "," + todayLastMillisecond + "]");
		}

		// Aggregate the raw values grouping by
		// providerId, username, model, callerStack, modelType, outcome, year, month, day.
		Map<ConsolidationKey, DailyAccumulator> grouped = new HashMap<>();
		long rawRows = 0;
		try (Stream<LLMUsageDetail> stream = usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(
				yesterdayFirstMillisecond, todayLastMillisecond)) {
			rawRows = stream.mapToLong(detail -> {
				LocalDate date = Instant.ofEpochMilli(detail.getTimestamp()).atZone(zone).toLocalDate();
				LLMCallOutcome outcome = resolveOutcome(detail);
				ModelType modelType = resolveModelType(detail);
				ConsolidationKey key = new ConsolidationKey(detail.getProviderId(), detail.getUsername(),
						detail.getModel(), detail.getCallerStack(), modelType, outcome,
						date.getYear(), date.getMonthValue(), date.getDayOfMonth());
				grouped.computeIfAbsent(key, k -> new DailyAccumulator()).add(detail);
				return 1;
			}).sum();
		}

		// Overwrite the daily document of every key with the sums rebuilt from its raw
		// rows. A daily document of yesterday or today whose key has no raw row left is
		// not touched: it was written before this rebuild existed, when the raw rows of
		// a finished day were deleted at midnight, and the rebuild has nothing to
		// replace it with.
		for (Map.Entry<ConsolidationKey, DailyAccumulator> entry : grouped.entrySet()) {
			ConsolidationKey key = entry.getKey();
			LLMDailyUsageDetail target = consolidatedRepo
					.findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndOutcomeAndYearAndMonthAndDay(
							key.providerId(), key.username(), key.model(), key.callerStack(), key.modelType(),
							key.outcome(), key.year(), key.month(), key.day())
					.orElseGet(() -> newDailyUsageDetail(key));
			entry.getValue().writeInto(target);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<DAILY_USAGE key=" + key + ">");
				LOGGER.trace("requests=" + target.getNrRequests() + " inputToken=" + target.getInputToken()
						+ " outputToken=" + target.getOutputToken() + " totalToken=" + target.getTotalToken()
						+ " responseTimeMin=" + target.getResponseTimeMin() + " responseTimeAvg="
						+ target.getResponseTimeAvg() + " responseTimeMax=" + target.getResponseTimeMax()
						+ " timeToFirstToken samples=" + target.getTimeToFirstTokenSamples() + " min="
						+ target.getTimeToFirstTokenMin() + " avg=" + target.getTimeToFirstTokenAvg() + " max="
						+ target.getTimeToFirstTokenMax());
				LOGGER.trace("</DAILY_USAGE>");
			}
			consolidatedRepo.save(target);
		}

		// Only the raw rows of the days before yesterday are deleted: yesterday and
		// today must stay complete for the next rebuild.
		usageRepo.deleteByTimestampLessThanEqual(yesterdayFirstMillisecond - 1);
		if (LOGGER.isDebugEnabled()) {
			Map<ModelType, Long> requestsByType = new java.util.EnumMap<>(ModelType.class);
			grouped.forEach((key, accumulator) -> requestsByType.merge(key.modelType(), accumulator.nrRequests,
					Long::sum));
			LOGGER.debug("End consolidate() rebuilt " + grouped.size() + " daily documents from " + rawRows
					+ " raw rows, requests by model type=" + requestsByType + ", deleted the raw rows before "
					+ today.minusDays(1));
		}
	}

	private static LLMDailyUsageDetail newDailyUsageDetail(ConsolidationKey key) {
		LLMDailyUsageDetail daily = new LLMDailyUsageDetail();
		daily.setProviderId(key.providerId());
		daily.setUsername(key.username());
		daily.setModel(key.model());
		daily.setCallerStack(key.callerStack());
		daily.setModelType(key.modelType());
		daily.setOutcome(key.outcome());
		daily.setYear(key.year());
		daily.setMonth(key.month());
		daily.setDay(key.day());
		return daily;
	}

	private record ConsolidationKey(String providerId, String username, String model, String callerStack,
			ModelType modelType, LLMCallOutcome outcome, int year, int month, int day) {
	}

	static final class DailyAccumulator {
		private long inputToken;
		private long outputToken;
		private long totalToken;
		private long nrRequests;
		private long responseTimeSum;
		private long responseTimeMin = Long.MAX_VALUE;
		private long responseTimeMax = Long.MIN_VALUE;
		// Over the calls that measured a time to first token only: the others would
		// otherwise drag the average toward zero.
		private long timeToFirstTokenSamples;
		private long timeToFirstTokenSum;
		private long timeToFirstTokenMin = Long.MAX_VALUE;
		private long timeToFirstTokenMax = Long.MIN_VALUE;

		void add(LLMUsageDetail detail) {
			inputToken += detail.getInputToken();
			outputToken += detail.getOutputToken();
			totalToken += detail.getTotalToken();
			nrRequests++;
			responseTimeSum += detail.getResponseTime();
			responseTimeMin = Math.min(responseTimeMin, detail.getResponseTime());
			responseTimeMax = Math.max(responseTimeMax, detail.getResponseTime());
			Long timeToFirstToken = detail.getTimeToFirstToken();
			if (timeToFirstToken != null) {
				timeToFirstTokenSamples++;
				timeToFirstTokenSum += timeToFirstToken;
				timeToFirstTokenMin = Math.min(timeToFirstTokenMin, timeToFirstToken);
				timeToFirstTokenMax = Math.max(timeToFirstTokenMax, timeToFirstToken);
			}
		}

		/**
		 * Replaces the sums of the target with the ones accumulated here, which are
		 * complete for the target's day.
		 */
		void writeInto(LLMDailyUsageDetail target) {
			target.setInputToken(inputToken);
			target.setOutputToken(outputToken);
			target.setTotalToken(totalToken);
			target.setNrRequests(nrRequests);
			target.setResponseTimeAvg(nrRequests > 0 ? responseTimeSum / nrRequests : 0);
			target.setResponseTimeMin(nrRequests > 0 ? responseTimeMin : 0);
			target.setResponseTimeMax(nrRequests > 0 ? responseTimeMax : 0);
			target.setTimeToFirstTokenSamples(timeToFirstTokenSamples);
			boolean timed = timeToFirstTokenSamples > 0;
			target.setTimeToFirstTokenAvg(timed ? timeToFirstTokenSum / timeToFirstTokenSamples : null);
			target.setTimeToFirstTokenMin(timed ? timeToFirstTokenMin : null);
			target.setTimeToFirstTokenMax(timed ? timeToFirstTokenMax : null);
		}
	}

}
