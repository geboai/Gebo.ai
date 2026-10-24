package ai.gebo.architecture.agents.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentsNetworkServiceFactory<InputType, OutputType, ServiceType extends IGAgentsNetworkService> {
	Logger NETWORK_SERVICE_FACTORY_LOGGER = LoggerFactory.getLogger(IGAgentsNetworkServiceFactory.class);

	public String getId();

	public String getDescription();

	public boolean canHandle(Class<IGAgentsNetworkService> agentNetworkService);

	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	public ServiceType create(GAgentsNetwork network, INotificationSink notificationSink, Class<InputType> inputType,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws NetworkOfAgentsException;

	/**
	 * The same service, its runs collaborating in the sessions the given factory
	 * builds instead of in sessions of their own (see
	 * {@link IAgentsCollaborationSessionContextFactory}).
	 * <p>
	 * Every factory inherits this as it is: the service is created exactly as
	 * before, and the session factory is handed to it when it is one that accepts
	 * one — which every service built on
	 * {@link GAbstractAgentsNetworkService} is. A null session factory, or a
	 * service that does not accept one, leaves the service to own its sessions, so
	 * calling this is never worse than calling
	 * {@link #create(GAgentsNetwork, INotificationSink, Class, Class, ReactiveIdentityUtil)}.
	 *
	 * @param sessionContextFactory who builds the sessions of this service's runs
	 */
	public default ServiceType create(GAgentsNetwork network, INotificationSink notificationSink,
			Class<InputType> inputType, Class<OutputType> outputType, ReactiveIdentityUtil runAs,
			IAgentsCollaborationSessionContextFactory sessionContextFactory) throws NetworkOfAgentsException {
		final ServiceType service = create(network, notificationSink, inputType, outputType, runAs);
		if (sessionContextFactory == null) {
			return service;
		}
		if (service instanceof IGSessionContextFactoryAware aware) {
			aware.setSessionContextFactory(sessionContextFactory);
			if (NETWORK_SERVICE_FACTORY_LOGGER.isDebugEnabled()) {
				NETWORK_SERVICE_FACTORY_LOGGER.debug("Network service:" + (service != null ? service.getId() : null)
						+ " of network:" + (network != null ? network.getCode() : null)
						+ " will collaborate in the sessions built from outside");
			}
		} else {
			// Not a failure: the caller asked for its own sessions and this service does not
			// take them, so its runs go on owning theirs. Said once, where it happens,
			// because a caller expecting to be given the session back would otherwise be
			// left wondering why it never is.
			NETWORK_SERVICE_FACTORY_LOGGER.warn("Network service:" + (service != null ? service.getId() : null)
					+ " of network:" + (network != null ? network.getCode() : null)
					+ " does not accept a session factory: its runs own their sessions");
		}
		return service;
	}
}
