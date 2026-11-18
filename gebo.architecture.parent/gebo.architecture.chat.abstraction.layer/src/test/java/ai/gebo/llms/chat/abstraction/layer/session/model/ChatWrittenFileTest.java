package ai.gebo.llms.chat.abstraction.layer.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIResourceType;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;

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
}
