package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

/**
 * Network-agent specialization of {@link GAbstractGenericalAgentService}. The agent
 * prompt template parametrization (createAgentTemplateParams, the render(...)
 * helpers, placeholder gating, etc.) now lives in the parent so that both network
 * agents and reactive agents can reuse it; this type only carries the
 * {@code <InputType, OutputType>} typing for its concrete network-agent subclasses.
 */
public abstract class GAbstractGenericalNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalAgentService {

	public GAbstractGenericalNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

}
