/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload.MessageFragmentType;
import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.system.ingestion.IGDocumentsHashingService;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;
import ai.gebo.systems.abstraction.layer.model.ContentHandshakeData;

/**
 * AI generated comments
 * Implementation of the IGContentDispatchingEvaluator interface.
 * This class evaluates and manages the dispatching of content based on
 * different policies.
 */
@Service
public class GContentDispatchingEvaluatorImpl implements IGContentDispatchingEvaluator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GContentDispatchingEvaluatorImpl.class);

    // Repositories and services to manage the storage and handling of content.
    @Autowired
    ContentHandshakeDataRepository chRepository;

    @Autowired
    IGDocumentReferenceIngestionHandler ingestionHandler;

    @Autowired
    IGDocumentsHashingService hashingService;

    // Service for estimating token counts in documents.
    JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

    /**
     * Default constructor.
     */
    public GContentDispatchingEvaluatorImpl() {

    }

    /**
     * Evaluate and store the content handshake data from a payload.
     *
     * @param handshake The content embedding handshake payload.
     */
    @Override
    public void evaluateContentHandshake(GContentEmbeddingHandshakePayload handshake) {
        chRepository.save(ContentHandshakeData.of(handshake));
    }

    /**
     * Streams content based on the given evaluation policy.
     *
     * @param policy       The streaming policy to apply.
     * @param reference    The document reference.
     * @param handler      The content management system handler.
     * @param handlerCache A cache for handler operations.
     * @return A stream of content message fragments, or null if not processable.
     * @throws GeboIngestionException if ingestion fails.
     */
    @Override
    public Stream<GAbstractContentMessageFragmentPayload> stream(SendEvaluationPolicy policy,
                                                                 GDocumentReference reference,
                                                                 IGContentManagementSystemHandler handler,
                                                                 Map<String, Object> handlerCache)
            throws GeboIngestionException {
        if (LOGGER.isDebugEnabled() && reference != null) {
            LOGGER.debug("Handling stream(...," + reference.getCode() + ",...)");
        }
        boolean sendData = false;
        try {
            switch (policy) {
                case SEND_ALWAYS: {
                    return streamData(reference, handler, handlerCache);
                }

                case SEND_SUCCESSFULLY_ONCE: {
                    long howMany = chRepository.countByContentCodeAndProcessedTrue(reference.getCode());
                    if (howMany <= 0) {
                        return streamData(reference, handler, handlerCache);
                    }
                }
                break;
                case TIMESTAMP_HASH_POLICY: {
                    long howMany = chRepository.countByContentCodeAndProcessedTrue(reference.getCode());
                    if (howMany <= 0) {
                        return streamData(reference, handler, handlerCache);
                    }
                    return streamConsideringTimestampAndHash(reference, handler, handlerCache);
                }

            }
        } catch (Throwable e) {
            throw new GeboIngestionException("Cant' handle streaming for " + reference.getCode(), e);
        } finally {
        }
        return null;
    }

    /**
     * Computes the number of estimated tokens in a given payload and updates it.
     *
     * @param payload The document message fragment payload.
     */
    private void computeTokensCount(GDocumentMessageFragmentPayload payload) {
        int estimatedTokens = 0;
        if (payload.getDocuments() != null) {
            for (Document d : payload.getDocuments()) {
                if (d.isText()) {
                    String text = d.getText();
                    estimatedTokens += tokenCountEstimator.estimate(text);
                } else if (d.getMedia() != null) {
                    // Media-specific estimation could be added here.
                }

            }
        }
        payload.setEstimatedTokens(estimatedTokens);
    }

    /**
     * Internal method for streaming document references into message fragments.
     *
     * @param reference The document reference.
     * @param data      The ingestion handler data.
     * @param hash      The document hash, if applicable.
     * @return A stream of content message fragments.
     */
    private Stream<GAbstractContentMessageFragmentPayload> internalStream(GDocumentReference reference,
                                                                         IngestionHandlerData data, String hash) {
        if (LOGGER.isDebugEnabled() && reference != null) {
            LOGGER.debug("Doing internal streaming of=>" + reference.getCode());
        }
        if (data.isSingleMessageRappresentable()) {
            GDocumentMessageFragmentPayload payload = new GDocumentMessageFragmentPayload();
            payload.setDocumentReference(reference);
            payload.setHashableContent(data.isHashableContent());
            payload.setRequiresEmbeddingHandshake(true);
            payload.setFragmentType(MessageFragmentType.SINGLE_FRAGMENT);
            payload.setDocuments(data.getStream().toList());
            computeTokensCount(payload);
            if (payload.getDocuments().isEmpty()) {
                return Stream.of();
            }
            if (hash != null) {
                payload.setHash(hash);
            }
            if (data.isHashableContent() && hash == null) {
                try {
                    payload.setHash(hashingService.recalculateHash(payload.getDocuments()));
                } catch (NoSuchAlgorithmException | IOException e) {
                    LOGGER.error("Error computing hash", e);
                }
            }
            return Stream.of(payload);
        } else {
            return chunked(data.getStream(), DOCUMENTS_CHUNKS_NR).map(x -> {
                GDocumentMessageFragmentPayload payload = new GDocumentMessageFragmentPayload();
                payload.setDocumentReference(reference);
                payload.setDocuments(x);
                payload.setHashableContent(data.isHashableContent());
                payload.setRequiresEmbeddingHandshake(true);
                payload.setFragmentType(MessageFragmentType.STREAM_FRAGMENT);
                computeTokensCount(payload);
                return payload;
            });
        }
    }

    /**
     * Streams content by considering both timestamp and hash.
     *
     * @param reference   The document reference.
     * @param handler     The content management system handler.
     * @param handlerCache A cache for handler operations.
     * @return A stream of content message fragments, or null if no valid data.
     * @throws GeboIngestionException if ingestion fails.
     * @throws IOException if an I/O error occurs.
     * @throws GeboContentHandlerSystemException if content handling fails.
     * @throws NoSuchAlgorithmException if hash calculation fails.
     */
    private Stream<GAbstractContentMessageFragmentPayload> streamConsideringTimestampAndHash(
            GDocumentReference reference, IGContentManagementSystemHandler handler, Map<String, Object> handlerCache)
            throws GeboIngestionException, IOException, GeboContentHandlerSystemException, NoSuchAlgorithmException {
        IngestionHandlerData data = ingestionHandler.handleContent(reference,
                handler.streamContent(reference, handlerCache));
        if (data.isUnmanagedContent()) {
            reference.setUnmanagedContentType(true);
            return null;
        }
        if (!data.isHashableContent() || reference.getModificationDate() == null) {
            return internalStream(reference, data, null);
        }
        Stream<ContentHandshakeData> feedbacks = chRepository.findByContentCode(reference.getCode());
        TreeMap<Date, List<ContentHandshakeData>> bymodificationDate = new TreeMap<>();
        List<ContentHandshakeData> withoutDate = new ArrayList<>();
        if (feedbacks != null) {
            feedbacks = feedbacks.filter(x -> {
                return x.getProcessed() != null && x.getProcessed();
            });
            feedbacks.forEach(x -> {
                if (x.getModificationDate() != null) {
                    if (!bymodificationDate.containsKey(x.getModificationDate())) {
                        bymodificationDate.put(x.getModificationDate(), new ArrayList<>());
                    }
                    bymodificationDate.get(x.getModificationDate()).add(x);
                } else if (x.getCreationdDate() != null) {
                    if (!bymodificationDate.containsKey(x.getCreationdDate())) {
                        bymodificationDate.put(x.getCreationdDate(), new ArrayList<>());
                    }
                    bymodificationDate.get(x.getCreationdDate()).add(x);
                } else {
                    withoutDate.add(x);
                }
            });
        }
        List<ContentHandshakeData> instances = bymodificationDate.get(reference.getModificationDate());
        if (instances == null) {
            return internalStream(reference, data, null);
        }
        List<Document> forhash = data.getStream().toList();
        String hash = hashingService.recalculateHash(forhash);
        // Replace again stream
        data.setStream(forhash.stream());
        if (!instances.stream().anyMatch(x -> {
            return x.getHash() != null && x.getHash().equals(hash);
        })) {
            return internalStream(reference, data, hash);
        }
        return null;
    }

    // Number of documents to be chunked together while streaming.
    private final static int DOCUMENTS_CHUNKS_NR = 30;

    /**
     * Divides a stream into chunks of specified size.
     *
     * @param stream    the stream to be chunked.
     * @param chunkSize the size of each chunk.
     * @param <T>       the type of elements in the stream.
     * @return a stream of lists, each containing a chunk of the input stream.
     */
    static <T> Stream<List<T>> chunked(Stream<T> stream, int chunkSize) {
        AtomicInteger index = new AtomicInteger(0);

        return stream.collect(Collectors.groupingBy(x -> index.getAndIncrement() / chunkSize)).entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue);
    }

    /**
     * Streams data for a given document reference.
     *
     * @param reference  the reference to the document.
     * @param handler    the content management system handler.
     * @param cache      the cache for the handler.
     * @return a stream of content message fragments, or null if not applicable.
     * @throws GeboIngestionException if ingestion fails.
     * @throws IOException if an I/O error occurs.
     * @throws GeboContentHandlerSystemException if content handling fails.
     * @throws NoSuchAlgorithmException if hash calculation fails.
     */
    private Stream<GAbstractContentMessageFragmentPayload> streamData(GDocumentReference reference,
                                                                      IGContentManagementSystemHandler handler, Map cache)
            throws GeboIngestionException, IOException, GeboContentHandlerSystemException, NoSuchAlgorithmException {
        IngestionHandlerData data = ingestionHandler.handleContent(reference, handler.streamContent(reference, cache));
        if (data.isUnmanagedContent()) {
            reference.setUnmanagedContentType(true);
            return null;
        }

        return internalStream(reference, data, null);
    }

    /**
     * Determines if a given document reference is processable based on the
     * evaluation policy.
     *
     * @param evaluationPolicy the evaluation policy to apply.
     * @param docref           the document reference to check.
     * @param handler          the content management system handler.
     * @param handlerCache     the cache for handler operations.
     * @return true if the document is processable; false otherwise.
     */
    @Override
    public boolean isProcessable(SendEvaluationPolicy evaluationPolicy, GDocumentReference docref,
                                 IGContentManagementSystemHandler handler, Map<String, Object> handlerCache) {

        return ingestionHandler.isProcessable(docref);
    }

}