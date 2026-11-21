/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service;

/**
 * A reactive agent answering the user that also hands work to the agents of its
 * communication list while it works: the controller of its network and its
 * streaming output node at once. It is adapted to the network by an adapter of its
 * own, which gives the network its commands in place of its answer until it
 * answers (see {@code LoopControllerNetworkAdapter}).
 */
public interface ILoopControllerReactiveAgentService extends IReportWriterReactiveAgentService {

}
