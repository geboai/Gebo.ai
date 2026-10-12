/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

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

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.FoldOutcome;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LaneBudget;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.RollingFold;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.llms.deepsearch.service.DeepSearchOutputThinking;
import ai.gebo.llms.deepsearch.service.DocumentNamesShown;
import ai.gebo.llms.deepsearch.service.DeepSearchVerdict;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * The deep search analysis, the one map/reduce of the deep search pipelines and of the
 * deep search tools: the fragments found are analysed batch by batch, each batch
 * fitting the service model's tokens budget, into partial analyses reduced into the
 * final one by the {@link TokensBudgetFluxCoordinator}, with the deep search prompts.
 * The analysis stops once enough partial analyses fully satisfy the question (or, with
 * the sufficiency check, once the consolidation judges the running report enough).
 * <p>
 * Each partial analysis lists the fragments it finds relevant (recorded in the given
 * {@link DeepSearchRelevance}, its list ignored when it runs away), and its quotations
 * are checked against the fragments of its batch (kept in the given
 * {@link DeepSearchQuotations}). The text returned still carries the verified quotations
 * by fragment: the caller renders them ({@link DeepSearchQuotations#render(Flux)}) where
 * its answer is assembled.
 */
@Service
public class DeepSearchAnalysis extends BaseLLMSInvokingAndProvidingService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DeepSearchAnalysis.class);
	static final String SORRY_SOMETHING_GONE_WRONG = "Sorry, something gone wrong on last step of the execution";
	private static final String EXCEPTION_ON_EMPTY_RESULTS = "Exception on empty results";
	private static final String CONSOLIDATED_SUMMARY_PROMPT_PARAM = "consolidated";
	public static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
	private final IGPromptConfigDao promptsDao;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;

	public DeepSearchAnalysis(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGPromptConfigDao promptsDao,
			DeepSearchDefaultConfig defaultDeepsearchConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.promptsDao = promptsDao;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
	}

	/**
	 * The text the analysis prompts are told the deliverable is: its name and the
	 * completeness it asks, with the caller's note (e.g. who reads the analysis).
	 */
	static String deliverableCompleteness(DeliverableIntent deliverable, String completenessNote) {
		return (deliverable != null ? deliverable.name() + " " + deliverable.getAgentDeliverableCompleteness() : "")
				+ (completenessNote != null ? completenessNote : "");
	}

	/**
	 * Analyses the fragments against the question of the context, sized for the
	 * deliverable.
	 *
	 * @param deliverable          what the analysis is for: sizes it and decides when
	 *                             enough partial analyses satisfy the question
	 * @param completenessNote     added to what the deliverable asks, e.g. who reads the
	 *                             analysis; may be null
	 * @param chatModel            writes the final analysis (and the consolidations)
	 * @param serviceModel         writes the partial analyses
	 * @param discardedFragmentIds receives the fragments left unprocessed
	 * @param notifier             tells the user the progress of the analysis
	 * @param quotations           receives the verified quotations of the partial
	 *                             analyses
	 * @param relevance            receives what the partial analyses find relevant
	 * @param outcome              receives what the last consolidation reports as
	 *                             missing and the fragments left unread; may be null
	 * @param onModelFailure       run when the model fails on empty results (e.g. the
	 *                             user told); may be null
	 * @param label                names the caller in the logs
	 * @return the final analysis, streamed, its quotations not rendered yet
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier,
			DeepSearchQuotations quotations, DeepSearchRelevance relevance, DeepSearchAnalysisOutcome outcome,
			Runnable onModelFailure, String label) {
		return analyze(fragments, context, runAs, deliverable, completenessNote, chatModel, serviceModel,
				discardedFragmentIds, notifier, quotations, relevance, outcome, onModelFailure, label,
				DeepSearchOutputThinking.NONE);
	}

	/**
	 * The same, the reasoning of the calls writing the output (the final analysis, the
	 * running report's folds, the answer when nothing was found) given to
	 * {@code outputThinking}: the deep search answering the user streams it to the chat.
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier,
			DeepSearchQuotations quotations, DeepSearchRelevance relevance, DeepSearchAnalysisOutcome outcome,
			Runnable onModelFailure, String label, DeepSearchOutputThinking outputThinking) {
		final DeepSearchOutputThinking thinking = outputThinking != null ? outputThinking
				: DeepSearchOutputThinking.NONE;
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
		sharedParams.put(AGENT_DELIVERABLE_COMPLETENESS, deliverableCompleteness(deliverable, completenessNote));
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
			LOGGER.debug(label + " analysis: deliverable:" + deliverable + " analyses model:" + serviceModel.getCode()
					+ " context:" + serviceModel.getContextLength() + " batch budget with no consolidation:"
					+ emptyBatchBudget + " (tok) lanes:" + analysisParallelism + " a lane goes on while its budget is "
					+ laneBudget.minimumBatchBudget() + " (tok) at least, satisfactoryThreshold:" + satisfactoryThreshold
					+ " sufficiencyCheck:" + defaultDeepsearchConfig.isSufficiencyCheckEnabled());
		}
		final Flux<String> backupNotFoundDocuments = Flux.defer(() -> {
			Flux<String> outFlux = null;
			try {
				Map<String, Object> params = new HashMap<>(sharedParams);
				params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "");
				params.put(CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
				outFlux = thinking.text(callLLMReactiveAnswer(chatModel, emptyResponsePrompt, context, params));
			} catch (Throwable th) {
				if (onModelFailure != null) {
					onModelFailure.run();
				}
				LOGGER.error(label + " analysis: " + EXCEPTION_ON_EMPTY_RESULTS, th);
				outFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
			}
			return outFlux;
		});
		GenerativeFunction<Document, String> intermediateProcess = (initialValue, _emitter, documentsList) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				Map<String, Object> params = new HashMap<>(sharedParams);
				params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(label + " begin partial analysis of " + documentsList.size() + " fragment(s) with model:"
							+ serviceModel.getCode() + " batch: " + DeepSearchBatchTrace.composition(documentsList));
				}
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace(label + " partial analysis fragments: " + DeepSearchBatchTrace.fragmentSources(documentsList));
				}
				final long start = System.currentTimeMillis();
				// the fragments numbered: the model lists short numbers, not long ids
				final DeepSearchBatchTrace.NumberedBatch numbered = DeepSearchBatchTrace.numbered(documentsList);
				// streamed: a long analysis keeps arriving instead of tripping the read timeout,
				// and is stopped as soon as its relevant fragments list runs away
				final String intermediateAnalisys = streamLLMWithDocumentsAndConsolidation(serviceModel,
						cumulativeAnalisysPrompt, context, numbered.documents(), initialValue, params,
						DeepSearchBatchTrace.runawayWatch(DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER,
								numbered.documents().size()));
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<DEEP_SEARCH_PARTIAL_ANALYSIS caller=\"" + label + "\">");
					LOGGER.trace(intermediateAnalisys);
					LOGGER.trace("</DEEP_SEARCH_PARTIAL_ANALYSIS>");
				}
				final String runaway = DeepSearchBatchTrace.runawayReport(intermediateAnalisys,
						DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER, numbered.documents());
				if (runaway != null) {
					// a list that repeats itself or makes ids up says nothing reliable: ignored
					LOGGER.warn(label + " partial analysis ran away in " + (System.currentTimeMillis() - start) + " ms, "
							+ (intermediateAnalisys != null ? intermediateAnalisys.length() : 0)
							+ " character(s), its relevant fragments list ignored: " + runaway + "on batch: "
							+ DeepSearchBatchTrace.composition(documentsList));
				}
				final int relevant = relevance.recordAnalysis(intermediateAnalisys, numbered, runaway != null);
				final String cleaned = DeepSearchBatchTrace.withoutIrrelevantLists(intermediateAnalisys,
						DeepSearchRelevance.RELEVANT_FRAGMENTS_MARKER);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(label + " end partial analysis of " + documentsList.size() + " fragment(s) in "
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
						LOGGER.debug(label + " final analysis of " + list.size() + " partial analyses");
					}
					Map<String, Object> params = new HashMap<>(sharedParams);
					params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, list);
					params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
					return DeepSearchVerdict.withoutVerdict(
							thinking.text(callLLMReactiveAnswer(chatModel, finalAnalisysPrompt, context, params)));
				} else {
					return backupNotFoundDocuments;
				}
			});
		};
		Predicate<Document> isValidDocument = (document) -> document.isText() && document.getText() != null
				&& document.getText().trim().length() > 0;
		final TokensLimitCompute<Document> tokensLimitCompute = new TokensLimitCompute<Document>() {
			@Override
			public boolean higherThanBudgetTokens(List<Document> list, long budget) {
				return TokensBudgetCalculator.higherThanBudget(list, budget);
			}

			/** The names of the batch's documents, of the knowledge base or of an external source. */
			@Override
			public String describe(List<Document> batch) {
				final String names = DocumentNamesShown.ofFragments(batch);
				return names != null ? names + " (" + batch.size() + " fragments)" : batch.size() + " fragments";
			}
		};
		Predicate<String> outOfBandString = (v) -> v == null || v.equals(ERROR_IN_PROCESS);
		Predicate<String> isEndOfProcessingCondition = (text) -> text != null
				&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY)
				&& satisfactorySubanalisys.incrementAndGet() > satisfactoryThreshold;
		Function<String, String> outputCleaningFunction = (text) -> text.replace(PARTIAL_ANALISYS_SATISFACTORY, "");
		final Consumer<Document> unprocessedCumulator = (document) -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(label + " left fragment unprocessed:" + document.getId());
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
				LOGGER.debug(label + " consolidation: model:" + chatModel.getCode() + " context:"
						+ chatModel.getContextLength() + " fold budget with no report:" + foldBudget.applyAsLong("")
						+ " (tok)");
			}
			final RollingFold<String> rollingFold = (report, partials, _emitter) -> {
				return runAs.doRunAsWithReturnAndException(() -> {
					final Map<String, Object> params = new HashMap<>(sharedParams);
					// the report is the output: the fold's reasoning goes where the output's goes
					final GChatAnswer folded = answerLLMWithDocumentsAndConsolidation(chatModel, finalAnalisysPrompt,
							context, partials, report, params);
					thinking.reasoning(folded.thinking());
					final DeepSearchVerdict verdict = DeepSearchVerdict.of(folded.answer());
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(label + " fold of " + partials.size() + " partial analyses: complete:"
								+ verdict.complete() + (verdict.missing() != null ? " missing:" + verdict.missing() : ""));
					}
					if (outcome != null) {
						// the last fold's verdict is the one on the whole report
						outcome.setNotCovered(verdict.complete() ? null : verdict.missing());
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<DEEP_SEARCH_RUNNING_REPORT caller=\"" + label + "\">");
						LOGGER.trace(verdict.report());
						LOGGER.trace("</DEEP_SEARCH_RUNNING_REPORT>");
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
}
