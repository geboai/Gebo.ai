package ai.gebo.architecture.llms.usage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import ai.gebo.architecture.llms.usage.service.ILLMUsageDailyAggregationService;

/**
 * The periodic trigger of the LLM usage consolidation, kept apart from the
 * consolidation logic so that the logic can be replaced without ever running twice.
 * <p>
 * It invokes exactly one {@link ILLMUsageDailyAggregationService}: the replacement
 * one when another bean implementing the interface is published, the default
 * {@link LLMUsageDailyAggregationServiceImpl} otherwise. The choice is made from the
 * beans present, not from their registration order, so it does not depend on which
 * one component scanning meets first. Two replacements at once are refused at
 * startup, since only one of them could run.
 * <p>
 * Configurable through properties:
 * <ul>
 * <li>{@code ai.gebo.llms.usage.consolidation.enabled} (default {@code true}): set to
 * {@code false} to drop this trigger entirely, e.g. to schedule the consolidation
 * another way;</li>
 * <li>{@code ai.gebo.llms.usage.consolidation.initialDelay} (ms, default 5000);</li>
 * <li>{@code ai.gebo.llms.usage.consolidation.fixedRate} (ms, default 600000).</li>
 * </ul>
 */
@Component
@ConditionalOnProperty(prefix = "ai.gebo.llms.usage.consolidation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LLMUsageConsolidationTicker {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMUsageConsolidationTicker.class);
	private final ILLMUsageDailyAggregationService aggregationService;

	public LLMUsageConsolidationTicker(List<ILLMUsageDailyAggregationService> implementations) {
		this.aggregationService = choose(implementations);
		LOGGER.info("LLM usage consolidation performed by "
				+ ClassUtils.getUserClass(aggregationService).getName());
	}

	/**
	 * The replacement implementation if there is one, the default otherwise. A
	 * subclass of the default counts as a replacement: it is how the default is
	 * extended.
	 */
	static ILLMUsageDailyAggregationService choose(List<ILLMUsageDailyAggregationService> implementations) {
		if (implementations == null || implementations.isEmpty()) {
			throw new IllegalStateException("No " + ILLMUsageDailyAggregationService.class.getSimpleName()
					+ " available for the LLM usage consolidation");
		}
		List<ILLMUsageDailyAggregationService> replacements = implementations.stream()
				.filter(x -> ClassUtils.getUserClass(x) != LLMUsageDailyAggregationServiceImpl.class)
				.collect(Collectors.toList());
		if (replacements.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No replacement LLM usage consolidation published, using the default");
			}
			return implementations.get(0);
		}
		if (replacements.size() > 1) {
			throw new IllegalStateException("More than one replacement of "
					+ ILLMUsageDailyAggregationService.class.getSimpleName() + " published, only one can run: "
					+ replacements.stream().map(x -> ClassUtils.getUserClass(x).getName())
							.collect(Collectors.joining(", ")));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Replacement LLM usage consolidation chosen over the default: "
					+ ClassUtils.getUserClass(replacements.get(0)).getName());
		}
		return replacements.get(0);
	}

	@Scheduled(initialDelayString = "${ai.gebo.llms.usage.consolidation.initialDelay:5000}", fixedRateString = "${ai.gebo.llms.usage.consolidation.fixedRate:600000}")
	public void tick() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tick() LLM usage consolidation");
		}
		try {
			aggregationService.consolidate();
		} catch (RuntimeException e) {
			// A failed run must not stop the schedule: the next tick rebuilds the same
			// days from the same raw records, so nothing is lost by retrying then.
			LOGGER.error("LLM usage consolidation failed, it will be retried at the next tick", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End tick() LLM usage consolidation");
		}
	}
}
