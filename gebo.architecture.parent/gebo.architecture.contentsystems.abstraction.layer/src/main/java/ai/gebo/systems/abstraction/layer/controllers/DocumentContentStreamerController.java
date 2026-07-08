package ai.gebo.systems.abstraction.layer.controllers;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@PreAuthorize("hasRole('ADMIN','USER','APPLICATION')")
@RequestMapping("api/users/DocumentContentStreamerController")
@AllArgsConstructor
public class DocumentContentStreamerController {
	private final IGDocumentContentStreamer streamer;

	@Data
	public static class GDocumentReferenceStreamRequest {
		@NotNull
		public StreamingPurpose streamingPurpose;
		@NotNull
		public GDocumentReference reference;
	}

	@Data
	public static class SearchResultStreamRequest {
		@NotNull
		public StreamingPurpose streamingPurpose;
		@NotNull
		public SearchResult reference;
	}

	@PostMapping(value = "streamDocumentReference", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> streamDocumentReference(@RequestBody GDocumentReferenceStreamRequest document)
			throws DocumentContentStreamerException, IOException {
		TypedInputStream tis = streamer.streamContent(document.streamingPurpose, document.reference);
		if (tis == null || tis.getInputStream() == null) {
			return ResponseEntity.noContent().build();
		}
		MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
		if (tis.getContentType() != null) {

			contentType = MediaType.parseMediaType(tis.getContentType());

		}
		ResponseEntity.BodyBuilder builder = ResponseEntity.ok().contentType(contentType);
		if (tis.getExtension() != null) {
			builder.header("X-Gebo-Content-Extension", tis.getExtension());
		}
		return builder.body(new InputStreamResource(tis.getInputStream()));

	}

	@PostMapping(value = "streamSearchResult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> streamSearchResult(@RequestBody SearchResultStreamRequest document)
			throws DocumentContentStreamerException, IOException {
		TypedInputStream tis = streamer.streamContent(document.streamingPurpose, document.reference);
		if (tis == null || tis.getInputStream() == null) {
			return ResponseEntity.noContent().build();
		}
		MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
		if (tis.getContentType() != null) {

			contentType = MediaType.parseMediaType(tis.getContentType());

		}
		ResponseEntity.BodyBuilder builder = ResponseEntity.ok().contentType(contentType);
		if (tis.getExtension() != null) {
			builder.header("X-Gebo-Content-Extension", tis.getExtension());
		}
		return builder.body(new InputStreamResource(tis.getInputStream()));

	}
}
