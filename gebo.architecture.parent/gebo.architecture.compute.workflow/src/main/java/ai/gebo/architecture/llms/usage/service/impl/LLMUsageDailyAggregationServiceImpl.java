package ai.gebo.architecture.llms.usage.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.architecture.llms.usage.repository.LLMUsageDetailRepository;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;

/**
 * Periodically folds raw {@link LLMUsageDetail} rows written by
 * {@link LLMUsageConcentratorReceiverFactory} into {@link LLMDailyUsageDetail}
 * daily aggregates. Moved verbatim from the old
 * {@code LLMSUsageCrudServiceImpl.consolidateTick()} in
 * {@code gebo.architecture.llms.abstraction.layer}.
 */
@Component
@AllArgsConstructor
public class LLMUsageDailyAggregationServiceImpl {
	private final LLMUsageDetailRepository usageRepo;
	private final LLMDailyUsageDetailRepository consolidatedRepo;

	@Scheduled(initialDelay = 5000, fixedRate = 10 * 60 * 1000)
	public void consolidateTick() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

		// Only completed days are consolidated: everything up to the last
		// millisecond of yesterday.
		long lastMillisOfYesterday = today.atStartOfDay(zone).toInstant().toEpochMilli() - 1;
		long todayFirstMillisecond = lastMillisOfYesterday + 1;
		long todayLastMillisecond = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1;
		;
		// Aggregate the raw values grouping by
		// providerId, username, model, callerStack, modelType, year, month, day.
		Map<ConsolidationKey, DailyAccumulator> grouped = new HashMap<>();
		try (Stream<LLMUsageDetail> stream = usageRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(
				todayFirstMillisecond, todayLastMillisecond)) {
			stream.forEach(detail -> {
				LocalDate date = Instant.ofEpochMilli(detail.getTimestamp()).atZone(zone).toLocalDate();
				ConsolidationKey key = new ConsolidationKey(detail.getProviderId(), detail.getUsername(),
						detail.getModel(), detail.getCallerStack(), detail.getModelType(), date.getYear(),
						date.getMonthValue(), date.getDayOfMonth());
				grouped.computeIfAbsent(key, k -> new DailyAccumulator()).add(detail);
			});
		}
		if (grouped.isEmpty()) {
			return;
		}

		// Complete the sums of the target daily documents, merging into any
		// previously consolidated record for the same key.
		for (Map.Entry<ConsolidationKey, DailyAccumulator> entry : grouped.entrySet()) {
			ConsolidationKey key = entry.getKey();
			LLMDailyUsageDetail target = consolidatedRepo
					.findByProviderIdAndUsernameAndModelAndCallerStackAndModelTypeAndYearAndMonthAndDay(
							key.providerId(), key.username(), key.model(), key.callerStack(), key.modelType(),
							key.year(), key.month(), key.day())
					.orElseGet(() -> newDailyUsageDetail(key));
			entry.getValue().mergeInto(target);
			consolidatedRepo.save(target);
		}

		// The processed raw details have been folded into the daily documents,
		// remove every detail belonging to an already-consolidated day.
		usageRepo.deleteByTimestampLessThanEqual(lastMillisOfYesterday);
	}

	private static LLMDailyUsageDetail newDailyUsageDetail(ConsolidationKey key) {
		LLMDailyUsageDetail daily = new LLMDailyUsageDetail();
		daily.setProviderId(key.providerId());
		daily.setUsername(key.username());
		daily.setModel(key.model());
		daily.setCallerStack(key.callerStack());
		daily.setModelType(key.modelType());
		daily.setYear(key.year());
		daily.setMonth(key.month());
		daily.setDay(key.day());
		return daily;
	}

	private record ConsolidationKey(String providerId, String username, String model, String callerStack,
			ModelType modelType, int year, int month, int day) {
	}

	private static final class DailyAccumulator {
		private long inputToken;
		private long outputToken;
		private long totalToken;
		private long nrRequests;
		private long latencySum;
		private long latencyMin = Long.MAX_VALUE;
		private long latencyMax = Long.MIN_VALUE;

		void add(LLMUsageDetail detail) {
			inputToken += detail.getInputToken();
			outputToken += detail.getOutputToken();
			totalToken += detail.getTotalToken();
			nrRequests++;
			latencySum += detail.getLatency();
			latencyMin = Math.min(latencyMin, detail.getLatency());
			latencyMax = Math.max(latencyMax, detail.getLatency());
		}

		void mergeInto(LLMDailyUsageDetail target) {
			long existingNr = target.getNrRequests();
			long existingLatencySum = target.getLatencyAvg() * existingNr;
			long combinedNr = existingNr + nrRequests;

			target.setInputToken(target.getInputToken() + inputToken);
			target.setOutputToken(target.getOutputToken() + outputToken);
			target.setTotalToken(target.getTotalToken() + totalToken);
			target.setNrRequests(combinedNr);
			target.setLatencyAvg(combinedNr > 0 ? (existingLatencySum + latencySum) / combinedNr : 0);
			if (existingNr > 0) {
				target.setLatencyMin(Math.min(target.getLatencyMin(), latencyMin));
				target.setLatencyMax(Math.max(target.getLatencyMax(), latencyMax));
			} else {
				target.setLatencyMin(latencyMin);
				target.setLatencyMax(latencyMax);
			}
		}
	}

}
