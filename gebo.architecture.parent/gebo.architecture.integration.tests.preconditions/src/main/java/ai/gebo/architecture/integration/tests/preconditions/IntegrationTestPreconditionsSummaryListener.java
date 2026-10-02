package ai.gebo.architecture.integration.tests.preconditions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repeats, at the end of the run, every test skipped or failed because of unmet
 * preconditions, so they are not lost in the middle of a long log. Registered
 * through {@code META-INF/services}; silent when nothing was affected.
 */
public class IntegrationTestPreconditionsSummaryListener implements TestExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestPreconditionsSummaryListener.class);

	private final List<String> skipped = new CopyOnWriteArrayList<>();
	private final List<String> failed = new CopyOnWriteArrayList<>();

	@Override
	public void executionSkipped(TestIdentifier testIdentifier, String reason) {
		if (reason != null && reason.startsWith(IntegrationTestPreconditionsExtension.REPORT_PREFIX)) {
			skipped.add(reason);
		}
	}

	@Override
	public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
		// JUnit wraps what a condition throws, so look for our exception among the causes.
		for (Throwable cause = testExecutionResult.getThrowable().orElse(null); cause != null; cause = cause
				.getCause()) {
			if (cause instanceof IntegrationTestPreconditionsNotMetException) {
				failed.add(cause.getMessage());
				break;
			}
		}
	}

	@Override
	public void testPlanExecutionFinished(TestPlan testPlan) {
		if (skipped.isEmpty() && failed.isEmpty()) {
			return;
		}
		StringBuilder summary = new StringBuilder("Integration tests not run because of unmet preconditions: ")
				.append(skipped.size()).append(" skipped, ").append(failed.size()).append(" failed");
		for (String report : skipped) {
			summary.append("\n[SKIPPED] ").append(report);
		}
		for (String report : failed) {
			summary.append("\n[FAILED] ").append(report);
		}
		LOGGER.warn(summary.toString());
		skipped.clear();
		failed.clear();
	}
}
