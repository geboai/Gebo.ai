/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.security.config.Oauth2ProvidersLibrary;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.services.IGOauth2ProvidersLibraryDao;

/**
 * Service implementation of {@link IGOauth2ProvidersLibraryDao}. This class
 * provides methods to interact with OAuth2 provider configurations.
 * 
 * AI generated comments
 */
@Service
public class GOauth2ProvidersLibraryDaoimpl extends GAbstractRuntimeConfigurationDao<Oauth2ProviderConfig>
		implements IGOauth2ProvidersLibraryDao {

	// Library containing OAuth2 provider configurations
	private final Oauth2ProvidersLibrary library;

	/**
	 * Constructs a new instance using the provided library.
	 * 
	 * @param library the OAuth2 providers library
	 */
	public GOauth2ProvidersLibraryDaoimpl(Oauth2ProvidersLibrary library) {
		super(library.getProviders(), null);
		this.library = library;

		if (library.getProviders() != null && !library.getProviders().isEmpty()) {
			StringBuffer logText = new StringBuffer();
			for (int i = 0; i < library.getProviders().size(); i++) {
				Oauth2ProviderConfig provider = library.getProviders().get(i);
				if (provider.getProvider() != null) {
					logText.append(provider.getProvider().name());
					if (i < library.getProviders().size() - 1) {
						logText.append(",");
					}
				}
			}
			LOGGER.info("Oauth2 static providers library: " + logText.toString());
		} else {
			LOGGER.error("No providers in oauth2 providers library!!!");
		}
	}

	/**
	 * Finds the OAuth2 provider configuration by its code.
	 * 
	 * @param code the code of the OAuth2 provider
	 * @return the configuration of the OAuth2 provider
	 */
	@Override
	public Oauth2ProviderConfig findByCode(String code) {
		// Uses the provider's code to find the corresponding configuration
		return findByPredicate(x -> {
			return x.getProvider() != null && x.getProvider().name().equals(code);
		});
	}

}