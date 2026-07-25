/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.hierarchy.local;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseHierarchyLookupService;

/**
 * The {@link IGKnowledgeBaseHierarchyLookupService} of a service that <b>owns</b>
 * the GProject/GKnowledgeBase store: it reads Mongo directly, through
 * {@link IGPersistentObjectManager}. This is brain.gebo.ai and the monolith.
 *
 * <p>
 * It deliberately does <b>not</b> live in
 * {@code gebo.architecture.contentsystems.abstraction.layer}: that module is on
 * every content-handler service's classpath (it hosts
 * {@code GeboIngestionManager}, {@code JobLauncherController}, ...), so an
 * implementation shipped inside it would be present everywhere, would always win
 * the {@code @ConditionalOnMissingBean} race, and the remote client could never
 * activate anywhere.
 * </p>
 *
 * <h2>Caching, even though this is the local, owning side</h2>
 * <p>
 * {@code GeboIngestionManager} and {@code GAbstractContentManagementSystemHandler}
 * resolve the SAME project (and, transitively, its knowledge base) once per
 * document/job on this hot path, and neither result changes within a session of
 * that work. A bounded, short-lived cache trades a small amount of staleness after
 * an admin edits a project/knowledge base (visible after the TTL, same trade-off
 * the security/secrets/acl cluster clients already make - see
 * {@code GeboTtlCache}) for skipping a Mongo round trip on every one of those
 * calls. Kept self-contained here rather than reusing that class: it lives in
 * {@code gebo.microservices.cluster.commons}, a microservices-only module this one
 * must not depend on, since the monolith packages this module too.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class LocalKnowledgeBaseHierarchyLookupService implements IGKnowledgeBaseHierarchyLookupService {

	private final IGPersistentObjectManager persistentObjectManager;
	private final long ttlMillis;
	private final int maxEntries;
	private final Map<String, Entry> cache = new ConcurrentHashMap<>();

	/**
	 * @param persistentObjectManager the local persistence access
	 * @param cacheTtl how long a lookup is cached, keyed by code; {@code null} or
	 *            non-positive disables caching entirely
	 * @param cacheMaxEntries hard bound on cached entries; past it the cache is
	 *            emptied rather than grown
	 */
	public LocalKnowledgeBaseHierarchyLookupService(IGPersistentObjectManager persistentObjectManager,
			Duration cacheTtl, int cacheMaxEntries) {
		this.persistentObjectManager = persistentObjectManager;
		this.ttlMillis = cacheTtl == null ? 0L : Math.max(0L, cacheTtl.toMillis());
		this.maxEntries = Math.max(1, cacheMaxEntries);
	}

	@Override
	public GProject findProjectByCode(String code) throws GeboPersistenceException {
		return code == null ? null
				: get("project:" + code, () -> persistentObjectManager.findById(GProject.class, code));
	}

	@Override
	public GKnowledgeBase findKnowledgeBaseByCode(String code) throws GeboPersistenceException {
		return code == null ? null
				: get("knowledgeBase:" + code, () -> persistentObjectManager.findById(GKnowledgeBase.class, code));
	}

	@SuppressWarnings("unchecked")
	private <T> T get(String key, ThrowingSupplier<T> loader) throws GeboPersistenceException {
		if (ttlMillis <= 0) {
			return loader.get();
		}
		long now = System.currentTimeMillis();
		Entry entry = cache.get(key);
		if (entry != null && entry.expiresAt > now) {
			return (T) entry.value;
		}
		T value = loader.get();
		if (cache.size() >= maxEntries) {
			cache.clear();
		}
		cache.put(key, new Entry(value, now + ttlMillis));
		return value;
	}

	@FunctionalInterface
	private interface ThrowingSupplier<T> {
		T get() throws GeboPersistenceException;
	}

	private static final class Entry {
		final Object value;
		final long expiresAt;

		Entry(Object value, long expiresAt) {
			this.value = value;
			this.expiresAt = expiresAt;
		}
	}
}
