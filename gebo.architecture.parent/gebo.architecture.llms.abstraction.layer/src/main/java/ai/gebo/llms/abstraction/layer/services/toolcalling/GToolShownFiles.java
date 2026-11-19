/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.toolcalling;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.architecture.ai.files.IGToolShownFiles;
import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;

/**
 * The files the tools of one model call asked to show to the model: collected while a
 * round of tool calls runs, handed to the model in a user message after the round's
 * results ({@link GeboToolCallingAdvisor}). Only what the model takes is accepted
 * ({@link ai.gebo.llms.abstraction.layer.model.GModelMediaGuard}), within a number
 * of files and of bytes a round.
 */
public final class GToolShownFiles implements IGToolShownFiles {
	/** The files a round of tool calls may show. */
	public static final int MAX_FILES_PER_ROUND = 6;
	/** The bytes a round of tool calls may show, all files together. */
	public static final long MAX_BYTES_PER_ROUND = 20L * 1024 * 1024;

	private final Predicate<String> modelTakes;
	private final List<Media> pending = new ArrayList<>();
	private final List<String> names = new ArrayList<>();
	private long pendingBytes = 0;

	/**
	 * @param modelTakes whether the model of the call takes a file of a MIME type
	 */
	public GToolShownFiles(Predicate<String> modelTakes) {
		this.modelTakes = modelTakes;
	}

	@Override
	public synchronized boolean show(String name, String mimeType, byte[] data) {
		final String type = GModelCarriedMediaTypes.essence(mimeType);
		if (type == null || data == null || data.length == 0 || !modelTakes.test(type)) {
			return false;
		}
		if (pending.size() >= MAX_FILES_PER_ROUND || pendingBytes + data.length > MAX_BYTES_PER_ROUND) {
			return false;
		}
		final String shownName = name != null && !name.isBlank() ? name : "file-" + (pending.size() + 1);
		pending.add(Media.builder().mimeType(MimeTypeUtils.parseMimeType("audio/mpeg".equals(type) ? "audio/mp3" : type))
				.data(data).name(shownName.replaceAll("[^A-Za-z0-9 \\-()\\[\\]]", "-")).build());
		names.add(shownName);
		pendingBytes += data.length;
		return true;
	}

	/**
	 * The message showing the files asked since the last one, or null when there are
	 * none. The files are handed over once.
	 */
	public synchronized UserMessage takeMessage() {
		if (pending.isEmpty()) {
			return null;
		}
		final UserMessage message = UserMessage.builder()
				.text("These are the files the tools just showed you: " + String.join(", ", names) + ".")
				.media(new ArrayList<>(pending)).build();
		pending.clear();
		names.clear();
		pendingBytes = 0;
		return message;
	}
}
