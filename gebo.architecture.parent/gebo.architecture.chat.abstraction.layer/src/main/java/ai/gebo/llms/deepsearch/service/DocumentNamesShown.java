/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;

/**
 * The names of the documents a search or an analysis works on, as the user is
 * told them in its progress: the first {@value #MAX_NAMES_SHOWN}, in the order
 * found, then how many more. The same for the documents of the knowledge base and
 * the ones of an external source (web, SharePoint, Confluence...): a web page by
 * its title, else its complete address without the request parameters.
 */
public final class DocumentNamesShown {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentNamesShown.class);
	/** Reads the search result stored with an external fragment, its unknown fields ignored. */
	private static final ObjectMapper JSON = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	/** The names shown in a progress message, the others counted. */
	public static final int MAX_NAMES_SHOWN = 3;

	private DocumentNamesShown() {
	}

	/**
	 * The name a fragment's document is told by: a fragment of an external search
	 * result as the result is (see {@link #nameOf(SearchResult)}), any other by its
	 * file name, else its title, else its address, else its code; null when it has
	 * none.
	 */
	public static String nameOf(Map<String, Object> metadata) {
		if (metadata == null) {
			return null;
		}
		final String ofResult = nameOf(searchResultOf(metadata));
		if (ofResult != null) {
			return ofResult;
		}
		final String fileName = text(metadata.get(DocumentMetaInfos.GEBO_FILE_NAME));
		if (fileName != null) {
			return fileName;
		}
		final String title = text(ExtractedDocumentMetaData.of(metadata).getTitle());
		if (title != null) {
			return title;
		}
		final String url = text(metadata.get(DocumentMetaInfos.CONTENT_ORIGINAL_URL));
		return url != null ? withoutParameters(url) : text(metadata.get(DocumentMetaInfos.CONTENT_CODE));
	}

	/**
	 * The name a search result is told by: its title, else its name unless it is
	 * only the host of its address (as a web search gives it), else its complete
	 * address without the request parameters, else the name of its path; null when it
	 * has none.
	 */
	public static String nameOf(SearchResult result) {
		if (result == null) {
			return null;
		}
		final SearchResultReference reference = result.getResultReference();
		final String url = reference != null ? text(reference.getUri()) : null;
		if (reference != null) {
			final String title = text(reference.getTitle());
			if (title != null) {
				return title;
			}
			final String name = text(reference.getName());
			if (name != null && !isHostOf(name, url)) {
				return name;
			}
		}
		if (url != null) {
			return withoutParameters(url);
		}
		final String pathName = result.getNavigationReference() != null
				&& result.getNavigationReference().path != null ? text(result.getNavigationReference().path.name)
						: null;
		return pathName != null ? pathName : reference != null ? text(reference.getName()) : null;
	}

	/** The search result a fragment of an external source was loaded from, null when none. */
	private static SearchResult searchResultOf(Map<String, Object> metadata) {
		final String json = text(metadata.get(DocumentMetaInfos.GEBO_EXTERNAL_SEARCH_RESULT_JSON));
		if (json == null) {
			return null;
		}
		try {
			return JSON.readValue(json, SearchResult.class);
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search result of a fragment not readable, named by its metadata: " + e.getMessage());
			}
			return null;
		}
	}

	/** Whether a name is only the host of the address (with or without www.). */
	static boolean isHostOf(String name, String url) {
		if (url == null) {
			return false;
		}
		try {
			final String host = URI.create(withoutParameters(url)).getHost();
			if (host == null) {
				return false;
			}
			final String bare = host.startsWith("www.") ? host.substring(4) : host;
			final String named = name.toLowerCase();
			return named.equals(host.toLowerCase()) || named.equals(bare.toLowerCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/** The complete address without its request parameters (nor its fragment). */
	static String withoutParameters(String url) {
		int end = url.length();
		final int query = url.indexOf('?');
		if (query >= 0) {
			end = query;
		}
		final int fragment = url.indexOf('#');
		if (fragment >= 0 && fragment < end) {
			end = fragment;
		}
		return url.substring(0, end);
	}

	/**
	 * The names of the documents of these fragments, shown: null when none has a
	 * name.
	 */
	public static String ofFragments(Collection<Document> fragments) {
		final List<String> names = new ArrayList<>();
		if (fragments != null) {
			for (Document fragment : fragments) {
				names.add(fragment != null ? nameOf(fragment.getMetadata()) : null);
			}
		}
		return shown(names);
	}

	/**
	 * The names shown: the distinct ones, the first {@value #MAX_NAMES_SHOWN} whole,
	 * in quotes on one line, then "and N more"; null when there is none.
	 */
	public static String shown(Collection<String> names) {
		final Set<String> distinct = new LinkedHashSet<>();
		if (names != null) {
			for (String name : names) {
				final String clean = text(name);
				if (clean != null) {
					distinct.add(clean);
				}
			}
		}
		if (distinct.isEmpty()) {
			return null;
		}
		final StringBuilder shown = new StringBuilder();
		int count = 0;
		for (String name : distinct) {
			if (count == MAX_NAMES_SHOWN) {
				break;
			}
			if (count > 0) {
				shown.append(", ");
			}
			shown.append('"').append(name).append('"');
			count++;
		}
		if (distinct.size() > MAX_NAMES_SHOWN) {
			shown.append(" and ").append(distinct.size() - MAX_NAMES_SHOWN).append(" more");
		}
		return shown.toString();
	}

	/** A value as one line of text, null when blank. */
	private static String text(Object value) {
		if (value == null) {
			return null;
		}
		final String text = value.toString().replaceAll("\\s+", " ").trim();
		return text.isEmpty() ? null : text;
	}
}
