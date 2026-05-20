package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TokensBudgetExecutor {
	@FunctionalInterface
	public static interface LastWork<X, Y> {
		Flux<Y> iterateCumulation(List<X> finalFunction);
	}

	@FunctionalInterface
	public static interface GenerativeFunction<X> {
		X iterateCumulation(X x, ISinkUIEmitter emitter, List<Document> documents);
	}

	@FunctionalInterface
	public static interface TokensCompute<D> {
		long computeTokens(D d);
	}

	public static <T, Y> Flux<Y> tokenBudgetExecute(Flux<Document> source, ISinkUIEmitter emitter,
			GenerativeFunction<T> generative, LastWork<T, Y> finalWork, T initialValue, long tokensBudget) {
		Flux<List<T>> flux = emitQueueWhenPredicateTrue(source, list -> higherThanBudget(list, tokensBudget))
				.parallel(4).runOn(Schedulers.parallel())
				.map(input -> generative.iterateCumulation(initialValue, emitter, input)).sequential().buffer();

		return flux.flatMap(finalWork::iterateCumulation);
	}

	static boolean higherThanBudget(List<Document> docs, long tokensBudget) {
		long tokens = 0;
		for (Document document : docs) {
			tokens += ITokensCountable.tokensSize(document.getMetadata());
			if (document.getMetadata() != null
					&& document.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number length) {
				tokens += length.longValue();
			} else {
				tokens += ITokensCountable.stringsTokensSize(document.getText());
			}
		}
		return tokens >= tokensBudget;
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
