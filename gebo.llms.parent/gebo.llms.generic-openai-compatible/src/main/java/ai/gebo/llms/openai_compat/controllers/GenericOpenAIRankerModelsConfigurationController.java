package ai.gebo.llms.openai_compat.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.controllers.AbstractRankerModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;

import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIRankerModelTypeConfig;
import ai.gebo.llms.openai_compat.services.GenericOpenAIAPIRankerModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenerigOpenAIRankerModelsConfigurationController")
public class GenericOpenAIRankerModelsConfigurationController extends
		AbstractRankerModelsConfigurationCRUDController<GenericOpenAIAPIRankerModelConfig, GenericOpenAIAPIRankerModelChoice> {
	private final IGRankerModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern;
	private final GenericOpenAICompatibleProvidersConfig config;

	public GenericOpenAIRankerModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGRankerModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			IGRankerModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern,
			GenericOpenAICompatibleProvidersConfig config) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GenericOpenAIAPIRankerModelConfig.class);
		this.supportServiceRepoPattern = supportServiceRepoPattern;
		this.config = config;
	}

	@Override
	protected OperationStatus<List<GenericOpenAIAPIRankerModelChoice>> getModelChoices(
			GenericOpenAIAPIRankerModelConfig cfg) {
		if (cfg.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGRankerModelConfigurationSupportService handler = supportServiceRepoPattern.findByCode(cfg.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + cfg.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(cfg);

	}

	/**
	 * Retrieves the list of available OpenAI-compatible ranker model types
	 * 
	 * @return List of ranker model type configurations
	 */
	@GetMapping(value = "getGenericOpenAIRankerModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIRankerModelTypeConfig> getGenericOpenAIRankerModelTypes() {
		return config.getRankerModelProviders();
	}
	@GetMapping(value = "getGenericOpenAIRankerModelConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIAPIRankerModelConfig> getGenericOpenAIRankerModelConfigs() throws GeboPersistenceException {
		return this.persistentObjectManager.findAll(GenericOpenAIAPIRankerModelConfig.class);
	}

	/**
	 * Creates a new OpenAI-compatible ranker model configuration
	 * 
	 * @param config The configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertGenericOpenAIAPIRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIRankerModelConfig> insertGenericOpenAIAPIRankerModelConfig(
			@RequestBody GenericOpenAIAPIRankerModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI-compatible ranker model configuration
	 * 
	 * @param config The configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateGenericOpenAIAPIRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIRankerModelConfig> updateGenericOpenAIAPIRankerModelConfig(
			@RequestBody GenericOpenAIAPIRankerModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI-compatible ranker model configuration
	 * 
	 * @param config The configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteGenericOpenAIAPIRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPIRankerModelConfig(
			@RequestBody GenericOpenAIAPIRankerModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI-compatible ranker model configuration by its code
	 * 
	 * @param code The unique code identifier for the configuration
	 * @return The matching configuration if found
	 * @throws GeboPersistenceException If there's an issue retrieving the
	 *                                  configuration
	 */
	@GetMapping(value = "findGenericOpenAIAPIRankerModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPIRankerModelConfig findGenericOpenAIAPIRankerModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available ranker models based on the provided configuration
	 * 
	 * @param config The configuration to use as filter
	 * @return Operation status with list of available ranker model choices
	 */
	@PostMapping(value = "getGenericOpenAIAPIRankerModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPIRankerModelChoice>> getGenericOpenAIAPIRankerModels(
			@RequestBody GenericOpenAIAPIRankerModelConfig config) {
		return getModelChoices(config);
	}

}
