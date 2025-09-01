package ai.gebo.security.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import ai.gebo.security.model.oauth2.Oauth2ClientConfig;
@NoRepositoryBean
public interface IOauth2DynamicClientRegistrationRepository extends ClientRegistrationRepository {

	public Oauth2ClientConfig findOauth2ClientConfigById(String registrationId);

}
