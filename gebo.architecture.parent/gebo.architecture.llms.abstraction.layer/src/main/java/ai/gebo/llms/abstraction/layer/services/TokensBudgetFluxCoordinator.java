package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.ToLongFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;

/**
 * Token budgeted map/reduce over a stream of documents: the documents are grouped in
 * batches fitting a tokens budget, each batch is analysed in parallel (map), the
 * analyses are then reduced into the final result. Progress goes to an
 * {@link IGProgressNotifier}, so the same coordination serves the chat pipelines,
 * the agents and the tools.
 */
public class TokensBudgetFluxCoordinator {
	private static final String EXCEPTION_IN_FLAT_MAP = "Exception in flatMap(...)";
	private static final String EXCEPTION_IN_MAP_PROCESS = "Exception in map(..) process";
	private static final Logger LOGGER = LoggerFactory.getLogger(TokensBudgetFluxCoordinator.class);

	@FunctionalInterface
	public static interface LastWork<X, Y> {
		Flux<Y> iterateCumulation(List<X> finalFunction, IGProgressNotifier emitter) throws Exception;
	}

	@FunctionalInterface
	public static interface GenerativeFunction<D, X> {
		X iterateCumulation(X x, IGProgressNotifier emitter, List<D> documents) throws Exception;
	}

	@FunctionalInterface
	public static interface TokensLimitCompute<D> {
		boolean higherThanBudgetTokens(List<D> d, long budget);
	}

	public static <D, T, Y> Flux<Y> tokenBudgetCoordinateAlreadySplitted(Flux<D> source, IGProgressNotifier emitter,
			Predicate<D> validDocumentCheck, GenerativeFunction<D, T> generative, LastWork<T, Y> finalWork,
			T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue, Y finalOutOFBoundValue,
			Predicate<Y> isFinalOutOFBoundValue, Predicate<T> isEndOfProcessingCondition,
			Function<T, T> outputCleaningFunction, Function<T, Flux<Y>> streamingFunction, ReactiveIdentityUtil runAs,
			int parallelism, Consumer<D> unprocessedCumulator) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinate(..) ");
		}
		final AtomicBoolean endOfProcessing = new AtomicBoolean(false);
		Flux<List<T>> flux = source.parallel(parallelism).runOn(runAs.wrap(Schedulers.boundedElastic())).map(input -> {
			return runAs.doRunAsWithReturn(() -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin map(...) code with 1  batch of documents");
				}
				if (endOfProcessing.get()) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Shortcutting process in map(...)");
					}
					if (input != null) {
						unprocessedCumulator.accept(input);
					}
					return null;
				}
				try {
					try {
						emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzing 1 batch of documents");
					} catch (Throwable th) {
						LOGGER.error("Error notifying user about documents analysis start", th);
					}
					T result = generative.iterateCumulation(initialValue, emitter, List.of(input));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("End map(...) code with 1 batch of documents returning:" + result);
					}
					try {
						emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzing 1 batch of documents!");
					} catch (Throwable th) {
						LOGGER.error("Error notifying user about documents analysis completion", th);
					}
					if (isEndOfProcessingCondition != null && isEndOfProcessingCondition.test(result)) {
						endOfProcessing.set(true);
					}
					return outputCleaningFunction.apply(result);
				} catch (Throwable th) {
					emitter.notifyLLMProblems();
					LOGGER.error(EXCEPTION_IN_MAP_PROCESS, th);
					return outOfBandValue;
				}
			});
		}).filter(V -> V != null && !isOutOfBandValue.test(V)).sequential().buffer();
		Flux<Y> finalFlux = flux.flatMap(IntermediateResult -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Begin flatMap(...) code with " + IntermediateResult.size() + " input elements");
					}
					Flux<Y> finalResult = null;
					if (IntermediateResult.size() == 1) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Returning shortcutted value");
						}

						finalResult = streamingFunction.apply(IntermediateResult.get(0));
					} else {
						try {
							emitter.notifyProgress(UUID.randomUUID().toString(), "Aggregating " + IntermediateResult.size() + " analisys");
						} catch (Throwable th) {
							LOGGER.error("Error notifying user about analisys aggregation start", th);
						}
						finalResult = finalWork.iterateCumulation(IntermediateResult, emitter);
						try {
							emitter.notifyProgress(UUID.randomUUID().toString(), "Aggregated " + IntermediateResult.size() + " analisys!");
						} catch (Throwable th) {
							LOGGER.error("Error notifying user about analisys aggregation completion", th);
						}
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("End flatMap(...) code");
					}
					return finalResult;
				} catch (Throwable th) {
					emitter.notifyLLMProblems();
					LOGGER.error(EXCEPTION_IN_FLAT_MAP, th);
					return Flux.just(finalOutOFBoundValue);
				}
			}).subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End tokenBudgetCoordinate(..) ");
		}
		return finalFlux;
	}

	public static <D, T, Y> Flux<Y> tokenBudgetCoordinate(Flux<D> source, IGProgressNotifier emitter,
			Predicate<D> validDocumentCheck, TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative,
			LastWork<T, Y> finalWork, T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue,
			Y finalOutOFBoundValue, Predicate<Y> isFinalOutOFBoundValue, Predicate<T> isEndOfProcessingCondition,
			Function<T, T> outputCleaningFunction, Function<T, Flux<Y>> streamingFunction, long tokensBudget,
			ReactiveIdentityUtil runAs, int parallelism, Consumer<D> unprocessedCumulator) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinate(..) ");
		}
		final AtomicBoolean endOfProcessing = new AtomicBoolean(false);

		Flux<List<T>> flux = analysedBatches(source, emitter, validDocumentCheck, tokensCompute, generative,
				initialValue, outOfBandValue, isOutOfBandValue, isEndOfProcessingCondition, outputCleaningFunction,
				tokensBudget, runAs, parallelism, unprocessedCumulator, endOfProcessing, null).buffer();
		Flux<Y> finalFlux = flux.flatMap(IntermediateResult -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Begin flatMap(...) code with " + IntermediateResult.size() + " input elements");
					}
					Flux<Y> finalResult = null;
					if (IntermediateResult.size() == 1) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Returning shortcutted value");
						}

						finalResult = streamingFunction.apply(IntermediateResult.get(0));
					} else {
						try {
							emitter.notifyProgress(UUID.randomUUID().toString(), "Aggregating " + IntermediateResult.size() + " analisys");
						} catch (Throwable th) {
							LOGGER.error("Error notifying user about analisys aggregation start", th);
						}
						finalResult = finalWork.iterateCumulation(IntermediateResult, emitter);
						try {
							emitter.notifyProgress(UUID.randomUUID().toString(), "Aggregated " + IntermediateResult.size() + " analisys!");
						} catch (Throwable th) {
							LOGGER.error("Error notifying user about analisys aggregation completion", th);
						}
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("End flatMap(...) code");
					}
					return finalResult;
				} catch (Throwable th) {
					emitter.notifyLLMProblems();
					LOGGER.error(EXCEPTION_IN_FLAT_MAP, th);
					return Flux.just(finalOutOFBoundValue);
				}
			}).subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End tokenBudgetCoordinate(..) ");
		}
		return finalFlux;
	}

	/**
	 * The map stage: the documents grouped in batches fitting the tokens budget, each
	 * batch analysed in parallel; once {@code endOfProcessing} is set, the batches not
	 * started yet are not analysed (their documents go to the unprocessed cumulator).
	 * Returns the analyses, out-of-band ones dropped, in completion order.
	 */
	private static <D, T> Flux<T> analysedBatches(Flux<D> source, IGProgressNotifier emitter,
			Predicate<D> validDocumentCheck, TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative,
			T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue, Predicate<T> isEndOfProcessingCondition,
			Function<T, T> outputCleaningFunction, long tokensBudget, ReactiveIdentityUtil runAs, int parallelism,
			Consumer<D> unprocessedCumulator, AtomicBoolean endOfProcessing, Runnable onAnalysed) {
		return emitQueueWhenPredicateTrue(source.filter(validDocumentCheck),
				list -> tokensCompute.higherThanBudgetTokens(list, tokensBudget)).parallel(parallelism)
				.runOn(runAs.wrap(Schedulers.boundedElastic())).map(input -> {
					return runAs.doRunAsWithReturn(() -> {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Begin map(...) code with " + input.size() + " elements");
						}
						if (endOfProcessing.get()) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Shortcutting process in map(...)");
							}
							if (input != null) {
								input.forEach(unprocessedCumulator);
							}
							// Reactor's map() forbids null: return the out-of-band value, which is
							// dropped downstream by the isOutOfBandValue filter (see .filter below).
							return outOfBandValue;
						}
						try {
							try {
								emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzing " + input.size() + " documents");
							} catch (Throwable th) {
								LOGGER.error("Error notifying user about documents analysis start", th);
							}
							T result = generative.iterateCumulation(initialValue, emitter, input);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("End map(...) code with " + input.size() + " returning:" + result);
							}
							try {
								emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzed " + input.size() + " documents!");
							} catch (Throwable th) {
								LOGGER.error("Error notifying user about documents analysis completion", th);
							}
							if (isEndOfProcessingCondition != null && isEndOfProcessingCondition.test(result)) {
								endOfProcessing.set(true);
							}
							if (onAnalysed != null) {
								onAnalysed.run();
							}
							return outputCleaningFunction.apply(result);
						} catch (Throwable th) {
							emitter.notifyLLMProblems();
							LOGGER.error(EXCEPTION_IN_MAP_PROCESS, th);
							return outOfBandValue;
						}
					});
			}).filter(V -> V != null && !isOutOfBandValue.test(V)).sequential();
	}

	/** The outcome of folding partial analyses into the report: the new report and its verdict. */
	public static record FoldOutcome<T>(T report, boolean complete) {
	}

	@FunctionalInterface
	public static interface RollingFold<T> {
		/**
		 * The report with the partial analyses folded in, and whether the report is now
		 * enough to answer (the verdict of the model that wrote it).
		 */
		FoldOutcome<T> fold(T report, List<T> partials, IGProgressNotifier emitter) throws Exception;
	}

	/**
	 * Token budgeted map with a rolling reduce: the batches are analysed in parallel as
	 * in {@link #tokenBudgetCoordinate}, and the analyses are folded into a running
	 * report while the others are still being analysed. The folding is adaptive: when
	 * the previous fold is over, the next one takes every analysis that arrived in the
	 * meantime, as many as fit {@code foldTokensBudget} with the report (always at least
	 * one), so the folds are few when the analyses come quickly and frequent when they
	 * come slowly. No fold is made before {@code minimumAnalysedBatches} batches are
	 * analysed (it could not stop anything), nor while a first analysis is alone (it may
	 * be the only one: a lone analysis with an empty report is the report itself, no fold
	 * needed), unless the waiting analyses already fill the budget.
	 * <p>
	 * After each fold, a report judged enough ({@link FoldOutcome#complete()}) once the
	 * minimum is reached stops the batches not started yet; the ones already running are
	 * still folded in. The final report is streamed with {@code streamingFunction}; with
	 * no analysis at all, {@code emptyResult} is returned. A failing fold keeps the
	 * report and appends the analyses it could not fold, so no analysis is lost.
	 */
	public static <D, T, Y> Flux<Y> tokenBudgetCoordinateWithRollingFold(Flux<D> source, IGProgressNotifier emitter,
			Predicate<D> validDocumentCheck, TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative,
			RollingFold<T> rollingFold, Function<List<T>, T> appendWhenFoldFails, ToLongFunction<T> tokensOf,
			long foldTokensBudget, T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue,
			Function<T, T> outputCleaningFunction, Function<T, Flux<Y>> streamingFunction, Flux<Y> emptyResult,
			long tokensBudget, ReactiveIdentityUtil runAs, int parallelism, int minimumAnalysedBatches,
			Consumer<D> unprocessedCumulator) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinateWithRollingFold(..) parallelism:" + parallelism
					+ " foldTokensBudget:" + foldTokensBudget + " minimumAnalysedBatches:" + minimumAnalysedBatches);
		}
		final AtomicBoolean endOfProcessing = new AtomicBoolean(false);
		final AtomicInteger analysed = new AtomicInteger(0);
		final Flux<T> partials = analysedBatches(source, emitter, validDocumentCheck, tokensCompute, generative,
				initialValue, outOfBandValue, isOutOfBandValue, null, outputCleaningFunction, tokensBudget, runAs,
				parallelism, unprocessedCumulator, endOfProcessing, analysed::incrementAndGet);
		final Mono<T> folded = Mono.<T>create(sink -> {
			final RollingReport<T> rolling = new RollingReport<>(rollingFold, appendWhenFoldFails, tokensOf,
					foldTokensBudget, initialValue, minimumAnalysedBatches, analysed, endOfProcessing, emitter);
			final Scheduler folding = runAs.wrap(Schedulers.boundedElastic());
			final AtomicInteger wip = new AtomicInteger(0);
			final AtomicBoolean sourceDone = new AtomicBoolean(false);
			final AtomicBoolean completed = new AtomicBoolean(false);
			// one drain at a time: the folds are sequential, each taking what is waiting
			final Runnable drain = () -> runAs.doRunAsWithReturn(() -> {
				int missed = 1;
				while (true) {
					rolling.foldWhatIsDue(sourceDone.get());
					if (sourceDone.get() && rolling.nothingWaiting() && completed.compareAndSet(false, true)) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("End tokenBudgetCoordinateWithRollingFold(..) " + analysed.get()
									+ " batch(es) analysed, " + rolling.folds() + " fold(s)");
						}
						sink.success(rolling.report());
					}
					missed = wip.addAndGet(-missed);
					if (missed == 0) {
						return null;
					}
				}
			});
			final Runnable schedule = () -> {
				if (wip.getAndIncrement() == 0) {
					folding.schedule(drain);
				}
			};
			final Disposable subscription = partials.subscribe(partial -> {
				rolling.arrived(partial);
				schedule.run();
			}, error -> {
				LOGGER.error("The deep analysis stream failed, the report keeps what was folded", error);
				sourceDone.set(true);
				schedule.run();
			}, () -> {
				sourceDone.set(true);
				schedule.run();
			});
			sink.onCancel(subscription);
		});
		return folded.flatMapMany(streamingFunction).switchIfEmpty(Flux.defer(() -> emptyResult));
	}

	/**
	 * The running report of a rolling fold and the analyses waiting to be folded in;
	 * only touched by the single drain of {@link #tokenBudgetCoordinateWithRollingFold},
	 * except {@link #arrived(Object)}.
	 */
	private static final class RollingReport<T> {
		private final RollingFold<T> rollingFold;
		private final Function<List<T>, T> appendWhenFoldFails;
		private final ToLongFunction<T> tokensOf;
		private final long foldTokensBudget;
		private final T initialValue;
		private final int minimumAnalysedBatches;
		private final AtomicInteger analysed;
		private final AtomicBoolean endOfProcessing;
		private final IGProgressNotifier emitter;
		private final ConcurrentLinkedQueue<T> waiting = new ConcurrentLinkedQueue<>();
		private T report = null;
		private int folds = 0;

		RollingReport(RollingFold<T> rollingFold, Function<List<T>, T> appendWhenFoldFails, ToLongFunction<T> tokensOf,
				long foldTokensBudget, T initialValue, int minimumAnalysedBatches, AtomicInteger analysed,
				AtomicBoolean endOfProcessing, IGProgressNotifier emitter) {
			this.rollingFold = rollingFold;
			this.appendWhenFoldFails = appendWhenFoldFails;
			this.tokensOf = tokensOf;
			this.foldTokensBudget = foldTokensBudget;
			this.initialValue = initialValue;
			this.minimumAnalysedBatches = minimumAnalysedBatches;
			this.analysed = analysed;
			this.endOfProcessing = endOfProcessing;
			this.emitter = emitter;
		}

		void arrived(T partial) {
			waiting.add(partial);
		}

		boolean nothingWaiting() {
			return waiting.isEmpty();
		}

		T report() {
			return report;
		}

		int folds() {
			return folds;
		}

		private long tokens(T value) {
			return value != null ? tokensOf.applyAsLong(value) : 0l;
		}

		/** Folds the waiting analyses as long as a fold is due. */
		void foldWhatIsDue(boolean sourceDone) {
			while (!waiting.isEmpty()) {
				if (report == null && sourceDone && waiting.size() == 1) {
					// the only analysis, nothing folded: it is the report
					report = waiting.poll();
					continue;
				}
				final long reportTokens = tokens(report);
				long waitingTokens = 0l;
				for (T partial : waiting) {
					waitingTokens += tokens(partial);
				}
				final boolean roomLeft = reportTokens + waitingTokens < foldTokensBudget;
				if (!sourceDone && roomLeft && (analysed.get() < minimumAnalysedBatches
						|| (report == null && waiting.size() == 1))) {
					// no stop possible yet, or a first analysis alone that may be the only one
					// (then it is the report, no fold needed): wait for more analyses
					return;
				}
				fold(takeGroup(reportTokens));
			}
		}

		/** The waiting analyses that fit the budget with the report, at least one. */
		private List<T> takeGroup(long reportTokens) {
			final List<T> group = new ArrayList<>();
			long used = reportTokens;
			while (!waiting.isEmpty()) {
				final long next = tokens(waiting.peek());
				if (!group.isEmpty() && used + next > foldTokensBudget) {
					break;
				}
				used += next;
				group.add(waiting.poll());
			}
			if (used > foldTokensBudget) {
				LOGGER.warn("A fold of " + group.size() + " analysis(es) and the report takes " + used
						+ " (tok), over its budget of " + foldTokensBudget + " (tok)");
			}
			return group;
		}

		private void fold(List<T> group) {
			try {
				final FoldOutcome<T> outcome = rollingFold.fold(report != null ? report : initialValue, group, emitter);
				report = outcome.report();
				folds++;
				final boolean stop = outcome.complete() && analysed.get() >= minimumAnalysedBatches;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Fold " + folds + " of " + group.size() + " analysis(es), " + analysed.get()
							+ " batch(es) analysed so far, report:" + tokens(report) + " (tok) complete:"
							+ outcome.complete() + " stop:" + stop);
				}
				if (stop && !endOfProcessing.getAndSet(true)) {
					LOGGER.info("Deep analysis judged complete after " + analysed.get()
							+ " analysed batch(es): the batches not started are not analysed");
				}
			} catch (Throwable th) {
				emitter.notifyLLMProblems();
				LOGGER.error("Fold of " + group.size() + " analysis(es) failed: they are appended to the report", th);
				final List<T> kept = new ArrayList<>();
				if (report != null) {
					kept.add(report);
				}
				kept.addAll(group);
				report = appendWhenFoldFails.apply(kept);
			}
		}
	}

	private static <T> Flux<List<T>> emitQueueWhenPredicateTrue(Flux<T> source, Predicate<List<T>> shouldEmit) {
		return Flux.defer(() -> {
			List<T> buffer = new ArrayList<>();

			Flux<List<T>> mainFlux = source.<List<T>>handle((item, sink) -> {
				if (shouldEmit.test(buffer)) {
					sink.next(new ArrayList<>(buffer));
					buffer.clear();
				}
				buffer.add(item);
			});

			Mono<List<T>> tailFlux = Mono.defer(() -> {
				if (!buffer.isEmpty()) {
					List<T> remaining = new ArrayList<>(buffer);
					buffer.clear();
					return Mono.just(remaining);
				}
				return Mono.empty();
			});

			return mainFlux.concatWith(tailFlux);
		});
	}

}
