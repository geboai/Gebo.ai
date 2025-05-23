/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.DriveList;

import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Service responsible for testing Google Drive connectivity and permissions.
 * This service validates Google Drive credentials and tests if the system
 * can successfully connect to Google Drive.
 */
@Service
public class GoogleDriveTestService {
	/** Logger for this class */
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveTestService.class);
	
	/** Factory for creating Google Drive credentials */
	@Autowired
	GoogleDriveCredentialsFactory credentialsFactory;

	/**
	 * Tests the connection to Google Drive using provided system credentials.
	 * 
	 * The method checks if:
	 * 1. Valid credentials exist
	 * 2. Credentials can be used to create a Drive service
	 * 3. The service can successfully list drives
	 * 
	 * @param system The Google Drive system configuration containing access credentials
	 * @return Operation status with result and any error messages
	 */
	public OperationStatus<GGoogleDriveSystem> test(GGoogleDriveSystem system) {
		OperationStatus<GGoogleDriveSystem> status = new OperationStatus<>();
		status.setResult(system);
		if (system.getDriveAccessSecret() == null || system.getDriveAccessSecret().trim().length() == 0) {
			// No credentials provided
			status.getMessages().add(
					GUserMessage.errorMessage("There are no credentials for google drive", "Missing configuration"));
		} else {
			Drive drive = null;
			try {
				// Attempt to create Drive service with credentials
				drive = credentialsFactory.getDriveService(system.getDriveAccessSecret());
			} catch (Throwable th) {
				final String msg="Invalid credentials accessing google cloud services";
				LOGGER.error(msg, th);
				status.getMessages()
						.add(GUserMessage.errorMessage(msg, th));
				return status;
			}
			try {
				// Test if we can successfully list drives
				DriveList list = drive.drives().list().execute();
				return OperationStatus.of(system);
			} catch (Throwable th) {
				final String msg="Credentials allocated but unable to accessing google drives";
				LOGGER.error(msg, th);
				status.getMessages().add(
						GUserMessage.errorMessage(msg, th));
			}
		}

		return status;
	}
}