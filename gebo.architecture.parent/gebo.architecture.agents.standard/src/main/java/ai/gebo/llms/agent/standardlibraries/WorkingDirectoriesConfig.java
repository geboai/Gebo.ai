/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardlibraries;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.environment.EnvironmentHolder;
import lombok.Data;

/**
 * Where the working directories of the agents live. Set via
 * {@code ai.gebo.libraries.working-directory.*} in application.yml.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.libraries.working-directory")
@Data
public class WorkingDirectoriesConfig {
	/** Whether the working directory library is offered at all. */
	private boolean enabled = true;
	/**
	 * The folder every working directory is a sub-folder of. Empty, the
	 * {@code libraries/working-directories} folder of the work directory
	 * ({@code GEBO_WORK_DIRECTORY}).
	 */
	private String root = null;

	/** The root folder, as configured or as defaulted. */
	public Path rootPath() {
		if (root != null && !root.isBlank()) {
			return Paths.get(root).toAbsolutePath().normalize();
		}
		final String work = EnvironmentHolder.GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE;
		return Paths.get(work != null && !work.isBlank() ? work : ".", "libraries", "working-directories")
				.toAbsolutePath().normalize();
	}
}
