/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.openproject.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

/**
 * Structured OpenProject work packages search filter, filled by a model from the
 * user's question and translated server side into the filters of a work packages
 * query of the API v3.
 */
@Data
@JsonClassDescription("OpenProject work packages search filter")
public class OpenProjectWorkPackagesSearchFilter implements INativeQueryObject {
	@JsonPropertyDescription("Projects to search in: the project codes as listed in the OpenProject projects catalog, or the project identifiers. Empty or null searches every project")
	private List<String> projects = null;
	@JsonPropertyDescription("OpenProject work package numbers explicitly mentioned (for example 1234 for #1234)")
	private List<Integer> workPackageIds = null;
	@JsonPropertyDescription("Plain text keywords or short phrases to find in the work packages subject, description and comments")
	private List<String> textTerms = null;
	@JsonPropertyDescription("ANY when any text term is enough, ALL when every text term must be present")
	private TextMatchMode textTermsMatchMode = null;
	@JsonPropertyDescription("Work package type names exactly as the user wrote them (for example Task, Bug, Feature, Milestone)")
	private List<String> types = null;
	@JsonPropertyDescription("Status names exactly as the user wrote them (for example New, In progress, Closed)")
	private List<String> statuses = null;
	@JsonPropertyDescription("true for open work packages only, false for closed work packages only, null for both")
	private Boolean open = null;
	@JsonPropertyDescription("Priority names exactly as the user wrote them (for example High, Immediate)")
	private List<String> priorities = null;
	@JsonPropertyDescription("true when the user asks for the work packages assigned to the person asking (me, my)")
	private Boolean assignedToMe = null;
	@JsonPropertyDescription("Work packages created on or after this date, format yyyy-MM-dd")
	private String createdFrom = null;
	@JsonPropertyDescription("Work packages created on or before this date, format yyyy-MM-dd")
	private String createdTo = null;
	@JsonPropertyDescription("Work packages updated on or after this date, format yyyy-MM-dd")
	private String updatedFrom = null;
	@JsonPropertyDescription("Work packages updated on or before this date, format yyyy-MM-dd")
	private String updatedTo = null;

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder = new KeywordListBuilder();
		builder.addKeywordsProvider(() -> textTerms);
		return builder.getKeywords();
	}
}
