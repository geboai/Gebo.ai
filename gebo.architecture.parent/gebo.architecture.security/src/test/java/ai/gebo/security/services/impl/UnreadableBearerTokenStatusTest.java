package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityDirectory;
import reactor.core.publisher.Mono;

/**
 * A bearer token the resolvers cannot route anywhere - not JWT-shaped with no OAuth2 provider
 * configured, or an OAUTH2 header with no provider configured - is a failed authentication:
 * the client gets 401, not 500, and is told only that its token is invalid: nothing about how
 * authentication is configured here.
 */
class UnreadableBearerTokenStatusTest {

	private static final String GARBAGE = "not-a-token";

	private IGOauth2RuntimeConfigurationDao configurations;

	@BeforeEach
	void noOauth2Providers() {
		configurations = mock(IGOauth2RuntimeConfigurationDao.class);
		when(configurations.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION)).thenReturn(List.of());
	}

	private static void assertGenericInvalidToken(String wwwAuthenticate) {
		assertThat(wwwAuthenticate).startsWith("Bearer error=\"invalid_token\"")
				.contains("error_description=\"Invalid bearer token\"").doesNotContainIgnoringCase("configuration")
				.doesNotContainIgnoringCase("oauth2");
	}

	private MockHttpServletResponse servletResponse(String authType) throws Exception {
		UserDetailsService users = mock(UserDetailsService.class);
		GHttpRequestAuthenticationManagerResolverImpl resolver = new GHttpRequestAuthenticationManagerResolverImpl(
				users, mock(PasswordEncoder.class), configurations, mock(LocalJwtTokenProvider.class), users,
				new GeboSecurityConfig(), mock(IGSecurityDirectory.class), null,
				mock(IGSecurityAuditLoggerService.class), null);
		BearerTokenAuthenticationFilter filter = new BearerTokenAuthenticationFilter(resolver);
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/anything");
		request.addHeader("Authorization", "Bearer " + GARBAGE);
		if (authType != null)
			request.addHeader("X-AuthType", authType);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		filter.doFilter(request, response, chain);
		assertThat(chain.getRequest()).as("the request must not reach the application").isNull();
		return response;
	}

	@Test
	void anUnreadableTokenWithoutAuthTypeIsUnauthorized() throws Exception {
		MockHttpServletResponse response = servletResponse(null);
		assertThat(response.getStatus()).isEqualTo(401);
		assertGenericInvalidToken(response.getHeader("WWW-Authenticate"));
	}

	@Test
	void anOauth2TokenWithNoProviderConfiguredIsUnauthorized() throws Exception {
		MockHttpServletResponse response = servletResponse("OAUTH2");
		assertThat(response.getStatus()).isEqualTo(401);
		assertGenericInvalidToken(response.getHeader("WWW-Authenticate"));
	}

	@Test
	void theReactiveChainAnswersUnauthorizedToo() {
		GReactiveHttpRequestAuthenticationManagerResolverImpl resolver = new GReactiveHttpRequestAuthenticationManagerResolverImpl(
				mock(UserDetailsService.class), configurations, mock(LocalJwtTokenProvider.class), null, null);
		AuthenticationWebFilter filter = new AuthenticationWebFilter(resolver);
		filter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
		filter.setAuthenticationFailureHandler(
				new ServerAuthenticationEntryPointFailureHandler(new BearerTokenServerAuthenticationEntryPoint()));
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/api/users/anything")
				.header("Authorization", "Bearer " + GARBAGE).header("X-AuthType", "OAUTH2"));
		boolean[] reached = { false };
		// the filter assembles chain.filter(...) eagerly as its fallback: count a subscription, not a call
		filter.filter(exchange, e -> Mono.fromRunnable(() -> reached[0] = true)).block();
		assertThat(reached[0]).as("the request must not reach the application").isFalse();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertGenericInvalidToken(exchange.getResponse().getHeaders().getFirst("WWW-Authenticate"));
	}
}
