package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The test needs a configuration value listing one or more existing
 * directories, separated by the platform path separator.
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(IntegrationTestPreconditionsExtension.class)
public @interface RequiresDirectories {

	/** Name of the configuration value holding the directories. */
	String value();
}
