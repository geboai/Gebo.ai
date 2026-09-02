package ai.gebo.architecture.patterns;

import java.util.List;

public abstract class GAbstractConditionedImplementationProvider<ParamType, Handler extends IGConditionedImplementation<ParamType>>
		extends GAbstractImplementationsRepositoryPattern<Handler>
		implements IGConditionedImplementationProvider<ParamType, Handler> {
	private final Handler defaultImplementation;

	public GAbstractConditionedImplementationProvider(List<Handler> implementations, Handler defaultImplementation) {
		super(implementations);
		this.defaultImplementation = defaultImplementation;
	}

	@Override
	public Handler defaultHandler() {
		
		return defaultImplementation;
	}

}
