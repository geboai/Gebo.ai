/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a repository pattern for external message receiver 
 * sources. This interface extends the generic repository pattern for 
 * implementations of {@code IGExternalMessageReceiverSource}.
 * 
 * @see IGExternalMessageReceiverSource
 * @see IGImplementationsRepositoryPattern
 */
public interface IGExternalMessageReceiverSourceRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGExternalMessageReceiverSource> {

}