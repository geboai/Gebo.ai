package ai.gebo.architecture.llms.usage.service;

/**
 * Folds the raw LLM usage records into the daily aggregates the usage reports read.
 * <p>
 * It is invoked periodically by {@code LLMUsageConsolidationTicker}, which owns the
 * schedule; an implementation holds only the consolidation logic. The default is
 * {@code LLMUsageDailyAggregationServiceImpl}. To replace it, publish another bean
 * implementing this interface: the ticker then calls that one instead of the default
 * (a single replacement is supported). To adjust the default instead of replacing
 * it, extend {@code LLMUsageDailyAggregationServiceImpl} and override its protected
 * hooks.
 */
public interface ILLMUsageDailyAggregationService {

	/**
	 * Runs one consolidation. Must be idempotent: it is invoked repeatedly, on a fixed
	 * rate, and running it twice over the same raw records must not change the daily
	 * aggregates.
	 */
	public void consolidate();
}
