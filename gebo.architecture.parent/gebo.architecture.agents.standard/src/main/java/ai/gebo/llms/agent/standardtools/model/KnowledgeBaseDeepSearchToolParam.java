/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools.model;

import java.util.List;

import org.springframework.ai.tool.annotation.ToolParam;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import ai.gebo.llms.agent.standardtools.KnowledgeBaseKeywords;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A deep search of the knowledge bases with keywords for the full-text leg of its
 * searches: the parameter of the knowledge base deep search tool when that leg
 * exists (see {@link KnowledgeBaseKeywords}).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonClassDescription("A deep search: the searches to run and the question their documents are analysed against")
public class KnowledgeBaseDeepSearchToolParam extends DeepSearchToolParam<String> {
	@ToolParam(required = false, description = KnowledgeBaseKeywords.KEYWORDS_DESCRIPTION)
	private List<String> keywords = null;
}
