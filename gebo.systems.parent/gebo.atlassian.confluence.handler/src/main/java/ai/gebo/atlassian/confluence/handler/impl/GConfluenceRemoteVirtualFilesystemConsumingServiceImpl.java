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
 * This service manages interactions with Confluence systems by providing virtual filesystem support.
 * It allows navigating and accessing Confluence content (spaces, pages, attachments) through
 * a filesystem-like interface.
 */
package ai.gebo.atlassian.confluence.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceAttachmentApi;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceContentApi;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentsList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceFullContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceListItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceVirtualFilesystemConsumingService;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNativePositionObject;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNavigationCoordinates;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluencePathComponent;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluencePathNodeType;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResourceReference;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceAttachmentApi;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceContentApi;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContent;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentsList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseFullContent;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.GeboNotFoundException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;
import jakarta.el.MethodNotFoundException;

/**
 * Implementation of the Confluence virtual filesystem service.
 * Provides access to Confluence content through a virtual filesystem interface,
 * supporting both cloud and on-premise Confluence instances.
 */
@Service
public class GConfluenceRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GConfluenceSystem, GConfluenceProjectEndpoint, ConfluenceNativePositionObject, ConfluenceNavigationCoordinates, ConfluenceResourceReference>
		implements IGConfluenceVirtualFilesystemConsumingService {
	protected final ConfluenceConnectionFactory connectionFactory;

	/**
	 * Constructor for the service.
	 * 
	 * @param documentFactory Factory for creating document references
	 * @param connectionFactory Factory for creating Confluence connections
	 */
	public GConfluenceRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			ConfluenceConnectionFactory connectionFactory) {
		super(documentFactory);

		this.connectionFactory = connectionFactory;

	}

	/**
	 * Helper method to safely retrieve a value from the cache.
	 * 
	 * @param constant The key to look up in the cache
	 * @param cache The cache to search
	 * @return The value as a string, or null if not found
	 */
	private String getId(String constant, Map<String, Object> cache) {
		return cache.containsKey(constant) ? cache.get(constant).toString() : null;
	}

	/**
	 * Creates a resource handle from a virtual filesystem object.
	 * 
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param reference The virtual filesystem object
	 * @param cache A cache for storing metadata
	 * @return A Confluence resource reference
	 * @throws GeboContentHandlerSystemException If metadata is missing or invalid
	 */
	@Override
	public ConfluenceResourceReference getResourceHandle(GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		ConfluenceResourceReference obj = new ConfluenceResourceReference();
		Map<String, Object> metainfos = reference.getCustomMetaInfos();
		if (metainfos == null)
			throw new GeboContentHandlerSystemException("customMetaInfos not present in document reference");
		if (metainfos.isEmpty()) {
			LOGGER.error("Reference :" + reference.getCode() + " with empty customMetaInfos");
		}
		obj.resourceType = getId(ConfluenceNativePositionObject.CONFLUENCE_RESOURCE_METAINFO, metainfos);
		obj.confluenceVersion = getId(ConfluenceNativePositionObject.CONFLUENCE_VERSION_METAINFO, metainfos);
		obj.spaceId = getId(ConfluenceNativePositionObject.CONFLUENCE_SPACE_ID_METAINFO, metainfos);
		obj.pageId = getId(ConfluenceNativePositionObject.CONFLUENCE_CONTENT_ID_METAINFO, metainfos);
		obj.attachmentTitle = getId(ConfluenceNativePositionObject.CONFLUENCE_ATTACHMENT_TITLE_METAINFO, metainfos);
		obj.parentPageId = getId(ConfluenceNativePositionObject.CONFLUENCE_CONTENT_PARENT_METAINFO, metainfos);
		obj.attachmentId = getId(ConfluenceNativePositionObject.CONFLUENCE_ATTACHMENT_ID_METAINFO, metainfos);
		return obj;
	}

	/****************************************************************************************************************
	 * Streams a page or an attachment content
	 * 
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param reference The resource reference
	 * @param cache A cache for storing metadata
	 * @return An input stream containing the content
	 * @throws GeboContentHandlerSystemException If the resource cannot be accessed
	 * @throws IOException If there is an I/O error
	 */
	@Override
	public InputStream streamResource(GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			ConfluenceResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		String resourceType = reference.resourceType;
		String pageId = reference.pageId;
		String attachmentId = reference.attachmentId;
		String attachmentTitle = reference.attachmentTitle;
		String parentPageId = reference.parentPageId;
		if (resourceType == null) {
			final String msg = "Reference=>" + reference + " has no resource type";
			LOGGER.error(msg);
			throw new GeboContentHandlerSystemException(msg);
		}
		ConfluencePathNodeType type = ConfluencePathNodeType.valueOf(resourceType);
		if (!cache.containsKey(CONFLUENCE_CONNECTION)) {

			Map<String, Object> environment = createEnvironment(system, endpoint,
					IGContentsAccessErrorConsumer.defaultImplementation());
			cache.putAll(environment);
		}
		Object confluenceBrowser = null;
		switch (system.getConfluenceVersion()) {
		case CLOUD: {
			confluenceBrowser = getCloudConnection(cache);
		}
			break;
		case ONPREMISE7X: {
			confluenceBrowser = getOnPremiseConnection(cache);
		}
			break;
		}
		try {
			switch (type) {
			case PAGE: {
				if (pageId == null) {
					throw new GeboContentHandlerSystemException(
							"Resource=>" + reference + " is a PAGE but has no pageId");
				}
				if (confluenceBrowser instanceof CloudConfluenceConnection) {
					CloudConfluenceConnection connection = (CloudConfluenceConnection) confluenceBrowser;
					CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
					CloudConfluenceContent content = contentApi.getContent(pageId);
					if (content.getBody() != null && content.getBody().getView() != null
							&& content.getBody().getView().getValue() != null) {
						return new ByteArrayInputStream(content.getBody().getView().getValue().getBytes());
					}
				} else if (confluenceBrowser instanceof OnPremiseConfluenceConnection) {
					OnPremiseConfluenceConnection connection = (OnPremiseConfluenceConnection) confluenceBrowser;
					OnPremiseConfluenceContentApi browser = new OnPremiseConfluenceContentApi(connection);
					OnPremiseConfluenceContent content = browser.getContent(pageId);
					if (content.getBody() != null && content.getBody().getView() != null
							&& content.getBody().getView().getValue() != null) {
						return new ByteArrayInputStream(content.getBody().getView().getValue().getBytes());
					}
				}

			}
				;
				break;
			case ATTACHMENT: {
				if (parentPageId == null || attachmentTitle == null) {
					throw new GeboContentHandlerSystemException(
							"Resource=>" + reference + " is an ATTACHMENT but has no parentPageId or attachmentTitle");
				}
				if (confluenceBrowser instanceof CloudConfluenceConnection) {
					CloudConfluenceConnection connection = (CloudConfluenceConnection) confluenceBrowser;
					CloudConfluenceAttachmentApi attachmentApi = new CloudConfluenceAttachmentApi(connection);
					return attachmentApi.getAttachmentContent(parentPageId, attachmentTitle);
				} else if (confluenceBrowser instanceof OnPremiseConfluenceConnection) {
					OnPremiseConfluenceConnection connection = (OnPremiseConfluenceConnection) confluenceBrowser;
					OnPremiseConfluenceAttachmentApi attachmentApi = new OnPremiseConfluenceAttachmentApi(connection);
					return attachmentApi.getAttachmentContent(parentPageId, attachmentTitle);
				}
			}

			}

		} catch (GeboRestIntegrationException e) {
			throw new GeboContentHandlerSystemException("Rest Confluence comunication exception", e);
		}
		LOGGER.warn("Content:" + reference.toString() + " returning nullInputStream()");
		return InputStream.nullInputStream();
	}

	/**
	 * Returns the ID of the messaging module used by this service.
	 * 
	 * @return The module ID string
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ATLASSIAN_CONFLUENCE_MODULE;
	}

	/**
	 * Converts a list of native position objects to navigation coordinates.
	 * 
	 * @param childCoordinates The list of native position objects
	 * @return Navigation coordinates
	 * @throws GeboContentHandlerSystemException If conversion fails
	 */
	@Override
	protected ConfluenceNavigationCoordinates getPositionCoordinate(
			List<ConfluenceNativePositionObject> childCoordinates) throws GeboContentHandlerSystemException {

		return ConfluenceNavigationUtil.toNavigationCoordinates(childCoordinates);
	}

	/********************************************************************************************************************
	 * Retrieve childs of the actual reference, basically childs are
	 * 
	 * @param nativeCoordinates The native coordinates
	 * @param position The navigation position
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param messagesConsumer The message consumer
	 * @param environment The environment map
	 * @return A list of native coordinate pointers
	 * @throws GeboContentHandlerSystemException If retrieval fails
	 */
	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<ConfluenceNativePositionObject> nativeCoordinates,
			ConfluenceNavigationCoordinates position, GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		ConfluenceNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		try {
			switch (system.getConfluenceVersion()) {
			case CLOUD: {
				CloudConfluenceConnection connection = getCloudConnection(environment);
				CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
				if (last.isConfluenceSpace()) {
					int startIndex = 0;
					int limit = 400;
					List<CloudConfluenceListItem> items = new ArrayList<CloudConfluenceListItem>();
					CloudConfluenceContentsList contents = null;
					do {
						contents = contentApi.getContents(last.getCode(), startIndex, limit);
						startIndex += limit;
						if ((contents != null && !contents.getResults().isEmpty())) {
							items.addAll(contents.getResults());
						}
					} while (contents != null && !contents.getResults().isEmpty());
					childs.addAll(toPointersWithChildFolders(items, nativeCoordinates, contentApi));
				} else if (last.isConfluenceSuperPage()) {
					CloudConfluenceFullContent fullContent = contentApi.getFullContent(last.getCode());
					childs.addAll(childTree(fullContent, nativeCoordinates, contentApi));

				} else if (last.isConfluencePage()) {
				} else if (last.isConfluenceAttachment()) {
				}
			}
				break;
			case ONPREMISE7X: {
				OnPremiseConfluenceConnection connection = getOnPremiseConnection(environment);
				OnPremiseConfluenceContentApi browser = new OnPremiseConfluenceContentApi(connection);
				if (last.isConfluenceSpace()) {
					int startIndex = 0;
					int limit = 400;
					List<OnPremiseConfluenceContentItem> items = new ArrayList<OnPremiseConfluenceContentItem>();
					OnPremiseConfluenceContentsList contents = null;
					do {
						contents = browser.getContents(last.getCode(), startIndex, limit);
						startIndex += limit;
						if ((contents != null && !contents.getResults().isEmpty())) {
							items.addAll(contents.getResults());
						}
					} while (contents != null && !contents.getResults().isEmpty());
					childs.addAll(toPointersWithChildFolders(items, nativeCoordinates, browser));
				} else if (last.isConfluenceSuperPage()) {
					OnPremiseFullContent fullContent = browser.getFullContent(last.getCode());
					childs.addAll(childTree(fullContent, nativeCoordinates, browser));
				} else if (last.isConfluencePage()) {
				} else if (last.isConfluenceAttachment()) {
				}
			}
				break;
			}
		} catch (GeboNotFoundException exc) {
			LOGGER.warn("Opening of " + (last != null ? last.getName() : " NULL ") + " with code: "
					+ (last != null ? last.getCode() : " NULL ") + " returns 404");
			return childs;
		} catch (GeboRestIntegrationException exc) {
			throw new GeboContentHandlerSystemException("Exception accessing confluence system", exc);
		}
		return childs;
	}

	/**
	 * Creates a list of coordinate pointers from an on-premise full content.
	 * 
	 * @param fullContent The full content
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> childTree(OnPremiseFullContent fullContent,
			List<ConfluenceNativePositionObject> nativeCoordinates, OnPremiseConfluenceContentApi browser) {
		List<NativeCoordinatePointer> pointers = new ArrayList<NativeCoordinatePointer>();
		if (fullContent.getRootContent() != null) {
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			pointer.child = new ConfluenceNativePositionObject();
			pointer.child.setOnPremiseConfluenceContent(fullContent.getRootContent());
			pointers.add(pointer);
			if (fullContent.getAttachmentsList() != null && fullContent.getAttachmentsList().getResults() != null) {
				for (OnPremiseConfluenceAttachmentItem attach : fullContent.getAttachmentsList().getResults()) {
					pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
					pointer.child = new ConfluenceNativePositionObject();
					pointer.child.setOnPremiseConfluenceAttachment(attach);
					pointers.add(pointer);
				}
			}
			if (fullContent.getChildPagesList() != null && fullContent.getChildPagesList().getResults() != null) {
				for (OnPremiseConfluenceContentItem page : fullContent.getChildPagesList().getResults()) {
					pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
					pointer.child = new ConfluenceNativePositionObject();
					pointer.child.setOnPremiseConfluenceListItemChildrens(page);
					pointers.add(pointer);
				}
			}
		}
		return pointers;
	}

	/**
	 * Creates a list of coordinate pointers from a cloud full content.
	 * 
	 * @param fullContent The full content
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> childTree(CloudConfluenceFullContent fullContent,
			List<ConfluenceNativePositionObject> nativeCoordinates, CloudConfluenceContentApi browser) {
		List<NativeCoordinatePointer> pointers = new ArrayList<NativeCoordinatePointer>();
		if (fullContent.getRootContent() != null) {
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			pointer.child = new ConfluenceNativePositionObject();
			pointer.child.setCloudConfluenceContent(fullContent.getRootContent());
			pointers.add(pointer);
			if (fullContent.getAttachmentsList() != null && fullContent.getAttachmentsList().getResults() != null) {
				for (CloudConfluenceAttachmentItem attach : fullContent.getAttachmentsList().getResults()) {
					pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
					pointer.child = new ConfluenceNativePositionObject();
					pointer.child.setCloudConfluenceAttachment(attach);
					pointers.add(pointer);
				}
			}
			if (fullContent.getChildPagesList() != null && fullContent.getChildPagesList().getResults() != null) {
				for (CloudConfluenceContentItem page : fullContent.getChildPagesList().getResults()) {
					pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
					pointer.child = new ConfluenceNativePositionObject();
					pointer.child.setCloudConfluenceItemChildrens(page);
					pointers.add(pointer);
				}
			}
		}
		return pointers;
	}

	/**
	 * Converts a list of on-premise content items to native coordinate pointers.
	 * 
	 * @param items The content items
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> toPointersWithChildFolders(List<OnPremiseConfluenceContentItem> items,
			List<ConfluenceNativePositionObject> nativeCoordinates, OnPremiseConfluenceContentApi browser) {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		for (OnPremiseConfluenceContentItem item : items) {
			/*
			 * NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			 * pointer.parentCoordinates = new
			 * ArrayList<ConfluenceNativePositionObject>(nativeCoordinates); pointer.child =
			 * new ConfluenceNativePositionObject();
			 * pointer.child.setOnPremiseConfluencePage(item); childs.add(pointer);
			 */
			ConfluenceNativePositionObject parent = new ConfluenceNativePositionObject();
			parent.setOnPremiseConfluenceListItemChildrens(item);
			NativeCoordinatePointer parentPointer = new NativeCoordinatePointer();
			parentPointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			parentPointer.child = parent;
			childs.add(parentPointer);

		}
		return childs;

	}

	/**
	 * Converts a list of cloud content items to native coordinate pointers.
	 * 
	 * @param items The content items
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> toPointersWithChildFolders(List<CloudConfluenceListItem> items,
			List<ConfluenceNativePositionObject> nativeCoordinates, CloudConfluenceContentApi browser) {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		for (CloudConfluenceListItem item : items) {
			/*
			 * NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			 * pointer.parentCoordinates = new
			 * ArrayList<ConfluenceNativePositionObject>(nativeCoordinates); pointer.child =
			 * new ConfluenceNativePositionObject();
			 * pointer.child.setCloudConfluencePage(item); childs.add(pointer);
			 */
			ConfluenceNativePositionObject parent = new ConfluenceNativePositionObject();
			parent.setCloudConfluenceItemChildrens(item);
			NativeCoordinatePointer parentPointer = new NativeCoordinatePointer();
			parentPointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			parentPointer.child = parent;
			childs.add(parentPointer);
		}
		return childs;
	}

	/**
	 * Creates a list of coordinate pointers from on-premise content items and attachments.
	 * 
	 * @param items The content items
	 * @param attachments The attachment items
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> toPointersWithAttachments(List<OnPremiseConfluenceContentItem> items,
			List<OnPremiseConfluenceAttachmentItem> attachments, List<ConfluenceNativePositionObject> nativeCoordinates,
			OnPremiseConfluenceContentApi browser) {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		for (OnPremiseConfluenceContentItem item : items) {
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			pointer.child = new ConfluenceNativePositionObject();
			pointer.child.setOnPremiseConfluencePage(item);
			childs.add(pointer);

			for (OnPremiseConfluenceAttachmentItem attachItem : attachments) {
				NativeCoordinatePointer attachPointer = new NativeCoordinatePointer();
				attachPointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
				attachPointer.child = new ConfluenceNativePositionObject();
				attachPointer.child.setOnPremiseConfluenceAttachment(attachItem);
				childs.add(attachPointer);
			}

		}
		return childs;

	}

	/**
	 * Creates a list of coordinate pointers from cloud content items and attachments.
	 * 
	 * @param items The content items
	 * @param attachments The attachment items
	 * @param nativeCoordinates The native coordinates
	 * @param browser The content API
	 * @return A collection of native coordinate pointers
	 */
	private Collection<NativeCoordinatePointer> toPointersWithAttachments(List<CloudConfluenceListItem> items,
			List<CloudConfluenceAttachmentItem> attachments, List<ConfluenceNativePositionObject> nativeCoordinates,
			CloudConfluenceContentApi browser) {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		for (CloudConfluenceListItem item : items) {
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
			pointer.child = new ConfluenceNativePositionObject();
			pointer.child.setCloudConfluencePage(item);
			childs.add(pointer);
			for (CloudConfluenceAttachmentItem attachItem : attachments) {
				NativeCoordinatePointer attachPointer = new NativeCoordinatePointer();
				attachPointer.parentCoordinates = new ArrayList<ConfluenceNativePositionObject>(nativeCoordinates);
				attachPointer.child = new ConfluenceNativePositionObject();
				attachPointer.child.setCloudConfluenceAttachment(attachItem);
				childs.add(attachPointer);
			}

		}
		return childs;
	}

	/**
	 * Converts navigation coordinates to native coordinates.
	 * 
	 * @param position The navigation position
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param root The virtual folder root
	 * @param consumer The content consumer
	 * @param messagesConsumer The message consumer
	 * @param errorConsumer The error consumer
	 * @param environment The environment map
	 * @return A list of native position objects
	 * @throws GeboContentHandlerSystemException If conversion fails
	 */
	@Override
	protected List<ConfluenceNativePositionObject> toNativeCoordinates(ConfluenceNavigationCoordinates position,
			GConfluenceSystem system, GConfluenceProjectEndpoint endpoint, GVirtualFolder root,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<ConfluenceNativePositionObject> natives = new ArrayList<ConfluenceNativePositionObject>();
		try {
			ConfluenceNativePositionObject _root = loadRoot(position.getRoot(), system, endpoint, messagesConsumer,
					environment);
			if (_root == null)
				return natives;
			natives.add(_root);
		} catch (GeboRestIntegrationException e) {
			errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			throw new GeboContentHandlerSystemException("Cannot load this item", e);
		}
		try {
			if (position.getBrowsingStepsCustom() != null) {
				for (ConfluencePathComponent customitem : position.getBrowsingStepsCustom()) {
					ConfluenceNativePositionObject thisPathEntry = loadPathComponent(customitem, natives, system,
							endpoint, messagesConsumer, environment);
					if (thisPathEntry == null) {
						LOGGER.error("The path item:" + customitem.type + " id=" + customitem.id
								+ " does not lead to a loadable entity");
					}
					natives.add(thisPathEntry);
				}
			}
		} catch (GeboRestIntegrationException e) {
			errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			throw new GeboContentHandlerSystemException("Cannot load this item", e);
		}
		return natives;
	}

	/**
	 * Loads a path component from the Confluence system.
	 * 
	 * @param customitem The path component
	 * @param natives The list of native position objects
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param messagesConsumer The message consumer
	 * @param environment The environment map
	 * @return A native position object
	 * @throws GeboRestIntegrationException If loading fails
	 */
	private ConfluenceNativePositionObject loadPathComponent(ConfluencePathComponent customitem,
			List<ConfluenceNativePositionObject> natives, GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboRestIntegrationException {
		ConfluenceNativePositionObject nativeObject = new ConfluenceNativePositionObject();
		switch (system.getConfluenceVersion()) {

		case CLOUD: {
			CloudConfluenceConnection connection = getCloudConnection(environment);
			CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
			switch (customitem.type) {
			case PAGE: {
				CloudConfluenceContent page = contentApi.getContent(customitem.id);
				nativeObject.setCloudConfluenceContent(page);
			}
				break;
			case PAGE_CONTAINER: {
				CloudConfluenceContent page = contentApi.getContent(customitem.id);
				nativeObject.setCloudConfluenceContentAsContainer(page);
			}
				break;
			case ATTACHMENT: {
				CloudConfluenceAttachmentApi attachmentApi = new CloudConfluenceAttachmentApi(connection);
				CloudConfluenceAttachmentItem attachent = attachmentApi.getAttachment(customitem.id);
				nativeObject.setCloudConfluenceAttachment(attachent);
			}

			case SPACE: {
				CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(connection);
				CloudConfluenceSpacesList space = spaceApi.getSpace(customitem.id);
				if (space != null && !space.getResults().isEmpty())
					nativeObject.setCloudConfluenceSpace(space.getResults().get(0));
				else
					throw new GeboRestIntegrationException(
							"Space " + customitem.id + " does not exist in the remote system");
			}
				break;
			}
		}
			break;
		case ONPREMISE7X: {
			OnPremiseConfluenceConnection connection = getOnPremiseConnection(environment);
			OnPremiseConfluenceContentApi browser = new OnPremiseConfluenceContentApi(connection);
			OnPremiseConfluenceSpaceApi spaceApi = new OnPremiseConfluenceSpaceApi(connection);
			switch (customitem.type) {
			case PAGE: {

				OnPremiseConfluenceContent page = browser.getContent(customitem.id);
				nativeObject.setOnPremiseConfluenceContent(page);
			}
				break;
			case PAGE_CONTAINER: {
				OnPremiseConfluenceContent page = browser.getContent(customitem.id);
				nativeObject.setOnPremiseConfluenceContentAsContainer(page);
			}
				break;
			case ATTACHMENT: {
				throw new MethodNotFoundException("Attachment " + customitem.id + " not yet loadable explicitely");
			}
			case SPACE: {
				OnPremiseConfluenceSpacesList space = spaceApi.getSpace(customitem.id);
				if (space != null && !space.getResults().isEmpty())
					nativeObject.setOnPremiseConfluenceSpace(space.getResults().get(0));
				else
					throw new GeboRestIntegrationException(
							"Space " + customitem.id + " does not exist in the remote system");
			}
				break;
			}
		}
			break;
		}

		return nativeObject;
	}

	/**
	 * Gets an on-premise Confluence connection from the environment.
	 * 
	 * @param environment The environment map
	 * @return The Confluence connection
	 */
	private OnPremiseConfluenceConnection getOnPremiseConnection(Map<String, Object> environment) {
		return (OnPremiseConfluenceConnection) environment.get(CONFLUENCE_CONNECTION);
	}

	/**
	 * Gets a cloud Confluence connection from the environment.
	 * 
	 * @param environment The environment map
	 * @return The Confluence connection
	 */
	private CloudConfluenceConnection getCloudConnection(Map<String, Object> environment) {
		return (CloudConfluenceConnection) environment.get(CONFLUENCE_CONNECTION);
	}

	/**
	 * Loads the root object from the Confluence system.
	 * 
	 * @param root The virtual filesystem root
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param messagesConsumer The message consumer
	 * @param environment The environment map
	 * @return A native position object for the root
	 * @throws GeboRestIntegrationException If loading fails
	 */
	private ConfluenceNativePositionObject loadRoot(GVirtualFilesystemRoot root, GConfluenceSystem system,
			GConfluenceProjectEndpoint endpoint, IGUserMessagesConsumer messagesConsumer,
			Map<String, Object> environment) throws GeboRestIntegrationException {
		ConfluenceNativePositionObject nativeObject = new ConfluenceNativePositionObject();
		if (system.getConfluenceVersion() != null) {
			switch (system.getConfluenceVersion()) {
			case CLOUD: {
				CloudConfluenceConnection connection = getCloudConnection(environment);
				CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(connection);
				CloudConfluenceSpacesList spaces = spaceApi.getSpace(root.getCode());
				if (!spaces.getResults().isEmpty()) {
					CloudConfluenceSpacesListItem space = spaces.getResults().get(0);
					nativeObject.setCloudConfluenceSpace(space);
				} else
					return null;
			}
				break;
			case ONPREMISE7X: {
				OnPremiseConfluenceConnection connection = getOnPremiseConnection(environment);
				OnPremiseConfluenceSpaceApi browser = new OnPremiseConfluenceSpaceApi(connection);
				OnPremiseConfluenceSpacesList spaces = browser.getSpace(root.getCode());
				if (!spaces.getResults().isEmpty()) {
					OnPremiseConfluenceSpacesListItem space = spaces.getResults().get(0);
					nativeObject.setOnPremiseConfluenceSpace(space);
				} else
					return null;
			}
				break;
			}
		}
		return nativeObject;
	}

	/**
	 * Constants used in the environment map.
	 */
	private static final String VERSION_ID = "VERSION_ID";
	private static final String CONFLUENCE_CONNECTION = "CONFLUENCE_CONNECTION";

	/**
	 * Creates an environment for interacting with the Confluence system.
	 * 
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param errorConsumer The error consumer
	 * @return A map containing environment variables
	 * @throws GeboContentHandlerSystemException If environment creation fails
	 */
	@Override
	protected Map<String, Object> createEnvironment(GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(VERSION_ID, system.getConfluenceVersion());
		try {
			switch (system.getConfluenceVersion()) {
			case CLOUD: {
				environment.put(CONFLUENCE_CONNECTION, connectionFactory.getCloudConnection(system));

			}
				break;
			case ONPREMISE7X: {
				environment.put(CONFLUENCE_CONNECTION, connectionFactory.getOnPremiseConnection(system));
			}
				break;
			}
		} catch (Throwable th) {
			LOGGER.error("Cannot allocate confluence connection", th);
			throw new GeboContentHandlerSystemException(th.getMessage(), th);
		}
		return environment;
	}

	/**
	 * Clears the environment map.
	 * 
	 * @param environment The environment map to clear
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @throws GeboContentHandlerSystemException If clearing fails
	 */
	@Override
	protected void clearEnvironment(Map<String, Object> environment, GConfluenceSystem system,
			GConfluenceProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		environment.clear();

	}

	/**
	 * Converts a filesystem reference to navigation coordinates.
	 * 
	 * @param path The filesystem reference
	 * @return Navigation coordinates
	 * @throws GeboContentHandlerSystemException If conversion fails
	 */
	@Override
	protected ConfluenceNavigationCoordinates toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException {
		ConfluenceNavigationCoordinates coordinates = new ConfluenceNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (path.path != null) {
			coordinates.setBrowsingSteps(ConfluenceNavigationUtil.splitPath(path.path));
			coordinates.setBrowsingStepsCustom(ConfluenceNavigationUtil.toCustomSteps(coordinates.getBrowsingSteps()));
		}
		return coordinates;
	}

	/**
	 * Provides a human-readable description of a Confluence object.
	 * 
	 * @param references The list of native position objects
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param environment The environment map
	 * @return A string describing the object
	 */
	@Override
	protected String describeObject(List<ConfluenceNativePositionObject> references, GConfluenceSystem system,
			GConfluenceProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty())
			return "<<Incoherent hierarchy>>";
		ConfluenceNativePositionObject last = references.get(references.size() - 1);
		String objectType = last.isConfluenceAttachment() ? "Page attachment"
				: last.isConfluencePage() ? "Page"
						: last.isConfluenceSuperPage() ? "Page hierarchy" : last.isConfluenceSpace() ? "Space" : "";
		return objectType + " " + last.getName() + " (" + last.getCode() + ")";
	}

	/**
	 * Provides a human-readable description of a Confluence system.
	 * 
	 * @param system The Confluence system
	 * @return A string describing the system
	 */
	@Override
	protected String describeSystem(GConfluenceSystem system) {

		return "Atlassian Confluence (version "
				+ (system.getConfluenceVersion() != null ? system.getConfluenceVersion().name() : "") + ") "
				+ system.getDescription();
	}

	/**
	 * Provides a human-readable description of a Confluence project endpoint.
	 * 
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param environment The environment map
	 * @return A string describing the endpoint
	 */
	@Override
	protected String describeProjectEndpoint(GConfluenceSystem system, GConfluenceProjectEndpoint endpoint,
			Map<String, Object> environment) {

		return "Atlassian Confluence contents source " + endpoint.getDescription();
	}

	/**
	 * Verifies that a remote object exists.
	 * 
	 * @param system The Confluence system
	 * @param endpoint The project endpoint
	 * @param doc The virtual filesystem object
	 * @param reference The resource reference
	 * @param environment The environment map
	 * @return The virtual filesystem object if it exists
	 * @throws GeboContentHandlerSystemException If verification fails
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GConfluenceSystem system,
			GConfluenceProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc,
			ConfluenceResourceReference reference, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}