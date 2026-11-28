/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.config.ZammadSystemsConfig;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemConfigurationDao;

/**
 * The Zammad systems, declared in the configuration and stored: a declared
 * code wins over a stored one.
 */
@Service
public class ZammadSystemsConfigurationDao extends GAbstractContentManagementSystemConfigurationDao<GZammadSystem> {
	public ZammadSystemsConfigurationDao(ZammadSystemsConfig config, ZammadSystemDynamicSource dynamic) {
		super(config.getSystems(), dynamic, ZammadContentManagementHandlerImpl.ZAMMAD);
	}
}
