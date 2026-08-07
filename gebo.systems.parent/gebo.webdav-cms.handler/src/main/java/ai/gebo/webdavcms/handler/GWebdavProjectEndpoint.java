package ai.gebo.webdavcms.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

@EntityDescription(description = "WebDAV remote documents source", entityCategory = GProjectEndpoint.class)
@Document
public class GWebdavProjectEndpoint extends GVirtualFilesystemProjectEndpoint {

	private String webdavSystemCode = null;

	public GWebdavProjectEndpoint() {
	}

	public String getWebdavSystemCode() {
		return webdavSystemCode;
	}

	public void setWebdavSystemCode(String webdavSystemCode) {
		this.webdavSystemCode = webdavSystemCode;
	}

}
