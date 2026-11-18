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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionException;
import org.springframework.ai.tool.function.FunctionToolCallback;

import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;

/**
 * A tool called with arguments its parameters do not take, or that cannot run in
 * this environment, fails as an error of the tool, which the tool calling hands
 * back to the model as the call's result, instead of ending the model call and
 * the agent's turn with it.
 */
class ToolArgumentsErrorsTest {

	/** An enum written by its name, as the schemas list enums, read by toString() as Jackson 3 does. */
	enum Font {
		CALIBRI("Calibri"), ARIAL("Arial");

		private final String fontName;

		Font(String fontName) {
			this.fontName = fontName;
		}

		@Override
		public String toString() {
			return fontName;
		}
	}

	record Paint(String text, Font font) {
	}

	private static ToolCallback paint() {
		return FunctionToolCallback.builder("paintText", (Paint paint, ToolContext context) -> "painted " + paint.text())
				.description("Paints a text in a font").inputType(Paint.class).build();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ReactiveIdentityUtil runningInPlace() {
		ReactiveIdentityUtil runAs = mock(ReactiveIdentityUtil.class);
		when(runAs.doRunAsWithReturn(any()))
				.thenAnswer(invocation -> ((RunAsWithReturn) invocation.getArgument(0)).apply());
		return runAs;
	}

	private static final ToolContext CONTEXT = new ToolContext(Map.of());

	@Test
	void argumentsItsParametersDoNotTakeAreAnErrorOfTheTool() {
		ToolCallsListener listener = new ToolCallsListener();
		RunAsToolCallback wrapped = new RunAsToolCallback(paint(), runningInPlace(), listener);

		ToolExecutionException error = assertThrows(ToolExecutionException.class,
				() -> wrapped.call("{\"text\":\"hello\",\"font\":\"PURPLE\"}", CONTEXT));
		assertEquals("paintText", error.getToolDefinition().name());
		String result = DefaultToolExecutionExceptionProcessor.builder().build().process(error);
		assertTrue(result.startsWith("The call of the tool paintText failed: "), result);
		assertTrue(result.contains("PURPLE") && result.contains("font"),
				"the model reads the value and the field it got wrong: " + result);
		assertTrue(listener.getCalls().isEmpty(), "a call that did not run is not recorded");

		assertThrows(ToolExecutionException.class, () -> wrapped.call("{\"text\":\"hello\",\"font\":\"PURPLE\"}"),
				"also without a tools context");
	}

	@Test
	void readArgumentsRunTheTool() {
		RunAsToolCallback wrapped = new RunAsToolCallback(paint(), runningInPlace(), new ToolCallsListener());
		assertEquals("\"painted hello\"", wrapped.call("{\"text\":\"hello\",\"font\":\"Calibri\"}", CONTEXT));
	}

	@Test
	void anErrorOfTheToolGoesOnAsItIs() {
		ToolCallback failing = FunctionToolCallback.builder("failing", (Paint paint, ToolContext context) -> {
			throw new IllegalStateException("No such document");
		}).description("Always fails").inputType(Paint.class).build();
		RunAsToolCallback wrapped = new RunAsToolCallback(failing, runningInPlace(), new ToolCallsListener());

		ToolExecutionException error = assertThrows(ToolExecutionException.class,
				() -> wrapped.call("{\"text\":\"hello\"}", CONTEXT));
		assertEquals("No such document", error.getMessage(), "the tool's own error, not wrapped again");
	}

	@Test
	void aToolThatCannotRunHereIsAnErrorOfTheTool() {
		ToolCallback unlinked = FunctionToolCallback.builder("measureText", (Paint paint, ToolContext context) -> {
			throw new UnsatisfiedLinkError("libfreetype.so.6: cannot open shared object file");
		}).description("Measures a text").inputType(Paint.class).build();
		RunAsToolCallback wrapped = new RunAsToolCallback(unlinked, runningInPlace(), new ToolCallsListener());

		ToolExecutionException error = assertThrows(ToolExecutionException.class,
				() -> wrapped.call("{\"text\":\"hello\"}", CONTEXT));
		String result = DefaultToolExecutionExceptionProcessor.builder().build().process(error);
		assertEquals("The call of the tool measureText failed: libfreetype.so.6: cannot open shared object file",
				result, "a native library missing: this tool cannot run, the run goes on");
	}

	@Test
	void theJvmsOwnErrorsGoOn() {
		ToolCallback exhausted = FunctionToolCallback.builder("exhausting", (Paint paint, ToolContext context) -> {
			throw new OutOfMemoryError("Java heap space");
		}).description("Exhausts the heap").inputType(Paint.class).build();
		RunAsToolCallback wrapped = new RunAsToolCallback(exhausted, runningInPlace(), new ToolCallsListener());
		assertThrows(OutOfMemoryError.class, () -> wrapped.call("{\"text\":\"hello\"}", CONTEXT));
	}

	@Test
	void theSameToolExecutionExceptionIsRethrown() {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition()).thenReturn(paint().getToolDefinition());
		ToolExecutionException own = new ToolExecutionException(paint().getToolDefinition(),
				new IllegalArgumentException("own"));
		when(tool.call(any(), any(ToolContext.class))).thenThrow(own);
		RunAsToolCallback wrapped = new RunAsToolCallback(tool, runningInPlace(), new ToolCallsListener());
		assertSame(own, assertThrows(ToolExecutionException.class, () -> wrapped.call("{}", CONTEXT)));
	}
}
