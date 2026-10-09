/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.llms.usage.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.core.messages.ILLMApiKeyTrafficReader;
import ai.gebo.core.messages.LLMApiKeyTraffic;
import lombok.AllArgsConstructor;

/**
 * {@link ILLMApiKeyTrafficReader} over the daily usage consolidated by
 * {@link LLMUsageDailyAggregationServiceImpl}: the raw records of the finished
 * days are deleted, the daily ones are kept.
 */
@Service
@AllArgsConstructor
public class LLMApiKeyTrafficReaderImpl implements ILLMApiKeyTrafficReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMApiKeyTrafficReaderImpl.class);
	private final LLMDailyUsageDetailRepository dailyRepo;

	private record TrafficKey(String modelTypeCode, String apiSecretCode) {
	}

	@Override
	public List<LLMApiKeyTraffic> readTraffic(LocalDate from, LocalDate toInclusive) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin readTraffic(from=" + from + ", toInclusive=" + toInclusive + ")");
		}
		Map<TrafficKey, Long> tokens = new HashMap<>();
		// The daily documents are keyed by year, month and day: read month by month.
		for (YearMonth month = YearMonth.from(from); !month.isAfter(YearMonth.from(toInclusive)); month = month
				.plusMonths(1)) {
			int dayFrom = month.equals(YearMonth.from(from)) ? from.getDayOfMonth() : 1;
			int dayTo = month.equals(YearMonth.from(toInclusive)) ? toInclusive.getDayOfMonth()
					: month.lengthOfMonth();
			try (Stream<LLMDailyUsageDetail> days = dailyRepo.findDaysOfMonth(
					month.getYear(), month.getMonthValue(), dayFrom, dayTo)) {
				days.forEach(day -> tokens.merge(new TrafficKey(day.getModelTypeCode(), day.getApiSecretCode()),
						day.getTotalToken(), Long::sum));
			}
		}
		List<LLMApiKeyTraffic> traffic = new ArrayList<>();
		tokens.forEach((key, total) -> traffic.add(new LLMApiKeyTraffic(key.modelTypeCode(), key.apiSecretCode(), total)));
		if (LOGGER.isTraceEnabled()) {
			traffic.forEach(x -> LOGGER.trace("traffic " + x));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End readTraffic(from=" + from + ", toInclusive=" + toInclusive + ") rows=" + traffic.size());
		}
		return traffic;
	}
}
