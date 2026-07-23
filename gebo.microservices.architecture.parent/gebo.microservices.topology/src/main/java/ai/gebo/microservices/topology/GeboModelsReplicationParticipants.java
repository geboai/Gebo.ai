/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The shared set of microservice ids that participate in the LLM
 * models-replication cache, resolved from the shared topology configuration
 * (built-in {@link GeboStandardMicroservices#DEFAULT_MODELS_REPLICATION_PARTICIPANTS}
 * overridable via {@code gebo.microservices.topology.models-replication-participants}).
 * <p>
 * Published as a bean by
 * {@link ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration}
 * so every service reads the <em>same</em> participant set from the single shared
 * source, rather than each carrying its own copy. Ids are stored in the dot-free
 * (underscore) canonical form.
 */
public final class GeboModelsReplicationParticipants {

	private final Set<String> microserviceIds;

	/**
	 * @param ids participant ids (dotted or underscore form; {@code null}/blank
	 *            entries are ignored). Normalised to the canonical underscore form.
	 */
	public GeboModelsReplicationParticipants(Collection<String> ids) {
		Set<String> normalized = new LinkedHashSet<>();
		if (ids != null) {
			for (String id : ids) {
				String canonical = GeboMicroservice.normalizeName(id);
				if (canonical != null && !canonical.isBlank()) {
					normalized.add(canonical);
				}
			}
		}
		this.microserviceIds = Collections.unmodifiableSet(normalized);
	}

	/**
	 * @return the participant microservice ids in canonical (underscore) form
	 */
	public Set<String> microserviceIds() {
		return microserviceIds;
	}

	/**
	 * @param applicationName a microservice name / {@code spring.application.name}
	 *            (dotted or underscore form)
	 * @return {@code true} if that microservice participates in models replication
	 */
	public boolean contains(String applicationName) {
		String canonical = GeboMicroservice.normalizeName(applicationName);
		return canonical != null && microserviceIds.contains(canonical);
	}

	/**
	 * @return {@code true} if no microservice participates (cache disabled
	 *         everywhere)
	 */
	public boolean isEmpty() {
		return microserviceIds.isEmpty();
	}

	@Override
	public String toString() {
		return "GeboModelsReplicationParticipants" + microserviceIds;
	}
}
