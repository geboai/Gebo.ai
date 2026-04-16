package ai.gebo.llms.chat.client.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.model.base.GBaseObject;
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
	private final IGDeepSearchConfigProvider configProvider;
	private final IGReactiveDynamicDataSourceServicesProvider dynamicDataSourcesProvider;

	@GetMapping(value = "getDeepSearchSystemConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig getDeepSearchSystemConfig() {
		return new DeepSearchConfig(deepSearchDefaultConfig);
	}

	@GetMapping(value = "getDeepSearchDefaultConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig getDeepSearchDefaultConfig() {
		return configRepository.findByDefaultConfig(true);
	}

	@GetMapping(value = "getDeepSearchDefaultOrSystemConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig getDeepSearchDefaultOrSystemConfig() {
		return configProvider.get();
	}

	@GetMapping(value = "getDeepSeachConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchConfig> getDeepSeachConfigs() {

		return configRepository.findAll();

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

	@GetMapping("getConfigurableDataSources")
	public List<GBaseObject> getConfigurableDataSources() {
		List<IGReactiveDeepSearchDataSourceService> services = dynamicDataSourcesProvider
				.getDynamicDeepSearchServices();
		TreeMap<String, GBaseObject> ordered = new TreeMap<>();
		services.stream().forEach(x -> {
			GBaseObject ds = new GBaseObject();
			ds.setCode(x.getHandlerId());
			ds.setDescription(x.getDescription(deepSearchDefaultConfig));
			ordered.put(ds.getDescription(), ds);
		});
		return new ArrayList<>(ordered.values());
	}
}
