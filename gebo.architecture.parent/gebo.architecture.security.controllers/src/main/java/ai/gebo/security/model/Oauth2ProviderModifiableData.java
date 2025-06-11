package ai.gebo.security.model;

import java.util.List;

import ai.gebo.security.model.oauth2.Oauth2AuthorizationGrantType;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthMethod;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Oauth2ProviderModifiableData {
	@NotNull
	private String registrationId = null;
	private Oauth2ClientAuthMethod authClientMethod;
	private Oauth2AuthorizationGrantType authGrantType;
	@NotNull
	private @NotNull String description;
	private @NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes;
	private List<String> scopes = null;
}