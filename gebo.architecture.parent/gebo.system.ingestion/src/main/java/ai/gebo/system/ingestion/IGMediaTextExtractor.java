/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion;

import java.io.IOException;
import java.io.InputStream;

/**
 * Turns what a text reader cannot read into text with a model: a picture or a scanned
 * page into a description of what it shows and the text it carries, a recording or a
 * video into the transcript of what is said. The text then follows the way of every
 * other document - chunked, embedded, searched.
 * <p>
 * It costs model calls on every file, so both are off unless the installation turns
 * them on ({@code ai.gebo.ingestion.media}). Where no implementation is deployed, or a
 * capability is off or has no model to run on, the readers behave as if it was not
 * there: the file stays one no reader takes.
 */
public interface IGMediaTextExtractor {
	/** Metadata key marking a text a model wrote from a picture or a recording: "description" or "transcript". */
	String MODEL_WRITTEN_METADATA = "gebo_model_written";

	/** Whether pictures and scanned pages are described: turned on, and a model that reads pictures is there. */
	boolean describesPictures();

	/**
	 * What a picture shows and the text it carries.
	 *
	 * @return the description; null or empty when the model gave none
	 */
	String describePicture(String name, String mimeType, byte[] data) throws IOException;

	/** The scanned pages of one document that are described at most; the pages beyond stay empty. */
	int maxDescribedPagesPerDocument();

	/** The size in bytes a picture or a recording may have to be read. */
	long maxFileBytes();

	/** Whether recordings and videos are transcribed: turned on, and a transcription model is there. */
	boolean transcribes();

	/**
	 * What is said in a recording or in a video.
	 *
	 * @return the transcript; null or empty when the model gave none
	 */
	String transcribe(String name, String mimeType, InputStream content) throws IOException;
}
