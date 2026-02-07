package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptUseInfo;

public interface IGPromptUseInfoDao extends IGRuntimeConfigurationDao<GPromptUseInfo> {
	public List<GPromptUseInfo> findByModule(String module);
}
