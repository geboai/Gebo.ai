package ai.gebo.security.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class Oauth2RuntimeConfiguration {
	@Id
	@NotNull
	/**
	 * The unique identifier of the configuration.
	 */
	private String id;
	/**
	 * The description of the configuration.
	 */
	@NotNull
	private String description;
	/**
	 * The authentication provider.
	 */
	@NotNull
	private AuthProvider provider;
	/**
	 * The provider configuration.
	 */
	//the following is used only if provider=OAUTH2_GENERIC
	private Oauth2ProviderConfig providerConfig;
	/**
	 * The client secret ID.
	 */
	@NotNull
	private String clientSecretId;
	/**
	 * The list of configuration types.
	 */
	@NotNull
	@NotEmpty
	private List<Oauth2ConfigurationType> configurationTypes;
}
