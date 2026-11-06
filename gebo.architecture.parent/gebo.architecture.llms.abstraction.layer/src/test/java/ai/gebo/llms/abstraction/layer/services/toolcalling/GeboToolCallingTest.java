package ai.gebo.llms.abstraction.layer.services.toolcalling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClient.DefaultChatClientRequestSpec;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallLimitBehavior;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisor;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.RequestSpec;
import ai.gebo.llms.abstraction.layer.services.RunAsToolCallback;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.publisher.Flux;

/**
 * The container of what the controlling software adds to a model call: the
 * interfaces it loads for the tools to call back, the tools it declares whatever the
 * configuration says, the tool calling loop it governs.
 */
class GeboToolCallingTest {

	/** A callback the controlling software loads: a tool asks a person through it. */
	interface Askable {
		void ask(String question);
	}

	/** The controlling software's own implementation: it stores what the tool said. */
	static final class Asked implements Askable {
		final List<String> questions = new ArrayList<>();

		@Override
		public void ask(String question) {
			questions.add(question);
		}
	}

	public static class AskParam {
		public String question;
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

	private static ChatResponse toolCall(String tool, String arguments) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content("")
				.toolCalls(List.of(new AssistantMessage.ToolCall("call-1", "function", tool, arguments))).build())));
	}

	private static ChatResponse text(String text) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content(text).build())));
	}

	/** The tool of the tests: it asks through the callback the controlling software loaded. */
	private static ToolCallback askingTool(boolean returningDirect) {
		return ToolCallbackDeclarationUtil.declare((AskParam param, ToolContext context) -> {
			final Askable askable = GeboToolCallingManagerFactory.control(context, Askable.class);
			if (askable == null) {
				return "Nobody can be asked here.";
			}
			askable.ask(param.question);
			return "Asked: " + param.question;
		}, "askAPerson", "Asks a person", AskParam.class, String.class,
				returningDirect ? ToolCallbackDeclarationUtil.returningDirect() : null);
	}

	@Test
	void theToolsFindWhatTheControllingSoftwareLoadedAndNothingElse() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).caller("agent-1")
				.build();
		final Map<String, Object> context = toolCalling.toolsContext(Map.of("other", "value"));
		assertEquals("value", context.get("other"), "the given context is kept");
		final IToolCallingControlsProvider provider = GeboToolCallingManagerFactory.controlsOf(new ToolContext(context));
		assertSame(asked, provider.get(Askable.class));
		assertTrue(provider.has(Askable.class));
		assertEquals("agent-1", provider.caller());
		assertNull(provider.get(Runnable.class), "an interface nobody loaded");
		assertSame(IToolCallingControlsProvider.NONE, GeboToolCallingManagerFactory.controlsOf(new ToolContext(Map.of())),
				"outside a call made with a GeboToolCalling");
		assertNull(GeboToolCallingManagerFactory.control(null, Askable.class));
	}

	@Test
	void aControlIsAnInterfaceAndAnInstanceOfIt() {
		assertThrows(IllegalArgumentException.class, () -> GeboToolCalling.builder().provide(Asked.class, new Asked()));
		assertThrows(IllegalArgumentException.class,
				() -> GeboToolCalling.builder().provide((Class) Askable.class, (Object) "not an Askable"));
	}

	@Test
	void theBudgetGivenCapsTheOneTheContextCarriesOrBecomesIt() {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().toolsBudget(100).build();
		assertEquals(100, ToolsTokenBudget.from(toolCalling.toolsContext(null)).left());
		final ToolsTokenBudget shared = new ToolsTokenBudget(1000);
		final Map<String, Object> capped = toolCalling
				.toolsContext(Map.of(ToolsTokenBudget.TOOLS_CONTEXT_KEY, shared));
		assertSame(shared, ToolsTokenBudget.from(capped));
		assertEquals(100, shared.left());
	}

	@Test
	void aToolCallsBackWhatWasLoadedAndTheControllingSoftwareReadsItAfterTheCall() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final ChatModel model = modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"which one?\"}") : text("done"));
		final ChatResponse response = ChatClient.builder(model).build().prompt().user("q")
				.toolCallbacks(askingTool(false)).toolContext(toolCalling.toolsContext(null))
				.advisors(toolCalling.advisor(ObservationRegistry.NOOP)).call().chatResponse();
		assertEquals("done", response.getResult().getOutput().getText());
		assertEquals(List.of("which one?"), asked.questions, "stored by the callback, read after the call");
		assertEquals(2, toolCalling.getRounds());
		assertFalse(toolCalling.isStoppedByTool());
		assertEquals(3, toolCalling.getTurnHistory().size(), "the request, the tool call, its result");
	}

	@Test
	void aToolReturningDirectlyEndsTheCallAndTheContainerSaysSo() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();
		final ChatModel model = modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"may I?\"}") : text("never asked"));
		final ChatResponse response = ChatClient.builder(model).build().prompt().user("q")
				.toolCallbacks(askingTool(true)).toolContext(toolCalling.toolsContext(null))
				.advisors(toolCalling.advisor(ObservationRegistry.NOOP)).call().chatResponse();
		// the tool's result, as Spring AI renders a tool's String result: JSON
		assertEquals("\"Asked: may I?\"", response.getResult().getOutput().getText(),
				"the tool's result is the answer");
		assertEquals(List.of("may I?"), asked.questions);
		assertTrue(toolCalling.isStoppedByTool());
		assertEquals(List.of("askAPerson"), toolCalling.getStoppingTools());
		assertEquals(1, toolCalling.getRounds(), "the model is not called again");
	}

	@Test
	void theLoopRunsWithinTheLimitsGiven() {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, new Asked())
				.maxTotalToolCalls(1).onLimitExceeded(ToolCallLimitBehavior.THROW).build();
		final ChatModel model = modelAnswering(round -> toolCall("askAPerson", "{\"question\":\"again?\"}"));
		final ChatResponse response = ChatClient.builder(model).build().prompt().user("q")
				.toolCallbacks(askingTool(false)).toolContext(toolCalling.toolsContext(null))
				.advisors(toolCalling.advisor(ObservationRegistry.NOOP)).call().chatResponse();
		assertTrue(response.getResult().getOutput().getText().contains("limit"),
				response.getResult().getOutput().getText());
		assertEquals(2, toolCalling.getRounds(), "one call allowed, the second is refused");
	}

	@Test
	void theFactoryBuildsWithTheConfiguredDefaults() {
		final GeboToolCallingManagerFactory factory = new GeboToolCallingManagerFactory(null, 3, 7,
				ToolCallLimitBehavior.RETURN_ERROR_RESPONSE);
		final GeboToolCalling toolCalling = factory.builder().build();
		assertEquals(3, toolCalling.getLimits().maxCallsPerTool());
		assertEquals(7, toolCalling.getLimits().maxTotalToolCalls());
		assertEquals(ToolCallLimitBehavior.RETURN_ERROR_RESPONSE, toolCalling.getLimits().onLimitExceeded());
		assertEquals(GeboToolCalling.Limits.SPRING_DEFAULTS, new GeboToolCallingManagerFactory().builder().build()
				.getLimits(), "unset, Spring AI's own defaults");
		assertTrue(factory.advisorOf(toolCalling) instanceof GeboToolCallingAdvisor);
	}

	// ---- the model's call, prepared with a GeboToolCalling

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static class PreparingModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		private final ChatModel providerModel;

		PreparingModel(IGToolCallbackSourceRepositoryPattern repository, ChatModel model) {
			super(null, repository, recordingNothing(), ObservationRegistry.NOOP, null);
			this.providerModel = model;
			this.config = new GBaseChatModelConfig();
			this.config.setCode("preparing-model");
			this.toolNames = List.of("configured");
			this.chatClient = ChatClient.builder(model).build();
		}

		PreparingModel withNoTools() {
			this.toolNames = List.of();
			return this;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new PreparingModel(toolCallbacksRepository, providerModel);
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type) {
			return providerModel;
		}
	}

	private static IChatModelUsageAdvisorFactory recordingNothing() {
		final IChatModelUsageAdvisor advisor = mock(IChatModelUsageAdvisor.class);
		when(advisor.getName()).thenReturn("usage");
		final IChatModelUsageAdvisorFactory factory = mock(IChatModelUsageAdvisorFactory.class);
		when(factory.create(any(), any(), any())).thenReturn(advisor);
		return factory;
	}

	private static ToolCallback tool(String name) {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition()).thenReturn(org.springframework.ai.tool.definition.ToolDefinition.builder()
				.name(name).description(name).inputSchema("{}").build());
		return tool;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ReactiveIdentityUtil runningInPlace() {
		ReactiveIdentityUtil runAs = mock(ReactiveIdentityUtil.class);
		when(runAs.doRunAsWithReturn(any()))
				.thenAnswer(invocation -> ((RunAsWithReturn) invocation.getArgument(0)).apply());
		return runAs;
	}

	private static GPromptTemplateConfig prompt() {
		final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("test");
		prompt.setSystemPromptTemplate("You answer.");
		prompt.setUserPromptTemplate("{question}");
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		return prompt;
	}

	private static IChatRequestContext context() {
		return IChatRequestContext.builder().actualUserRequest("the question").consolidatedHistory("")
				.interactions(List.of()).documents(List.of()).toolsContext(Map.of("given", "yes"))
				.pipelineInfos(Map.of()).build();
	}

	private static List<String> names(List<ToolCallback> tools) {
		return tools.stream().map(x -> x.getToolDefinition().name()).toList();
	}

	@Test
	void theMandatoryToolsAreDeclaredWhateverToolsTheModelHasWithTheProviderAndTheLoop() {
		final IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		final List<ToolCallback> configured = List.of(tool("configured"));
		when(repository.getTools(any()))
				.thenAnswer(call -> ((List<?>) call.getArgument(0)).isEmpty() ? List.of() : configured);
		final PreparingModel model = new PreparingModel(repository, modelAnswering(round -> text("ok")));
		final PreparingModel noTools = new PreparingModel(repository, modelAnswering(round -> text("ok")))
				.withNoTools();
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked)
				.mandatoryTool(askingTool(true)).caller("agent-1").build();

		// a model without tools: the mandatory one is declared all the same
		RequestSpec spec = noTools.prepareCall(prompt(), Map.of(), context(), runningInPlace(), toolCalling);
		DefaultChatClientRequestSpec request = (DefaultChatClientRequestSpec) spec.getRequestSpec();
		assertEquals(List.of("askAPerson"), names(request.getToolCallbacks()));
		assertTrue(request.getToolCallbacks().get(0) instanceof RunAsToolCallback, "wrapped as every tool is");
		assertSame(toolCalling, request.getToolContext().get(GeboToolCalling.CONTEXT_KEY));
		assertSame(toolCalling.getProvider(),
				GeboToolCallingManagerFactory.controlsOf(new ToolContext(request.getToolContext())));
		assertEquals("yes", request.getToolContext().get("given"), "the request's context is kept");
		assertEquals(1, request.getAdvisors().stream().filter(a -> a instanceof GeboToolCallingAdvisor).count());
		assertEquals(1, request.getAdvisors().stream().filter(Advisor.class::isInstance)
				.filter(a -> a.getName().contains("Tool Calling")).count(), "one tool calling loop");

		// a model with tools: the configured ones and the mandatory one
		spec = model.prepareCall(prompt(), Map.of(), context(), runningInPlace(), toolCalling);
		request = (DefaultChatClientRequestSpec) spec.getRequestSpec();
		assertEquals(List.of("configured", "askAPerson"), names(request.getToolCallbacks()));

		// a plain call: as before, nothing of the above
		spec = noTools.prepareCall(prompt(), Map.of(), context(), runningInPlace());
		request = (DefaultChatClientRequestSpec) spec.getRequestSpec();
		assertTrue(request.getToolCallbacks().isEmpty());
		assertNull(request.getToolContext().get(GeboToolCalling.CONTEXT_KEY));
		assertTrue(request.getAdvisors().stream().noneMatch(a -> a instanceof GeboToolCallingAdvisor));
	}

	@Test
	void aMandatoryToolTakesThePlaceOfTheConfiguredOneOfItsName() {
		final IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		final List<ToolCallback> configured = List.of(tool("configured"));
		when(repository.getTools(any())).thenReturn(configured);
		final PreparingModel model = new PreparingModel(repository, modelAnswering(round -> text("ok")));
		final ToolCallback bound = tool("configured");
		final GeboToolCalling toolCalling = GeboToolCalling.builder().mandatoryTool(bound).build();
		final RequestSpec spec = model.prepareCall(prompt(), Map.of(), context(),
				runningInPlace(), toolCalling);
		final DefaultChatClientRequestSpec request = (DefaultChatClientRequestSpec) spec.getRequestSpec();
		assertEquals(List.of("configured"), names(request.getToolCallbacks()), "declared once");
	}

	@Test
	void aCallMadeWithTheContainerRunsItsToolsThroughTheModel() throws Exception {
		final IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getTools(any())).thenReturn(List.of());
		final PreparingModel model = new PreparingModel(repository, modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"ready?\"}") : text("after the ask")));
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked)
				.mandatoryTool(askingTool(false)).build();
		// the model runs as whoever calls: the identity is the test's, in place
		final String answer = model.textResponse(prompt(), Map.of(), context(),
				toolCalling);
		assertEquals("after the ask", answer);
		assertEquals(List.of("ready?"), asked.questions);
		assertEquals(2, toolCalling.getRounds());
	}

	@Test
	void aContainerListenerRecordsBesideTheRecorderOfTheRequest() throws Exception {
		final IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getTools(any())).thenReturn(List.of());
		final PreparingModel model = new PreparingModel(repository, modelAnswering(
				round -> round == 1 ? toolCall("askAPerson", "{\"question\":\"ready?\"}") : text("after the ask")));
		final ToolCallsListener ofTheRequest = new ToolCallsListener();
		final ToolCallsListener ofTheRun = new ToolCallsListener();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, new Asked())
				.mandatoryTool(askingTool(false)).listener(ofTheRun).build();
		final IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("the question")
				.consolidatedHistory("").interactions(List.of()).documents(List.of()).toolsContext(Map.of())
				.pipelineInfos(Map.of()).toolCallListener(ofTheRequest).build();

		model.textResponse(prompt(), Map.of(), context, toolCalling);

		assertEquals(1, ofTheRequest.getCalls().size(), "the request's recorder keeps recording");
		assertEquals(1, ofTheRun.getCalls().size(), "the run's recorder records as well");
		assertEquals("askAPerson", ofTheRun.getCalls().get(0).getName());
	}

	// ---- what travels through a network: merged, replaced, passed to a nested call

	interface Reports {
		void report(String text);
	}

	@Test
	void anAgentMergesItsOwnContainerWithTheReceivedOne() {
		Asked runSink = new Asked();
		Reports agentSink = text -> {
		};
		Asked agentAsked = new Asked();
		GeboToolCalling received = GeboToolCalling.builder().provide(Askable.class, runSink)
				.mandatoryTool(tool("askUser")).mandatoryTool(tool("remember")).caller("harness:h1").toolsBudget(500)
				.maxTotalToolCalls(3).build();
		GeboToolCalling own = GeboToolCalling.builder().provide(Askable.class, agentAsked)
				.provide(Reports.class, agentSink).mandatoryTool(tool("askUser")).listener(new ToolCallsListener(null))
				.build();

		GeboToolCalling merged = GeboToolCalling.merged(own, received);

		assertEquals(List.of("remember", "askUser"), names(merged.getMandatoryTools()),
				"both tools, the agent's askUser in the place of the received one");
		assertSame(merged.getMandatoryTools().get(1), own.getMandatoryTools().get(0));
		assertSame(agentAsked, merged.getProvider().get(Askable.class), "the agent's instance found first");
		assertSame(agentSink, merged.getProvider().get(Reports.class));
		assertEquals("harness:h1", merged.getCaller(), "the received caller, the agent set none");
		assertEquals(500, merged.getToolsBudget());
		assertEquals(3, merged.getLimits().maxTotalToolCalls(), "the received limits, the agent set none");
		assertSame(own.getListener(), merged.getListener(), "the agent's listener only");
		assertSame(received, GeboToolCalling.merged(null, received));
		assertSame(own, GeboToolCalling.merged(own, null));
	}

	@Test
	void aToolFindsTheContainerItRunsInToPassItToANestedCall() {
		GeboToolCalling toolCalling = GeboToolCalling.builder().caller("agent").build();
		ToolContext toolContext = new ToolContext(toolCalling.toolsContext(Map.of("other", "value")));

		assertSame(toolCalling, GeboToolCallingManagerFactory.nestedFrom(toolContext, "deepSearch"));
		assertSame(toolCalling, GeboToolCalling.from(toolContext));
		// outside a governed call the tool gets one of its own, naming itself
		final GeboToolCalling own = GeboToolCallingManagerFactory.nestedFrom(new ToolContext(Map.of()), "deepSearch");
		assertNotNull(own, "a tool always has a container for its own model calls");
		assertEquals(GeboToolCallingManagerFactory.TOOL_CALLER_PREFIX + "deepSearch", own.getCaller());
		assertEquals(GeboToolCallingManagerFactory.TOOL_CALLER_PREFIX + "deepSearch",
				GeboToolCallingManagerFactory.nestedFrom(null, "deepSearch").getCaller());
	}

	@Test
	void eachCallRecordsOnItsOwnAndTheLastToEndIsRead() {
		GeboToolCalling toolCalling = GeboToolCalling.builder().mandatoryTool(askingTool(true)).build();
		assertEquals(0, toolCalling.getCalls());
		assertEquals(0, toolCalling.getRounds());
		ChatClient client = ChatClient.builder(modelAnswering(round -> text("plain"))).build();
		// a call that ends without any tool, then one a tool ends
		client.prompt("no tools").advisors(toolCalling.advisor(null)).call().content();
		assertEquals(1, toolCalling.getCalls());
		GeboToolCalling.CallRecord first = toolCalling.getLastCall();
		assertEquals(1, first.getRounds());
		assertFalse(first.isStoppedByTool());

		ChatClient stopping = ChatClient.builder(modelAnswering(round -> toolCall("askAPerson", "{\"question\":\"q?\"}")))
				.build();
		stopping.prompt("ask").toolCallbacks(askingTool(true)).toolContext(toolCalling.toolsContext(null))
				.advisors(toolCalling.advisor(null)).call().content();
		assertEquals(2, toolCalling.getCalls());
		assertTrue(toolCalling.isStoppedByTool());
		assertEquals(List.of("askAPerson"), toolCalling.getStoppingTools());
		assertFalse(first.isStoppedByTool(), "the first call's record is untouched by the second");
	}

	/** A structured call: what its answer parses to, through the container. */
	private static java.util.LinkedHashMap parsed(GeboToolCalling toolCalling, ChatModel model) {
		return toolCalling.parsedUnlessStopped(() -> ChatClient.builder(model).build().prompt().user("q")
				.toolCallbacks(askingTool(true)).toolContext(toolCalling.toolsContext(null))
				.advisors(toolCalling.advisor(ObservationRegistry.NOOP)).call().entity(java.util.LinkedHashMap.class));
	}

	@Test
	void aCallEndedByAToolHasNoResultToParse() {
		final Asked asked = new Asked();
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, asked).build();

		assertNull(parsed(toolCalling, modelAnswering(round -> toolCall("askAPerson", "{\"question\":\"may I?\"}"))));

		assertEquals(List.of("may I?"), asked.questions);
		assertTrue(toolCalling.isStoppedByTool());
	}

	@Test
	void aCallNoToolEndedParsesAsAlways() {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().build();

		assertEquals(1, parsed(toolCalling, modelAnswering(round -> text("{\"a\":1}"))).get("a"));
		assertThrows(RuntimeException.class, () -> parsed(toolCalling, modelAnswering(round -> text("not json"))),
				"an answer that does not parse is a failure");
	}

	@Test
	void anEarlierCallEndedByAToolHidesNoFailure() {
		final GeboToolCalling toolCalling = GeboToolCalling.builder().provide(Askable.class, new Asked()).build();
		parsed(toolCalling, modelAnswering(round -> toolCall("askAPerson", "{\"question\":\"may I?\"}")));

		assertThrows(IllegalStateException.class, () -> toolCalling.parsedUnlessStopped(() -> {
			throw new IllegalStateException("the provider is down");
		}));
	}

	@Test
	void theContainerAnAgentMergedFromHearsOfItsCalls() {
		final GeboToolCalling received = GeboToolCalling.builder().caller("the-run").build();
		final GeboToolCalling merged = GeboToolCalling.merged(GeboToolCalling.builder().build(), received);

		parsed(merged, modelAnswering(round -> text("{\"a\":1}")));

		assertEquals(1, merged.getCalls());
		assertEquals(1, received.getCalls());
	}
}
