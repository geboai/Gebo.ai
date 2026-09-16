package ai.gebo.architecture.rag_threasholds_autotune.service.impl;

import java.util.Set;
import java.util.HashSet;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IdentityUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentMetadata;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SearchRequest.Builder;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.rag_threasholds_autotune.config.RagThreasholdAutotuneConfig;
import ai.gebo.architecture.rag_threasholds_autotune.config.RagThreasholdAutotunePromptConfig;
import ai.gebo.architecture.rag_threasholds_autotune.model.AutotuneVectorStoreInfo;
import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import ai.gebo.architecture.rag_threasholds_autotune.model.ThreasholdAutotuneProcessResult;
import ai.gebo.architecture.rag_threasholds_autotune.model.ThreasholdAutotuneProcessResult.ListOfJsonObjects;
import ai.gebo.architecture.rag_threasholds_autotune.repository.ThreasholdAutotuneProcessResultRepository;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneMatchRate;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneMatchWithRate;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneQueryHardness;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneQuestion;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneQuestionResult;
import ai.gebo.architecture.rag_threasholds_autotune.service.impl.model.AutoTuneRatedThreashold;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.DocumentMetaInfos;

@Component
@Scope("singleton")
public class RagThreasholdAutotuneServiceImpl extends BaseLLMSInvokingAndProvidingService
		implements IRagThreasholdAutotuneService {
	private final ThreasholdAutotuneProcessResultRepository resultRepo;
	private final VectorizedContentRepository vectorizedContentsRepository;
	private final RagThreasholdAutotuneConfig config;
	private final IGPromptConfigDao promptsDao;
	/**
	 * The platform's own identity. The tuning runs on a scheduler thread, which carries
	 * no SecurityContext at all, and the first thing it does is call an LLM to generate
	 * the sample questions - a path that reaches isCurrentUserAdmin(..) and threw
	 * "Not authenticated", leaving the question list empty. The bound discovery then
	 * probed 41 thresholds against zero questions, found zero results at every one of
	 * them, walked below zero and aborted with "Threashold is -0.025 seriusly wrong!".
	 */
	private final IGeboSystemUserService systemUserService;
	private static final Logger LOGGER = LoggerFactory.getLogger(RagThreasholdAutotuneServiceImpl.class);
	private static boolean runningTuning = false;

	public RagThreasholdAutotuneServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao,
			ThreasholdAutotuneProcessResultRepository resultRepo,
			VectorizedContentRepository vectorizedContentsRepository, RagThreasholdAutotuneConfig config,
			IGPromptConfigDao promptsDao, IGeboSystemUserService systemUserService) {
		super(chatModelsConfigDao, embeddingModelsConfigDao);
		this.resultRepo = resultRepo;
		this.vectorizedContentsRepository = vectorizedContentsRepository;
		this.config = config;
		this.promptsDao = promptsDao;
		this.systemUserService = systemUserService;
	}

	@Override
	public OptimizedThreashold findByVectorStoreId(String vectorStoreId) {

		ThreasholdAutotuneProcessResult data = internalFindByVectorStoreId(vectorStoreId);
		return data != null ? data.getThreasholds() : null;
	}

	@Override
	public OptimizedThreashold findByEmbeddingModelCode(String embeddingModelCode) {

		ThreasholdAutotuneProcessResult data = internalFindByEmbeddingModelCode(embeddingModelCode);
		return data != null ? data.getThreasholds() : null;
	}

	@Override
	public OptimizedThreashold findByKnowledgeBase(String knowledgeBaseCode) {
		List<ThreasholdAutotuneProcessResult> data = resultRepo.findByRootKnowledgeBase(knowledgeBaseCode);
		return data.isEmpty() ? null : data.get(0).getThreasholds();
	}

	private String inExpression(String field, List<String> ids) {
		String _expression = field + " IN [";
		for (int i = 0; i < ids.size(); i++) {
			_expression += "'" + ids.get(i) + "'";
			if (i < ids.size() - 1) {
				_expression += ",";
			}
		}
		_expression += "]";
		return _expression;
	}

	@Scheduled(initialDelay = 10000, fixedRate = 240 * 60000)
	public void onTick() {
		if (!config.isEnabled())
			return;
		if (runningTuning)
			return;
		try {
			synchronized (this) {
				runningTuning = true;
			}
			// No user asked for this work, so it runs as the platform itself rather than on
			// an empty SecurityContext. Everything the tuning touches - reading the model
			// configurations, searching the vector store, calling the LLM to generate and
			// rate the questions - goes through the normal authorization path under this
			// identity instead of failing at the first isCurrentUserAdmin(..).
			IdentityUtil.create(systemUserService.getUsername(), systemUserService.getRoles()).doAs(() -> {
				List<String> vectorStoreIds = embeddingModelsRuntimeDao.getConfigurations().stream()
						.map(x -> x.getCode()).toList();
				for (String vectorStoreId : vectorStoreIds) {
					try {
						processAutotune(vectorStoreId);
					} catch (LLMConfigException e) {
						LOGGER.error("Error in processAutotune(" + vectorStoreId + ")", e);
					}
				}
			});
		} finally {
			synchronized (this) {
				runningTuning = false;
			}
		}
	}

	@Override
	public void processAutotune(String vectorStoreId) throws LLMConfigException {
		final int MAXQUESTIONS = this.config.getAutotuneMaxGeneratedQuestions();

		ThreasholdAutotuneProcessResult lastEntry = internalFindByVectorStoreId(vectorStoreId);
		long count = vectorizedContentsRepository.countByIdVectorStoreId(vectorStoreId);
		if (count == 0l) {
			LOGGER.info("No found vectorized entries in " + vectorStoreId + " exiting autotune process");
			return;
		}
		long lastCardinality = lastEntry != null && lastEntry.getVectorStoreVectorizedCount() != null
				? lastEntry.getVectorStoreVectorizedCount()
				: 0l;
		boolean runOptimization = lastEntry == null && count > 0l;
		if (runOptimization) {
			LOGGER.info(
					"Running threashold tuning, because it has been run before lastEntry == null and count = " + count);
		}
		if (lastEntry != null && lastEntry.getProcessedDateTime() != null && !runOptimization) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(GregorianCalendar.DAY_OF_YEAR, -1 * config.getDayElapsedWithoutTuning());
			Date dateThreashold = calendar.getTime();
			runOptimization = lastEntry.getProcessedDateTime().before(dateThreashold);
			if (runOptimization) {
				LOGGER.info("Running threashold tuning, because it has been run before "
						+ config.getDayElapsedWithoutTuning() + " days ago");
			}
		}
		if (!runOptimization && lastEntry != null && lastCardinality > 0l) {
			double delta = Math.abs(count - lastCardinality);
			double lastCardinalityD = lastCardinality;
			double deltaPercent = delta / lastCardinalityD * 100.0;
			runOptimization = deltaPercent >= config.getDocumentsCardinalityAddedPercentTrigger();
			if (runOptimization) {
				LOGGER.info("Running threashold tuning, because the amount of data is aughmented by " + deltaPercent
						+ " %");
			}
		}
		if (runOptimization) {
			IGConfigurableEmbeddingModel embeddingModel = embeddingModelsRuntimeDao.findByCode(vectorStoreId);
			VectorStore vectorStore = embeddingModel.getVectorStore();
			final int budgetTotal = 30;
			final List<Document> sampled = new ArrayList<Document>();
			if (config.getAutotuneSamples() != null && config.getAutotuneSamples().getPhrases() != null
					&& !config.getAutotuneSamples().getPhrases().isEmpty()) {
				sampled.addAll(sampleFromConfiguredPhrases(vectorStore, budgetTotal));
			} else {
				// Draw a wider pool than needed, then keep the fragments that actually
				// represent the corpus rather than the first ones the store happened to return.
				final List<Document> candidates = sampleAcrossCorpus(vectorStoreId, vectorStore,
						budgetTotal * Math.max(1, config.getSampleCandidatesPerFragment()));
				sampled.addAll(selectRepresentative(vectorStore, candidates, budgetTotal));
			}
			final int topK = sampled.size();
			IGConfigurableChatModel serviceChatModel = chatModelsConfigDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			if (serviceChatModel == null) {
				LOGGER.error("No INTERNAL_SERVICE or Default chat model configured");
				return;
			} else {
				LOGGER.info("Running tuning with module:" + serviceChatModel.getCode());
			}
			final GPromptTemplateConfig inTopicPrompt = promptsDao
					.findByPromptUse(RagThreasholdAutotunePromptConfig.RAG_IN_TOPIC_QUERY_GENERATOR_PROMPT);
			final List<AutoTuneQuestion> questions = generateQuestions(serviceChatModel, inTopicPrompt, sampled,
					MAXQUESTIONS);
			if (questions.isEmpty()) {
				// Without questions every threshold returns nothing, so the bound discovery
				// below would walk the threshold past zero and abort on a message that
				// describes the symptom rather than the cause.
				LOGGER.warn("No autotune question could be generated for " + vectorStoreId + " by "
						+ serviceChatModel.getCode() + ": skipping the tuning instead of probing an empty"
						+ " question set");
				return;
			}
			double startingSearch = 1.0;
			final double increment = 0.025;
			final double fineIncrement = 0.025;
			int resultsCardinality = 0;
			int nMaxIterations = 100;
			double threashold = startingSearch;
			do {
				List<AutoTuneQuestionResult> results = new ArrayList<AutoTuneQuestionResult>();
				for (AutoTuneQuestion question : questions) {
					AutoTuneQuestionResult result = executeQuestion(question, threashold, topK, vectorStore);
					results.add(result);
				}
				resultsCardinality = computeCardinality(results);
				LOGGER.info("Finding maximum threashold limit trying with:" + threashold);
				LOGGER.info("TopK requests saturation:" + topKSaturationPercent(results, topK));
				if (resultsCardinality <= 0) {
					threashold -= increment;
					threashold = round3decimal(threashold);
				}
				if (threashold < 0) {
					LOGGER.error("Threashold is " + threashold + " seriusly wrong!");
					return;
				}
				nMaxIterations--;
			} while (resultsCardinality <= 0 && nMaxIterations > 0);
			final double upperBound = threashold;
			final int upperBoundCardinality = resultsCardinality;
			threashold = 0.0;
			nMaxIterations = 100;
			final int nMaxTotalCardinality = questions.size() * topK;
			do {

				List<AutoTuneQuestionResult> results = new ArrayList<AutoTuneQuestionResult>();
				for (AutoTuneQuestion question : questions) {
					AutoTuneQuestionResult result = executeQuestion(question, threashold, topK, vectorStore);
					results.add(result);
				}
				LOGGER.info("Finding minimum threashold limit trying with:" + threashold);
				LOGGER.info("TopK requests saturation:" + topKSaturationPercent(results, topK));
				resultsCardinality = computeCardinality(results);
				if (resultsCardinality >= nMaxTotalCardinality) {
					threashold += increment;
					threashold = round3decimal(threashold);
				}
				if (threashold > 1.0) {
					LOGGER.error("Threashold is " + threashold + " seriusly wrong!");
					return;
				}
				nMaxIterations--;
			} while (resultsCardinality >= nMaxTotalCardinality && nMaxIterations > 0);
			final double lowerBound = threashold;
			LOGGER.info("Tuning will go between " + lowerBound + " and " + upperBound);
			TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds = new TreeMap<Double, List<AutoTuneRatedThreashold>>();
			Map<String, Double> cache = new HashMap<String, Double>();
			maximizeInTreeSequence(lowerBound, upperBound, fineIncrement, vectorStore, serviceChatModel, questions,
					rateOrderedOptimizationThreasholds, cache, topK);
			AutoTuneRatedThreashold foundThreashold = selectResult(rateOrderedOptimizationThreasholds);
			if (foundThreashold == null) {
				LOGGER.error("No found threashold!!");
				return;
			}
			List<AutoTuneRatedThreashold> logrates = new ArrayList<AutoTuneRatedThreashold>();
			rateOrderedOptimizationThreasholds.values().forEach(x -> {
				logrates.addAll(x);
			});
			OptimizedThreashold optimized = new OptimizedThreashold();
			optimized.setFirstHopOptimizedThreashold(foundThreashold.threashold);
			optimized.setSecondHopOptimizedThreashold(foundThreashold.threashold);
			optimized.setOptimizedThreashold(foundThreashold.threashold);
			ThreasholdAutotuneProcessResult result = new ThreasholdAutotuneProcessResult();
			result.setCode(UUID.randomUUID().toString());
			result.setDescription("Auto tune threashold");
			result.setEmbeddingModelCode(embeddingModel.getConfig().getChoosedModel() != null
					? embeddingModel.getConfig().getChoosedModel().getCode()
					: null);
			result.setVectorStoreId(vectorStoreId);
			result.setProcessedDateTime(new Date());
			result.setVectorStoreVectorizedCount(count);
			result.setEvaluationPoints(foundThreashold.resultsPoints);
			result.setScore(foundThreashold.rating);
			result.setThreasholds(optimized);
			try {
				ObjectMapper mapper = new ObjectMapper();
				String value = mapper.writeValueAsString(logrates);
				ListOfJsonObjects mapped = mapper.readValue(value, ListOfJsonObjects.class);
				result.setComputedElements(mapped);
			} catch (Throwable th) {
			}
			resultRepo.insert(result);
			LOGGER.info("New threashold=> " + foundThreashold);
			LOGGER.info("All threasholds data=> " + rateOrderedOptimizationThreasholds);
		}
	}

	/**
	 * A candidate fragment and how much of the corpus it reaches.
	 */
	private static record RepresentativeSample(Document document, int similarDocuments, int similarFragments) {
	}

	/**
	 * Keeps the candidates that best represent the corpus, by using each one as a query and
	 * counting what it finds.
	 * <p>
	 * A fragment whose neighbours span several documents sits on a subject the corpus
	 * actually covers, so a question generated from it has somewhere to be answered from
	 * and a threshold tuned on it means something. A fragment that finds nothing but itself
	 * is an isolate - a licence page, a colophon, a table of numbers - and tuning on it
	 * measures the corpus at its least representative point.
	 * <p>
	 * The candidate always retrieves itself first, so that hit is skipped by document id:
	 * counting it would score every fragment alike. Ranking is by how many distinct
	 * documents are reached, with the raw fragment count breaking ties, and the number kept
	 * is the sample budget the caller asked for.
	 * <p>
	 * When no candidate reaches anything at all the measure has nothing to say - a corpus
	 * of unrelated one-off documents, or an embedding that separates everything - so the
	 * proportional selection made across the documents is kept instead of imposing an order
	 * that would be arbitrary.
	 */
	private List<Document> selectRepresentative(VectorStore vectorStore, List<Document> candidates, int wanted) {
		if (candidates.size() <= wanted) {
			return candidates;
		}
		final int probeTopK = Math.max(2, config.getSampleRepresentativenessProbeTopK());
		final List<RepresentativeSample> scored = new ArrayList<RepresentativeSample>();
		for (Document candidate : candidates) {
			if (candidate.getText() == null || candidate.getText().isBlank()) {
				continue;
			}
			try {
				Builder builder = SearchRequest.builder();
				builder.query(candidate.getText());
				builder.topK(probeTopK);
				builder.similarityThresholdAll();
				final List<Document> neighbours = vectorStore.similaritySearch(builder.build());
				final Set<String> reachedDocuments = new HashSet<String>();
				int reachedFragments = 0;
				for (Document neighbour : neighbours) {
					if (candidate.getId() != null && candidate.getId().equals(neighbour.getId())) {
						// the candidate is always its own nearest neighbour
						continue;
					}
					reachedFragments++;
					final Object code = neighbour.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
					if (code != null) {
						reachedDocuments.add(String.valueOf(code));
					}
				}
				scored.add(new RepresentativeSample(candidate, reachedDocuments.size(), reachedFragments));
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot probe the representativeness of a sampled fragment, keeping it unscored", e);
				scored.add(new RepresentativeSample(candidate, 0, 0));
			}
		}
		if (scored.isEmpty()) {
			return spreadAcrossDocuments(candidates, wanted);
		}
		if (scored.stream().allMatch(x -> x.similarFragments() == 0)) {
			LOGGER.warn("No sampled fragment reaches any other fragment in the store: falling back to an even"
					+ " spread across the documents instead of ranking by representativeness");
			return spreadAcrossDocuments(candidates, wanted);
		}
		final List<Document> selected = scored.stream()
				.sorted(Comparator.comparingInt(RepresentativeSample::similarDocuments).reversed()
						.thenComparing(Comparator.comparingInt(RepresentativeSample::similarFragments).reversed()))
				.limit(wanted).map(RepresentativeSample::document).toList();
		if (LOGGER.isInfoEnabled()) {
			final RepresentativeSample best = scored.stream()
					.max(Comparator.comparingInt(RepresentativeSample::similarDocuments)).orElse(null);
			// Ranking by reach can in principle pull the whole budget into the handful of
			// documents that happen to be the best connected, undoing the spread the
			// sampling just paid for, so how many documents survive it is worth seeing.
			final Set<String> keptDocuments = new HashSet<String>();
			for (Document kept : selected) {
				final Object code = kept.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
				if (code != null) {
					keptDocuments.add(String.valueOf(code));
				}
			}
			LOGGER.info("Kept " + selected.size() + " representative fragment(s) of " + scored.size()
					+ " candidate(s) spanning " + keptDocuments.size() + " document(s); the best reaches "
					+ (best != null ? best.similarDocuments() : 0) + " document(s)");
		}
		return selected;
	}

	/**
	 * The fallback when representativeness cannot be measured: take the candidates one
	 * per document in rotation until the budget is met.
	 * <p>
	 * Truncating the candidate list instead would hand the whole budget to whichever
	 * documents the repository happened to enumerate first - the candidates are drawn
	 * document by document, so the first few files would fill every slot and the spread
	 * the sampling just paid for would be thrown away at the last step.
	 */
	private List<Document> spreadAcrossDocuments(List<Document> candidates, int wanted) {
		final Map<String, List<Document>> byDocument = new LinkedHashMap<String, List<Document>>();
		for (Document candidate : candidates) {
			final Object code = candidate.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			byDocument.computeIfAbsent(code == null ? "" : String.valueOf(code),
					k -> new ArrayList<Document>()).add(candidate);
		}
		final List<Document> out = new ArrayList<Document>();
		int round = 0;
		while (out.size() < wanted) {
			boolean tookAny = false;
			for (List<Document> ofDocument : byDocument.values()) {
				if (round < ofDocument.size()) {
					out.add(ofDocument.get(round));
					tookAny = true;
					if (out.size() >= wanted) {
						break;
					}
				}
			}
			if (!tookAny) {
				break;
			}
			round++;
		}
		return out;
	}

	/**
	 * Draws the fragments the tuning questions are generated from, spread across the
	 * documents actually present in the vector store.
	 * <p>
	 * The previous sampling ran one similarity search for the literal text
	 * "Meaningless query" with no threshold and took the top fragments. "Most similar to
	 * nonsense" is arbitrary and, worse, systematically favours generic prose: on a real
	 * corpus it returned Project Gutenberg licence boilerplate - tax identification
	 * numbers and mailing addresses - from which no in-topic question could be generated,
	 * so the whole tuning silently produced nothing. Three consecutive runs over the same
	 * unchanged corpus yielded 6 questions, then 0, then 0.
	 * <p>
	 * The documents are enumerated from the repository rather than guessed at, each one
	 * gets a share of the budget proportional to how many chunks it actually contributed,
	 * and each search is scoped to that document by CONTENT_CODE - the payload field that
	 * holds exactly the {@code docReferenceCode} the repository is keyed by. No single
	 * file can monopolise the sample, and the query is the document's own name rather than
	 * a meaningless string, so the fragments returned are the ones characteristic of it.
	 */
	/**
	 * Draws the fragments from the phrases configured under
	 * {@code ai.gebo.rag-threashold-autotune.config.autotune-samples}, when an operator has
	 * said which subjects the tuning should be measured on.
	 * <p>
	 * The corpus sampling in {@link #sampleAcrossCorpus} is deliberately not applied here:
	 * choosing the phrases is choosing what to probe, and re-selecting among them by how
	 * much of the corpus they reach would override exactly the intent that configuring them
	 * expressed. What this shares with that path is the handling of the ways sampling can
	 * come back empty, which used to be silent on this side.
	 * <p>
	 * The budget was split by integer division alone, so configuring more phrases than the
	 * budget gave every phrase {@code 0}. Spring AI accepts that - its assertion is
	 * {@code topK >= 0}, not {@code > 0} - so every search returned nothing, the sample was
	 * empty, and the tuning failed several steps later with a message about the model
	 * having generated no questions. Every phrase now gets at least one fragment and the
	 * remainder is spread over the first few instead of being dropped; configuring more
	 * phrases than the budget therefore samples slightly more than the budget, which is the
	 * lesser evil against sampling nothing.
	 */
	private List<Document> sampleFromConfiguredPhrases(VectorStore vectorStore, int budgetTotal) {
		final RagThreasholdAutotuneConfig.InitialAutotunePhrases samples = config.getAutotuneSamples();
		final List<String> phrases = samples.getPhrases();
		final int perPhrase = Math.max(1, budgetTotal / phrases.size());
		int remainder = Math.max(0, budgetTotal - perPhrase * phrases.size());
		// Keyed by fragment id: two phrases on the same subject retrieve the same fragments,
		// and a duplicate is a chunk the question generator reads twice and a topK inflated
		// by work already counted.
		final Map<String, Document> sampled = new LinkedHashMap<String, Document>();
		for (String query : phrases) {
			int wanted = perPhrase;
			if (remainder > 0) {
				wanted++;
				remainder--;
			}
			LOGGER.info("Autoune with sample: \"" + query + "\" threashold:" + samples.getDefaultThreashold());
			try {
				Builder builder = SearchRequest.builder();
				builder.filterExpression(
						DocumentMetaInfos.GEBO_TOKEN_LENGTH + ">" + config.getSampleFragmentsMinTokenLength());
				builder.topK(wanted);
				builder.similarityThreshold(samples.getDefaultThreashold());
				builder.query(query);
				final List<Document> documents = vectorStore.similaritySearch(builder.build());
				LOGGER.info("Found " + documents.size() + " docs on this sample");
				for (Document document : documents) {
					sampled.putIfAbsent(document.getId(), document);
				}
			} catch (RuntimeException e) {
				// One unusable phrase must not cost the whole sample, as one unparseable
				// document code does not cost it on the corpus side.
				LOGGER.warn("Cannot sample fragments for the configured phrase \"" + query + "\", skipping it", e);
			}
		}
		if (sampled.isEmpty()) {
			// Naming the three settings that can cause this is the whole point: the failure
			// otherwise surfaces much later as the model having answered nothing, which sends
			// the reader to the wrong place entirely.
			LOGGER.warn("None of the " + phrases.size() + " configured autotune phrase(s) matched any fragment of "
					+ vectorStore.getClass().getSimpleName() + " at threashold " + samples.getDefaultThreashold()
					+ " with a minimum token length of " + config.getSampleFragmentsMinTokenLength()
					+ ": the tuning has nothing to generate questions from. Lower the threashold, lower the minimum"
					+ " token length, or remove the phrases to sample across the corpus instead");
		} else {
			LOGGER.info("Autotune sampled " + sampled.size() + " fragment(s) from " + phrases.size()
					+ " configured phrase(s)");
		}
		return new ArrayList<Document>(sampled.values());
	}

	private List<Document> sampleAcrossCorpus(String vectorStoreId, VectorStore vectorStore, int budgetTotal) {
		final List<GVectorizedContent> corpus = new ArrayList<GVectorizedContent>();
		try (Stream<GVectorizedContent> stream = vectorizedContentsRepository.findByIdVectorStoreId(vectorStoreId)) {
			stream.forEach(corpus::add);
		}
		// A registered document holding no vector is not vectorised: it cannot contribute a
		// fragment, and it is worth saying so - it is the signature of an ingestion that
		// reported success while producing nothing.
		final List<GVectorizedContent> vectorised = corpus.stream()
				.filter(x -> x.getVectorsId() != null && !x.getVectorsId().isEmpty()).toList();
		if (vectorised.size() < corpus.size()) {
			LOGGER.warn((corpus.size() - vectorised.size()) + " of " + corpus.size() + " document(s) in "
					+ vectorStoreId + " carry no vector and are excluded from the autotune sample");
		}
		if (vectorised.isEmpty()) {
			LOGGER.warn("No vectorised document found in " + vectorStoreId + ": nothing to sample");
			return List.of();
		}
		final long totalChunks = vectorised.stream().mapToLong(x -> x.getVectorsId().size()).sum();
		final List<Document> sampled = new ArrayList<Document>();
		for (GVectorizedContent content : vectorised) {
			final String docCode = content.getId().getDocReferenceCode();
			// Proportional to the document's real weight in the store, so a book of hundreds
			// of chunks is not represented like a one page note, but never zero.
			final int perDocument = Math.max(1,
					(int) Math.round(budgetTotal * (content.getVectorsId().size() / (double) totalChunks)));
			// The document is addressed by its file name rather than by the full
			// docReferenceCode: the code is a path carrying slashes, spaces and hyphens,
			// and the filter grammar has to accept all of them inside the quoted literal.
			// The name is the last segment of that same code, so it needs no extra lookup.
			final String fileName = fileNameOf(docCode);
			try {
				Builder builder = SearchRequest.builder();
				builder.filterExpression(DocumentMetaInfos.GEBO_FILE_NAME + " == '" + fileName + "' && "
						+ DocumentMetaInfos.GEBO_TOKEN_LENGTH + " > " + config.getSampleFragmentsMinTokenLength());
				builder.topK(perDocument);
				builder.similarityThresholdAll();
				builder.query(fileName);
				final List<Document> documents = vectorStore.similaritySearch(builder.build());
				// The file name scopes the search, the reference code confirms it. Two
				// documents can share a name in different folders, and the name is all the
				// filter could carry safely; CONTENT_CODE holds the full docReferenceCode,
				// so anything that came back from a namesake is dropped here rather than
				// being attributed to this document.
				final List<Document> confirmed = documents.stream()
						.filter(x -> docCode.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE))).toList();
				if (confirmed.size() < documents.size()) {
					LOGGER.warn((documents.size() - confirmed.size()) + " fragment(s) named " + fileName
							+ " belong to another document than " + docCode + " and were discarded");
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Sampled " + confirmed.size() + " of " + perDocument + " requested fragment(s) from "
							+ docCode + " (" + content.getVectorsId().size() + " chunk(s) in store)");
				}
				sampled.addAll(confirmed);
			} catch (RuntimeException e) {
				// One unparseable document code must not lose the whole sample: the codes are
				// paths and can carry characters the filter grammar rejects.
				LOGGER.warn("Cannot sample fragments of " + docCode + ", skipping it", e);
			}
		}
		LOGGER.info("Autotune sampled " + sampled.size() + " fragment(s) across " + vectorised.size()
				+ " document(s) of " + vectorStoreId);
		return sampled;
	}

	/**
	 * The document's file name: the last segment of its reference code. Used both to
	 * scope the search to that document and as the query, so the fragments returned are
	 * the ones characteristic of it rather than whatever sits nearest an arbitrary
	 * embedding. Two documents sharing a name in different folders would be sampled
	 * together, which costs a little spread and no correctness.
	 */
	private String fileNameOf(String docReferenceCode) {
		final int slash = docReferenceCode.lastIndexOf('/');
		final String name = slash >= 0 ? docReferenceCode.substring(slash + 1) : docReferenceCode;
		return name.isBlank() ? docReferenceCode : name;
	}

	private ThreasholdAutotuneProcessResult internalFindByEmbeddingModelCode(String vectorStoreId) {
		// Callers pass the configurable model CODE (e.g.
		// "embedding-<provider>-<model config code>"), which is what the process stores
		// in vectorStoreId. The embeddingModelCode column holds the provider's own
		// model name (e.g. "Qwen3-Embedding-8B"), so querying that column with a code
		// never matched and the autotuned thresholds were silently never applied.
		// vectorStoreId is tried first and the old column kept as a fallback so records
		// written before this fix, and callers that really pass a model name, still
		// resolve.
		List<ThreasholdAutotuneProcessResult> results = this.resultRepo.findByVectorStoreId(vectorStoreId);
		ThreasholdAutotuneProcessResult latest = this.latest(results);
		if (latest == null) {
			latest = this.latest(this.resultRepo.findByEmbeddingModelCode(vectorStoreId));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Autotuned thresholds lookup for '" + vectorStoreId + "' resolved:" + (latest != null));
		}
		return latest;
	}

	private ThreasholdAutotuneProcessResult latest(List<ThreasholdAutotuneProcessResult> results) {

		TreeMap<Date, ThreasholdAutotuneProcessResult> ordered = new TreeMap<>();
		if (results != null)
			for (ThreasholdAutotuneProcessResult r : results) {
				ordered.put(r.getProcessedDateTime(), r);
			}
		return !ordered.isEmpty() ? ordered.lastEntry().getValue() : null;
	}

	private ThreasholdAutotuneProcessResult internalFindByVectorStoreId(String vectorStoreId) {
		List<ThreasholdAutotuneProcessResult> results = this.resultRepo.findByVectorStoreId(vectorStoreId);
		return this.latest(results);
	}

	/**
	 * Upper bound on narrowing rounds. Each one cuts the bracket to two thirds, so a
	 * bracket of any width reaches the increment long before this; it exists so a rounding
	 * quirk can never turn the search into a non terminating loop.
	 */
	private static final int MAX_NARROWING_STEPS = 40;

	/**
	 * Finds the best rated threshold in a bracket, by trisection.
	 * <p>
	 * The previous narrowing moved away from the peak. It evaluated the two ends and the
	 * midpoint, and when the midpoint was the best of the three - the ordinary case for a
	 * single humped curve - <em>both</em> branches fired, each recursing into a sub
	 * interval that excluded the midpoint's own neighbourhood
	 * ({@code [left + inc, mid - inc]} and {@code [mid + inc, right - inc]}), and the
	 * second assignment discarded whatever the first had returned. When an endpoint was
	 * the best instead, only the test on the opposite side could fire, so the search
	 * recursed into the half running away from the peak. Observed on a live run: 0.35
	 * rated 0.63, 0.55 rated 4.05 and 0.75 rated 0.00 were evaluated, and the bracket
	 * chosen next was 0.375 to 0.525 - which cannot contain 0.55.
	 * <p>
	 * This is the textbook ternary search instead: two interior probes at a third and two
	 * thirds of the bracket, and the side beyond the weaker probe is dropped. On a single
	 * humped curve the peak provably survives every step, the bracket shrinks to two
	 * thirds each round, and only one bracket is ever live, so nothing can be overwritten.
	 * <p>
	 * Both ends are measured before any narrowing. They are part of the curve, and the
	 * coverage floor {@link #selectResult} applies is relative to the best coverage seen
	 * anywhere - the loose end is where coverage is highest, so leaving it unmeasured
	 * would compute the floor from a biased sample of the curve.
	 * <p>
	 * The narrowing follows the rating alone and deliberately ignores that floor, even
	 * though the floor is what finally decides. Rating rises as the threshold tightens
	 * while coverage falls, so the best <em>selectable</em> threshold sits exactly on the
	 * coverage boundary: a search climbing toward the rating peak has to cross that
	 * boundary and therefore probes right where the answer is. Steering the search by the
	 * floor as well turns it back early and starves that region - on the measured curve it
	 * ended at 0.599 rated 19.8 where the plain search reaches 0.612 rated 32.8.
	 * <p>
	 * None of this ever made the final answer wrong, because {@code selectResult} ranks
	 * every threshold ever evaluated rather than the last bracket, so the peak stayed
	 * eligible even when the search walked past it. What it cost was LLM calls spent
	 * refining the wrong part of the curve.
	 */
	private AutoTuneRatedThreashold maximizeInTreeSequence(double lowerBound, double upperBound, double fineIncrement,
			VectorStore vectorStore, IGConfigurableChatModel defaultChatModel, List<AutoTuneQuestion> questions,
			TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds, Map<String, Double> cache,
			int topK) throws LLMConfigException {
		double lower = round3decimal(lowerBound);
		double upper = round3decimal(upperBound);
		evaluateAndRecord(lower, vectorStore, defaultChatModel, questions, cache, topK,
				rateOrderedOptimizationThreasholds);
		evaluateAndRecord(upper, vectorStore, defaultChatModel, questions, cache, topK,
				rateOrderedOptimizationThreasholds);
		int remainingSteps = MAX_NARROWING_STEPS;
		while ((upper - lower) > 2.0 * fineIncrement && remainingSteps-- > 0) {
			final double lowerProbe = round3decimal(lower + (upper - lower) / 3.0);
			final double upperProbe = round3decimal(upper - (upper - lower) / 3.0);
			if (lowerProbe >= upperProbe) {
				// rounding has collapsed the two probes onto each other: nothing left to split
				break;
			}
			LOGGER.info("maximizeInTreeSequence scanning between: " + lower + "," + lowerProbe + "," + upperProbe + ","
					+ upper);
			final AutoTuneRatedThreashold atLowerProbe = evaluateAndRecord(lowerProbe, vectorStore, defaultChatModel,
					questions, cache, topK, rateOrderedOptimizationThreasholds);
			final AutoTuneRatedThreashold atUpperProbe = evaluateAndRecord(upperProbe, vectorStore, defaultChatModel,
					questions, cache, topK, rateOrderedOptimizationThreasholds);
			if (atLowerProbe.rating < atUpperProbe.rating) {
				lower = lowerProbe;
			} else {
				upper = upperProbe;
			}
		}
		// One last look at the middle of what survived: the loop stops while the bracket is
		// still an increment or two wide, and its centre has not necessarily been measured.
		evaluateAndRecord(round3decimal((lower + upper) / 2.0), vectorStore, defaultChatModel, questions, cache, topK,
				rateOrderedOptimizationThreasholds);
		return selectResult(rateOrderedOptimizationThreasholds);
	}

	/**
	 * Evaluates one threshold and files it under its rating, once.
	 * <p>
	 * {@code evaluateThreashold} already returns the earlier result when the same
	 * threshold comes round again, but the caller used to file that returned object a
	 * second time, so a threshold kept as a bracket bound accumulated a duplicate at every
	 * level of the search. The duplicates never changed the outcome - a maximum does not
	 * care how many times it appears - but they made the recorded curve unreadable, and
	 * trisection keeps its bounds far longer than the old recursion did.
	 */
	private AutoTuneRatedThreashold evaluateAndRecord(double threashold, VectorStore vectorStore,
			IGConfigurableChatModel defaultChatModel, List<AutoTuneQuestion> questions, Map<String, Double> cache,
			int topK, TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds)
			throws LLMConfigException {
		final AutoTuneRatedThreashold rated = evaluateThreashold(threashold, vectorStore, defaultChatModel, questions,
				cache, topK, rateOrderedOptimizationThreasholds);
		final List<AutoTuneRatedThreashold> sameRating = rateOrderedOptimizationThreasholds.computeIfAbsent(rated.rating,
				k -> new ArrayList<AutoTuneRatedThreashold>());
		if (sameRating.stream().noneMatch(x -> x.threashold == rated.threashold)) {
			sameRating.add(rated);
		}
		return rated;
	}

	private AutoTuneRatedThreashold selectResult(
			TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds) {
		if (rateOrderedOptimizationThreasholds.isEmpty())
			return null;
		final List<AutoTuneRatedThreashold> all = new ArrayList<AutoTuneRatedThreashold>();
		rateOrderedOptimizationThreasholds.values().forEach(all::addAll);
		if (all.isEmpty())
			return null;
		// Coverage is a constraint, not the goal. Taking the most answered questions first
		// pinned the choice to the loosest end of the bracket, because answeredQuestions
		// only ever grows as the threshold falls: one question that matches far down forced
		// every other question to be flooded with marginal fragments, and the rating - the
		// whole point of the LLM work in evaluateThreashold - never entered the decision at
		// all. On the first recorded run that chose a threshold rated 12.03 while one rated
		// 103.66 sat in the same result set, returning 40 fragments for 6 questions where
		// the better rated one returned 7.
		final double bestAnswered = all.stream().mapToDouble(x -> x.answeredQuestions).max().orElse(0.0);
		final double minimumAnswered = Math.ceil(bestAnswered * config.getMinimumAnsweredQuestionsShare());
		List<AutoTuneRatedThreashold> eligible = all.stream().filter(x -> x.answeredQuestions >= minimumAnswered)
				.toList();
		if (eligible.isEmpty()) {
			// The floor is relative to what was actually achievable, so this is unreachable in
			// practice; keep every candidate rather than returning nothing.
			LOGGER.warn("No threashold answered " + minimumAnswered
					+ " question(s), selecting among every evaluated one instead");
			eligible = all;
		}
		// Highest rated among those that still cover enough questions. Ties go to the tighter
		// threshold: the same rating with fewer, closer fragments is the better configuration.
		final AutoTuneRatedThreashold selected = eligible.stream()
				.max(Comparator.comparingDouble((AutoTuneRatedThreashold x) -> x.rating)
						.thenComparingDouble(x -> x.threashold))
				.orElse(null);
		if (selected != null && LOGGER.isInfoEnabled()) {
			LOGGER.info("Selected threashold " + selected.threashold + " rated " + selected.rating + " answering "
					+ selected.answeredQuestions + " of " + bestAnswered + " question(s), among " + eligible.size()
					+ " of " + all.size() + " evaluated threashold(s) meeting the coverage floor");
		}
		return selected;
	}

	private AutoTuneRatedThreashold evaluateThreashold(double threashold, VectorStore vectorStore,
			IGConfigurableChatModel defaultChatModel, List<AutoTuneQuestion> questions, Map<String, Double> cache,
			int topK, TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds)
			throws LLMConfigException {
		double globalRating = 0.0;
		double evaluationPoints = 0.0;
		double totalDistance = 0.0;
		double answeredQuestions = 0.0;
		GPromptTemplateConfig ratingPrompt = promptsDao
				.findByPromptUse(RagThreasholdAutotunePromptConfig.RAG_AUTOTUNE_RATING_PROMPT);
		List<AutoTuneRatedThreashold> allComputed = new ArrayList<AutoTuneRatedThreashold>();
		rateOrderedOptimizationThreasholds.values().forEach(x -> {
			allComputed.addAll(x);
		});
		Optional<AutoTuneRatedThreashold> cacheHit = allComputed.stream().filter(x -> x.threashold == threashold)
				.findFirst();
		if (cacheHit.isPresent()) {
			LOGGER.info("Returning cached rates:" + cacheHit.get());
			return cacheHit.get();
		}
		for (final AutoTuneQuestion question : questions) {
			Builder builder = SearchRequest.builder();
			builder.query(question.text);
			builder.topK(topK);
			builder.similarityThreshold(threashold);
			SearchRequest request = builder.build();
			final List<Document> retrieved = vectorStore.similaritySearch(request);
			if (!retrieved.isEmpty()) {
				answeredQuestions++;
			}
			evaluationPoints += retrieved.size();
			final Map<String, Document> retrievedById = new HashMap<String, Document>();
			for (Document d : retrieved) {
				retrievedById.put(d.getId(), d);
			}
			final List<AutoTuneMatchWithRate> matchWithRate = retrieved.stream().map(x -> {
				AutoTuneMatchWithRate mr = new AutoTuneMatchWithRate();
				mr.document = x;
				// Spring AI writes the similarity distance under its own metadata key rather
				// than a convention of ours: every store normalises it to 1 - similarity, so
				// lower is closer. Read it through the framework constant so a rename in
				// Spring AI breaks the build instead of silently returning null here.
				Object distance = x.getMetadata().get(DocumentMetadata.DISTANCE.value());
				if (distance != null) {
					if (distance instanceof Number d) {
						mr.distance = d.doubleValue();
					} else if (distance instanceof String d) {
						try {
							mr.distance = Double.parseDouble(d);
						} catch (Throwable th) {
							LOGGER.error("Error evaluating distance", th);
						}
					} else {
						LOGGER.warn("Distance of unknown type" + distance.getClass().getName());
					}
				} else {
					LOGGER.warn("Distance not emitted");
				}

				mr.rating = cache.get(question.id + "<-->" + x.getId());
				return mr;
			}).toList();
			for (AutoTuneMatchWithRate x : matchWithRate) {
				if (x.distance != null) {
					totalDistance += x.distance;
				}
			}
			final List<AutoTuneMatchWithRate> alreadyMatched = matchWithRate.stream().filter(x -> x.rating != null)
					.toList();
			List<Document> toCalculate = matchWithRate.stream().filter(x -> x.rating == null).map(y -> y.document)
					.toList();
			String csvExtracted = callLLMWithDocuments(defaultChatModel, ratingPrompt,
					IChatRequestContext.of(question.text), new HashMap<String, Object>(), toCalculate);
			List<AutoTuneMatchRate> matches = readCSVLines(csvExtracted, 2, this::readMatchRate).toList();
			List<AutoTuneMatchWithRate> rated = new ArrayList<AutoTuneMatchWithRate>(alreadyMatched);
			for (AutoTuneMatchRate match : matches) {
				String key = question.id + "<-->" + match.documentId;
				cache.put(key, match.rating);
				Document document = retrievedById.get(match.documentId);
				AutoTuneMatchWithRate mr = new AutoTuneMatchWithRate();
				mr.document = document;
				mr.rating = match.rating;
				rated.add(mr);
			}
			for (AutoTuneMatchWithRate mr : rated) {
				if (mr.rating != null) {
					globalRating += mr.rating.doubleValue();
				}

			}

		}
		AutoTuneRatedThreashold ratedT = new AutoTuneRatedThreashold();
		ratedT.threashold = threashold;
		ratedT.resultsPoints = evaluationPoints;
		ratedT.totalDistance = totalDistance;
		ratedT.answeredQuestions = answeredQuestions;
		ratedT.totalLLMRating = globalRating;
		ratedT.averageDistance = totalDistance / evaluationPoints;
		ratedT.rating = evaluationPoints > 1
				? answeredQuestions * globalRating / (evaluationPoints * ratedT.totalDistance)
				: 0;
		LOGGER.info("Calculated rating:" + ratedT);
		return ratedT;
	}

	private double round3decimal(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

	private AutoTuneMatchRate readMatchRate(String csvExtracted) {
		AutoTuneMatchRate rate = new AutoTuneMatchRate();
		rate.documentId = "<unknown id>";
		rate.rating = 0.0;
		try {
			StringTokenizer tokenizer = new StringTokenizer(csvExtracted, CSV_COLUMN_SEPARATOR_STRING);
			rate.documentId = tokenizer.nextToken();
			rate.rating = 0.0;

			String rateValue = tokenizer.nextToken();
			if (rateValue != null && rateValue.trim().length() > 0) {
				try {
					rate.rating = Double.valueOf(rateValue);
				} catch (Throwable th) {
				}
			}
		} catch (Throwable error) {
			LOGGER.error("Promblem in received:" + csvExtracted);
		}
		return rate;
	}

	private double[] createTreeSequence(double lowerBound, double upperBound, double fineIncrement) {
		double midStep = (lowerBound + upperBound) / 2.0;
		if ((midStep - lowerBound) <= fineIncrement || (upperBound - midStep) <= fineIncrement) {
			return new double[] { lowerBound, midStep, upperBound };
		} else {
			double leftNodes[] = createTreeSequence(lowerBound, midStep, fineIncrement);
			double rightNodes[] = createTreeSequence(midStep, upperBound, fineIncrement);
			double globalArray[] = new double[leftNodes.length + rightNodes.length];
			for (int i = 0; i < leftNodes.length; i++) {
				globalArray[i] = leftNodes[i];
			}
			for (int i = 0; i < rightNodes.length; i++) {
				globalArray[leftNodes.length + i] = rightNodes[i];
			}
			return globalArray;
		}

	}

	private int computeCardinality(List<AutoTuneQuestionResult> results) {
		int total = 0;
		for (AutoTuneQuestionResult questionResult : results) {
			total += questionResult.relatedDocuments.size();
		}
		return total;
	}

	private double topKSaturationPercent(List<AutoTuneQuestionResult> results, int topK) {
		double cardinality = computeCardinality(results);
		double maxTopK = results.size() * topK;
		if (maxTopK != 0.0)
			return 100.0 * cardinality / maxTopK;
		return 0;
	}

	private AutoTuneQuestionResult executeQuestion(AutoTuneQuestion question, double threashold, int topK,
			VectorStore vectorStore) {
		AutoTuneQuestionResult qr = new AutoTuneQuestionResult();
		qr.query = question;
		qr.threashold = threashold;
		qr.topK = topK;
		Builder builder = SearchRequest.builder();
		builder.query(question.text);
		builder.similarityThreshold(threashold);
		builder.topK(topK);
		SearchRequest request = builder.build();
		qr.relatedDocuments = vectorStore.similaritySearch(request);
		return qr;
	}

	/**
	 * Generates the tuning questions, a few sampled fragments per call.
	 * <p>
	 * Sending every sampled fragment in one prompt is what the code used to do, and on a
	 * reasoning model it returns nothing at all: the recorded call carried 15,592 input
	 * tokens, spent all 4,000 of its output allowance - exactly the ceiling - reasoning
	 * about thirty fragments at once, and was cut off before writing a single CSV line.
	 * The completion came back as the empty string with the call reported successful, so
	 * the tuning saw no questions and stopped, with nothing anywhere saying why.
	 * <p>
	 * Batching is therefore sized by the answer the model has to write rather than by how
	 * much prompt fits in the context window - which is why
	 * {@code callLLMConcatenateText}, that splits on the input budget alone, does not help
	 * here. Generation also stops as soon as enough questions exist: the tuning keeps at
	 * most {@code maxQuestions} of them, so the remaining calls would be paid for and
	 * discarded.
	 * <p>
	 * A batch that yields nothing is logged with what the model answered - the only thing
	 * that separates an empty completion from one the CSV parser could not read - and the
	 * remaining batches still run, so one bad answer no longer costs the whole tuning.
	 */
	private List<AutoTuneQuestion> generateQuestions(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			List<Document> sampled, int maxQuestions) throws LLMConfigException {
		final int perCall = Math.max(1, config.getAutotuneQuestionChunksPerCall());
		final List<AutoTuneQuestion> out = new ArrayList<AutoTuneQuestion>();
		final Map<String, Boolean> alreadyAsked = new HashMap<String, Boolean>();
		for (int from = 0; from < sampled.size() && out.size() < maxQuestions; from += perCall) {
			final int to = Math.min(from + perCall, sampled.size());
			final String csvResult = callLLMWithDocuments(chatModel, prompt,
					IChatRequestContext.of("Sampling queries"), Map.of(), sampled.subList(from, to));
			final List<AutoTuneQuestion> parsed = parseQuestions(csvResult);
			if (parsed.isEmpty()) {
				LOGGER.warn("Fragments [" + from + "," + to + ") produced no autotune question. " + chatModel.getCode()
						+ " answered: " + (csvResult == null ? "<null>"
								: csvResult.length() > 1000 ? csvResult.substring(0, 1000) + "... (truncated)"
										: "\"" + csvResult + "\""));
				continue;
			}
			for (AutoTuneQuestion question : parsed) {
				if (out.size() >= maxQuestions) {
					break;
				}
				// parseQuestions only dedupes inside one answer; the batches do not see
				// each other, and neighbouring fragments do produce the same question.
				if (alreadyAsked.put(question.text, Boolean.TRUE) == null) {
					out.add(question);
				}
			}
		}
		LOGGER.info("Generated " + out.size() + " autotune question(s) from " + sampled.size() + " sampled fragment(s)");
		return out;
	}

	private List<AutoTuneQuestion> parseQuestions(String csvResult) {
		Map<String, Boolean> ensureUnique = new HashMap<>();
		if (csvResult != null && csvResult.trim().length() > 0) {
			List<AutoTuneQuestion> out = new ArrayList<AutoTuneQuestion>();
			ByteArrayInputStream bis = new ByteArrayInputStream(csvResult.getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis));
			String line = null;
			try {
				do {

					line = br.readLine();
					AutoTuneQuestion question = readCSVLine(line);
					if (question != null && question.text != null && !ensureUnique.containsKey(question.text)) {
						out.add(question);
						ensureUnique.put(question.text, true);
					}
				} while (line != null);
			} catch (IOException e) {

			}
			return out;
		}
		return List.of();
	}

	private AutoTuneQuestion readCSVLine(String line) {
		AutoTuneQuestion q = null;
		if (line != null && line.indexOf(";") >= 0) {
			StringTokenizer tokenizer = new StringTokenizer(line, ";");
			if (tokenizer.hasMoreTokens()) {
				q = new AutoTuneQuestion();
				q.text = tokenizer.nextToken();
				if (tokenizer.hasMoreTokens()) {
					try {
						// The enum constants are upper case and models answer in whatever case the
						// prompt suggested, so normalise rather than silently dropping the value.
						q.hardness = AutoTuneQueryHardness.valueOf(tokenizer.nextToken().trim().toUpperCase());
					} catch (Throwable th) {
					}
					if (tokenizer.hasMoreTokens()) {
						q.documentId = tokenizer.nextToken();
					}
				}
			}
		}
		return q;
	}

	@Override
	public List<AutotuneVectorStoreInfo> getLatestComputedVectorStores() {
		List<AutotuneVectorStoreInfo> out = new ArrayList<AutotuneVectorStoreInfo>();
		List<IGConfigurableEmbeddingModel> data = embeddingModelsRuntimeDao.getConfigurations();
		for (IGConfigurableEmbeddingModel model : data) {
			AutotuneVectorStoreInfo vectorStoreInfo = new AutotuneVectorStoreInfo(model.getConfig());
			List<ThreasholdAutotuneProcessResult> entries = resultRepo.findByVectorStoreId(vectorStoreInfo.getCode());
			vectorStoreInfo.setAutotuneResult(entries.isEmpty() ? null : entries.get(0));
			out.add(vectorStoreInfo);
		}
		return out;
	}

	@Override
	public boolean isRunning() {

		return runningTuning;
	}

}
