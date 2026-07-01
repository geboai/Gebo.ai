package ai.gebo.architecture.agents.services;

public interface IGAgentsNetworkCallerProxy<InputType, OutputType> {
	

	public Class<InputType> getInputType();

	public Class<OutputType> getOutputType();

	public OutputType call(InputType input) throws AgentException;

	public String getAdaptedNetworkServiceId();

	public String getId();

}
