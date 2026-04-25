package ai.gebo.ranker.standard.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.ai.document.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import lombok.Builder;
import lombok.Getter;

public class GeboStandardRankerClient implements RankerModel {

    private final String serviceUrl;
    private final String model;

    private final RestClient restClient;
    private final RetryTemplate retryTemplate;

    private final int modelContextWindowTokens;
    private final int maxDocumentsPerRequest;
    private final int maxDocumentTokens;
    private final int responseReserveTokens;

    @Builder
    public GeboStandardRankerClient(
            String apiKey,
            String serviceUrl,
            String model,

            RestClient.Builder restClientBuilder,
            RetryTemplate retryTemplate,

            Integer modelContextWindowTokens,
            Integer maxDocumentsPerRequest,
            Integer maxDocumentTokens,
            Integer responseReserveTokens
    ) {
        Objects.requireNonNull(apiKey, "apiKey is required");

        this.serviceUrl = normalizeServiceUrl(Objects.requireNonNull(serviceUrl, "serviceUrl is required"));
        this.model = Objects.requireNonNull(model, "model is required");

        RestClient.Builder builder = restClientBuilder != null
                ? restClientBuilder
                : RestClient.builder();

        this.restClient = builder
                .baseUrl(this.serviceUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.retryTemplate = Objects.requireNonNull(retryTemplate, "retryTemplate is required");

        this.modelContextWindowTokens = modelContextWindowTokens != null ? modelContextWindowTokens : 8192;
        this.maxDocumentsPerRequest = maxDocumentsPerRequest != null ? maxDocumentsPerRequest : 96;
        this.maxDocumentTokens = maxDocumentTokens != null ? maxDocumentTokens : 1024;
        this.responseReserveTokens = responseReserveTokens != null ? responseReserveTokens : 512;
    }

    @Override
    public RankingOutput call(RankingInput input) {
        validateInput(input);

        String query = input.getQuery();
        List<Document> documents = input.getDocuments();

        if (documents.isEmpty()) {
            return RankingOutput.builder()
                    .ranked(List.of())
                    .build();
        }

        int requestedTopK = input.getTopK() != null && input.getTopK() > 0
                ? input.getTopK()
                : documents.size();

        List<DocumentBatchItem> preparedDocuments = prepareDocuments(documents);
        List<List<DocumentBatchItem>> batches = createBatches(query, preparedDocuments);

        List<RankingOutput.RankingItem> allRanked = new ArrayList<>();

        for (List<DocumentBatchItem> batch : batches) {
            StandardRerankRequest request = toRequest(query, batch, requestedTopK);

            StandardRerankResponse response = retryTemplate.execute(context -> execute(request));

            if (response == null || response.getResults() == null) {
                continue;
            }

            for (StandardRerankResponse.Result result : response.getResults()) {
                int batchIndex = result.getIndex();

                if (batchIndex < 0 || batchIndex >= batch.size()) {
                    continue;
                }

                Document originalDocument = batch.get(batchIndex).originalDocument();

                allRanked.add(new RankingOutput.RankingItem(
                        originalDocument,
                        result.getRelevanceScore()
                ));
            }
        }

        List<RankingOutput.RankingItem> ranked = allRanked.stream()
                .filter(item -> item.getRanking() != null)
                .sorted(Comparator.comparing(RankingOutput.RankingItem::getRanking).reversed())
                .limit(requestedTopK)
                .toList();

        return RankingOutput.builder()
                .ranked(ranked)
                .build();
    }

    private StandardRerankResponse execute(StandardRerankRequest request) {
        return restClient
                .post()
                .body(request)
                .retrieve()
                .body(StandardRerankResponse.class);
    }

    private void validateInput(RankingInput input) {
        if (input == null) {
            throw new IllegalArgumentException("RankingInput cannot be null");
        }

        if (input.getQuery() == null || input.getQuery().isBlank()) {
            throw new IllegalArgumentException("RankingInput.query is required for reranking");
        }

        if (input.getDocuments() == null) {
            throw new IllegalArgumentException("RankingInput.documents cannot be null");
        }
    }

    private List<DocumentBatchItem> prepareDocuments(List<Document> documents) {
        List<DocumentBatchItem> prepared = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            Document document = documents.get(i);

            if (document == null) {
                continue;
            }

            String text = extractText(document);

            if (text == null || text.isBlank()) {
                continue;
            }

            String truncated = truncateToEstimatedTokens(text, maxDocumentTokens);

            prepared.add(new DocumentBatchItem(
                    i,
                    document,
                    truncated,
                    estimateTokens(truncated)
            ));
        }

        return prepared;
    }

    private List<List<DocumentBatchItem>> createBatches(
            String query,
            List<DocumentBatchItem> documents
    ) {
        List<List<DocumentBatchItem>> batches = new ArrayList<>();

        int queryTokens = estimateTokens(query);
        int availableTokens = modelContextWindowTokens - queryTokens - responseReserveTokens;

        if (availableTokens <= 0) {
            throw new IllegalArgumentException(
                    "Query is too large for ranker model context window. Estimated query tokens=" + queryTokens
            );
        }

        List<DocumentBatchItem> currentBatch = new ArrayList<>();
        int currentTokens = 0;

        for (DocumentBatchItem item : documents) {
            boolean exceedsTokenBudget = currentTokens + item.estimatedTokens() > availableTokens;
            boolean exceedsDocumentBudget = currentBatch.size() >= maxDocumentsPerRequest;

            if (!currentBatch.isEmpty() && (exceedsTokenBudget || exceedsDocumentBudget)) {
                batches.add(currentBatch);
                currentBatch = new ArrayList<>();
                currentTokens = 0;
            }

            if (item.estimatedTokens() > availableTokens) {
                String truncated = truncateToEstimatedTokens(item.text(), availableTokens);

                DocumentBatchItem reduced = new DocumentBatchItem(
                        item.originalIndex(),
                        item.originalDocument(),
                        truncated,
                        estimateTokens(truncated)
                );

                currentBatch.add(reduced);
                batches.add(currentBatch);

                currentBatch = new ArrayList<>();
                currentTokens = 0;
            } else {
                currentBatch.add(item);
                currentTokens += item.estimatedTokens();
            }
        }

        if (!currentBatch.isEmpty()) {
            batches.add(currentBatch);
        }

        return batches;
    }

    private StandardRerankRequest toRequest(
            String query,
            List<DocumentBatchItem> batch,
            int topK
    ) {
        List<String> documents = batch.stream()
                .map(DocumentBatchItem::text)
                .toList();

        return StandardRerankRequest.builder()
                .model(model)
                .query(query)
                .documents(documents)
                .topN(Math.min(topK, documents.size()))
                .returnDocuments(false)
                .build();
    }

    private String extractText(Document document) {
        return document.getText();
    }

    private int estimateTokens(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }

        return Math.max(1, (int) Math.ceil(text.length() / 4.0));
    }

    private String truncateToEstimatedTokens(String text, int maxTokens) {
        if (text == null) {
            return null;
        }

        int maxChars = Math.max(1, maxTokens * 4);

        if (text.length() <= maxChars) {
            return text;
        }

        return text.substring(0, maxChars);
    }

    private static String normalizeServiceUrl(String serviceUrl) {
        String trimmed = serviceUrl.trim();

        if (trimmed.endsWith("/")) {
            return trimmed.substring(0, trimmed.length() - 1);
        }

        return trimmed;
    }

    private record DocumentBatchItem(
            int originalIndex,
            Document originalDocument,
            String text,
            int estimatedTokens
    ) {
    }

    @Getter
    @Builder
    private static class StandardRerankRequest {

        private String model;

        private String query;

        private List<String> documents;

        @JsonProperty("top_n")
        private Integer topN;

        @JsonProperty("return_documents")
        private Boolean returnDocuments;
    }

    @Getter
    public static class StandardRerankResponse {

        private List<Result> results;

        @Getter
        public static class Result {

            private Integer index;

            @JsonProperty("relevance_score")
            private Double relevanceScore;
        }
    }
}