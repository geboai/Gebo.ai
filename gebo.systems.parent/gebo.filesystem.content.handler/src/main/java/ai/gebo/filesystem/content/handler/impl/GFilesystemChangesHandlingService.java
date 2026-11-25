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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.filesystem.content.handler.GFilesystemChangesQueueEntry;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.repositories.FilesystemChangesQueueRepository;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService.GConsumers;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;

/**
 * Follows the changes of the files of the filesystem data sources that ask for it
 * ({@link GFilesystemProjectEndpoint#getSyncOnFilesModifications()}) and hands them to
 * the ingestion.
 * <p>
 * A pool of at most {@code ai.gebo.filesystem.watchers-pool-limits} threads wakes
 * every {@code ai.gebo.filesystem.watchers-period} seconds, counted from the end of a
 * look at every data source to the beginning of the next. At each wake-up the data
 * sources that follow their changes are read from the repository and put in one
 * queue the threads of the pool take them from: none is tied to a thread, and one
 * created, flagged, unflagged or deleted is simply there, or no longer there, at the
 * next wake-up - nothing is stopped for it.
 * <p>
 * The service keeps, for each folder of a data source, the picture of its files as it
 * last saw them (name, size, time of the last change). A folder met for the first
 * time is only pictured: what changes from then on is told. A data source not yet
 * published, or being published, is not looked at: its picture stays, so what changed
 * meanwhile is found by the first look after. A change is told once the folder stayed
 * as it is for the quiet time, so a file being written is not read half way; a folder
 * still changing is looked at again at the next wake-up.
 * <p>
 * What a look finds is not handed to the ingestion at once: it is stored
 * ({@link GFilesystemChangesQueueEntry}) and the changes of a data source gather for
 * {@code ai.gebo.filesystem.watcher-change-events-window} minutes, counted from the
 * first one found. Then the whole group is handed over together, in one session of
 * the ingestion, so a folder that keeps changing does not keep its data source always
 * in publication. A group is handed over only while its data source is not being
 * published: it waits through a publication, and is still there after a restart.
 */
public class GFilesystemChangesHandlingService implements ApplicationListener<ContextRefreshedEvent>, AutoCloseable {
	private static Logger LOGGER = LoggerFactory.getLogger(GFilesystemChangesHandlingService.class);
	/** The threads of the pool when the installation states none. */
	static final int DEFAULT_POOL_LIMIT = 1;
	/** The seconds between two wake-ups when the installation states none. */
	static final long DEFAULT_PERIOD_SECONDS = 60;
	/** The minutes the changes of a data source gather for when the installation states none. */
	static final long DEFAULT_CHANGES_WINDOW_MINUTES = 5;
	/** How long a changed folder must stay as it is before its changes are told. */
	static final Duration QUIET_PERIOD = Duration.ofSeconds(5);
	/** The most files one stored entry holds: a look that finds more stores several. */
	static final int MAX_FILES_PER_ENTRY = 1000;
	/** How many times handing a group of stored changes to the ingestion is tried before it is dropped. */
	static final int MAX_ATTEMPTS = 3;

	volatile boolean started = false;
	/** Runtime binder for dependency resolution */
	final IGRuntimeBinder runtimeBinder;
	private final int poolLimit;
	private final long periodSeconds;
	private final long changesWindowMinutes;
	private volatile ScheduledExecutorService pool = null;
	/**
	 * The pictures: for the code of a data source, for the absolute path of each of its
	 * folders, the files as last seen.
	 */
	private final Map<String, Map<String, Map<String, FileState>>> pictures = new ConcurrentHashMap<>();

	/** What is compared of a file between two looks. */
	record FileState(long length, long lastModified) {
	}

	/**
	 * Constructor that initializes the service with a runtime binder
	 *
	 * @param runtimeBinder Dependency injection handler
	 */
	public GFilesystemChangesHandlingService(IGRuntimeBinder runtimeBinder) {
		this(runtimeBinder, DEFAULT_POOL_LIMIT, DEFAULT_PERIOD_SECONDS, DEFAULT_CHANGES_WINDOW_MINUTES);
	}

	/**
	 * @param runtimeBinder        Dependency injection handler
	 * @param poolLimit            the most threads looking for changes, at least one
	 * @param periodSeconds        the seconds between two wake-ups, at least one
	 * @param changesWindowMinutes the minutes the changes of a data source gather for,
	 *                             from the first one found, before they are handed over
	 *                             together; zero hands them over at the look that finds them
	 */
	public GFilesystemChangesHandlingService(IGRuntimeBinder runtimeBinder, int poolLimit, long periodSeconds,
			long changesWindowMinutes) {
		this.runtimeBinder = runtimeBinder;
		this.poolLimit = Math.max(1, poolLimit);
		this.periodSeconds = Math.max(1, periodSeconds);
		this.changesWindowMinutes = Math.max(0, changesWindowMinutes);
	}

	/**
	 * Starts the pool when the application context is ready.
	 * <p>
	 * {@link ContextRefreshedEvent} bubbles up to this listener from every
	 * descendant context too - including the short-lived per-service child
	 * contexts Spring Cloud LoadBalancer creates the first time a
	 * {@code @LoadBalanced} client is used. {@link #started} guards against
	 * running this initialization more than once for those spurious re-fires.
	 *
	 * @param event The context refreshed event
	 */
	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		if (started) {
			return;
		}
		final AtomicInteger number = new AtomicInteger();
		pool = Executors.newScheduledThreadPool(poolLimit, task -> {
			final Thread thread = new Thread(task, "filesystem-changes-" + number.incrementAndGet());
			thread.setDaemon(true);
			return thread;
		});
		pool.scheduleWithFixedDelay(this::pass, periodSeconds, periodSeconds, TimeUnit.SECONDS);
		started = true;
		LOGGER.info("The changes of the files of the filesystem data sources that follow them are looked for every "
				+ periodSeconds + " second(s) by at most " + poolLimit + " thread(s), and gather for "
				+ changesWindowMinutes + " minute(s) before they are handed to the ingestion");
	}

	/** Stops the pool with the application: a look under way is interrupted. */
	@Override
	public void close() {
		final ScheduledExecutorService running = pool;
		if (running != null) {
			running.shutdownNow();
		}
	}

	/**
	 * One wake-up: every data source that follows its changes is visited once, by the
	 * threads of the pool taking them from one queue. Nothing thrown here stops the
	 * next wake-ups.
	 */
	void pass() {
		try {
			final List<GFilesystemProjectEndpoint> followed = followed();
			forgetTheOthers(followed);
			if (followed.isEmpty()) {
				return;
			}
			final Queue<GFilesystemProjectEndpoint> queue = new ConcurrentLinkedQueue<>(followed);
			final Runnable serve = () -> {
				GFilesystemProjectEndpoint next;
				while (!Thread.currentThread().isInterrupted() && (next = queue.poll()) != null) {
					try {
						visit(next);
					} catch (InterruptedException stopping) {
						Thread.currentThread().interrupt();
					} catch (Throwable failure) {
						// one data source that cannot be looked at never stops the others
						LOGGER.error("The changes of the files of the filesystem data source " + next.getCode()
								+ " cannot be looked for, it is tried again at the next wake-up", failure);
					}
				}
			};
			// this thread serves the queue too: with a limit of one no other is used
			final List<Future<?>> others = new ArrayList<>();
			for (int i = 1; i < poolLimit && i < followed.size(); i++) {
				others.add(pool.submit(serve));
			}
			serve.run();
			for (Future<?> other : others) {
				other.get();
			}
		} catch (InterruptedException stopping) {
			Thread.currentThread().interrupt();
		} catch (Throwable failure) {
			LOGGER.error("The changes of the files of the filesystem data sources cannot be looked for, "
					+ "it is tried again at the next wake-up", failure);
		}
	}

	/** The data sources that follow the changes of their files, as stored now. */
	private List<GFilesystemProjectEndpoint> followed() {
		final List<GFilesystemProjectEndpoint> followed = new ArrayList<>();
		for (GFilesystemProjectEndpoint endpoint : endpoints().findBySyncOnFilesModificationsTrue()) {
			if (endpoint != null && endpoint.getCode() != null) {
				followed.add(endpoint);
			}
		}
		return followed;
	}

	/**
	 * Forgets the data sources no longer followed - unflagged or deleted: their
	 * pictures, and the changes stored for them, which nobody would hand over.
	 */
	private void forgetTheOthers(List<GFilesystemProjectEndpoint> followed) {
		final Set<String> codes = new HashSet<>();
		for (GFilesystemProjectEndpoint endpoint : followed) {
			codes.add(endpoint.getCode());
		}
		pictures.keySet().retainAll(codes);
		final FilesystemChangesQueueRepository stored = stored();
		if (codes.isEmpty()) {
			if (stored.count() > 0) {
				stored.deleteAll();
			}
		} else {
			stored.deleteByEndpointCodeNotIn(codes);
		}
	}

	/**
	 * The visit to one data source: its new folders are pictured; when it is published
	 * and not being published its folders are looked at, what changed is stored, and
	 * the changes stored for it are handed over when they gathered for long enough.
	 */
	void visit(GFilesystemProjectEndpoint endpoint) throws InterruptedException {
		final Map<String, Map<String, FileState>> known = pictures.computeIfAbsent(endpoint.getCode(),
				code -> new ConcurrentHashMap<>());
		final List<File> folders = foldersOf(endpoint);
		final Set<String> paths = new HashSet<>();
		for (File folder : folders) {
			paths.add(folder.getAbsolutePath());
		}
		known.keySet().retainAll(paths);
		// a folder met for the first time is only pictured: what changes from now on is told
		final List<File> pictured = new ArrayList<>();
		for (File folder : folders) {
			if (known.containsKey(folder.getAbsolutePath())) {
				pictured.add(folder);
			} else if (folder.isDirectory()) {
				known.put(folder.getAbsolutePath(), pictureOf(folder));
			}
		}
		if (!Boolean.TRUE.equals(endpoint.getPublished())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Filesystem data source " + endpoint.getCode() + " is not published: not looked at");
			}
			return;
		}
		if (beingPublished(endpoint)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Filesystem data source " + endpoint.getCode() + " is being published: not looked at");
			}
			return;
		}
		final Map<File, Map<String, FileState>> changed = new LinkedHashMap<>();
		for (File folder : pictured) {
			final Map<String, FileState> now = pictureOf(folder);
			if (!now.equals(known.get(folder.getAbsolutePath()))) {
				changed.put(folder, now);
			}
		}
		if (changed.isEmpty()) {
			handOverDue(endpoint);
			return;
		}
		// a file being written is not read half way: what changed is told once the folder
		// stayed as it is for the quiet time
		Thread.sleep(QUIET_PERIOD.toMillis());
		for (Map.Entry<File, Map<String, FileState>> seen : changed.entrySet()) {
			final File folder = seen.getKey();
			final Map<String, FileState> settled = pictureOf(folder);
			if (!settled.equals(seen.getValue())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Folder " + folder + " of filesystem data source " + endpoint.getCode()
							+ " is still changing: looked at again at the next wake-up");
				}
				continue;
			}
			// stored first: a change that cannot be stored is found again at the next look
			store(endpoint, folder, known.get(folder.getAbsolutePath()), settled);
			known.put(folder.getAbsolutePath(), settled);
		}
		// a publication may have begun during the quiet time
		if (!beingPublished(endpoint)) {
			handOverDue(endpoint);
		}
	}

	/**
	 * The folders the path of a data source names. An entry that names no folder
	 * (neither a path nor a root) is left out: the data source's other folders are
	 * followed all the same.
	 */
	private static List<File> foldersOf(GFilesystemProjectEndpoint endpoint) {
		final List<File> folders = new ArrayList<>();
		final Set<String> met = new HashSet<>();
		if (endpoint.getPath() != null) {
			for (VFilesystemReference entry : endpoint.getPath()) {
				final String path = VFilesystemReference.absolutePathOf(entry);
				if (path == null) {
					LOGGER.warn("Filesystem endpoint " + endpoint.getCode()
							+ " has a path entry naming no folder: it is not followed");
					continue;
				}
				final File folder = new File(path);
				if (met.add(folder.getAbsolutePath())) {
					folders.add(folder);
				}
			}
		}
		return folders;
	}

	/**
	 * Whether a publication of the data source is running. When it cannot be told the
	 * data source is left alone until the next wake-up.
	 */
	private boolean beingPublished(GFilesystemProjectEndpoint endpoint) {
		try {
			final IGGeboIngestionJobQueueService jobs = runtimeBinder
					.getImplementationOf(IGGeboIngestionJobQueueService.class);
			return jobs.isRunningSyncJob(endpoint);
		} catch (RuntimeException unknown) {
			LOGGER.warn("Cannot tell whether filesystem data source " + endpoint.getCode()
					+ " is being published, it is left alone until the next wake-up: " + unknown);
			return true;
		}
	}

	/** The files of a folder and of the folders inside it, each with what is compared of it. */
	static Map<String, FileState> pictureOf(File folder) {
		final Map<String, FileState> files = new HashMap<>();
		collect(folder, files);
		return files;
	}

	private static void collect(File folder, Map<String, FileState> files) {
		final File[] children = folder.listFiles();
		if (children == null) {
			return;
		}
		for (File child : children) {
			if (child.isDirectory()) {
				collect(child, files);
			} else if (child.isFile()) {
				files.put(child.getAbsolutePath(), new FileState(child.length(), child.lastModified()));
			}
		}
	}

	/**
	 * Stores what changed in a folder between two pictures of it, in entries of at
	 * most {@link #MAX_FILES_PER_ENTRY} files.
	 */
	private void store(GFilesystemProjectEndpoint endpoint, File folder, Map<String, FileState> before,
			Map<String, FileState> now) {
		// in the order the ingestion takes them: the new files, the changed ones, the gone ones
		final List<String[]> changes = new ArrayList<>();
		for (String path : new TreeSet<>(now.keySet())) {
			if (!before.containsKey(path)) {
				changes.add(new String[] { ChangedFile.Type.ADD.name(), path });
			}
		}
		for (String path : new TreeSet<>(now.keySet())) {
			if (before.containsKey(path) && !before.get(path).equals(now.get(path))) {
				changes.add(new String[] { ChangedFile.Type.MODIFY.name(), path });
			}
		}
		for (String path : new TreeSet<>(before.keySet())) {
			if (!now.containsKey(path)) {
				changes.add(new String[] { ChangedFile.Type.DELETE.name(), path });
			}
		}
		final Date detectedAt = new Date();
		final List<GFilesystemChangesQueueEntry> entries = new ArrayList<>();
		GFilesystemChangesQueueEntry entry = null;
		int held = 0;
		for (String[] change : changes) {
			if (entry == null || held == MAX_FILES_PER_ENTRY) {
				entry = new GFilesystemChangesQueueEntry();
				entry.setEndpointCode(endpoint.getCode());
				entry.setFolder(folder.getAbsolutePath());
				entry.setDetectedAt(detectedAt);
				entry.setPart(entries.size());
				entries.add(entry);
				held = 0;
			}
			switch (ChangedFile.Type.valueOf(change[0])) {
			case ADD -> entry.getAdded().add(change[1]);
			case MODIFY -> entry.getModified().add(change[1]);
			case DELETE -> entry.getDeleted().add(change[1]);
			}
			held++;
		}
		stored().saveAll(entries);
		LOGGER.info("Filesystem data source " + endpoint.getCode() + ": " + changes.size()
				+ " changed file(s) found in " + folder + ", stored in " + entries.size() + " entry(ies)");
	}

	/**
	 * Hands the changes stored for a data source to the ingestion when they gathered
	 * for the window, counted from the first one found: the whole group together, in
	 * one session. The group is removed once handed over. One that cannot be handed
	 * over stays for the next wake-up; after {@link #MAX_ATTEMPTS} failures it is
	 * dropped, and the next publication of the data source finds what it held.
	 */
	private void handOverDue(GFilesystemProjectEndpoint endpoint) {
		final FilesystemChangesQueueRepository stored = stored();
		final List<GFilesystemChangesQueueEntry> group = stored
				.findByEndpointCodeOrderByDetectedAtAscPartAsc(endpoint.getCode());
		if (group.isEmpty()) {
			return;
		}
		final Date first = group.get(0).getDetectedAt();
		if (first != null
				&& first.getTime() + TimeUnit.MINUTES.toMillis(changesWindowMinutes) > System.currentTimeMillis()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Filesystem data source " + endpoint.getCode() + ": " + group.size()
						+ " stored entry(ies) of changes still gathering since " + first);
			}
			return;
		}
		try {
			final List<ChangedFiles> changes = changesOf(group);
			handOver(endpoint, changes);
			stored.deleteAll(group);
			LOGGER.info("Filesystem data source " + endpoint.getCode() + ": the changes gathered since " + first
					+ " (" + group.size() + " stored entry(ies)) were handed to the ingestion");
		} catch (Throwable failure) {
			int attempts = 0;
			for (GFilesystemChangesQueueEntry entry : group) {
				attempts = Math.max(attempts, entry.getAttempts());
			}
			attempts++;
			if (attempts >= MAX_ATTEMPTS) {
				LOGGER.error("The changes stored for filesystem data source " + endpoint.getCode()
						+ " could not be handed to the ingestion " + MAX_ATTEMPTS
						+ " times: they are dropped, the next publication of the data source finds them", failure);
				stored.deleteAll(group);
			} else {
				LOGGER.error("The changes stored for filesystem data source " + endpoint.getCode()
						+ " could not be handed to the ingestion: tried again at the next wake-up", failure);
				for (GFilesystemChangesQueueEntry entry : group) {
					entry.setAttempts(attempts);
				}
				stored.saveAll(group);
			}
		}
	}

	/**
	 * What a group of stored entries comes to, folder by folder: a file found changed
	 * more than once is told once, as what happened to it over the whole group - new
	 * and then gone, nothing; new and then changed, new; there before and gone at the
	 * end, gone; there before and still there, changed.
	 */
	static List<ChangedFiles> changesOf(List<GFilesystemChangesQueueEntry> group) {
		// for each folder, for each file: what it was found as first and last
		final Map<String, Map<String, ChangedFile.Type[]>> byFolder = new LinkedHashMap<>();
		for (GFilesystemChangesQueueEntry entry : group) {
			final Map<String, ChangedFile.Type[]> files = byFolder.computeIfAbsent(entry.getFolder(),
					folder -> new LinkedHashMap<>());
			note(files, entry.getAdded(), ChangedFile.Type.ADD);
			note(files, entry.getModified(), ChangedFile.Type.MODIFY);
			note(files, entry.getDeleted(), ChangedFile.Type.DELETE);
		}
		final List<ChangedFiles> changes = new ArrayList<>();
		for (Map.Entry<String, Map<String, ChangedFile.Type[]>> ofFolder : byFolder.entrySet()) {
			final File folder = new File(ofFolder.getKey());
			final Set<ChangedFile> files = new LinkedHashSet<>();
			for (Map.Entry<String, ChangedFile.Type[]> ofFile : ofFolder.getValue().entrySet()) {
				final boolean newInTheGroup = ofFile.getValue()[0] == ChangedFile.Type.ADD;
				final boolean goneAtTheEnd = ofFile.getValue()[1] == ChangedFile.Type.DELETE;
				if (newInTheGroup && goneAtTheEnd) {
					continue;
				}
				files.add(new ChangedFile(folder, new File(ofFile.getKey()), newInTheGroup ? ChangedFile.Type.ADD
						: goneAtTheEnd ? ChangedFile.Type.DELETE : ChangedFile.Type.MODIFY));
			}
			if (!files.isEmpty()) {
				changes.add(new ChangedFiles(folder, files));
			}
		}
		return changes;
	}

	private static void note(Map<String, ChangedFile.Type[]> files, List<String> paths, ChangedFile.Type found) {
		if (paths == null) {
			return;
		}
		for (String path : paths) {
			final ChangedFile.Type[] firstAndLast = files.get(path);
			if (firstAndLast == null) {
				files.put(path, new ChangedFile.Type[] { found, found });
			} else {
				firstAndLast[1] = found;
			}
		}
	}

	/**
	 * Hands the changed files of the folders of a data source to the ingestion, in one
	 * session, through the handler of the filesystem data sources.
	 */
	protected void handOver(GFilesystemProjectEndpoint endpoint, List<ChangedFiles> changes) throws Exception {
		if (changes.isEmpty()) {
			return;
		}
		final IGFilesystemContentManagementSystemHandler handler = this.runtimeBinder
				.getImplementationOf(IGFilesystemContentManagementSystemHandler.class);
		final IGeboInversionOfControlIngestionService iocService = this.runtimeBinder
				.getImplementationOf(IGeboInversionOfControlIngestionService.class);
		final GConsumers consumers = iocService.getConsumer(endpoint, new NoContentConsumingSessionParam());
		try {
			for (ChangedFiles changedFiles : changes) {
				handler.consume(endpoint, consumers.getContentConsumer(), consumers.getMessagesConsumer(),
						consumers.getErrorConsumer(), changedFiles);
			}
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

	private FilesystemProjectEndpointRepository endpoints() {
		return runtimeBinder.getImplementationOf(FilesystemProjectEndpointRepository.class);
	}

	private FilesystemChangesQueueRepository stored() {
		return runtimeBinder.getImplementationOf(FilesystemChangesQueueRepository.class);
	}
}
