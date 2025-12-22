package ai.gebo.llms.abstraction.layer.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*******************************************************************************************************************
 * General base class for services using large language models
 */
@AllArgsConstructor
public class BaseLlmsInvokingService {
	private static final String NEWLINE = "\r\n";
	protected final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	protected final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
	public static final String CONSOLIDATED_TEMPLATE_VARIABLE = "consolidated";
	public static final String DOCUMENTS_TEMPLATE_VARIABLE = "documents";
	public static final String USER_QUESTION_TEMPLATE_VARIABLE = "question";
	public static final String FORMAT_TEMPLATE_VARIABLE = "format";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final JTokkitTokenCountEstimator tokensEstimation = new JTokkitTokenCountEstimator();

	protected IGConfigurableEmbeddingModel getDefaultEmbeddingModel() {
		return embeddingModelsRuntimeDao.defaultHandler();
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases) {
		return this.getEmbeddingModelsListByKnowledgeBases(knowledgeBases, true);
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases, boolean includeDefaultEmbeddingModel) {
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		IGConfigurableEmbeddingModel defaultEmbeddingModel = embeddingModelsRuntimeDao.defaultHandler();
		if (defaultEmbeddingModel != null && includeDefaultEmbeddingModel) {
			embeddingModels.add(defaultEmbeddingModel);
		}

		knowledgeBases.stream().map(x -> x.getEmbeddingModelReferences()).filter(y -> y != null && !y.isEmpty())
				.forEach(modelsList -> {
					modelsList.forEach(modelReference -> {
						IGConfigurableEmbeddingModel model = embeddingModelsRuntimeDao
								.findByModelReference(modelReference);
						if (model != null && model != defaultEmbeddingModel) {
							if (!embeddingModels.stream()
									.anyMatch(x -> (x == model || model.getCode().equals(x.getCode())))) {
								embeddingModels.add(model);
							}
						}
					});
				});
		return embeddingModels;
	}

	protected IGConfigurableChatModel getChatModel(GObjectRef<GBaseChatModelConfig> chatModelReference)
			throws LLMConfigException {

		IGConfigurableChatModel chatModel = null;
		if (chatModelReference != null) {
			chatModel = chatModelsConfigDao.findByModelReference(chatModelReference);
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			throw new LLMConfigException("No ChatModel configured");
		return chatModel;
	}

	protected String callLLMWithDocuments(IGConfigurableChatModel chatModel, String prompt, Object documents,
			String question) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	protected String callLLMWithDocumentsAndConsolidation(IGConfigurableChatModel chatModel, String prompt,
			Object documents, String question, String consolidated) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);

		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	protected String fixPromptWithFormat(String prompt) {
		if (!prompt.toLowerCase().contains("{" + FORMAT_TEMPLATE_VARIABLE.toLowerCase() + "}")) {
			prompt += "\r\nOUTPUT FORMAT:\r\n";
			prompt += "{" + FORMAT_TEMPLATE_VARIABLE + "}";
		}
		return prompt;
	}

	protected <T> T callLLMWithConsolidationStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			String question, Object consolidated, Class<T> type) throws LLMConfigException {
		prompt = fixPromptWithFormat(prompt);
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(type);
		promptTemplate.add(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	protected <T> T callLLMWithConsolidationStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			String question, Object consolidated, Map<String, Object> additionalVariables, Class<T> type)
			throws LLMConfigException {
		prompt = fixPromptWithFormat(prompt);
		PromptTemplate promptTemplate = new PromptTemplate(prompt);

		BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(type);
		promptTemplate.add(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		for (Entry<String, Object> entry : additionalVariables.entrySet()) {
			promptTemplate.add(entry.getKey(), entry.getValue());
		}
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	protected <T> T callLLMWithDocumentsAndConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			String prompt, Object documents, String question, Object consolidated, Class<T> type)
			throws LLMConfigException {
		prompt = fixPromptWithFormat(prompt);
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(type);
		promptTemplate.add(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	protected <T> T callLLMWithDocumentsStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			Object documents, String question, Class<T> type) throws LLMConfigException {
		prompt = fixPromptWithFormat(prompt);
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(type);
		promptTemplate.add(FORMAT_TEMPLATE_VARIABLE, outputConverter.getFormat());
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	@Getter
	@AllArgsConstructor
	public static class ConsolidationInput {
		final String documentReference;
		final String documentUrl;
		final String title;
		final String text;
	}

	static class ConsolidationBatchItem {
		ConsolidationInput input = null;
		int tokensCount = 0;
	}

	static class ConsolidationInputBatch {
		List<ConsolidationBatchItem> inputs = new ArrayList<ConsolidationBatchItem>();
		int totaltokens = 0;
		boolean complete = false;
	}

	public static double ERRONEUS_TOKEN_LENGTH_ERROR_COEFF = 0.8;

	protected int computeFragmentBudget(String consolidated, int promptLength, int contextWindowLength) {
		double consolidationLength = tokensEstimation.estimate(consolidated);
		double contextWindowD = contextWindowLength;
		double promptLengthD = promptLength;
		return (int) Math
				.round((contextWindowD - (consolidationLength + promptLengthD)) * ERRONEUS_TOKEN_LENGTH_ERROR_COEFF);
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			String question, String pastConsolidation, Class<T> type, BiFunction<T, T, T> aggregator,
			Supplier<ConsolidationInput> input) throws LLMConfigException, IOException {
		final int contextWindow = chatModel.getContextLength();
		List<ConsolidationInputBatch> currentBatchesQueue = new ArrayList<BaseLlmsInvokingService.ConsolidationInputBatch>();
		ConsolidationInput currentInput = null;
		final int promptLength = tokensEstimation.estimate(prompt);
		T consolidated = null;
		String _consolidated = pastConsolidation;

		int fragmentBudget = computeFragmentBudget(_consolidated, promptLength, contextWindow);
		do {
			currentInput = input.get();
			if (currentInput != null && currentInput.text != null && currentInput.text.trim().length() > 0) {
				StringBuffer metaInfos = new StringBuffer();
				if (currentInput.documentReference != null) {
					metaInfos.append("DOCUMENT-REFERENCE:" + currentInput.documentReference);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.documentUrl != null) {
					metaInfos.append("DOCUMENT-URL:" + currentInput.documentUrl);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.title != null) {
					metaInfos.append("DOCUMENT-TITLE:" + currentInput.title);
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
					TokenTextSplitter splitter = new TokenTextSplitter(fragmentBudget, fragmentBudget, fragmentBudget,
							fragmentBudget, true);
					Document document = new Document(currentInput.text);
					List<Document> documents = splitter.split(document);
					final ConsolidationInput _currentInput = currentInput;
					List<ConsolidationBatchItem> splitted = documents.stream().map(x -> {
						ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
						ConsolidationInput ci = new ConsolidationInput(_currentInput.documentReference,
								_currentInput.documentUrl, _currentInput.title, metaData + x.getText());
						batchItem.input = ci;
						batchItem.tokensCount = tokensEstimation.estimate(ci.text);
						return batchItem;
					}).toList();
					List<ConsolidationInputBatch> newBatchesQueue = splitted.stream().map(x -> {
						ConsolidationInputBatch d = new ConsolidationInputBatch();
						d.inputs.add(x);
						d.totaltokens = x.tokensCount;
						d.complete = true;
						return d;
					}).toList();
					currentBatchesQueue.addAll(newBatchesQueue);
				} else {

					ConsolidationInputBatch lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);
					ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
					ConsolidationInput ci = new ConsolidationInput(currentInput.documentReference,
							currentInput.documentUrl, currentInput.title, fullTextWithMetaData);
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
					T oldConsolidated = consolidated;
					consolidated = callLLMWithDocumentsAndConsolidationStructuredReturn(chatModel, prompt,
							currentText.toString(), question, _consolidated, type);
					// eventual handling of consolidation programmable aggregation
					if (aggregator != null) {
						consolidated = aggregator.apply(oldConsolidated, consolidated);
					}
					_consolidated = objectMapper.writeValueAsString(consolidated);
					fragmentBudget = computeFragmentBudget(_consolidated, promptLength, contextWindow);
				}
			}
		} while (currentInput != null);
		return consolidated;
	}

	protected String callLLMConsolidateText(IGConfigurableChatModel chatModel, String prompt, String question,
			String pastConsolidation, Supplier<ConsolidationInput> input) {
		final int contextWindow = chatModel.getContextLength();
		List<ConsolidationInputBatch> currentBatchesQueue = new ArrayList<BaseLlmsInvokingService.ConsolidationInputBatch>();
		ConsolidationInput currentInput = null;
		final int promptLength = tokensEstimation.estimate(prompt);
		String consolidated = pastConsolidation != null ? pastConsolidation : "";
		// Following 2 variables have to be updated once a consolidation is re-run

		int fragmentBudget = computeFragmentBudget(consolidated, promptLength, contextWindow);
		do {
			currentInput = input.get();
			if (currentInput != null && currentInput.text != null && currentInput.text.trim().length() > 0) {
				StringBuffer metaInfos = new StringBuffer();
				if (currentInput.documentReference != null) {
					metaInfos.append("DOCUMENT-REFERENCE:" + currentInput.documentReference);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.documentUrl != null) {
					metaInfos.append("DOCUMENT-URL:" + currentInput.documentUrl);
					metaInfos.append(NEWLINE);
				}
				if (currentInput.title != null) {
					metaInfos.append("DOCUMENT-TITLE:" + currentInput.title);
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
					TokenTextSplitter splitter = new TokenTextSplitter(fragmentBudget, fragmentBudget, fragmentBudget,
							fragmentBudget, true);
					Document document = new Document(currentInput.text);
					List<Document> documents = splitter.split(document);
					final ConsolidationInput _currentInput = currentInput;
					List<ConsolidationBatchItem> splitted = documents.stream().map(x -> {
						ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
						ConsolidationInput ci = new ConsolidationInput(_currentInput.documentReference,
								_currentInput.documentUrl, _currentInput.title, metaData + x.getText());
						batchItem.input = ci;
						batchItem.tokensCount = tokensEstimation.estimate(ci.text);
						return batchItem;
					}).toList();
					List<ConsolidationInputBatch> newBatchesQueue = splitted.stream().map(x -> {
						ConsolidationInputBatch d = new ConsolidationInputBatch();
						d.inputs.add(x);
						d.totaltokens = x.tokensCount;
						d.complete = true;
						return d;
					}).toList();
					currentBatchesQueue.addAll(newBatchesQueue);
				} else {

					ConsolidationInputBatch lastBatch = currentBatchesQueue.isEmpty() ? null
							: currentBatchesQueue.get(currentBatchesQueue.size() - 1);
					ConsolidationBatchItem batchItem = new ConsolidationBatchItem();
					ConsolidationInput ci = new ConsolidationInput(currentInput.documentReference,
							currentInput.documentUrl, currentInput.title, fullTextWithMetaData);
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
					consolidated = callLLMWithDocumentsAndConsolidation(chatModel, prompt, currentText.toString(),
							question, consolidated);
					fragmentBudget = computeFragmentBudget(consolidated, promptLength, contextWindow);
				}
			}
		} while (currentInput != null);
		return consolidated;
	}

	protected String callLLMConsolidateText(IGConfigurableChatModel chatModel, String prompt, String question,
			String pastConsolidation, List<ConsolidationInput> input) {
		Iterator<ConsolidationInput> iterator = input.iterator();
		return callLLMConsolidateText(chatModel, prompt, question, pastConsolidation, () -> {
			if (iterator.hasNext())
				return iterator.next();
			return null;
		});
	}

	protected <T> T callLLMConsolidateStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			String question, String pastConsolidation, Class<T> type, BiFunction<T, T, T> aggregator,
			List<ConsolidationInput> input) throws LLMConfigException, IOException {
		Iterator<ConsolidationInput> iterator = input.iterator();
		return callLLMConsolidateStructuredReturn(chatModel, prompt, question, pastConsolidation, type, aggregator,
				() -> {
					if (iterator.hasNext())
						return iterator.next();
					return null;
				});
	}

}
