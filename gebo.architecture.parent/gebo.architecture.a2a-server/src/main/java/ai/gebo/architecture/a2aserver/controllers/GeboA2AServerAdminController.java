package ai.gebo.architecture.a2aserver.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.service.A2AServerConfigManagerService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Admin REST controller for published A2A servers: full CRUD, delegated to the
 * {@link A2AServerConfigManagerService} which keeps the live endpoints in sync.
 * All operations require the {@code ADMIN} role, mirroring
 * {@code GeboMCPServerAdminController}.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeboA2AServerAdminController")
@AllArgsConstructor
public class GeboA2AServerAdminController {

	private final A2AServerConfigManagerService managementService;
	/**
	 * The insert/update/delete of an A2A export are security events: each one
	 * publishes, changes or removes an endpoint external clients can call to run a
	 * Gebo agent or network of agents.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<A2AServerConfig> findAllA2AServer() {
		return managementService.findAll();
	}

	@GetMapping(value = "findByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> findByCodeA2AServer(@RequestParam("code") String code) {
		return managementService.findByCode(code);
	}

	@PostMapping(value = "insertA2AServer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> insertA2AServer(@RequestBody @Valid @NotNull A2AServerConfig config) {
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION, SecurityAuditTaxonomy.Action.A2A_EXPORT_INSERT,
				SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, config != null ? config.getCode() : null, config,
				() -> managementService.insert(config));
	}

	@PostMapping(value = "updateA2AServer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> updateA2AServer(@RequestBody @Valid @NotNull A2AServerConfig config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION, SecurityAuditTaxonomy.Action.A2A_EXPORT_UPDATE,
				SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, config != null ? config.getCode() : null, config,
				() -> managementService.update(config));
	}

	@DeleteMapping(value = "deleteA2AServer", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteA2AServer(@RequestParam("code") String code) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION, SecurityAuditTaxonomy.Action.A2A_EXPORT_DELETE,
				SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, code, null, () -> managementService.delete(code));
	}
}
