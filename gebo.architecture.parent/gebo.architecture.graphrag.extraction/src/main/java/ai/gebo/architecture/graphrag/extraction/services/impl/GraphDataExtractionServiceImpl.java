package ai.gebo.architecture.graphrag.extraction.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.config.GraphRagExtractionStaticConfig;
import ai.gebo.architecture.graphrag.extraction.model.GraphEntityStandardType;
import ai.gebo.architecture.graphrag.extraction.model.GraphObjectType;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.repositories.GraphRagExtractionConfigRepository;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GraphDataExtractionServiceImpl implements IGraphDataExtractionService {
	private final IGChatModelRuntimeConfigurationDao chatModelsConfiguration;
	private final GraphRagExtractionStaticConfig staticConfig;
	private final GraphRagExtractionConfigRepository configRepository;

	@Override
	public LLMExtractionResult extract(Document document, GraphRagExtractionConfig configuration)
			throws LLMConfigException {
		String text = document.getMetadata() != null ? document.getMetadata().toString() + "\r\n" : "";
		text += document.getText();
		return extract(text, configuration);
	}

	@Override
	public LLMExtractionResult extract(String text, GraphRagExtractionConfig configuration) throws LLMConfigException {
		IGConfigurableChatModel chatModel = getChatModel(configuration);
		if (chatModel == null)
			throw new LLMConfigException("No configured extraction model");
		String formatSpecification = createFormatSpecification(configuration);
		String prompt = staticConfig.getExtractionConfig() != null
				? staticConfig.getExtractionConfig().getExtractionPrompt()
				: null;
		if (configuration != null && configuration.getExtractionPrompt() != null) {
			prompt = configuration.getExtractionPrompt();
		}
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add("format", formatSpecification);
		ChatClientRequestSpec requestSpec = chatModel.getChatClient().prompt(promptTemplate.create()).system(text);

		return requestSpec.call().entity(LLMExtractionResult.class);
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
	public LLMExtractionResult extract(Document document, GDocumentReference docreference) throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig dataSourceLevelConfig = null;
		GraphRagExtractionConfig projectLevelConfig = null;
		GraphRagExtractionConfig knowledgeBaseLevelConfig = null;
		GraphRagExtractionConfig defaultLevelConfig = null;
		List<GraphRagExtractionConfig> defaultConfigs = this.configRepository.findByDefaultConfiguration(Boolean.TRUE);
		if (!defaultConfigs.isEmpty()) {
			defaultLevelConfig = defaultConfigs.get(0);
		}
		List<GraphRagExtractionConfig> forDataSource = this.configRepository.findByEndpointClassNameAndEndpointCode(
				docreference.getProjectEndpointReference().getClassName(),
				docreference.getProjectEndpointReference().getCode());

		String knowledgeBaseCode = docreference.getRootKnowledgebaseCode();
		String projectCode = docreference.getParentProjectCode();
		if (knowledgeBaseCode != null) {
			List<GraphRagExtractionConfig> forKnowledgeBase = this.configRepository
					.findByKnowledgeBaseCode(knowledgeBaseCode);
			for (GraphRagExtractionConfig kc : forKnowledgeBase) {
				if (kc.getProjectCode() == null && knowledgeBaseLevelConfig == null) {
					knowledgeBaseLevelConfig = kc;
				}
				if (kc.getProjectCode() != null && projectCode != null && kc.getProjectCode().equals(projectCode)
						&& projectLevelConfig == null) {
					projectLevelConfig = kc;
				}
			}
		}
		if (!forDataSource.isEmpty()) {
			dataSourceLevelConfig = forDataSource.get(0);
		}

		return extract(document, mainConfiguration, defaultLevelConfig, knowledgeBaseLevelConfig, projectLevelConfig,
				dataSourceLevelConfig);
	}

	private LLMExtractionResult extract(Document document, GraphRagExtractionConfig mainConfiguration,
			GraphRagExtractionConfig defaultLevelConfig, GraphRagExtractionConfig knowledgeBaseLevelConfig,
			GraphRagExtractionConfig projectLevelConfig, GraphRagExtractionConfig dataSourceLevelConfig)
			throws LLMConfigException {
		if (dataSourceLevelConfig != null) {
			return extract(document, dataSourceLevelConfig);
		}
		if (projectLevelConfig != null) {
			return extract(document, projectLevelConfig);
		}
		if (knowledgeBaseLevelConfig != null) {
			return extract(document, knowledgeBaseLevelConfig);
		}
		if (defaultLevelConfig != null) {
			return extract(document, defaultLevelConfig);
		}
		// TODO: return to throw when no custom configs or default are there
		return extract(document, mainConfiguration);
		// throw new LLMConfigException("There is no available configuration for
		// knowledge extraction");
	}

	@Override
	public boolean isConfigured() {
		// TODO: return to uncomment
		// long count = this.configRepository.countByDefaultConfiguration(Boolean.TRUE);
		// return count > 0;
		return true;
	}

	@Override
	public LLMExtractionResult extract(String query, List<String> knowledgeBases) throws LLMConfigException {
		GraphRagExtractionConfig mainConfiguration = staticConfig.getExtractionConfig();
		GraphRagExtractionConfig defaultLevelConfig = null;
		List<GraphRagExtractionConfig> defaultConfigs = this.configRepository.findByDefaultConfiguration(Boolean.TRUE);
		if (!defaultConfigs.isEmpty()) {
			defaultLevelConfig = defaultConfigs.get(0);
		}
		GraphRagExtractionConfig  joinConfiguration=joinConfigurations(mainConfiguration,defaultLevelConfig,knowledgeBases);
		return extract(query, joinConfiguration);
	}

	private GraphRagExtractionConfig joinConfigurations(GraphRagExtractionConfig mainConfiguration,
			GraphRagExtractionConfig defaultLevelConfig, List<String> knowledgeBases) {
		//TODO: implement logics
		return mainConfiguration;	
	}

}
