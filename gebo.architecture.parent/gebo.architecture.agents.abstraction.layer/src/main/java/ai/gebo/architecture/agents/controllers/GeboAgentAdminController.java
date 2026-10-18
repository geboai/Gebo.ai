package ai.gebo.architecture.agents.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsCopyService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.model.OperationStatus;
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
@RequestMapping(path = "api/admin/GeboAgentAdminController")
@AllArgsConstructor
public class GeboAgentAdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboAgentAdminController.class);
	private final IAgentConfigDao agentsConfigDao;
	private final IGPromptConfigDao promptsDao;
	private final IGRuntimeBinder runtimeBinder;
	private final IGAgentsCopyService agentsCopyService;
	/**
	 * The insert/update/delete/copy of an agent are security events: an agent decides
	 * which model, tools and prompt are used inside the networks it takes part in.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@GetMapping(value = "getPromptTemplateByAgentId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GPromptTemplateConfig> getPromptTemplatesByAgentId(@RequestParam("agentId") String agentId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getPromptTemplatesByAgentId agentId:" + agentId);
		}
		List<GPromptTemplateConfig> prompts = promptsDao.findListByPredicate(x -> x.getAgentPrompt() != null
				&& x.getAgentPrompt() && x.getAgentId() != null && x.getAgentId().equals(agentId));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getPromptTemplatesByAgentId agentId:" + agentId + " returned "
					+ (prompts != null ? prompts.size() : 0) + " prompt template(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_PROMPT_TEMPLATES agentId=" + agentId + ">");
			LOGGER.trace(String.valueOf(prompts));
			LOGGER.trace("</AGENT_PROMPT_TEMPLATES>");
		}
		return prompts;
	}

	@GetMapping(value = "getAgentsChoices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgentsChoices() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentsChoices");
		}
		IGAgentServiceRuntimeDao dao=runtimeBinder.getImplementationOf(IGAgentServiceRuntimeDao.class);
		return dao.getConfigurations().stream().map(x -> {
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Agent service choice: " + x.getId() + " - " + x.getDescription());
			}
			GBaseObject object = new GBaseObject();
			object.setCode(x.getId());
			object.setDescription(x.getDescription());
			return object;
		}).toList();
	}

	/**
	 * The roles an agent configuration can take ({@code agentRoleCode} is mandatory):
	 * the role library the prompts are built with.
	 */
	@GetMapping(value = "getAgentRoles", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GAgentRole> getAgentRoles() {
		IAgentRoleDao dao = runtimeBinder.getImplementationOf(IAgentRoleDao.class);
		List<GAgentRole> roles = dao != null && dao.getConfigurations() != null ? dao.getConfigurations() : List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentRoles returned " + roles.size() + " role(s)");
		}
		return roles;
	}

	@GetMapping(value = "getAgentByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig getAgentByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentByCode code:" + code);
		}
		GAgentConfig config = this.agentsConfigDao.findByCode(code);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_CONFIG code=" + code + ">");
			LOGGER.trace(String.valueOf(config));
			LOGGER.trace("</AGENT_CONFIG>");
		}
		return config;
	}

	@PostMapping(value = "updateAgent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig updateAgent(@Valid @NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST updateAgent code:" + (config != null ? config.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_CONFIG_UPDATE>");
			LOGGER.trace(String.valueOf(config));
			LOGGER.trace("</AGENT_CONFIG_UPDATE>");
		}
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_UPDATE,
				SecurityAuditTaxonomy.ResourceType.AGENT, config != null ? config.getCode() : null, config,
				() -> this.agentsConfigDao.update(config));
	}

	@PostMapping(value = "insertAgent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig insertAgent(@Valid @NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST insertAgent code:" + (config != null ? config.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_CONFIG_INSERT>");
			LOGGER.trace(String.valueOf(config));
			LOGGER.trace("</AGENT_CONFIG_INSERT>");
		}
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_INSERT,
				SecurityAuditTaxonomy.ResourceType.AGENT, config != null ? config.getCode() : null, config,
				() -> this.agentsConfigDao.insert(config));
	}

	/**
	 * Copies the agent with the given code into a new, editable agent, also when the
	 * original is a read-only agent declared by a configuration bean (see
	 * {@link IGAgentsCopyService#copyAgent(String)}).
	 */
	@PostMapping(value = "copyAgent", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentConfig> copyAgent(@RequestParam("code") String code) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST copyAgent code:" + code);
		}
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		// the agent copied is the resource; the code of the new, editable copy is a detail
		OperationStatus<GAgentConfig> status = ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_COPY,
				SecurityAuditTaxonomy.ResourceType.AGENT, code, null, () -> agentsCopyService.copyAgent(code),
				(e, copied) -> {
					if (copied != null && copied.getResult() != null) {
						e.getDetails().put("copyCode", copied.getResult().getCode());
					}
				});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST copyAgent code:" + code + " produced a copy:"
					+ (status != null && status.getResult() != null ? status.getResult().getCode() : null)
					+ " message(s):" + (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	/**
	 * Deletes the agent configuration: not validated, an agent being deleted by its
	 * code also when its configuration is no longer valid.
	 */
	@DeleteMapping(value = "deleteAgent", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAgent(@NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST deleteAgent code:" + (config != null ? config.getCode() : null));
		}
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		ConfigurationAuditSupport.auditedVoid(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_DELETE,
				SecurityAuditTaxonomy.ResourceType.AGENT, config != null ? config.getCode() : null, null,
				() -> this.agentsConfigDao.delete(config));
	}

	@GetMapping(value = "getAgents", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgents() throws GeboPersistenceException {
		List<GAgentConfig> list = agentsConfigDao.getConfigurations();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgents returned " + (list != null ? list.size() : 0) + " agent configuration(s)");
		}
		return list.stream().map(x -> new GBaseObject(x)).toList();
	}

}
