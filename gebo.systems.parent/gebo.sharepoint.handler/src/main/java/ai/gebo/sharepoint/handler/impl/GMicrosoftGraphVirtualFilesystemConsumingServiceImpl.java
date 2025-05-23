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
 * 
 * Implementation of the Microsoft Graph Virtual Filesystem consuming service for SharePoint.
 * This service allows for browsing, accessing, and managing files and folders in 
 * Microsoft SharePoint and OneDrive systems through the Microsoft Graph API.
 */
package ai.gebo.sharepoint.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.graph.drives.item.items.item.content.ContentRequestBuilder;
import com.microsoft.graph.drives.item.root.RootRequestBuilder;
import com.microsoft.graph.models.BaseSitePage;
import com.microsoft.graph.models.BaseSitePageCollectionResponse;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCollectionResponse;
import com.microsoft.graph.models.Site;
import com.microsoft.graph.models.WebPart;
import com.microsoft.graph.models.WebPartCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.sites.item.SiteItemRequestBuilder;
import com.microsoft.graph.sites.item.drive.DriveRequestBuilder;
import com.microsoft.graph.sites.item.pages.PagesRequestBuilder;
import com.microsoft.kiota.store.BackingStore;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemConsumingService;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNativePositionObject;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNavigationCoordinates;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathComponent;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphResourceReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;

/**
 * Service implementation for accessing and managing Microsoft SharePoint and OneDrive resources 
 * through the Microsoft Graph API.
 */
@Service
public class GMicrosoftGraphVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GSharepointContentManagementSystem, GSharepointProjectEndpoint, MicrosoftGraphNativePositionObject, MicrosoftGraphNavigationCoordinates, MicrosoftGraphResourceReference>
		implements IGMicrosoftGraphVirtualFilesystemConsumingService {
	
	/**
	 * Constructor for the service.
	 * 
	 * @param documentFactory Factory for creating document references
	 */
	public GMicrosoftGraphVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory) {
		super(documentFactory);

	}

	@Autowired
	GMicrosoftGraphClientFactory clientFactory;

	/** Key for accessing the Graph client in the environment map */
	static final String CLIENT = "CLIENT";

	/**
	 * Retrieves the Graph client from the environment map.
	 * 
	 * @param environment The environment map containing the client
	 * @return The Graph service client
	 */
	private GraphServiceClient getClient(Map<String, Object> environment) {
		return (GraphServiceClient) environment.get(CLIENT);
	}

	/**
	 * Splits a path into its component parts.
	 * 
	 * @param pathInfo The path information to split
	 * @return A list of path components
	 */
	private List<PathInfo> splitPath(PathInfo pathInfo) {
		List<PathInfo> splitten = new ArrayList<PathInfo>();
		StringTokenizer tokenizer = new StringTokenizer(pathInfo.absolutePath,
				GMicrosoftGraphNavigationUtils.HIERARCHY_SEPARATOR);
		StringBuffer path = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			String lastToken = tokenizer.nextToken();
			path.append(lastToken);
			PathInfo thisSection = new PathInfo();
			thisSection.absolutePath = path.toString();
			thisSection.folder = true;
			splitten.add(thisSection);
		}
		splitten.get(splitten.size() - 1).folder = pathInfo.folder;
		splitten.get(splitten.size() - 1).name = pathInfo.name;
		return splitten;
	}

	/**
	 * Converts a filesystem reference to a navigation position.
	 * 
	 * @param path The filesystem reference
	 * @return Navigation coordinates
	 * @throws GeboContentHandlerSystemException If there's an error in the conversion
	 */
	@Override
	protected MicrosoftGraphNavigationCoordinates toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException {
		MicrosoftGraphNavigationCoordinates coordinates = new MicrosoftGraphNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (path.path != null) {
			coordinates.setBrowsingSteps(splitPath(path.path));
			coordinates.setBrowsingStepsCustom(GMicrosoftGraphNavigationUtils.pathComponents(path.path));
		}
		return coordinates;
	}

	/**
	 * Creates an environment for browsing the Microsoft Graph API.
	 * 
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param errorConsumer Consumer for error handling
	 * @return Environment map with client
	 * @throws GeboContentHandlerSystemException If there's an error creating the environment
	 */
	@Override
	protected Map<String, Object> createEnvironment(GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(CLIENT, clientFactory.getServiceClient(system));
		} catch (VirtualFilesystemBrowsingException | GeboCryptSecretException e) {
			LOGGER.error("Connectivity with Sharepoint system gone wrong", e);
			errorConsumer.accept(ContentsAccessError.of(e, null, null));
			throw new GeboContentHandlerSystemException("Connectivity with Sharepoint system gone wrong", e);
		} catch (Throwable th) {
			LOGGER.error("Connectivity with Sharepoint system gone wrong", th);
			errorConsumer.accept(ContentsAccessError.of(th, null, null));
			throw new RuntimeException("Connectivity with Sharepoint system gone wrong", th);
		}
		return environment;
	}

	/**
	 * Cleans up resources in the environment.
	 * 
	 * @param environment The environment to clear
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @throws GeboContentHandlerSystemException If there's an error clearing the environment
	 */
	@Override
	protected void clearEnvironment(Map<String, Object> environment, GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		// No resources to clean up
	}

	/**
	 * Converts navigation coordinates to native Microsoft Graph coordinates.
	 * 
	 * @param position The navigation position
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param root The virtual folder root
	 * @param consumer Content consumer
	 * @param messagesConsumer Message consumer
	 * @param errorConsumer Error consumer
	 * @param environment The environment
	 * @return List of native position objects
	 * @throws GeboContentHandlerSystemException If there's an error in the conversion
	 */
	@Override
	protected List<MicrosoftGraphNativePositionObject> toNativeCoordinates(MicrosoftGraphNavigationCoordinates position,
			GSharepointContentManagementSystem system, GSharepointProjectEndpoint endpoint, GVirtualFolder root,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {

		List<MicrosoftGraphNativePositionObject> nativePath = new ArrayList<MicrosoftGraphNativePositionObject>();
		GraphServiceClient client = getClient(environment);
		MicrosoftGraphNativePositionObject firstItem = new MicrosoftGraphNativePositionObject();
		nativePath.add(firstItem);
		String siteId = null;
		String driveId = null;
		if (GMicrosoftGraphNavigationUtils.isSite(position.getRoot())) {
			siteId = GMicrosoftGraphNavigationUtils.getSiteId(position.getRoot());
			Site site = client.sites().bySiteId(siteId).get();
			firstItem.setSite(site);
		} else if (GMicrosoftGraphNavigationUtils.isDrive(position.getRoot())) {
			driveId = GMicrosoftGraphNavigationUtils.getDriveId(position.getRoot());
			Drive drive = client.drives().byDriveId(driveId).get();
			firstItem.setDrive(drive);
		}
		List<MicrosoftGraphPathComponent> pathNodes = position.getBrowsingStepsCustom();
		for (int i = 0; i < pathNodes.size(); i++) {
			MicrosoftGraphPathComponent microsoftGraphPathComponent = pathNodes.get(i);
			MicrosoftGraphNativePositionObject thisStepItem = new MicrosoftGraphNativePositionObject();
			thisStepItem.setPath(position.getBrowsingSteps().get(i));
			switch (microsoftGraphPathComponent.type) {
			case DRIVE: {
				driveId = microsoftGraphPathComponent.id;
				Drive drive = client.drives().byDriveId(driveId).get();
				thisStepItem.setDrive(drive);
			}
				break;
			case DRIVE_ITEM:
			case DRIVE_ITEM_FOLDER: {
				DriveItem driveItem = client.drives().byDriveId(driveId).items()
						.byDriveItemId(microsoftGraphPathComponent.id).get();
				thisStepItem.setDriveItem(driveItem);
			}
				break;
			case SHAREPOINT_PAGE_ITEM: {
				BaseSitePage page = client.sites().bySiteId(siteId).pages()
						.byBaseSitePageId(microsoftGraphPathComponent.id).get();
				thisStepItem.setPage(page);
			}
				break;
			}
			nativePath.add(thisStepItem);
		}
		childrenMapsEnrich(nativePath);
		return nativePath;

	}

	/**
	 * Gets a resource handle for a virtual filesystem object.
	 * 
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param reference The virtual filesystem object
	 * @param cache The cache map
	 * @return A resource reference
	 * @throws GeboContentHandlerSystemException If there's an error getting the resource handle
	 */
	@Override
	public MicrosoftGraphResourceReference getResourceHandle(GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint, GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		MicrosoftGraphResourceReference out = new MicrosoftGraphResourceReference();
		if (reference != null && reference.getCustomMetaInfos() != null) {
			out.driveId = (String) reference.getCustomMetaInfos()
					.get(MicrosoftGraphNativePositionObject.DRIVE_REFERENCE);
			out.driveItemId = (String) reference.getCustomMetaInfos()
					.get(MicrosoftGraphNativePositionObject.DRIVE_ITEM_REFERENCE);
			out.siteId = (String) reference.getCustomMetaInfos()
					.get(MicrosoftGraphNativePositionObject.SHAREPOINT_SITE_REFERENCE);
			out.sitePageId = (String) reference.getCustomMetaInfos()
					.get(MicrosoftGraphNativePositionObject.SHAREPOINT_PAGE_REFERENCE);
			return out;
		}

		throw new GeboContentHandlerSystemException("The document => " + reference.getCode()
				+ " does not have a proper  customMetaInfo custom meta info object");
	}

	/**
	 * Streams the content of a resource.
	 * 
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param reference The resource reference
	 * @param cache The cache map
	 * @return Input stream with the resource content
	 * @throws GeboContentHandlerSystemException If there's an error accessing the resource
	 * @throws IOException If there's an I/O error
	 */
	@Override
	public InputStream streamResource(GSharepointContentManagementSystem system, GSharepointProjectEndpoint endpoint,
			MicrosoftGraphResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		GraphServiceClient client = getClient(system, cache);

		if (reference.driveId != null && reference.driveItemId != null) {
			ContentRequestBuilder content = client.drives().byDriveId(reference.driveId).items()
					.byDriveItemId(reference.driveItemId).content();
			return content.get();
		}
		if (reference.siteId != null && reference.sitePageId != null) {
			return streamPage(client, reference.siteId, reference.sitePageId);

		}
		return null;
	}

	/**
	 * Gets or creates a Graph client for a SharePoint system.
	 * 
	 * @param system The SharePoint system
	 * @param cache The cache map
	 * @return The Graph service client
	 * @throws GeboContentHandlerSystemException If there's an error getting the client
	 */
	private GraphServiceClient getClient(GSharepointContentManagementSystem system, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		try {
			GraphServiceClient client = getClient(cache);
			if (client == null) {
				client = clientFactory.getServiceClient(system);
				cache.put(CLIENT, client);
			}
			return client;
		} catch (Throwable e) {
			throw new GeboContentHandlerSystemException("Cannot connect to MSGraph instance in cloud", e);
		}
	}

	/** Key for the inner HTML property in web parts */
	private final String INNER_HTML_KEY = "innerHtml";

	/**
	 * Streams the content of a SharePoint page.
	 * 
	 * @param client The Graph service client
	 * @param siteId The site ID
	 * @param sitePageId The page ID
	 * @return Input stream with the page content
	 * @throws IOException If there's an I/O error
	 */
	private InputStream streamPage(GraphServiceClient client, String siteId, String sitePageId) throws IOException {
		WebPartCollectionResponse webparts = client.sites().bySiteId(siteId).pages().byBaseSitePageId(sitePageId)
				.graphSitePage().webParts().get();
		if (webparts != null && webparts.getValue() != null) {
			List<WebPart> vector = webparts.getValue();
			StringBuffer buffer = new StringBuffer("");
			for (WebPart wp : vector) {
				Map<String, Object> data = wp.getAdditionalData();
				BackingStore backingStore = wp.getBackingStore();

				Map<String, Object> backingStoreMap = backingStore.enumerate();
				if (backingStoreMap.containsKey(INNER_HTML_KEY)) {
					String content = backingStore.get(INNER_HTML_KEY);
					buffer.append(content);
				}

			}
			if (buffer.isEmpty()) {
				return null;
			}
			return new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
		}

		return null;
	}

	/**
	 * Gets the messaging module ID for this service.
	 * 
	 * @return The module ID
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.SHAREPOINT_MODULE;
	}

	/**
	 * Gets the position coordinates from native path objects.
	 * 
	 * @param nativepath The list of native position objects
	 * @return The navigation coordinates
	 * @throws GeboContentHandlerSystemException If there's an error getting the coordinates
	 */
	@Override
	protected MicrosoftGraphNavigationCoordinates getPositionCoordinate(
			List<MicrosoftGraphNativePositionObject> nativepath) throws GeboContentHandlerSystemException {
		MicrosoftGraphNavigationCoordinates coordinate = new MicrosoftGraphNavigationCoordinates();
		if (!nativepath.isEmpty()) {
			MicrosoftGraphNativePositionObject firstItem = nativepath.get(0);
			VFilesystemReference reference = new VFilesystemReference();
			if (firstItem.isDrive()) {
				reference.root = GMicrosoftGraphNavigationUtils.toRoot(firstItem.getDrive());
			} else if (firstItem.isSite()) {
				reference.root = GMicrosoftGraphNavigationUtils.toRoot(firstItem.getSite());
			} else {
				return coordinate;
			}
			StringBuffer absoultePath = new StringBuffer();
			BrowseParam param = new BrowseParam();
			param.root = reference.root;
			for (int i = 1; i < nativepath.size(); i++) {
				MicrosoftGraphNativePositionObject step = nativepath.get(i);
				PathInfo pathInfo = toPathInfo(param, step);
				param.path = pathInfo;
				absoultePath.append(pathInfo.absolutePath);
				if (i < nativepath.size() - 1) {
					absoultePath.append(GMicrosoftGraphNavigationUtils.HIERARCHY_SEPARATOR);
				}
				reference.path = pathInfo;
			}
			coordinate = toNavigationPosition(reference);
		}
		return coordinate;

	}

	/**
	 * Converts a native position object to path info.
	 * 
	 * @param param The browse parameters
	 * @param step The native position object
	 * @return The path info
	 */
	private PathInfo toPathInfo(BrowseParam param, MicrosoftGraphNativePositionObject step) {
		if (step.isDrive()) {
			return GMicrosoftGraphNavigationUtils.toPathInfo(param.root, step.getDrive());
		} else if (step.isDriveItem()) {
			return GMicrosoftGraphNavigationUtils.toPathInfo(param, step.getDriveItem());
		} else if (step.isSharepointPage()) {
			return GMicrosoftGraphNavigationUtils.toPathInfo(step.getPage());
		}
		return null;
	}

	/**
	 * Retrieves the child objects for a given path.
	 * 
	 * @param nativeCoordinates The native coordinates
	 * @param position The navigation position
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param messagesConsumer The message consumer
	 * @param environment The environment
	 * @return List of child pointers
	 */
	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<MicrosoftGraphNativePositionObject> nativeCoordinates,
			MicrosoftGraphNavigationCoordinates position, GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint, IGUserMessagesConsumer messagesConsumer,
			Map<String, Object> environment) {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		MicrosoftGraphNativePositionObject lastNative = nativeCoordinates.get(nativeCoordinates.size() - 1);
		GraphServiceClient client = getClient(environment);
		if (lastNative.isDrive()) {
			Drive drive = lastNative.getDrive();
			String driveId = drive.getId();
			RootRequestBuilder driveRootRQ = client.drives().byDriveId(driveId).root();
			DriveItem driveItem = driveRootRQ.get();
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<MicrosoftGraphNativePositionObject>(nativeCoordinates);
			pointer.child = new MicrosoftGraphNativePositionObject();
			pointer.child.getResourceReferenceMetaInfos().put(MicrosoftGraphNativePositionObject.DRIVE_REFERENCE,
					drive.getId());
			pointer.child.setDriveItem(driveItem);
			childs.add(pointer);

		} else if (lastNative.isDriveItem() && lastNative.isFolder()) {
			String driveId = null;
			String driveItemId = lastNative.getCode();
			Optional<MicrosoftGraphNativePositionObject> driveFound = nativeCoordinates.stream()
					.filter(x -> x.isDrive()).findFirst();
			if (driveFound.isEmpty())
				throw new RuntimeException("Cannot access drive item without drive in the path");
			driveId = driveFound.get().getCode();
			DriveItemCollectionResponse items = client.drives().byDriveId(driveId).items().byDriveItemId(driveItemId)
					.children().get();
			if (items != null && items.getValue() != null) {
				for (DriveItem item : items.getValue()) {
					NativeCoordinatePointer pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<MicrosoftGraphNativePositionObject>(nativeCoordinates);
					pointer.child = new MicrosoftGraphNativePositionObject();
					pointer.child.getResourceReferenceMetaInfos()
							.put(MicrosoftGraphNativePositionObject.DRIVE_REFERENCE, driveId);
					pointer.child.setDriveItem(item);
					childs.add(pointer);
				}
			}
		} else if (lastNative.isSite()) {
			Site site = lastNative.getSite();
			SiteItemRequestBuilder siteRQ = client.sites().bySiteId(site.getId());
			PagesRequestBuilder pagesRQ = siteRQ.pages();
			BaseSitePageCollectionResponse pagesResponse = pagesRQ.get();
			List<BaseSitePage> pages = pagesResponse.getValue();
			if (pages != null) {
				for (BaseSitePage baseSitePage : pages) {
					NativeCoordinatePointer pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<MicrosoftGraphNativePositionObject>(nativeCoordinates);
					pointer.child = new MicrosoftGraphNativePositionObject();
					pointer.child.getResourceReferenceMetaInfos()
							.put(MicrosoftGraphNativePositionObject.SHAREPOINT_SITE_REFERENCE, site.getId());
					pointer.child.setPage(baseSitePage);
					childs.add(pointer);
				}
			}
			DriveRequestBuilder driveRQ = siteRQ.drive();
			Drive drive = driveRQ.get();
			if (drive != null) {
				NativeCoordinatePointer pointer = new NativeCoordinatePointer();
				pointer.parentCoordinates = new ArrayList<MicrosoftGraphNativePositionObject>(nativeCoordinates);
				pointer.child = new MicrosoftGraphNativePositionObject();
				pointer.child.getResourceReferenceMetaInfos()
						.put(MicrosoftGraphNativePositionObject.SHAREPOINT_SITE_REFERENCE, site.getId());
				pointer.child.setDrive(drive);
				childs.add(pointer);
			}
		}
		return childs;
	}

	/**
	 * Provides a description of a Microsoft Graph object.
	 * 
	 * @param references The list of native position objects
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param environment The environment
	 * @return A description of the object
	 */
	@Override
	protected String describeObject(List<MicrosoftGraphNativePositionObject> references,
			GSharepointContentManagementSystem system, GSharepointProjectEndpoint endpoint,
			Map<String, Object> environment) {
		MicrosoftGraphNativePositionObject last = references.get(references.size() - 1);
		String objectType = last.isDrive() ? "OneDrive drive"
				: last.isSite() ? "SharePoint site:"
						: last.isSharepointPage() ? "Web page" : last.isDriveItem() ? "Drive item:" : "";
		return objectType + ":" + last.getName() + " (" + last.getCode() + ")";
	}

	/**
	 * Provides a description of a SharePoint system.
	 * 
	 * @param system The SharePoint system
	 * @return A description of the system
	 */
	@Override
	protected String describeSystem(GSharepointContentManagementSystem system) {
		return "Microsoft Cloud SharePoint/OneDrive system :\"" + system.getDescription() + "\"";
	}

	/**
	 * Provides a description of a project endpoint.
	 * 
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param environment The environment
	 * @return A description of the endpoint
	 */
	@Override
	protected String describeProjectEndpoint(GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint, Map<String, Object> environment) {
		return "Microsoft Cloud SharePoint/OneDrive data source:\"" + endpoint.getDescription() + "\"";
	}

	/**
	 * Verifies if a remote object exists.
	 * 
	 * @param system The SharePoint system
	 * @param endpoint The project endpoint
	 * @param doc The virtual filesystem object
	 * @param reference The resource reference
	 * @param environment The environment
	 * @return The virtual filesystem object if it exists
	 * @throws GeboContentHandlerSystemException If there's an error verifying the existence
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GSharepointContentManagementSystem system,
			GSharepointProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc,
			MicrosoftGraphResourceReference reference, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		// TODO Auto-generated method stub
		return null;
	}
}