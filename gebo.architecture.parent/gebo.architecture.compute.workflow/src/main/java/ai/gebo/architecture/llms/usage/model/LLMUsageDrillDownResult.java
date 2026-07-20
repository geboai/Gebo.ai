package ai.gebo.architecture.llms.usage.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response of an LLM usage drill-down request.
 *
 * <ul>
 * <li>{@code monthly}: usage aggregated by year/month (plus the dimension
 * values coherent with the criteria).</li>
 * <li>{@code currentMonthDaily}: usage of the current calendar month aggregated
 * by year/month/day (plus the dimension values coherent with the
 * criteria).</li>
 * </ul>
 *
 * Each dataset is accompanied by its {@link LLMUsageDrillDownLevelSubdimensions},
 * listing the distinct values of the dimensions that were aggregated across.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LLMUsageDrillDownResult {
	private List<LLMUsageAggregationBucket> monthly;
	private LLMUsageDrillDownLevelSubdimensions monthlySubdimensions;
	private List<LLMUsageAggregationBucket> currentMonthDaily;
	private LLMUsageDrillDownLevelSubdimensions currentMonthDailySubdimensions;
}
