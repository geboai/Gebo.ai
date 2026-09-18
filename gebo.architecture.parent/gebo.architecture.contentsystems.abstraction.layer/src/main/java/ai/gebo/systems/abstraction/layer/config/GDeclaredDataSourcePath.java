/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * One path of a declared data source: what to ingest, and whether it is a folder
 * to walk or a single file to take.
 *
 * <p>
 * The {@code path} is written in the addressing the content handler itself uses
 * - a WebDAV href, a {@code bucket/key} for S3, a {@code driveId/itemId} for
 * Google Drive and OneDrive - and each module translates it into the
 * {@code VFilesystemReference} its navigation understands. It is deliberately
 * NOT a common syntax invented for the configuration: a remote system's notion
 * of "where" is its own, and a translation layer that pretended otherwise would
 * only be able to express the intersection.
 * </p>
 *
 * <p>
 * {@code folder} is not derivable from the string - no remote call is made while
 * reading the configuration, and for the id-addressed systems the string carries
 * no hint at all - so it is declared. Getting it wrong is caught at ingestion,
 * where the navigation refuses a node whose kind disagrees with the declaration.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Data
public class GDeclaredDataSourcePath {

	/**
	 * Where to ingest from, in the content handler's own addressing. Required.
	 */
	@NotBlank
	private String path = null;

	/**
	 * {@code true} for a folder whose contents are walked, {@code false} (the
	 * default) for a single file taken on its own.
	 */
	private boolean folder = false;
}
