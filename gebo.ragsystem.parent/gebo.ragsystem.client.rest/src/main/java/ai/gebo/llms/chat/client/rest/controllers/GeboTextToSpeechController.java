/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGTextToSpeechService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * Dedicated user level REST controller that exposes the text to speech
 * functionalities. It exports the availability check ({@code isEnabled}) and the
 * actual text to speech conversion endpoint, delegating to
 * {@link IGTextToSpeechService} which resolves the default configured text to
 * speech model.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping(path = "api/users/GeboTextToSpeechController")
public class GeboTextToSpeechController {

	/** Service that handles text to speech functionality */
	@Autowired
	IGTextToSpeechService textToSpeechService;

	/**
	 * Checks whether a default text to speech model is configured and available.
	 * 
	 * @return true if the text to speech functionality is enabled, false otherwise
	 */
	@GetMapping(value = "isEnabled", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean isEnabled() {
		return textToSpeechService.isEnabled();
	}

	/**
	 * Request class for text to speech conversion.
	 */
	public static class SpeechRequest {
		@NotNull
		public String text = null;
	}

	/**
	 * Converts the given text to speech audio using the default configured text to
	 * speech model.
	 * 
	 * @param sr the request containing the text to convert
	 * @return an input stream resource containing the audio data
	 * @throws LLMConfigException if there is a configuration issue with the model
	 */
	@PostMapping(value = "speechText", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InputStreamResource speechText(@RequestBody @Valid SpeechRequest sr) throws LLMConfigException {
		InputStream is = textToSpeechService.speech(sr.text);
		InputStreamResource resource = new InputStreamResource(is);
		return resource;
	}
}
