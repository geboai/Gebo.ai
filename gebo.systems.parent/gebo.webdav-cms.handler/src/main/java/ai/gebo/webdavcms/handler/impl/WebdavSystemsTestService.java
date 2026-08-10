package ai.gebo.webdavcms.handler.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.WebdavVersion;

@Service
public class WebdavSystemsTestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebdavSystemsTestService.class);

	@Autowired
	private IGeboSecretsAccessService secretsService;

	@Autowired
	private WebdavConnectionFactory connectionFactory;

	public OperationStatus<GWebdavContentManagementSystem> testWebdavSystem(GWebdavContentManagementSystem object) {
		OperationStatus<GWebdavContentManagementSystem> outValue = new OperationStatus<GWebdavContentManagementSystem>();
		outValue.setResult(object);

		String secretCode = object.getSecretCode();
		WebdavVersion authType = object.getWebdavAuthType();

		if (authType == null || authType == WebdavVersion.NONE) {
			return testConnection(outValue, object);
		}

		if (secretCode != null && !secretCode.trim().isEmpty()) {
			try {
				AbstractGeboSecretContent secretContent = secretsService.getSecretContentById(secretCode);
				if (!validateSecretType(authType, secretContent, outValue)) {
					return outValue;
				}
				return testConnection(outValue, object);
			} catch (GeboCryptSecretException e) {
				outValue.getMessages().add(GUserMessage.errorMessage("Gebo.ai secret credentials access error",
						"Something went wrong in the system credentials storing system"));
			}
		} else {
			outValue.getMessages()
					.add(GUserMessage.errorMessage("No credentials inserted", "The WebDAV credentials are missing"));
		}
		return outValue;
	}

	private boolean validateSecretType(WebdavVersion authType, AbstractGeboSecretContent secretContent,
			OperationStatus<GWebdavContentManagementSystem> outValue) {
		GeboSecretType secretType = secretContent.type();
		switch (authType) {
		case BASIC:
		case DIGEST:
		case NTLM:
			if (secretType != GeboSecretType.USERNAME_PASSWORD) {
				outValue.getMessages().add(GUserMessage.errorMessage("Wrong credential format",
						"This authentication method requires username+password credentials"));
				return false;
			}
			break;
		case BEARER_TOKEN:
			if (secretType != GeboSecretType.TOKEN) {
				outValue.getMessages().add(GUserMessage.errorMessage("Wrong credential format",
						"Bearer token authentication requires token credentials"));
				return false;
			}
			break;
		default:
			break;
		}
		return true;
	}

	private OperationStatus<GWebdavContentManagementSystem> testConnection(
			OperationStatus<GWebdavContentManagementSystem> outValue, GWebdavContentManagementSystem object) {
		try {
			Sardine sardine = connectionFactory.getConnection(object);
			String baseUri = object.getBaseUri();
			if (baseUri != null && !baseUri.isEmpty()) {
				sardine.list(baseUri, 0);
			} else {
				outValue.getMessages()
						.add(GUserMessage.errorMessage("No base URI configured", "The WebDAV base URI is missing"));
			}
		} catch (GeboCryptSecretException e) {
			outValue.getMessages().add(GUserMessage.errorMessage("Credential decrypt error", e.getMessage()));
		} catch (IOException e) {
			LOGGER.warn("WebDAV connection test failed", e);
			outValue.getMessages().add(GUserMessage.errorMessage("Cannot connect to WebDAV server",
					"Check the base URI and credentials. " + e.getMessage()));
		}
		return outValue;
	}
}