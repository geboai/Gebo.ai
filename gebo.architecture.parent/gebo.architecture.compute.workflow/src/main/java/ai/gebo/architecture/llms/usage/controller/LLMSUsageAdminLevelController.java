package ai.gebo.architecture.llms.usage.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownLevel;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownResult;
import ai.gebo.architecture.llms.usage.service.impl.LLMSUsageAggregationService;
import ai.gebo.architecture.llms.usage.service.impl.LLMUsageExcelExportService;
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
	private final LLMUsageExcelExportService exportService;

	@PostMapping(value = "drillDown", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LLMUsageDrillDownResult adminDrillDown(@RequestBody LLMUsageDrillDownLevel criteria) {
		return aggregationService.drillDown(criteria, null);
	}

	/** The usage of every user the criteria select, as an Excel workbook. */
	@PostMapping(value = "exportExcel", produces = LLMUsageExcelExportService.CONTENT_TYPE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<byte[]> adminExportExcel(@RequestBody LLMUsageDrillDownLevel criteria) {
		return LLMSUsageExcelResponse.of(exportService.export(criteria, null));
	}
}
