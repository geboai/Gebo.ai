package ai.gebo.llms.abstraction.layer.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient.StreamResponseSpec;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter.Builder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

public class BaseLLMSInvokingService {

	private static final String ASSIGNMENT = "=";

	static class ConsolidationBatchItem {
		LLMInputDocument input = null;
		int tokensCount = 0;
	}

	static class ConsolidationDocuments {
		List<Document> inputs = new ArrayList<Document>();
		int totaltokens = 0;
		boolean complete = false;
	}

	static class ConsolidationInputBatch {
		List<ConsolidationBatchItem> inputs = new ArrayList<ConsolidationBatchItem>();
		int totaltokens = 0;
		boolean complete = false;
	}

	public static final String CONSOLIDATED_TEMPLATE_VARIABLE = "consolidated";
	public static final char CSV_COLUMN_SEPARATOR = ';';
	public static final String CSV_COLUMN_SEPARATOR_STRING = ";";
	public static final String DOCUMENT_REFERENCE = "DOCUMENT-REFERENCE:";
	public static final String DOCUMENT_TITLE = "DOCUMENT-TITLE:";
	public static final String DOCUMENT_URL = "DOCUMENT-URL:";
	public static final String DOCUMENTS_TEMPLATE_VARIABLE = "documents";
	public static double ERRONEUS_TOKEN_LENGTH_ERROR_COEFF = 0.7;
	public static final String FORMAT_TEMPLATE_VARIABLE = "format";
	private static final String NEWLINE = "\r\n";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	protected static final JTokkitTokenCountEstimator tokensEstimation = new JTokkitTokenCountEstimator();
	public static final String USER_QUESTION_TEMPLATE_VARIABLE = "question";
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public BaseLLMSInvokingService() {

	}

	protected static final <T> Supplier<T> stream2supplier(Stream<T> stream) {
		if (stream == null)
			return () -> null;
		final Iterator<T> iterator = stream.iterator();
		return () -> iterator.hasNext() ? iterator.next() : null;
	}

	protected static <T> Stream<T> supplier2stream(Supplier<T> supplier) {
		if (supplier == null)
			return Stream.empty();
		return Stream.generate(supplier).takeWhile(Objects::nonNull);
	}

	protected static int tokensLength(String... contents) {
		if (contents != null)
			return 0;
		int totalTokens = 0;
		for (int i = 0; i < contents.length; i++) {
			String content = contents[i];
			if (content != null) {
				totalTokens += tokensEstimation.estimate(content);
			}
		}
		return totalTokens;
	}

	protected String callLLM(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> params) throws LLMConfigException {

		return chatModel.textResponse(prompt, params, context);
	}

	protected Map<String, List<String>> callLLMRepeatableFieldEntryOutput(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params,
			List<String> validFields) throws LLMConfigException {

		Map<String, String> validFieldsMap = new HashMap<String, String>();
		for (String fieldName : validFields) {
			validFieldsMap.put(fieldName.toLowerCase().trim(), fieldName.trim());
		}
		Map<String, List<String>> outValue = new HashMap<String, List<String>>();
		String toBeParsed = callLLM(chatModel, prompt, context, params);
		ByteArrayInputStream bis = new ByteArrayInputStream(toBeParsed.getBytes());
		DataInputStream dis = new DataInputStream(bis);
		String line = null;
		try {
			while ((line = dis.readLine()) != null) {

				int equalIndex = line.indexOf(ASSIGNMENT);
				if (equalIndex > 0) {
					String fieldName = line.substring(0, equalIndex);
					String fieldValue = (equalIndex + 1 < line.length()) ? line.substring(equalIndex + 1) : null;
					if (fieldValue != null) {
						fieldValue = fieldValue.replace("\n", " ").replace("\r", " ").trim();
						String originalCaseFieldName = validFieldsMap.get(fieldName.toLowerCase().trim());
						if (originalCaseFieldName != null) {
							if (!outValue.containsKey(originalCaseFieldName)) {
								outValue.put(originalCaseFieldName, new ArrayList<String>());
							}
							outValue.get(originalCaseFieldName).add(fieldValue);
						}
					}
				}
			}
		} catch (IOException e) {
			LOGGER.warn("Exception while doing a in-memory readLine()", e);
		}
		return outValue;

	}

	protected String callLLMConcatenateText(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> additionalParams, Stream<Document> toCalculate) {
		return callLLMConcatenateText(chatModel, prompt, context, additionalParams, stream2supplier(toCalculate));
	}

	protected String callLLMConcatenateText(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> additionalParams, Supplier<Document> input) {
		final int contextWindow = chatModel.getContextLength();

		Document currentInput = null;
		final int promptLength = prompt.getTokensSize();
		StringBuffer outBuffer = new StringBuffer();
		// Following 2 variables have to be updated once a consolidation is re-run

		int fragmentBudget = computeFragmentBudget("", promptLength, contextWindow, additionalParams);
		List<ConsolidationDocuments> currentBatchesQueue = new ArrayList<ConsolidationDocuments>();
		do {

			currentInput = input.get();
			if (currentInput != null && currentInput.isText() && currentInput.getText().trim().length() > 0) {

				final int textTokensLength = tokensEstimation.estimate(currentInput.toString());

				if (textTokensLength > fragmentBudget) {
					// splits will be loaded in currentBatchesQueue
					TokenTextSplitter splitter = createTokenSplitter(fragmentBudget);
					List<Document> documents = splitter.split(currentInput);
					final Document _currentInput = currentInput;
					List<ConsolidationDocuments> splitted = documents.stream().map(x -> {
						ConsolidationDocuments batchItem = new ConsolidationDocuments();
						batchItem.inputs.add(x);

						batchItem.totaltokens = tokensEstimation.estimate(x.toString());
						x.getMetadata().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, batchItem.totaltokens);
						return batchItem;
					}).toList();

					currentBatchesQueue.addAll(splitted);
				} else {

					ConsolidationDocuments lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);

					boolean allocateNewBatch = false;
					if (lastBatch != null) {
						if (fragmentBudget > (lastBatch.totaltokens + textTokensLength)) {
							lastBatch.totaltokens += textTokensLength;
							lastBatch.inputs.add(currentInput);
						} else {
							lastBatch.complete = true;
							allocateNewBatch = true;
						}
					} else {
						allocateNewBatch = true;
					}
					if (allocateNewBatch) {
						ConsolidationDocuments newBatch = new ConsolidationDocuments();
						newBatch.inputs.add(currentInput);
						newBatch.totaltokens += textTokensLength;
						currentBatchesQueue.add(newBatch);
					}
				}
			}
			List<ConsolidationDocuments> unmanaged = new ArrayList<BaseLLMSInvokingAndProvidingService.ConsolidationDocuments>();
			for (ConsolidationDocuments consolidationInputBatch : currentBatchesQueue) {
				// if this batch is complete or we are at the end of contents
				if (consolidationInputBatch.complete || currentInput == null) {
					String outValue = callLLMWithDocuments(chatModel, prompt, context, additionalParams,
							consolidationInputBatch.inputs);

					outValue = ClientChatCallUtil.removeThinking(outValue);

					if (outValue != null) {
						outBuffer.append(outValue);
						outBuffer.append(NEWLINE);
					}
					fragmentBudget = computeFragmentBudget("", promptLength, contextWindow, additionalParams);
				} else {
					unmanaged.add(consolidationInputBatch);
				}
			}
			currentBatchesQueue = unmanaged;
		} while (currentInput != null);
		return outBuffer.isEmpty() ? null : outBuffer.toString();
	}

	protected String callLLMWithDocuments(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> additionalParams, List<Document> inputs) {
		// TODO Auto-generated method stub
		return null;
	}

	Map<Integer, TokenTextSplitter> splittersCache = new HashMap<Integer, TokenTextSplitter>();
	private static double AVG_TOKENS_CHARS_RATIO = 4.2;

	protected TokenTextSplitter createTokenSplitter(int fragmentBudget) {
		return splittersCache.computeIfAbsent(fragmentBudget, fb -> {
			int chunkSize = fb;

			int estChunkChars = (int) Math.round(fb * AVG_TOKENS_CHARS_RATIO);

			// serve solo per scegliere un breakpoint "carino" dopo un minimo
			int minChunkSizeChars = clamp((int) (estChunkChars * 0.35), 0, 4000);

			int minChunkLengthToEmbed = 0; // <-- non scartare nulla

			int maxNumChunks = 20000; // safety

			boolean keepSeparator = true;
			Builder builder = TokenTextSplitter.builder();
			builder.withChunkSize(chunkSize);
			builder.withKeepSeparator(keepSeparator);
			builder.withMaxNumChunks(maxNumChunks);
			builder.withMinChunkLengthToEmbed(minChunkLengthToEmbed);
			builder.withMinChunkSizeChars(minChunkSizeChars);
			return builder.build();
		});
	}

	private static int clamp(int v, int lo, int hi) {
		return Math.max(lo, Math.min(hi, v));
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Class<T> type, BiFunction<T, T, T> aggregator,
			Function<T, String> consolidatedExtractor, Function<T, Boolean> endProcessTriggeringLogic,
			List<LLMInputDocument> input) throws LLMConfigException, IOException {
		return this.callLLMConsolidateStructuredReturn(chatModel, prompt, context, pastConsolidation, Map.of(), type,
				aggregator, consolidatedExtractor, endProcessTriggeringLogic, input);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Class<T> type, BiFunction<T, T, T> aggregator,
			Function<T, String> consolidatedExtractor, Function<T, Boolean> endProcessTriggeringLogic,
			List<LLMInputDocument> input, boolean alreadySplitted) throws LLMConfigException, IOException {
		return this.callLLMConsolidateStructuredReturn(chatModel, prompt, context, pastConsolidation, Map.of(), type,
				aggregator, consolidatedExtractor, endProcessTriggeringLogic, input);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams, Class<T> type,
			BiFunction<T, T, T> aggregator, Function<T, String> consolidatedExtractor,
			Function<T, Boolean> endProcessTriggeringLogic, List<LLMInputDocument> input)
			throws LLMConfigException, IOException {
		return callLLMConsolidateStructuredReturn(chatModel, prompt, context, pastConsolidation, additionalParams, type,
				aggregator, consolidatedExtractor, endProcessTriggeringLogic, input, false);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams, Class<T> type,
			BiFunction<T, T, T> aggregator, Function<T, String> consolidatedExtractor,
			Function<T, Boolean> endProcessTriggeringLogic, List<LLMInputDocument> input, boolean alreadySplitted)
			throws LLMConfigException, IOException {
		Iterator<LLMInputDocument> iterator = input.iterator();
		return callLLMConsolidateStructuredReturn(chatModel, prompt, context, pastConsolidation, additionalParams, type,
				aggregator, consolidatedExtractor, endProcessTriggeringLogic, () -> {
					if (iterator.hasNext())
						return iterator.next();
					return null;
				}, alreadySplitted);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams, Class<T> type,
			BiFunction<T, T, T> aggregator, Function<T, String> consolidatedExtractor,
			Function<T, Boolean> endProcessTriggeringLogic, Supplier<LLMInputDocument> input)
			throws LLMConfigException, IOException {
		return this.callLLMConsolidateStructuredReturn(chatModel, prompt, context, pastConsolidation, additionalParams,
				type, aggregator, consolidatedExtractor, endProcessTriggeringLogic, input, false);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams, Class<T> type,
			BiFunction<T, T, T> aggregator, Function<T, String> consolidatedExtractor,
			Function<T, Boolean> endProcessTriggeringLogic, Supplier<LLMInputDocument> input, boolean alreadySplitted)
			throws LLMConfigException, IOException {
		final int contextWindow = chatModel.getContextLength();
		List<ConsolidationInputBatch> currentBatchesQueue = new ArrayList<BaseLLMSInvokingAndProvidingService.ConsolidationInputBatch>();
		LLMInputDocument currentInput = null;
		final int promptLength = prompt.getTokensSize();
		T consolidated = null;
		String _consolidated = pastConsolidation;
		BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(type);
		final String formatSpecification = outputConverter.getFormat();
		final int formatSpecificationLength = tokensEstimation.estimate(formatSpecification);
		int fragmentBudget = computeFragmentBudget(_consolidated, promptLength, contextWindow, additionalParams);
		do {
			currentInput = input.get();

			if (currentInput != null && currentInput.text != null && currentInput.text.trim().length() > 0) {
				StringBuffer metaInfos = new StringBuffer();
				if (currentInput.documentReference != null) {
					metaInfos.append(DOCUMENT_REFERENCE + currentInput.documentReference);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.documentUrl != null) {
					metaInfos.append(DOCUMENT_URL + currentInput.documentUrl);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.title != null) {
					metaInfos.append(DOCUMENT_TITLE + currentInput.title);
					metaInfos.append(NEWLINE);
				}
				String metaData = metaInfos.toString();
				final String fullTextWithMetaData = metaData + currentInput.text;
				fragmentBudget -= formatSpecificationLength;
				final int textTokensLength = tokensEstimation.estimate(fullTextWithMetaData);

				if (!alreadySplitted && textTokensLength > fragmentBudget) {
					// splits will be loaded in currentBatchesQueue
					TokenTextSplitter splitter = createTokenSplitter(fragmentBudget);
					Document document = new Document(currentInput.text);
					List<Document> documents = splitter.split(document);
					final LLMInputDocument _currentInput = currentInput;
					List<ConsolidationBatchItem> splitted = documents.stream().map(x -> {
						ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
						LLMInputDocument ci = new LLMInputDocument(_currentInput.documentReference,
								_currentInput.documentUrl, _currentInput.title, metaData + x.getText());
						batchItem.input = ci;
						batchItem.tokensCount = tokensEstimation.estimate(ci.text);
						return batchItem;
					}).toList();
					List<ConsolidationInputBatch> newBatchesQueue = splitted.stream().map(x -> {
						ConsolidationInputBatch d = new ConsolidationInputBatch();
						d.inputs.add(x);
						d.totaltokens += x.tokensCount;
						d.complete = true;
						return d;
					}).toList();
					currentBatchesQueue.addAll(newBatchesQueue);
				} else {

					ConsolidationInputBatch lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);
					ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
					LLMInputDocument ci = new LLMInputDocument(currentInput.documentReference, currentInput.documentUrl,
							currentInput.title, fullTextWithMetaData);
					batchItem.input = ci;
					batchItem.tokensCount = textTokensLength;
					boolean allocateNewBatch = false;
					if (lastBatch != null) {
						if (fragmentBudget > (lastBatch.totaltokens + batchItem.tokensCount)) {
							lastBatch.totaltokens += batchItem.tokensCount;
							lastBatch.inputs.add(batchItem);
						} else {
							lastBatch.complete = true;
							allocateNewBatch = true;
						}
					} else {
						allocateNewBatch = true;
					}
					if (allocateNewBatch) {
						ConsolidationInputBatch newBatch = new ConsolidationInputBatch();
						newBatch.inputs.add(batchItem);
						newBatch.totaltokens += batchItem.tokensCount;
						currentBatchesQueue.add(newBatch);
					}
				}
			}

			for (ConsolidationInputBatch consolidationInputBatch : currentBatchesQueue) {
				// if this batch is complete or we are at the end of contents
				if (consolidationInputBatch.complete || currentInput == null) {
					StringBuffer currentText = new StringBuffer();
					int tokens = 0;
					for (ConsolidationBatchItem d : consolidationInputBatch.inputs) {
						String thisContent = d.input.text;
						tokens += d.tokensCount;
						currentText.append(thisContent);
						currentText.append(NEWLINE);
					}
					T oldConsolidated = consolidated;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Sending " + tokens
								+ " tokens to callLLMWithDocumentsAndConsolidationStructuredReturn(..)");
					}
					consolidated = callLLMWithDocumentsAndConsolidationStructuredReturn(chatModel, prompt, context,
							currentText.toString(), _consolidated, additionalParams, type);

					// eventual handling of consolidation programmable aggregation
					if (aggregator != null) {
						consolidated = aggregator.apply(oldConsolidated, consolidated);
					}
					boolean exit = endProcessTriggeringLogic != null && endProcessTriggeringLogic.apply(consolidated);
					if (exit) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Applying iteration shortcut on llm processing");
						}
						return consolidated;
					}
					if (consolidatedExtractor != null) {
						_consolidated = consolidatedExtractor.apply(consolidated);
					} else {
						_consolidated = objectMapper.writeValueAsString(consolidated);
					}
					fragmentBudget = computeFragmentBudget(_consolidated, promptLength, contextWindow,
							additionalParams);
					fragmentBudget -= formatSpecificationLength;
				}
			}
		} while (currentInput != null);
		return consolidated;
	}

	protected String callLLMConsolidateText(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, List<LLMInputDocument> input) throws LLMConfigException {
		return this.callLLMConsolidateText(chatModel, prompt, context, pastConsolidation, Map.of(), input);
	}

	protected String callLLMConsolidateText(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams,
			List<LLMInputDocument> input) throws LLMConfigException {
		Iterator<LLMInputDocument> iterator = input.iterator();
		return callLLMConsolidateText(chatModel, prompt, context, pastConsolidation, additionalParams, () -> {
			if (iterator.hasNext())
				return iterator.next();
			return null;
		});
	}

	protected String callLLMConsolidateText(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, String pastConsolidation, Map<String, Object> additionalParams,
			Supplier<LLMInputDocument> input) throws LLMConfigException {
		final int contextWindow = chatModel.getContextLength();
		List<ConsolidationInputBatch> currentBatchesQueue = new ArrayList<BaseLLMSInvokingAndProvidingService.ConsolidationInputBatch>();
		LLMInputDocument currentInput = null;
		final int promptLength = prompt.getTokensSize();
		String consolidated = pastConsolidation != null ? pastConsolidation : "";
		// Following 2 variables have to be updated once a consolidation is re-run

		int fragmentBudget = computeFragmentBudget(pastConsolidation, promptLength, contextWindow, additionalParams);
		do {
			currentInput = input.get();
			if (currentInput != null && currentInput.text != null && currentInput.text.trim().length() > 0) {
				StringBuffer metaInfos = new StringBuffer();
				if (currentInput.documentReference != null) {
					metaInfos.append(DOCUMENT_REFERENCE + currentInput.documentReference);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.documentUrl != null) {
					metaInfos.append(DOCUMENT_URL + currentInput.documentUrl);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.title != null) {
					metaInfos.append(DOCUMENT_TITLE + currentInput.title);
					metaInfos.append(NEWLINE);
				}
				String metaData = metaInfos.toString();
				final int metaDataTokens = tokensEstimation.estimate(metaData);
				final String fullTextWithMetaData = metaData + currentInput.text;
				fragmentBudget -= metaDataTokens;
				final int textTokensLength = tokensEstimation.estimate(fullTextWithMetaData);
				// final PromptTemplate promptTemplate = new PromptTemplate(prompt);
				// promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
				// promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
				// if text token is too big for the residual i consolidate inside the single
				// text
				if (textTokensLength > fragmentBudget) {
					// splits will be loaded in currentBatchesQueue
					TokenTextSplitter splitter = createTokenSplitter(fragmentBudget);
					Document document = new Document(currentInput.text);
					List<Document> documents = splitter.split(document);
					final LLMInputDocument _currentInput = currentInput;
					List<ConsolidationBatchItem> splitted = documents.stream().map(x -> {
						ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
						LLMInputDocument ci = new LLMInputDocument(_currentInput.documentReference,
								_currentInput.documentUrl, _currentInput.title, metaData + x.getText());
						batchItem.input = ci;
						batchItem.tokensCount = tokensEstimation.estimate(ci.text);
						return batchItem;
					}).toList();
					List<ConsolidationInputBatch> newBatchesQueue = splitted.stream().map(x -> {
						ConsolidationInputBatch d = new ConsolidationInputBatch();
						d.inputs.add(x);
						d.totaltokens += x.tokensCount;
						d.complete = true;
						return d;
					}).toList();
					currentBatchesQueue.addAll(newBatchesQueue);
				} else {

					ConsolidationInputBatch lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);
					ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
					LLMInputDocument ci = new LLMInputDocument(currentInput.documentReference, currentInput.documentUrl,
							currentInput.title, fullTextWithMetaData);
					batchItem.input = ci;
					batchItem.tokensCount = textTokensLength;
					boolean allocateNewBatch = false;
					if (lastBatch != null) {
						if (fragmentBudget > (lastBatch.totaltokens + batchItem.tokensCount)) {
							lastBatch.totaltokens += batchItem.tokensCount;
							lastBatch.inputs.add(batchItem);
						} else {
							lastBatch.complete = true;
							allocateNewBatch = true;
						}
					} else {
						allocateNewBatch = true;
					}
					if (allocateNewBatch) {
						ConsolidationInputBatch newBatch = new ConsolidationInputBatch();
						newBatch.inputs.add(batchItem);
						newBatch.totaltokens += batchItem.tokensCount;
						currentBatchesQueue.add(newBatch);
					}
				}
			}

			for (ConsolidationInputBatch consolidationInputBatch : currentBatchesQueue) {
				// if this batch is complete or we are at the end of contents
				if (consolidationInputBatch.complete || currentInput == null) {
					StringBuffer currentText = new StringBuffer();
					for (ConsolidationBatchItem d : consolidationInputBatch.inputs) {
						String thisContent = d.input.text;
						currentText.append(thisContent);
						currentText.append(NEWLINE);
					}
					
					consolidated = callLLMWithDocumentsAndConsolidation(chatModel, prompt, context,currentText.toString(), consolidated,
							additionalParams);
					fragmentBudget = computeFragmentBudget(consolidated, promptLength, contextWindow, additionalParams);
				}
			}
		} while (currentInput != null);
		return consolidated;
	}

	protected <T> T callLLMStructuredReturn(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> additionalVariables, Class<T> type)
			throws LLMConfigException {

		Map<String, Object> params = new HashMap<>(additionalVariables);
		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		T data = (T) chatModel.structuredResponse(prompt, params, context, type);
		return data;
	}

	protected <T> T callLLMWithConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object consolidated, Class<T> type)
			throws LLMConfigException {
		Map<String, Object> params = new HashMap<>();
		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		params.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);

		T data = (T) chatModel.structuredResponse(prompt, params, context, type);

		return data;
	}

	protected <T> T callLLMWithConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object consolidated,
			Map<String, Object> additionalVariables, Class<T> type) throws LLMConfigException {
		Map<String, Object> params = new HashMap<>(additionalVariables);
		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		params.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);

		T data = (T) chatModel.structuredResponse(prompt, params, context, type);

		return data;
	}

	protected String callLLMWithDocuments(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Object documents) throws LLMConfigException {
		Map<String, Object> params = new HashMap<>();
		params.put(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		String result = chatModel.textResponse(prompt, params, context);
		final boolean skipThinkingMarkup = chatModel.isApplyThinkingMarkupHandling();
		if (result != null && skipThinkingMarkup) {
			result = ClientChatCallUtil.removeThinking(result);
		}
		return result;
	}

	protected String callLLMWithDocuments(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Object documents, Map<String, Object> params) throws LLMConfigException {

		Map<String, Object> _params = new HashMap<>(params);
		context = context.integrateWithDocuments(documents);

		String result = chatModel.textResponse(prompt, _params, context);
		final boolean skipThinkingMarkup = chatModel.isApplyThinkingMarkupHandling();
		if (result != null && skipThinkingMarkup) {
			result = ClientChatCallUtil.removeThinking(result);
		}
		return result;
	}

	protected String callLLMWithDocumentsAndConsolidation(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object documents, String consolidated)
			throws LLMConfigException {
		return this.callLLMWithDocumentsAndConsolidation(chatModel, prompt, context, documents, consolidated, Map.of());
	}

	protected String callLLMWithDocumentsAndConsolidation(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object documents, String consolidated,
			Map<String, Object> additionalParams) throws LLMConfigException {

		Map<String, Object> params = new HashMap<>(additionalParams);
		params.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		context = context.integrateWithDocuments(documents);

		String result = chatModel.textResponse(prompt, params, context);
		final boolean skipThinkingMarkup = chatModel.isApplyThinkingMarkupHandling();
		if (result != null && skipThinkingMarkup) {
			result = ClientChatCallUtil.removeThinking(result);
		}
		return result;
	}

	protected <T> T callLLMWithDocumentsAndConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object documents, Object consolidated,
			Class<T> type) throws LLMConfigException {
		Map<String, Object> params = new HashMap<>();

		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		params.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		context = context.integrateWithDocuments(documents);

		T data = (T) chatModel.structuredResponse(prompt, params, context, type);

		return data;
	}

	protected <T> T callLLMWithDocumentsAndConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object documents, Object consolidated,
			Map<String, Object> additionalParams, Class<T> type) throws LLMConfigException {
		Map<String, Object> params = new HashMap<>(additionalParams);
		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		params.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		context = context.integrateWithDocuments(documents);
		T data = (T) chatModel.structuredResponse(prompt, params, context, type);
		return data;
	}

	protected <T> T callLLMWithDocumentsStructuredReturn(IGConfigurableChatModel chatModel,
			GPromptTemplateConfig prompt, IChatRequestContext context, Object documents, Class<T> type)
			throws LLMConfigException {
		Map<String, Object> params = new HashMap<>();

		BeanOutputConverter<T> outputConverter = chatModel.createConverter(type);
		params.put(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		context = context.integrateWithDocuments(documents);

		T data = (T) chatModel.structuredResponse(prompt, params, context, type, outputConverter);

		return data;
	}

	protected int computeFragmentBudget(String consolidated, int promptLength, int contextWindowLength) {
		return computeFragmentBudget(consolidated, promptLength, contextWindowLength, Map.of());
	}

	protected int computeFragmentBudget(String consolidated, int promptLength, int contextWindowLength,
			Map<String, Object> additionalParams) {
		double consolidationLength = tokensEstimation.estimate(consolidated);
		double contextWindowD = contextWindowLength;
		double promptLengthD = promptLength;
		if (additionalParams != null) {
			for (Object entry : additionalParams.values()) {
				if (entry != null) {
					contextWindowD -= tokensEstimation.estimate(entry.toString());
				}
			}
		}
		return (int) Math
				.round((contextWindowD - (consolidationLength + promptLengthD)) * ERRONEUS_TOKEN_LENGTH_ERROR_COEFF);
	}

	protected Stream<String> filterCSVLines(Stream<String> streamedLines, int nColumns) {
		return streamedLines.filter(x -> this.isCSV(x, nColumns));
	}

	protected Stream<String> filterCSVLines(String content, int nColumns) {
		if (content == null || content.trim().length() == 0)
			return Stream.empty();
		final ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes());
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bis));
		final Supplier<String> supplier = () -> {
			String line = null;
			try {
				line = bufferedReader.readLine();
			} catch (Throwable th) {
			}
			return line;
		};
		return filterCSVLines(supplier2stream(supplier), nColumns);
	}

	protected String fixPromptWithFormat(String prompt) {
		if (!prompt.toLowerCase().contains("{" + FORMAT_TEMPLATE_VARIABLE.toLowerCase() + "}")) {
			prompt += "\r\nOUTPUT FORMAT:\r\n";
			prompt += "{" + FORMAT_TEMPLATE_VARIABLE + "}";
		}
		return prompt;
	}

	protected boolean isCSV(String line, int nColumns) {
		if (line == null || line.trim().length() == 0)
			return false;
		char chars[] = line.toCharArray();
		int count = 0;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == CSV_COLUMN_SEPARATOR) {
				count++;
			}
		}
		return count >= (nColumns - 1);
	}

	protected void loadParams(PromptTemplate template, Map<String, Object> params) {
		if (params != null) {
			for (Entry<String, Object> entry : params.entrySet()) {
				template.add(entry.getKey(), entry.getValue());
			}
		}
	}

	protected <T> Stream<T> readCSVLines(String content, int nColumns, Function<String, T> reader) {
		return filterCSVLines(content, nColumns).map(reader).filter(y -> y != null);
	}

	protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> params, Stream<LLMInputDocument> inputStream)
			throws LLMConfigException {
		int contextWindow = chatModel.getContextLength() * 2 / 3;
		List<ConsolidationInputBatch> currentBatchesQueue = new ArrayList<BaseLLMSInvokingAndProvidingService.ConsolidationInputBatch>();
		LLMInputDocument currentInput = null;
		final int promptLength = prompt.getTokensSize();
		String consolidated = "";
		// Following 2 variables have to be updated once a consolidation is re-run
		Iterator<LLMInputDocument> input = inputStream.iterator();
		Flux<String> output = Flux.empty();
		int fragmentBudget = computeFragmentBudget(consolidated, promptLength, contextWindow, params);
		boolean hasNext = input.hasNext();
		int iteration = 0;
		while (hasNext) {
			currentInput = input.next();
			hasNext = input.hasNext();
			iteration++;
			if (currentInput != null && currentInput.text != null && currentInput.text.trim().length() > 0) {
				StringBuffer metaInfos = new StringBuffer();
				if (currentInput.documentReference != null) {
					metaInfos.append(DOCUMENT_REFERENCE + currentInput.documentReference);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.documentUrl != null) {
					metaInfos.append(DOCUMENT_URL + currentInput.documentUrl);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.title != null) {
					metaInfos.append(DOCUMENT_TITLE + currentInput.title);
					metaInfos.append(NEWLINE);
				}
				String metaData = metaInfos.toString();
				final int metaDataTokens = tokensEstimation.estimate(metaData);
				final String fullTextWithMetaData = metaData + currentInput.text;
				fragmentBudget -= metaDataTokens;
				final int textTokensLength = tokensEstimation.estimate(fullTextWithMetaData);
				// final PromptTemplate promptTemplate = new PromptTemplate(prompt);
				// promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
				// promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
				// if text token is too big for the residual i consolidate inside the single
				// text
				if (textTokensLength > fragmentBudget) {
					// splits will be loaded in currentBatchesQueue
					TokenTextSplitter splitter = createTokenSplitter(fragmentBudget);
					Document document = new Document(currentInput.text);
					List<Document> documents = splitter.split(document);
					final LLMInputDocument _currentInput = currentInput;
					List<ConsolidationBatchItem> splitted = documents.stream().map(x -> {
						ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
						LLMInputDocument ci = new LLMInputDocument(_currentInput.documentReference,
								_currentInput.documentUrl, _currentInput.title, metaData + x.getText());
						batchItem.input = ci;
						batchItem.tokensCount = tokensEstimation.estimate(ci.text);
						return batchItem;
					}).toList();
					List<ConsolidationInputBatch> newBatchesQueue = splitted.stream().map(x -> {
						ConsolidationInputBatch d = new ConsolidationInputBatch();
						d.inputs.add(x);
						d.totaltokens += x.tokensCount;
						d.complete = true;
						return d;
					}).toList();
					currentBatchesQueue.addAll(newBatchesQueue);
				} else {

					ConsolidationInputBatch lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);
					ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
					LLMInputDocument ci = new LLMInputDocument(currentInput.documentReference, currentInput.documentUrl,
							currentInput.title, fullTextWithMetaData);
					batchItem.input = ci;
					batchItem.tokensCount = textTokensLength;
					boolean allocateNewBatch = false;
					if (lastBatch != null) {
						if (fragmentBudget > (lastBatch.totaltokens + batchItem.tokensCount)) {
							lastBatch.totaltokens += batchItem.tokensCount;
							lastBatch.inputs.add(batchItem);
						} else {
							lastBatch.complete = true;
							allocateNewBatch = true;
						}
					} else {
						allocateNewBatch = true;
					}
					if (allocateNewBatch) {
						ConsolidationInputBatch newBatch = new ConsolidationInputBatch();
						newBatch.inputs.add(batchItem);
						newBatch.totaltokens += batchItem.tokensCount;
						currentBatchesQueue.add(newBatch);
					}
				}
			}

			for (ConsolidationInputBatch consolidationInputBatch : currentBatchesQueue) {
				// if this batch is complete or we are at the end of contents
				if (consolidationInputBatch.complete || !hasNext) {
					StringBuffer currentText = new StringBuffer();
					for (ConsolidationBatchItem d : consolidationInputBatch.inputs) {
						String thisContent = d.input.text;
						currentText.append(thisContent);
						currentText.append(NEWLINE);
					}
					if (!hasNext) {
						Map<String, Object> clonedParams = new HashMap<>(params);
						clonedParams.put(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);

						// clonedParams.put(DOCUMENTS_TEMPLATE_VARIABLE, currentText.toString());
						context = context.integrateWithDocuments(currentText.toString());
						output = callLLMReactive(chatModel, prompt, context, clonedParams);
					} else {
						consolidated = callLLMWithDocumentsAndConsolidation(chatModel, prompt, context,
								currentText.toString(), consolidated, params);
						fragmentBudget = computeFragmentBudget(consolidated, promptLength, contextWindow, params);
					}
				}
			}
		}

		return output;
	}

	protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
			IChatRequestContext context, Map<String, Object> params) throws LLMConfigException {
		return chatModel.streamStringResponse(prompt, params, context);
	}

}
