package ai.gebo.workflows.compute.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response of a workflow-stats drill-down request.
 *
 * <ul>
 * <li>{@code monthly}: usage aggregated by year/month (plus the dimension values
 * coherent with the criteria).</li>
 * <li>{@code currentMonthDaily}: usage of the current calendar month aggregated
 * by year/month/day (plus the dimension values coherent with the
 * criteria).</li>
 * </ul>
 *
 * Each dataset is accompanied by its {@link WorkflowStatsDrillDownLevelSubdimensions},
 * listing the distinct values of the dimensions that were aggregated across.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStatsDrillDownResult {
	private List<WorkflowStatsAggregationBucket> monthly;
	private WorkflowStatsDrillDownLevelSubdimensions monthlySubdimensions;
	private List<WorkflowStatsAggregationBucket> currentMonthDaily;
	private WorkflowStatsDrillDownLevelSubdimensions currentMonthDailySubdimensions;
}
