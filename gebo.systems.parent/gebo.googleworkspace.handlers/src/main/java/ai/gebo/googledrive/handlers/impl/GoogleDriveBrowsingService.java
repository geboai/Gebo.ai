/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.DriveList;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.GoogleDriveSystemContext;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemBrowser;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * This service implements the Google Drive virtual filesystem browser interface.
 * It provides functionality to browse Google Drive contents, list drive roots,
 * and navigate through folders.
 */
@Service
public class GoogleDriveBrowsingService implements IGGoogleDriveVirtualFilesystemBrowser {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveBrowsingService.class);
	public final static String APPLICATION_NAME = "gebo.ai";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	@Autowired
	GoogleDriveCredentialsFactory credentialsFactory;
	@Autowired
	IGPersistentObjectManager persistenceManager;

	/**
	 * Retrieves a Google Drive system configuration by its code.
	 * 
	 * @param driveSystemCode The code identifying the Google Drive system
	 * @return The Google Drive system configuration
	 * @throws GeboPersistenceException If there's an error retrieving the system from persistence
	 */
	private GGoogleDriveSystem getGoogleDriveSystem(String driveSystemCode) throws GeboPersistenceException {
		return persistenceManager.findById(GGoogleDriveSystem.class, driveSystemCode);
	}

	/**
	 * Creates a Drive service instance for the specified Google Drive system.
	 * 
	 * @param driveSystemCode The code identifying the Google Drive system
	 * @return A configured Drive service
	 * @throws GeboPersistenceException If there's an error retrieving the system from persistence
	 * @throws GeboCryptSecretException If there's an error with the cryptographic secret
	 * @throws GeboContentHandlerSystemException If there's a content handling system error
	 * @throws IOException If there's an I/O error
	 * @throws GeneralSecurityException If there's a security-related error
	 * @throws VirtualFilesystemBrowsingException If there's an error browsing the virtual filesystem
	 */
	private Drive driveServiceByDriveSystemCode(String driveSystemCode)
			throws GeboPersistenceException, GeboCryptSecretException, GeboContentHandlerSystemException, IOException,
			GeneralSecurityException, VirtualFilesystemBrowsingException {
		GGoogleDriveSystem system = getGoogleDriveSystem(driveSystemCode);
		String driveAccessSecret = system.getDriveAccessSecret();

		return credentialsFactory.getDriveService(driveAccessSecret);

	}

	/**
	 * Retrieves all root folders (shared drives) from Google Drive.
	 * 
	 * @param driveSystemCode The code identifying the Google Drive system
	 * @return An operation status containing the list of root folders or error messages
	 */
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(String driveSystemCode) {
		List<GVirtualFilesystemRoot> roots = new ArrayList<>();
		OperationStatus<List<GVirtualFilesystemRoot>> status = null;
		try {
			GGoogleDriveSystem system = getGoogleDriveSystem(driveSystemCode);
			Drive service = driveServiceByDriveSystemCode(driveSystemCode);
			
			Drive.Drives.List request = service.drives().list();
			DriveList drives;
			boolean continueCycle = true;
			do {
				drives = request.execute();
				List<com.google.api.services.drive.model.Drive> driveList = drives.getDrives();
				if (driveList == null || driveList.isEmpty()) {
					continueCycle = false;
				} else {
					for (com.google.api.services.drive.model.Drive drive : driveList) {
						GVirtualFilesystemRoot rootItem = GoogleDriveNavigationUtil.toRoot(drive);
						roots.add(rootItem);
					}
				}
				request.setPageToken(drives.getNextPageToken());
			} while (request.getPageToken() != null && continueCycle);
			status = OperationStatus.of(roots);
		} catch (Throwable th) {
			String msg = "Error accessing google drive";
			LOGGER.error(msg, th);
			status = OperationStatus.of(roots);
			status.getMessages().clear();
			status.getMessages().add(GUserMessage.errorMessage(msg, th));
		}
		return status;
	}

	/**
	 * Defines the number of files/folders to load per API request
	 */
	private static final int LOAD_PER_ITERATION = 1000;

	/**
	 * Browses a specific path in Google Drive and retrieves its contents.
	 * 
	 * @param param The browsing parameters including root and path
	 * @param driveSystemCode The code identifying the Google Drive system
	 * @return An operation status containing the list of path items or error messages
	 */
	private OperationStatus<List<PathInfo>> browsePath(BrowseParam param, String driveSystemCode) {
		OperationStatus<List<PathInfo>> status = null;
		List<PathInfo> out = new ArrayList<PathInfo>();
		GVirtualFilesystemRoot root = param.root;
		PathInfo path = param.path;

		try {
			Drive driveService = driveServiceByDriveSystemCode(driveSystemCode);
			if (path == null) {
				// Browse root folder content
				FileList result = null;
				int i = 1;
				String nextPageToken = null;
				final String query = "'" + root.getCode() + "' in parents and trashed = false";

				do {
					if (nextPageToken == null) {
						/*
						 * .setSupportsAllDrives(true).setSupportsTeamDrives(true).
						 * setIncludeItemsFromAllDrives(true) .setIncludeTeamDriveItems(true)
						 */
						result = driveService.files().list().setQ(query).setDriveId(root.getCode())
								.setPageSize(LOAD_PER_ITERATION)

								.setFields("nextPageToken, files(id, name,mimeType)").execute();
					} else {
						/*
						 * .setSupportsAllDrives(true).setSupportsTeamDrives(true)
						 * .setIncludeItemsFromAllDrives(true).setIncludeTeamDriveItems(true)
						 */
						result = driveService.files().list().setQ(query).setDriveId(root.getCode())
								.setPageSize(LOAD_PER_ITERATION).setPageToken(nextPageToken)
								.setFields("nextPageToken, files(id, name,mimeType)").execute();
					}
					nextPageToken = result.getNextPageToken();
					List<File> files = result.getFiles();
					for (File file : files) {
						PathInfo pathInfo = GoogleDriveNavigationUtil.toPath(file);
						out.add(pathInfo);
					}
					i++;
				} while (nextPageToken == null);
			} else {
				// Browse specific folder content
				String folderId = GoogleDriveNavigationUtil.getDriveFolderId(path);
				String query = "'" + folderId + "' in parents and trashed = false";

				Drive.Files.List request = driveService.files().list().setQ(query).setDriveId(root.getCode())
						.setSupportsAllDrives(true).setFields("nextPageToken, files(id, name, mimeType)");

				FileList result;
				do {
					result = request.execute();
					List<File> files = result.getFiles();
					for (File file : files) {
						PathInfo pathInfo = GoogleDriveNavigationUtil.toPath(path, file);
						out.add(pathInfo);
					}
					request.setPageToken(result.getNextPageToken());
				} while (request.getPageToken() != null);
			}
			status = OperationStatus.of(out);
		} catch (Throwable th) {
			String msg = "Error accessing google drive";
			LOGGER.error(msg, th);
			status = OperationStatus.of(out);
			status.getMessages().clear();
			status.getMessages().add(GUserMessage.errorMessage(msg, th));
		}
		return status;
	}

	/**
	 * Retrieves all root folders (shared drives) from Google Drive using the context.
	 * 
	 * @param context The Google Drive system context
	 * @return An operation status containing the list of root folders or error messages
	 * @throws VirtualFilesystemBrowsingException If there's an error browsing the virtual filesystem
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GoogleDriveSystemContext context)
			throws VirtualFilesystemBrowsingException {

		return getRoots(context.getSystemCode());

	}

	/**
	 * Browses a specific path in Google Drive and retrieves its contents using the context.
	 * 
	 * @param param The browsing parameters including root and path
	 * @param context The Google Drive system context
	 * @return An operation status containing the list of path items or error messages
	 * @throws VirtualFilesystemBrowsingException If there's an error browsing the virtual filesystem
	 */
	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, GoogleDriveSystemContext context)
			throws VirtualFilesystemBrowsingException {

		return browsePath(param, context.getSystemCode());

	}

	/**
	 * Checks if navigation status is supported.
	 * 
	 * @return false as navigation status is not supported in this implementation
	 */
	@Override
	public boolean isSupportsNavigationStatus() {

		return false;
	}

	/**
	 * Gets the parent reference for a given virtual filesystem reference.
	 * 
	 * @param reference The virtual filesystem reference
	 * @param context The Google Drive system context
	 * @return The parent reference (null in this implementation)
	 * @throws VirtualFilesystemBrowsingException If there's an error browsing the virtual filesystem
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, GoogleDriveSystemContext context)
			throws VirtualFilesystemBrowsingException {

		return null;
	}

}