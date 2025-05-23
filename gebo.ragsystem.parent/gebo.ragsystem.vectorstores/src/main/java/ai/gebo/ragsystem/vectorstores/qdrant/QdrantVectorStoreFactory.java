/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.qdrant;

import java.io.IOException;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore.Builder;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.GExtendedVectorStoreWrapper;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Points.PointId;

/**
 * Factory for creating Qdrant vector store instances.
 * AI generated comments
 */
public class QdrantVectorStoreFactory implements IGVectorStoreFactory {
	/**
	 * Configuration for the Qdrant server connection
	 */
	final QdrantConfig qdrantConfig;

	/**
	 * Constructs a factory with the provided Qdrant configuration
	 * 
	 * @param qdrantConfig the configuration for connecting to Qdrant
	 */
	public QdrantVectorStoreFactory(QdrantConfig qdrantConfig) {
		this.qdrantConfig = qdrantConfig;

	}

	/**
	 * Extended Qdrant vector store implementation that wraps the Spring AI QdrantVectorStore
	 * and provides additional functionality like resource management.
	 */
	public static class QdrantExtendedVectorStore extends GExtendedVectorStoreWrapper<QdrantVectorStore> {
		/**
		 * The underlying Qdrant client
		 */
		final QdrantClient client;

		/**
		 * Constructs a new extended vector store
		 * 
		 * @param client the Qdrant client
		 * @param vs the underlying Spring AI QdrantVectorStore
		 */
		public QdrantExtendedVectorStore(QdrantClient client, QdrantVectorStore vs) {
			super(vs);
			this.client = client;
		}

		/**
		 * Closes the underlying Qdrant client connection
		 */
		@Override
		public void close() throws IOException {
			client.close();
		}
	};

	/**
	 * Creates a new Qdrant vector store with the provided embedding model configuration
	 * 
	 * @param embeddingConfiguration the embedding model configuration
	 * @param embeddingModel the embedding model to use
	 * @return a configured vector store instance
	 * @throws LLMConfigException if the vector store cannot be created
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
			throws LLMConfigException {

		try {
			ManagedChannel channel = null;
			// Configure the gRPC channel with TLS if enabled
			if (qdrantConfig.isTls()) {
				channel = ManagedChannelBuilder.forAddress(qdrantConfig.getHost(), qdrantConfig.getPort())
						.useTransportSecurity().maxInboundMessageSize(Integer.MAX_VALUE).build();
			} else
				channel = ManagedChannelBuilder.forAddress(qdrantConfig.getHost(), qdrantConfig.getPort())
						.usePlaintext().maxInboundMessageSize(Integer.MAX_VALUE).build();

			QdrantGrpcClient.Builder grpcClientBuilder = QdrantGrpcClient
					.newBuilder(channel); /*
											 * QdrantGrpcClient.newBuilder(qdrantConfig.getHost(),
											 * qdrantConfig.getPort(), qdrantConfig.isTls());
											 */
			// Add API key if provided
			if (qdrantConfig.getApiKey() != null)
				grpcClientBuilder.withApiKey(qdrantConfig.getApiKey());
			QdrantGrpcClient client = grpcClientBuilder.build();

			QdrantClient qdrantClient = new QdrantClient(client);

			// Configure Spring AI's QdrantVectorStore
			Builder builder = QdrantVectorStore.builder(qdrantClient, embeddingModel);
			builder.collectionName(embeddingConfiguration.getCode());
			builder.initializeSchema(true);

			QdrantVectorStore qdrantVectorStore = builder.build();
			qdrantVectorStore.afterPropertiesSet();
			return new QdrantExtendedVectorStore(qdrantClient, qdrantVectorStore);
		} catch (Throwable e) {
			throw new LLMConfigException(
					"Cannot create vector store or embedding model initialization exception: " + e.getMessage(), e);
		}

	}

}