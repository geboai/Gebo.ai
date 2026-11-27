/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.config.RedmineSystemsConfig;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemConfigurationDao;

/**
 * The Redmine systems, declared in the configuration and stored: a declared
 * code wins over a stored one.
 */
@Service
public class RedmineSystemsConfigurationDao extends GAbstractContentManagementSystemConfigurationDao<GRedmineSystem> {
	public RedmineSystemsConfigurationDao(RedmineSystemsConfig config, RedmineSystemDynamicSource dynamic) {
		super(config.getSystems(), dynamic, RedmineContentManagementHandlerImpl.REDMINE);
	}
}
