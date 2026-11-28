/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * One node of the Zammad virtual filesystem as read from the Zammad API. Its
 * meta infos carry everything needed to stream it again later from the stored
 * document alone, and children inherit their parents' entries.
 */
public class ZammadNativePositionObject extends AbstractNativePositionObject {
	public static final String ZAMMAD_OBJECT_TYPE = "ZAMMAD-OBJECT-TYPE";
	/** The code of the data source root the node is under: GROUP:<id> or KNOWLEDGE_BASE:<id>. */
	public static final String ZAMMAD_ROOT = "ZAMMAD-ROOT";
	public static final String ZAMMAD_GROUP_ID = "ZAMMAD-GROUP-ID";
	public static final String ZAMMAD_KNOWLEDGE_BASE_ID = "ZAMMAD-KNOWLEDGE-BASE-ID";
	public static final String ZAMMAD_TICKET_ID = "ZAMMAD-TICKET-ID";
	public static final String ZAMMAD_TICKET_NUMBER = "ZAMMAD-TICKET-NUMBER";
	public static final String ZAMMAD_ARTICLE_ID = "ZAMMAD-ARTICLE-ID";
	public static final String ZAMMAD_CATEGORY_ID = "ZAMMAD-CATEGORY-ID";
	public static final String ZAMMAD_ANSWER_ID = "ZAMMAD-ANSWER-ID";
	public static final String ZAMMAD_ATTACHMENT_ID = "ZAMMAD-ATTACHMENT-ID";
	public static final String ZAMMAD_FILE_NAME = "ZAMMAD-FILE-NAME";
	public static final String ZAMMAD_CONTENT_TYPE = "ZAMMAD-CONTENT-TYPE";
	/**
	 * {@code true} on an internal note and on an attachment of an internal article:
	 * Zammad shows them to the agents of the ticket's group, never to its customer.
	 * Absent on everything else.
	 */
	public static final String ZAMMAD_INTERNAL_NOTE = "ZAMMAD-INTERNAL-NOTE";
	/** The id of the Zammad user who wrote an internal article ({@code created_by_id}), next to {@link #ZAMMAD_INTERNAL_NOTE}. */
	public static final String ZAMMAD_NOTE_AUTHOR_ID = "ZAMMAD-NOTE-AUTHOR-ID";

	private ZammadPathNodeType type = null;
	/** The id of the node within its type (see {@link ZammadPathComponent}). */
	private String id = null;
	private String code = null;
	private String name = null;
	private String url = null;
	private String resourceContentType = null;
	private Date resourceModificationTime = null;
	private Long resourceFileSize = null;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();

	public ZammadNativePositionObject() {
	}

	public ZammadPathNodeType getType() {
		return type;
	}

	public void setType(ZammadPathNodeType type) {
		this.type = type;
		resourceReferenceMetaInfos.put(ZAMMAD_OBJECT_TYPE, type != null ? type.name() : null);
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

	public ZammadNativePositionObject meta(String key, Object value) {
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
		meta(ZAMMAD_CONTENT_TYPE, resourceContentType);
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
