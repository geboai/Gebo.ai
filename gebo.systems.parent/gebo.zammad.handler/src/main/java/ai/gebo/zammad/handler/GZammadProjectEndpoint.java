/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

/**
 * A Zammad data source: the groups (tickets) and the knowledge bases
 * (categories, answers) selected on one Zammad system, read into a knowledge
 * base.
 */
@EntityDescription(description = "Zammad tickets and knowledge base source", entityCategory = GProjectEndpoint.class)
@Document
public class GZammadProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	private String zammadSystemCode = null;

	public GZammadProjectEndpoint() {
	}

	public String getZammadSystemCode() {
		return zammadSystemCode;
	}

	public void setZammadSystemCode(String zammadSystemCode) {
		this.zammadSystemCode = zammadSystemCode;
	}
}
