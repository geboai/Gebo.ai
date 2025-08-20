package ai.gebo.security.services;

import java.io.IOException;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.SecurityHeaderData;

public interface IGBackendOauth2LoginSPASupportService {
	public static final String BACKEND_OAUTH2_COOKIE_NAME = "BackendOauth2Cookie";

	public String registerOauth2LoginAttempt(String remoteAddress, String nextUri, String tenantId,
			String registrationId) throws BackendOauth2LoginSPASupportException, GeboCryptSecretException;

	public String oauth2LoginSuccess(String loginAttemptId, String remoteAddress, DefaultOidcUser user)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException;

	public OperationStatus<SecurityHeaderData> getAuthorizationData(String loginAttemptId, String remoteAddress)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException, IOException;

}
