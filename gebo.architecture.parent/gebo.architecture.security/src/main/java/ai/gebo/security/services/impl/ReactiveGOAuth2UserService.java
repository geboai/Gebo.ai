package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGUsersAdminService;
import reactor.core.publisher.Mono;

/**
 * The reactive twin of {@link GOAuth2UserService}: the decision
 * {@code ai.gebo.security.loginPolicy} governs is made by
 * {@link GOauth2LoginPolicyEnforcer}, as for the servlet logins, and audited there
 * under the flow {@code oauth2LoginReactive}.
 *
 * <p>
 * It used to carry a copy of that decision, which drifted: a change to the policy,
 * such as refusing a disabled account, had to be made twice to reach both logins.
 * A refusal is thrown from inside the {@code flatMap}, which Reactor turns into the
 * error of the returned {@code Mono}, as the copy's own failures already were.
 * </p>
 */
public class ReactiveGOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final GOauth2LoginPolicyEnforcer policy;

	public ReactiveGOAuth2UserService(IGOauth2ConfigurationService oauth2ConfigService,
			IGUsersAdminService userService, GeboSecurityConfig securityProperties,
			IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.policy = new GOauth2LoginPolicyEnforcer(oauth2ConfigService, userService, securityProperties,
				oauth2SyncService, securityAuditLoggerService);
	}

	@Override
	public Mono<OAuth2User> loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		return new DefaultReactiveOAuth2UserService().loadUser(userRequest).flatMap(oauth2User -> {
			policy.enforce(registrationId, oauth2User, "oauth2LoginReactive");
			return Mono.just(oauth2User);
		});
	}
}
