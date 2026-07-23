/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import java.util.StringTokenizer;

import ai.gebo.awss3.content.handler.impl.model.AwsS3NavigationCoordinates;
import ai.gebo.awss3.content.handler.impl.model.AwsS3PathComponent;
import ai.gebo.awss3.content.handler.impl.model.AwsS3PathNodeType;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import software.amazon.awssdk.services.s3.model.Bucket;

class AwsS3NavigationUtil {

	static final String S3_BUCKET_PREFIX = "S3_BUCKET:";
	static final String S3_FOLDER_PREFIX = "S3_FOLDER:";
	static final String S3_RESOURCE_PREFIX = "S3_RESOURCE:";
	static final String PATH_SEPARATOR = "|";

	static GVirtualFilesystemRoot toRoot(Bucket bucket) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(bucket.name());
		root.setAbsolutePath(bucket.name());
		root.setDescription(bucket.name());
		return root;
	}

	static PathInfo toPath(String prefix) {
		PathInfo info = new PathInfo();
		info.folder = true;
		info.name = prefix;
		info.absolutePath = S3_FOLDER_PREFIX + prefix;
		return info;
	}

	static PathInfo toPath(PathInfo parent, String prefix) {
		PathInfo info = toPath(prefix);
		if (parent != null) {
			info.absolutePath = parent.absolutePath + PATH_SEPARATOR + info.absolutePath;
		}
		return info;
	}

	static PathInfo toResourcePath(String objectKey, long size) {
		PathInfo info = new PathInfo();
		info.folder = false;
		String name = objectKey;
		if (name.contains("/")) {
			name = name.substring(name.lastIndexOf('/') + 1);
		}
		info.name = name;
		info.absolutePath = S3_RESOURCE_PREFIX + objectKey;
		return info;
	}

	static PathInfo toResourcePath(PathInfo parent, String objectKey, long size) {
		PathInfo info = toResourcePath(objectKey, size);
		if (parent != null) {
			info.absolutePath = parent.absolutePath + PATH_SEPARATOR + info.absolutePath;
		}
		return info;
	}

	static String getBucketName(PathInfo path) throws VirtualFilesystemBrowsingException {
		if (path == null || path.absolutePath == null) {
			throw new VirtualFilesystemBrowsingException("No absolute path available");
		}
		StringTokenizer tokenizer = new StringTokenizer(path.absolutePath, PATH_SEPARATOR);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.startsWith(S3_BUCKET_PREFIX)) {
				return token.substring(S3_BUCKET_PREFIX.length());
			}
		}
		return path.absolutePath;
	}

	static String getFolderPrefix(PathInfo path) {
		if (path == null || path.absolutePath == null) {
			return "";
		}
		StringTokenizer tokenizer = new StringTokenizer(path.absolutePath, PATH_SEPARATOR);
		String folderId = null;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.startsWith(S3_FOLDER_PREFIX)) {
				folderId = token.substring(S3_FOLDER_PREFIX.length());
			} else {
				folderId = null;
			}
		}
		return folderId;
	}

	static AwsS3NavigationCoordinates toCoordinates(VFilesystemReference path)
			throws VirtualFilesystemBrowsingException {
		AwsS3NavigationCoordinates coordinates = new AwsS3NavigationCoordinates();
		if (path.root != null) {
			AwsS3PathComponent position = new AwsS3PathComponent();
			position.type = AwsS3PathNodeType.BUCKET;
			position.id = path.root.getCode();
			coordinates.setRoot(path.root);
			coordinates.getBrowsingStepsCustom().add(position);
		}
		if (path.path != null && path.path.absolutePath != null) {
			StringTokenizer tokenizer = new StringTokenizer(path.path.absolutePath, PATH_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				PathInfo pathInfo = new PathInfo();
				pathInfo.absolutePath = token;
				AwsS3PathComponent position = new AwsS3PathComponent();
				if (token.startsWith(S3_FOLDER_PREFIX)) {
					position.id = token.substring(S3_FOLDER_PREFIX.length());
					position.type = AwsS3PathNodeType.FOLDER;
					pathInfo.folder = true;
				} else if (token.startsWith(S3_RESOURCE_PREFIX)) {
					position.id = token.substring(S3_RESOURCE_PREFIX.length());
					position.type = AwsS3PathNodeType.RESOURCE;
					pathInfo.folder = false;
				}
				coordinates.getBrowsingStepsCustom().add(position);
				coordinates.getBrowsingSteps().add(pathInfo);
			}
		}
		return coordinates;
	}
}