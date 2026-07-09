/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;

public class AwsS3NativePositionObject extends AbstractNativePositionObject {

	public static final String S3_BUCKET_NAME = "S3_BUCKET_NAME";
	public static final String S3_OBJECT_KEY = "S3_OBJECT_KEY";
	public static final String S3_OBJECT_ETAG = "S3_OBJECT_ETAG";

	private Bucket bucket;
	private S3Object s3Object;
	private boolean resource = false;
	private boolean folder = false;
	private String code = null;
	private String name = null;
	private String url = null;
	private String resourceContentType = null;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<>();
	private Date modificationTime = null;
	private Long resourceFileSize = null;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public boolean isResource() {
		return resource;
	}

	@Override
	public boolean isFolder() {
		return folder;
	}

	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return resourceReferenceMetaInfos;
	}

	@Override
	public String getResourceContentType() {
		return resourceContentType;
	}

	@Override
	public Date getResourceModificationTime() {
		return modificationTime;
	}

	@Override
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	public boolean isBucket() {
		return bucket != null;
	}

	public Bucket getBucket() {
		return bucket;
	}

	public S3Object getS3Object() {
		return s3Object;
	}

	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
		this.code = bucket.name();
		this.name = bucket.name();
		this.resourceReferenceMetaInfos.put(S3_BUCKET_NAME, bucket.name());
		this.folder = true;
	}

	public void setS3Object(S3Object s3Object) {
		this.s3Object = s3Object;
		String key = s3Object.key();
		this.code = key;
		if (key.endsWith("/")) {
			String folderName = key;
			if (folderName.endsWith("/")) {
				folderName = folderName.substring(0, folderName.length() - 1);
			}
			if (folderName.contains("/")) {
				folderName = folderName.substring(folderName.lastIndexOf('/') + 1);
			}
			this.name = folderName;
			this.resourceReferenceMetaInfos.put(S3_OBJECT_KEY, key);
			this.folder = true;
		} else {
			String fileName = key;
			if (fileName.contains("/")) {
				fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			}
			this.name = fileName;
			this.url = key;
			this.resourceContentType = null;
			this.resourceFileSize = s3Object.size();
			this.modificationTime = Date.from(s3Object.lastModified());
			this.resourceReferenceMetaInfos.put(S3_OBJECT_KEY, key);
			this.resourceReferenceMetaInfos.put(S3_OBJECT_ETAG, s3Object.eTag());
			this.resource = true;
		}
	}
}