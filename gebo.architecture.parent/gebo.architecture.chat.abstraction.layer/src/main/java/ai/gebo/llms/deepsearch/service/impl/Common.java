package ai.gebo.llms.deepsearch.service.impl;

import java.util.function.Function;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.model.GUserMessage;
import reactor.core.publisher.Flux;

public class Common {
	private static final String SOME_PROBLEMS_IN_THE_DEEP_SEARCH = "Some problems in the deep search...";
	private static final Logger LOGGER = LoggerFactory.getLogger(Common.class);

	public static Function<? super Throwable, ? extends Publisher<? extends AbstractDeepSearchEvent>> commonFallBack(
			DeepSearchRequest deepSearchRequest) {
		final Function<? super Throwable, ? extends Publisher<? extends AbstractDeepSearchEvent>> out = x -> {
			LOGGER.error(SOME_PROBLEMS_IN_THE_DEEP_SEARCH, x);
			DeepSearchErrorEvent event = new DeepSearchErrorEvent();
			event.setInputData(deepSearchRequest);
			event.setOutputData(GUserMessage.errorMessage(SOME_PROBLEMS_IN_THE_DEEP_SEARCH, x));
			return Flux.just(event);

		};
		return out;
	}

}
