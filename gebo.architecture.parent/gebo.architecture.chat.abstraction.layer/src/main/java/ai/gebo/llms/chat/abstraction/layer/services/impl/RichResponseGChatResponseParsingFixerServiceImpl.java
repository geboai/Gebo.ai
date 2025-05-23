/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.richresponse.model.PreformattedText;
import ai.gebo.llms.chat.abstraction.layer.richresponse.model.RichResponse;
import ai.gebo.llms.chat.abstraction.layer.richresponse.model.RichresponseFragment;
import ai.gebo.llms.chat.abstraction.layer.richresponse.model.RichresponseFragmentType;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerService;

/**
 * RichResponseGChatResponseParsingFixerServiceImpl is an implementation of
 * IGChatResponseParsingFixerService that handles parsing of RichResponse 
 * types for GChat responses.
 * <p>
 * Gebo.ai comment agent
 */
@Service
public class RichResponseGChatResponseParsingFixerServiceImpl
		implements IGChatResponseParsingFixerService<RichResponse> {

	/**
	 * Default constructor for RichResponseGChatResponseParsingFixerServiceImpl.
	 */
	public RichResponseGChatResponseParsingFixerServiceImpl() {

	}

	/**
	 * Returns the class type that this service can handle.
	 * 
	 * @return Class of type RichResponse that this service can handle.
	 */
	@Override
	public Class<RichResponse> getHandledType() {
		return RichResponse.class;
	}

	/**
	 * Determines if the given RichResponse contains valid content.
	 * Checks if there is at least one fragment with meaningful content.
	 * 
	 * @param response The RichResponse to check.
	 * @return true if the response contains at least one valid fragment, otherwise false.
	 */
	@Override
	public boolean contentOk(RichResponse response) {
		boolean atLeastOneContent = false;
		if (response != null && response.getFragments() != null && !response.getFragments().isEmpty()) {
			// Iterate through each fragment to check for content
			for (RichresponseFragment f : response.getFragments()) {
				atLeastOneContent = atLeastOneContent || (f.getPreformattedText() != null)
						|| f.getProgrammingLanguageContent() != null || f.getRichHtmlText() != null
						|| f.getTableContent() != null;
			}
		}
		return atLeastOneContent;
	}

	/**
	 * Provides a fallback RichResponse content for a given ChatResponse.
	 * Creates a new RichResponse with a single fragment containing preformatted text.
	 * 
	 * @param response The ChatResponse to generate fallback content from.
	 * @return A RichResponse with preformatted text based on the ChatResponse.
	 * @throws GeboChatException If an error occurs during processing.
	 */
	@Override
	public RichResponse getFallBackContent(ChatResponse response) throws GeboChatException {
		RichResponse richResponse = new RichResponse();
		richResponse.getFragments().add(new RichresponseFragment());
		richResponse.getFragments().get(0).setFragmentType(RichresponseFragmentType.PreformattedTextType);
		richResponse.getFragments().get(0).setPreformattedText(new PreformattedText());
		richResponse.getFragments().get(0).getPreformattedText().setText(response.getResult().getOutput().getText());
		return richResponse;
	}

}