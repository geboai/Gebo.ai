/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.config.OpenProjectDataSourcesConfig;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.openproject.handler.model.OpenProjectSection;
import ai.gebo.openproject.handler.repositories.OpenProjectProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Seeds the OpenProject data sources declared under ai.gebo.openproject.datasources.
 * A declared path starts with the project identifier and can go down:
 * <ul>
 * <li>{@code <project>}: the whole project</li>
 * <li>{@code <project>/work_packages}, {@code <project>/documents},
 * {@code <project>/meetings}, {@code <project>/news}: one section</li>
 * <li>{@code <project>/work_packages/<number>}: one work package with its
 * children</li>
 * <li>{@code <project>/documents/<number>}, {@code <project>/meetings/<number>}:
 * one document or meeting with its attachments</li>
 * <li>{@code <project>/news/<number>} with {@code folder: false}: one news</li>
 * </ul>
 * All but the single file are folders ({@code folder: true}).
 */
@Service
public class OpenProjectDeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GOpenProjectProjectEndpoint, OpenProjectProjectEndpointRepository> {

	public OpenProjectDeclaredDataSourcesSeeder(OpenProjectDataSourcesConfig config, OpenProjectProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GOpenProjectProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GOpenProjectProjectEndpoint endpoint = new GOpenProjectProjectEndpoint();
		endpoint.setOpenProjectSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GOpenProjectProjectEndpoint endpoint, List<VFilesystemReference> references) {
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
			throw new IllegalStateException("An OpenProject declared path starts with the project identifier: " + declared);
		}
		String projectId = parts[0];
		List<OpenProjectPathComponent> steps = new ArrayList<OpenProjectPathComponent>();
		if (parts.length > 1) {
			OpenProjectSection section;
			try {
				section = OpenProjectSection.valueOf(parts[1].toUpperCase(Locale.ROOT).replace('-', '_'));
			} catch (IllegalArgumentException e) {
				throw new IllegalStateException("An OpenProject declared path goes on with work_packages, documents,"
						+ " meetings or news: " + declared);
			}
			steps.add(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, section.name()));
			if (parts.length > 2) {
				OpenProjectPathNodeType type = switch (section) {
				case WORK_PACKAGES -> OpenProjectPathNodeType.WORK_PACKAGE;
				case DOCUMENTS -> OpenProjectPathNodeType.DOCUMENT;
				case MEETINGS -> OpenProjectPathNodeType.MEETING;
				case NEWS -> OpenProjectPathNodeType.NEWS;
				};
				steps.add(new OpenProjectPathComponent(type, number(parts[2], declared)));
			}
		}
		boolean isFolder = steps.isEmpty() || steps.get(steps.size() - 1).type.isFolder();
		if (isFolder != folder) {
			throw new IllegalStateException("The OpenProject declared path " + declared + " is "
					+ (isFolder ? "a folder, declare it folder: true" : "a file, declare it folder: false"));
		}
		return OpenProjectNavigationUtil.toReference(projectId, projectId, steps, null);
	}

	private static String number(String value, String declared) {
		try {
			return String.valueOf(Integer.parseInt(value.startsWith("#") ? value.substring(1) : value));
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Not an OpenProject number in the declared path " + declared, e);
		}
	}
}
