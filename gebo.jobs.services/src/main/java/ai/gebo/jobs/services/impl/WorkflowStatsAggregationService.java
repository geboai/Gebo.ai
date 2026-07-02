package ai.gebo.jobs.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import ai.gebo.jobs.services.model.ComputedWorkflowStatusData;
import ai.gebo.jobs.services.model.WorkflowStatsAggregationBucket;
import ai.gebo.jobs.services.model.WorkflowStatsDrillDownLevel;
import ai.gebo.jobs.services.model.WorkflowStatsDrillDownLevelSubdimensions;
import ai.gebo.jobs.services.model.WorkflowStatsDrillDownResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Aggregates the {@link ComputedWorkflowStatusData} snapshots into the two
 * datasets exposed by the workflow-stats admin controller:
 * <ul>
 * <li>a per year/month aggregation;</li>
 * <li>a per year/month/day aggregation restricted to the current calendar
 * month.</li>
 * </ul>
 *
 * Both are computed coherently with the field presence of the drill-down
 * criteria: every non-null criteria field is applied as an equality filter,
 * while null fields are aggregated across. Each dataset is produced by a single
 * {@code $facet} pipeline that computes, over the same matched documents in one
 * pass, both the aggregation buckets and the distinct values of the
 * aggregated-across dimensions (sub-dimensions).
 */
@Service
@AllArgsConstructor
public class WorkflowStatsAggregationService {

	private final MongoTemplate mongoTemplate;

	public WorkflowStatsDrillDownResult drillDown(WorkflowStatsDrillDownLevel criteria) {
		Dataset monthly = aggregateDataset(buildMatch(criteria, false), false, criteria);
		Dataset daily = aggregateDataset(buildMatch(criteria, true), true, criteria);
		return new WorkflowStatsDrillDownResult(monthly.buckets, monthly.subdimensions, daily.buckets,
				daily.subdimensions);
	}

	/**
	 * Runs, for one dataset, a single {@code $match} + {@code $facet} pipeline that
	 * yields both the aggregation buckets and the sub-dimensions in one pass over
	 * the matched documents.
	 *
	 * @param includeDay when true buckets are grouped by year/month/day
	 *                   (current-month daily dataset), otherwise by year/month
	 *                   (monthly dataset).
	 */
	private Dataset aggregateDataset(Criteria match, boolean includeDay, WorkflowStatsDrillDownLevel c) {
		FacetOperation facet = Aggregation.facet(bucketPipeline(includeDay)).as("buckets")
				.and(subdimensionsPipeline(c, includeDay)).as("subdimensions");

		FacetResult raw = mongoTemplate
				.aggregate(Aggregation.newAggregation(Aggregation.match(match), facet), ComputedWorkflowStatusData.class,
						FacetResult.class)
				.getUniqueMappedResult();

		Dataset dataset = new Dataset();
		dataset.buckets = toBuckets(raw == null ? null : raw.getBuckets(), c);
		dataset.subdimensions = (raw == null || raw.getSubdimensions() == null || raw.getSubdimensions().isEmpty())
				? new WorkflowStatsDrillDownLevelSubdimensions()
				: raw.getSubdimensions().get(0);
		return dataset;
	}

	/**
	 * Sub-pipeline computing the aggregation buckets: sums of the data volumes plus
	 * the number of snapshots (job steps) aggregated.
	 */
	private AggregationOperation[] bucketPipeline(boolean includeDay) {
		List<AggregationOperation> ops = new ArrayList<>();

		GroupOperation group = includeDay ? Aggregation.group("year", "month", "day")
				: Aggregation.group("year", "month");
		group = group.sum("batchDocumentsInput").as("batchDocumentsInput").sum("batchDocumentsProcessed")
				.as("batchDocumentsProcessed").sum("batchDocumentsProcessingErrors").as("batchDocumentsProcessingErrors")
				.sum("batchSentToNextStep").as("batchSentToNextStep").sum("batchDiscardedInput").as("batchDiscardedInput")
				.sum("chunksProcessed").as("chunksProcessed").sum("tokensProcessed").as("tokensProcessed").count()
				.as("nrSnapshots");
		ops.add(group);

		ProjectionOperation flatten = Aggregation.project().and("_id.year").as("year").and("_id.month").as("month")
				.and("batchDocumentsInput").as("batchDocumentsInput").and("batchDocumentsProcessed")
				.as("batchDocumentsProcessed").and("batchDocumentsProcessingErrors").as("batchDocumentsProcessingErrors")
				.and("batchSentToNextStep").as("batchSentToNextStep").and("batchDiscardedInput").as("batchDiscardedInput")
				.and("chunksProcessed").as("chunksProcessed").and("tokensProcessed").as("tokensProcessed")
				.and("nrSnapshots").as("nrSnapshots");
		if (includeDay)
			flatten = flatten.and("_id.day").as("day");
		ops.add(flatten);

		ops.add(includeDay ? Aggregation.sort(Sort.by("year", "month", "day"))
				: Aggregation.sort(Sort.by("year", "month")));

		return ops.toArray(new AggregationOperation[0]);
	}

	/**
	 * Sub-pipeline collecting the distinct values of the dimensions that were
	 * aggregated across (the ones not fixed by the criteria).
	 *
	 * @param forceCurrentMonth when true year/month are fixed (current month), so
	 *                          they are not reported as sub-dimensions.
	 */
	private AggregationOperation[] subdimensionsPipeline(WorkflowStatsDrillDownLevel c, boolean forceCurrentMonth) {
		List<AggregationOperation> ops = new ArrayList<>();

		// Reduce the reference fields to className+code before collecting the
		// distinct sets, so they are keyed on identity only and the (possibly
		// varying) description does not produce spurious duplicates.
		Document refReduction = new Document();
		if (!isKnowledgeBaseFixed(c))
			refReduction.append("knowledgeBaseReference", refIdentity("knowledgeBaseReference"));
		if (!isProjectFixed(c))
			refReduction.append("projectReference", refIdentity("projectReference"));
		if (!isProjectEndpointFixed(c))
			refReduction.append("projectEndpointReference", refIdentity("projectEndpointReference"));
		if (!refReduction.isEmpty())
			ops.add(context -> new Document("$addFields", refReduction));

		GroupOperation group = Aggregation.group();
		if (!isKnowledgeBaseFixed(c))
			group = group.addToSet("knowledgeBaseReference").as("knowledgeBaseReference");
		if (!isProjectFixed(c))
			group = group.addToSet("projectReference").as("projectReference");
		if (!isProjectEndpointFixed(c))
			group = group.addToSet("projectEndpointReference").as("projectEndpointReference");
		if (c == null || c.getWorkflowType() == null)
			group = group.addToSet("workflowType").as("workflowType");
		if (c == null || c.getWorkflowId() == null)
			group = group.addToSet("workflowId").as("workflowId");
		if (c == null || c.getWorkflowStepId() == null)
			group = group.addToSet("workflowStepId").as("workflowStepId");
		if (!forceCurrentMonth && (c == null || c.getYear() == null))
			group = group.addToSet("year").as("year");
		if (!forceCurrentMonth && (c == null || c.getMonth() == null))
			group = group.addToSet("month").as("month");
		ops.add(group);

		return ops.toArray(new AggregationOperation[0]);
	}

	/**
	 * {@code $expr} building a reference document reduced to its identity fields
	 * (className + code), dropping the description.
	 */
	private static Document refIdentity(String field) {
		return new Document("className", "$" + field + ".className").append("code", "$" + field + ".code");
	}

	private Criteria buildMatch(WorkflowStatsDrillDownLevel c, boolean forceCurrentMonth) {
		List<Criteria> parts = new ArrayList<>();

		if (c != null) {
			if (isKnowledgeBaseFixed(c)) {
				parts.add(Criteria.where("knowledgeBaseReference.className")
						.is(c.getKnowledgeBaseReference().getClassName()));
				parts.add(Criteria.where("knowledgeBaseReference.code")
						.is(c.getKnowledgeBaseReference().getCode()));
			}
			if (isProjectFixed(c)) {
				parts.add(Criteria.where("projectReference.className")
						.is(c.getProjectReference().getClassName()));
				parts.add(Criteria.where("projectReference.code").is(c.getProjectReference().getCode()));
			}
			if (isProjectEndpointFixed(c)) {
				parts.add(Criteria.where("projectEndpointReference.className")
						.is(c.getProjectEndpointReference().getClassName()));
				parts.add(Criteria.where("projectEndpointReference.code")
						.is(c.getProjectEndpointReference().getCode()));
			}
			if (c.getWorkflowType() != null)
				parts.add(Criteria.where("workflowType").is(c.getWorkflowType()));
			if (c.getWorkflowId() != null)
				parts.add(Criteria.where("workflowId").is(c.getWorkflowId()));
			if (c.getWorkflowStepId() != null)
				parts.add(Criteria.where("workflowStepId").is(c.getWorkflowStepId()));
		}

		if (forceCurrentMonth) {
			// The daily dataset is always the current calendar month.
			LocalDate now = LocalDate.now();
			parts.add(Criteria.where("year").is(now.getYear()));
			parts.add(Criteria.where("month").is(now.getMonthValue()));
		} else {
			if (c != null && c.getYear() != null)
				parts.add(Criteria.where("year").is(c.getYear()));
			if (c != null && c.getMonth() != null)
				parts.add(Criteria.where("month").is(c.getMonth()));
		}

		Criteria criteria = new Criteria();
		if (!parts.isEmpty())
			criteria.andOperator(parts.toArray(new Criteria[0]));
		return criteria;
	}

	private boolean isKnowledgeBaseFixed(WorkflowStatsDrillDownLevel c) {
		return c != null && c.getKnowledgeBaseReference() != null && c.getKnowledgeBaseReference().getCode() != null;
	}

	private boolean isProjectFixed(WorkflowStatsDrillDownLevel c) {
		return c != null && c.getProjectReference() != null && c.getProjectReference().getCode() != null;
	}

	private boolean isProjectEndpointFixed(WorkflowStatsDrillDownLevel c) {
		return c != null && c.getProjectEndpointReference() != null
				&& c.getProjectEndpointReference().getCode() != null;
	}

	private List<WorkflowStatsAggregationBucket> toBuckets(List<AggregatedRow> rows, WorkflowStatsDrillDownLevel c) {
		if (rows == null || rows.isEmpty())
			return new ArrayList<>();

		List<WorkflowStatsAggregationBucket> buckets = new ArrayList<>(rows.size());
		for (AggregatedRow r : rows) {
			WorkflowStatsAggregationBucket b = new WorkflowStatsAggregationBucket();
			// Dimension values coherent with the criteria (only the filtered ones).
			if (c != null) {
				if (isKnowledgeBaseFixed(c))
					b.setKnowledgeBaseReference(c.getKnowledgeBaseReference());
				if (isProjectFixed(c))
					b.setProjectReference(c.getProjectReference());
				if (isProjectEndpointFixed(c))
					b.setProjectEndpointReference(c.getProjectEndpointReference());
				b.setWorkflowType(c.getWorkflowType());
				b.setWorkflowId(c.getWorkflowId());
				b.setWorkflowStepId(c.getWorkflowStepId());
			}
			b.setYear(r.getYear());
			b.setMonth(r.getMonth());
			b.setDay(r.getDay());
			b.setBatchDocumentsInput(r.getBatchDocumentsInput());
			b.setBatchDocumentsProcessed(r.getBatchDocumentsProcessed());
			b.setBatchDocumentsProcessingErrors(r.getBatchDocumentsProcessingErrors());
			b.setBatchSentToNextStep(r.getBatchSentToNextStep());
			b.setBatchDiscardedInput(r.getBatchDiscardedInput());
			b.setChunksProcessed(r.getChunksProcessed());
			b.setTokensProcessed(r.getTokensProcessed());
			b.setNrSnapshots(r.getNrSnapshots());
			buckets.add(b);
		}
		return buckets;
	}

	/**
	 * Holds the two outputs of one dataset's {@code $facet} pipeline.
	 */
	private static class Dataset {
		private List<WorkflowStatsAggregationBucket> buckets;
		private WorkflowStatsDrillDownLevelSubdimensions subdimensions;
	}

	/**
	 * Mapping target for the {@code $facet} stage.
	 */
	@Data
	public static class FacetResult {
		private List<AggregatedRow> buckets;
		private List<WorkflowStatsDrillDownLevelSubdimensions> subdimensions;
	}

	/**
	 * Intermediate mapping target for a single aggregation bucket.
	 */
	@Data
	public static class AggregatedRow {
		private Integer year;
		private Integer month;
		private Integer day;
		private long batchDocumentsInput;
		private long batchDocumentsProcessed;
		private long batchDocumentsProcessingErrors;
		private long batchSentToNextStep;
		private long batchDiscardedInput;
		private long chunksProcessed;
		private long tokensProcessed;
		private long nrSnapshots;
	}
}
