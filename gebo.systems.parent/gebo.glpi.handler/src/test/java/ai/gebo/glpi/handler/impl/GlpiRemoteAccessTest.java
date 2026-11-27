/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient.ResponseSpec;

import ai.gebo.glpi.client.api.AdministrationApi;
import ai.gebo.glpi.client.api.AssistanceApi;
import ai.gebo.glpi.client.api.KnowledgebaseApi;
import ai.gebo.glpi.client.api.ManagementApi;
import ai.gebo.glpi.client.api.SessionApi;
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;

class GlpiRemoteAccessTest {

	private final AssistanceApi assistance = mock(AssistanceApi.class);
	private final ManagementApi management = mock(ManagementApi.class);
	private final GlpiRemoteAccess access = new GlpiRemoteAccess(assistance, mock(KnowledgebaseApi.class),
			mock(AdministrationApi.class), management, mock(SessionApi.class));

	@SuppressWarnings("unchecked")
	private static ResponseSpec answering(String json) {
		ResponseSpec spec = mock(ResponseSpec.class);
		when(spec.body(any(ParameterizedTypeReference.class)))
				.thenReturn(GlpiTestModels.MAPPER.readValue(json.replace('\'', '"'), List.class));
		return spec;
	}

	@Test
	void timelineEntriesAreReadWrappedAsGlpiAnswersThem() {
		// GLPI 11.0.13 answers the timeline lists as [{type, item}] entries
		ResponseSpec spec = answering("[{'type':'Followup','item':{'id':881,'content':'<p>Restarted</p>','is_private':true}},"
				+ "{'type':'Followup','item':{'id':882,'content':'<p>Still offline</p>'}}]");
		when(assistance.assistanceTicketIdTimelineFollowupGetWithResponseSpec(eq(1042), any(), any(), any(), any()))
				.thenReturn(spec);
		List<Followup> followups = access.followups(1042);
		assertThat(followups).extracting(Followup::getId).containsExactly(881L, 882L);
		assertThat(followups.get(0).getContent()).isEqualTo("<p>Restarted</p>");
		assertThat(followups.get(0).getIsPrivate()).isTrue();
	}

	@Test
	void plainTimelineItemsAreReadToo() {
		ResponseSpec spec = answering("[{'id':12,'content':'<p>New toner</p>'}]");
		when(assistance.assistanceTicketIdTimelineSolutionGetWithResponseSpec(eq(1042), any(), any(), any(), any()))
				.thenReturn(spec);
		assertThat(access.solutions(1042)).extracting(Solution::getId).containsExactly(12L);
	}

	@Test
	void aTicketDocumentIsReadThroughItsTimelineLink() {
		// a timeline document entry is the document-ticket link, not the document
		ResponseSpec links = answering("[{'type':'Document','item':{'id':7,'itemtype':'Ticket','items_id':1042,"
				+ "'documents_id':9,'document':{'id':9,'name':'Invoice'}}}]");
		when(assistance.assistanceTicketIdTimelineDocumentGetWithResponseSpec(eq(1042), any(), any(), any(), any()))
				.thenReturn(links);
		when(management.managementDocumentIdGet(eq(9), any(), any(), any(), any())).thenReturn(GlpiTestModels
				.read("{'id':9,'name':'Invoice','filename':'invoice.pdf','mime':'application/pdf'}", Document.class));
		assertThat(access.ticketDocuments(1042)).extracting(Document::getFilename).containsExactly("invoice.pdf");
		assertThat(access.ticketDocument(1042, 9)).isNotNull();
		assertThat(access.ticketDocument(1042, 7)).isNull();
	}

	private static List<Ticket> tickets(long fromId, long count) {
		return LongStream.range(fromId, fromId + count).mapToObj(id -> GlpiTestModels.read("{'id':" + id + "}", Ticket.class)).toList();
	}

	@Test
	void everyPageIsReadUntilAnEmptyOne() {
		when(assistance.assistanceTicketGet(eq("entity.id==3;is_deleted==false"), eq(0L), anyLong(), any(), isNull(),
				isNull(), eq("true"), isNull())).thenReturn(tickets(1, 100));
		when(assistance.assistanceTicketGet(any(), eq(100L), anyLong(), any(), any(), any(), any(), any()))
				.thenReturn(tickets(101, 100));
		when(assistance.assistanceTicketGet(any(), eq(200L), anyLong(), any(), any(), any(), any(), any()))
				.thenReturn(tickets(201, 7));
		when(assistance.assistanceTicketGet(any(), eq(207L), anyLong(), any(), any(), any(), any(), any()))
				.thenReturn(List.of());
		assertThat(access.tickets(3)).hasSize(207).extracting(Ticket::getId).doesNotHaveDuplicates();
	}

	@Test
	void aServerPageSmallerThanAskedDoesNotTruncate() {
		// the server caps the page at 20 items: the listing goes on from the next one
		when(assistance.assistanceTicketGet(any(), anyLong(), anyLong(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					long start = invocation.getArgument(1);
					return start < 50 ? tickets(start + 1, Math.min(20, 50 - start)) : new ArrayList<Ticket>();
				});
		assertThat(access.tickets(3)).hasSize(50);
	}

	@Test
	void aServerIgnoringTheStartDoesNotLoopForever() {
		when(assistance.assistanceTicketGet(any(), anyLong(), anyLong(), any(), any(), any(), any(), any()))
				.thenReturn(tickets(1, 100));
		assertThat(access.tickets(3)).hasSize(100);
	}

	@Test
	void aMissingItemIsNull() {
		when(assistance.assistanceTicketIdGet(anyInt(), any(), any(), any(), any())).thenThrow(
				HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not found", null, new byte[0],
						StandardCharsets.UTF_8));
		assertThat(access.ticket(5)).isNull();
	}
}
