/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * One node of the Redmine virtual filesystem as read from the Redmine API. Its
 * meta infos carry everything needed to stream it again later from the stored
 * document alone, and children inherit their parents' entries.
 */
public class RedmineNativePositionObject extends AbstractNativePositionObject {
	public static final String REDMINE_OBJECT_TYPE = "REDMINE-OBJECT-TYPE";
	public static final String REDMINE_PROJECT_ID = "REDMINE-PROJECT-ID";
	public static final String REDMINE_PROJECT_IDENTIFIER = "REDMINE-PROJECT-IDENTIFIER";
	public static final String REDMINE_ISSUE_ID = "REDMINE-ISSUE-ID";
	public static final String REDMINE_WIKI_TITLE = "REDMINE-WIKI-TITLE";
	public static final String REDMINE_ATTACHMENT_ID = "REDMINE-ATTACHMENT-ID";
	public static final String REDMINE_FILE_NAME = "REDMINE-FILE-NAME";
	public static final String REDMINE_CONTENT_TYPE = "REDMINE-CONTENT-TYPE";

	private RedminePathNodeType type = null;
	/** The id of the node within its type (see {@link RedminePathComponent}). */
	private String id = null;
	private String code = null;
	private String name = null;
	private String url = null;
	private String resourceContentType = null;
	private Date resourceModificationTime = null;
	private Long resourceFileSize = null;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();

	public RedmineNativePositionObject() {
	}

	public RedminePathNodeType getType() {
		return type;
	}

	public void setType(RedminePathNodeType type) {
		this.type = type;
		resourceReferenceMetaInfos.put(REDMINE_OBJECT_TYPE, type != null ? type.name() : null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean isResource() {
		return type != null && !type.isFolder();
	}

	@Override
	public boolean isFolder() {
		return type != null && type.isFolder();
	}

	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return resourceReferenceMetaInfos;
	}

	public void setResourceReferenceMetaInfos(HashMap<String, Object> resourceReferenceMetaInfos) {
		this.resourceReferenceMetaInfos = resourceReferenceMetaInfos;
	}

	public RedmineNativePositionObject meta(String key, Object value) {
		if (value != null) {
			resourceReferenceMetaInfos.put(key, value.toString());
		}
		return this;
	}

	@Override
	public String getResourceContentType() {
		return resourceContentType;
	}

	public void setResourceContentType(String resourceContentType) {
		this.resourceContentType = resourceContentType;
		meta(REDMINE_CONTENT_TYPE, resourceContentType);
	}

	@Override
	public Date getResourceModificationTime() {
		return resourceModificationTime;
	}

	public void setResourceModificationTime(Date resourceModificationTime) {
		this.resourceModificationTime = resourceModificationTime;
	}

	@Override
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	public void setResourceFileSize(Long resourceFileSize) {
		this.resourceFileSize = resourceFileSize;
	}

	@Override
	public String toString() {
		return "{type=" + type + ", id=" + id + ", code=" + code + ", name=" + name + "}";
	}
}
