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
import com.microsoft.graph.serviceclient.GraphServiceClient;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

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
		String tenantId = oauth2secret.getTenantId();
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
}