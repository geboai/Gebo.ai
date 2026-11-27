/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.zammad.client.model.Group;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadNavigationCoordinates;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;

class ZammadNavigationUtilTest {

	@Test
	void pathRoundTrip() {
		String path = ZammadNavigationUtil.childPath(null, ZammadPathNodeType.CATEGORY, "1");
		path = ZammadNavigationUtil.childPath(path, ZammadPathNodeType.CATEGORY, "2");
		path = ZammadNavigationUtil.childPath(path, ZammadPathNodeType.ANSWER, "7");
		assertEquals("CATEGORY:1|CATEGORY:2|ANSWER:7", path);
		List<ZammadPathComponent> steps = ZammadNavigationUtil.parsePath(path);
		assertEquals(3, steps.size());
		assertEquals(ZammadPathNodeType.CATEGORY, steps.get(1).type);
		assertEquals("2", steps.get(1).id);
		assertEquals("CATEGORY:1|CATEGORY:2", ZammadNavigationUtil.parentPath(path));
		assertNull(ZammadNavigationUtil.parentPath("CATEGORY:1"));
	}

	@Test
	void rootsAreGroupsAndKnowledgeBases() {
		var group = ZammadNavigationUtil.toRoot(ZammadPathNodeType.GROUP, "2", "Tickets of Gebo Support", null);
		assertEquals("GROUP:2", group.getCode());
		assertEquals("GROUP:2", group.getAbsolutePath());
		assertEquals("Tickets of Gebo Support", group.getDescription());
		ZammadPathComponent root = ZammadNavigationUtil.rootOf(group);
		assertEquals(ZammadPathNodeType.GROUP, root.type);
		assertEquals("2", root.id);
		var kb = ZammadNavigationUtil.toRoot(ZammadPathNodeType.KNOWLEDGE_BASE, "1", null, null);
		assertEquals("KNOWLEDGE_BASE:1", kb.getDescription());
		assertEquals(ZammadPathNodeType.KNOWLEDGE_BASE, ZammadNavigationUtil.rootOf(kb).type);
		assertThrows(IllegalArgumentException.class,
				() -> ZammadNavigationUtil.toRoot(ZammadPathNodeType.TICKET, "2", null, null));
		var notRoot = ZammadNavigationUtil.toRoot(ZammadPathNodeType.GROUP, "2", null, null);
		notRoot.setAbsolutePath("TICKET:2");
		assertThrows(IllegalArgumentException.class, () -> ZammadNavigationUtil.rootOf(notRoot));
	}

	@Test
	void unknownStepIsRefused() {
		assertThrows(IllegalArgumentException.class, () -> ZammadNavigationUtil.parsePath("NOPE:1"));
		assertThrows(IllegalArgumentException.class, () -> ZammadNavigationUtil.parsePath("TICKET"));
	}

	@Test
	void coordinatesDescribeTheirLastStepForTheJobMessages() {
		VFilesystemReference ticket = ZammadNavigationUtil.toReference(ZammadPathNodeType.GROUP, "2", null,
				List.of(new ZammadPathComponent(ZammadPathNodeType.TICKET, "38")), "#97038 SMTP");
		assertEquals("ticket 38 of the Zammad group 2", ZammadNavigationUtil.toCoordinates(ticket).describe());
		VFilesystemReference answer = ZammadNavigationUtil.toReference(ZammadPathNodeType.KNOWLEDGE_BASE, "1", null,
				List.of(new ZammadPathComponent(ZammadPathNodeType.CATEGORY, "1"),
						new ZammadPathComponent(ZammadPathNodeType.ANSWER, "3")),
				"Install guide");
		assertEquals("answer 3 of the Zammad knowledge base 1", ZammadNavigationUtil.toCoordinates(answer).describe());
		VFilesystemReference whole = ZammadNavigationUtil.toReference(ZammadPathNodeType.KNOWLEDGE_BASE, "1", null,
				List.of(), null);
		assertEquals("the Zammad knowledge base 1", ZammadNavigationUtil.toCoordinates(whole).describe());
	}

	@Test
	void referenceToCoordinatesKeepsEveryStep() {
		VFilesystemReference reference = ZammadNavigationUtil.toReference(ZammadPathNodeType.GROUP, "2",
				"Tickets of Gebo Support", List.of(new ZammadPathComponent(ZammadPathNodeType.TICKET, "2"),
						new ZammadPathComponent(ZammadPathNodeType.ATTACHMENT, "5-9")),
				"relay.log");
		assertEquals("TICKET:2|ATTACHMENT:5-9", reference.path.absolutePath);
		assertFalse(reference.path.folder);
		ZammadNavigationCoordinates coordinates = ZammadNavigationUtil.toCoordinates(reference);
		assertEquals(2, coordinates.getBrowsingStepsCustom().size());
		assertEquals("TICKET:2", coordinates.getBrowsingSteps().get(0).absolutePath);
		assertTrue(coordinates.getBrowsingSteps().get(0).folder);
		assertEquals("relay.log", coordinates.getBrowsingSteps().get(1).name);
	}

	@Test
	void nativesToCoordinatesMatchTheBrowsingPaths() {
		String base = "http://zammad";
		Ticket ticket = ZammadTestData.ticket();
		Group group = ZammadTestData.read("{'id':2,'name':'Gebo Support','active':true}", Group.class);
		String rootCode = ZammadNodes.rootCode(ZammadPathNodeType.GROUP, 2);
		List<ZammadNativePositionObject> natives = List.of(ZammadNodes.group(base, group),
				ZammadNodes.ticket(base, rootCode, ticket), ZammadNodes.ticketContent(base, rootCode, ticket));
		ZammadNavigationCoordinates coordinates = ZammadNavigationUtil.toCoordinates(natives);
		assertEquals("GROUP:2", coordinates.getRoot().getCode());
		assertEquals("Tickets of Gebo Support", coordinates.getRoot().getDescription());
		assertEquals("TICKET:2|TICKET_CONTENT:2", coordinates.getBrowsingSteps().get(1).absolutePath);
		assertEquals("#97002 SMTP relay rejects large mails.html", coordinates.getBrowsingSteps().get(1).name);
		assertFalse(coordinates.getBrowsingSteps().get(1).folder);
	}

	@Test
	void namesLoseTheCharactersFilesCannotHold() {
		assertEquals("#7 a-b-c-d", ZammadNavigationUtil.sanitizeName("#7 a/b:c|d"));
	}
}
