/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;
import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/***********************************************************************************************************************************
 * Meta implementation for a publisher that will browse the remote system and
 * navigate it while publishing
 * 
 * @param <SystemType> Type of the content management system used
 * @param <EndpointType> Type of the project endpoint
 * @param <ImplementativePositionObjectType> Type of the position object
 * @param <VFSContext> Context for the virtual filesystem
 * @param <VirtualFilesystemBrowser> Browser for virtual filesystem
 * 
 * AI generated comments
 */
public abstract class GAbstractRemoteVirtualFilesystemConsumingService<SystemType extends GContentManagementSystem, EndpointType extends GVirtualFilesystemProjectEndpoint, ImplementativePositionObjectType extends AbstractNativePositionObject, PositionsCoordinateType extends AbstractNavigationCoordinates, ResourceReferenceType extends IGRemoteVirtualFilesystemResourceReference>
		implements IGRemoteVirtualFilesystemConsumingService<SystemType, EndpointType, ResourceReferenceType> {

	/**
	 * Factory for creating document references.
	 */
	protected final IGDocumentReferenceFactory documentFactory;

	/**
	 * Logger for logging purposes.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor initializing with a document reference factory.
	 * 
	 * @param documentFactory Factory for creating document references.
	 */
	public GAbstractRemoteVirtualFilesystemConsumingService(IGDocumentReferenceFactory documentFactory) {
		this.documentFactory = documentFactory;
	}

	/**
	 * Logs an informational step in the process.
	 * 
	 * @param summary Summary of the log.
	 * @param detail Detailed message.
	 * @param messagesConsumer Consumer for user messages.
	 */
	protected void logInfoStep(String summary, String detail, IGUserMessagesConsumer messagesConsumer) {
		messagesConsumer.accept(GUserMessage.infoMessage(summary, detail));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(summary);
		}
	}

	@Override
	public final void consumeAll(SystemType system, EndpointType endpoint, GVirtualFolder root,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		logInfoStep("Accessing for listing resources " + describeSystem(system), "Start accessing remote system",
				messagesConsumer);
		Map<String, Object> environment = createEnvironment(system, endpoint, errorConsumer);
		logInfoStep(
				"System accessed, starting integrating data source:"
						+ describeProjectEndpoint(system, endpoint, environment),
				"Start listing resources", messagesConsumer);

		if (endpoint.getPaths() != null) {
			for (VFilesystemReference path : endpoint.getPaths()) {
				try {
					PositionsCoordinateType position = toNavigationPosition(path);
					List<ImplementativePositionObjectType> nativeCoordinates = toNativeCoordinates(position, system,
							endpoint, root, consumer, messagesConsumer, errorConsumer, environment);
					if (nativeCoordinates != null && !nativeCoordinates.isEmpty()) {
						List<ImplementativePositionObjectType> previusFolderCoordinates = nativeCoordinates.subList(0,
								nativeCoordinates.size() - 1);
						ImplementativePositionObjectType resource = nativeCoordinates.get(nativeCoordinates.size() - 1);
						if (resource.getPath() == null) {
							String msg = "Node=>" + resource.toString() + " does not have an associated path";
							LOGGER.warn(msg);
						} else {
							if (resource.getPath().folder != resource.isFolder()) {
								String msg = "Node=>" + resource.toString()
										+ " folder attributes differs from navigation to native implementation ";
								LOGGER.error(msg);
								throw new GeboContentHandlerSystemException(msg);
							}
							if (resource.getPath().folder && resource.isResource()) {
								String msg = "Node=>" + resource.toString()
										+ " reported to be a folder in navigation but is a resource in implementative object ";
								LOGGER.error(msg);
								throw new GeboContentHandlerSystemException(msg);
							}
						}
						if (resource.isFolder()) {
							logInfoStep(
									"Listing resource(s) of "
											+ describeObject(nativeCoordinates, system, endpoint, environment),
									"Listing resources", messagesConsumer);
							GVirtualFolder parentNode = createAndConsumeVirtualFolderNodes(nativeCoordinates, position,
									system, endpoint, root, consumer, messagesConsumer, errorConsumer, environment);
							consumeChilds(nativeCoordinates, position, system, endpoint, parentNode, consumer,
									messagesConsumer, errorConsumer, environment);
						} else if (resource.isResource()) {
							logInfoStep(
									"Listing resource  "
											+ describeObject(nativeCoordinates, system, endpoint, environment),
									"Listing resource", messagesConsumer);
							GVirtualFolder parentNode = createAndConsumeVirtualFolderNodes(previusFolderCoordinates,
									position, system, endpoint, root, consumer, messagesConsumer, errorConsumer,
									environment);
							createAndConsumeDocumentReference(previusFolderCoordinates, resource, system, endpoint,
									parentNode, consumer, messagesConsumer, errorConsumer, environment);
						} else
							throw new GeboContentHandlerSystemException(
									"The node=>" + resource.toString() + " is not a resource nor a folder");
					} else {
						LOGGER.error("The path: " + path.toString() + " does not lead to a native rappresentation");
					}
				} catch (GeboContentHandlerSystemException exc) {
					LOGGER.error("Exception while managing:" + path.toString(), exc);
				} catch (Throwable th) {
					LOGGER.error("Untrappable Exception while managing:" + path.toString(), th);
					errorConsumer.accept(ContentsAccessError.of(th, null, getMessagingModuleId()));
				}
			}
		}
		clearEnvironment(environment, system, endpoint);
		logInfoStep("End of listing resources through " + describeSystem(system), "End of listing phase",
				messagesConsumer);

	}

	/***************************************
	 * Creates and consumes a specific documentReference from native parent and
	 * resource references and all system parameters, the documentReference must be
	 * with detailed meta infos and urls etc. to let the driver reconstruct how to
	 * stream contents from it
	 * 
	 * @param previusFolderCoordinates List of previous folder coordinates
	 * @param resource The current resource
	 * @param system The current system
	 * @param endpoint The endpoint being used
	 * @param root The root virtual folder
	 * @param consumer The content consumer
	 * @param messagesConsumer The user messages consumer
	 * @param errorConsumer Consumer for access errors
	 * @param environment The operating environment
	 * @return A GDocumentReference representing the consumed document reference
	 */
	protected final GDocumentReference createAndConsumeDocumentReference(
			List<ImplementativePositionObjectType> previusFolderCoordinates, ImplementativePositionObjectType resource,
			SystemType system, EndpointType endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		GVirtualFolder spaceFolder = root;
		GProjectEndpoint projectEndpoint = endpoint;
		String code = resource.getCode();
		String name = resource.getName();
		String contentType = resource.getResourceContentType();
		String url = resource.getUrl();
		Date modificationTimestamp = resource.getResourceModificationTime();
		HashMap<String, Object> meta = resource.getResourceReferenceMetaInfos();
		Long size = resource.getResourceFileSize();
		String moduleId = getMessagingModuleId();
		GDocumentReference dr = this.documentFactory.createWebDocumentReference(spaceFolder, projectEndpoint, code,
				name, contentType, url, modificationTimestamp, meta, moduleId);
		dr.setFileSize(size);
		consumer.accept(dr);
		return dr;
	}

	/**
	 * Inner class that points to a native coordinate.
	 */
	protected class NativeCoordinatePointer {
		/**
		 * Constructor.
		 */
		public NativeCoordinatePointer() {

		}

		/**
		 * List of parent coordinates.
		 */
		public List<ImplementativePositionObjectType> parentCoordinates = null;

		/**
		 * Child coordinate.
		 */
		public ImplementativePositionObjectType child = null;
	}

	/******************************************************************************
	 * Does a recursive listing and consume of all a virtual folder content in the
	 * native system
	 * 
	 * @param nativeCoordinates List of native position coordinates
	 * @param position Current navigation coordinates position
	 * @param system Current system
	 * @param endpoint Current endpoint
	 * @param parentNode Parent node in the virtual filesystem
	 * @param consumer Content consumer
	 * @param messagesConsumer User messages consumer
	 * @param errorConsumer Consumer for access errors
	 * @param environment Operating environment
	 */
	protected final void consumeChilds(List<ImplementativePositionObjectType> nativeCoordinates,
			PositionsCoordinateType position, SystemType system, EndpointType endpoint, GVirtualFolder parentNode,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = null;
		try {
			childs = this.retrieveChilds(nativeCoordinates, position, system, endpoint, messagesConsumer, environment);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieved => " + (childs != null ? childs.size() : 0) + " childs");
			}
		} catch (Throwable th) {
			String msg = "Problem accessing remote system for "
					+ describeProjectEndpoint(system, endpoint, environment);
			LOGGER.error(msg, th);
			messagesConsumer.accept(GUserMessage.errorMessage(msg, th));
			errorConsumer.accept(ContentsAccessError.of(th, ContentsAccessedObjectType.FOLDER, position.toString()));
			return;
		}
		if (childs != null) {
			for (NativeCoordinatePointer nativeCoordinatePointer : childs) {
				List<ImplementativePositionObjectType> completeCoordinates = new ArrayList<ImplementativePositionObjectType>(
						nativeCoordinatePointer.parentCoordinates);
				completeCoordinates.add(nativeCoordinatePointer.child);
				childrenMapsEnrich(completeCoordinates);
				if (nativeCoordinatePointer.child.isResource()) {
					GDocumentReference docreference = createAndConsumeDocumentReference(
							nativeCoordinatePointer.parentCoordinates, nativeCoordinatePointer.child, system, endpoint,
							parentNode, consumer, messagesConsumer, errorConsumer, environment);
					if (LOGGER.isDebugEnabled() && docreference != null) {
						final String msg = "Document reference generated=>" + docreference.getCode();
						LOGGER.debug(msg);
					}
				} else if (nativeCoordinatePointer.child.isFolder()) {
					GVirtualFolder childFolder = createVirtualFolder(nativeCoordinatePointer.child, parentNode, system,
							endpoint);
					consumer.accept(childFolder);
					if (LOGGER.isDebugEnabled() && childFolder != null) {
						final String msg = "VFolder reference generated=>" + childFolder.getCode();
						LOGGER.debug(msg);
					}
					PositionsCoordinateType childPosition = getPositionCoordinate(completeCoordinates);
					consumeChilds(completeCoordinates, childPosition, system, endpoint, childFolder, consumer,
							messagesConsumer, errorConsumer, environment);
				} else {
					final String msg = "The node=>" + nativeCoordinatePointer.child.toString()
							+ " is not a resource nor a folder";
					ContentsAccessError error = new ContentsAccessError();
					error.setMsg(msg);
					errorConsumer.accept(error);
					throw new GeboContentHandlerSystemException(msg);
				}
			}
		}
	}

	/********************************************************************
	 * Translates from native position coordinates to an intermediate one used for
	 * UI navigation
	 * 
	 * @param childCoordinates List of child coordinates in native format
	 * @return PositionsCoordinateType result of the translation
	 * @throws GeboContentHandlerSystemException If a coordinate cannot be translated
	 */
	protected abstract PositionsCoordinateType getPositionCoordinate(
			List<ImplementativePositionObjectType> childCoordinates) throws GeboContentHandlerSystemException;

	/********************************************************************************************************************
	 * Retrieve the list of childs of a specific native navigation coordinates
	 * position
	 * 
	 * @param nativeCoordinates List of native coordinates
	 * @param position PositionsCoordinateType
	 * @param system SystemType
	 * @param endpoint EndpointType
	 * @param messagesConsumer IGUserMessagesConsumer
	 * @param environment Operating environment map
	 * @return List of NativeCoordinatePointer for each child
	 * @throws GeboContentHandlerSystemException If an error occurs during retrieval
	 */
	protected abstract List<NativeCoordinatePointer> retrieveChilds(
			List<ImplementativePositionObjectType> nativeCoordinates, PositionsCoordinateType position,
			SystemType system, EndpointType endpoint, IGUserMessagesConsumer messagesConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException;

	/***********************************************************************************************
	 * When all nativeCoordinates indicate a virtual path folder in the native
	 * system it creates all intermediate virtual filesystem node contents and
	 * consume them with all detailed meta infos
	 * 
	 * @param nativeCoordinates List of native coordinates
	 * @param position Position of the navigation
	 * @param system Content management system
	 * @param endpoint Project endpoint
	 * @param root Root virtual folder
	 * @param consumer Content consumer
	 * @param messagesConsumer User messages consumer
	 * @param errorConsumer Consumer for access errors
	 * @param environment Operational environment
	 * @return GVirtualFolder representing the created nodes
	 */
	protected final GVirtualFolder createAndConsumeVirtualFolderNodes(
			List<ImplementativePositionObjectType> nativeCoordinates, PositionsCoordinateType position,
			SystemType system, EndpointType endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		GVirtualFolder currentFolder = root;
		for (ImplementativePositionObjectType microsoftGraphNativePositionObject : nativeCoordinates) {
			currentFolder = createVirtualFolder(microsoftGraphNativePositionObject, root, system, endpoint);
			consumer.accept(currentFolder);
		}
		return currentFolder;
	}

	/*****************************************************************************************************
	 * Creates a virtual folder representing an implementative node of the
	 * navigation
	 * 
	 * @param node Native position object
	 * @param root Root virtual folder
	 * @param system Content management system
	 * @param endpoint Endpoint used
	 * @return GVirtualFolder representing the virtual folder
	 */
	protected final GVirtualFolder createVirtualFolder(ImplementativePositionObjectType node, GVirtualFolder root,
			SystemType system, EndpointType endpoint) {
		String code = node.getCode();
		String name = node.getName();
		String url = node.getUrl();
		GVirtualFolder child = createChildItem(root, endpoint, code, name, name, url,
				node.getResourceReferenceMetaInfos());
		return child;
	}

	/*************************************************************************
	 * Does a reconstruction from native coordinates metainfos in term of native
	 * objects reconstructed from "position" encoded navigation steps informations
	 * 
	 * @param position Positions coordinate type
	 * @param system Content management system used
	 * @param endpoint Project endpoint
	 * @param root Root virtual folder
	 * @param consumer Content consumer
	 * @param messagesConsumer User messages consumer
	 * @param errorConsumer Consumer for access errors
	 * @param environment Operational environment
	 * @return List of ImplementativePositionObjectType representing native coordinates
	 */
	protected abstract List<ImplementativePositionObjectType> toNativeCoordinates(PositionsCoordinateType position,
			SystemType system, EndpointType endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException;

	/****************************************************************************************************************
	 * Creates a runtime environment map that binds all required
	 * connections/resources required for the following interactions with the remote
	 * native system, the returned map will be available for all subsequent business
	 * methods.
	 * 
	 * @param system Content management system
	 * @param endpoint Project endpoint
	 * @param errorConsumer Consumer for access errors
	 * @return Map<String, Object> representing the environment context
	 */
	protected abstract Map<String, Object> createEnvironment(SystemType system, EndpointType endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException;

	/******************************************************************************************************************
	 * Releases eventually allocated resources
	 * 
	 * @param environment Map containing environment variables
	 * @param system Content management system
	 * @param endpoint Project endpoint
	 */
	protected abstract void clearEnvironment(Map<String, Object> environment, SystemType system, EndpointType endpoint)
			throws GeboContentHandlerSystemException;

	/*******************************************************
	 * Transforms a memorized VFilesystemReference to a root with navigation steps
	 * navigation coordinates infos
	 * 
	 * @param path Filesystem reference path
	 * @return PositionsCoordinateType representing navigation coordinates
	 */
	protected abstract PositionsCoordinateType toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException;

	/******************************************************************************************************************
	 * Utility method to create a child virtual folder
	 * 
	 * @param parent Parent virtual folder
	 * @param endpoint Project endpoint reference
	 * @param codePart Code part for child item
	 * @param name Name of the child item
	 * @param urlPart URL part for the child item
	 * @param webUrl Web URL for the child item
	 * @param customMap Map containing custom metadata
	 * @return GVirtualFolder representing the child item
	 */
	protected GVirtualFolder createChildItem(GVirtualFolder parent, EndpointType endpoint, String codePart, String name,
			String urlPart, String webUrl, Map<String, Object> customMap) {
		GVirtualFolder childItem = new GVirtualFolder();
		childItem.setCode(parent.getCode() + "/" + codePart);
		childItem.setDescription(name);
		childItem.setRootKnowledgebaseCode(parent.getRootKnowledgebaseCode());
		childItem.setParentProjectCode(parent.getParentProjectCode());
		childItem.setParentVirtualFolderCode(parent.getCode());
		childItem.setName(name);
		childItem.setRelativePath((parent.getRelativePath() != null ? (parent.getRelativePath() + "/") : "") + urlPart);
		childItem.setProjectEndpointReference(GObjectRef.of(endpoint));
		childItem.setMessagingModuleId(GStandardModulesConstraints.SHAREPOINT_MODULE);
		childItem.setUri(webUrl);
		childItem.setCustomMetaInfos(customMap != null ? customMap : new HashMap<String, Object>());
		return childItem;

	}

	/***************************************************************************************************************
	 * Provides a user understandable description of a virtual folder or a resource
	 * remote node while trying to accessing it or listing it
	 * 
	 * @param references List of implementative position objects
	 * @param system The content management system
	 * @param endpoint The project endpoint
	 * @param environment Environment context map
	 * @return String description of the remote object
	 */
	protected abstract String describeObject(List<ImplementativePositionObjectType> references, SystemType system,
			EndpointType endpoint, Map<String, Object> environment);

	/**************************************************************************************************************
	 * Provides a user understandable description of the system before accessing it
	 * or trying to do so
	 * 
	 * @param system Content management system
	 * @return String description of the system
	 */
	protected abstract String describeSystem(SystemType system);

	/********************************************************************************************
	 * Provides a user understandable description of the endpoint before accessing
	 * it or trying to do so
	 * 
	 * @param system Content management system
	 * @param endpoint Project endpoint
	 * @param environment Map with environment context
	 * @return String description of the project endpoint
	 */
	protected abstract String describeProjectEndpoint(SystemType system, EndpointType endpoint,
			Map<String, Object> environment);

	/**
	 * Retrieves the last node from a list of references.
	 * 
	 * @param references List of implementative position objects
	 * @return Last ImplementativePositionObjectType in the list
	 */
	protected ImplementativePositionObjectType lastNode(List<ImplementativePositionObjectType> references) {

		return references.get(references.size() - 1);
	}

	/**************************************************************************************************
	 * Goes thru the full path and adds missing parent item meta maps item in the
	 * following child
	 * 
	 * @param nativePath List of native position objects
	 */
	protected void childrenMapsEnrich(List<ImplementativePositionObjectType> nativePath) {
		Map<String, Object> inheritedAttributes = new HashMap<String, Object>();
		HashMap<String, Object> actualMap = null;
		for (ImplementativePositionObjectType implementativePositionObjectType : nativePath) {
			actualMap = implementativePositionObjectType.getResourceReferenceMetaInfos();
			if (actualMap != null && !actualMap.isEmpty()) {
				if (inheritedAttributes.isEmpty()) {
					// if inherited attributes are empty than fill with present map and no
					// modification to this II map
					inheritedAttributes.putAll(actualMap);
				} else {
					// copy inherited attributes not existing in the actual map in the actual map
					Set<String> keys = inheritedAttributes.keySet();
					for (String key : keys) {
						if (!actualMap.containsKey(key)) {
							Object value = inheritedAttributes.get(key);
							if (value != null) {
								actualMap.put(key, value);
							}
						}
					}
					// override inherited attributes for next steps with actual map values
					inheritedAttributes.putAll(actualMap);
				}
			}
		}
		if (LOGGER.isDebugEnabled() && actualMap != null) {
			LOGGER.debug("Most depth child customMap=>" + actualMap);
		}
	}

	@Override
	public Stream<GAbstractVirtualFilesystemObject> checkUpdatedOrDeleted(final SystemType system,
			final EndpointType endpoint, IGContentsAccessErrorConsumer errorConsumer,
			final Stream<GAbstractVirtualFilesystemObject> itemsToCheck) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = createEnvironment(system, endpoint, errorConsumer);
		return itemsToCheck.map(x -> {
			return verifyRemoteExistence(system, endpoint, x, environment);
		});
	}

	/**
	 * Verifies the existence of a remote object.
	 * 
	 * @param system Content management system
	 * @param endpoint Project endpoint
	 * @param x Abstract virtual filesystem object to verify
	 * @param environment Map representing the environment context
	 * @return The virtual filesystem object with updated existence info
	 */
	protected GAbstractVirtualFilesystemObject verifyRemoteExistence(SystemType system, EndpointType endpoint,
			GAbstractVirtualFilesystemObject x, Map<String, Object> environment) {
		try {
			ResourceReferenceType reference = getResourceHandle(system, endpoint, x, environment);
			x = verifyRemoteObjectExistence(system, endpoint, x, reference, environment);
		} catch (GeboContentHandlerSystemException e) {
			LOGGER.error("Exception in verify remote document", e);
			throw new RuntimeException("Unmanageable exception, safer stop checking for deletion", e);
		}
		return x;
	}

	/*******************************************************************************************************
	 * Checks if the remote referenced object is still alive or not
	 * 
	 * @param system Content management system
	 * @param endpoint Endpoint being used
	 * @param doc Virtual filesystem object to check
	 * @param reference Resource reference type
	 * @param environment The operation environment map
	 * @return Updated GAbstractVirtualFilesystemObject after verification
	 * @throws GeboContentHandlerSystemException if the object cannot be verified
	 */
	protected abstract GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(SystemType system,
			EndpointType endpoint, GAbstractVirtualFilesystemObject doc, ResourceReferenceType reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException;

}