package ai.gebo.architecture.opensearch.config;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Conflicts;
import org.opensearch.client.opensearch._types.mapping.FlatObjectProperty;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.Property.Builder;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.core.UpdateByQueryResponse;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.GetMappingResponse;
import org.opensearch.client.opensearch.indices.IndexSettingsAnalysis;
import org.opensearch.client.opensearch.indices.get_mapping.IndexMappingRecord;
import org.opensearch.client.util.ObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.function.Function;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Configuration
public class OpenSearchIndexBootstrapConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenSearchIndexBootstrapConfig.class);
	/**
	 * The analyzer of the folded text: the words lowercased and without their accents
	 * ("Svâbhâvat" is indexed as "svabhavat"), Latin scripts (OpenSearch's built-in
	 * asciifolding; other scripts would need the ICU plugin).
	 */
	public static final String FOLDING_ANALYZER = "gebo_folding";
	/** The sub-field of a text field holding its folded text. */
	public static final String FOLDED_SUBFIELD = "folded";

	@Bean
	public ApplicationRunner openSearchIndexBootstrap(OpenSearchClient client) {
		return args -> {
			ensureKbChunksIndex(client, "kb_chunks");
		};
	}

	private static void ensureKbChunksIndex(OpenSearchClient client, String indexName) throws IOException {

		boolean exists = client.indices().exists(ExistsRequest.of(b -> b.index(indexName))).value();
		if (exists) {
			LOGGER.info("OpenSearch index '{}' already exists", indexName);
			ensureFoldedText(client, indexName);
			return;
		}

		// settings + mappings (minimo sensato per il tuo caso)
		CreateIndexRequest req = CreateIndexRequest.of(b -> b.index(indexName)
				.settings(s -> s.numberOfShards(1).numberOfReplicas(0)
						// utile in ingestion massiva (opzionale)
						.refreshInterval(r -> r.time("1s")).analysis(foldingAnalysis()))
				.mappings(m -> m
						// source enabled di default
						.properties("chunk_id", p -> p.keyword(k -> k))
						.properties("document_code", p -> p.keyword(k -> k))
						.properties("document_title", textWithFolded())
						.properties("knowledgebase_code", p -> p.keyword(k -> k))
						.properties("project_code", p -> p.keyword(k -> k))
						.properties("project_endpoint_code", p -> p.keyword(k -> k))

						.properties("content", textWithFolded()).properties("lang", p -> p.keyword(k -> k))
						.properties("tokens_length", p -> p.long_(n -> n))
						.properties("position", p -> p.integer(n -> n))

						.properties("content_code", p -> p.keyword(k -> k))
						.properties("content_extension", p -> p.keyword(k -> k))
						.properties("content_type", p -> p.keyword(k -> k))
						.properties("content_original_url", p -> p.keyword(k -> k))
						.properties("content_page", p -> p.integer(n -> n))

						.properties("file_treat_as", p -> p.keyword(k -> k))
						.properties("file_name", p -> p.keyword(k -> k))
						.properties("file_relative_path", p -> p.keyword(k -> k))
						.properties("reference_type", p -> p.keyword(k -> k))

						// meta: se vuoi query su meta.* usa "flattened" (consigliato)
						.properties("meta", p -> {
							return configMeta(p);
						})));

		client.indices().create(req);
		LOGGER.info("Created OpenSearch index '{}' with the folded text of its content and titles", indexName);
	}

	/**
	 * An index made before the folded text gets it: the analyzer is added (the index
	 * closed for it, then opened again), the folded sub-fields mapped, and the chunks
	 * already indexed analyzed again from their stored source in the background. On
	 * failure the searches go on with the text as written: a query on the missing
	 * sub-field matches nothing.
	 */
	private static void ensureFoldedText(OpenSearchClient client, String indexName) {
		try {
			if (hasFoldedText(client, indexName)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("OpenSearch index '{}' has the folded text already", indexName);
				}
				return;
			}
			LOGGER.info("OpenSearch index '{}' has no folded text: adding it", indexName);
			client.indices().close(c -> c.index(indexName));
			try {
				client.indices().putSettings(p -> p.index(indexName).settings(s -> s.analysis(foldingAnalysis())));
			} finally {
				client.indices().open(o -> o.index(indexName));
			}
			client.indices().putMapping(
					p -> p.index(indexName).properties("content", textWithFolded()).properties("document_title",
							textWithFolded()));
			final UpdateByQueryResponse reanalysis = client
					.updateByQuery(u -> u.index(indexName).conflicts(Conflicts.Proceed).waitForCompletion(false));
			LOGGER.info("OpenSearch index '{}' folded text mapped, the indexed chunks analyzed again in the background"
					+ " (task {})", indexName, reanalysis.task());
		} catch (IOException | RuntimeException e) {
			LOGGER.error("OpenSearch index '" + indexName
					+ "' could not get the folded text: the searches match the text as written only", e);
		}
	}

	private static boolean hasFoldedText(OpenSearchClient client, String indexName) throws IOException {
		final GetMappingResponse mapping = client.indices().getMapping(g -> g.index(indexName));
		for (IndexMappingRecord record : mapping.result().values()) {
			final TypeMapping typeMapping = record.mappings();
			final Property content = typeMapping != null && typeMapping.properties() != null
					? typeMapping.properties().get("content")
					: null;
			if (content != null && content.isText() && content.text().fields() != null
					&& content.text().fields().containsKey(FOLDED_SUBFIELD)) {
				return true;
			}
		}
		return false;
	}

	private static Function<IndexSettingsAnalysis.Builder, ObjectBuilder<IndexSettingsAnalysis>> foldingAnalysis() {
		return a -> a.analyzer(FOLDING_ANALYZER,
				an -> an.custom(c -> c.tokenizer("standard").filter("lowercase", "asciifolding")));
	}

	/** A text field as written, with its folded text in a sub-field. */
	private static Function<Property.Builder, ObjectBuilder<Property>> textWithFolded() {
		return p -> p.text(t -> t.fields(FOLDED_SUBFIELD, f -> f.text(tt -> tt.analyzer(FOLDING_ANALYZER))));
	}

	private static ObjectBuilder<Property> configMeta(Builder p) {
		FlatObjectProperty meta = FlatObjectProperty.of(f -> f);
		return p.flatObject(meta);
	}

}
