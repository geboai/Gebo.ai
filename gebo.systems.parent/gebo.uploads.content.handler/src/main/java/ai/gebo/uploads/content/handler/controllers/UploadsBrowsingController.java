/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */




package ai.gebo.uploads.content.handler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGServerVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ServerFileSystemContext;
import ai.gebo.uploads.content.handler.service.UploadsSystemsManagementServiceImpl;

/**
 * Browses the contents of a single uploads data source.
 *
 * <p>
 * The uploads handler keeps its contents on the local filesystem
 * ({@code isContentsOnLocalFilesystem() == true}), so browsing is delegated to
 * the shared {@link IGServerVirtualFilesystemBrowsingService} exactly like the
 * shared filesystems browsing does. The difference is the context: instead of
 * the configured shares, the only limiting root is the persistent folder of the
 * requested endpoint, which makes that folder both the browsing scope and the
 * security boundary (the browsing service refuses any path escaping its
 * limiting roots).
 * </p>
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/UploadsBrowsingController")
public class UploadsBrowsingController {

	/** Shared local filesystem browsing logic. */
	@Autowired
	IGServerVirtualFilesystemBrowsingService serverFilesystemBrowsingService;

	/** Resolves the persistent folder owning the contents of an endpoint. */
	@Autowired
	UploadsSystemsManagementServiceImpl uploadsService;

	public UploadsBrowsingController() {

	}

	/**
	 * Builds the browsing context confined to the persistent folder of the given
	 * uploads endpoint.
	 *
	 * @param endpointCode code of the uploads project endpoint.
	 * @return a context whose only limiting root is that endpoint folder.
	 * @throws VirtualFilesystemBrowsingException when the endpoint or its folder
	 *                                            cannot be resolved.
	 */
	private ServerFileSystemContext getEndpointContext(String endpointCode) throws VirtualFilesystemBrowsingException {
		ServerFileSystemContext context = new ServerFileSystemContext();
		try {
			context.getLimitingRoots().add(uploadsService.resolveContentsFolder(endpointCode, true));
		} catch (GeboContentHandlerSystemException contentHandlerException) {
			throw new VirtualFilesystemBrowsingException(
					"Cannot resolve the contents folder of the data source " + endpointCode, contentHandlerException);
		}
		return context;
	}

	/**
	 * Returns the single browsing root of an uploads endpoint: its own contents
	 * folder.
	 *
	 * @param endpointCode code of the uploads project endpoint.
	 * @return operation status carrying the endpoint root.
	 * @throws VirtualFilesystemBrowsingException on browsing errors.
	 */
	@GetMapping(value = "getUploadsEndpointRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getUploadsEndpointRoots(
			@RequestParam("endpointCode") String endpointCode) throws VirtualFilesystemBrowsingException {
		OperationStatus<List<GVirtualFilesystemRoot>> roots = serverFilesystemBrowsingService
				.getRoots(getEndpointContext(endpointCode));
		if (roots.getResult() != null) {
			for (GVirtualFilesystemRoot root : roots.getResult()) {
				// The folder is named after the endpoint identity, which is meaningless to the
				// admin: show the data source description and the uploads icon instead.
				root.setDescription(uploadsService.describeEndpoint(endpointCode, root.getDescription()));
				root.setIconKey("GUploadsProjectEndpoint");
			}
		}
		return roots;
	}

	/**
	 * Lists the children of a path inside an uploads endpoint folder.
	 *
	 * @param param        the browsing coordinates.
	 * @param endpointCode code of the uploads project endpoint.
	 * @return operation status carrying the children of the requested path.
	 * @throws VirtualFilesystemBrowsingException on browsing errors.
	 */
	@PostMapping(value = "browseUploadsEndpointPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseUploadsEndpointPath(@RequestBody BrowseParam param,
			@RequestParam("endpointCode") String endpointCode) throws VirtualFilesystemBrowsingException {
		return serverFilesystemBrowsingService.browse(param, getEndpointContext(endpointCode));
	}

	/**
	 * Rebuilds the navigation tree leading to the given references, so a stored
	 * selection can be reopened expanded.
	 *
	 * @param references   the references to reach.
	 * @param endpointCode code of the uploads project endpoint.
	 * @return operation status carrying the navigation trees.
	 * @throws VirtualFilesystemBrowsingException on browsing errors.
	 */
	@PostMapping(value = "getUploadsEndpointNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getUploadsEndpointNavigationStatus(
			@RequestBody List<VFilesystemReference> references, @RequestParam("endpointCode") String endpointCode)
			throws VirtualFilesystemBrowsingException {
		return serverFilesystemBrowsingService.navigationStatus(references, getEndpointContext(endpointCode));
	}
}
