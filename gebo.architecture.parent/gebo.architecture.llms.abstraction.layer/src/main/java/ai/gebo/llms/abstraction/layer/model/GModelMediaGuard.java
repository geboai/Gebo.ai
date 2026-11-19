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
import java.util.List;

/**
 * The one place deciding whether a file may go to a model as media: only when the
 * model's configuration declares the file's modality <b>and</b> the provider's
 * integration carries its MIME type. Everything else stays out of the request, with
 * the reason, so that no call fails on a type its integration rejects, no file is
 * dropped silently, and none reaches a provider as something it is not.
 */
public final class GModelMediaGuard {
	private GModelMediaGuard() {
	}

	/** Why a file goes, or does not go, to a model. */
	public static enum Outcome {
		/** The model declares the modality and the integration carries the type. */
		SENT,
		/** The model's configuration does not declare the file's modality. */
		MODEL_DOES_NOT_READ_IT,
		/** The model declares the modality, the provider's integration does not carry the type. */
		INTEGRATION_DOES_NOT_CARRY_IT,
		/** The MIME type is unknown or unreadable. */
		UNKNOWN_TYPE
	}

	/** What the guard decides of one file. */
	public static Outcome decide(List<GModelInputModality> declared, GModelCarriedMediaTypes carried,
			String mimeType) {
		final String type = GModelCarriedMediaTypes.essence(mimeType);
		if (type == null) {
			return Outcome.UNKNOWN_TYPE;
		}
		final GModelInputModality modality = GModelInputModality.ofMimeType(type);
		if (declared == null || !declared.contains(modality)) {
			return Outcome.MODEL_DOES_NOT_READ_IT;
		}
		if (carried == null || !carried.carries(type)) {
			return Outcome.INTEGRATION_DOES_NOT_CARRY_IT;
		}
		return Outcome.SENT;
	}

	/** Whether a file of this type may be attached to a request of the model. */
	public static boolean sends(List<GModelInputModality> declared, GModelCarriedMediaTypes carried,
			String mimeType) {
		return decide(declared, carried, mimeType) == Outcome.SENT;
	}

	/**
	 * The MIME types (exact ones or {@code family/*}) a model effectively takes: those
	 * its integration carries for the modalities its configuration declares. Empty for
	 * a text only model and for an integration that carries nothing.
	 */
	public static List<String> effectiveMimeTypes(List<GModelInputModality> declared,
			GModelCarriedMediaTypes carried) {
		final List<String> types = new ArrayList<>();
		if (declared == null || carried == null) {
			return types;
		}
		for (GModelInputModality modality : GModelInputModality.values()) {
			if (declared.contains(modality)) {
				for (String type : carried.typesOf(modality)) {
					if (type != null && !types.contains(type)) {
						types.add(type);
					}
				}
			}
		}
		return types;
	}

	/**
	 * The declared modalities the integration carries nothing for: what a
	 * configuration says the model reads and Gebo cannot deliver through this provider.
	 */
	public static List<GModelInputModality> declaredNotCarried(List<GModelInputModality> declared,
			GModelCarriedMediaTypes carried) {
		final List<GModelInputModality> missing = new ArrayList<>();
		if (declared == null) {
			return missing;
		}
		for (GModelInputModality modality : declared) {
			if (modality != null && modality != GModelInputModality.TEXT
					&& (carried == null || carried.typesOf(modality).isEmpty()) && !missing.contains(modality)) {
				missing.add(modality);
			}
		}
		return missing;
	}
}
