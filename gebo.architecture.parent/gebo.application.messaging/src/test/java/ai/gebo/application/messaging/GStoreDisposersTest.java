/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;

/**
 * Pins how a deletion reaches the stores: one envelope addressed to each store
 * eraser this node can reach (the vectorizator's, the knowledge graph's, the
 * full-text index's), none to an eraser that is not deployed.
 */
class GStoreDisposersTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void aDeletionIsAddressedToEveryReachableStoreEraser() {
		IGMessageBroker broker = mock(IGMessageBroker.class);
		when(broker.checkReceivingComponentPresent(anyString(), anyString())).thenReturn(true);

		int sent = GStoreDisposers.send(broker, () -> new GMessageEnvelope());

		assertEquals(3, sent);
		ArgumentCaptor<GMessageEnvelope> sentEnvelopes = ArgumentCaptor.forClass(GMessageEnvelope.class);
		verify(broker, times(3)).accept(sentEnvelopes.capture());
		assertEquals(List.of(
				GStandardModulesConstraints.VECTORIZATOR_MODULE + "." + GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT,
				GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE + "."
						+ GStandardModulesConstraints.KNOWLEDGE_GRAPH_DISPOSE_COMPONENT,
				GStandardModulesConstraints.FULLTEXT_MODULE + "." + GStandardModulesConstraints.FULLTEXT_DISPOSE_COMPONENT),
				sentEnvelopes.getAllValues().stream().map(x -> x.getTargetModule() + "." + x.getTargetComponent())
						.toList());
	}

	@Test
	void anEraserNotDeployedHereGetsNothing() {
		IGMessageBroker broker = mock(IGMessageBroker.class);
		// a semantic only installation: the graph and the full-text index are not deployed
		when(broker.checkReceivingComponentPresent(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT)).thenReturn(true);

		assertEquals(1, GStoreDisposers.send(broker, () -> new GMessageEnvelope<>()));
		verify(broker, times(1)).accept(any());
	}

	@Test
	void withNoEraserNothingIsSent() {
		IGMessageBroker broker = mock(IGMessageBroker.class);

		assertEquals(0, GStoreDisposers.send(broker, () -> new GMessageEnvelope<>()));
		verify(broker, never()).accept(any());
	}
}
