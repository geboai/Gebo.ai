/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;

/**
 * Guards the promise the Windows MSI makes: that MongoDB is the only service an
 * installation needs.
 *
 * The MSI ships {@code src/packaging/msi/instance/config/application.properties}
 * as an ACTIVE configuration - the service launcher sets
 * {@code -Duser.dir=$APPDIR\instance}, so Spring Boot reads that folder as its
 * {@code ./config/} directory and the file overrides the {@code application.yml}
 * packaged in the jar. Those packaged defaults ask for Qdrant on 6334, Neo4j on
 * 7687 and OpenSearch on 9200, none of which a Windows install is expected to
 * have, so if the shipped file ever stops switching them off the installer
 * silently goes back to needing three extra services.
 *
 * That regression is invisible in every other test - it lives in a packaging
 * resource, not in code - and it cannot be caught by building the MSI here,
 * since jpackage produces an MSI only on Windows with WiX present. Hence a plain
 * assertion on the file that gets shipped.
 */
public class WindowsInstallerMongoOnlyConfigTest {

	/**
	 * The shipped configuration, relative to the module directory surefire runs in.
	 */
	private static final Path SHIPPED_CONFIG = Path.of("src", "packaging", "msi", "instance", "config",
			"application.properties");

	/** The parsed contents of {@link #SHIPPED_CONFIG}. */
	private static Properties shipped;

	/** Its non-comment, non-blank lines, for assertions about what is active. */
	private static List<String> activeLines;

	/**
	 * Loads the shipped file once.
	 *
	 * @throws IOException if it cannot be read
	 */
	@BeforeAll
	public static void loadShippedConfiguration() throws IOException {
		assertTrue(Files.isRegularFile(SHIPPED_CONFIG),
				"The Windows installer must ship an ACTIVE application.properties at " + SHIPPED_CONFIG.toAbsolutePath()
						+ ". Without it the install falls back to the packaged application.yml, which requires "
						+ "Qdrant, Neo4j and OpenSearch.");
		shipped = new Properties();
		try (InputStream in = Files.newInputStream(SHIPPED_CONFIG)) {
			shipped.load(in);
		}
		activeLines = new ArrayList<>();
		for (String line : Files.readAllLines(SHIPPED_CONFIG)) {
			String trimmed = line.trim();
			if (!trimmed.isEmpty() && !trimmed.startsWith("#") && !trimmed.startsWith("!")) {
				activeLines.add(trimmed);
			}
		}
	}

	/**
	 * The vector store must be the embedded one, since no vector database is
	 * installed.
	 */
	@Test
	public void testVectorStoreIsTheEmbeddedOne() {
		String use = shipped.getProperty("ai.gebo.vectorstore.use");
		assertEquals(VectorStoreProduct.LOCAL.name(), use,
				"The Windows installer must select the embedded vector store; anything else needs a service that "
						+ "the install does not provide. Found: " + use);
		// Ties the packaged string to the enum, so a typo or a renamed product fails
		// here instead of at the customer's first startup.
		assertDoesNotThrow(() -> VectorStoreProduct.valueOf(use),
				"ai.gebo.vectorstore.use must name a real VectorStoreProduct, found: " + use);

		assertFalse(activeLines.stream().anyMatch(x -> x.startsWith("ai.gebo.vectorstore.qdrant.")),
				"No Qdrant connection setting may be active in the shipped configuration: "
						+ activeLines.stream().filter(x -> x.startsWith("ai.gebo.vectorstore.qdrant.")).toList());
	}

	/**
	 * Neo4j and OpenSearch must be switched off, so none of their beans are
	 * registered and nothing tries to connect.
	 */
	@Test
	public void testGraphAndFullTextStacksAreSwitchedOff() {
		assertEquals("false", shipped.getProperty("ai.gebo.neo4j.enabled"),
				"The Windows installer must disable Neo4j; the packaged default is true, which makes the graph beans "
						+ "reach bolt://localhost:7687.");
		assertEquals("false", shipped.getProperty("ai.gebo.opensearch.enabled"),
				"The Windows installer must disable OpenSearch; the packaged default is true, which makes the "
						+ "full-text beans reach https://localhost:9200.");
	}

	/**
	 * MongoDB must be enabled and pointed at a default local installation.
	 */
	@Test
	public void testMongoIsTheOnlyServiceAndMatchesADefaultWindowsInstall() {
		assertEquals("true", shipped.getProperty("ai.gebo.mongodb.enabled"),
				"MongoDB is the one service this installation uses, so it must be enabled");

		String connectionString = shipped.getProperty("ai.gebo.mongodb.connectionString");
		assertTrue(connectionString != null && connectionString.startsWith("mongodb://"),
				"A MongoDB connection string must be shipped, found: " + connectionString);
		assertTrue(connectionString.contains("localhost") || connectionString.contains("127.0.0.1"),
				"The shipped connection string must point at the local MongoDB service, found: " + connectionString);
		// A default MongoDB Windows install has access control disabled, so shipping
		// credentials would break the out-of-the-box case - and any credential baked
		// into an installer is the same in every installation anyway.
		assertFalse(connectionString.contains("@"),
				"The shipped connection string must not embed credentials, found: " + connectionString);
	}

	/**
	 * The example file must stay inert documentation: every line commented out, so
	 * it can never be mistaken for configuration that is in effect.
	 *
	 * @throws IOException if the example cannot be read
	 */
	@Test
	public void testTheExampleFileIsPurelyDocumentation() throws IOException {
		Path example = SHIPPED_CONFIG.resolveSibling("example-application.properties");
		assertTrue(Files.isRegularFile(example), "The optional-settings example must ship at " + example);
		List<String> active = new ArrayList<>();
		for (String line : Files.readAllLines(example)) {
			String trimmed = line.trim();
			if (!trimmed.isEmpty() && !trimmed.startsWith("#") && !trimmed.startsWith("!")) {
				active.add(trimmed);
			}
		}
		assertTrue(active.isEmpty(),
				"example-application.properties documents OPTIONAL settings and is not read by the application, so "
						+ "every line must stay commented out. Active lines found: " + active);
	}
}
