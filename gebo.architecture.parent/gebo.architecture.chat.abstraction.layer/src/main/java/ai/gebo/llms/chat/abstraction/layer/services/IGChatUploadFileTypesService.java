/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.Set;

import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GChatUploadFileTypes;

/**
 * The files a chat accepts as uploads: the ones the content handlers read, read into
 * the chat, and the ones the libraries of its agents work on, kept for their tools
 * (see {@link ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType#getUploadFileTypes()}).
 */
public interface IGChatUploadFileTypesService {

	/**
	 * The extensions, with the dot and lower case, of the files the libraries of the
	 * agents the chats of a profile are handed to work on; a null profile means a free
	 * chat. Empty when there are no such agents or libraries.
	 */
	public Set<String> toolsFileTypes(GChatProfileConfiguration profile);

	/** The same for a chat session, by its profile; empty for an unknown session. */
	public Set<String> toolsFileTypesOfSession(String userSessionCode);

	/**
	 * What a chat accepts: by its session when there is one, else by the profile it is
	 * being created with (null for a free chat).
	 */
	public GChatUploadFileTypes acceptedFileTypes(String userSessionCode, String chatProfileCode);

	/**
	 * The MIME types of the files the model of a chat takes as they are, see
	 * {@link GChatUploadFileTypes#getModelMimeTypes()}: those of the chat's profile's
	 * model, else of the chat's own model, else of the default one. Empty when the
	 * model gets text only.
	 */
	public java.util.List<String> modelMimeTypesOfSession(String userSessionCode);
}
