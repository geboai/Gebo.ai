/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingInfo;
import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingUpdate;
import ai.gebo.llms.chat.pipelines.model.PipelineToolingInfo;
import ai.gebo.llms.chat.pipelines.service.IPipelineToolingAdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/** The admin's configuration of the tools of the chat pipelines' steps. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminChatPipelinesToolingController")
@AllArgsConstructor
public class GeboAdminChatPipelinesToolingController {
	private final IPipelineToolingAdminService toolingService;

	@GetMapping(value = "getPipelinesTooling", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PipelineToolingInfo> getPipelinesTooling() {
		return toolingService.getPipelinesTooling();
	}

	@PostMapping(value = "saveStepTooling", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PipelineStepToolingInfo saveStepTooling(@Valid @RequestBody PipelineStepToolingUpdate update) {
		try {
			return toolingService.saveStepTooling(update);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
