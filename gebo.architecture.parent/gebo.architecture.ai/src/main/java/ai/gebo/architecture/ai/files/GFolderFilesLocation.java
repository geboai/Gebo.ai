/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ai.gebo.model.base.TypedInputStream;

/**
 * A files location that is a folder on disk: what a library source keeping its
 * instances in folders gives as their files. Nothing outside the folder is
 * reached, links leading out of it included; names starting with a dot are the
 * source's own (its metadata, the files being saved) and are neither listed nor
 * read nor written.
 * <p>
 * A write goes to a file beside the target, then takes its place; reads and
 * writes of the same file - from this location or any other one on the same
 * folder, whatever tool asks - take turns on one lock per file, so that a
 * version read is the version of the content read with it.
 */
public class GFolderFilesLocation implements IGFilesLocation {
	private static final Map<Path, ReentrantLock> LOCKS = new ConcurrentHashMap<>();
	private static final String SAVING_PREFIX = ".saving-";
	/** The default largest file read or written: 50 MB. */
	public static final long DEFAULT_MAX_FILE_SIZE = 50L * 1024 * 1024;

	private final String id;
	private final String description;
	private final Path root;
	private final boolean writable;
	private final long maxFileSize;

	public GFolderFilesLocation(String id, String description, Path root, boolean writable) {
		this(id, description, root, writable, DEFAULT_MAX_FILE_SIZE);
	}

	public GFolderFilesLocation(String id, String description, Path root, boolean writable, long maxFileSize) {
		this.id = id;
		this.description = description;
		this.root = root.toAbsolutePath().normalize();
		this.writable = writable;
		this.maxFileSize = maxFileSize;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isWritable() {
		return writable;
	}

	public Path getRoot() {
		return root;
	}

	/**
	 * Runs an action on a file while no other reader or writer of it - through
	 * any folder location - does: what a tool changing a file in place in several
	 * steps holds the file with.
	 */
	public static <R> R withLock(Path file, Supplier<R> action) {
		final ReentrantLock lock = LOCKS.computeIfAbsent(file.toAbsolutePath().normalize(),
				any -> new ReentrantLock());
		lock.lock();
		try {
			return action.get();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * The file a path names in the folder: refused when it leads out of it, by
	 * dots or by links, or names one of the source's own files.
	 */
	public Path resolve(String path) throws IOException {
		final String given = path != null ? path.trim().replace('\\', '/') : "";
		if (given.isEmpty() || given.equals("/") || given.equals(".")) {
			return root;
		}
		final Path resolved = root.resolve(given.startsWith("/") ? given.substring(1) : given).normalize();
		if (!resolved.startsWith(root)) {
			throw new IllegalArgumentException("Not a path of " + id + ": " + path);
		}
		for (Path part : root.relativize(resolved)) {
			if (part.toString().startsWith(".")) {
				throw new IllegalArgumentException("Not a path of " + id + ": " + path);
			}
		}
		// a link inside the folder must not lead out of it: the deepest existing part is checked for real
		Path existing = resolved;
		while (existing != null && !Files.exists(existing, LinkOption.NOFOLLOW_LINKS)) {
			existing = existing.getParent();
		}
		if (existing != null && Files.exists(root)) {
			final Path real = existing.toRealPath();
			if (!real.startsWith(root.toRealPath())) {
				throw new IllegalArgumentException("Not a path of " + id + ": " + path);
			}
		}
		return resolved;
	}

	/** A path of the folder as the tools name it: relative, separated by /. */
	public String relative(Path file) {
		return root.relativize(file.toAbsolutePath().normalize()).toString().replace('\\', '/');
	}

	@Override
	public List<GLibraryFile> list(String folder, boolean recursive) throws IOException {
		final Path start = resolve(folder);
		final List<GLibraryFile> files = new ArrayList<>();
		if (!Files.isDirectory(start)) {
			if (Files.isRegularFile(start)) {
				throw new IllegalArgumentException("Not a folder of " + id + ": " + folder);
			}
			return files;
		}
		try (Stream<Path> walk = recursive ? Files.walk(start) : Files.list(start)) {
			for (Path file : walk.sorted().toList()) {
				if (file.equals(start) || hidden(file) || !Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
					continue;
				}
				files.add(fileOf(file, Files.readAttributes(file, BasicFileAttributes.class)));
			}
		}
		return files;
	}

	private boolean hidden(Path file) {
		for (Path part : root.relativize(file)) {
			if (part.toString().startsWith(".")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public GLibraryFile describe(String path) throws IOException {
		final Path file = resolve(path);
		if (!Files.isRegularFile(file)) {
			return null;
		}
		return fileOf(file, Files.readAttributes(file, BasicFileAttributes.class));
	}

	@Override
	public GLibraryFileContent read(String path) throws IOException {
		final Path file = resolve(path);
		return withIO(file, () -> {
			if (!Files.isRegularFile(file)) {
				throw new IllegalArgumentException("There is no file " + path + " in " + id);
			}
			final BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
			checkSize(path, attributes.size());
			final byte[] bytes = Files.readAllBytes(file);
			final GLibraryFile described = fileOf(file, attributes);
			return new GLibraryFileContent(described,
					TypedInputStream.of(new ByteArrayInputStream(bytes), described.contentType(), extensionOf(file)));
		});
	}

	@Override
	public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) throws IOException {
		if (!writable) {
			throw new UnsupportedOperationException(id + " is read-only: write the file into a writable location");
		}
		if (content == null) {
			throw new IllegalArgumentException("No content to write to " + path);
		}
		final Path file = resolve(path);
		if (file.equals(root)) {
			throw new IllegalArgumentException("A file path is needed in " + id);
		}
		return withIO(file, () -> {
			if (Files.isDirectory(file)) {
				throw new IllegalArgumentException(path + " is a folder of " + id);
			}
			checkVersion(path, file, expectedVersion);
			Files.createDirectories(file.getParent());
			final Path temp = Files.createTempFile(file.getParent(), SAVING_PREFIX, ".tmp");
			try {
				long written;
				try (InputStream in = content.getInputStream(); OutputStream out = Files.newOutputStream(temp)) {
					written = transfer(path, in, out);
				}
				checkSize(path, written);
				try {
					Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
				} catch (AtomicMoveNotSupportedException notAtomic) {
					Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				Files.deleteIfExists(temp);
			}
			return fileOf(file, Files.readAttributes(file, BasicFileAttributes.class));
		});
	}

	private long transfer(String path, InputStream in, OutputStream out) throws IOException {
		final byte[] buffer = new byte[64 * 1024];
		long total = 0;
		int read;
		while ((read = in.read(buffer)) >= 0) {
			total += read;
			checkSize(path, total);
			out.write(buffer, 0, read);
		}
		return total;
	}

	private void checkVersion(String path, Path file, String expectedVersion) throws IOException {
		if (expectedVersion == null) {
			return;
		}
		final boolean exists = Files.isRegularFile(file);
		if (ABSENT.equals(expectedVersion)) {
			if (exists) {
				throw new GLibraryFileConflictException("There is a file " + path + " in " + id + " already");
			}
			return;
		}
		if (!exists) {
			throw new GLibraryFileConflictException("The file " + path + " of " + id + " was deleted since it was read");
		}
		if (!expectedVersion.equals(versionOf(Files.readAttributes(file, BasicFileAttributes.class)))) {
			throw new GLibraryFileConflictException(
					"The file " + path + " of " + id + " changed since it was read: read it again");
		}
	}

	private void checkSize(String path, long size) {
		if (size > maxFileSize) {
			throw new IllegalArgumentException(
					"The file " + path + " is too large: " + size + " bytes, at most " + maxFileSize + " here");
		}
	}

	private GLibraryFile fileOf(Path file, BasicFileAttributes attributes) {
		return new GLibraryFile(id, relative(file), attributes.size(), attributes.lastModifiedTime().toString(),
				versionOf(attributes), contentTypeOf(file.getFileName().toString()));
	}

	/** Size and time of the last change: what a rewrite always changes, at no reading cost. */
	static String versionOf(BasicFileAttributes attributes) {
		return attributes.size() + "-" + attributes.lastModifiedTime().toInstant().getEpochSecond() + "."
				+ attributes.lastModifiedTime().toInstant().getNano();
	}

	/** The extension of a file with its dot, as document references have it, or null. */
	static String extensionOf(Path file) {
		final String name = file.getFileName().toString();
		final int dot = name.lastIndexOf('.');
		return dot > 0 ? name.substring(dot).toLowerCase(Locale.ROOT) : null;
	}

	/** The media type of a file name: the office ones first, which the JDK does not all know. */
	public static String contentTypeOf(String name) {
		final String lower = name.toLowerCase(Locale.ROOT);
		final int dot = lower.lastIndexOf('.');
		final String extension = dot >= 0 ? lower.substring(dot + 1) : "";
		final String known = switch (extension) {
		case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "xlsm" -> "application/vnd.ms-excel.sheet.macroEnabled.12";
		case "xls" -> "application/vnd.ms-excel";
		case "ods" -> "application/vnd.oasis.opendocument.spreadsheet";
		case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		case "doc" -> "application/msword";
		case "odt" -> "application/vnd.oasis.opendocument.text";
		case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		case "odp" -> "application/vnd.oasis.opendocument.presentation";
		case "pdf" -> "application/pdf";
		case "md" -> "text/markdown";
		case "json" -> "application/json";
		case "csv" -> "text/csv";
		default -> null;
		};
		return known != null ? known : URLConnection.guessContentTypeFromName(name);
	}

	@FunctionalInterface
	private interface IOAction<R> {
		R run() throws IOException;
	}

	private static <R> R withIO(Path file, IOAction<R> action) throws IOException {
		try {
			return withLock(file, () -> {
				try {
					return action.run();
				} catch (IOException failed) {
					throw new WrappedIO(failed);
				}
			});
		} catch (WrappedIO wrapped) {
			throw wrapped.io;
		}
	}

	private static final class WrappedIO extends RuntimeException {
		private static final long serialVersionUID = 1L;
		final IOException io;

		WrappedIO(IOException io) {
			super(io);
			this.io = io;
		}
	}
}
