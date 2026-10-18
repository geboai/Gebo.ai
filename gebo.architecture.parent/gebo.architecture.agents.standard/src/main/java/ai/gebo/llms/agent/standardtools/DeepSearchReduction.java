/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The reduce step of a deep search, run by a search agent of a network on the
 * fragments its searches found: the analysis of the deep search tools
 * ({@link AbstractDeepSearchTool}), batch by batch against the agent's command,
 * the partial analyses reduced into one. The agent hands that analysis to the
 * writer instead of the fragments, with the documents it rests on, so it reads
 * far more than the fragments that fit the writer's context.
 * <p>
 * The searches are the agent's own; this only analyses what they found. The
 * depth asked by the command sizes the search ({@link #fragmentsPerDocument})
 * and the analysis, as it does for the tools.
 */
public final class DeepSearchReduction {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchReduction.class);

	private DeepSearchReduction() {
	}

	/**
	 * What the reduce step gives: the analysis and what it rests on. No analysis
	 * ({@link #hasAnalysis()} false) when nothing was found, no model could analyse
	 * or the analysis read none of the fragments.
	 *
	 * @param analysis       the final analysis, null when none
	 * @param fragmentsFound the fragments given to the analysis
	 * @param fragmentsRead  the fragments the analysis read (the ones it stopped
	 *                       before are left out)
	 * @param sourceCodes    the codes of the documents whose fragments were read, in
	 *                       the order found: the documents the analysis rests on
	 * @param notCovered     what the analysis reports as missing, null when nothing
	 */
	public record Outcome(String analysis, int fragmentsFound, int fragmentsRead, List<String> sourceCodes,
			String notCovered) {
		public boolean hasAnalysis() {
			return fragmentsRead > 0 && analysis != null && !analysis.isBlank();
		}

		public static Outcome none(int fragmentsFound) {
			return new Outcome(null, fragmentsFound, 0, List.of(), null);
		}
	}

	/** The depth of the deep search tools a command's depth maps to: BROAD when not given. */
	public static Depth depthOf(SearchAgentCommand.DeepSearchDepth depth) {
		if (depth == null) {
			return Depth.BROAD;
		}
		switch (depth) {
		case FOCUSED:
			return Depth.FOCUSED;
		case EXHAUSTIVE:
			return Depth.EXHAUSTIVE;
		default:
			return Depth.BROAD;
		}
	}

	/** The fragments read of each document found, from the depth, as the deep search tools read them. */
	public static int fragmentsPerDocument(Depth depth) {
		return AbstractDeepSearchTool.fragmentsPerDocument(depth);
	}

	/** The fragments the knowledge base search returns of each document, per document found. */
	public static int knowledgeBaseFragmentsPerDocument() {
		return KnowledgeBaseDeepSearchTool.FRAGMENTS_PER_DOCUMENT;
	}

	/** The tokens the knowledge base search may return to a deep search: read in batches, not in one context. */
	public static int knowledgeBaseRetrievedTokens() {
		return KnowledgeBaseDeepSearchTool.MAX_RETRIEVED_TOKENS;
	}

	/** The fragments kept of each document found in an external source. */
	public static int externalFragmentsPerDocument() {
		return SearchServiceDeepSearchTool.MAX_FRAGMENTS_PER_DOCUMENT;
	}

	/**
	 * Analyses the fragments against the question batch by batch and reduces the
	 * partial analyses into one, as the deep search tools do.
	 *
	 * @param support   the deep search tools' collaborators (the analysis, the models)
	 * @param label     names the caller in the logs
	 * @param question  what the fragments are analysed against: the agent's command
	 * @param depth     sizes the analysis; BROAD when null
	 * @param fragments the fragments found by the agent's searches
	 * @param context   the request the agent runs for: its id and the user's language
	 * @param chatModel writes the final analysis; the default chat model when null
	 * @param runAs     the identity the analysis runs with; a new one when null
	 * @param notifier  tells the user the progress of the analysis; none when null
	 */
	public static Outcome reduce(DeepSearchToolsSupport support, String label, String question, Depth depth,
			List<Document> fragments, IChatRequestContext context, IGConfigurableChatModel chatModel,
			ReactiveIdentityUtil runAs, IGProgressNotifier notifier) {
		final int found = fragments != null ? fragments.size() : 0;
		if (found == 0) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(label + " deep search reduce: no fragment to analyse");
			}
			return Outcome.none(0);
		}
		final IGChatModelRuntimeConfigurationDao chatModelsDao = support.chatModelsDao();
		final IGConfigurableChatModel finalModel = chatModel != null ? chatModel : chatModelsDao.defaultHandler();
		final IGConfigurableChatModel serviceModel = chatModelsDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		if (finalModel == null || serviceModel == null) {
			LOGGER.warn(label + " deep search reduce: no chat model to analyse with (final:" + (finalModel != null)
					+ " service:" + (serviceModel != null) + ")");
			return Outcome.none(found);
		}
		final Depth effectiveDepth = depth != null ? depth : Depth.BROAD;
		final DeliverableIntent deliverable = AbstractDeepSearchTool.deliverable(effectiveDepth);
		final String completenessNote = AbstractDeepSearchTool.TOOL_COMPLETENESS_NOTE
				+ AbstractDeepSearchTool.lengthTarget(effectiveDepth, support.maxAnalysisTokens());
		final IChatRequestContext analysisContext = AbstractDeepSearchTool.analysisContext(question,
				context != null ? context.getRequestID() : null, context != null ? context.getUserLanguage() : null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin " + label + " deep search reduce of " + found + " fragment(s) depth:" + effectiveDepth
					+ " deliverable:" + deliverable + " finalModel:" + finalModel.getCode() + " serviceModel:"
					+ serviceModel.getCode() + " request:" + (context != null ? context.getRequestID() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<DEEP_SEARCH_REDUCE_QUESTION agent=" + label + ">");
			LOGGER.trace(question);
			LOGGER.trace("</DEEP_SEARCH_REDUCE_QUESTION>");
		}
		final Vector<String> discardedFragmentIds = new Vector<>();
		final DeepSearchAnalysisOutcome analysisOutcome = new DeepSearchAnalysisOutcome();
		final String analysis = support.analysis()
				.analyze(Flux.fromIterable(fragments), analysisContext,
						runAs != null ? runAs : ReactiveIdentityUtil.create(), deliverable, completenessNote,
						finalModel, serviceModel, discardedFragmentIds,
						notifier != null ? notifier : IGProgressNotifier.NONE, analysisOutcome)
				.reduce(new StringBuilder(), StringBuilder::append).map(StringBuilder::toString)
				.block(AbstractDeepSearchTool.DEEP_SEARCH_TIMEOUT);
		// the fragments the analysis stopped before were never read: not its sources
		final Set<String> unread = analysisOutcome.getUnreadFragmentIds();
		final List<String> sources = new ArrayList<>();
		int read = 0;
		for (Document fragment : fragments) {
			if (fragment == null || (fragment.getId() != null && unread.contains(fragment.getId()))) {
				continue;
			}
			read++;
			final Object code = fragment.getMetadata() != null
					? fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)
					: null;
			if (code != null && !sources.contains(code.toString())) {
				sources.add(code.toString());
			}
		}
		if (read == 0) {
			LOGGER.warn(label + " deep search reduce: the analysis read none of the " + found
					+ " fragment(s) found, there is no analysis of them");
			return Outcome.none(found);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End " + label + " deep search reduce: " + read + " of " + found + " fragment(s) read, "
					+ discardedFragmentIds.size() + " discarded, rests on " + sources.size() + " document(s), analysis:"
					+ (analysis != null ? analysis.length() : 0) + " character(s)"
					+ (analysisOutcome.getNotCovered() != null ? " not covered:" + analysisOutcome.getNotCovered()
							: ""));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<DEEP_SEARCH_REDUCE_ANALYSIS agent=" + label + " sources=" + sources + ">");
			LOGGER.trace(analysis);
			LOGGER.trace("</DEEP_SEARCH_REDUCE_ANALYSIS>");
		}
		return new Outcome(analysis != null ? analysis.trim() : null, found, read, List.copyOf(sources),
				analysisOutcome.getNotCovered());
	}
}
