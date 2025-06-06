package ai.gebo.security.services;

import ai.gebo.security.model.oauth2.Oauth2InitializationInfo;
import jakarta.validation.constraints.NotNull;

public interface IGOauth2InitializationService {
	
	public static final String LOGIN_ID = "login_id";
	public static final String OAUTH2_AUTHORIZATION_REGISTRATION_ID = "/oauth2/authorization/{registrationId}";

	public Oauth2InitializationInfo startOauthLogin(@NotNull String registrationId, @NotNull String remoteAddr);

	public Oauth2InitializationInfo endOauthLogin(@NotNull String loginId, @NotNull String remoteAddr);

	public void updateAuthenticated(@NotNull String loginId, @NotNull String principalName, @NotNull String accessToken);

}
