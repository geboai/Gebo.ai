/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.config.RedmineDataSourcesConfig;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.redmine.handler.model.RedmineSection;
import ai.gebo.redmine.handler.repositories.RedmineProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Seeds the Redmine data sources declared under ai.gebo.redmine.datasources.
 * A declared path starts with the project identifier and can go down:
 * <ul>
 * <li>{@code <project>}: the whole project</li>
 * <li>{@code <project>/issues}, {@code <project>/wiki},
 * {@code <project>/files}: one section</li>
 * <li>{@code <project>/issues/<number>}: one issue with its sub-issues</li>
 * <li>{@code <project>/wiki/<Title>}: one wiki page with its child pages</li>
 * <li>{@code <project>/files/<id>} with {@code folder: false}: one file</li>
 * </ul>
 * All but the single file are folders ({@code folder: true}).
 */
@Service
public class RedmineDeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GRedmineProjectEndpoint, RedmineProjectEndpointRepository> {

	public RedmineDeclaredDataSourcesSeeder(RedmineDataSourcesConfig config, RedmineProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GRedmineProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GRedmineProjectEndpoint endpoint = new GRedmineProjectEndpoint();
		endpoint.setRedmineSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GRedmineProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSource declaration, GDeclaredDataSourcePath declaredPath) {
		return toReference(declaredPath.getPath(), declaredPath.isFolder());
	}

	static VFilesystemReference toReference(String declared, boolean folder) {
		String path = declared.trim();
		while (path.startsWith("/")) {
			path = path.substring(1);
		}
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		String[] parts = path.split("/", 3);
		if (parts[0].isEmpty()) {
			throw new IllegalStateException("A Redmine declared path starts with the project identifier: " + declared);
		}
		String projectId = parts[0];
		List<RedminePathComponent> steps = new ArrayList<RedminePathComponent>();
		if (parts.length > 1) {
			RedmineSection section;
			try {
				section = RedmineSection.valueOf(parts[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalStateException(
						"A Redmine declared path goes on with issues, wiki or files: " + declared);
			}
			steps.add(new RedminePathComponent(RedminePathNodeType.SECTION, section.name()));
			if (parts.length > 2) {
				switch (section) {
				case ISSUES:
					steps.add(new RedminePathComponent(RedminePathNodeType.ISSUE, number(parts[2], declared)));
					break;
				case WIKI:
					steps.add(new RedminePathComponent(RedminePathNodeType.WIKI_PAGE, parts[2]));
					break;
				case FILES:
					steps.add(new RedminePathComponent(RedminePathNodeType.FILE, number(parts[2], declared)));
					break;
				}
			}
		}
		boolean isFolder = steps.isEmpty() || steps.get(steps.size() - 1).type.isFolder();
		if (isFolder != folder) {
			throw new IllegalStateException("The Redmine declared path " + declared + " is "
					+ (isFolder ? "a folder, declare it folder: true" : "a file, declare it folder: false"));
		}
		return RedmineNavigationUtil.toReference(projectId, projectId, steps, null);
	}

	private static String number(String value, String declared) {
		try {
			return String.valueOf(Integer.parseInt(value.startsWith("#") ? value.substring(1) : value));
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Not a Redmine number in the declared path " + declared, e);
		}
	}
}
