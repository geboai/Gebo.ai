/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;

/**
 * A request context that is another one, every value read from it: the base
 * of the contexts that change one thing of a context and keep the rest. A
 * method added to {@link IChatRequestContext} is forwarded here, once, instead
 * of in every one of them - and a decorator that forgot one would silently
 * answer the interface default in its place.
 */
public abstract class ForwardingChatRequestContext implements IChatRequestContext {
	protected final IChatRequestContext delegate;

	protected ForwardingChatRequestContext(IChatRequestContext delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getRequestID() {
		return delegate.getRequestID();
	}

	@Override
	public String getSessionID() {
		return delegate.getSessionID();
	}

	@Override
	public String getConsolidatedHistory() {
		return delegate.getConsolidatedHistory();
	}

	@Override
	public List<IChatSessionEntry> getInteractions() {
		return delegate.getInteractions();
	}

	@Override
	public List<Document> getDocuments() {
		return delegate.getDocuments();
	}

	@Override
	public String getActualUserRequest() {
		return delegate.getActualUserRequest();
	}

	@Override
	public Map<String, Object> getToolsContext() {
		return delegate.getToolsContext();
	}

	@Override
	public Map<String, Object> getPipelineInfos() {
		return delegate.getPipelineInfos();
	}

	@Override
	public ToolCallsListener getToolCallListener() {
		return delegate.getToolCallListener();
	}

	@Override
	public List<String> getRulesToFollow() {
		return delegate.getRulesToFollow();
	}

	@Override
	public String getUserLanguage() {
		return delegate.getUserLanguage();
	}

	@Override
	public GChatRunInfo getRunInfo() {
		return delegate.getRunInfo();
	}

	@Override
	public Map<String, Object> getRunEnvironment() {
		return delegate.getRunEnvironment();
	}
}
