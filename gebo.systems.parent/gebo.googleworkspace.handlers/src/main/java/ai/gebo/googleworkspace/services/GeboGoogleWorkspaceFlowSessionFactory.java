/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googleworkspace.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.googleworkspaces.model.GeboGoogleWorkspaceAccessSecret;

/**
 * AI generated comments
 * 
 * Factory for creating and managing Google Workspace authorization flow sessions.
 * This singleton component handles the OAuth2 flow for Google Workspace integration,
 * including creating sessions, generating authorization URLs, and handling credentials.
 */
@Component
@Scope("singleton")
public class GeboGoogleWorkspaceFlowSessionFactory {
	@Autowired
	IGGeboConfigService geboConfig;
	/**
	 * Application name.
	 */
	private static final String APPLICATION_NAME = "Gebo.ai";
	/**
	 * Global instance of the JSON factory.
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	/**
	 * Directory to store authorization tokens for this application.
	 */
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = List.of(DriveScopes.DRIVE_METADATA_READONLY, DriveScopes.DRIVE_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	private static final String GOOGLE_WORKSPACE_FOLDER = "GOOGLE_WORKSPACES";

	/**
	 * Default constructor for the factory.
	 */
	public GeboGoogleWorkspaceFlowSessionFactory() {

	}

	/**
	 * Represents a session for the Google Workspace authorization flow.
	 * Stores session state including credentials, authorization URLs, and user information.
	 */
	public static class GeboGoogleWorkspaceFlowSession implements Serializable {
		private final String id;
		private final String uiSuccessForward;
		private final String uiErrorForward;
		private final String geboAiBaseUrl;
		private final String redirectUrl;
		GeboGoogleWorkspaceFlowSessionFactory factory = null;
		GoogleAuthorizationCodeFlow flow = null;
		String userId = null;

		Credential credential = null;
		AuthorizationCodeRequestUrl authorizationCodeRequestUrl = null;
		public static final String SESSION_ID_KEY = "${sessionId}";
		public static final String SESSION_ID_PARAMETER = "gwsid";
		public static final String SECRET_ID = "s___id";

		/**
		 * Decorates a URL with the session ID either by replacing a placeholder or appending as a parameter.
		 * 
		 * @param url The URL to decorate
		 * @return The decorated URL containing the session ID
		 */
		private String decorateUrl(String url) {
			if (url.contains(SESSION_ID_KEY)) {
				return url.replace(SESSION_ID_KEY, id);
			}
			if (!url.contains("?")) {
				return url += "?" + SESSION_ID_PARAMETER + "=" + URLEncoder.encode(id);
			}
			return url = url + "&" + SESSION_ID_PARAMETER + "=" + URLEncoder.encode(id);

		}

		/**
		 * Constructor for creating a new flow session.
		 * 
		 * @param id The session ID
		 * @param uiSuccessForward URL to redirect to on success
		 * @param uiErrorForward URL to redirect to on error
		 * @param geboAiBaseUrl Base URL for the Gebo.ai application
		 */
		GeboGoogleWorkspaceFlowSession(String id, String uiSuccessForward, String uiErrorForward,
				String geboAiBaseUrl) {
			this.id = id;
			this.uiSuccessForward = decorateUrl(uiSuccessForward);
			this.uiErrorForward = decorateUrl(uiErrorForward);
			this.geboAiBaseUrl = geboAiBaseUrl;
			this.redirectUrl = decorateUrl(geboAiBaseUrl + "/oauth2/google-workspace-redirect");
		}

		/**
		 * Checks if the session has valid credentials.
		 * 
		 * @return true if credentials exist, false otherwise
		 */
		public boolean hasCredentials() {
			return credential != null;
		}

		/**
		 * Gets the current credentials as a GeboGoogleWorkspaceAccessSecret.
		 * 
		 * @return The access secret containing credentials, or null if no credentials exist
		 */
		public GeboGoogleWorkspaceAccessSecret getCredentials() {
			if (credential != null) {
				return GeboGoogleWorkspaceAccessSecret.of(credential, id, userId);
			} else
				return null;
		}

		/**
		 * Gets the redirect URL for this session.
		 * 
		 * @return The redirect URL
		 */
		public String getRedirectURL() {
			return redirectUrl;
		}

		/**
		 * Handles the authorization code returned from Google's OAuth service.
		 * 
		 * @param code The authorization code
		 * @return The access secret containing credentials, or null if authorization failed
		 * @throws IOException If there's an error during the authorization process
		 */
		public GeboGoogleWorkspaceAccessSecret handshakeCode(String code) throws IOException {
			credential = factory.finalizeWithCode(code, flow, redirectUrl, userId);
			if (credential == null)
				return null;
			return GeboGoogleWorkspaceAccessSecret.of(credential, id, userId);
		}

		/**
		 * Gets the Google authorization URL for this session.
		 * 
		 * @return The authorization URL
		 */
		public String getGoogleAuthorizzationUrl() {
			return authorizationCodeRequestUrl.build();
		}

		/**
		 * Gets the session ID.
		 * 
		 * @return The session ID
		 */
		public String getId() {
			return id;
		}

		/**
		 * Gets the UI success forward URL.
		 * 
		 * @return The success URL
		 */
		public String getUiSuccessForward() {
			return uiSuccessForward;
		}

		/**
		 * Gets the UI error forward URL.
		 * 
		 * @return The error URL
		 */
		public String getUiErrorForward() {
			return uiErrorForward;
		}

	}

	// Storage for active sessions indexed by session ID
	static Map<String, GeboGoogleWorkspaceFlowSession> states = new HashMap();

	/**
	 * Creates a new Google Workspace flow session.
	 * 
	 * @param userId User identifier
	 * @param uiSuccessForward URL to redirect to on success
	 * @param uiErrorForward URL to redirect to on error
	 * @param geboAiBaseUrl Base URL for the Gebo.ai application
	 * @return The created session
	 * @throws IOException If there's an error during session creation
	 * @throws GeneralSecurityException If there's a security-related error
	 */
	public GeboGoogleWorkspaceFlowSession createSession(String userId, String uiSuccessForward, String uiErrorForward,
			String geboAiBaseUrl) throws IOException, GeneralSecurityException {
		GeboGoogleWorkspaceFlowSession session = new GeboGoogleWorkspaceFlowSession(UUID.randomUUID().toString(),
				uiSuccessForward, uiErrorForward, geboAiBaseUrl);
		session.factory = this;
		session.userId = userId;
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		session.flow = createFlow(HTTP_TRANSPORT, userId);
		session.credential = checkCurrentCredential(session.flow, userId);
		if (session.credential == null) {
			session.authorizationCodeRequestUrl = createAuthorizationCodeRequestUrl(session.flow, session.redirectUrl);
		}
		states.put(session.id, session);
		return session;

	}

	/**
	 * Retrieves a session by its ID.
	 * 
	 * @param id Session identifier
	 * @return The session, or null if not found
	 */
	public GeboGoogleWorkspaceFlowSession get(String id) {
		return states.get(id);
	}

	/**
	 * Checks if there are valid credentials for the user.
	 * 
	 * @param flow The authorization flow
	 * @param userId User identifier
	 * @return The credentials if valid, null otherwise
	 * @throws IOException If there's an error loading credentials
	 */
	private Credential checkCurrentCredential(GoogleAuthorizationCodeFlow flow, String userId) throws IOException {
		Credential credential = flow.loadCredential(userId);
		if (credential != null && (credential.getRefreshToken() != null || credential.getExpiresInSeconds() == null
				|| credential.getExpiresInSeconds() > 60)) {
			return credential;
		} else
			return null;
	}

	/**
	 * Creates an authorization code request URL.
	 * 
	 * @param flow The authorization flow
	 * @param redirectUri Redirect URI for after authorization
	 * @return The authorization request URL
	 */
	private AuthorizationCodeRequestUrl createAuthorizationCodeRequestUrl(GoogleAuthorizationCodeFlow flow,
			String redirectUri) {
		// open in browser
		AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri);
		// receive authorization code and exchange it for an access token

		return authorizationUrl;
	}

	/**
	 * Loads client secrets from the credentials file.
	 * 
	 * @return The loaded Google client secrets
	 * @throws IOException If the credentials file cannot be loaded
	 */
	private GoogleClientSecrets loadClientSecrets() throws IOException {
		InputStream in = this.getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		return clientSecrets;

	}

	/**
	 * Creates a Google authorization code flow.
	 * 
	 * @param HTTP_TRANSPORT The HTTP transport to use
	 * @param user User identifier
	 * @return The created authorization flow
	 * @throws IOException If there's an error creating the flow
	 */
	private GoogleAuthorizationCodeFlow createFlow(final NetHttpTransport HTTP_TRANSPORT, String user)
			throws IOException {
		// Load client secrets.

		GoogleClientSecrets clientSecrets = loadClientSecrets();
		Path path = Path.of(geboConfig.getGeboWorkDirectory(), GOOGLE_WORKSPACE_FOLDER, TOKENS_DIRECTORY_PATH);
		path.toFile().mkdirs();
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(path.toFile()))
				.setAccessType("offline").build();
		return flow;
	}

	/**
	 * Finalizes the authorization process using the received code.
	 * 
	 * @param code Authorization code
	 * @param flow The authorization flow
	 * @param redirectUri Redirect URI used in the request
	 * @param userId User identifier
	 * @return The created credential
	 * @throws IOException If there's an error during authorization
	 */
	Credential finalizeWithCode(String code, GoogleAuthorizationCodeFlow flow, String redirectUri, String userId)
			throws IOException {
		TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
		// store credential and return it
		return flow.createAndStoreCredential(response, userId);
	}

	/**
	 * Gets a new access token using a refresh token.
	 * 
	 * @param refreshToken The refresh token
	 * @param clientId Client ID
	 * @param clientSecret Client secret
	 * @param HTTP_TRANSPORT The HTTP transport to use
	 * @return The new access token
	 * @throws IOException If there's an error refreshing the token
	 */
	String getNewToken(String refreshToken, String clientId, String clientSecret, NetHttpTransport HTTP_TRANSPORT)
			throws IOException {

		TokenResponse tokenResponse = new GoogleRefreshTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, refreshToken,
				clientId, clientSecret).setScopes(SCOPES).setGrantType("refresh_token").execute();

		return tokenResponse.getAccessToken();
	}

	/**
	 * Creates Google credentials from a Gebo access secret.
	 * 
	 * @param googleWorkspaceSecret The access secret containing token information
	 * @return The created credentials
	 * @throws IOException If there's an error creating credentials
	 * @throws GeneralSecurityException If there's a security-related error
	 */
	Credential createGoogleCredentials(GeboGoogleWorkspaceAccessSecret googleWorkspaceSecret)
			throws IOException, GeneralSecurityException {
		GoogleClientSecrets clientSecrets = loadClientSecrets();
		String clientId = clientSecrets.getDetails().getClientId();
		String clientSecret = clientSecrets.getDetails().getClientSecret();
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY).setClientSecrets(clientId, clientSecret).build();
		credential.setAccessToken(googleWorkspaceSecret.getAccessToken());
		credential.setRefreshToken(googleWorkspaceSecret.getRefreshToken());
		credential.setExpirationTimeMilliseconds(googleWorkspaceSecret.getExpirationTime());
		String refreshToken = googleWorkspaceSecret.getRefreshToken();
		if (googleWorkspaceSecret.getExpirationTime() == null
				|| ((System.currentTimeMillis() - 60000) >= googleWorkspaceSecret.getExpirationTime().longValue())) {
			credential.setAccessToken(getNewToken(refreshToken, clientId, clientSecret, HTTP_TRANSPORT));
		} else {
			credential.setAccessToken(googleWorkspaceSecret.getAccessToken());
		}

		credential.setRefreshToken(refreshToken);
		return credential;
	}
}