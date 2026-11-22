/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.mcpclients.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.ai.files.GConversationFilesKeeper;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.architecture.ai.files.IGConversationGeneratedFilesStore;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * What a remote tool returns besides text is kept in the conversation and named to
 * the model, not flattened to a placeholder.
 */
class McpRemoteToolResultFilesTest {
	private final List<Object[]> stored = new ArrayList<>();

	@SuppressWarnings("unchecked")
	private McpRemoteToolCallback callback(boolean withStore) {
		final IGConversationGeneratedFilesStore store = (session, name, mimeType, data) -> {
			stored.add(new Object[] { session, name, mimeType, data });
			return new GLibraryFile(IGConversationGeneratedFilesStore.LOCATION_ID,
					name.contains(".") ? name : name + IGConversationGeneratedFilesStore.extensionOf(mimeType),
					data.length, "now", "1", mimeType);
		};
		final ObjectProvider<IGConversationGeneratedFilesStore> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(withStore ? store : null);
		return new McpRemoteToolCallback(null, new MCPClientConfig(), "srv_chart", "chart", "draws a chart", null,
				provider);
	}

	private static String base64(byte... bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	@Test
	void aPictureAToolReturnsIsKeptInTheConversationAndNamedToTheModel() {
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		final ToolContext context = new ToolContext(Map.of(ToolCallbackDeclarationUtil.CHAT_SESSION_CONTEXT_KEY,
				"chat-1", IGConversationFilesKeeper.TOOLS_CONTEXT_KEY, keeper));
		final McpSchema.CallToolResult result = McpSchema.CallToolResult.builder()
				.addTextContent("Here is the chart. ")
				.addContent(new McpSchema.ImageContent(null, base64((byte) 1, (byte) 2, (byte) 3), "image/png"))
				.addContent(new McpSchema.EmbeddedResource(null, new McpSchema.BlobResourceContents(
						"file:///reports/q3.pdf", "application/pdf", base64((byte) 9))))
				.build();

		final String rendered = callback(true).renderResult(result, context);

		assertEquals("Here is the chart. [image kept in this conversation as generated:srv_chart-image-1.png"
				+ " (image/png, 3 bytes)][file kept in this conversation as generated:q3.pdf (application/pdf, 1 bytes)]",
				rendered);
		assertEquals(2, stored.size());
		assertEquals("chat-1", stored.get(0)[0]);
		assertArrayEquals(new byte[] { 1, 2, 3 }, (byte[]) stored.get(0)[3]);
		assertEquals(List.of("generated:srv_chart-image-1.png", "generated:q3.pdf"),
				keeper.getWritten().stream().map(file -> file.key()).toList(),
				"the answer lists them among the files the request wrote");
	}

	@Test
	void outsideAConversationAFileIsNamedByItsKindAndType() {
		final McpSchema.CallToolResult result = McpSchema.CallToolResult.builder()
				.addContent(new McpSchema.ImageContent(null, base64((byte) 1), "image/png")).build();
		assertEquals("[image image/png content]", callback(true).renderResult(result, null));
		assertEquals("[image image/png content]", callback(false).renderResult(result,
				new ToolContext(Map.of(ToolCallbackDeclarationUtil.CHAT_SESSION_CONTEXT_KEY, "chat-1"))));
		assertTrue(stored.isEmpty());
	}

	@Test
	void theStructuredContentOfAResultWithoutTextIsGivenAsJson() {
		final McpSchema.CallToolResult structured = McpSchema.CallToolResult.builder()
				.structuredContent(Map.of("total", 42)).build();
		assertEquals("{\"total\":42}", callback(true).renderResult(structured, null));
		final McpSchema.CallToolResult both = McpSchema.CallToolResult.builder().addTextContent("total 42")
				.structuredContent(Map.of("total", 42)).build();
		assertEquals("total 42", callback(true).renderResult(both, null), "the text already says it");
		final McpSchema.CallToolResult failed = McpSchema.CallToolResult.builder().addTextContent("no data")
				.isError(true).build();
		assertEquals("ERROR: no data", callback(true).renderResult(failed, null));
	}

	@Test
	void aPictureIsShownToAModelThatTakesPictures() {
		final List<String> shown = new ArrayList<>();
		final ai.gebo.architecture.ai.files.IGToolShownFiles viewer = (name, mimeType, data) -> {
			shown.add(name + " " + mimeType + " " + data.length);
			return mimeType.startsWith("image/");
		};
		final ToolContext context = new ToolContext(Map.of(ToolCallbackDeclarationUtil.CHAT_SESSION_CONTEXT_KEY,
				"chat-1", ai.gebo.architecture.ai.files.IGToolShownFiles.TOOLS_CONTEXT_KEY, viewer));
		final McpSchema.CallToolResult result = McpSchema.CallToolResult.builder()
				.addContent(new McpSchema.ImageContent(null, base64((byte) 1, (byte) 2), "image/png"))
				.addContent(new McpSchema.AudioContent(null, base64((byte) 3), "audio/wav")).build();

		final String rendered = callback(true).renderResult(result, context);

		assertEquals(List.of("srv_chart-image-1 image/png 2", "srv_chart-audio-2 audio/wav 1"), shown);
		assertEquals("[image kept in this conversation as generated:srv_chart-image-1.png (image/png, 2 bytes)"
				+ "; it is shown to you in the next message]"
				+ "[audio kept in this conversation as generated:srv_chart-audio-2.wav (audio/wav, 1 bytes)]", rendered);
	}
}
