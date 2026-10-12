package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Repeatable;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The test needs every listed configuration value, each looked up by
 * {@link IntegrationTestConfig} as a JVM system property or an environment
 * variable. Tests should read the values through {@link IntegrationTestConfig}
 * too, so the check and the test agree on where they come from.
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(IntegrationTestPreconditionsExtension.class)
@Repeatable(RequiresConfigs.class)
public @interface RequiresConfig {

	/** Names of the required configuration values. */
	String[] value();

	/** What the values are, added to the message when one is missing. */
	String description() default "";
}
