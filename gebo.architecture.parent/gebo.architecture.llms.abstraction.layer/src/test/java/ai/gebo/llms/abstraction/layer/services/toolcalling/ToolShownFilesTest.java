/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.toolcalling;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;

import com.fasterxml.jackson.annotation.JsonProperty;

import ai.gebo.architecture.ai.files.IGToolShownFiles;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.publisher.Flux;

/**
 * A tool shows a file to the model that called it: the file reaches the model in a
 * user message after the results of the round's tools, when the model takes it.
 */
class ToolShownFilesTest {

	private java.util.Map<ai.gebo.llms.abstraction.layer.model.GModelInputModality, ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits> before;

	/** Every kind of file is sent in these tests: the installation's switches are tried on their own. */
	@org.junit.jupiter.api.BeforeEach
	void filesAreSent() {
		before = ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.get();
		final java.util.Map<ai.gebo.llms.abstraction.layer.model.GModelInputModality, ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits> on = new java.util.EnumMap<>(
				ai.gebo.llms.abstraction.layer.model.GModelInputModality.class);
		for (ai.gebo.llms.abstraction.layer.model.GModelInputModality kind : ai.gebo.llms.abstraction.layer.model.GModelInputModality
				.values()) {
			on.put(kind, ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits.of(true, 6, 20, 20, 1000));
		}
		ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.set(on);
	}

	@org.junit.jupiter.api.AfterEach
	void asBefore() {
		ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.set(before);
	}
	public static class PathParam {
		@JsonProperty
		public String path;
	}

	private final List<Prompt> prompts = new ArrayList<>();
	private final List<Boolean> shown = new ArrayList<>();

	private ChatModel modelCallingTheToolOnce() {
		return new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				prompts.add(prompt);
				if (prompts.size() == 1) {
					return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content("")
							.toolCalls(List.of(new AssistantMessage.ToolCall("call-1", "function", "viewPicture",
									"{\"path\":\"chart.png\"}")))
							.build())));
				}
				return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content("a bar chart").build())));
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return Flux.just(call(prompt));
			}

			@Override
			public ChatOptions getOptions() {
				return ToolCallingChatOptions.builder().build();
			}
		};
	}

	private ToolCallback viewingTool() {
		return ToolCallbackDeclarationUtil.declare((PathParam param, ToolContext context) -> {
			final Boolean seen = IGToolShownFiles.show(context, param.path, "image/png", new byte[] { 1, 2, 3 });
			shown.add(seen);
			return Boolean.TRUE.equals(seen) ? param.path + " is shown to you in the next message"
					: param.path + " cannot be shown to this model";
		}, "viewPicture", "Shows a picture", PathParam.class, String.class);
	}

	private String run(GToolShownFiles shownFiles) {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().build();
		final Map<String, Object> toolsContext = new HashMap<>(toolCalling.toolsContext(null));
		if (shownFiles != null) {
			toolsContext.put(IGToolShownFiles.TOOLS_CONTEXT_KEY, shownFiles);
		}
		return ChatClient.builder(modelCallingTheToolOnce()).build().prompt().user("what does the chart show?")
				.tools(viewingTool()).toolContext(toolsContext)
				.advisors(toolCalling.advisor(ObservationRegistry.NOOP, shownFiles)).call().content();
	}

	@Test
	void theFileAToolShowsReachesTheModelAfterTheToolsResults() {
		assertEquals("a bar chart", run(new GToolShownFiles(type -> type.startsWith("image/"))));

		assertEquals(List.of(true), shown);
		assertEquals(2, prompts.size());
		final List<Message> second = prompts.get(1).getInstructions();
		final Message last = second.get(second.size() - 1);
		assertTrue(last instanceof UserMessage, "the picture travels in a user message of its own");
		assertTrue(second.get(second.size() - 2) instanceof ToolResponseMessage, "right after the tools' results");
		final UserMessage picture = (UserMessage) last;
		assertEquals(1, picture.getMedia().size());
		assertEquals("image/png", picture.getMedia().get(0).getMimeType().toString());
		assertArrayEquals(new byte[] { 1, 2, 3 }, picture.getMedia().get(0).getDataAsByteArray());
		assertTrue(picture.getText().contains("chart.png"));
	}

	@Test
	void aModelThatDoesNotTakeTheFileIsToldSoAndGetsNoMessage() {
		run(new GToolShownFiles(type -> false));

		assertEquals(List.of(false), shown);
		final List<Message> second = prompts.get(1).getInstructions();
		assertTrue(second.get(second.size() - 1) instanceof ToolResponseMessage, "nothing is added to the round");
	}

	@Test
	void aCallWithNoWayToShowFilesSaysSoToTheTool() {
		run(null);

		assertEquals(1, shown.size());
		assertNull(shown.get(0));
		assertFalse(prompts.get(1).getInstructions().stream()
				.anyMatch(message -> message instanceof UserMessage user && !user.getMedia().isEmpty()));
	}

	@Test
	void aRoundShowsWithinItsLimitsAndHandsItsFilesOverOnce() {
		final GToolShownFiles files = new GToolShownFiles(type -> true);
		for (int i = 0; i < 6; i++) {
			assertTrue(files.show("p" + i + ".png", "image/png", new byte[] { 1 }));
		}
		assertFalse(files.show("one-too-many.png", "image/png", new byte[] { 1 }));
		assertEquals(6, files.takeMessage().getMedia().size());
		assertNull(files.takeMessage(), "shown once");
		assertTrue(files.show("next-round.png", "image/png", new byte[] { 1 }));
	}

	@Test
	void aKindOfFileTheInstallationDoesNotSendIsNotShown() {
		final java.util.Map<ai.gebo.llms.abstraction.layer.model.GModelInputModality, ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits> imagesOnly = new java.util.EnumMap<>(
				ai.gebo.llms.abstraction.layer.model.GModelInputModality.class);
		imagesOnly.put(ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE,
				ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.Limits.of(true, 1, 1, 1, 1500));
		ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.set(imagesOnly);
		final GToolShownFiles files = new GToolShownFiles(type -> true);
		assertFalse(files.show("talk.mp3", "audio/mpeg", new byte[] { 1 }), "recordings are off");
		assertFalse(files.show("huge.png", "image/png", new byte[2 * 1024 * 1024]), "beyond the size of an image");
		assertTrue(files.show("chart.png", "image/png", new byte[] { 1 }));
		assertFalse(files.show("second.png", "image/png", new byte[] { 1 }), "one image a round");

		ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery.set(null);
		assertFalse(new GToolShownFiles(type -> true).show("chart.png", "image/png", new byte[] { 1 }),
				"every kind off: nothing is shown");
	}
}
