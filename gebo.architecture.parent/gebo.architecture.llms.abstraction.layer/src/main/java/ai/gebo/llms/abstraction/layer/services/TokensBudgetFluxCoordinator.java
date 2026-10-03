package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

		Flux<List<T>> flux = emitQueueWhenPredicateTrue(source.filter(validDocumentCheck),
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
