/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.config.SharepointSystemsConfig;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemConfigurationDao;

/**
 * The SharePoint systems this deployment knows about: the ones declared under
 * {@code ai.gebo.sharepoint.systems} plus the ones an admin created through the
 * UI, combined by {@link GAbstractContentManagementSystemConfigurationDao} -
 * which is also where a declared code wins over a stored record carrying the
 * same one.
 *
 * Gebo.ai comment agent
 */
@Service
class SharepointSystemsConfiguratoinDao
		extends GAbstractContentManagementSystemConfigurationDao<GSharepointContentManagementSystem> {

	/**
	 * Constructs the DAO over the declared systems and the Mongo-backed ones.
	 *
	 * @param config  the declared SharePoint systems.
	 * @param dynamic the repository-backed source of the stored ones.
	 */
	public SharepointSystemsConfiguratoinDao(SharepointSystemsConfig config,
			SharepointSystemsDynamicConfigurationSource dynamic) {
		super(config.getSystems(), dynamic, GStandardModulesConstraints.SHAREPOINT_MODULE);
	}
}
