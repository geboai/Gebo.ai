/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The progress a long LLM work (e.g. a token budgeted map/reduce over many
 * documents) reports while it runs, independently of where it ends up: the chat
 * UI of a pipeline, an agent's notifications, or nowhere when the work runs inside
 * a tool.
 */
public interface IGProgressNotifier {
	/**
	 * Notifies a transient progress of the work.
	 *
	 * @param code    identifies the progress kind, so a new one can replace an older
	 *                one of the same kind
	 * @param message what is going on, for the user
	 */
	public void notifyProgress(String code, String message);

	/** Notifies that the LLM provider failed while doing the work. */
	public void notifyLLMProblems();

	/** A notifier reporting nothing but the logs. */
	public static final IGProgressNotifier NONE = new IGProgressNotifier() {
		private static final Logger LOGGER = LoggerFactory.getLogger(IGProgressNotifier.class);

		@Override
		public void notifyProgress(String code, String message) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Progress " + code + ": " + message);
			}
		}

		@Override
		public void notifyLLMProblems() {
			LOGGER.warn("The LLM provider failed while doing a long work with no one to notify");
		}
	};
}
