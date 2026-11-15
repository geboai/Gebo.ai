/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsSessionCreationContext;

/**
 * Builds the collaboration session a run of a network of agents happens in, and
 * is told when that run has ended.
 * <p>
 * Without one, a network builds a session of its own for every run: a fresh
 * identity, no past, and nothing left of it once the run returns. That is the
 * right default, and it stays the default.
 * <p>
 * A caller that lives longer than a single run supplies one of these instead, and
 * can then:
 * <ul>
 * <li><b>give the session its own identity</b>, so the run — and every message
 * the agents exchange in it, which carries
 * {@code AgentsExchangeMessage.getCollaborationContextId()} — is traceable to
 * something the caller knows;</li>
 * <li><b>give the session a past</b>, by replaying earlier contributions into it,
 * so the agents read them in their shared context and genuinely continue rather
 * than start over;</li>
 * <li><b>watch the collaboration as it happens</b>, through
 * {@link AgentsCollaborationSessionContext#setContributionListener}, instead of
 * only seeing the final output;</li>
 * <li><b>keep the session</b> once the run is over, through
 * {@link #sessionCompleted}.</li>
 * </ul>
 * Supplied to a network service through
 * {@link IGAgentsNetworkServiceFactory#create(ai.gebo.architecture.agents.model.GAgentsNetwork, INotificationSink, Class, Class, ai.gebo.security.services.ReactiveIdentityUtil, IAgentsCollaborationSessionContextFactory)}.
 */
public interface IAgentsCollaborationSessionContextFactory {

	/**
	 * The session this run collaborates in.
	 * <p>
	 * Returning {@code null} is not a failure: the network then builds its own
	 * session, exactly as it does when no factory was supplied at all.
	 *
	 * @param creation what the run is about
	 * @return the session of the run, or {@code null} to leave it to the network
	 */
	AgentsCollaborationSessionContext createSessionContext(AgentsSessionCreationContext creation);

	/**
	 * Called once the run has ended, whether it produced an output or failed, and
	 * called exactly once per session this factory created.
	 * <p>
	 * This is where a caller that keeps its sessions persists what the run did: the
	 * session handed back holds every contribution the agents made. The default
	 * implementation does nothing, so a factory that only wants to supply a session
	 * need not care.
	 * <p>
	 * Whatever this method throws is logged and swallowed by the network: closing a
	 * session must never be able to turn a good run into a failed one, nor mask the
	 * failure of a bad one.
	 *
	 * @param session  the session the run happened in
	 * @param creation what the run was about
	 * @param output   what the run produced, {@code null} when it produced nothing
	 *                 or failed
	 * @param failure  what made the run fail, {@code null} when it did not
	 */
	default void sessionCompleted(AgentsCollaborationSessionContext session, AgentsSessionCreationContext creation,
			Object output, Throwable failure) {
	}
}
