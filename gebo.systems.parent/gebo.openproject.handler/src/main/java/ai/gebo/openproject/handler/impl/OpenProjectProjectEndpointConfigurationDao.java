/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * The OpenProject data sources: the declared ones are seeded as read-only stored
 * records, so this DAO reads the stored ones only.
 */
@Service
public class OpenProjectProjectEndpointConfigurationDao extends GAbstractRuntimeConfigurationDao<GOpenProjectProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GOpenProjectProjectEndpoint> {
	public OpenProjectProjectEndpointConfigurationDao(OpenProjectProjectEndpointDynamicSource dynamic) {
		super(List.of(), dynamic);
	}
}
