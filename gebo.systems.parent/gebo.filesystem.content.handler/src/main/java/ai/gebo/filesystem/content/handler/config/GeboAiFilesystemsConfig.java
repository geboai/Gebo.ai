/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.filesystem.content.handler.GFileSystemShareReference;

/**
 * AI generated comments
 * Configuration class for the Gebo AI filesystem settings.
 * This class binds properties from the application configuration with the prefix "ai.gebo.filesystem"
 * to Java properties, allowing for type-safe access to configuration values.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.filesystem")
public class GeboAiFilesystemsConfig {
    /**
     * List of file share configurations available in the system.
     * Defaults to an empty ArrayList.
     */
	private List<GeboFileShareConfig> shares = new ArrayList<GeboFileShareConfig>();
    
    /**
     * Flag indicating whether filesystem shares UI is allowed.
     * Defaults to false.
     */
	private Boolean allowFilesystemSharesUI = false;

    /**
     * The most threads that look for the changes of the files of the data sources
     * that follow them (ai.gebo.filesystem.watchers-pool-limits).
     */
	private int watchersPoolLimits = 1;

    /**
     * Every how many seconds those threads look, counted from the end of a look at
     * every data source to the beginning of the next (ai.gebo.filesystem.watchers-period).
     */
	private long watchersPeriod = 60;

    /**
     * For how many minutes the changes found in a data source gather, from the first
     * one, before they are handed to the ingestion together
     * (ai.gebo.filesystem.watcher-change-events-window).
     */
	private long watcherChangeEventsWindow = 5;

    /**
     * Default constructor for GeboAiFilesystemsConfig.
     */
	public GeboAiFilesystemsConfig() {

	}

    /**
     * Gets the list of file share configurations.
     * 
     * @return List of GeboFileShareConfig objects representing configured shares
     */
	public List<GeboFileShareConfig> getShares() {
		return shares;
	}

    /**
     * Sets the list of file share configurations.
     * 
     * @param shares List of GeboFileShareConfig objects to be set
     */
	public void setShares(List<GeboFileShareConfig> shares) {
		this.shares = shares;
	}

    /**
     * Gets the flag indicating whether filesystem shares UI is allowed.
     * 
     * @return Boolean value - true if UI for filesystem shares is allowed, false otherwise
     */
	public Boolean getAllowFilesystemSharesUI() {
		return allowFilesystemSharesUI;
	}

    /**
     * Sets the flag for allowing filesystem shares UI.
     * 
     * @param allowFilesystemSharesUI Boolean value to set the UI permission
     */
	public void setAllowFilesystemSharesUI(Boolean allowFilesystemSharesUI) {
		this.allowFilesystemSharesUI = allowFilesystemSharesUI;
	}

	public int getWatchersPoolLimits() {
		return watchersPoolLimits;
	}

	public void setWatchersPoolLimits(int watchersPoolLimits) {
		this.watchersPoolLimits = watchersPoolLimits;
	}

	public long getWatchersPeriod() {
		return watchersPeriod;
	}

	public void setWatchersPeriod(long watchersPeriod) {
		this.watchersPeriod = watchersPeriod;
	}

	public long getWatcherChangeEventsWindow() {
		return watcherChangeEventsWindow;
	}

	public void setWatcherChangeEventsWindow(long watcherChangeEventsWindow) {
		this.watcherChangeEventsWindow = watcherChangeEventsWindow;
	}

}