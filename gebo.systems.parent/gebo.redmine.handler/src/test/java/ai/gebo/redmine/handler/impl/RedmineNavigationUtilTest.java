/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedmineNavigationCoordinates;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;

class RedmineNavigationUtilTest {

	@Test
	void pathRoundTrip() {
		String path = RedmineNavigationUtil.childPath(null, RedminePathNodeType.SECTION, "WIKI");
		path = RedmineNavigationUtil.childPath(path, RedminePathNodeType.WIKI_PAGE, "Install: Linux");
		path = RedmineNavigationUtil.childPath(path, RedminePathNodeType.WIKI_CONTENT, "Install: Linux");
		assertEquals("SECTION:WIKI|WIKI_PAGE:Install: Linux|WIKI_CONTENT:Install: Linux", path);
		List<RedminePathComponent> steps = RedmineNavigationUtil.parsePath(path);
		assertEquals(3, steps.size());
		assertEquals(RedminePathNodeType.WIKI_PAGE, steps.get(1).type);
		// a title with a colon is split at the first colon only
		assertEquals("Install: Linux", steps.get(1).id);
		assertEquals("SECTION:WIKI|WIKI_PAGE:Install: Linux", RedmineNavigationUtil.parentPath(path));
		assertNull(RedmineNavigationUtil.parentPath("SECTION:WIKI"));
	}

	@Test
	void rootsCarryTheProjectIdentifier() {
		var root = RedmineNavigationUtil.toRoot("gebo-core", "Gebo core", "http://redmine/projects/gebo-core");
		assertEquals("PROJECT:gebo-core", root.getCode());
		assertEquals("PROJECT:gebo-core", root.getAbsolutePath());
		assertEquals("Gebo core (gebo-core)", root.getDescription());
		assertEquals("gebo-core", RedmineNavigationUtil.projectIdOf(root));
	}

	@Test
	void unknownStepIsRefused() {
		assertThrows(IllegalArgumentException.class, () -> RedmineNavigationUtil.parsePath("NOPE:1"));
		assertThrows(IllegalArgumentException.class, () -> RedmineNavigationUtil.parsePath("ISSUE"));
	}

	@Test
	void coordinatesDescribeTheirLastStepForTheJobMessages() {
		VFilesystemReference page = RedmineNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new RedminePathComponent(RedminePathNodeType.SECTION, "WIKI"),
						new RedminePathComponent(RedminePathNodeType.WIKI_PAGE, "Install_guide")),
				"Install_guide");
		assertEquals("wiki page Install_guide of the Redmine project gebo-demo",
				RedmineNavigationUtil.toCoordinates(page).describe());
		VFilesystemReference section = RedmineNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new RedminePathComponent(RedminePathNodeType.SECTION, "ISSUES")), "Issues");
		assertEquals("the issues section of the Redmine project gebo-demo",
				RedmineNavigationUtil.toCoordinates(section).describe());
	}

	@Test
	void referenceToCoordinatesKeepsEveryStep() {
		VFilesystemReference reference = RedmineNavigationUtil.toReference("gebo-core", "Gebo core",
				List.of(new RedminePathComponent(RedminePathNodeType.SECTION, "ISSUES"),
						new RedminePathComponent(RedminePathNodeType.ISSUE, "12"),
						new RedminePathComponent(RedminePathNodeType.ATTACHMENT, "34")),
				"log.txt");
		assertEquals("SECTION:ISSUES|ISSUE:12|ATTACHMENT:34", reference.path.absolutePath);
		assertFalse(reference.path.folder);
		RedmineNavigationCoordinates coordinates = RedmineNavigationUtil.toCoordinates(reference);
		assertEquals(3, coordinates.getBrowsingStepsCustom().size());
		assertEquals("SECTION:ISSUES|ISSUE:12", coordinates.getBrowsingSteps().get(1).absolutePath);
		assertTrue(coordinates.getBrowsingSteps().get(1).folder);
		assertEquals("log.txt", coordinates.getBrowsingSteps().get(2).name);
	}

	@Test
	void nativesToCoordinatesMatchTheBrowsingPaths() {
		String base = "http://redmine";
		List<RedmineNativePositionObject> natives = List.of(RedmineNodes.project(base, "gebo-core", "Gebo core"),
				RedmineNodes.section(base, "gebo-core", ai.gebo.redmine.handler.model.RedmineSection.ISSUES),
				RedmineNodes.issue(base, "gebo-core", 12, "Crash on save"),
				RedmineNodes.issueContent(base, "gebo-core", 12, "Crash on save", null));
		RedmineNavigationCoordinates coordinates = RedmineNavigationUtil.toCoordinates(natives);
		assertEquals("PROJECT:gebo-core", coordinates.getRoot().getCode());
		assertEquals("SECTION:ISSUES|ISSUE:12|ISSUE_CONTENT:12",
				coordinates.getBrowsingSteps().get(2).absolutePath);
		assertEquals("#12 Crash on save.md", coordinates.getBrowsingSteps().get(2).name);
	}

	@Test
	void aPrivateNoteIsAWebPageUnderItsIssue() {
		VFilesystemReference note = RedmineNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new RedminePathComponent(RedminePathNodeType.SECTION, "ISSUES"),
						new RedminePathComponent(RedminePathNodeType.ISSUE, "12"),
						new RedminePathComponent(RedminePathNodeType.PRIVATE_NOTE, "56")),
				"#12 private note 56.md");
		assertEquals("SECTION:ISSUES|ISSUE:12|PRIVATE_NOTE:56", note.path.absolutePath);
		assertFalse(note.path.folder);
		assertEquals(ai.gebo.model.virtualfs.PathInfoMetaType.WEB_PAGE, note.path.metaType);
		assertEquals("SECTION:ISSUES|ISSUE:12", RedmineNavigationUtil.parentPath(note.path.absolutePath));
		RedmineNavigationCoordinates coordinates = RedmineNavigationUtil.toCoordinates(note);
		assertEquals(RedminePathNodeType.PRIVATE_NOTE, coordinates.getBrowsingStepsCustom().get(2).type);
		assertEquals("56", coordinates.getBrowsingStepsCustom().get(2).id);
		assertEquals("private note 56 of the Redmine project gebo-demo", coordinates.describe());
	}

	@Test
	void namesLoseTheCharactersFilesCannotHold() {
		assertEquals("#7 a-b-c-d", RedmineNavigationUtil.sanitizeName("#7 a/b:c|d"));
	}
}
