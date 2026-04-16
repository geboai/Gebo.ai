package ai.gebo.architecture.documents.cache.service.impl.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class ChunkingSession extends GBaseObject {
	@HashIndexed
	String chunkingReference = null;
	Boolean canBeDeleted = null;
	Date logicalDeletionTimestamp = null;
}
