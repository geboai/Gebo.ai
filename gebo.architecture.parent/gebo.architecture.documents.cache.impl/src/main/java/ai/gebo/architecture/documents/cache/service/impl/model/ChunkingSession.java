package ai.gebo.architecture.documents.cache.service.impl.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * A chunking session: the chunks a procedure (an ingestion job, a tool call, a search)
 * makes and reads, under its unique reference ("job:&lt;id&gt;", ...).
 */
@Document
@Data
public class ChunkingSession extends GBaseObject {
	@HashIndexed
	String chunkingReference = null;
	Boolean canBeDeleted = null;
	/**
	 * When the session was disposed, null while it lives. A disposed session keeps its
	 * records for the retention (see CacheOrphansCleanupConfig): a late read produces its
	 * chunks again, a late chunking still records into it; its chunk files go after the
	 * grace period, unless a living session names them.
	 */
	Date logicalDeletionTimestamp = null;

	/** Whether the session was disposed. */
	public boolean disposed() {
		return logicalDeletionTimestamp != null;
	}
}
