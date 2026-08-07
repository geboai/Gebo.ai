package ai.gebo.webdavcms.handler;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

public class GWebdavResourceReference implements IGRemoteVirtualFilesystemResourceReference {

	public String href = null;
	public String contentType = null;
	public String resourceType = null;

	@Override
	public String toString() {
		return "{resourceType=" + resourceType + ", href=" + href + ", contentType=" + contentType + "}";
	}

}
