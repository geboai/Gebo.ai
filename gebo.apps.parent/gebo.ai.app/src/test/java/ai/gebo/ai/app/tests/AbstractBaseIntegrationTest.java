/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.ai.app.tests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.FileCopyUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTests;
import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.monolithic.app.Main;

/**
 * AI generated comments
 * AbstractBaseIntegrationTest serves as a base class for integration tests within the Gebo monolithic application.
 * It is designed to work with Spring Boot and makes use of testcontainers for MongoDB testing.
 */
@SpringBootTest(classes = Main.class)
public abstract class AbstractBaseIntegrationTest extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	
	// Paths to various test files used in the integration tests
	public static final String TEST_001_PDF_FILE = "TEST-CONTENTS-001/v4man.pdf";
	public static final String TEST_001_DOCX_FILE = "TEST-CONTENTS-001/demo.docx";
	public static final String TEST_001_ODT_FILE = "TEST-CONTENTS-001/file-sample_1MB.odt";
	public static final String TEST_001_DOC_FILE = "TEST-CONTENTS-001/file-sample_500kB.doc";
	public static final String TEST_001_XLS_FILE = "TEST-CONTENTS-001/file_example_XLS_5000.xls";
	public static final String TEST_001_XLSX_FILE = "TEST-CONTENTS-001/file_example_XLSX_5000.xlsx";
	public static final String TEST_001_ZIP_FILE = "TEST-CONTENTS-001/TEST-CONTENTS-001.zip";
	public static final String TEST_001_WRONG_FILE_FORMAT = "TEST-CONTENTS-001/wrong-file-format.pdf";
	
	// A list of all test files utilized in the integration testing scenarios
	public static final List<String> ALL_DATA_FILES = List.of(TEST_001_DOC_FILE, TEST_001_DOCX_FILE, TEST_001_ODT_FILE,
			TEST_001_PDF_FILE, TEST_001_WRONG_FILE_FORMAT, TEST_001_XLS_FILE, TEST_001_XLSX_FILE);
	
}