package ai.gebo.architecture.opensearch.service.impl;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.Operator;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.FieldAndFormat;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.JsonpMapper;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.model.DocumentMetaInfos;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Service
public class OpenSearchFullTextChunkSearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenSearchFullTextChunkSearchService.class);

	private final OpenSearchClient client;
	private final String indexName = "kb_chunks";

	public OpenSearchFullTextChunkSearchService(OpenSearchClient client) {
		this.client = client;
	}

	public List<FullTextChunkSearchHit> searchTopKChunks(List<String> q, int topK, FullTextSearchMetaDataFilter filter)
			throws OpenSearchException, IOException {

		if (q == null || q.isEmpty())
			return List.of();
		if (topK <= 0)
			return List.of();

		// normalize + dedup preserve order
		LinkedHashSet<String> qs = new LinkedHashSet<>();
		for (String s : q) {
			if (s == null)
				continue;
			String t = s.trim();
			if (!t.isEmpty())
				qs.add(t);
		}
		if (qs.isEmpty())
			return List.of();

		topK = Math.min(topK, 500);

		// Build SHOULD queries (OR)
		List<Query> shouldQueries = new ArrayList<>(qs.size());
		for (String single : qs) {
			shouldQueries.add(buildMainQuery(single)); // returns Query
		}

		// Filters (AND)
		List<Query> filters = buildFilters(filter);

		BoolQuery.Builder bb = new BoolQuery.Builder();

		// OR across queries
		bb.should(shouldQueries);
		bb.minimumShouldMatch("1");

		// AND filters
		if (!filters.isEmpty()) {
			bb.filter(filters);
		}

		Query finalQuery = new Query.Builder().bool(bb.build()).build();

		SearchRequest.Builder sb = new SearchRequest.Builder().index(indexName).query(finalQuery)
				.trackTotalHits(t -> t.enabled(false))
				.highlight(h -> h.fields("content", hf -> hf.fragmentSize(180).numberOfFragments(2)))
				.source(s -> s.filter(sf -> sf.includes("chunk_id", "content", "lang", "tokens_length", "position",
						"document_code", "document_title", "document_size", "document_tokens_total",
						"document_n_chunks", "knowledgebase_code", "project_code", "project_endpoint_code",
						"content_code", "content_page", "content_extension", "content_type", "content_original_url",
						"language", "language_confidence", "file_treat_as", "file_name", "file_relative_path",
						"reference_type", "meta")));

		// Optional collapse by document_code
		if (filter != null && filter.isCollapseByDocument()) {
			int inner = Math.max(1, filter.getPerDocumentInnerHits());
			sb.collapse(c -> c.field("document_code")
					.innerHits(ih -> ih.name("top_chunks_per_doc").size(inner)
							.highlight(h -> h.fields("content", hf -> hf.fragmentSize(180).numberOfFragments(2)))
							.source(s -> s.filter(sf -> sf.includes("chunk_id", "content", "lang", "tokens_length",
									"position", "document_code", "document_title", "knowledgebase_code", "project_code",
									"project_endpoint_code", "content_code", "content_page", "content_extension",
									"content_type", "language", "file_treat_as", "file_relative_path", "reference_type",
									"meta")))));
			sb.size(topK);
		} else {
			sb.size(topK);
		}

		return runSearch(sb, finalQuery, filter, qs.size() + " quer(ies) OR-ed", topK);
	}

	/**
	 * Search topK chunks with optional filters.
	 * 
	 * @throws IOException
	 * @throws OpenSearchException
	 */
	public List<FullTextChunkSearchHit> searchTopKChunks(String q, int topK, FullTextSearchMetaDataFilter filter)
			throws OpenSearchException, IOException {
		if (q == null || q.trim().isEmpty())
			return List.of();
		if (topK <= 0)
			return List.of();

		topK = Math.min(topK, 500);

		Query mainQuery = buildMainQuery(q.trim());
		List<Query> filters = buildFilters(filter);

		BoolQuery.Builder bb = new BoolQuery.Builder();
		bb.must(mainQuery);
		if (!filters.isEmpty()) {
			bb.filter(filters);
		}

		Query finalQuery = new Query.Builder().bool(bb.build()).build();

		SearchRequest.Builder sb = new SearchRequest.Builder().index(indexName).query(finalQuery)
				.trackTotalHits(t -> t.enabled(false))
				.highlight(h -> h.fields("content", hf -> hf.fragmentSize(180).numberOfFragments(2)))
				.source(s -> s.filter(sf -> sf.includes("chunk_id", "content", "lang", "tokens_length", "position",
						"document_code", "document_title", "document_size", "document_tokens_total",
						"document_n_chunks",
						// promoted metadata
						"knowledgebase_code", "project_code", "project_endpoint_code", "content_code", "content_page",
						"content_extension", "content_type", "content_original_url", "language", "language_confidence",
						"file_treat_as", "file_name", "file_relative_path", "reference_type", "meta", "acl_aliases")));

		// Optional: collapse by document_code to avoid too many chunks from same doc
		if (filter != null && filter.isCollapseByDocument()) {
			int inner = Math.max(1, filter.getPerDocumentInnerHits());
			sb.collapse(c -> c.field("document_code")
					.innerHits(ih -> ih.name("top_chunks_per_doc").size(inner)
							.highlight(h -> h.fields("content", hf -> hf.fragmentSize(180).numberOfFragments(2)))
							.source(s -> s.filter(sf -> sf.includes("chunk_id", "content", "lang", "tokens_length",
									"position", "document_code", "document_title", "knowledgebase_code", "project_code",
									"project_endpoint_code", "content_code", "content_page", "content_extension",
									"content_type", "language", "file_treat_as", "file_relative_path", "reference_type",
									"meta", "acl_aliases")))));

			// In collapse mode, "size" = number of docs (i.e., distinct document_code)
			sb.size(topK);
		} else {
			sb.size(topK);
		}

		// Convert hits (also include inner_hits if collapse is enabled)
		return runSearch(sb, finalQuery, filter, "1 query", topK);
	}

	/**
	 * Runs the search, logging the call at DEBUG and the query sent and the hits at
	 * TRACE: every term of a query must match (operator AND, see buildMainQuery), so a
	 * query with no hit is told apart from an index with nothing for these filters.
	 */
	private List<FullTextChunkSearchHit> runSearch(SearchRequest.Builder sb, Query finalQuery,
			FullTextSearchMetaDataFilter filter, String what, int topK) throws OpenSearchException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(...) index:" + indexName + " " + what + " topK:" + topK + " knowledge bases:"
					+ (filter != null ? filter.getKnowledgebaseCodes() : null) + " collapseByDocument:"
					+ (filter != null && filter.isCollapseByDocument()));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<FULLTEXT_QUERY index=" + indexName + ">");
			LOGGER.trace(toJson(finalQuery));
			LOGGER.trace("</FULLTEXT_QUERY>");
		}
		final long start = System.currentTimeMillis();
		final SearchResponse<Map> resp = client.search(sb.build(), Map.class);
		final List<FullTextChunkSearchHit> hits = flattenHits(resp, filter);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End search(...) index:" + indexName + " " + what + " returned " + hits.size() + " chunk(s) in "
					+ (System.currentTimeMillis() - start) + " ms");
		}
		if (LOGGER.isTraceEnabled()) {
			for (FullTextChunkSearchHit hit : hits) {
				LOGGER.trace("Full-text hit score:" + hit.getScore() + " chunk:"
						+ (hit.getChunk() != null ? hit.getChunk().getId() + " "
								+ (hit.getChunk().getDocument() != null ? hit.getChunk().getDocument().getTitle() : null)
								: null)
						+ " highlight:" + hit.getHighlight());
			}
		}
		return hits;
	}

	/** The query as OpenSearch receives it, for the TRACE log. */
	private String toJson(Query query) {
		try {
			final JsonpMapper mapper = client._transport().jsonpMapper();
			final StringWriter writer = new StringWriter();
			try (JsonGenerator generator = mapper.jsonProvider().createGenerator(writer)) {
				query.serialize(generator, mapper);
			}
			return writer.toString();
		} catch (RuntimeException e) {
			return String.valueOf(query);
		}
	}

	private Query buildMainQuery(String q) {
		return Query.of(qq -> qq.multiMatch(mm -> mm.query(q).fields("content^4", "document_title^2", "meta.*^0.5")
				.operator(org.opensearch.client.opensearch._types.query_dsl.Operator.And)
				.type(org.opensearch.client.opensearch._types.query_dsl.TextQueryType.BestFields)));
	}

	private List<Query> buildFilters(FullTextSearchMetaDataFilter f) {
		if (f == null) {
			return List.of();
		}

		List<Query> filters = new ArrayList<>();

		// Exact keyword filters
		termsIfPresent(filters, "knowledgebase_code", f.getKnowledgebaseCodes());
		termIfPresent(filters, "project_code", f.getProjectCode());
		termIfPresent(filters, "project_endpoint_code", f.getProjectEndpointCode());

		termIfPresent(filters, "content_code", f.getContentCode());
		termIfPresent(filters, "content_extension", f.getContentExtension());
		termIfPresent(filters, "content_type", f.getContentType());

		/*
		 * if (f.getContentPage() != null) { Integer page = f.getContentPage();
		 * filters.add(Query.of(q -> q.term(t -> t.field("content_page").value(v ->
		 * v.intValue(page))))); }
		 */

		termIfPresent(filters, "language", f.getLanguage());
		termIfPresent(filters, "file_treat_as", f.getFileTreatAs());
		termIfPresent(filters, "reference_type", f.getReferenceType());
		termIfPresent(filters, "acl_aliases", f.getAclAliases());
		// Prefix filter on file path (keyword field)
		if (f.getFileRelativePathPrefix() != null && !f.getFileRelativePathPrefix().isBlank()) {
			String prefix = f.getFileRelativePathPrefix().trim();
			filters.add(Query.of(q -> q.prefix(p -> p.field("file_relative_path").value(prefix))));
		}

		// Restrict to a set of documents (doc->chunk second stage)
		if (f.getDocumentCodes() != null && !f.getDocumentCodes().isEmpty()) {
			List<String> codes = f.getDocumentCodes().stream().filter(Objects::nonNull).map(String::trim)
					.filter(s -> !s.isEmpty()).distinct().toList();

			if (!codes.isEmpty()) {
				List<FieldValue> values = codes.stream().map(FieldValue::of).toList();

				filters.add(Query.of(q -> q.terms(t -> t.field("document_code").terms(tt -> tt.value(values)))));
			}
		}

		// TODO Enterprise: ACL / tenant filters
		// termIfPresent(filters, "tenant_id", tenantId);
		// filters.add(Query.of(q -> q.terms(t -> t.field("aclTokens").terms(tt ->
		// tt.value(userTokensAsJsonData)))));

		return filters;
	}

	private void termIfPresent(List<Query> filters, String field, List<Integer> aclAliases) {
		if (aclAliases == null || aclAliases.isEmpty()) {
			return;
		}

		List<FieldValue> values = aclAliases.stream().filter(Objects::nonNull).distinct()
				.map(v -> FieldValue.of(v.longValue())).toList();

		if (values.isEmpty()) {
			return;
		}

		filters.add(Query.of(q -> q.terms(t -> t.field(field).terms(tt -> tt.value(values)))));

	}

	private static void termsIfPresent(List<Query> filters, String field, List<String> values) {
		if (values == null || values.isEmpty())
			return;

		List<FieldValue> fv = values.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty())
				.distinct().map(FieldValue::of).toList();

		if (fv.isEmpty())
			return;

		filters.add(Query.of(q -> q.terms(t -> t.field(field).terms(tt -> tt.value(fv)))));
	}

	private static void termIfPresent(List<Query> filters, String field, String value) {
		if (value == null)
			return;
		String v = value.trim();
		if (v.isEmpty())
			return;

		filters.add(Query.of(q -> q.term(t -> t.field(field).value(val -> val.stringValue(v)))));
	}

	private List<FullTextChunkSearchHit> flattenHits(SearchResponse<Map> resp, FullTextSearchMetaDataFilter filter) {
		List<FullTextChunkSearchHit> out = new ArrayList<>();

		boolean collapsed = filter != null && filter.isCollapseByDocument();

		for (Hit<Map> h : resp.hits().hits()) {
			if (!collapsed) {
				out.add(toHitMap(h));
				continue;
			}

			// Collapse enabled: main hit is per-document representative.
			// We primarily return inner_hits chunks if present.
			if (h.innerHits() != null && h.innerHits().containsKey("top_chunks_per_doc")) {
				var inner = h.innerHits().get("top_chunks_per_doc");
				if (inner != null && inner.hits() != null && inner.hits().hits() != null) {
					for (Hit<JsonData> ih : inner.hits().hits()) {
						out.add(toHit(ih));
					}
					continue;
				}
			}

			// Fallback: return main hit
			out.add(toHitMap(h));
		}

		return out;
	}

	FullTextChunkSearchHit toHitMap(Hit<Map> hit) {
		Map src = hit.source();
		if (src == null)
			return null;
		FullTextChunk chunk = fromSource(src);
		FullTextChunkSearchHit out = new FullTextChunkSearchHit();
		out.setChunk(chunk);
		out.setScore(hit.score());
		String snippet = null;
		if (hit.highlight() != null) {
			List<String> frags = hit.highlight().get("content");
			if (frags != null && !frags.isEmpty())
				snippet = String.join(" ... ", frags);
		}
		out.setHighlight(snippet);
		return out;
	}

	private FullTextChunkSearchHit toHit(Hit<JsonData> hit) {
		JsonData srcData = hit.source();
		if (srcData == null) {
			return null;
		}

		// JsonData -> Map: the map holds jakarta.json values (a JsonString prints with its
		// quotes, a JsonNumber is no Number), turned into plain Java values
		@SuppressWarnings("unchecked")
		Map<String, Object> src = (Map<String, Object>) plain(srcData.to(Map.class));

		FullTextChunk chunk = fromSource(src);

		FullTextChunkSearchHit out = new FullTextChunkSearchHit();
		out.setChunk(chunk);
		out.setScore(hit.score());

		String snippet = null;
		if (hit.highlight() != null) {
			List<String> frags = hit.highlight().get("content");
			if (frags != null && !frags.isEmpty()) {
				snippet = String.join(" ... ", frags);
			}
		}
		out.setHighlight(snippet);

		return out;
	}

	@SuppressWarnings("unchecked")
	private FullTextChunk fromSource(Map src) {
		FullTextChunk c = new FullTextChunk();
		c.setId(toString(src.get("chunk_id"), null));
		c.setContent(toString(src.get("content"), null));
		c.setLang(toString(src.get("lang"), null));

		Object tl = src.get("tokens_length");
		if (tl instanceof Number n)
			c.setTokensLength(n.longValue());

		Object pos = src.get("position");
		if (pos instanceof Number n)
			c.setPosition(n.intValue());

		// --- Start from stored meta if present (recommended) ---
		Map<String, Object> meta = new HashMap<>();
		Object metaObj = src.get("meta");
		if (metaObj instanceof Map m) {
			meta.putAll((Map<String, Object>) m);
		}

		// --- Recompose meta entries from promoted top-level fields ---
		// KB / Project
		putBack(meta, DocumentMetaInfos.KNOWLEDGEBASE_CODE, src.get("knowledgebase_code"));
		putBack(meta, DocumentMetaInfos.PROJECT_CODE, src.get("project_code"));
		putBack(meta, DocumentMetaInfos.PROJECT_ENDPOINT_CODE, src.get("project_endpoint_code"));

		// Content identity / type
		putBack(meta, DocumentMetaInfos.CONTENT_CODE, src.get("content_code"));
		putBack(meta, DocumentMetaInfos.CONTENT_PAGE, src.get("content_page"));

		putBack(meta, DocumentMetaInfos.CONTENT_EXTENSION, src.get("content_extension"));
		putBack(meta, DocumentMetaInfos.CONTENT_ORIGINAL_URL, src.get("content_original_url"));
		putBack(meta, DocumentMetaInfos.CONTENT_TYPE, src.get("content_type"));
		putBack(meta, DocumentMetaInfos.CONTENT_DESCRIPTION, src.get("content_description"));

		// Titles (these were stored as title_meta/subtitle to avoid collision)
		putBack(meta, DocumentMetaInfos.TITLE, src.get("title_meta"));
		putBack(meta, DocumentMetaInfos.SUBTITLE, src.get("subtitle"));

		// Language (keep both if you want; meta LANGUAGE is useful for filtering later)
		putBack(meta, DocumentMetaInfos.LANGUAGE, firstNonBlank(src.get("language"), c.getLang()));
		putBack(meta, DocumentMetaInfos.LANGUAGE_CONFIDENCE, src.get("language_confidence"));

		// File info
		putBack(meta, DocumentMetaInfos.GEBO_FILE_TYPE_ID, src.get("file_type_id"));
		putBack(meta, DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION, src.get("file_type_description"));
		putBack(meta, DocumentMetaInfos.GEBO_FILE_TREAT_AS, src.get("file_treat_as"));

		putBack(meta, DocumentMetaInfos.GEBO_FILE_NAME, src.get("file_name"));
		putBack(meta, DocumentMetaInfos.GEBO_FILE_RELATIVE_PATH, src.get("file_relative_path"));
		putBack(meta, DocumentMetaInfos.GEBO_FILE_FULLPATH, src.get("file_fullpath"));

		putBack(meta, DocumentMetaInfos.GEBO_FILE_ARCHETYPEID, src.get("file_archetype_id"));
		putBack(meta, DocumentMetaInfos.GEBO_ARCHIVE_FULLPATH, src.get("archive_fullpath"));
		putBack(meta, DocumentMetaInfos.GEBO_ARCHIVE_INTERNALPATH, src.get("archive_internalpath"));

		// Sizes
		putBack(meta, DocumentMetaInfos.GEBO_TOKEN_LENGTH, src.get("token_length"));
		putBack(meta, DocumentMetaInfos.GEBO_BYTES_LENGTH, src.get("bytes_length"));

		// Reference / chunk position
		putBack(meta, DocumentMetaInfos.GEBO_REFERENCE_TYPE, src.get("reference_type"));
		putBack(meta, DocumentMetaInfos.GEBO_CHUNK_POSITION, src.get("chunk_position_meta"));
		putBack(meta, DocumentMetaInfos.GEBO_ACL_ALIASES, src.get("acl_aliases"));
		// If you also stored it top-level (I previously commented it out):
		// putBack(meta, DocumentMetaInfos.GEBO_EMBEDDING_METADATA,
		// src.get("embedding_meta"));

		c.setMetaData(meta);

		// Document
		FullTextDocument d = new FullTextDocument();
		d.setCode(toString(src.get("document_code"), null));
		d.setTitle(toString(src.get("document_title"), null));

		Object size = src.get("document_size");
		if (size instanceof Number n)
			d.setSize(n.longValue());

		Object tt = src.get("document_tokens_total");
		if (tt instanceof Number n)
			d.setTokensTotal(n.longValue());

		Object nc = src.get("document_n_chunks");
		if (nc instanceof Number n)
			d.setNChunks(n.intValue());

		c.setDocument(d);
		return c;
	}

	private String toString(Object object, Object object2) {
		if (object == null)
			return object2 != null ? object2.toString() : null;
		if (object instanceof String s) {
			return s;
		} else {
			return object.toString();
		}

	}

	/**
	 * The given value with every jakarta.json value in it turned into the plain Java
	 * one (String, Integer/Long/Double, Boolean, null, Map, List), as the source of a
	 * main hit is read.
	 */
	static Object plain(Object value) {
		if (value instanceof JsonString s) {
			return s.getString();
		}
		if (value instanceof JsonNumber n) {
			if (!n.isIntegral()) {
				return n.doubleValue();
			}
			return n.bigIntegerValue().bitLength() < 32 ? (Object) n.intValue() : (Object) n.longValue();
		}
		if (value instanceof JsonObject o) {
			Map<String, Object> out = new LinkedHashMap<>();
			o.forEach((k, v) -> out.put(k, plain(v)));
			return out;
		}
		if (value instanceof JsonArray a) {
			List<Object> out = new ArrayList<>();
			a.forEach(v -> out.add(plain(v)));
			return out;
		}
		if (value instanceof JsonValue v) {
			return switch (v.getValueType()) {
			case TRUE -> Boolean.TRUE;
			case FALSE -> Boolean.FALSE;
			default -> null;
			};
		}
		if (value instanceof Map<?, ?> m) {
			Map<Object, Object> out = new LinkedHashMap<>();
			m.forEach((k, v) -> out.put(k, plain(v)));
			return out;
		}
		return value;
	}

	private static void putBack(Map<String, Object> meta, String key, Object value) {
		if (value == null)
			return;

		// keep existing meta value if already present (writer might store full meta
		// anyway)
		if (meta.containsKey(key))
			return;

		// normalize blank strings to "skip"
		if (value instanceof String s) {
			String t = s.trim();
			if (t.isEmpty())
				return;
			meta.put(key, t);
			return;
		}

		meta.put(key, value);
	}

	private static Object firstNonBlank(Object a, Object b) {
		if (a instanceof String s1 && !s1.trim().isEmpty())
			return s1.trim();
		if (b instanceof String s2 && !s2.trim().isEmpty())
			return s2.trim();
		return a != null ? a : b;
	}
}
