package ai.gebo.architecture.ai.service;

import java.util.List;

import ai.gebo.architecture.ai.model.GPromptUseInfo;

public interface IGStaticPromptUseInfoProvider {
	List<GPromptUseInfo> uses();
}
