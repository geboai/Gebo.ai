/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.core.messages.ILLMApiKeyTrafficReader;
import ai.gebo.core.messages.LLMApiKeyTraffic;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.model.SystemMessageAudience;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;

class GProviderDealTrafficLimitsCheckerTest {
	private static final LocalDate TODAY = LocalDate.of(2026, 9, 29);
	private static final LocalDate YESTERDAY = TODAY.minusDays(1);

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	/** One model type of code "regolo-chat" of provider "regolo.ai". */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ObjectProvider<IGImplementationsRepositoryPattern<? extends IGModelConfigurationSupportService<?, ?, ?, ?>>> types() {
		GChatModelType type = new GChatModelType();
		type.setCode("regolo-chat");
		type.setProviderId("regolo.ai");
		IGModelConfigurationSupportService service = mock(IGModelConfigurationSupportService.class);
		when(service.getType()).thenReturn(type);
		IGImplementationsRepositoryPattern repository = mock(IGImplementationsRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn(List.of(service));
		ObjectProvider provider = mock(ObjectProvider.class);
		when(provider.orderedStream()).thenAnswer(x -> Stream.of(repository));
		return provider;
	}

	private static GProviderDeal deal(String id, Double daily, Double monthly, String... keys) {
		GProviderDeal deal = new GProviderDeal();
		deal.setId(id);
		deal.setProviderId("regolo.ai");
		deal.setDescription("Regolo flat");
		deal.setSecretCodes(new ArrayList<>(List.of(keys)));
		if (daily != null || monthly != null) {
			GProviderFlatConditions flat = new GProviderFlatConditions();
			flat.setCurrencyCode("EUR");
			flat.setMonthlyFlatCost(100d);
			flat.setDailyTrafficLimits(daily);
			flat.setMonthlyTrafficLimits(monthly);
			deal.setFlatConditions(flat);
		}
		return deal;
	}

	private static ILLMApiKeyTrafficReader traffic(List<LLMApiKeyTraffic> day, List<LLMApiKeyTraffic> month) {
		ILLMApiKeyTrafficReader reader = mock(ILLMApiKeyTrafficReader.class);
		when(reader.readTraffic(YESTERDAY, YESTERDAY)).thenReturn(day);
		when(reader.readTraffic(YESTERDAY.withDayOfMonth(1), YESTERDAY)).thenReturn(month);
		return reader;
	}

	private static GProviderDealTrafficLimitsChecker checker(List<GProviderDeal> deals,
			ILLMApiKeyTrafficReader reader, IGSystemMessagesService messages) {
		IGProviderDealService dealService = mock(IGProviderDealService.class);
		when(dealService.findDeals(null)).thenReturn(deals);
		return new GProviderDealTrafficLimitsChecker(provider(dealService), provider(reader), provider(messages),
				types());
	}

	@Test
	void exceededLimitsWarnTheAdministrators() {
		IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
		when(messages.findAll()).thenReturn(List.of());
		ILLMApiKeyTrafficReader reader = traffic(List.of(new LLMApiKeyTraffic("regolo-chat", "key1", 3_000_000)),
				List.of(new LLMApiKeyTraffic("regolo-chat", "key1", 50_000_000)));

		checker(List.of(deal("d1", 2d, 40d, "key1")), reader, messages).check(TODAY);

		ArgumentCaptor<SystemMessagePublication> published = ArgumentCaptor.forClass(SystemMessagePublication.class);
		verify(messages, times(2)).publish(published.capture());
		SystemMessagePublication daily = published.getAllValues().get(0);
		assertEquals(GProviderDealTrafficLimitsChecker.MESSAGES_SOURCE, daily.source());
		assertEquals("traffic-daily:d1", daily.key());
		assertEquals(MsgServerity.warn, daily.severity());
		assertEquals(SystemMessageAudience.ADMINS, daily.audience());
		assertTrue(daily.detail().contains("3.00 million tokens on 2026-09-28"), daily.detail());
		assertEquals("traffic-monthly:d1", published.getAllValues().get(1).key());
		assertTrue(published.getAllValues().get(1).detail().contains("from 2026-09-01 to 2026-09-28"));
	}

	@Test
	void trafficWithinTheLimitsOrOfOtherKeysDoesNotWarn() {
		IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
		when(messages.findAll()).thenReturn(List.of());
		ILLMApiKeyTrafficReader reader = traffic(
				List.of(new LLMApiKeyTraffic("regolo-chat", "key1", 1_000_000),
						new LLMApiKeyTraffic("regolo-chat", "other", 9_000_000)),
				List.of(new LLMApiKeyTraffic("regolo-chat", "key1", 10_000_000)));

		checker(List.of(deal("d1", 2d, 40d, "key1"), deal("d2", null, null, "other")), reader, messages)
				.check(TODAY);

		verify(messages, never()).publish(any());
	}

	@Test
	void aWarningIsPublishedOnceADay() {
		GSystemMessage already = new GSystemMessage();
		already.setSource(GProviderDealTrafficLimitsChecker.MESSAGES_SOURCE);
		already.setKey("traffic-daily:d1");
		already.setUpdatedAt(Date.from(TODAY.atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(60)));
		IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
		when(messages.findAll()).thenReturn(List.of(already));
		ILLMApiKeyTrafficReader reader = traffic(List.of(new LLMApiKeyTraffic("regolo-chat", "key1", 3_000_000)),
				List.of());

		checker(List.of(deal("d1", 2d, null, "key1")), reader, messages).check(TODAY);

		verify(messages, never()).publish(any());
	}

	@Test
	void withoutTrafficLimitsTheUsageIsNotRead() {
		IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
		ILLMApiKeyTrafficReader reader = mock(ILLMApiKeyTrafficReader.class);

		checker(List.of(deal("d1", null, null, "key1")), reader, messages).check(TODAY);

		verify(reader, never()).readTraffic(any(), any());
		verify(messages, never()).publish(any());
	}

	@Test
	void unattributedUsageCountsForTheOnlyDealOfItsProvider() {
		Map<String, String> providerOfType = Map.of("regolo-chat", "regolo.ai", "openai-chat", "openai");
		List<LLMApiKeyTraffic> rows = List.of(new LLMApiKeyTraffic("regolo-chat", null, 5),
				new LLMApiKeyTraffic("openai-chat", null, 7),
				new LLMApiKeyTraffic("openai-chat", GProviderDeal.NO_API_KEY, 11),
				new LLMApiKeyTraffic("regolo-chat", GProviderDeal.NO_API_KEY, 13));
		GProviderDeal deal = deal("d1", 1d, null, "key1", GProviderDeal.NO_API_KEY);

		assertEquals(5 + 13,
				GProviderDealTrafficLimitsChecker.tokensOf(deal, rows, providerOfType, Map.of("regolo.ai", 1L)));
		assertEquals(13,
				GProviderDealTrafficLimitsChecker.tokensOf(deal, rows, providerOfType, Map.of("regolo.ai", 2L)));
	}

	@Test
	void aFailingUsageReadNeverEscapes() {
		IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
		ILLMApiKeyTrafficReader reader = mock(ILLMApiKeyTrafficReader.class);
		when(reader.readTraffic(any(), any())).thenThrow(new IllegalStateException("store down"));

		checker(List.of(deal("d1", 2d, null, "key1")), reader, messages).dailyCheck();

		verify(messages, never()).publish(any());
	}
}
