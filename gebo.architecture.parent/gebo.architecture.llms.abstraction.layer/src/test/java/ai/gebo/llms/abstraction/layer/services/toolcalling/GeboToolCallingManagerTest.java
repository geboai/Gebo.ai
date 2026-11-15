package ai.gebo.llms.abstraction.layer.services.toolcalling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntFunction;

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
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.publisher.Flux;

/**
 * The manager of a call made with a container: Spring AI's own, plus the end of the
 * call the loaded controls decide once a tool told them something ending it.
 */
class GeboToolCallingManagerTest {

	/** A callback the controlling software loads: a tool asks a person through it. */
	interface Askable {
		void ask(String question);
	}

	/**
	 * The controlling software's own implementation: it stores what the tool said,
	 * and once a question was asked the call ends.
	 */
	static final class Asked implements Askable, IToolCallEndingControl {
		final List<String> questions = new ArrayList<>();

		@Override
		public void ask(String question) {
			questions.add(question);
		}

		@Override
		public boolean endsTheCall() {
			return !questions.isEmpty();
		}
	}

	public static class AskParam {
		public String question;
	}

	private static final String REFUSAL = "Give a question to ask.";

	/**
	 * The tool of the tests: it asks through the callback the controlling software
	 * loaded, or refuses, telling the model what it needs, when given no question.
	 */
	private static ToolCallback askingTool(boolean returningDirect) {
		return ToolCallbackDeclarationUtil.declare((AskParam param, ToolContext context) -> {
			final Askable askable = GeboToolCallingManagerFactory.control(context, Askable.class);
			if (askable == null) {
				return "Nobody can be asked here.";
			}
			if (param.question == null || param.question.isBlank()) {
				return REFUSAL;
			}
			askable.ask(param.question);
			return "Asked: " + param.question;
		}, "askAPerson", "Asks a person", AskParam.class, String.class,
				returningDirect ? ToolCallbackDeclarationUtil.returningDirect() : null);
	}

	private static Prompt prompt(GeboToolCalling toolCalling, ToolCallback... tools) {
		return new Prompt(List.of(UserMessage.builder().text("q").build()), ToolCallingChatOptions.builder()
				.toolCallbacks(List.of(tools)).toolContext(toolCalling.toolsContext(null)).build());
	}

	private static ChatResponse toolCall(String tool, String arguments) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content("")
				.toolCalls(List.of(new AssistantMessage.ToolCall("call-1", "function", tool, arguments))).build())));
	}

	private static ChatResponse text(String text) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content(text).build())));
	}

	/** A model answering each round as told: a tool call, or text. */
	private static ChatModel modelAnswering(IntFunction<ChatResponse> rounds) {
		final AtomicInteger round = new AtomicInteger();
		return new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				return rounds.apply(round.incrementAndGet());
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

	/** The last tool result of the round, as Spring AI renders a tool's String result: JSON. */
	private static String lastToolResult(ToolExecutionResult result) {
		final List<Message> history = result.conversationHistory();
		final ToolResponseMessage last = assertInstanceOf(ToolResponseMessage.class, history.get(history.size() - 1));
		return last.getResponses().get(0).responseData();
	}

	private static String json(String text) {
		return "\"" + text + "\"";
	}

	// ---- the manager on its own

	@Test
	void aRoundWhoseToolToldTheControlSomethingEndingEndsTheCall() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final GeboToolCallingManager manager = new GeboToolCallingManager(toolCalling.manager(ObservationRegistry.NOOP),
				List.of(asked));
		final ToolExecutionResult result = manager.executeToolCalls(prompt(toolCalling, askingTool(false)),
				toolCall("askAPerson", "{\"question\":\"which one?\"}"));
		assertTrue(result.returnDirect(), "the control says the call ends here");
		assertEquals(List.of("which one?"), asked.questions);
		assertEquals(3, result.conversationHistory().size(), "the request, the tool call, its result");
		assertEquals(json("Asked: which one?"), lastToolResult(result));
	}

	@Test
	void aRoundWhoseToolToldNothingEndingGoesBackToTheModel() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final GeboToolCallingManager manager = new GeboToolCallingManager(toolCalling.manager(ObservationRegistry.NOOP),
				List.of(asked));
		final ToolExecutionResult result = manager.executeToolCalls(prompt(toolCalling, askingTool(false)),
				toolCall("askAPerson", "{\"question\":\"\"}"));
		assertFalse(result.returnDirect(), "a refusal is a tool result the model reads");
		assertTrue(asked.questions.isEmpty());
		assertEquals(json(REFUSAL), lastToolResult(result));
	}

	@Test
	void aToolDeclaredDirectKeepsItsStopWhateverTheControlsSay() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final GeboToolCallingManager manager = new GeboToolCallingManager(toolCalling.manager(ObservationRegistry.NOOP),
				List.of(asked));
		final ToolExecutionResult result = manager.executeToolCalls(prompt(toolCalling, askingTool(true)),
				toolCall("askAPerson", "{\"question\":\"\"}"));
		assertTrue(result.returnDirect(), "the declaration ends the call whatever the tool returned, as before");
		assertEquals(json(REFUSAL), lastToolResult(result));
	}

	@Test
	void aRoundWithNoEndingControlLoadedIsTheDelegatesResultItself() {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, question -> {
		}).build();
		final AtomicReference<ToolExecutionResult> computed = new AtomicReference<>();
		final ToolCallingManager spring = toolCalling.manager(ObservationRegistry.NOOP);
		final ToolCallingManager recording = new ToolCallingManager() {
			@Override
			public List<ToolDefinition> resolveToolDefinitions(ToolCallingChatOptions chatOptions) {
				return spring.resolveToolDefinitions(chatOptions);
			}

			@Override
			public ToolExecutionResult executeToolCalls(Prompt prompt, ChatResponse chatResponse) {
				computed.set(spring.executeToolCalls(prompt, chatResponse));
				return computed.get();
			}
		};
		final GeboToolCallingManager manager = new GeboToolCallingManager(recording, List.of(new Object()));
		final ToolExecutionResult result = manager.executeToolCalls(prompt(toolCalling, askingTool(false)),
				toolCall("askAPerson", "{\"question\":\"which one?\"}"));
		assertSame(computed.get(), result, "untouched");
		assertFalse(result.returnDirect());
	}

	@Test
	void onlyTheEndingControlsAreKeptEachOnce() {
		final Asked asked = new Asked();
		final GeboToolCallingManager manager = new GeboToolCallingManager(
				ToolCallingManager.builder().observationRegistry(ObservationRegistry.NOOP).build(),
				List.of(new Object(), asked, "not a control", asked));
		assertEquals(List.of(asked), manager.getEndingControls());
		assertTrue(new GeboToolCallingManager(manager.getDelegate(), null).getEndingControls().isEmpty());
	}

	@Test
	void theDelegateIsRequired() {
		assertThrows(IllegalArgumentException.class, () -> new GeboToolCallingManager(null, List.of()));
	}

	// ---- through the loop of a call made with the container

	private static ChatResponse callThrough(GeboToolCalling toolCalling, Asked asked, ChatModel model) {
		final GeboToolCallingManager manager = new GeboToolCallingManager(toolCalling.manager(ObservationRegistry.NOOP),
				List.of(asked));
		return ChatClient.builder(model).build().prompt().user("q").toolCallbacks(askingTool(false))
				.toolContext(toolCalling.toolsContext(null)).advisors(new GeboToolCallingAdvisor(toolCalling, manager))
				.call().chatResponse();
	}

	@Test
	void throughTheLoopTheCallEndsWhereTheControlSaysAndTheContainerSaysSo() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final ChatModel model = modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"may I?\"}") : text("never asked"));
		final ChatResponse response = callThrough(toolCalling, asked, model);
		// the tool's result, as Spring AI renders a tool's String result: JSON
		assertEquals("\"Asked: may I?\"", response.getResult().getOutput().getText(),
				"the tool's result is the answer");
		assertEquals(List.of("may I?"), asked.questions);
		assertTrue(toolCalling.isStoppedByTool());
		assertEquals(List.of("askAPerson"), toolCalling.getStoppingTools());
		assertEquals(1, toolCalling.getRounds(), "the model is not called again");
	}

	@Test
	void throughTheLoopARefusalReachesTheModelWhichCallsAgainProperly() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final ChatModel model = modelAnswering(round -> switch (round) {
		case 1 -> toolCall("askAPerson", "{\"question\":\"\"}");
		case 2 -> toolCall("askAPerson", "{\"question\":\"which one?\"}");
		default -> text("never reached");
		});
		final ChatResponse response = callThrough(toolCalling, asked, model);
		assertEquals("\"Asked: which one?\"", response.getResult().getOutput().getText());
		assertEquals(List.of("which one?"), asked.questions, "asked once, properly");
		assertTrue(toolCalling.isStoppedByTool());
		assertEquals(2, toolCalling.getRounds(), "the refusal went back to the model, which called again");
		// the turn's messages as the loop continued them: the round that ended the call
		// is the answer, not history, as for a tool declared direct
		final List<Message> history = toolCalling.getTurnHistory();
		assertEquals(3, history.size(), "the request, the refused call, the refusal the model read");
		assertEquals(json(REFUSAL), ((ToolResponseMessage) history.get(2)).getResponses().get(0).responseData());
	}

	@Test
	void throughTheLoopARefusalTheModelAcceptsLetsItAnswerOnItsOwn() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final ChatModel model = modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"\"}") : text("I assumed the first"));
		final ChatResponse response = callThrough(toolCalling, asked, model);
		assertEquals("I assumed the first", response.getResult().getOutput().getText());
		assertTrue(asked.questions.isEmpty());
		assertFalse(toolCalling.isStoppedByTool(), "no tool ended the call: its answer is the model's");
		assertEquals(2, toolCalling.getRounds());
	}
}
