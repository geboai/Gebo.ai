package ai.gebo.architecture.a2aserver.runtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.repository.A2AServerConfigRepository;
import jakarta.annotation.PostConstruct;

/**
 * Owns the live published A2A endpoints and the single composite
 * {@link RouterFunction} the dispatcher delegates to. Keyed by config {@code code};
 * the composite is held in a {@code volatile} field and recomputed on every change,
 * so endpoints can be added, updated or removed at runtime with no restart —
 * mirroring {@code GeboMcpServerRegistry}.
 */
@Service
public class A2AServerRegistry {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AServerRegistry.class);

	/** A router that matches nothing, used while no A2A server is published. */
	private static final RouterFunction<ServerResponse> EMPTY_ROUTER = request -> Optional.empty();

	private final A2AServerConfigRepository repository;
	private final A2AServerBuilder builder;

	private final Map<String, A2AServerInstance> instances = new ConcurrentHashMap<>();
	private volatile RouterFunction<ServerResponse> composite = EMPTY_ROUTER;

	public A2AServerRegistry(A2AServerConfigRepository repository, A2AServerBuilder builder) {
		this.repository = repository;
		this.builder = builder;
	}

	public RouterFunction<ServerResponse> currentComposite() {
		return composite;
	}

	@PostConstruct
	public synchronized void reloadAll() {
		instances.clear();
		List<A2AServerConfig> configs = repository.findAll();
		for (A2AServerConfig config : configs) {
			if (isServable(config)) {
				safeBuild(config);
			}
		}
		rebuildComposite();
		LOGGER.info("A2A server registry initialized with {} published endpoint(s)", instances.size());
	}

	/** Rebuilds (or removes) the endpoint for the given config code and refreshes the composite. */
	public synchronized void reload(String code) {
		if (code == null) {
			return;
		}
		A2AServerConfig config = repository.findById(code).orElse(null);
		if (config == null || !isServable(config)) {
			instances.remove(code);
		} else {
			safeBuild(config);
		}
		rebuildComposite();
	}

	public synchronized void remove(String code) {
		if (code != null) {
			instances.remove(code);
			rebuildComposite();
		}
	}

	private boolean isServable(A2AServerConfig config) {
		return config != null && config.getEnabled() != null && config.getEnabled()
				&& config.getExportedRelativeUrl() != null && !config.getExportedRelativeUrl().isBlank();
	}

	private void safeBuild(A2AServerConfig config) {
		try {
			instances.put(config.getCode(), builder.build(config));
		} catch (Throwable t) {
			LOGGER.error("Failed to build A2A endpoint for config {}", config.getCode(), t);
			instances.remove(config.getCode());
		}
	}

	private void rebuildComposite() {
		RouterFunction<ServerResponse> next = null;
		for (A2AServerInstance instance : instances.values()) {
			next = (next == null) ? instance.getRouterFunction() : next.and(instance.getRouterFunction());
		}
		composite = next != null ? next : EMPTY_ROUTER;
	}
}
