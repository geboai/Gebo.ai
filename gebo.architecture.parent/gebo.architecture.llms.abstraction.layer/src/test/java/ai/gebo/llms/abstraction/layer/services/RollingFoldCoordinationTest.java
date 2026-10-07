/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.FoldOutcome;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LaneBudget;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.RollingFold;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

/**
 * Pins the rolling fold of the deep analyses: the documents are analysed in lanes, each
 * a consolidation chain handing its consolidation over when its analysis is satisfying,
 * when no room is left for a batch, or when the documents end; the hand-overs are folded
 * into a running report as they come, and a report judged enough stops the analyses not
 * started yet once the minimum of analysed batches is reached.
 */
class RollingFoldCoordinationTest {

	private final List<String> analysedDocuments = Collections.synchronizedList(new ArrayList<>());
	private final List<String> unprocessed = Collections.synchronizedList(new ArrayList<>());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ReactiveIdentityUtil runningInPlace() {
		ReactiveIdentityUtil runAs = mock(ReactiveIdentityUtil.class);
		when(runAs.doRunAsWithReturn(any())).thenAnswer(invocation -> ((RunAsWithReturn) invocation.getArgument(0)).apply());
		when(runAs.wrap(any(Scheduler.class))).thenAnswer(invocation -> invocation.getArgument(0));
		return runAs;
	}

	/**
	 * One document per batch, each analysed in {@code millis}: an analysis gives the
	 * consolidation it received followed by its own, as the analysis prompt combines them.
	 */
	private GenerativeFunction<String, String> analysis(long millis) {
		return (consolidation, emitter, documents) -> {
			Thread.sleep(millis);
			analysedDocuments.addAll(documents);
			return consolidation + "A(" + String.join(",", documents) + ")";
		};
	}

	/** Every analysis satisfying: each hands over, one checkpoint per analysis. */
	private String run(int documents, GenerativeFunction<String, String> analysis, RollingFold<String> fold,
			int minimumAnalysedBatches) {
		return run(documents, analysis, fold, minimumAnalysedBatches, 100_000, 1, text -> true,
				new LaneBudget<>(consolidation -> 1, 0));
	}

	/**
	 * A batch is as many documents as its budget; each checkpoint and the report weigh
	 * their length, the report taking its weight out of the fold budget.
	 */
	private String run(int documents, GenerativeFunction<String, String> analysis, RollingFold<String> fold,
			int minimumAnalysedBatches, long foldTokensBudget, int lanes, Predicate<String> laneSatisfied,
			LaneBudget<String> laneBudget) {
		List<String> source = new ArrayList<>();
		for (int i = 0; i < documents; i++) {
			source.add("d" + i);
		}
		Predicate<String> outOfBand = value -> value == null || value.equals("ERR");
		Flux<String> result = TokensBudgetFluxCoordinator.tokenBudgetCoordinateWithRollingFold(
				Flux.fromIterable(source), IGProgressNotifier.NONE, document -> true,
				(list, budget) -> list.size() >= Math.max(1, budget), analysis, fold,
				reports -> String.join("+", reports), String::length, report -> foldTokensBudget - report.length(), "",
				"ERR", outOfBand, text -> text, text -> Flux.just(text), Flux.just("EMPTY"), laneBudget,
				runningInPlace(), lanes, minimumAnalysedBatches, laneSatisfied, unprocessed::add);
		return String.join("", result.collectList().block(Duration.ofSeconds(30)));
	}

	@Test
	void aReportJudgedEnoughStopsTheBatchesNotStarted() {
		AtomicInteger folds = new AtomicInteger();
		RollingFold<String> fold = (report, partials, emitter) -> {
			folds.incrementAndGet();
			return new FoldOutcome<>(report + "[" + String.join(",", partials) + "]", true);
		};

		String report = run(12, analysis(100), fold, 1);

		assertTrue(report.startsWith("[A(d0),A(d1)"), report);
		assertTrue(analysedDocuments.size() < 12, "stopped after " + analysedDocuments.size() + " analysed");
		assertEquals(12, analysedDocuments.size() + unprocessed.size(), "every document analysed or left unprocessed");
		assertTrue(!unprocessed.isEmpty());
	}

	@Test
	void aReportNeverJudgedEnoughFoldsEveryAnalysis() {
		AtomicInteger folds = new AtomicInteger();
		RollingFold<String> fold = (report, partials, emitter) -> {
			folds.incrementAndGet();
			return new FoldOutcome<>(report + "[" + String.join(",", partials) + "]", false);
		};

		String report = run(5, analysis(10), fold, 1);

		assertEquals(5, analysedDocuments.size());
		assertTrue(unprocessed.isEmpty());
		assertTrue(folds.get() >= 1 && folds.get() <= 4, folds.get() + " fold(s)");
		for (int i = 0; i < 5; i++) {
			assertTrue(report.contains("A(d" + i + ")"), report);
		}
	}

	@Test
	void theMinimumOfAnalysedBatchesComesBeforeAnyStop() {
		RollingFold<String> fold = (report, partials, emitter) -> new FoldOutcome<>(
				report + "[" + String.join(",", partials) + "]", true);

		run(12, analysis(100), fold, 6);

		assertTrue(analysedDocuments.size() >= 6, "analysed " + analysedDocuments.size());
	}

	@Test
	void aSingleAnalysisIsTheReportAndNoAnalysisGivesTheEmptyResult() {
		AtomicInteger folds = new AtomicInteger();
		RollingFold<String> fold = (report, partials, emitter) -> {
			folds.incrementAndGet();
			return new FoldOutcome<>(report, false);
		};

		assertEquals("A(d0)", run(1, analysis(1), fold, 1));
		assertEquals(0, folds.get(), "a lone analysis needs no fold");
		assertEquals("EMPTY", run(0, analysis(1), fold, 1));
	}

	@Test
	void analysesComingFasterThanTheFoldsAreFoldedTogether() {
		AtomicInteger folds = new AtomicInteger();
		RollingFold<String> fold = (report, partials, emitter) -> {
			folds.incrementAndGet();
			Thread.sleep(200);
			return new FoldOutcome<>(report + "[" + String.join(",", partials) + "]", false);
		};

		String report = run(10, analysis(1), fold, 1);

		assertTrue(folds.get() <= 4, "10 quick analyses in " + folds.get() + " fold(s)");
		for (int i = 0; i < 10; i++) {
			assertTrue(report.contains("A(d" + i + ")"), report);
		}
	}

	@Test
	void everyFoldHoldsInItsTokensBudget() {
		List<String> overBudget = Collections.synchronizedList(new ArrayList<>());
		RollingFold<String> fold = (report, partials, emitter) -> {
			int weight = report.length() + partials.stream().mapToInt(String::length).sum();
			if (weight > 30 && partials.size() > 1) {
				overBudget.add(report + partials);
			}
			// the report stays short: it is what the consolidation keeps of the analyses
			return new FoldOutcome<>("R" + partials.size(), false);
		};

		run(8, analysis(1), fold, 1, 30, 1, text -> true, new LaneBudget<>(consolidation -> 1, 0));

		assertEquals(8, analysedDocuments.size());
		assertTrue(overBudget.isEmpty(), "folds over their budget: " + overBudget);
	}

	@Test
	void noFoldBeforeTheMinimumOfAnalysedBatches() {
		AtomicInteger analysedAtFirstFold = new AtomicInteger(-1);
		RollingFold<String> fold = (report, partials, emitter) -> {
			analysedAtFirstFold.compareAndSet(-1, analysedDocuments.size());
			return new FoldOutcome<>(report + "[" + String.join(",", partials) + "]", false);
		};

		run(8, analysis(50), fold, 4);

		assertTrue(analysedAtFirstFold.get() >= 4, "first fold after " + analysedAtFirstFold.get() + " analyses");
	}

	@Test
	void aFailingFoldKeepsEveryAnalysis() {
		RollingFold<String> fold = (report, partials, emitter) -> {
			throw new IllegalStateException("model down");
		};

		String report = run(3, analysis(1), fold, 1);

		for (int i = 0; i < 3; i++) {
			assertTrue(report.contains("A(d" + i + ")"), report);
		}
	}

	@Test
	void theDocumentsOfAFailingOrOutOfBandBatchAreLeftUnprocessed() {
		GenerativeFunction<String, String> failing = (initial, emitter, documents) -> {
			if (documents.contains("d1")) {
				throw new IllegalStateException("stream timed out");
			}
			if (documents.contains("d2")) {
				return "ERR";
			}
			analysedDocuments.addAll(documents);
			return "A(" + String.join(",", documents) + ")";
		};
		RollingFold<String> fold = (report, partials, emitter) -> new FoldOutcome<>(
				report + "[" + String.join(",", partials) + "]", false);

		String report = run(4, failing, fold, 1);

		assertEquals(List.of("d0", "d3"), analysedDocuments.stream().sorted().toList());
		assertEquals(List.of("d1", "d2"), unprocessed.stream().sorted().toList(), "not analysed");
		assertTrue(report.contains("A(d0)") && report.contains("A(d3)"), report);
	}

	@Test
	void aLaneChainsItsConsolidation() {
		final List<String> received = Collections.synchronizedList(new ArrayList<>());
		GenerativeFunction<String, String> chained = (consolidation, emitter, documents) -> {
			received.add(consolidation);
			analysedDocuments.addAll(documents);
			return consolidation + "A(" + String.join(",", documents) + ")";
		};
		RollingFold<String> fold = (report, partials, emitter) -> new FoldOutcome<>(report + "[" + partials + "]",
				false);

		String report = run(3, chained, fold, 1, 100_000, 1, text -> false, new LaneBudget<>(consolidation -> 1, 0));

		assertEquals(List.of("", "A(d0)", "A(d0)A(d1)"), received, "each analysis gets the consolidation before it");
		assertEquals("A(d0)A(d1)A(d2)", report, "one chain, handed over when the documents end");
	}

	@Test
	void aLaneHandsOverWhenItsConsolidationLeavesNoRoom() {
		final List<String> received = Collections.synchronizedList(new ArrayList<>());
		GenerativeFunction<String, String> chained = (consolidation, emitter, documents) -> {
			received.add(consolidation);
			analysedDocuments.addAll(documents);
			return consolidation + "A(" + String.join(",", documents) + ")";
		};
		RollingFold<String> fold = (report, partials, emitter) -> new FoldOutcome<>(
				report + "[" + String.join(",", partials) + "]", false);
		// a batch is as many documents as the budget: 3 less a fifth of the consolidation's weight
		LaneBudget<String> budget = new LaneBudget<>(consolidation -> 3 - consolidation.length() / 5, 2);

		String report = run(6, chained, fold, 1, 100_000, 1, text -> false, budget);

		assertEquals(6, analysedDocuments.size());
		for (String consolidation : received) {
			assertTrue(3 - consolidation.length() / 5 >= 2, "no analysis starts without room: " + consolidation);
		}
		assertTrue(received.stream().filter(String::isEmpty).count() > 1, "a new chain after each hand-over");
		for (int i = 0; i < 6; i++) {
			assertTrue(report.contains("d" + i), report);
		}
	}

	@Test
	void theLanesShareTheDocumentsEachAnalysingItsOwn() {
		RollingFold<String> fold = (report, partials, emitter) -> new FoldOutcome<>(
				report + "[" + String.join(",", partials) + "]", false);

		String report = run(10, analysis(20), fold, 1, 100_000, 3, text -> false,
				new LaneBudget<>(consolidation -> 1, 0));

		assertEquals(10, analysedDocuments.size());
		assertEquals(10, analysedDocuments.stream().distinct().count(), "each document analysed once");
		for (int i = 0; i < 10; i++) {
			assertTrue(report.contains("A(d" + i + ")"), report);
		}
	}
}
