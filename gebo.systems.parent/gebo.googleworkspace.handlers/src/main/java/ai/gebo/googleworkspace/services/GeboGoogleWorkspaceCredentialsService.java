/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googleworkspace.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.impl.GGoogleDriveSystemContentHandlerImpl;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveSystemRepository;
import ai.gebo.googleworkspaces.model.GeboGoogleWorkspaceAccessSecret;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * Service for managing Google Workspace credentials.
 * AI generated comments
 * This service provides methods to store, retrieve, and update Google Workspace access credentials,
 * and ensures that corresponding Google Drive systems exist.
 */
@Service
public class GeboGoogleWorkspaceCredentialsService {
	public static final String GOOGLE_WORKSPACE_CONTEXT = "GOOGLE_WORKSPACE_CONTEXT";
	public static final String GOOGLE_WORKSPACE_SECRETS_PREFIX = "gworkspace-";
	private static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	IGeboSecretsAccessService secretAccess;
	@Autowired
	GoogleDriveSystemRepository driveSystemsRepository;
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	GeboGoogleWorkspaceFlowSessionFactory factory;

	/**
	 * Default constructor for the service.
	 */
	public GeboGoogleWorkspaceCredentialsService() {

	}

	/**
	 * Ensures that a Google Drive system exists for the given access secret.
	 * If no system exists, creates a new one.
	 *
	 * @param driveAccessSecret The secret ID for accessing Google Drive
	 * @param uid User identifier
	 * @return The system code of the existing or newly created Google Drive system
	 * @throws GeboPersistenceException If there's an issue with the persistence layer
	 */
	private String ensureDriveSystemExists(String driveAccessSecret, String uid) throws GeboPersistenceException {
		List<GGoogleDriveSystem> systems = driveSystemsRepository.findByDriveAccessSecret(driveAccessSecret);
		if (systems.isEmpty()) {
			GGoogleDriveSystem system = new GGoogleDriveSystem();
			system.setContentManagementSystemType(GGoogleDriveSystemContentHandlerImpl.GOOGLE_DRIVE_HANDLER);
			system.setDescription("Google drive system " + uid);
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setDriveAccessSecret(driveAccessSecret);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			return persistentObjectManager.insert(system).getCode();
		} else
			return systems.get(0).getCode();

	}

	/**
	 * Inserts a new Google Workspace access secret.
	 * 
	 * @param credentials The Google Workspace credentials to store
	 * @return Information about the stored secret
	 * @throws GeboContentHandlerSystemException If there's an issue with content handling
	 * @throws GeboCryptSecretException If there's an issue with encryption of the secret
	 * @throws GeboPersistenceException If there's an issue with persistence
	 */
	public SecretInfo insert(GeboGoogleWorkspaceAccessSecret credentials)
			throws GeboContentHandlerSystemException, GeboCryptSecretException, GeboPersistenceException {
		GeboCustomSecretContent secretContent = new GeboCustomSecretContent();
		secretContent.setContentType("application/json");
		if (findByUid(credentials.getUid()) != null)
			throw new GeboContentHandlerSystemException("Already existing google workspace account");
		try {
			String id = getSecretId(credentials.getUid());
			secretContent.setContent(mapper.writeValueAsString(credentials));
			secretContent.setCustomContentDescription("Google workspace access secret");
			secretAccess.storeSecret(secretContent, "Google workspace access secret", GOOGLE_WORKSPACE_CONTEXT, id);
			// here we ensure that this google drive system exists
			ensureDriveSystemExists(id, credentials.getUid());
			return secretAccess.getSecretInfoById(id);
		} catch (JsonProcessingException | GeboCryptSecretException e) {
			throw new GeboContentHandlerSystemException("Exception saving google workspace access secret", e);
		}

	}

	/**
	 * Updates an existing Google Workspace access secret.
	 * 
	 * @param credentials The updated Google Workspace credentials
	 * @throws GeboContentHandlerSystemException If there's an issue with content handling
	 * @throws GeboCryptSecretException If there's an issue with encryption of the secret
	 * @throws GeboPersistenceException If there's an issue with persistence
	 */
	public void update(GeboGoogleWorkspaceAccessSecret credentials)
			throws GeboContentHandlerSystemException, GeboCryptSecretException, GeboPersistenceException {
		try {
			String id = getSecretId(credentials.getUid());
			GeboCustomSecretContent secretContent = new GeboCustomSecretContent();
			secretContent.setContentType("application/json");
			secretContent.setContent(mapper.writeValueAsString(credentials));
			secretContent.setCustomContentDescription("Google workspace access secret");
			secretAccess.updateSecret(secretContent, "Gootle Workspace account " + credentials.getUid(),
					GOOGLE_WORKSPACE_CONTEXT, id);
			ensureDriveSystemExists(id, credentials.getUid());
		} catch (JsonProcessingException | GeboCryptSecretException e) {
			throw new GeboContentHandlerSystemException("Exception saving google workspace access secret", e);
		}
	}

	/**
	 * Finds a Google Workspace secret by user ID.
	 * 
	 * @param uid The user ID to search for
	 * @return Information about the found secret, or null if not found
	 * @throws GeboCryptSecretException If there's an issue with decryption of the secret
	 */
	public SecretInfo findByUid(String uid) throws GeboCryptSecretException {
		return secretAccess.getSecretInfoById(getSecretId(uid));
	}

	/**
	 * Generates a secret ID based on the user ID.
	 * 
	 * @param uid The user ID
	 * @return The generated secret ID
	 */
	private String getSecretId(String uid) {
		return GOOGLE_WORKSPACE_SECRETS_PREFIX + uid.toLowerCase();
	}

	/**
	 * Loads Google Workspace credentials by their ID and creates a Google Credential object.
	 * Updates the access token in the stored credentials if it has changed.
	 * 
	 * @param id The ID of the credentials to load
	 * @return A Google API Credential object
	 * @throws GeboCryptSecretException If there's an issue with decryption of the secret
	 * @throws GeboContentHandlerSystemException If there's an issue with content handling
	 * @throws IOException If there's an I/O error
	 * @throws GeneralSecurityException If there's a security-related error
	 * @throws GeboPersistenceException If there's an issue with persistence
	 */
	public Credential loadGoogleWorkspaceCredentialsById(String id)
			throws GeboCryptSecretException, GeboContentHandlerSystemException, IOException, GeneralSecurityException, GeboPersistenceException {
		AbstractGeboSecretContent secret = secretAccess.getSecretContentById(id);
		if (secret instanceof GeboCustomSecretContent) {
			GeboCustomSecretContent sc = (GeboCustomSecretContent) secret;
			String json = sc.getContent();
			try {
				GeboGoogleWorkspaceAccessSecret googleWorkspaceSecret = mapper.readValue(json,
						GeboGoogleWorkspaceAccessSecret.class);
				Credential credential = factory.createGoogleCredentials(googleWorkspaceSecret);
				if (credential.getAccessToken() != null && googleWorkspaceSecret.getAccessToken() != null
						&& !googleWorkspaceSecret.getAccessToken().equals(credential.getAccessToken())) {
					googleWorkspaceSecret.setAccessToken(credential.getAccessToken());
					update(googleWorkspaceSecret);
				}
				return credential;
			} catch (JsonProcessingException e) {
				throw new GeboContentHandlerSystemException("Wrong json unwrap", e);
			}

		} else
			throw new IllegalStateException(
					"Wrong secret type " + secret != null ? secret.getClass().getName() : "null");

	}

}