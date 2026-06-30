/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.repository.GeboMCPServerConfigRepository;
import ai.gebo.architecture.mcpserver.runtime.GeboMcpServerRegistry;
import ai.gebo.architecture.mcpserver.service.IGMCPServerConfigManagerService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IGSecurityService.AclOwnerInfo;
import lombok.AllArgsConstructor;

/**
 * Default implementation of {@link IGMCPServerConfigManagerService}.
 * <p>
 * Persists {@link GeboMCPServerConfig} nodes through the shared
 * {@link IGPersistentObjectManager}, enforces that each {@code exportedUniqueRelativeUrl}
 * is unique and URL-safe, applies ACL grants through {@link IGSecurityService}, and after
 * every mutation refreshes the {@link GeboMcpServerRegistry} so the live {@code mcp/<url>}
 * endpoints track the persisted state without a restart.
 */
@Service
@AllArgsConstructor
public class GMCPServerConfigManagerServiceImpl implements IGMCPServerConfigManagerService {

	/** Relative URL: starts with an alphanumeric, then alphanumerics, '-', '_', '.' or '/'. */
	private static final Pattern URL_PATTERN = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9._/-]*$");

	private final IGPersistentObjectManager persistentObjectManager;
	private final GeboMCPServerConfigRepository repository;
	private final GeboMcpServerRegistry registry;
	private final IGSecurityService securityService;

	@Override
	public GeboMCPServerConfig insert(GeboMCPServerConfig config) throws GeboPersistenceException {
		validate(config);
		GeboMCPServerConfig saved = persistentObjectManager.insert(config);
		registry.reload(saved.getCode());
		return saved;
	}

	@Override
	public GeboMCPServerConfig update(GeboMCPServerConfig config) throws GeboPersistenceException {
		validate(config);
		GeboMCPServerConfig saved = persistentObjectManager.update(config);
		registry.reload(saved.getCode());
		return saved;
	}

	@Override
	public void delete(String code) throws GeboPersistenceException {
		GeboMCPServerConfig existing = findByCode(code);
		if (existing != null) {
			persistentObjectManager.delete(existing);
			registry.remove(code);
		}
	}

	@Override
	public GeboMCPServerConfig findByCode(String code) throws GeboPersistenceException {
		return persistentObjectManager.findById(GeboMCPServerConfig.class, code);
	}

	@Override
	public List<GeboMCPServerConfig> findAll() throws GeboPersistenceException {
		return persistentObjectManager.findAll(GeboMCPServerConfig.class);
	}

	@Override
	public Page<GeboMCPServerConfig> getPagedList(Pageable pageable) throws GeboPersistenceException {
		return persistentObjectManager.findAll(GeboMCPServerConfig.class, pageable);
	}

	@Override
	public GeboMCPServerConfig setAccessAcls(String code, List<AclOwnerInfo> owners) throws GeboPersistenceException {
		GeboMCPServerConfig config = findByCode(code);
		if (config == null) {
			throw new GeboPersistenceException("MCP server configuration '" + code + "' does not exist");
		}
		securityService.setAclAliasesForOwners(config, owners);
		registry.reload(code);
		return findByCode(code);
	}

	/**
	 * Validates the relative URL: present, URL-safe, not ending with a slash, and not
	 * already used by a different configuration.
	 */
	private void validate(GeboMCPServerConfig config) throws GeboPersistenceException {
		String url = config.getExportedUniqueRelativeUrl();
		if (url == null || url.isBlank()) {
			throw new GeboPersistenceException("exportedUniqueRelativeUrl is required");
		}
		if (url.endsWith("/") || !URL_PATTERN.matcher(url).matches()) {
			throw new GeboPersistenceException("exportedUniqueRelativeUrl '" + url
					+ "' is not a valid relative URL (allowed: letters, digits, '-', '_', '.', '/')");
		}
		Optional<GeboMCPServerConfig> existing = repository.findByExportedUniqueRelativeUrl(url);
		if (existing.isPresent() && !existing.get().getCode().equals(config.getCode())) {
			throw new GeboPersistenceException(
					"exportedUniqueRelativeUrl '" + url + "' is already used by another MCP server configuration");
		}
	}
}
