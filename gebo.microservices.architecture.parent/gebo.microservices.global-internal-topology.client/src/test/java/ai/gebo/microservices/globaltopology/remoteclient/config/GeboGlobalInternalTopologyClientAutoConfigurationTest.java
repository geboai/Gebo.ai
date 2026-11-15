package ai.gebo.microservices.globaltopology.remoteclient.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.webclient.WebClientCustomizer;
import org.springframework.boot.webclient.autoconfigure.WebClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import reactor.core.publisher.Mono;

/**
 * The service client is built the way Spring Boot builds its own
 * {@code WebClient.Builder}: with the application's {@link WebClientCustomizer}s
 * applied, so they shape its calls. With tracing, that is the observation filter
 * that gives each call a span and sends the current trace ({@code traceparent})
 * to the service called. Without any customizer (a service without the WebClient
 * auto-configuration) the client is still built, bare.
 */
class GeboGlobalInternalTopologyClientAutoConfigurationTest {

	private final ApplicationContextRunner runner = new ApplicationContextRunner()
			.withBean(GeboMicroserviceUrlResolver.class, () -> mock(GeboMicroserviceUrlResolver.class))
			.withBean(IGeboCallerTokenPropagator.class, () -> mock(IGeboCallerTokenPropagator.class));

	@Test
	void theClientCarriesTheApplicationsWebClientCustomizations() {
		AtomicReference<ClientRequest> sent = new AtomicReference<>();
		// what a customizer of the application does, seen from the request it shapes
		WebClientCustomizer customizer = builder -> builder.defaultHeader("X-Gebo-Customized", "yes")
				.exchangeFunction(request -> {
					sent.set(request);
					return Mono.just(ClientResponse.create(HttpStatus.OK).build());
				});
		runner.withConfiguration(AutoConfigurations.of(WebClientAutoConfiguration.class,
				GeboGlobalInternalTopologyClientAutoConfiguration.class))
				.withBean("testCustomizer", WebClientCustomizer.class, () -> customizer).run(context -> {
					// (Spring Boot's own WebClient.Builder backs off when the application
					// defines one, as the service clients do: the customizers are what the
					// two have in common)
					WebClient client = context.getBean(GeboGlobalInternalTopologyClientAutoConfiguration.WEB_CLIENT_BEAN,
							WebClient.class);
					client.get().uri("http://tyr_gebo_ai/api/admin/GlobalInternalTopologyController/topology")
							.retrieve().toBodilessEntity().block();
					assertNotNull(sent.get(), "the call went through the customized exchange");
					assertEquals("yes", sent.get().headers().getFirst("X-Gebo-Customized"),
							"the application's customizer shaped the service client's request");
				});
	}

	@Test
	void withoutAnyCustomizerTheClientIsStillBuilt() {
		runner.withConfiguration(AutoConfigurations.of(GeboGlobalInternalTopologyClientAutoConfiguration.class))
				.run(context -> {
					assertTrue(context.containsBean(GeboGlobalInternalTopologyClientAutoConfiguration.WEB_CLIENT_BEAN));
					assertNotNull(context.getBean(GeboGlobalInternalTopologyClientAutoConfiguration.WEB_CLIENT_BEAN,
							WebClient.class));
				});
	}
}
