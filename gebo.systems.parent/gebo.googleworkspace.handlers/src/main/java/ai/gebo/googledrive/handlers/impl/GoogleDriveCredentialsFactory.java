/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.DriveList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * Factory class responsible for creating Google Drive service instances.
 * This class manages the authentication and creation of Google Drive service
 * objects using service account credentials.
 */
@Service
class GoogleDriveCredentialsFactory {
	/** The JSON factory used for Google API requests */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	
	/** Service for accessing secret credentials */
	@Autowired
	private IGeboSecretsAccessService secretAccessService;
	
	/** Application name used for Google API interactions */
	public final static String APPLICATION_NAME = "gebo.ai";

	/**
	 * Retrieves Google credentials from a secret store using the provided secret code.
	 * 
	 * @param secretCode The identifier for the secret to retrieve
	 * @return GoogleCredentials object with the appropriate scopes for Drive access
	 * @throws GeboCryptSecretException If there's an issue decrypting the secret
	 * @throws IOException If there's an issue reading the credentials
	 * @throws VirtualFilesystemBrowsingException If the secret type is invalid
	 * @throws GeneralSecurityException If there's a security-related issue
	 */
	@SuppressWarnings("deprecation")
	private GoogleCredentials getCredential(String secretCode)
			throws GeboCryptSecretException, IOException, VirtualFilesystemBrowsingException, GeneralSecurityException {

		AbstractGeboSecretContent secret = secretAccessService.getSecretContentById(secretCode);
		GoogleCredentials credential = null;
		if (secret.type() == GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS) {
			GeboGoogleJsonSecretContent content = (GeboGoogleJsonSecretContent) secret;
			credential = ServiceAccountCredentials
					.fromStream(new ByteArrayInputStream(content.getJsonContent().getBytes()))
					.createScoped(List.of(DriveScopes.DRIVE_READONLY, DriveScopes.DRIVE_METADATA_READONLY)).createDelegated(content.getDelegatedUser());

		} else
			throw new VirtualFilesystemBrowsingException("Illegal Gebo secret type, required GOOGLE_CLOUD_JSON_CREDENTIALS");

		credential.refreshIfExpired();

		return credential;
	}

	/**
	 * Creates a Google Drive service instance using credentials from the specified secret.
	 * 
	 * @param secretCode The identifier for the secret containing Google credentials
	 * @return A configured Drive service instance
	 * @throws GeboCryptSecretException If there's an issue decrypting the secret
	 * @throws IOException If there's an issue reading the credentials
	 * @throws VirtualFilesystemBrowsingException If the secret type is invalid
	 * @throws GeneralSecurityException If there's a security-related issue
	 */
	Drive getDriveService(String secretCode)
			throws GeboCryptSecretException, IOException, VirtualFilesystemBrowsingException, GeneralSecurityException {
		GoogleCredentials credential = getCredential(secretCode);
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		HttpCredentialsAdapter adaptedcredential = new HttpCredentialsAdapter(credential);
		Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, adaptedcredential)
				.setApplicationName(APPLICATION_NAME).build();

		return service;
	}

	/**
	 * Test method to demonstrate Google Drive API interaction.
	 * Lists all available shared drives accessible to the service account.
	 * 
	 * @param args Command line arguments (not used)
	 * @throws FileNotFoundException If the credentials file cannot be found
	 * @throws IOException If there's an issue reading the credentials
	 * @throws GeneralSecurityException If there's a security-related issue
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, GeneralSecurityException {
		String json="c:\\users\\paolo\\downloads\\prefab-bedrock-455714-f3-b1b2c2449305.json";
		//String json = "c:\\users\\paolo\\downloads\\persuasive-byte-455709-h0-35bde2deedfd.json";
		GoogleCredentials credential = ServiceAccountCredentials.fromStream(new FileInputStream(json))
				.createScoped(List.of(DriveScopes.DRIVE_READONLY, DriveScopes.DRIVE_METADATA_READONLY));
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		HttpCredentialsAdapter adaptedcredential = new HttpCredentialsAdapter(credential);
		Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, adaptedcredential)
				.setApplicationName(APPLICATION_NAME).build();
		System.out.println(service.getUniverseDomain());
		String additionalParams = "supportsAllDrives=true&supportsTeamDrives=true";

		com.google.api.services.drive.Drive.Drives.List request = service.drives().list();

		DriveList list = request.execute();
		if (list != null && list.getDrives() != null) {
			for (com.google.api.services.drive.model.Drive d : list.getDrives()) {
				System.out.println(d.getName());
			}
		}

	}
}