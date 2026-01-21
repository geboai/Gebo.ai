package ai.gebo.llms.deepsearch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public final class ReactiveMonitor {
	private final static Logger LOGGER = LoggerFactory.getLogger(ReactiveMonitor.class);

	public static <T> java.util.function.Function<Flux<T>, Flux<T>> monitor(String name) {
		return flux -> flux.doOnError(e -> LOGGER.error("[{}] uncaught", name, e))
				.doFinally(sig -> LOGGER.info("[{}] done: {}", name, sig)).checkpoint(name, true);
	}
}