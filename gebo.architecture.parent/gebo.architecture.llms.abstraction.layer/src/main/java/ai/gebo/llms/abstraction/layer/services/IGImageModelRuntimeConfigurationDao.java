package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;

public interface IGImageModelRuntimeConfigurationDao extends IGRuntimeModelConfigurationDao<IGConfigurableImageModel,GBaseImageModelConfig> {
	public List<GModuleUseInfo> getModuleUseInfo();
}
