/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Guards the JVM options the installers hand to the service launcher.
 *
 * These live in the packaging profiles of this module's pom, so no other test
 * ever looks at them - and two of the mistakes they can carry do not show up as a
 * failing build, they show up as a service that will not start on a customer's
 * machine:
 *
 * <ul>
 * <li>a {@code -Xlog} file whose directory is not part of the installed layout.
 * The JVM does not create it and does not carry on without the log: it reports
 * {@code Could not create the Java Virtual Machine} and exits, so the Windows
 * service fails outright;</li>
 * <li>{@code -XX:+AggressiveHeap}, which silently replaces G1 with the throughput
 * collector, and {@code -XX:+PrintGCDetails}, which is deprecated and re-routes
 * the whole GC stream to stdout instead of the configured log file. The project's
 * own compose file documents both as mistakes, while the packaging profiles were
 * still passing them.</li>
 * </ul>
 *
 * The heap SIZE is deliberately not asserted: Gebo.ai is a server application and
 * sizing it is a deployment decision.
 */
public class WindowsInstallerLaunchOptionsTest {

	/** This module's pom, which carries the deb, rpm and msi packaging profiles. */
	private static final Path POM = Path.of("pom.xml");

	/** Root of the tree the MSI installs as {@code $APPDIR\instance}. */
	private static final Path MSI_INSTANCE = Path.of("src", "packaging", "msi", "instance");

	/** Every {@code <option>} value declared in the packaging profiles. */
	private static List<String> jvmOptions;

	/**
	 * Extracts the declared JVM options from the pom.
	 *
	 * Only {@code <option>} elements are read, never the raw text, so the comments
	 * that explain why a flag was removed cannot make this test pass or fail.
	 *
	 * @throws IOException if the pom cannot be read
	 */
	@BeforeAll
	public static void readDeclaredJvmOptions() throws IOException {
		assertTrue(Files.isRegularFile(POM), "Expected this module's pom at " + POM.toAbsolutePath());
		String pom = Files.readString(POM);
		jvmOptions = new ArrayList<>();
		Matcher matcher = Pattern.compile("<option>(.*?)</option>", Pattern.DOTALL).matcher(pom);
		while (matcher.find()) {
			jvmOptions.add(matcher.group(1).trim());
		}
		assertFalse(jvmOptions.isEmpty(), "The packaging profiles must declare JVM options");
	}

	/**
	 * Neither of the two flags the project documents as mistakes may be passed.
	 */
	@Test
	public void testTheDiscouragedGcFlagsAreNotPassed() {
		List<String> offenders = jvmOptions.stream()
				.filter(x -> x.contains("AggressiveHeap") || x.contains("PrintGCDetails")).toList();
		assertTrue(offenders.isEmpty(),
				"-XX:+AggressiveHeap replaces G1 with the throughput collector and -XX:+PrintGCDetails is deprecated "
						+ "and sends the GC stream to stdout instead of the log file. Neither may be passed by an "
						+ "installer; found: " + offenders);
	}

	/**
	 * A heap ceiling must be set, since the default would be a fraction of whatever
	 * machine the installer happens to land on.
	 */
	@Test
	public void testAHeapCeilingIsConfigured() {
		assertTrue(jvmOptions.stream().anyMatch(x -> x.startsWith("-Xmx")),
				"The installers must set an explicit -Xmx; the JVM default is a share of the host's RAM, which makes "
						+ "the footprint of an installation unpredictable");
	}

	/**
	 * Every {@code -Xlog} file must sit in a directory the MSI actually installs.
	 *
	 * This is the assertion that matters most: getting it wrong produces an
	 * installer whose service never starts, and nothing else in the build notices.
	 */
	@Test
	public void testEveryMsiLogFileDirectoryIsPartOfTheInstalledLayout() {
		// Matches the instance-relative directory of an -Xlog file under $APPDIR,
		// e.g. -Xlog:gc:file=$APPDIR\instance\logs\gc.log:...
		Pattern underAppDir = Pattern.compile("\\$APPDIR\\\\instance\\\\([^\\\\]+)\\\\[^\\\\:]+");
		List<String> missing = new ArrayList<>();
		int checked = 0;
		for (String option : jvmOptions) {
			if (!option.startsWith("-Xlog:")) {
				continue;
			}
			Matcher matcher = underAppDir.matcher(option);
			if (!matcher.find()) {
				continue;
			}
			checked++;
			Path required = MSI_INSTANCE.resolve(matcher.group(1));
			if (!Files.isDirectory(required)) {
				missing.add(option + " needs " + required);
			}
		}
		assertTrue(checked > 0,
				"Expected the MSI profile to log GC to a file under $APPDIR\\instance; none was found, so this guard "
						+ "would silently pass");
		assertTrue(missing.isEmpty(),
				"The JVM refuses to start when it cannot open its -Xlog file, which makes the installed Windows "
						+ "service fail outright. These directories must ship under src/packaging/msi/instance: "
						+ missing);
	}
}
