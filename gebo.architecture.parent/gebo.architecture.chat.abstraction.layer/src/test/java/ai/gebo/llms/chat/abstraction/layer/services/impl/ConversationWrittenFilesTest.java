package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GConversationFilesText;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSWrittenFile;

/**
 * The files the tools of a conversation wrote: kept by the history, by the
 * summary for the requests it took the place of, and given to the agents of the
 * next requests; the current request's in one keeper for every context of it.
 */
class ConversationWrittenFilesTest {

	static GConversationFile file(String path, String version) {
		return new GConversationFile("presentations", path, version);
	}

	static CSSSimplefiedInteraction interaction(Integer index, GConversationFile... written) {
		final CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction();
		interaction.setUser("request " + index);
		interaction.setInteractionIndex(index);
		interaction.setWrittenFiles(written.length > 0 ? new ArrayList<>(List.of(written)) : null);
		return interaction;
	}

	@Test
	void aSummaryKeepsTheFilesOfTheRequestsItTakesThePlaceOf() {
		final CSSSimplifiedChatHistory history = new CSSSimplifiedChatHistory();
		history.getInteractions().add(interaction(2, file("a.pptx", "a2")));
		history.getInteractions().add(interaction(3, file("b.xlsx", "b1"), file("a.pptx", "a3")));
		history.getInteractions().add(interaction(4, file("c.docx", "c1")));
		history.getInteractions().add(interaction(5));
		final CSSConsolidatedChatHistory previous = new CSSConsolidatedChatHistory();
		previous.setEarlierWrittenFiles(new ArrayList<>(List.of(new CSSWrittenFile(0, file("a.pptx", "a1")),
				new CSSWrittenFile(1, file("old.pptx", "o1")))));
		// the last 2 interactions stay verbatim: 2 and 3 are summarized
		final List<CSSWrittenFile> kept = GChatSessionStateShrinkerServiceImpl.earlierWrittenFiles(history, 2,
				previous);
		assertEquals(List.of(new CSSWrittenFile(1, file("old.pptx", "o1")), new CSSWrittenFile(3, file("b.xlsx", "b1")),
				new CSSWrittenFile(3, file("a.pptx", "a3"))), kept);
		assertNull(GChatSessionStateShrinkerServiceImpl.earlierWrittenFiles(new CSSSimplifiedChatHistory(), 2, null));
	}

	@Test
	void theAgentsOfARequestAreGivenTheEarlierFilesAndTheRequestsIndex() {
		final CSSConsolidatedChatHistory history = new CSSConsolidatedChatHistory();
		history.setEarlierWrittenFiles(List.of(new CSSWrittenFile(1, file("old.pptx", "o1"))));
		history.setLastInteractionPointer(3);
		history.getLatestEntries().getInteractions().add(interaction(3, file("a.pptx", "a3")));
		history.getLatestEntries().getInteractions().add(interaction(4));
		final LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setChathistory(history);
		final IChatRequestContext context = resources.createChatRequestContext();
		assertEquals(List.of(new GConversationFilesText.Entry(1, file("old.pptx", "o1")),
				new GConversationFilesText.Entry(3, file("a.pptx", "a3"))), context.getEarlierWrittenFiles());
		assertEquals(5, context.getCurrentRequestIndex());
		assertEquals(0, new LLMChatRequestResources().createChatRequestContext().getCurrentRequestIndex());
	}

	@Test
	void everyContextOfTheRequestCarriesTheSameKeeper() {
		final LLMChatRequestResources resources = new LLMChatRequestResources();
		final IGConversationFilesKeeper first = IGConversationFilesKeeper
				.of(resources.createChatRequestContext().getToolsContext());
		final IGConversationFilesKeeper second = IGConversationFilesKeeper
				.of(resources.createChatRequestContext().withToolsContextValue("other", 1).getToolsContext());
		assertSame(resources.getConversationFilesKeeper(), first);
		assertSame(first, second);
	}
}
