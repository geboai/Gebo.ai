package ai.gebo.architecture.ai.service;

import java.util.List;

import ai.gebo.architecture.ai.model.GPromptUseInfo;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;

public interface IGPromptUseInfoDao extends IGRuntimeConfigurationDao<GPromptUseInfo> {
	public List<GPromptUseInfo> findByModule(String module);
}
