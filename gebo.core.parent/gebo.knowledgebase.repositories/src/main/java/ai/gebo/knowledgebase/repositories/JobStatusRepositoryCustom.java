/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knowledgebase.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;

/**
 * AI generated comments Custom fragment of {@link JobStatusRepository} adding a
 * multi criteria, searchable and sortable lookup of job status entries, which
 * cannot be expressed as a derived query.
 */
public interface JobStatusRepositoryCustom {

	/**
	 * Searches the job status entries matching the given criteria.
	 * 
	 * @param criteria the criteria to filter the entries with, every field being
	 *                 optional.
	 * @param pageable the pagination and sorting information.
	 * @return a page of job status items.
	 */
	Page<GJobStatusItem> searchJobsEntries(JobStatusSearchCriteria criteria, Pageable pageable);
}
