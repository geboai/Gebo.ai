package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelRuntimeConfigureHandler {
	private final IGPersistentObjectManager persistentManager;
	private final IGRuntimeBinder runtimeBinder;
	private final static Logger LOGGER = LoggerFactory.getLogger(ModelRuntimeConfigureHandler.class);

	@Transactional
	public <ModelType extends GBaseModelConfig> OperationStatus<ModelType> insertAndConfigure(ModelType model,
			GModelType modelType) throws GeboPersistenceException, LLMConfigException {
		model = persistentManager.insert(model);
		if (model instanceof GBaseChatModelConfig chatModel) {
			IGChatModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGChatModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfig(chatModel);
			handleDefaultModel(GBaseChatModelConfig.class, chatModel, dao);
		}
		if (model instanceof GBaseEmbeddingModelConfig embeddingModel) {
			IGEmbeddingModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGEmbeddingModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfig(embeddingModel);
			handleDefaultModel(GBaseEmbeddingModelConfig.class, embeddingModel, dao);
		}

		return OperationStatus.of(model);
	}

	protected void handleDefaultModel(Class<? extends GBaseModelConfig> configType, GBaseModelConfig config,
			IGRuntimeModelConfigurationDao<? extends IGConfigurableModel> dao) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<? extends GBaseModelConfig> all = persistentManager.findAllExtendingType(configType);
			for (GBaseModelConfig gBaseChatModelConfig : all) {
				if (!(gBaseChatModelConfig.getClass().getName().equals(config.getClass().getName())
						&& gBaseChatModelConfig.getCode().equals(config.getCode()))) {
					if (gBaseChatModelConfig.getDefaultModel() != null && gBaseChatModelConfig.getDefaultModel()) {
						gBaseChatModelConfig.setDefaultModel(false);
						persistentManager.update(gBaseChatModelConfig);
						IGConfigurableModel model = dao.findByCode(gBaseChatModelConfig.getCode());
						try {
							model.reconfigure(config);
						} catch (LLMConfigException e) {
							LOGGER.error("Error in reconfigure a llm", e);
						}
					}
				}
			}
		}
	}
}
