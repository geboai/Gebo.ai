/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.contents;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * Abstract class representing a virtual filesystem object. This class provides
 * a base structure for objects participating in a virtual file system,
 * including metadata such as paths and project associations. AI generated
 * comments
 */
@Data
public abstract class GAbstractVirtualFilesystemObject extends GBaseVersionableObject implements IAclGrantedResource {

	/**
	 * The code of the parent virtual folder.
	 */
	@HashIndexed
	private String parentVirtualFolderCode = null;

	/**
	 * The absolute path of the filesystem object.
	 */
	@HashIndexed
	private String absolutePath = null;

	/**
	 * The code of the parent project associated with this filesystem object. This
	 * is a reference to a GProject.
	 */
	@HashIndexed
	@GObjectReference(referencedType = GProject.class)
	private String parentProjectCode = null;

	/**
	 * The code of the root knowledgebase associated with this filesystem object.
	 * This is a reference to a GKnowledgeBase.
	 */
	@HashIndexed
	@GObjectReference(referencedType = GKnowledgeBase.class)
	private String rootKnowledgebaseCode = null;

	/**
	 * The URI of the filesystem object.
	 */
	private String uri = null;

	/**
	 * The relative path of the filesystem object.
	 */
	private String relativePath = null;

	/**
	 * The name of the filesystem object.
	 */
	@HashIndexed
	@TextIndexed
	private String name = null;

	/**
	 * Flag indicating whether the filesystem object is marked as deleted.
	 */
	private Boolean deleted = false;

	/**
	 * ID of the messaging module associated with this filesystem object.
	 */
	@HashIndexed
	private String messagingModuleId = null;

	/**
	 * Reference to the project endpoint associated with this filesystem object.
	 */
	@HashIndexed
	private GObjectRef<GProjectEndpoint> projectEndpointReference = null;

	/**
	 * Flag indicating whether the filesystem object is nested in an archive.
	 */
	private Boolean nestedInArchive = null;

	/**
	 * The absolute path of the archive containing this filesystem object.
	 */
	private String absoluteArchivePath = null;

	/**
	 * The internal path within the archive.
	 */
	private String archiveInternalPath = null;

	/**
	 * Custom metadata information associated with this filesystem object.
	 */
	private Map<String, Object> customMetaInfos = null;

	/**
	 * The ID of the latest job associated with this filesystem object.
	 */
	@HashIndexed
	private String lastesJobId = null;
	@HashIndexed
	private List<Integer> aclAliases = null;

	/**
	 * Default constructor.
	 */
	public GAbstractVirtualFilesystemObject() {
	}

	/**
	 * Copy constructor that initializes a new instance using another instance.
	 * 
	 * @param o The GAbstractVirtualFilesystemObject to copy data from.
	 */
	public GAbstractVirtualFilesystemObject(GAbstractVirtualFilesystemObject o) {
		this.setCode(o.getCode());
		this.setDescription(o.getDescription());
		this.absolutePath = o.absolutePath;
		this.messagingModuleId = o.messagingModuleId;
		this.name = o.name;
		this.parentProjectCode = o.parentProjectCode;
		this.projectEndpointReference = o.projectEndpointReference;
		this.parentVirtualFolderCode = o.parentVirtualFolderCode;
		this.rootKnowledgebaseCode = o.rootKnowledgebaseCode;
		this.absolutePath = o.absolutePath;
		this.deleted = o.deleted;
		this.uri = o.uri;
		this.setModificationDate(o.getModificationDate());
		this.setCreationDate(o.getCreationDate());

		this.aclAliases = o.aclAliases;
	}

}