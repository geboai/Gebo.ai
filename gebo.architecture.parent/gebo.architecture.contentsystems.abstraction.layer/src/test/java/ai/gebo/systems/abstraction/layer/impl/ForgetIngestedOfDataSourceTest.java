/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;

/**
 * Pins which acknowledgements a deleted data source forgets: those of every
 * content whose code starts with {@code <any knowledge base>/<project>/<data source>/},
 * the codes taken literally - not a data source whose code only starts the same,
 * nor the same data source code in another project.
 */
class ForgetIngestedOfDataSourceTest {
	private ContentHandshakeDataRepository repository;
	private GContentDispatchingEvaluatorImpl evaluator;

	@BeforeEach
	void setUp() {
		repository = mock(ContentHandshakeDataRepository.class);
		evaluator = new GContentDispatchingEvaluatorImpl();
		evaluator.chRepository = repository;
	}

	private static boolean matches(String project, String endpoint, String contentCode) {
		return Pattern.compile(GContentDispatchingEvaluatorImpl.dataSourceContentsRegex(project, endpoint))
				.matcher(contentCode).find();
	}

	@Test
	void theContentsOfTheDataSourceMatch() {
		assertTrue(matches("pj", "pj-docs", "kb/pj/pj-docs/a.txt"));
		assertTrue(matches("pj", "pj-docs", "other-kb/pj/pj-docs/folder/sub/b.md"));
		assertTrue(matches("biblioteca", "documenti-gebo.ai_v2", "kb/biblioteca/documenti-gebo.ai_v2/x.pdf"));
	}

	@Test
	void otherDataSourcesDoNotMatch() {
		assertFalse(matches("pj", "pj-docs", "kb/pj/pj-docs-2/a.txt"), "a code only starting the same");
		assertFalse(matches("pj", "pj-docs", "kb/other/pj-docs/a.txt"), "the same code in another project");
		assertFalse(matches("pj", "pj-docs", "kb/pj/pj-docs"), "the data source's root itself is no content");
		assertFalse(matches("pj", "pj-docs", "x/kb/pj/pj-docs/a.txt"), "only at the start");
		assertFalse(matches("pj", "gebo.ai", "kb/pj/geboXai/a.txt"), "a dot taken literally");
	}

	@Test
	void theRepositoryDeletesTheMatchingAcknowledgements() {
		when(repository.deleteByContentCodeMatching(GContentDispatchingEvaluatorImpl.dataSourceContentsRegex("pj",
				"pj-docs"))).thenReturn(3l);

		assertEquals(3l, evaluator.forgetIngestedOfDataSource("pj", "pj-docs"));
	}

	@Test
	void withACodeMissingNothingIsDeleted() {
		assertEquals(0l, evaluator.forgetIngestedOfDataSource(null, "pj-docs"));
		assertEquals(0l, evaluator.forgetIngestedOfDataSource("pj", null));
		verify(repository, never()).deleteByContentCodeMatching(anyString());
	}
}
