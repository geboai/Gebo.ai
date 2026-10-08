package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelRuntimeConfigureHandler {
	private final IGPersistentObjectManager persistentManager;
	private final IGRuntimeBinder runtimeBinder;
	private final static Logger LOGGER = LoggerFactory.getLogger(ModelRuntimeConfigureHandler.class);

	@Transactional("mongoTransactionManager")
	public <ModelType extends GBaseModelConfig> OperationStatus<ModelType> insertAndConfigure(ModelType model,
			GModelType modelType) throws GeboPersistenceException, LLMConfigException {
		model = persistentManager.insert(model);
		try {
			return configureInserted(model);
		} catch (RuntimeException | GeboPersistenceException | LLMConfigException e) {
			// The runtime refused the model (invalid credentials, provider unreachable): a
			// configuration that could not be allocated is never kept, so the row inserted
			// above is removed before the error travels back to the caller. The surrounding
			// transaction cannot be relied on for this - Mongo leaves the document in place.
			try {
				persistentManager.delete(model);
			} catch (Throwable th) {
				LOGGER.error("Cannot remove the configuration that failed to be allocated", th);
			}
			throw e;
		}
	}

	private <ModelType extends GBaseModelConfig> OperationStatus<ModelType> configureInserted(ModelType model)
			throws GeboPersistenceException, LLMConfigException {
		if (model instanceof GBaseChatModelConfig chatModel) {
			IGChatModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGChatModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(chatModel);
			handleDefaultModel(GBaseChatModelConfig.class, chatModel, dao);
		}
		if (model instanceof GBaseEmbeddingModelConfig embeddingModel) {
			IGEmbeddingModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGEmbeddingModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(embeddingModel);
			handleDefaultModel(GBaseEmbeddingModelConfig.class, embeddingModel, dao);
		}
		if (model instanceof GBaseRankerModelConfig rankerConfig) {
			IGRankerModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGRankerModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(rankerConfig);
		}
		if (model instanceof GBaseTextToSpeachModelConfig ttsConfig) {
			IGTextToSpeechModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGTextToSpeechModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(ttsConfig);
			handleDefaultModel(GBaseTextToSpeachModelConfig.class, ttsConfig, dao);
		}
		if (model instanceof GBaseTranscriptModelConfig transcriptConfig) {
			IGTranscriptModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGTranscriptModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(transcriptConfig);
			handleDefaultModel(GBaseTranscriptModelConfig.class, transcriptConfig, dao);
		}
		if (model instanceof GBaseImageModelConfig imageConfig) {
			IGImageModelRuntimeConfigurationDao dao = runtimeBinder
					.getImplementationOf(IGImageModelRuntimeConfigurationDao.class);
			dao.addRuntimeByConfigClustered(imageConfig);
			handleDefaultModel(GBaseImageModelConfig.class, imageConfig, dao);
		}
		return OperationStatus.of(model);
	}

	/**
	 * The system roles of a chat model saved by a chat model configuration screen, each
	 * held by one model of the configured ones: the system default service model
	 * ({@link ChatModelsUses#INTERNAL_SERVICES}, not default) and the system default chat
	 * model ({@link ChatModelsUses#CHAT}, default).
	 */
	public void handleSystemChatModels(GBaseChatModelConfig config) throws GeboPersistenceException {
		handleDefaultModel(GBaseChatModelConfig.class, config,
				runtimeBinder.getImplementationOf(IGChatModelRuntimeConfigurationDao.class));
	}

	/**
	 * Keeps one default model of the kind and, for chat models, the two system roles
	 * unique: the system default chat model (uses CHAT, default) and the system default
	 * service model (uses INTERNAL_SERVICES, not default). When the given model takes a
	 * role, the other models holding it lose it; a chat model losing a role is a plain
	 * chat model (uses CHAT, not default). Two models with the service use left the
	 * runtime on the first one found, whatever the setup showed.
	 */
	protected void handleDefaultModel(Class<? extends GBaseModelConfig> configType, GBaseModelConfig config,
			IGRuntimeModelConfigurationDao<? extends IGConfigurableModel, ? extends GBaseModelConfig> dao)
			throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			takeRoleFromOthers(configType, config, dao, "default model",
					other -> other.getDefaultModel() != null && other.getDefaultModel(),
					ModelRuntimeConfigureHandler::toPlainModel);
		} else if (config instanceof GBaseChatModelConfig chatConfig && isServiceModel(chatConfig)) {
			takeRoleFromOthers(configType, config, dao, "system default service model",
					other -> other instanceof GBaseChatModelConfig otherChat && isServiceModel(otherChat),
					ModelRuntimeConfigureHandler::toPlainModel);
		}
	}

	private static boolean isServiceModel(GBaseChatModelConfig config) {
		return config.getForUses() != null && config.getForUses().contains(ChatModelsUses.INTERNAL_SERVICES);
	}

	/** A model without its system role: not default and, for a chat model, a plain chat model. */
	private static void toPlainModel(GBaseModelConfig config) {
		config.setDefaultModel(false);
		if (config instanceof GBaseChatModelConfig chatConfig) {
			chatConfig.setForUses(new ArrayList<>(List.of(ChatModelsUses.CHAT)));
		}
	}

	/**
	 * Takes a role from the other models of the kind holding it: each one is saved
	 * without it and its live model refreshed.
	 */
	private void takeRoleFromOthers(Class<? extends GBaseModelConfig> configType, GBaseModelConfig config,
			IGRuntimeModelConfigurationDao<? extends IGConfigurableModel, ? extends GBaseModelConfig> dao, String role,
			Predicate<GBaseModelConfig> holdsRole, Consumer<GBaseModelConfig> dropRole)
			throws GeboPersistenceException {
		List<? extends GBaseModelConfig> all = persistentManager.findAllExtendingType(configType);
		for (GBaseModelConfig other : all) {
			if (other.getClass().getName().equals(config.getClass().getName())
					&& other.getCode().equals(config.getCode())) {
				continue;
			}
			if (!holdsRole.test(other)) {
				continue;
			}
			dropRole.accept(other);
			persistentManager.update(other);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model " + other.getCode() + " is no longer the " + role + ": " + config.getCode()
						+ " is");
			}
			IGConfigurableModel model = dao.findByCode(other.getCode());
			if (model != null) {
				try {
					// The demoted model is refreshed with its OWN configuration - the one
					// just updated without the role. Handing it the incoming model's
					// config would push a foreign provider's type into it (and fail).
					model.reconfigure(other);
				} catch (LLMConfigException e) {
					LOGGER.error("Error in reconfigure a llm", e);
				}
			}
		}
	}
}
