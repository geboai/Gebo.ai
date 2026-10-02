/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// One per answer, kept apart from the chat so it survives the chat's deletion and branching.
@Document
@Data
public class GChatAnswerFeedback {
	private static final String SEPARATOR = "-|-";
	@Id
	private String id = null;
	@NotNull
	@HashIndexed
	private String userChatContextCode = null;
	@NotNull
	private String requestId = null;
	@NotNull
	@HashIndexed
	private String username = null;
	@NotNull
	private ChatAnswerFeedbackRating rating = null;
	private String comment = null;
	private String chatProfileCode = null;
	private String chatModelCode = null;
	private String pipelineCode = null;
	private Date createdAt = null;
	private Date modifiedAt = null;

	public static String idOf(String userChatContextCode, String requestId) {
		return userChatContextCode + SEPARATOR + requestId;
	}
}
