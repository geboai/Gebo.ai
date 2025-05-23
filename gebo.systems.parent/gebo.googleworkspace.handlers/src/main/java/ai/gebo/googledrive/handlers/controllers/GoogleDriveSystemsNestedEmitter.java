/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.controllers;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController.ControllerNestedEmitter;

/**
 * AI generated comments
 * 
 * This service class provides Google Drive module-specific implementation of the ControllerNestedEmitter.
 * It's responsible for identifying the messaging module ID for Google Drive operations within the system.
 * The class is registered as a Spring service component for dependency injection.
 */
@Service
public class GoogleDriveSystemsNestedEmitter extends ControllerNestedEmitter {

	/**
	 * Returns the unique identifier for the Google Drive messaging module.
	 * This ID is used by the messaging system to route messages to the appropriate handler.
	 * 
	 * @return String representing the Google Drive module identifier from the standard modules constraints
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GOOGLE_DRIVE_MODULE;
	}

}