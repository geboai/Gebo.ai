/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local.model;

import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * Configuration of the embedded (server-less) vector store.
 *
 * Every setting has a working default, so an installation that only writes
 *
 * <pre>
 * ai.gebo.vectorstore:
 *   use: LOCAL
 * </pre>
 *
 * gets a usable store whose data file lives under {@code $GEBO_WORK_DIRECTORY}.
 */
public class LocalConfig extends GBaseVectorStoreConfig {

	/**
	 * Directory that holds one JSON file per collection. When null the store falls
	 * back to {@code $GEBO_WORK_DIRECTORY/vectorstore-local}, so the data lands on
	 * the volume that is already part of the Gebo.ai backup unit.
	 */
	private String directory = null;

	/**
	 * When true every batch of writes is persisted before the call returns.
	 *
	 * It defaults to FALSE on purpose. The underlying store serialises its WHOLE
	 * content on each save, so writing after every batch turns one ingestion into
	 * a quadratic amount of I/O. With it off, saving is debounced by
	 * {@link #minimumSaveIntervalSeconds} and always happens when the store is
	 * closed or the JVM shuts down.
	 */
	private boolean saveOnEveryWrite = false;

	/**
	 * Shortest delay between two debounced saves, in seconds. A write that arrives
	 * sooner than this only marks the store dirty; the next write past the interval
	 * - or the shutdown save - writes it out. Ignored when
	 * {@link #saveOnEveryWrite} is true.
	 */
	private Integer minimumSaveIntervalSeconds = 30;

	/**
	 * Fragment count above which the store logs a capacity warning, once.
	 *
	 * The embedded store keeps the whole corpus in memory and scans all of it per
	 * query, which is the accepted trade-off of the single-dependency
	 * installation. This threshold is what tells an operator they have outgrown it
	 * and should point {@code ai.gebo.vectorstore.use} at a server-backed product.
	 * Null disables the warning.
	 */
	private Integer warnAboveFragments = 100000;

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public boolean isSaveOnEveryWrite() {
		return saveOnEveryWrite;
	}

	public void setSaveOnEveryWrite(boolean saveOnEveryWrite) {
		this.saveOnEveryWrite = saveOnEveryWrite;
	}

	public Integer getMinimumSaveIntervalSeconds() {
		return minimumSaveIntervalSeconds;
	}

	public void setMinimumSaveIntervalSeconds(Integer minimumSaveIntervalSeconds) {
		this.minimumSaveIntervalSeconds = minimumSaveIntervalSeconds;
	}

	public Integer getWarnAboveFragments() {
		return warnAboveFragments;
	}

	public void setWarnAboveFragments(Integer warnAboveFragments) {
		this.warnAboveFragments = warnAboveFragments;
	}
}
