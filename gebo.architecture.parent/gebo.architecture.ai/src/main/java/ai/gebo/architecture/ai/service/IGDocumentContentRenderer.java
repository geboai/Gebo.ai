package ai.gebo.architecture.ai.service;

public interface IGDocumentContentRenderer<T> {
	public static final String STANDARD_RENDERER = "STANDARD_RENDERER";
	public static final String CUSTOM_RENDERER = "CUSTOM_RENDERER";

	public String getId();

	public Class<T> getRenderedType();

	public boolean isCanRender(T document);

	public String render(T document);
}
