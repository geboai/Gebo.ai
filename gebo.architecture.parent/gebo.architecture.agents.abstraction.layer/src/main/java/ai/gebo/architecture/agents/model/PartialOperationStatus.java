package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import lombok.Data;

@Data
public class PartialOperationStatus<T> extends OperationStatus<T> {
	protected boolean finalMessage = false;

	/**
	 * Creates an OperationStatus with the given result and a default success
	 * message.
	 * 
	 * @param <T> the type of the result
	 * @param t   the result to set
	 * @return a new OperationStatus with the result
	 */
	public static <T> PartialOperationStatus<T> of(T t) {
		PartialOperationStatus<T> o = new PartialOperationStatus<T>();
		o.setResult(t);
		o.messages.add(GUserMessage.successMessage("OK!", "Operation done successfully"));
		return o;
	}

	/**
	 * Creates an OperationStatus for a given Throwable, including an error message.
	 * 
	 * @param <T> the type of the result
	 * @param t   the Throwable to handle
	 * @return a new OperationStatus with an error message
	 */
	public static <T> PartialOperationStatus<T> of(Throwable t) {
		PartialOperationStatus<T> o = new PartialOperationStatus<T>();
		o.setResult(null);
		o.messages.add(GUserMessage.errorMessage("Exception during operation:" + t.getMessage(), t));
		return o;
	}

	/**
	 * Creates an OperationStatus with a custom error message.
	 * 
	 * @param <T>     the type of the result
	 * @param summary the summary of the error
	 * @param detail  the detail of the error
	 * @return a new OperationStatus with the error message
	 */
	public static <T> PartialOperationStatus<T> ofError(String summary, String detail) {
		PartialOperationStatus<T> os = new PartialOperationStatus<T>();
		os.messages.add(GUserMessage.errorMessage(summary, detail));
		return os;
	}

	/**
	 * Creates an OperationStatus with a default error message and the provided
	 * detail.
	 * 
	 * @param <T>    the type of the result
	 * @param detail the detail of the error
	 * @return a new OperationStatus with the error message
	 */
	public static <T> OperationStatus<T> ofError(String detail) {

		return ofError("Operation failed", detail);
	}

	/**
	 * Creates an OperationStatus with a result and a list of messages.
	 * 
	 * @param <T>      the type of the result
	 * @param out      the result to set
	 * @param messages the list of messages to include
	 * @return a new OperationStatus with the result and messages
	 */
	public static <T> PartialOperationStatus<T> of(T out, List<GUserMessage> messages) {
		PartialOperationStatus<T> data = of(out);
		data.messages.clear();
		data.messages.addAll(messages);
		return data;
	}

	/**
	 * Creates an OperationStatus with a result and a single message.
	 * 
	 * @param <T>     the type of the result
	 * @param out     the result to set
	 * @param message the message to include
	 * @return a new OperationStatus with the result and message
	 */
	public static <T> PartialOperationStatus<T> of(T out, GUserMessage message) {
		PartialOperationStatus<T> data = of(out);
		data.messages.clear();
		data.messages.add(message);
		return data;
	}

}
