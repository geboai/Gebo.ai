/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.awss3.content.handler.config.AwsS3DataSourcesConfig;
import ai.gebo.awss3.content.handler.repositories.AwsS3ProjectEndpointRepository;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractDeclaredDataSourcesSeeder;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * Writes the data sources declared under {@code ai.gebo.awss3.datasources} into
 * the AWS S3 endpoint repository at startup.
 *
 * <h2>How a declared path becomes an S3 position</h2>
 * <p>
 * The module navigation root is the bucket - {@code AwsS3NavigationUtil} reads
 * it straight off {@code root.code} - and a step under it is the object key,
 * carried as {@code S3_FOLDER:} for a prefix or {@code S3_RESOURCE:} for an
 * object. A declaration is written the way an S3 location is normally written,
 * {@code bucket/key}, and split here on the first slash.
 * </p>
 *
 * <p>
 * A prefix is given the trailing slash S3 uses to mean everything under it if
 * the declaration left it out, since without it the listing would also match
 * sibling keys that merely start with the same characters.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class AwsS3DeclaredDataSourcesSeeder
		extends GAbstractDeclaredDataSourcesSeeder<GAwsS3ProjectEndpoint, AwsS3ProjectEndpointRepository> {

	/**
	 * Constructs the seeder over the declared data sources and the repository they
	 * are written into.
	 *
	 * @param config             the declared AWS S3 data sources.
	 * @param repository         the AWS S3 endpoint repository.
	 * @param applicationContext this bean's own context.
	 */
	public AwsS3DeclaredDataSourcesSeeder(AwsS3DataSourcesConfig config, AwsS3ProjectEndpointRepository repository,
			ApplicationContext applicationContext) {
		super(config.getDatasources(), repository, applicationContext);
	}

	@Override
	protected GAwsS3ProjectEndpoint newEndpoint(GDeclaredDataSource declaration) {
		GAwsS3ProjectEndpoint endpoint = new GAwsS3ProjectEndpoint();
		endpoint.setS3SystemCode(declaration.getSystemCode());
		return endpoint;
	}

	@Override
	protected void setPaths(GAwsS3ProjectEndpoint endpoint, List<VFilesystemReference> references) {
		endpoint.setPaths(references);
	}

	@Override
	protected VFilesystemReference toReference(GDeclaredDataSource declaration,
			GDeclaredDataSourcePath declaredPath) {
		String declared = declaredPath.getPath().trim();
		while (declared.startsWith("/")) {
			declared = declared.substring(1);
		}
		int firstSlash = declared.indexOf('/');
		String bucket = firstSlash < 0 ? declared : declared.substring(0, firstSlash);
		String key = firstSlash < 0 ? "" : declared.substring(firstSlash + 1);
		if (bucket.length() == 0) {
			throw new IllegalStateException("an AWS S3 path starts with the bucket name, as in bucket/prefix/");
		}

		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(bucket);
		root.setAbsolutePath(bucket);
		root.setDescription(bucket);

		VFilesystemReference reference = new VFilesystemReference();
		reference.root = root;
		if (key.length() == 0) {
			// The bucket itself: a root with no step.
			if (!declaredPath.isFolder()) {
				throw new IllegalStateException("a bucket is a folder, declare it folder: true or name an object key");
			}
			return reference;
		}

		PathInfo path = new PathInfo();
		if (declaredPath.isFolder()) {
			String prefix = key.endsWith("/") ? key : key + "/";
			path.absolutePath = AwsS3NavigationUtil.S3_FOLDER_PREFIX + prefix;
			path.folder = true;
			path.name = nameOfPrefix(prefix);
		} else {
			if (key.endsWith("/")) {
				throw new IllegalStateException(
						"an object key ending with a slash is a prefix, declare it folder: true or drop the trailing slash");
			}
			path.absolutePath = AwsS3NavigationUtil.S3_RESOURCE_PREFIX + key;
			path.folder = false;
			path.name = key.contains("/") ? key.substring(key.lastIndexOf('/') + 1) : key;
		}
		reference.path = path;
		return reference;
	}

	/**
	 * The last segment of a prefix, used as the displayed name.
	 *
	 * @param prefix an object key prefix ending with a slash.
	 * @return the last segment.
	 */
	private static String nameOfPrefix(String prefix) {
		String trimmed = prefix.substring(0, prefix.length() - 1);
		return trimmed.contains("/") ? trimmed.substring(trimmed.lastIndexOf('/') + 1) : trimmed;
	}
}
