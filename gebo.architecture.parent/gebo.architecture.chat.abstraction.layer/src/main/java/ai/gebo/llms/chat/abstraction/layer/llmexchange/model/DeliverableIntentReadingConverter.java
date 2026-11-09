/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.persistence.IGMongoConverter;

/**
 * Reads a saved {@link DeliverableIntent} by its name, and a name this version
 * no longer has (an intent removed since, like {@code PURE_SEARCH}) as
 * {@link DeliverableIntent#UNKNOWN}: the turns saved before stay readable, and
 * with them the conversation, instead of failing whoever loads it. The saved
 * name stays until the document is saved again.
 */
@Component
@ReadingConverter
public class DeliverableIntentReadingConverter implements Converter<String, DeliverableIntent>, IGMongoConverter {

	@Override
	public DeliverableIntent convert(String name) {
		try {
			return DeliverableIntent.valueOf(name);
		} catch (IllegalArgumentException removed) {
			return DeliverableIntent.UNKNOWN;
		}
	}
}
