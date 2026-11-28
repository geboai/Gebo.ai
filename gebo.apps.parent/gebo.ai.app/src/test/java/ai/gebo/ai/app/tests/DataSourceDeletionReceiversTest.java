/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessagingSystem;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;

/**
 * Certifies that every content handler deletes its data sources: a systems
 * controller's delete sends the deletion to its module's resources dispose
 * component, the only receiver that removes the data source itself, so a module
 * without one answers the delete and keeps the data source.
 */
public class DataSourceDeletionReceiversTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	List<GAbstractSystemsArchitectureController<?, ?>> controllers;

	@Autowired
	List<IGMessageReceiverFactory> receivers;

	@Test
	public void everySystemsControllerModuleHasADisposeReceiver() {
		final Set<String> disposing = receivers.stream()
				.filter(x -> GStandardModulesConstraints.RESOURCES_DISPOSE_COMPONENT.equals(x.getMessagingSystemId()))
				.map(IGMessageReceiverFactory::getMessagingModuleId).collect(Collectors.toSet());
		final TreeMap<String, String> deleting = new TreeMap<>();
		for (GAbstractSystemsArchitectureController<?, ?> controller : controllers) {
			final Object target = AopTestUtils.getUltimateTargetObject(controller);
			final IGMessagingSystem emitter = (IGMessagingSystem) ReflectionTestUtils.getField(target,
					"controllerEmitter");
			deleting.put(emitter.getMessagingModuleId(), target.getClass().getSimpleName());
		}
		LOGGER.info("Modules deleting data sources: " + deleting + ", disposing them: " + disposing);
		assertFalse(deleting.isEmpty(), "the systems controllers found");
		final TreeMap<String, String> missing = new TreeMap<>(deleting);
		missing.keySet().removeAll(disposing);
		assertTrue(missing.isEmpty(), "modules whose deleted data sources are never removed: " + missing);
	}
}
