package ai.gebo.security.repository;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class Oauth2DynamicClientRegistrationRepository implements ClientRegistrationRepository{

	@Override
	public ClientRegistration findByRegistrationId(String registrationId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
