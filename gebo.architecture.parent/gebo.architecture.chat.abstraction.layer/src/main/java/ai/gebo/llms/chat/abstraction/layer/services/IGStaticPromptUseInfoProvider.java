package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptUseInfo;

public interface IGStaticPromptUseInfoProvider {
	List<GPromptUseInfo> uses();
}
