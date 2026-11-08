/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.agents.conditionevents.services;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;

/**
 * How a component of the platform solves a condition a suspended network of
 * agents waits for: the event goes through the message broker to the events
 * layer's receiver, a channel trusted as infrastructure, so it is meant for code
 * running in the platform (a workflow step, a harness, a robot), never exposed as
 * is to the outside, where REST with the caller's credentials is the way in.
 * <p>
 * The delivery is asynchronous: the outcome is read afterwards through
 * {@link IConditionEventsService#status}.
 */
public interface IConditionEventsPublisher {
	/**
	 * Sends the event to the events layer.
	 *
	 * @param event      the condition solved, named by session code and condition
	 *                   id or by their composed correlation id
	 * @param onBehalfOf the account the component acts for, kept as the event's
	 *                   actor; may be null
	 * @return the id of the message sent
	 * @throws IllegalStateException when no receiver of condition events is
	 *                               reachable from this application
	 */
	public String publish(ConditionEvent event, String onBehalfOf);
}
