/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.pdf.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The PDF ingestion: whether a page in columns is read column by column
 * ({@link ColumnAwarePdfTextExtractor}) or line by line across its whole width as before.
 * Set via ai.gebo.ingestion.pdf.column-aware-extraction (true when not set).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.ingestion.pdf")
@Data
public class PdfIngestionConfig {
	private boolean columnAwareExtraction = true;
}
