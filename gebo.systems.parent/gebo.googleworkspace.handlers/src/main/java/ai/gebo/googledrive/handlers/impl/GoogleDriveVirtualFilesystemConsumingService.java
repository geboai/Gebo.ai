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
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemConsumingService;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveCustomPosition;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveCustomPosition.GoogleDriveType;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNativePositionObject;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNavigationCoordinates;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResourceReference;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * Service class that provides virtual filesystem functionality for Google Drive.
 * This implementation extends the abstract remote virtual filesystem service
 * to handle specific Google Drive operations such as navigating through drives, folders,
 * and accessing files.
 */
@Service
public class GoogleDriveVirtualFilesystemConsumingService extends
		GAbstractRemoteVirtualFilesystemConsumingService<GGoogleDriveSystem, GGoogleDriveProjectEndpoint, GoogleDriveNativePositionObject, GoogleDriveNavigationCoordinates, GoogleDriveResourceReference>
		implements IGGoogleDriveVirtualFilesystemConsumingService {

	/** Constant key used to store the Drive service in the cache map */
	public static final String DRIVE_SERVICE = "DRIVE-SERVICE";
	
	/** Factory for creating Google Drive credentials */
	final GoogleDriveCredentialsFactory credentialsFactory;

	/**
	 * Constructor with required dependencies
	 * 
	 * @param documentFactory Factory for creating document references
	 * @param credentialsFactory Factory for creating Google Drive credentials
	 */
	public GoogleDriveVirtualFilesystemConsumingService(IGDocumentReferenceFactory documentFactory,
			GoogleDriveCredentialsFactory credentialsFactory) {
		super(documentFactory);
		this.credentialsFactory = credentialsFactory;

	}

	/**
	 * Creates a resource handle from a virtual filesystem object
	 * 
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param reference The virtual filesystem object
	 * @param cache The cache map
	 * @return A Google Drive resource reference
	 */
	@Override
	public GoogleDriveResourceReference getResourceHandle(GGoogleDriveSystem system,
			GGoogleDriveProjectEndpoint endpoint, GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		GoogleDriveResourceReference _reference = new GoogleDriveResourceReference();
		_reference.driveId = (String) reference.getCustomMetaInfos()
				.get(GoogleDriveNativePositionObject.GOOGLE_DRIVE_ID);
		_reference.folderId = (String) reference.getCustomMetaInfos()
				.get(GoogleDriveNativePositionObject.GOOGLE_FOLDER_ID);
		_reference.resourceId = (String) reference.getCustomMetaInfos()
				.get(GoogleDriveNativePositionObject.GOOGLE_RESOURCE_ID);
		return _reference;
	}

	/**
	 * Opens a stream to a Google Drive resource
	 * 
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param reference The resource reference
	 * @param cache The cache map
	 * @return InputStream to the requested resource
	 */
	@Override
	public InputStream streamResource(GGoogleDriveSystem system, GGoogleDriveProjectEndpoint endpoint,
			GoogleDriveResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		if (!cache.containsKey(DRIVE_SERVICE)) {
			IGContentsAccessErrorConsumer acc = IGContentsAccessErrorConsumer.defaultImplementation();
			cache.putAll(createEnvironment(system, endpoint, acc));
		}
		Drive driveService = (Drive) cache.get(DRIVE_SERVICE);
		return driveService.files().get(reference.resourceId).setSupportsAllDrives(true).executeMediaAsInputStream();
	}

	/**
	 * Returns the messaging module ID for Google Drive
	 * 
	 * @return The module ID string
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GOOGLE_DRIVE_MODULE;
	}

	/**
	 * Constructs navigation coordinates from a list of native position objects
	 * 
	 * @param childCoordinates List of native position objects
	 * @return Navigation coordinates for Google Drive
	 */
	@Override
	protected GoogleDriveNavigationCoordinates getPositionCoordinate(
			List<GoogleDriveNativePositionObject> childCoordinates) throws GeboContentHandlerSystemException {
		GoogleDriveNavigationCoordinates coordinates = new GoogleDriveNavigationCoordinates();

		PathInfo currentPath = null;
		for (GoogleDriveNativePositionObject item : childCoordinates) {
			GoogleDriveCustomPosition position = new GoogleDriveCustomPosition();
			position.id = item.getCode();

			if (item.isDrive()) {
				coordinates.setRoot(GoogleDriveNavigationUtil.toRoot(item.getDrive()));
				position.type = GoogleDriveType.DRIVE;
			} else if (item.isFolderFile()) {
				if (currentPath == null) {
					currentPath = GoogleDriveNavigationUtil.toPath(item.getFolderFile());
					coordinates.getBrowsingSteps().add(currentPath);
				} else {
					currentPath = GoogleDriveNavigationUtil.toPath(currentPath, item.getFolderFile());
					coordinates.getBrowsingSteps().add(currentPath);
				}
				position.type = GoogleDriveType.FOLDER;
			} else if (item.isFileResource()) {
				if (currentPath == null) {
					currentPath = GoogleDriveNavigationUtil.toPath(item.getResourceFile());
					coordinates.getBrowsingSteps().add(currentPath);
				} else {
					currentPath = GoogleDriveNavigationUtil.toPath(currentPath, item.getResourceFile());
					coordinates.getBrowsingSteps().add(currentPath);
				}
				position.type = GoogleDriveType.RESOURCE;
			}
			coordinates.getBrowsingStepsCustom().add(position);
		}

		return coordinates;
	}

	/**
	 * Retrieves child objects from a Google Drive folder
	 * 
	 * @param nativeCoordinates The current position in native coordinates
	 * @param position The navigation coordinates
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param messagesConsumer Consumer for user messages
	 * @param environment Environment containing the Drive service
	 * @return List of coordinate pointers to child objects
	 */
	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<GoogleDriveNativePositionObject> nativeCoordinates,
			GoogleDriveNavigationCoordinates position, GGoogleDriveSystem system, GGoogleDriveProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		GoogleDriveNativePositionObject lastNode = lastNode(nativeCoordinates);
		List<NativeCoordinatePointer> pointers = new ArrayList<>();
		String folderId = lastNode.getCode();
		String driveId = position.getRoot().getCode();
		String query = "'" + folderId + "' in parents and trashed = false";
		Drive driveService = (Drive) environment.get(DRIVE_SERVICE);
		try {
			Drive.Files.List request = driveService.files().list().setQ(query).setDriveId(driveId)
					.setSupportsAllDrives(true).setFields("nextPageToken, files(id, name, mimeType)");

			FileList result;
			do {
				result = request.execute();
				List<File> files = result.getFiles();
				for (File file : files) {
					NativeCoordinatePointer pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
					pointer.child = new GoogleDriveNativePositionObject();
					if (file.getMimeType() != null
							&& file.getMimeType().equals(GoogleDriveNavigationUtil.GOOGLE_DRIVE_FOLDER_CONTENT_TYPE)) {
						pointer.child.setFolderFile(file);
					} else {
						pointer.child.setResourceFile(file);
					}
					pointers.add(pointer);
				}
				request.setPageToken(result.getNextPageToken());
			} while (request.getPageToken() != null);
		} catch (IOException e) {
			throw new GeboContentHandlerSystemException("Error accessing google drive", e);
		}
		return pointers;
	}

	/**
	 * Converts navigation coordinates to native Google Drive coordinates
	 * 
	 * @param position The navigation coordinates
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param root The root virtual folder
	 * @param consumer The content consumer
	 * @param messagesConsumer Consumer for user messages
	 * @param errorConsumer Consumer for access errors
	 * @param environment Environment containing the Drive service
	 * @return List of native position objects
	 */
	@Override
	protected List<GoogleDriveNativePositionObject> toNativeCoordinates(GoogleDriveNavigationCoordinates position,
			GGoogleDriveSystem system, GGoogleDriveProjectEndpoint endpoint, GVirtualFolder root,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<GoogleDriveNativePositionObject> path = new ArrayList<>();
		String driveId = position.getRoot().getCode();
		Drive drive = (Drive) environment.get(DRIVE_SERVICE);
		try {
			com.google.api.services.drive.model.Drive driveObject = drive.drives().get(driveId).execute();
			GoogleDriveNativePositionObject driveNative = new GoogleDriveNativePositionObject();
			driveNative.setDrive(driveObject);
			path.add(driveNative);
			for (GoogleDriveCustomPosition item : position.getBrowsingStepsCustom()) {
				File file = drive.files().get(item.id).execute();
				if (file == null)
					new GeboContentHandlerSystemException("File id=>" + item.id + " is not found");
				GoogleDriveNativePositionObject itemNative = new GoogleDriveNativePositionObject();
				if (file.getMimeType() != null
						&& file.getMimeType().equals(GoogleDriveNavigationUtil.GOOGLE_DRIVE_FOLDER_CONTENT_TYPE)) {
					itemNative.setFolderFile(file);
				} else {
					itemNative.setResourceFile(file);
				}
				path.add(itemNative);
			}
		} catch (IOException e) {
			throw new GeboContentHandlerSystemException("Cannot access google drive", e);
		}
		return path;
	}

	/**
	 * Creates an environment with the Google Drive service
	 * 
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param errorConsumer Consumer for access errors
	 * @return Map containing the Drive service
	 */
	@Override
	protected Map<String, Object> createEnvironment(GGoogleDriveSystem system, GGoogleDriveProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put(DRIVE_SERVICE, credentialsFactory.getDriveService(system.getDriveAccessSecret()));
		} catch (GeboCryptSecretException | IOException | VirtualFilesystemBrowsingException
				| GeneralSecurityException e) {
			throw new GeboContentHandlerSystemException("Cannot access google drive service", e);
		}
		return map;
	}

	/**
	 * Clears the environment map
	 * 
	 * @param environment The environment map to clear
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 */
	@Override
	protected void clearEnvironment(Map<String, Object> environment, GGoogleDriveSystem system,
			GGoogleDriveProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		environment.clear();
	}

	/**
	 * Converts a filesystem reference to navigation coordinates
	 * 
	 * @param path The filesystem reference
	 * @return Google Drive navigation coordinates
	 */
	@Override
	protected GoogleDriveNavigationCoordinates toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException {
		return GoogleDriveNavigationUtil.toCoordinates(path);
	}

	/**
	 * Returns a description of the object at the specified location
	 * 
	 * @param references List of native position objects
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param environment The environment map
	 * @return Description of the object
	 */
	@Override
	protected String describeObject(List<GoogleDriveNativePositionObject> references, GGoogleDriveSystem system,
			GGoogleDriveProjectEndpoint endpoint, Map<String, Object> environment) {
		GoogleDriveNativePositionObject last = lastNode(references);
		return last.getName();
	}

	/**
	 * Returns a description of the Google Drive system
	 * 
	 * @param system The Google Drive system
	 * @return Description of the system
	 */
	@Override
	protected String describeSystem(GGoogleDriveSystem system) {
		return "Google drive " + system.getDescription();
	}

	/**
	 * Returns a description of the project endpoint
	 * 
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param environment The environment map
	 * @return Description of the project endpoint
	 */
	@Override
	protected String describeProjectEndpoint(GGoogleDriveSystem system, GGoogleDriveProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "Google drive data source " + endpoint.getDescription();
	}

	/**
	 * Verifies that a remote object exists
	 * 
	 * @param system The Google Drive system
	 * @param endpoint The project endpoint
	 * @param doc The virtual filesystem object
	 * @param reference The resource reference
	 * @param environment The environment map
	 * @return The verified virtual filesystem object
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GGoogleDriveSystem system,
			GGoogleDriveProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc,
			GoogleDriveResourceReference reference, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		return doc;
	}
}