/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.awss3.content.handler.GAwsS3System;
import ai.gebo.awss3.content.handler.IGAwsS3VirtualFilesystemConsumingService;
import ai.gebo.awss3.content.handler.impl.model.AwsS3NativePositionObject;
import ai.gebo.awss3.content.handler.impl.model.AwsS3NavigationCoordinates;
import ai.gebo.awss3.content.handler.impl.model.AwsS3ResourceReference;
import ai.gebo.awss3.content.handler.impl.model.AwsS3PathComponent;
import ai.gebo.awss3.content.handler.impl.model.AwsS3PathNodeType;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CommonPrefix;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

public class AwsS3VirtualFilesystemConsumingService
		extends
		GAbstractRemoteVirtualFilesystemConsumingService<GAwsS3System, GAwsS3ProjectEndpoint, AwsS3NativePositionObject, AwsS3NavigationCoordinates, AwsS3ResourceReference>
		implements IGAwsS3VirtualFilesystemConsumingService {

	public static final String S3_CLIENT = "S3_CLIENT";

	private final AwsS3ConnectionFactory connectionFactory;

	public AwsS3VirtualFilesystemConsumingService(IGDocumentReferenceFactory documentFactory,
			AwsS3ConnectionFactory connectionFactory) {
		super(documentFactory);
		this.connectionFactory = connectionFactory;
	}

	@Override
	public AwsS3ResourceReference getResourceHandle(GAwsS3System system, GAwsS3ProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		AwsS3ResourceReference ref = new AwsS3ResourceReference();
		ref.bucketName = (String) reference.getCustomMetaInfos().get(AwsS3NativePositionObject.S3_BUCKET_NAME);
		ref.objectKey = (String) reference.getCustomMetaInfos().get(AwsS3NativePositionObject.S3_OBJECT_KEY);
		if (ref.objectKey == null && reference instanceof GVirtualFolder) {
			ref.objectKey = reference.getUri();
		}
		return ref;
	}

	@Override
	public InputStream streamResource(GAwsS3System system, GAwsS3ProjectEndpoint endpoint,
			AwsS3ResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		if (!cache.containsKey(S3_CLIENT)) {
			IGContentsAccessErrorConsumer acc = IGContentsAccessErrorConsumer.defaultImplementation();
			cache.putAll(createEnvironment(system, endpoint, acc));
		}
		S3Client s3Client = (S3Client) cache.get(S3_CLIENT);
		ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
				r -> r.bucket(reference.bucketName).key(reference.objectKey));
		return response;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.AWS_S3_MODULE;
	}

	@Override
	protected AwsS3NavigationCoordinates getPositionCoordinate(
			List<AwsS3NativePositionObject> childCoordinates, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		AwsS3NavigationCoordinates coordinates = new AwsS3NavigationCoordinates();
		PathInfo currentPath = null;
		for (AwsS3NativePositionObject item : childCoordinates) {
			AwsS3PathComponent position = new AwsS3PathComponent();
			position.id = item.getCode();
			if (item.isBucket()) {
				coordinates.setRoot(AwsS3NavigationUtil.toRoot(item.getBucket()));
				position.type = AwsS3PathNodeType.BUCKET;
			} else if (item.isFolder()) {
				if (currentPath == null) {
					currentPath = AwsS3NavigationUtil.toPath(item.getCode());
					coordinates.getBrowsingSteps().add(currentPath);
				} else {
					currentPath = AwsS3NavigationUtil.toPath(currentPath, item.getCode());
					coordinates.getBrowsingSteps().add(currentPath);
				}
				position.type = AwsS3PathNodeType.FOLDER;
			} else if (item.isResource()) {
				if (currentPath == null) {
					currentPath = AwsS3NavigationUtil.toResourcePath(item.getCode(), item.getResourceFileSize() != null ? item.getResourceFileSize() : 0);
					coordinates.getBrowsingSteps().add(currentPath);
				} else {
					currentPath = AwsS3NavigationUtil.toResourcePath(currentPath, item.getCode(), item.getResourceFileSize() != null ? item.getResourceFileSize() : 0);
					coordinates.getBrowsingSteps().add(currentPath);
				}
				position.type = AwsS3PathNodeType.RESOURCE;
			}
			coordinates.getBrowsingStepsCustom().add(position);
		}
		return coordinates;
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<AwsS3NativePositionObject> nativeCoordinates,
			AwsS3NavigationCoordinates position, GAwsS3System system, GAwsS3ProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		AwsS3NativePositionObject lastNode = nativeCoordinates.get(nativeCoordinates.size() - 1);
		S3Client s3Client = (S3Client) environment.get(S3_CLIENT);
		String bucketName = lastNode.isBucket() ? lastNode.getBucket().name()
				: (String) lastNode.getResourceReferenceMetaInfos().get(AwsS3NativePositionObject.S3_BUCKET_NAME);
		String prefix = lastNode.isBucket() ? "" : lastNode.getCode();
		if (!prefix.isEmpty() && !prefix.endsWith("/")) {
			prefix = prefix + "/";
		}

		List<NativeCoordinatePointer> pointers = new ArrayList<>();
		List<String> folderPrefixes = new ArrayList<>();
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
					String cp = commonPrefix.prefix();
					if (!folderPrefixes.contains(cp)) {
						folderPrefixes.add(cp);
						NativeCoordinatePointer pointer = new NativeCoordinatePointer();
						pointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
						pointer.child = new AwsS3NativePositionObject();
						S3Object folderObj = S3Object.builder().key(cp).build();
						pointer.child.setS3Object(folderObj);
						pointers.add(pointer);
					}
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
					NativeCoordinatePointer pointer = new NativeCoordinatePointer();
					pointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
					pointer.child = new AwsS3NativePositionObject();
					pointer.child.setS3Object(s3Object);
					pointers.add(pointer);
				}
			}

			continuationToken = response.isTruncated() ? response.nextContinuationToken() : null;
		} while (continuationToken != null);

		return pointers;
	}

	@Override
	protected List<AwsS3NativePositionObject> toNativeCoordinates(AwsS3NavigationCoordinates position,
			GAwsS3System system, GAwsS3ProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		List<AwsS3NativePositionObject> path = new ArrayList<>();

		if (position.getRoot() != null) {
			String bucketName = position.getRoot().getCode();
			AwsS3NativePositionObject bucketNode = new AwsS3NativePositionObject();
			bucketNode.setBucket(software.amazon.awssdk.services.s3.model.Bucket.builder()
					.name(bucketName).build());
			path.add(bucketNode);
		}

		if (position.getBrowsingStepsCustom() != null) {
			for (AwsS3PathComponent step : position.getBrowsingStepsCustom()) {
				if (step.type == AwsS3PathNodeType.FOLDER) {
					AwsS3NativePositionObject obj = new AwsS3NativePositionObject();
					S3Object s3Obj = S3Object.builder().key(step.id).build();
					obj.setS3Object(s3Obj);
					path.add(obj);
				}
			}
		}
		return path;
	}

	@Override
	protected Map<String, Object> createEnvironment(GAwsS3System system, GAwsS3ProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		Map<String, Object> map = new HashMap<>();
		try {
			S3Client s3Client = connectionFactory.createS3Client(system, system.getS3SecretCode());
			map.put(S3_CLIENT, s3Client);
		} catch (VirtualFilesystemBrowsingException e) {
			throw new GeboContentHandlerSystemException("Cannot access AWS S3 service", e);
		}
		return map;
	}

	@Override
	protected Map<String, Object> createEnvironment(GAwsS3System system) throws GeboContentHandlerSystemException {
		Map<String, Object> map = new HashMap<>();
		try {
			S3Client s3Client = connectionFactory.createS3Client(system, system.getS3SecretCode());
			map.put(S3_CLIENT, s3Client);
		} catch (VirtualFilesystemBrowsingException e) {
			throw new GeboContentHandlerSystemException("Cannot access AWS S3 service", e);
		}
		return map;
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GAwsS3System system,
			GAwsS3ProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		environment.clear();
	}

	@Override
	protected AwsS3NavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		try {
			return AwsS3NavigationUtil.toCoordinates(path);
		} catch (VirtualFilesystemBrowsingException e) {
			throw new GeboContentHandlerSystemException("Cannot convert navigation position", e);
		}
	}

	@Override
	protected List<AwsS3NativePositionObject> toResourcesNativeCoordinates(AwsS3NavigationCoordinates position,
			GAwsS3System system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return null;
	}

	@Override
	protected String describeObject(List<AwsS3NativePositionObject> references, GAwsS3System system,
			GAwsS3ProjectEndpoint endpoint, Map<String, Object> environment) {
		AwsS3NativePositionObject last = references.get(references.size() - 1);
		return last.getName();
	}

	@Override
	protected String describeSystem(GAwsS3System system) {
		return "AWS S3 " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GAwsS3System system, GAwsS3ProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "AWS S3 data source " + endpoint.getDescription();
	}

	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GAwsS3System system,
			GAwsS3ProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, AwsS3ResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return doc;
	}

	@Override
	protected AwsS3ResourceReference getResourceHandle(SearchableSystemMetaData system,
			AwsS3NavigationCoordinates navigationPosition, List<AwsS3NativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		AwsS3NativePositionObject last = nativeCoordinates.isEmpty() ? null
				: nativeCoordinates.get(nativeCoordinates.size() - 1);
		if (last == null) {
			return new AwsS3ResourceReference();
		}
		AwsS3ResourceReference ref = new AwsS3ResourceReference();
		ref.bucketName = (String) last.getResourceReferenceMetaInfos().get(AwsS3NativePositionObject.S3_BUCKET_NAME);
		ref.objectKey = (String) last.getResourceReferenceMetaInfos().get(AwsS3NativePositionObject.S3_OBJECT_KEY);
		return ref;
	}

	@Override
	protected InputStream streamResource(GAwsS3System system, AwsS3ResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException, IOException {
		if (!cache.containsKey(S3_CLIENT)) {
			cache.putAll(createEnvironment(system));
		}
		S3Client s3Client = (S3Client) cache.get(S3_CLIENT);
		return s3Client.getObject(r -> r.bucket(reference.bucketName).key(reference.objectKey));
	}
}