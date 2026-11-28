/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * One node of the GLPI tree as the consuming walk sees it. It holds only
 * scalars: everything streaming needs later goes into the meta infos map,
 * which becomes the custom meta infos of the stored folder or document.
 */
public class GlpiNativePositionObject extends AbstractNativePositionObject {
	private static final long serialVersionUID = 1L;
	/** Kind of node, the GlpiPathNodeType name */
	public static final String GLPI_OBJECT_TYPE = "GLPI-OBJECT-TYPE";
	/** Id of the GLPI entity the node belongs to */
	public static final String GLPI_ENTITY_ID = "GLPI-ENTITY-ID";
	/** Full name of the GLPI entity the node belongs to */
	public static final String GLPI_ENTITY_NAME = "GLPI-ENTITY-NAME";
	/** Id of the ticket the node is or belongs to */
	public static final String GLPI_TICKET_ID = "GLPI-TICKET-ID";
	/** GLPI id of the node itself (ticket, follow-up, task, solution, document, article) */
	public static final String GLPI_ITEM_ID = "GLPI-ITEM-ID";
	public static final String GLPI_DOCUMENT_FILENAME = "GLPI-DOCUMENT-FILENAME";
	public static final String GLPI_DOCUMENT_MIME = "GLPI-DOCUMENT-MIME";
	public static final String GLPI_TICKET_NAME = "GLPI-TICKET-NAME";
	public static final String GLPI_TICKET_STATUS = "GLPI-TICKET-STATUS";
	public static final String GLPI_TICKET_TYPE = "GLPI-TICKET-TYPE";
	public static final String GLPI_TICKET_PRIORITY = "GLPI-TICKET-PRIORITY";
	public static final String GLPI_TICKET_CATEGORY = "GLPI-TICKET-CATEGORY";
	public static final String GLPI_PRIVATE = "GLPI-PRIVATE";
	public static final String GLPI_KB_CATEGORIES = "GLPI-KB-CATEGORIES";

	private GlpiPathNodeType type = null;
	private Long id = null;
	private String code = null;
	private String name = null;
	private String url = null;
	private String contentType = null;
	private Date modificationTime = null;
	private Long fileSize = null;
	private final HashMap<String, Object> metaInfos = new HashMap<String, Object>();

	public GlpiNativePositionObject() {
	}

	public GlpiNativePositionObject(GlpiPathNodeType type, Long id, String code, String name, String url) {
		this.type = type;
		this.id = id;
		this.code = code;
		this.name = name;
		this.url = url;
		this.metaInfos.put(GLPI_OBJECT_TYPE, type.name());
		if (id != null) {
			this.metaInfos.put(GLPI_ITEM_ID, id);
		}
	}

	public GlpiPathNodeType getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUrl() {
		return url;
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
		return metaInfos;
	}

	/** Puts a meta info, ignoring null values (the map is persisted) */
	public GlpiNativePositionObject meta(String key, Object value) {
		if (value != null) {
			metaInfos.put(key, value);
		}
		return this;
	}

	@Override
	public String getResourceContentType() {
		return contentType;
	}

	public void setResourceContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public Date getResourceModificationTime() {
		return modificationTime;
	}

	public void setResourceModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Override
	public Long getResourceFileSize() {
		return fileSize;
	}

	public void setResourceFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getEntityId() {
		return asLong(metaInfos.get(GLPI_ENTITY_ID));
	}

	public Long getTicketId() {
		return asLong(metaInfos.get(GLPI_TICKET_ID));
	}

	public static Long asLong(Object value) {
		if (value instanceof Number n) {
			return n.longValue();
		}
		if (value != null) {
			try {
				return Long.valueOf(value.toString().trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
}
