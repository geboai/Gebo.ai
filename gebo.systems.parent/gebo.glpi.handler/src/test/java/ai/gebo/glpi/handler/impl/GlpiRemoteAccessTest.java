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
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import ai.gebo.glpi.client.api.AdministrationApi;
import ai.gebo.glpi.client.api.AssistanceApi;
import ai.gebo.glpi.client.api.KnowledgebaseApi;
import ai.gebo.glpi.client.api.ManagementApi;
import ai.gebo.glpi.client.api.SessionApi;
import ai.gebo.glpi.client.model.Ticket;

class GlpiRemoteAccessTest {

	private final AssistanceApi assistance = mock(AssistanceApi.class);
	private final GlpiRemoteAccess access = new GlpiRemoteAccess(assistance, mock(KnowledgebaseApi.class),
			mock(AdministrationApi.class), mock(ManagementApi.class), mock(SessionApi.class));

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
