/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

/**
 * A network service whose collaboration session can be built by someone else (see
 * {@link IAgentsCollaborationSessionContextFactory}).
 * <p>
 * Implemented by {@link GAbstractAgentsNetworkService}, so every network service
 * built on it accepts a session factory without its own factory having to know
 * anything about it: that is what lets
 * {@link IGAgentsNetworkServiceFactory#create(ai.gebo.architecture.agents.model.GAgentsNetwork, INotificationSink, Class, Class, ai.gebo.security.services.ReactiveIdentityUtil, IAgentsCollaborationSessionContextFactory)}
 * be a default method that every existing factory inherits unchanged.
 */
public interface IGSessionContextFactoryAware {

	/**
	 * The factory that will build the session of this service's runs, or
	 * {@code null} to go back to the service building its own.
	 */
	void setSessionContextFactory(IAgentsCollaborationSessionContextFactory sessionContextFactory);
}
