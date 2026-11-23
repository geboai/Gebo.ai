/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.a2aproject.sdk.spec.FilePart;
import org.a2aproject.sdk.spec.FileWithBytes;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.runtime.A2AInboundResources;
import ai.gebo.architecture.a2aserver.runtime.A2AResultAdapter;
import ai.gebo.architecture.a2aserver.runtime.A2ATaskBridge;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.runtime.GeboMCPAgentsNetworkAsToolsProvider;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.agent.standardlibraries.WorkingDirectoryLibrarySource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Certifies that the files a run wrote come back to who asked for the run from outside,
 * over the real path: a network of agents runs in a conversation of a chat profile, one
 * of its agents writes a file with a tool of the working directory, and the answer an
 * MCP caller and an A2A client read carries that file, once, with the conversation it
 * happened in.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
public class RunFilesInMcpAndA2AAnswersIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	private static final String REQUEST = "GEBO_TEST_RUN_FILES_REQUEST: write the note of the meeting in a file.";
	private static final String FILE = "meeting-note.md";
	private static final String CONTENT = "# Meeting\nAll agreed.";
	private static final String ANSWER = "GEBO_TEST_RUN_FILES_ANSWER: the note of the meeting is written.";

	@Autowired
	private GeboMCPAgentsNetworkAsToolsProvider networkToolsProvider;
	@Autowired
	private A2ATaskBridge taskBridge;
	@Autowired
	private A2AResultAdapter resultAdapter;

	/** The code of the chat profile of the test, given when it is stored. */
	private String profileCode;
	/** Whether the model already asked for the file to be written, in the run under way. */
	private final AtomicBoolean asked = new AtomicBoolean(false);
	/** The tools the model was offered by the calls carrying the request, as it reads them. */
	private final Set<String> offered = ConcurrentHashMap.newKeySet();
	/** The name of the tool writing a file among the ones of the call under way. */
	private final ThreadLocal<String> writeTool = new ThreadLocal<String>();

	@Override
	protected void beforeEachCallback() throws Exception {
		// the chat profile the exported network runs with: its conversations are of this profile
		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		// a profile of its own for each test: a conversation takes its code from its profile's name
		profile.setDescription("Run files test profile " + java.util.UUID.randomUUID().toString().substring(0, 8));
		profile.setDefaultChatNetworkOfAgents(NETWORK);
		profileCode = persistentObjectManager.insert(profile).getCode();
		TestChatModel.setGlobalPromptObserver(prompt -> {
			writeTool.remove();
			if (prompt != null && prompt.getOptions() instanceof ToolCallingChatOptions options
					&& options.getToolCallbacks() != null) {
				for (ToolCallback callback : options.getToolCallbacks()) {
					final String name = callback.getToolDefinition().name();
					if (prompt.getContents() != null && prompt.getContents().contains(REQUEST)) {
						offered.add(name);
					}
					if (name != null && name.endsWith(WorkingDirectoryLibrarySource.WRITE_FILE_TOOL)) {
						writeTool.set(name);
					}
				}
			}
		});
		// the agent that can write files, asked for the note, writes it once
		TestChatModel.setGlobalToolCallLogic(prompt -> {
			final String tool = writeTool.get();
			if (prompt == null || !prompt.contains(REQUEST) || tool == null || !asked.compareAndSet(false, true)) {
				return null;
			}
			return List.of(new AssistantMessage.ToolCall("call-run-files-1", "function", tool,
					"{\"path\":\"" + FILE + "\",\"content\":\"" + CONTENT.replace("\n", "\\n") + "\"}"));
		});
		TestChatModel.setGlobalResponseLogic(prompt -> prompt != null && prompt.contains(REQUEST) ? ANSWER : "");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalToolCallLogic();
		TestChatModel.clearGlobalResponseLogic();
		TestChatModel.setGlobalPromptObserver(null);
	}

	@Test
	public void theFileARunWroteComesBackToTheMcpCaller() throws Exception {
		GeboMCPServerConfig config = new GeboMCPServerConfig();
		config.setExportedUniqueRelativeUrl("test-run-files");
		GeboMCPAgentsNetworkTool tool = new GeboMCPAgentsNetworkTool();
		tool.setAgentsNetworkCode(NETWORK);
		tool.setToolName("ask_the_loop");
		tool.setChatProfileCode(profileCode);
		config.setAgentNetworkAsTools(List.of(tool));
		final SyncToolSpecification specification = networkToolsProvider.buildTools(config).get(0);

		asked.set(false);
		final McpSchema.CallToolResult result = specification.callHandler().apply(null,
				new McpSchema.CallToolRequest("ask_the_loop", Map.of("request", REQUEST)));

		assertTrue(asked.get(), "the run's agent was offered a tool writing files; it was offered: " + offered);
		assertFalse(Boolean.TRUE.equals(result.isError()), "the run answers: " + result.content());
		final List<McpSchema.BlobResourceContents> files = new ArrayList<>();
		for (McpSchema.Content content : result.content()) {
			if (content instanceof McpSchema.EmbeddedResource embedded
					&& embedded.resource() instanceof McpSchema.BlobResourceContents blob) {
				files.add(blob);
			}
		}
		assertEquals(1, files.size(), "the file the run wrote comes back, once: " + result.content());
		assertTrue(files.get(0).uri().endsWith(FILE), "named as it was written: " + files.get(0).uri());
		assertEquals(CONTENT, new String(Base64.getDecoder().decode(files.get(0).blob()), StandardCharsets.UTF_8));
	}

	@Test
	public void theFileARunWroteComesBackToTheA2AClientOnce() throws Exception {
		A2AServerConfig server = new A2AServerConfig();
		server.setExportedRelativeUrl("test-run-files");
		A2AExportedAgent exported = new A2AExportedAgent();
		exported.setKind(A2AExportedAgent.Kind.NETWORK);
		exported.setNetworkCode(NETWORK);
		exported.setSkillName("ask_the_loop");
		exported.setChatProfileCode(profileCode);
		server.setExportedAgents(List.of(exported));

		asked.set(false);
		final ChatEnvironmentRun run = taskBridge.runInChatEnvironment(server, "ask_the_loop",
				A2AInboundResources.of(List.<Part<?>>of(new TextPart(REQUEST))), null,
				GTextAgentsNetworkRunner.LOGGING_SINK);
		assertNotNull(run, "the export is a network: it runs in a conversation");
		try {
			assertTrue(asked.get(), "the run's agent was offered a tool writing files; it was offered: " + offered);
			// as the A2A server reads a run: the responses its frames carry, the last one the answer
			final List<GeboChatResponse> responses = new ArrayList<>();
			for (GeboChatMessageEnvelope<?> frame : run.getFrames().collectList().block(Duration.ofSeconds(60))) {
				final GeboChatResponse carried = resultAdapter.responseOf(frame);
				if (carried != null) {
					responses.add(carried);
				}
			}
			assertFalse(responses.isEmpty(), "the run answers");
			final GeboChatResponse last = responses.get(responses.size() - 1);

			// message/send: the answer, with the conversation the run opened
			final Message answer = resultAdapter.messageOf(last, null, last.getUserChatContextCode(), List.of());
			assertEquals(1, filesNamed(answer, FILE), "the file the run wrote comes back: " + answer.parts());
			assertNotNull(run.getChatContextCode());
			assertEquals(run.getChatContextCode(), answer.contextId(),
					"a caller that named no conversation is told the one the run opened");

			// message/stream: one message for each response; the file travels once
			int streamed = 0;
			for (GeboChatResponse carried : responses) {
				streamed += filesNamed(
						resultAdapter.messageOf(carried, null, carried.getUserChatContextCode(), List.of()), FILE);
			}
			assertEquals(1, streamed, "over the " + responses.size() + " streamed message(s) the file travels once");
		} finally {
			run.dispose();
		}
	}

	private static int filesNamed(Message message, String name) {
		int found = 0;
		for (Part<?> part : message.parts()) {
			if (part instanceof FilePart file && file.file() instanceof FileWithBytes bytes && name.equals(bytes.name())) {
				assertEquals(CONTENT, new String(Base64.getDecoder().decode(bytes.bytes()), StandardCharsets.UTF_8));
				found++;
			}
		}
		return found;
	}
}
