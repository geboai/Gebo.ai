package ai.gebo.security.model;

import java.util.List;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.oauth2.Oauth2AuthorizationGrantType;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthMethod;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Oauth2ProviderModifiableData {

	private String code = null;
	private @NotNull AuthProvider authProvider;
	private Oauth2ProviderConfig providerConfiguration;
	private @NotNull @Valid GeboOauth2SecretContent oauth2ClientContent;
	private Oauth2ClientAuthMethod authClientMethod;
	private Oauth2AuthorizationGrantType authGrantType;
	private @NotNull Oauth2ConfigurationType configurationType;
	private @NotNull String description;

	/**
	 * Which audiences a token presented to the api may carry to be accepted for this
	 * configuration, usually the client id of this registration. Empty accepts any
	 * token the provider signed, including one issued to a different client of the
	 * same provider, which is the behaviour of an installation that never set this.
	 */
	private List<String> resourceServerAudiences = null;

	@NotNull
	private Boolean readOnly = false;
}