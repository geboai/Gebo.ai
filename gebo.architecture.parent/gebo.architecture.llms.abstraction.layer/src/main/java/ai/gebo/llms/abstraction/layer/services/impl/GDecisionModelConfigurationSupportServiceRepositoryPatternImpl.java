/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportServiceRepositoryPattern;

/**
 * The decision model support services present in the context, keyed by the model type
 * code they declare: the same key the configurations carry in modelTypeCode.
 */
@Service
public class GDecisionModelConfigurationSupportServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGDecisionModelConfigurationSupportService>
		implements IGDecisionModelConfigurationSupportServiceRepositoryPattern {

	public GDecisionModelConfigurationSupportServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IGDecisionModelConfigurationSupportService> implementations) {
		super(implementations);
	}

	@Override
	public String getCodeValue(IGDecisionModelConfigurationSupportService x) {
		return x.getType().getCode();
	}
}
