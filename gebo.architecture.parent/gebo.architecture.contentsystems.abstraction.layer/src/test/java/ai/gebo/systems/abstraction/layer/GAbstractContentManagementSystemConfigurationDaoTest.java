/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * Covers what
 * {@link GAbstractContentManagementSystemConfigurationDao} adds over the plain
 * static+dynamic merge of {@code GAbstractRuntimeConfigurationDao}: the declared
 * systems winning over the stored ones, the implied handled type, and the
 * declarations that must fail the startup.
 *
 * Gebo.ai comment agent
 */
class GAbstractContentManagementSystemConfigurationDaoTest {

	private static final String HANDLED_TYPE = "TEST-CONTENT-HANDLER";

	/** Minimal concrete DAO: the abstract class is what is under test. */
	private static class TestDao extends GAbstractContentManagementSystemConfigurationDao<GContentManagementSystem> {
		TestDao(List<GContentManagementSystem> declared, IGDynamicConfigurationSource<GContentManagementSystem> dynamic,
				String handledTypeCode) {
			super(declared, dynamic, handledTypeCode);
		}
	}

	/** A dynamic source standing in for the module's Mongo repository. */
	private static class StoredSystems implements IGDynamicConfigurationSource<GContentManagementSystem> {
		private final List<GContentManagementSystem> stored;

		StoredSystems(GContentManagementSystem... systems) {
			this.stored = new ArrayList<GContentManagementSystem>(Arrays.asList(systems));
		}

		@Override
		public List<GContentManagementSystem> getConfigurations() {
			return stored;
		}

		@Override
		public GContentManagementSystem findByCode(String code) {
			for (GContentManagementSystem system : stored) {
				if (system.getCode().equalsIgnoreCase(code)) {
					return system;
				}
			}
			return null;
		}
	}

	private static GContentManagementSystem system(String code, String description) {
		GContentManagementSystem system = new GContentManagementSystem();
		system.setCode(code);
		system.setDescription(description);
		return system;
	}

	@Test
	void declaredAndStoredSystemsAreBothReturned() {
		TestDao dao = new TestDao(List.of(system("declared", "from the file")),
				new StoredSystems(system("stored", "from the UI")), HANDLED_TYPE);

		List<GContentManagementSystem> configurations = dao.getConfigurations();

		assertEquals(2, configurations.size());
		assertEquals("declared", configurations.get(0).getCode());
		assertEquals("stored", configurations.get(1).getCode());
	}

	@Test
	void aStoredSystemCannotDisplaceADeclaredOne() {
		GContentManagementSystem declared = system("shared-code", "from the file");
		TestDao dao = new TestDao(List.of(declared), new StoredSystems(system("SHARED-CODE", "from the UI")),
				HANDLED_TYPE);

		List<GContentManagementSystem> configurations = dao.getConfigurations();

		assertEquals(1, configurations.size(), "the admin list must not show the same system twice");
		assertSame(declared, configurations.get(0));
		assertSame(declared, dao.findByCode("shared-code"));
		assertSame(declared, dao.findByCode("SHARED-CODE"));
		assertTrue(dao.isDeclaredInConfiguration("Shared-Code"));
	}

	@Test
	void aStoredSystemIsStillResolvedWhenNothingDeclaresItsCode() {
		TestDao dao = new TestDao(List.of(system("declared", null)), new StoredSystems(system("stored", null)),
				HANDLED_TYPE);

		assertEquals("stored", dao.findByCode("stored").getCode());
		assertFalse(dao.isDeclaredInConfiguration("stored"));
		assertNull(dao.findByCode("never-configured"));
	}

	@Test
	void theHandledTypeIsAppliedToADeclarationThatDoesNotNameOne() {
		GContentManagementSystem implied = system("implied", null);
		GContentManagementSystem explicit = system("explicit", null);
		explicit.setContentManagementSystemType("SOME-OTHER-HANDLER");

		new TestDao(List.of(implied, explicit), null, HANDLED_TYPE);

		assertEquals(HANDLED_TYPE, implied.getContentManagementSystemType());
		assertEquals("SOME-OTHER-HANDLER", explicit.getContentManagementSystemType(),
				"a declaration that names its own type keeps it");
	}

	@Test
	void aModuleWithSeveralHandledTypesImpliesNone() {
		GContentManagementSystem declared = system("declared", null);

		new TestDao(List.of(declared), null, null);

		assertNull(declared.getContentManagementSystemType());
	}

	@Test
	void aDeclarationWithoutACodeFailsTheStartup() {
		List<GContentManagementSystem> declared = List.of(system(" ", "no code"));

		assertThrows(IllegalStateException.class, () -> new TestDao(declared, null, HANDLED_TYPE));
	}

	@Test
	void aCodeDeclaredTwiceFailsTheStartup() {
		List<GContentManagementSystem> declared = List.of(system("twice", "first"), system("TWICE", "second"));

		assertThrows(IllegalStateException.class, () -> new TestDao(declared, null, HANDLED_TYPE));
	}

	@Test
	void noDeclarationsLeavesTheStoredSystemsAlone() {
		TestDao dao = new TestDao(null, new StoredSystems(system("stored", null)), HANDLED_TYPE);

		assertEquals(1, dao.getConfigurations().size());
		assertEquals("stored", dao.findByCode("stored").getCode());
	}
}
