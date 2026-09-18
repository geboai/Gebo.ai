package ai.gebo.architecture.environment.monolithic;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.environment.GeboClientsTopologyInfo;
import ai.gebo.architecture.environment.IGClientsTopologyProvider;

@Service
public class GClientsTopologyProviderImpl implements IGClientsTopologyProvider {

	@Override
	public GeboClientsTopologyInfo getTopology() {

		return GeboClientsTopologyInfo.ofMonolithic();
	}

}
