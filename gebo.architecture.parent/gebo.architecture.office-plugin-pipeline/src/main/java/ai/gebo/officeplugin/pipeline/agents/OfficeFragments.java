/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;

/**
 * Helpers around the office document fragments ({@code AdditionalContent}) the
 * user is editing: normalisation (drop empties, clamp size) and a single typed
 * accessor to the normalised list stored in the shared environment. Kept in one
 * place so the input step, the network streaming step and the input adapter all
 * agree on the same normalised view.
 */
public final class OfficeFragments {

	private OfficeFragments() {
	}

	/** Hard cap on characters per fragment kept in the shared context, defensive. */
	public static final int MAX_FRAGMENT_CHARS = 200_000;

	public static List<AdditionalContent> normalize(List<AdditionalContent> raw) {
		List<AdditionalContent> out = new ArrayList<>();
		if (raw == null) {
			return out;
		}
		for (AdditionalContent c : raw) {
			if (c == null || c.getContent() == null || c.getContent().isBlank()) {
				continue;
			}
			AdditionalContent copy = new AdditionalContent();
			copy.setName(c.getName() != null && !c.getName().isBlank() ? c.getName() : "fragment-" + (out.size() + 1));
			copy.setContentType(c.getContentType() != null && !c.getContentType().isBlank() ? c.getContentType()
					: OfficeDocumentStreamSplitter.DEFAULT_CONTENT_TYPE);
			String content = c.getContent();
			if (content.length() > MAX_FRAGMENT_CHARS) {
				content = content.substring(0, MAX_FRAGMENT_CHARS);
			}
			copy.setContent(content);
			out.add(copy);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public static List<AdditionalContent> fromEnvironment(Map<String, Object> environment) {
		if (environment == null) {
			return new ArrayList<>();
		}
		Object value = environment.get(OfficeAssistantConstants.OFFICE_DOCUMENT_FRAGMENTS);
		if (value instanceof List<?> list) {
			return (List<AdditionalContent>) list;
		}
		return new ArrayList<>();
	}
}
