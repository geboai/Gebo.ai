/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.io.IOException;
import java.util.List;

import ai.gebo.model.base.TypedInputStream;

/**
 * Files a tool can reach the same way wherever they live: a library instance's
 * folder, the files uploaded to a conversation, the documents of a knowledge
 * base. A tool working on a kind of file - a spreadsheet, a document - names a
 * location and a path, reads the file, and writes it back when the location is
 * writable, without knowing where the bytes are.
 * <p>
 * Paths are relative to the location, folders separated by {@code /}; a path
 * leading out of the location is refused with an
 * {@link IllegalArgumentException}, as is a file that is not there.
 */
public interface IGFilesLocation {
	/** Version a write names when the file must not exist yet. */
	String ABSENT = "absent";

	/** The id the tools name the location by: a library type id, or a provider's id. */
	String getId();

	/** What the location is, written for the agents to read. */
	String getDescription();

	/** Whether files can be written here; a read-only location refuses every write. */
	boolean isWritable();

	/**
	 * The files of a folder of the location, or of the whole location.
	 *
	 * @param folder    the folder, null or empty for the location's top
	 * @param recursive whether the files of the folders inside are listed too
	 */
	List<GLibraryFile> list(String folder, boolean recursive) throws IOException;

	/** A file as it is now, or null when there is none at the path. */
	GLibraryFile describe(String path) throws IOException;

	/** A file's content and the version it was read at. */
	GLibraryFileContent read(String path) throws IOException;

	/**
	 * Writes a file whole, creating it and its folders when missing: a reader
	 * never sees half of it.
	 *
	 * @param path            the file
	 * @param content         its new content; its stream is read whole and closed
	 * @param expectedVersion null to write whatever the file is, {@link #ABSENT}
	 *                        when it must not exist, else the version it was read
	 *                        at: a file that changed meanwhile is not written
	 * @return the file as written, with its new version
	 * @throws GLibraryFileConflictException when the file is not at the expected
	 *                                       version
	 * @throws UnsupportedOperationException when the location is read-only
	 */
	GLibraryFile write(String path, TypedInputStream content, String expectedVersion) throws IOException;
}
