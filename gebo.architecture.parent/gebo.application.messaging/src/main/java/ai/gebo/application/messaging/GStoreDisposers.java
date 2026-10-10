/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.model.base.GeboComponentInfo;

/**
 * The components erasing what the ingestion stores when a document, a data source,
 * a project or a knowledge base is deleted: the vectorizator's, the knowledge
 * graph's and the full-text index's. Each one accepts the same deletion payloads
 * ({@code GDeletedProjectEndpointPayload}, {@code GInternalDeletionMessagePayload},
 * {@code GDeletedProjectPayload}, {@code GDeletedKnowledgeBasePayload}).
 *
 * <p>
 * A deletion is addressed to each of them, not broadcast: every sender of a
 * deletion goes through {@link #send}, so a store with an erasure component never
 * misses one. The knowledge graph and the full-text index are optional (Neo4j,
 * OpenSearch): a store whose eraser is not reachable from this node is skipped, as
 * the broker could not route the message to it.
 * </p>
 */
public final class GStoreDisposers {
	private static final Logger LOGGER = LoggerFactory.getLogger(GStoreDisposers.class);

	/** The erasure components of the stores, by module and component id. */
	public static final List<GeboComponentInfo> COMPONENTS = List.of(
			new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
					GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT),
			new GeboComponentInfo(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
					GStandardModulesConstraints.KNOWLEDGE_GRAPH_DISPOSE_COMPONENT),
			new GeboComponentInfo(GStandardModulesConstraints.FULLTEXT_MODULE,
					GStandardModulesConstraints.FULLTEXT_DISPOSE_COMPONENT));

	private GStoreDisposers() {
	}

	/**
	 * Sends a deletion to every store eraser this node can reach.
	 *
	 * @param broker   the broker delivering the messages
	 * @param envelope builds the envelope of the deletion, one per eraser, from its
	 *                 sender and with its payload: the target is set here
	 * @return how many erasers the deletion was sent to
	 */
	public static int send(IGMessageBroker broker, Supplier<GMessageEnvelope<?>> envelope) {
		int sent = 0;
		for (GeboComponentInfo disposer : COMPONENTS) {
			if (!broker.checkReceivingComponentPresent(disposer.getMessagingModuleId(),
					disposer.getMessagingComponentId())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Store eraser " + disposer.getCompleteComponentId()
							+ " is not reachable from this node, deletion not sent there");
				}
				continue;
			}
			final GMessageEnvelope<?> message = envelope.get();
			message.setTargetModule(disposer.getMessagingModuleId());
			message.setTargetComponent(disposer.getMessagingComponentId());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending deletion " + message.getPayloadType() + " to store eraser "
						+ disposer.getCompleteComponentId());
			}
			broker.accept(message);
			sent++;
		}
		return sent;
	}
}
