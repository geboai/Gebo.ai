package ai.gebo.llms.abstraction.layer.tests;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.ai.chat.client.ChatClient.AdvisorSpec;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.client.ChatClient.PromptSystemSpec;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.ai.chat.client.ChatClient.StreamResponseSpec;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.template.TemplateRenderer;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestKnowledgeExtractionChatClientRequestSpecWrapper implements ChatClientRequestSpec {
	private final ChatClientRequestSpec wrapped;
	private final TestKnowledgeExtractionModelConfiguration configuration;
	@Override
	public Builder mutate() {

		return wrapped.mutate();
	}

	@Override
	public ChatClientRequestSpec advisors(Consumer<AdvisorSpec> consumer) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.advisors(consumer), configuration);
	}

	@Override
	public ChatClientRequestSpec advisors(Advisor... advisors) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.advisors(advisors), configuration);
	}

	@Override
	public ChatClientRequestSpec advisors(List<Advisor> advisors) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.advisors(advisors), configuration);
	}

	@Override
	public ChatClientRequestSpec messages(Message... messages) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.messages(messages), configuration);
	}

	@Override
	public ChatClientRequestSpec messages(List<Message> messages) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.messages(messages), configuration);
	}

	@Override
	public <T extends ChatOptions> ChatClientRequestSpec options(T options) {

		return wrapped.options(options);
	}

	@Override
	public ChatClientRequestSpec toolNames(String... toolNames) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.toolNames(toolNames), configuration);
	}

	@Override
	public ChatClientRequestSpec tools(Object... toolObjects) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.tools(toolObjects), configuration);
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(ToolCallback... toolCallbacks) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.toolCallbacks(toolCallbacks), configuration);
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(List<ToolCallback> toolCallbacks) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.toolCallbacks(toolCallbacks), configuration);
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(ToolCallbackProvider... toolCallbackProviders) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.toolCallbacks(toolCallbackProviders), configuration);
	}

	@Override
	public ChatClientRequestSpec toolContext(Map<String, Object> toolContext) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.toolContext(toolContext), configuration);
	}

	@Override
	public ChatClientRequestSpec system(String text) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.system(text), configuration);
	}

	@Override
	public ChatClientRequestSpec system(Resource textResource, Charset charset) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.system(textResource, charset), configuration);
	}

	@Override
	public ChatClientRequestSpec system(Resource text) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.system(text), configuration);
	}

	@Override
	public ChatClientRequestSpec system(Consumer<PromptSystemSpec> consumer) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.system(consumer), configuration);
	}

	@Override
	public ChatClientRequestSpec user(String text) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.system(text), configuration);
	}

	@Override
	public ChatClientRequestSpec user(Resource text, Charset charset) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.user(text, charset), configuration);
	}

	@Override
	public ChatClientRequestSpec user(Resource text) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.user(text), configuration);
	}

	@Override
	public ChatClientRequestSpec user(Consumer<PromptUserSpec> consumer) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.user(consumer), configuration);
	}

	@Override
	public ChatClientRequestSpec templateRenderer(TemplateRenderer templateRenderer) {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(wrapped.templateRenderer(templateRenderer), configuration);
	}

	@Override
	public CallResponseSpec call() {

		return new TestKnowledgeExtractionCallResponseSpecWrapper(wrapped.call(), configuration);
	}

	@Override
	public StreamResponseSpec stream() {
		return wrapped.stream();
	}

}
