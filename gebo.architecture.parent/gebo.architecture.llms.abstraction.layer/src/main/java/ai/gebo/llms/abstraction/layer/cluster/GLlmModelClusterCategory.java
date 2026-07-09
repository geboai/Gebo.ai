/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

/**
 * Identifies the family of runtime LLM model configuration DAO a cluster event
 * targets. It lets a receiving node dispatch a propagated
 * insertion/update/deletion to the correct
 * {@link ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao}
 * implementation without having to resolve it from the serialized payload type.
 */
public enum GLlmModelClusterCategory {
	CHAT, EMBEDDING, RANKER, TEXT_TO_SPEECH, TRANSCRIPT, IMAGE;
}
