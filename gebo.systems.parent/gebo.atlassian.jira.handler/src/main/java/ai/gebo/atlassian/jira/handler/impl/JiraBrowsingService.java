/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.api.IssueSearchApi;
import ai.gebo.jira.cloud.client.api.IssuesApi;
import ai.gebo.jira.cloud.client.api.JqlApi;
import ai.gebo.jira.cloud.client.api.ProjectsApi;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.JqlQueriesToParse;
import ai.gebo.jira.cloud.client.model.PageBeanProject;
import ai.gebo.jira.cloud.client.model.ParsedJqlQueries;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileResults;
import ai.gebo.jira.cloud.client.model.StringList;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * Service implementation for browsing Jira projects and issues as a virtual file system.
 * This class allows navigation through Jira projects and their issues using a hierarchical structure.
 */
@Service
public class JiraBrowsingService implements IGVirtualFilesystemBrowsingService<JiraBrowsingContext> {
	/**
	 * Factory for creating Jira API clients
	 */
	@Autowired
	JiraApiClientFactory factory;
	
	/**
	 * Service for handling REST template operations
	 */
	@Autowired
	RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Retrieves the root directories of the Jira virtual file system.
	 * These roots correspond to Jira projects.
	 *
	 * @param context The Jira browsing context containing system information
	 * @return OperationStatus containing a list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(JiraBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> roots = new ArrayList<>();
		try {
			ApiClient client = factory.getApiClient(context.getSystemCode());
			ProjectsApi api = new ProjectsApi(client);
			Long start = 0l;
			Integer howMany = 100;
			PageBeanProject retrieved = null;
			do { /*
					 * @param startAt The index of the first item to return in a page of results
					 * (page offset). (optional, default to 0)
					 * 
					 * @param maxResults The maximum number of items to return per page. (optional,
					 * default to 50)
					 * 
					 * @param orderBy [Order](#ordering) the results by a field. *
					 * &#x60;category&#x60; Sorts by project category. A complete list of category
					 * IDs is found using [Get all project
					 * categories](#api-rest-api-3-projectCategory-get). * &#x60;issueCount&#x60;
					 * Sorts by the total number of issues in each project. * &#x60;key&#x60; Sorts
					 * by project key. * &#x60;lastIssueUpdatedTime&#x60; Sorts by the last issue
					 * update time. * &#x60;name&#x60; Sorts by project name. * &#x60;owner&#x60;
					 * Sorts by project lead. * &#x60;archivedDate&#x60; EXPERIMENTAL. Sorts by
					 * project archived date. * &#x60;deletedDate&#x60; EXPERIMENTAL. Sorts by
					 * project deleted date. (optional, default to key)
					 * 
					 * @param id The project IDs to filter the results by. To include multiple IDs,
					 * provide an ampersand-separated list. For example,
					 * &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. Up to 50 project IDs can be
					 * provided. (optional)
					 * 
					 * @param keys The project keys to filter the results by. To include multiple
					 * keys, provide an ampersand-separated list. For example,
					 * &#x60;keys&#x3D;PA&amp;keys&#x3D;PB&#x60;. Up to 50 project keys can be
					 * provided. (optional)
					 * 
					 * @param query Filter the results using a literal string. Projects with a
					 * matching &#x60;key&#x60; or &#x60;name&#x60; are returned (case insensitive).
					 * (optional)
					 * 
					 * @param typeKey Orders results by the [project
					 * type](https://confluence.atlassian.com/x/GwiiLQ#Jiraapplicationsoverview-
					 * Productfeaturesandprojecttypes). This parameter accepts a comma-separated
					 * list. Valid values are &#x60;business&#x60;, &#x60;service_desk&#x60;, and
					 * &#x60;software&#x60;. (optional)
					 * 
					 * @param categoryId The ID of the project&#x27;s category. A complete list of
					 * category IDs is found using the [Get all project
					 * categories](#api-rest-api-3-projectCategory-get) operation. (optional)
					 * 
					 * @param action Filter results by projects for which the user can: *
					 * &#x60;view&#x60; the project, meaning that they have one of the following
					 * permissions: * *Browse projects* [project
					 * permission](https://confluence.atlassian.com/x/yodKLg) for the project. *
					 * *Administer projects* [project
					 * permission](https://confluence.atlassian.com/x/yodKLg) for the project. *
					 * *Administer Jira* [global
					 * permission](https://confluence.atlassian.com/x/x4dKLg). * &#x60;browse&#x60;
					 * the project, meaning that they have the *Browse projects* [project
					 * permission](https://confluence.atlassian.com/x/yodKLg) for the project. *
					 * &#x60;edit&#x60; the project, meaning that they have one of the following
					 * permissions: * *Administer projects* [project
					 * permission](https://confluence.atlassian.com/x/yodKLg) for the project. *
					 * *Administer Jira* [global
					 * permission](https://confluence.atlassian.com/x/x4dKLg). * &#x60;create&#x60;
					 * the project, meaning that they have the *Create issues* [project
					 * permission](https://confluence.atlassian.com/x/yodKLg) for the project in
					 * which the issue is created. (optional, default to view)
					 * 
					 * @param expand Use [expand](#expansion) to include additional information in
					 * the response. This parameter accepts a comma-separated list. Expanded options
					 * include: * &#x60;description&#x60; Returns the project description. *
					 * &#x60;projectKeys&#x60; Returns all project keys associated with a project. *
					 * &#x60;lead&#x60; Returns information about the project lead. *
					 * &#x60;issueTypes&#x60; Returns all issue types associated with the project. *
					 * &#x60;url&#x60; Returns the URL associated with the project. *
					 * &#x60;insight&#x60; EXPERIMENTAL. Returns the insight details of total issue
					 * count and last issue update time for the project. (optional)
					 * 
					 * @param status EXPERIMENTAL. Filter results by project status: *
					 * &#x60;live&#x60; Search live projects. * &#x60;archived&#x60; Search archived
					 * projects. * &#x60;deleted&#x60; Search deleted projects, those in the recycle
					 * bin. (optional)
					 * 
					 * @param properties EXPERIMENTAL. A list of project properties to return for
					 * the project. This parameter accepts a comma-separated list. (optional)
					 * 
					 * @param propertyQuery EXPERIMENTAL. A query string used to search properties.
					 * The query string cannot be specified using a JSON object. For example, to
					 * search for the value of &#x60;nested&#x60; from
					 * &#x60;{\&quot;something\&quot;:{\&quot;nested\&quot;:1,\&quot;other\&quot;:2}
					 * }&#x60; use &#x60;[thepropertykey].something.nested&#x3D;1&#x60;. Note that
					 * the propertyQuery key is enclosed in square brackets to enable searching
					 * where the propertyQuery key includes dot (.) or equals (&#x3D;) characters.
					 * Note that &#x60;thepropertykey&#x60; is only returned when included in
					 * &#x60;properties&#x60;. (optional)
					 */

				Long startAt = start;
				Integer maxResults = howMany;
				String orderBy = null;// "key";
				List<Long> id = null;
				List<String> keys = null;
				String query = null;
				String typeKey = null;
				Long categoryId = null;
				String action = null;// "view";
				String expand = null;// "description,projectKeys,lead,issueTypes,url";
				List<String> status = null;// List.of("live", "archived");
				List<StringList> properties = null;
				String propertyQuery = null;
				retrieved = api.searchProjects(startAt, maxResults, orderBy, id, keys, query, typeKey, categoryId,
						action, expand, status, properties, propertyQuery);
				if (retrieved != null && retrieved.getValues() != null) {
					roots.addAll(retrieved.getValues().stream().map(JiraNavigationUtil::toRoot).toList());
				}
				start = start + howMany;
			} while (retrieved != null && retrieved.getValues() != null && !retrieved.getValues().isEmpty()
					&& retrieved.isIsLast() != null && retrieved.isIsLast());
		} catch (HttpClientErrorException exc) {
			GUserMessage msg = RestTemplateWrapperService.toMessage(exc, "jira", "jira service");
			if (msg != null) {
				return OperationStatus.of(roots, msg);
			}
		} catch (Throwable exc) {
			GUserMessage msg = GUserMessage.errorMessage("Error connecting to jira", exc);
			if (msg != null) {
				return OperationStatus.of(roots, msg);
			}
		} finally {
			// No cleanup required
		}
		return OperationStatus.of(roots);
	}

	/**
	 * Browses a specific path in the Jira virtual file system.
	 * For a project path, retrieves issues based on parent-child relationships.
	 *
	 * @param param The browse parameters including root and path information
	 * @param context The Jira browsing context containing system information
	 * @return OperationStatus containing a list of path information entries
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing
	 */
	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, JiraBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> paths = new ArrayList<>();
		try {
			ApiClient client = factory.getApiClient(context.getSystemCode());

			boolean isProject = JiraNavigationUtil.isProject(param.root);
			if (isProject) {
				String projectCode = JiraNavigationUtil.getProjectCode(param.root);
				JqlApi jqlApi = new JqlApi(client);
				IssueSearchApi searchApi = new IssueSearchApi(client);
				int startAt = 0;
				int howMany = 50;
				SearchAndReconcileResults queryResult = null;
				IssueSearchApi issueSearch = new IssueSearchApi(client);
				do {
					// Build JQL query based on whether we're at root or navigating into child issues
					String jql = null;
					if (param.path == null) {
						jql = "project=" + projectCode + " AND parent is EMPTY";
					} else {
						jql = "project=" + projectCode + " AND parent=" + JiraNavigationUtil.lastIssueKey(param.path);
					}
					String nextPageToken = queryResult != null ? queryResult.getNextPageToken() : null;
					Integer maxResults = howMany;
					List<String> fields = JiraNavigationUtil.ISSUES_FIELDS;
					String expand = "names,schema,transitions";
					List<String> properties = null;
					Boolean fieldsByKeys = true;
					Boolean failFast = true;
					List<Long> reconcileIssues = null;
					queryResult = searchApi.searchAndReconsileIssuesUsingJql(jql, nextPageToken, maxResults, fields,
							expand, properties, fieldsByKeys, failFast, reconcileIssues);
					if (queryResult != null && queryResult.getIssues() != null) {
						paths.addAll(queryResult.getIssues().stream()
								.map(x -> JiraNavigationUtil.toPathInfo(param, x)).toList());
					}
				} while (queryResult != null && queryResult.getIssues() != null && !queryResult.getIssues().isEmpty()
						&& queryResult.getNextPageToken() != null);
			}

		} catch (HttpClientErrorException exc) {
			GUserMessage msg = RestTemplateWrapperService.toMessage(exc, "jira", "jira service");
			if (msg != null) {
				return OperationStatus.of(paths, msg);
			}
		} catch (Throwable exc) {
			GUserMessage msg = GUserMessage.errorMessage("Error connecting to jira", exc);
			if (msg != null) {
				return OperationStatus.of(paths, msg);
			}
		}
		return OperationStatus.of(paths);
	}

	/**
	 * Indicates whether the service supports navigation status.
	 *
	 * @return false as navigation status is not currently supported
	 */
	@Override
	public boolean isSupportsNavigationStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the parent reference for a given filesystem reference.
	 *
	 * @param reference The filesystem reference to find the parent for
	 * @param context The Jira browsing context containing system information
	 * @return The parent filesystem reference, or null if not implemented
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, JiraBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		// TODO Auto-generated method stub
		return null;
	}
}