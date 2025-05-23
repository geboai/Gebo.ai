/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;

/**
 * Gebo.ai comment agent
 * Interface for evaluating and dispatching content based on certain policies.
 * Provides methods to evaluate content handshakes, stream content based on
 * specific conditions, and check if content is processable.
 */
public interface IGContentDispatchingEvaluator {

    /**
     * Enumeration to define various policies for sending evaluation.
     * - TIMESTAMP_HASH_POLICY: Evaluates based on timestamp and hash values.
     * - SEND_ALWAYS: Always sends the content without condition.
     * - SEND_SUCCESSFULLY_ONCE: Sends the content only if not previously sent successfully.
     */
    public static enum SendEvaluationPolicy {
        TIMESTAMP_HASH_POLICY, SEND_ALWAYS, SEND_SUCCESSFULLY_ONCE
    }

    /**
     * Evaluates the content handshake.
     * 
     * @param handshake The content embedding handshake payload.
     */
    public void evaluateContentHandshake(GContentEmbeddingHandshakePayload handshake);

    /**
     * Streams abstract content message fragments based on the specified policy, document reference,
     * and handler information.
     * 
     * @param policy The policy dictating how content is to be streamed.
     * @param reference The document reference to be used in streaming.
     * @param handler The content management system handler.
     * @param handlerCache Cache supporting the handler operations.
     * @return A stream of abstract content message fragment payloads.
     * @throws GeboIngestionException If an error occurs during content ingestion.
     */
    public Stream<GAbstractContentMessageFragmentPayload> stream(SendEvaluationPolicy policy,
            GDocumentReference reference, IGContentManagementSystemHandler handler, Map<String, Object> handlerCache)
            throws GeboIngestionException;

    /**
     * Determines if content is processable given a particular evaluation policy.
     * 
     * @param evaluationPolicy The policy for evaluating content processability.
     * @param docref The reference to the document in question.
     * @param handler The content management system handler managing the document.
     * @param handlerCache Cache supporting the handler operations.
     * @return True if the content is processable, false otherwise.
     */
    public boolean isProcessable(SendEvaluationPolicy evaluationPolicy, GDocumentReference docref,
            IGContentManagementSystemHandler handler,
            Map<String, Object> handlerCache);

}