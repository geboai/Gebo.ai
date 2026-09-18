package ai.gebo.architecture.a2aserver.runtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Registers the single Spring MVC functional endpoint that serves every published
 * A2A endpoint. Spring collects {@link RouterFunction} beans once at startup, so a
 * single delegating router is published here; on every request it consults the
 * {@link A2AServerRegistry}'s live composite router, which the registry rebuilds on
 * every change — giving runtime updates of the {@code /a2a/<url>} endpoints with no
 * restart (mirrors {@code GeboMcpDispatcherConfig}).
 */
@Configuration
public class A2ADispatcherConfig {

	@Bean
	public RouterFunction<ServerResponse> geboA2ARouterFunction(A2AServerRegistry registry) {
		return request -> registry.currentComposite().route(request);
	}
}
