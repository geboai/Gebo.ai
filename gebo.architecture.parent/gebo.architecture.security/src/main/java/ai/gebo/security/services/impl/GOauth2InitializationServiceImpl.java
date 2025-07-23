package ai.gebo.security.services.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.security.model.oauth2.Oauth2InitializationInfo;
import ai.gebo.security.repository.Oauth2InitializationInfoRepository;
import ai.gebo.security.services.IGOauth2InitializationService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOauth2InitializationServiceImpl implements IGOauth2InitializationService {
	private ClientRegistrationRepository clientRegistrationRepository;

	private Oauth2InitializationInfoRepository initializationRepo;

	@Override
	public Oauth2InitializationInfo startOauthLogin(String registrationId, String remoteAddr, String currentUri)
			throws MalformedURLException {
		ClientRegistration registration = ((ClientRegistrationRepository) clientRegistrationRepository)
				.findByRegistrationId(registrationId);

		if (registration == null) {
			throw new RuntimeException("Unknown " + registrationId);
		}

		String loginId = UUID.randomUUID().toString();
		Oauth2InitializationInfo infos = new Oauth2InitializationInfo();
		infos.setLoginId(loginId);
		URL url = new URL(currentUri);
		String baseUrl = url.getProtocol() + "://" + url.getHost() + (url.getPort() > 0 ? ":" + url.getPort() : "");
		String redirectUri = UriComponentsBuilder
				.fromPath(IGOauth2InitializationService.OAUTH2_AUTHORIZATION_REGISTRATION_ID)
				.queryParam(IGOauth2InitializationService.LOGIN_ID, loginId).buildAndExpand(registrationId)
				.toUriString();
		String loginAbsoluteUrl = baseUrl + redirectUri;
		infos.setLoginAbsoluteUrl(loginAbsoluteUrl);
		infos.setRegistrationId(registrationId);
		infos.setCreationTimestamp(new Date());
		infos.setRemoteHost(remoteAddr);
		initializationRepo.insert(infos);
		return infos;
	}

	@Override
	public Oauth2InitializationInfo endOauthLogin(String loginId, String remoteAddr) {
		Optional<Oauth2InitializationInfo> infosOptional = initializationRepo.findById(loginId);
		if (infosOptional.isPresent())
			throw new RuntimeException("Unkown login session");
		Oauth2InitializationInfo infos = infosOptional.get();
		if (!infos.getRemoteHost().equals(remoteAddr)) {
			throw new RuntimeException("Unkown remote host");
		}
		infos.setLoginFinishedTimestamp(new Date());
		initializationRepo.save(infos);
		return infos;
	}

	@Override
	public void updateAuthenticated(@NotNull String loginId, @NotNull String principalName,
			@NotNull String accessToken) {
		Optional<Oauth2InitializationInfo> infosOptional = initializationRepo.findById(loginId);
		if (infosOptional.isPresent())
			throw new RuntimeException("Unkown login session");
		Oauth2InitializationInfo infos = infosOptional.get();
		infos.setOauth2Token(accessToken);
		infos.setPrincipalName(principalName);
		infos.setLoginFinishedTimestamp(new Date());
		initializationRepo.save(infos);

	}

}
