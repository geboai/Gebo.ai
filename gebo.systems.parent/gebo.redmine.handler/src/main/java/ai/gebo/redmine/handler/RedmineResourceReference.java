/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler;

import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * The minimal handle to stream one Redmine item, rebuilt from the document's
 * custom meta infos.
 */
public class RedmineResourceReference implements IGRemoteVirtualFilesystemResourceReference {
	public RedminePathNodeType type = null;
	public String projectId = null;
	public Integer issueId = null;
	public String wikiTitle = null;
	public Integer attachmentId = null;
	public String contentType = null;

	@Override
	public String toString() {
		return "{type=" + type + ", projectId=" + projectId + ", issueId=" + issueId + ", wikiTitle=" + wikiTitle
				+ ", attachmentId=" + attachmentId + ", contentType=" + contentType + "}";
	}
}
