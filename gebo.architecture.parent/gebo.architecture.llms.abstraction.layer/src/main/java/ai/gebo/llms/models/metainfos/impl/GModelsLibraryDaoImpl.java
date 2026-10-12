/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;

/**
 * The models library: the installation's entries ({@code ai.gebo.models.library}) first,
 * then the pieces the provider modules declare ({@link GProviderModelsLibrary} beans),
 * each found by provider and model id, or by model id alone for the models information.
 */
@Service
@Scope("singleton")
public class GModelsLibraryDaoImpl implements IGModelsLibraryDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(GModelsLibraryDaoImpl.class);
	/** The installation's library, searched first. */
	private final ModelsLibraryConfig config;
	/** The libraries of the provider modules on the classpath. */
	private final List<GProviderModelsLibrary> providers;

	@Autowired
	public GModelsLibraryDaoImpl(ModelsLibraryConfig config, ObjectProvider<GProviderModelsLibrary> providers) {
		this(config, providers.orderedStream().toList());
	}

	public GModelsLibraryDaoImpl(ModelsLibraryConfig config, List<GProviderModelsLibrary> providers) {
		this.config = config;
		this.providers = providers != null ? List.copyOf(providers) : List.of();
		LOGGER.info("Models library: " + this.providers.size() + " provider librar(y/ies), "
				+ this.providers.stream().mapToInt(p -> sizeOf(p.getModelsInfo())).sum() + " model(s), "
				+ this.providers.stream().mapToInt(p -> sizeOf(p.getParameterRules())).sum() + " parameter rule(s)");
		if (LOGGER.isDebugEnabled()) {
			this.providers.forEach(p -> LOGGER.debug("Provider models library " + p.getClass().getName() + ": "
					+ sizeOf(p.getModelsInfo()) + " model(s), " + sizeOf(p.getParameterRules()) + " rule(s)"));
		}
	}

	private static int sizeOf(List<?> list) {
		return list != null ? list.size() : 0;
	}

	/** The installation's entries, then every provider library's, in order. */
	private <T> List<T> all(Function<ModelsLibraryConfig, List<T>> installation,
			Function<GProviderModelsLibrary, List<T>> provider) {
		final List<T> all = new ArrayList<>();
		if (config != null && installation.apply(config) != null)
			all.addAll(installation.apply(config));
		for (GProviderModelsLibrary library : providers) {
			if (provider.apply(library) != null)
				all.addAll(provider.apply(library));
		}
		return all;
	}

	private ModelMetaInfo firstModel(Predicate<ModelMetaInfo> matching) {
		return all(ModelsLibraryConfig::getModelsInfo, GProviderModelsLibrary::getModelsInfo).stream()
				.filter(Objects::nonNull).filter(matching).findFirst().orElse(null);
	}

	@Override
	public ModelMetaInfo findByModelId(String modelId) {
		return firstModel(x -> x.getModelId() != null && x.getModelId().equals(modelId));
	}

	@Override
	public ModelMetaInfo findByProviderIdAndModelId(String providerId, String modelId) {
		return firstModel(x -> x.getProviderId() != null && x.getProviderId().equals(providerId)
				&& x.getModelId() != null && x.getModelId().equals(modelId));
	}

	@Override
	public ModelParameterRules findParameterRules(String providerId, String modelId) {
		final ModelParameterRules found = all(ModelsLibraryConfig::getParameterRules,
				GProviderModelsLibrary::getParameterRules).stream().filter(Objects::nonNull)
				.filter(rule -> rule.covers(providerId, modelId)).findFirst().orElse(null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findParameterRules(" + providerId + ", " + modelId + ") => " + found);
		}
		return found;
	}
}
