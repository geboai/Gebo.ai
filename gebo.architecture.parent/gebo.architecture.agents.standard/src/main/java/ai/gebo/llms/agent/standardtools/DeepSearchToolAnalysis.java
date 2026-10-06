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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.FoldOutcome;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.RollingFold;
import ai.gebo.llms.deepsearch.service.DeepSearchVerdict;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchBatchTrace;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchBatchTrace.NumberedBatch;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * The analysis of the deep search tools: the fragments found are analysed batch by
 * batch, each batch fitting the service model's tokens budget, into partial
 * analyses reduced into the final one by the {@link TokensBudgetFluxCoordinator},
 * with the deep search prompts. The analysis stops once enough partial analyses
 * fully satisfy the question, and the fragments the model judges irrelevant are
 * collected so their documents are not given as sources.
 * <p>
 * The same map/reduce the deep search pipelines run, kept apart from them: a tool
 * has no pipeline to notify, and the question the fragments are analysed against
 * is the agent's one, carried by the given request context.
 */
@Service
public class DeepSearchToolAnalysis extends BaseLLMSInvokingAndProvidingService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DeepSearchToolAnalysis.class);
	/**
	 * Share of the service model's context a batch of fragments of a partial analysis
	 * may fill: half of it, the rest left to the prompt and to the partial analysis it
	 * writes.
	 */
	static final double BATCH_CONTEXT_SHARE = 0.5d;
	private static final String SORRY_SOMETHING_GONE_WRONG = "Sorry, something gone wrong on last step of the execution";
	private static final String CONSOLIDATED_SUMMARY_PROMPT_PARAM = "consolidated";
	static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
	private static final String IRRELEVANT_FRAGMENT_MARKER = "IRRILEVANT";
	private static final String COMMA_CHARACTER = ",";
	private final IGPromptConfigDao promptsDao;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;

	public DeepSearchToolAnalysis(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGPromptConfigDao promptsDao,
			DeepSearchDefaultConfig defaultDeepsearchConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.promptsDao = promptsDao;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
	}

	/**
	 * Analyses the fragments against the question of the context, sized for the
	 * given deliverable.
	 *
	 * @param completenessNote     added to what the deliverable asks, e.g. who reads
	 *                             the analysis
	 * @param chatModel            writes the final analysis
	 * @param serviceModel         writes the partial analyses
	 * @param discardedFragmentIds receives the fragments judged irrelevant or left
	 *                             unprocessed
	 * @param notifier             tells the user the progress of the analysis
	 *                             ({@link IGProgressNotifier#NONE} for none)
	 * @return the final analysis, streamed
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier) {
		return analyze(fragments, context, runAs, deliverable, completenessNote, chatModel, serviceModel,
				discardedFragmentIds, notifier, null);
	}

	/**
	 * The same, handing out what the consolidation reports as missing.
	 *
	 * @param outcome receives what the last consolidation of the partial analyses
	 *                reports as missing (its verdict), null when it reports the report
	 *                complete, left untouched when no consolidation runs (a single
	 *                batch of fragments, no sufficiency check); and the fragments left
	 *                unread. May be null
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier,
			DeepSearchAnalysisOutcome outcome) {
		final GPromptTemplateConfig cumulativeAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT);
		final GPromptTemplateConfig finalAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT);
		final GPromptTemplateConfig emptyResponsePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);
		final int satisfactoryThreshold = defaultDeepsearchConfig.getSatisfactorySubAnalisysThreashold(deliverable);
		final int analysisParallelism = Math.max(1, defaultDeepsearchConfig.getAnalysisParallelism());
		final AtomicInteger satisfactorySubanalisys = new AtomicInteger(0);
		final long tokensBudget = (long) (serviceModel.getContextLength() * BATCH_CONTEXT_SHARE);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin analyze(...) deliverable:" + deliverable + " tokensBudget:" + tokensBudget
					+ " parallelism:" + analysisParallelism + " satisfactoryThreshold:" + satisfactoryThreshold
					+ " sufficiencyCheck:" + defaultDeepsearchConfig.isSufficiencyCheckEnabled());
		}
		final Map<String, Object> sharedParams = new HashMap<>();
		sharedParams.put(AGENT_DELIVERABLE_COMPLETENESS,
				(deliverable != null ? deliverable.name() + " " + deliverable.getAgentDeliverableCompleteness() : "")
						+ (completenessNote != null ? completenessNote : ""));
		final Flux<String> backupNotFoundDocuments = Flux.defer(() -> {
			Flux<String> outFlux = null;
			try {
				Map<String, Object> params = new HashMap<>(sharedParams);
				params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "");
				params.put(CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
				outFlux = callLLMReactive(chatModel, emptyResponsePrompt, context, params);
			} catch (Throwable th) {
				LOGGER.error("Deep search tool analysis failed on empty results", th);
				outFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
			}
			return outFlux;
		});
		GenerativeFunction<Document, String> intermediateProcess = (initialValue, _emitter, documentsList) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				Map<String, Object> params = new HashMap<>(sharedParams);
				params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search tool partial analysis of " + documentsList.size() + " fragment(s) batch: "
							+ DeepSearchBatchTrace.composition(documentsList));
				}
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("Deep search tool partial analysis fragments: "
							+ DeepSearchBatchTrace.fragmentSources(documentsList));
				}
				final long start = System.currentTimeMillis();
				// the fragments numbered: the model lists short numbers, not long ids
				final NumberedBatch numbered = DeepSearchBatchTrace.numbered(documentsList);
				// streamed: a long analysis keeps arriving instead of tripping the read timeout,
				// and is stopped as soon as its irrelevant fragments list runs away
				final String intermediateAnalisys = streamLLMWithDocumentsAndConsolidation(serviceModel,
						cumulativeAnalisysPrompt, context, numbered.documents(), initialValue, params,
						DeepSearchBatchTrace.runawayWatch(IRRELEVANT_FRAGMENT_MARKER, numbered.documents().size()));
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<DEEP_SEARCH_TOOL_PARTIAL_ANALYSIS>");
					LOGGER.trace(intermediateAnalisys);
					LOGGER.trace("</DEEP_SEARCH_TOOL_PARTIAL_ANALYSIS>");
				}
				final int discardedBefore = discardedFragmentIds.size();
				final String runaway = DeepSearchBatchTrace.runawayReport(intermediateAnalisys,
						IRRELEVANT_FRAGMENT_MARKER, numbered.documents());
				final String cleaned;
				if (runaway != null) {
					// a list that repeats itself or makes ids up says nothing reliable: the
					// fragments of the batch stay read and not judged
					LOGGER.warn("Deep search tool partial analysis ran away in " + (System.currentTimeMillis() - start)
							+ " ms, " + (intermediateAnalisys != null ? intermediateAnalisys.length() : 0)
							+ " character(s), its irrelevant fragments list ignored: " + runaway + "on batch: "
							+ DeepSearchBatchTrace.composition(documentsList));
					cleaned = DeepSearchBatchTrace.withoutIrrelevantLists(intermediateAnalisys,
							IRRELEVANT_FRAGMENT_MARKER);
				} else {
					final Vector<String> numbers = new Vector<>();
					cleaned = cumulateDiscardedFragmentsAndCleanOutput(intermediateAnalisys, numbers);
					discardByNumber(numbers, numbered, discardedFragmentIds);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search tool partial analysis of " + documentsList.size() + " fragment(s) done in "
							+ (System.currentTimeMillis() - start) + " ms: "
							+ (intermediateAnalisys != null ? intermediateAnalisys.length() : 0) + " character(s), "
							+ (discardedFragmentIds.size() - discardedBefore) + " fragment id(s) discarded");
				}
				return cleaned;
			});
		};
		LastWork<String, String> finalAnalisysWork = (list, _emitter) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				if (list != null && !list.isEmpty()) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deep search tool final analysis of " + list.size() + " partial analyses");
					}
					Map<String, Object> params = new HashMap<>(sharedParams);
					params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, list);
					params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
					return DeepSearchVerdict
							.withoutVerdict(callLLMReactive(chatModel, finalAnalisysPrompt, context, params));
				} else {
					return backupNotFoundDocuments;
				}
			});
		};
		Predicate<Document> isValidDocument = (document) -> document.isText() && document.getText() != null
				&& document.getText().trim().length() > 0;
		TokensLimitCompute<Document> tokensLimitCompute = (list, budget) -> TokensBudgetCalculator
				.higherThanBudget(list, budget);
		Predicate<String> outOfBandString = (v) -> v == null || v.equals(ERROR_IN_PROCESS);
		Predicate<String> isEndOfProcessingCondition = (text) -> text != null
				&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY)
				&& satisfactorySubanalisys.incrementAndGet() > satisfactoryThreshold;
		Function<String, String> outputCleaningFunction = (text) -> text.replace(PARTIAL_ANALISYS_SATISFACTORY, "");
		final Consumer<Document> unprocessedCumulator = (document) -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deep search tool left fragment unprocessed:" + document.getId());
			}
			discardedFragmentIds.add(document.getId());
			if (outcome != null) {
				// not judged irrelevant: never read
				outcome.getUnreadFragmentIds().add(document.getId());
			}
		};
		final IGProgressNotifier progress = notifier != null ? notifier : IGProgressNotifier.NONE;
		final Flux<String> resultFlux;
		if (defaultDeepsearchConfig.isSufficiencyCheckEnabled()) {
			// the partial analyses folded into a running report, the analysis stopping once the
			// consolidation model judges it enough (its verdict line)
			final int minimumAnalysedBatches = defaultDeepsearchConfig.minimumAnalysedBatchesBeforeStop(deliverable);
			// a fold holds the report and the analyses in 2/3 of the chat model context, beside its prompt
			final long foldTokensBudget = Math.max(1, chatModel.getContextLength() * 2l / 3 - finalAnalisysPrompt.getTokensSize());
			final RollingFold<String> rollingFold = (report, partials, _emitter) -> {
				return runAs.doRunAsWithReturnAndException(() -> {
					final Map<String, Object> params = new HashMap<>(sharedParams);
					final DeepSearchVerdict verdict = DeepSearchVerdict.of(callLLMWithDocumentsAndConsolidation(
							chatModel, finalAnalisysPrompt, context, partials, report, params));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deep search tool fold of " + partials.size() + " partial analyses: complete:"
								+ verdict.complete() + (verdict.missing() != null ? " missing:" + verdict.missing() : ""));
					}
					if (outcome != null) {
						// the last fold's verdict is the one on the whole report
						outcome.setNotCovered(verdict.complete() ? null : verdict.missing());
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<DEEP_SEARCH_TOOL_RUNNING_REPORT>");
						LOGGER.trace(verdict.report());
						LOGGER.trace("</DEEP_SEARCH_TOOL_RUNNING_REPORT>");
					}
					return new FoldOutcome<String>(verdict.report(), verdict.complete());
				});
			};
			resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinateWithRollingFold(fragments, progress,
					isValidDocument, tokensLimitCompute, intermediateProcess, rollingFold,
					reports -> String.join("\n\n", reports), ITokensCountable::stringsTokensSize, foldTokensBudget, "",
					ERROR_IN_PROCESS, outOfBandString, outputCleaningFunction, STRING_STREAMER, backupNotFoundDocuments,
					tokensBudget, runAs, analysisParallelism, minimumAnalysedBatches, unprocessedCumulator);
		} else {
			resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinate(fragments, progress, isValidDocument,
					tokensLimitCompute, intermediateProcess, finalAnalisysWork, "", ERROR_IN_PROCESS, outOfBandString,
					ERROR_IN_PROCESS, outOfBandString, isEndOfProcessingCondition, outputCleaningFunction,
					STRING_STREAMER, tokensBudget, runAs, analysisParallelism, unprocessedCumulator);
		}
		return resultFlux.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
	}

	/** Streams an already complete text in small pieces, as a model would. */
	private static final Function<String, Flux<String>> STRING_STREAMER = (data) -> {
		String inputString = data != null ? data : "";
		List<String> separateTokens = new ArrayList<>();
		for (int index = 0; index < inputString.length(); index += 4) {
			int stopChar = Math.min(index + 4, inputString.length());
			separateTokens.add(inputString.substring(index, stopChar));
		}
		return Flux.fromIterable(separateTokens);
	};

	/**
	 * The fragments of a numbered batch the model listed as irrelevant, by number: each
	 * added once to the discarded ones; a number no fragment has (made up) is ignored.
	 */
	static void discardByNumber(List<String> numbers, NumberedBatch numbered, Vector<String> discardedFragmentIds) {
		for (String number : numbers) {
			final String fragmentId = numbered.idOf(number);
			if (fragmentId == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search tool analysis listed fragment number:" + number + " not in its batch");
				}
				continue;
			}
			synchronized (discardedFragmentIds) {
				// the partial analyses run in parallel
				if (!discardedFragmentIds.contains(fragmentId)) {
					discardedFragmentIds.add(fragmentId);
				}
			}
		}
	}

	static String cumulateDiscardedFragmentsAndCleanOutput(String intermediateAnalisys,
			Vector<String> discardedFragmentIds) {
		if (intermediateAnalisys == null || intermediateAnalisys.trim().length() == 0)
			return "";
		final int startCharacter = intermediateAnalisys.toLowerCase().indexOf(IRRELEVANT_FRAGMENT_MARKER.toLowerCase());
		if (startCharacter < 0)
			return intermediateAnalisys;
		final int endCharacter = Math.max(intermediateAnalisys.indexOf("\r", startCharacter),
				intermediateAnalisys.indexOf("\n", startCharacter));
		if (endCharacter < 0) {
			extractIrrelevantFragmentsFromLine(intermediateAnalisys.substring(startCharacter), discardedFragmentIds);
			return intermediateAnalisys.substring(0, startCharacter);
		} else {
			extractIrrelevantFragmentsFromLine(intermediateAnalisys.substring(startCharacter, endCharacter),
					discardedFragmentIds);
			return intermediateAnalisys.substring(0, startCharacter) + intermediateAnalisys.substring(endCharacter);
		}
	}

	private static void extractIrrelevantFragmentsFromLine(String line, Vector<String> discardedFragmentIds) {
		int startIndex = line.toLowerCase().indexOf(IRRELEVANT_FRAGMENT_MARKER.toLowerCase());
		String commaSeparatedList = line.substring(startIndex + IRRELEVANT_FRAGMENT_MARKER.length()).replace("=", "")
				.trim();
		if (commaSeparatedList.length() > 0) {
			StringTokenizer tokenizer = new StringTokenizer(commaSeparatedList, COMMA_CHARACTER);
			while (tokenizer.hasMoreTokens()) {
				StringBuilder cleanedFragmentId = new StringBuilder();
				for (char ch : tokenizer.nextToken().toCharArray()) {
					if (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '-') {
						cleanedFragmentId.append(ch);
					}
				}
				// a model can repeat the same id over and over: each id counts once
				final String fragmentId = cleanedFragmentId.toString();
				synchronized (discardedFragmentIds) {
					// the partial analyses run in parallel
					if (fragmentId.isEmpty() || discardedFragmentIds.contains(fragmentId)) {
						continue;
					}
					discardedFragmentIds.add(fragmentId);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search tool analysis discarded fragment:" + fragmentId);
				}
			}
		}
	}
}
