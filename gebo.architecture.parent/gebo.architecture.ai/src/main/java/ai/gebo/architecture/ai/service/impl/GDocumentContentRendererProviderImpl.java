package ai.gebo.architecture.ai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;

@Service
public class GDocumentContentRendererProviderImpl implements IGDocumentContentRendererProvider {
	final List<IGDocumentContentRenderer> standard;
	final List<IGDocumentContentRenderer> custom;

	public GDocumentContentRendererProviderImpl(
			@Autowired(required = false) @Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER) List<IGDocumentContentRenderer> standard,
			@Autowired(required = false) @Qualifier(IGDocumentContentRenderer.CUSTOM_RENDERER) List<IGDocumentContentRenderer> custom) {
		this.standard = standard != null ? standard : List.of();
		this.custom = custom != null ? custom : List.of();
	}

	@Override
	public <T> IGDocumentContentRenderer<T> get(T doc) {
		IGDocumentContentRenderer handler = custom.stream().filter(h -> h.isCanRender(doc)).findFirst()
				.orElse(standard.stream().filter(y -> y.isCanRender(doc)).findFirst().orElseThrow(() -> {
					throw new RuntimeException("Cannot handle document of type " + doc.getClass().getName());
				}));
		return handler;
	}

}
