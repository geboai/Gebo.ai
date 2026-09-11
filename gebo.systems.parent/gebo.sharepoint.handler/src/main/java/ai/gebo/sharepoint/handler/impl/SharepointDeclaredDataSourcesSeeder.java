/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.config.SharepointDataSourcesConfig;
import ai.gebo.sharepoint.handler.repositories.SharepointProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Writes the data sources declared under {@code ai.gebo.sharepoint.datasources}
 * into the SharePoint endpoint repository at startup.
 *
 * <h2>How a declared path becomes a Graph position</h2>
 * <p>
 * {@code toNativeCoordinates} reads the root through
 * {@code GMicrosoftGraphNavigationUtils.isDrive}/{@code getDriveId} and resolves
 * it with {@code drives().byDriveId(...)}; a step under it is a drive item id,
 * carried as {@code DRIVE-ITEM-FOLDER:} or {@code DRIVE-ITEM:} and resolved with
 * {@code items().byDriveItemId(...)}. A declaration of {@code driveId/itemId}
 * therefore becomes a {@code ONE-DRIVE:} root and one step - the same shape the
 * browser stores.
 * </p>
 *
 * <p>
 * Only drive roots are built here; see {@link SharepointDataSourcesConfig} for
 * why a SharePoint site is not declarable.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class SharepointDeclaredDataSourcesSeeder extends
		GAbstractDeclaredDataSourcesSeeder<GSharepointProjectEndpoint, SharepointProjectEndpointRepository> {

	/**
	 * Constructs the seeder over the declared data sources and the repository they
	 * are written into.
	 *
	 * @param config             the declared OneDrive data sources.
	 * @param repository         the SharePoint endpoint repository.
	 * @param applicationContext this bean's own context.
	 */
	public SharepointDeclaredDataSourcesSeeder(SharepointDataSourcesConfig config,
			SharepointProjectEndpointRepository repository, ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GSharepointProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GSharepointProjectEndpoint endpoint = new GSharepointProjectEndpoint();
		endpoint.setSharePointSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GSharepointProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSourcePath declaredPath) {
		String declared = declaredPath.getPath().trim();
		while (declared.startsWith("/")) {
			declared = declared.substring(1);
		}
		if (declared.startsWith(GMicrosoftGraphNavigationUtils.SITE_PREFIX)) {
			throw new IllegalStateException(
					"only OneDrive drives are declarable here, not SharePoint sites: create a site-backed source in the admin UI");
		}
		int firstSlash = declared.indexOf('/');
		String driveId = firstSlash < 0 ? declared : declared.substring(0, firstSlash);
		String itemId = firstSlash < 0 ? "" : declared.substring(firstSlash + 1).trim();
		if (driveId.length() == 0) {
			throw new IllegalStateException("a OneDrive path starts with the drive id, as in driveId/itemId");
		}
		if (itemId.contains("/")) {
			throw new IllegalStateException(
					"a OneDrive path is driveId/itemId: Microsoft Graph addresses drive items by id, not by folder name");
		}

		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(GMicrosoftGraphNavigationUtils.DRIVE_PREFIX + driveId);
		root.setDescription("OneDrive: " + driveId);

		VFilesystemReference reference = new VFilesystemReference();
		reference.root = root;
		if (itemId.length() == 0) {
			// The drive itself: a root with no step.
			if (!declaredPath.isFolder()) {
				throw new IllegalStateException("a drive is a folder, declare it folder: true or name an item id");
			}
			return reference;
		}

		PathInfo path = new PathInfo();
		path.absolutePath = (declaredPath.isFolder() ? GMicrosoftGraphNavigationUtils.DRIVE_ITEM_FOLDER_PREFIX
				: GMicrosoftGraphNavigationUtils.DRIVE_ITEM_PREFIX) + itemId;
		path.folder = declaredPath.isFolder();
		path.name = itemId;
		reference.path = path;
		return reference;
	}
}
