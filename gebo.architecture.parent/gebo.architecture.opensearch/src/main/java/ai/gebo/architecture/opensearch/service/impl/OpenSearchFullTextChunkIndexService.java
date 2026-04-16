package ai.gebo.architecture.opensearch.service.impl;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Conflicts;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.core.DeleteByQueryRequest;
import org.opensearch.client.opensearch.core.DeleteByQueryResponse;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.BulkResponseItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.model.DocumentMetaInfos;

import java.io.IOException;
import java.util.*;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Service
public class OpenSearchFullTextChunkIndexService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenSearchFullTextChunkIndexService.class);

	private final OpenSearchClient client;

	private final String indexName = "kb_chunks";

	private final int bulkBatchSize = 500;
	private final int deleteBatchSize = 25;

	public OpenSearchFullTextChunkIndexService(OpenSearchClient client) {
		this.client = client;
	}

	public void bulkUpsertChunks(List<FullTextChunk> chunks) throws OpenSearchException, IOException {
		if (chunks == null || chunks.isEmpty())
			return;

		for (FullTextChunk c : chunks) {
			if (c == null || isBlank(c.getId()) || isBlank(c.getContent()) || c.getDocument() == null
					|| isBlank(c.getDocument().getCode())) {
				throw new IllegalArgumentException("Invalid chunk: id/content/document/code are required");
			}
		}

		int total = chunks.size();
		int from = 0;

		while (from < total) {
			int to = Math.min(from + bulkBatchSize, total);
			List<FullTextChunk> batch = chunks.subList(from, to);

			BulkRequest.Builder br = new BulkRequest.Builder();

			for (FullTextChunk c : batch) {
				Map<String, Object> doc = toOpenSearchDoc(c);

				BulkOperation op = new BulkOperation.Builder()
						.index(i -> i.index(indexName).id(c.getId()).document(doc)).build();

				br.operations(op);
			}

			BulkResponse resp = client.bulk(br.build());

			if (resp.errors()) {
				List<String> failures = new ArrayList<>();
				for (BulkResponseItem item : resp.items()) {
					if (item.error() != null) {
						failures.add("id=" + item.id() + " type=" + item.error().type() + " reason="
								+ item.error().reason());
					}
				}
				int maxLog = Math.min(30, failures.size());
				LOGGER.error("OpenSearch bulk had errors (batch {}-{} of {}, failures={}): {}", from, to, total,
						failures.size(), failures.subList(0, maxLog));

				throw new RuntimeException("OpenSearch bulk indexing errors. failures=" + failures.size());
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("OpenSearch bulk indexed {} chunks ({}-{} of {}) into index={}", batch.size(), from,
							to, total, indexName);
				}
			}

			from = to;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> toOpenSearchDoc(FullTextChunk c) {
		FullTextDocument d = c.getDocument();
		Map<String, Object> m = (c.getMetaData() != null) ? c.getMetaData() : Collections.emptyMap();

		Map<String, Object> doc = new LinkedHashMap<>();

		doc.put("chunk_id", c.getId());
		doc.put("content", c.getContent());
		doc.put("lang", c.getLang());
		doc.put("tokens_length", c.getTokensLength());
		doc.put("position", c.getPosition());

		doc.put("document_code", d.getCode());
		doc.put("document_title", d.getTitle());
		doc.put("document_size", d.getSize());
		doc.put("document_tokens_total", d.getTokensTotal());
		doc.put("document_n_chunks", d.getNChunks());

		putStringFromMeta(doc, "knowledgebase_code", m, DocumentMetaInfos.KNOWLEDGEBASE_CODE);
		putStringFromMeta(doc, "project_code", m, DocumentMetaInfos.PROJECT_CODE);
		putStringFromMeta(doc, "project_endpoint_code", m, DocumentMetaInfos.PROJECT_ENDPOINT_CODE);

		putStringFromMeta(doc, "content_code", m, DocumentMetaInfos.CONTENT_CODE);
		putIntegerFromMeta(doc, "content_page", m, DocumentMetaInfos.CONTENT_PAGE);

		putStringFromMeta(doc, "content_extension", m, DocumentMetaInfos.CONTENT_EXTENSION);
		putStringFromMeta(doc, "content_original_url", m, DocumentMetaInfos.CONTENT_ORIGINAL_URL);
		putStringFromMeta(doc, "content_type", m, DocumentMetaInfos.CONTENT_TYPE);
		putStringFromMeta(doc, "content_description", m, DocumentMetaInfos.CONTENT_DESCRIPTION);

		putStringFromMeta(doc, "title_meta", m, DocumentMetaInfos.TITLE);
		putStringFromMeta(doc, "subtitle", m, DocumentMetaInfos.SUBTITLE);

		putStringFromMeta(doc, "language", m, DocumentMetaInfos.LANGUAGE);
		putFloatFromMeta(doc, "language_confidence", m, DocumentMetaInfos.LANGUAGE_CONFIDENCE);

		putStringFromMeta(doc, "file_type_id", m, DocumentMetaInfos.GEBO_FILE_TYPE_ID);
		putStringFromMeta(doc, "file_type_description", m, DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION);
		putStringFromMeta(doc, "file_treat_as", m, DocumentMetaInfos.GEBO_FILE_TREAT_AS);

		putStringFromMeta(doc, "file_name", m, DocumentMetaInfos.GEBO_FILE_NAME);
		putStringFromMeta(doc, "file_relative_path", m, DocumentMetaInfos.GEBO_FILE_RELATIVE_PATH);
		putStringFromMeta(doc, "file_fullpath", m, DocumentMetaInfos.GEBO_FILE_FULLPATH);

		putStringFromMeta(doc, "file_archetype_id", m, DocumentMetaInfos.GEBO_FILE_ARCHETYPEID);
		putStringFromMeta(doc, "archive_fullpath", m, DocumentMetaInfos.GEBO_ARCHIVE_FULLPATH);
		putStringFromMeta(doc, "archive_internalpath", m, DocumentMetaInfos.GEBO_ARCHIVE_INTERNALPATH);

		putLongFromMeta(doc, "token_length", m, DocumentMetaInfos.GEBO_TOKEN_LENGTH);
		putLongFromMeta(doc, "bytes_length", m, DocumentMetaInfos.GEBO_BYTES_LENGTH);

		putStringFromMeta(doc, "reference_type", m, DocumentMetaInfos.GEBO_REFERENCE_TYPE);
		putIntegerFromMeta(doc, "chunk_position_meta", m, DocumentMetaInfos.GEBO_CHUNK_POSITION);
		putListIntegerFromMeta(doc, "acl_aliases", m, DocumentMetaInfos.GEBO_ACL_ALIASES);
		doc.put("meta", m);
		return doc;
	}

	private void putListIntegerFromMeta(Map<String, Object> doc, String fieldName, Map<String, Object> m,
			String mapEntryCode) {
		if (m.containsKey(mapEntryCode) && m.get(mapEntryCode) instanceof List list && !list.isEmpty()
				&& list.get(0) instanceof Number) {
			doc.put(fieldName, list);
		}

	}

	public void deleteByDocuments(List<FullTextDocument> documents) throws OpenSearchException, IOException {
		if (documents == null || documents.isEmpty())
			return;

		LinkedHashSet<String> codes = new LinkedHashSet<>();
		for (FullTextDocument d : documents) {
			if (d == null || isBlank(d.getCode()))
				continue;
			codes.add(d.getCode().trim());
		}
		if (codes.isEmpty())
			return;

		List<String> codeList = new ArrayList<>(codes);
		int total = codeList.size();
		int from = 0;

		while (from < total) {
			int to = Math.min(from + deleteBatchSize, total);
			List<String> batchCodes = codeList.subList(from, to);

			// One delete-by-query per batch (faster than N requests)
			List<FieldValue> values = batchCodes.stream().map(FieldValue::of).toList();

			Query termsQuery = Query.of(q -> q.terms(t -> t.field("document_code").terms(tt -> tt.value(values))));

			Refresh rValue = Refresh.True;
			DeleteByQueryRequest req = new DeleteByQueryRequest.Builder().index(indexName).query(termsQuery)
					.conflicts(Conflicts.Proceed).refresh(rValue) // consider making configurable (costly at scale)
					.build();

			DeleteByQueryResponse resp = client.deleteByQuery(req);
			long deleted = resp.deleted() != null ? resp.deleted() : 0L;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"OpenSearch deleteByDocuments deleted ~{} chunks for document codes {}-{} of {} (index={})",
						deleted, from, to, total, indexName);
			}

			from = to;
		}
	}

	private static void putStringFromMeta(Map<String, Object> dst, String dstField, Map<String, Object> meta,
			String metaKey) {
		Object v = meta.get(metaKey);
		if (v == null)
			return;
		String s = v.toString();
		if (!s.isBlank())
			dst.put(dstField, s);
	}

	private static void putIntegerFromMeta(Map<String, Object> dst, String dstField, Map<String, Object> meta,
			String metaKey) {
		Integer i = toInteger(meta.get(metaKey));
		if (i != null)
			dst.put(dstField, i);
	}

	private static void putLongFromMeta(Map<String, Object> dst, String dstField, Map<String, Object> meta,
			String metaKey) {
		Long l = toLong(meta.get(metaKey));
		if (l != null)
			dst.put(dstField, l);
	}

	private static void putFloatFromMeta(Map<String, Object> dst, String dstField, Map<String, Object> meta,
			String metaKey) {
		Float f = toFloat(meta.get(metaKey));
		if (f != null)
			dst.put(dstField, f);
	}

	private static Integer toInteger(Object v) {
		if (v == null)
			return null;
		if (v instanceof Number n)
			return n.intValue();
		try {
			return Integer.parseInt(v.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	private static Long toLong(Object v) {
		if (v == null)
			return null;
		if (v instanceof Number n)
			return n.longValue();
		try {
			return Long.parseLong(v.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	private static Float toFloat(Object v) {
		if (v == null)
			return null;
		if (v instanceof Number n)
			return n.floatValue();
		try {
			return Float.parseFloat(v.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	private static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
}
