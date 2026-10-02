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
 * The test talks to an externally managed service that must accept TCP
 * connections at {@link #url()}. Only for genuinely external runtimes: a
 * dependency started by Testcontainers binds a random port and is covered by
 * {@link RequiresDocker}.
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(IntegrationTestPreconditionsExtension.class)
@Repeatable(RequiresEndpoints.class)
public @interface RequiresEndpoint {

	/** Name of the service, used in messages. */
	String name();

	/**
	 * URL of the service; {@code ${name}} and {@code ${name:default}}
	 * placeholders are resolved through {@link IntegrationTestConfig}, so
	 * declare it the same way the test resolves it.
	 */
	String url();

	/** Connect timeout in milliseconds. */
	int timeoutMillis() default 2000;
}
