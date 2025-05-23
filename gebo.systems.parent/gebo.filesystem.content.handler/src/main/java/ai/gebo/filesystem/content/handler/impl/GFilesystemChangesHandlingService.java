/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.SnapshotStateRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService.GConsumers;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * 
 * Service responsible for monitoring and handling filesystem changes for registered endpoints.
 * This class listens for changes in the filesystem and triggers appropriate actions when files are modified.
 * It implements both ApplicationListener to initialize on application startup and FileChangeListener
 * to respond to file changes.
 */
public class GFilesystemChangesHandlingService
		implements ApplicationListener<ContextRefreshedEvent>, FileChangeListener {
	private static Logger LOGGER = LoggerFactory.getLogger(GFilesystemChangesHandlingService.class);
	boolean started = false;
	/** File system watcher configured to check for changes every 30 seconds with a 5-second quiet period */
	FileSystemWatcher watcher = new FileSystemWatcher(true, Duration.ofSeconds(30), Duration.ofSeconds(5),
			SnapshotStateRepository.STATIC);
	/** List of monitored endpoints and their corresponding filesystem folders */
	List<EndpointFolderEntries> handledEndpoints = new ArrayList<EndpointFolderEntries>();
	/** Runtime binder for dependency resolution */
	IGRuntimeBinder runtimeBinder = null;

	/**
	 * Inner class representing the association between a filesystem folder and a project endpoint
	 */
	static class EndpointFolderEntries {
		EndpointFolderEntries(File f, GFilesystemProjectEndpoint e) {
			this.folder = f;
			this.endpoint = e;
		}

		File folder = null;
		GFilesystemProjectEndpoint endpoint = null;
	}

	/**
	 * Constructor that initializes the service with a runtime binder
	 * 
	 * @param runtimeBinder Dependency injection handler
	 */
	public GFilesystemChangesHandlingService(IGRuntimeBinder runtimeBinder) {
		this.runtimeBinder = runtimeBinder;
	}

	/**
	 * Adds a project endpoint to be monitored for filesystem changes
	 * 
	 * @param endpoint The project endpoint to monitor
	 */
	public void addHandling(GFilesystemProjectEndpoint endpoint) {

		if (endpoint.getPath() != null) {
			for (VFilesystemReference endpath : endpoint.getPath()) {
				String path = endpath.path != null ? endpath.path.absolutePath : endpath.root.getAbsolutePath();
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					handledEndpoints.add(new EndpointFolderEntries(file, endpoint));
					if (started)
						watcher.stop();
					watcher.addSourceDirectory(file);
					if (started)
						watcher.start();
				}
			}
		}

	}

	/**
	 * Removes a project endpoint from being monitored
	 * 
	 * @param endpoint The project endpoint to stop monitoring
	 */
	public void removeHandling(GFilesystemProjectEndpoint endpoint) {
		EndpointFolderEntries f = null;
		for (EndpointFolderEntries e : handledEndpoints) {
			if (e.endpoint.getCode().equals(endpoint.getCode())) {
				f = e;
			}
		}
		if (f != null) {
			handledEndpoints.remove(f);
		}
	}

	/**
	 * Initializes the service when the application context is ready.
	 * Loads all project endpoints from the repository and starts monitoring them.
	 * 
	 * @param event The context refreshed event
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		FilesystemProjectEndpointRepository repo = runtimeBinder
				.getImplementationOf(FilesystemProjectEndpointRepository.class);
		Stream<GFilesystemProjectEndpoint> stream = repo.findAll().stream();
		stream.forEach(x -> {
			addHandling(x);
		});
		watcher.addListener(this);
		if (!started) {
			watcher.start();
			started = true;
		}

	}

	/**
	 * Handles file change events by processing each changed file through the appropriate endpoint handler.
	 * This method is called by the file system watcher when changes are detected.
	 * 
	 * @param changeSet Set of changed files grouped by source directory
	 */
	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		GFilesystemContentManagementSystemHandlerImpl handler = this.runtimeBinder
				.getImplementationOf(GFilesystemContentManagementSystemHandlerImpl.class);
		IGeboInversionOfControlIngestionService iocService = this.runtimeBinder
				.getImplementationOf(IGeboInversionOfControlIngestionService.class);
		for (ChangedFiles changedFiles : changeSet) {
			File directory = changedFiles.getSourceDirectory();
			for (EndpointFolderEntries e : handledEndpoints) {
				if (e.folder.getAbsolutePath().equals(directory.getAbsolutePath())) {
					GConsumers consumers = null;
					try {
						consumers = iocService.getConsumer(e.endpoint);
						handler.consume(e.endpoint, consumers.getContentConsumer(), consumers.getMessagesConsumer(),
								consumers.getErrorConsumer(),changedFiles);
					} catch (Throwable e1) {
						LOGGER.error("exception in filesystem changes handling", e1);
					} finally {
						try {
							consumers.getContentConsumer().endConsuming();
						} catch (Throwable t1) {
						}
						try {
							consumers.getMessagesConsumer().endConsuming();
						} catch (Throwable t1) {
						}
						try {
							consumers.end();
						} catch (Throwable t1) {
						}
					}
				}
			}
		}
	}
}