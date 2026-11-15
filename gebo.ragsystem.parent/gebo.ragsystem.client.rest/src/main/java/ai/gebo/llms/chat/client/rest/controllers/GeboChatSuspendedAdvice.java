/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.client.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSuspendedException;

/**
 * A conversation waiting for the answer to a suspended network of agents refuses
 * a normal interaction (see {@link GeboChatSuspendedException}): the client gets
 * a conflict with the pending state, to show the question again and answer it
 * through the respawn endpoints.
 */
@RestControllerAdvice(assignableTypes = GeboChatPipelinesController.class)
public class GeboChatSuspendedAdvice {
	@ExceptionHandler(GeboChatSuspendedException.class)
	public ResponseEntity<NetworkOfAgentsSuspendedState> suspended(GeboChatSuspendedException waiting) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(waiting.getPendingState());
	}
}
