/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googleworkspace.controllers;

/**
 * AI generated comments
 * 
 * Controller that handles the OAuth2 authentication handshake between Gebo and Google Workspace.
 * This controller manages the authorization flow, credential storage, and redirection processes
 * required for Google Workspace integration.
 */
import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.googleworkspace.services.GeboGoogleWorkspaceCredentialsService;
import ai.gebo.googleworkspace.services.GeboGoogleWorkspaceFlowSessionFactory;
import ai.gebo.googleworkspace.services.GeboGoogleWorkspaceFlowSessionFactory.GeboGoogleWorkspaceFlowSession;
import ai.gebo.googleworkspaces.model.GeboGoogleWorkspaceAccessSecret;
import ai.gebo.secrets.model.SecretInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

@RestController
public class GoogleWorkspaceAccessHandshakeController {
	/**
	 * Factory for creating and managing Google Workspace authentication flow sessions
	 */
	@Autowired
	GeboGoogleWorkspaceFlowSessionFactory sessionFactory;

	/**
	 * Service to manage Google Workspace credentials
	 */
	@Autowired
	GeboGoogleWorkspaceCredentialsService cService;

	/**
	 * Default constructor
	 */
	public GoogleWorkspaceAccessHandshakeController() {

	}

	/**
	 * Handles the OAuth2 redirect from Google Workspace authentication.
	 * Processes the authorization code and completes the handshake process.
	 * 
	 * @param request The HTTP request containing code and session parameters
	 * @param response The HTTP response for redirection
	 * @throws IOException If an I/O error occurs during redirection
	 * @throws GeboContentHandlerSystemException If content handling fails
	 * @throws GeboCryptSecretException If cryptographic operations fail
	 * @throws GeboPersistenceException If persistence operations fail
	 */
	@GetMapping("/oauth2/google-workspace-redirect")
	public void googleWorkspaceRedirect(HttpServletRequest request, HttpServletResponse response)
			throws IOException, GeboContentHandlerSystemException, GeboCryptSecretException, GeboPersistenceException {
		String code = request.getParameter("code");
		// TODO: CHECK HOW TO DEAL WITH CHANGING UID IN GOOGLE ACCESS PAGE
		String gwsid = request.getParameter(GeboGoogleWorkspaceFlowSession.SESSION_ID_PARAMETER);
		GeboGoogleWorkspaceFlowSession session = sessionFactory.get(gwsid);
		GeboGoogleWorkspaceAccessSecret credentials = code != null ? session.handshakeCode(code) : null;
		SecretInfo secret = null;
		if (credentials != null) {
			secret = cService.findByUid(credentials.getUid());
			if (secret == null)
				secret = cService.insert(credentials);
			else {
				cService.update(credentials);
			}
		}
		if (credentials != null) {
			String okUrl = session.getUiSuccessForward();
			if (okUrl.contains("?")) {
				okUrl = okUrl + "&" + GeboGoogleWorkspaceFlowSession.SECRET_ID + "="
						+ URLEncoder.encode(secret.getCode());
			} else {
				okUrl = okUrl + "?" + GeboGoogleWorkspaceFlowSession.SECRET_ID + "="
						+ URLEncoder.encode(secret.getCode());
			}
			response.sendRedirect(okUrl);
		} else {
			response.sendRedirect(session.getUiErrorForward());
		}

	}

	/**
	 * Request class for starting Google Workspace authentication flow
	 */
	public static class StartGooglWorkspaceAccessRequest {
		@NotNull
		private String user = null;
		@NotNull
		private String uiSuccessForward = null;
		@NotNull
		private String uiErrorForward = null;

		/**
		 * @return The user identifier
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @param user The user identifier to set
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * @return The URL to redirect to on successful authentication
		 */
		public String getUiSuccessForward() {
			return uiSuccessForward;
		}

		/**
		 * @param uiSuccessForward The success redirect URL to set
		 */
		public void setUiSuccessForward(String uiSuccessForward) {
			this.uiSuccessForward = uiSuccessForward;
		}

		/**
		 * @return The URL to redirect to on authentication failure
		 */
		public String getUiErrorForward() {
			return uiErrorForward;
		}

		/**
		 * @param uiErrorForward The error redirect URL to set
		 */
		public void setUiErrorForward(String uiErrorForward) {
			this.uiErrorForward = uiErrorForward;
		}
	}

	/**
	 * Response class for the Google Workspace authentication flow initiation
	 */
	public static class StartGooglWorkspaceAccessRespose {
		private Boolean notExecuted = false;
		private Boolean alreadyOwningCredentials = false;
		private String forwardUrl = null;
		private String jumpTo = null;
		private String authenticationSessionId = null;

		/**
		 * @return The URL to forward the user to for authentication
		 */
		public String getForwardUrl() {
			return forwardUrl;
		}

		/**
		 * @param forwardUrl The forward URL to set
		 */
		public void setForwardUrl(String forwardUrl) {
			this.forwardUrl = forwardUrl;
		}

		/**
		 * @return Whether the authentication flow was not executed
		 */
		public Boolean getNotExecuted() {
			return notExecuted;
		}

		/**
		 * @param notExecuted The not executed flag to set
		 */
		public void setNotExecuted(Boolean notExecuted) {
			this.notExecuted = notExecuted;
		}

		/**
		 * @return Whether the user already has valid credentials
		 */
		public Boolean getAlreadyOwningCredentials() {
			return alreadyOwningCredentials;
		}

		/**
		 * @param alreadyOwningCredentials The already owning credentials flag to set
		 */
		public void setAlreadyOwningCredentials(Boolean alreadyOwningCredentials) {
			this.alreadyOwningCredentials = alreadyOwningCredentials;
		}

		/**
		 * @return The authentication session ID
		 */
		public String getAuthenticationSessionId() {
			return authenticationSessionId;
		}

		/**
		 * @param authenticationSessionId The authentication session ID to set
		 */
		public void setAuthenticationSessionId(String authenticationSessionId) {
			this.authenticationSessionId = authenticationSessionId;
		}

		/**
		 * @return The URL to jump to for proceeding with authentication
		 */
		public String getJumpTo() {
			return jumpTo;
		}

		/**
		 * @param jumpTo The jump URL to set
		 */
		public void setJumpTo(String jumpTo) {
			this.jumpTo = jumpTo;
		}
	}

	/**
	 * Relative URL for initiating the workspace access flow
	 */
	static final String LOCAL_JUMP_TO_RELATIVE_URL = "/oauth2/start-workspace-access-go";

	/**
	 * Handles the redirection to Google's authorization page
	 * 
	 * @param request The HTTP request containing session parameters
	 * @param response The HTTP response for redirection
	 * @throws IOException If an I/O error occurs during redirection
	 */
	@GetMapping(value = LOCAL_JUMP_TO_RELATIVE_URL)
	public void startWorkspaceAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gwsid = request.getParameter(GeboGoogleWorkspaceFlowSession.SESSION_ID_PARAMETER);
		GeboGoogleWorkspaceFlowSession session = sessionFactory.get(gwsid);
		if (session == null)
			response.setStatus(200);
		else {
			response.sendRedirect(session.getRedirectURL());
		}

	}

	/**
	 * API endpoint to initiate Google Workspace authentication flow
	 * 
	 * @param request The request containing user and redirection information
	 * @param _request The HTTP request for building base URLs
	 * @return Response with authentication flow details
	 * @throws GeneralSecurityException If a security error occurs
	 * @throws IOException If an I/O error occurs
	 * @throws GeboCryptSecretException If cryptographic operations fail
	 * @throws GeboContentHandlerSystemException If content handling fails
	 * @throws GeboPersistenceException If persistence operations fail
	 */
	@PostMapping(value = "/api/users/start-workspace-access", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public StartGooglWorkspaceAccessRespose tryGoogleWorkspaceAccess(
			@RequestBody StartGooglWorkspaceAccessRequest request, HttpServletRequest _request)
			throws GeneralSecurityException, IOException, GeboCryptSecretException, GeboContentHandlerSystemException,
			GeboPersistenceException {
		StartGooglWorkspaceAccessRespose response = new StartGooglWorkspaceAccessRespose();
		String baseUrl = _request.getScheme() + "://" + _request.getServerName() + ":" + _request.getServerPort();
		GeboGoogleWorkspaceFlowSession session = sessionFactory.createSession(request.getUser(),
				request.getUiSuccessForward(), request.getUiErrorForward(), baseUrl);
		response.setAlreadyOwningCredentials(session.hasCredentials());
		response.setAuthenticationSessionId(session.getId());
		if (!session.hasCredentials()) {
			response.setForwardUrl(session.getGoogleAuthorizzationUrl());
			response.setJumpTo(baseUrl + LOCAL_JUMP_TO_RELATIVE_URL);
		} else {
			GeboGoogleWorkspaceAccessSecret credentials = session.getCredentials();
			SecretInfo found = cService.findByUid(request.getUser());
			if (found == null) {
				cService.insert(credentials);
			}

		}
		return response;
	}

}