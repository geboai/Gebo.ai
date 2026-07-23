/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.awss3.content.handler.AwsS3SystemContext;
import ai.gebo.awss3.content.handler.GAwsS3System;
import ai.gebo.awss3.content.handler.IGAwsS3VirtualFilesystemBrowser;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CommonPrefix;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class AwsS3BrowsingService implements IGAwsS3VirtualFilesystemBrowser {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3BrowsingService.class);

	@Autowired
	private AwsS3ConnectionFactory connectionFactory;

	@Autowired
	private IGPersistentObjectManager persistenceManager;

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(AwsS3SystemContext context)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> roots = new ArrayList<>();
		OperationStatus<List<GVirtualFilesystemRoot>> status;
		try {
			GAwsS3System system = persistenceManager.findById(GAwsS3System.class, context.getSystemCode());
			S3Client s3Client = connectionFactory.createS3Client(system, system.getS3SecretCode());
			ListBucketsResponse response = s3Client.listBuckets();
			for (Bucket bucket : response.buckets()) {
				roots.add(AwsS3NavigationUtil.toRoot(bucket));
			}
			status = OperationStatus.of(roots);
		} catch (VirtualFilesystemBrowsingException | GeboPersistenceException e) {
			LOGGER.error("Error accessing AWS S3", e);
			status = OperationStatus.of(roots);
			status.getMessages().add(GUserMessage.errorMessage("Error accessing AWS S3", e));
		}
		return status;
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, AwsS3SystemContext context)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> out = new ArrayList<>();
		OperationStatus<List<PathInfo>> status;
		try {
			GAwsS3System system = persistenceManager.findById(GAwsS3System.class, context.getSystemCode());
			S3Client s3Client = connectionFactory.createS3Client(system, system.getS3SecretCode());

			String bucketName;
			String prefix;

			if (param.root != null && param.path == null) {
				bucketName = param.root.getCode();
				prefix = "";
			} else if (param.path != null) {
				bucketName = param.root != null ? param.root.getCode()
						: AwsS3NavigationUtil.getBucketName(param.path);
				String folderPrefix = AwsS3NavigationUtil.getFolderPrefix(param.path);
				prefix = folderPrefix != null ? folderPrefix : "";
				if (!prefix.isEmpty() && !prefix.endsWith("/")) {
					prefix = prefix + "/";
				}
			} else {
				status = OperationStatus.of(out);
				status.getMessages().add(GUserMessage.errorMessage("Missing root or path parameter", ""));
				return status;
			}

			String continuationToken = null;
			do {
				ListObjectsV2Request.Builder requestBuilder = ListObjectsV2Request.builder()
						.bucket(bucketName)
						.delimiter("/");
				if (!prefix.isEmpty()) {
					requestBuilder.prefix(prefix);
				}
				if (continuationToken != null) {
					requestBuilder.continuationToken(continuationToken);
				}

				ListObjectsV2Response response = s3Client.listObjectsV2(requestBuilder.build());

				if (response.commonPrefixes() != null) {
					for (CommonPrefix commonPrefix : response.commonPrefixes()) {
						out.add(AwsS3NavigationUtil.toPath(param.path, commonPrefix.prefix()));
					}
				}

				if (response.contents() != null) {
					for (S3Object s3Object : response.contents()) {
						if (s3Object.key().equals(prefix)) {
							continue;
						}
						if (s3Object.key().endsWith("/")) {
							continue;
						}
						out.add(AwsS3NavigationUtil.toResourcePath(param.path, s3Object.key(), s3Object.size()));
					}
				}

				continuationToken = response.isTruncated() ? response.nextContinuationToken() : null;
			} while (continuationToken != null);

			status = OperationStatus.of(out);
		} catch (VirtualFilesystemBrowsingException | GeboPersistenceException e) {
			LOGGER.error("Error browsing AWS S3", e);
			status = OperationStatus.of(out);
			status.getMessages().add(GUserMessage.errorMessage("Error browsing AWS S3", e));
		}
		return status;
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return false;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, AwsS3SystemContext context)
			throws VirtualFilesystemBrowsingException {
		return null;
	}
}