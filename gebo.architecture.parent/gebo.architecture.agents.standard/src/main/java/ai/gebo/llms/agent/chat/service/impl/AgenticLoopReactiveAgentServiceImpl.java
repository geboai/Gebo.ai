/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.util.json.JsonParser;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.agent.standardtools.DeepSearchToolSource;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDeepSearchTool;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource;
import ai.gebo.llms.agent.standardtools.StandardSearchesToolsImpl;
import ai.gebo.llms.agent.standardtools.ToolsFoundDocuments;
import ai.gebo.llms.agent.standardtools.ToolsProgress;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.agent.standardtools.WebSearchToolSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * A single agent with tools, working in the classic agentic loop over the network
 * of agents: at each iteration the model uses its tools as it sees fit, then either
 * answers and ends the loop, or asks for another iteration by ending its text with
 * a control marker. Every iteration is streamed to the user without the markers,
 * and the next one receives the history of the previous ones (their text and the
 * tools they called). The loop ends when the model says so, or after
 * {@link #maxIterations(AgentNetworkParticipant)} iterations.
 * <p>
 * It runs as the output node of its own network, reusing the report writer's
 * streaming output and final response (documents and called functions).
 */
@Service
public class AgenticLoopReactiveAgentServiceImpl extends ReportWriterReactiveAgentServiceImpl {
	public static final String AGENTIC_LOOP_NETWORK_AGENT_SERVICE = "AgenticLoopNetworkAgentService";
	private static final String DESCRIPTION = "Single agent that operates every available tool in a loop and decides by itself when the answer is complete";
	/** Iterations of the loop when the network does not set them. */
	static final int DEFAULT_MAX_ITERATIONS = 5;
	static final String MAX_ITERATIONS_PARAM = "MAX_ITERATIONS";
	/**
	 * The rules of the chat, also given next to the user's question: appended at the end
	 * of the long system prompt only, the model kept the prompt's defaults over them.
	 */
	static final String RULES_TO_FOLLOW_PARAM = "RULES_TO_FOLLOW";
	private static final String NEWLINE = "\r\n";

	public AgenticLoopReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

	@Override
	public String getId() {
		return AGENTIC_LOOP_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability("Operate the available tools in a loop until the user request is fully answered");
		return capabilities;
	}

	/**
	 * This agent is the one that operates the tools, so it mounts all of them, the
	 * ones kept out of the other agents' automatic mounting included.
	 */
	@Override
	protected List<String> filterAutoMountedTools(List<String> toolNames) {
		return toolNames;
	}

	/**
	 * When the persona may notify the user, the model of the loop can tell the user
	 * what it is doing with the {@code notifyUser} tool, as the other tool-calling
	 * agents of the network do.
	 */
	@Override
	protected List<ToolCallback> additionalTools(AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink) {
		if (!mayNotifyUser(contextAgentPersona, notificationSink)) {
			return null;
		}
		return List.of(createUserMessageTool(notificationSink));
	}

	private static boolean mayNotifyUser(AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink) {
		return notificationSink != null && contextAgentPersona != null && contextAgentPersona.isAllowedToNotifyUser();
	}

	/** One iteration of the loop: what the model wrote and the tools it called. */
	record LoopIteration(int number, String text, List<ToolCallExecuted> calls) {
	}

	/**
	 * What the next iteration is told of an iteration whose answer was discarded
	 * because it used no tool giving the sources' evidence (see
	 * {@link #needsEvidence(DeliverableIntent)}).
	 */
	static final String DISCARDED_WITHOUT_EVIDENCE = "This answer was discarded, the user never saw it: it used no search tool, "
			+ "while the user asked for a deliverable that must rest on what the sources contain now (the chat history "
			+ "is not a source). Search the sources with the tools first, then answer from what they return.";

	/**
	 * The deliverables built on the sources' evidence: an analysis or report, a pure
	 * search. An answer to them that used no tool rests on the model's memory or on
	 * the chat history only.
	 */
	static boolean needsEvidence(DeliverableIntent intent) {
		return intent == DeliverableIntent.ANALISYS || intent == DeliverableIntent.PURE_SEARCH;
	}

	/**
	 * The tool sources whose tools return what the sources contain: a call to one of
	 * their tools is the evidence an answer rests on. The knowledge base browsing tools
	 * are among them: they list the documents of the chat's knowledge bases and read
	 * them whole. The other tools (the date, the users, notifyUser...) are not.
	 */
	static final Set<String> EVIDENCE_TOOL_SOURCES = Set.of(
			InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE,
			KnowledgeBaseBrowsingToolSource.KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE,
			WebSearchToolSource.WEB_SEARCH_TOOL_SOURCE, StandardSearchesToolsImpl.STANDARD_SEARCHES_TOOLS_SOURCE,
			DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE, "GArtifactInformationsSearchFunctionsFactory");

	/**
	 * The tools whose call is the evidence the answer must rest on, among the ones the
	 * agent model mounts; none when the deliverable does not need the sources'
	 * evidence. An analysis needs a deep search when the agent has one (a plain search
	 * returns a few fragments), or the whole text of the documents (the knowledge base
	 * documents read whole); any other deliverable any search.
	 */
	protected Set<String> evidenceTools(DeliverableIntent intent, IGConfigurableChatModel<?> agentModel) {
		if (!needsEvidence(intent)) {
			return Set.of();
		}
		final MountedSearchTools mounted = searchTools(agentModel);
		final Set<String> evidence;
		if (intent == DeliverableIntent.ANALISYS && !mounted.deepSearches().isEmpty()) {
			evidence = new LinkedHashSet<>(mounted.deepSearches());
			if (mounted.searches().contains(KnowledgeBaseBrowsingToolSource.DOCUMENT_CONTENTS_TOOL)) {
				evidence.add(KnowledgeBaseBrowsingToolSource.DOCUMENT_CONTENTS_TOOL);
			}
		} else {
			evidence = mounted.searches();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " deliverable:" + intent + " evidence tools:" + evidence);
		}
		return evidence;
	}

	/**
	 * The search tools the agent model mounts, the deep searches among them, and the
	 * ones reaching the internal knowledge bases (search, browsing, deep search).
	 */
	record MountedSearchTools(Set<String> searches, Set<String> deepSearches, Set<String> knowledgeBaseTools) {
		MountedSearchTools(Set<String> searches, Set<String> deepSearches) {
			this(searches, deepSearches, Set.of());
		}
	}

	/** The tools of the search sources (see {@link #EVIDENCE_TOOL_SOURCES}) the agent model mounts. */
	protected MountedSearchTools searchTools(IGConfigurableChatModel<?> agentModel) {
		final List<String> enabled = agentModel != null && agentModel.getConfig() != null
				? agentModel.getConfig().getEnabledFunctions()
				: null;
		if (enabled == null || enabled.isEmpty() || toolsRepositoryPattern == null) {
			return new MountedSearchTools(Set.of(), Set.of());
		}
		final Set<String> searches = new LinkedHashSet<>();
		final Set<String> deepSearches = new LinkedHashSet<>();
		final Set<String> knowledgeBaseTools = new LinkedHashSet<>();
		for (IGToolCallbackSource source : toolsRepositoryPattern.getImplementations()) {
			if (source == null || !EVIDENCE_TOOL_SOURCES.contains(source.getId())) {
				continue;
			}
			try {
				for (ToolCallback tool : source.getToolCallbacks()) {
					final String name = tool.getToolDefinition().name();
					if (enabled.contains(name)) {
						searches.add(name);
						if (DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE.equals(source.getId())) {
							deepSearches.add(name);
						}
						if (InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE
								.equals(source.getId())
								|| KnowledgeBaseBrowsingToolSource.KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE.equals(source.getId())
								|| KnowledgeBaseDeepSearchTool.DEEP_SEARCH_KNOWLEDGE_BASE_TOOL.equals(name)) {
							knowledgeBaseTools.add(name);
						}
					}
				}
			} catch (RuntimeException e) {
				LOGGER.warn("Agentic loop agent id:" + getId() + " could not read the tools of source:"
						+ source.getId() + ": " + e);
			}
		}
		return new MountedSearchTools(searches, deepSearches, knowledgeBaseTools);
	}

	/**
	 * How the first iteration's answer is checked before the user sees it: held back
	 * until it calls one of {@code tools} (none: not held back); when it called none,
	 * discarded and done again once if the deliverable needs the sources' evidence, or
	 * if it cites document files not among {@code readDocumentNames} (the chat's own
	 * documents: nothing else was read).
	 */
	record SourceGate(Set<String> tools, boolean evidenceRequired, Collection<String> readDocumentNames,
			CoverageGate coverage) {
		static final SourceGate NONE = new SourceGate(Set.of(), false, List.of());

		SourceGate(Set<String> tools, boolean evidenceRequired, Collection<String> readDocumentNames) {
			this(tools, evidenceRequired, readDocumentNames, null);
		}
	}

	/**
	 * The coverage part of the gate: an answer that follows a deep search whose
	 * coverage asks to be completed ({@code coverage.completionRequired} in its result,
	 * see {@link ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage}) is held
	 * back until a search of the same kind of source follows it (a knowledge base deep
	 * search by a knowledge base tool, any other by a tool of the other sources); when
	 * the iteration ends without one, its answer is discarded unseen and done again
	 * once, told what to complete.
	 *
	 * @param searchTools        the search tools mounted (what completes a coverage)
	 * @param deepSearchTools    the deep search tools mounted (what reports a coverage)
	 * @param knowledgeBaseTools the tools reaching the internal knowledge bases
	 */
	record CoverageGate(Set<String> searchTools, Set<String> deepSearchTools, Set<String> knowledgeBaseTools) {
	}

	/** What a deep search result says of its coverage: whether to complete it, and why. */
	record CoverageVerdict(boolean completionRequired, String note) {
	}

	/** What the next iteration is told of an answer discarded for a thin deep search coverage. */
	static final String DISCARDED_THIN_COVERAGE = "This answer was discarded, the user never saw it: it was written "
			+ "on a deep search whose coverage is thin, without completing it. Complete what is missing with focused "
			+ "searches, documents read whole or a deep search aimed at it, then answer. The coverage: ";

	/** What the next iteration is told of an answer discarded for citing documents it did not read. */
	static final String DISCARDED_UNREAD_CITATIONS = "This answer was discarded, the user never saw it: it cites documents "
			+ "it did not read in this request (what the chat history says of them is not their content). Search them "
			+ "with the tools before citing them, or answer without citing them. Documents cited without being read: ";

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, String request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mySessionContext,
			IGConfigurableChatModel agentModel, GAgentRole agentRole, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener) throws LLMConfigException, AgentException {
		final int maxIterations = maxIterations(contextAgentPersona);
		final int budget = agentTokenBudget(agentModel, agentPrompt, chatRequestContext);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createResponse(...) agentic loop agent id:" + getId() + " maxIterations:"
					+ maxIterations + " budget:" + budget + " (tok)");
		}
		// The kind of deliverable the user asked for (a direct answer, an analysis...)
		// shapes every iteration, as it shapes the report writer's answer.
		final DeliverableIntent userIntent = sessionUserIntent(session);
		final Map<String, Object> deliverableParams = deliverableTemplateParams(userIntent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " shapes its answer for the deliverable:"
					+ userIntent.name());
		}
		final List<LoopIteration> history = new ArrayList<>();
		// The search and deep search tools called by the loop add their documents to this
		// collector, shared through the tools context of the loop's model calls: they
		// become the answer's documents.
		final ToolsFoundDocuments toolDocuments = new ToolsFoundDocuments();
		IChatRequestContext loopContext = toolDocuments.sharedThrough(chatRequestContext);
		if (mayNotifyUser(contextAgentPersona, notificationSink)) {
			// as the other tool-calling agents of the network, each tool used is told to the
			// user; the search tools also tell what they are doing while they work
			loopContext = IChatRequestContext.forAgent(ToolsProgress.sharedThrough(loopContext, notificationSink),
					notifyingToolCallsListener(contextAgentPersona, notificationSink, callBacksListener));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " notifies the user of its tools' work");
			}
		}
		// an answer to a request for an analysis or a search must rest on the tools' results:
		// the first iteration is held back until it uses a tool (see iteration(...))
		final Set<String> evidenceTools = evidenceTools(userIntent, agentModel);
		final SourceGate gate;
		if (!evidenceTools.isEmpty()) {
			// an analysis or a search on a deep search whose coverage is thin is completed
			// before the answer (see CoverageGate)
			final MountedSearchTools mounted = searchTools(agentModel);
			final CoverageGate coverage = mounted.deepSearches().isEmpty() ? null
					: new CoverageGate(mounted.searches(), mounted.deepSearches(), mounted.knowledgeBaseTools());
			gate = new SourceGate(evidenceTools, true, chatDocumentNames(chatRequestContext), coverage);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " coverage gate:" + (coverage != null)
						+ (coverage != null ? " deep searches:" + coverage.deepSearchTools() + " knowledge base tools:"
								+ coverage.knowledgeBaseTools() : ""));
			}
		} else {
			// any other answer may not cite documents it did not read: it is checked when it
			// used no search
			final Set<String> searches = searchTools(agentModel).searches();
			gate = searches.isEmpty() ? SourceGate.NONE
					: new SourceGate(searches, false, chatDocumentNames(chatRequestContext));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " evidence required:" + gate.evidenceRequired()
					+ " answer held until a search:" + !gate.tools().isEmpty());
		}
		Flux<String> text = iteration(1, maxIterations, budget, history, agentModel, agentPrompt, loopContext,
				contextAgentPersona, notificationSink, callBacksListener, deliverableParams, gate, runAs);
		final GeboChatResponse response = new GeboChatResponse();
		return renderOutputStream(text, response, session, contextAgentPersona, notificationSink, callBacksListener)
				.doOnNext(operation -> {
					if (operation != null && operation.getData() != null
							&& operation.getData().getContent() == response) {
						final int before = response.getDocumentsRef() != null ? response.getDocumentsRef().size() : 0;
						response.setDocumentsRef(toolDocuments.mergeInto(response.getDocumentsRef()));
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " answer documents: " + before
									+ " from the session, " + response.getDocumentsRef().size()
									+ " with the search tools' ones");
						}
						warnAboutUnreadCitations(response, chatRequestContext, toolDocuments);
					}
				});
	}

	/** A document file name as an answer cites it. */
	static final Pattern CITED_DOCUMENT = Pattern.compile(
			"[\\p{L}\\p{N}_.\\-]+\\.(?:pdf|docx?|xlsx?|pptx?|odt|ods|odp|rtf|txt|md|csv|html?|xml|json|epub)\\b",
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);

	/**
	 * The document file names the answer cites that this request did not read: not
	 * among the documents found by its tools nor among the documents of the chat. Their
	 * content can only come from earlier answers of the chat or the model's memory.
	 */
	static List<String> unreadCitations(String answer, Collection<String> readDocumentNames) {
		if (answer == null || answer.isBlank()) {
			return List.of();
		}
		final Set<String> read = new HashSet<>();
		for (String name : readDocumentNames) {
			if (name != null) {
				read.add(name.trim().toLowerCase());
			}
		}
		final Set<String> unread = new LinkedHashSet<>();
		final Matcher matcher = CITED_DOCUMENT.matcher(answer);
		while (matcher.find()) {
			final String cited = matcher.group();
			final String lowerCited = cited.toLowerCase();
			// a name with spaces is cited by its last part
			if (read.stream().noneMatch(name -> name.equals(lowerCited) || name.endsWith(" " + lowerCited)
					|| name.endsWith("/" + lowerCited))) {
				unread.add(cited);
			}
		}
		return new ArrayList<>(unread);
	}

	/**
	 * The names an answer may cite a document by: its name and, when it comes from a
	 * search result, the address of the result (without its query) both as it is and
	 * decoded, a cited file name matching the address's last part.
	 */
	static List<String> citableNames(GResponseDocumentRef ref) {
		final List<String> names = new ArrayList<>();
		if (ref == null) {
			return names;
		}
		if (ref.getName() != null) {
			names.add(ref.getName());
		}
		final ai.gebo.architecture.search.model.SearchResult result = ref.getNestedSearchResult();
		if (result != null) {
			if (result.getResultReference() != null) {
				addAddress(names, result.getResultReference().getUri());
				if (result.getResultReference().getName() != null) {
					names.add(result.getResultReference().getName());
				}
			}
			if (result.getNavigationReference() != null && result.getNavigationReference().path != null) {
				addAddress(names, result.getNavigationReference().path.absolutePath);
				if (result.getNavigationReference().path.name != null) {
					names.add(result.getNavigationReference().path.name);
				}
			}
		}
		return names;
	}

	private static void addAddress(List<String> names, String address) {
		if (address == null || address.isBlank()) {
			return;
		}
		String path = address.trim();
		final int cut = indexOfAny(path, '?', '#');
		if (cut >= 0) {
			path = path.substring(0, cut);
		}
		names.add(path);
		try {
			final String decoded = java.net.URLDecoder.decode(path, java.nio.charset.StandardCharsets.UTF_8);
			if (!decoded.equals(path)) {
				names.add(decoded);
			}
		} catch (IllegalArgumentException e) {
			// not an encoded address: kept as it is
		}
	}

	private static int indexOfAny(String text, char... chars) {
		int first = -1;
		for (char c : chars) {
			final int index = text.indexOf(c);
			if (index >= 0 && (first < 0 || index < first)) {
				first = index;
			}
		}
		return first;
	}

	/** The file names of the chat's own documents (chosen or uploaded by the user). */
	static List<String> chatDocumentNames(IChatRequestContext chatRequestContext) {
		final List<String> names = new ArrayList<>();
		if (chatRequestContext != null && chatRequestContext.getDocuments() != null) {
			for (Document document : chatRequestContext.getDocuments()) {
				if (document != null && document.getMetadata() != null) {
					final Object name = document.getMetadata().get(DocumentMetaInfos.GEBO_FILE_NAME);
					if (name != null) {
						names.add(String.valueOf(name));
					}
				}
			}
		}
		return names;
	}

	/**
	 * Tells the user, with a warning on the answer, which cited documents the answer
	 * did not read in this request: the answer text is left as it is.
	 */
	protected void warnAboutUnreadCitations(GeboChatResponse response, IChatRequestContext chatRequestContext) {
		warnAboutUnreadCitations(response, chatRequestContext, null);
	}

	/**
	 * Tells the user, with a warning on the answer, which cited documents the answer
	 * did not read in this request: the answer text is left as it is. The documents of
	 * the answer count by their name and, for a web page or an external system's
	 * document, by its address (an answer cites a page by the file name its address
	 * ends with, the page being named after its site); the documents the tools only
	 * listed ({@code toolDocuments}) count by their name.
	 */
	protected void warnAboutUnreadCitations(GeboChatResponse response, IChatRequestContext chatRequestContext,
			ToolsFoundDocuments toolDocuments) {
		final List<String> readNames = new ArrayList<>(chatDocumentNames(chatRequestContext));
		if (response.getDocumentsRef() != null) {
			for (GResponseDocumentRef ref : response.getDocumentsRef()) {
				readNames.addAll(citableNames(ref));
			}
		}
		if (toolDocuments != null) {
			readNames.addAll(toolDocuments.getListedNames());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Agentic loop agent id:" + getId() + " names the answer may cite: " + readNames);
		}
		final List<String> unread = unreadCitations(response.getQueryResponse(), readNames);
		if (unread.isEmpty()) {
			return;
		}
		LOGGER.warn("Agentic loop agent id:" + getId() + " answer cites " + unread.size()
				+ " document(s) not read for this request: " + unread);
		if (response.getBackendMessages() != null) {
			response.getBackendMessages().add(GUserMessage.warnMessage("Sources not read for this answer",
					"The answer cites " + String.join(", ", unread)
							+ ", not read for this request: what it says of them may come from earlier answers."));
		}
	}

	/**
	 * The iterations of the loop, from the given one: the model's streamed text
	 * without the control markers, followed by the next iteration when the model
	 * asked for one and the limit is not reached.
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener) {
		return iteration(number, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
				contextAgentPersona, notificationSink, callBacksListener,
				deliverableTemplateParams(DeliverableIntent.SUMMARY));
	}

	/**
	 * The iterations of the loop, from the given one, with the prompt parameters
	 * shaping the deliverable the user asked for (see
	 * {@link #deliverableTemplateParams(DeliverableIntent)}).
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener,
			Map<String, Object> deliverableParams) {
		return iteration(number, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
				contextAgentPersona, notificationSink, callBacksListener, deliverableParams, false);
	}

	/**
	 * The iterations of the loop, from the given one. When {@code evidenceRequired},
	 * the iteration's text is held back until it uses a tool (models call their tools
	 * before writing, so it then streams as usual); an iteration that ends without
	 * using any tool is discarded unseen and the next one is told why, once: the
	 * iteration after a discarded one streams whatever it does.
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener,
			Map<String, Object> deliverableParams, boolean evidenceRequired) {
		return iteration(number, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
				contextAgentPersona, notificationSink, callBacksListener, deliverableParams,
				evidenceRequired ? new SourceGate(ANY_SEARCH, true, List.of()) : SourceGate.NONE, null);
	}

	/**
	 * The iterations of the loop, from the given one, each further iteration running
	 * as the user ({@code runAs}): it starts on the thread the previous one's model
	 * stream ended on, which carries no identity, and its model call samples the
	 * identity its tools run with.
	 */
	protected Flux<String> iteration(int number, int maxIterations, int budget, List<LoopIteration> history,
			IGConfigurableChatModel agentModel, GPromptTemplateConfig agentPrompt,
			IChatRequestContext chatRequestContext, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ToolCallsListener callBacksListener,
			Map<String, Object> deliverableParams, SourceGate gate, ReactiveIdentityUtil runAs) {
		return Flux.defer(() -> {
			final Map<String, Object> params = new HashMap<>(deliverableParams);
			params.put(CURRENT_ITERATION_PROMPT_PARAM, number);
			params.put(MAX_ITERATIONS_PARAM, maxIterations);
			params.put(AGENT_CONTROL_FINISHED_PROMPT_PARAM, AGENT_CONTROL_FINISHED);
			params.put(AGENT_CONTROL_CONTINUE_PROMPT_PARAM, AGENT_CONTROL_MORE_TOOLS);
			params.put(AGENT_SESSION_STORY_PROMPT_PARAM, loopStory(history, budget));
			params.put(RULES_TO_FOLLOW_PARAM, rulesToFollow(chatRequestContext));
			final int callsBefore = callBacksListener.getCalls().size();
			final ControlMarkerStripper stripper = new ControlMarkerStripper();
			final StringBuilder text = new StringBuilder();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " of " + maxIterations);
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<AGENTIC_LOOP_STORY iteration=" + number + ">");
				LOGGER.trace(String.valueOf(params.get(AGENT_SESSION_STORY_PROMPT_PARAM)));
				LOGGER.trace("</AGENTIC_LOOP_STORY>");
			}
			Flux<String> modelText;
			try {
				// the tools' results of this iteration pile up in its model call: they may take
				// what the loop's budget leaves after this iteration's own placeholders
				modelText = callLLMReactive(agentModel, agentPrompt,
						chatRequestContext != null
								? chatRequestContext.withToolsRoom(ToolsTokenBudget.leftForTools(budget, params))
								: null,
						params);
			} catch (LLMConfigException e) {
				return Flux.error(e);
			}
			// the text held back while the iteration has not used a tool yet
			final StringBuilder held = new StringBuilder();
			final boolean[] open = { gate == null || gate.tools().isEmpty() };
			// the coverage the deep searches of this iteration ask to complete, re-read as calls arrive
			final CoverageWatch coverageWatch = new CoverageWatch(gate != null ? gate.coverage() : null,
					callBacksListener, callsBefore);
			Flux<String> visible = modelText.map(chunk -> {
				String out = stripper.accept(chunk);
				text.append(out);
				return passed(out, held, open, callBacksListener, callsBefore, gate.tools(), coverageWatch);
			}).concatWith(Flux.defer(() -> {
				String tail = stripper.complete();
				text.append(tail);
				return Flux.just(passed(tail, held, open, callBacksListener, callsBefore, gate.tools(), coverageWatch));
			})).filter(chunk -> !chunk.isEmpty());
			Flux<String> next = Flux.defer(() -> {
				List<ToolCallExecuted> calls = callBacksListener.getCalls();
				final List<ToolCallExecuted> iterationCalls = new ArrayList<>(
						calls.subList(Math.min(callsBefore, calls.size()), calls.size()));
				// held for a deep search coverage to complete: the sources were searched
				final String coverageNote = coverageWatch.pending();
				final boolean heldForCoverage = !open[0] && coverageNote != null
						&& usedEvidenceTool(callBacksListener, callsBefore, gate.tools());
				// no search used: the answer has not been shown yet
				final List<String> unread = open[0] || heldForCoverage || gate.evidenceRequired() ? List.of()
						: unreadCitations(text.toString(), gate.readDocumentNames());
				final boolean discard = !open[0] && !heldForCoverage
						&& (gate.evidenceRequired() || !unread.isEmpty());
				Flux<String> released = Flux.empty();
				if (!open[0] && !discard) {
					// nothing to check against: the answer is shown as it is
					released = Flux.just(held.toString()).filter(chunk -> !chunk.isEmpty());
				}
				if (discard) {
					if (number < maxIterations) {
						history.add(new LoopIteration(number,
								gate.evidenceRequired() ? discardedNote(gate.tools())
										: DISCARDED_UNREAD_CITATIONS + String.join(", ", unread) + ".",
								iterationCalls));
						LOGGER.info("Agentic loop agent id:" + getId() + " iteration " + number
								+ (gate.evidenceRequired()
										? " answered without using any tool a request needing the sources' evidence"
										: " answered citing documents not read: " + unread)
								+ ": discarded, searching the sources in the next iteration");
						if (LOGGER.isTraceEnabled()) {
							LOGGER.trace("<AGENTIC_LOOP_DISCARDED_ITERATION number=" + number + ">");
							LOGGER.trace(text.toString());
							LOGGER.trace("</AGENTIC_LOOP_DISCARDED_ITERATION>");
						}
						notificationSink.next(
								"Agent: " + contextAgentPersona.getNetworkAgentName()
										+ " searches the sources before answering..",
								ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
						return asUser(runAs, iteration(number + 1, maxIterations, budget, history, agentModel,
								agentPrompt, chatRequestContext, contextAgentPersona, notificationSink, callBacksListener,
								deliverableParams, SourceGate.NONE, runAs));
					}
					// no iteration left: the answer is shown as it is
					LOGGER.warn("Agentic loop agent id:" + getId() + " last iteration " + number
							+ " used no search although its answer needed one: answering anyway");
					history.add(new LoopIteration(number, text.toString(), iterationCalls));
					return Flux.just(held.toString()).filter(chunk -> !chunk.isEmpty());
				}
				history.add(new LoopIteration(number, text.toString(), iterationCalls));
				final Flux<String> releasedText = released;
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<AGENTIC_LOOP_ITERATION number=" + number + ">");
					for (ToolCallExecuted call : history.get(history.size() - 1).calls()) {
						LOGGER.trace("tool called: " + call.getName());
					}
					LOGGER.trace(text.toString());
					LOGGER.trace("</AGENTIC_LOOP_ITERATION>");
				}
				boolean another = stripper.isContinueRequested() && number < maxIterations;
				if (heldForCoverage && !another) {
					if (number < maxIterations) {
						// the answer rests on a thin coverage: done again once, told what to complete
						history.set(history.size() - 1,
								new LoopIteration(number, DISCARDED_THIN_COVERAGE + coverageNote, iterationCalls));
						LOGGER.info("Agentic loop agent id:" + getId() + " iteration " + number
								+ " answered on a deep search coverage asking to be completed: discarded, completing it"
								+ " in the next iteration");
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " coverage to complete: " + coverageNote);
						}
						if (LOGGER.isTraceEnabled()) {
							LOGGER.trace("<AGENTIC_LOOP_DISCARDED_ITERATION number=" + number + ">");
							LOGGER.trace(text.toString());
							LOGGER.trace("</AGENTIC_LOOP_DISCARDED_ITERATION>");
						}
						notificationSink.next(
								"Agent: " + contextAgentPersona.getNetworkAgentName()
										+ " searches what the deep search did not cover..",
								ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
						return asUser(runAs, iteration(number + 1, maxIterations, budget, history, agentModel,
								agentPrompt, chatRequestContext, contextAgentPersona, notificationSink, callBacksListener,
								deliverableParams, SourceGate.NONE, runAs));
					}
					// no iteration left: the answer is shown as it is
					LOGGER.warn("Agentic loop agent id:" + getId() + " last iteration " + number
							+ " answered on a deep search coverage asking to be completed: answering anyway");
					return Flux.just(held.toString()).filter(chunk -> !chunk.isEmpty());
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " ended, tools called:"
							+ (calls.size() - Math.min(callsBefore, calls.size())) + " continue requested:"
							+ stripper.isContinueRequested() + " finish requested:" + stripper.isFinishRequested()
							+ " next iteration:" + another);
				}
				if (!another) {
					return releasedText;
				}
				notificationSink.next(
						"Agent: " + contextAgentPersona.getNetworkAgentName() + " goes on working (step " + (number + 1)
								+ " of " + maxIterations + ")..",
						ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
				return releasedText.concatWith(Flux.just(NEWLINE + NEWLINE)).concatWith(asUser(runAs, iteration(
						number + 1, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
						contextAgentPersona, notificationSink, callBacksListener, deliverableParams, SourceGate.NONE,
						runAs)));
			});
			return visible.concatWith(next);
		});
	}

	/**
	 * Stands for "any search tool" when the tools are not known: every tool but
	 * notifyUser counts (see {@link #usedEvidenceTool}).
	 */
	static final Set<String> ANY_SEARCH = Set.of("*");

	/** Whether a tool counting as evidence was called since {@code callsBefore}. */
	static boolean usedEvidenceTool(ToolCallsListener callBacksListener, int callsBefore, Set<String> evidenceTools) {
		final List<ToolCallExecuted> calls = callBacksListener.getCalls();
		for (ToolCallExecuted call : calls.subList(Math.min(callsBefore, calls.size()), calls.size())) {
			final String name = call.getName();
			if (evidenceTools == ANY_SEARCH ? !NOTIFY_USER_TOOL.equals(name) : evidenceTools.contains(name)) {
				return true;
			}
		}
		return false;
	}

	/** What the next iteration is told of the discarded one, with the tools that count. */
	static String discardedNote(Set<String> evidenceTools) {
		return evidenceTools == null || evidenceTools.isEmpty() || evidenceTools == ANY_SEARCH
				? DISCARDED_WITHOUT_EVIDENCE
				: DISCARDED_WITHOUT_EVIDENCE + " The answer must rest on the results of: " + String.join(", ", evidenceTools)
						+ ".";
	}

	/** The rules of the chat, one per line, or "none". */
	static String rulesToFollow(IChatRequestContext chatRequestContext) {
		final List<String> rules = chatRequestContext != null ? chatRequestContext.getRulesToFollow() : null;
		if (rules == null || rules.isEmpty()) {
			return "none";
		}
		final StringBuilder text = new StringBuilder();
		for (String rule : rules) {
			if (rule != null && !rule.isBlank()) {
				text.append("- ").append(rule.trim()).append(NEWLINE);
			}
		}
		return text.length() > 0 ? text.toString() : "none";
	}

	/** The iteration subscribed as the user, when the identity is known. */
	private static Flux<String> asUser(ReactiveIdentityUtil runAs, Flux<String> iteration) {
		return runAs != null ? iteration.subscribeOn(runAs.wrap(Schedulers.boundedElastic())) : iteration;
	}

	/**
	 * The part of the text that can be shown now: all of it once the iteration used a
	 * tool (with what was held back before), nothing while it has not ({@code open}
	 * stays false and the text is kept in {@code held}).
	 */
	private static String passed(String out, StringBuilder held, boolean[] open, ToolCallsListener callBacksListener,
			int callsBefore, Set<String> evidenceTools) {
		return passed(out, held, open, callBacksListener, callsBefore, evidenceTools, null);
	}

	/**
	 * The same, also holding the text back while a deep search coverage of the
	 * iteration asks to be completed ({@code coverageWatch}, null for none): it is
	 * released once a search of the same kind of source follows the deep search.
	 */
	private static String passed(String out, StringBuilder held, boolean[] open, ToolCallsListener callBacksListener,
			int callsBefore, Set<String> evidenceTools, CoverageWatch coverageWatch) {
		final boolean coverageToComplete = coverageWatch != null && coverageWatch.pending() != null;
		if (open[0]) {
			if (!coverageToComplete) {
				return out;
			}
			// a deep search asked to complete its coverage: what follows waits for it
			open[0] = false;
		}
		held.append(out);
		if (!coverageToComplete && usedEvidenceTool(callBacksListener, callsBefore, evidenceTools)) {
			open[0] = true;
			final String released = held.toString();
			held.setLength(0);
			return released;
		}
		return "";
	}

	/**
	 * The coverage the deep searches of an iteration ask to complete, re-read only when
	 * a tool call arrives (the deep search results are parsed once per call, not per
	 * streamed chunk).
	 */
	static final class CoverageWatch {
		private final CoverageGate gate;
		private final ToolCallsListener listener;
		private final int callsBefore;
		private int callsSeen = -1;
		private String pending = null;

		CoverageWatch(CoverageGate gate, ToolCallsListener listener, int callsBefore) {
			this.gate = gate;
			this.listener = listener;
			this.callsBefore = callsBefore;
		}

		/** The coverage note still to complete, or null. */
		synchronized String pending() {
			if (gate == null || listener == null) {
				return null;
			}
			final int calls = listener.getCalls().size();
			if (calls != callsSeen) {
				callsSeen = calls;
				pending = pendingCoverage(listener, callsBefore, gate);
			}
			return pending;
		}
	}

	/**
	 * The coverage still to complete after the calls since {@code callsBefore}: a deep
	 * search whose result asks to complete its coverage stays pending until a search of
	 * the same kind of source follows it (a knowledge base tool for a knowledge base
	 * deep search, a tool of the other sources for any other); a later deep search of
	 * the same kind replaces it, unless it was refused or failed. The notes of both kinds when both are pending; null
	 * when none is.
	 */
	static String pendingCoverage(ToolCallsListener listener, int callsBefore, CoverageGate gate) {
		String knowledgeBase = null;
		String others = null;
		final List<ToolCallExecuted> calls = listener.getCalls();
		for (ToolCallExecuted call : new ArrayList<>(calls.subList(Math.min(callsBefore, calls.size()), calls.size()))) {
			final String name = call.getName();
			final boolean ofKnowledgeBase = gate.knowledgeBaseTools().contains(name);
			if (gate.deepSearchTools().contains(name)) {
				if (didNotRun(call.getResult())) {
					// refused (searches repeated, deep searches used up) or failed: nothing completed
					continue;
				}
				final CoverageVerdict verdict = coverageOf(call.getResult());
				final String note = verdict != null && verdict.completionRequired()
						? (verdict.note() != null ? verdict.note() : "complete what the deep search did not cover")
						: null;
				if (ofKnowledgeBase) {
					knowledgeBase = note;
				} else {
					others = note;
				}
			} else if (gate.searchTools().contains(name)) {
				if (ofKnowledgeBase) {
					knowledgeBase = null;
				} else {
					others = null;
				}
			}
		}
		if (knowledgeBase != null && others != null) {
			return knowledgeBase + " " + others;
		}
		return knowledgeBase != null ? knowledgeBase : others;
	}

	private static final Pattern DID_NOT_RUN = Pattern.compile("\"status\"\\s*:\\s*\"(NOT_ALLOWED|FAILED)\"");

	/** Whether a tool result says the tool refused to run or failed (status NOT_ALLOWED or FAILED). */
	static boolean didNotRun(String result) {
		return result != null && DID_NOT_RUN.matcher(result).find();
	}

	private static final Pattern COMPLETION_REQUIRED =Pattern.compile("\"completionRequired\"\\s*:\\s*true");
	private static final Pattern COVERAGE_NOTE = Pattern.compile("\"note\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"");

	/**
	 * What a deep search result says of its coverage, read from its JSON (the coverage
	 * comes before the analysis, so a result cut to the room keeps it: when the whole
	 * JSON cannot be read, the coverage's own fields are looked for); null when the
	 * result says nothing of it or cannot be read, which never holds an answer back.
	 */
	@SuppressWarnings("unchecked")
	static CoverageVerdict coverageOf(String result) {
		if (result == null || !result.contains("completionRequired")) {
			return null;
		}
		try {
			final Map<String, Object> read = JsonParser.fromJson(result, Map.class);
			final Object coverage = read != null ? read.get("coverage") : null;
			if (coverage instanceof Map<?, ?> fields) {
				final boolean required = Boolean.TRUE.equals(fields.get("completionRequired"));
				final Object note = fields.get("note");
				return new CoverageVerdict(required, note != null ? String.valueOf(note) : null);
			}
			return null;
		} catch (RuntimeException e) {
			if (!COMPLETION_REQUIRED.matcher(result).find()) {
				return null;
			}
			final Matcher note = COVERAGE_NOTE.matcher(result);
			return new CoverageVerdict(true, note.find() ? note.group(1).replace("\\\"", "\"") : null);
		}
	}

	/**
	 * The history of the previous iterations for the next one: the tools each called
	 * and what it wrote, within half of the budget, shared equally among them; the
	 * other half is left to the chat history and the tool results of the iteration.
	 */
	protected String loopStory(List<LoopIteration> history, int budget) {
		if (history.isEmpty()) {
			return "No previous iteration: this is the first one.";
		}
		List<String> pieces = new ArrayList<>();
		for (LoopIteration iteration : history) {
			StringBuilder piece = new StringBuilder();
			piece.append("BEGIN_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			List<CalledFunction> functions = renderFunctions(iteration.calls());
			for (int i = 0; functions != null && i < functions.size(); i++) {
				piece.append("TOOL-CALLED-").append(i + 1).append(": ").append(functions.get(i).getFunctionName())
						.append(" params:").append(functions.get(i).getParams()).append(NEWLINE);
			}
			piece.append("RESPONSE: ").append(iteration.text()).append(NEWLINE);
			piece.append("END_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			pieces.add(piece.toString());
		}
		return String.join("", fitEqually(pieces, Math.max(budget / 2, MIN_SHARED_CONTEXT_TOKENS)));
	}

	/** The iterations the loop may run. */
	protected int maxIterations(AgentNetworkParticipant contextAgentPersona) {
		return DEFAULT_MAX_ITERATIONS;
	}

	/**
	 * Removes the loop control markers from the streamed text, remembering which one
	 * the model wrote. The markers can be split across chunks: the end of a chunk
	 * that could be the beginning of a marker is held back until the next one.
	 */
	static final class ControlMarkerStripper {
		private static final List<String> MARKERS = List.of(AGENT_CONTROL_FINISHED, AGENT_CONTROL_MORE_TOOLS);
		private static final int LONGEST_MARKER = MARKERS.stream().mapToInt(String::length).max().orElse(0);
		private final StringBuilder pending = new StringBuilder();
		private boolean continueRequested = false;
		private boolean finishRequested = false;

		/** The text of the chunk that can be shown now. */
		String accept(String chunk) {
			if (chunk == null || chunk.isEmpty()) {
				return "";
			}
			pending.append(chunk);
			removeMarkers();
			int held = heldBack();
			String out = pending.substring(0, pending.length() - held);
			pending.delete(0, pending.length() - held);
			return out;
		}

		/** The text still held back, once the stream is over. */
		String complete() {
			removeMarkers();
			String out = pending.toString();
			pending.setLength(0);
			return out;
		}

		boolean isContinueRequested() {
			// The last word wins: a model that asks to go on and then says it is done has
			// finished.
			return continueRequested && !finishRequested;
		}

		boolean isFinishRequested() {
			return finishRequested;
		}

		private void removeMarkers() {
			for (String marker : MARKERS) {
				int index;
				while ((index = pending.indexOf(marker)) >= 0) {
					pending.delete(index, index + marker.length());
					if (marker.equals(AGENT_CONTROL_FINISHED)) {
						finishRequested = true;
					} else {
						continueRequested = true;
					}
				}
			}
		}

		private int heldBack() {
			for (int length = Math.min(pending.length(), LONGEST_MARKER - 1); length > 0; length--) {
				String suffix = pending.substring(pending.length() - length);
				for (String marker : MARKERS) {
					if (marker.startsWith(suffix)) {
						return length;
					}
				}
			}
			return 0;
		}
	}
}
