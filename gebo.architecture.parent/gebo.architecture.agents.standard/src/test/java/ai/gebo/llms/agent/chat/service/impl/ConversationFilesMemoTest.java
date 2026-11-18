package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GConversationFilesKeeper;
import ai.gebo.architecture.ai.files.GLibraryFile;

/** The iterations of a run are told of the conversation's files again only when a tool wrote one. */
class ConversationFilesMemoTest {

	@Test
	void theFilesAreRenderedAgainOnlyAfterAWrite() {
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		final AtomicInteger renders = new AtomicInteger();
		final AgenticLoopReactiveAgentServiceImpl.ConversationFilesMemo memo = new AgenticLoopReactiveAgentServiceImpl.ConversationFilesMemo(
				() -> "files " + renders.incrementAndGet(), keeper::getWritten);
		assertEquals("files 1", memo.text());
		assertEquals("files 1", memo.text());
		keeper.written(new GLibraryFile("presentations", "a.pptx", 1, null, "v1", null));
		assertEquals("files 2", memo.text());
		assertEquals("files 2", memo.text());
		// the same file written again is a new version: told again
		keeper.written(new GLibraryFile("presentations", "a.pptx", 1, null, "v2", null));
		assertEquals("files 3", memo.text());
		final List<GConversationFile> none = new ArrayList<>();
		assertEquals("x", new AgenticLoopReactiveAgentServiceImpl.ConversationFilesMemo(() -> "x", () -> none).text());
	}
}
