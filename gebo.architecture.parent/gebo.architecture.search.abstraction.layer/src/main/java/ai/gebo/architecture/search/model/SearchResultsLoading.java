/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.model;

/**
 * How the results a search service found are loaded to be read, once the search
 * has given its pool of candidates.
 */
public enum SearchResultsLoading {
	/**
	 * A system sized and connected to answer (a document management system, a wiki, a
	 * ticketing system): a few results at a time, each within the document timeout.
	 */
	RELIABLE,
	/**
	 * Sites of an open network (the web): the candidates loaded wide, grouped by host
	 * with a per-host policy so no site is overloaded, each page within its deadline and
	 * the whole loading within the loading phase deadline; what was not loaded is told
	 * with its reason (see
	 * {@link ai.gebo.architecture.search.config.OpenNetworkLoadingConfig}).
	 */
	OPEN_NETWORK
}
