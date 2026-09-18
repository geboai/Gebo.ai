package ai.gebo.architecture.a2aserver.runtime;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import lombok.Getter;

/**
 * A single published A2A endpoint: the {@link A2AServerConfig} snapshot it was
 * built from and the access-filtered {@link RouterFunction} that serves its Agent
 * Card and JSON-RPC routes. Created by {@link A2AServerBuilder} and owned by
 * {@link A2AServerRegistry}.
 */
@Getter
public class A2AServerInstance {

	private final A2AServerConfig config;
	private final RouterFunction<ServerResponse> routerFunction;

	public A2AServerInstance(A2AServerConfig config, RouterFunction<ServerResponse> routerFunction) {
		this.config = config;
		this.routerFunction = routerFunction;
	}
}
