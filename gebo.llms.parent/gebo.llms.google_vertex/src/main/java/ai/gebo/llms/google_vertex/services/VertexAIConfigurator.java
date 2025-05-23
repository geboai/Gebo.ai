/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.google_vertex.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails.Builder;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.cloud.aiplatform.v1.stub.PredictionServiceStubSettings;
import com.google.cloud.vertexai.Transport;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.PredictionServiceClient;
import com.google.common.base.Supplier;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.model.GeboGoogleOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service class responsible for configuring Google Vertex AI connections.
 * This class provides methods to create and configure VertexAI instances
 * and embedding connection details using different types of Google credentials.
 */
@Service
class VertexAIConfigurator {
	/**
	 * Service for accessing secret credentials stored in the Gebo system.
	 */
	@Autowired
	private IGeboSecretsAccessService secretService;

	/**
	 * Creates and configures a VertexAI instance using provided secret credentials.
	 * 
	 * @param secretCode The identifier for the secret to be used for authentication
	 * @param baseUrl Optional custom API endpoint URL
	 * @return Configured VertexAI instance
	 * @throws LLMConfigException If configuration fails due to invalid credentials or other issues
	 */
	VertexAI createVertexAI(String secretCode, String baseUrl) throws LLMConfigException {
		GeboGoogleOauth2SecretContent googleOauth2 = null;
		GeboGoogleJsonSecretContent googleJson = null;
		try {
			if (secretCode == null || secretCode.trim().length() == 0)
				throw new LLMConfigException("Google vertex  api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(secretCode);
				if (secret.type() == GeboSecretType.OAUTH2_GOOGLE) {
					googleOauth2 = (GeboGoogleOauth2SecretContent) secret;

				} else if (secret.type() == GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS) {
					googleJson = (GeboGoogleJsonSecretContent) secret;

				} else {
					throw new LLMConfigException(
							"Google vertex  api can work only with an api key of type OAUTH2_GOOGLE or GOOGLE_CLOUD_JSON_CREDENTIALS");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Google vertex  api  key configuration gone wrong ", e);
			}
			VertexAI vertexAI = null;
			VertexAI.Builder vBuilder = new VertexAI.Builder();
			GoogleCredentials credentials = null;
			CredentialsProvider cProvider = null;
			
			// Configure credentials based on secret type
			if (googleOauth2 != null) {
				credentials = GoogleCredentials.create(new AccessToken(googleOauth2.getToken(), null));
				final GoogleCredentials gcredentials=credentials;
				cProvider=()->gcredentials;
			}
			if (googleJson != null) {
				credentials = GoogleCredentials
						.fromStream(new ByteArrayInputStream(googleJson.getJsonContent().getBytes()));
				final GoogleCredentials gcredentials=credentials;
				cProvider=()->gcredentials;
			}
			credentials.refreshIfExpired();
			com.google.cloud.aiplatform.v1.PredictionServiceSettings.Builder predictionBuilder = PredictionServiceSettings
					.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials));

			// Configure custom endpoint if provided
			if (baseUrl != null && baseUrl.trim().length() > 0) {
				vBuilder.setApiEndpoint(baseUrl);
				predictionBuilder.setEndpoint(baseUrl);
			}

			com.google.cloud.vertexai.api.PredictionServiceSettings serviceSettings = com.google.cloud.vertexai.api.PredictionServiceSettings
					.newBuilder().setCredentialsProvider(cProvider).build();
			final PredictionServiceClient serviceClient = PredictionServiceClient.create(serviceSettings);
			vBuilder.setPredictionClientSupplier(() -> serviceClient);
			vBuilder.setTransport(Transport.REST);
			vBuilder = vBuilder.setCredentials(credentials);
			
			// Set additional configuration properties from OAuth2 credentials if available
			if (googleOauth2 != null) {
				if (googleOauth2.getProjectId() != null) {
					vBuilder = vBuilder.setProjectId(googleOauth2.getProjectId());
				}
				if (googleOauth2.getLocation() != null) {
					vBuilder = vBuilder.setLocation(googleOauth2.getLocation());
				}
				if (googleOauth2.getScopes() != null && !googleOauth2.getScopes().isEmpty()) {
					vBuilder = vBuilder.setScopes(googleOauth2.getScopes());
				} else {
					vBuilder = vBuilder.setScopes(List.of("https://www.googleapis.com/auth/cloud-platform"));
				}
			} else {
				vBuilder = vBuilder.setScopes(List.of("https://www.googleapis.com/auth/cloud-platform"));
			}
			vertexAI = vBuilder.build();
			return vertexAI;
		} catch (IOException e) {
			throw new LLMConfigException(
					"IO Exception while trying to configure Google Vertex AI [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * Creates and configures a VertexAiEmbeddingConnectionDetails object for embedding operations.
	 * 
	 * @param secretCode The identifier for the secret to be used for authentication
	 * @param baseUrl Optional custom API endpoint URL
	 * @return Configured VertexAiEmbeddingConnectionDetails object
	 * @throws LLMConfigException If configuration fails due to invalid credentials or other issues
	 */
	VertexAiEmbeddingConnectionDetails createVertexAiEmbeddingConnectionDetails(String secretCode, String baseUrl)
			throws LLMConfigException {
		try {
			GeboGoogleJsonSecretContent googleJson = null;
			GeboGoogleOauth2SecretContent googleOauth2 = null;
			if (secretCode == null || secretCode.trim().length() == 0)
				throw new LLMConfigException("Google vertex  api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(secretCode);
				if (secret.type() == GeboSecretType.OAUTH2_GOOGLE) {
					googleOauth2 = (GeboGoogleOauth2SecretContent) secret;

				} else if (secret.type() == GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS) {
					googleJson = (GeboGoogleJsonSecretContent) secret;

				} else {
					throw new LLMConfigException(
							"Google vertex  api can work only with an api key of type OAUTH2_GOOGLE or GOOGLE_CLOUD_JSON_CREDENTIALS");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Google vertex  api  key configuration gone wrong ", e);
			}
			GoogleCredentials credentials = null;
			// Initialize credentials based on secret type
			if (googleOauth2 != null)
				credentials = GoogleCredentials.create(new AccessToken(googleOauth2.getToken(), null));
			if (googleJson != null)
				credentials = GoogleCredentials
						.fromStream(new ByteArrayInputStream(googleJson.getJsonContent().getBytes()));
			credentials.refreshIfExpired();
			
			Builder builder = VertexAiEmbeddingConnectionDetails.builder();
			// Set project and location if available from OAuth2 credentials
			if (googleOauth2 != null) {
				if (googleOauth2.getProjectId() != null) {
					builder = builder.projectId(googleOauth2.getProjectId());
				}
				if (googleOauth2.getLocation() != null) {
					builder = builder.location(googleOauth2.getLocation());
				}
			}
			com.google.cloud.aiplatform.v1.PredictionServiceSettings.Builder predictionBuilder = PredictionServiceSettings
					.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials));
			
			// Configure custom endpoint if provided
			if (baseUrl != null && baseUrl.trim().length() > 0) {
				builder.apiEndpoint(baseUrl);
				predictionBuilder.setEndpoint(baseUrl);
			}
			PredictionServiceSettings predictionService = predictionBuilder.build();
			builder = builder.predictionServiceSettings(predictionService);
			VertexAiEmbeddingConnectionDetails connectionDetails = builder.build();
			return connectionDetails;
		} catch (IOException e) {
			throw new LLMConfigException("IO Exception while trying to configure Google Vertex Embedding", e);
		}
	}
}