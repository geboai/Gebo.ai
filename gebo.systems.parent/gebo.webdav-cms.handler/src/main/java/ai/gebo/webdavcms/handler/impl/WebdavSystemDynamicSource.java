/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.repositories.WebdavContentManagementSystemRepository;

/**
 * The WebDAV systems created through the admin UI, read from Mongo: the dynamic
 * half that {@link WebdavSystemsConfigurationDao} combines with the ones
 * declared in the configuration.
 *
 * Gebo.ai comment agent
 */
@Service
public class WebdavSystemDynamicSource extends
		GDynamicConfigurationSourceAdapter<GWebdavContentManagementSystem, WebdavContentManagementSystemRepository>
		implements IGDynamicConfigurationSource<GWebdavContentManagementSystem> {

}
