/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 * Represents a content holder with a token limit, managing both a generic value
 * and its associated token count.
 * 
 * @param <T> The type of the value being stored.
 */
public class TokenLimitedContent<T> {

    // The value being stored of generic type T.
    private T value = null;

    // The number of tokens associated with the stored value.
    private int NToken = 0;

    /**
     * Retrieves the stored value.
     * 
     * @return the current value of type T.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets a new value to be stored.
     * 
     * @param value The new value to store of type T.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Retrieves the number of tokens for the stored value.
     * 
     * @return the current token count.
     */
    public int getNToken() {
        return NToken;
    }

    /**
     * Sets a new token count for the stored value.
     * 
     * @param nToken The new token count.
     */
    public void setNToken(int nToken) {
        NToken = nToken;
    }
}