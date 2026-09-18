/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * The SharePoint/OneDrive data sources, read from the module's repository.
 *
 * <p>
 * Built over the repository alone: the sources declared under
 * {@code ai.gebo.sharepoint.datasources} are written into that same repository
 * at startup by {@link SharepointDeclaredDataSourcesSeeder}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
class SharepointProjectEndpointConfiguratoinDao
		extends GAbstractRuntimeConfigurationDao<GSharepointProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GSharepointProjectEndpoint> {

	/**
	 * Constructs the DAO over the module's repository.
	 *
	 * @param dynamic the repository-backed source of the stored data sources.
	 */
	public SharepointProjectEndpointConfiguratoinDao(SharepointProjectEndpointDynamicConfigurationSource dynamic) {
		super(List.of(), dynamic);
	}
}
