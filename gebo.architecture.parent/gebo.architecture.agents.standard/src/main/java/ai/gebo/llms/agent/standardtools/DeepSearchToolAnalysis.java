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
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

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
import ai.gebo.llms.deepsearch.service.impl.DeepSearchBudgets;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchRelevance;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchBatchTrace.NumberedBatch;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LaneBudget;
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
	private static final String SORRY_SOMETHING_GONE_WRONG = "Sorry, something gone wrong on last step of the execution";
	private static final String CONSOLIDATED_SUMMARY_PROMPT_PARAM = "consolidated";
	static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
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
	 * @param discardedFragmentIds receives the fragments left unprocessed
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
		final Map<String, Object> sharedParams = new HashMap<>();
		sharedParams.put(AGENT_DELIVERABLE_COMPLETENESS,
				(deliverable != null ? deliverable.name() + " " + deliverable.getAgentDeliverableCompleteness() : "")
						+ (completenessNote != null ? completenessNote : ""));
		// the batch of an analysis: the one budget formula on the model writing the analyses, given
		// the consolidation its lane carries; a lane whose consolidation leaves less than a piece of
		// a document hands it over and starts a new chain
		final Map<String, Object> analysisKnown = DeepSearchBudgets.knownValues(cumulativeAnalisysPrompt, sharedParams,
				context);
		final ToLongFunction<String> batchBudget = consolidation -> Math.max(0,
				computeFragmentBudget(consolidation != null ? consolidation : "", cumulativeAnalisysPrompt.getTokensSize(),
						serviceModel.getContextLength(), analysisKnown));
		final long emptyBatchBudget = batchBudget.applyAsLong("");
		final LaneBudget<String> laneBudget = new LaneBudget<>(batchBudget,
				defaultDeepsearchConfig.chunkTokens(emptyBatchBudget));
		// an analysis judging what its lane consolidated enough hands it over
		final Predicate<String> laneSatisfied = text -> text != null
				&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin analyze(...) deliverable:" + deliverable + " batch budget with no consolidation:"
					+ emptyBatchBudget + " (tok) lanes:" + analysisParallelism + " satisfactoryThreshold:"
					+ satisfactoryThreshold + " sufficiencyCheck:" + defaultDeepsearchConfig.isSufficiencyCheckEnabled());
		}
		// the quotations of this analysis: the outcome's when given, so the tool can list them
		// what the partial analyses find relevant: told in the logs, the agent choosing the
		// documents its answer rests on
		final DeepSearchRelevance relevance = new DeepSearchRelevance();
		final DeepSearchQuotations quotations = outcome != null ? outcome.getQuotations()
				: new DeepSearchQuotations();
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
				// and is stopped as soon as its relevant fragments list runs away
				final String intermediateAnalisys = streamLLMWithDocumentsAndConsolidation(serviceModel,
						cumulativeAnalisysPrompt, context, numbered.documents(), initialValue, params,
						DeepSearchBatchTrace.runawayWatch(DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER,
								numbered.documents().size()));
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<DEEP_SEARCH_TOOL_PARTIAL_ANALYSIS>");
					LOGGER.trace(intermediateAnalisys);
					LOGGER.trace("</DEEP_SEARCH_TOOL_PARTIAL_ANALYSIS>");
				}
				final String runaway = DeepSearchBatchTrace.runawayReport(intermediateAnalisys,
						DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER, numbered.documents());
				if (runaway != null) {
					// a list that repeats itself or makes ids up says nothing reliable: ignored
					LOGGER.warn("Deep search tool partial analysis ran away in " + (System.currentTimeMillis() - start)
							+ " ms, " + (intermediateAnalisys != null ? intermediateAnalisys.length() : 0)
							+ " character(s), its relevant fragments list ignored: " + runaway + "on batch: "
							+ DeepSearchBatchTrace.composition(documentsList));
				}
				final int relevant = relevance.recordAnalysis(intermediateAnalisys, numbered, runaway != null);
				final String cleaned = DeepSearchBatchTrace.withoutIrrelevantLists(intermediateAnalisys,
						DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search tool partial analysis of " + documentsList.size() + " fragment(s) done in "
							+ (System.currentTimeMillis() - start) + " ms: "
							+ (intermediateAnalisys != null ? intermediateAnalisys.length() : 0) + " character(s), "
							+ relevant + " fragment(s) relevant by its list or the documents it names");
				}
				// its quotations checked against the fragments of its batch (best effort)
				return quotations.keepVerified(cleaned, numbered);
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
				// never read
				outcome.getUnreadFragmentIds().add(document.getId());
			}
		};
		final IGProgressNotifier progress = notifier != null ? notifier : IGProgressNotifier.NONE;
		final Flux<String> resultFlux;
		if (defaultDeepsearchConfig.isSufficiencyCheckEnabled()) {
			// the partial analyses folded into a running report, the analysis stopping once the
			// consolidation model judges it enough (its verdict line)
			final int minimumAnalysedBatches = defaultDeepsearchConfig.minimumAnalysedBatchesBeforeStop(deliverable);
			// the lanes' hand-overs a fold takes: the one budget formula on the chat model, which
			// writes it, given the report the fold carries
			final Map<String, Object> foldKnown = DeepSearchBudgets.knownValues(finalAnalisysPrompt, sharedParams,
					context);
			final ToLongFunction<String> foldBudget = report -> Math.max(0,
					computeFragmentBudget(report != null ? report : "", finalAnalisysPrompt.getTokensSize(),
							chatModel.getContextLength(), foldKnown));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deep search tool consolidation: model:" + chatModel.getCode()
						+ " fold budget with no report:" + foldBudget.applyAsLong("") + " (tok)");
			}
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
					reports -> String.join("\n\n", reports), ITokensCountable::stringsTokensSize, foldBudget, "",
					ERROR_IN_PROCESS, outOfBandString, outputCleaningFunction, STRING_STREAMER, backupNotFoundDocuments,
					laneBudget, runAs, analysisParallelism, minimumAnalysedBatches, laneSatisfied, unprocessedCumulator);
		} else {
			resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinate(fragments, progress, isValidDocument,
					tokensLimitCompute, intermediateProcess, finalAnalisysWork, "", ERROR_IN_PROCESS, outOfBandString,
					ERROR_IN_PROCESS, outOfBandString, isEndOfProcessingCondition, outputCleaningFunction,
					STRING_STREAMER, laneBudget, runAs, analysisParallelism, laneSatisfied, unprocessedCumulator);
		}
		// the quotations the standard way, with no fragment id
		return quotations.render(resultFlux).subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
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

}
