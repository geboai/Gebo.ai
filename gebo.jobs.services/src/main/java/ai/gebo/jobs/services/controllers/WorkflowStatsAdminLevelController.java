/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.jobs.services.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.jobs.services.impl.WorkflowStatsAggregationService;
import ai.gebo.jobs.services.model.WorkflowStatsDrillDownLevel;
import ai.gebo.jobs.services.model.WorkflowStatsDrillDownResult;
import lombok.AllArgsConstructor;

/**
 * Admin level workflow data-volume reporting over the
 * {@code ComputedWorkflowStatusData} stats base. Not restricted in terms of
 * user: filters are applied only when present in the drill-down criteria.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/WorkflowStatsAdminLevelController")
@AllArgsConstructor
public class WorkflowStatsAdminLevelController {

	private final WorkflowStatsAggregationService aggregationService;

	@PostMapping(value = "drillDown", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public WorkflowStatsDrillDownResult drillDown(@RequestBody WorkflowStatsDrillDownLevel criteria) {
		return aggregationService.drillDown(criteria);
	}
}
