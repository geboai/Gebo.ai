package ai.gebo.architecture.agents.model;

import ai.gebo.model.GUserMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IGPartialOperation<T> {
	public boolean isLastMessage();

	public GUserMessage getErrorMessage();

	public T getData();

	public static <T> IGPartialOperation<T> of(T data, boolean lastMessage) {
		return new PartialOperation<>(lastMessage, data, null);
	}

	public static <T> IGPartialOperation<T> of(T data, GUserMessage errorMessage) {
		return new PartialOperation<T>(true, data, errorMessage);
	}

	@AllArgsConstructor
	@Getter
	static class PartialOperation<T> implements IGPartialOperation<T> {
		final boolean lastMessage;
		final T data;
		final GUserMessage errorMessage;
	}
}
