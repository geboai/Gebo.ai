package ai.gebo.architecture.llms.usage.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageAggregationBucket;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownLevel;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownLevelSubdimensions;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Aggregates the consolidated {@link LLMDailyUsageDetail} documents into the two
 * datasets exposed by the usage controllers:
 * <ul>
 * <li>a per year/month aggregation;</li>
 * <li>a per year/month/day aggregation restricted to the current calendar
 * month.</li>
 * </ul>
 *
 * Both are computed coherently with the field presence of the drill-down
 * criteria: every non-null criteria field is applied as an equality filter,
 * while null fields are aggregated across.
 *
 * Each dataset is produced by a single {@code $facet} pipeline that computes,
 * over the same matched documents in one pass, both the aggregation buckets and
 * the distinct values of the aggregated-across dimensions (sub-dimensions).
 */
@Service
@AllArgsConstructor
public class LLMSUsageAggregationService {

	private final MongoTemplate mongoTemplate;

	/**
	 * @param criteria             the drill-down criteria (non-null fields are
	 *                             equality filters); may be null.
	 * @param usernameRestriction  when not null, forces the username filter to this
	 *                             value regardless of the criteria (used to restrict
	 *                             a user to its own data). When null, the criteria
	 *                             username is used (admin, unrestricted).
	 */
	public LLMUsageDrillDownResult drillDown(LLMUsageDrillDownLevel criteria, String usernameRestriction) {
		String effectiveUsername = usernameRestriction != null ? usernameRestriction
				: (criteria != null ? criteria.getUsername() : null);

		Dataset monthly = aggregateDataset(buildMatch(criteria, effectiveUsername, false), false, criteria,
				effectiveUsername);
		Dataset daily = aggregateDataset(buildMatch(criteria, effectiveUsername, true), true, criteria,
				effectiveUsername);

		return new LLMUsageDrillDownResult(monthly.buckets, monthly.subdimensions, daily.buckets, daily.subdimensions);
	}

	/**
	 * Runs, for one dataset, a single {@code $match} + {@code $facet} pipeline that
	 * yields both the aggregation buckets and the sub-dimensions in one pass over
	 * the matched documents.
	 *
	 * @param includeDay        when true buckets are grouped by year/month/day
	 *                          (current-month daily dataset), otherwise by
	 *                          year/month (monthly dataset).
	 */
	private Dataset aggregateDataset(Criteria match, boolean includeDay, LLMUsageDrillDownLevel c,
			String effectiveUsername) {
		FacetOperation facet = Aggregation.facet(bucketPipeline(includeDay)).as("buckets")
				.and(subdimensionsPipeline(c, effectiveUsername, includeDay)).as("subdimensions");

		FacetResult raw = mongoTemplate
				.aggregate(Aggregation.newAggregation(Aggregation.match(match), facet), LLMDailyUsageDetail.class,
						FacetResult.class)
				.getUniqueMappedResult();

		Dataset dataset = new Dataset();
		dataset.buckets = toBuckets(raw == null ? null : raw.getBuckets(), c, effectiveUsername);
		dataset.subdimensions = (raw == null || raw.getSubdimensions() == null || raw.getSubdimensions().isEmpty())
				? new LLMUsageDrillDownLevelSubdimensions()
				: raw.getSubdimensions().get(0);
		return dataset;
	}

	/**
	 * Sub-pipeline computing the aggregation buckets. Pre-computes the response time
	 * weighted by the number of requests, and the time to first token weighted by the
	 * number of calls that measured it, so that correct weighted averages can be
	 * recomposed after the grouping.
	 * <p>
	 * The response time fields are read under their stored names
	 * ({@link LLMDailyUsageDetail#RESPONSE_TIME_MIN_FIELD} and siblings), not their
	 * property names, so the pipeline does not depend on property-to-field mapping
	 * inside the {@code $facet}. The time to first token of a document where no call
	 * measured it is null: {@code $min}/{@code $max} skip it and {@code $sum} counts it
	 * as nothing, so it neither lowers the minimum nor dilutes the average.
	 */
	private AggregationOperation[] bucketPipeline(boolean includeDay) {
		List<AggregationOperation> ops = new ArrayList<>();
		ops.add(Aggregation
				.project("inputToken", "outputToken", "totalToken", "nrRequests", "year", "month", "day",
						"timeToFirstTokenMin", "timeToFirstTokenMax", "timeToFirstTokenSamples")
				.and(LLMDailyUsageDetail.RESPONSE_TIME_MIN_FIELD).as("responseTimeMin")
				.and(LLMDailyUsageDetail.RESPONSE_TIME_MAX_FIELD).as("responseTimeMax")
				.andExpression(LLMDailyUsageDetail.RESPONSE_TIME_AVG_FIELD + " * nrRequests")
				.as("responseTimeWeighted").andExpression("timeToFirstTokenAvg * timeToFirstTokenSamples")
				.as("timeToFirstTokenWeighted"));

		GroupOperation group = includeDay ? Aggregation.group("year", "month", "day")
				: Aggregation.group("year", "month");
		group = group.sum("inputToken").as("inputToken").sum("outputToken").as("outputToken").sum("totalToken")
				.as("totalToken").sum("nrRequests").as("nrRequests").sum("responseTimeWeighted")
				.as("responseTimeWeighted").min("responseTimeMin").as("responseTimeMin").max("responseTimeMax")
				.as("responseTimeMax").sum("timeToFirstTokenWeighted").as("timeToFirstTokenWeighted")
				.sum("timeToFirstTokenSamples").as("timeToFirstTokenSamples").min("timeToFirstTokenMin")
				.as("timeToFirstTokenMin").max("timeToFirstTokenMax").as("timeToFirstTokenMax");
		ops.add(group);

		ProjectionOperation flatten = Aggregation.project().and("_id.year").as("year").and("_id.month").as("month")
				.and("inputToken").as("inputToken").and("outputToken").as("outputToken").and("totalToken")
				.as("totalToken").and("nrRequests").as("nrRequests").and("responseTimeMin").as("responseTimeMin")
				.and("responseTimeMax").as("responseTimeMax").and("responseTimeWeighted").as("responseTimeWeighted")
				.and("timeToFirstTokenWeighted").as("timeToFirstTokenWeighted").and("timeToFirstTokenSamples")
				.as("timeToFirstTokenSamples").and("timeToFirstTokenMin").as("timeToFirstTokenMin")
				.and("timeToFirstTokenMax").as("timeToFirstTokenMax");
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
	private AggregationOperation[] subdimensionsPipeline(LLMUsageDrillDownLevel c, String effectiveUsername,
			boolean forceCurrentMonth) {
		GroupOperation group = Aggregation.group();
		if (c == null || c.getProviderId() == null)
			group = group.addToSet("providerId").as("providerId");
		if (effectiveUsername == null)
			group = group.addToSet("username").as("username");
		if (c == null || c.getModel() == null)
			group = group.addToSet("model").as("model");
		if (c == null || c.getCallerStack() == null)
			group = group.addToSet("callerStack").as("callerStack");
		if (c == null || c.getModelType() == null)
			group = group.addToSet("modelType").as("modelType");
		if (!forceCurrentMonth && (c == null || c.getYear() == null))
			group = group.addToSet("year").as("year");
		if (!forceCurrentMonth && (c == null || c.getMonth() == null))
			group = group.addToSet("month").as("month");

		return new AggregationOperation[] { group };
	}

	private Criteria buildMatch(LLMUsageDrillDownLevel c, String effectiveUsername, boolean forceCurrentMonth) {
		List<Criteria> parts = new ArrayList<>();

		if (c != null) {
			if (c.getProviderId() != null)
				parts.add(Criteria.where("providerId").is(c.getProviderId()));
			if (c.getModel() != null)
				parts.add(Criteria.where("model").is(c.getModel()));
			if (c.getCallerStack() != null)
				parts.add(Criteria.where("callerStack").is(c.getCallerStack()));
			if (c.getModelType() != null)
				parts.add(Criteria.where("modelType").is(c.getModelType()));
		}
		if (effectiveUsername != null)
			parts.add(Criteria.where("username").is(effectiveUsername));

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

	private List<LLMUsageAggregationBucket> toBuckets(List<AggregatedRow> rows, LLMUsageDrillDownLevel c,
			String effectiveUsername) {
		if (rows == null || rows.isEmpty())
			return new ArrayList<>();

		List<LLMUsageAggregationBucket> buckets = new ArrayList<>(rows.size());
		for (AggregatedRow r : rows) {
			LLMUsageAggregationBucket b = new LLMUsageAggregationBucket();
			// Dimension values coherent with the criteria (only the filtered ones).
			if (c != null) {
				b.setProviderId(c.getProviderId());
				b.setModel(c.getModel());
				b.setCallerStack(c.getCallerStack());
				b.setModelType(c.getModelType());
			}
			b.setUsername(effectiveUsername);
			b.setYear(r.getYear());
			b.setMonth(r.getMonth());
			b.setDay(r.getDay());
			b.setInputToken(r.getInputToken());
			b.setOutputToken(r.getOutputToken());
			b.setTotalToken(r.getTotalToken());
			b.setNrRequests(r.getNrRequests());
			b.setResponseTimeMin(r.getResponseTimeMin());
			b.setResponseTimeMax(r.getResponseTimeMax());
			b.setResponseTimeAvg(r.getNrRequests() > 0 ? r.getResponseTimeWeighted() / r.getNrRequests() : 0);
			b.setTimeToFirstTokenSamples(r.getTimeToFirstTokenSamples());
			if (r.getTimeToFirstTokenSamples() > 0) {
				b.setTimeToFirstTokenMin(r.getTimeToFirstTokenMin());
				b.setTimeToFirstTokenMax(r.getTimeToFirstTokenMax());
				b.setTimeToFirstTokenAvg(r.getTimeToFirstTokenWeighted() / r.getTimeToFirstTokenSamples());
			}
			buckets.add(b);
		}
		return buckets;
	}

	/**
	 * Holds the two outputs of one dataset's {@code $facet} pipeline.
	 */
	private static class Dataset {
		private List<LLMUsageAggregationBucket> buckets;
		private LLMUsageDrillDownLevelSubdimensions subdimensions;
	}

	/**
	 * Mapping target for the {@code $facet} stage.
	 */
	@Data
	public static class FacetResult {
		private List<AggregatedRow> buckets;
		private List<LLMUsageDrillDownLevelSubdimensions> subdimensions;
	}

	/**
	 * Intermediate mapping target for a single aggregation bucket.
	 */
	@Data
	public static class AggregatedRow {
		private Integer year;
		private Integer month;
		private Integer day;
		private long inputToken;
		private long outputToken;
		private long totalToken;
		private long nrRequests;
		private long responseTimeMin;
		private long responseTimeMax;
		private long responseTimeWeighted;
		private Long timeToFirstTokenMin;
		private Long timeToFirstTokenMax;
		private long timeToFirstTokenWeighted;
		private long timeToFirstTokenSamples;
	}
}
