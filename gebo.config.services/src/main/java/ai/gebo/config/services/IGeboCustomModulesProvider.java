package ai.gebo.config.services;

import java.util.List;

import ai.gebo.config.model.GeboModuleInfo;

public interface IGeboCustomModulesProvider {
	public List<GeboModuleInfo> get();
}
