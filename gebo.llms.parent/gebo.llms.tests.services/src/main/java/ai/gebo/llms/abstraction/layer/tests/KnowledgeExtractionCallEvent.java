package ai.gebo.llms.abstraction.layer.tests;

import java.util.List;

import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KnowledgeExtractionCallEvent {

	private final Prompt prompt;
	private final List<Message> messages;
	private final List<Advisor> advisors;

}
