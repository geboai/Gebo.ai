package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.dto.LLMUsageDetailDto;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.services.ILLMSUsageCrudService;
import ai.gebo.llms.abstraction.layer.services.StackSamplingUtils;
import ai.gebo.model.ModelType;
import lombok.AllArgsConstructor;

/**
 * Writes the single usage record of one model call, whatever the model type. The
 * chat usage advisor and the usage recording wrappers of the other model types all
 * go through here, so the user, response time and caller stack of a record are derived
 * the same way for every type.
 */
@Service
@AllArgsConstructor
public class LLMUsageRecorder {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMUsageRecorder.class);
	/** Number of distinct packages sampled from the caller's stack. */
	public static final int CALLER_PACKAGES = 3;
	private final ILLMSUsageCrudService usageCrudService;

	/**
	 * Package prefixes left out of the caller stack: the platform and the frameworks
	 * the call travels through (Spring AI's advisor chain and observation wrapping,
	 * Reactor), which say nothing about who made the call.
	 */
	static final List<String> SKIPPED_PACKAGE_PREFIXES = List.of("java.", "javax.", "jdk.", "sun.", "com.sun.",
			"org.springframework.", "io.micrometer.", "reactor.", "io.netty.", "io.opentelemetry.",
			"com.fasterxml.", "tools.jackson.", "kotlin.", "kotlinx.");

	/**
	 * Packages left out exactly (not their subpackages): the LLM abstraction layer's
	 * own plumbing, which every call crosses whoever makes it. The usage recorders and
	 * wrappers live in the second one.
	 */
	static final List<String> SKIPPED_PACKAGES = List.of("ai.gebo.llms.abstraction.layer.services",
			"ai.gebo.llms.abstraction.layer.services.impl");

	static boolean isSkippedCallerPackage(String packageName) {
		if (SKIPPED_PACKAGES.contains(packageName)) {
			return true;
		}
		for (String prefix : SKIPPED_PACKAGE_PREFIXES) {
			if (packageName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Samples the caller's stack: the first {@link #CALLER_PACKAGES} distinct
	 * application packages that led to the call, framework and LLM layer plumbing
	 * left out, so that calls made by different components are told apart. When no
	 * application frame is on the stack at all (a stream subscribed from a pure
	 * Reactor thread), falls back to the unfiltered sample rather than to nothing.
	 * Must be called on the thread that issued the call, before any asynchronous hop,
	 * or the sample describes the scheduler instead.
	 */
	public static String sampleCaller() {
		String callers = StackSamplingUtils.sampleCallerPackages(CALLER_PACKAGES,
				LLMUsageRecorder::isSkippedCallerPackage);
		if (callers.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No application frame on the caller stack, sampling it unfiltered");
			}
			return StackSamplingUtils.sampleCallerPackages(CALLER_PACKAGES);
		}
		return callers;
	}

	/**
	 * The user the call is made for. Like {@link #sampleCaller()} it must be read on
	 * the calling thread: a streamed call completes on a Reactor thread whose
	 * security context is usually empty.
	 */
	public static String currentUsername() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getName).orElse("anonymous");
	}

	/**
	 * Records one call. Always writes, even with zero tokens: model types that have
	 * no token accounting (speech, transcription, images, ranking) and providers that
	 * return no usage are still worth counting and timing.
	 *
	 * @param modelType        the type of the model actually called, stated by the
	 *                         recording point: it is not inferred from the
	 *                         configuration's class, which yields no type for a
	 *                         configuration class it does not know.
	 * @param startNanos       {@link System#nanoTime()} when the request was issued;
	 *                         the response time runs from here to now, the response
	 *                         being complete.
	 * @param firstTokenNanos  {@link System#nanoTime()} when the first chunk carrying
	 *                         generated content arrived, for a streamed call; null when
	 *                         the call was not streamed or produced no content, which
	 *                         leaves the time to first token unmeasured.
	 * @param pricing          the pricing conditions of the model called, as its
	 *                         {@code IGConfigurableModel.getPricingConditions()}
	 *                         returns them; read here, best effort, to price the call.
	 *                         Null, or a supplier returning null, leaves the call
	 *                         unpriced.
	 */
	public void record(GBaseModelConfig config, ModelType modelType, Supplier<GModelPricingConditions> pricing,
			String username, String callerStack, long startNanos, Long firstTokenNanos, long inputToken,
			long outputToken, long totalToken, LLMCallOutcome outcome) {
		long responseTimeMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
		Long timeToFirstTokenMs = firstTokenNanos != null
				? TimeUnit.NANOSECONDS.toMillis(firstTokenNanos.longValue() - startNanos)
				: null;
		LLMUsageDetailDto detail = LLMUsageDetailDto.of(config);
		if (modelType != null) {
			if (detail.getModelType() != null && detail.getModelType() != modelType && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Usage model type stated by the caller=" + modelType
						+ " differs from the one inferred from the configuration=" + detail.getModelType()
						+ ", keeping the stated one");
			}
			detail.setModelType(modelType);
		}
		detail.setInputToken(inputToken);
		detail.setOutputToken(outputToken);
		detail.setTotalToken(totalToken);
		detail.setUsername(username);
		detail.setCallerStack(callerStack);
		detail.setResponseTime(responseTimeMs);
		detail.setTimeToFirstToken(timeToFirstTokenMs);
		detail.setOutcome(outcome);
		priceCall(detail, pricing, inputToken, outputToken, outcome);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Recording usage modelType=" + detail.getModelType() + " provider=" + detail.getProviderId()
					+ " model=" + detail.getModel() + " user=" + username + " outcome=" + outcome
					+ " responseTime=" + responseTimeMs + "ms timeToFirstToken="
					+ (timeToFirstTokenMs != null ? timeToFirstTokenMs + "ms" : "n/a") + " tokens=" + inputToken
					+ "/" + outputToken + "/" + totalToken + " cost="
					+ (detail.getCost() != null ? detail.getCost() + " " + detail.getCurrencyCode() : "n/a"));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<USAGE_CALLER_STACK>" + callerStack + "</USAGE_CALLER_STACK>");
		}
		this.usageCrudService.enqueueUsage(detail);
	}

	/**
	 * Prices the call from the model's pricing conditions, best effort: pricing only
	 * enriches the record, so any failure is logged and leaves the call unpriced, the
	 * call itself is recorded all the same.
	 */
	private void priceCall(LLMUsageDetailDto detail, Supplier<GModelPricingConditions> pricing, long inputToken,
			long outputToken, LLMCallOutcome outcome) {
		if (pricing == null) {
			return;
		}
		try {
			GModelPricingConditions conditions = pricing.get();
			if (conditions == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("No pricing conditions for model=" + detail.getModel() + ", call left unpriced");
				}
				return;
			}
			Double cost = conditions.usageCost(inputToken, outputToken, outcome == LLMCallOutcome.SUCCESS);
			if (cost != null) {
				detail.setCost(cost);
				detail.setCurrencyCode(conditions.getCurrencyCode());
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model=" + detail.getModel() + " pricing type=" + conditions.getPricingType()
						+ " attributes no cost to a single call");
			}
		} catch (Throwable e) {
			LOGGER.error("Cannot price the usage of model=" + detail.getModel() + ", the call is recorded unpriced",
					e);
		}
	}

	private static long toLong(Integer value) {
		return value != null ? value.longValue() : 0L;
	}

	/**
	 * Starts the accounting of one call to a model of the given type, capturing on the
	 * calling thread everything that cannot be read once the call has completed.
	 *
	 * @param pricing the model's {@code IGConfigurableModel.getPricingConditions()},
	 *                read when the call ends; null for an unpriced model
	 */
	public Call begin(GBaseModelConfig config, ModelType modelType, Supplier<GModelPricingConditions> pricing) {
		return new Call(config, modelType, pricing, currentUsername(), sampleCaller(), System.nanoTime());
	}

	/**
	 * One call being accounted: ended exactly once, with its outcome.
	 */
	@AllArgsConstructor
	public final class Call {
		private final GBaseModelConfig config;
		private final ModelType modelType;
		private final Supplier<GModelPricingConditions> pricing;
		private final String username;
		private final String callerStack;
		private final long startNanos;

		public void success(long inputToken, long outputToken, long totalToken) {
			record(config, modelType, pricing, username, callerStack, startNanos, null, inputToken, outputToken,
					totalToken, LLMCallOutcome.SUCCESS);
		}

		public void success() {
			success(0, 0, 0);
		}

		/** Ends the call with the token counts of a provider {@link Usage}, when present. */
		public void success(Usage usage) {
			if (usage == null) {
				success();
				return;
			}
			success(toLong(usage.getPromptTokens()), toLong(usage.getCompletionTokens()),
					toLong(usage.getTotalTokens()));
		}

		public void failure() {
			record(config, modelType, pricing, username, callerStack, startNanos, null, 0, 0, 0,
					LLMCallOutcome.ERROR);
		}
	}
}
