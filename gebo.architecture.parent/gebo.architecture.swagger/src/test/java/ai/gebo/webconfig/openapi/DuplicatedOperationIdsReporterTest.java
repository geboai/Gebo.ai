package ai.gebo.webconfig.openapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationIdCustomizer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

/**
 * Two controllers with an equal method name give two equal operationIds, which
 * springdoc numbers in its own customizer. The reporter tells them whether it
 * runs before that customizer or after it: the customizers run in no given
 * order, and the reporter used to see nothing when it ran first.
 */
class DuplicatedOperationIdsReporterTest {

	private final OpenApiCustomizer reporter = new SwaggerConfig().duplicatedOperationIdsReporter();
	private final Logger logger = (Logger) LoggerFactory.getLogger(SwaggerConfig.class);
	private final ListAppender<ILoggingEvent> appender = new ListAppender<>();

	@BeforeEach
	void listen() {
		appender.start();
		logger.addAppender(appender);
	}

	@AfterEach
	void stopListening() {
		logger.detachAppender(appender);
	}

	@Test
	void equalOperationIdsAreReportedBeforeSpringdocNumbersThem() {
		OpenAPI openApi = twoControllersWithAnEqualMethodName();

		reporter.customise(openApi);

		List<String> errors = errors();
		assertEquals(1, errors.size(), "one report");
		assertTrue(errors.get(0).contains("goOnWithout [POST /api/a/goOnWithout, POST /api/b/goOnWithout]"),
				errors.get(0));
	}

	@Test
	void numberedOperationIdsAreReportedAfterSpringdocNumberedThem() {
		OpenAPI openApi = twoControllersWithAnEqualMethodName();
		new OperationIdCustomizer().customise(openApi);

		reporter.customise(openApi);

		List<String> errors = errors();
		assertEquals(1, errors.size(), "one report");
		assertTrue(errors.get(0).contains("goOnWithout_1"), errors.get(0));
	}

	@Test
	void uniqueOperationIdsAreNotReported() {
		OpenAPI openApi = new OpenAPI().paths(new Paths()
				.addPathItem("/api/a/goOnWithout", new PathItem().post(new Operation().operationId("goOnWithout")))
				.addPathItem("/api/b/goOnWithout",
						new PathItem().post(new Operation().operationId("adminGoOnWithout"))));

		reporter.customise(openApi);

		assertTrue(errors().isEmpty(), "nothing to report");
	}

	private static OpenAPI twoControllersWithAnEqualMethodName() {
		return new OpenAPI().paths(new Paths()
				.addPathItem("/api/a/goOnWithout", new PathItem().post(new Operation().operationId("goOnWithout")))
				.addPathItem("/api/b/goOnWithout", new PathItem().post(new Operation().operationId("goOnWithout"))));
	}

	private List<String> errors() {
		return appender.list.stream().filter(e -> e.getLevel() == Level.ERROR).map(ILoggingEvent::getFormattedMessage)
				.collect(Collectors.toList());
	}
}
