/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectNavigationCoordinates;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;

class OpenProjectNavigationUtilTest {

	@Test
	void pathRoundTrip() {
		String path = OpenProjectNavigationUtil.childPath(null, OpenProjectPathNodeType.SECTION, "MEETINGS");
		path = OpenProjectNavigationUtil.childPath(path, OpenProjectPathNodeType.MEETING, "7");
		path = OpenProjectNavigationUtil.childPath(path, OpenProjectPathNodeType.MEETING_CONTENT, "7");
		assertEquals("SECTION:MEETINGS|MEETING:7|MEETING_CONTENT:7", path);
		List<OpenProjectPathComponent> steps = OpenProjectNavigationUtil.parsePath(path);
		assertEquals(3, steps.size());
		assertEquals(OpenProjectPathNodeType.MEETING, steps.get(1).type);
		assertEquals("7", steps.get(1).id);
		assertEquals("SECTION:MEETINGS|MEETING:7", OpenProjectNavigationUtil.parentPath(path));
		assertNull(OpenProjectNavigationUtil.parentPath("SECTION:MEETINGS"));
	}

	@Test
	void rootsCarryTheProjectIdentifier() {
		var root = OpenProjectNavigationUtil.toRoot("gebo-core", "Gebo core", "http://openproject/projects/gebo-core");
		assertEquals("PROJECT:gebo-core", root.getCode());
		assertEquals("PROJECT:gebo-core", root.getAbsolutePath());
		assertEquals("Gebo core (gebo-core)", root.getDescription());
		assertEquals("gebo-core", OpenProjectNavigationUtil.projectIdOf(root));
	}

	@Test
	void unknownStepIsRefused() {
		assertThrows(IllegalArgumentException.class, () -> OpenProjectNavigationUtil.parsePath("NOPE:1"));
		assertThrows(IllegalArgumentException.class, () -> OpenProjectNavigationUtil.parsePath("WORK_PACKAGE"));
	}

	@Test
	void coordinatesDescribeTheirLastStepForTheJobMessages() {
		VFilesystemReference page = OpenProjectNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, "WORK_PACKAGES"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE, "38")),
				"#38 SMTP");
		assertEquals("work package #38 of the OpenProject project gebo-demo",
				OpenProjectNavigationUtil.toCoordinates(page).describe());
		VFilesystemReference section = OpenProjectNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, "WORK_PACKAGES")), "Work packages");
		assertEquals("the work packages section of the OpenProject project gebo-demo",
				OpenProjectNavigationUtil.toCoordinates(section).describe());
	}

	@Test
	void referenceToCoordinatesKeepsEveryStep() {
		VFilesystemReference reference = OpenProjectNavigationUtil.toReference("gebo-core", "Gebo core",
				List.of(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, "WORK_PACKAGES"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE, "12"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.ATTACHMENT, "34")),
				"log.txt");
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:12|ATTACHMENT:34", reference.path.absolutePath);
		assertFalse(reference.path.folder);
		OpenProjectNavigationCoordinates coordinates = OpenProjectNavigationUtil.toCoordinates(reference);
		assertEquals(3, coordinates.getBrowsingStepsCustom().size());
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:12", coordinates.getBrowsingSteps().get(1).absolutePath);
		assertTrue(coordinates.getBrowsingSteps().get(1).folder);
		assertEquals("log.txt", coordinates.getBrowsingSteps().get(2).name);
	}

	@Test
	void nativesToCoordinatesMatchTheBrowsingPaths() {
		String base = "http://openproject";
		List<OpenProjectNativePositionObject> natives = List.of(OpenProjectNodes.project(base, "gebo-core", "Gebo core"),
				OpenProjectNodes.section(base, "gebo-core", ai.gebo.openproject.handler.model.OpenProjectSection.WORK_PACKAGES),
				OpenProjectNodes.workPackage(base, "gebo-core", Map.of("id", 12, "subject", "Crash on save")),
				OpenProjectNodes.workPackageContent(base, "gebo-core", Map.of("id", 12, "subject", "Crash on save")));
		OpenProjectNavigationCoordinates coordinates = OpenProjectNavigationUtil.toCoordinates(natives);
		assertEquals("PROJECT:gebo-core", coordinates.getRoot().getCode());
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:12|WORK_PACKAGE_CONTENT:12",
				coordinates.getBrowsingSteps().get(2).absolutePath);
		assertEquals("#12 Crash on save.md", coordinates.getBrowsingSteps().get(2).name);
	}

	@Test
	void anInternalCommentIsAPageUnderItsWorkPackage() {
		VFilesystemReference comment = OpenProjectNavigationUtil.toReference("gebo-demo", "Gebo demo",
				List.of(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, "WORK_PACKAGES"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE, "38"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.INTERNAL_COMMENT, "57")),
				"#38 internal comment 57.md");
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:38|INTERNAL_COMMENT:57", comment.path.absolutePath);
		assertFalse(comment.path.folder);
		assertEquals(PathInfoMetaType.WEB_PAGE, comment.path.metaType);
		OpenProjectNavigationCoordinates coordinates = OpenProjectNavigationUtil.toCoordinates(comment);
		assertEquals(OpenProjectPathNodeType.INTERNAL_COMMENT, coordinates.getBrowsingStepsCustom().get(2).type);
		assertEquals("internal comment 57 of the OpenProject project gebo-demo", coordinates.describe());
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:38",
				OpenProjectNavigationUtil.parentPath(comment.path.absolutePath));
	}

	@Test
	void namesLoseTheCharactersFilesCannotHold() {
		assertEquals("#7 a-b-c-d", OpenProjectNavigationUtil.sanitizeName("#7 a/b:c|d"));
	}
}
