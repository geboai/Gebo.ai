/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.atlassian.jira.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.config.JiraSystemsConfig;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemConfigurationDao;

/**
 * The Jira systems this deployment knows about: the ones declared under
 * {@code ai.gebo.jira.systems} plus the ones an admin created through the UI,
 * combined by {@link GAbstractContentManagementSystemConfigurationDao} - which
 * is also where a declared code wins over a stored record carrying the same one.
 *
 * Gebo.ai comment agent
 */
@Service
public class JiraSystemsConfigurationDao extends GAbstractContentManagementSystemConfigurationDao<GJiraSystem> {

	/**
	 * Constructs the DAO over the declared systems and the Mongo-backed ones.
	 *
	 * @param config  the declared Jira systems.
	 * @param dynamic the repository-backed source of the stored ones.
	 */
	public JiraSystemsConfigurationDao(JiraSystemsConfig config, JiraSystemDynamicSource dynamic) {
		super(config.getSystems(), dynamic, JiraContentManagementHandlerImpl.ATLASSIAN_JIRA);
	}
}
