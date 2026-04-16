package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.rag_threasholds_autotune.model.AutotuneVectorStoreInfo;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminRagAutotuneController")
@AllArgsConstructor
public class GeboAdminRagAutotuneController {
	private final IRagThreasholdAutotuneService ragAutoTuneService;

	@GetMapping(value = "getLatestComputedVectorStores", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AutotuneVectorStoreInfo> getLatestComputedVectorStores() {
		return ragAutoTuneService.getLatestComputedVectorStores();
	}

}
