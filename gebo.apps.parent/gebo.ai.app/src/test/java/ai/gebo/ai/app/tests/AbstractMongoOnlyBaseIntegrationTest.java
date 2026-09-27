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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicMongoOnlyIntegrationTests;
import ai.gebo.monolithic.app.Main;

/**
 * {@link AbstractBaseIntegrationTest} for the single-dependency perimeter: the
 * whole monolith boots with MongoDB as its only companion service, and the
 * vectors live in the embedded store.
 *
 * It is a sibling of {@link AbstractBaseIntegrationTest} rather than a subclass
 * because the two differ exactly where it matters - the base class they inherit
 * their container perimeter from - while everything a test needs on top of it
 * (the bundled fixtures, the authenticated admin context) is identical and is
 * repeated here.
 */
@SpringBootTest(classes = Main.class)
public abstract class AbstractMongoOnlyBaseIntegrationTest extends AbstractGeboMonolithicMongoOnlyIntegrationTests {

	// Paths to various test files used in the integration tests
	public static final String TEST_001_PDF_FILE = "TEST-CONTENTS-001/v4man.pdf";
	public static final String TEST_001_DOCX_FILE = "TEST-CONTENTS-001/demo.docx";
	public static final String TEST_001_ODT_FILE = "TEST-CONTENTS-001/file-sample_1MB.odt";
	public static final String TEST_001_DOC_FILE = "TEST-CONTENTS-001/file-sample_500kB.doc";
	public static final String TEST_001_XLS_FILE = "TEST-CONTENTS-001/file_example_XLS_5000.xls";
	public static final String TEST_001_XLSX_FILE = "TEST-CONTENTS-001/file_example_XLSX_5000.xlsx";
	public static final String TEST_001_WRONG_FILE_FORMAT = "TEST-CONTENTS-001/wrong-file-format.pdf";

	/**
	 * The same corpus the other ingestion tests use. One of the seven is
	 * deliberately a broken file, so an ingestion of this list is expected to
	 * produce six documents.
	 */
	public static final List<String> ALL_DATA_FILES = List.of(TEST_001_DOC_FILE, TEST_001_DOCX_FILE, TEST_001_ODT_FILE,
			TEST_001_PDF_FILE, TEST_001_WRONG_FILE_FORMAT, TEST_001_XLS_FILE, TEST_001_XLSX_FILE);

	/**
	 * Impersonates the platform admin user for the duration of each test, so the
	 * run has an authenticated ADMIN+USER context instead of an anonymous one.
	 */
	@BeforeEach
	public void impersonateAdminUser() {
		List<SimpleGrantedAuthority> authorities = ALL_ROLES.stream().map(SimpleGrantedAuthority::new).toList();
		Authentication authentication = new UsernamePasswordAuthenticationToken(DEFAULT_ALL_ROLES_USER, "NOPASSWORD",
				authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@AfterEach
	public void clearImpersonatedAdminUser() {
		SecurityContextHolder.clearContext();
	}
}
