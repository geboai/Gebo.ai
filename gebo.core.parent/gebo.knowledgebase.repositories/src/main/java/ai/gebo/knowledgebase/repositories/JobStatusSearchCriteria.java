/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knowledgebase.repositories;

import java.util.List;

import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments Criteria used to search job status entries. Every field
 * is optional: a null or empty value means "do not filter on it".
 */
public class JobStatusSearchCriteria {
	// Class names of the project endpoint references to keep, empty means every
	// datasource type.
	private List<String> classNames = null;
	// Single project endpoint the jobs must belong to.
	private GObjectRef<GProjectEndpoint> endpointReference = null;
	// Job type the jobs must have.
	private JobType jobType = null;
	// Free text matched, case insensitively, against code, description and
	// workflow type.
	private String searchText = null;

	public List<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}

	public GObjectRef<GProjectEndpoint> getEndpointReference() {
		return endpointReference;
	}

	public void setEndpointReference(GObjectRef<GProjectEndpoint> endpointReference) {
		this.endpointReference = endpointReference;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
