/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.model;

/**
 * AI generated comments
 * Represents a single item in Google search results.
 * This class encapsulates various properties of a search result from Google's search API,
 * including titles, links, and snippets in both plain text and HTML formats.
 */
public class GoogleSearchResultItem {
	/** The type or category of the search result */
	private String kind = null;
	/** The plain text title of the search result */
	private String title = null;
	/** The HTML-formatted title of the search result */
	private String htmlTitle = null;
	/** The URL link to the search result */
	private String link = null;
	/** The displayed text for the link */
	private String displayLink = null;
	/** A short plain text description or excerpt from the search result */
	private String snippet = null;
	/** A short HTML-formatted description or excerpt from the search result */
	private String htmlSnippet = null;

	/**
	 * Gets the kind/type of the search result
	 * @return the kind value
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind/type of the search result
	 * @param kind the kind value to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Gets the plain text title of the search result
	 * @return the title text
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the plain text title of the search result
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the HTML-formatted title of the search result
	 * @return the HTML title
	 */
	public String getHtmlTitle() {
		return htmlTitle;
	}

	/**
	 * Sets the HTML-formatted title of the search result
	 * @param htmlTitle the HTML title to set
	 */
	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}

	/**
	 * Gets the URL link to the search result
	 * @return the URL link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the URL link to the search result
	 * @param link the URL link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Gets the displayed text for the link
	 * @return the display link text
	 */
	public String getDisplayLink() {
		return displayLink;
	}

	/**
	 * Sets the displayed text for the link
	 * @param displayLink the display link text to set
	 */
	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}

	/**
	 * Gets the plain text snippet/description of the search result
	 * @return the snippet text
	 */
	public String getSnippet() {
		return snippet;
	}

	/**
	 * Sets the plain text snippet/description of the search result
	 * @param snippet the snippet text to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	/**
	 * Gets the HTML-formatted snippet/description of the search result
	 * @return the HTML snippet
	 */
	public String getHtmlSnippet() {
		return htmlSnippet;
	}

	/**
	 * Sets the HTML-formatted snippet/description of the search result
	 * @param htmlSnippet the HTML snippet to set
	 */
	public void setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}
}