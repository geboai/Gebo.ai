/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;

/**
 * The files a run wrote and generated, kept on the run's own response, are merged
 * into the response its last frame carries, each once.
 */
class ChatRunFilesMergeTest {
	private static GConversationFile file(String location, String path) {
		final GConversationFile f = new GConversationFile();
		f.setLocation(location);
		f.setPath(path);
		return f;
	}

	private static LLMGeneratedResource generated(String code) {
		final LLMGeneratedResource r = new LLMGeneratedResource();
		r.setCode(code);
		return r;
	}

	@Test
	void theRunsFilesAreAddedToTheCarriedResponseEachOnce() {
		final GeboChatResponse carried = new GeboChatResponse();
		carried.setWrittenFiles(List.of(file("working-directory", "a.md")));
		carried.setGeneratedResources(new java.util.ArrayList<>(List.of(generated("g1"))));
		final GeboChatResponse ofTheRun = new GeboChatResponse();
		ofTheRun.setWrittenFiles(List.of(file("working-directory", "a.md"), file("generated", "b.png")));
		ofTheRun.setGeneratedResources(List.of(generated("g1"), generated("g2")));

		final GeboChatResponse merged = ChatRunFiles.withFilesOfTheRun(carried, ofTheRun);
		assertSame(carried, merged, "the carried response, completed");
		assertEquals(List.of("a.md", "b.png"), merged.getWrittenFiles().stream().map(GConversationFile::getPath).toList());
		assertEquals(List.of("g1", "g2"), merged.getGeneratedResources().stream().map(LLMGeneratedResource::getCode).toList());
	}

	@Test
	void withNoCarriedResponseTheRunsOwnIsTheAnswer() {
		final GeboChatResponse ofTheRun = new GeboChatResponse();
		assertSame(ofTheRun, ChatRunFiles.withFilesOfTheRun(null, ofTheRun));
		final GeboChatResponse carried = new GeboChatResponse();
		assertSame(carried, ChatRunFiles.withFilesOfTheRun(carried, null));
		assertSame(carried, ChatRunFiles.withFilesOfTheRun(carried, carried));
	}
}
