/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.integration.tests;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Base class for the integration tests of the single-dependency installation:
 * Gebo.ai with MongoDB as its ONLY companion service.
 *
 * <h2>What the perimeter is, and why</h2> MongoDB holds the application state
 * and the embedded {@code VectorStoreProduct.LOCAL} store - Spring AI's
 * SimpleVectorStore, persisted as JSON - keeps the vectors inside the work
 * directory. Nothing else runs: no Qdrant, no Neo4j, no OpenSearch - not
 * disabled-but-present, genuinely absent, since
 * {@link #containersProperties(DynamicPropertyRegistry)} of the parent never
 * starts their containers.
 *
 * This is the shape of the OSS / community installation; a deployment that needs
 * a server-backed vector store simply points the same switch at one.
 *
 * This is the shape a Windows installation wants, because MongoDB is the one
 * dependency that ships a Windows installer. The obvious alternative - letting
 * MongoDB hold the vectors too - is NOT available: {@code $vectorSearch} is
 * served by the separate {@code mongot} binary, which MongoDB publishes for
 * Linux only ("Native Windows mongot binaries are not available") and which
 * additionally requires a replica set rather than the standalone {@code mongod}
 * every Gebo.ai compose file runs. The embedded store removes the dependency
 * instead of moving it.
 *
 * <h2>How the perimeter is selected</h2> The static initializer below sets
 * {@link AbstractGeboMonolithicIntegrationTests#MONGO_ONLY_PERIMETER_PROPERTY}
 * before JUnit can ask Spring for a context, because the JVM runs it when this
 * class is loaded. Test classes run in their own fork
 * ({@code reuseForks=false}), so this never leaks into a test that does want the
 * full stack.
 *
 * The vendor selection itself goes through the ordinary
 * {@code ai.gebo.vectorstore.use} switch, the same one an installation edits in
 * its application.yml - so this test proves the architecture stayed
 * vendor-neutral rather than working around it.
 */
public abstract class AbstractGeboMonolithicMongoOnlyIntegrationTests
		extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {

	static {
		// Runs at class load time, i.e. before the Spring context is built.
		System.setProperty(MONGO_ONLY_PERIMETER_PROPERTY, "true");
	}

	/**
	 * Name of the vector store product this perimeter runs on, as
	 * {@code ai.gebo.vectorstore.use} spells it.
	 */
	public static final String EMBEDDED_VECTOR_STORE_PRODUCT = "LOCAL";

	/**
	 * Points the platform at the embedded vector store.
	 *
	 * This is a SECOND {@code @DynamicPropertySource}: Spring collects the
	 * annotated methods of the whole hierarchy, so the parent still contributes the
	 * MongoDB connection and this one only adds the vendor switch. The store's
	 * directory is deliberately left unset, so the test also exercises the default
	 * placement under {@code GEBO_WORK_DIRECTORY} - which the parent has already
	 * pointed at a throwaway temporary directory.
	 *
	 * @param registry registry the properties are added to
	 */
	@DynamicPropertySource
	public static void embeddedVectorStoreProperties(DynamicPropertyRegistry registry) {
		registry.add("ai.gebo.vectorstore.use", () -> EMBEDDED_VECTOR_STORE_PRODUCT);
	}
}
