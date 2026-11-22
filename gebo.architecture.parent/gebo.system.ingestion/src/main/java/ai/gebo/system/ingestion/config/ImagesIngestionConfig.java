/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Whether the readers take the images of the documents they read, and within
 * which limits. Off at first: the readers then give texts only, as they always
 * did. On, each image - a picture file, a picture inside a document - is given
 * as a document of its own holding the image itself, with the metadata the
 * texts of its document have. The readers never call a model: what is done with
 * an image document is decided by who receives it (the vectorizator, see
 * {@code ai.gebo.vectorizator.embed-images}).
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.images")
@Data
public class ImagesIngestionConfig {
	/** The metadata of an image document telling the image's name in its document, when it has one. */
	public static final String IMAGE_NAME_METADATA = "gebo_image_name";
	private boolean ingestionEnabled = false;
	/** The images taken of one document at most; the ones after them are passed over and logged. */
	private int maxImagesPerDocument = 30;
	/** An image smaller than this is taken for a decoration (a bullet, a rule, an icon) and passed over. */
	private long minImageBytes = 2048;
	/** An image larger than this, in megabytes, is passed over. */
	private int maxImageMb = 20;

	public long getMaxImageBytes() {
		return Math.max(0, maxImageMb) * 1024L * 1024L;
	}
}
