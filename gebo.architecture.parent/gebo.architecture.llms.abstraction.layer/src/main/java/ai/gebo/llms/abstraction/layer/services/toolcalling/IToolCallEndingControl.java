/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

/**
 * A control the controlling software loads for a model call (see
 * {@link GeboToolCalling.Builder#provide(Class, Object)}) that ends the call once a
 * tool told it so: the tool calling loop stops at the round the tool ran in, the
 * round's results are the call's answer and the model is not called again, exactly as
 * a tool declared as returning directly ends it (see
 * {@code ToolCallbackDeclarationUtil#returningDirect()}).
 * <p>
 * The difference is who decides. The declaration ends the call whatever the tool
 * returned: a refusal the tool wrote for the model, "give at least two options", is
 * made the call's answer and the model never reads it. A control ends the call only
 * when what the tool told it says so: a tool that declared a wait for a person's answer
 * ends the call, the same tool refusing to ask sends its refusal back to the model as
 * any tool result, and the model corrects itself as the text asks.
 * <p>
 * Read by the manager of a call made with a {@link GeboToolCalling}
 * ({@link GeboToolCallingManager}) after each round of tool calls, never before one.
 * An implementation is loaded under its own contract, the interface the tools call it
 * through, and is found as an ending control by implementing this one as well.
 */
public interface IToolCallEndingControl {
	/**
	 * Whether what the tools told this ends the call: true once a tool told it
	 * something that must stop the work here, false while nothing of the kind was told.
	 */
	public boolean endsTheCall();
}
