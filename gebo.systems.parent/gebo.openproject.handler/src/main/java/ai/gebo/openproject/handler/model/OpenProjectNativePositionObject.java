/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * One node of the OpenProject virtual filesystem as read from the OpenProject API. Its
 * meta infos carry everything needed to stream it again later from the stored
 * document alone, and children inherit their parents' entries.
 */
public class OpenProjectNativePositionObject extends AbstractNativePositionObject {
	public static final String OPENPROJECT_OBJECT_TYPE = "OPENPROJECT-OBJECT-TYPE";
	public static final String OPENPROJECT_PROJECT_ID = "OPENPROJECT-PROJECT-ID";
	public static final String OPENPROJECT_PROJECT_IDENTIFIER = "OPENPROJECT-PROJECT-IDENTIFIER";
	public static final String OPENPROJECT_WORK_PACKAGE_ID = "OPENPROJECT-WORK-PACKAGE-ID";
	public static final String OPENPROJECT_DOCUMENT_ID = "OPENPROJECT-DOCUMENT-ID";
	public static final String OPENPROJECT_MEETING_ID = "OPENPROJECT-MEETING-ID";
	public static final String OPENPROJECT_NEWS_ID = "OPENPROJECT-NEWS-ID";
	public static final String OPENPROJECT_ATTACHMENT_ID = "OPENPROJECT-ATTACHMENT-ID";
	public static final String OPENPROJECT_FILE_NAME = "OPENPROJECT-FILE-NAME";
	public static final String OPENPROJECT_CONTENT_TYPE = "OPENPROJECT-CONTENT-TYPE";
	/** The id of the activity an internal comment is. */
	public static final String OPENPROJECT_ACTIVITY_ID = "OPENPROJECT-ACTIVITY-ID";
	/**
	 * {@code true} on an internal comment: OpenProject shows it only to the roles
	 * with {@code view_internal_comments}, not to whoever sees the work package.
	 */
	public static final String OPENPROJECT_INTERNAL_COMMENT = "OPENPROJECT-INTERNAL-COMMENT";
	/** The id of the OpenProject user who wrote an internal comment. */
	public static final String OPENPROJECT_COMMENT_AUTHOR_ID = "OPENPROJECT-COMMENT-AUTHOR-ID";

	private OpenProjectPathNodeType type = null;
	/** The id of the node within its type (see {@link OpenProjectPathComponent}). */
	private String id = null;
	private String code = null;
	private String name = null;
	private String url = null;
	private String resourceContentType = null;
	private Date resourceModificationTime = null;
	private Long resourceFileSize = null;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();

	public OpenProjectNativePositionObject() {
	}

	public OpenProjectPathNodeType getType() {
		return type;
	}

	public void setType(OpenProjectPathNodeType type) {
		this.type = type;
		resourceReferenceMetaInfos.put(OPENPROJECT_OBJECT_TYPE, type != null ? type.name() : null);
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

	public OpenProjectNativePositionObject meta(String key, Object value) {
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
		meta(OPENPROJECT_CONTENT_TYPE, resourceContentType);
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
