/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.restexceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.model.GUserMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * GeboRestResponseStatusExceptionResolver is a custom exception handler
 * for the web MVC subsystem, converting exceptions into HTTP responses
 * with the appropriate status code and JSON error message.
 * 
 * @author AI generated comments
 */
@Component
public class GeboRestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboRestResponseStatusExceptionResolver.class);

	/**
	 * Default constructor for GeboRestResponseStatusExceptionResolver.
	 */
	public GeboRestResponseStatusExceptionResolver() {

	}

	/**
	 * Prepares the response for the given exception by setting the status code
	 * and adding a custom error message to the response headers.
	 * 
	 * @param ex the exception that occurred
	 * @param response the HttpServletResponse to be modified
	 */
	@Override
	protected void prepareResponse(Exception ex, HttpServletResponse response) {
		// Set the HTTP response status to 500 (Internal Server Error)
		response.setStatus(500);
		ObjectMapper mapper = new ObjectMapper();

		// Create a GUserMessage object containing error details
		GUserMessage message = GUserMessage
				.errorMessage("Exception happened in backend, please report to your IT manager", ex);
		try {
			// Convert GUserMessage into a JSON string for the response header
			String header = mapper.writeValueAsString(message);
			response.setHeader("geboCustomError", header);
			
		} catch (Throwable e) {
			// Log error if any issue occurs during header preparation
			LOGGER.error("Exception in preparing response", e);
		}
	}

	/**
	 * Resolves exceptions by logging the error and adjusting the response.
	 * 
	 * @param request the HttpServletRequest during which the exception occurred
	 * @param response the HttpServletResponse to be modified
	 * @param handler the executed handler, or null if none chosen at the time of the exception
	 * @param ex the exception that occurred
	 * @return a ModelAndView to render, or null if handled directly
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// Log the exception using logger
		LOGGER.error("Exception in webmvc subsystem", ex);
		// Set the HTTP response status to 500 (Internal Server Error)
		response.setStatus(500);
		// Return null as the exception has been handled in the response
		return null;
	}

}