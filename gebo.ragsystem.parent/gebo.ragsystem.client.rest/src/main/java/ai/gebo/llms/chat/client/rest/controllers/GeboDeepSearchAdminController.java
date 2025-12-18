package ai.gebo.llms.chat.client.rest.controllers;

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

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboDeepSearchAdminController")
@AllArgsConstructor
public class GeboDeepSearchAdminController {
	private final DeepSearchDefaultConfig deepSearchDefaultConfig;
	private final IGPersistentObjectManager persistentObjectManager;
	private final DeepSearchConfigRepository configRepository;

	@GetMapping(value = "getDeepSearchSystemConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig getDeepSearchSystemConfig() {
		return deepSearchDefaultConfig;
	}

	@GetMapping(value = "getDeepSearchDefaultConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig getDeepSearchDefaultConfig() {
		return configRepository.findByDefaultConfig(true);
	}

	@GetMapping(value = "findDeepSearchDefaultConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig findDeepSearchDefaultConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(DeepSearchConfig.class, code);
	}

	@GetMapping(value = "getDeepSeachConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchConfig> getDeepSeachConfigs(
			@RequestParam(value = "chatProfileCode", required = false) String chatProfileCode) {
		if (chatProfileCode == null) {
			return configRepository.findAll();
		} else {
			return configRepository.findByChatProfileCode(chatProfileCode);
		}
	}

	@PostMapping(value = "insertDeepSearchConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig insertDeepSearchConfig(@RequestBody @Valid @NotNull DeepSearchConfig deepSearchConfig)
			throws GeboPersistenceException {
		return persistentObjectManager.insert(deepSearchConfig);
	}

	@PostMapping(value = "updateDeepSearchConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig updateDeepSearchConfig(@RequestBody @Valid @NotNull DeepSearchConfig deepSearchConfig)
			throws GeboPersistenceException {
		return persistentObjectManager.update(deepSearchConfig);
	}

	@DeleteMapping(value = "deleteDeepSearchConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteDeepSearchConfig(@RequestBody @Valid @NotNull DeepSearchConfig deepSearchConfig)
			throws GeboPersistenceException {
		persistentObjectManager.delete(deepSearchConfig);
	}

}
