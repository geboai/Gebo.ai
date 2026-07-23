/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload propagated across the cluster whenever a live LLM model client is
 * created, reconfigured or deleted on one instance, so every other instance can
 * apply the same change to its own in-memory (live-connection) client.
 * <p>
 * The change is carried by value: {@link #configJson} holds the serialized model
 * configuration together with its concrete {@link #configClassName}, so a
 * receiving node can rebuild/reconfigure its client deterministically from the
 * event alone, without racing the shared configuration store. For a
 * {@link Operation#DELETE} the config payload is absent and only the
 * {@link #code} is needed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GLlmModelClusterEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The kind of change being propagated. */
	public enum Operation {
		ADD, UPDATE, DELETE
	}

	/** Which model family (and therefore which DAO) this event targets. */
	private GLlmModelClusterCategory category;

	/** The change to apply. */
	private Operation operation;

	/**
	 * Concrete class name of the serialized configuration (used to deserialize
	 * into the correct provider-specific type). {@code null} for
	 * {@link Operation#DELETE}.
	 */
	private String configClassName;

	/** Unique code of the affected model configuration. */
	private String code;

	/**
	 * JSON serialization of the model configuration. {@code null} for
	 * {@link Operation#DELETE}.
	 */
	private String configJson;
}
