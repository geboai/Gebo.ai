package ai.gebo.integration.content.handler.impl;

import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.integration.content.handler.GIntegrationContentSystem;
import ai.gebo.integration.content.handler.GIntegrationProjectEndpoint;
import ai.gebo.integration.content.handler.IGIntegrationSystemContentHandler;
import ai.gebo.integration.content.handler.model.IntegrationDocumentEnvelop;
import ai.gebo.integration.content.handler.model.JobTicket;
import ai.gebo.integration.content.handler.repositories.IntegrationProjectEndpointRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

@Service
public class IntegrationService {
	@Autowired
	IGSecurityService securityService;
	@Autowired
	IntegrationProjectEndpointRepository repository;
	@Autowired
	IGLocalPersistentFolderDiscoveryService folderDiscoveryService;
	@Autowired
	IGIntegrationSystemContentHandler contentHandler;

	public IntegrationService() {

	}

	public JobTicket spoolDocument(String endpointCode, IntegrationDocumentEnvelop envelop) throws GeboContentHandlerSystemException {
		Optional<GIntegrationProjectEndpoint> endpointOptional = repository.findById(endpointCode);
		if (endpointOptional.isEmpty()) {
			throw new RuntimeException("Unkown");
		}
		UserInfos user = securityService.getCurrentUser();
		GIntegrationProjectEndpoint endpoint = endpointOptional.get();
		if (!endpoint.getAllowedApplicationUsers().contains(user.getUsername()))
			throw new RuntimeException("Cannot access");
		GIntegrationContentSystem system = contentHandler.getSystem(endpoint);
		String path = folderDiscoveryService.getLocalPersistentFolder(system, endpoint);
		Path pathFinal = Path.of(path,"spool");
		
		return null;
	}

}
