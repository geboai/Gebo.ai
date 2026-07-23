/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpclients.config.McpClientsConfig;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.McpAuthMode;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import io.modelcontextprotocol.client.McpSyncClient;
import lombok.AllArgsConstructor;

/**
 * A small pool/cache of connected {@link McpSyncClient}s, so that repeated tool
 * invocations against the same MCP server reuse a single live connection instead
 * of reconnecting (and re-authenticating) on every call.
 * <p>
 * <b>Keying.</b> Entries are keyed by the configuration code plus, for the
 * per-user authentication modes (where the bearer token is the calling user's
 * own token), the current username. For the shared auth modes (none / API key /
 * static bearer / client-credentials) a single connection is shared across users.
 * <p>
 * <b>Invalidation.</b> A signature of the connection-relevant fields of the
 * config (transport, URLs, auth, secret references, STDIO process, last
 * modification date) is stored with each entry; if the config changes the stale
 * connection is closed and rebuilt. Entries also expire after a TTL — which both
 * bounds resource usage and refreshes credentials that were resolved at connect
 * time (e.g. client-credentials access tokens). On an operation failure the entry
 * is evicted and the operation retried once on a fresh connection, to recover
 * from connections dropped by the server.
 * <p>
 * All pooled clients are closed on application shutdown.
 */
@Service
@AllArgsConstructor
public class McpClientPool implements DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(McpClientPool.class);

	/** User part of the cache key for auth modes that are not per-user. */
	private static final String SHARED_USER = "*";
	private static final String ANONYMOUS_USER = "anonymous";

	private final McpClientConnector connector;
	private final IGSecurityService securityService;
	private final McpClientsConfig clientsConfig;

	private final Map<String, PooledClient> pool = new ConcurrentHashMap<>();
	private final Map<String, Object> locks = new ConcurrentHashMap<>();

	/**
	 * An operation to run against a pooled {@link McpSyncClient}.
	 */
	@FunctionalInterface
	public interface McpClientOperation<T> {
		T run(McpSyncClient client) throws Exception;
	}

	/**
	 * Runs the given operation against a pooled connection for the configuration,
	 * transparently (re)connecting as needed. The connection is owned by the pool
	 * and must not be closed by the operation.
	 *
	 * @param config    the MCP client configuration
	 * @param operation the operation to run with the connected client
	 * @return the operation result
	 * @throws Exception if the connection cannot be established or the operation
	 *                   fails on both the pooled and a freshly rebuilt connection
	 */
	public <T> T execute(MCPClientConfig config, McpClientOperation<T> operation) throws Exception {
		String key = cacheKey(config);
		PooledClient pooled = obtain(config, key);
		try {
			return operation.run(pooled.client);
		} catch (Throwable firstError) {
			LOGGER.warn("MCP operation on pooled client '{}' failed; refreshing connection and retrying once", key,
					firstError);
			evict(key, pooled);
			PooledClient refreshed = obtain(config, key);
			try {
				return operation.run(refreshed.client);
			} catch (Throwable secondError) {
				evict(key, refreshed);
				throw secondError;
			}
		}
	}

	private PooledClient obtain(MCPClientConfig config, String key) throws Exception {
		String signature = signature(config);
		PooledClient existing = pool.get(key);
		if (isUsable(existing, signature)) {
			return existing;
		}
		// Serialize concurrent (re)connections for the same key so we open at most one.
		Object lock = locks.computeIfAbsent(key, k -> new Object());
		synchronized (lock) {
			existing = pool.get(key);
			if (isUsable(existing, signature)) {
				return existing;
			}
			if (existing != null) {
				pool.remove(key, existing);
				connector.closeQuietly(existing.client);
			}
			McpSyncClient client = connector.connectAndInitialize(config);
			PooledClient created = new PooledClient(client, signature, System.currentTimeMillis());
			pool.put(key, created);
			return created;
		}
	}

	private boolean isUsable(PooledClient pooled, String signature) {
		if (pooled == null || !pooled.signature.equals(signature)) {
			return false;
		}
		long ttlMillis = ttlMillis();
		// A non-positive TTL disables expiry.
		return ttlMillis <= 0 || (System.currentTimeMillis() - pooled.createdAt) < ttlMillis;
	}

	private long ttlMillis() {
		long seconds = clientsConfig.getConnectionPoolTtlSeconds();
		return seconds > 0 ? seconds * 1000L : 0L;
	}

	private void evict(String key, PooledClient pooled) {
		if (pooled != null && pool.remove(key, pooled)) {
			connector.closeQuietly(pooled.client);
		}
	}

	private String cacheKey(MCPClientConfig config) {
		String userPart = isPerUser(config.getAuthMode()) ? currentUsername() : SHARED_USER;
		return config.getCode() + "::" + userPart;
	}

	private static boolean isPerUser(McpAuthMode authMode) {
		return authMode == McpAuthMode.OAUTH2_AUTHORIZATION_CODE_PER_USER || authMode == McpAuthMode.USER_TOKEN_RELAY
				|| authMode == McpAuthMode.TOKEN_EXCHANGE;
	}

	private String currentUsername() {
		try {
			UserInfos user = securityService.getCurrentUser();
			return user != null && user.getUsername() != null ? user.getUsername() : ANONYMOUS_USER;
		} catch (Throwable t) {
			return ANONYMOUS_USER;
		}
	}

	private static String signature(MCPClientConfig config) {
		StringBuilder sb = new StringBuilder();
		append(sb, config.getTransportType());
		append(sb, config.getBaseUrl());
		append(sb, config.getMcpEndpoint());
		append(sb, config.getSseEndpoint());
		append(sb, config.getAuthMode());
		append(sb, config.getSecretCode());
		append(sb, config.getOauth2AuthenticatorCode());
		append(sb, config.getStdioCommand());
		append(sb, config.getStdioArgs());
		append(sb, config.getStdioEnvironment());
		Date dateModified = config.getDateModified();
		append(sb, dateModified != null ? dateModified.getTime() : null);
		return sb.toString();
	}

	private static void append(StringBuilder sb, Object value) {
		sb.append(value).append('|');
	}

	@Override
	public void destroy() {
		for (Map.Entry<String, PooledClient> entry : pool.entrySet()) {
			connector.closeQuietly(entry.getValue().client);
		}
		pool.clear();
		locks.clear();
	}

	private static final class PooledClient {
		private final McpSyncClient client;
		private final String signature;
		private final long createdAt;

		private PooledClient(McpSyncClient client, String signature, long createdAt) {
			this.client = client;
			this.signature = signature;
			this.createdAt = createdAt;
		}
	}
}
