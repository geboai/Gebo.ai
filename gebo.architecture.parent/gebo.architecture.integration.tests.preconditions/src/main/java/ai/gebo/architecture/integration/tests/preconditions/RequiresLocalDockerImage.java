package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The test needs Docker images that are built locally rather than pulled, for
 * instance the snapshot images of the Gebo services. Implies
 * {@link RequiresDocker}.
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(IntegrationTestPreconditionsExtension.class)
public @interface RequiresLocalDockerImage {

	/**
	 * Image references; {@code ${name}} and {@code ${name:default}}
	 * placeholders are resolved through {@link IntegrationTestConfig}.
	 */
	String[] value();

	/** How to obtain the images, added to the message when one is missing. */
	String hint() default "";
}
