package ai.gebo.architecture.patterns;

public interface IGConditionedImplementation<ParamType> {
	public boolean isHandlerFor(ParamType param);
}
