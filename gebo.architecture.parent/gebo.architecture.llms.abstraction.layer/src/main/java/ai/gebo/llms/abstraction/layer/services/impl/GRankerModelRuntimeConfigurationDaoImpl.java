package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
@Service
public class GRankerModelRuntimeConfigurationDaoImpl extends GAbstractRuntimeConfigurationDao<IGConfigurableRankerModel>
		implements IGRankerModelRuntimeConfigurationDao, ApplicationListener<ContextRefreshedEvent> {
	// AI generated comments
	// Logger for this class
	static final Logger LOGGER = LoggerFactory.getLogger(GRankerModelRuntimeConfigurationDaoImpl.class);

	// ObjectMapper for handling JSON processing
	static final ObjectMapper mapper = new ObjectMapper();

	// Repository pattern for managing embedding model configuration support
	// services

	private final IGRankerModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;

	// Manager for persistent object operations
	private final IGPersistentObjectManager persistentObjectManager;

	public GRankerModelRuntimeConfigurationDaoImpl(
			IGRankerModelConfigurationSupportServiceRepositoryPattern supportRepoPattern,
			IGPersistentObjectManager persistentObjectManager) {

		super(new ArrayList<>(), null);
		this.supportRepoPattern = supportRepoPattern;
		this.persistentObjectManager = persistentObjectManager;

	}

	@Override
	public void add(IGConfigurableRankerModel element) {
		this.staticConfigs.add(element);

	}

	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		IGConfigurableRankerModel item = this.findByCode(code);
		staticConfigs.remove(item);
		item.delete();

	}

	@Override
	public void addRuntimeByConfig(GBaseRankerModelConfig config) throws LLMConfigException {
		IGRankerModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
			return x.getType().getCode().equals(config.getModelTypeCode());
		});
		if (handler == null) {
			LOGGER.error("Received in configuration a rerank model with type=>" + config.getModelTypeCode()
					+ " that is not found");
			throw new LLMConfigException("Cannot find handler for code=>" + config.getModelTypeCode());
		} else {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Initializing reranker with configuration:" + mapper.writeValueAsString(config));
				}
			} catch (JsonProcessingException e) {
				// Log parsing exception if necessary
			}
			IGConfigurableRankerModel imageModel = handler.create(config);
			LOGGER.info("Initialized reranker successfully");
			this.staticConfigs.add(imageModel);
		}
	}

	@Override
	public IGConfigurableRankerModel findByCode(String code) {
		return this.findByPredicate(x -> {
			return x.getCode() != null && code != null && x.getCode().equals(code);
		});
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Begin initializing image models dinamically");
		try {
			// Retrieve all configurations extending GBaseChatModelConfig
			List<GBaseRankerModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseRankerModelConfig.class);
			for (GBaseRankerModelConfig config : configs) {
				this.addRuntimeByConfig(config);
			}
		} catch (GeboPersistenceException | LLMConfigException e) {
			String msg = "FATAL RERANK MODELS INITIALIZATION EXCEPTION";
			LOGGER.error(msg, e);
			throw new RuntimeException(msg, e);
		}

		LOGGER.info("End initializing image models dinamically");

	}

}
