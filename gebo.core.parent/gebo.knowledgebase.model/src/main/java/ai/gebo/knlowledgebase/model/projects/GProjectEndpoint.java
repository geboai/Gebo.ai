/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.projects;

import java.util.List;

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import ai.gebo.knlowledgebase.model.systems.BuildSystemRef;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * GProjectEndpoint represents the endpoint configuration for a specific project
 * within the knowledge base system. AI generated comments
 */
@Data
public class GProjectEndpoint extends GBaseObject implements IAclGrantedResource {

	/**
	 * FilesSynchronizationStrategy defines strategies for synchronizing files.
	 */
	public static enum FilesSynchronizationStrategy {
		SIZE_AND_TIMESTAMP_AND_HASH_CHECK, HASH_CHECK
	}

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = -8881032775757831188L;

	@GObjectReference(referencedType = GProject.class)
	private String parentProjectCode = null; // The code of the parent project
	private Boolean readonly = null; // Flag indicating if the project is read-only
	private Boolean published = null; // Flag indicating if the project is published
	private Boolean synchPeriodically = null; // Flag for periodic synchronization
	private Boolean openZips = null; // Flag for opening zip files
	private List<BuildSystemRef> buildSystemsRefs = null; // List of build system references
	private String catalogingCriteria = null; // Criteria for cataloging the project
	private List<ReindexingProgrammedTable> programmedTables = null; // Scheduled tables for reindexing
	private List<String> vectorizeOnlyExtensions = null; // File extensions to vectorize
	public FilesSynchronizationStrategy synchroStrategy = null; // Synchronization strategy
	private ObjectSpaceType objectSpaceType = null; // The type of object space

	private List<Integer> aclAliases = null;

	// Whether this data source is known to hold personal data (GDPR). Set by the
	// data controller per source; the compliance data-flow register treats a flow
	// as carrying personal data only when it originates from one or more sources
	// with this flag set, rather than assuming it. Defaults to false (business /
	// company data until classified otherwise).
	private Boolean personalData = false;

	/**
	 * Creates a clone of the GProjectEndpoint object.
	 * 
	 * @return a cloned instance of GProjectEndpoint
	 */
	public GProjectEndpoint clone() {
		GProjectEndpoint e = new GProjectEndpoint();
		e.buildSystemsRefs = buildSystemsRefs;
		e.parentProjectCode = parentProjectCode;
		e.published = published;
		e.synchPeriodically = synchPeriodically;
		e.setCode(getCode());
		e.setDescription(getDescription());
		e.catalogingCriteria = catalogingCriteria;
		e.parentProjectCode = parentProjectCode;
		e.programmedTables = programmedTables;
		return e;
	}

}