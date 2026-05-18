package ai.gebo.architecture.rag_threasholds_autotune.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SearchRequest.Builder;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.rag_threasholds_autotune.config.RagThreasholdAutotuneConfig;
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
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.DocumentMetaInfos;

@Component
@Scope("singleton")
public class RagThreasholdAutotuneServiceImpl extends BaseLLMSInvokingAndProvidingService
		implements IRagThreasholdAutotuneService {
	private final ThreasholdAutotuneProcessResultRepository resultRepo;
	private final VectorizedContentRepository vectorizedContentsRepository;
	private final RagThreasholdAutotuneConfig config;
	private static final Logger LOGGER = LoggerFactory.getLogger(RagThreasholdAutotuneServiceImpl.class);
	private static boolean runningTuning = false;

	private static final GPromptTemplateConfig inTopicPrompt = new GPromptTemplateConfig();
	static {
		inTopicPrompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		inTopicPrompt.setSystemPromptTemplate("You are a synthetic query generator for retrieval evaluation.\r\n"
				+ "\r\n" + "INPUT\r\n" + "You will receive a batch of N text segments (\"chunks\"). Each chunk has:\r\n"
				+ "- chunkId: a unique identifier\r\n" + "- text: the chunk content\r\n" + "\r\n");
		inTopicPrompt.setUserPromptTemplate("TASK\r\n" + "For each chunk, generate K strings that are:\r\n"
				+ "1) Semantically IN-TOPIC: the chunk should plausibly contain the answer or directly support it.\r\n"
				+ "2) Semantically COHERENT: the string must be a realistic user query or statement (natural language, single intent).\r\n"
				+ "3) Not a copy: do not quote verbatim phrases longer than 6 consecutive words from the chunk.\r\n"
				+ "4) Lexical diversity: avoid reusing the same key terms from the chunk; use paraphrases and synonyms when possible.\r\n"
				+ "5) Answerability: the query must be answerable using only the chunk content (or overwhelmingly supported by it).\r\n"
				+ "\r\n" + "DIFFICULTY MIX (per chunk)\r\n"
				+ "Produce K strings split like this (if K is divisible by 3; otherwise keep proportions):\r\n"
				+ "- Easy (≈ 1/3): direct, explicit questions about facts/procedures clearly present.\r\n"
				+ "- Medium (≈ 1/3): paraphrased or implicit questions (same meaning, different wording).\r\n"
				+ "- Hard (≈ 1/3): compositional questions that combine two details present in the chunk, or require inference that is still fully supported by the chunk.\r\n"
				+ "\r\n" + "OUTPUT FORMAT IS STRICT CSV HAVING ; as column separator WITH FOLLOWING LINES \r\n"
				+ "<query text>;<difficulty: easy|medium|hard>;<document id>\r\n" + "\r\n" + "CONSTRAINTS\r\n"
				+ "- Do not add any extra keys.\r\n" + "- Do not include markdown.\r\n"
				+ "- Generate exactly K inTopic strings per chunk.\r\n"
				+ "- Do not mention that you are generating synthetic data.\r\n"
				+ "- Keep each \"text\" between 6 and 22 words (unless the chunk is very technical; then up to 28 words).\r\n"
				+ "{question}" + "CONTENTS TO EXTRACT FROM:\r\n" + "{documents}\r\n");
	}

	public RagThreasholdAutotuneServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao,
			ThreasholdAutotuneProcessResultRepository resultRepo,
			VectorizedContentRepository vectorizedContentsRepository, RagThreasholdAutotuneConfig config) {
		super(chatModelsConfigDao, embeddingModelsConfigDao);
		this.resultRepo = resultRepo;
		this.vectorizedContentsRepository = vectorizedContentsRepository;
		this.config = config;
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
			List<String> vectorStoreIds = embeddingModelsRuntimeDao.getConfigurations().stream().map(x -> x.getCode())
					.toList();
			for (String vectorStoreId : vectorStoreIds) {
				processAutotune(vectorStoreId);
			}
		} finally {
			synchronized (this) {
				runningTuning = false;
			}
		}
	}

	@Override
	public void processAutotune(String vectorStoreId) {
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
			Builder builder = SearchRequest.builder();
			builder.filterExpression(
					DocumentMetaInfos.GEBO_TOKEN_LENGTH + ">" + config.getSampleFragmentsMinTokenLength());
			builder.topK(budgetTotal);
			builder.similarityThresholdAll();
			builder.query("Meaningless query");
			SearchRequest request = builder.build();
			List<Document> documents = vectorStore.similaritySearch(request);
			sampled.addAll(documents);
			final int topK = sampled.size();
			IGConfigurableChatModel serviceChatModel = chatModelsConfigDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			if (serviceChatModel == null) {
				LOGGER.error("No INTERNAL_SERVICE or Default chat model configured");
				return;
			} else {
				LOGGER.info("Running tuning with module:" + serviceChatModel.getCode());
			}
			String csvResult = callLLMConcatenateText(serviceChatModel, inTopicPrompt, null, null, sampled.stream());
			List<AutoTuneQuestion> questions = parseQuestions(csvResult);
			while (questions.size() > MAXQUESTIONS) {
				questions.remove(questions.size() - 1);
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

	private ThreasholdAutotuneProcessResult internalFindByEmbeddingModelCode(String vectorStoreId) {
		List<ThreasholdAutotuneProcessResult> results = this.resultRepo.findByEmbeddingModelCode(vectorStoreId);
		return this.latest(results);
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

	private AutoTuneRatedThreashold maximizeInTreeSequence(double lowerBound, double upperBound, double fineIncrement,
			VectorStore vectorStore, IGConfigurableChatModel defaultChatModel, List<AutoTuneQuestion> questions,
			TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds,
			Map<String, Double> cache, int topK) {
		boolean isInRange = Math.abs(upperBound - lowerBound) < fineIncrement;
		lowerBound = round3decimal(lowerBound);
		upperBound = round3decimal(upperBound);
		double midStep = round3decimal((lowerBound + upperBound) / 2.0);
		if (isInRange) {
			AutoTuneRatedThreashold evaluateThreasholdMid = evaluateThreashold(midStep, vectorStore, defaultChatModel,
					questions, cache, topK, rateOrderedOptimizationThreasholds);
			if (!rateOrderedOptimizationThreasholds.containsKey(evaluateThreasholdMid.rating)) {
				rateOrderedOptimizationThreasholds.put(evaluateThreasholdMid.rating, new ArrayList());
			}
			rateOrderedOptimizationThreasholds.get(evaluateThreasholdMid.rating).add(evaluateThreasholdMid);
			return this.selectResult(rateOrderedOptimizationThreasholds);
		}
		LOGGER.info("maximizeInTreeSequence scanning between: " + lowerBound + "," + midStep + "," + upperBound);

		AutoTuneRatedThreashold evaluateThresholdLeft = evaluateThreashold(lowerBound, vectorStore, defaultChatModel,
				questions, cache, topK, rateOrderedOptimizationThreasholds);
		AutoTuneRatedThreashold evaluateThreasholdRight = evaluateThreashold(upperBound, vectorStore, defaultChatModel,
				questions, cache, topK, rateOrderedOptimizationThreasholds);
		AutoTuneRatedThreashold evaluateThreasholdMid = evaluateThreashold(midStep, vectorStore, defaultChatModel,
				questions, cache, topK, rateOrderedOptimizationThreasholds);
		if (!rateOrderedOptimizationThreasholds.containsKey(evaluateThresholdLeft.rating)) {
			rateOrderedOptimizationThreasholds.put(evaluateThresholdLeft.rating, new ArrayList());
		}
		if (!rateOrderedOptimizationThreasholds.containsKey(evaluateThreasholdRight.rating)) {
			rateOrderedOptimizationThreasholds.put(evaluateThreasholdRight.rating, new ArrayList());
		}
		if (!rateOrderedOptimizationThreasholds.containsKey(evaluateThreasholdMid.rating)) {
			rateOrderedOptimizationThreasholds.put(evaluateThreasholdMid.rating, new ArrayList());
		}
		rateOrderedOptimizationThreasholds.get(evaluateThresholdLeft.rating).add(evaluateThresholdLeft);
		rateOrderedOptimizationThreasholds.get(evaluateThreasholdRight.rating).add(evaluateThreasholdRight);
		rateOrderedOptimizationThreasholds.get(evaluateThreasholdMid.rating).add(evaluateThreasholdMid);
		AutoTuneRatedThreashold maxevaluation = null;
		if ((midStep - lowerBound) <= fineIncrement || (upperBound - midStep) <= fineIncrement) {
			maxevaluation = this.selectResult(rateOrderedOptimizationThreasholds);

		} else {
			if (evaluateThresholdLeft.rating < evaluateThreasholdMid.rating) {
				maxevaluation = maximizeInTreeSequence(evaluateThresholdLeft.threashold + fineIncrement,
						evaluateThreasholdMid.threashold - fineIncrement, fineIncrement, vectorStore, defaultChatModel,
						questions, rateOrderedOptimizationThreasholds, cache, topK);
			}
			if (evaluateThreasholdRight.rating < evaluateThreasholdMid.rating) {
				maxevaluation = maximizeInTreeSequence(evaluateThreasholdMid.threashold + fineIncrement,
						evaluateThreasholdRight.threashold - fineIncrement, fineIncrement, vectorStore,
						defaultChatModel, questions, rateOrderedOptimizationThreasholds, cache, topK);
			}
		}
		if (maxevaluation == null) {
			maxevaluation = selectResult(rateOrderedOptimizationThreasholds);
		}
		return maxevaluation;
	}

	private AutoTuneRatedThreashold selectResult(
			TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds) {
		if (rateOrderedOptimizationThreasholds.isEmpty())
			return null;
		TreeMap<Double, TreeMap<Double, AutoTuneRatedThreashold>> byquestionAnswered = new TreeMap<Double, TreeMap<Double, AutoTuneRatedThreashold>>();
		for (List<AutoTuneRatedThreashold> items : rateOrderedOptimizationThreasholds.values()) {
			for (AutoTuneRatedThreashold item : items) {
				if (byquestionAnswered.get(item.answeredQuestions) == null) {
					byquestionAnswered.put(item.answeredQuestions, new TreeMap<Double, AutoTuneRatedThreashold>());
				}
				byquestionAnswered.get(item.answeredQuestions).put(item.averageDistance, item);
			}
		}
		// I Take the configuration returning the most high number of answered questions
		// but with the lower average distance
		return byquestionAnswered.lastEntry().getValue().firstEntry().getValue();

	}

	private static final GPromptTemplateConfig ratingPrompt = new GPromptTemplateConfig();
	static {
		ratingPrompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		ratingPrompt.setSystemPromptTemplate("You are a strict RAG retrieval judge.\r\n" + "\r\n" + "INPUT\r\n"
				+ "You will receive:\r\n" + "- A user query: {question}\r\n"
				+ "- A list of document fragments (\"chunks\"). Each chunk has:\r\n"
				+ "  - documentId: a stable identifier (string)\r\n" + "  - text: the chunk content\r\n" + "\r\n");
		ratingPrompt.setUserPromptTemplate("TASK\r\n"
				+ "For EACH chunk, assign a relevance/coherence rating with respect to the query, from 0 to 100:\r\n"
				+ "- 0  = totally irrelevant / wrong topic / cannot help answer the query\r\n"
				+ "- 25 = weakly related (same broad domain, but not useful)\r\n"
				+ "- 50 = partially relevant (some overlap; could help but incomplete or indirect)\r\n"
				+ "- 75 = highly relevant (strong evidence/support; likely useful to answer)\r\n"
				+ "- 100 = directly answers the query or contains the key information needed\r\n" + "\r\n"
				+ "RATING RULES (IMPORTANT)\r\n"
				+ "1) Judge ONLY based on the chunk text. Do not use external knowledge.\r\n"
				+ "2) Do not reward generic statements. Prefer chunks that contain specific facts, procedures, definitions, constraints, or data answering the query.\r\n"
				+ "3) If the chunk is about the same general area but does not address the query intent, keep rating <= 25.\r\n"
				+ "4) If the chunk contradicts the query intent or is about a different entity/system/version, rating must be <= 25.\r\n"
				+ "5) If the chunk is mostly boilerplate (headers, navigation, legal footer) and not informative, rating must be <= 10.\r\n"
				+ "6) If the query requires a concrete answer and the chunk provides it clearly, rating should be 90–100.\r\n"
				+ "7) Be consistent across chunks: use the same criteria and scale.\r\n" + "\r\n"
				+ "OUTPUT (STRICT)\r\n" + "Return ONLY a CSV text (no markdown, no explanations, no extra lines).\r\n"
				+ "Format:\r\n" + "<documentId>;<rating>\r\n"
				+ "One line per chunk, in the same order as the input chunks.\r\n"
				+ "Rating must be an integer between 0 and 100.\r\n" + "Do not include a header row.\r\n"
				+ "Do not quote or escape fields unless documentId contains ';' (if it does, replace ';' with '_').\r\n"
				+ "DOCUMENT FRAGMENTS\r\n\r\n{documents}\r\n");
	}

	private AutoTuneRatedThreashold evaluateThreashold(double threashold, VectorStore vectorStore,
			IGConfigurableChatModel defaultChatModel, List<AutoTuneQuestion> questions, Map<String, Double> cache,
			int topK, TreeMap<Double, List<AutoTuneRatedThreashold>> rateOrderedOptimizationThreasholds) {
		double globalRating = 0.0;
		double evaluationPoints = 0.0;
		double totalDistance = 0.0;
		double answeredQuestions = 0.0;
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
				Object distance = x.getMetadata().get("distance");
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
			Stream<Document> toCalculate = matchWithRate.stream().filter(x -> x.rating == null).map(y -> y.document);
			String csvExtracted = callLLMConcatenateText(defaultChatModel, ratingPrompt,
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

	private List<AutoTuneQuestion> parseQuestions(String csvResult) {
		if (csvResult != null && csvResult.trim().length() > 0) {
			List<AutoTuneQuestion> out = new ArrayList<AutoTuneQuestion>();
			ByteArrayInputStream bis = new ByteArrayInputStream(csvResult.getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis));
			String line = null;
			try {
				do {

					line = br.readLine();
					AutoTuneQuestion question = readCSVLine(line);
					if (question != null) {
						out.add(question);
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
						q.hardness = AutoTuneQueryHardness.valueOf(tokenizer.nextToken());
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
