/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.searxngsearch.handler.model.GSearxngSearchApiCredentials;
import ai.gebo.searxngsearch.handler.model.SearxngSearchConfig;
import ai.gebo.searxngsearch.handler.repository.GSearxngSearchApiCredentialsRepository;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

@Service
public class SearxngSearchConfigDaoImpl implements IGRuntimeConfigurationDao<SearxngSearchConfig> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearxngSearchConfigDaoImpl.class);

	@Autowired
	GSearxngSearchApiCredentialsRepository repository;
	@Autowired
	IGeboSecretsAccessService secretService;

	@Override
	public List<SearxngSearchConfig> getConfigurations() {
		List<GSearxngSearchApiCredentials> list = repository.findAll();
		List<SearxngSearchConfig> configs = new ArrayList<SearxngSearchConfig>();
		try {
			for (GSearxngSearchApiCredentials x : list) {
				SearxngSearchConfig config = new SearxngSearchConfig();
				config.setBaseUrl(x.getBaseUrl());
				if (StringUtils.hasText(x.getSecretCode())) {
					AbstractGeboSecretContent data = secretService.getSecretContentById(x.getSecretCode());
					config.setApiKey(((GeboTokenContent) data).getToken());
				}
				config.setEnabled(true);
				configs.add(config);
			}
		} catch (Throwable th) {
			LOGGER.error("Error in reading searxng search config", th);
		}
		return configs;
	}

	@Override
	public SearxngSearchConfig findByCode(String code) {
		return null;
	}
}
