package ai.gebo.architecture.patterns;

public interface IGConditionedImplementationProvider<ParamType, Handler extends IGConditionedImplementation<ParamType>>
		extends IGImplementationsRepositoryPattern<Handler> {
	public default Handler handlerOf(ParamType param) {
		Handler handler = findImplementation(x -> x.isHandlerFor(param));
		if (handler == null)
			return defaultHandler();
		return handler;

	}

	public Handler defaultHandler();
}
