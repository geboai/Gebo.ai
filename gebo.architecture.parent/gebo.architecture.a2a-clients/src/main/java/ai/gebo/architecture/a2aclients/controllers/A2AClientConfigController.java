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

	@PostMapping(value = "testAndDiscovery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> testAndDiscovery(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		return managementService.testAndDiscovery(config);
	}

	@PostMapping(value = "insertA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> insert(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		return managementService.insert(config);
	}

	@PostMapping(value = "updateA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> update(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		return managementService.update(config);
	}

	@DeleteMapping(value = "deleteA2AAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> delete(@RequestBody @Valid @NotNull A2ARemoteAgentConfig config) {
		return managementService.delete(config);
	}

	@GetMapping(value = "findByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2ARemoteAgentConfig> findByCode(@RequestParam("code") String code) {
		return managementService.findByCode(code);
	}

	@GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<A2ARemoteAgentConfig> list(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		return managementService.list(PageRequest.of(page, size));
	}
}
