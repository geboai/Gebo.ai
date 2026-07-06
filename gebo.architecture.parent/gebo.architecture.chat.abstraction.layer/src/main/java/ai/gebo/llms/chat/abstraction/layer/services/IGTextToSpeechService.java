/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.InputStream;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Gebo.ai comment agent
 * 
 * Interface defining the contract for a text to speech service. The service
 * converts text to an audio stream using the default configured text to speech
 * model, decoupled from the chat model architecture.
 */
public interface IGTextToSpeechService {

	/**
	 * Checks whether a default text to speech model is currently configured and
	 * available.
	 * 
	 * @return true if the default text to speech model handler is available,
	 *         false otherwise.
	 */
	public boolean isEnabled();

	/**
	 * Converts the given text to speech using the default configured text to
	 * speech model.
	 *
	 * @param text the text to be converted to speech
	 * @return an InputStream with the resulting audio
	 * @throws LLMConfigException if there is an issue with the model configuration
	 */
	public InputStream speech(String text) throws LLMConfigException;
}
