package ai.gebo.architecture.opensearch.config;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch._types.mapping.FlatObjectProperty;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.Property.Builder;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.GetIndexResponse;
import org.opensearch.client.util.ObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Configuration
public class OpenSearchIndexBootstrapConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenSearchIndexBootstrapConfig.class);

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
			return;
		}

		// settings + mappings (minimo sensato per il tuo caso)
		CreateIndexRequest req = CreateIndexRequest.of(b -> b.index(indexName)
				.settings(s -> s.numberOfShards(1).numberOfReplicas(0)
						// utile in ingestion massiva (opzionale)
						.refreshInterval(r -> r.time("1s")))
				.mappings(m -> m
						// source enabled di default
						.properties("chunk_id", p -> p.keyword(k -> k))
						.properties("document_code", p -> p.keyword(k -> k))
						.properties("document_title", p -> p.text(t -> t))
						.properties("knowledgebase_code", p -> p.keyword(k -> k))
						.properties("project_code", p -> p.keyword(k -> k))
						.properties("project_endpoint_code", p -> p.keyword(k -> k))

						.properties("content", p -> p.text(t -> t)).properties("lang", p -> p.keyword(k -> k))
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
		LOGGER.info("Created OpenSearch index '{}'", indexName);
	}

	private static ObjectBuilder<Property> configMeta(Builder p) {
		FlatObjectProperty meta = FlatObjectProperty.of(f -> f);
		return p.flatObject(meta);
	}

}
