package ai.gebo.architecture.documents.cache.service.impl;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class DocumentCacheEntry extends AbstractCachedEntry {

	private String jsonGeboDocumentName = null;
	private String binaryDocumentName = null;

}
