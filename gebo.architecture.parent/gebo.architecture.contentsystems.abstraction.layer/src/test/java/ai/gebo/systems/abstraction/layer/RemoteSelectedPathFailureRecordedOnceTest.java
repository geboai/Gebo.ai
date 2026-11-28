/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.systems.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * A selected path that cannot be resolved is one failure: recorded once whether
 * the handler records it before throwing (WebDAV, Confluence, the ticketing
 * handlers) or only throws it (Google Drive), so it is counted and told once.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class RemoteSelectedPathFailureRecordedOnceTest {

	@Test
	void aFailureTheHandlerRecordsBeforeThrowingIsRecordedOnce() throws Exception {
		final GAbstractRemoteVirtualFilesystemConsumingService service = service();
		doAnswer(invocation -> {
			invocation.getArgument(6, IGContentsAccessErrorConsumer.class).accept(ContentsAccessError
					.of(new RuntimeException("refused"), ContentsAccessedObjectType.FOLDER, "the selected item"));
			throw new GeboContentHandlerSystemException("Cannot load this item");
		}).when(service).toNativeCoordinates(any(), any(), any(), any(), any(), any(), any(), any());

		final IGContentsAccessErrorConsumer errors = consumeOneSelectedPath(service);

		assertEquals(1, errors.getErrors().size(), "one failure, one error");
		assertEquals("refused", errors.getErrors().get(0).getMsg(), "the handler's own");
	}

	@Test
	void aFailureTheHandlerOnlyThrowsIsRecordedOnce() throws Exception {
		final GAbstractRemoteVirtualFilesystemConsumingService service = service();
		doThrow(new GeboContentHandlerSystemException("Cannot access")).when(service).toNativeCoordinates(any(),
				any(), any(), any(), any(), any(), any(), any());

		final IGContentsAccessErrorConsumer errors = consumeOneSelectedPath(service);

		assertEquals(1, errors.getErrors().size(), "the loop records it");
		assertEquals("Cannot access", errors.getErrors().get(0).getMsg());
	}

	@Test
	void eachSelectedPathFailingIsRecorded() throws Exception {
		final GAbstractRemoteVirtualFilesystemConsumingService service = service();
		doAnswer(invocation -> {
			invocation.getArgument(6, IGContentsAccessErrorConsumer.class).accept(ContentsAccessError
					.of(new RuntimeException("refused"), ContentsAccessedObjectType.FOLDER, "a selected item"));
			throw new GeboContentHandlerSystemException("Cannot load this item");
		}).when(service).toNativeCoordinates(any(), any(), any(), any(), any(), any(), any(), any());
		final GVirtualFilesystemProjectEndpoint endpoint = new GVirtualFilesystemProjectEndpoint();
		endpoint.setPaths(new ArrayList<VFilesystemReference>(List.of(new VFilesystemReference(), new VFilesystemReference())));
		final IGContentsAccessErrorConsumer errors = IGContentsAccessErrorConsumer.defaultImplementation();

		service.consumeAll(null, endpoint, null, null, mock(IGContentConsumer.class), mock(IGUserMessagesConsumer.class), errors, "module", "system");

		assertEquals(2, errors.getErrors().size(), "one error for each failing path");
	}

	private static GAbstractRemoteVirtualFilesystemConsumingService service() {
		return mock(GAbstractRemoteVirtualFilesystemConsumingService.class,
				withSettings().useConstructor(mock(IGDocumentReferenceFactory.class))
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	private static IGContentsAccessErrorConsumer consumeOneSelectedPath(
			GAbstractRemoteVirtualFilesystemConsumingService service) throws Exception {
		final GVirtualFilesystemProjectEndpoint endpoint = new GVirtualFilesystemProjectEndpoint();
		endpoint.setPaths(new ArrayList<VFilesystemReference>(List.of(new VFilesystemReference())));
		final IGContentsAccessErrorConsumer errors = IGContentsAccessErrorConsumer.defaultImplementation();
		service.consumeAll(null, endpoint, null, null, mock(IGContentConsumer.class), mock(IGUserMessagesConsumer.class), errors, "module", "system");
		return errors;
	}
}
