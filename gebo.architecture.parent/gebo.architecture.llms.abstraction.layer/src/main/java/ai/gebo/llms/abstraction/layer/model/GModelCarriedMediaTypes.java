/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * What a provider's integration can deliver to its models besides text: the MIME
 * types of the files a request may carry, by modality. It describes the integration,
 * not the model - what the model reads is its configuration's
 * {@link GBaseChatModelConfig#getInputModalities() inputModalities} - and a file is
 * sent only when both agree, see {@link GModelMediaGuard}.
 * <p>
 * A type is an exact MIME type ({@code application/pdf}) or a whole family
 * ({@code image/*}). An integration that states nothing carries nothing, so no file
 * ever reaches a provider nobody checked.
 */
public class GModelCarriedMediaTypes {
	private List<String> image = new ArrayList<>();
	private List<String> audio = new ArrayList<>();
	private List<String> video = new ArrayList<>();
	private List<String> document = new ArrayList<>();

	public GModelCarriedMediaTypes() {
	}

	/** An integration that carries no file. */
	public static GModelCarriedMediaTypes none() {
		return new GModelCarriedMediaTypes();
	}

	public GModelCarriedMediaTypes images(String... types) {
		image = List.of(types);
		return this;
	}

	public GModelCarriedMediaTypes audio(String... types) {
		audio = List.of(types);
		return this;
	}

	public GModelCarriedMediaTypes video(String... types) {
		video = List.of(types);
		return this;
	}

	public GModelCarriedMediaTypes documents(String... types) {
		document = List.of(types);
		return this;
	}

	/** The types carried for a modality; none for text and for an unknown one. */
	public List<String> typesOf(GModelInputModality modality) {
		if (modality == null) {
			return List.of();
		}
		final List<String> types = switch (modality) {
		case IMAGE -> image;
		case AUDIO -> audio;
		case VIDEO -> video;
		case DOCUMENT -> document;
		default -> null;
		};
		return types != null ? types : List.of();
	}

	/** Whether the integration carries a file of this MIME type. */
	public boolean carries(String mimeType) {
		final String type = essence(mimeType);
		return type != null && matchesAny(typesOf(GModelInputModality.ofMimeType(type)), type);
	}

	/** Whether the integration carries nothing at all. */
	public boolean carriesNothing() {
		return typesOf(GModelInputModality.IMAGE).isEmpty() && typesOf(GModelInputModality.AUDIO).isEmpty()
				&& typesOf(GModelInputModality.VIDEO).isEmpty() && typesOf(GModelInputModality.DOCUMENT).isEmpty();
	}

	/**
	 * A MIME type without its parameters, lower case: {@code Image/PNG; q=1} is
	 * {@code image/png}; null when it is not a type/subtype pair.
	 */
	public static String essence(String mimeType) {
		if (mimeType == null) {
			return null;
		}
		final int semicolon = mimeType.indexOf(';');
		final String type = (semicolon >= 0 ? mimeType.substring(0, semicolon) : mimeType).trim()
				.toLowerCase(Locale.ROOT);
		final int slash = type.indexOf('/');
		return slash > 0 && slash < type.length() - 1 ? type : null;
	}

	/** Whether a MIME type is one of the given ones, {@code family/*} standing for a family. */
	public static boolean matchesAny(Collection<String> types, String mimeType) {
		final String type = essence(mimeType);
		if (type == null || types == null) {
			return false;
		}
		for (String candidate : types) {
			if (candidate == null) {
				continue;
			}
			final String pattern = candidate.trim().toLowerCase(Locale.ROOT);
			if (pattern.equals(type)
					|| (pattern.endsWith("/*") && type.startsWith(pattern.substring(0, pattern.length() - 1)))) {
				return true;
			}
		}
		return false;
	}

	public List<String> getImage() {
		return image;
	}

	public void setImage(List<String> image) {
		this.image = image;
	}

	public List<String> getAudio() {
		return audio;
	}

	public void setAudio(List<String> audio) {
		this.audio = audio;
	}

	public List<String> getVideo() {
		return video;
	}

	public void setVideo(List<String> video) {
		this.video = video;
	}

	public List<String> getDocument() {
		return document;
	}

	public void setDocument(List<String> document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return "GModelCarriedMediaTypes[image=" + image + ", audio=" + audio + ", video=" + video + ", document="
				+ document + "]";
	}
}
