/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

/**
 * An OpenProject data source: the projects, sections, work packages, documents,
 * meetings and news
 * selected on one OpenProject system, read into a knowledge base.
 */
@EntityDescription(description = "OpenProject work packages, documents, meetings and news source", entityCategory = GProjectEndpoint.class)
@Document
public class GOpenProjectProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	private String openprojectSystemCode = null;

	public GOpenProjectProjectEndpoint() {
	}

	public String getOpenProjectSystemCode() {
		return openprojectSystemCode;
	}

	public void setOpenProjectSystemCode(String openprojectSystemCode) {
		this.openprojectSystemCode = openprojectSystemCode;
	}
}
