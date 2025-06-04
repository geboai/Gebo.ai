package ai.gebo.security.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.oauth2.Oauth2ClientAuthorizativeInfo;
import ai.gebo.security.services.IGOauth2ConfigurationService;

@RestController
@RequestMapping("/public/oauth2")
public class OAuth2ProvidersController {

	private final IGOauth2ConfigurationService oauth2Service;

	public OAuth2ProvidersController(IGOauth2ConfigurationService oauth2Service) {
		this.oauth2Service = oauth2Service;
	}

	@GetMapping(value = "listAvailableProviders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Oauth2ClientAuthorizativeInfo> listAvailableProviders() {
		return oauth2Service.findAllAauthorizativeRegistrations();
	}

}
