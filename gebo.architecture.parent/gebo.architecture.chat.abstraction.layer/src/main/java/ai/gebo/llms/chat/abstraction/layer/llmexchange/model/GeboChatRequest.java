/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.List;

import ai.gebo.architecture.ai.model.ITokensCountable;
import lombok.Getter;
import lombok.Setter;

/**
 * Gebo.ai comment agent
 *
 * Represents a chat request specific to the Gebo application that handles
 * templated chat requests. This class extends the functionality of the
 * GeboTemplatedChatRequest for a specific type of data (String).
 */
public class GeboChatRequest extends GeboTemplatedChatRequest<String> implements Cloneable, ITokensCountable {

	/** Additional content attached to the request; the list itself is optional. */
	@Getter
	@Setter
	private List<AdditionalContent> additionalsContent = null;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public int getTokensSize() {

		return getQuery() == null ? 0 : tokensEstimator.estimate(getQuery());
	}
}