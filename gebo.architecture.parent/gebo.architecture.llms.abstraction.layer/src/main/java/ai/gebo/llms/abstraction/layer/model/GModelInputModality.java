/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * What a chat model accepts as input: text always, and what else its provider says.
 * It characterizes the model only: Gebo does not yet give the models anything but
 * text.
 */
public enum GModelInputModality {
	TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT;

	/**
	 * A modality as the providers' model metadata name it - text, image, audio,
	 * video, file or pdf (a document) - case ignored; null for one it does not know.
	 */
	public static GModelInputModality fromProvider(String value) {
		if (value == null) {
			return null;
		}
		return switch (value.trim().toLowerCase(Locale.ROOT)) {
		case "text" -> TEXT;
		case "image", "images", "vision" -> IMAGE;
		case "audio", "speech", "voice" -> AUDIO;
		case "video" -> VIDEO;
		case "file", "files", "pdf", "document", "documents" -> DOCUMENT;
		default -> null;
		};
	}

	/**
	 * The modalities a provider's metadata lists, in this enum's order, text always
	 * among them; the ones it does not know are left out.
	 */
	public static List<GModelInputModality> fromProvider(Collection<String> values) {
		final Set<GModelInputModality> modalities = EnumSet.of(TEXT);
		if (values != null) {
			for (String value : values) {
				final GModelInputModality modality = fromProvider(value);
				if (modality != null) {
					modalities.add(modality);
				}
			}
		}
		return List.copyOf(modalities);
	}

	/** Text, and images when the model takes them: what a provider telling only "vision" says. */
	public static List<GModelInputModality> textAndImages(Boolean images) {
		return Boolean.TRUE.equals(images) ? List.of(TEXT, IMAGE) : List.of(TEXT);
	}
}
