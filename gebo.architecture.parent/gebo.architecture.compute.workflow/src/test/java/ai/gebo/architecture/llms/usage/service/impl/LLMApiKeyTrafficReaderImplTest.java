/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.llms.usage.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.repository.LLMDailyUsageDetailRepository;
import ai.gebo.core.messages.LLMApiKeyTraffic;

class LLMApiKeyTrafficReaderImplTest {

	private static LLMDailyUsageDetail day(String type, String key, long tokens) {
		LLMDailyUsageDetail detail = new LLMDailyUsageDetail();
		detail.setProviderId(type);
		detail.setApiSecretCode(key);
		detail.setTotalToken(tokens);
		return detail;
	}

	@Test
	void theDaysAreReadMonthByMonthAndSummedPerTypeAndKey() {
		LLMDailyUsageDetailRepository repo = mock(LLMDailyUsageDetailRepository.class);
		when(repo.findDaysOfMonth(2026, 8, 30, 31))
				.thenAnswer(x -> Stream.of(day("regolo-chat", "k1", 10), day("regolo-chat", null, 1)));
		when(repo.findDaysOfMonth(2026, 9, 1, 2))
				.thenAnswer(x -> Stream.of(day("regolo-chat", "k1", 5), day("openai-chat", "k2", 7)));

		List<LLMApiKeyTraffic> traffic = new LLMApiKeyTrafficReaderImpl(repo)
				.readTraffic(LocalDate.of(2026, 8, 30), LocalDate.of(2026, 9, 2)).stream()
				.sorted(Comparator.comparing(LLMApiKeyTraffic::totalToken)).toList();

		assertEquals(List.of(new LLMApiKeyTraffic("regolo-chat", null, 1), new LLMApiKeyTraffic("openai-chat", "k2", 7),
				new LLMApiKeyTraffic("regolo-chat", "k1", 15)), traffic);
	}
}
