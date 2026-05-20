package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TokensBudgetFluxCoordinator {
	private static final String EXCEPTION_IN_FLAT_MAP = "Exception in flatMap(...)";
	private static final String EXCEPTION_IN_MAP_PROCESS = "Exception in map(..) process";
	private static final Logger LOGGER = LoggerFactory.getLogger(TokensBudgetFluxCoordinator.class);

	@FunctionalInterface
	public static interface LastWork<X, Y> {
		Flux<Y> iterateCumulation(List<X> finalFunction, ISinkUIEmitter emitter) throws Exception;
	}

	@FunctionalInterface
	public static interface GenerativeFunction<D, X> {
		X iterateCumulation(X x, ISinkUIEmitter emitter, List<D> documents) throws Exception;
	}

	@FunctionalInterface
	public static interface TokensLimitCompute<D> {
		boolean higherThanBudgetTokens(List<D> d, long budget);
	}

	public static <D, T, Y> Flux<Y> tokenBudgetCoordinate(Flux<D> source, ISinkUIEmitter emitter,
			Predicate<D> validDocumentCheck, TokensLimitCompute<D> tokensCompute, GenerativeFunction<D, T> generative,
			LastWork<T, Y> finalWork, T initialValue, T outOfBandValue, Predicate<T> isOutOfBandValue,
			Y finalOutOFBoundValue, Predicate<Y> isFinalOutOFBoundValue, long tokensBudget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenBudgetCoordinate(..) ");
		}
		Flux<List<T>> flux = emitQueueWhenPredicateTrue(source.filter(validDocumentCheck),
				list -> tokensCompute.higherThanBudgetTokens(list, tokensBudget)).parallel(4)
				.runOn(Schedulers.boundedElastic()).map(input -> {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Begin map(...) code with " + input.size() + " elements");
					}
					try {

						T result = generative.iterateCumulation(initialValue, emitter, input);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("End map(...) code with " + input.size() + " returning:" + result);
						}
						return result;
					} catch (Throwable th) {
						LOGGER.error(EXCEPTION_IN_MAP_PROCESS, th);
						return outOfBandValue;
					}
				}).filter(V -> !isOutOfBandValue.test(V)).sequential().buffer();
		Flux<Y> finalFlux = flux.flatMap(IntermediateResult -> {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin flatMap(...) code with " + IntermediateResult.size() + " input elements");
				}
				Flux<Y> finalResult = finalWork.iterateCumulation(IntermediateResult, emitter);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End flatMap(...) code");
				}
				return finalResult;
			} catch (Throwable th) {
				LOGGER.error(EXCEPTION_IN_FLAT_MAP, th);
				return Flux.just(finalOutOFBoundValue);
			}
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
