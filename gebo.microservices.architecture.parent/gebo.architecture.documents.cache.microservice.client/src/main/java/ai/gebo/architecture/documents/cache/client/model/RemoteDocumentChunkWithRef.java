/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.client.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.IGComponentOriginatedDocument;

/**
 * Jackson-deserializable wire form of {@link IDocumentChunkWithRef}.
 *
 * <p>
 * The interface's own implementation is package-private and lombok-generated
 * (no default constructor), so it cannot be the target of WebClient body
 * decoding. This public bean mirrors the JSON emitted by
 * {@code DocumentsChunkServiceController} — {@code chunk}, {@code documentRef},
 * {@code errorState}, {@code errorMessage} — and is upcast to
 * {@link IDocumentChunkWithRef} by the client. The polymorphic
 * {@code documentRef} is resolved by field deduction between the two concrete
 * document references, exactly as the controller does.
 */
public class RemoteDocumentChunkWithRef implements IDocumentChunkWithRef {

	private DocumentChunk chunk;

	@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
	@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
	private IGComponentOriginatedDocument documentRef;

	private boolean errorState;

	private GUserMessage errorMessage;

	public RemoteDocumentChunkWithRef() {
	}

	@Override
	public DocumentChunk getChunk() {
		return chunk;
	}

	public void setChunk(DocumentChunk chunk) {
		this.chunk = chunk;
	}

	@Override
	public IGComponentOriginatedDocument getDocumentRef() {
		return documentRef;
	}

	public void setDocumentRef(IGComponentOriginatedDocument documentRef) {
		this.documentRef = documentRef;
	}

	@Override
	public boolean isErrorState() {
		return errorState;
	}

	public void setErrorState(boolean errorState) {
		this.errorState = errorState;
	}

	@Override
	public GUserMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(GUserMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
