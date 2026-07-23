/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import ai.gebo.ranker.model.RankingOutput.RankingItem;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;
import software.amazon.awssdk.services.bedrockagentruntime.model.BedrockRerankingConfiguration;
import software.amazon.awssdk.services.bedrockagentruntime.model.BedrockRerankingModelConfiguration;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankDocument;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankQuery;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankRequest;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankResponse;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankResult;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankSource;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankTextDocument;
import software.amazon.awssdk.services.bedrockagentruntime.model.RerankingConfiguration;

/**
 * Native platform {@link RankerModel} backed by the AWS Bedrock Agent Runtime
 * {@code Rerank} operation (Amazon Rerank / Cohere Rerank). Documents are
 * submitted inline as text and the relevance scores returned by Bedrock are
 * mapped back onto the source documents.
 */
public class BedrockRankerModel implements RankerModel {

	private final BedrockAgentRuntimeClient client;
	private final String modelArn;

	public BedrockRankerModel(BedrockAgentRuntimeClient client, String modelArn) {
		this.client = client;
		this.modelArn = modelArn;
	}

	@Override
	public RankingOutput call(RankingInput input) {
		List<Document> documents = input.getDocuments();

		List<RerankSource> sources = new ArrayList<>();
		for (Document document : documents) {
			RerankTextDocument textDocument = RerankTextDocument.builder().text(document.getText()).build();
			RerankDocument rerankDocument = RerankDocument.builder().type("TEXT").textDocument(textDocument).build();
			sources.add(RerankSource.builder().type("INLINE").inlineDocumentSource(rerankDocument).build());
		}

		RerankQuery query = RerankQuery.builder().type("TEXT")
				.textQuery(RerankTextDocument.builder().text(input.getQuery()).build()).build();

		int numberOfResults = input.getTopK() != null ? input.getTopK() : documents.size();

		RerankingConfiguration rerankingConfiguration = RerankingConfiguration.builder()
				.type("BEDROCK_RERANKING_MODEL")
				.bedrockRerankingConfiguration(BedrockRerankingConfiguration.builder()
						.modelConfiguration(BedrockRerankingModelConfiguration.builder().modelArn(modelArn).build())
						.numberOfResults(numberOfResults)
						.build())
				.build();

		RerankRequest request = RerankRequest.builder()
				.queries(query)
				.sources(sources)
				.rerankingConfiguration(rerankingConfiguration)
				.build();

		RerankResponse response = client.rerank(request);

		List<RankingItem> ranked = new ArrayList<>();
		for (RerankResult result : response.results()) {
			Document document = documents.get(result.index());
			Double score = result.relevanceScore() != null ? result.relevanceScore().doubleValue() : null;
			ranked.add(new RankingItem(document, score));
		}
		return RankingOutput.builder().ranked(ranked).build();
	}
}
