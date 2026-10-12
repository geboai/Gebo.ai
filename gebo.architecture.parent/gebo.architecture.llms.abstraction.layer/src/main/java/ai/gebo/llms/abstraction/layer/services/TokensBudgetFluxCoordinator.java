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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.ToLongFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;

/**
 * Token budgeted map/reduce over a stream of documents. The documents are analysed in
 * lanes running in parallel: each lane is a consolidation chain, every analysis of a
 * lane receiving the consolidation its analysis before gave (the initial value on the
 * first) and giving the new one, the batch of documents of each analysis sized on the
 * room that consolidation leaves ({@link LaneBudget}). A lane hands its consolidation
 * over (a checkpoint) and starts a new chain when its analysis declares it satisfying,
 * when the consolidation leaves no room for a batch any more, and when the documents
 * end; the checkpoints, each on different documents, are then reduced into the final
 * result. Progress goes to an {@link IGProgressNotifier}, so the same coordination
 * serves the chat pipelines, the agents and the tools.
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

		/**
		 * The documents of a batch as the user is told them while it is analysed: how
		 * many by default, their names when the documents carry them.
		 */
		default String describe(List<D> batch) {
			return batch.size() + " documents";
		}
	}

	/**
	 * The batch budget of a lane: the tokens of documents an analysis may be given beside
	 * the consolidation it carries, and the least budget worth an analysis: below it the
	 * lane hands its consolidation over and starts a new chain.
	 */
	public static record LaneBudget<T>(ToLongFunction<T> batchBudget, long minimumBatchBudget) {
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
					if (result != null && isOutOfBandValue.test(result)) {
						// no analysis came out of the batch: its documents were not analysed
						notAnalysed(List.of(input), unprocessedCumulator);
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
					// the batch failed: its documents were not analysed
					notAnalysed(input != null ? List.of(input) : List.of(), unprocessedCumulator);
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
			Function<T, T> outputCleaningFunction, Function<T, Flux<Y>> streamingFunction, LaneBudget<T> laneBudget,
			ReactiveIdentityUtil runAs, int lanes, Predicate<T> laneSatisfied, Consumer<D> unprocessedCumulator) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinate(..) lanes:" + lanes);
		}
		final AtomicBoolean endOfProcessing = new AtomicBoolean(false);

		Flux<List<T>> flux = laneCheckpoints(source, emitter, validDocumentCheck, tokensCompute, generative,
				initialValue, isOutOfBandValue, isEndOfProcessingCondition, laneSatisfied, outputCleaningFunction,
				laneBudget, runAs, lanes, unprocessedCumulator, endOfProcessing, null).buffer();
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

	/** The end of the documents, in the queue the lanes take them from. */
	private static final Object END_OF_DOCUMENTS = new Object();
	/** How long a lane waits for a document before checking it was not cancelled. */
	private static final long TAKE_POLL_MILLIS = 200l;

	/**
	 * The map stage: the documents analysed in {@code lanes} consolidation chains running
	 * in parallel (see {@link TokensBudgetFluxCoordinator}), each taking the next
	 * documents as it is ready for them. Returns the lanes' checkpoints, in the order they
	 * come. Once {@code endOfProcessing} is set, no analysis starts: the documents not
	 * analysed go to the unprocessed cumulator, and every lane hands over what it
	 * consolidated so far.
	 */
	private static <D, T> Flux<T> laneCheckpoints(Flux<D> source, IGProgressNotifier emitter,
			Predicate<D> validDocumentCheck, TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative,
			T initialValue, Predicate<T> isOutOfBandValue, Predicate<T> isEndOfProcessingCondition,
			Predicate<T> laneSatisfied, Function<T, T> outputCleaningFunction, LaneBudget<T> laneBudget,
			ReactiveIdentityUtil runAs, int lanes, Consumer<D> unprocessedCumulator, AtomicBoolean endOfProcessing,
			Runnable onAnalysed) {
		return Flux.defer(() -> {
			final LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
			final Disposable feeding = source.filter(validDocumentCheck).subscribe(queue::add, error -> {
				LOGGER.error("The documents stream failed: the lanes analyse the documents that came", error);
				queue.add(END_OF_DOCUMENTS);
			}, () -> queue.add(END_OF_DOCUMENTS));
			final int laneCount = Math.max(1, lanes);
			return Flux.range(0, laneCount).flatMap(lane -> Flux.<T>create(sink -> runAs.doRunAsWithReturn(() -> {
				new Lane<D, T>(lane, queue, emitter, tokensCompute, generative, initialValue, isOutOfBandValue,
						isEndOfProcessingCondition, laneSatisfied, outputCleaningFunction, laneBudget,
						unprocessedCumulator, endOfProcessing, onAnalysed).run(sink);
				return null;
			})).subscribeOn(runAs.wrap(Schedulers.boundedElastic())), laneCount)
					.doFinally(signal -> feeding.dispose());
		});
	}

	/**
	 * A consolidation chain: each analysis receives the consolidation of the one before
	 * and a batch of the next documents sized on the room that consolidation leaves.
	 */
	private static final class Lane<D, T> {
		private final int number;
		private final LinkedBlockingQueue<Object> queue;
		private final IGProgressNotifier emitter;
		private final TokensLimitCompute<D> tokensCompute;
		private final GenerativeFunction<D, T> generative;
		private final T initialValue;
		private final Predicate<T> isOutOfBandValue;
		private final Predicate<T> isEndOfProcessingCondition;
		private final Predicate<T> laneSatisfied;
		private final Function<T, T> outputCleaningFunction;
		private final LaneBudget<T> laneBudget;
		private final Consumer<D> unprocessedCumulator;
		private final AtomicBoolean endOfProcessing;
		private final Runnable onAnalysed;
		private T consolidation;
		private int cycles = 0;

		Lane(int number, LinkedBlockingQueue<Object> queue, IGProgressNotifier emitter,
				TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative, T initialValue,
				Predicate<T> isOutOfBandValue, Predicate<T> isEndOfProcessingCondition, Predicate<T> laneSatisfied,
				Function<T, T> outputCleaningFunction, LaneBudget<T> laneBudget, Consumer<D> unprocessedCumulator,
				AtomicBoolean endOfProcessing, Runnable onAnalysed) {
			this.number = number;
			this.queue = queue;
			this.emitter = emitter;
			this.tokensCompute = tokensCompute;
			this.generative = generative;
			this.initialValue = initialValue;
			this.isOutOfBandValue = isOutOfBandValue;
			this.isEndOfProcessingCondition = isEndOfProcessingCondition;
			this.laneSatisfied = laneSatisfied;
			this.outputCleaningFunction = outputCleaningFunction;
			this.laneBudget = laneBudget;
			this.unprocessedCumulator = unprocessedCumulator;
			this.endOfProcessing = endOfProcessing;
			this.onAnalysed = onAnalysed;
			this.consolidation = initialValue;
		}

		@SuppressWarnings("unchecked")
		void run(FluxSink<T> sink) {
			boolean documentsEnded = false;
			while (!documentsEnded && !sink.isCancelled()) {
				if (endOfProcessing.get()) {
					leaveUnprocessed(sink);
					break;
				}
				final long budget = laneBudget.batchBudget().applyAsLong(consolidation);
				if (budget < laneBudget.minimumBatchBudget()) {
					if (cycles > 0) {
						// the consolidation leaves no room for a batch: handed over, a new chain starts
						checkpoint(sink, "no room left for a batch, budget:" + budget + " (tok)");
						continue;
					}
					LOGGER.warn("Lane " + number + " batch budget " + budget + " (tok) under its minimum "
							+ laneBudget.minimumBatchBudget() + " (tok) with no consolidation: one document per batch");
				}
				final List<D> batch = new ArrayList<>();
				while (batch.isEmpty() || !tokensCompute.higherThanBudgetTokens(batch, budget)) {
					final Object next = take(sink);
					if (next == null) {
						// cancelled
						batch.forEach(unprocessedCumulator);
						return;
					}
					if (next == END_OF_DOCUMENTS) {
						// left for the other lanes
						queue.add(END_OF_DOCUMENTS);
						documentsEnded = true;
						break;
					}
					batch.add((D) next);
				}
				if (!batch.isEmpty()) {
					analyse(batch, budget, sink);
				}
			}
			if (cycles > 0) {
				checkpoint(sink, documentsEnded ? "documents ended" : "analysis stopped");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Lane " + number + " ended");
			}
			sink.complete();
		}

		/** The batch as the user is told it, best effort: how many when it can not be described. */
		private String described(List<D> batch) {
			try {
				final String described = tokensCompute.describe(batch);
				if (described != null && !described.isBlank()) {
					return described;
				}
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot describe a batch of " + batch.size() + " documents to the user: " + e);
			}
			return batch.size() + " documents";
		}

		private void analyse(List<D> batch, long budget, FluxSink<T> sink) {
			if (endOfProcessing.get()) {
				batch.forEach(unprocessedCumulator);
				return;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Lane " + number + " analysis " + (cycles + 1) + " of its chain: " + batch.size()
						+ " document(s), batch budget:" + budget + " (tok)");
			}
			try {
				try {
					emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzing " + described(batch));
				} catch (Throwable th) {
					LOGGER.error("Error notifying user about documents analysis start", th);
				}
				final T result = generative.iterateCumulation(consolidation, emitter, batch);
				if (result == null || isOutOfBandValue.test(result)) {
					// no analysis came out of the batch: its documents were not analysed, the
					// consolidation stays as it was
					notAnalysed(batch, unprocessedCumulator);
					return;
				}
				try {
					emitter.notifyProgress(UUID.randomUUID().toString(), "Analyzed " + described(batch) + "!");
				} catch (Throwable th) {
					LOGGER.error("Error notifying user about documents analysis completion", th);
				}
				if (onAnalysed != null) {
					onAnalysed.run();
				}
				final boolean satisfied = laneSatisfied != null && laneSatisfied.test(result);
				if (isEndOfProcessingCondition != null && isEndOfProcessingCondition.test(result)) {
					endOfProcessing.set(true);
				}
				consolidation = outputCleaningFunction.apply(result);
				cycles++;
				if (satisfied) {
					checkpoint(sink, "its analysis is satisfying");
				}
			} catch (Throwable th) {
				emitter.notifyLLMProblems();
				LOGGER.error(EXCEPTION_IN_MAP_PROCESS, th);
				// the batch failed: its documents were not analysed, the consolidation stays
				notAnalysed(batch, unprocessedCumulator);
			}
		}

		/** Hands the consolidation over and starts a new chain. */
		private void checkpoint(FluxSink<T> sink, String why) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Lane " + number + " hands over its consolidation of " + cycles + " analysis(es): " + why);
			}
			sink.next(consolidation);
			consolidation = initialValue;
			cycles = 0;
		}

		/** The documents left once the analysis stopped: not analysed. */
		@SuppressWarnings("unchecked")
		private void leaveUnprocessed(FluxSink<T> sink) {
			while (true) {
				final Object next = take(sink);
				if (next == null) {
					return;
				}
				if (next == END_OF_DOCUMENTS) {
					queue.add(END_OF_DOCUMENTS);
					return;
				}
				unprocessedCumulator.accept((D) next);
			}
		}

		/** The next document, or the end of them; null when the lane is cancelled. */
		private Object take(FluxSink<T> sink) {
			try {
				while (!sink.isCancelled()) {
					final Object next = queue.poll(TAKE_POLL_MILLIS, TimeUnit.MILLISECONDS);
					if (next != null) {
						return next;
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return null;
		}
	}

	/**
	 * The documents of a batch that gave no analysis (it failed, or its analysis was out
	 * of band) go to the unprocessed cumulator, as the batches never started: they were
	 * not analysed.
	 */
	private static <D> void notAnalysed(List<D> batch, Consumer<D> unprocessedCumulator) {
		if (batch == null || unprocessedCumulator == null) {
			return;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Batch of " + batch.size() + " document(s) gave no analysis: left unprocessed");
		}
		batch.forEach(unprocessedCumulator);
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
	 * Token budgeted map with a rolling reduce: the documents are analysed in lanes as
	 * in {@link #tokenBudgetCoordinate}, and the lanes' checkpoints are folded into a
	 * running report while the lanes go on; a lane hands over when its analysis is
	 * satisfying ({@code laneSatisfied}), so the report can be judged as soon as a lane
	 * believes it has enough. The folding is adaptive: when the previous fold is over,
	 * the next one takes every checkpoint that arrived in the meantime, as many as fit
	 * the budget {@code foldBudget} gives beside the report (always at least one), so the
	 * folds are few when the checkpoints come quickly and frequent when they come
	 * slowly. No fold is made before {@code minimumAnalysedBatches} batches are
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
			ToLongFunction<T> foldBudget, T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue,
			Function<T, T> outputCleaningFunction, Function<T, Flux<Y>> streamingFunction, Flux<Y> emptyResult,
			LaneBudget<T> laneBudget, ReactiveIdentityUtil runAs, int lanes, int minimumAnalysedBatches,
			Predicate<T> laneSatisfied, Consumer<D> unprocessedCumulator) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinateWithRollingFold(..) lanes:" + lanes + " minimumAnalysedBatches:"
					+ minimumAnalysedBatches);
		}
		final AtomicBoolean endOfProcessing = new AtomicBoolean(false);
		final AtomicInteger analysed = new AtomicInteger(0);
		final Flux<T> partials = laneCheckpoints(source, emitter, validDocumentCheck, tokensCompute, generative,
				initialValue, isOutOfBandValue, null, laneSatisfied, outputCleaningFunction, laneBudget, runAs, lanes,
				unprocessedCumulator, endOfProcessing, analysed::incrementAndGet);
		final Mono<T> folded = Mono.<T>create(sink -> {
			final RollingReport<T> rolling = new RollingReport<>(rollingFold, appendWhenFoldFails, tokensOf,
					foldBudget, initialValue, minimumAnalysedBatches, analysed, endOfProcessing, emitter);
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
		/** The budget of a fold's checkpoints, given the report it carries. */
		private final ToLongFunction<T> foldBudget;
		private final T initialValue;
		private final int minimumAnalysedBatches;
		private final AtomicInteger analysed;
		private final AtomicBoolean endOfProcessing;
		private final IGProgressNotifier emitter;
		private final ConcurrentLinkedQueue<T> waiting = new ConcurrentLinkedQueue<>();
		private T report = null;
		private int folds = 0;

		RollingReport(RollingFold<T> rollingFold, Function<List<T>, T> appendWhenFoldFails, ToLongFunction<T> tokensOf,
				ToLongFunction<T> foldBudget, T initialValue, int minimumAnalysedBatches, AtomicInteger analysed,
				AtomicBoolean endOfProcessing, IGProgressNotifier emitter) {
			this.rollingFold = rollingFold;
			this.appendWhenFoldFails = appendWhenFoldFails;
			this.tokensOf = tokensOf;
			this.foldBudget = foldBudget;
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
				// the budget of the checkpoints beside the report this fold carries
				final long budget = foldBudget.applyAsLong(report != null ? report : initialValue);
				long waitingTokens = 0l;
				for (T partial : waiting) {
					waitingTokens += tokens(partial);
				}
				final boolean roomLeft = waitingTokens < budget;
				if (!sourceDone && roomLeft && (analysed.get() < minimumAnalysedBatches
						|| (report == null && waiting.size() == 1))) {
					// no stop possible yet, or a first analysis alone that may be the only one
					// (then it is the report, no fold needed): wait for more analyses
					return;
				}
				fold(takeGroup(budget));
			}
		}

		/** The waiting checkpoints that fit the budget left beside the report, at least one. */
		private List<T> takeGroup(long budget) {
			final List<T> group = new ArrayList<>();
			long used = 0l;
			while (!waiting.isEmpty()) {
				final long next = tokens(waiting.peek());
				if (!group.isEmpty() && used + next > budget) {
					break;
				}
				used += next;
				group.add(waiting.poll());
			}
			if (used > budget) {
				LOGGER.warn("A fold of " + group.size() + " checkpoint(s) takes " + used
						+ " (tok), over the budget of " + budget + " (tok) its report leaves");
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
