/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knowledgebase.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments MongoTemplate based implementation of
 * {@link JobStatusRepositoryCustom}. It lives in the repository package so that
 * Spring Data picks it up as the "Impl" fragment of
 * {@link JobStatusRepository}.
 */
public class JobStatusRepositoryImpl implements JobStatusRepositoryCustom {

	// Fields of GJobStatus scanned by the free text search.
	private static final List<String> SEARCHED_FIELDS = List.of("code", "description", "workflowType");

	private final MongoTemplate mongoTemplate;

	public JobStatusRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Page<GJobStatusItem> searchJobsEntries(JobStatusSearchCriteria criteria, Pageable pageable) {
		Pageable actualPageable = pageable != null ? pageable : Pageable.unpaged();
		Query query = buildQuery(criteria);

		long total = mongoTemplate.count(query, GJobStatus.class);

		List<GJobStatusItem> content = mongoTemplate.query(GJobStatus.class).as(GJobStatusItem.class)
				.matching(query.with(actualPageable)).all();

		return new PageImpl<>(content, actualPageable, total);
	}

	/**
	 * Builds the mongo query matching the given criteria, skipping every criteria
	 * which is not valued.
	 * 
	 * @param criteria the criteria to translate.
	 * @return the corresponding query, empty when nothing has to be filtered.
	 */
	private Query buildQuery(JobStatusSearchCriteria criteria) {
		Query query = new Query();

		if (criteria == null) {
			return query;
		}

		List<Criteria> conditions = new ArrayList<>();

		List<String> classNames = normalizeClassNames(criteria.getClassNames());
		if (!classNames.isEmpty()) {
			conditions.add(Criteria.where("projectEndpointReference.className").in(classNames));
		}

		GObjectRef<?> endpointReference = criteria.getEndpointReference();
		if (endpointReference != null) {
			if (!isBlank(endpointReference.getClassName())) {
				conditions.add(
						Criteria.where("projectEndpointReference.className").is(endpointReference.getClassName()));
			}
			if (!isBlank(endpointReference.getCode())) {
				conditions.add(Criteria.where("projectEndpointReference.code").is(endpointReference.getCode()));
			}
		}

		if (criteria.getJobType() != null) {
			conditions.add(Criteria.where("jobType").is(criteria.getJobType()));
		}

		Criteria searchCriteria = buildSearchCriteria(criteria.getSearchText());
		if (searchCriteria != null) {
			conditions.add(searchCriteria);
		}

		if (!conditions.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(conditions.toArray(new Criteria[0])));
		}

		return query;
	}

	/**
	 * Builds the free text criteria, matching the searched text, case
	 * insensitively, as a substring of any of the searched fields.
	 * 
	 * @param searchText the text looked for.
	 * @return the criteria, or null when no text has been given.
	 */
	private Criteria buildSearchCriteria(String searchText) {
		if (isBlank(searchText)) {
			return null;
		}

		String escaped = Pattern.quote(searchText.trim());
		Criteria[] alternatives = SEARCHED_FIELDS.stream()
				.map(field -> Criteria.where(field).regex(".*" + escaped + ".*", "i")).toArray(Criteria[]::new);

		return new Criteria().orOperator(alternatives);
	}

	private List<String> normalizeClassNames(List<String> classNames) {
		if (classNames == null) {
			return List.of();
		}

		return classNames.stream().filter(className -> !isBlank(className)).map(String::trim).distinct().toList();
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
