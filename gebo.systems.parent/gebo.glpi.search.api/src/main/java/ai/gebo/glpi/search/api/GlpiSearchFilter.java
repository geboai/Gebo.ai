/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

/**
 * Structured search over GLPI helpdesk tickets and knowledge base articles. The
 * LLM fills it, the GLPI handler translates it into the RSQL filter of the GLPI
 * high-level API.
 */
@Data
@JsonClassDescription("GLPI helpdesk tickets and knowledge base articles search filter")
public class GlpiSearchFilter implements INativeQueryObject {

	@JsonClassDescription("Kind of GLPI item")
	public static enum GlpiItemKind {
		TICKET, KB_ARTICLE
	}

	@JsonClassDescription("Group of keywords search matching policy")
	public static enum TextMatchMode {
		ANY, ALL
	}

	@JsonClassDescription("GLPI ticket status")
	public static enum GlpiTicketStatus {
		NEW, PROCESSING_ASSIGNED, PROCESSING_PLANNED, PENDING, SOLVED, CLOSED, APPROVAL
	}

	@JsonClassDescription("GLPI ticket type")
	public static enum GlpiTicketType {
		INCIDENT, REQUEST
	}

	@JsonPropertyDescription("Kinds of GLPI items to search: TICKET for helpdesk tickets, KB_ARTICLE for knowledge base articles. Null or empty searches both kinds")
	private List<GlpiItemKind> itemKinds = null;

	@JsonPropertyDescription("Numeric ids of the GLPI entities (from the entities catalog) the searched items belong to")
	private List<Long> entityIds = null;

	@JsonPropertyDescription("Numeric ids of the searched GLPI tickets (the number written after # in GLPI, for example 1042 for ticket #1042)")
	private List<Long> ticketIds = null;

	@JsonPropertyDescription("Keywords or short phrases contained in the title of tickets or articles")
	private List<String> titleTerms = null;

	private TextMatchMode titleTermsMatchMode = null;

	@JsonPropertyDescription("Keywords or short phrases contained in the description of tickets or in the body of articles")
	private List<String> contentTerms = null;

	private TextMatchMode contentTermsMatchMode = null;

	@JsonPropertyDescription("Statuses of the searched tickets, only when the user asks for them")
	private List<GlpiTicketStatus> ticketStatuses = null;

	@JsonPropertyDescription("Types of the searched tickets: INCIDENT or REQUEST, only when the user asks for them")
	private List<GlpiTicketType> ticketTypes = null;

	@JsonPropertyDescription("Priorities of the searched tickets, from 1 (very low) to 6 (major), only when the user asks for them")
	private List<Integer> priorities = null;

	@JsonPropertyDescription("Names, or parts of names, of the ITIL categories of the searched tickets")
	private List<String> categoryNames = null;

	@JsonPropertyDescription("Only items created on or after this date, format YYYY-MM-DD")
	private String createdFrom = null;

	@JsonPropertyDescription("Only items created on or before this date, format YYYY-MM-DD")
	private String createdTo = null;

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder = new KeywordListBuilder();
		builder.addKeywordsProvider(() -> {
			return titleTerms;
		});
		builder.addKeywordsProvider(() -> {
			return contentTerms;
		});
		return builder.getKeywords();
	}
}
