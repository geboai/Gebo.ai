package ai.gebo.llms.abstraction.layer.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.core.ParameterizedTypeReference;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestKnowledgeExtractionCallResponseSpecWrapper implements CallResponseSpec {
	private final CallResponseSpec wrapped;
	private final TestKnowledgeExtractionModelConfiguration configuration;

	@Override
	public <T> T entity(ParameterizedTypeReference<T> type) {

		throw new RuntimeException(
				"The method entity(ParameterizedTypeReference<T>) has been called but must not be called by design");
	}

	@Override
	public <T> T entity(StructuredOutputConverter<T> structuredOutputConverter) {
		throw new RuntimeException(
				"The method entity(StructuredOutputConverter<T>) has been called but must not be called by design");
	}

	@Override
	public <T> T entity(Class<T> type) {
		if (!configuration.getResponseObjects().containsKey(type))
			throw new RuntimeException("The required object value of type=> " + type.getName()
					+ " has not been loaded before calling the responseEntity(..) method");
		T data = (T) configuration.getResponseObjects().get(type);
		return data;
	}

	@Override
	public ChatClientResponse chatClientResponse() {

		return wrapped.chatClientResponse();
	}

	@Override
	public ChatResponse chatResponse() {

		return wrapped.chatResponse();
	}

	@Override
	public String content() {

		return "Here's you bloody response !!!";
	}

	@Override
	public <T> ResponseEntity<ChatResponse, T> responseEntity(Class<T> type) {
		if (!configuration.getResponseObjects().containsKey(type))
			throw new RuntimeException("The required object value of type=> " + type.getName()
					+ " has not been loaded before calling the responseEntity(..) method");
		T data = (T) configuration.getResponseObjects().get(type);
		ChatResponse resp = new ChatResponse(List.of());
		return new ResponseEntity<ChatResponse, T>(resp, data);
	}

	@Override
	public <T> ResponseEntity<ChatResponse, T> responseEntity(ParameterizedTypeReference<T> type) {
		throw new RuntimeException(
				"The method responseEntity(ParameterizedTypeReference<T> ) has been called but must not be called by design");
	}

	@Override
	public <T> ResponseEntity<ChatResponse, T> responseEntity(StructuredOutputConverter<T> structuredOutputConverter) {
		throw new RuntimeException(
				"The method responseEntity(StructuredOutputConverter<T> ) has been called but must not be called by design");
	}

}
