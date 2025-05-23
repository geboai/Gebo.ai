/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a thread-local interaction context for managing knowledge base interactions and function calls.
 * This class contains static nested classes to hold information about called functions and context data.
 * AI generated comments
 */
public class LLMtInteractionContextThreadLocal {

    /**
     * Represents a function that has been called within the interaction context.
     */
    public static class CalledFunction {
        private String functionName = null;
        private String functionDescription = null;
        private List<String> paramsDescription = new ArrayList<String>();

        /**
         * Gets the name of the called function.
         *
         * @return the function name.
         */
        public String getFunctionName() {
            return functionName;
        }

        /**
         * Sets the name of the function that was called.
         *
         * @param functionName the name of the function.
         */
        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        /**
         * Gets the description of the called function.
         *
         * @return the function description.
         */
        public String getFunctionDescription() {
            return functionDescription;
        }

        /**
         * Sets the description for the called function.
         *
         * @param functionDescription a description of the function.
         */
        public void setFunctionDescription(String functionDescription) {
            this.functionDescription = functionDescription;
        }

        /**
         * Gets the descriptions of the parameters used in the function call.
         *
         * @return a list of parameter descriptions.
         */
        public List<String> getParamsDescription() {
            return paramsDescription;
        }

        /**
         * Sets the descriptions for the parameters used in the function call.
         *
         * @param paramsDescription a list of parameter descriptions.
         */
        public void setParamsDescription(List<String> paramsDescription) {
            this.paramsDescription = paramsDescription;
        }
    }

    /**
     * Represents the context of the interaction, including user information, knowledge bases codes,
     * called functions, and a custom environment map.
     */
    public static class KBContext {
        private String actualUser = null;
        private List<String> knowledgeBasesCodes = new ArrayList<String>();
        private List<CalledFunction> calledFunctions = new ArrayList<LLMtInteractionContextThreadLocal.CalledFunction>();
        private Map<String, Object> customEnvironment = new HashMap<String, Object>();
        private String usedEmbeddingSystem = null;

        /**
         * Gets the list of knowledge base codes associated with this context.
         *
         * @return a list of knowledge base codes.
         */
        public List<String> getKnowledgeBasesCodes() {
            return knowledgeBasesCodes;
        }

        /**
         * Sets the list of knowledge base codes for this context.
         *
         * @param knowledgeBasesCodes the list of knowledge base codes.
         */
        public void setKnowledgeBasesCodes(List<String> knowledgeBasesCodes) {
            this.knowledgeBasesCodes = knowledgeBasesCodes;
        }

        /**
         * Gets the system used for embedding in this context.
         *
         * @return the embedding system used.
         */
        public String getUsedEmbeddingSystem() {
            return usedEmbeddingSystem;
        }

        /**
         * Sets the embedding system used in this context.
         *
         * @param usedEmbeddingSystem the embedding system.
         */
        public void setUsedEmbeddingSystem(String usedEmbeddingSystem) {
            this.usedEmbeddingSystem = usedEmbeddingSystem;
        }

        /**
         * Gets the list of functions that have been called in this context.
         *
         * @return a list of called functions.
         */
        public List<CalledFunction> getCalledFunctions() {
            return calledFunctions;
        }

        /**
         * Sets the list of functions that have been called in this context.
         *
         * @param calledFunctions the list of called functions.
         */
        public void setCalledFunctions(List<CalledFunction> calledFunctions) {
            this.calledFunctions = calledFunctions;
        }

        /**
         * Gets the actual user associated with this context.
         *
         * @return the actual user.
         */
        public String getActualUser() {
            return actualUser;
        }

        /**
         * Sets the actual user for this context.
         *
         * @param actualUser the user to set.
         */
        public void setActualUser(String actualUser) {
            this.actualUser = actualUser;
        }

        /**
         * Gets the custom environment variables map.
         *
         * @return a map of custom environment variables.
         */
        public Map<String, Object> getCustomEnvironment() {
            return customEnvironment;
        }

        /**
         * Sets the custom environment variables map.
         *
         * @param customEnvironment a map of custom environment variables.
         */
        public void setCustomEnvironment(Map<String, Object> customEnvironment) {
            this.customEnvironment = customEnvironment;
        }
    }

    // ThreadLocal storage for the KBContext object associated with the current thread
    public static final ThreadLocal<KBContext> Context = new ThreadLocal<>();

}