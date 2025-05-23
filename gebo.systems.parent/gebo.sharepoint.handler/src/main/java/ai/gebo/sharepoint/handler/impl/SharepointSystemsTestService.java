/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGOauth2AccessTokenService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemBrowsingService;

/**
 * AI generated comments
 * Service class responsible for testing SharePoint content management system connectivity 
 * and configuration validity.
 */
@Service
public class SharepointSystemsTestService {
	/** Service for accessing Gebo secrets */
	@Autowired
	IGeboSecretsAccessService secretsService;
	
	/** Service for REST template operations */
	@Autowired
	RestTemplateWrapperService templateWrapperService;
	
	/** Service for OAuth2 access token management */
	@Autowired
	IGOauth2AccessTokenService oauthAccessService;
	
	/** Service for browsing Microsoft Graph virtual filesystem */
	@Autowired
	IGMicrosoftGraphVirtualFilesystemBrowsingService browsing;

	/**
	 * Tests the connectivity and configuration of a SharePoint content management system.
	 * Validates the secret code and base URL, then attempts to retrieve roots
	 * from the filesystem browsing service if credentials are valid.
	 * 
	 * @param object The SharePoint system configuration to test
	 * @return Operation status containing test results and any error messages
	 */
	public OperationStatus<GSharepointContentManagementSystem> testSharepointSystem(
			GSharepointContentManagementSystem object) {
		OperationStatus<GSharepointContentManagementSystem> outValue = null;
		outValue = new OperationStatus<GSharepointContentManagementSystem>();
		outValue.setResult(object);
		String secretCode = object.getSecretCode();
		String baseUrl = object.getBaseUri();

		// Validate configuration parameters
		boolean secretCodeValid = (secretCode != null && secretCode.trim().length() > 0);
		boolean baseUrlExists = (baseUrl != null && baseUrl.trim().length() > 0);
		boolean validUrl = baseUrlExists;
		try {
			new URL(baseUrl);
			validUrl = true;
		} catch (MalformedURLException e) {
			validUrl = false;
		}
		if (secretCodeValid) {
			// If secret code is valid, attempt to get filesystem roots
			OperationStatus<GSharepointContentManagementSystem> status = OperationStatus.of(object);
			OperationStatus<List<GVirtualFilesystemRoot>> _status = browsing.getRoots(object);
			status.getMessages().clear();
			status.setMessages(_status.getMessages());
			outValue = status;

		} else {
			// Add appropriate error messages for invalid configuration
			if (!secretCodeValid) {
				outValue.getMessages().add(
						GUserMessage.errorMessage("No credentials inserted", "The Sharepoint credentials are missing"));
			}
			// Following only for non CLOUD version
			/*
			 * if (!baseUrlExists) {
			 * outValue.getMessages().add(GUserMessage.errorMessage("No Base url inserted",
			 * "The Sharepoint base internet address is not inserted")); } if (!validUrl) {
			 * outValue.getMessages().add(GUserMessage.
			 * errorMessage("Invalid  Base url inserted",
			 * "The Sharepoint base internet address has an invalid format")); }
			 */
		}
		return outValue;
	}

}