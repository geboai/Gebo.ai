package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Evaluates the {@code @Requires*} preconditions of a test class or method
 * before anything runs - in particular before Spring builds a context or
 * Testcontainers starts a container - and, when some are not met:
 * <ul>
 * <li>skips it with a warning listing what is missing, by default or with
 * {@code -DtestWhatIsConfigured=true};</li>
 * <li>fails it with the same list when {@code -DtestWhatIsConfigured=false}, the
 * setting meant for environments that are supposed to be fully configured (CI
 * jobs dedicated to a suite).</li>
 * </ul>
 * Registered automatically by every {@code @Requires*} annotation. Skipping
 * keeps the build green while the skip stays visible in the surefire/failsafe
 * counts, rather than reporting a test that ran nothing as passed.
 */
public class IntegrationTestPreconditionsExtension implements ExecutionCondition {

	/** Start of every skip reason and failure message, recognised by the summary listener. */
	static final String REPORT_PREFIX = "Integration test preconditions not met for ";

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestPreconditionsExtension.class);
	private static final ConditionEvaluationResult NOTHING_DECLARED = ConditionEvaluationResult
			.enabled("no integration test preconditions declared");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		Optional<AnnotatedElement> element = context.getElement();
		List<Requirement> requirements;
		String subject;
		if (element.isPresent() && element.get() instanceof Class<?> testClass) {
			requirements = RequirementCollector.forClass(testClass);
			subject = testClass.getName();
		} else if (element.isPresent() && element.get() instanceof Method method) {
			// Class level requirements were already checked on the enclosing container.
			requirements = RequirementCollector.forMethod(method);
			subject = method.getDeclaringClass().getName() + "#" + method.getName();
		} else {
			return NOTHING_DECLARED;
		}
		if (requirements.isEmpty()) {
			return NOTHING_DECLARED;
		}
		List<String> missing = PreconditionEvaluator.missing(requirements);
		if (missing.isEmpty()) {
			return ConditionEvaluationResult.enabled("integration test preconditions met");
		}
		String report = report(subject, missing);
		if (IntegrationTestConfig.testWhatIsConfigured()) {
			LOGGER.warn("{}\nSkipped, as {} is true (the default); run with -D{}=false to make this a failure.",
					report, IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED, IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED);
			return ConditionEvaluationResult.disabled(report);
		}
		String failure = report + "\nFailing, as -D" + IntegrationTestConfig.TEST_WHAT_IS_CONFIGURED
				+ "=false; leave it unset (or true) to skip tests whose preconditions are not met.";
		LOGGER.error(failure);
		throw new IntegrationTestPreconditionsNotMetException(failure);
	}

	static String report(String subject, List<String> missing) {
		StringBuilder report = new StringBuilder(REPORT_PREFIX).append(subject).append(':');
		for (String line : missing) {
			report.append("\n  - ").append(line);
		}
		return report.toString();
	}
}
