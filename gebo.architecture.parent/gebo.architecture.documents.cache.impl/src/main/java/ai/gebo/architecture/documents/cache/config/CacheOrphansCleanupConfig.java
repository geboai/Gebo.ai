/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The release of what the chunking sessions left behind. The chunk files no record of a
 * living session names, and the cached document copies no record names, are deleted once
 * they are older than the grace period, which is also the interval between two checks (a
 * disposed session's files are touched at its disposal: their grace starts then). The
 * grace period covers the moments a distributed run has no settled state: a chunking
 * ending while its session is disposed, files written before their record, another
 * instance's records, a session reusing files just released. The disposed sessions and
 * their records (metadata, no content) are deleted after the retention.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.documents-cache.orphans")
@Data
public class CacheOrphansCleanupConfig {
	public static final long DEFAULT_GRACE_SECONDS = 300;
	public static final long DEFAULT_RETENTION_DAYS = 2;
	/** The age, in seconds, after which an orphaned cache entry or file is deleted. */
	private long graceSeconds = DEFAULT_GRACE_SECONDS;
	/**
	 * The days a disposed chunking session and its records are kept, for a late read to
	 * produce its chunks again.
	 */
	private long retentionDays = DEFAULT_RETENTION_DAYS;

	/** The grace period in milliseconds, never less than one second. */
	public long graceMillis() {
		return Math.max(1, graceSeconds) * 1000L;
	}

	/** The retention in milliseconds, never less than the grace period. */
	public long retentionMillis() {
		return Math.max(graceMillis(), retentionDays * 24L * 3600L * 1000L);
	}
}
