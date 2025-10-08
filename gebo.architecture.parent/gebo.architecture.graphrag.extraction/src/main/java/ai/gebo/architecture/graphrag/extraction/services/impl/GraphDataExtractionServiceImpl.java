package ai.gebo.architecture.graphrag.extraction.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.config.GraphRagExtractionStaticConfig;
import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.GraphEntityStandardType;
import ai.gebo.architecture.graphrag.extraction.model.GraphObjectType;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.extraction.repositories.GraphRagExtractionConfigRepository;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.DocumentMetaInfos;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GraphDataExtractionServiceImpl implements IGraphDataExtractionService {
	private static final String GRAPHRAG_EXTRACTION_PROMPT_OBJECT = "Graphrag-extraction-prompt-object";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG_CHECKED = "GraphRagExtractionConfig-by-default-config-checked";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG = "GraphRagExtractionConfig-by-default-config";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED = "GraphRagExtractionConfig-by-knowledgebase-and-project-checked";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE = "GraphRagExtractionConfig-by-knowledgebase";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED = "GraphRagExtractionConfig-by-datasource-checked";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT = "GraphRagExtractionConfig-by-project";
	private static final String GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE = "GraphRagExtractionConfig-by-datasource";
	private final IGChatModelRuntimeConfigurationDao chatModelsConfiguration;
	private final GraphRagExtractionStaticConfig staticConfig;
	private final GraphRagExtractionConfigRepository configRepository;

	@Override
	public LLMExtractionResult extract(Document document, GraphRagExtractionConfig configuration,
			Map<String, Object> cache) throws LLMConfigException {
		String text = document.getMetadata() != null ? document.getMetadata().toString() + "\r\n" : "";
		text += document.getText();
		return extract(text, configuration, cache);
	}

	@Override
	public LLMExtractionResult extract(String text, GraphRagExtractionConfig configuration, Map<String, Object> cache)
			throws LLMConfigException {
		IGConfigurableChatModel chatModel = getChatModel(configuration);

		if (chatModel == null)
			throw new LLMConfigException("No configured extraction model");
		Prompt promptObject = (Prompt) cache.get(GRAPHRAG_EXTRACTION_PROMPT_OBJECT);
		if (promptObject == null) {
			String formatSpecification = createFormatSpecification(configuration);
			String prompt = staticConfig.getExtractionConfig() != null
					? staticConfig.getExtractionConfig().getExtractionPrompt()
					: null;
			if (configuration != null && configuration.getExtractionPrompt() != null) {
				prompt = configuration.getExtractionPrompt();
			}
			PromptTemplate promptTemplate = new PromptTemplate(prompt);
			promptTemplate.add("format", formatSpecification);
			promptObject = promptTemplate.create();
			cache.put(GRAPHRAG_EXTRACTION_PROMPT_OBJECT, promptObject);
		}
		ChatClientRequestSpec requestSpec = chatModel.getChatClient().prompt(promptObject).system(text);

		return clean(requestSpec.call().entity(LLMExtractionResult.class));
	}

	private LLMExtractionResult clean(LLMExtractionResult data) {
		LLMExtractionResult cleanResult = new LLMExtractionResult();
		List<EntityObject> entities = data.getEntities().stream().filter(GraphDataExtractionServiceImpl::validEntity)
				.toList();
		cleanResult.setEntities(entities);
		List<EventObject> events = data.getEvents().stream().filter(GraphDataExtractionServiceImpl::validEvent)
				.map(y -> {
					List<EntityObject> participants = y.getParticipantEntities().stream()
							.filter(GraphDataExtractionServiceImpl::validEntity).toList();
					y.setParticipantEntities(participants);
					return y;
				}).filter(u -> u.getParticipantEntities() != null && !u.getParticipantEntities().isEmpty()).toList();
		cleanResult.setEvents(events);
		List<RelationObject> relations = cleanResult.getRelations().stream()
				.filter(GraphDataExtractionServiceImpl::validRelation).toList();
		cleanResult.setRelations(relations);
		List<EntityAliasObject> aliases = data
				.getEntityAliases().stream().filter(x -> validEntity(x.getAliasObject())
						&& validEntity(x.getReferenceObject()) && x.getEquivalenceType() != null && x.getType() != null)
				.toList();
		cleanResult.setEntityAliases(aliases);
		List<EventAliasObject> eventAlias = data
				.getEventAliases().stream().filter(x -> validEvent(x.getAliasObject())
						&& validEvent(x.getReferenceObject()) && x.getType() != null && x.getEquivalenceType() != null)
				.toList();
		cleanResult.setEventAliases(eventAlias);
		return cleanResult;
	}

	private static boolean validEntity(EntityObject x) {
		return x != null && x.getName() != null && x.getType() != null;
	}

	private static boolean validEvent(EventObject x) {
		return x != null && x.getTitle() != null && x.getType() != null;
	}

	private static boolean validRelation(RelationObject x) {
		return x.getType() != null && validEntity(x.getFromEntity()) && validEntity(x.getToEntity());
	}

	private String createFormatSpecification(GraphRagExtractionConfig configuration) {
		BeanOutputConverter<LLMExtractionResult> beanOutputConverter = new BeanOutputConverter<>(
				LLMExtractionResult.class);
		String format = beanOutputConverter.getFormat() + "\r\n";
		List<GraphObjectType> entityTypes = getAllowedEntityTypes(configuration);
		List<GraphObjectType> relationTypes = getAllowedRelationTypes(configuration);
		List<GraphObjectType> eventTypes = getAllowedEventTypes(configuration);
		format += formatAllowedTypes("ALLOWED ENTITY TYPES SPECIFICATION", entityTypes);
		format += formatAllowedTypes("ALLOWED RELATION TYPES SPECIFICATION", relationTypes);
		format += formatAllowedTypes("ALLOWED EVENT TYPES SPECIFICATION", eventTypes);
		return format;
	}

	private List<GraphObjectType> getAllowedEventTypes(GraphRagExtractionConfig configuration) {

		return configuration.getCustomEventTypes() != null && !configuration.getCustomEventTypes().isEmpty()
				? configuration.getCustomEventTypes()
				: staticConfig.getExtractionConfig().getCustomEventTypes();
	}

	private List<GraphObjectType> getAllowedRelationTypes(GraphRagExtractionConfig configuration) {
		return configuration.getCustomRelationTypes() != null && !configuration.getCustomRelationTypes().isEmpty()
				? configuration.getCustomRelationTypes()
				: staticConfig.getExtractionConfig().getCustomRelationTypes();
	}

	private String formatAllowedTypes(String specification, List<GraphObjectType> entityTypes) {
		String output = "";
		if (entityTypes != null && !entityTypes.isEmpty()) {
			output += "\r\n[BEGIN " + specification + "]\r\n";
			for (GraphObjectType graphObjectType : entityTypes) {
				output += "[TYPE]" + graphObjectType.getCode().toLowerCase() + "[/TYPE]";
				if (graphObjectType.getDescription() != null)
					output += "[DESCRIPTION]" + graphObjectType.getDescription() + "[/DESCRIPTION]";
				output += "\r\n";
			}
			output += "[END " + specification + "]\r\n";
		}
		return output;
	}

	private List<GraphObjectType> getAllowedEntityTypes(GraphRagExtractionConfig configuration) {
		List<GraphObjectType> entityTypes = new ArrayList<GraphObjectType>();
		GraphEntityStandardType[] predefinedTypes = GraphEntityStandardType.values();
		for (GraphEntityStandardType st : predefinedTypes) {
			GraphObjectType got = new GraphObjectType();
			got.setCode(st.getTypeCode());
			got.setDescription(st.getDescription());
			entityTypes.add(got);
		}
		if (configuration.getCustomEntityTypes() != null && !configuration.getCustomEntityTypes().isEmpty()) {
			entityTypes.addAll(configuration.getCustomEntityTypes());
		} else if (staticConfig != null && staticConfig.getExtractionConfig() != null
				&& staticConfig.getExtractionConfig().getCustomEntityTypes() != null) {
			entityTypes.addAll(staticConfig.getExtractionConfig().getCustomEntityTypes());
		}
		return entityTypes;

	}

	private IGConfigurableChatModel getChatModel(GraphRagExtractionConfig configuration) throws LLMConfigException {
		IGConfigurableChatModel value = null;
		if (configuration.getUsedModelConfiguration() != null) {
			value = this.chatModelsConfiguration.findByPredicate(x -> {
				return x.getCode().equals(configuration.getUsedModelConfiguration().getCode()) && x.getConfig()
						.getClass().getName().equals(configuration.getUsedModelConfiguration().getClassName());
			});
		}
		if (value == null) {
			value = this.chatModelsConfiguration.findByPredicate(x -> {
				GBaseChatModelConfig chatConfig;
				chatConfig = ((GBaseChatModelConfig) x.getConfig());
				return chatConfig.getForUses() != null
						&& chatConfig.getForUses().contains(ChatModelsUses.GRAPH_EXTRACTION);
			});
			if (value == null) {
				value = this.chatModelsConfiguration.defaultHandler();
			}
		}
		return value;
	}

	@Override
	public LLMExtractionResult extract(Document document, GDocumentReference docreference, Map<String, Object> cache)
			throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig dataSourceLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE);
		GraphRagExtractionConfig projectLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT);
		GraphRagExtractionConfig knowledgeBaseLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE);
		GraphRagExtractionConfig defaultLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG);
		if (defaultLevelConfig == null) {
			List<GraphRagExtractionConfig> defaultConfigs = this.configRepository
					.findByDefaultConfiguration(Boolean.TRUE);
			if (!defaultConfigs.isEmpty()) {
				defaultLevelConfig = defaultConfigs.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG, defaultLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG_CHECKED, "true");
		}

		String knowledgeBaseCode = docreference.getRootKnowledgebaseCode();
		String projectCode = docreference.getParentProjectCode();
		if (knowledgeBaseCode != null
				&& !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED)) {
			List<GraphRagExtractionConfig> forKnowledgeBase = this.configRepository
					.findByKnowledgeBaseCode(knowledgeBaseCode);
			for (GraphRagExtractionConfig kc : forKnowledgeBase) {
				if (kc.getProjectCode() == null && knowledgeBaseLevelConfig == null) {
					knowledgeBaseLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE, knowledgeBaseLevelConfig);
				}
				if (kc.getProjectCode() != null && projectCode != null && kc.getProjectCode().equals(projectCode)
						&& projectLevelConfig == null) {
					projectLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT, projectLevelConfig);
				}
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED, "true");
		}
		if (dataSourceLevelConfig == null && !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED)) {
			List<GraphRagExtractionConfig> forDataSource = this.configRepository.findByEndpointClassNameAndEndpointCode(
					docreference.getProjectEndpointReference().getClassName(),
					docreference.getProjectEndpointReference().getCode());
			if (!forDataSource.isEmpty()) {
				dataSourceLevelConfig = forDataSource.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE, dataSourceLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED, "true");

		}

		return extract(document, cache, mainConfiguration, defaultLevelConfig, knowledgeBaseLevelConfig,
				projectLevelConfig, dataSourceLevelConfig);
	}

	private LLMExtractionResult extract(Document document, Map<String, Object> cache,
			GraphRagExtractionConfig mainConfiguration, GraphRagExtractionConfig defaultLevelConfig,
			GraphRagExtractionConfig knowledgeBaseLevelConfig, GraphRagExtractionConfig projectLevelConfig,
			GraphRagExtractionConfig dataSourceLevelConfig) throws LLMConfigException {
		if (dataSourceLevelConfig != null) {
			return extract(document, dataSourceLevelConfig, cache);
		}
		if (projectLevelConfig != null) {
			return extract(document, projectLevelConfig, cache);
		}
		if (knowledgeBaseLevelConfig != null) {
			return extract(document, knowledgeBaseLevelConfig, cache);
		}
		if (defaultLevelConfig != null) {
			return extract(document, defaultLevelConfig, cache);
		}
		throw new LLMConfigException("There is no available configuration for knowledge extraction");
	}

	@Override
	public boolean isConfigured() {

		long count = this.configRepository.countByDefaultConfiguration(Boolean.TRUE);
		return count > 0;
		// return true;
	}

	@Override
	public LLMExtractionResult extract(String query, List<String> knowledgeBases, Map<String, Object> cache)
			throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig defaultLevelConfig = null;
		List<GraphRagExtractionConfig> defaultConfigs = this.configRepository.findByDefaultConfiguration(Boolean.TRUE);
		if (!defaultConfigs.isEmpty()) {
			defaultLevelConfig = defaultConfigs.get(0);
		}
		GraphRagExtractionConfig joinConfiguration = joinConfigurations(mainConfiguration, defaultLevelConfig,
				knowledgeBases);
		return extract(query, joinConfiguration, cache);
	}

	private GraphRagExtractionConfig joinConfigurations(GraphRagExtractionConfig mainConfiguration,
			GraphRagExtractionConfig defaultLevelConfig, List<String> knowledgeBases) {
		// TODO: implement logics
		return defaultLevelConfig;
	}

	@Override
	public LLMExtractionResult extract(List<Document> documents, GDocumentReference docreference,
			Map<String, Object> cache) throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig dataSourceLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE);
		GraphRagExtractionConfig projectLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT);
		GraphRagExtractionConfig knowledgeBaseLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE);
		GraphRagExtractionConfig defaultLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG);
		if (defaultLevelConfig == null) {
			List<GraphRagExtractionConfig> defaultConfigs = this.configRepository
					.findByDefaultConfiguration(Boolean.TRUE);
			if (!defaultConfigs.isEmpty()) {
				defaultLevelConfig = defaultConfigs.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG, defaultLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG_CHECKED, "true");
		}

		String knowledgeBaseCode = docreference.getRootKnowledgebaseCode();
		String projectCode = docreference.getParentProjectCode();
		if (knowledgeBaseCode != null
				&& !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED)) {
			List<GraphRagExtractionConfig> forKnowledgeBase = this.configRepository
					.findByKnowledgeBaseCode(knowledgeBaseCode);
			for (GraphRagExtractionConfig kc : forKnowledgeBase) {
				if (kc.getProjectCode() == null && knowledgeBaseLevelConfig == null) {
					knowledgeBaseLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE, knowledgeBaseLevelConfig);
				}
				if (kc.getProjectCode() != null && projectCode != null && kc.getProjectCode().equals(projectCode)
						&& projectLevelConfig == null) {
					projectLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT, projectLevelConfig);
				}
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED, "true");
		}
		if (dataSourceLevelConfig == null && !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED)) {
			List<GraphRagExtractionConfig> forDataSource = this.configRepository.findByEndpointClassNameAndEndpointCode(
					docreference.getProjectEndpointReference().getClassName(),
					docreference.getProjectEndpointReference().getCode());
			if (!forDataSource.isEmpty()) {
				dataSourceLevelConfig = forDataSource.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE, dataSourceLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED, "true");

		}
		return extract(documents, cache, mainConfiguration, defaultLevelConfig, knowledgeBaseLevelConfig,
				projectLevelConfig, dataSourceLevelConfig);

	}

	private LLMExtractionResult extract(List<Document> documents, Map<String, Object> cache,
			GraphRagExtractionConfig mainConfiguration, GraphRagExtractionConfig defaultLevelConfig,
			GraphRagExtractionConfig knowledgeBaseLevelConfig, GraphRagExtractionConfig projectLevelConfig,
			GraphRagExtractionConfig dataSourceLevelConfig) throws LLMConfigException {

		if (dataSourceLevelConfig != null) {
			return extract(documents, dataSourceLevelConfig, cache);
		}
		if (projectLevelConfig != null) {
			return extract(documents, projectLevelConfig, cache);
		}
		if (knowledgeBaseLevelConfig != null) {
			return extract(documents, knowledgeBaseLevelConfig, cache);
		}
		if (defaultLevelConfig != null) {
			return extract(documents, defaultLevelConfig, cache);
		}
		throw new LLMConfigException("There is no available configuration for knowledge extraction");
	}

	@Override
	public LLMExtractionResult extract(List<Document> documents, GraphRagExtractionConfig configuration,
			Map<String, Object> cache) throws LLMConfigException {

		IGConfigurableChatModel chatModel = getChatModel(configuration);

		if (chatModel == null)
			throw new LLMConfigException("No configured extraction model");
		Prompt promptObject = (Prompt) cache.get(GRAPHRAG_EXTRACTION_PROMPT_OBJECT);
		if (promptObject == null) {
			String formatSpecification = createFormatSpecification(configuration);
			String prompt = staticConfig.getExtractionConfig() != null
					? staticConfig.getExtractionConfig().getExtractionPrompt()
					: null;
			if (configuration != null && configuration.getExtractionPrompt() != null) {
				prompt = configuration.getExtractionPrompt();
			}
			PromptTemplate promptTemplate = new PromptTemplate(prompt);
			promptTemplate.add("format", formatSpecification);
			promptObject = promptTemplate.create();
			cache.put(GRAPHRAG_EXTRACTION_PROMPT_OBJECT, promptObject);
		}
		List<Message> messages = documents.stream()
				.map(x -> (Message) new SystemMessage(
						"[CHUNK-ID]" + x.getId() + "[/CHUNK-ID]\r\n" + x.getText() + "\r\n" + x.getMetadata()))
				.toList();
		ChatClientRequestSpec requestSpec = chatModel.getChatClient().prompt(promptObject).messages(messages);
		return clean(requestSpec.call().entity(LLMExtractionResult.class));
	}

	public final static BiPredicate<List<Document>, Integer> groupingPredicate = (List<Document> batch,
			Integer tokensBudget) -> {
		boolean continueGrouping = true;
		long totalSize = 0;
		if (batch != null) {
			for (Document doc : batch) {
				if (doc.getMetadata() != null && doc.getMetadata().containsKey(DocumentMetaInfos.GEBO_TOKEN_LENGTH)
						&& doc.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number size) {
					totalSize += size.longValue();
					continueGrouping = continueGrouping && (totalSize < tokensBudget.longValue());
				} else {
					continueGrouping = false;
					break;
				}
			}
		}
		return continueGrouping;
	};

	@Override
	public Predicate<List<Document>> getBatchingPredicate(GraphRagExtractionConfig configuration,
			Map<String, Object> cache) throws LLMConfigException {
		IGConfigurableChatModel chatModel = getChatModel(configuration);
		int length = chatModel.getContextLength();
		if (length > 512) {
			length -= 512;
		}
		final int lengthMax = length;
		Predicate<List<Document>> predicate = (List<Document> data) -> groupingPredicate.test(data, lengthMax);
		return predicate;
	}

	@Override
	public Predicate<List<Document>> getBatchingPredicate(GDocumentReference docreference, Map<String, Object> cache)
			throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig dataSourceLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE);
		GraphRagExtractionConfig projectLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT);
		GraphRagExtractionConfig knowledgeBaseLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE);
		GraphRagExtractionConfig defaultLevelConfig = (GraphRagExtractionConfig) cache
				.get(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG);
		if (defaultLevelConfig == null) {
			List<GraphRagExtractionConfig> defaultConfigs = this.configRepository
					.findByDefaultConfiguration(Boolean.TRUE);
			if (!defaultConfigs.isEmpty()) {
				defaultLevelConfig = defaultConfigs.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG, defaultLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DEFAULT_CONFIG_CHECKED, "true");
		}

		String knowledgeBaseCode = docreference.getRootKnowledgebaseCode();
		String projectCode = docreference.getParentProjectCode();
		if (knowledgeBaseCode != null
				&& !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED)) {
			List<GraphRagExtractionConfig> forKnowledgeBase = this.configRepository
					.findByKnowledgeBaseCode(knowledgeBaseCode);
			for (GraphRagExtractionConfig kc : forKnowledgeBase) {
				if (kc.getProjectCode() == null && knowledgeBaseLevelConfig == null) {
					knowledgeBaseLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE, knowledgeBaseLevelConfig);
				}
				if (kc.getProjectCode() != null && projectCode != null && kc.getProjectCode().equals(projectCode)
						&& projectLevelConfig == null) {
					projectLevelConfig = kc;
					cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_PROJECT, projectLevelConfig);
				}
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_KNOWLEDGEBASE_AND_PROJECT_CHECKED, "true");
		}
		if (dataSourceLevelConfig == null && !cache.containsKey(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED)) {
			List<GraphRagExtractionConfig> forDataSource = this.configRepository.findByEndpointClassNameAndEndpointCode(
					docreference.getProjectEndpointReference().getClassName(),
					docreference.getProjectEndpointReference().getCode());
			if (!forDataSource.isEmpty()) {
				dataSourceLevelConfig = forDataSource.get(0);
				cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE, dataSourceLevelConfig);
			}
			cache.put(GRAPH_RAG_EXTRACTION_CONFIG_BY_DATASOURCE_CHECKED, "true");

		}
		if (dataSourceLevelConfig != null) {
			return getBatchingPredicate(dataSourceLevelConfig, cache);
		}
		if (projectLevelConfig != null) {
			return getBatchingPredicate(projectLevelConfig, cache);
		}
		if (knowledgeBaseLevelConfig != null) {
			return getBatchingPredicate(knowledgeBaseLevelConfig, cache);
		}
		if (defaultLevelConfig != null) {
			return getBatchingPredicate(defaultLevelConfig, cache);
		}
		throw new LLMConfigException("There is no available configuration for knowledge extraction");
	}

}
