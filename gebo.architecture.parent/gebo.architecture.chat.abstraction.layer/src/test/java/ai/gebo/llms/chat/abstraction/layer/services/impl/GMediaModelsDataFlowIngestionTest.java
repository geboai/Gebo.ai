/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;

/**
 * The compliance register tells what the ingestion gives to the models: the
 * pictures of the documents to the chat model describing them, when the
 * installation turned descriptions on.
 */
class GMediaModelsDataFlowIngestionTest {
	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> providing(T value) {
		final ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void thePicturesOfTheIngestedDocumentsReachTheModelDescribingThem() {
		final GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setModelTypeCode("openai");
		config.setBaseUrl("https://api.openai.com/v1");
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getConfig()).thenReturn(config);
		final GMediaTextExtractorImpl ingestion = mock(GMediaTextExtractorImpl.class);
		when(ingestion.describesPictures()).thenReturn(true);
		when(ingestion.pictureModel()).thenReturn(model);

		final GMediaModelsDataFlowComponent component = new GMediaModelsDataFlowComponent(providing(null),
				providing(null), providing(null));
		assertNull(component.getDataFlowMetaInfos(), "nothing configured, nothing reported");

		component.setIngestionMediaProvider(providing(ingestion));
		final GDataFlowMetaInfos flow = component.getDataFlowMetaInfos();
		final DataEndpoint input = flow.getDataEndpoints().stream()
				.filter(x -> "media-input-ingestion-pictures".equals(x.getId())).findFirst().orElseThrow();
		assertEquals(List.of(MetaEndpointType.DOCUMENTS), input.getTypes());
		final DataEndpoint reached = flow.getDataEndpoints().stream()
				.filter(x -> "media-model-ingestion-pictures".equals(x.getId())).findFirst().orElseThrow();
		assertEquals("https://api.openai.com/v1", reached.getEndpoint());
		assertTrue(flow.getTransformations().stream()
				.anyMatch(x -> flow.qualifiedId(reached.getId()).equals(x.getDataDestinationId())));
		// recordings are not transcribed here: no such flow
		assertTrue(flow.getDataEndpoints().stream().noneMatch(x -> x.getId().contains("ingestion-recordings")));
	}
}
