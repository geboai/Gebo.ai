/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.io.InputStream;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Gebo.ai comment agent
 * 
 * Interface defining the contract for a transcript service. The service
 * transcribes audio to text using the default configured transcript model,
 * decoupled from the chat model architecture.
 */
public interface IGTranscriptService {

	/**
	 * Checks whether a default transcript model is currently configured and
	 * available.
	 * 
	 * @return true if the default transcript model handler is available, false
	 *         otherwise.
	 */
	public boolean isEnabled();

	/**
	 * Transcribes the audio from the given input stream to text using the default
	 * configured transcript model.
	 *
	 * @param is the input stream containing the audio to transcribe
	 * @return a String containing the transcribed text
	 * @throws LLMConfigException if there is an issue with the model configuration
	 * @throws IOException         if there is an issue reading the input stream
	 */
	public String transcript(InputStream is) throws LLMConfigException, IOException;
}
