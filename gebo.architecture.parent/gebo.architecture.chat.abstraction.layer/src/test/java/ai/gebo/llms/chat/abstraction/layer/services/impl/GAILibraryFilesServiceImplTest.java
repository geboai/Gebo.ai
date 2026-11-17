package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.support.StaticListableBeanFactory;

import ai.gebo.architecture.ai.files.GFolderFilesLocation;
import ai.gebo.architecture.ai.files.IGAILibraryFilesSource;
import ai.gebo.architecture.ai.files.IGConversationFilesProvider;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.model.base.TypedInputStream;

class GAILibraryFilesServiceImplTest {
	@TempDir
	Path work;

	/** A library source holding files, as a working directory does. */
	interface FilesSource extends IGAIToolsAndResourcesLibrarySource, IGAILibraryFilesSource {
	}

	private static AIToolsAndResourcesLibraryType type(String id) {
		final AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		return type;
	}

	private static AIToolsAndResourcesLibraryInstance instance(String id, AIToolsAndResourcesLibraryType type) {
		final AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
		instance.setId(id);
		instance.setLibraryType(type);
		return instance;
	}

	private UserUploadContentServerSide upload(String fileName, String relative, String content, long created)
			throws IOException {
		final Path folder = Files.createDirectories(work.resolve("SESSIONS_AREA").resolve("chat-1"));
		Files.writeString(folder.resolve(relative), content);
		final UserUploadContentServerSide upload = new UserUploadContentServerSide();
		upload.setFileName(fileName);
		upload.setRelativeFilePath(relative);
		upload.setExtension(relative.substring(relative.lastIndexOf('.')));
		upload.setUserContextCode("chat-1");
		upload.setDateCreated(new Date(created));
		return upload;
	}

	@Test
	void aConversationReachesItsLibrariesHoldingFilesThenItsUploads() throws IOException {
		final AIToolsAndResourcesLibraryType folderType = type("working-directory");
		final AIToolsAndResourcesLibraryType questionsType = type("user-questions");
		final AIToolsAndResourcesLibraryInstance folderInstance = instance("wd-1", folderType);
		final FilesSource folders = mock(FilesSource.class);
		when(folders.getManagedTypes()).thenReturn(List.of(folderType));
		when(folders.filesOf(folderInstance)).thenReturn(
				new GFolderFilesLocation("working-directory", "the working directory", work.resolve("wd-1"), true));
		final IGAIToolsAndResourcesLibrarySource questions = mock(IGAIToolsAndResourcesLibrarySource.class);
		when(questions.getManagedTypes()).thenReturn(List.of(questionsType));
		final IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries = mock(
				IGAIToolsAndResourcesLibrarySourceRepositoryPattern.class);
		when(libraries.getImplementations()).thenReturn(List.of(questions, folders));
		final IGAIToolsLibraryInstancesService instances = mock(IGAIToolsLibraryInstancesService.class);
		final Map<String, AIToolsAndResourcesLibraryInstance> held = new LinkedHashMap<>();
		held.put("user-questions", instance("q-1", questionsType));
		held.put("working-directory", folderInstance);
		when(instances.heldBy("chat-1")).thenReturn(held);

		final UserUploadContentServerSideRepository repository = mock(UserUploadContentServerSideRepository.class);
		final UserUploadContentServerSide first = upload("data.xlsx", "u1.xlsx", "first", 1);
		final UserUploadContentServerSide second = upload("data.xlsx", "u2.xlsx", "second", 2);
		when(repository.findByUserContextCode("chat-1")).thenAnswer(any -> Stream.of(second, first));
		final IGGeboConfigService configuration = mock(IGGeboConfigService.class);
		when(configuration.getGeboWorkDirectory()).thenReturn(work.toString());
		final StaticListableBeanFactory beans = new StaticListableBeanFactory();
		beans.addBean("uploads", new ChatUploadsFilesProvider(repository, configuration));

		final GAILibraryFilesServiceImpl service = new GAILibraryFilesServiceImpl(instances, libraries,
				beans.getBeanProvider(IGConversationFilesProvider.class));
		assertEquals(List.of("working-directory", "uploads"),
				service.locationsOf("chat-1").stream().map(IGFilesLocation::getId).toList());

		final IGFilesLocation folder = service.locationOf("chat-1", "working-directory");
		folder.write("copy.txt", TypedInputStream.of(new ByteArrayInputStream("x".getBytes()), null, ".txt"), null);
		assertTrue(Files.exists(work.resolve("wd-1").resolve("copy.txt")));

		final IGFilesLocation uploads = service.locationOf("chat-1", "uploads");
		assertFalse(uploads.isWritable());
		assertEquals(List.of("data.xlsx", "data (2).xlsx"), uploads.list(null, false).stream().map(f -> f.path()).toList());
		assertEquals("second", new String(uploads.read("data (2).xlsx").content().getInputStream().readAllBytes(),
				StandardCharsets.UTF_8));
		assertEquals(".xlsx", uploads.read("data.xlsx").content().getExtension());
		assertNull(uploads.describe("other.xlsx"));
		assertThrows(IllegalArgumentException.class, () -> uploads.read("other.xlsx"));
		assertThrows(UnsupportedOperationException.class, () -> uploads.write("data.xlsx", null, null));

		final IllegalArgumentException none = assertThrows(IllegalArgumentException.class,
				() -> service.locationOf("chat-1", "spreadsheets-folder"));
		assertTrue(none.getMessage().contains("working-directory"), none.getMessage());
		assertTrue(service.locationsOf(null).isEmpty());
	}
}
