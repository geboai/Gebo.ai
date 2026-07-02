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
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * User level LLM usage reporting. Restricted to the currently authenticated
 * user: the username filter is always forced to the current user, regardless of
 * the drill-down criteria.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping(path = "api/users/LLMSUsageUserLevelController")
@AllArgsConstructor
public class LLMSUsageUserLevelController {

	private final LLMSUsageAggregationService aggregationService;
	private final IGSecurityService securityService;

	@PostMapping(value = "drillDown", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LLMUsageDrillDownResult userDrillDown(@RequestBody LLMUsageDrillDownLevel criteria) {
		String username = securityService.getCurrentUser().getUsername();
		return aggregationService.drillDown(criteria, username);
	}
}
