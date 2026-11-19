/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.IngestionMediaConfig;
import ai.gebo.system.ingestion.IGMediaTextExtractor;

/**
 * Describes pictures with a chat model that reads them and transcribes recordings
 * with the default transcription model, for the readers of the knowledge bases
 * ({@code ai.gebo.ingestion.media}).
 */
@Service
public class GMediaTextExtractorImpl implements IGMediaTextExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GMediaTextExtractorImpl.class);
	static final String DESCRIBE_SYSTEM_PROMPT = "You turn a picture into text for a search index. Write, in the language of the text the picture carries (English when it carries none): first every text you can read in it, as it is written and in its reading order, tables row by row; then what the picture shows - the kind of picture, what a chart or a diagram says with its figures, labels and relations. Write only what is in the picture, no introduction and no comment. If the picture is empty or cannot be made out, answer with nothing at all.";
	private final IngestionMediaConfig config;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModels;
	private final ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModels;

	public GMediaTextExtractorImpl(IngestionMediaConfig config,
			ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModels,
			ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModels) {
		this.config = config;
		this.chatModels = chatModels;
		this.transcriptModels = transcriptModels;
	}

	/**
	 * The chat model that describes pictures: the one the installation names, else the
	 * default one when it reads pictures, else the first configured one that does. Null
	 * when none takes a PNG picture.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	IGConfigurableChatModel pictureModel() {
		final IGChatModelRuntimeConfigurationDao dao = chatModels.getIfAvailable();
		if (dao == null) {
			return null;
		}
		try {
			if (config.getPictureModelCode() != null && !config.getPictureModelCode().isBlank()) {
				final IGConfigurableChatModel named = dao.findByCode(config.getPictureModelCode().trim());
				return named != null && named.acceptsMedia("image/png") ? named : null;
			}
			final IGConfigurableChatModel byDefault = dao.defaultHandler();
			if (byDefault != null && byDefault.acceptsMedia("image/png")) {
				return byDefault;
			}
			final List<IGConfigurableChatModel> all = dao.getConfigurations();
			if (all != null) {
				for (IGConfigurableChatModel model : all) {
					if (model != null && model.acceptsMedia("image/png")) {
						return model;
					}
				}
			}
		} catch (RuntimeException unreadable) {
			LOGGER.warn("The chat models cannot be read to find one that describes pictures: " + unreadable);
		}
		return null;
	}

	@Override
	public boolean describesPictures() {
		return config.isDescribePictures() && pictureModel() != null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String describePicture(String name, String mimeType, byte[] data) throws IOException {
		final IGConfigurableChatModel model = pictureModel();
		final String type = GModelCarriedMediaTypes.essence(mimeType);
		if (model == null || type == null || data == null || data.length == 0 || !model.acceptsMedia(type)) {
			return null;
		}
		final Prompt prompt = new Prompt(List.of(new SystemMessage(DESCRIBE_SYSTEM_PROMPT),
				UserMessage.builder().text("The picture" + (name != null ? " " + name : "") + ".")
						.media(Media.builder().mimeType(MimeTypeUtils.parseMimeType(type)).data(data).name("picture")
								.build())
						.build()));
		try {
			final Object answered = model.doWithChatModel(chatModel -> chatModel.call(prompt));
			final ChatResponse response = (ChatResponse) answered;
			final String text = response != null && response.getResult() != null
					&& response.getResult().getOutput() != null ? response.getResult().getOutput().getText() : null;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("describePicture(" + name + ", " + type + ", " + data.length + " bytes) by model code="
						+ model.getCode() + " -> " + (text != null ? text.length() : 0) + " character(s)");
			}
			return text;
		} catch (LLMConfigException e) {
			throw new IOException("The picture " + name + " could not be described", e);
		}
	}

	@Override
	public int maxDescribedPagesPerDocument() {
		return config.getMaxDescribedPagesPerDocument();
	}

	@Override
	public long maxFileBytes() {
		return config.getMaxFileBytes();
	}

	private IGConfigurableTranscriptModel transcriptModel() {
		final IGTranscriptModelRuntimeConfigurationDao dao = transcriptModels.getIfAvailable();
		try {
			return dao != null ? dao.defaultHandler() : null;
		} catch (RuntimeException unreadable) {
			return null;
		}
	}

	@Override
	public boolean transcribes() {
		return config.isTranscribeRecordings() && transcriptModel() != null;
	}

	@Override
	public String transcribe(String name, String mimeType, InputStream content) throws IOException {
		final IGConfigurableTranscriptModel model = transcriptModel();
		if (model == null || content == null) {
			return null;
		}
		try {
			final String text = model.call(content);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("transcribe(" + name + ", " + mimeType + ") by model code=" + model.getCode() + " -> "
						+ (text != null ? text.length() : 0) + " character(s)");
			}
			return text;
		} catch (LLMConfigException e) {
			throw new IOException("The recording " + name + " could not be transcribed", e);
		}
	}
}
