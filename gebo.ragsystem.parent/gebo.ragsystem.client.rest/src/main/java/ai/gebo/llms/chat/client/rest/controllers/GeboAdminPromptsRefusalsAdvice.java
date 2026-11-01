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

import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * A prompt the administrator saves can be refused for what it names (a
 * placeholder nobody fills): the refusal is answered as a bad request whose
 * body is an {@link OperationStatus} carrying the reason, the way the editors
 * read a backend's messages, instead of a server error with no explanation.
 */
@RestControllerAdvice(assignableTypes = GeboAdminPromptsController.class)
public class GeboAdminPromptsRefusalsAdvice {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<OperationStatus<Void>> refused(IllegalArgumentException refusal) {
		final OperationStatus<Void> status = OperationStatus.of((Void) null);
		status.getMessages().add(GUserMessage.errorMessage("The prompt cannot be saved", refusal.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
	}
}
