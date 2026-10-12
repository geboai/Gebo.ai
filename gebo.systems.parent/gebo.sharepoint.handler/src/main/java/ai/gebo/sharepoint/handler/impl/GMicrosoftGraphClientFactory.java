/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.implementation.IdentityClientOptions;
import com.microsoft.graph.core.authentication.AzureIdentityAuthenticationProvider;
import com.microsoft.graph.core.requests.GraphClientFactory;
import com.microsoft.graph.core.requests.options.GraphClientOption;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.kiota.RequestOption;
import com.microsoft.kiota.http.middleware.options.RetryHandlerOption;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import okhttp3.OkHttpClient;

/**
 * Factory class responsible for creating Microsoft Graph clients for SharePoint access.
 * This service creates authenticated clients using OAuth2 credentials stored in the Gebo system.
 * AI generated comments
 */
@Service
class GMicrosoftGraphClientFactory {
	/** Microsoft Graph default scope for authentication */
	static final String MS_GRAPH_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";
	/** Microsoft Graph scope for reading sites */
	static final String SITES_READALL_SCOPE = "https://graph.microsoft.com/Sites.Read.All";
	/** Microsoft Graph scope for reading files */
	static final String FILES_READALL_SCOPE = "https://graph.microsoft.com/Files.Read.All";
	
	/** Service for accessing Gebo secrets */
	@Autowired
	IGeboSecretsAccessService secretAccessService;
	
	/**
	 * Creates and returns a Microsoft Graph Service Client authenticated with the credentials
	 * associated with the provided SharePoint system.
	 * 
	 * @param system The SharePoint content management system containing secret code for authentication
	 * @return A configured and authenticated GraphServiceClient
	 * @throws VirtualFilesystemBrowsingException If the SharePoint system lacks valid credentials
	 * @throws GeboCryptSecretException If there's an issue decrypting the secret
	 */
	GraphServiceClient getServiceClient(GSharepointContentManagementSystem system)
			throws VirtualFilesystemBrowsingException, GeboCryptSecretException {
		String secretCode = system.getSecretCode();
		if (secretCode == null)
			throw new VirtualFilesystemBrowsingException("Sharepoint system without credentials");
		AbstractGeboSecretContent secretContent = secretAccessService.getSecretContentById(secretCode);
		if (secretContent == null || secretContent.type() != GeboSecretType.OAUTH2_STANDARD) {
			throw new VirtualFilesystemBrowsingException(
					"Sharepoint system with credentials " + secretCode + " invalid");
		}
		GeboOauth2SecretContent oauth2secret = (GeboOauth2SecretContent) secretContent;
		String clientId = oauth2secret.getClientId();
		String tenantId = oauth2secret.getCustomAttributes().get("tenantId");
		String secret = oauth2secret.getSecret();
		var scopes = new String[] { MS_GRAPH_DEFAULT_SCOPE };
		IdentityClientOptions options = new IdentityClientOptions();

		// https://learn.microsoft.com/dotnet/api/azure.identity.clientsecretcredential
		ClientSecretCredentialBuilder clientSecretCredentialBuilder = new ClientSecretCredentialBuilder();
		clientSecretCredentialBuilder.clientId(clientId);
		clientSecretCredentialBuilder.tenantId(tenantId);
		clientSecretCredentialBuilder.clientSecret(secret);
		ClientSecretCredential clientSecretCredential = clientSecretCredentialBuilder.build();
		GraphServiceClient graphClient = new GraphServiceClient(clientSecretCredential, scopes);
		return graphClient;
	}

	/**
	 * A client of the SharePoint system for a search: its HTTP calls time out, and are
	 * retried, as the search call parameters say (the clients of the content
	 * integration, given by {@link #getServiceClient(GSharepointContentManagementSystem)},
	 * keep the SDK's own settings).
	 */
	GraphServiceClient getServiceClient(GSharepointContentManagementSystem system, SearchCallParameters searchParameters)
			throws VirtualFilesystemBrowsingException, GeboCryptSecretException {
		if (searchParameters == null) {
			return getServiceClient(system);
		}
		final AzureIdentityAuthenticationProvider authentication = new AzureIdentityAuthenticationProvider(
				clientSecretCredential(system), new String[0], MS_GRAPH_DEFAULT_SCOPE);
		final RetryHandlerOption retries = new RetryHandlerOption(RetryHandlerOption.DEFAULT_SHOULD_RETRY,
				Math.min(Math.max(0, searchParameters.retries()), RetryHandlerOption.MAX_RETRIES),
				RetryHandlerOption.DEFAULT_DELAY);
		final OkHttpClient httpClient = GraphClientFactory
				.create(new RequestOption[] { new GraphClientOption(), retries })
				.connectTimeout(searchParameters.connectTimeout()).readTimeout(searchParameters.readTimeout())
				.callTimeout(searchParameters.connectTimeout().plus(searchParameters.readTimeout())).build();
		return new GraphServiceClient(authentication, httpClient);
	}

	/** The credential of the system, as {@link #getServiceClient(GSharepointContentManagementSystem)} builds it. */
	private ClientSecretCredential clientSecretCredential(GSharepointContentManagementSystem system)
			throws VirtualFilesystemBrowsingException, GeboCryptSecretException {
		String secretCode = system.getSecretCode();
		if (secretCode == null)
			throw new VirtualFilesystemBrowsingException("Sharepoint system without credentials");
		AbstractGeboSecretContent secretContent = secretAccessService.getSecretContentById(secretCode);
		if (secretContent == null || secretContent.type() != GeboSecretType.OAUTH2_STANDARD) {
			throw new VirtualFilesystemBrowsingException(
					"Sharepoint system with credentials " + secretCode + " invalid");
		}
		GeboOauth2SecretContent oauth2secret = (GeboOauth2SecretContent) secretContent;
		ClientSecretCredentialBuilder clientSecretCredentialBuilder = new ClientSecretCredentialBuilder();
		clientSecretCredentialBuilder.clientId(oauth2secret.getClientId());
		clientSecretCredentialBuilder.tenantId(oauth2secret.getCustomAttributes().get("tenantId"));
		clientSecretCredentialBuilder.clientSecret(oauth2secret.getSecret());
		return clientSecretCredentialBuilder.build();
	}
}
