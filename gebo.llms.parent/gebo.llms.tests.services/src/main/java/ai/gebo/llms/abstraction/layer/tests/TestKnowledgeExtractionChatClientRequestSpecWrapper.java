package ai.gebo.llms.abstraction.layer.tests;

import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.template.TemplateRenderer;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestKnowledgeExtractionChatClientRequestSpecWrapper implements ChatClientRequestSpec {
	final Prompt prompt;
	private final ChatClientRequestSpec wrapped;
	private final TestKnowledgeExtractionModelConfiguration configuration;
	final List<Message> messages = new ArrayList<>();
	final List<Advisor> advisors = new ArrayList<>();

	private <T> void add(List<T> list, T[] array) {
		if (array != null) {
			for (T d : array) {
				if (d != null)
					list.add(d);
			}
		}
	}

	private <T> void add(List<T> list, List<T> array) {
		for (T d : array) {
			if (d != null)
				list.add(d);
		}
	}

	@Override
	public Builder mutate() {

		return wrapped.mutate();
	}

	@Override
	public ChatClientRequestSpec advisors(Consumer<AdvisorSpec> consumer) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(prompt, wrapped.advisors(consumer),
				configuration);
	}

	@Override
	public ChatClientRequestSpec advisors(Advisor... advisors) {
		add(this.advisors, advisors);
		return this;
	}

	@Override
	public ChatClientRequestSpec advisors(List<Advisor> advisors) {
		add(this.advisors, advisors);
		return this;
	}

	@Override
	public ChatClientRequestSpec messages(Message... messages) {
		add(this.messages, messages);
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(prompt, wrapped.messages(messages),
				configuration);
	}

	@Override
	public ChatClientRequestSpec messages(List<Message> messages) {
		add(this.messages, messages);
		return this;
	}

	@Override
	public <T extends ChatOptions> ChatClientRequestSpec options(T options) {

		return wrapped.options(options);
	}

	@Override
	public ChatClientRequestSpec toolNames(String... toolNames) {
		return this;
	}

	@Override
	public ChatClientRequestSpec tools(Object... toolObjects) {
		return this;
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(ToolCallback... toolCallbacks) {
		return this;
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(List<ToolCallback> toolCallbacks) {
		return this;
	}

	@Override
	public ChatClientRequestSpec toolCallbacks(ToolCallbackProvider... toolCallbackProviders) {
		return this;
	}

	@Override
	public ChatClientRequestSpec toolContext(Map<String, Object> toolContext) {
		return this;
	}

	@Override
	public ChatClientRequestSpec system(String text) {
		this.messages.add(new SystemMessage(text));
		return this;
	}

	@Override
	public ChatClientRequestSpec system(Resource textResource, Charset charset) {
		this.messages.add(new SystemMessage(textResource));
		return this;
	}

	@Override
	public ChatClientRequestSpec system(Resource text) {
		this.messages.add(new SystemMessage(text));
		return this;
	}

	@Override
	public ChatClientRequestSpec system(Consumer<PromptSystemSpec> consumer) {
		return this;
	}

	@Override
	public ChatClientRequestSpec user(String text) {
		this.messages.add(new UserMessage(text));
		return this;
	}

	@Override
	public ChatClientRequestSpec user(Resource text, Charset charset) {
		this.messages.add(new UserMessage(text));
		return this;
	}

	@Override
	public ChatClientRequestSpec user(Resource text) {
		this.messages.add(new UserMessage(text));
		return this;
	}

	@Override
	public ChatClientRequestSpec user(Consumer<PromptUserSpec> consumer) {
		return this;
	}

	@Override
	public ChatClientRequestSpec templateRenderer(TemplateRenderer templateRenderer) {
		return this;
		
	}

	@Override
	public CallResponseSpec call() {

		return new TestKnowledgeExtractionCallResponseSpecWrapper(wrapped.call(), configuration, this);
	}

	@Override
	public StreamResponseSpec stream() {
		return wrapped.stream();
	}

}
