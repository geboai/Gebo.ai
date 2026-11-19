package ai.gebo.llms.chat.abstraction.layer.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIResourceType;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;
import ai.gebo.model.base.TypedInputStream;

/**
 * The files the tools of a conversation wrote, as its readers are shown them:
 * once each, with the requests that wrote them, and downloadable only while
 * the conversation holds them as resources of their own.
 */
class ChatWrittenFileTest {

	static GConversationFile file(String location, String path, String version) {
		return new GConversationFile(location, path, version);
	}

	static ChatInteractions answer(GConversationFile... written) {
		final ChatInteractions interaction = new ChatInteractions();
		final GeboTemplatedChatResponse response = new GeboTemplatedChatResponse();
		response.setWrittenFiles(written.length > 0 ? new ArrayList<>(List.of(written)) : null);
		interaction.setResponse(response);
		return interaction;
	}

	static AIToolsAndResourcesLibraryInstance library(String typeId, boolean downloadable, String... resourceIds) {
		final AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(typeId);
		final AIResourceType resourceType = new AIResourceType();
		resourceType.setDownloadable(downloadable);
		final List<AIResource> resources = new ArrayList<>();
		for (String id : resourceIds) {
			final AIResource resource = new AIResource();
			resource.setId(id);
			resource.setResourceType(resourceType);
			resources.add(resource);
		}
		final AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
		instance.setLibraryType(type);
		instance.setResources(resources);
		return instance;
	}

	@Test
	void eachFileOnceWithTheRequestsThatWroteItAndTheLastVersion() {
		final GUserChatSession conversation = new GUserChatSession();
		conversation.getInteractions().add(answer(file("presentations-folder", "deck.pptx", "v1")));
		conversation.getInteractions().add(answer());
		final ChatInteractions unanswered = new ChatInteractions();
		conversation.getInteractions().add(unanswered);
		conversation.getInteractions().add(answer(file("spreadsheets-folder", "sales.xlsx", "s1"),
				file("presentations-folder", "deck.pptx", "v2"), file("presentations-folder", null, "x")));
		final List<ChatWrittenFile> files = ChatWrittenFile.of(conversation);
		assertEquals(2, files.size());
		assertEquals("deck.pptx", files.get(0).getPath());
		assertEquals(List.of(1, 4), files.get(0).getRequests());
		assertEquals("v2", files.get(0).getVersion());
		assertEquals(4, files.get(0).lastRequest());
		assertEquals("sales.xlsx", files.get(1).getPath());
		assertEquals(List.of(4), files.get(1).getRequests());
		assertNull(files.get(1).getDownloadable());
		assertTrue(ChatWrittenFile.of(null).isEmpty());
		assertTrue(ChatWrittenFile.of(new GUserChatSession()).isEmpty());
	}

	@Test
	void downloadableOnlyAsAResourceOfItsOwnStillHeld() {
		final GUserChatSession conversation = new GUserChatSession();
		conversation.getInteractions().add(answer(file("presentations-folder", "decks/q3.pptx", "v1"),
				file("spreadsheets-folder", "gone.xlsx", "g1"), file("working-directory", "notes.txt", "n1"),
				file("text-documents-folder", "draft.docx", "d1")));
		final List<ChatWrittenFile> files = ChatWrittenFile.markDownloadable(ChatWrittenFile.of(conversation),
				List.of(library("presentations-folder", true, "decks\\q3.pptx"),
						library("spreadsheets-folder", true, "other.xlsx"), library("working-directory", true, "files"),
						library("text-documents-folder", false, "draft.docx")));
		assertEquals(List.of(true, false, false, false),
				files.stream().map(ChatWrittenFile::getDownloadable).toList());
		assertEquals(List.of(false, false, false, false), ChatWrittenFile
				.markDownloadable(ChatWrittenFile.of(conversation), null).stream().map(ChatWrittenFile::getDownloadable)
				.toList());
	}

	/** A location of files known by path, with their version; or one that cannot be read. */
	static IGFilesLocation location(String id, boolean readable, String... pathsAndVersions) {
		final Map<String, GLibraryFile> files = new HashMap<>();
		for (int index = 0; index + 1 < pathsAndVersions.length; index += 2) {
			files.put(pathsAndVersions[index],
					new GLibraryFile(id, pathsAndVersions[index], 10, null, pathsAndVersions[index + 1], null));
		}
		return new IGFilesLocation() {
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
				if (!readable) {
					throw new IOException("the share is offline");
				}
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
		};
	}

	@Test
	void eachFileSaysHowItIsNowAgainstWhatTheToolsLeft() {
		final GUserChatSession conversation = new GUserChatSession();
		conversation.getInteractions().add(answer(file("presentations-folder", "deck.pptx", "v1"),
				file("presentations-folder", "edited.pptx", "e1"), file("presentations-folder", "gone.pptx", "g1"),
				file("presentations-folder", "unversioned.pptx", null), file("working-directory", "notes/a.txt", "n1"),
				file("spreadsheets-folder", "sales.xlsx", "s1"), file("text-documents-folder", "draft.docx", "d1")));
		final Map<String, IGFilesLocation> held = Map.of(
				"presentations-folder",
				location("presentations-folder", true, "deck.pptx", "v1", "edited.pptx", "by-hand",
						"unversioned.pptx", "u7"),
				"working-directory", location("working-directory", true, "notes/a.txt", "n1"),
				"spreadsheets-folder", location("spreadsheets-folder", false));
		final List<ChatWrittenFile> files = ChatWrittenFile.markState(ChatWrittenFile.of(conversation), held::get);
		// the text documents folder is no longer held: its file is gone with it
		assertEquals(List.of(ChatWrittenFile.State.AS_WRITTEN, ChatWrittenFile.State.CHANGED,
				ChatWrittenFile.State.GONE, ChatWrittenFile.State.AS_WRITTEN, ChatWrittenFile.State.AS_WRITTEN,
				ChatWrittenFile.State.UNKNOWN, ChatWrittenFile.State.GONE),
				files.stream().map(ChatWrittenFile::getState).toList());
		assertEquals(List.of(ChatWrittenFile.State.GONE),
				ChatWrittenFile.markState(ChatWrittenFile.of(conversation), null).stream()
						.map(ChatWrittenFile::getState).distinct().toList());
		assertEquals(List.of(ChatWrittenFile.State.UNKNOWN), ChatWrittenFile.markState(ChatWrittenFile.of(conversation), id -> {
			throw new IllegalStateException("no files service");
		}).stream().map(ChatWrittenFile::getState).distinct().toList());
	}
}
