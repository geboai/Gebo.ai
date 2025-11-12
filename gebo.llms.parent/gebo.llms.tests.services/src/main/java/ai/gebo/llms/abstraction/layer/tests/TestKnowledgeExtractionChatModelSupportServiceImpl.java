/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.tests;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Service implementation for testing chat model configuration support. This
 * class provides a test implementation of the
 * IGChatModelConfigurationSupportService interface used to test the chat model
 * abstraction layer.
 */
@Service
public class TestKnowledgeExtractionChatModelSupportServiceImpl extends AbstractTestingBusinessLogic implements
		IGChatModelConfigurationSupportService<GBaseChatModelChoice, TestKnowledgeExtractionModelConfiguration> {
	/** Constant identifier for the test model */
	public static final String TEST_KNOWLEDGE_EXTRACTION_MODEL_001 = "TEST-KNOWLEDGE-EXTRACTION-MODEL-001";
	/** Constant identifier for the test configurable chat model service */
	public static final String TEST_CONFIGURABLE_KNOWLEDGE_EXTRACTION_CHAT_MODEL_SERVICE = "TEST-CONFIGURABLE-KNOWLEDGE-EXTRACTION-CHAT-MODEL-SERVICE";
	/** Chat model type instance used for testing */
	static final GChatModelType type = new GChatModelType();
	/** Base chat model choice instance used for testing */
	static final GBaseChatModelChoice model = new GBaseChatModelChoice();

	/**
	 * Inner class representing a test implementation of the configurable chat
	 * model. Extends the abstract class to provide test-specific configuration.
	 */
	static class TestConfigurableKnowledgeExtractionChatModel extends
			GAbstractConfigurableChatModel<TestKnowledgeExtractionModelConfiguration, TestKnowledgeExtractionChatModel> {

		/**
		 * Configures a test chat model with the provided configuration.
		 * 
		 * @param config The test chat model configuration to apply
		 * @param type   The type of chat model
		 * @return A configured test chat model instance
		 * @throws LLMConfigException If there's an error during configuration
		 */
		@Override
		protected TestKnowledgeExtractionChatModel configureModel(TestKnowledgeExtractionModelConfiguration config,
				GChatModelType type) throws LLMConfigException {
			TestKnowledgeExtractionChatModel m = new TestKnowledgeExtractionChatModel();
			m.setConfiguration(config);
			return m;
		}

		@Override
		public ChatClient getChatClient() {
			ChatClient wrapped = super.getChatClient();
			TestKnowledgeExtractionChatClientWrapper client = new TestKnowledgeExtractionChatClientWrapper(wrapped,
					this.config);
			return client;
		}

		@Override
		public int getContextLength() {

			return 8192;
		}

	};

	/**
	 * Static initializer block to set up the test model type and model choice
	 */
	static {
		type.setCode(TEST_CONFIGURABLE_KNOWLEDGE_EXTRACTION_CHAT_MODEL_SERVICE);
		model.setCode(TEST_KNOWLEDGE_EXTRACTION_MODEL_001);

	}

	/**
	 * Default constructor for the test chat model support service
	 */
	public TestKnowledgeExtractionChatModelSupportServiceImpl() {

	}

	/**
	 * Returns the type of chat model supported by this service
	 * 
	 * @return The chat model type
	 */
	@Override
	public GChatModelType getType() {

		return type;
	}

	/**
	 * Retrieves the available model choices for a given configuration
	 * 
	 * @param config The test chat model configuration
	 * @return An OperationStatus containing a list of available chat model choices
	 */
	@Override
	public OperationStatus<List<GBaseChatModelChoice>> getModelChoices(
			TestKnowledgeExtractionModelConfiguration config) {

		return OperationStatus.of(List.of(model));
	}

	/**
	 * Creates a base configuration for the specified preset model
	 * 
	 * @param presetModel The identifier of the preset model
	 * @return A new TestChatModelConfiguration with default settings
	 */
	@Override
	public TestKnowledgeExtractionModelConfiguration createBaseConfiguration(String presetModel) {
		TestKnowledgeExtractionModelConfiguration c = new TestKnowledgeExtractionModelConfiguration();
		c.setModelTypeCode(type.getCode());
		c.setChoosedModel(model);
		return c;
	}

	/**
	 * Creates a configurable chat model instance with the provided configuration
	 * 
	 * @param config The configuration to apply to the new model
	 * @return A configured chat model implementation
	 * @throws LLMConfigException If there's an error during model creation or
	 *                            configuration
	 */
	@Override
	public IGConfigurableChatModel<TestKnowledgeExtractionModelConfiguration> create(
			TestKnowledgeExtractionModelConfiguration config) throws LLMConfigException {
		TestConfigurableKnowledgeExtractionChatModel out = new TestConfigurableKnowledgeExtractionChatModel();
		out.initialize(config, type);
		return out;
	}

	@Override
	public OperationStatus<TestKnowledgeExtractionModelConfiguration> insertAndConfigure(
			TestKnowledgeExtractionModelConfiguration config) throws GeboPersistenceException, LLMConfigException {
		// TODO Auto-generated method stub
		return null;
	}

}