/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.impl;

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
import ai.gebo.serpapisearch.handler.model.GSerpapiSearchApiCredentials;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchConfig;
import ai.gebo.serpapisearch.handler.repository.GSerpapiSearchApiCredentialsRepository;

@Service
public class SerpapiSearchConfigDaoImpl implements IGRuntimeConfigurationDao<SerpapiSearchConfig> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerpapiSearchConfigDaoImpl.class);

	@Autowired
	GSerpapiSearchApiCredentialsRepository repository;
	@Autowired
	IGeboSecretsAccessService secretService;

	@Override
	public List<SerpapiSearchConfig> getConfigurations() {
		List<GSerpapiSearchApiCredentials> list = repository.findAll();
		List<SerpapiSearchConfig> configs = new ArrayList<SerpapiSearchConfig>();
		try {
			for (GSerpapiSearchApiCredentials x : list) {
				AbstractGeboSecretContent data = secretService.getSecretContentById(x.getSecretCode());
				SerpapiSearchConfig config = new SerpapiSearchConfig();
				config.setApiKey(((GeboTokenContent) data).getToken());
				config.setEnabled(true);
				configs.add(config);
			}
		} catch (Throwable th) {
			LOGGER.error("Error in reading serpapi search config", th);
		}
		return configs;
	}

	@Override
	public SerpapiSearchConfig findByCode(String code) {
		return null;
	}
}
