/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.model;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * Gebo.ai comment agent
 *
 * Represents a scheduled task specifically related to a project endpoint.
 * This class extends the AbstractScheduledTask with a type parameter of GObjectRef of a GProjectEndpoint.
 * It's used in the scheduling system to handle tasks associated with project endpoints.
 */
public class ProjectEndpointScheduledTask extends AbstractScheduledTask<GObjectRef<GProjectEndpoint>> {

}