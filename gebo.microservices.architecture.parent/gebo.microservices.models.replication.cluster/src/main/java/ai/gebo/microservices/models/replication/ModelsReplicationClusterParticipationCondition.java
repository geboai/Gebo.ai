/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.models.replication;

import java.util.List;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboStandardMicroservices;

/**
 * Matches when the running service should participate in the models-replication
 * cache, i.e. when its own {@code spring.application.name} (normalised) is in the
 * shared participant set — the
 * {@code gebo.microservices.topology.models-replication-participants} override
 * when set, otherwise the built-in
 * {@link GeboStandardMicroservices#DEFAULT_MODELS_REPLICATION_PARTICIPANTS}.
 * <p>
 * Participation is therefore governed by the single shared topology
 * configuration: the same deployment ships one participant set, and only the
 * listed services register the
 * {@link ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider}
 * bean (and hence start the cache). A service absent from the set — or a
 * deployable without a {@code spring.application.name}, such as the monolith or
 * the gateway — does not.
 * <p>
 * The condition resolves the set directly from the {@link Environment} (a
 * condition runs before beans exist), mirroring the
 * {@code GeboModelsReplicationParticipants} bean produced by the topology
 * auto-configuration.
 */
public class ModelsReplicationClusterParticipationCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		String currentId = GeboMicroservice.normalizeName(environment.getProperty("spring.application.name"));
		if (!StringUtils.hasText(currentId)) {
			return false;
		}
		List<String> configured = Binder.get(environment)
				.bind("gebo.microservices.topology.models-replication-participants", Bindable.listOf(String.class))
				.orElse(null);
		List<String> participants = configured != null ? configured
				: GeboStandardMicroservices.DEFAULT_MODELS_REPLICATION_PARTICIPANTS;
		for (String participant : participants) {
			if (currentId.equals(GeboMicroservice.normalizeName(participant))) {
				return true;
			}
		}
		return false;
	}
}
