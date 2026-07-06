/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGTranscriptService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * AI generated comments
 * 
 * Dedicated user level REST controller that exposes the transcript (speech to
 * text) functionalities. It exports the availability check ({@code isEnabled})
 * and the actual transcription endpoint, delegating to
 * {@link IGTranscriptService} which resolves the default configured transcript
 * model.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping(path = "api/users/GeboTranscriptController")
public class GeboTranscriptController {

	/** Service that handles transcript functionality */
	@Autowired
	IGTranscriptService transcriptService;

	/**
	 * Checks whether a default transcript model is configured and available.
	 * 
	 * @return true if the transcript functionality is enabled, false otherwise
	 */
	@GetMapping(value = "isEnabled", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean isEnabled() {
		return transcriptService.isEnabled();
	}

	/**
	 * Simple response class for transcript text.
	 */
	public static class TranscriptResponse {
		public TranscriptResponse(String s) {
			this.text = s;
		}

		public final String text;
	}

	/**
	 * Transcribes the audio from the request input stream to text using the default
	 * configured transcript model.
	 * 
	 * @param request the HTTP request containing the audio stream
	 * @return a response containing the transcribed text
	 * @throws LLMConfigException if there is a configuration issue with the model
	 * @throws IOException        if there is an issue reading the input stream
	 */
	@PostMapping(value = "transcriptText", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public TranscriptResponse transcriptText(HttpServletRequest request) throws LLMConfigException, IOException {
		InputStream is = request.getInputStream();

		return new TranscriptResponse(transcriptService.transcript(is));
	}
}
