package ai.gebo.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class Oauth2DynamicReactiveRegistrationRepository implements ReactiveClientRegistrationRepository {
	@Autowired
	Oauth2DynamicClientRegistrationRepository repo;

	public Oauth2DynamicReactiveRegistrationRepository() {

	}

	@Override
	public Mono<ClientRegistration> findByRegistrationId(String registrationId) {

		return Mono.just(repo.findByRegistrationId(registrationId));
	}

}
