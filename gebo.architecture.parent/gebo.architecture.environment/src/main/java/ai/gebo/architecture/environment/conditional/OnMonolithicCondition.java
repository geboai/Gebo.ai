package ai.gebo.architecture.environment.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

/**
 * Matches on the monolithic distribution.
 *
 * <p>
 * Deliberately checks classpath presence of the monolith's own
 * {@code GeboApplicationArchitecture} declaration class, not the
 * bean-factory contents: on a plain {@code @ComponentScan}-discovered
 * {@code @Component} (as opposed to an auto-configuration import, which
 * Spring Boot can order with {@code @AutoConfiguration(before=/after=)}),
 * {@code @Conditional} is evaluated in classpath-scan order, which gives no
 * guarantee that {@code MonolithicArchitectureDeclaration}'s {@code @Bean}
 * method has already been registered - let alone instantiated - by the time
 * a sibling {@code @ConditionalOnMonolithic} component's condition runs. A
 * {@code getBeansOfType(GeboApplicationArchitecture.class)} lookup is
 * therefore order-dependent and can silently return empty, skipping the
 * component entirely (observed: {@code AbstractJobStatusEmitter} had no
 * qualifying bean in the monolith's Spring context). Classpath presence has
 * no such ordering dependency - it is available on this condition's very
 * first evaluation - and is exactly the same invariant
 * {@code MonolithicArchitectureDeclaration}'s own presence encodes: exactly
 * one of the monolithic/microservices declaration modules is ever on a given
 * distribution's classpath (enforced by the Maven dependency graph, and
 * double-checked at runtime by {@code RuntimeArchitectureCheck}).
 * </p>
 */
public class OnMonolithicCondition implements Condition {

	private static final String MONOLITHIC_DECLARATION_CLASS = "ai.gebo.architecture.environment.monolithic.MonolithicArchitectureDeclaration";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return ClassUtils.isPresent(MONOLITHIC_DECLARATION_CLASS, context.getClassLoader());
	}
}