/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.repository.GeboMCPServerConfigRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Owns the live erogated MCP servers and the single composite {@link RouterFunction}
 * that the dispatcher delegates to.
 * <p>
 * Instances are keyed by their configuration {@code code}. The composite router is held
 * in a {@code volatile} field and recomputed on every change, so the management service
 * can add, update or remove a server at runtime ({@link #reload(String)} /
 * {@link #remove(String)} / {@link #reloadAll()}) and the {@code mcp/<url>} endpoints
 * change immediately without an application restart.
 */
@Service
public class GeboMcpServerRegistry {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMcpServerRegistry.class);

	/** A router that matches nothing, used while no MCP server is published. */
	private static final RouterFunction<ServerResponse> EMPTY_ROUTER = request -> Optional.empty();

	private final GeboMCPServerConfigRepository repository;
	private final GeboMcpServerBuilder builder;

	private final Map<String, GeboMcpServerInstance> instances = new ConcurrentHashMap<>();
	private volatile RouterFunction<ServerResponse> composite = EMPTY_ROUTER;

	public GeboMcpServerRegistry(GeboMCPServerConfigRepository repository, GeboMcpServerBuilder builder) {
		this.repository = repository;
		this.builder = builder;
	}

	/**
	 * The current composite router, evaluated per request by the dispatcher.
	 *
	 * @return the live composite router (never {@code null})
	 */
	public RouterFunction<ServerResponse> currentComposite() {
		return composite;
	}

	/**
	 * Builds every configured MCP server from scratch. Invoked at startup and whenever
	 * a bulk refresh is required.
	 */
	@PostConstruct
	public synchronized void reloadAll() {
		closeAll();
		instances.clear();
		for (GeboMCPServerConfig config : repository.findAll()) {
			buildQuietly(config);
		}
		recomputeComposite();
		LOGGER.info("Loaded {} erogated MCP server(s)", instances.size());
	}

	/**
	 * Rebuilds (or removes) the MCP server for a single configuration code, reflecting
	 * the latest persisted state.
	 *
	 * @param code the configuration code
	 */
	public synchronized void reload(String code) {
		if (code == null) {
			return;
		}
		Optional<GeboMCPServerConfig> opt = repository.findById(code);
		closeInstance(instances.remove(code));
		if (opt.isPresent()) {
			buildQuietly(opt.get());
		}
		recomputeComposite();
	}

	/**
	 * Removes and stops the MCP server for the given configuration code.
	 *
	 * @param code the configuration code
	 */
	public synchronized void remove(String code) {
		if (code == null) {
			return;
		}
		closeInstance(instances.remove(code));
		recomputeComposite();
	}

	private void buildQuietly(GeboMCPServerConfig config) {
		if (config == null || config.getCode() == null || config.getExportedUniqueRelativeUrl() == null) {
			return;
		}
		try {
			instances.put(config.getCode(), builder.build(config));
		} catch (Throwable t) {
			// A single misconfigured server must not break the whole dispatcher.
			LOGGER.error("Error building erogated MCP server '{}' ({})", config.getCode(),
					config.getExportedUniqueRelativeUrl(), t);
		}
	}

	private void recomputeComposite() {
		RouterFunction<ServerResponse> result = null;
		for (GeboMcpServerInstance instance : instances.values()) {
			result = result == null ? instance.getRouterFunction() : result.and(instance.getRouterFunction());
		}
		composite = result != null ? result : EMPTY_ROUTER;
	}

	private void closeAll() {
		for (GeboMcpServerInstance instance : instances.values()) {
			closeInstance(instance);
		}
	}

	private void closeInstance(GeboMcpServerInstance instance) {
		if (instance != null) {
			instance.close();
		}
	}

	@PreDestroy
	public synchronized void shutdown() {
		closeAll();
		instances.clear();
		composite = EMPTY_ROUTER;
	}
}
