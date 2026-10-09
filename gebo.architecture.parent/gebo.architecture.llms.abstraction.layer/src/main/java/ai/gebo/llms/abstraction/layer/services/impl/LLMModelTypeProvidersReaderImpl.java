/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.core.messages.ILLMModelTypeProvidersReader;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;
import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import lombok.RequiredArgsConstructor;

/**
 * The providers of the model types registered by the LLM modules of this process.
 */
@Service
@RequiredArgsConstructor
public class LLMModelTypeProvidersReaderImpl implements ILLMModelTypeProvidersReader {
	private final ObjectProvider<IGImplementationsRepositoryPattern<? extends IGModelConfigurationSupportService<?, ?, ?, ?>>> modelTypes;

	@Override
	public Map<String, String> providersOfModelTypes() {
		return modelTypes.orderedStream().flatMap(repository -> repository.getImplementations().stream())
				.map(IGModelConfigurationSupportService::getType).filter(Objects::nonNull).map(GModelType.class::cast)
				.filter(type -> type.getCode() != null && type.getProviderId() != null)
				.collect(Collectors.toMap(GModelType::getCode, GModelType::getProviderId, (a, b) -> a));
	}
}
