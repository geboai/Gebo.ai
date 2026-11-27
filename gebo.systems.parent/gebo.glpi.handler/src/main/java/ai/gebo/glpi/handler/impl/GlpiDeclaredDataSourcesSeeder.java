/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.config.GlpiDataSourcesConfig;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.repositories.GlpiProjectEndpointRepository;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Writes the GLPI data sources declared in configuration as readonly records.
 * A declared path is the GLPI navigation encoding with the entity first:
 * {@code ENTITY:0}, {@code ENTITY:0|TICKETS},
 * {@code ENTITY:0|TICKETS|TICKET:1042}, {@code ENTITY:0|KB},
 * {@code ENTITY:0|KB|KBARTICLE:15}.
 */
@Service
public class GlpiDeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GGlpiProjectEndpoint, GlpiProjectEndpointRepository> {

	public GlpiDeclaredDataSourcesSeeder(GlpiDataSourcesConfig config, GlpiProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GGlpiProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GGlpiProjectEndpoint endpoint = new GGlpiProjectEndpoint();
		endpoint.setGlpiSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GGlpiProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSource declaration,
			GDeclaredDataSourcePath declaredPath) {
		String declared = declaredPath.getPath().trim();
		int separator = declared.indexOf(GlpiNavigationUtil.PATH_SEPARATOR);
		String rootPart = separator >= 0 ? declared.substring(0, separator) : declared;
		String pathPart = separator >= 0 ? declared.substring(separator + 1) : null;
		if (!rootPart.startsWith(GlpiNavigationUtil.ENTITY_PREFIX)) {
			throw new IllegalStateException("a GLPI declared path starts with the entity (" + GlpiNavigationUtil.ENTITY_PREFIX
					+ "<id>), found: " + declared);
		}
		Long entityId = GlpiNavigationUtil.entityIdOf(rootOf(rootPart));
		if (entityId == null) {
			throw new IllegalStateException("not a GLPI entity id: " + rootPart);
		}
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = GlpiNavigationUtil.toRoot(entityId, null);
		if (pathPart == null || pathPart.isBlank()) {
			if (!declaredPath.isFolder()) {
				throw new IllegalStateException("a GLPI entity is a folder, declare it folder: true (" + declared + ")");
			}
			return reference;
		}
		List<GlpiPathComponent> components = GlpiNavigationUtil.parsePath(pathPart);
		if (components.isEmpty() || GlpiNavigationUtil.encodePath(components).length() != pathPart.trim().length()) {
			throw new IllegalStateException("not a GLPI path: " + pathPart);
		}
		GlpiPathComponent last = components.get(components.size() - 1);
		if (last.type.isFolder() != declaredPath.isFolder()) {
			throw new IllegalStateException("the GLPI path " + declared + " is " + (last.type.isFolder() ? "" : "not ")
					+ "a folder: declare it folder: " + last.type.isFolder());
		}
		PathInfo path = null;
		for (GlpiPathComponent component : components) {
			path = GlpiNavigationUtil.toPathInfo(path, component, null);
		}
		path.name = GlpiNavigationUtil.segment(last);
		reference.path = path;
		return reference;
	}

	private static GVirtualFilesystemRoot rootOf(String encoded) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(encoded.trim());
		return root;
	}
}
