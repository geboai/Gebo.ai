/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.security.model.AuthProvider;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments Represents an OAuth2 Runtime Configuration.
 */
@Document
@Data
public class Oauth2RuntimeConfiguration {
	@Id
	@NotNull
	/**
	 * The unique identifier of the configuration.
	 */
	private String registrationId = null;

	/**
	 * The description of the configuration.
	 */
	@NotNull
	private String description = null;

	/**
	 * The authentication provider.
	 */
	@NotNull
	@HashIndexed
	private AuthProvider provider = null;

	/**
	 * The provider configuration. Used only if provider is OAUTH2_GENERIC.
	 */
	private Oauth2ProviderConfig providerConfig = null;

	/**
	 * The client secret ID.
	 */
	@NotNull
	private String clientSecretId = null;

	/**
	 * The list of configuration types.
	 */
	@NotNull
	@NotEmpty
	@HashIndexed
	private List<Oauth2ConfigurationType> configurationTypes = null;

	/**
	 * The OAuth2 client authentication method.
	 */
	private Oauth2ClientAuthMethod clientAuthMethod = null;

	/**
	 * The OAuth2 authorization grant type.
	 */
	private Oauth2AuthorizationGrantType authGrantType = null;

	private Boolean readOnly = null;

}