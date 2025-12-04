package ai.gebo.llms.abstraction.layer.tests;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.prompt.Prompt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class TestKnowledgeExtractionChatClientWrapper implements ChatClient {
	private final ChatClient wrapped;
	private final TestKnowledgeExtractionModelConfiguration configuration;

	@Override
	public ChatClientRequestSpec prompt() {
		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(null, wrapped.prompt(), configuration);

	}

	@Override
	public ChatClientRequestSpec prompt(String content) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(new Prompt(content), wrapped.prompt(content),
				configuration);
	}

	@Override
	public ChatClientRequestSpec prompt(Prompt prompt) {

		return new TestKnowledgeExtractionChatClientRequestSpecWrapper(prompt, wrapped.prompt(prompt), configuration);
	}

	@Override
	public Builder mutate() {

		return wrapped.mutate();
	}

}