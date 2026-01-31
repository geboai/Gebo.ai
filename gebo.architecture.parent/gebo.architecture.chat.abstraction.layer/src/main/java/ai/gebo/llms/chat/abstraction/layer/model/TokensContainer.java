/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Gebo.ai comment agent Represents a content holder with a token limit,
 * managing both a generic value and its associated token count.
 * 
 * @param <T> The type of the value being stored.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokensContainer<T> implements ITokensCountable {

	// The value being stored of generic type T.
	private T value = null;

	// The number of tokens associated with the stored value.
	private int tokensSize = 0;

	public int getTokensSize() {
		if (value != null && value instanceof ITokensCountable countable) {
			return countable.getTokensSize();
		}
		return tokensSize;
	}

}