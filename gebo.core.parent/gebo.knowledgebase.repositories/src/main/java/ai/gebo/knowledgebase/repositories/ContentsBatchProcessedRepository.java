/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knowledgebase.repositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessedSummary;

/**
 * AI generated comments Repository interface for accessing and performing
 * operations on the 'ContentsBatchProcessed' collection in the MongoDB.
 */
public interface ContentsBatchProcessedRepository extends MongoRepository<ContentsBatchProcessed, String> {

	@Aggregation(pipeline = { "{ $match: { jobId: ?0 } }", "{ $group: { "
			+ "  _id: { workflowType: '$workflowType', workflowId: '$workflowId', workflowStepId: '$workflowStepId' }, "
			+ "  jobId: { $first: '$jobId' }, " + "  batchDocumentsInput: { $sum: '$batchDocumentsInput' }, "
			+ "  batchSentToNextStep: { $sum: '$batchSentToNextStep' }, "
			+ "  batchDiscardedInput: { $sum: '$batchDiscardedInput' },"
			+ "  chunksProcessed: { $sum: '$chunksProcessed' }, " + "  tokensProcessed: { $sum: '$tokensProcessed' }, "
			+ "  errorChunks:{ $sum: '$errorChunks' },  errorTokens: { $sum: '$errorTokens' },"
			+ "  batchDocumentsProcessingErrors: { $sum: '$batchDocumentsProcessingErrors' }, "
			+ "  batchDocumentsProcessed: { $sum: '$batchDocumentsProcessed' }, "
			+ "  lastMessageAny: { $sum: { $cond: [ { $eq: ['$lastMessage', true] }, 1, 0 ] } }, "
			+ "  timestampMin: { $min: '$timestamp' }, " + "  timestampMax: { $max: '$timestamp' } " + "} }",
			"{ $project: { " + "  _id: 0, " + "  jobId: 1, " + "  workflowType: '$_id.workflowType', "
					+ "  workflowId: '$_id.workflowId', " + "  workflowStepId: '$_id.workflowStepId', "
					+ "  lastMessage: { $gt: ['$lastMessageAny', 0] }, " + "  batchDocumentsInput: 1, "
					+ "  batchSentToNextStep: 1, batchDiscardedInput: 1, " + "  chunksProcessed: 1, "
					+ "  tokensProcessed: 1, errorChunks:1,  errorTokens: 1," + "  batchDocumentsProcessingErrors: 1, "
					+ "  batchDocumentsProcessed: 1, " + "  timestampMin: 1, " + "  timestampMax: 1 " + "} }",
			"{ $sort: { workflowType: 1, workflowId: 1, workflowStepId: 1 } }" })
	public List<ContentsBatchProcessedSummary> aggregateByJobId(String jobId);

	/**
	 * Retrieves a stream of 'ContentsBatchProcessed' objects filtered by the
	 * specified job ID.
	 *
	 * @param code The job ID to filter the 'ContentsBatchProcessed' entries.
	 * @return A stream of 'ContentsBatchProcessed' objects matching the given job
	 *         ID.
	 */
	public Stream<ContentsBatchProcessed> findByJobId(String code);

}