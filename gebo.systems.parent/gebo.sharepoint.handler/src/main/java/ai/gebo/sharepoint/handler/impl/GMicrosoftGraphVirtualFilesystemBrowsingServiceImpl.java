/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.graph.drives.DrivesRequestBuilder;
import com.microsoft.graph.drives.item.DriveItemRequestBuilder;
import com.microsoft.graph.drives.item.root.RootRequestBuilder;
import com.microsoft.graph.models.BaseSitePage;
import com.microsoft.graph.models.BaseSitePageCollectionResponse;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveCollectionResponse;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCollectionResponse;
import com.microsoft.graph.models.Site;
import com.microsoft.graph.models.SiteCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.sites.SitesRequestBuilder;
import com.microsoft.graph.sites.item.SiteItemRequestBuilder;
import com.microsoft.graph.sites.item.drive.DriveRequestBuilder;
import com.microsoft.graph.sites.item.pages.PagesRequestBuilder;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemBrowsingService;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathComponent;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathNodeType;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * Service implementation for browsing Microsoft SharePoint virtual filesystem through Microsoft Graph API.
 * AI generated comments
 * This service handles navigation of SharePoint sites, drives, and their contents.
 */
@Service
public class GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl
		implements IGMicrosoftGraphVirtualFilesystemBrowsingService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl.class);
	@Autowired
	IGeboSecretsAccessService secretAccessService;
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	IGDocumentReferenceFactory documentReferenceFactory;
	
	@Autowired
	GMicrosoftGraphClientFactory clientFactory;
	
	/** Key used for storing GraphServiceClient instance in the cache */
	private static final String GRAPH_SERVICE_CLIENT = "GRAPH_SERVICE_CLIENT";

	/**
	 * Retrieves a Graph Service Client for the specified SharePoint browsing context.
	 * 
	 * @param context the SharePoint browsing context
	 * @return GraphServiceClient instance
	 * @throws GeboPersistenceException if there's an error accessing persistent data
	 * @throws VirtualFilesystemBrowsingException if there's an error browsing the filesystem
	 * @throws GeboCryptSecretException if there's an error with cryptographic secrets
	 */
	private GraphServiceClient getServiceClient(SharepointBrowsingContext context)
			throws GeboPersistenceException, VirtualFilesystemBrowsingException, GeboCryptSecretException {
		GSharepointContentManagementSystem found = persistentObjectManager
				.findById(GSharepointContentManagementSystem.class, context.getSystemCode());
		return clientFactory.getServiceClient(found);
	}

	/**
	 * Retrieves the roots of the SharePoint virtual filesystem.
	 * This includes both drives and sites available to the user.
	 * 
	 * @param context the SharePoint browsing context
	 * @return operation status containing a list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException if there's an error browsing the filesystem
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(SharepointBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
		try {
			GraphServiceClient client = getServiceClient(context);
			DrivesRequestBuilder drivesRB = client.drives();
			DriveCollectionResponse drives = drivesRB.get();
			List<Drive> drivesList = drives.getValue();
			if (drivesList != null) {
				for (Drive drive : drivesList) {
					GVirtualFilesystemRoot root = GMicrosoftGraphNavigationUtils.toRoot(drive);
					roots.add(root);
				}
			}
			SitesRequestBuilder sitesRB = client.sites();
			SiteCollectionResponse sites = sitesRB.get();
			List<Site> sitesList = sites.getValue();
			if (sitesList != null) {
				for (Site site : sitesList) {
					GVirtualFilesystemRoot root = GMicrosoftGraphNavigationUtils.toRoot(site);
					roots.add(root);
				}
			}
		} catch (GeboPersistenceException | GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cannot create a service Client", e);
		}
		return OperationStatus.of(roots);
	}

	/**
	 * Browses the contents of a specific path in the SharePoint filesystem.
	 * 
	 * @param param navigation parameters including root and path
	 * @param context the SharePoint browsing context
	 * @return operation status containing a list of path information for child items
	 * @throws VirtualFilesystemBrowsingException if there's an error browsing the filesystem
	 */
	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, SharepointBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> childs = new ArrayList<PathInfo>();
		try {
			GraphServiceClient client = getServiceClient(context);
			if (param.path == null && param.root != null) {
				if (GMicrosoftGraphNavigationUtils.isSite(param.root)) {
					String id = GMicrosoftGraphNavigationUtils.getSiteId(param.root);
					SiteItemRequestBuilder siteRQ = client.sites().bySiteId(id);
					PagesRequestBuilder pagesRQ = siteRQ.pages();
					BaseSitePageCollectionResponse pagesResponse = pagesRQ.get();
					List<BaseSitePage> pages = pagesResponse.getValue();
					if (pages != null) {

						for (BaseSitePage baseSitePage : pages) {
							PathInfo path = GMicrosoftGraphNavigationUtils.toPathInfo(baseSitePage);
							childs.add(path);
						}

					}
					DriveRequestBuilder driveRQ = siteRQ.drive();
					Drive drive = driveRQ.get();
					if (drive != null) {
						PathInfo path = GMicrosoftGraphNavigationUtils.toPathInfo(param.root, drive);
						childs.add(path);
					}
				}
				if (GMicrosoftGraphNavigationUtils.isDrive(param.root)) {
					String id = GMicrosoftGraphNavigationUtils.getDriveId(param.root);
					DriveItemRequestBuilder driveRQ = client.drives().byDriveId(id);
					if (driveRQ != null) {
						RootRequestBuilder rootRQ = driveRQ.root();
						if (rootRQ != null) {
							DriveItem driveItem = rootRQ.get();
							if (driveItem != null) {
								PathInfo path = GMicrosoftGraphNavigationUtils.toPathInfo(driveItem);
								childs.add(path);
							}
						}
					}

				}

			} else if (param.path != null && param.root != null) {
				if (GMicrosoftGraphNavigationUtils.isSite(param.root)) {

					childs.addAll(navigateSiteChilds(param, client));

				} else if (GMicrosoftGraphNavigationUtils.isDrive(param.root)) {

					childs.addAll(navigateDriveChilds(param, client));
				}
			} else
				throw new VirtualFilesystemBrowsingException("root null and path null, invalid browseParam");
		} catch (Throwable e) {
			LOGGER.error("Cannot access root resources for microsoft graph implementation", e);
			return OperationStatus.of(e);
		}
		return OperationStatus.of(childs);
	}

	/**
	 * Navigates child items of a drive in the SharePoint filesystem.
	 * 
	 * @param param navigation parameters including root and path
	 * @param client the Graph Service Client
	 * @return collection of path information for child items
	 */
	private Collection<PathInfo> navigateDriveChilds(BrowseParam param, GraphServiceClient client) {
		if (!GMicrosoftGraphNavigationUtils.isDrive(param.root))
			throw new RuntimeException("Drive code in non drive root....");
		List<PathInfo> childs = new ArrayList<PathInfo>();
		List<MicrosoftGraphPathComponent> paths = GMicrosoftGraphNavigationUtils.pathComponents(param.path);
		MicrosoftGraphPathComponent lastItem = paths.get(paths.size() - 1);
		if (lastItem.type == MicrosoftGraphPathNodeType.DRIVE_ITEM_FOLDER) {
			String driveId = GMicrosoftGraphNavigationUtils.getDriveId(param.root);

			DriveItemCollectionResponse items = client.drives().byDriveId(driveId).items().byDriveItemId(lastItem.id)
					.children().get();
			List<DriveItem> driveItems = items.getValue();
			if (driveItems != null) {
				for (DriveItem driveItem : driveItems) {
					childs.add(GMicrosoftGraphNavigationUtils.toPathInfo(param, driveItem));
				}
			}
		}
		return childs;
	}

	/**
	 * Navigates child items of a site in the SharePoint filesystem.
	 * 
	 * @param param navigation parameters including root and path
	 * @param client the Graph Service Client
	 * @return collection of path information for child items
	 */
	private Collection<PathInfo> navigateSiteChilds(BrowseParam param, GraphServiceClient client) {
		List<PathInfo> childs = new ArrayList<PathInfo>();
		List<MicrosoftGraphPathComponent> paths = GMicrosoftGraphNavigationUtils.pathComponents(param.path);
		String id = GMicrosoftGraphNavigationUtils.getSiteId(param.root);
		MicrosoftGraphPathComponent lastItem = paths.get(paths.size() - 1);
		String thisObjectId = lastItem.id;
		switch (lastItem.type) {
		case DRIVE: {
			DriveItem driveItem = client.drives().byDriveId(thisObjectId).root().get();
			if (driveItem != null)
				childs.add(GMicrosoftGraphNavigationUtils.toPathInfo(param, driveItem));
		}
			break;
		case DRIVE_ITEM_FOLDER: {
			MicrosoftGraphPathComponent _driveItem = GMicrosoftGraphNavigationUtils.findLast(paths,
					MicrosoftGraphPathNodeType.DRIVE);
			DriveItemCollectionResponse result = client.drives().byDriveId(_driveItem.id).items()
					.byDriveItemId(lastItem.id).children().get();
			if (result != null && result.getValue() != null) {
				for (DriveItem driveItem : result.getValue()) {
					childs.add(GMicrosoftGraphNavigationUtils.toPathInfo(param, driveItem));
				}
			}

		}
			break;
		case SHAREPOINT_PAGE_ITEM: {
		}
			break;
		}

		return childs;
	}

	/**
	 * Checks if navigation status is supported.
	 * 
	 * @return false as navigation status is not supported
	 */
	@Override
	public boolean isSupportsNavigationStatus() {
		return false;
	}

	/**
	 * Gets the parent of a filesystem reference.
	 * 
	 * @param reference the filesystem reference
	 * @param context the SharePoint browsing context
	 * @return the parent filesystem reference
	 * @throws VirtualFilesystemBrowsingException if there's an error browsing the filesystem
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, SharepointBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		return null;
	}

	/**
	 * Retrieves the roots of the SharePoint virtual filesystem for a specific SharePoint system.
	 * 
	 * @param object the SharePoint content management system
	 * @return operation status containing a list of virtual filesystem roots
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GSharepointContentManagementSystem object) {
		List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
		try {
			GraphServiceClient client = clientFactory.getServiceClient(object);
			DrivesRequestBuilder drivesRB = client.drives();
			DriveCollectionResponse drives = drivesRB.get();
			List<Drive> drivesList = drives.getValue();
			if (drivesList != null) {
				for (Drive drive : drivesList) {
					GVirtualFilesystemRoot root = GMicrosoftGraphNavigationUtils.toRoot(drive);
					roots.add(root);
				}
			}
			SitesRequestBuilder sitesRB = client.sites();
			SiteCollectionResponse sites = sitesRB.get();
			List<Site> sitesList = sites.getValue();
			if (sitesList != null) {
				for (Site site : sitesList) {
					GVirtualFilesystemRoot root = GMicrosoftGraphNavigationUtils.toRoot(site);
					roots.add(root);
				}
			}
		} catch (Throwable e) {
			return OperationStatus.of(e);
		}
		return OperationStatus.of(roots);
	}

	/**
	 * Gets or creates a Graph Service Client for the given SharePoint system.
	 * Stores the client in the provided cache for reuse.
	 * 
	 * @param system the SharePoint content management system
	 * @param cache the cache to store the client
	 * @return the Graph Service Client
	 * @throws VirtualFilesystemBrowsingException if there's an error creating the client
	 */
	private GraphServiceClient getClient(GSharepointContentManagementSystem system, Map<String, Object> cache)
			throws VirtualFilesystemBrowsingException {
		if (cache.containsKey(GRAPH_SERVICE_CLIENT))
			return (GraphServiceClient) cache.get(GRAPH_SERVICE_CLIENT);
		GraphServiceClient client;
		try {
			client = clientFactory.getServiceClient(system);
			cache.put(GRAPH_SERVICE_CLIENT, client);
			return client;
		} catch (GeboCryptSecretException e) {
			String msg = "Cannot allocate MSClient for crypting problems";
			LOGGER.error(msg, e);
			throw new VirtualFilesystemBrowsingException(msg, e);
		}
	}
}