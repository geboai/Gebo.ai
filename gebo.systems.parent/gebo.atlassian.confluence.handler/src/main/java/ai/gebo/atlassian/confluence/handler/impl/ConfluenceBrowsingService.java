/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceContentApi;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentsList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceFullContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceSystemRepository;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceContentApi;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentsList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseFullContent;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.GeboNotFoundException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * Service implementation that provides browsing capabilities for Confluence systems.
 * This service handles both Cloud and On-Premise Confluence versions, allowing users
 * to navigate through spaces, pages, and attachments.
 */
@Service
public class ConfluenceBrowsingService implements IGVirtualFilesystemBrowsingService<ConfluenceBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceBrowsingService.class);
	
	@Autowired
	protected ConfluenceSystemRepository systemsRepo;
	
	@Autowired
	protected ConfluenceConnectionFactory connectionFactory;

	/**
	 * Default constructor for the ConfluenceBrowsingService.
	 */
	public ConfluenceBrowsingService() {

	}

	/**
	 * Retrieves a Confluence system by its ID.
	 * 
	 * @param id The ID of the Confluence system to retrieve
	 * @return The Confluence system instance
	 * @throws IllegalStateException if the system is not found
	 */
	private GConfluenceSystem get(String id) {
		Optional<GConfluenceSystem> r = systemsRepo.findById(id);
		if (r.isPresent())
			return r.get();
		throw new IllegalStateException("Unkown system=>" + id);
	}

	/**
	 * Retrieves the list of root spaces from the specified Confluence system.
	 * Supports both Cloud and On-Premise Confluence instances.
	 * 
	 * @param systemCode The ID of the Confluence system
	 * @return Operation status containing the list of filesystem roots
	 * @throws VirtualFilesystemBrowsingException if browsing fails
	 */
	private OperationStatus<List<GVirtualFilesystemRoot>> getRoots(String systemCode)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> contents = new ArrayList<GVirtualFilesystemRoot>();
		try {

			GConfluenceSystem system = get(systemCode);
			switch (system.getConfluenceVersion()) {
			case ONPREMISE7X: {
				OnPremiseConfluenceConnection connection = connectionFactory.getOnPremiseConnection(system);
				OnPremiseConfluenceSpaceApi spaceApi = new OnPremiseConfluenceSpaceApi(connection);

				int init = 1;
				int limit = 500;
				OnPremiseConfluenceSpacesList spaces = null;
				do {
					spaces = spaceApi.getAllSpaces(init, limit, null);

					for (OnPremiseConfluenceSpacesListItem s : spaces.getResults()) {
						GVirtualFilesystemRoot item = ConfluenceNavigationUtil.encodeSpace(s.getKey(), s.getName(),
								s.get_expandable());
						contents.add(item);
					}
					init += limit;
				} while (spaces != null && spaces.getResults().size() == limit);
			}
				break;
			case CLOUD: {
				CloudConfluenceConnection connection = connectionFactory.getCloudConnection(system);
				int init = 1;
				int limit = 500;
				CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(connection);
				CloudConfluenceSpacesList spaces = null;
				do {
					spaces = spaceApi.getAllSpaces(init, limit, null);
					for (CloudConfluenceSpacesListItem s : spaces.getResults()) {
						GVirtualFilesystemRoot item = ConfluenceNavigationUtil.encodeSpace(s.getKey(), s.getName(),
								s.get_expandable());
						contents.add(item);
					}
					init += limit;
				} while (spaces != null && spaces.getResults().size() == limit);
			}
				break;
			}
		} catch (GeboCryptSecretException exc) {
			throw new VirtualFilesystemBrowsingException("Cryptation problem", exc);
		} catch (GeboRestIntegrationException exc) {
			GUserMessage message = RestTemplateWrapperService.toMessage(exc, " Confluence ", "spaces list");
			OperationStatus<List<GVirtualFilesystemRoot>> status = OperationStatus.of(contents);
			status.getMessages().clear();
			status.getMessages().add(message);
			return status;

		}
		return OperationStatus.of(contents);
	}

	/**
	 * Browses a specific path in the Confluence system, retrieving contents like pages and attachments.
	 * Handles both space-level browsing and sub-page navigation.
	 * 
	 * @param param The browsing parameters including path and root information
	 * @param systemCode The ID of the Confluence system
	 * @return Operation status containing the list of path information
	 * @throws VirtualFilesystemBrowsingException if browsing fails
	 */
	private OperationStatus<List<PathInfo>> browsePath(BrowseParam param, String systemCode)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> paths = new ArrayList<PathInfo>();
		try {

			GConfluenceSystem system = get(systemCode);
			switch (system.getConfluenceVersion()) {
			case ONPREMISE7X: {
				OnPremiseConfluenceConnection connection = connectionFactory.getOnPremiseConnection(system);
				OnPremiseConfluenceSpaceApi spaceApi = new OnPremiseConfluenceSpaceApi(connection);
				OnPremiseConfluenceContentApi browser = new OnPremiseConfluenceContentApi(connection);

				int init = 1;
				int limit = 500;

				String parentContentId = null;

				PathInfo path = param.path;
				GVirtualFilesystemRoot root = param.root;
				if (path == null) {

					OnPremiseConfluenceSpacesList spacesList = spaceApi.getSpace(root.getCode());
					if (spacesList.getResults().size() == 1) {
						OnPremiseConfluenceSpacesListItem space = spacesList.getResults().get(0);
						if (space.getHomepage() != null) {
							paths.add(ConfluenceNavigationUtil.encodeAsPage(space.getHomepage()));
							paths.add(ConfluenceNavigationUtil.encodeAsFolder(space.getHomepage()));
							break;
						}
					}
					OnPremiseConfluenceContentsList pages = null;
					do {
						pages = browser.getContents(root.getCode(), init, limit);
						for (OnPremiseConfluenceContentItem p : pages.getResults()) {

							PathInfo info = ConfluenceNavigationUtil.encodeAsFolder(p);
							if (param.path != null) {
								info = ConfluenceNavigationUtil.combine(param.path, info);
							}
							paths.add(info);
						}
						init += limit;
					} while (pages != null && pages.getResults().size() == limit);
				} else {
					parentContentId = path.absolutePath.substring(ConfluenceNavigationUtil.SUBPAGES_PREFIX.length());
					OnPremiseFullContent fullContents = browser.getFullContent(parentContentId);
					if (fullContents.getRootContent() != null) {
						paths.add(ConfluenceNavigationUtil.encodeAsPage(fullContents.getRootContent()));
					}
					// ATTACHMENT WILL NOT BE LISTED BECAUSE CANNOT SELECT AND RETRIEVE THEM
					/*
					 * if (fullContents.attachmentsList != null &&
					 * fullContents.attachmentsList.results != null) { for
					 * (OnPremiseConfluenceAttachmentItem attach :
					 * fullContents.attachmentsList.results) {
					 * paths.add(ConfluenceNavigationUtil.encodeAsAttachment(attach)); } }
					 */
					if (fullContents.getChildPagesList() != null
							&& fullContents.getChildPagesList().getResults() != null) {
						for (OnPremiseConfluenceContentItem childpage : fullContents.getChildPagesList().getResults()) {
							paths.add(ConfluenceNavigationUtil.encodeAsPage(childpage));
						}
					}

				}

			}
				break;
			case CLOUD: {
				CloudConfluenceConnection connection = connectionFactory.getCloudConnection(system);
				CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(connection);
				CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
				int init = 1;
				int limit = 500;

				PathInfo path = param.path;
				GVirtualFilesystemRoot root = param.root;
				if (path == null) {

					CloudConfluenceSpacesList spacesList = spaceApi.getSpace(root.getCode());
					if (spacesList.getResults().size() == 1) {
						CloudConfluenceSpacesListItem space = spacesList.getResults().get(0);
						if (space.getHomepage() != null) {
							paths.add(ConfluenceNavigationUtil.encodeAsPage(space.getHomepage()));
							paths.add(ConfluenceNavigationUtil.encodeAsFolder(space.getHomepage()));
							break;
						}
					}
					CloudConfluenceContentsList pages = null;
					do {
						pages = contentApi.getContents(root.getCode(), init, limit);
						for (CloudConfluenceContentItem p : pages.getResults()) {

							PathInfo info = ConfluenceNavigationUtil.encodeAsFolder(p);
							if (param.path != null) {
								info = ConfluenceNavigationUtil.combine(param.path, info);
							}
							paths.add(info);
						}
						init += limit;
					} while (pages != null && pages.getResults().size() == limit);
				} else {
					String parentContentId = path.absolutePath
							.substring(ConfluenceNavigationUtil.SUBPAGES_PREFIX.length());
					CloudConfluenceFullContent fullContents = contentApi.getFullContent(parentContentId);
					if (fullContents.getRootContent() != null) {
						paths.add(ConfluenceNavigationUtil.encodeAsPage(fullContents.getRootContent()));
					}
					if (fullContents.getAttachmentsList() != null
							&& fullContents.getAttachmentsList().getResults() != null) {

						for (CloudConfluenceAttachmentItem attach : fullContents.getAttachmentsList().getResults()) {
							paths.add(ConfluenceNavigationUtil.encodeAsAttachment(attach));
						}
					}
					if (fullContents.getChildPagesList() != null
							&& fullContents.getChildPagesList().getResults() != null) {
						for (CloudConfluenceContentItem childpage : fullContents.getChildPagesList().getResults()) {
							paths.add(ConfluenceNavigationUtil.encodeAsPage(childpage));
						}
					}
				}

			}
				break;
			}
		} catch (GeboCryptSecretException exc) {
			throw new VirtualFilesystemBrowsingException("Cryptation problem", exc);
		} catch (GeboNotFoundException exc) {
			LOGGER.warn("Resource not found on " + param.toString(), exc);
			OperationStatus<List<PathInfo>> result = OperationStatus.of(paths);
			result.getMessages().clear();
			result.getMessages().add(GUserMessage.warnMessage("Confluence path with no object found", ""));
			return result;
		} catch (GeboRestIntegrationException exc) {
			GUserMessage message = RestTemplateWrapperService.toMessage(exc, " Confluence ", " pages ");
			OperationStatus<List<PathInfo>> status = OperationStatus.of(paths);
			status.getMessages().clear();
			status.getMessages().add(message);
			return status;
		}
		return OperationStatus.of(paths);
	}

	/**
	 * Implements the interface method to retrieve Confluence root spaces.
	 * 
	 * @param context The Confluence browsing context containing the system code
	 * @return Operation status containing the list of filesystem roots
	 * @throws VirtualFilesystemBrowsingException if browsing fails
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(ConfluenceBrowsingContext context)
			throws VirtualFilesystemBrowsingException {

		return getRoots(context.getSystemCode());
	}

	/**
	 * Implements the interface method to browse a specific path in Confluence.
	 * 
	 * @param param The browsing parameters
	 * @param context The Confluence browsing context
	 * @return Operation status containing the list of path information
	 * @throws VirtualFilesystemBrowsingException if browsing fails
	 */
	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, ConfluenceBrowsingContext context)
			throws VirtualFilesystemBrowsingException {

		return browsePath(param, context.getSystemCode());
	}

	/**
	 * Indicates whether this service supports navigation status.
	 * 
	 * @return false as navigation status is not supported
	 */
	@Override
	public boolean isSupportsNavigationStatus() {

		return false;
	}

	/**
	 * Gets the parent reference for a given filesystem reference.
	 * 
	 * @param reference The filesystem reference
	 * @param context The Confluence browsing context
	 * @return The parent reference
	 * @throws VirtualFilesystemBrowsingException if browsing fails
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, ConfluenceBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		// TODO Auto-generated method stub
		return null;
	}

}