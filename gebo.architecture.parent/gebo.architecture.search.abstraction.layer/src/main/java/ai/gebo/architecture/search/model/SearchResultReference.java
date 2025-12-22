package ai.gebo.architecture.search.model;

import java.util.UUID;

import lombok.Data;

@Data
public class SearchResultReference {
	private String uri;
	private String name;
	private String contentType;
	private String extension;
	private Long size;
	private String title = null;
	private String id = UUID.randomUUID().toString();
}
