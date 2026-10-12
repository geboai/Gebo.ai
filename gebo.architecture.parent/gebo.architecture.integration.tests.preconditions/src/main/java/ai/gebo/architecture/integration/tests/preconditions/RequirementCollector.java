package ai.gebo.architecture.integration.tests.preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Gathers the requirements declared on a test class or method.
 * <p>
 * The class hierarchy is walked explicitly instead of relying on
 * {@link java.lang.annotation.Inherited}: with Java's inheritance rules a
 * subclass declaring its own {@link RequiresConfig} would hide the ones of its
 * superclass, whereas a test needs the union of both. Superclass requirements
 * come first and duplicates are dropped. Composed annotations carrying
 * {@code @Requires*} meta-annotations are expanded.
 */
final class RequirementCollector {

	private RequirementCollector() {
	}

	static List<Requirement> forClass(Class<?> testClass) {
		Set<Requirement> requirements = new LinkedHashSet<>();
		collectFromType(testClass, requirements, new HashSet<>());
		return new ArrayList<>(requirements);
	}

	static List<Requirement> forMethod(Method method) {
		Set<Requirement> requirements = new LinkedHashSet<>();
		collectFromAnnotations(method.getDeclaredAnnotations(), requirements, new HashSet<>());
		return new ArrayList<>(requirements);
	}

	private static void collectFromType(Class<?> type, Set<Requirement> requirements, Set<Class<?>> visitedTypes) {
		if (type == null || type == Object.class || !visitedTypes.add(type)) {
			return;
		}
		collectFromType(type.getSuperclass(), requirements, visitedTypes);
		for (Class<?> implemented : type.getInterfaces()) {
			collectFromType(implemented, requirements, visitedTypes);
		}
		collectFromAnnotations(type.getDeclaredAnnotations(), requirements, new HashSet<>());
	}

	private static void collectFromAnnotations(Annotation[] annotations, Set<Requirement> requirements,
			Set<Class<? extends Annotation>> visitedAnnotationTypes) {
		for (Annotation annotation : annotations) {
			switch (annotation) {
			case RequiresDocker docker -> requirements.add(new Requirement.Docker());
			case RequiresConfig config -> addConfig(config, requirements);
			case RequiresConfigs configs -> {
				for (RequiresConfig config : configs.value()) {
					addConfig(config, requirements);
				}
			}
			case RequiresEndpoint endpoint -> addEndpoint(endpoint, requirements);
			case RequiresEndpoints endpoints -> {
				for (RequiresEndpoint endpoint : endpoints.value()) {
					addEndpoint(endpoint, requirements);
				}
			}
			case RequiresLocalDockerImage images -> {
				// Inspecting a local image needs the daemon: listing Docker first
				// lets the evaluator report a missing daemon once, not per image.
				requirements.add(new Requirement.Docker());
				for (String image : images.value()) {
					requirements.add(new Requirement.LocalImage(image, images.hint()));
				}
			}
			case RequiresDirectories directories -> requirements.add(new Requirement.Directories(directories.value()));
			case RequiresCustom custom -> {
				for (Class<? extends PreconditionCheck> check : custom.value()) {
					requirements.add(new Requirement.Custom(check));
				}
			}
			default -> {
				Class<? extends Annotation> type = annotation.annotationType();
				if (!type.getName().startsWith("java.") && visitedAnnotationTypes.add(type)) {
					collectFromAnnotations(type.getDeclaredAnnotations(), requirements, visitedAnnotationTypes);
				}
			}
			}
		}
	}

	private static void addConfig(RequiresConfig config, Set<Requirement> requirements) {
		for (String name : config.value()) {
			requirements.add(new Requirement.Config(name, config.description()));
		}
	}

	private static void addEndpoint(RequiresEndpoint endpoint, Set<Requirement> requirements) {
		requirements.add(new Requirement.Endpoint(endpoint.name(), endpoint.url(), endpoint.timeoutMillis()));
	}
}
