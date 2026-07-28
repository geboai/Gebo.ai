/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.services;

import java.util.List;

import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import jakarta.validation.constraints.NotNull;

/**
 * Pure calendar-math display helper, unconditionally available on every
 * deployment (no Mongo/messaging involved) - unlike the actual publish
 * scheduling engine, which is centralized (see
 * {@code ai.gebo.architecture.scheduling.services.impl.AbstractCentralSchedulingService}
 * and its {@code @ConditionalOnMonolithic}/{@code @ConditionalOnMicroservices}
 * subclasses) and reached only via the messaging system, never a direct call.
 */
public interface IGSchedulingTimeService {

    /**
     * Retrieves a list of display time values based on the supplied list of `ReindexingProgrammedTable`.
     *
     * @param programmedTable a list of `ReindexingProgrammedTable` objects, cannot be null
     * @return a list of display time values as strings
     */
    public List<String> getDisplayTimeValues(@NotNull List<ReindexingProgrammedTable> programmedTable);
}