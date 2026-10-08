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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
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
import ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.agent.standardtools.DeepSearchToolSource;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.llms.agent.standardtools.CitedAddresses;
import ai.gebo.llms.agent.standardtools.SummaryQuotationGuard;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDeepSearchTool;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource;
import ai.gebo.llms.agent.standardtools.StandardSearchesToolsImpl;
import ai.gebo.llms.agent.standardtools.ToolsFoundDocuments;
import ai.gebo.llms.agent.standardtools.ToolsProgress;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.agent.standardtools.WebSearchToolSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.impl.CutAnswer;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.IChatSessionEntryDocuments;
import ai.gebo.llms.chat.abstraction.layer.services.impl.ThinkingStream;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
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
	/**
	 * The documents of the chat's earlier answers, given apart from the answers: given
	 * after each answer, models copied them into their own.
	 */
	static final String EARLIER_ANSWERS_DOCUMENTS_PARAM = "EARLIER_ANSWERS_DOCUMENTS";
	/** The language the answer is written in, named (see {@link #sessionUserLanguage}). */
	static final String USER_LANGUAGE_PARAM = "userLanguage";
	/** What the prompts say of the answer's language when the user's one was not detected. */
	static final String USER_LANGUAGE_UNDETECTED = "the language of the user's current request";
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
	record LoopIteration(int number, String text, List<ToolCallExecuted> calls, String discardedFor,
			boolean draftToBuildOn, boolean cut) {
		/** An iteration the model's output limit did not cut. */
		LoopIteration(int number, String text, List<ToolCallExecuted> calls, String discardedFor,
				boolean draftToBuildOn) {
			this(number, text, calls, discardedFor, draftToBuildOn, false);
		}

		/** An iteration whose text was shown to the user. */
		LoopIteration(int number, String text, List<ToolCallExecuted> calls) {
			this(number, text, calls, null, false);
		}

		/**
		 * An iteration whose text was discarded, never shown, for the given reason: a draft
		 * that rests on no tool result is not one to build on, only why it was discarded
		 * is told.
		 */
		LoopIteration(int number, String text, List<ToolCallExecuted> calls, String discardedFor) {
			this(number, text, calls, discardedFor, false);
		}

		/** Whether its text was discarded, never shown: then why. */
		boolean discarded() {
			return discardedFor != null;
		}
	}

	/**
	 * What the next iteration is told of an iteration whose answer was discarded
	 * because it used no tool giving the sources' evidence (see
	 * {@link #needsEvidence(DeliverableIntent, boolean)}).
	 */
	/**
	 * Why an iteration that wrote no text is discarded: the model's output was cut by
	 * its output limit before any text (a reasoning model may spend it all reasoning on a
	 * large context), the provider reporting it as a normal end.
	 */
	static final String DISCARDED_EMPTY = "This attempt wrote no text: its output was cut by the output limit before "
			+ "any answer, its reasoning took it all.";
	/** What the next iteration is told of an iteration that wrote no text. */
	static final String EMPTY_ANSWER_STORY = "EMPTY ANSWER (the user saw nothing): the output limit was reached before any "
			+ "text, the reasoning took it all. Its tools' results are not kept here: call again only what the answer "
			+ "needs, with focused searches rather than whole documents, keep the reasoning short and write the answer.";
	/**
	 * Why an iteration cut by the model's output limit, its reasoning having taken most of
	 * it, is written again (see {@link CutAnswer}).
	 */
	static final String DISCARDED_CUT = "This answer was cut by the output limit, its reasoning took most of it: write "
			+ "the whole answer again from its beginning, keeping the reasoning short.";
	/**
	 * Where, in the streamed answer, the one written again after a cut one starts: a
	 * character no text has, on a line of its own, parted by a rule once the answer is
	 * guarded (see {@link #createResponse}).
	 */
	static final String ANSWER_RESTART_MARK = "\uE000";
	static final String ANSWER_RESTART = "\n\n" + ANSWER_RESTART_MARK + "\n\n";
	/** What the mark becomes in the answer the user sees while it streams. */
	static final String ANSWER_RESTART_RULE = "---";

	static final String DISCARDED_WITHOUT_EVIDENCE = "This answer was discarded, the user never saw it: it used no search tool, "
			+ "while the user asked to search or for an analysis, which must rest on what the sources contain now (the chat "
			+ "history is not a source: the earlier answers and the documents of the chat's earlier answers are no "
			+ "search). Call a search tool now, then answer from what it returns.";

	/**
	 * The requests built on the sources' evidence: an analysis or report, and any
	 * request in which the user asked to search, find, research, look up or verify, or
	 * named the sources (see the request understanding). An answer to them that used no
	 * tool rests on the model's memory or on the chat history only.
	 */
	static boolean needsEvidence(DeliverableIntent intent, boolean searchRequested) {
		return intent == DeliverableIntent.ANALISYS || searchRequested;
	}

	/** The same, the user not having asked to search. */
	static boolean needsEvidence(DeliverableIntent intent) {
		return needsEvidence(intent, false);
	}

	/**
	 * Whether the user asked to search, as the shared session environment says; false
	 * when it does not say.
	 */
	protected boolean sessionSearchRequested(AgentsCollaborationSessionContext session) {
		final Object value = session != null && session.getEnvironment() != null
				? session.getEnvironment().get(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED)
				: null;
		return Boolean.TRUE.equals(value);
	}

	/**
	 * Whether the user asked to answer without searching (from memory, from the
	 * conversation), as the shared session environment says; false when it does not
	 * say.
	 */
	protected boolean sessionSearchForbidden(AgentsCollaborationSessionContext session) {
		final Object value = session != null && session.getEnvironment() != null
				? session.getEnvironment().get(StandardAgentsNetworkEnvironmentEntries.SEARCH_FORBIDDEN)
				: null;
		return Boolean.TRUE.equals(value);
	}

	/**
	 * The language the answer is written in, as the prompts name it: the one detected
	 * on the user's message (see the shared session environment), otherwise "the
	 * language of the user's current request", left to the model.
	 */
	protected String sessionUserLanguage(AgentsCollaborationSessionContext session) {
		final Object value = session != null && session.getEnvironment() != null
				? session.getEnvironment().get(StandardAgentsNetworkEnvironmentEntries.USER_LANGUAGE)
				: null;
		return value instanceof String language && !language.isBlank() ? language : USER_LANGUAGE_UNDETECTED;
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
		return evidenceTools(intent, false, agentModel);
	}

	/** The same, also needed when the user asked to search (see {@link #needsEvidence(DeliverableIntent, boolean)}). */
	protected Set<String> evidenceTools(DeliverableIntent intent, boolean searchRequested,
			IGConfigurableChatModel<?> agentModel) {
		if (!needsEvidence(intent, searchRequested)) {
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
			LOGGER.debug("Agentic loop agent id:" + getId() + " deliverable:" + intent + " search requested:"
					+ searchRequested + " evidence tools:" + evidence);
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
			CoverageGate coverage, boolean holdsUntilSearch) {
		static final SourceGate NONE = new SourceGate(Set.of(), false, List.of());

		SourceGate(Set<String> tools, boolean evidenceRequired, Collection<String> readDocumentNames) {
			this(tools, evidenceRequired, readDocumentNames, null);
		}

		SourceGate(Set<String> tools, boolean evidenceRequired, Collection<String> readDocumentNames,
				CoverageGate coverage) {
			this(tools, evidenceRequired, readDocumentNames, coverage, true);
		}

		/**
		 * The gate of an iteration that goes on after one whose text was shown: nothing
		 * held, only a deep search whose coverage asks to be completed holds what follows.
		 */
		static SourceGate continuing(CoverageGate coverage) {
			return coverage == null ? NONE
					: new SourceGate(coverage.searchTools(), false, List.of(), coverage, false);
		}

		/** Whether the text is held from the start of the iteration until a search. */
		boolean holdsFromStart() {
			return holdsUntilSearch && !tools.isEmpty();
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
	record CoverageGate(Set<String> searchTools, Set<String> deepSearchTools, Set<String> knowledgeBaseTools,
			CoverageState state) {
		CoverageGate(Set<String> searchTools, Set<String> deepSearchTools, Set<String> knowledgeBaseTools) {
			this(searchTools, deepSearchTools, knowledgeBaseTools, new CoverageState());
		}
	}

	/**
	 * The coverage of the request's deep searches, shared by its iterations: whether the
	 * answer was already redone once to complete a coverage (once per request), and the
	 * coverage note still not completed, told to the user with the answer.
	 */
	static final class CoverageState {
		private final java.util.concurrent.atomic.AtomicBoolean redone = new java.util.concurrent.atomic.AtomicBoolean();
		private final java.util.concurrent.atomic.AtomicReference<String> notCompleted = new java.util.concurrent.atomic.AtomicReference<>();

		/** Uses the request's one redo: true the first time only. */
		boolean redoOnce() {
			return redone.compareAndSet(false, true);
		}

		void notCompleted(String note) {
			notCompleted.set(note);
		}

		void completed() {
			notCompleted.set(null);
		}

		/** The coverage note no search completed, null when none. */
		String notCompleted() {
			return notCompleted.get();
		}
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
		final boolean searchRequested = sessionSearchRequested(session);
		final Map<String, Object> deliverableParams = new HashMap<>(deliverableTemplateParams(userIntent));
		// named for the prompts, the user template and the closing of the tools' results
		deliverableParams.put(USER_LANGUAGE_PARAM, sessionUserLanguage(session));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " shapes its answer for the deliverable:"
					+ userIntent.name() + ", search requested:" + searchRequested + ", answer's language:"
					+ deliverableParams.get(USER_LANGUAGE_PARAM));
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
		final boolean searchForbidden = sessionSearchForbidden(session);
		final Set<String> evidenceTools = searchForbidden ? Set.of()
				: evidenceTools(userIntent, searchRequested, agentModel);
		// whatever the deliverable, an answer on a deep search whose coverage is thin is
		// completed once before it is shown (see CoverageGate): the deep search tells
		// its coverage by the depth the agent asked, a precise answer is never thin
		final MountedSearchTools mounted = searchTools(agentModel);
		final CoverageGate coverage = searchForbidden || mounted.deepSearches().isEmpty() ? null
				: new CoverageGate(mounted.searches(), mounted.deepSearches(), mounted.knowledgeBaseTools());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " coverage gate:" + (coverage != null)
					+ (coverage != null ? " deep searches:" + coverage.deepSearchTools() + " knowledge base tools:"
							+ coverage.knowledgeBaseTools() : ""));
		}
		final SourceGate gate;
		if (searchForbidden) {
			// the user asked to answer without searching: nothing is held nor checked
			gate = SourceGate.NONE;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " the user asked not to search: no gate");
			}
		} else if (!evidenceTools.isEmpty()) {
			gate = new SourceGate(evidenceTools, true, chatDocumentNames(chatRequestContext), coverage);
		} else {
			// any other answer may not cite documents it did not read: it is checked when it
			// used no search
			final Set<String> searches = mounted.searches();
			gate = searches.isEmpty() ? SourceGate.NONE
					: new SourceGate(searches, false, chatDocumentNames(chatRequestContext), coverage);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " evidence required:" + gate.evidenceRequired()
					+ " answer held until a search:" + !gate.tools().isEmpty());
		}
		final Flux<String> iterations = iteration(1, maxIterations, budget, history, agentModel, agentPrompt,
				loopContext, contextAgentPersona, notificationSink, callBacksListener, deliverableParams, gate, runAs);
		// an address no tool returned, nor the user or the chat gave, is made up: removed as
		// the answer streams
		final Set<String> removedAddresses = java.util.Collections.synchronizedSet(new LinkedHashSet<>());
		final KnownAddresses knownAddresses = new KnownAddresses(chatRequestContext, callBacksListener, toolDocuments);
		Flux<String> text = CitedAddresses.guarded(iterations, knownAddresses::isKnown, address -> {
			if (removedAddresses.add(address)) {
				LOGGER.warn("Agentic loop agent id:" + getId() + " answer gives an address no tool returned, removed: "
						+ address);
			}
		});
		// a deep search's summary quoted as if it were a document's words is not a quotation:
		// its quotation marks are taken away as the answer streams (best effort)
		final SummaryQuotationGuard summaryQuotations = new SummaryQuotationGuard(callBacksListener,
				mounted.deepSearches());
		text = DeepSearchQuotations.unquotingWhere(text, words -> {
			final boolean keep = summaryQuotations.keep(words);
			if (!keep) {
				LOGGER.warn("Agentic loop agent id:" + getId() + " answer quotes a deep search's summary as a document,"
						+ " quotation marks taken away: " + (words.length() <= 80 ? words : words.substring(0, 80) + "..."));
			}
			return keep;
		});
		// the answer written again after a cut one is the answer: the cut one, shown as it
		// streamed, is parted from it by a rule (see iteration(...))
		final AtomicInteger restartAt = new AtomicInteger(-1);
		final AtomicInteger streamed = new AtomicInteger();
		text = text.map(chunk -> {
			final int mark = chunk.indexOf(ANSWER_RESTART_MARK);
			if (mark < 0) {
				streamed.addAndGet(chunk.length());
				return chunk;
			}
			final String parted = chunk.substring(0, mark) + ANSWER_RESTART_RULE
					+ chunk.substring(mark + ANSWER_RESTART_MARK.length());
			restartAt.set(streamed.get() + mark + ANSWER_RESTART_RULE.length());
			streamed.addAndGet(parted.length());
			return parted;
		});
		final GeboChatResponse response = new GeboChatResponse();
		return renderOutputStream(text, response, session, contextAgentPersona, notificationSink, callBacksListener)
				.doOnNext(operation -> {
					if (operation != null && operation.getData() != null
							&& operation.getData().getContent() == response) {
						keepTheAnswerWrittenAgain(response, restartAt.get());
						final int before = response.getDocumentsRef() != null ? response.getDocumentsRef().size() : 0;
						// the documents the answer rests on, not every one the tools returned
						response.setDocumentsRef(ToolsFoundDocuments.mergeInto(response.getDocumentsRef(),
								answerDocuments(toolDocuments, response.getQueryResponse())));
						// the documents of the chat's earlier answers it names are given with it
						final List<GResponseDocumentRef> earlier = citedEarlierDocuments(response.getQueryResponse(),
								chatRequestContext);
						if (!earlier.isEmpty()) {
							response.setDocumentsRef(ToolsFoundDocuments.mergeInto(response.getDocumentsRef(), earlier));
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Agentic loop agent id:" + getId() + " answer names " + earlier.size()
										+ " document(s) of the chat's earlier answers: given with it");
							}
						}
						// the documents the tools only listed (in this request or for an earlier
						// answer) it names: kept with the chat's history, the next requests may name them
						final List<String> listed = new ArrayList<>(
								toolDocuments != null ? toolDocuments.getListedNames() : List.of());
						listed.addAll(earlierAnswersListedNames(chatRequestContext));
						final List<String> namedListed = namedDocuments(response.getQueryResponse(), listed);
						response.setListedDocumentNames(namedListed.isEmpty() ? null : namedListed);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " answer names " + namedListed.size()
									+ " of " + listed.size() + " document(s) only listed: kept with the history");
						}
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " answer documents: " + before
									+ " from the session, " + response.getDocumentsRef().size()
									+ " with the search tools' ones");
						}
						if (!searchForbidden) {
							// the user asked not to search: naming a document is no claim to have read it
							warnAboutUnreadCitations(response, chatRequestContext, toolDocuments);
						}
						warnAboutRemovedAddresses(response, removedAddresses);
						warnAboutAnswerWithoutSearch(response, toolDocuments);
						warnAboutThinCoverage(response, coverage);
						warnAboutEmptyAnswer(response);
						warnAboutCutAnswer(response, history);
					}
				});
	}

	/**
	 * The answer written again after a cut one ({@code restartAt}, where it starts in the
	 * streamed text, -1 for none) kept as the answer, the user told it was written again.
	 */
	protected void keepTheAnswerWrittenAgain(GeboChatResponse response, int restartAt) {
		final String answer = response.getQueryResponse();
		if (restartAt < 0 || answer == null || restartAt > answer.length()) {
			return;
		}
		response.setQueryResponse(answer.substring(restartAt).stripLeading());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " the answer written again is kept: " + restartAt
					+ " character(s) of the cut one left out of " + answer.length());
		}
		if (response.getBackendMessages() != null) {
			response.getBackendMessages().add(CutAnswer.writtenAgainNote());
		}
	}

	/**
	 * Tells the user, with a warning on the answer, that the model's output limit cut it
	 * and it was not written again: mostly answer (a model looping), no lower thinking
	 * level to ask, written again already or no iteration left.
	 */
	protected void warnAboutCutAnswer(GeboChatResponse response, List<LoopIteration> history) {
		if (response.getBackendMessages() == null || history.stream().noneMatch(i -> !i.discarded() && i.cut())) {
			return;
		}
		LOGGER.warn("Agentic loop agent id:" + getId() + " the answer was cut by the output limit: the user is told");
		response.getBackendMessages().add(CutAnswer.incompleteWarning());
	}

	/**
	 * Tells the user, with a warning on the answer, that no answer could be written:
	 * the model's output was cut before any text, also when written once more.
	 */
	protected void warnAboutEmptyAnswer(GeboChatResponse response) {
		final Object text = response.getQueryResponse();
		if ((text != null && !String.valueOf(text).isBlank()) || response.getBackendMessages() == null) {
			return;
		}
		LOGGER.warn("Agentic loop agent id:" + getId() + " the answer is empty: the user is told");
		response.getBackendMessages().add(GUserMessage.warnMessage("No answer written",
				"The model wrote no answer: its output limit was reached before any text, its reasoning took it all. "
						+ "Ask again, or narrow the question."));
	}

	/**
	 * Tells the user, with a warning on the answer, the addresses removed from it: no
	 * tool of the request returned them, nor did the user or the chat give them.
	 */
	protected void warnAboutRemovedAddresses(GeboChatResponse response, Set<String> removedAddresses) {
		if (removedAddresses.isEmpty() || response.getBackendMessages() == null) {
			return;
		}
		final List<String> removed;
		synchronized (removedAddresses) {
			removed = new ArrayList<>(removedAddresses);
		}
		response.getBackendMessages().add(GUserMessage.warnMessage("Addresses removed from the answer",
				"The answer gave " + String.join(", ", removed)
						+ ", not returned by the searches of this request: removed, what it says of them is not checked."));
	}

	/**
	 * The addresses an answer may give: the ones its tools returned in this request
	 * (except the documents they could not read), the user's request, the chat history
	 * and documents, and the answer's documents; re-read as the tools are called.
	 */
	static final class KnownAddresses {
		private final IChatRequestContext context;
		private final ToolCallsListener listener;
		private final ToolsFoundDocuments toolDocuments;
		private int callsSeen = -1;
		private Set<String> known = Set.of();

		KnownAddresses(IChatRequestContext context, ToolCallsListener listener, ToolsFoundDocuments toolDocuments) {
			this.context = context;
			this.listener = listener;
			this.toolDocuments = toolDocuments;
		}

		synchronized boolean isKnown(String address) {
			final int calls = listener != null ? listener.getCalls().size() : 0;
			if (calls != callsSeen) {
				callsSeen = calls;
				known = addresses();
			}
			return CitedAddresses.isKnown(address, known);
		}

		private Set<String> addresses() {
			final Set<String> addresses = new LinkedHashSet<>();
			if (listener != null) {
				for (ToolCallExecuted call : new ArrayList<>(listener.getCalls())) {
					CitedAddresses.addressesIn(CitedAddresses.withoutDocumentsNotRead(call.getResult()), addresses);
				}
			}
			if (context != null) {
				CitedAddresses.addressesIn(context.getActualUserRequest(), addresses);
				CitedAddresses.addressesIn(context.getConsolidatedHistory(), addresses);
				if (context.getDocuments() != null) {
					for (Document document : context.getDocuments()) {
						if (document != null) {
							CitedAddresses.addressesIn(String.valueOf(document.getMetadata()), addresses);
						}
					}
				}
			}
			if (toolDocuments != null) {
				for (GResponseDocumentRef ref : toolDocuments.getDocuments()) {
					CitedAddresses.addresses(citableNames(ref), addresses);
				}
			}
			return addresses;
		}
	}

	/**
	 * Records, in the request's found documents, the documents the shown text of an
	 * iteration says it rests on (its {@code ANSWER-DOCUMENTS} marker).
	 */
	void recordAnswerDocuments(IChatRequestContext chatRequestContext, ControlMarkerStripper stripper, int number) {
		final List<String> ids = stripper.getAnswerDocuments();
		if (ids == null || chatRequestContext == null || chatRequestContext.getToolsContext() == null) {
			return;
		}
		final ToolsFoundDocuments collector = collectorOf(chatRequestContext);
		if (collector == null) {
			return;
		}
		collector.addAnswerDocumentIds(ids);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " rests on the document(s): "
					+ ids);
		}
	}

	/** The request's found documents a request context shares with its tools, or null. */
	static ToolsFoundDocuments collectorOf(IChatRequestContext chatRequestContext) {
		if (chatRequestContext == null || chatRequestContext.getToolsContext() == null) {
			return null;
		}
		return ToolsFoundDocuments
				.from(new org.springframework.ai.chat.model.ToolContext(chatRequestContext.getToolsContext()));
	}

	/**
	 * Tells the user, with a warning on the answer, that it needed the sources and no
	 * source was searched: it rests on the model's knowledge only.
	 */
	/**
	 * Tells the user, with a warning on the answer, that a deep search of the request
	 * said its coverage was thin and no search completed it: what the deep search said
	 * is missing.
	 */
	protected void warnAboutThinCoverage(GeboChatResponse response, CoverageGate coverage) {
		final String note = coverage != null ? coverage.state().notCompleted() : null;
		if (note == null || response.getBackendMessages() == null) {
			return;
		}
		LOGGER.warn("Agentic loop agent id:" + getId() + " answer shown on a deep search coverage not completed: " + note);
		response.getBackendMessages().add(GUserMessage.warnMessage("The sources were covered in part",
				"A deep search of this answer covered its sources in part, and the answer did not complete it: " + note));
	}

	protected void warnAboutAnswerWithoutSearch(GeboChatResponse response, ToolsFoundDocuments toolDocuments) {
		if (toolDocuments == null || !toolDocuments.isAnsweredWithoutSearch() || response.getBackendMessages() == null) {
			return;
		}
		response.getBackendMessages().add(GUserMessage.warnMessage("No source searched for this answer",
				"The request needed the sources, and none was searched: the answer rests on the model's knowledge only "
						+ "and is not checked against any source."));
	}

	/**
	 * The documents the answer rests on, among the ones the tools of the request
	 * returned: the ones its {@code ANSWER-DOCUMENTS} marker lists; without a usable
	 * marker, the ones its text cites (by address or name); when it cites none, every
	 * one, as no answer could tell.
	 */
	List<GResponseDocumentRef> answerDocuments(ToolsFoundDocuments collector, String answer) {
		final List<GResponseDocumentRef> collected = collector.getDocuments();
		if (collected.isEmpty()) {
			return collected;
		}
		final List<String> ids = collector.getAnswerDocumentIds();
		if (ids != null) {
			final List<String> unknown = new ArrayList<>();
			// a knowledge base document's uniqueId, a number too, may be given in place of its
			// id: an id that is also another document's uniqueId names the one the answer cites
			final List<GResponseDocumentRef> listed = resolveAnswerIds(collector, collected, ids, answer, unknown);
			if (!unknown.isEmpty()) {
				LOGGER.warn("Agentic loop agent id:" + getId() + " answer lists document id(s) no tool gave: " + unknown);
			}
			if (!listed.isEmpty() || unknown.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " answer rests on " + listed.size() + " of the "
							+ collected.size() + " document(s) the tools returned: " + ids);
				}
				return listed;
			}
		} else {
			LOGGER.warn("Agentic loop agent id:" + getId()
					+ " answer gives no ANSWER-DOCUMENTS marker: its documents are the ones it cites");
		}
		final List<GResponseDocumentRef> cited = citedDocuments(collected, answer);
		if (!cited.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " answer cites " + cited.size() + " of the "
						+ collected.size() + " document(s) the tools returned");
			}
			return cited;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " answer cites none of the " + collected.size()
					+ " document(s) the tools returned: all of them are its documents");
		}
		return collected;
	}

	/** Resolves the uniqueId of a knowledge base document by its code; null without one. */
	private VirtualFilesystemUniqueIds uniqueIds = null;

	@Autowired(required = false)
	public void setUniqueIds(VirtualFilesystemUniqueIds uniqueIds) {
		this.uniqueIds = uniqueIds;
	}

	/**
	 * The documents the answer's ids name. An id names the document with that id in the
	 * request (#2) or, as a model reading both may give it, the knowledge base document
	 * with that uniqueId (12). When an id names two documents that way, the ones the
	 * answer cites (by name or address) are kept, the one with that id when it cites
	 * neither. The ids naming no document go to {@code unknown}.
	 */
	List<GResponseDocumentRef> resolveAnswerIds(ToolsFoundDocuments collector, List<GResponseDocumentRef> collected,
			List<String> ids, String answer, List<String> unknown) {
		final Map<Long, GResponseDocumentRef> byUniqueId = uniqueIdsOf(collected);
		final List<GResponseDocumentRef> cited = byUniqueId.isEmpty() ? List.of() : citedDocuments(collected, answer);
		final List<GResponseDocumentRef> listed = new ArrayList<>();
		for (String id : ids) {
			final List<GResponseDocumentRef> byId = collector.documentsOf(List.of(id), null);
			final Matcher number = Pattern.compile("\\d+").matcher(id);
			final GResponseDocumentRef byUnique = number.find() ? byUniqueId.get(Long.valueOf(number.group())) : null;
			final List<GResponseDocumentRef> named = new ArrayList<>();
			if (!byId.isEmpty() && byUnique != null && !byId.contains(byUnique)) {
				// two documents for one number: the answer's citations tell
				for (GResponseDocumentRef candidate : List.of(byId.get(0), byUnique)) {
					if (cited.contains(candidate)) {
						named.add(candidate);
					}
				}
				if (named.isEmpty()) {
					named.add(byId.get(0));
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " answer document id " + id
							+ " is both the id of " + byId.get(0).getDocumentCode() + " and the uniqueId of "
							+ byUnique.getDocumentCode() + ": kept " + named.size() + " the answer cites");
				}
			} else if (!byId.isEmpty()) {
				named.addAll(byId);
			} else if (byUnique != null) {
				named.add(byUnique);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " answer document id " + id
							+ " is the uniqueId of " + byUnique.getDocumentCode());
				}
			} else if (unknown != null) {
				unknown.add(id);
			}
			for (GResponseDocumentRef ref : named) {
				if (!listed.contains(ref)) {
					listed.add(ref);
				}
			}
		}
		return listed;
	}

	/** The documents of {@code collected} by their knowledge base uniqueId, when known. */
	Map<Long, GResponseDocumentRef> uniqueIdsOf(List<GResponseDocumentRef> collected) {
		final Map<Long, GResponseDocumentRef> byUniqueId = new HashMap<>();
		if (uniqueIds == null) {
			return byUniqueId;
		}
		for (GResponseDocumentRef ref : collected) {
			if (ref.getDocumentCode() == null) {
				continue;
			}
			try {
				final Long uniqueId = uniqueIds.documentUniqueId(ref.getDocumentCode());
				if (uniqueId != null) {
					byUniqueId.putIfAbsent(uniqueId, ref);
				}
			} catch (RuntimeException e) {
				LOGGER.error("Agentic loop agent id:" + getId() + " cannot resolve the uniqueId of "
						+ ref.getDocumentCode(), e);
			}
		}
		return byUniqueId;
	}

	/**
	 * Moves to {@code listed} the documents of {@code collected} whose uniqueId is one of
	 * the {@code unknown} ids: a model reading both a document's id (#2) and its
	 * uniqueId (12) may give the latter. The ids matched leave {@code unknown}.
	 */
	void byUniqueId(List<GResponseDocumentRef> collected, List<String> unknown, List<GResponseDocumentRef> listed) {
		final Map<Long, GResponseDocumentRef> byUniqueId = uniqueIdsOf(collected);
		for (java.util.Iterator<String> it = unknown.iterator(); it.hasNext();) {
			final String id = it.next();
			final Matcher number = Pattern.compile("\\d+").matcher(id);
			final GResponseDocumentRef ref = number.find() ? byUniqueId.get(Long.valueOf(number.group())) : null;
			if (ref != null) {
				if (!listed.contains(ref)) {
					listed.add(ref);
				}
				it.remove();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " answer document id " + id
							+ " is the uniqueId of " + ref.getDocumentCode());
				}
			}
		}
	}

	/** The documents an answer cites, by their address or their name. */
	static List<GResponseDocumentRef> citedDocuments(List<GResponseDocumentRef> documents, String answer) {
		final List<GResponseDocumentRef> cited = new ArrayList<>();
		if (answer == null || answer.isBlank()) {
			return cited;
		}
		final String lower = answer.toLowerCase();
		final Set<String> addresses = CitedAddresses.addressesIn(answer);
		for (GResponseDocumentRef ref : documents) {
			for (String name : citableNames(ref)) {
				if (name == null || name.isBlank()) {
					continue;
				}
				final boolean address = name.regionMatches(true, 0, "http", 0, 4);
				if (address ? addresses.contains(CitedAddresses.normalized(name))
						: name.trim().length() >= MIN_CITED_NAME_LENGTH && lower.contains(name.trim().toLowerCase())) {
					cited.add(ref);
					break;
				}
			}
		}
		return cited;
	}

	/** Shortest document name looked for in an answer: shorter ones match by chance. */
	static final int MIN_CITED_NAME_LENGTH = 6;

	/** A document file name as an answer cites it. */
	/** A file name without its separators (spaces, underscores, hyphens), lower case. */
	static String compactName(String name) {
		return name == null ? "" : name.toLowerCase().replaceAll("[\\s_\\-]+", "");
	}

	/** An address in an answer, up to the first space. */
	static final Pattern WEB_ADDRESS = Pattern.compile("(?i)\\b(?:https?|ftp)://\\S+");

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
		// an address is no file name: what it cites is checked by the addresses the tools
		// returned (see CitedAddresses), and a file name cut out of it (an encoded
		// character ends the name) would be one no document has
		final Matcher matcher = CITED_DOCUMENT.matcher(WEB_ADDRESS.matcher(answer).replaceAll(" "));
		while (matcher.find()) {
			final String cited = matcher.group();
			final String lowerCited = cited.toLowerCase();
			// a name with spaces is cited by its last part; a name may be cited with its
			// words spaced or joined otherwise ("a b.pdf" for "ab.pdf"): compared without them
			if (read.stream().noneMatch(name -> citesName(lowerCited, name))) {
				unread.add(cited);
			}
		}
		return new ArrayList<>(unread);
	}

	/** Whether a file name cited in an answer (lower case) is the given document name's (lower case). */
	private static boolean citesName(String lowerCited, String lowerName) {
		return lowerName.equals(lowerCited) || lowerName.endsWith(" " + lowerCited)
				|| lowerName.endsWith("/" + lowerCited) || compactName(lowerName).endsWith(compactName(lowerCited));
	}

	/** The given document names the answer cites, once each, in their order. */
	static List<String> namedDocuments(String answer, Collection<String> documentNames) {
		final List<String> named = new ArrayList<>();
		if (documentNames == null || documentNames.isEmpty()) {
			return named;
		}
		final List<String> cited = unreadCitations(answer, List.of());
		for (String name : documentNames) {
			if (name == null || named.contains(name)) {
				continue;
			}
			final String lowerName = name.trim().toLowerCase();
			if (cited.stream().anyMatch(c -> citesName(c.toLowerCase(), lowerName))) {
				named.add(name);
			}
		}
		return named;
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

	/**
	 * The names an answer may cite a document by after the tools ran in this request:
	 * the chat's own documents and every document the tools returned or listed so far.
	 */
	static List<String> readSoFar(IChatRequestContext chatRequestContext) {
		final List<String> names = new ArrayList<>(chatDocumentNames(chatRequestContext));
		final ToolsFoundDocuments collector = collectorOf(chatRequestContext);
		if (collector != null) {
			names.addAll(collector.getListedNames());
			for (GResponseDocumentRef ref : collector.getDocuments()) {
				names.addAll(citableNames(ref));
			}
		}
		return names;
	}

	/**
	 * The names of the chat's documents: its own (chosen or uploaded by the user) and the
	 * ones its earlier answers rested on, read then: they stay valid for the chat, each
	 * answer being one of what the next requests are given.
	 */
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
		for (GResponseDocumentRef ref : earlierAnswersDocuments(chatRequestContext)) {
			names.addAll(citableNames(ref));
		}
		names.addAll(earlierAnswersListedNames(chatRequestContext));
		return names;
	}

	/** The names of the documents the tools only listed for the chat's earlier answers, as they named them. */
	static List<String> earlierAnswersListedNames(IChatRequestContext chatRequestContext) {
		final List<String> names = new ArrayList<>();
		final List<IChatSessionEntry> interactions = chatRequestContext != null ? chatRequestContext.getInteractions()
				: null;
		if (interactions != null) {
			for (IChatSessionEntry interaction : interactions) {
				if (interaction instanceof IChatSessionEntryDocuments withDocuments
						&& withDocuments.getListedDocumentNames() != null) {
					for (String name : withDocuments.getListedDocumentNames()) {
						if (name != null && !names.contains(name)) {
							names.add(name);
						}
					}
				}
			}
		}
		return names;
	}

	/** The longest part of a user's request naming the earlier answer its documents go with. */
	static final int EARLIER_REQUEST_SHOWN_LENGTH = 120;

	/**
	 * What the model is told of the documents of the chat's earlier answers, apart from
	 * the answers: for each answer that had some, the request it answered and the
	 * documents it rested on (read then) and the ones the tools only listed that it
	 * named; "none" when no answer had any.
	 */
	static String earlierAnswersDocumentsText(IChatRequestContext chatRequestContext) {
		final List<IChatSessionEntry> interactions = chatRequestContext != null ? chatRequestContext.getInteractions()
				: null;
		final StringBuilder text = new StringBuilder();
		if (interactions != null) {
			int number = 0;
			for (IChatSessionEntry interaction : interactions) {
				number++;
				if (!(interaction instanceof IChatSessionEntryDocuments withDocuments)) {
					continue;
				}
				final List<String> read = new ArrayList<>();
				if (withDocuments.getDocumentsRef() != null) {
					for (GResponseDocumentRef ref : withDocuments.getDocumentsRef()) {
						final List<String> citable = citableNames(ref);
						if (!citable.isEmpty() && !read.contains(citable.get(0))) {
							read.add(citable.get(0));
						}
					}
				}
				final List<String> listed = withDocuments.getListedDocumentNames() != null
						? withDocuments.getListedDocumentNames()
						: List.of();
				if (read.isEmpty() && listed.isEmpty()) {
					continue;
				}
				String request = interaction.getUser() != null ? interaction.getUser().strip().replaceAll("\\s+", " ")
						: "";
				if (request.length() > EARLIER_REQUEST_SHOWN_LENGTH) {
					request = request.substring(0, EARLIER_REQUEST_SHOWN_LENGTH) + "...";
				}
				text.append("- Earlier answer ").append(number).append(", to the request \"").append(request)
						.append("\":").append(NEWLINE);
				if (!read.isEmpty()) {
					text.append("  rested on (read then): ").append(String.join("; ", read)).append(NEWLINE);
				}
				if (!listed.isEmpty()) {
					text.append("  named from a list of the tools (only listed, not read): ")
							.append(String.join("; ", listed)).append(NEWLINE);
				}
			}
		}
		return text.length() > 0 ? text.toString() : "none";
	}

	/** The documents the chat's earlier answers rested on, as kept with its history. */
	static List<GResponseDocumentRef> earlierAnswersDocuments(IChatRequestContext chatRequestContext) {
		final List<GResponseDocumentRef> documents = new ArrayList<>();
		final List<IChatSessionEntry> interactions = chatRequestContext != null ? chatRequestContext.getInteractions()
				: null;
		if (interactions != null) {
			for (IChatSessionEntry interaction : interactions) {
				if (interaction instanceof IChatSessionEntryDocuments withDocuments
						&& withDocuments.getDocumentsRef() != null) {
					documents.addAll(withDocuments.getDocumentsRef());
				}
			}
		}
		return documents;
	}

	/**
	 * The documents of the chat's earlier answers this answer names: given with it, as
	 * the documents it rests on.
	 */
	static List<GResponseDocumentRef> citedEarlierDocuments(String answer, IChatRequestContext chatRequestContext) {
		final List<GResponseDocumentRef> cited = new ArrayList<>();
		final int citedNames = unreadCitations(answer, List.of()).size();
		if (citedNames == 0) {
			return cited;
		}
		for (GResponseDocumentRef ref : earlierAnswersDocuments(chatRequestContext)) {
			// one of the names the answer cites is this document's
			if (unreadCitations(answer, citableNames(ref)).size() < citedNames) {
				cited.add(ref);
			}
		}
		return cited;
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
			// every document the tools read counts as read, also the ones the answer does
			// not rest on (not among its documents)
			for (GResponseDocumentRef ref : toolDocuments.getDocuments()) {
				readNames.addAll(citableNames(ref));
			}
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
			params.put(AGENT_SESSION_STORY_PROMPT_PARAM, loopStory(history, budget, collectorOf(chatRequestContext)));
			params.put(RULES_TO_FOLLOW_PARAM, rulesToFollow(chatRequestContext));
			params.put(EARLIER_ANSWERS_DOCUMENTS_PARAM, earlierAnswersDocumentsText(chatRequestContext));
			// the prompts name the answer's language: never missing, whoever started the loop
			params.putIfAbsent(USER_LANGUAGE_PARAM, USER_LANGUAGE_UNDETECTED);
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
				LOGGER.trace("<AGENTIC_LOOP_EARLIER_ANSWERS_DOCUMENTS iteration=" + number + ">");
				LOGGER.trace(String.valueOf(params.get(EARLIER_ANSWERS_DOCUMENTS_PARAM)));
				LOGGER.trace("</AGENTIC_LOOP_EARLIER_ANSWERS_DOCUMENTS>");
			}
			// why the model stopped writing, and its reasoning, streamed to the user as it comes
			final AtomicReference<String> finishReason = new AtomicReference<>();
			final ThinkingStream thinking = new ThinkingStream();
			final ISinkUIEmitter ui = notificationSink instanceof ISinkUIEmitter emitter ? emitter : null;
			Flux<String> modelText;
			try {
				// the tools' results of this iteration pile up in its model call: they may take
				// what the loop's budget leaves after this iteration's own placeholders
				modelText = callLLMReactiveResponses(agentModel, agentPrompt,
						chatRequestContext != null
								? chatRequestContext.withToolsRoom(ToolsTokenBudget.leftForTools(budget, params))
								: null,
						params).map(response -> chunkText(response, finishReason, thinking, ui))
						.filter(chunk -> !chunk.isEmpty()).concatWith(Flux.defer(() -> {
							// a reasoning no text came after ends with the model's output
							thinkingTo(ui, thinking.complete());
							return Flux.<String>empty();
						}));
			} catch (LLMConfigException e) {
				return Flux.error(e);
			}
			// the text held back while the iteration has not used a tool yet
			final StringBuilder held = new StringBuilder();
			final boolean[] open = { gate == null || !gate.holdsFromStart() };
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
				// the model's output limit cut this iteration (see CutAnswer)
				final boolean cut = CutAnswer.isCut(finishReason.get());
				// cut while its reasoning took the output limit: the next iteration asks for less
				final boolean reasoningCut = cut && CutAnswer.reasoningTookTheBudget(thinking.writtenChars(), text.length());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " finish reason:"
							+ finishReason.get() + " after " + text.length() + " text and " + thinking.writtenChars()
							+ " reasoning character(s)");
				}
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
						// the draft is kept, with why it was discarded: the next iteration builds on it
						history.add(new LoopIteration(number, text.toString(), iterationCalls,
								gate.evidenceRequired() ? discardedNote(gate.tools())
										: DISCARDED_UNREAD_CITATIONS + String.join(", ", unread) + "."));
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
						// a draft cut while reasoning: the next iteration asks for less reasoning
						final IGConfigurableChatModel lessReasoning = reasoningCut ? lowerThinkingAs(runAs, agentModel)
								: null;
						if (lessReasoning != null) {
							LOGGER.warn("Agentic loop agent id:" + getId() + " discarded iteration " + number
									+ " was cut by the output limit after " + text.length() + " text and "
									+ thinking.writtenChars() + " reasoning character(s): iteration " + (number + 1)
									+ " asks for less reasoning");
						}
						// a request needing the sources stays held until it searches them, iteration
						// after iteration: a model that answers from memory again is discarded again
						return asUser(runAs, iteration(number + 1, maxIterations, budget, history,
								lessReasoning != null ? lessReasoning : agentModel,
								agentPrompt, chatRequestContext, contextAgentPersona, notificationSink, callBacksListener,
								deliverableParams, gate.evidenceRequired() ? gate : SourceGate.NONE, runAs));
					}
					// no iteration left: the answer is shown as it is
					LOGGER.warn("Agentic loop agent id:" + getId() + " last iteration " + number
							+ " used no search although its answer needed one: answering anyway");
					history.add(new LoopIteration(number, text.toString(), iterationCalls));
					recordAnswerDocuments(chatRequestContext, stripper, number);
					if (gate.evidenceRequired()) {
						// the user is told the answer rests on no search
						final ToolsFoundDocuments collector = collectorOf(chatRequestContext);
						if (collector != null) {
							collector.markAnsweredWithoutSearch();
						}
					}
					return Flux.just(held.toString()).filter(chunk -> !chunk.isEmpty());
				}
				if (text.toString().isBlank() && !stripper.isContinueRequested()) {
					final boolean previousEmpty = !history.isEmpty()
							&& DISCARDED_EMPTY.equals(history.get(history.size() - 1).discardedFor());
					if (number < maxIterations && !previousEmpty) {
						// nothing to show: the model's output was cut before any text, written once more
						history.add(new LoopIteration(number, text.toString(), iterationCalls, DISCARDED_EMPTY));
						// cut by the output limit: written again asking for less reasoning, when it can be asked
						final IGConfigurableChatModel againModel = cut ? lowerThinkingAs(runAs, agentModel) : null;
						LOGGER.warn("Agentic loop agent id:" + getId() + " iteration " + number + " wrote no text after "
								+ iterationCalls.size() + " tool call(s) (the output limit may have been reached while "
								+ "reasoning, finish reason " + finishReason.get() + "): writing it in iteration "
								+ (number + 1) + (againModel != null ? " with less reasoning" : ""));
						notificationSink.next(
								"Agent: " + contextAgentPersona.getNetworkAgentName() + " writes the answer again..",
								ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
						return asUser(runAs, iteration(number + 1, maxIterations, budget, history,
								againModel != null ? againModel : agentModel,
								agentPrompt, chatRequestContext, contextAgentPersona, notificationSink, callBacksListener,
								deliverableParams, SourceGate.continuing(gate.coverage()), runAs));
					}
					LOGGER.warn("Agentic loop agent id:" + getId() + " iteration " + number + " wrote no text"
							+ (previousEmpty ? " again" : "") + (number < maxIterations ? "" : ", the last one")
							+ ": the answer is empty");
				}
				if (cut && !text.toString().isBlank()) {
					final boolean writtenAgainBefore = history.stream()
							.anyMatch(i -> DISCARDED_CUT.equals(i.discardedFor()));
					final IGConfigurableChatModel againModel = !writtenAgainBefore && number < maxIterations
							&& CutAnswer.reasoningTookTheBudget(thinking.writtenChars(), text.length())
									? lowerThinkingAs(runAs, agentModel)
									: null;
					if (againModel != null) {
						// the cut draft is kept: the next iteration writes the whole answer again
						history.add(new LoopIteration(number, text.toString(), iterationCalls, DISCARDED_CUT, true));
						LOGGER.warn("Agentic loop agent id:" + getId() + " iteration " + number
								+ " cut by the output limit (finish reason " + finishReason.get() + ") after "
								+ text.length() + " text and " + thinking.writtenChars()
								+ " reasoning character(s): written again with less reasoning in iteration "
								+ (number + 1));
						notificationSink.next(
								"Agent: " + contextAgentPersona.getNetworkAgentName() + " writes the answer again..",
								ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
						// what the user saw of the cut answer ends here; a held one was never shown
						final Flux<String> restart = open[0] ? Flux.just(ANSWER_RESTART) : Flux.<String>empty();
						return restart.concatWith(asUser(runAs, iteration(number + 1, maxIterations, budget, history,
								againModel, agentPrompt, chatRequestContext, contextAgentPersona, notificationSink,
								callBacksListener, deliverableParams, SourceGate.continuing(gate.coverage()), runAs)));
					}
					LOGGER.warn("Agentic loop agent id:" + getId() + " iteration " + number
							+ " cut by the output limit (finish reason " + finishReason.get() + ") after " + text.length()
							+ " text and " + thinking.writtenChars() + " reasoning character(s), not written again ("
							+ (writtenAgainBefore ? "written again already"
									: number < maxIterations ? "mostly text, or no lower thinking level to ask"
											: "no iteration left")
							+ "): the user is told it is incomplete");
				}
				history.add(new LoopIteration(number, text.toString(), iterationCalls, null, false, cut));
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
				final CoverageState coverageState = gate.coverage() != null ? gate.coverage().state() : null;
				if (coverageState != null) {
					if (heldForCoverage) {
						// told to the user with the answer unless a later search completes it
						coverageState.notCompleted(coverageNote);
					} else if (coverageNote == null
							&& usedEvidenceTool(callBacksListener, callsBefore, gate.coverage().searchTools())) {
						if (coverageState.notCompleted() != null && LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number
									+ " searched the sources after a coverage asking to be completed");
						}
						coverageState.completed();
					}
				}
				if (heldForCoverage && !another) {
					if (number < maxIterations && coverageState.redoOnce()) {
						// the answer rests on a thin coverage: done again once per request, told what
						// to complete; the draft is kept, with what to complete: the next iteration
						// builds on it
						history.set(history.size() - 1, new LoopIteration(number, text.toString(), iterationCalls,
								DISCARDED_THIN_COVERAGE + coverageNote, true));
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
						// the sources were searched: the next iteration needs no new evidence, but an
						// answer without a search may not cite documents this request did not read
						final SourceGate searched = new SourceGate(gate.tools(), false,
								readSoFar(chatRequestContext), gate.coverage());
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + (number + 1)
									+ " needs no new evidence, may cite " + searched.readDocumentNames().size()
									+ " document name(s) read so far");
						}
						return asUser(runAs, iteration(number + 1, maxIterations, budget, history, agentModel,
								agentPrompt, chatRequestContext, contextAgentPersona, notificationSink, callBacksListener,
								deliverableParams, searched, runAs));
					}
					// done again already, or no iteration left: the answer is shown as it is, the user
					// is told what the coverage misses
					LOGGER.warn("Agentic loop agent id:" + getId() + " iteration " + number
							+ " answered on a deep search coverage asking to be completed, "
							+ (number < maxIterations ? "already redone once" : "no iteration left")
							+ ": answering anyway");
					recordAnswerDocuments(chatRequestContext, stripper, number);
					return Flux.just(held.toString()).filter(chunk -> !chunk.isEmpty());
				}
				// the text of this iteration is shown: the documents it says it rests on count
				recordAnswerDocuments(chatRequestContext, stripper, number);
				if (stripper.getRemovedDocumentsNotes() > 0 && LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agentic loop agent id:" + getId() + " iteration " + number + " removed "
							+ stripper.getRemovedDocumentsNotes()
							+ " note(s) naming an answer's documents copied into its text");
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
				// the next iteration is not held, but a deep search of its own whose coverage is
				// thin holds what follows it, as in the first iteration
				return releasedText.concatWith(Flux.just(NEWLINE + NEWLINE)).concatWith(asUser(runAs, iteration(
						number + 1, maxIterations, budget, history, agentModel, agentPrompt, chatRequestContext,
						contextAgentPersona, notificationSink, callBacksListener, deliverableParams,
						SourceGate.continuing(gate.coverage()), runAs)));
			});
			return visible.concatWith(next);
		});
	}

	/**
	 * The model's answer to an iteration as it streams, with what its text alone does not
	 * carry: its reasoning and why it stopped.
	 */
	protected Flux<ChatResponse> callLLMReactiveResponses(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params)
			throws LLMConfigException {
		return chatModel.streamResponse(prompt, params, context);
	}

	/**
	 * The text of a streamed chunk: its reasoning sent to the user ({@code ui}, null when
	 * the request has no chat to show it in), why the model stopped kept.
	 */
	private String chunkText(ChatResponse response, AtomicReference<String> finishReason, ThinkingStream thinking,
			ISinkUIEmitter ui) {
		if (response == null || response.getResults() == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder();
		for (Generation generation : response.getResults()) {
			final String finish = generation.getMetadata() != null ? generation.getMetadata().getFinishReason() : null;
			if (finish != null && !finish.isBlank()) {
				finishReason.set(finish);
			}
			if (generation.getOutput() == null) {
				continue;
			}
			final Object reasoning = generation.getOutput().getMetadata() != null
					? generation.getOutput().getMetadata().get(ThinkingStream.REASONING_CONTENT_METADATA)
					: null;
			if (reasoning instanceof String soFar) {
				thinkingTo(ui, thinking.reasoning(soFar));
			}
			if (generation.getOutput().getText() != null) {
				out.append(generation.getOutput().getText());
			}
		}
		if (!out.toString().isBlank()) {
			// the text starts: the reasoning ended
			thinkingTo(ui, thinking.complete());
		}
		return out.toString();
	}

	/** The reasoning events sent to the user's chat, never failing the answer. */
	private void thinkingTo(ISinkUIEmitter ui, List<GThinkingEvent> events) {
		if (ui == null || events.isEmpty()) {
			return;
		}
		try {
			for (GThinkingEvent event : events) {
				ui.next(new GeboChatMessageEnvelope<GThinkingEvent>(event));
			}
		} catch (RuntimeException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agentic loop agent id:" + getId() + " the reasoning could not be sent to the chat: "
						+ e.getMessage());
			}
		}
	}

	/**
	 * The same, the copy made as the user ({@code runAs}, null to keep the thread's
	 * identity): an iteration's decisions run on the thread its model stream ended on,
	 * which carries none, and the copy resolves its tools for the user.
	 */
	private IGConfigurableChatModel lowerThinkingAs(ReactiveIdentityUtil runAs, IGConfigurableChatModel agentModel) {
		return runAs != null ? runAs.doRunAsWithReturn(() -> lowerThinking(agentModel)) : lowerThinking(agentModel);
	}

	/**
	 * The agent's model asked for one thinking level less (see {@link CutAnswer#lower}),
	 * null when there is none to ask or it is not this agent's running model.
	 */
	protected IGConfigurableChatModel lowerThinking(IGConfigurableChatModel agentModel) {
		if (agentModel == null || !(agentModel.getConfig() instanceof GBaseChatModelConfig modelConfig)) {
			return null;
		}
		final ChatModelThinkingOption lower = CutAnswer.lower(modelConfig.getThinking());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " model thinking " + modelConfig.getThinking()
					+ ", one level less: " + lower);
		}
		if (lower == null) {
			return null;
		}
		try {
			return withThinking(agentModel, lower);
		} catch (LLMConfigException | RuntimeException e) {
			LOGGER.error("Agentic loop agent id:" + getId() + " cannot ask its model for thinking " + lower, e);
			return null;
		}
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

	/** The same, without the documents the tools returned. */
	protected String loopStory(List<LoopIteration> history, int budget) {
		return loopStory(history, budget, null);
	}

	/**
	 * The history of the previous iterations for the next one, which goes on from the
	 * last outcome as the report writer goes on from its last draft: the tools each
	 * iteration called and what it wrote. A text shown to the user is part of the
	 * answer, kept so it is not repeated; a discarded draft is kept whole when it is the
	 * last outcome, with why it was discarded, so the next iteration completes it
	 * instead of starting over, and only by its tool calls once a later draft
	 * supersedes it. Then the documents the tools returned so far, with their ids. All
	 * within half of the budget, shared equally; the other half is left to the chat
	 * history and the tool results of the iteration.
	 */
	protected String loopStory(List<LoopIteration> history, int budget, ToolsFoundDocuments collector) {
		if (history.isEmpty()) {
			return "No previous iteration: this is the first one.";
		}
		int lastDraft = -1;
		for (int i = 0; i < history.size(); i++) {
			if (history.get(i).discarded()) {
				lastDraft = i;
			}
		}
		List<String> pieces = new ArrayList<>();
		for (int index = 0; index < history.size(); index++) {
			final LoopIteration iteration = history.get(index);
			StringBuilder piece = new StringBuilder();
			piece.append("BEGIN_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			List<CalledFunction> functions = renderFunctions(iteration.calls());
			for (int i = 0; functions != null && i < functions.size(); i++) {
				piece.append("TOOL-CALLED-").append(i + 1).append(": ").append(functions.get(i).getFunctionName())
						.append(" params:").append(functions.get(i).getParams()).append(NEWLINE);
			}
			if (!iteration.discarded()) {
				piece.append("RESPONSE: ").append(iteration.text()).append(NEWLINE);
			} else if (index == lastDraft && DISCARDED_EMPTY.equals(iteration.discardedFor())) {
				// it wrote nothing: the next iteration writes the answer
				piece.append(EMPTY_ANSWER_STORY).append(NEWLINE);
			} else if (index == lastDraft && iteration.draftToBuildOn()) {
				// written on the tools' results: the next iteration completes it
				piece.append(DISCARDED_DRAFT).append(iteration.text()).append(NEWLINE);
				piece.append(WHY_DISCARDED).append(iteration.discardedFor()).append(NEWLINE);
			} else if (index == lastDraft) {
				// written on no tool result: only why it was discarded, a draft to rewrite would
				// be rewritten instead of searching
				piece.append(DISCARDED_WITHOUT_DRAFT).append(NEWLINE);
				piece.append(WHY_DISCARDED).append(iteration.discardedFor()).append(NEWLINE);
			} else {
				piece.append(SUPERSEDED_DRAFT).append(NEWLINE);
			}
			piece.append("END_AGENT-LOOP-").append(iteration.number()).append(NEWLINE);
			pieces.add(piece.toString());
		}
		final String documents = documentsSoFar(collector);
		if (!documents.isEmpty()) {
			pieces.add(documents);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop agent id:" + getId() + " story of " + history.size() + " iteration(s)"
					+ (lastDraft >= 0 ? ", iteration " + history.get(lastDraft).number() + " discarded, its draft "
							+ (history.get(lastDraft).draftToBuildOn() ? "kept whole" : "left out") : "")
					+ ", documents so far:" + (collector != null ? collector.getDocuments().size() : 0));
		}
		return String.join("", fitEqually(pieces, Math.max(budget / 2, MIN_SHARED_CONTEXT_TOKENS)));
	}

	/** Introduces a discarded draft in the story: the user never saw it. */
	static final String DISCARDED_DRAFT = "DISCARDED DRAFT (the user never saw it, build on it): ";
	static final String WHY_DISCARDED = "WHY IT WAS DISCARDED: ";
	static final String SUPERSEDED_DRAFT = "DISCARDED DRAFT: superseded by a later one.";
	/** A discarded draft that rests on no tool result: not shown, only why. */
	static final String DISCARDED_WITHOUT_DRAFT = "DISCARDED ANSWER (the user never saw it, it rested on no source: do not "
			+ "rewrite it, search first).";

	/**
	 * The documents the tools returned so far in the request, each with its id: the
	 * ones the answer may rest on and list in its ANSWER-DOCUMENTS line.
	 */
	static String documentsSoFar(ToolsFoundDocuments collector) {
		if (collector == null) {
			return "";
		}
		final List<GResponseDocumentRef> documents = collector.getDocuments();
		if (documents.isEmpty()) {
			return "";
		}
		final StringBuilder list = new StringBuilder("DOCUMENTS RETURNED SO FAR (their doc ids):").append(NEWLINE);
		for (GResponseDocumentRef ref : documents) {
			final String id = collector.idOf(ref.getDocumentCode());
			if (id == null) {
				continue;
			}
			list.append(id).append(' ').append(ref.getName() != null ? ref.getName() : ref.getDocumentCode());
			for (String name : citableNames(ref)) {
				if (name != null && name.regionMatches(true, 0, "http", 0, 4)) {
					list.append(", ").append(name);
					break;
				}
			}
			list.append(NEWLINE);
		}
		return list.toString();
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
	/**
	 * Opens the line of the documents an answer rests on, by their short ids
	 * ({@code @@DOCS@@ #1 #4}): a sequence no text has, so the line is found even when a
	 * model writes it its own way.
	 */
	static final String ANSWER_DOCUMENTS_SEQUENCE = "@@DOCS@@";
	/** The former tag form of the line, still recognized. */
	static final String ANSWER_DOCUMENTS_OPEN = "<ANSWER-DOCUMENTS>";
	static final String ANSWER_DOCUMENTS_CLOSE = "</ANSWER-DOCUMENTS>";
	/** A document id in the ANSWER-DOCUMENTS marker: its number. */
	private static final Pattern DOCUMENT_ID = Pattern.compile("#?\\s*(\\d+)");

	static final class ControlMarkerStripper {
		private static final List<String> MARKERS = List.of(AGENT_CONTROL_FINISHED, AGENT_CONTROL_MORE_TOOLS);
		private static final int LONGEST_MARKER = MARKERS.stream().mapToInt(String::length).max().orElse(0);
		private final StringBuilder pending = new StringBuilder();
		private boolean continueRequested = false;
		private boolean finishRequested = false;
		/** The ids of the ANSWER-DOCUMENTS marker; null when the text gave none. */
		private List<String> answerDocuments = null;
		/** The notes naming an answer's documents the model copied into its text, removed. */
		private int removedDocumentsNotes = 0;

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
			// a marker the model did not close ends with the text
			removeAnswerDocuments(true);
			removeDocumentsNotes(true);
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

		int getRemovedDocumentsNotes() {
			return removedDocumentsNotes;
		}

		/**
		 * Removes the notes naming the documents an answer rested on (see
		 * {@link CSSSimplefiedInteraction#DOCUMENTS_NOTE_START}), from their start to
		 * their closing bracket or their line end, with the blanks before them: the
		 * earlier answers were given with such notes, and a model copied them into its own
		 * answer. A note not ended yet is held back until the next chunk, or removed when
		 * the stream is over.
		 */
		/** Where the blanks before the given position start. */
		private int withBlanksBefore(int index) {
			int start = index;
			while (start > 0 && Character.isWhitespace(pending.charAt(start - 1))) {
				start--;
			}
			return start;
		}

		private void removeDocumentsNotes(boolean ended) {
			int index;
			int from = 0;
			while ((index = pending.indexOf(CSSSimplefiedInteraction.DOCUMENTS_NOTE_START, from)) >= 0) {
				int end = index + CSSSimplefiedInteraction.DOCUMENTS_NOTE_START.length();
				while (end < pending.length() && pending.charAt(end) != ']' && pending.charAt(end) != '\r'
						&& pending.charAt(end) != '\n') {
					end++;
				}
				if (end == pending.length() && !ended) {
					// held back until the note ends
					return;
				}
				if (end < pending.length() && pending.charAt(end) == ']') {
					end++;
				}
				final int start = withBlanksBefore(index);
				pending.delete(start, end);
				removedDocumentsNotes++;
				from = start;
			}
		}

		/** The document ids the text says it rests on, "#n" each; null when it gave no marker. */
		List<String> getAnswerDocuments() {
			return answerDocuments != null ? new ArrayList<>(answerDocuments) : null;
		}

		/**
		 * Removes the line of the documents the answer rests on: it starts with
		 * {@link #ANSWER_DOCUMENTS_SEQUENCE} (or the tag {@link #ANSWER_DOCUMENTS_OPEN}, or
		 * the bare word ANSWER-DOCUMENTS a model may write instead) and goes on with the
		 * ids, their separators and line ends, up to the next text or control marker. A
		 * bare word with no id after it is text, left as it is. A line whose ids may still
		 * go on is held back until the next chunk, or captured when the stream is over.
		 */
		private void removeAnswerDocuments(boolean ended) {
			int from = 0;
			DocumentsLine line;
			while ((line = documentsLine(pending, from, ended)) != null) {
				if (!line.complete()) {
					// held back until the ids end
					return;
				}
				if (!line.marker()) {
					// the word in the text, no ids: not the line
					from = line.end();
					continue;
				}
				captureAnswerDocuments(line.ids());
				pending.delete(line.start(), line.end());
				from = line.start();
			}
		}

		private void captureAnswerDocuments(String ids) {
			if (answerDocuments == null) {
				answerDocuments = new ArrayList<>();
			}
			final Matcher matcher = DOCUMENT_ID.matcher(ids);
			while (matcher.find()) {
				final String id = ToolsFoundDocuments.ID_PREFIX + matcher.group(1);
				if (!answerDocuments.contains(id)) {
					answerDocuments.add(id);
				}
			}
		}

		private static int indexOfIgnoreCase(CharSequence text, String marker, int from) {
			for (int i = Math.max(0, from); i <= text.length() - marker.length(); i++) {
				if (text.toString().regionMatches(true, i, marker, 0, marker.length())) {
					return i;
				}
			}
			return -1;
		}

		private void removeMarkers() {
			removeAnswerDocuments(false);
			removeDocumentsNotes(false);
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
			// a note naming an answer's documents not ended yet
			final int note = pending.indexOf(CSSSimplefiedInteraction.DOCUMENTS_NOTE_START);
			if (note >= 0) {
				return pending.length() - withBlanksBefore(note);
			}
			// a documents line whose ids may still go on
			int from = 0;
			DocumentsLine line;
			while ((line = documentsLine(pending, from, false)) != null) {
				if (!line.complete()) {
					return pending.length() - line.start();
				}
				from = line.end();
			}
			for (int length = Math.min(pending.length(), Math.max(Math.max(LONGEST_MARKER, LONGEST_DOCUMENTS_START),
					CSSSimplefiedInteraction.DOCUMENTS_NOTE_START.length()) - 1); length > 0; length--) {
				String suffix = pending.substring(pending.length() - length);
				if (CSSSimplefiedInteraction.DOCUMENTS_NOTE_START.startsWith(suffix)) {
					// the blanks before a note go with it
					return pending.length() - withBlanksBefore(pending.length() - length);
				}
				for (String marker : MARKERS) {
					if (marker.startsWith(suffix)) {
						return length;
					}
				}
				for (String start : DOCUMENTS_STARTS) {
					if (start.regionMatches(true, 0, suffix, 0, suffix.length())) {
						return length;
					}
				}
			}
			return 0;
		}
	}

	/**
	 * The documents line found in a text: where it starts and ends, its ids, whether
	 * it is a marker (it gave ids, or used the sequence or the tag) and whether it is
	 * complete (something follows its ids, or the text is over).
	 */
	record DocumentsLine(int start, int end, String ids, boolean marker, boolean complete) {
	}

	/** What opens the documents line: the sequence asked, the tag, the bare word. */
	static final List<String> DOCUMENTS_STARTS = List.of(ANSWER_DOCUMENTS_SEQUENCE, ANSWER_DOCUMENTS_OPEN,
			"ANSWER-DOCUMENTS");
	static final int LONGEST_DOCUMENTS_START = DOCUMENTS_STARTS.stream().mapToInt(String::length).max().orElse(0);

	/** The first documents line of {@code text} from {@code from}; null when none starts there. */
	static DocumentsLine documentsLine(CharSequence text, int from, boolean ended) {
		int start = -1;
		String token = null;
		for (String candidate : DOCUMENTS_STARTS) {
			final int index = ControlMarkerStripper.indexOfIgnoreCase(text, candidate, from);
			if (index >= 0 && (start < 0 || index < start)) {
				start = index;
				token = candidate;
			}
		}
		if (start < 0) {
			return null;
		}
		final int idsStart = start + token.length();
		int end = idsStart;
		while (end < text.length() && isDocumentsLineChar(text.charAt(end))) {
			end++;
		}
		final String ids = text.subSequence(idsStart, end).toString();
		final boolean gaveIds = DOCUMENT_ID_NUMBER.matcher(ids).find();
		final boolean marker = gaveIds || !token.equals("ANSWER-DOCUMENTS");
		if (end < text.length() || ended) {
			// the closing tag of the tag form goes with the line
			if (end < text.length() && text.toString().regionMatches(true, end, ANSWER_DOCUMENTS_CLOSE, 0,
					Math.min(ANSWER_DOCUMENTS_CLOSE.length(), text.length() - end))) {
				if (text.length() - end < ANSWER_DOCUMENTS_CLOSE.length() && !ended) {
					return new DocumentsLine(start, end, ids, marker, false);
				}
				end = Math.min(text.length(), end + ANSWER_DOCUMENTS_CLOSE.length());
			}
			return new DocumentsLine(start, end, ids, marker, true);
		}
		return new DocumentsLine(start, end, ids, marker, false);
	}

	/** The ids of a documents line, their separators and line ends. */
	private static boolean isDocumentsLineChar(char c) {
		return Character.isWhitespace(c) || Character.isDigit(c) || c == '#' || c == ',' || c == ';' || c == ':'
				|| c == '>';
	}

	private static final Pattern DOCUMENT_ID_NUMBER = Pattern.compile("\\d");
}
