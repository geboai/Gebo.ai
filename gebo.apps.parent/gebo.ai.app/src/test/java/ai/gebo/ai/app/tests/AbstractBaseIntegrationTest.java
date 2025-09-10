/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.ai.app.tests;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
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