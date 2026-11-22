/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * What a chosen chat model accepts as input as Gebo knows it: the metadata stored with
 * the choice, and when they know nothing, the models library - the model's own entry,
 * else a rule covering it. A configuration made before the input modalities were
 * recorded has a choice whose metadata know nothing of them, while the library may;
 * without this, such a configuration could only be set by hand.
 */
@Service
public class GChatModelInputModalitiesResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(GChatModelInputModalitiesResolver.class);
	private final ObjectProvider<IGModelsLibraryDao> library;
	private final ObjectProvider<IGChatModelConfigurationSupportServiceRepositoryPattern> chatModelTypes;

	public GChatModelInputModalitiesResolver(ObjectProvider<IGModelsLibraryDao> library,
			ObjectProvider<IGChatModelConfigurationSupportServiceRepositoryPattern> chatModelTypes) {
		this.library = library;
		this.chatModelTypes = chatModelTypes;
	}

	/**
	 * @param modelTypeCode the chat model type of the configuration, which tells the
	 *                      provider the library knows the model by
	 * @param choice        the chosen model
	 * @return the input modalities, or null when neither the metadata nor the library
	 *         know them, which means text only
	 */
	public List<GModelInputModality> of(String modelTypeCode, GBaseModelChoice choice) {
		final List<GModelInputModality> known = IGChatModelConfigurationSupportService.inputModalitiesOf(choice);
		if (known != null || choice == null || choice.getCode() == null || modelTypeCode == null) {
			return known;
		}
		try {
			final IGModelsLibraryDao dao = library.getIfAvailable();
			final IGChatModelConfigurationSupportServiceRepositoryPattern types = chatModelTypes.getIfAvailable();
			final IGChatModelConfigurationSupportService type = types != null ? types.findByCode(modelTypeCode) : null;
			final String providerId = type != null && type.getType() != null ? type.getType().getProviderId() : null;
			if (dao == null || providerId == null) {
				return null;
			}
			final ModelMetaInfo entry = dao.findByProviderIdAndModelId(providerId, choice.getCode());
			if (entry != null && entry.knownInputModalities() != null) {
				return entry.knownInputModalities();
			}
			final List<GModelInputModality> ruled = dao.findInputModalities(providerId, choice.getCode());
			// an empty answer tells nothing either
			return ruled != null && !ruled.isEmpty() ? ruled : null;
		} catch (RuntimeException unreadable) {
			LOGGER.warn("What model " + choice.getCode() + " accepts as input could not be read from the library: "
					+ unreadable);
			return null;
		}
	}
}
