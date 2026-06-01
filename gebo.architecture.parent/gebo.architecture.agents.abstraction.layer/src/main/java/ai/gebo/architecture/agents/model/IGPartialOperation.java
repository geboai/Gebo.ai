package ai.gebo.architecture.agents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IGPartialOperation<T> {
	public boolean isLastMessage();

	public T getData();

	public static <T> IGPartialOperation<T> of(T data, boolean lastMessage) {
		return new PartialOperation<>(lastMessage, data);
	}

	@AllArgsConstructor
	@Getter
	static class PartialOperation<T> implements IGPartialOperation<T> {
		final boolean lastMessage;
		final T data;
	}
}
