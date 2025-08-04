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
	private @NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes;
	private @NotNull String description;
	private List<String> scopes=null;
	@NotNull
	private Boolean readOnly=false;
}