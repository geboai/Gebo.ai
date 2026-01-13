package ai.gebo.architecture.documents.cache.service.impl.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntry;
import lombok.Data;

@Document
@Data
public class DocumentCacheEntry extends AbstractCachedEntry {

	private String jsonGeboDocumentName = null;
	private String binaryDocumentName = null;
	private String extension = null;
	private String contentType = null;

}
