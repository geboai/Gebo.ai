package ai.gebo.architecture.documents.cache.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import lombok.Data;

@Document
@Data
public class DocumentChunkOperation {
	@Id
	private String id = UUID.randomUUID().toString();
	@HashIndexed
	private String originalDocumentCode = null;
	private Date created = new Date();
	@HashIndexed
	private Date lastAccessed = new Date();
	private List<String> chunksList = new ArrayList<String>();
	private List<AbstractChunkingSpecs> chunkingSpecs=new ArrayList<AbstractChunkingSpecs>();
	public DocumentChunkOperation() {

	}

}
