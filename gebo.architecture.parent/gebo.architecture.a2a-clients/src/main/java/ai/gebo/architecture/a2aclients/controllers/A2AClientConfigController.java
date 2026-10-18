package ai.gebo.architecture.a2aclients.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aclients.service.A2AClientManagementService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Admin REST controller for registering and managing remote A2A agents: CRUD,
 * connectivity test &amp; skill discovery, and a paged list. All operations
 * require the {@code ADMIN} role, mirroring {@code McpClientConfigController}.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/A2AClientConfigController")
@AllArgsConstructor
public class A2AClientConfigController {

	private final A2AClientManagementService managementService;
	/**
	 * The insert/update/delete of an imported A2A agent are security events: the
	 * networks of agents hand work, and the data it carries, to that remote agent.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@PostMapping(value = "testAndDiscovery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> testAndDiscoveryA2AClientConfig(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		return managementService.testAndDiscovery(config);
	}

	@PostMapping(value = "insertA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> insertA2AClientConfig(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_INSERT, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				config != null ? config.getCode() : null, config, () -> managementService.insert(config));
	}

	@PostMapping(value = "updateA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> updateA2AClientConfig(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_UPDATE, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				config != null ? config.getCode() : null, config, () -> managementService.update(config));
	}

	@DeleteMapping(value = "deleteA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteA2AClientConfig(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_REMOTE_AGENT_DELETE, SecurityAuditTaxonomy.ResourceType.A2A_REMOTE_AGENT,
				config != null ? config.getCode() : null, null, () -> managementService.delete(config));
	}

	@GetMapping(value = "findByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> findByCodeA2AClientConfig(@RequestParam("code") String code) {
		return managementService.findByCode(code);
	}

	@GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<A2ARemoteAgentConfig> listA2AClientConfig(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		return managementService.list(PageRequest.of(page, size));
	}
}
