/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.tavilysearch.handler.model.GTavilySearchApiCredentials;
import ai.gebo.tavilysearch.handler.model.TavilySearchConfig;
import ai.gebo.tavilysearch.handler.repository.GTavilySearchApiCredentialsRepository;

@Service
public class TavilySearchConfigDaoImpl implements IGRuntimeConfigurationDao<TavilySearchConfig> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TavilySearchConfigDaoImpl.class);

	@Autowired
	GTavilySearchApiCredentialsRepository repository;
	@Autowired
	IGeboSecretsAccessService secretService;

	@Override
	public List<TavilySearchConfig> getConfigurations() {
		List<GTavilySearchApiCredentials> list = repository.findAll();
		List<TavilySearchConfig> configs = new ArrayList<TavilySearchConfig>();
		try {
			for (GTavilySearchApiCredentials x : list) {
				AbstractGeboSecretContent data = secretService.getSecretContentById(x.getSecretCode());
				TavilySearchConfig config = new TavilySearchConfig();
				config.setApiKey(((GeboTokenContent) data).getToken());
				config.setEnabled(true);
				configs.add(config);
			}
		} catch (Throwable th) {
			LOGGER.error("Error in reading tavily search config", th);
		}
		return configs;
	}

	@Override
	public TavilySearchConfig findByCode(String code) {
		return null;
	}
}
