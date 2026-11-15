/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.messaging;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;

/** An event solving a condition, carried by the message broker. */
public class ConditionEventPayload extends GBaseMessagePayload {
	private static final long serialVersionUID = 1L;
	private ConditionEvent event = null;

	public ConditionEventPayload() {
	}

	public ConditionEventPayload(ConditionEvent event) {
		this.event = event;
	}

	public ConditionEvent getEvent() {
		return event;
	}

	public void setEvent(ConditionEvent event) {
		this.event = event;
	}
}
