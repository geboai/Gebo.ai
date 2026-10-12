/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.IGObjectWithSecurity;
import lombok.Data;

@Document
@Data
public class GChatRule implements IGObjectWithSecurity {
	@Id
	private String id = null;
	private String text = null;
	private ChatRuleScope scope = null;
	@HashIndexed
	private String userChatContextCode = null;
	@HashIndexed
	private String ownerUsername = null;
	private Boolean accessibleToAll = null;
	private List<String> accessibleUsers = null;
	private List<String> accessibleGroups = null;
	// Optional restrictions: when set, the rule applies only to chats of that profile / pipeline.
	private String chatProfileCode = null;
	private String pipelineCode = null;
	private boolean enabled = true;
	private String sourceUserChatContextCode = null;
	private String sourceRequestId = null;
	private Date createdAt = null;
	private Date modifiedAt = null;
	private String modifiedBy = null;
	private List<GChatRuleChange> changes = new ArrayList<GChatRuleChange>();

	@Override
	public String owner() {
		return ownerUsername;
	}
}
