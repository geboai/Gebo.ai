package ai.gebo.llms.agent.chat.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.FileCopyUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.AgentPromptTemplateParams;
import ai.gebo.architecture.agents.services.GAbstractReactiveAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.agent.chat.service.IReportWriterReactiveAgentService;
import ai.gebo.llms.agent.standard.config.StandardAgentsPromptsLibraryConfig;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.impl.ThinkingStream;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReportWriterReactiveAgentServiceImpl
		extends GAbstractReactiveAgentService<String, GeboChatMessageEnvelope, GeboChatResponse>
		implements IReportWriterReactiveAgentService {
	protected static final String REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM = "REQUIRED_AGENT_COMPLETENESS";
	protected static final String DELIVERABLE_FORMATTING_RULES_TEMPLATE_PARAM = "DELIVERABLE_FORMATTING_RULES";
	static final String DELIVERABLE_FORMATS_RESOURCE_PATH = "/agents-prompt-library/en/deliverable-formats/";
	private static final Map<DeliverableIntent, String> DELIVERABLE_FORMATS_CACHE = new ConcurrentHashMap<>();
	private static final String END_AGENT_LOOP = "END_AGENT-LOOP-";
	private static final String TOOL_CALLED = "TOOL-CALLED-";
	private static final String RESPONSE = "RESPONSE: ";
	private static final String BEGIN_AGENT_LOOP = "BEGIN_AGENT-LOOP-";
	private static final String NEWLINE = "\r\n";
	public static final String AGENT_CONTROL_CONTINUE_PROMPT_PARAM = "AGENT_CONTROL_CONTINUE_PROMPT_PARAM";
	public static final String AGENT_CONTROL_FINISHED_PROMPT_PARAM = "AGENT_CONTROL_FINISHED_PROMPT_PARAM";
	private static final String MAX_ITERATIONS_PROMPT_PARAM = "MAX_ITERATIONS";
	public static final String CURRENT_ITERATION_PROMPT_PARAM = "CURRENT_ITERATION";
	public static final String AGENT_SESSION_STORY_PROMPT_PARAM = "AGENT_SESSION_STORY";
	private static final String REPORT_WRITER_NETWORK_AGENT_SERVICE_DESC = "Report writer network agent service";
	public static final String REPORT_WRITER_NETWORK_AGENT_SERVICE = "ReportWriterNetworkAgentService";
	public static final String AGENT_CONTROL_FINISHED = "<AGENT-CONTROL-STOP/>";
	public static final String AGENT_CONTROL_MORE_TOOLS = "<AGENT-CONTROL-MORE-TOOLS/>";

	public ReportWriterReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);

	}
	/**
	 * Per-deliverable formatting rules, packaged as one .txt resource per
	 * {@link DeliverableIntent} under {@value #DELIVERABLE_FORMATS_RESOURCE_PATH} and
	 * assembled into the module jar, so the wording is maintained as prompt text rather
	 * than as a Java string literal. Resolved by the enum constant name, read once and
	 * cached.
	 * <p>
	 * When the resource for an intent cannot be read, the rules of
	 * {@link DeliverableIntent#SUMMARY} are used: it is the balanced middle of the ladder
	 * and it is already the intent this writer falls back to when the shared environment
	 * carries no user intent at all, so an unresolvable rule degrades to the same shape
	 * as an unresolvable intent rather than to no shape at all.
	 */
	private String deliverableFormattingRules(DeliverableIntent intent) {
		return DELIVERABLE_FORMATS_CACHE.computeIfAbsent(intent, key -> {
			String rules = readDeliverableFormatResource(key);
			if (rules == null && key != DeliverableIntent.SUMMARY) {
				LOGGER.warn("No usable formatting rules for deliverable " + key.name()
						+ ", falling back to the " + DeliverableIntent.SUMMARY.name() + " ones");
				rules = readDeliverableFormatResource(DeliverableIntent.SUMMARY);
			}
			if (rules == null) {
				// Only reachable when the SUMMARY resource itself is missing from the jar.
				LOGGER.warn("No formatting rules resource could be read at all: the writer prompt"
						+ " keeps only its unconditional formatting rules");
				return "";
			}
			return rules;
		});
	}

	/**
	 * Reads one deliverable formatting rules resource, or returns {@code null} when it is
	 * absent or unreadable, so the caller can apply the fallback.
	 */
	private String readDeliverableFormatResource(DeliverableIntent intent) {
		final String reference = DELIVERABLE_FORMATS_RESOURCE_PATH + intent.name() + ".txt";
		try (InputStream is = ReportWriterReactiveAgentServiceImpl.class.getResourceAsStream(reference)) {
			if (is == null) {
				LOGGER.warn("No deliverable formatting rules resource at " + reference);
				return null;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			FileCopyUtils.copy(is, bos);
			final String rules = bos.toString(StandardCharsets.UTF_8);
			if (rules.isBlank()) {
				LOGGER.warn("The deliverable formatting rules at " + reference + " are empty");
				return null;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Loaded deliverable formatting rules for " + intent.name() + " from " + reference
						+ " (" + rules.length() + " chars)");
			}
			return rules;
		} catch (IOException e) {
			LOGGER.warn("Cannot read the deliverable formatting rules at " + reference, e);
			return null;
		}
	}

	@Override
	protected <I, O> List<Map<String, Object>> createAgentTemplateParams(GPromptTemplateConfig prompt,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext<I, O> mySessionContext, Object input,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget, boolean splitByBudget) {
		// Base adds the routing-cycle signals; here we add the required deliverable
		// completeness derived from the shared USER_INTENT.
		List<Map<String, Object>> output = super.createAgentTemplateParams(prompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, input, agentsDao, actualContributionNr, tokenBudget,
				splitByBudget);
		final DeliverableIntent actualUserIntent = sessionUserIntent(session);
		final Map<String, Object> deliverableParams = deliverableTemplateParams(actualUserIntent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Report writer required deliverable completeness:" + actualUserIntent.name()
					+ " applied to " + output.size() + " parameter window(s)");
		}
		for (Map<String, Object> window : output) {
			window.putAll(deliverableParams);
		}
		return output;
	}

	/**
	 * The kind of deliverable the user asked for (QA, HOWTO, ANALISYS...), as the
	 * routing classified it into the shared environment, SUMMARY when it is absent.
	 */
	protected DeliverableIntent sessionUserIntent(AgentsCollaborationSessionContext session) {
		DeliverableIntent actualUserIntent = session != null && session.getEnvironment() != null
				? (DeliverableIntent) session.getEnvironment().get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT)
				: null;
		if (actualUserIntent == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No " + StandardAgentsNetworkEnvironmentEntries.USER_INTENT
						+ " in the shared environment, defaulting to SUMMARY");
			}
			actualUserIntent = DeliverableIntent.SUMMARY;
		}
		return actualUserIntent;
	}

	/**
	 * The prompt parameters shaping the deliverable: the required completeness and the
	 * formatting rules of that deliverable kind only. The writer is never asked to
	 * select its own branch out of a catalogue of every type, and the prompt does not
	 * carry the branches it will not use.
	 */
	protected Map<String, Object> deliverableTemplateParams(DeliverableIntent intent) {
		final String completeness = intent.name() + ": " + intent.getAgentDeliverableCompleteness();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<" + REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM + ">");
			LOGGER.trace(completeness);
			LOGGER.trace("</" + REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM + ">");
		}
		final Map<String, Object> params = new HashMap<>();
		params.put(REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM, completeness);
		params.put(DELIVERABLE_FORMATTING_RULES_TEMPLATE_PARAM, deliverableFormattingRules(intent));
		return params;
	}
	@Override
	public String getId() {

		return REPORT_WRITER_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {

		return REPORT_WRITER_NETWORK_AGENT_SERVICE_DESC;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Read the user question and the evidence gathered by the other agents and write the final, user-facing answer/report");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Report writer agent id:" + getId() + " advertises the report writing capability");
		}
		return capabilities;
	}

	/**
	 * The writer's private context. The writer works over cycles: each draft already
	 * includes the evidence of the previous ones, so its last output is the base of
	 * the new draft and goes in whole, while the older turns are superseded and are
	 * only recalled by their instruction.
	 */
	@Override
	protected <InputType, OutputType> String render(AgentPrivateSessionContext<InputType, OutputType> mySessionContext,
			int actualContributionNr, int remainingBudget) {
		List<AgentPrivateSessionContext<InputType, OutputType>.AgentInteraction> interactions = mySessionContext
				.getInteractions();
		if (interactions == null || interactions.isEmpty()) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		buffer.append(BEGIN_PREVIOUS_TURNS).append(NEWLINE);
		int last = interactions.size() - 1;
		for (int turn = 0; turn < last; turn++) {
			buffer.append(SUPERSEDED_TURN).append(turn + 1).append(": ")
					.append(truncateToTokens(render(interactions.get(turn).getInputMessage()).strip()
							.replaceAll("\\s+", " "), SUPERSEDED_TURN_INSTRUCTION_TOKENS))
					.append(NEWLINE);
		}
		AgentPrivateSessionContext<InputType, OutputType>.AgentInteraction lastTurn = interactions.get(last);
		buffer.append(LAST_TURN_INSTRUCTION).append(NEWLINE)
				.append(renderHandlingTruncate(lastTurn.getInputMessage())).append(NEWLINE);
		String lastOutput = renderOutput(lastTurn.getOutput());
		int lastOutputTokens = ITokensCountable.stringsTokensSize(lastOutput);
		if (lastOutputTokens > remainingBudget) {
			// Bounded by the model's output tokens, so only reachable with a budget
			// already spent: the draft still leads the budget, cut to it.
			LOGGER.warn("Report writer agent id:" + getId() + " last output of " + lastOutputTokens
					+ " (tok) over the budget of " + remainingBudget + " (tok), it is cut");
			lastOutput = fitEqually(List.of(lastOutput), Math.max(remainingBudget, MIN_SHARED_CONTEXT_TOKENS)).get(0);
		}
		buffer.append(LAST_TURN_OUTPUT).append(NEWLINE).append(lastOutput).append(NEWLINE);
		buffer.append(END_PREVIOUS_TURNS).append(NEWLINE);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("render(privateContext) report writer agent id:" + getId() + " " + interactions.size()
					+ " turn(s), last output " + lastOutputTokens + " (tok) whole, size:"
					+ ITokensCountable.stringsTokensSize(buffer.toString()) + " (tok)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<REPORT_WRITER_PRIVATE_CONTEXT>");
			LOGGER.trace(buffer.toString());
			LOGGER.trace("</REPORT_WRITER_PRIVATE_CONTEXT>");
		}
		return buffer.toString();
	}

	private static final String BEGIN_PREVIOUS_TURNS = "BEGIN YOUR PREVIOUS TURNS";
	private static final String END_PREVIOUS_TURNS = "END YOUR PREVIOUS TURNS";
	private static final String SUPERSEDED_TURN = "Earlier turn (superseded by your last output) ";
	private static final String LAST_TURN_INSTRUCTION = "YOUR LAST TURN INSTRUCTION:";
	private static final String LAST_TURN_OUTPUT = "YOUR LAST OUTPUT (the base to update with the new evidence):";
	/** Tokens of a superseded turn's instruction recalled in the private context. */
	private static final int SUPERSEDED_TURN_INSTRUCTION_TOKENS = 40;

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, String request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext,
			IGConfigurableChatModel agentModel, GAgentRole agentRole, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createResponse(...) report writer agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getNetworkAgentName() : null));
		}
		final GeboChatResponse response = new GeboChatResponse();
		final int tokenBudget = agentTokenBudget(agentModel, agentPrompt, chatRequestContext);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Report writer agent id:" + getId() + " contextLength:" + agentModel.getContextLength()
					+ " promptSize:" + agentPrompt.getTokensSize() + " (tok) tokenBudget:" + tokenBudget + " (tok)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<REPORT_WRITER_REQUEST agent=" + getId() + ">");
			LOGGER.trace(request);
			LOGGER.trace("</REPORT_WRITER_REQUEST>");
		}
		// The routed query (the coordinator's writing command) is the agent input that
		// feeds the {INPUT} placeholder; pass the raw payload as the other agents do,
		// so
		// it is rendered by the standard String renderer. SHARED_CONTEXT, identity and
		// scenario placeholders are built from the network/session. agentsDao is not
		// available at this layer, hence null (peer descriptions are then omitted).
		final List<Map<String, Object>> params = createAgentTemplateParams(agentPrompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, request, null, 0, tokenBudget, true);
		notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " is writing a report..",
				ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
		Flux<String> textStream = null;
		if (params.size() == 1) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Report writer using single-window reactive LLM call (tokenBudget:" + tokenBudget + ")");
			}
			textStream = writeReactive(agentModel, agentPrompt, chatRequestContext, params.get(0), notificationSink);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Report writer using token-budget coordinator over " + params.size()
						+ " shared-context window(s)");
			}
			textStream = streamThroughEvidenceExtraction(chatRequestContext, network, agentRole,
					contextAgentPersona, session, mySessionContext, request, agentModel, agentPrompt, params,
					tokenBudget, runAs, notificationSink);
		}

		return renderOutputStream(textStream, response, session, contextAgentPersona, notificationSink,
				callBacksListener);
	}

	/**
	 * The model's answer as it streams, with what its text alone does not carry: its
	 * reasoning, every provider alike and every model round of its tools included, and why
	 * it stopped.
	 */
	protected Flux<GChatAnswerChunk> callLLMReactiveResponses(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params)
			throws LLMConfigException {
		return chatModel.streamAnswer(prompt, params, context);
	}

	/**
	 * The text the writer writes, as it streams: an output of the network, its reasoning
	 * goes to the user's chat as it comes ({@code notificationSink}, when it is the chat's
	 * emitter), completed when the text starts.
	 */
	protected Flux<String> writeReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> params, INotificationSink notificationSink)
			throws LLMConfigException {
		final Flux<GChatAnswerChunk> chunks = callLLMReactiveResponses(chatModel, prompt, context, params);
		final ISinkUIEmitter ui = notificationSink instanceof ISinkUIEmitter emitter ? emitter : null;
		return Flux.defer(() -> {
			final ThinkingStream thinking = new ThinkingStream();
			return chunks.map(chunk -> {
				thinkingTo(ui, thinking.delta(chunk.thinking()));
				if (!chunk.answer().isBlank()) {
					// the text starts: the reasoning ended
					thinkingTo(ui, thinking.complete());
				}
				return chunk.answer();
			}).filter(text -> !text.isEmpty()).concatWith(Flux.defer(() -> {
				// a reasoning no text came after ends with the model's output
				thinkingTo(ui, thinking.complete());
				return Flux.<String>empty();
			}));
		});
	}

	/** The reasoning events sent to the user's chat, never failing the answer. */
	protected void thinkingTo(ISinkUIEmitter ui, List<GThinkingEvent> events) {
		if (ui == null || events.isEmpty()) {
			return;
		}
		try {
			for (GThinkingEvent event : events) {
				ui.next(new GeboChatMessageEnvelope<GThinkingEvent>(event));
			}
		} catch (RuntimeException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent id:" + getId() + " the reasoning could not be sent to the chat: "
						+ e.getMessage());
			}
		}
	}

	/**
	 * Turns the writer's raw text stream into the outgoing partial-operation stream:
	 * the running text is emitted chunk by chunk as body envelopes, then a final
	 * envelope carries the assembled {@link GeboChatResponse} (query response,
	 * documents, called functions).
	 *
	 * <p>
	 * This is the single seam where the output is materialised, so a subclass can
	 * post-process the writer's stream - for instance the office assistant writer,
	 * which splits an escaped document part out of the user-facing chat text and
	 * attaches it to {@link GeboChatResponse#setAdditionalContents(List)} - by
	 * overriding this method while keeping {@link #createResponse} untouched.
	 */
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> renderOutputStream(Flux<String> textStream,
			GeboChatResponse response, AgentsCollaborationSessionContext session,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			ToolCallsListener callBacksListener) {
		final StringBuffer cumulatedContent = new StringBuffer();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin renderOutputStream(...) report writer agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getNetworkAgentName() : null));
		}
		Flux<IGPartialOperation<GeboChatMessageEnvelope>> bodyStream = textStream.map(content -> {
			cumulatedContent.append(content);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<REPORT_WRITER_CHUNK cumulated=" + cumulatedContent.length() + ">");
				LOGGER.trace(content);
				LOGGER.trace("</REPORT_WRITER_CHUNK>");
			}
			return IGPartialOperation.of(new GeboChatMessageEnvelope(content), false);
		});
		Flux<IGPartialOperation<GeboChatMessageEnvelope>> lastItem = Flux.defer(() -> {
			String queryResponse = cumulatedContent.toString();
			boolean lastMessage = false;
			notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " has finished",
					ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
			response.setQueryResponse(queryResponse);
			response.setDocumentsRef(extractDocumentsList(session));
			// This network response carries the writer's own calls, for its cycle history;
			// the calls of the whole request (every agent) are recorded on the pipeline
			// response by the request's recorder.
			response.setCalledFunctions(renderFunctions(callBacksListener.getCalls()));
			GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
			envelope.setLastMessage(lastMessage);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End createResponse(...) report writer agent id:" + getId() + " final response length:"
						+ queryResponse.length() + " own tool calls:" + callBacksListener.getCalls().size());
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<REPORT_WRITER_FINAL_TEXT>");
				LOGGER.trace(queryResponse);
				LOGGER.trace("</REPORT_WRITER_FINAL_TEXT>");
			}
			return Flux.just(IGPartialOperation.of(envelope, lastMessage));
		});
		return Flux.concat(bodyStream, lastItem);
	}

	protected List<GResponseDocumentRef> documentsList(AgentsCollaborationSessionContext session) {
		return extractDocumentsList(session);
	}

	protected List<CalledFunction> calledFunctions(ToolCallsListener callBacksListener) {
		return renderFunctions(callBacksListener.getCalls());
	}

	private List<GResponseDocumentRef> extractDocumentsList(AgentsCollaborationSessionContext session) {
		List<Document> documentRef = new ArrayList<Document>();
		for (AgentProducedSessionContribution contrib : session.getSampledContributions()) {
			if (contrib.getData() instanceof Document doc) {
				documentRef.add(doc);
			} else if (contrib.getData() instanceof Collection collection) {
				for (Object obj : collection) {
					if (obj != null && obj instanceof Document doc) {
						documentRef.add(doc);
					}
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("extractDocumentsList(...) collected " + documentRef.size()
					+ " document(s) out of the shared session contributions");
		}
		TreeMap<String, Document> forDocCode = new TreeMap<String, Document>();
		for (Document document : documentRef) {
			if (document.getMetadata() != null && document.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
					&& document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE) instanceof String code) {
				forDocCode.put(code, document);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("extractDocumentsList(...) deduplicated to " + forDocCode.size() + " referenced document(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Referenced document codes: " + forDocCode.keySet());
		}
		// Prefer the rich refs the external-search agents published upstream (keyed by the same
		// getCode() as the Document's CONTENT_CODE): those were built from the typed SearchResult
		// and carry nestedSearchResult, so the user can "chat with" them. Fall back to building
		// from the Document for anything not published (internal knowledge-base documents).
		final Map<String, GResponseDocumentRef> publishedRefs = chatWithDocumentRefs(session);
		return forDocCode.entrySet().stream().map(entry -> {
			GResponseDocumentRef published = publishedRefs.get(entry.getKey());
			return published != null ? published : new GResponseDocumentRef(entry.getValue());
		}).toList();
	}

	@SuppressWarnings("unchecked")
	private Map<String, GResponseDocumentRef> chatWithDocumentRefs(AgentsCollaborationSessionContext session) {
		Object existing = session.getEnvironment().get(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE);
		return existing instanceof Map<?, ?> ? (Map<String, GResponseDocumentRef>) existing : Map.of();
	}

	/**
	 * Writes the report when the cycle's evidence does not fit one writing call. The
	 * evidence is paged into windows sized for the extraction prompt
	 * ({@value StandardAgentsPromptsLibraryConfig#REPORT_EVIDENCE_EXTRACTOR_PROMPT}),
	 * each reduced to the flat list of its relevant facts with their sources; while
	 * the extractions do not fit the final writing call they are grouped and reduced
	 * again with the same prompt; the writer prompt then writes the answer from them,
	 * with its own previous output as the base.
	 */
	protected Flux<String> streamThroughEvidenceExtraction(IChatRequestContext chatRequestContext,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext, String request,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt, List<Map<String, Object>> params,
			int tokenBudget, ReactiveIdentityUtil runAs, INotificationSink notificationSink) throws AgentException {
		final GPromptTemplateConfig extractorPrompt = resolvePrompt(null,
				StandardAgentsPromptsLibraryConfig.REPORT_EVIDENCE_EXTRACTOR_PROMPT, false);
		final int extractorBudget = agentTokenBudget(agentModel, extractorPrompt, chatRequestContext);
		final List<Map<String, Object>> windows = createAgentTemplateParams(extractorPrompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, request, null, 0, extractorBudget, true);
		// A copy: the windows' own maps must keep their shared context.
		final Map<String, Object> finalParams = new HashMap<>(params.get(0));
		finalParams.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, "");
		finalParams.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
		final int finalBudget = tokenBudget - tokensOf(finalParams);
		final Map<String, Object> extractorParams = new HashMap<>(windows.get(0));
		extractorParams.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, "");
		final int groupBudget = extractorBudget - tokensOf(extractorParams);
		final AtomicInteger failures = new AtomicInteger();
		final ISinkUIEmitter emitter = notificationSink instanceof ISinkUIEmitter em ? em
				: toSinkUIEmitter(notificationSink);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Report writer agent id:" + getId() + " extracting the evidence of " + params.size()
					+ " writing window(s) through " + windows.size() + " extraction window(s), extractorBudget:"
					+ extractorBudget + " (tok) finalBudget:" + finalBudget + " (tok)");
		}
		notificationSink.next(
				"Agent: " + contextAgentPersona.getNetworkAgentName() + " is extracting the evidence of "
						+ windows.size() + " part(s)..",
				ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
		return extractAll(windows, agentModel, extractorPrompt, chatRequestContext, runAs, failures, emitter)
				.publishOn(runAs.wrap(Schedulers.boundedElastic())).flatMapMany(extractions -> {
					try {
						return runAs.doRunAsWithReturnAndException(() -> {
							List<String> reduced = reduceExtractions(extractions, finalBudget, groupBudget,
									extractorParams, agentModel, extractorPrompt, chatRequestContext, runAs, failures,
									emitter, contextAgentPersona, notificationSink);
							StringBuilder evidence = new StringBuilder(String.join(NEWLINE, reduced));
							if (failures.get() > 0) {
								LOGGER.warn("Report writer agent id:" + getId() + " " + failures.get()
										+ " evidence extraction call(s) failed, the answer is written without them");
								evidence.append(NEWLINE).append("NOTE: ").append(failures.get())
										.append(" part(s) of the evidence could not be analysed because of model errors,"
												+ " the evidence above is incomplete.");
							}
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Writing the final report from " + reduced.size() + " extraction(s) of "
										+ ITokensCountable.stringsTokensSize(evidence.toString())
										+ " (tok), failed extraction call(s):" + failures.get());
							}
							Map<String, Object> writing = new HashMap<>(finalParams);
							writing.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, evidence.toString());
							if (LOGGER.isTraceEnabled()) {
								LOGGER.trace("<EXTRACTED_EVIDENCE>");
								LOGGER.trace(evidence.toString());
								LOGGER.trace("</EXTRACTED_EVIDENCE>");
							}
							return writeReactive(agentModel, agentPrompt, chatRequestContext, writing, notificationSink);
						});
					} catch (LLMConfigException e) {
						return Flux.error(e);
					}
				});
	}

	/**
	 * Runs the extraction prompt over every window, a few at a time, keeping the
	 * windows' order; a failed call is logged, counted and left out.
	 */
	protected Mono<List<String>> extractAll(List<Map<String, Object>> windows, IGConfigurableChatModel agentModel,
			GPromptTemplateConfig extractorPrompt, IChatRequestContext chatRequestContext, ReactiveIdentityUtil runAs,
			AtomicInteger failures, ISinkUIEmitter emitter) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extractAll(...) report writer agent id:" + getId() + " over " + windows.size()
					+ " window(s), parallelism:" + EXTRACTION_PARALLELISM);
		}
		final AtomicInteger windowNumber = new AtomicInteger();
		return Flux.fromIterable(windows).flatMapSequential(window -> Mono
				.fromCallable(() -> runAs.doRunAsWithReturnAndException(() -> {
					final int number = windowNumber.incrementAndGet();
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Extracting the evidence of window " + number + " of " + windows.size() + " size:"
								+ ITokensCountable.stringsTokensSize(String.valueOf(
										window.get(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM)))
								+ " (tok)");
					}
					String extraction = agentModel.textResponse(extractorPrompt, window, chatRequestContext);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Extracted the evidence of window " + number + " of " + windows.size()
								+ " into " + ITokensCountable.stringsTokensSize(extraction) + " (tok), relevant:"
								+ isRelevantExtraction(extraction));
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<EVIDENCE_EXTRACTION window=" + number + ">");
						LOGGER.trace(extraction);
						LOGGER.trace("</EVIDENCE_EXTRACTION>");
					}
					return extraction;
				}))
				.subscribeOn(runAs.wrap(Schedulers.boundedElastic())).onErrorResume(error -> {
					LOGGER.error("Report writer agent id:" + getId() + " evidence extraction call failed", error);
					failures.incrementAndGet();
					try {
						emitter.notifyLLMProblems();
					} catch (Throwable th) {
						LOGGER.error("Cannot notify the evidence extraction failure", th);
					}
					return Mono.just("");
				}), EXTRACTION_PARALLELISM).filter(ReportWriterReactiveAgentServiceImpl::isRelevantExtraction)
				.collectList();
	}

	/**
	 * Reduces the extractions until they fit the final writing call: they are grouped
	 * as long as a group fits one extraction call, and each group is extracted again.
	 * When grouping no longer reduces them, the budget is shared equally among them
	 * as a last resort.
	 */
	protected List<String> reduceExtractions(List<String> extractions, int finalBudget, int groupBudget,
			Map<String, Object> extractorParams, IGConfigurableChatModel agentModel,
			GPromptTemplateConfig extractorPrompt, IChatRequestContext chatRequestContext, ReactiveIdentityUtil runAs,
			AtomicInteger failures, ISinkUIEmitter emitter, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink) {
		List<String> current = extractions;
		int round = 0;
		while (current.size() > 1 && ITokensCountable.stringsTokensSize(String.join(NEWLINE, current)) > finalBudget
				&& round < MAX_REDUCTION_ROUNDS) {
			List<String> groups = groupToBudget(current, groupBudget);
			if (groups.size() >= current.size()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("reduceExtractions(...) " + current.size()
							+ " extraction(s) cannot be grouped in a budget of " + groupBudget + " (tok)");
				}
				break;
			}
			round++;
			notificationSink.next(
					"Agent: " + contextAgentPersona.getNetworkAgentName() + " is condensing " + current.size()
							+ " extraction(s) into " + groups.size() + "..",
					ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
			List<Map<String, Object>> groupParams = new ArrayList<>();
			for (String group : groups) {
				Map<String, Object> params = new HashMap<>(extractorParams);
				params.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, group);
				groupParams.add(params);
			}
			List<String> reduced = extractAll(groupParams, agentModel, extractorPrompt, chatRequestContext, runAs,
					failures, emitter).block();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("reduceExtractions(...) round " + round + " reduced " + current.size()
						+ " extraction(s) to " + (reduced != null ? reduced.size() : 0) + " of "
						+ (reduced != null ? ITokensCountable.stringsTokensSize(String.join(NEWLINE, reduced)) : 0)
						+ " (tok), final budget:" + finalBudget + " (tok)");
			}
			if (LOGGER.isTraceEnabled() && reduced != null) {
				LOGGER.trace("<REDUCED_EXTRACTIONS round=" + round + ">");
				LOGGER.trace(String.join(NEWLINE, reduced));
				LOGGER.trace("</REDUCED_EXTRACTIONS>");
			}
			if (reduced == null || reduced.isEmpty()) {
				break;
			}
			current = reduced;
		}
		int size = ITokensCountable.stringsTokensSize(String.join(NEWLINE, current));
		if (size > finalBudget) {
			LOGGER.warn("Report writer agent id:" + getId() + " extractions of " + size
					+ " (tok) still over the final budget of " + finalBudget + " (tok), each is cut to an equal share");
			return fitEqually(current, finalBudget);
		}
		return current;
	}

	/**
	 * Joins consecutive extractions into groups of at most the budget each; an
	 * extraction alone over the budget is a group of its own.
	 */
	static List<String> groupToBudget(List<String> extractions, int budget) {
		List<String> groups = new ArrayList<>();
		StringBuilder group = new StringBuilder();
		int used = 0;
		for (String extraction : extractions) {
			int size = ITokensCountable.stringsTokensSize(extraction);
			if (group.length() > 0 && used + size > budget) {
				groups.add(group.toString());
				group.setLength(0);
				used = 0;
			}
			if (group.length() > 0) {
				group.append(NEWLINE);
			}
			group.append(extraction);
			used += size;
		}
		if (group.length() > 0) {
			groups.add(group.toString());
		}
		return groups;
	}

	static boolean isRelevantExtraction(String extraction) {
		return extraction != null && !extraction.isBlank() && !extraction.strip().equals(NO_RELEVANT_FACTS);
	}

	private static int tokensOf(Map<String, Object> params) {
		int tokens = 0;
		for (Object value : params.values()) {
			if (value != null) {
				tokens += ITokensCountable.stringsTokensSize(value.toString());
			}
		}
		return tokens;
	}

	/** The extraction prompt's answer for a part with nothing relevant. */
	static final String NO_RELEVANT_FACTS = "NO RELEVANT FACTS";
	/** Extraction calls run at the same time. */
	static final int EXTRACTION_PARALLELISM = 4;
	/** Rounds of reduction of the extractions before sharing the budget among them. */
	static final int MAX_REDUCTION_ROUNDS = 3;

	private ISinkUIEmitter toSinkUIEmitter(INotificationSink notificationSink) {
		return new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					NotificationType notificationType) {
				ChatNotificationContent chatNotification = new ChatNotificationContent();
				chatNotification.setCode(code);
				chatNotification.setDuration(duration);
				chatNotification.setIcon(icon);
				chatNotification.setMessage(message);
				chatNotification.setNotificationType(notificationType);
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(chatNotification);
				next(envelope);
			}

			@Override
			public synchronized void next(GeboChatMessageEnvelope event) {
				if (event.getContent() instanceof ChatNotificationContent content) {

					if (notificationSink != null) {
						NotificationObject object = new NotificationObject(content.getCode(), content.getMessage(),
								content.getIcon(),
								ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
						notificationSink.next(object);
					}
				}
			}

			@Override
			public void error(Throwable error) {
				LOGGER.error("Error in report writer token-budget coordination", error);
			}

			@Override
			public void complete() {

			}
		};
	}

	protected String createCycleHistoryVariable(
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> privateMemory, String request) {
		List<GeboChatResponse> pastResponses = new ArrayList<GeboChatResponse>();
		for (AgentPrivateSessionContext<String, GeboChatMessageEnvelope>.AgentInteraction interaction : privateMemory
				.getInteractions()) {
			pastResponses.add((GeboChatResponse) interaction.getOutput().getContent());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createCycleHistoryVariable(...) rendering " + pastResponses.size()
					+ " past agent loop(s)");
		}
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		for (GeboChatResponse geboChatResponse : pastResponses) {
			buffer.append(BEGIN_AGENT_LOOP + index);
			buffer.append(NEWLINE);

			if (geboChatResponse.getCalledFunctions() != null && !geboChatResponse.getCalledFunctions().isEmpty()) {
				int tcIndex = 0;
				for (CalledFunction callF : geboChatResponse.getCalledFunctions()) {
					buffer.append(
							TOOL_CALLED + tcIndex + ": " + callF.getFunctionName() + " params:"
									+ callF.getParamsDescription());
					buffer.append(NEWLINE);
					tcIndex++;
				}
			}
			buffer.append(RESPONSE + geboChatResponse.getQueryResponse());
			buffer.append(NEWLINE);
			buffer.append(END_AGENT_LOOP + index);
			buffer.append(NEWLINE);
			index++;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End createCycleHistoryVariable(...) rendered " + buffer.length() + " character(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<" + AGENT_SESSION_STORY_PROMPT_PARAM + ">");
			LOGGER.trace(buffer.toString());
			LOGGER.trace("</" + AGENT_SESSION_STORY_PROMPT_PARAM + ">");
		}
		return buffer.toString();
	}

	protected List<CalledFunction> renderFunctions(List<ToolCallExecuted> calls) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("renderFunctions(...) rendering " + (calls != null ? calls.size() : 0)
					+ " executed tool call(s)");
		}
		if (LOGGER.isTraceEnabled() && calls != null) {
			for (ToolCallExecuted call : calls) {
				LOGGER.trace("<EXECUTED_TOOL_CALL name=" + call.getName() + ">");
				LOGGER.trace(String.valueOf(call.getToolInput()));
				LOGGER.trace("</EXECUTED_TOOL_CALL>");
			}
		}
		// the input goes to paramsDescription: params is not serialized, the user never saw it
		return calls != null ? calls.stream().map(ToolCallsListener::toCalledFunction).toList() : List.of();
	}

}
