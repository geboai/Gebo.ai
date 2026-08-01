package ai.gebo.architecture.environment.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

/**
 * Matches on the microservices distribution. See {@link OnMonolithicCondition}
 * for why this checks classpath presence of the declaration class rather than
 * bean-factory contents.
 */
public class OnMicroservicesCondition implements Condition {

	private static final String MICROSERVICES_DECLARATION_CLASS = "ai.gebo.architecture.environment.microservices.MicroservicesArchitectureDeclaration";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return ClassUtils.isPresent(MICROSERVICES_DECLARATION_CLASS, context.getClassLoader());
	}
}