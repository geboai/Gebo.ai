/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.secrets.config.GeboStaticSecretEntry;
import ai.gebo.secrets.config.GeboStaticSecretsConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGSecretsStaticConfigurationDao;
import tools.jackson.databind.ObjectMapper;

/**
 * {@link IGSecretsStaticConfigurationDao} over {@link GeboStaticSecretsConfig}.
 *
 * <h2>The configuration is validated at startup, not at first read</h2>
 * <p>
 * A missing code, a missing content or the same code declared twice is a
 * configuration error, and a secrets store that half-works is worse than one
 * that refuses to start: the constructor throws, so the deployment fails loudly
 * on the operator's own change rather than hours later, on whichever request
 * happens to need that secret.
 * </p>
 *
 * <h2>Each read gets its own copy</h2>
 * <p>
 * Every entry's content is serialised once, here, and each
 * {@link #findByCode(String)} deserialises that snapshot afresh. The stored-secret
 * path behaves that way for free - it decrypts per call - and callers rely on it:
 * handing out the single bound instance would let one caller's mutation leak into
 * every later read of that secret, for the lifetime of the process. The snapshot
 * also pins the {@code readOnly} marker into the content, so the flag survives
 * the round trip the same way it does for a stored secret.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GSecretsStaticConfigurationDaoImpl extends GAbstractRuntimeConfigurationDao<AbstractGeboSecretContent>
		implements IGSecretsStaticConfigurationDao {

	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * One configured secret, reduced to what a read needs: its metadata and a JSON
	 * snapshot of its content plus the class to read that snapshot back into.
	 */
	private static class ConfiguredSecret {
		private final SecretInfo info;
		private final Class<? extends AbstractGeboSecretContent> contentClass;
		private final String contentJson;

		private ConfiguredSecret(SecretInfo info, Class<? extends AbstractGeboSecretContent> contentClass,
				String contentJson) {
			this.info = info;
			this.contentClass = contentClass;
			this.contentJson = contentJson;
		}

		private AbstractGeboSecretContent content() {
			AbstractGeboSecretContent content = mapper.readValue(contentJson, contentClass);
			// Belt and braces: the snapshot already carries it, but nothing that leaves
			// this DAO may ever be mistaken for a writable secret.
			content.setReadOnly(Boolean.TRUE);
			return content;
		}
	}

	/** Insertion-ordered so that listings follow the configuration's own order. */
	private final Map<String, ConfiguredSecret> byCode;

	public GSecretsStaticConfigurationDaoImpl(GeboStaticSecretsConfig config) {
		super(new ArrayList<>(), null);
		this.byCode = load(config);
		if (byCode.isEmpty()) {
			LOGGER.debug("No secret declared under ai.gebo.secrets.config");
		} else {
			LOGGER.info("Read-only secrets declared under ai.gebo.secrets.config: " + byCode.keySet());
		}
	}

	private Map<String, ConfiguredSecret> load(GeboStaticSecretsConfig config) {
		Map<String, ConfiguredSecret> loaded = new LinkedHashMap<>();
		for (GeboStaticSecretEntry<? extends AbstractGeboSecretContent> entry : config.allEntries()) {
			String code = entry.getCode() == null ? null : entry.getCode().trim();
			if (code == null || code.isEmpty()) {
				throw new IllegalStateException(
						"A secret declared under ai.gebo.secrets.config has no code: " + entry);
			}
			AbstractGeboSecretContent content = entry.getSecret();
			if (content == null) {
				throw new IllegalStateException("The secret declared under ai.gebo.secrets.config with code=>" + code
						+ " has no secret content");
			}
			if (loaded.containsKey(code)) {
				throw new IllegalStateException(
						"The code=>" + code + " is declared more than once under ai.gebo.secrets.config");
			}
			// Set on the instance BEFORE the snapshot is taken, so the marker is part of
			// the JSON every later read is built from.
			content.setReadOnly(Boolean.TRUE);
			GeboSecretType secretType = content.type();
			SecretInfo info = new SecretInfo(code, entry.getDescription(), secretType, entry.getContextCode(),
					Boolean.TRUE);
			loaded.put(code, new ConfiguredSecret(info, content.getClass(), mapper.writeValueAsString(content)));
		}
		return loaded;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Overridden rather than left to {@code GAbstractRuntimeConfigurationDao}: the
	 * contents are rebuilt per call from the snapshots (see the class comment), so
	 * there is no static list to concatenate a dynamic one onto.
	 * </p>
	 */
	@Override
	public List<AbstractGeboSecretContent> getConfigurations() {
		List<AbstractGeboSecretContent> all = new ArrayList<>(byCode.size());
		for (ConfiguredSecret configured : byCode.values()) {
			all.add(configured.content());
		}
		return all;
	}

	@Override
	public AbstractGeboSecretContent findByCode(String code) {
		ConfiguredSecret configured = code == null ? null : byCode.get(code.trim());
		return configured == null ? null : configured.content();
	}

	@Override
	public List<String> getAllSecretsId() {
		return List.copyOf(byCode.keySet());
	}

	@Override
	public boolean isConfiguredCode(String code) {
		return code != null && byCode.containsKey(code.trim());
	}

	@Override
	public SecretInfo findInfoByCode(String code) {
		ConfiguredSecret configured = code == null ? null : byCode.get(code.trim());
		return configured == null ? null : copyOf(configured.info);
	}

	@Override
	public List<SecretInfo> findInfoByContextCode(String contextCode) {
		List<SecretInfo> infos = new ArrayList<>();
		for (ConfiguredSecret configured : byCode.values()) {
			String declared = configured.info.getContextCode();
			if (declared == null ? contextCode == null : declared.equals(contextCode)) {
				infos.add(copyOf(configured.info));
			}
		}
		return infos;
	}

	/**
	 * A {@link SecretInfo} is a mutable bean that reaches REST controllers and the
	 * admin UI; the held one is shared, so it is never handed out directly.
	 */
	private SecretInfo copyOf(SecretInfo info) {
		return new SecretInfo(info.getCode(), info.getDescription(), info.getSecretType(), info.getContextCode(),
				info.getReadOnly());
	}
}
