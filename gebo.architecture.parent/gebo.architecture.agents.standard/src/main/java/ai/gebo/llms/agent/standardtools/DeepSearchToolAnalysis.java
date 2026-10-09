/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Vector;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.deepsearch.service.DeepSearchAnalysisOutcome;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchAnalysis;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchRelevance;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * The analysis of the deep search tools: the deep search pipelines' one
 * ({@link DeepSearchAnalysis}), the question the fragments are analysed against being
 * the agent's, carried by the given request context. A tool has no pipeline to notify:
 * the agent reads what the consolidation reports as missing and the fragments left
 * unread (its coverage). What the partial analyses find relevant is told in the logs:
 * the agent chooses the documents its answer rests on.
 */
@Service
public class DeepSearchToolAnalysis {
	private final DeepSearchAnalysis analysis;

	public DeepSearchToolAnalysis(DeepSearchAnalysis analysis) {
		this.analysis = analysis;
	}

	/**
	 * Analyses the fragments against the question of the context, sized for the
	 * given deliverable.
	 *
	 * @param completenessNote     added to what the deliverable asks, e.g. who reads
	 *                             the analysis
	 * @param chatModel            writes the final analysis
	 * @param serviceModel         writes the partial analyses
	 * @param discardedFragmentIds receives the fragments left unprocessed
	 * @param notifier             tells the user the progress of the analysis
	 *                             ({@link IGProgressNotifier#NONE} for none)
	 * @return the final analysis, streamed
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier) {
		return analyze(fragments, context, runAs, deliverable, completenessNote, chatModel, serviceModel,
				discardedFragmentIds, notifier, null);
	}

	/**
	 * The same, handing out what the consolidation reports as missing.
	 *
	 * @param outcome receives what the last consolidation of the partial analyses
	 *                reports as missing (its verdict), null when it reports the report
	 *                complete, left untouched when no consolidation runs (a single
	 *                batch of fragments, no sufficiency check); and the fragments left
	 *                unread. May be null
	 */
	public Flux<String> analyze(Flux<Document> fragments, IChatRequestContext context, ReactiveIdentityUtil runAs,
			DeliverableIntent deliverable, String completenessNote, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Vector<String> discardedFragmentIds, IGProgressNotifier notifier,
			DeepSearchAnalysisOutcome outcome) {
		// the quotations of this analysis: the outcome's when given, so the tool can list them
		final DeepSearchQuotations quotations = outcome != null ? outcome.getQuotations()
				: new DeepSearchQuotations();
		final Flux<String> analysed = analysis.analyze(fragments, context, runAs, deliverable, completenessNote,
				chatModel, serviceModel, discardedFragmentIds, notifier, quotations, new DeepSearchRelevance(), outcome,
				null, "Deep search tool");
		// the quotations the standard way, with no fragment id
		return quotations.render(analysed).subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
	}
}
