/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static ai.gebo.glpi.handler.impl.GlpiTestModels.read;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.contenthandling.interfaces.impl.GDocumentReferenceFactoryImpl;
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Entity;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.GlpiResourceReference;
import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;

class GGlpiRemoteVirtualFilesystemConsumingServiceImplTest {

	private final GlpiRemoteAccess access = mock(GlpiRemoteAccess.class);

	/** The service with its GLPI access replaced by the mock */
	private final GGlpiRemoteVirtualFilesystemConsumingServiceImpl service = new GGlpiRemoteVirtualFilesystemConsumingServiceImpl(
			new GDocumentReferenceFactoryImpl(), null) {
		@Override
		protected Map<String, Object> createEnvironment(GGlpiSystem system) {
			return environment();
		}
	};

	private final GGlpiSystem system = new GGlpiSystem();
	private final GGlpiProjectEndpoint endpoint = new GGlpiProjectEndpoint();
	private final List<GVirtualFolder> folders = new ArrayList<GVirtualFolder>();
	private final List<GDocumentReference> documents = new ArrayList<GDocumentReference>();

	private Map<String, Object> environment() {
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(GGlpiRemoteVirtualFilesystemConsumingServiceImpl.GLPI_ACCESS, access);
		environment.put(GGlpiRemoteVirtualFilesystemConsumingServiceImpl.GLPI_ROOT_URL, "https://glpi.example.com");
		return environment;
	}

	private final IGContentConsumer consumer = new IGContentConsumer() {
		@Override
		public void accept(GBaseVersionableObject object) {
			if (object instanceof GDocumentReference document) {
				documents.add(document);
			} else if (object instanceof GVirtualFolder folder) {
				folders.add(folder);
			}
		}

		@Override
		public void endConsuming() {
		}
	};

	private static final String TICKET = "{'id':1042,'name':'Printer/offline','content':'<p>The printer is offline</p>',"
			+ "'date_mod':'2026-05-05T10:00:00+02:00','entity':{'id':3,'name':'Sales','completename':'Root > Sales'},"
			+ "'status':{'id':2,'name':'Processing (assigned)'},'priority':6}";

	@BeforeEach
	void setUp() {
		system.setCode("glpi1");
		system.setDescription("Company GLPI");
		system.setBaseUri("https://glpi.example.com");
		endpoint.setCode("glpi-source");
		endpoint.setDescription("Helpdesk");
		endpoint.setGlpiSystemCode("glpi1");
		when(access.entity(3)).thenReturn(
				read("{'id':3,'name':'Sales','completename':'Root > Sales'}", Entity.class));
		Ticket ticket = read(TICKET, Ticket.class);
		when(access.tickets(3)).thenReturn(List.of(ticket));
		when(access.ticket(1042)).thenReturn(ticket);
		when(access.followups(1042)).thenReturn(List.of(read(
				"{'id':881,'content':'<p>Restarted it</p>','is_private':true,'date_mod':'2026-05-05T11:00:00+02:00'}",
				Followup.class)));
		when(access.followup(1042, 881)).thenReturn(read("{'id':881,'content':'<p>Restarted it</p>'}", Followup.class));
		when(access.tasks(1042)).thenReturn(List.of());
		when(access.solutions(1042)).thenReturn(List.of(read("{'id':12,'content':'<p>New toner</p>'}", Solution.class)));
		when(access.ticketDocuments(1042)).thenReturn(List.of(
				read("{'id':9,'name':'Invoice','filename':'invoice.pdf','mime':'application/pdf'}", Document.class)));
		when(access.kbArticles(3)).thenReturn(List.of(read(
				"{'id':15,'name':'VPN setup','content':'<p>Steps</p>','entity':{'id':3,'name':'Sales'},'categories':[{'id':1,'name':'Network'}]}",
				KBArticle.class)));
	}

	private GVirtualFolder root() {
		GVirtualFolder root = new GVirtualFolder();
		root.setCode("glpi-source-root");
		root.setRelativePath("");
		root.setParentProjectCode("kb");
		return root;
	}

	private void consume(VFilesystemReference... selected) throws Exception {
		endpoint.setPaths(List.of(selected));
		service.consumeAll(system, endpoint, null, root(), consumer, new IGUserMessagesConsumer() {
			@Override
			public void accept(GUserMessage message) {
			}

			@Override
			public void endConsuming() {
			}
		}, IGContentsAccessErrorConsumer.defaultImplementation(), "glpi-module", "glpi");
	}

	private static VFilesystemReference entity(long id) {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = GlpiNavigationUtil.toRoot(id, "Root > Sales");
		return reference;
	}

	private GDocumentReference document(String codeEnd) {
		return documents.stream().filter(d -> d.getCode().endsWith(codeEnd)).findFirst().orElseThrow();
	}

	@Test
	void anEntityIsReadWithItsTicketsAndArticles() throws Exception {
		consume(entity(3));

		assertThat(folders).extracting(GVirtualFolder::getCode).containsExactly("glpi-source-root/entity-3",
				"glpi-source-root/entity-3/tickets", "glpi-source-root/entity-3/tickets/ticket-1042",
				"glpi-source-root/entity-3/knowledge-base");
		assertThat(documents).extracting(GDocumentReference::getCode).containsExactly(
				"glpi-source-root/entity-3/tickets/ticket-1042/ticket-1042-body",
				"glpi-source-root/entity-3/tickets/ticket-1042/followup-881",
				"glpi-source-root/entity-3/tickets/ticket-1042/solution-12",
				"glpi-source-root/entity-3/tickets/ticket-1042/document-9",
				"glpi-source-root/entity-3/knowledge-base/kbarticle-15");

		GDocumentReference body = document("ticket-1042-body");
		assertThat(body.getName()).isEqualTo("Ticket #1042 Printer-offline.html");
		assertThat(body.getExtension()).isEqualTo(".html");
		assertThat(body.getContentType()).isEqualTo("text/html");
		assertThat(body.getUri()).isEqualTo("https://glpi.example.com/front/ticket.form.php?id=1042");
		assertThat(body.getModificationDate()).isNotNull();

		GDocumentReference followup = document("followup-881");
		assertThat(followup.getCustomMetaInfos()).containsEntry(GlpiNativePositionObject.GLPI_OBJECT_TYPE, "FOLLOWUP")
				.containsEntry(GlpiNativePositionObject.GLPI_ITEM_ID, 881L)
				.containsEntry(GlpiNativePositionObject.GLPI_TICKET_ID, 1042L)
				.containsEntry(GlpiNativePositionObject.GLPI_ENTITY_ID, 3L)
				.containsEntry(GlpiNativePositionObject.GLPI_TICKET_NAME, "Printer/offline")
				.containsEntry(GlpiNativePositionObject.GLPI_PRIVATE, true);

		GDocumentReference attachment = document("document-9");
		assertThat(attachment.getName()).isEqualTo("invoice.pdf");
		assertThat(attachment.getExtension()).isEqualTo(".pdf");
		assertThat(attachment.getContentType()).isEqualTo("application/pdf");

		GDocumentReference article = document("kbarticle-15");
		assertThat(article.getName()).isEqualTo("VPN setup.html");
		assertThat(article.getCustomMetaInfos()).containsEntry(GlpiNativePositionObject.GLPI_KB_CATEGORIES, "Network");
	}

	@Test
	void aSelectedTicketIsReadWithTheFoldersAboveIt() throws Exception {
		VFilesystemReference reference = entity(3);
		reference.path = new PathInfo();
		reference.path.absolutePath = "TICKETS|TICKET:1042";
		reference.path.folder = true;
		consume(reference);

		assertThat(folders).extracting(GVirtualFolder::getCode).containsExactly("glpi-source-root/entity-3",
				"glpi-source-root/entity-3/tickets", "glpi-source-root/entity-3/tickets/ticket-1042");
		assertThat(documents).hasSize(4);
	}

	@Test
	void aSelectedArticleIsReadAlone() throws Exception {
		KBArticle article = access.kbArticles(3).get(0);
		when(access.kbArticle(15)).thenReturn(article);
		VFilesystemReference reference = entity(3);
		reference.path = new PathInfo();
		reference.path.absolutePath = "KB|KBARTICLE:15";
		reference.path.folder = false;
		consume(reference);

		assertThat(folders).extracting(GVirtualFolder::getCode).containsExactly("glpi-source-root/entity-3",
				"glpi-source-root/entity-3/knowledge-base");
		assertThat(documents).extracting(GDocumentReference::getCode)
				.containsExactly("glpi-source-root/entity-3/knowledge-base/kbarticle-15");
	}

	@Test
	void aVanishedSelectionReadsNothing() throws Exception {
		VFilesystemReference reference = entity(3);
		reference.path = new PathInfo();
		reference.path.absolutePath = "TICKETS|TICKET:77";
		reference.path.folder = true;
		consume(reference);
		assertThat(folders).isEmpty();
		assertThat(documents).isEmpty();
	}

	@Test
	void aStoredDocumentIsStreamedBackFromItsMetaInfos() throws Exception {
		consume(entity(3));
		Map<String, Object> cache = environment();

		GlpiResourceReference followup = service.getResourceHandle(system, endpoint, document("followup-881"), cache);
		assertThat(followup.objectType).isEqualTo(GlpiPathNodeType.FOLLOWUP);
		assertThat(followup.ticketId).isEqualTo(1042L);
		assertThat(followup.itemId).isEqualTo(881L);
		try (InputStream stream = service.streamResource(system, endpoint, followup, cache)) {
			String html = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
			assertThat(html).contains("<p>Restarted it</p>").contains("#1042 Printer/offline")
					.contains("<meta charset=\"utf-8\">");
		}

		GlpiResourceReference body = service.getResourceHandle(system, endpoint, document("ticket-1042-body"), cache);
		try (InputStream stream = service.streamResource(system, endpoint, body, cache)) {
			String html = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
			assertThat(html).contains("<p>The printer is offline</p>").contains("Root &gt; Sales")
					.contains("Processing (assigned)");
		}
	}

	private GDocumentReference stored(GlpiPathNodeType type, long itemId) {
		GDocumentReference document = new GDocumentReference();
		document.setCode("stored-" + type + "-" + itemId);
		document.setDeleted(false);
		HashMap<String, Object> meta = new HashMap<String, Object>();
		meta.put(GlpiNativePositionObject.GLPI_OBJECT_TYPE, type.name());
		meta.put(GlpiNativePositionObject.GLPI_ITEM_ID, itemId);
		meta.put(GlpiNativePositionObject.GLPI_TICKET_ID, 1042L);
		meta.put(GlpiNativePositionObject.GLPI_ENTITY_ID, 3L);
		document.setCustomMetaInfos(meta);
		return document;
	}

	private Boolean checkDeleted(GDocumentReference document) throws Exception {
		Map<String, Object> environment = environment();
		var checked = service.verifyRemoteObjectExistence(system, endpoint, document,
				service.getResourceHandle(system, endpoint, document, environment), environment);
		return checked == null ? null : checked.getDeleted();
	}

	@Test
	void anItemStillInGlpiIsKept() throws Exception {
		assertThat(checkDeleted(stored(GlpiPathNodeType.FOLLOWUP, 881))).isFalse();
		assertThat(checkDeleted(stored(GlpiPathNodeType.TICKET_BODY, 1042))).isFalse();
	}

	@Test
	void anItemGoneFromGlpiIsDeleted() throws Exception {
		assertThat(checkDeleted(stored(GlpiPathNodeType.FOLLOWUP, 999))).isTrue();
		when(access.kbArticle(15)).thenReturn(null);
		assertThat(checkDeleted(stored(GlpiPathNodeType.KB_ARTICLE, 15))).isTrue();
	}

	@Test
	void theItemsOfADeletedTrashedOrMovedTicketAreDeleted() throws Exception {
		when(access.ticket(1042)).thenReturn(null);
		assertThat(checkDeleted(stored(GlpiPathNodeType.FOLLOWUP, 881))).isTrue();
		when(access.ticket(1042)).thenReturn(read("{'id':1042,'is_deleted':true,'entity':{'id':3}}", Ticket.class));
		assertThat(checkDeleted(stored(GlpiPathNodeType.TICKET_BODY, 1042))).isTrue();
		when(access.ticket(1042)).thenReturn(read("{'id':1042,'entity':{'id':4}}", Ticket.class));
		assertThat(checkDeleted(stored(GlpiPathNodeType.SOLUTION, 12))).isTrue();
	}

	@Test
	void aFailingCheckGivesNoInformation() throws Exception {
		when(access.ticket(1042)).thenThrow(new IllegalStateException("GLPI down"));
		assertThat(checkDeleted(stored(GlpiPathNodeType.FOLLOWUP, 881))).isNull();
	}
}
