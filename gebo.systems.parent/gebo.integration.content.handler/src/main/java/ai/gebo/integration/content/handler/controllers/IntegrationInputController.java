package ai.gebo.integration.content.handler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.integration.content.handler.impl.IntegrationService;
import ai.gebo.integration.content.handler.model.IntegrationDocumentEnvelop;
import ai.gebo.integration.content.handler.model.JobTicket;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;

@RestController
@PreAuthorize("hasRole('APPLICATION')")
@RequestMapping(value = "api/application/IntegrationInputController")
public class IntegrationInputController {
	@Autowired
	IntegrationService integrationService;

	public IntegrationInputController() {

	}

	@PostMapping(value = "spoolDocument/{endpointCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JobTicket spoolDocument(@NotNull @Valid @PathParam("endpointCode") String endpointCode,
			@NotNull @Valid @RequestParam("relativePath") String relativePath,
			@NotNull @Valid @RequestBody IntegrationDocumentEnvelop envelop) throws GeboContentHandlerSystemException {

		return this.integrationService.spoolDocument(endpointCode, relativePath, envelop);
	}

	@PutMapping(value = "spoolDocument/{endpointCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public JobTicket spoolDocument(@NotNull @Valid @PathParam("endpointCode") String endpointCode,
			@NotNull @Valid @RequestParam("relativePath") String relativePath,
			@NotNull @Valid @RequestParam("file") MultipartFile file) throws GeboContentHandlerSystemException {

		return this.integrationService.spoolDocument(endpointCode, relativePath, file);
	}

	@PutMapping(value = "publishContents/{endpointCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JobTicket publishContents(@NotNull @Valid @PathParam("endpointCode") String endpointCode,
			@NotNull @Valid @RequestBody List<JobTicket> ingestTickets) {
		return this.integrationService.publishContents(endpointCode, ingestTickets);
	}

	@GetMapping(value = "publishSync/{endpointCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JobTicket publishSync(@NotNull @Valid @PathParam("endpointCode") String endpointCode) {
		return this.integrationService.publishSync(endpointCode);
	}

}
