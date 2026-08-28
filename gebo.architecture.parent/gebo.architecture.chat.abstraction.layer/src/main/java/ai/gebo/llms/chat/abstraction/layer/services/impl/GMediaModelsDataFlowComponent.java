/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.model.base.GeboComponentInfo;

/**
 * Symbolic reporter for the media model uses - speech-to-text (transcript),
 * text-to-speech and image generation.
 *
 * <p>
 * The standard chat pipeline reporter already surfaces the chat, embedding and
 * ranker models a query is sent to. The transcript, text-to-speech and image
 * models are a separate set of user-facing flows that equally send user content
 * to a - usually external - provider: a recorded voice to a speech-to-text
 * model, a prompt to an image model, text to a voice. For a GDPR Art. 30 / Art.
 * 44 review those are third parties that see user content and must appear in the
 * register too, so this component reports each configured default media model as
 * its own endpoint, located by the base URL the content is actually sent to.
 * </p>
 *
 * <p>
 * Like the chat pipeline reporter this is a pure reporting component: it emits no
 * real traffic and exists only to be picked up by {@code
 * MessageBrokeringAssembler} and answer {@link #getDataFlowMetaInfos()}. Every
 * DAO is resolved lazily through an {@code ObjectProvider} so the reporter never
 * pulls the model subsystem into its own construction graph, and each use is
 * reported only when a default model for it is actually configured.
 * </p>
 */
@Component
public class GMediaModelsDataFlowComponent implements IGMessageEmitter {

	public static final String MEDIA_SERVICES_MODULE = "media-services-module";
	public static final String MEDIA_MODELS_COMPONENT = "media-models";

	private final ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModelsDaoProvider;
	private final ObjectProvider<IGTextToSpeechModelRuntimeConfigurationDao> textToSpeechModelsDaoProvider;
	private final ObjectProvider<IGImageModelRuntimeConfigurationDao> imageModelsDaoProvider;

	public GMediaModelsDataFlowComponent(
			@Autowired ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModelsDaoProvider,
			@Autowired ObjectProvider<IGTextToSpeechModelRuntimeConfigurationDao> textToSpeechModelsDaoProvider,
			@Autowired ObjectProvider<IGImageModelRuntimeConfigurationDao> imageModelsDaoProvider) {
		this.transcriptModelsDaoProvider = transcriptModelsDaoProvider;
		this.textToSpeechModelsDaoProvider = textToSpeechModelsDaoProvider;
		this.imageModelsDaoProvider = imageModelsDaoProvider;
	}

	@Override
	public String getMessagingModuleId() {
		return MEDIA_SERVICES_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return MEDIA_MODELS_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		// Symbolic: it never emits real traffic, it only reports its data flows.
		return List.of();
	}

	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		boolean any = false;
		any |= addMediaFlow(flow, "transcript", "Audio transcription request (user speech)", "Speech-to-text model",
				"Speech-to-text", defaultConfig(transcriptModelsDaoProvider));
		any |= addMediaFlow(flow, "tts", "Speech synthesis request (text)", "Text-to-speech model", "Text-to-speech",
				defaultConfig(textToSpeechModelsDaoProvider));
		any |= addMediaFlow(flow, "image", "Image generation prompt", "Image generation model", "Image generation",
				defaultConfig(imageModelsDaoProvider));

		return any ? flow : null;
	}

	/**
	 * Resolves the default model configuration for one media use, tolerating both an
	 * absent DAO (provider disabled) and a repository that has no default yet.
	 */
	private GBaseModelConfig defaultConfig(ObjectProvider<? extends ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao<?, ?>> daoProvider) {
		ai.gebo.llms.abstraction.layer.services.IGRuntimeModelConfigurationDao<?, ?> dao = daoProvider.getIfAvailable();
		if (dao == null) {
			return null;
		}
		try {
			ai.gebo.llms.abstraction.layer.services.IGConfigurableModel<?, ?> handler = dao.defaultHandler();
			return handler != null ? handler.getConfig() : null;
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * Adds one media use to the flow: a user-input node feeding the provider model.
	 * Returns whether the use was configured (and therefore reported).
	 */
	private boolean addMediaFlow(GDataFlowMetaInfos flow, String key, String inputDescription, String modelLabel,
			String engineDescription, GBaseModelConfig config) {
		if (config == null) {
			return false;
		}

		DataEndpoint input = new DataEndpoint();
		input.setId("media-input-" + key);
		input.setDescription(inputDescription);
		input.setProduct("User media request");
		input.setEndpoint("media-request", key, null, null);
		input.setInput(true);
		input.setTypes(list(MetaEndpointType.CHAT_SESSION));
		input.setPersonalData(false);
		input.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
		flow.getDataEndpoints().add(input);

		DataEndpoint model = new DataEndpoint();
		model.setId("media-model-" + key);
		model.setDescription(describeModel(modelLabel, config));
		model.setProduct(providerOf(config, modelLabel));
		model.setEndpoint(config.getBaseUrl());
		model.setTypes(list(MetaEndpointType.LLM_ENDPOINT));
		model.setInput(true);
		model.setOutput(true);
		model.setPersonalData(false);
		if (notEmpty(config.getApiSecretCode())) {
			model.setSecretReference(config.getApiSecretCode());
		}
		model.setLocality(localityOf(model.getEndpoint()));
		flow.getDataEndpoints().add(model);

		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(key + "-engine", engineDescription,
				list(MetaEndpointType.CHAT_SESSION), list(MetaEndpointType.LLM_ENDPOINT));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.of(key + "-flow", engineDescription, engine,
				flow.qualifiedId(input.getId()), flow.qualifiedId(model.getId())));
		return true;
	}

	private DataEndpointLocality localityOf(String locator) {
		DataEndpointLocality hint = DataEndpointLocality.hintFromLocator(locator);
		return hint == DataEndpointLocality.LOCAL_DEPLOYMENT ? DataEndpointLocality.LOCAL_DEPLOYMENT
				: DataEndpointLocality.EXTERNAL_PROVIDER;
	}

	private String providerOf(GBaseModelConfig config, String fallback) {
		GBaseModelChoice choice = config.getChoosedModel();
		if (choice != null && choice.getMetaInfos() != null && notEmpty(choice.getMetaInfos().getProviderId())) {
			return choice.getMetaInfos().getProviderId();
		}
		return notEmpty(config.getModelTypeCode()) ? config.getModelTypeCode() : fallback;
	}

	private String describeModel(String prefix, GBaseModelConfig config) {
		GBaseModelChoice choice = config.getChoosedModel();
		String modelName = choice != null ? choice.getCode() : null;
		return notEmpty(modelName) ? prefix + " (" + modelName + ")" : prefix;
	}

	private static boolean notEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}

	private static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new ArrayList<MetaEndpointType>(List.of(types));
	}
}
