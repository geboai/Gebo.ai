package ai.gebo.webdavcms.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

public class GWebdavContentManagementSystem extends GContentManagementSystem {

	private WebdavVersion webdavAuthType = null;
	private String secretCode = null;

	public GWebdavContentManagementSystem() {
	}

	public WebdavVersion getWebdavAuthType() {
		return webdavAuthType;
	}

	public void setWebdavAuthType(WebdavVersion webdavAuthType) {
		this.webdavAuthType = webdavAuthType;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

}
