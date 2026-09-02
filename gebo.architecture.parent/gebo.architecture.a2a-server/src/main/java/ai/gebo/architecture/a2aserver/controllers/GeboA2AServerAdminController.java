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

	@GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<A2AServerConfig> findAll() {
		return managementService.findAll();
	}

	@GetMapping(value = "findByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> findByCode(@RequestParam("code") String code) {
		return managementService.findByCode(code);
	}

	@PostMapping(value = "insertA2AServer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> insert(@RequestBody @Valid @NotNull A2AServerConfig config) {
		return managementService.insert(config);
	}

	@PostMapping(value = "updateA2AServer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<A2AServerConfig> update(@RequestBody @Valid @NotNull A2AServerConfig config) {
		return managementService.update(config);
	}

	@DeleteMapping(value = "deleteA2AServer", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> delete(@RequestParam("code") String code) {
		return managementService.delete(code);
	}
}
