package ai.gebo.userspace.handler.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController.ControllerNestedEmitter;

/**
 * Nested emitter component for userspace controller messaging
 */
@Component
@Scope("singleton")
public class UserspaceControllerEmitter extends ControllerNestedEmitter {

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.USERSPACE_MODULE;
	}
}