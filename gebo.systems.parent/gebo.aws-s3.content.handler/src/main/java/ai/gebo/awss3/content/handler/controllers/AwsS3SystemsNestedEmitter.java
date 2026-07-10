/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.controllers;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController.ControllerNestedEmitter;

@Service
public class AwsS3SystemsNestedEmitter extends ControllerNestedEmitter {

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.AWS_S3_MODULE;
	}
}