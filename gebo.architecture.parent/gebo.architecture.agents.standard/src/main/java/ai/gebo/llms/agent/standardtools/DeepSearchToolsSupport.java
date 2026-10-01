/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;

/**
 * What every deep search tool shares: its collaborators, resolved on use (the tool
 * sources are collected while the chat models are built, and the deep search
 * reaches back to the chat models), and the count of the deep searches of each user
 * request.
 * <p>
 * The counts live in memory: all the tool calls of a request run on the node
 * serving it. Requests idle for more than {@link #IDLE_TTL_MILLIS} are forgotten.
 */
@Service
public class DeepSearchToolsSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchToolsSupport.class);
	/** A request is long over after an hour without deep searches. */
	static final long IDLE_TTL_MILLIS = 60L * 60L * 1000L;
	private final ObjectProvider<DeepSearchToolAnalysis> analysis;
	private final ObjectProvider<IDocumentsChunkService> chunkingService;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDao;
	private final ObjectProvider<GeboRagSearchConfig> ragSearchConfig;
	private final ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService;

	private static final class RequestCount {
		final AtomicInteger deepSearches = new AtomicInteger(0);
		volatile long lastAccess = System.currentTimeMillis();
	}

	private final Map<String, RequestCount> requests = new ConcurrentHashMap<>();

	public DeepSearchToolsSupport(ObjectProvider<DeepSearchToolAnalysis> analysis,
			ObjectProvider<IDocumentsChunkService> chunkingService,
			ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDao,
			ObjectProvider<GeboRagSearchConfig> ragSearchConfig,
			ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService) {
		this.analysis = analysis;
		this.chunkingService = chunkingService;
		this.chatModelsDao = chatModelsDao;
		this.ragSearchConfig = ragSearchConfig;
		this.externalSearchSecurityService = externalSearchSecurityService;
	}

	public DeepSearchToolAnalysis analysis() {
		return analysis.getObject();
	}

	public IDocumentsChunkService chunkingService() {
		return chunkingService.getObject();
	}

	public IGChatModelRuntimeConfigurationDao chatModelsDao() {
		return chatModelsDao.getObject();
	}

	/** The access check every external search shares (search tools, agents, deep search). */
	public IGExternalSearchSecurityService externalSearchSecurityService() {
		return externalSearchSecurityService.getObject();
	}

	/** Most documents a deep search reads, as the deep search pipelines do. */
	public int searchTopK() {
		GeboRagSearchConfig config = ragSearchConfig.getIfAvailable();
		return config != null && config.getDeepSearchGlobalTopK() > 0 ? config.getDeepSearchGlobalTopK() : 30;
	}

	/**
	 * Counts a deep search of the request, returning how many the request made with
	 * this one; 1 when the request is unknown.
	 */
	public int countDeepSearch(String requestId) {
		if (requestId == null) {
			return 1;
		}
		evictIdle();
		RequestCount count = requests.computeIfAbsent(requestId, id -> new RequestCount());
		count.lastAccess = System.currentTimeMillis();
		final int calls = count.deepSearches.incrementAndGet();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countDeepSearch(...) request:" + requestId + " deep search " + calls + ", "
					+ requests.size() + " request(s) tracked");
		}
		return calls;
	}

	private void evictIdle() {
		final long threshold = System.currentTimeMillis() - IDLE_TTL_MILLIS;
		int evicted = 0;
		for (Iterator<Map.Entry<String, RequestCount>> iterator = requests.entrySet().iterator(); iterator
				.hasNext();) {
			if (iterator.next().getValue().lastAccess < threshold) {
				iterator.remove();
				evicted++;
			}
		}
		if (evicted > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Evicted " + evicted + " idle request(s) from the deep search tools counts");
		}
	}
}
