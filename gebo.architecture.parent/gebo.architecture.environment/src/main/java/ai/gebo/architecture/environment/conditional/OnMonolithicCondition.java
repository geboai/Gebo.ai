package ai.gebo.architecture.environment.conditional;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.GeboApplicationArchitecture.ArchitectureType;

public class OnMonolithicCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, GeboApplicationArchitecture> beans =
				context.getBeanFactory().getBeansOfType(GeboApplicationArchitecture.class);
		return beans.values().stream()
				.anyMatch(a -> a.getArchitecture() == ArchitectureType.MONOLITHIC);
	}
}