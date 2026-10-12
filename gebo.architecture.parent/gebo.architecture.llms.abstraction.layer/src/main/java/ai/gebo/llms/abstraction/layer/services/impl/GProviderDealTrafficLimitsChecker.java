/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.core.messages.ILLMApiKeyTrafficReader;
import ai.gebo.core.messages.LLMApiKeyTraffic;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
import ai.gebo.llms.abstraction.layer.model.GProviderFlatConditions;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGProviderDealService;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.model.SystemMessageAudience;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;
import lombok.RequiredArgsConstructor;

/**
 * Once a day warns the administrators, through the system messages, of the provider
 * deals whose flat conditions traffic limits were exceeded: the daily limit by the
 * tokens of the day before, the monthly one by the tokens of that day's month up to
 * it.
 * <p>
 * The tokens are read from the usage consolidated by day, where it is available: a
 * deployment without the consolidated usage or the system messages skips the check.
 * The usage is attributed to a deal by the API key it went through; the usage
 * recorded before the key was is attributed to its provider's deal when the provider
 * has only one.
 * <p>
 * Runs every day and at startup, in case the day's run was missed, but publishes a
 * deal's warning at most once a day, whatever the number of runs and instances.
 * Best effort: a failure is logged and never affects the models.
 */
@Component
@RequiredArgsConstructor
public class GProviderDealTrafficLimitsChecker {
	private static final Logger LOGGER = LoggerFactory.getLogger(GProviderDealTrafficLimitsChecker.class);
	/** The source of the system messages published by this checker. */
	public static final String MESSAGES_SOURCE = "llms-provider-deals";
	static final String DAILY_KEY_PREFIX = "traffic-daily:";
	static final String MONTHLY_KEY_PREFIX = "traffic-monthly:";
	private static final double MILLION = 1_000_000d;

	private final ObjectProvider<IGProviderDealService> dealsProvider;
	private final ObjectProvider<ILLMApiKeyTrafficReader> trafficProvider;
	private final ObjectProvider<IGSystemMessagesService> messagesProvider;
	private final ObjectProvider<IGImplementationsRepositoryPattern<? extends IGModelConfigurationSupportService<?, ?, ?, ?>>> modelTypes;

	@EventListener(ApplicationReadyEvent.class)
	public void checkAtStartup() {
		// Startup must not wait on the check.
		Thread.ofVirtual().name("provider-deals-traffic-limits").start(this::dailyCheck);
	}

	@Scheduled(cron = "${ai.gebo.llms.providerDeals.trafficLimitsCheckCron:0 30 0 * * *}")
	public void dailyCheck() {
		try {
			check(LocalDate.now());
		} catch (Throwable e) {
			LOGGER.error("Cannot check the traffic limits of the provider deals, retrying at the next run", e);
		}
	}

	/**
	 * Checks the day before today, and its month up to it, against the traffic limits
	 * of the deals.
	 *
	 * @param today the day the check runs
	 */
	void check(LocalDate today) {
		IGProviderDealService deals = dealsProvider.getIfAvailable();
		ILLMApiKeyTrafficReader traffic = trafficProvider.getIfAvailable();
		IGSystemMessagesService messages = messagesProvider.getIfAvailable();
		if (deals == null || traffic == null || messages == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Provider deals traffic limits not checked: deals=" + (deals != null) + " usage="
						+ (traffic != null) + " system messages=" + (messages != null));
			}
			return;
		}
		List<GProviderDeal> allDeals = deals.findDeals(null);
		List<GProviderDeal> limited = allDeals.stream().filter(GProviderDealTrafficLimitsChecker::hasTrafficLimits)
				.toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin check(today=" + today + ") deals with traffic limits=" + limited.size() + " of "
					+ allDeals.size());
		}
		if (limited.isEmpty()) {
			return;
		}
		LocalDate day = today.minusDays(1);
		LocalDate monthStart = day.withDayOfMonth(1);
		List<LLMApiKeyTraffic> dayTraffic = traffic.readTraffic(day, day);
		List<LLMApiKeyTraffic> monthTraffic = traffic.readTraffic(monthStart, day);
		Map<String, String> providerOfType = providerOfModelType();
		Map<String, Long> dealsPerProvider = allDeals.stream().filter(x -> x.getProviderId() != null)
				.collect(Collectors.groupingBy(GProviderDeal::getProviderId, Collectors.counting()));
		Date startOfToday = toDate(today);
		Set<String> publishedToday = messages.findAll().stream()
				.filter(x -> MESSAGES_SOURCE.equals(x.getSource()) && x.getUpdatedAt() != null
						&& !x.getUpdatedAt().before(startOfToday))
				.map(GSystemMessage::getKey).collect(Collectors.toSet());
		for (GProviderDeal deal : limited) {
			try {
				GProviderFlatConditions flat = deal.getFlatConditions();
				String dealName = "The " + deal.getProviderId() + " deal \"" + deal.getDescription() + "\"";
				if (isLimit(flat.getDailyTrafficLimits())) {
					long tokens = tokensOf(deal, dayTraffic, providerOfType, dealsPerProvider);
					if (tokens > flat.getDailyTrafficLimits() * MILLION) {
						publishOnce(messages, publishedToday, DAILY_KEY_PREFIX + deal.getId(),
								"Provider deal daily traffic limit exceeded",
								dealName + " consumed " + millionsOf(tokens) + " million tokens on " + day
										+ ", over its daily traffic limit of " + format(flat.getDailyTrafficLimits())
										+ " million tokens.",
								toDate(today.plusDays(2)));
					} else if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deal id=" + deal.getId() + " consumed " + tokens + " tokens on " + day
								+ ", within its daily limit");
					}
				}
				if (isLimit(flat.getMonthlyTrafficLimits())) {
					long tokens = tokensOf(deal, monthTraffic, providerOfType, dealsPerProvider);
					if (tokens > flat.getMonthlyTrafficLimits() * MILLION) {
						publishOnce(messages, publishedToday, MONTHLY_KEY_PREFIX + deal.getId(),
								"Provider deal monthly traffic limit exceeded",
								dealName + " consumed " + millionsOf(tokens) + " million tokens from " + monthStart
										+ " to " + day + ", over its monthly traffic limit of "
										+ format(flat.getMonthlyTrafficLimits()) + " million tokens.",
								toDate(monthStart.plusMonths(1).plusDays(1)));
					} else if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deal id=" + deal.getId() + " consumed " + tokens + " tokens from " + monthStart
								+ " to " + day + ", within its monthly limit");
					}
				}
			} catch (Throwable e) {
				LOGGER.error("Cannot check the traffic limits of the provider deal id=" + deal.getId(), e);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End check(today=" + today + ")");
		}
	}

	private void publishOnce(IGSystemMessagesService messages, Set<String> publishedToday, String key,
			String summary, String detail, Date expiresAt) {
		if (publishedToday.contains(key)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Traffic limit warning key=" + key + " already published today");
			}
			return;
		}
		LOGGER.warn(detail);
		messages.publish(new SystemMessagePublication(MESSAGES_SOURCE, key, MsgServerity.warn, summary, detail,
				SystemMessageAudience.ADMINS, true, expiresAt));
	}

	static boolean hasTrafficLimits(GProviderDeal deal) {
		GProviderFlatConditions flat = deal.getFlatConditions();
		return deal.getId() != null && deal.getProviderId() != null && flat != null
				&& (isLimit(flat.getDailyTrafficLimits()) || isLimit(flat.getMonthlyTrafficLimits()));
	}

	private static boolean isLimit(Double millionsOfTokens) {
		return millionsOfTokens != null && millionsOfTokens > 0;
	}

	/**
	 * The tokens of the rows going through the deal's API keys. A row without API key
	 * predates the keys being recorded: it counts when its provider has this deal only.
	 */
	static long tokensOf(GProviderDeal deal, List<LLMApiKeyTraffic> rows, Map<String, String> providerOfType,
			Map<String, Long> dealsPerProvider) {
		List<String> keys = deal.getSecretCodes() != null ? deal.getSecretCodes() : List.of();
		long tokens = 0;
		for (LLMApiKeyTraffic row : rows) {
			String key = row.apiSecretCode();
			boolean sameProvider = deal.getProviderId().equals(providerOfType.get(row.modelTypeCode()));
			boolean counts;
			if (key == null) {
				counts = sameProvider && dealsPerProvider.getOrDefault(deal.getProviderId(), 0L) == 1;
			} else if (GProviderDeal.NO_API_KEY.equals(key)) {
				// The pseudo key is shared by every provider.
				counts = sameProvider && keys.contains(key);
			} else {
				counts = keys.contains(key);
			}
			if (counts) {
				tokens += row.totalToken();
			}
		}
		return tokens;
	}

	/** The provider of every model type code, from the model types declared. */
	private Map<String, String> providerOfModelType() {
		return modelTypes.orderedStream().flatMap(repository -> repository.getImplementations().stream())
				.map(IGModelConfigurationSupportService::getType).filter(Objects::nonNull).map(GModelType.class::cast)
				.filter(type -> type.getCode() != null && type.getProviderId() != null)
				.collect(Collectors.toMap(GModelType::getCode, GModelType::getProviderId, (a, b) -> a));
	}

	private static String millionsOf(long tokens) {
		return format(tokens / MILLION);
	}

	private static String format(double millions) {
		return String.format(Locale.ROOT, "%,.2f", millions);
	}

	private static Date toDate(LocalDate day) {
		return Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
