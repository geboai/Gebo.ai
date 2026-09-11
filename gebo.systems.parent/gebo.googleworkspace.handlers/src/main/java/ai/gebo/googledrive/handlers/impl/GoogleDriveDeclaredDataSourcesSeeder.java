/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.googledrive.handlers.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.config.GoogleDriveDataSourcesConfig;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveProjectEndpointRepository;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Writes the data sources declared under
 * {@code ai.gebo.googleworkspace.datasources} into the Google Drive endpoint
 * repository at startup.
 *
 * <h2>How a declared path becomes a Drive position</h2>
 * <p>
 * The module navigation root is the shared drive - {@code toNativeCoordinates}
 * resolves {@code root.code} through {@code drives().get(driveId)} - and a step
 * under it is a file id, carried as {@code DRIVE_FOLDER:} or
 * {@code DRIVE_ITEM:}. A declaration is therefore {@code driveId/fileId}, split
 * here on the first slash; neither id may contain one, so the split is exact.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GoogleDriveDeclaredDataSourcesSeeder extends
		GAbstractDeclaredDataSourcesSeeder<GGoogleDriveProjectEndpoint, GoogleDriveProjectEndpointRepository> {

	/**
	 * Constructs the seeder over the declared data sources and the repository they
	 * are written into.
	 *
	 * @param config             the declared Google Drive data sources.
	 * @param repository         the Google Drive endpoint repository.
	 * @param applicationContext this bean's own context.
	 */
	public GoogleDriveDeclaredDataSourcesSeeder(GoogleDriveDataSourcesConfig config,
			GoogleDriveProjectEndpointRepository repository, ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GGoogleDriveProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GGoogleDriveProjectEndpoint endpoint = new GGoogleDriveProjectEndpoint();
		endpoint.setDriveSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GGoogleDriveProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSource declaration,
			GDeclaredDataSourcePath declaredPath) {
		String declared = declaredPath.getPath().trim();
		while (declared.startsWith("/")) {
			declared = declared.substring(1);
		}
		int firstSlash = declared.indexOf('/');
		String driveId = firstSlash < 0 ? declared : declared.substring(0, firstSlash);
		String fileId = firstSlash < 0 ? "" : declared.substring(firstSlash + 1).trim();
		if (driveId.length() == 0) {
			throw new IllegalStateException("a Google Drive path starts with the drive id, as in driveId/fileId");
		}
		if (fileId.contains("/")) {
			throw new IllegalStateException(
					"a Google Drive path is driveId/fileId: Drive addresses items by id, not by folder name");
		}

		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(driveId);
		root.setAbsolutePath(driveId);
		root.setDescription(driveId);

		VFilesystemReference reference = new VFilesystemReference();
		reference.root = root;
		if (fileId.length() == 0) {
			// The drive itself: a root with no step.
			if (!declaredPath.isFolder()) {
				throw new IllegalStateException("a drive is a folder, declare it folder: true or name a file id");
			}
			return reference;
		}

		PathInfo path = new PathInfo();
		path.absolutePath = (declaredPath.isFolder() ? GoogleDriveNavigationUtil.GOOGLE_DRIVE_FOLDER_PREFIX
				: GoogleDriveNavigationUtil.GOOGLE_DRIVE_RESOURCE_PREFIX) + fileId;
		path.folder = declaredPath.isFolder();
		path.name = fileId;
		reference.path = path;
		return reference;
	}
}
