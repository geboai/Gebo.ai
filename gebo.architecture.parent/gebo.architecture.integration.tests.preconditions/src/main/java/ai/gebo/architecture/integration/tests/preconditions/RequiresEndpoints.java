package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/** Container of repeated {@link RequiresEndpoint} declarations. */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(IntegrationTestPreconditionsExtension.class)
public @interface RequiresEndpoints {
	RequiresEndpoint[] value();
}
