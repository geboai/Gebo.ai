package ai.gebo.systems.abstraction.layer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DataStructureJoinUtils {
	/*********************************************************************************************
	 * Join the 2nd eventually provided value with the eventual present 1st parameter values without
	 * duplicated assigning them to the consumer on the 3rd value.
	 * @param data
	 * @param supplier
	 * @param consumer
	 * @param <T>
	 */
	public static <T> void join(List<T> data, Supplier<List<T>> supplier, Consumer<List<T>> consumer) {
		List<T> actualValue = supplier.get();
		final List<T> setValue = new ArrayList<T>();
		if (actualValue != null && !actualValue.isEmpty()) {
			setValue.addAll(actualValue);
		}
		if (data != null && !data.isEmpty()) {
			data.forEach((T value) -> {
				if (!setValue.contains(value)) {
					setValue.add(value);
				}
			});
		}
		consumer.accept(setValue);
	}

	public static <T> boolean doneCopy(List<T> list, Consumer<List<T>> consumer) {
		boolean _doneCopy = false;
		if (list != null && !list.isEmpty()) {
			consumer.accept(list);
			_doneCopy = true;
		}
		return _doneCopy;
	}

}
