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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.InlineThinkingSplitter.Split;

/**
 * The reasoning a model writes between thinking tags, split from its answer as the text
 * streams and on the whole text ({@link ClientChatCallUtil#removeThinking(String)}).
 */
class InlineThinkingSplitterTest {

	/** The text fed in pieces of the given size: {answer, reasoning} as given back, ended. */
	private static String[] streamed(String text, int piece) {
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter();
		final StringBuilder answer = new StringBuilder();
		final StringBuilder thinking = new StringBuilder();
		for (int i = 0; i < text.length(); i += piece) {
			final Split split = splitter.next(text.substring(i, Math.min(text.length(), i + piece)));
			answer.append(split.answer());
			thinking.append(split.thinking());
		}
		final Split rest = splitter.end();
		answer.append(rest.answer());
		thinking.append(rest.thinking());
		return new String[] { answer.toString(), thinking.toString() };
	}

	@Test
	void theTagsNeverComeBackHoweverTheTextIsCut() {
		final String text = "<think>\nThe user asks for the sum.\n</think>\n\nThe sum is 4.";
		for (int piece = 1; piece <= text.length(); piece++) {
			final String[] split = streamed(text, piece);
			assertEquals("\n\nThe sum is 4.", split[0], "pieces of " + piece);
			assertEquals("\nThe user asks for the sum.\n", split[1], "pieces of " + piece);
		}
	}

	@Test
	void bothTagStylesAnyCaseSeveralBlocks() {
		final String text = "<THINKING>first</THINKING>\nPart one.\n<think>second</think>\nPart two.";
		for (int piece = 1; piece <= 7; piece++) {
			final String[] split = streamed(text, piece);
			assertEquals("\nPart one.\n\nPart two.", split[0], "pieces of " + piece);
			assertEquals("firstsecond", split[1], "pieces of " + piece);
		}
		assertEquals(List.of("first", "second"), InlineThinkingSplitter.blocks(text));
	}

	@Test
	void aModelWritingNoTagsAnswersAsItStreams() {
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter();

		assertEquals(new Split("The answer ", ""), splitter.next("The answer "));
		assertEquals(new Split("is 4", ""), splitter.next("is 4"));
		assertTrue(splitter.end().isEmpty());
	}

	@Test
	void theReasoningStreamsBeforeItsClosingTag() {
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter();

		assertEquals(new Split("", "Let me "), splitter.next("<think>Let me "));
		assertTrue(splitter.isInsideThinking());
		assertEquals(new Split("", "think "), splitter.next("think </"), "a possible tag end is held");
		assertEquals(new Split("Done.", ""), splitter.next("think>Done."));
		assertFalse(splitter.isInsideThinking());
	}

	@Test
	void aBlockStillOpenWhenTheTextEndsIsReasoning() {
		final String[] split = streamed("<think>cut while reasoni", 5);

		assertEquals("", split[0]);
		assertEquals("cut while reasoni", split[1]);
		assertEquals("", ClientChatCallUtil.removeThinking("<think>cut while reasoni"));
		assertEquals(List.of("cut while reasoni"), InlineThinkingSplitter.blocks("<think>cut while reasoni"));
	}

	@Test
	void aTagNamedInsideALineOfTheAnswerIsText() {
		final String text = "Models wrap it in `<think>` and `</think>` tags.";

		assertEquals(text, streamed(text, 3)[0]);
		assertEquals(text, ClientChatCallUtil.removeThinking(text));
		assertNull(ClientChatCallUtil.extractThinking(text));
	}

	@Test
	void aLessThanInTheAnswerIsHeldOnlyUntilItIsNoTag() {
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter();

		assertEquals(new Split("a ", ""), splitter.next("a <"));
		assertEquals(new Split("< b", ""), splitter.next(" b"));
		assertEquals(new Split("List", ""), splitter.next("List<"));
		assertEquals(new Split("<", ""), splitter.end(), "the text ended: nothing held");
	}

	@Test
	void theEndOfABlockTheTemplateOpened() {
		final String text = "The user asks for the sum.\n</think>\n\nThe sum is 4.";

		assertEquals("\n\nThe sum is 4.", ClientChatCallUtil.removeThinking(text),
				"on the whole text what comes before is reasoning");
		assertEquals(List.of("The user asks for the sum."), ClientChatCallUtil.extractThinking(text));
		assertEquals("The user asks for the sum.\n\n\nThe sum is 4.", streamed(text, 4)[0],
				"while streaming it was answer already: only the tag is dropped");
	}

	@Test
	void theSplitterStartsOverAfterTheEnd() {
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter();
		splitter.next("<think>first round");
		splitter.end();

		assertEquals(new Split("written again", ""), splitter.next("written again"));
	}

	@Test
	void removeThinkingRegressions() {
		// the closing tag cut with the length of the opening one left a '>'
		assertEquals("\nThe answer.", ClientChatCallUtil.removeThinking("<thinking>reasoning</thinking>\nThe answer."));
		// the text before the first block was lost
		assertEquals("Intro.\n\nThe answer.",
				ClientChatCallUtil.removeThinking("Intro.\n<think>reasoning</think>\nThe answer."));
		// only the first block was removed
		assertEquals("A\nB", ClientChatCallUtil.removeThinking("<think>one</think>A\n<think>two</think>B"));
		// a closing tag at the very start was kept
		assertEquals("The answer.", ClientChatCallUtil.removeThinking("</think>The answer."));
		// no tags: as it is
		assertEquals("Just the answer.", ClientChatCallUtil.removeThinking("Just the answer."));
		assertNull(ClientChatCallUtil.removeThinking(null));
	}

	@Test
	void theReasoningStepsOfEveryBlock() {
		assertEquals(List.of("first step", "second step", "other block"), ClientChatCallUtil
				.extractThinking("<think>first step\n\nsecond step</think>\nAnswer\n<think><p>other block</p></think>"));
	}
}
