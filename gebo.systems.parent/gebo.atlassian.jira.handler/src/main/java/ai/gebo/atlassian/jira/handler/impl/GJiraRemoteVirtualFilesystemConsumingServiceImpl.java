/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Implementation of a Jira remote virtual filesystem service that integrates with Jira Cloud API.
 * This service allows for browsing and accessing Jira projects, issues, comments, and attachments
 * in a filesystem-like structure.
 */
package ai.gebo.atlassian.jira.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraVirtualFilesystemConsumingService;
import ai.gebo.atlassian.jira.handler.impl.model.JiraAttachment;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNativePositionObject;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNavigationCoordinates;
import ai.gebo.atlassian.jira.handler.impl.model.JiraPathComponent;
import ai.gebo.atlassian.jira.handler.impl.model.JiraPathNodeType;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResourceReference;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.api.IssueAttachmentsApi;
import ai.gebo.jira.cloud.client.api.IssueCommentsApi;
import ai.gebo.jira.cloud.client.api.IssueSearchApi;
import ai.gebo.jira.cloud.client.api.IssuesApi;
import ai.gebo.jira.cloud.client.api.ProjectsApi;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.Comment;
import ai.gebo.jira.cloud.client.model.IssueBean;
import ai.gebo.jira.cloud.client.model.PageOfComments;
import ai.gebo.jira.cloud.client.model.Project;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileResults;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;

@Service
public class GJiraRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GJiraSystem, GJiraProjectEndpoint, JiraNativePositionObject, JiraNavigationCoordinates, JiraResourceReference>
		implements IGJiraVirtualFilesystemConsumingService {
	/**
	 * Constant key for storing the Jira API client in the cache
	 */
	private static final String JIRA_CLIENT = "JIRA-CLIENT";
	
	/**
	 * Service for wrapping REST template operations
	 */
	protected final RestTemplateWrapperService restTemplateWrapper;
	
	/**
	 * Factory for creating Jira API clients
	 */
	protected final JiraApiClientFactory apiClientFactory;
	
	/**
	 * Constant key for attachment filename in metadata
	 */
	public static final String ATTACHMENT_FILENAME = "ATTACHMENT-FILENAME";
	
	/**
	 * Constant key for attachment ID in metadata
	 */
	public static final String ATTACHMENT_ID = "ATTACHMENT-ID";
	
	/**
	 * Constant key for comment content in metadata
	 */
	public static final String COMMENT_CONTENT = "COMMENT-CONTENT";
	
	/**
	 * Constant key for comment reference in metadata
	 */
	public static final String COMMENT_REFERENCE = "COMMENT-REFERENCE";
	
	/**
	 * Constant key for issue content reference in metadata
	 */
	public static final String ISSUE_CONTENT_REFERENCE = "ISSUE-CONTENT-REFERENCE";
	
	/**
	 * Constant key for issue reference in metadata
	 */
	public static final String ISSUE_REFERENCE = "ISSUE-REFERNCE";
	
	/**
	 * Constant key for Jira object type in metadata
	 */
	public static final String JIRA_OBJECT_TYPE = "JIRA-OBJECT-TYPE";
	
	/**
	 * Constant key for attachment content URL in metadata
	 */
	public static final String ATTACHMENT_CONTENT_URL = "ATTACHMENT-CONTENT-URL";
	
	/**
	 * Constant key for attachment MIME type in metadata
	 */
	public static final String ATTACHMENT_MIME_TYPE = "ATTACHMENT-MIME-TYPE";

	/**
	 * Constructor for the Jira remote virtual filesystem service
	 * 
	 * @param documentFactory Factory for document references
	 * @param restTemplateWrapper Service for REST template operations
	 * @param browserFactory Factory for creating Jira API clients
	 */
	public GJiraRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			RestTemplateWrapperService restTemplateWrapper, JiraApiClientFactory browserFactory) {
		super(documentFactory);
		this.restTemplateWrapper = restTemplateWrapper;
		this.apiClientFactory = browserFactory;

	}

	/**
	 * Creates a resource handle from a virtual filesystem object
	 * 
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param reference The virtual filesystem object
	 * @param cache Cache for storing temporary data
	 * @return A Jira resource reference
	 * @throws GeboContentHandlerSystemException If an error occurs during handling
	 */
	@Override
	public JiraResourceReference getResourceHandle(GJiraSystem system, GJiraProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		JiraResourceReference _reference = new JiraResourceReference();
		Map<String, Object> metainfos = reference.getCustomMetaInfos();
		if (metainfos != null) {
			_reference.jiraObjectType = extractString(metainfos, JIRA_OBJECT_TYPE);
			_reference.issueContainerId = extractString(metainfos, ISSUE_REFERENCE);
			_reference.issueContentId = extractString(metainfos, ISSUE_CONTENT_REFERENCE);
			_reference.attachmentId = extractString(metainfos, ATTACHMENT_ID);
			_reference.attachmentFileName = extractString(metainfos, ATTACHMENT_FILENAME);
			_reference.attachmentUrl = extractString(metainfos, ATTACHMENT_CONTENT_URL);
			_reference.attachmentContentType = extractString(metainfos, ATTACHMENT_MIME_TYPE);
			_reference.commentId = extractString(metainfos, COMMENT_REFERENCE);
			_reference.commentContent = extractString(metainfos, COMMENT_CONTENT);
		}
		return _reference;
	}

	/**
	 * Helper method to extract string values from metadata map
	 * 
	 * @param metainfos Map containing metadata
	 * @param field Field name to extract
	 * @return The string value or null if not found
	 */
	private String extractString(Map<String, Object> metainfos, String field) {
		return metainfos.containsKey(field) && metainfos.get(field) != null ? metainfos.get(field).toString() : null;
	}

	/**
	 * Streams a Jira resource (attachment, comment, or issue) as an InputStream
	 * 
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param reference The Jira resource reference
	 * @param cache Cache for storing temporary data
	 * @return InputStream of the resource content
	 * @throws GeboContentHandlerSystemException If an error occurs during handling
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	public InputStream streamResource(GJiraSystem system, GJiraProjectEndpoint endpoint,
			JiraResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		if (reference.jiraObjectType != null) {
			ApiClient apiClient = (ApiClient) cache.get(JIRA_CLIENT);
			if (apiClient == null) {
				try {
					apiClient = this.apiClientFactory.getApiClient(system);
					cache.put(JIRA_CLIENT, apiClient);
				} catch (GeboCryptSecretException e) {
					throw new GeboContentHandlerSystemException("Cannot allocate jira client", e);
				}
			}
			JiraPathNodeType type = JiraPathNodeType.valueOf(reference.jiraObjectType);
			switch (type) {
			case ATTACHMENT: {
				IssueAttachmentsApi attachmentApi = new IssueAttachmentsApi(apiClient);
				return attachmentApi.streamAttachmentContent(reference.attachmentId, reference.attachmentFileName,
						reference.attachmentContentType, reference.attachmentUrl);

			}

			case COMMENT: {
				if (reference.commentContent != null) {
					return new ByteArrayInputStream(reference.commentContent.getBytes());
				}
			}

			case TICKET: {
				IssuesApi issueApi = new IssuesApi(apiClient);
				IssueBean issue = issueApi.getIssue(reference.issueContentId, JiraNavigationUtil.ISSUES_FIELDS, null,
						"names,schema,transitions", null, null, null);
				if (issue != null && issue.getFields().containsKey("description")) {
					return new ByteArrayInputStream(issue.getFields().get("description").toString().getBytes());
				}
			}
				break;
			default: {
				// The only 3 valid resources to stream are ATTACHMENT/COMMENT/TICKETS other
				// stuff if are here some errors has occurred.
				throw new GeboContentHandlerSystemException(
						"Object of type " + type.name() + " is a container and is not streamable");
			}
			}
		}
		return null;
	}

	/**
	 * Returns the messaging module ID for this service
	 * 
	 * @return The messaging module ID
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.ATLASSIAN_JIRA_MODULE;
	}

	/**
	 * Converts a list of native position objects to navigation coordinates
	 * 
	 * @param childCoordinates List of native position objects
	 * @return Navigation coordinates
	 * @throws GeboContentHandlerSystemException If an error occurs during conversion
	 */
	@Override
	protected JiraNavigationCoordinates getPositionCoordinate(List<JiraNativePositionObject> childCoordinates)
			throws GeboContentHandlerSystemException {
		return JiraNavigationUtil.toJiraNavigationCoordinates(childCoordinates);
	}

	/**
	 * Retrieves child elements from a Jira location
	 * 
	 * @param nativeCoordinates List of native position objects
	 * @param position Navigation coordinates
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param messagesConsumer Consumer for user messages
	 * @param environment Environment data
	 * @return List of child coordinate pointers
	 * @throws GeboContentHandlerSystemException If an error occurs during retrieval
	 */
	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<JiraNativePositionObject> nativeCoordinates,
			JiraNavigationCoordinates position, GJiraSystem system, GJiraProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<>();
		if (!nativeCoordinates.isEmpty()) {
			JiraNativePositionObject lastChild = nativeCoordinates.get(nativeCoordinates.size() - 1);
			ApiClient apiClient = (ApiClient) environment.get(JIRA_CLIENT);
			IssueSearchApi searchApi = new IssueSearchApi(apiClient);
			if (lastChild.isProject()) {
				String projectCode = lastChild.getCode();
				String jql = null;
				// query for root tickets
				jql = "project=" + projectCode + " AND parent is EMPTY";
				childs.addAll(getIssuesNodesByJQL(jql, nativeCoordinates, searchApi));
			} else if (lastChild.isIssueFolder()) {
				IssueCommentsApi commentApi = new IssueCommentsApi(apiClient);
				JiraNativePositionObject nativeProject = (JiraNativePositionObject) nativeCoordinates.get(0);
				NativeCoordinatePointer pageContentPointer = new NativeCoordinatePointer();
				pageContentPointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
				pageContentPointer.child = new JiraNativePositionObject();
				// appending the issue content body as child
				pageContentPointer.child.setIssueAsResource(lastChild.getIssueAsFolder());
				childs.add(pageContentPointer);
				// Add comments
				childs.addAll(getCommentsNodes(lastChild.getCode(), nativeCoordinates, commentApi));
				// Add attachments
				List<NativeCoordinatePointer> attachmentPointers = readAttachmentsMetaInfos(
						lastChild.getIssueAsFolder(), nativeCoordinates);
				childs.addAll(attachmentPointers);
				String projectCode = nativeProject.getCode();
				String jql = null;
				// query for child tickets
				jql = "project=" + projectCode + " AND parent=" + lastChild.getCode();

				childs.addAll(getIssuesNodesByJQL(jql, nativeCoordinates, searchApi));

			}

		}
		return childs;
	}

	/**
	 * Retrieves comments for a specific issue
	 * 
	 * @param issueKey Issue key
	 * @param nativeCoordinates Parent coordinates
	 * @param commentApi Jira comment API client
	 * @return List of comment coordinate pointers
	 */
	private List<NativeCoordinatePointer> getCommentsNodes(String issueKey,
			List<JiraNativePositionObject> nativeCoordinates, IssueCommentsApi commentApi) {
		List<NativeCoordinatePointer> childs = new ArrayList<>();
		PageOfComments commentsResult = null;
		long startAt = 0;
		int howMany = 20;
		do {
			commentsResult = commentApi.getComments(issueKey, startAt, howMany, null, null);
			if (commentsResult != null && commentsResult.getComments() != null) {
				for (Comment comment : commentsResult.getComments()) {
					NativeCoordinatePointer nativePointer = new NativeCoordinatePointer();
					nativePointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
					nativePointer.child = new JiraNativePositionObject();
					nativePointer.child.setComment(comment);
					childs.add(nativePointer);
				}
			}
			startAt += howMany;

		} while (commentsResult != null && commentsResult.getComments() != null
				&& !commentsResult.getComments().isEmpty());
		return childs;
	}

	/**
	 * Retrieves issues based on a JQL query
	 * 
	 * @param jql JQL query string
	 * @param nativeCoordinates Parent coordinates
	 * @param searchApi Jira search API client
	 * @return List of issue coordinate pointers
	 */
	private List<NativeCoordinatePointer> getIssuesNodesByJQL(String jql,
			List<JiraNativePositionObject> nativeCoordinates, IssueSearchApi searchApi) {
		List<NativeCoordinatePointer> childs = new ArrayList<>();
		int startAt = 0;
		int howMany = 50;
		SearchAndReconcileResults queryResult = null;

		do {

			String nextPageToken = queryResult != null ? queryResult.getNextPageToken() : null;
			Integer maxResults = howMany;
			List<String> fields = JiraNavigationUtil.ISSUES_FIELDS;
			String expand = "names,schema,transitions";
			List<String> properties = null;
			Boolean fieldsByKeys = true;
			Boolean failFast = true;
			List<Long> reconcileIssues = null;
			queryResult = searchApi.searchAndReconsileIssuesUsingJql(jql, nextPageToken, maxResults, fields, expand,
					properties, fieldsByKeys, failFast, reconcileIssues);
			if (queryResult != null && queryResult.getIssues() != null) {
				for (IssueBean issue : queryResult.getIssues()) {
					NativeCoordinatePointer nativePointer = new NativeCoordinatePointer();
					nativePointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
					nativePointer.child = new JiraNativePositionObject();
					nativePointer.child.setIssueAsFolder(issue);
					childs.add(nativePointer);
				}
			}
		} while (queryResult != null && queryResult.getIssues() != null && !queryResult.getIssues().isEmpty()
				&& queryResult.getNextPageToken() != null);
		return childs;
	}

	/**
	 * Retrieves attachment metadata from an issue
	 * 
	 * @param issueAsFolder Issue bean
	 * @param nativeCoordinates Parent coordinates
	 * @return List of attachment coordinate pointers
	 */
	private List<NativeCoordinatePointer> readAttachmentsMetaInfos(IssueBean issueAsFolder,
			List<JiraNativePositionObject> nativeCoordinates) {
		List<NativeCoordinatePointer> childs = new ArrayList<>();
		Object attachment = issueAsFolder.getFields() != null ? issueAsFolder.getFields().get("attachment") : null;
		if (attachment != null && attachment instanceof Collection) {
			Collection collection = (Collection) attachment;
			for (Object entry : collection) {
				if (entry instanceof LinkedHashMap) {
					LinkedHashMap<String, Object> value = (LinkedHashMap<String, Object>) entry;
					if (!value.isEmpty()) {
						NativeCoordinatePointer pointer = new NativeCoordinatePointer();
						pointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
						pointer.child = new JiraNativePositionObject();
						pointer.child.setAttachment(new JiraAttachment(value));
						childs.add(pointer);
					}
				}
			}
		}
		return childs;
	}

	/**
	 * Converts navigation coordinates to native coordinates
	 * 
	 * @param position Navigation coordinates
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param root Root virtual folder
	 * @param consumer Content consumer
	 * @param messagesConsumer User messages consumer
	 * @param errorConsumer Error consumer
	 * @param environment Environment data
	 * @return List of native position objects
	 * @throws GeboContentHandlerSystemException If an error occurs during conversion
	 */
	@Override
	protected List<JiraNativePositionObject> toNativeCoordinates(JiraNavigationCoordinates position, GJiraSystem system,
			GJiraProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		List<JiraNativePositionObject> nativePositions = new ArrayList<>();
		ApiClient apiClient = (ApiClient) environment.get(JIRA_CLIENT);
		ProjectsApi projectApi = new ProjectsApi(apiClient);
		IssuesApi issueApi = new IssuesApi(apiClient);
		String projectKey = JiraNavigationUtil.getProjectCode(position.getRoot());
		Project project = projectApi.getProject(projectKey, null, null);
		if (project == null)
			return List.of();
		JiraNativePositionObject nativeRoot = new JiraNativePositionObject();
		nativeRoot.setProject(project);
		nativePositions.add(nativeRoot);

		for (JiraPathComponent pos : position.getBrowsingStepsCustom()) {
			JiraNativePositionObject nativePosition = new JiraNativePositionObject();
			IssueBean issue = issueApi.getIssue(pos.id, JiraNavigationUtil.ISSUES_FIELDS, null, null, null, null, null);
			if (issue == null)
				return List.of();
			nativePosition.setIssueAsFolder(issue);
			nativePositions.add(nativePosition);
		}
		return nativePositions;
	}

	/**
	 * Creates an environment for API operations
	 * 
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param errorConsumer Error consumer
	 * @return Environment map
	 * @throws GeboContentHandlerSystemException If an error occurs during creation
	 */
	@Override
	protected Map<String, Object> createEnvironment(GJiraSystem system, GJiraProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<>();
		try {
			environment.put(JIRA_CLIENT, this.apiClientFactory.getApiClient(system));
			return environment;
		} catch (GeboCryptSecretException e) {
			throw new GeboContentHandlerSystemException("Cannot instantiate jira-client for credentials secret issues",
					e);
		}

	}

	/**
	 * Clears the environment after operations
	 * 
	 * @param environment Environment map
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @throws GeboContentHandlerSystemException If an error occurs during clearing
	 */
	@Override
	protected void clearEnvironment(Map<String, Object> environment, GJiraSystem system, GJiraProjectEndpoint endpoint)
			throws GeboContentHandlerSystemException {
		environment.clear();

	}

	/**
	 * Converts a filesystem reference to navigation coordinates
	 * 
	 * @param path Filesystem reference
	 * @return Navigation coordinates
	 * @throws GeboContentHandlerSystemException If an error occurs during conversion
	 */
	@Override
	protected JiraNavigationCoordinates toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException {

		return JiraNavigationUtil.toJiraNavigationCoordinats(path);
	}

	/**
	 * Provides a description for a Jira object
	 * 
	 * @param references List of native position objects
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param environment Environment data
	 * @return Description string
	 */
	@Override
	protected String describeObject(List<JiraNativePositionObject> references, GJiraSystem system,
			GJiraProjectEndpoint endpoint, Map<String, Object> environment) {
		if (!references.isEmpty()) {
			JiraNativePositionObject ref = references.get(references.size() - 1);
			if (ref.isIssueFolder()) {
				return "Issue " + ref.getName();
			}
			if (ref.isProject()) {
				return "Project " + ref.getName();
			}
		}
		return null;
	}

	/**
	 * Provides a description for a Jira system
	 * 
	 * @param system The Jira system
	 * @return Description string
	 */
	@Override
	protected String describeSystem(GJiraSystem system) {

		return "Jira system " + system.getDescription();
	}

	/**
	 * Provides a description for a Jira project endpoint
	 * 
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param environment Environment data
	 * @return Description string
	 */
	@Override
	protected String describeProjectEndpoint(GJiraSystem system, GJiraProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "Data source " + system.getDescription();
	}

	/**
	 * Verifies if a remote object exists
	 * 
	 * @param system The Jira system
	 * @param endpoint The Jira project endpoint
	 * @param doc Virtual filesystem object
	 * @param reference Resource reference
	 * @param environment Environment data
	 * @return The verified virtual filesystem object or null
	 * @throws GeboContentHandlerSystemException If an error occurs during verification
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GJiraSystem system,
			GJiraProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, JiraResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}