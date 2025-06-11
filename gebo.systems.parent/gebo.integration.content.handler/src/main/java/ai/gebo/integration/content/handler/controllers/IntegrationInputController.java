package ai.gebo.integration.content.handler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.integration.content.handler.impl.IntegrationService;
import ai.gebo.integration.content.handler.model.IntegrationDocumentEnvelop;
import ai.gebo.integration.content.handler.model.JobTicket;
import jakarta.websocket.server.PathParam;

@RestController
@PreAuthorize("hasRole('APPLICATION')")
@RequestMapping(value = "api/application/IntegrationInputController")
public class IntegrationInputController {
	@Autowired
	IntegrationService integrationService;

	public IntegrationInputController() {

	}

	@PostMapping(value = "spoolDocument/{endpointCode}")
	public JobTicket spoolDocument(@PathParam("endpointCode") String endpointCode,
			@RequestBody IntegrationDocumentEnvelop envelop) throws GeboContentHandlerSystemException {
		
		return this.integrationService.spoolDocument(endpointCode,envelop);
	}

}
