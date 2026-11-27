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

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiNavigationCoordinates;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;

class GlpiNavigationUtilTest {

	@Test
	void anEntityRootEncodesItsIdOnly() {
		var root = GlpiNavigationUtil.toRoot(3, "Root entity > Sales");
		assertThat(root.getCode()).isEqualTo("ENTITY:3");
		assertThat(root.getAbsolutePath()).isEqualTo("ENTITY:3");
		assertThat(root.getDescription()).isEqualTo("Root entity > Sales");
		assertThat(GlpiNavigationUtil.entityIdOf(root)).isEqualTo(3L);
	}

	@Test
	void aRootThatIsNotAnEntityHasNoEntityId() {
		var root = GlpiNavigationUtil.toRoot(3, null);
		root.setCode("PROJECT-KEY:X");
		root.setAbsolutePath("PROJECT-KEY:X");
		assertThat(GlpiNavigationUtil.entityIdOf(root)).isNull();
		assertThat(GlpiNavigationUtil.entityIdOf(null)).isNull();
	}

	@Test
	void everySegmentRoundTrips() {
		for (GlpiPathNodeType type : GlpiPathNodeType.values()) {
			if (type == GlpiPathNodeType.ENTITY) {
				continue;
			}
			Long id = type == GlpiPathNodeType.TICKETS_SECTION || type == GlpiPathNodeType.KB_SECTION ? null : 42L;
			String segment = GlpiNavigationUtil.segment(new GlpiPathComponent(type, id));
			GlpiPathComponent parsed = GlpiNavigationUtil.parseSegment(segment);
			assertThat(parsed.type).as(segment).isEqualTo(type);
			assertThat(parsed.id).as(segment).isEqualTo(id);
		}
	}

	@Test
	void theTicketPrefixDoesNotSwallowTheTicketBodyOne() {
		assertThat(GlpiNavigationUtil.parseSegment("TICKET-BODY:7").type).isEqualTo(GlpiPathNodeType.TICKET_BODY);
		assertThat(GlpiNavigationUtil.parseSegment("TICKET:7").type).isEqualTo(GlpiPathNodeType.TICKET);
	}

	@Test
	void unknownOrMalformedSegmentsAreIgnored() {
		assertThat(GlpiNavigationUtil.parseSegment("ISSUE-KEY:7")).isNull();
		assertThat(GlpiNavigationUtil.parseSegment("TICKET:abc")).isNull();
		assertThat(GlpiNavigationUtil.parseSegment("TICKET")).isNull();
		assertThat(GlpiNavigationUtil.parsePath("TICKETS|BOGUS|TICKET:5")).extracting(c -> c.type)
				.containsExactly(GlpiPathNodeType.TICKETS_SECTION, GlpiPathNodeType.TICKET);
	}

	@Test
	void childPathsAreCumulative() {
		PathInfo section = GlpiNavigationUtil.toPathInfo(null,
				new GlpiPathComponent(GlpiPathNodeType.TICKETS_SECTION, null), "Tickets");
		PathInfo ticket = GlpiNavigationUtil.toPathInfo(section, new GlpiPathComponent(GlpiPathNodeType.TICKET, 1042L),
				"#1042 Printer offline");
		PathInfo followup = GlpiNavigationUtil.toPathInfo(ticket,
				new GlpiPathComponent(GlpiPathNodeType.FOLLOWUP, 881L), "f");
		PathInfo document = GlpiNavigationUtil.toPathInfo(ticket,
				new GlpiPathComponent(GlpiPathNodeType.DOCUMENT, 9L), "invoice.pdf");
		assertThat(section.absolutePath).isEqualTo("TICKETS");
		assertThat(section.folder).isTrue();
		assertThat(ticket.absolutePath).isEqualTo("TICKETS|TICKET:1042");
		assertThat(ticket.folder).isTrue();
		assertThat(ticket.metaType).isEqualTo(PathInfoMetaType.FOLDER);
		assertThat(followup.absolutePath).isEqualTo("TICKETS|TICKET:1042|FOLLOWUP:881");
		assertThat(followup.folder).isFalse();
		assertThat(followup.metaType).isEqualTo(PathInfoMetaType.WEB_PAGE);
		assertThat(document.metaType).isEqualTo(PathInfoMetaType.FILE);
	}

	@Test
	void aStoredReferenceDecodesIntoTypedSteps() {
		VFilesystemReference reference = GlpiNavigationUtil.toVirtualFilesystemReference(3, "Sales",
				List.of(new GlpiPathComponent(GlpiPathNodeType.TICKETS_SECTION, null),
						new GlpiPathComponent(GlpiPathNodeType.TICKET, 1042L),
						new GlpiPathComponent(GlpiPathNodeType.TICKET_BODY, 1042L)),
				"Ticket #1042.html");
		assertThat(reference.path.absolutePath).isEqualTo("TICKETS|TICKET:1042|TICKET-BODY:1042");
		assertThat(reference.path.folder).isFalse();
		assertThat(reference.path.name).isEqualTo("Ticket #1042.html");

		GlpiNavigationCoordinates coordinates = GlpiNavigationUtil.toNavigationCoordinates(reference);
		assertThat(coordinates.getRoot().getCode()).isEqualTo("ENTITY:3");
		assertThat(coordinates.getBrowsingStepsCustom()).extracting(c -> c.type).containsExactly(
				GlpiPathNodeType.TICKETS_SECTION, GlpiPathNodeType.TICKET, GlpiPathNodeType.TICKET_BODY);
		assertThat(coordinates.getBrowsingSteps()).extracting(p -> p.absolutePath).containsExactly("TICKETS",
				"TICKETS|TICKET:1042", "TICKETS|TICKET:1042|TICKET-BODY:1042");
		assertThat(coordinates.getBrowsingSteps().get(2).folder).isFalse();
	}

	@Test
	void aRootOnlyReferenceHasNoStep() {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = GlpiNavigationUtil.toRoot(0, "Root entity");
		GlpiNavigationCoordinates coordinates = GlpiNavigationUtil.toNavigationCoordinates(reference);
		assertThat(coordinates.getBrowsingSteps()).isEmpty();
		assertThat(coordinates.getBrowsingStepsCustom()).isEmpty();
	}

	@Test
	void aNativePathEncodesLikeTheStoredReference() {
		List<GlpiNativePositionObject> natives = List.of(GlpiNativeObjects.entity(3, "Sales", null),
				GlpiNativeObjects.ticketsSection(3),
				new GlpiNativePositionObject(GlpiPathNodeType.TICKET, 1042L, "ticket-1042", "#1042", null));
		GlpiNavigationCoordinates coordinates = GlpiNavigationUtil.toNavigationCoordinates(natives);
		assertThat(coordinates.getRoot().getCode()).isEqualTo("ENTITY:3");
		assertThat(coordinates.getBrowsingSteps()).extracting(p -> p.absolutePath).containsExactly("TICKETS",
				"TICKETS|TICKET:1042");
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = coordinates.getRoot();
		reference.path = coordinates.getBrowsingSteps().get(1);
		assertThat(GlpiNavigationUtil.toNavigationCoordinates(reference).getBrowsingStepsCustom())
				.extracting(c -> c.id).containsExactly(null, 1042L);
	}

	@Test
	void namesLoseSlashesAndLineBreaks() {
		assertThat(GlpiNavigationUtil.sanitizeName(" Printer/Scanner\n offline \r\n")).isEqualTo("Printer-Scanner offline");
		assertThat(GlpiNavigationUtil.sanitizeName("   ")).isNull();
		assertThat(GlpiNavigationUtil.sanitizeName(null)).isNull();
	}
}
