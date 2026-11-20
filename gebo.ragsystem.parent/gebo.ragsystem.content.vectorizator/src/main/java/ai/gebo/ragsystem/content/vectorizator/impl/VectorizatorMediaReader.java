/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ragsystem.content.vectorizator.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.cache.model.DocumentChunkType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ragsystem.content.vectorizator.config.VectorizatorMediaConfig;

/**
 * Turns the media documents of a document being vectorized into text
 * documents, with the models of this service: an image is described by a chat
 * model that reads pictures, a recording or a video transcribed by the default
 * transcription model. The text takes the media's metadata and is marked as
 * written by a model; a long one is split as texts are. What comes out is
 * embedded by the code that embeds the document's texts, unchanged.
 * <p>
 * A kind of media is read only while its switch is on
 * ({@code ai.gebo.vectorizator.embed-images} and its like) and a model for it is
 * configured; otherwise it is left out. A media that cannot be read is logged and
 * left out: the document's texts are vectorized all the same.
 */
@Service
public class VectorizatorMediaReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(VectorizatorMediaReader.class);
	/** The metadata telling a text was written by a model, and from what: description or transcript. */
	public static final String MODEL_WRITTEN_METADATA = "gebo_model_written";
	static final String DESCRIBE_SYSTEM_PROMPT = "You turn a picture into text for a search index. Write, in the language of the text the picture carries (English when it carries none): first every text you can read in it, as it is written and in its reading order, tables row by row; then what the picture shows - the kind of picture, what a chart or a diagram says with its figures, labels and relations. Write only what is in the picture, no introduction and no comment. If the picture is empty or cannot be made out, answer with nothing at all.";

	private final VectorizatorMediaConfig config;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModels;
	private final ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModels;

	public VectorizatorMediaReader(VectorizatorMediaConfig config,
			ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModels,
			ObjectProvider<IGTranscriptModelRuntimeConfigurationDao> transcriptModels) {
		this.config = config;
		this.chatModels = chatModels;
		this.transcriptModels = transcriptModels;
	}

	/**
	 * The chat model that describes images: the one the installation names, else the
	 * default one when it reads pictures, else the first configured one that does;
	 * null when none takes a PNG picture.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	IGConfigurableChatModel imageModel() {
		final IGChatModelRuntimeConfigurationDao dao = chatModels.getIfAvailable();
		if (dao == null) {
			return null;
		}
		try {
			if (config.getImageModelCode() != null && !config.getImageModelCode().isBlank()) {
				final IGConfigurableChatModel named = dao.findByCode(config.getImageModelCode().trim());
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
			LOGGER.warn("The chat models cannot be read to find one that describes images: " + unreadable);
		}
		return null;
	}

	IGConfigurableTranscriptModel transcriptModel() {
		final IGTranscriptModelRuntimeConfigurationDao dao = transcriptModels.getIfAvailable();
		try {
			return dao != null ? dao.defaultHandler() : null;
		} catch (RuntimeException unreadable) {
			return null;
		}
	}

	/** Whether media of this kind are read here now: its switch is on and a model for it is configured. */
	public boolean reads(DocumentChunkType kind) {
		if (kind == null) {
			return false;
		}
		switch (kind) {
		case IMAGE:
			return config.isEmbedImages() && imageModel() != null;
		case AUDIO:
			return config.isEmbedRecordings() && transcriptModel() != null;
		case VIDEO:
			return config.isEmbedVideos() && transcriptModel() != null;
		default:
			return false;
		}
	}

	/**
	 * The documents with every media document replaced by the text documents made
	 * of it, in its place; the text documents are returned as they are, the same
	 * objects. A list without media is returned itself.
	 */
	public List<Document> withMediaAsText(String documentName, List<Document> documents) {
		if (documents == null || documents.stream().allMatch(d -> d == null || d.isText())) {
			return documents;
		}
		final List<Document> out = new ArrayList<Document>();
		int read = 0;
		int left = 0;
		for (Document document : documents) {
			if (document == null || document.isText()) {
				out.add(document);
				continue;
			}
			final List<Document> texts = textOf(documentName, document);
			if (texts.isEmpty()) {
				left++;
			} else {
				read++;
				out.addAll(texts);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("withMediaAsText(" + documentName + ") media read:" + read + " left out:" + left);
		}
		return out;
	}

	/** The text documents made of a media document; none when it is not read or gives no text. */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	List<Document> textOf(String documentName, Document mediaDocument) {
		final Media media = mediaDocument.getMedia();
		final String type = media != null && media.getMimeType() != null ? media.getMimeType().toString() : null;
		final DocumentChunkType kind = DocumentChunkType.ofMediaMimeType(type);
		if (kind == null || !reads(kind)) {
			return List.of();
		}
		try {
			final byte[] data = media.getDataAsByteArray();
			if (data == null || data.length == 0 || data.length > config.maxMediaBytes()) {
				LOGGER.warn("A media of " + documentName + " (" + type + ", " + (data != null ? data.length : 0)
						+ " bytes) is empty or larger than " + config.getMaxMediaMb() + " MB: it is not read");
				return List.of();
			}
			final String text;
			final String written;
			if (kind == DocumentChunkType.IMAGE) {
				final IGConfigurableChatModel model = imageModel();
				if (!model.acceptsMedia(type)) {
					LOGGER.warn("An image of " + documentName + " is of type " + type + ", which model code="
							+ model.getCode() + " does not take: it is not read");
					return List.of();
				}
				final Prompt prompt = new Prompt(List.of(new SystemMessage(DESCRIBE_SYSTEM_PROMPT),
						UserMessage.builder().text("The picture, from the document " + documentName + ".")
								.media(Media.builder().mimeType(media.getMimeType()).data(data).name("picture").build())
								.build()));
				final ChatResponse response = (ChatResponse) model.doWithChatModel(chatModel -> chatModel.call(prompt));
				text = response != null && response.getResult() != null && response.getResult().getOutput() != null
						? response.getResult().getOutput().getText()
						: null;
				written = "description";
			} else {
				text = transcriptModel().call(new ByteArrayInputStream(data));
				written = "transcript";
			}
			if (text == null || text.trim().isEmpty()) {
				return List.of();
			}
			final Map<String, Object> metadata = new HashMap<String, Object>(
					mediaDocument.getMetadata() != null ? mediaDocument.getMetadata() : Map.of());
			metadata.put(MODEL_WRITTEN_METADATA, written);
			// a long text (a transcript) is split as the document's texts are; a short one is one piece
			final List<Document> pieces = new TokenTextSplitter()
					.split(new Document(mediaDocument.getId(), text.trim(), metadata));
			for (Document piece : pieces) {
				final String content = piece.getText() != null ? piece.getText() : "";
				piece.getMetadata().put(DocumentMetaInfos.GEBO_BYTES_LENGTH, content.length() * 2);
				piece.getMetadata().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, Math.max(1, content.length() / 4));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("A " + kind + " of " + documentName + " (" + type + ", " + data.length + " bytes) read as "
						+ written + ": " + text.length() + " character(s) in " + pieces.size() + " piece(s)");
			}
			return pieces;
		} catch (Exception unreadable) {
			LOGGER.error("A media of " + documentName + " (" + type + ") could not be read with a model: it is left out",
					unreadable);
			return List.of();
		}
	}
}
