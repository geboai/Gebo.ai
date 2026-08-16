/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.Transferable;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Final test of the gebo.ai.app suite: takes the {@code security-log.jsonl}
 * audit trail that all the OTHER tests in this module just produced (the
 * append-only, "Wazuh-compatible" sink wired in
 * {@code src/test/resources/logback-spring.xml}, exactly like every executable
 * module's production logback config) and proves a real Wazuh manager can
 * actually decode it - instead of only trusting our own JSON assertions, as
 * {@link SecurityAuditLoggingIntegrationTest} does.
 *
 * <p>
 * It boots the official {@code wazuh/wazuh-manager} image and drives
 * {@code /var/ossec/bin/wazuh-logtest} (the manager's own decoder/rule test
 * tool, talking to the live {@code wazuh-analysisd} over its
 * {@code queue/sockets/logtest} socket) feeding it the real audit lines, then
 * asserts that for every event:
 * </p>
 * <ul>
 * <li>Wazuh picks its built-in <b>{@code json}</b> decoder - i.e. what the
 * README promises ("point your agent at {@code security-log.jsonl} with
 * {@code log_format json} and start ingesting") holds with no custom decoder;
 * </li>
 * <li>every non-null field of the event comes back out of phase 2 as a
 * decoded, SIEM-queryable field with an unchanged value (nested objects
 * flattened by Wazuh as {@code parent.child});</li>
 * <li>the taxonomy fields Wazuh rules/dashboards would key on
 * ({@code timestamp}, {@code eventType}, {@code category}, {@code action},
 * {@code outcome}) are present on every single event.</li>
 * </ul>
 *
 * <p>
 * It also validates the file as JSON Lines in plain Java, on every line - one
 * complete JSON object per line, no prefix, no pretty-printing, no multi-line
 * records. That is the check that would catch a regression turning the file
 * unparseable for a SIEM, e.g. anything logging non-JSON (or a stack trace) on
 * the {@code security-log} logger.
 * </p>
 *
 * <p>
 * This test is deliberately NOT a {@code @SpringBootTest}: it must observe the
 * audit trail left behind by the other test classes, so it needs no
 * application context of its own. It runs last thanks to its
 * {@code wazuh-log-compatibility} tag: this module's pom excludes that tag from
 * the main {@code default-test} surefire execution and runs it in a second,
 * later {@code wazuh-security-log-compatibility-test} execution. It skips
 * itself - rather than failing - when Docker is unavailable, when the audit
 * trail is missing/empty (e.g. {@code -Dtest=...} ran a subset that logs
 * nothing), or when {@code -Dgebo.wazuh.compatibility.test.skip=true} is set to
 * avoid pulling the ~900MB Wazuh image.
 * </p>
 *
 * <p>
 * The deployment-side counterpart of this test - where the trail lives in each
 * install layout and the agent/manager configuration to actually ingest it -
 * is {@code docs/wazuh-integration.md} and {@code deploy/wazuh/}.
 * </p>
 */
@Tag("wazuh-log-compatibility")
public class WazuhSecurityLogCompatibilityTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WazuhSecurityLogCompatibilityTest.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	/** Pinned so a new Wazuh release can never silently change this test's verdict. */
	private static final String WAZUH_IMAGE = System.getProperty("gebo.wazuh.image", "wazuh/wazuh-manager:4.14.7");

	private static final String LOGTEST_BINARY = "/var/ossec/bin/wazuh-logtest";

	private static final String IN_CONTAINER_SAMPLE = "/tmp/gebo-security-log-sample.jsonl";

	/**
	 * Cap on how many events get fed to wazuh-logtest in one session: the whole
	 * file is always validated as JSON Lines in Java, but a developer running
	 * this against a long-lived (or production-sized) log does not need every
	 * line decoded to know the format is compatible.
	 */
	private static final int MAX_EVENTS_SENT_TO_WAZUH = 300;

	/**
	 * Fields every audit event must carry for a SIEM to be able to correlate,
	 * classify and alert on it - the ones Wazuh rules would match on.
	 * correlationId/sourceIp/userId/httpMethod/requestUri are deliberately NOT
	 * here: they come from MDC and are legitimately null for events raised
	 * outside an HTTP request (schedulers, background workflows, ...).
	 */
	private static final List<String> MANDATORY_EVENT_FIELDS = List.of("timestamp", "eventType", "category", "action",
			"outcome");

	private static GenericContainer<?> wazuhManager;

	/** The audit lines actually fed to wazuh-logtest, in the order they were fed. */
	private static List<String> sentEvents;

	/** wazuh-logtest's phase-2 decoding result per fed event, same order. */
	private static List<DecodedEvent> decodedEvents;

	/**
	 * wazuh-logtest's per-event report: the decoder Wazuh selected plus the
	 * fields it extracted (flattened, values stringified - that is how Wazuh
	 * would hand them to the indexer/rules).
	 */
	private record DecodedEvent(String decoderName, Map<String, String> fields) {
	}

	private static File resolveSecurityLogFile() {
		// user.dir is this module's directory during `mvn test`, and the test
		// logback config writes ${LOGS}/security-log.jsonl with LOGS=logs.
		String override = System.getProperty("gebo.security.log.file");
		return override != null ? new File(override)
				: new File(System.getProperty("user.dir"), "logs/security-log.jsonl");
	}

	@BeforeAll
	static void decodeRealAuditTrailWithWazuh() throws IOException, InterruptedException {
		assumeTrue(!Boolean.getBoolean("gebo.wazuh.compatibility.test.skip"),
				"Skipped on request (-Dgebo.wazuh.compatibility.test.skip=true)");

		File securityLog = resolveSecurityLogFile();
		assumeTrue(securityLog.isFile(), () -> "No security audit trail to check at " + securityLog
				+ " - nothing in this test run produced security events");

		List<String> lines = Files.readAllLines(securityLog.toPath(), StandardCharsets.UTF_8).stream()
				.map(String::strip).filter(line -> !line.isEmpty()).toList();
		assumeTrue(!lines.isEmpty(), () -> "The security audit trail at " + securityLog + " is empty");

		// Do this before paying for the Wazuh container: a malformed file is a
		// failure regardless of what any SIEM makes of it.
		assertEveryLineIsOneCompleteJsonObject(securityLog, lines);

		assumeTrue(DockerClientFactory.instance().isDockerAvailable(),
				"Docker is not available: cannot run wazuh-logtest in a containerized Wazuh manager");

		sentEvents = selectEventsToSend(lines);
		LOGGER.info("Checking Wazuh compatibility of {} of the {} audit events in {} using {}", sentEvents.size(),
				lines.size(), securityLog, WAZUH_IMAGE);

		wazuhManager = new GenericContainer<>(WAZUH_IMAGE)
				// The manager's own daemons are what we test against, so wait
				// until analysisd really answers on the logtest socket AND
				// picks the json decoder - the container is "running" long
				// before that (filebeat/modulesd noise makes a log-message
				// wait strategy useless here).
				.waitingFor(Wait.forSuccessfulCommand("echo '{\"geboProbe\":\"wazuh-logtest-readiness\"}' | "
						+ LOGTEST_BINARY + " 2>&1 | grep -q \"name: 'json'\"")
						.withStartupTimeout(Duration.ofMinutes(5)))
				.withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("wazuh-manager"));
		wazuhManager.start();

		wazuhManager.copyFileToContainer(Transferable.of(String.join("\n", sentEvents) + "\n"), IN_CONTAINER_SAMPLE);

		// One wazuh-logtest session for all the events (it reads one log per
		// line off stdin), and everything it reports - banner, phases, errors -
		// goes to STDERR, hence the 2>&1.
		ExecResult result = wazuhManager.execInContainer("/bin/sh", "-c",
				"cat " + IN_CONTAINER_SAMPLE + " | " + LOGTEST_BINARY + " 2>&1");
		String output = result.getStdout();
		assertEquals(0, result.getExitCode(), () -> "wazuh-logtest failed:\n" + output);

		decodedEvents = parseLogtestOutput(output);
		assertEquals(sentEvents.size(), decodedEvents.size(),
				() -> "wazuh-logtest did not report one decoding phase per submitted audit event. Raw output:\n"
						+ output);
	}

	@AfterAll
	static void stopWazuhManager() {
		if (wazuhManager != null) {
			wazuhManager.stop();
		}
	}

	/**
	 * The format contract itself (JSON Lines / NDJSON): one self-contained JSON
	 * object per line, with no logging prefix and no line breaks inside a
	 * record, which is what makes Wazuh's {@code log_format json} - and any
	 * other SIEM's line-oriented JSON reader - able to read the file at all.
	 */
	private static void assertEveryLineIsOneCompleteJsonObject(File securityLog, List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			int lineNumber = i + 1;
			JsonNode node;
			try {
				node = mapper.readTree(line);
			} catch (RuntimeException e) {
				throw new AssertionError(securityLog + ":" + lineNumber
						+ " is not parseable JSON, which breaks the whole file for a SIEM reading it as JSON Lines: "
						+ line, e);
			}
			assertTrue(node.isObject(), () -> securityLog + ":" + lineNumber
					+ " is valid JSON but not a JSON object: " + line);
		}
	}

	/**
	 * Everything when the trail is small enough (the normal case for a test
	 * run), otherwise one representative per distinct
	 * eventType/category/action/outcome combination plus the most recent
	 * events, so a big pre-existing file still gets its whole taxonomy checked.
	 */
	private static List<String> selectEventsToSend(List<String> lines) {
		if (lines.size() <= MAX_EVENTS_SENT_TO_WAZUH) {
			return lines;
		}
		Map<String, Integer> firstIndexPerTaxonomy = new LinkedHashMap<>();
		for (int i = 0; i < lines.size(); i++) {
			JsonNode node = mapper.readTree(lines.get(i));
			String taxonomy = textOrNull(node, "eventType") + "|" + textOrNull(node, "category") + "|"
					+ textOrNull(node, "action") + "|" + textOrNull(node, "outcome");
			firstIndexPerTaxonomy.putIfAbsent(taxonomy, i);
		}
		Set<Integer> selected = new TreeSet<>(firstIndexPerTaxonomy.values());
		for (int i = lines.size() - 1; i >= 0 && selected.size() < MAX_EVENTS_SENT_TO_WAZUH; i--) {
			selected.add(i);
		}
		return selected.stream().limit(MAX_EVENTS_SENT_TO_WAZUH).map(lines::get).toList();
	}

	/**
	 * Turns wazuh-logtest's human-readable report into one {@link DecodedEvent}
	 * per submitted line, in submission order. Its output per event looks like:
	 *
	 * <pre>
	 * **Phase 1: Completed pre-decoding.
	 * 	full event: '{"eventType":"secretManagement",...}'
	 *
	 * **Phase 2: Completed decoding.
	 * 	name: 'json'
	 * 	action: 'secretCreate'
	 * 	details.reason: 'expired'
	 *
	 * **Phase 3: Completed filtering (rules).
	 * 	id: '1002'
	 * </pre>
	 *
	 * Phase 3 is intentionally ignored: which (if any) of Wazuh's stock rules
	 * fires on a Gebo audit event - and whether it is deduplicated away - is
	 * not part of the log format's compatibility.
	 *
	 * <p>
	 * Each field is reported as a tab-indented {@code key: 'value'} line, but a
	 * value containing newlines (a JSON-escaped {@code \n} inside, say, a
	 * details message) is printed across several output lines - so a field is
	 * collected until the line that closes its quote, not line by line.
	 * </p>
	 */
	private static List<DecodedEvent> parseLogtestOutput(String output) {
		List<DecodedEvent> events = new ArrayList<>();
		String decoderName = null;
		Map<String, String> fields = null;
		boolean inDecodingPhase = false;
		String pendingKey = null;
		StringBuilder pendingValue = null;
		for (String rawLine : output.split("\\R")) {
			if (pendingKey != null) {
				pendingValue.append('\n').append(rawLine);
				if (!rawLine.endsWith("'")) {
					continue;
				}
				fields.put(pendingKey, pendingValue.substring(0, pendingValue.length() - 1));
				pendingKey = null;
				pendingValue = null;
				continue;
			}
			if (rawLine.startsWith("**Phase 2")) {
				decoderName = null;
				fields = new LinkedHashMap<>();
				inDecodingPhase = true;
				continue;
			}
			if (rawLine.startsWith("**Phase")) {
				// Phase 1 of the NEXT event (or this event's phase 3) closes
				// the decoding section we were collecting.
				if (inDecodingPhase) {
					events.add(new DecodedEvent(decoderName, fields));
					inDecodingPhase = false;
				}
				continue;
			}
			if (!inDecodingPhase || !rawLine.startsWith("\t")) {
				continue;
			}
			String field = rawLine.strip();
			int separator = field.indexOf(": '");
			if (separator < 0) {
				continue;
			}
			String key = field.substring(0, separator);
			String value = field.substring(separator + 3);
			if (!value.endsWith("'")) {
				// Multi-line value: keep reading until its closing quote.
				pendingKey = key;
				pendingValue = new StringBuilder(value);
				continue;
			}
			value = value.substring(0, value.length() - 1);
			if ("name".equals(key) && decoderName == null) {
				decoderName = value;
			} else {
				fields.put(key, value);
			}
		}
		if (inDecodingPhase) {
			events.add(new DecodedEvent(decoderName, fields));
		}
		return events;
	}

	@Test
	public void wazuhDecodesEveryAuditEventWithItsBuiltInJsonDecoder() {
		for (int i = 0; i < decodedEvents.size(); i++) {
			DecodedEvent decoded = decodedEvents.get(i);
			String event = sentEvents.get(i);
			assertEquals("json", decoded.decoderName(),
					() -> "Wazuh did not decode this audit event with its built-in 'json' decoder, so ingesting "
							+ "security-log.jsonl with log_format json would not yield queryable fields:\n" + event);
			assertFalse(decoded.fields().isEmpty(),
					() -> "Wazuh extracted no field at all from this audit event:\n" + event);
		}
	}

	@Test
	public void wazuhExtractsEveryAuditFieldWithAnUnchangedValue() {
		for (int i = 0; i < decodedEvents.size(); i++) {
			Map<String, String> decodedFields = decodedEvents.get(i).fields();
			String event = sentEvents.get(i);
			Map<String, String> expectedFields = new LinkedHashMap<>();
			flattenScalars(mapper.readTree(event), "", expectedFields);
			for (Map.Entry<String, String> expected : expectedFields.entrySet()) {
				assertTrue(decodedFields.containsKey(expected.getKey()),
						() -> "Wazuh's json decoder dropped the '" + expected.getKey()
								+ "' field of this audit event (it kept " + decodedFields.keySet() + "):\n" + event);
				assertEquals(expected.getValue(), decodedFields.get(expected.getKey()),
						() -> "Wazuh's json decoder altered the value of '" + expected.getKey()
								+ "' in this audit event:\n" + event);
			}
		}
	}

	@Test
	public void everyAuditEventCarriesTheTaxonomyFieldsASiemNeeds() {
		for (int i = 0; i < decodedEvents.size(); i++) {
			Map<String, String> decodedFields = decodedEvents.get(i).fields();
			String event = sentEvents.get(i);
			for (String mandatory : MANDATORY_EVENT_FIELDS) {
				String value = decodedFields.get(mandatory);
				// "null" (the literal string) is what Wazuh's json decoder makes
				// of a JSON null, i.e. of a SecurityEvent field a call site
				// forgot to set - as useless to a rule as a missing field.
				assertTrue(value != null && !value.isEmpty() && !"null".equals(value),
						() -> "This audit event reaches Wazuh without a usable '" + mandatory
								+ "' field, so no rule could classify or correlate it - fix the call site logging it "
								+ "(see its stackPoint):\n" + event);
			}
		}
	}

	/**
	 * Same flattening Wazuh's json decoder applies (nested objects joined with
	 * '.', every scalar stringified), so decoded output can be compared to the
	 * source event field by field.
	 *
	 * <p>
	 * Two Wazuh behaviours are deliberately left out of the comparison, since
	 * they are the decoder's own conventions and not something Gebo controls:
	 * a JSON {@code null} is surfaced as the literal string {@code 'null'}, and
	 * empty objects (a {@code details:{}} with nothing in it) and arrays are
	 * reported in a Python-ish form rather than as JSON.
	 * </p>
	 */
	private static void flattenScalars(JsonNode node, String prefix, Map<String, String> target) {
		node.propertyStream().forEach(property -> {
			String key = prefix.isEmpty() ? property.getKey() : prefix + "." + property.getKey();
			JsonNode value = property.getValue();
			if (value.isObject()) {
				flattenScalars(value, key, target);
			} else if (value.isNull() || value.isArray()) {
				return;
			} else {
				target.put(key, value.asString());
			}
		});
	}

	private static String textOrNull(JsonNode node, String field) {
		JsonNode value = node.get(field);
		return value == null || value.isNull() ? null : value.asString();
	}
}
