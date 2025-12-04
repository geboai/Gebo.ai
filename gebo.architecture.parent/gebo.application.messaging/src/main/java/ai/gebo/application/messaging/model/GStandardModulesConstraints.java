/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the constraints for standard modules within the application. This
 * class provides static references to various module names used throughout the
 * application.
 * 
 * Gebo.ai comment agent
 */
public class GStandardModulesConstraints {

	// Constants for module names
	public static final String CORE_MODULE = "core-module";
	public static final String SHARED_FILESYSTEM_MODULE = "shared-filesystem-module";
	public static final String UPLOADS_MODULE = "uploads-module";
	public static final String USERSPACE_MODULE = "userspace-module";
	public static final String VECTORIZATOR_MODULE = "vectorizator-module";
	public static final String GIT_MODULE = "git-module";
	public static final String SHAREPOINT_MODULE = "sharepoint-module";
	public static final String FULLTEXT_MODULE = "fulltext-module";
	public static final String ATLASSIAN_CONFLUENCE_MODULE = "confluence-module";
	public static final String ATLASSIAN_JIRA_MODULE = "jira-module";
	public static final String GOOGLE_DRIVE_MODULE = "google-drive-module";
	public static final String ASYNC_PUBLISHING_JOB_MODULE = "async-publishing-job-module";
	public static final String ASYNC_PUBLISHING_JOB_COMPONENT = "async-publishing-job-component";
	public static final String SCHEDULER_MODULE = "scheduler-module";
	public static final String INTEGRATION_MODULE = "integration-module";
	public static final String SCHEDULER_COMPONENT = "scheduler-component";
	public static final String TOKENIZER_MODULE = "tokenizer-module";
	public static final String TOKENIZER_COMPONENT = "tokenizer-component";

	public static final String KNOWLEDGE_GRAPH_MODULE = "knowledge-graph-module";
	public static final String KNOWLEDGE_GRAPH_COMPONENT = "knowledge-graph-component";

	// Constants for component names
	public static final String RESOURCES_DISPOSE_COMPONENT = "resources-dispose-component";
	public static final String MODULE_IOC_DISPATCHER_COMPONENT = "module-ioc-dispatcher-component";
	public static final String FULLTEXT_INDEXING_COMPONENT = "fulltext-indexing-component";
	public static final String MONGO_DISPOSE_DOCUMENTS_COMPONENT = "mongo-dispose-documents-component";
	public static final String VECTORIZATION_DISPOSE_COMPONENT = "vectorization-dispose-component";
	public static final String VECTORIZATION_COMPONENT = "vectorization-component";
	public static final String VECTORIZATION_EMITTER_COMPONENT = "vectorization-emitter-component";
	public static final String ARCHITECTURE_MASTER_COMPONENT = "architecture-master-component";
	public static final String ARCHITECTURE_EMITTER_COMPONENT = "architecture-emitter-component";
	public static final String SYSTEM_SETTINGS_CONTROLLER_COMPONENT = "system-settings-controller-component";
	public static final String USER_MESSAGES_CONCENTRATOR_COMPONENT = "user-messages-concentrator-component";

	// Lists of modules categorized by their type
	public static final List<String> EXTERNAl_MODULES = new ArrayList<String>();
	public static final List<String> UNDER_DEVELOPMENT_MODULES = of();
	public static final List<String> ALL_MODULES = of(CORE_MODULE, SHARED_FILESYSTEM_MODULE, UPLOADS_MODULE,
			VECTORIZATOR_MODULE, GIT_MODULE, ATLASSIAN_CONFLUENCE_MODULE, ATLASSIAN_JIRA_MODULE, SHAREPOINT_MODULE,
			FULLTEXT_MODULE, USERSPACE_MODULE, ASYNC_PUBLISHING_JOB_MODULE, SCHEDULER_MODULE, GOOGLE_DRIVE_MODULE);
	public static final List<String> COMMUNITY_MODULES = of(CORE_MODULE, SHARED_FILESYSTEM_MODULE, UPLOADS_MODULE,
			VECTORIZATOR_MODULE, GIT_MODULE, ATLASSIAN_CONFLUENCE_MODULE, ATLASSIAN_JIRA_MODULE, FULLTEXT_MODULE,
			USERSPACE_MODULE, SHAREPOINT_MODULE, ASYNC_PUBLISHING_JOB_MODULE, SCHEDULER_MODULE, GOOGLE_DRIVE_MODULE);

	static {
		// Check system property to determine if UNDER_DEVELOPMENT modules should be
		// added to other lists
		boolean underDevelopment = System.getProperty("under-development") != null
				&& System.getProperty("under-development").equalsIgnoreCase("true");
		if (underDevelopment) {
			// If in under-development mode, include under-development modules in all and
			// community categories
			ALL_MODULES.addAll(UNDER_DEVELOPMENT_MODULES);
			COMMUNITY_MODULES.addAll(UNDER_DEVELOPMENT_MODULES);
		}
	}

	/**
	 * Creates a list from the given parameters.
	 * 
	 * @param params Array of string parameters to be included in the list.
	 * @return A list containing all given parameters.
	 */
	private static List<String> of(String... params) {
		return new ArrayList<String>(List.of(params));
	}

}