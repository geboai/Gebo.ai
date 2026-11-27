/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.zammad.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

/**
 * Structured Zammad search filter, filled by a model from the user's question
 * and translated server side into a ticket search condition, every given
 * criterion holding; its text terms also search the knowledge base answers.
 */
@Data
@JsonClassDescription("Zammad tickets and knowledge base search filter")
public class ZammadTicketsSearchFilter implements INativeQueryObject {
	@JsonPropertyDescription("Groups to search the tickets of: the group names as listed in the Zammad groups catalog. Empty or null searches every group")
	private List<String> groups = null;
	@JsonPropertyDescription("Zammad ticket numbers explicitly mentioned (for example 97002 for #97002)")
	private List<String> ticketNumbers = null;
	@JsonPropertyDescription("Plain text keywords or short phrases to find in the ticket titles and messages, and in the knowledge base answers")
	private List<String> textTerms = null;
	@JsonPropertyDescription("ANY when any text term is enough, ALL when every text term must be present")
	private TextMatchMode textTermsMatchMode = null;
	@JsonPropertyDescription("Ticket state names exactly as the user wrote them (for example new, open, pending reminder, closed)")
	private List<String> states = null;
	@JsonPropertyDescription("true for open tickets only (not closed nor merged), false for closed tickets only, null for both")
	private Boolean open = null;
	@JsonPropertyDescription("Priority names as the user wrote them (for example low, normal, high)")
	private List<String> priorities = null;
	@JsonPropertyDescription("true when the user asks for the tickets owned by the person asking (me, my)")
	private Boolean ownedByMe = null;
	@JsonPropertyDescription("Tickets created on or after this date, format yyyy-MM-dd")
	private String createdFrom = null;
	@JsonPropertyDescription("Tickets created on or before this date, format yyyy-MM-dd")
	private String createdTo = null;
	@JsonPropertyDescription("Tickets updated on or after this date, format yyyy-MM-dd")
	private String updatedFrom = null;
	@JsonPropertyDescription("Tickets updated on or before this date, format yyyy-MM-dd")
	private String updatedTo = null;
	@JsonPropertyDescription("false when the user asks for tickets only; otherwise the text terms also search the knowledge base answers")
	private Boolean searchKnowledgeBase = null;

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder = new KeywordListBuilder();
		builder.addKeywordsProvider(() -> textTerms);
		return builder.getKeywords();
	}
}
