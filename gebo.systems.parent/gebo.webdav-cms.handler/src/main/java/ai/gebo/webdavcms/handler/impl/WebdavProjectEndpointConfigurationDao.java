/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;

/**
 * The WebDAV data sources, read from the module's repository.
 *
 * <p>
 * Built over the repository alone: the sources declared under
 * {@code ai.gebo.webdav.datasources} are written into that same repository at
 * startup by {@link WebdavDeclaredDataSourcesSeeder}, so there is one place a
 * data source can come from and every path that resolves one - this DAO, the
 * scheduler, the job launcher - sees the same records.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class WebdavProjectEndpointConfigurationDao extends GAbstractRuntimeConfigurationDao<GWebdavProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GWebdavProjectEndpoint> {

	/**
	 * Constructs the DAO over the module's repository.
	 *
	 * @param dynamic the repository-backed source of the stored data sources.
	 */
	public WebdavProjectEndpointConfigurationDao(WebdavProjectEndpointDynamicSource dynamic) {
		super(List.of(), dynamic);
	}
}
