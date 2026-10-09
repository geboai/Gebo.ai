package ai.gebo.architecture.documents.cache.service.impl.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntry;
import lombok.Data;

/**
 * The chunks of one document in one chunking session. Each session has its own records,
 * never shared: a session reusing the chunks of another (same document version, same
 * chunking parameters) records its own, naming the same chunk files. A record outlives
 * its session's disposal (see {@link ChunkingSession#getLogicalDeletionTimestamp()}): its
 * files go once no record of a living session names them, the record itself after the
 * retention, so a late read of the session can produce its chunks again.
 */
@Document
@Data
public class DocumentChunkOperation extends AbstractCachedEntry {

	@HashIndexed
	private String originalDocumentCode = null;
	private List<String> chunkSetsList = new ArrayList<String>();
	private List<AbstractChunkingSpecs> chunkingSpecs = new ArrayList<AbstractChunkingSpecs>();
	/**
	 * The whole chunking parameters the chunks were made with: what makes them reusable
	 * by another session and producible again. Null for a record written before they were
	 * recorded (neither reusable nor producible again).
	 */
	private ChunkingParams chunkingParams = null;
	/** The modification date of the document the chunks were made from, as its caller gave it. */
	private Date documentModificationDate = null;
	private boolean enrichWithMetaData = false;
	private long totalBytesSize = 0l, totalTokensSize = 0l;
	private int totalChunks = 0;
	/**
	 * The chunks the whole document was split into, the ones a matching policy left out
	 * included: the count the chunk positions refer to. 0 for an operation written before
	 * it was recorded.
	 */
	private long documentChunks = 0l;
	@HashIndexed
	private String chunkingSessionId = null;

	public DocumentChunkOperation() {
		id = UUID.randomUUID().toString();
	}

}
