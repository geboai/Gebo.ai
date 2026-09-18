package ai.gebo.security.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Oauth2SyncUsersData {
	private final EditableUser user;
	private final OAuth2User oauth2User;
	private final Oauth2ClientRegistration config;
}
