package ai.gebo.llms.chat.client.rest.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.chat.client.rest.model.LLMUsageDrillDownLevel;
import ai.gebo.llms.chat.client.rest.model.LLMUsageDrillDownResult;
import ai.gebo.llms.chat.client.rest.services.LLMSUsageAggregationService;
import lombok.AllArgsConstructor;

/**
 * Admin level LLM usage reporting. Not restricted in terms of username: the
 * username filter is applied only when present in the drill-down criteria.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/LLMSUsageAdminLevelController")
@AllArgsConstructor
public class LLMSUsageAdminLevelController {

	private final LLMSUsageAggregationService aggregationService;

	@PostMapping(value = "drillDown", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LLMUsageDrillDownResult adminDrillDown(@RequestBody LLMUsageDrillDownLevel criteria) {
		return aggregationService.drillDown(criteria, null);
	}
}
