package ai.gebo.architecture.environment.runtime;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.environment.GeboApplicationArchitecture;

@Component
@Scope("singleton")
public class RuntimeArchitectureCheck {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public RuntimeArchitectureCheck(
			@Autowired(required = false) List<GeboApplicationArchitecture> architectureConfigs) {
		if (architectureConfigs == null || architectureConfigs.isEmpty()) {
			LOGGER.error("No GeboApplicationArchitecture bean declared, the architecture cannot start");
			System.exit(-1);
		}
		if (architectureConfigs.size() > 1) {
			LOGGER.error("Multiple GeboApplicationArchitecture declared, the architecture cannot start");
			System.exit(-1);
		}
		if (architectureConfigs.get(0).getArchitecture() == null) {
			LOGGER.error("GeboApplicationArchitecture bean declared, but no architecture type set");
			System.exit(-1);
		}
		LOGGER.info("*************************************************************************************");
		LOGGER.info("Architecture type:" + architectureConfigs.get(0).getArchitecture().name());
		LOGGER.info("*************************************************************************************");
	}
}
