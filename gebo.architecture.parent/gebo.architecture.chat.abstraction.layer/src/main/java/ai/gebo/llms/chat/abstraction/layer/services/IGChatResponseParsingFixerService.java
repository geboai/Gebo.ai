/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import org.springframework.ai.chat.model.ChatResponse;

/**
 * Gebo.ai comment agent
 * This interface defines methods for handling and parsing chat response content.
 * It is designed to ensure that responses are valid and to provide fallback content if necessary.
 *
 * @param <ResponseType> the type of response this service handles
 */
public interface IGChatResponseParsingFixerService<ResponseType> {

    /**
     * Returns the class type that this service can handle.
     *
     * @return the Class object representing the handled type
     */
    public Class<ResponseType> getHandledType();

    /**
     * Checks if the content of the given response is valid.
     *
     * @param response the response to check for content validity
     * @return true if the content is valid, false otherwise
     */
    public boolean contentOk(ResponseType response);

    /**
     * Provides fallback content in case the original response content is not valid.
     *
     * @param response the original chat response that needs a fallback
     * @return a response of type ResponseType that serves as a fallback
     * @throws GeboChatException if an error occurs while creating fallback content
     */
    public ResponseType getFallBackContent(ChatResponse response) throws GeboChatException;

}