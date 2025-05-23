/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.richresponse.model;

/**
 * Gebo.ai comment agent
 * Enum representing different types of rich response fragments that can be used 
 * in a chat abstraction layer for rendering various content types.
 */
public enum RichresponseFragmentType {
	/** Represents content in the format of a programming language */
	ProgrammingLanguageContentType, 
	
	/** Represents content structured in a table format */
	TableContentType, 
	
	/** Represents content with rich HTML text styling */
	RichHtmlTextType, 
	
	/** Represents content with preformatted text, often used for code */
	PreformattedTextType
}