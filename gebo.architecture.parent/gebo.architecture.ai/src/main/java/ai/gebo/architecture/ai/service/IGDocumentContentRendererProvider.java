package ai.gebo.architecture.ai.service;

public interface IGDocumentContentRendererProvider {
	<T> IGDocumentContentRenderer<T> get(T doc);
}
