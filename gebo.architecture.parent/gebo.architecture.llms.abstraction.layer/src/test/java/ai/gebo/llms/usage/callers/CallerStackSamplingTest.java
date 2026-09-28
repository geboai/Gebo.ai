package ai.gebo.llms.usage.callers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.StackSamplingUtils;
import ai.gebo.llms.abstraction.layer.services.impl.LLMUsageRecorder;

/**
 * Pins that the caller stack of a usage record names the application code that made
 * the call, not the frameworks and LLM layer plumbing the call travels through.
 * Lives outside the LLM layer's packages on purpose: those are left out of the
 * sample, exactly as for real callers.
 */
class CallerStackSamplingTest {

	private static final List<String> PLUMBING = List.of("ai.gebo.llms.abstraction.layer.services",
			"ai.gebo.llms.abstraction.layer.services.impl");

	@Test
	void theCallingApplicationPackageComesFirst() {
		// A Supplier from java.util.function stands for a framework frame in between.
		Supplier<String> throughFramework = LLMUsageRecorder::sampleCaller;
		String sampled = throughFramework.get();

		List<String> packages = Arrays.asList(sampled.split(","));
		assertEquals("ai.gebo.llms.usage.callers", packages.get(0));
		for (String p : packages) {
			assertFalse(p.startsWith("java.") || p.startsWith("jdk.") || p.startsWith("org.springframework.")
					|| p.startsWith("reactor.") || PLUMBING.contains(p), "framework package sampled: " + p);
		}
	}

	@Test
	void theUnfilteredSampleStillStartsAtTheInnermostFrame() {
		String unfiltered = StackSamplingUtils.sampleCallerPackages(1);
		assertEquals("ai.gebo.llms.usage.callers", unfiltered);
	}

	@Test
	void everythingSkippedYieldsAnEmptySample() {
		assertTrue(StackSamplingUtils.sampleCallerPackages(3, p -> true).isEmpty());
	}
}
