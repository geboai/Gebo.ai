/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Remembers, for each user request, the codes of the search results whose
 * contents the search tools already returned, so a tool called again within the
 * same request (a retry with a refined query, another loop iteration) does not
 * hand the model the same document twice.
 * <p>
 * The state lives in memory: all the tool calls of a request run on the node
 * serving that request. Entries idle for more than {@link #IDLE_TTL_MILLIS} are
 * evicted.
 */
@Service
public class SearchToolsRequestRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchToolsRequestRegistry.class);
	/** A request is long over after an hour without tool calls. */
	static final long IDLE_TTL_MILLIS = 60L * 60L * 1000L;

	private static final class RequestEntry {
		final Set<String> returnedCodes = ConcurrentHashMap.newKeySet();
		volatile long lastAccess = System.currentTimeMillis();
	}

	private final Map<String, RequestEntry> requests = new ConcurrentHashMap<>();

	/**
	 * The codes of the search results already returned for the request, empty when
	 * the request is unknown or null.
	 */
	public Set<String> returnedCodes(String requestId) {
		if (requestId == null) {
			return Set.of();
		}
		RequestEntry entry = requests.get(requestId);
		if (entry == null) {
			return Set.of();
		}
		entry.lastAccess = System.currentTimeMillis();
		return Set.copyOf(entry.returnedCodes);
	}

	/** Records the codes of the search results just returned for the request. */
	public void markReturned(String requestId, Collection<String> codes) {
		if (requestId == null || codes == null || codes.isEmpty()) {
			return;
		}
		evictIdle();
		RequestEntry entry = requests.computeIfAbsent(requestId, id -> new RequestEntry());
		entry.returnedCodes.addAll(codes);
		entry.lastAccess = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("markReturned(...) request:" + requestId + " now counts " + entry.returnedCodes.size()
					+ " returned search result(s), " + requests.size() + " request(s) tracked");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Search result codes returned for request " + requestId + ": " + codes);
		}
	}

	private void evictIdle() {
		final long threshold = System.currentTimeMillis() - IDLE_TTL_MILLIS;
		int evicted = 0;
		for (Iterator<Map.Entry<String, RequestEntry>> iterator = requests.entrySet().iterator(); iterator
				.hasNext();) {
			if (iterator.next().getValue().lastAccess < threshold) {
				iterator.remove();
				evicted++;
			}
		}
		if (evicted > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Evicted " + evicted + " idle request(s) from the search tools registry");
		}
	}
}
