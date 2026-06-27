package ai.gebo.architecture.ai.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;

@Service

public class GDocumentContentRendererProviderImpl implements IGDocumentContentRendererProvider {
	private static final String DEFAULT_RENDERER_ID = "GDocumentContentRendererProviderImpl.genericRenderer";
	final List<IGDocumentContentRenderer> standard;
	final List<IGDocumentContentRenderer> custom;
	private final static Logger LOGGER = LoggerFactory.getLogger(GDocumentContentRendererProviderImpl.class);

	public GDocumentContentRendererProviderImpl(
			@Autowired(required = false) @Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER) List<IGDocumentContentRenderer> standard,
			@Autowired(required = false) @Qualifier(IGDocumentContentRenderer.CUSTOM_RENDERER) List<IGDocumentContentRenderer> custom) {
		this.standard = standard != null ? standard : List.of();
		this.custom = custom != null ? custom : List.of();
	}

	@Override
	public <T> IGDocumentContentRenderer<T> get(T doc) {
		IGDocumentContentRenderer handler = custom.stream().filter(h -> h.isCanRender(doc)).findFirst()
				.orElse(standard.stream().filter(y -> y.isCanRender(doc)).findFirst().orElse(newDefaultRenderer(doc)));
		return handler;
	}

	static final <T> IGDocumentContentRenderer<T> newDefaultRenderer(T object) {
		return new IGDocumentContentRenderer<T>() {
			@Override
			public Class<T> getRenderedType() {

				return (Class<T>) object.getClass();
			}

			public boolean isCanRender(Object document) {
				return true;
			}

			public String render(T document) {
				return genericRender(document);
			}

			@Override
			public String getId() {

				return DEFAULT_RENDERER_ID;
			}
		};
	}

	static String genericRender(Object object) {
		if (object == null) {
			return "";
		}
		Class<?> actualClass = object.getClass();
		if (!directlyImplementsToString(actualClass)) {
			LOGGER.warn("The class {} does not directy implement the toString() method", actualClass.getName());
		}
		return object.toString();
	}

	private static boolean directlyImplementsToString(Class<?> type) {
		try {
			type.getDeclaredMethod("toString");
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}
}
