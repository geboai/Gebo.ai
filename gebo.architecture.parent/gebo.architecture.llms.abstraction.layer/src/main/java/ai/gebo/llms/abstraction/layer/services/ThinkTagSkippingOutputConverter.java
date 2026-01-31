package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.convert.converter.Converter;

public class ThinkTagSkippingOutputConverter<T> extends BeanOutputConverter<T> {

	public ThinkTagSkippingOutputConverter(Class<T> clazz) {
		super(clazz);

	}

	@Override
	public T convert(String text) {
		text = ClientChatCallUtil.removeThinking(text);
		return super.convert(text);
	}

	@Override
	public <U> Converter<String, U> andThen(Converter<? super T, ? extends U> after) {

		return super.andThen(after);
	}

}