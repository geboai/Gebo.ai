package ai.gebo.webdavcms.handler.impl;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.WebdavVersion;

@Service
class WebdavConnectionFactory {

	@Autowired
	private IGeboSecretsAccessService secretService;

	Sardine getConnection(GWebdavContentManagementSystem system) throws GeboCryptSecretException {
		WebdavVersion authType = system.getWebdavAuthType();
		if (authType == null || authType == WebdavVersion.NONE) {
			return SardineFactory.begin();
		}

		String secretCode = system.getSecretCode();
		if (secretCode == null || secretCode.trim().isEmpty()) {
			return SardineFactory.begin();
		}

		AbstractGeboSecretContent secret = secretService.getSecretContentById(secretCode);

		switch (authType) {
		case BASIC:
		case DIGEST:
		case NTLM: {
			GeboUsernamePasswordContent credentials = (GeboUsernamePasswordContent) secret;
			return SardineFactory.begin(credentials.getUsername(), credentials.getPassword());
		}
		case BEARER_TOKEN: {
			GeboTokenContent token = (GeboTokenContent) secret;
			Sardine sardine = SardineFactory.begin();
			sardine.setCredentials(token.getUser(), token.getToken());
			return sardine;
		}
		default:
			return SardineFactory.begin();
		}
	}
}