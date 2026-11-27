package ai.gebo.architecture.llms.usage.controller;

import java.time.LocalDate;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ai.gebo.architecture.llms.usage.service.impl.LLMUsageExcelExportService;

/** The LLM usage workbook as a file the browser saves. */
final class LLMSUsageExcelResponse {
	private LLMSUsageExcelResponse() {
	}

	static ResponseEntity<byte[]> of(byte[] workbook) {
		final String fileName = "gebo-llm-usage-" + LocalDate.now() + ".xlsx";
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename(fileName).build().toString())
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
				.contentType(MediaType.parseMediaType(LLMUsageExcelExportService.CONTENT_TYPE)).body(workbook);
	}
}
