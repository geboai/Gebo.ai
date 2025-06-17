package ai.gebo.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.Oauth2ProviderModifiableData;
import ai.gebo.security.model.Oauth2ProviderRegistrationInsertData;
import ai.gebo.security.model.AuthProvider.AuthProviderDto;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OAuth2AdminController")

public class OAuth2AdminController {
	@Autowired
	IGOauth2ConfigurationService oauth2ConfiguratoinService;

	public OAuth2AdminController() {

	}

	@GetMapping(value = "getProviders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AuthProviderDto> getProviders() {
		return AuthProvider.getOauth2Providers();

	}

	@PostMapping(value = "insertOauth2ProviderRegistration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ProviderModifiableData insertOauth2ProviderRegistration(
			@NotNull @Valid @RequestBody Oauth2ProviderRegistrationInsertData data) throws GeboOauth2Exception {
		String registrationId = null;
		if (data.getAuthProvider() == AuthProvider.oauth2_generic) {
			if (data.getOauth2ClientContent() == null) {
				throw new RuntimeException(
						"If authProvider is oauth2_generic a custom provider configuration has to be inserted");
			}
			registrationId = oauth2ConfiguratoinService.insertOauth2Configuration(data.getProviderConfiguration(),
					data.getOauth2ClientContent(), data.getAuthClientMethod(), data.getAuthGrantType(),
					data.getConfigurationTypes(), data.getDescription());
		} else {
			registrationId = oauth2ConfiguratoinService.insertOauth2Configuration(data.getAuthProvider(),
					data.getOauth2ClientContent(), data.getAuthClientMethod(), data.getAuthGrantType(),
					data.getConfigurationTypes(), data.getDescription());
		}
		Oauth2ClientRegistration registration = oauth2ConfiguratoinService
				.findOauth2ClientRegistrationByRegistrationId(registrationId);
		return toModifiableData(registration);
	}

	@GetMapping(value = "findOauth2ProviderRegistrationByRegistrationId", produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ProviderModifiableData findOauth2ProviderRegistrationByRegistrationId(
			@RequestParam("registrationId") String registrationId) throws GeboOauth2Exception {
		Oauth2ClientRegistration registration = oauth2ConfiguratoinService
				.findOauth2ClientRegistrationByRegistrationId(registrationId);
		return toModifiableData(registration);
	}

	@PostMapping(value = "updateOauth2ProviderRegistration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ProviderModifiableData updateOauth2ProviderRegistration(
			@NotNull @Valid @RequestBody Oauth2ProviderModifiableData data) throws GeboOauth2Exception {
		oauth2ConfiguratoinService.updateOauth2Configuration(data.getRegistrationId(), data.getScopes(),
				data.getAuthClientMethod(), data.getAuthGrantType(), data.getConfigurationTypes(),
				data.getDescription());
		Oauth2ClientRegistration registration = oauth2ConfiguratoinService
				.findOauth2ClientRegistrationByRegistrationId(data.getRegistrationId());
		return toModifiableData(registration);
	}

	@DeleteMapping(value = "deleteOauth2ProviderRegistration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOauth2ProviderRegistration(@NotNull @Valid @RequestBody Oauth2ProviderModifiableData data)
			throws GeboOauth2Exception {
		this.oauth2ConfiguratoinService.deleteOauth2Configuration(data.getRegistrationId());
	}

	private Oauth2ProviderModifiableData toModifiableData(Oauth2ClientRegistration registration) {
		if (registration == null)
			return null;

		return null;
	}
}
