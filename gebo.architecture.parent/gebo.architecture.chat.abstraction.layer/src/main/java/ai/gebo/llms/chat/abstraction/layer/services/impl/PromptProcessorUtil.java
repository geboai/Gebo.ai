/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.ai.chat.prompt.PromptTemplate;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;

/**
 * Utility class for processing and creating prompt templates.
 * Provides methods to process prompts with custom configurations and generate prompt templates.
 * 
 * Gebo.ai comment agent
 */
public class PromptProcessorUtil {

    /**
     * Processes the given prompt configuration to generate a formatted prompt string.
     *
     * @param c The prompt configuration object containing prompt details.
     * @return A processed prompt string.
     */
    public final static String processPrompt(GPromptConfig c) {
        String prompt = c.getPrompt();
        // The following commented code can be used for handling specific formatting logic 
        /*
        if (prompt.contains("{format}"))
            return prompt;
        int firstThreeQuotes = prompt.indexOf("\"\"\"");
        if (firstThreeQuotes < 0) {
            prompt = "\"\"\"{format}\n" + prompt + "\"\"\"";
        } else {
            prompt = prompt.substring(0, firstThreeQuotes + 3) + "{format}\n" + prompt.substring(firstThreeQuotes + 4);
        }*/
        return prompt;
    }

    /**
     * Creates a {@link PromptTemplate} using the processed prompt from the given configuration.
     *
     * @param c The prompt configuration object.
     * @return A newly created prompt template.
     */
    public final PromptTemplate createPrompt(GPromptConfig c) {
        return new PromptTemplate(processPrompt(c));
    }
}