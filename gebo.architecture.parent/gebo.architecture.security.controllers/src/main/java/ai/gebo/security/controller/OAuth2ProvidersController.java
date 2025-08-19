package ai.gebo.security.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.config.GeboConfig;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthorizativeInfo;
import ai.gebo.security.model.oauth2.Oauth2ClientConfig;
import ai.gebo.security.repository.IOauth2DynamicClientRegistrationRepository;
import ai.gebo.security.services.IGOauth2ConfigurationService;

@RestController
@RequestMapping("/public/oauth2")
public class OAuth2ProvidersController {

	private final IGOauth2ConfigurationService oauth2Service;
	private final IOauth2DynamicClientRegistrationRepository dynamicClientRegistrationRepository;
	private final GeboSecurityConfig geboConfig;

	public OAuth2ProvidersController(IGOauth2ConfigurationService oauth2Service,
			IOauth2DynamicClientRegistrationRepository dynamicClientRegistrationRepository,
			GeboSecurityConfig geboConfig) {
		this.oauth2Service = oauth2Service;
		this.dynamicClientRegistrationRepository = dynamicClientRegistrationRepository;
		this.geboConfig = geboConfig;
	}

	@GetMapping(value = "listAvailableProviders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Oauth2ClientAuthorizativeInfo> listAvailableProviders() {
		if (geboConfig.getOauth2LoginEnabled() != null && geboConfig.getOauth2LoginEnabled())
			return oauth2Service.findAllAauthorizativeRegistrations();
		else
			return List.of();
	}

	@GetMapping(value = "getProviderClientConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2ClientConfig getProviderClientConfig(@RequestParam("registrationId") String registrationId) {
		if (geboConfig.getOauth2LoginEnabled() != null && geboConfig.getOauth2LoginEnabled())
			return this.dynamicClientRegistrationRepository.findOauth2ClientConfigById(registrationId);
		else
			return null;

	}
	
	
}
