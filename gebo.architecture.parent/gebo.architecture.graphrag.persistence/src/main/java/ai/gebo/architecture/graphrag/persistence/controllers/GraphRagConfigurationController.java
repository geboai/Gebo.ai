package ai.gebo.architecture.graphrag.persistence.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.graphrag.extraction.config.GraphRagExtractionStaticConfig;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.repositories.GraphRagExtractionConfigRepository;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GraphRagConfigurationController")
@AllArgsConstructor
public class GraphRagConfigurationController {
	private final GraphRagExtractionConfigRepository configRepository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final GraphRagExtractionStaticConfig staticConfig;

	@GetMapping(value = "getDefaultGraphRagExtractionConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GraphRagExtractionConfig> getDefaultGraphRagExtractionConfig() {
		return configRepository.findByDefaultConfiguration(Boolean.TRUE);
	}

	@GetMapping(value = "getSystemGraphRagExtractionConfign", produces = MediaType.APPLICATION_JSON_VALUE)
	public GraphRagExtractionConfig getSystemGraphRagExtractionConfign() {
		return staticConfig.getExtractionConfig();
	}

	@DeleteMapping(value = "deleteGraphRagExtractionConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGraphRagExtractionConfig(@RequestBody GraphRagExtractionConfig data) {
		this.configRepository.delete(data);
	}

	@GetMapping(value = "findGraphRagExtractionConfigByKnowledgeBase", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GraphRagExtractionConfig> findGraphRagExtractionConfigByKnowledgeBase(
			@RequestParam("knowledgeBaseCode") String knowledgeBaseCode) {
		return configRepository.findByKnowledgeBaseCodeAndProjectCodeIsNull(knowledgeBaseCode);
	}

	@GetMapping(value = "findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GraphRagExtractionConfig> findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(
			@RequestParam("knowledgeBaseCode") String knowledgeBaseCode,
			@RequestParam("projectCode") String projectCode) {
		return configRepository.findByKnowledgeBaseCodeAndProjectCode(knowledgeBaseCode, projectCode);
	}

	@GetMapping(value = "findGraphRagExtractionConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GraphRagExtractionConfig findGraphRagExtractionConfigByCode(@RequestParam("code") String code) {
		Optional<GraphRagExtractionConfig> data = configRepository.findById(code);
		return data.isPresent() ? data.get() : null;
	}

	@PostMapping(value = "saveGraphRagExtractionConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GraphRagExtractionConfig saveGraphRagExtractionConfig(@RequestBody GraphRagExtractionConfig data) throws GeboPersistenceException {
		return this.persistentObjectManager.update(data);
	}
	@PostMapping(value = "instertGraphRagExtractionConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GraphRagExtractionConfig instertGraphRagExtractionConfig(@RequestBody GraphRagExtractionConfig data) throws GeboPersistenceException {
		return this.persistentObjectManager.insert(data);
	}
}
