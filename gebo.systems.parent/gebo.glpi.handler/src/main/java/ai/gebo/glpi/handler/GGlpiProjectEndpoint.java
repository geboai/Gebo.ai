/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A GLPI data source: the entities, tickets and knowledge base articles
 * selected (in paths) from one GLPI system.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EntityDescription(description = "GLPI tickets and knowledge base source", entityCategory = GProjectEndpoint.class)
@Document
public class GGlpiProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	private String glpiSystemCode = null;
}
