/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;

/**
 * The decision models running in this instance: created from their persisted
 * configurations at startup, added, reconfigured and deleted as the admin changes them,
 * the changes propagated to the cluster. {@link #defaultHandler()} is the decision
 * model the services use when none is indicated, null when none is configured.
 */
public interface IGDecisionModelRuntimeConfigurationDao
		extends IGRuntimeModelConfigurationDao<IGConfigurableDecisionModel, GBaseDecisionModelConfig> {

}
