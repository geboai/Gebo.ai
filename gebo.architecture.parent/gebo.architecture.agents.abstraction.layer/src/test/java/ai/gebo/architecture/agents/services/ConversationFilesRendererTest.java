package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.StaticListableBeanFactory;

import ai.gebo.architecture.agents.config.ConversationFilesConfig;
import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GConversationFilesKeeper;
import ai.gebo.architecture.ai.files.GConversationFilesText;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGAILibraryFilesService;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.files.IGFilesOutliner;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.ForwardingChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.model.base.TypedInputStream;

/** What an agent is told of the files the tools of its conversation wrote. */
class ConversationFilesRendererTest {

	/** A location holding the files of a conversation, all of 1 KB. */
	static class Location implements IGFilesLocation {
		final List<String> paths;

		Location(String... paths) {
			this.paths = List.of(paths);
		}

		@Override
		public String getId() {
			return "presentations";
		}

		@Override
		public String getDescription() {
			return "presentations";
		}

		@Override
		public boolean isWritable() {
			return true;
		}

		@Override
		public List<GLibraryFile> list(String folder, boolean recursive) {
			return List.of();
		}

		@Override
		public GLibraryFile describe(String path) throws IOException {
			if (!paths.contains(path)) {
				throw new NoSuchFileException(path);
			}
			return new GLibraryFile(getId(), path, 1024, null, "v1", null);
		}

		@Override
		public GLibraryFileContent read(String path) throws IOException {
			throw new NoSuchFileException(path);
		}

		@Override
		public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) {
			throw new UnsupportedOperationException();
		}
	}

	static IChatRequestContext context(GConversationFilesKeeper keeper, List<GConversationFilesText.Entry> earlier,
			int current) {
		final IChatRequestContext base = IChatRequestContext.ChatRequestContextImpl.builder().sessionID("chat-1")
				.toolsContext(Map.of(IGConversationFilesKeeper.TOOLS_CONTEXT_KEY, keeper)).build();
		return new ForwardingChatRequestContext(base) {
			@Override
			public List<GConversationFilesText.Entry> getEarlierWrittenFiles() {
				return earlier;
			}

			@Override
			public int getCurrentRequestIndex() {
				return current;
			}
		};
	}

	static ConversationFilesRenderer renderer(ConversationFilesConfig config, IGFilesLocation location) {
		final StaticListableBeanFactory beans = new StaticListableBeanFactory();
		beans.addBean("files", new IGAILibraryFilesService() {
			@Override
			public List<IGFilesLocation> locationsOf(String chatSessionCode) {
				return List.of(location);
			}

			@Override
			public IGFilesLocation locationOf(String chatSessionCode, String locationId) {
				if (!"chat-1".equals(chatSessionCode) || !location.getId().equals(locationId)) {
					throw new IllegalArgumentException("no location " + locationId);
				}
				return location;
			}
		});
		beans.addBean("outliner", new IGFilesOutliner() {
			@Override
			public boolean outlines(String path) {
				return path.endsWith(".pptx");
			}

			@Override
			public String outline(IGFilesLocation in, String path, int maxTokens) {
				return "PPTX, 1 slide";
			}
		});
		return new ConversationFilesRenderer(config, beans.getBeanProvider(IGAILibraryFilesService.class),
				beans.getBeanProvider(IGFilesOutliner.class));
	}

	@Test
	void theEarlierFilesAndThoseOfThisRequestAreToldWithTheirOutlines() {
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		keeper.written(new GLibraryFile("presentations", "new.pptx", 1024, null, "v1", null));
		final IChatRequestContext context = context(keeper,
				List.of(new GConversationFilesText.Entry(0, new GConversationFile("presentations", "old.pptx", "v1"))), 2);
		assertEquals(2, ConversationFilesRenderer.entries(context).size());
		final String text = renderer(new ConversationFilesConfig(), new Location("old.pptx", "new.pptx"))
				.render(context, 100_000);
		assertTrue(text.contains("presentations: new.pptx - written in this request, 1.0 KB\n  PPTX, 1 slide"), text);
		assertTrue(text.contains("presentations: old.pptx - written in request 1"), text);
		assertTrue(text.indexOf("new.pptx") < text.indexOf("old.pptx"), text);
	}

	@Test
	void nothingIsToldWithNoFilesOrWhenDisabled() {
		final ConversationFilesConfig disabled = new ConversationFilesConfig();
		disabled.setEnabled(false);
		final GConversationFilesKeeper keeper = new GConversationFilesKeeper();
		keeper.written(new GLibraryFile("presentations", "new.pptx", 1024, null, "v1", null));
		assertNull(renderer(disabled, new Location("new.pptx")).render(context(keeper, List.of(), 0), 100_000));
		assertNull(renderer(new ConversationFilesConfig(), new Location())
				.render(context(new GConversationFilesKeeper(), List.of(), 0), 100_000));
	}

	@Test
	void aPromptWithoutTheFilesPlaceholderGetsTheRulesSlotForThem() {
		final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setSystemPromptTemplate("You are an agent.");
		assertTrue(AgentAdditionalRules.withSlot(prompt, "", true).getSystemPromptTemplate()
				.contains("{" + AgentPromptTemplateParams.ADDITIONAL_RULES_SLOT_TEMPLATE_PARAM + "}"));
		assertEquals("You are an agent.", AgentAdditionalRules.withSlot(prompt, "", false).getSystemPromptTemplate());
	}
}
