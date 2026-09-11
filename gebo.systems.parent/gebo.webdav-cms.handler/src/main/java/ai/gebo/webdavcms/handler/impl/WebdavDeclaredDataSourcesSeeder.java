/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.config.WebdavDataSourcesConfig;
import ai.gebo.webdavcms.handler.repositories.WebdavProjectEndpointRepository;

/**
 * Writes the data sources declared under {@code ai.gebo.webdav.datasources} into
 * the WebDAV endpoint repository at startup.
 *
 * <h2>How a declared path becomes a WebDAV position</h2>
 * <p>
 * The module addresses a DAV resource by its href, and a navigation position is
 * a listable collection href (the root) followed by the steps taken inside it,
 * each carried as {@code DAV-FOLDER:}/{@code DAV-FILE:} over a base64 href - see
 * {@link WebdavNavigationUtil}. A declaration therefore translates to the
 * href's <i>parent</i> collection as the root and the href itself as the single
 * step, which is the same two-node shape the browser produces when an admin
 * picks one folder or file: both hrefs are listable, and the parent gives the
 * ingested content a folder to hang under.
 * </p>
 *
 * <p>
 * An href with no parent left to take - the server origin itself - becomes a
 * root with no step, meaning the whole share.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class WebdavDeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GWebdavProjectEndpoint, WebdavProjectEndpointRepository> {

	/**
	 * Constructs the seeder over the declared data sources and the repository they
	 * are written into.
	 *
	 * @param config             the declared WebDAV data sources.
	 * @param repository         the WebDAV endpoint repository.
	 * @param applicationContext this bean's own context.
	 */
	public WebdavDeclaredDataSourcesSeeder(WebdavDataSourcesConfig config, WebdavProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GWebdavProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GWebdavProjectEndpoint endpoint = new GWebdavProjectEndpoint();
		endpoint.setWebdavSystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GWebdavProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSourcePath declaredPath) {
		String href = declaredPath.getPath().trim();
		if (!href.startsWith("http://") && !href.startsWith("https://")) {
			throw new IllegalStateException(
					"a WebDAV path is the resource full href, starting with http:// or https://");
		}
		while (href.length() > 1 && href.endsWith("/")) {
			href = href.substring(0, href.length() - 1);
		}
		String parent = parentHrefOf(href);
		VFilesystemReference reference = new VFilesystemReference();
		if (parent == null) {
			// Nothing above it on the server: the whole share, as a root with no step.
			if (!declaredPath.isFolder()) {
				throw new IllegalStateException("the root of a WebDAV server is a collection, declare it folder: true");
			}
			reference.root = WebdavNavigationUtil.encodeRoot(href, nameOf(href));
			return reference;
		}
		reference.root = WebdavNavigationUtil.encodeRoot(parent, nameOf(parent));
		reference.path = declaredPath.isFolder() ? WebdavNavigationUtil.encodeAsFolder(href, nameOf(href))
				: WebdavNavigationUtil.encodeAsFile(href, nameOf(href));
		return reference;
	}

	/**
	 * The collection href containing the given one, or {@code null} when the href is
	 * already the server origin.
	 *
	 * @param href a trimmed href with no trailing slash.
	 * @return the parent collection href, or {@code null}.
	 */
	private static String parentHrefOf(String href) {
		int schemeEnd = href.indexOf("://");
		int lastSlash = href.lastIndexOf('/');
		if (lastSlash <= schemeEnd + 2) {
			return null;
		}
		return href.substring(0, lastSlash);
	}

	/**
	 * The last segment of an href, used as the displayed name.
	 *
	 * @param href a trimmed href with no trailing slash.
	 * @return the last segment, or the href itself when it has none.
	 */
	private static String nameOf(String href) {
		int lastSlash = href.lastIndexOf('/');
		if (lastSlash < 0 || lastSlash == href.length() - 1) {
			return href;
		}
		return href.substring(lastSlash + 1);
	}
}
