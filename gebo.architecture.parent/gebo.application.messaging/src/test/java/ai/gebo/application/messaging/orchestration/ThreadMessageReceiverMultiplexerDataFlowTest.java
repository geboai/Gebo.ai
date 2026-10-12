/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.orchestration;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Pins that a receiver factory's data-flow report survives its registration in the
 * broker: the broker registers the {@link ThreadMessageReceiverMultiplexer} in the
 * factory's place, so the register reads the report from the multiplexer. Before the
 * multiplexer delegated it, the vectorizator's and the full-text indexer's reports
 * (the vector store, the full-text index) never reached the register.
 */
class ThreadMessageReceiverMultiplexerDataFlowTest {

	/** A receiver factory reporting the given flow, the rest of it inert. */
	private static IGMessageReceiverFactory factory(GDataFlowMetaInfos flow) {
		return new IGMessageReceiverFactory() {
			@Override
			public GDataFlowMetaInfos getDataFlowMetaInfos() {
				return flow;
			}

			@Override
			public String getMessagingModuleId() {
				return "vectorizator-module";
			}

			@Override
			public String getMessagingSystemId() {
				return "vectorization-component";
			}

			@Override
			public SystemComponentType getComponentType() {
				return SystemComponentType.APPLICATION_COMPONENT;
			}

			@Override
			public List<String> getAcceptedPayloadTypes() {
				return List.of();
			}

			@Override
			public int getPoolCardinality() {
				return 0;
			}

			@Override
			public boolean useSenderThread() {
				return true;
			}

			@Override
			public boolean isAcceptEveryPayloadType() {
				return false;
			}

			@Override
			public IGMessageReceiver create() {
				return null;
			}
		};
	}

	/** The receiver run in the sender's thread, which the multiplexer requires when it has no threads. */
	private static IGMessageReceiver backupReceiver() {
		return new IGMessageReceiver() {
			@Override
			public void accept(GMessageEnvelope envelope) {
			}

			@Override
			public String getMessagingModuleId() {
				return "vectorizator-module";
			}

			@Override
			public String getMessagingSystemId() {
				return "vectorization-component";
			}

			@Override
			public SystemComponentType getComponentType() {
				return SystemComponentType.APPLICATION_COMPONENT;
			}

			@Override
			public List<String> getAcceptedPayloadTypes() {
				return List.of();
			}

			@Override
			public boolean isAcceptEveryPayloadType() {
				return false;
			}
		};
	}

	private static ThreadMessageReceiverMultiplexer multiplexerOf(GDataFlowMetaInfos flow) {
		return new ThreadMessageReceiverMultiplexer(factory(flow), List.of(), backupReceiver(),
				(envelope, delivery) -> delivery.run());
	}

	@Test
	void theFactoryReportReachesTheBrokerThroughTheMultiplexer() {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();

		assertSame(flow, multiplexerOf(flow).getDataFlowMetaInfos());
	}

	@Test
	void aFactoryWithNoReportStaysWithout() {
		assertNull(multiplexerOf(null).getDataFlowMetaInfos());
	}
}
