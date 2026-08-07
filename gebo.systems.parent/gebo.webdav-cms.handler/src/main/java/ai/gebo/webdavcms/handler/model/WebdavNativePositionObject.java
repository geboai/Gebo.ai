package ai.gebo.webdavcms.handler.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

public class WebdavNativePositionObject extends AbstractNativePositionObject {

	private String code = null;
	private String name = null;
	private String url = null;
	private boolean folder = false;
	private boolean resource = false;
	private String resourceContentType = null;
	private Date resourceModificationTime = null;
	private Long resourceFileSize = null;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();

	public static final String WEBDAB_RESOURCE_METAINFO = "WEBDAB_RESOURCE_METAINFO";
	public static final String WEBDAB_RESOURCE_METAINFO_FILE = "FILE";
	public static final String WEBDAB_RESOURCE_METAINFO_FOLDER = "FOLDER";
	public static final String WEBDAB_HREF_METAINFO = "WEBDAB_HREF_METAINFO";
	public static final String WEBDAB_CONTENT_TYPE_METAINFO = "WEBDAB_CONTENT_TYPE_METAINFO";
	public static final String WEBDAB_AUTH_TYPE_METAINFO = "WEBDAB_AUTH_TYPE_METAINFO";

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean isResource() {
		return resource;
	}

	public void setResource(boolean resource) {
		this.resource = resource;
	}

	@Override
	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return resourceReferenceMetaInfos;
	}

	public void setResourceReferenceMetaInfos(HashMap<String, Object> resourceReferenceMetaInfos) {
		this.resourceReferenceMetaInfos = resourceReferenceMetaInfos;
	}

	@Override
	public String getResourceContentType() {
		return resourceContentType;
	}

	public void setResourceContentType(String resourceContentType) {
		this.resourceContentType = resourceContentType;
	}

	@Override
	public Date getResourceModificationTime() {
		return resourceModificationTime;
	}

	public void setResourceModificationTime(Date resourceModificationTime) {
		this.resourceModificationTime = resourceModificationTime;
	}

	@Override
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	public void setResourceFileSize(Long resourceFileSize) {
		this.resourceFileSize = resourceFileSize;
	}

	public boolean isWebdavFolder() {
		return folder;
	}

	public boolean isWebdavFile() {
		return resource;
	}

	public static WebdavNativePositionObject createFolder(String href, String name) {
		WebdavNativePositionObject obj = new WebdavNativePositionObject();
		obj.code = href;
		obj.name = name;
		obj.url = href;
		obj.folder = true;
		obj.resource = false;
		obj.resourceReferenceMetaInfos.put(WEBDAB_RESOURCE_METAINFO, WEBDAB_RESOURCE_METAINFO_FOLDER);
		obj.resourceReferenceMetaInfos.put(WEBDAB_HREF_METAINFO, href);
		return obj;
	}

	public static WebdavNativePositionObject createFile(String href, String name, String contentType, Long fileSize,
			Date modificationTime) {
		WebdavNativePositionObject obj = new WebdavNativePositionObject();
		obj.code = href;
		obj.name = name;
		obj.url = href;
		obj.folder = false;
		obj.resource = true;
		obj.resourceContentType = contentType;
		obj.resourceFileSize = fileSize;
		obj.resourceModificationTime = modificationTime;
		obj.resourceReferenceMetaInfos.put(WEBDAB_RESOURCE_METAINFO, WEBDAB_RESOURCE_METAINFO_FILE);
		obj.resourceReferenceMetaInfos.put(WEBDAB_HREF_METAINFO, href);
		obj.resourceReferenceMetaInfos.put(WEBDAB_CONTENT_TYPE_METAINFO, contentType);
		return obj;
	}
}