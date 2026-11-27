/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.config.ZammadDataSourcesConfig;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.zammad.handler.repositories.ZammadProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Seeds the Zammad data sources declared under ai.gebo.zammad.datasources.
 * A declared path starts with the kind of its root and the root's id, and can
 * go down:
 * <ul>
 * <li>{@code groups/<group id>}: every ticket of a group</li>
 * <li>{@code groups/<group id>/tickets/<ticket id>}: one ticket with its
 * attachments</li>
 * <li>{@code knowledge_bases/<knowledge base id>}: every published or internal
 * answer of a knowledge base</li>
 * <li>{@code knowledge_bases/<knowledge base id>/categories/<category id>}: a
 * category with its sub-categories</li>
 * <li>{@code knowledge_bases/<knowledge base id>/answers/<answer id>}: one
 * answer with its attachments</li>
 * </ul>
 * Every declared path is a folder.
 */
@Service
public class ZammadDeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GZammadProjectEndpoint, ZammadProjectEndpointRepository> {

	public ZammadDeclaredDataSourcesSeeder(ZammadDataSourcesConfig config, ZammadProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GZammadProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GZammadProjectEndpoint endpoint = new GZammadProjectEndpoint();
		endpoint.setZammadSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GZammadProjectEndpoint endpoint, List<VFilesystemReference> references) {
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
		String[] parts = path.split("/");
		if (parts.length != 2 && parts.length != 4) {
			throw new IllegalStateException("A Zammad declared path is groups/<id>, groups/<id>/tickets/<id>,"
					+ " knowledge_bases/<id>, knowledge_bases/<id>/categories/<id> or"
					+ " knowledge_bases/<id>/answers/<id>: " + declared);
		}
		ZammadPathNodeType rootType = switch (parts[0]) {
		case "groups" -> ZammadPathNodeType.GROUP;
		case "knowledge_bases" -> ZammadPathNodeType.KNOWLEDGE_BASE;
		default -> throw new IllegalStateException(
				"A Zammad declared path starts with groups or knowledge_bases: " + declared);
		};
		String rootId = number(parts[1], declared);
		List<ZammadPathComponent> steps = new ArrayList<ZammadPathComponent>();
		if (parts.length == 4) {
			ZammadPathNodeType type = rootType == ZammadPathNodeType.GROUP ? switch (parts[2]) {
			case "tickets" -> ZammadPathNodeType.TICKET;
			default -> null;
			} : switch (parts[2]) {
			case "categories" -> ZammadPathNodeType.CATEGORY;
			case "answers" -> ZammadPathNodeType.ANSWER;
			default -> null;
			};
			if (type == null) {
				throw new IllegalStateException("A Zammad group path goes on with tickets, a knowledge base path with"
						+ " categories or answers: " + declared);
			}
			steps.add(new ZammadPathComponent(type, number(parts[3], declared)));
		}
		if (!folder) {
			throw new IllegalStateException(
					"The Zammad declared path " + declared + " is a folder, declare it folder: true");
		}
		return ZammadNavigationUtil.toReference(rootType, rootId, null, steps, null);
	}

	private static String number(String value, String declared) {
		try {
			return String.valueOf(Long.parseLong(value.startsWith("#") ? value.substring(1) : value));
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Not a Zammad id in the declared path " + declared, e);
		}
	}
}
