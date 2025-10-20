package ai.gebo.multilanguage.support.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import ai.gebo.multilanguage.support.config.RCFolderConfig;
import ai.gebo.multilanguage.support.model.UIExistingText;
import ai.gebo.multilanguage.support.repository.UIExistingTextRepository;
import ai.gebo.multilanguage.support.services.UIExistingTextService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/UITextResourcesController")
@AllArgsConstructor
public class UITextResourcesController {
	private final RCFolderConfig config;
	private final UIExistingTextService service;

	@AllArgsConstructor
	@Getter
	public static class UiTextResourcesModule {
		private final boolean enabled;
	}

	@GetMapping(value = "getUiTextResourcesModule", produces = MediaType.APPLICATION_JSON_VALUE)
	public UiTextResourcesModule getUiTextResourcesModule() {
		return new UiTextResourcesModule(config != null && config.isLiveRecording());
	}

	@PostMapping(value = "updateUIExistingTexts", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUIExistingTexts(@RequestBody @Valid @NotNull @NotEmpty List<UIExistingText> texts) {
		if (!config.isLiveRecording())
			throw new RuntimeException("Cannot post here if system not in liveRecording mode");
		service.update(texts);
	}

	@GetMapping
	public ResponseEntity<Resource> getI18n() throws StreamWriteException, DatabindException, IOException {
		if (!config.isLiveRecording())
			throw new RuntimeException("Cannot post here if system not in liveRecording mode");
		InputStream inputStream = service.getCurrentLanguageResourcesStream();
		InputStreamResource resource = new InputStreamResource(inputStream);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; en.json");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_JSON).body(resource);
	}

}
