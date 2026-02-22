package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptUseInfo;
import ai.gebo.architecture.ai.service.IGPromptUseInfoDao;
import ai.gebo.architecture.ai.service.IGStaticPromptUseInfoProvider;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;

@Component
@Scope("singleton")
public class GPromptUseInfoDaoImpl extends GAbstractRuntimeConfigurationDao<GPromptUseInfo>
		implements IGPromptUseInfoDao {

	public GPromptUseInfoDaoImpl(@Autowired(required = false) List<IGStaticPromptUseInfoProvider> staticProviders) {
		super(declared(staticProviders), null);

	}

	private static List<GPromptUseInfo> declared(List<IGStaticPromptUseInfoProvider> staticProviders) {
		if (staticProviders == null || staticProviders.isEmpty())
			return List.of();
		List<GPromptUseInfo> out = new ArrayList<GPromptUseInfo>();
		for (IGStaticPromptUseInfoProvider provider : staticProviders) {
			out.addAll(provider.uses());
		}
		return out;
	}

	@Override
	public GPromptUseInfo findByCode(String code) {
		return findByPredicate(x -> x.getCode().equals(code));
	}

	@Override
	public List<GPromptUseInfo> findByModule(String module) {
		return findListByPredicate(x -> x.getModule() != null && x.getModule().equals(module));
	}

}
