/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;

/**
 * What the software controlling a model call puts around the tools the call declares,
 * loaded into its {@link GeboToolCalling} ({@code provide(IToolCallbacksDecorator.class, ...)}):
 * the model is given the tools it returns, in place of the ones its configuration and the
 * call enable. An agent asking a person to confirm some of its tools' calls wraps them
 * this way.
 */
@FunctionalInterface
public interface IToolCallbacksDecorator {
	/** The tools of the call as the model is to be given them; never null. */
	List<ToolCallback> decorate(List<ToolCallback> tools);
}
