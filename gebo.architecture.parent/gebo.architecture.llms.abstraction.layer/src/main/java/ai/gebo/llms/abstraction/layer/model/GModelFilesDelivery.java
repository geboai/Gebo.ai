/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Whether, and how much, files go to the chat models as they are: one switch and one
 * set of limits for each kind of file (images, recordings, videos, other documents).
 * Every kind is off until the installation turns it on
 * ({@link ai.gebo.llms.abstraction.layer.services.config.ModelFilesDeliveryConfig}):
 * with a kind off no file of that kind reaches a model, neither with a request of a
 * conversation nor shown by a tool.
 */
public final class GModelFilesDelivery {
	/**
	 * The limits of one kind of file.
	 *
	 * @param enabled                whether files of the kind go to the models at all
	 * @param maxFiles               the files of the kind one request, or one round of
	 *                               tool calls, carries at most
	 * @param maxFileBytes           the size a file of the kind may have to be carried
	 * @param maxTotalBytes          the bytes of the kind carried at most, all together
	 * @param estimatedTokensPerFile the room kept in the model's context for each file
	 *                               of the kind: an estimate, no provider is asked
	 */
	public record Limits(boolean enabled, int maxFiles, long maxFileBytes, long maxTotalBytes,
			int estimatedTokensPerFile) {
		public static Limits off() {
			return new Limits(false, 0, 0, 0, 0);
		}

		/** From megabytes, as the settings state the sizes. */
		public static Limits of(boolean enabled, int maxFiles, int maxFileMb, int maxTotalMb,
				int estimatedTokensPerFile) {
			return new Limits(enabled, Math.max(0, maxFiles), Math.max(0, maxFileMb) * 1024L * 1024L,
					Math.max(0, maxTotalMb) * 1024L * 1024L, Math.max(0, estimatedTokensPerFile));
		}
	}

	private static volatile Map<GModelInputModality, Limits> current = new EnumMap<>(GModelInputModality.class);

	private GModelFilesDelivery() {
	}

	/** Sets the limits of every kind; a kind not given is off. */
	public static void set(Map<GModelInputModality, Limits> limits) {
		final Map<GModelInputModality, Limits> copy = new EnumMap<>(GModelInputModality.class);
		if (limits != null) {
			limits.forEach((kind, value) -> {
				if (kind != null && kind != GModelInputModality.TEXT && value != null) {
					copy.put(kind, value);
				}
			});
		}
		current = copy;
	}

	/** The limits as they are now, to set them back after a test changed them. */
	public static Map<GModelInputModality, Limits> get() {
		return new EnumMap<>(current);
	}

	/** The limits of a kind; never null, off for text and for an unknown kind. */
	public static Limits of(GModelInputModality kind) {
		final Limits limits = kind != null ? current.get(kind) : null;
		return limits != null ? limits : Limits.off();
	}

	/** The limits of the kind a MIME type, or a family of types such as image/*, belongs to. */
	public static Limits ofMimeType(String mimeType) {
		return of(kindOf(mimeType));
	}

	/** Whether files of the kind of this MIME type go to the models at all. */
	public static boolean delivers(String mimeType) {
		final Limits limits = ofMimeType(mimeType);
		return limits.enabled() && limits.maxFiles() > 0;
	}

	/** Whether files of any kind go to the models. */
	public static boolean deliversAnything() {
		return current.values().stream().anyMatch(x -> x.enabled() && x.maxFiles() > 0);
	}

	/** Of the MIME types a model takes, the ones whose kind is on. */
	public static List<String> delivered(List<String> mimeTypes) {
		return mimeTypes == null ? List.of() : mimeTypes.stream().filter(GModelFilesDelivery::delivers).toList();
	}

	/** The room to keep in a model's context for a file of this MIME type. */
	public static int estimatedTokens(String mimeType) {
		final Limits limits = ofMimeType(mimeType);
		return limits.enabled() ? limits.estimatedTokensPerFile() : 0;
	}

	private static GModelInputModality kindOf(String mimeType) {
		final GModelInputModality kind = GModelInputModality.ofMimeType(mimeType);
		return kind == GModelInputModality.TEXT ? null : kind;
	}

	/**
	 * The files admitted to one request, or to one round of tool calls: each kind within
	 * its own number of files and of bytes. Not thread safe: its owner guards it.
	 */
	public static final class Admission {
		private final Map<GModelInputModality, Integer> files = new EnumMap<>(GModelInputModality.class);
		private final Map<GModelInputModality, Long> bytes = new EnumMap<>(GModelInputModality.class);

		/**
		 * Admits a file when its kind is on and it stays within the limits of its kind;
		 * an admitted file counts for the following ones.
		 */
		public boolean admit(String mimeType, long size) {
			final GModelInputModality kind = kindOf(mimeType);
			final Limits limits = GModelFilesDelivery.of(kind);
			if (kind == null || !limits.enabled() || size < 0) {
				return false;
			}
			final int count = files.getOrDefault(kind, 0);
			final long total = bytes.getOrDefault(kind, 0L);
			if (count >= limits.maxFiles() || size > limits.maxFileBytes() || total + size > limits.maxTotalBytes()) {
				return false;
			}
			files.put(kind, count + 1);
			bytes.put(kind, total + size);
			return true;
		}
	}
}
