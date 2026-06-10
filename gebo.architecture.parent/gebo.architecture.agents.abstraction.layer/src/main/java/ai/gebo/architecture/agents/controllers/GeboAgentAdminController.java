package ai.gebo.architecture.agents.controllers;

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

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAgentAdminController")
@AllArgsConstructor
public class GeboAgentAdminController {
	private final IAgentConfigDao agentsConfigDao;
	private final IGPromptConfigDao promptsDao;
	private final IGAgentServiceRuntimeDao agentsRepositoryPattern;

	@GetMapping(value = "getPromptTemplateByAgentId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GPromptTemplateConfig> getPromptTemplatesByAgentId(@RequestParam("agentId") String agentId) {
		return promptsDao.findListByPredicate(x -> x.getAgentPrompt() != null && x.getAgentPrompt()
				&& x.getAgentId() != null && x.getAgentId().equals(agentId));
	}

	@GetMapping(value = "getAgentsChoices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgentsChoices() {
		return agentsRepositoryPattern.getConfigurations().stream().map(x -> {
			GBaseObject object = new GBaseObject();
			object.setCode(x.getId());
			object.setDescription(x.getDescription());
			return object;
		}).toList();
	}

	@GetMapping(value = "getAgentByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig getAgentByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return this.agentsConfigDao.findByCode(code);
	}

	@PostMapping(value = "updateAgent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig updateAgent(@Valid @NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		return this.agentsConfigDao.update(config);
	}

	@PostMapping(value = "insertAgent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAgentConfig insertAgent(@Valid @NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		return this.agentsConfigDao.insert(config);
	}

	@DeleteMapping(value = "deleteAgent", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAgent(@Valid @NotNull @RequestBody GAgentConfig config) throws GeboPersistenceException {
		this.agentsConfigDao.delete(config);
	}

	@GetMapping(value = "getAgents", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgents() throws GeboPersistenceException {
		List<GAgentConfig> list = agentsConfigDao.getConfigurations();
		return list.stream().map(x -> new GBaseObject(x)).toList();
	}

}
