package ai.gebo.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.AuthProvider.AuthProviderDto;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthorizativeInfo;
import ai.gebo.security.model.oauth2.Oauth2ClientConfig;
import ai.gebo.security.repository.IOauth2DynamicClientRegistrationRepository;
import ai.gebo.security.services.IGOauth2ConfigurationService;

@RestController
@RequestMapping("/public/AutenticationProvidersController")
public class AuthProvidersController {

	private final IGOauth2ConfigurationService oauth2Service;
	private final IOauth2DynamicClientRegistrationRepository dynamicClientRegistrationRepository;
	private final GeboSecurityConfig geboConfig;

	public AuthProvidersController(IGOauth2ConfigurationService oauth2Service,
			IOauth2DynamicClientRegistrationRepository dynamicClientRegistrationRepository,
			GeboSecurityConfig geboConfig) {
		this.oauth2Service = oauth2Service;
		this.dynamicClientRegistrationRepository = dynamicClientRegistrationRepository;
		this.geboConfig = geboConfig;
	}

	@GetMapping(value = "listAvailableProvidersConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Oauth2ClientAuthorizativeInfo> listAvailableProvidersConfig() {
		if (geboConfig.getOauth2LoginEnabled() != null && geboConfig.getOauth2LoginEnabled())
			return oauth2Service.findAllAauthorizativeRegistrations();
		else
			return List.of();
	}

	@GetMapping(value = "listAuthProviders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AuthProviderDto> listAuthProviders() {
		if (geboConfig.getOauth2LoginEnabled() != null && geboConfig.getOauth2LoginEnabled()) {
			List<AuthProvider> authProviders = new ArrayList<AuthProvider>();
			authProviders.add(AuthProvider.local);
			List<Oauth2ClientAuthorizativeInfo> registrations = oauth2Service.findAllAauthorizativeRegistrations();
			List<AuthProvider> oauth2List = registrations.stream().map(x -> x.getProviderName()).filter(name -> {
				try {
					AuthProvider.valueOf(name);
					return true;
				} catch (Throwable th) {
					return false;
				}
			}).map(name -> AuthProvider.valueOf(name)).toList();
			for (AuthProvider authProvider : oauth2List) {
				if (!authProviders.contains(authProvider)) {
					authProviders.add(authProvider);
				}
			}
			return authProviders.stream().map(x -> x.getAuthProviderDto()).toList();
		} else
			return List.of(AuthProvider.local.getAuthProviderDto());
	}

	@GetMapping(value = "getProviderClientConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ClientConfig getProviderClientConfig(@RequestParam("registrationId") String registrationId) {
		if (geboConfig.getOauth2LoginEnabled() != null && geboConfig.getOauth2LoginEnabled())
			return this.dynamicClientRegistrationRepository.findOauth2ClientConfigById(registrationId);
		else
			return null;

	}

}
