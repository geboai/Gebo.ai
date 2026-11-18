/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.model.base.TypedInputStream;

class GConversationFilesTest {

	/** A location of files known by path, with their size and version. */
	static class Files implements IGFilesLocation {
		final String id;
		final Map<String, GLibraryFile> files = new HashMap<>();

		Files(String id) {
			this.id = id;
		}

		Files with(String path, long size, String version) {
			files.put(path, new GLibraryFile(id, path, size, null, version, null));
			return this;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getDescription() {
			return id;
		}

		@Override
		public boolean isWritable() {
			return true;
		}

		@Override
		public List<GLibraryFile> list(String folder, boolean recursive) {
			return List.copyOf(files.values());
		}

		@Override
		public GLibraryFile describe(String path) throws IOException {
			final GLibraryFile file = files.get(path);
			if (file == null) {
				throw new NoSuchFileException(path);
			}
			return file;
		}

		@Override
		public GLibraryFileContent read(String path) {
			throw new UnsupportedOperationException();
		}

		@Override
		public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) {
			throw new UnsupportedOperationException();
		}
	}

	/** Outlines .pptx files with as many lines as asked, one token or so each. */
	static class Decks implements IGFilesOutliner {
		final int lines;

		Decks(int lines) {
			this.lines = lines;
		}

		@Override
		public boolean outlines(String path) {
			return path.endsWith(".pptx");
		}

		@Override
		public String outline(IGFilesLocation location, String path, int maxTokens) throws IOException {
			final StringBuilder outline = new StringBuilder();
			for (int line = 1; line <= lines; line++) {
				outline.append(line > 1 ? "\n" : "").append(line).append(" TITLE_AND_CONTENT \"Slide ").append(line)
						.append("\"");
			}
			return outline.toString();
		}
	}

	static GConversationFilesText.Entry entry(int request, String location, String path, String version) {
		return new GConversationFilesText.Entry(request, new GConversationFile(location, path, version));
	}

	@Test
	void theKeeperKeepsEachFileOnceAtItsLastVersionInTheOrderFirstWritten() {
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		keeper.written(new GLibraryFile("presentations", "deck.pptx", 10, null, "v1", null));
		keeper.written(new GLibraryFile("spreadsheets", "sales.xlsx", 10, null, "s1", null));
		keeper.written(new GLibraryFile("presentations", "deck.pptx", 12, null, "v2", null));
		keeper.written(new GLibraryFile("working-directory", "notes\\today.txt", 1, null, "n1", null));
		keeper.written(null);
		assertEquals(List.of(new GConversationFile("presentations", "deck.pptx", "v2"),
				new GConversationFile("spreadsheets", "sales.xlsx", "s1"),
				new GConversationFile("working-directory", "notes/today.txt", "n1")), keeper.getWritten());
	}

	@Test
	void aToolReportsToTheKeeperItsContextCarries() throws InterruptedException {
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		final ToolContext context = new ToolContext(Map.of("filesKeeper", keeper, "other", "value"));
		final ExecutorService tools = Executors.newFixedThreadPool(8);
		for (int index = 0; index < 200; index++) {
			final int file = index % 20;
			tools.submit(() -> IGConversationFilesKeeper.report(context,
					new GLibraryFile("working-directory", "f" + file + ".txt", 1, null, "v", null)));
		}
		tools.shutdown();
		assertTrue(tools.awaitTermination(10, TimeUnit.SECONDS));
		assertEquals(20, keeper.getWritten().size());
		// no keeper, no harm
		IGConversationFilesKeeper.report(new ToolContext(Map.of()), new GLibraryFile("x", "y", 1, null, "v", null));
		IGConversationFilesKeeper.report(null, null);
	}

	@Test
	void theFilesAreShownLatestFirstWithTheirOutlineAsTheyAreNow() {
		final Files presentations = new Files("presentations").with("deck.pptx", 2048, "v2");
		final Files work = new Files("working-directory").with("notes.txt", 300, "n1");
		final String text = GConversationFilesText.render(
				List.of(entry(1, "presentations", "deck.pptx", "v1"), entry(1, "working-directory", "notes.txt", "n1"),
						entry(2, "presentations", "deck.pptx", "v2")), 3,
				Map.of("presentations", (IGFilesLocation) presentations, "working-directory", work)::get,
				List.of(new Decks(2)), 20000, 100000);
		assertTrue(text.startsWith(GConversationFilesText.HEADING), text);
		// the deck once, at request 2, before the notes of request 1
		assertEquals(1, text.split("presentations: deck.pptx", -1).length - 1, text);
		assertTrue(text.indexOf("presentations: deck.pptx - written in request 3, 2.0 KB")
				< text.indexOf("working-directory: notes.txt - written in request 2, 300 bytes"), text);
		assertTrue(text.contains("\n  1 TITLE_AND_CONTENT \"Slide 1\"\n  2 TITLE_AND_CONTENT \"Slide 2\""), text);
		assertTrue(!text.contains("changed since"), text);
	}

	@Test
	void aFileChangedSinceSaysSoAndADeletedOneIsLeftOut() {
		final Files presentations = new Files("presentations").with("deck.pptx", 2048, "edited-by-hand");
		final String text = GConversationFilesText.render(
				List.of(entry(1, "presentations", "deck.pptx", "v1"), entry(1, "presentations", "gone.pptx", "g1"),
						entry(1, "uploads", "held-no-more.pptx", "u1")), 3,
				Map.of("presentations", (IGFilesLocation) presentations)::get, List.of(new Decks(1)), 20000, 100000);
		assertTrue(text.contains("deck.pptx - written in request 2, 2.0 KB; changed since by something else than the tools"),
				text);
		assertTrue(!text.contains("gone.pptx") && !text.contains("held-no-more.pptx"), text);
		assertNull(GConversationFilesText.render(List.of(entry(1, "presentations", "gone.pptx", "g1")), 3,
				Map.of("presentations", (IGFilesLocation) presentations)::get, List.of(), 20000, 100000));
		assertNull(GConversationFilesText.render(List.of(), 3, id -> null, List.of(), 20000, 100000));
	}

	@Test
	void filesFindingNoRoomAreStillNamed() {
		final Files presentations = new Files("presentations").with("big.pptx", 900000, "b1").with("small.pptx", 900,
				"s1");
		final String text = GConversationFilesText.render(
				List.of(entry(1, "presentations", "small.pptx", "s1"), entry(2, "presentations", "big.pptx", "b1")), 3,
				Map.of("presentations", (IGFilesLocation) presentations)::get, List.of(new Decks(400)), 20000, 1500);
		// the big deck's outline takes more than the whole budget: only named; the small one finds no room after it
		assertTrue(text.contains("Also written, without an outline here: presentations: big.pptx (written in request 3)"),
				text);
		assertTrue(!text.contains("Slide 400"), text);
	}

	@Test
	void anOutlineThatFailsIsSaidAndAFileOfAnUnknownKindIsOnlyDescribed() {
		final Files work = new Files("working-directory").with("broken.pptx", 10, "b1").with("data.csv", 5000, "c1");
		final IGFilesOutliner failing = new Decks(1) {
			@Override
			public String outline(IGFilesLocation location, String path, int maxTokens) throws IOException {
				throw new IOException("not a presentation");
			}
		};
		final String text = GConversationFilesText.render(
				List.of(entry(1, "working-directory", "broken.pptx", "b1"), entry(1, "working-directory", "data.csv", "c1")), 3,
				Map.of("working-directory", (IGFilesLocation) work)::get, List.of(failing), 20000, 100000);
		assertTrue(text.contains("broken.pptx - written in request 2, 10 bytes\n  (its outline could not be read: not a presentation)"),
				text);
		assertTrue(text.contains("data.csv - written in request 2, 4.9 KB"), text);
	}

	@Test
	void theFilesOfTheRequestBeingAnsweredAreWrittenInThisRequest() {
		final Files presentations = new Files("presentations").with("deck.pptx", 2048, "v1").with("old.pptx", 10, "o1");
		final String text = GConversationFilesText.render(
				List.of(entry(0, "presentations", "old.pptx", "o1"), entry(4, "presentations", "deck.pptx", "v1")), 4,
				Map.of("presentations", (IGFilesLocation) presentations)::get, List.of(new Decks(1)), 20000, 100000);
		assertTrue(text.contains("presentations: deck.pptx - written in this request, 2.0 KB"), text);
		assertTrue(text.contains("presentations: old.pptx - written in request 1, 10 bytes"), text);
	}

	@Test
	void theKeeperTellsEveryWriteAndCountsThem() {
		final List<List<GConversationFile>> told = new java.util.ArrayList<>();
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper(told::add);
		keeper.written(new GLibraryFile("presentations", "decks\\a.pptx", 1, null, "v1", null));
		keeper.written(new GLibraryFile("presentations", "decks/a.pptx", 1, null, "v2", null));
		assertEquals(2, keeper.changes());
		assertEquals(2, told.size());
		assertEquals(List.of(new GConversationFile("presentations", "decks/a.pptx", "v2")), told.get(1));
		assertEquals(keeper, IGConversationFilesKeeper.of(Map.of(IGConversationFilesKeeper.TOOLS_CONTEXT_KEY, keeper)));
		assertNull(IGConversationFilesKeeper.of(Map.of()));
	}
}
