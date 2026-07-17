package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportServiceRepositoryPattern;

@Service
public class GRankerModelConfigurationSupportServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGRankerModelConfigurationSupportService>
		implements IGRankerModelConfigurationSupportServiceRepositoryPattern {

	public GRankerModelConfigurationSupportServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IGRankerModelConfigurationSupportService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGRankerModelConfigurationSupportService x) {
		// Handlers are looked up by the model type code they declare - the same key the
		// configurations carry in modelTypeCode. getId() defaults to the class name for
		// any handler that does not override it, which makes it unfindable by type code.
		return x.getType().getCode();
	}

}
