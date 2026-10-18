package ai.gebo.llms.chat.client.rest.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
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
	/**
	 * The insert/update/delete of a deep search configuration are security events:
	 * the configuration decides who may search the external sources, all together or
	 * per data source.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

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
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		describePerDataSourceAccess(event, deepSearchConfig);
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_INSERT,
				SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG, deepSearchConfig.getCode(), deepSearchConfig,
				() -> persistentObjectManager.insert(deepSearchConfig));
	}

	@PostMapping(value = "updateDeepSearchConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchConfig updateDeepSearchConfig(@RequestBody @Valid @NotNull DeepSearchConfig deepSearchConfig)
			throws GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		describePerDataSourceAccess(event, deepSearchConfig);
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_UPDATE,
				SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG, deepSearchConfig.getCode(), deepSearchConfig,
				() -> persistentObjectManager.update(deepSearchConfig));
	}

	@DeleteMapping(value = "deleteDeepSearchConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteDeepSearchConfig(@RequestBody @Valid @NotNull DeepSearchConfig deepSearchConfig)
			throws GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		ConfigurationAuditSupport.auditedVoid(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.DEEP_SEARCH_CONFIG_DELETE,
				SecurityAuditTaxonomy.ResourceType.DEEP_SEARCH_CONFIG, deepSearchConfig.getCode(), null,
				() -> persistentObjectManager.delete(deepSearchConfig));
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

	/**
	 * Adds to the event whether access is configured per data source and, for each
	 * data source, whether everyone may search it and through which groups (users only
	 * as a count: their ids are e-mail addresses).
	 */
	private static void describePerDataSourceAccess(SecurityEvent event, DeepSearchConfig config) {
		if (config == null) {
			return;
		}
		event.getDetails().put("perDataSourceConfigured", config.getPerDataSourceConfigured());
		if (config.getDataSourcesAccesses() == null || config.getDataSourcesAccesses().isEmpty()) {
			return;
		}
		Map<String, Object> perDataSource = new LinkedHashMap<>();
		for (DeepSearchDataSourceAccess access : config.getDataSourcesAccesses()) {
			if (access == null || access.getDataSourceId() == null) {
				continue;
			}
			Map<String, Object> described = new LinkedHashMap<>();
			described.put("accessibleToAll", access.getAccessibleToAll());
			described.put("accessibleGroups", access.getAccessibleGroups() != null ? access.getAccessibleGroups() : List.of());
			described.put("accessibleUsersCount", access.getAccessibleUsers() != null ? access.getAccessibleUsers().size() : 0);
			perDataSource.put(access.getDataSourceId(), described);
		}
		event.getDetails().put("dataSourcesAccesses", perDataSource);
	}
}
