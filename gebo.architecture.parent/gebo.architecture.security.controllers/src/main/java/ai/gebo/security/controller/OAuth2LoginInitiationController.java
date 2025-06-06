package ai.gebo.security.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.security.model.oauth2.Oauth2InitializationInfo;
import ai.gebo.security.payload.UserInfo;
import ai.gebo.security.repository.Oauth2InitializationInfoRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGOauth2InitializationService;
import ai.gebo.security.services.IGSecurityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/public/oauth2")
public class OAuth2LoginInitiationController {
	@Autowired
	private IGOauth2InitializationService initializationService;
	@Autowired
	private IGSecurityService securityService;

	@GetMapping(value = "start/{registrationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2InitializationInfo startOauthLogin(@PathVariable("registrationId") String registrationId,
			HttpServletRequest request) {
		return initializationService.startOauthLogin(registrationId, request.getRemoteAddr());
	}

	@AllArgsConstructor
	@Getter
	public static class Oauth2LoginInfo {
		private final UserInfos userInfo;
		private final String token;
	}

	@GetMapping(value = "end/{loginId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Oauth2LoginInfo endOauthLogin(@PathVariable("loginId") String loginId, HttpServletRequest request) {
		Oauth2InitializationInfo data = initializationService.endOauthLogin(loginId, request.getRemoteAddr());
		// TODO: CALL THE securityService, check that is correct
		UserInfos currentUser = securityService.getCurrentUser();
		if (!currentUser.getUsername().equals(data.getPrincipalName()))
			throw new RuntimeException("Security Incoherency");
		return new Oauth2LoginInfo(currentUser, data.getOauth2Token());
	}
}
