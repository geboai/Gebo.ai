/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.agents.config.ConversationFilesConfig;
import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GConversationFilesText;
import ai.gebo.architecture.ai.files.IGAILibraryFilesService;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.files.IGFilesOutliner;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * What an agent is told of the files the tools of its conversation wrote: those
 * of the earlier requests and those written so far in this one, each with its
 * outline as it is now (see {@link GConversationFilesText}), within the share of
 * the agent's budget of tokens {@link ConversationFilesConfig} sets.
 */
@Component
public class ConversationFilesRenderer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConversationFilesRenderer.class);
	private final ConversationFilesConfig config;
	private final ObjectProvider<IGAILibraryFilesService> files;
	private final ObjectProvider<IGFilesOutliner> outliners;

	public ConversationFilesRenderer(ConversationFilesConfig config, ObjectProvider<IGAILibraryFilesService> files,
			ObjectProvider<IGFilesOutliner> outliners) {
		this.config = config;
		this.files = files;
		this.outliners = outliners;
	}

	/** The files the request's tools wrote so far: its context's keeper's, empty when it carries none. */
	public static List<GConversationFile> writtenInThisRequest(IChatRequestContext context) {
		final IGConversationFilesKeeper keeper = context != null ? IGConversationFilesKeeper.of(context.getToolsContext())
				: null;
		return keeper != null ? keeper.getWritten() : List.of();
	}

	/** The files written in the conversation: the earlier requests', then this one's. */
	public static List<GConversationFilesText.Entry> entries(IChatRequestContext context) {
		final List<GConversationFilesText.Entry> entries = new ArrayList<>();
		if (context == null) {
			return entries;
		}
		if (context.getEarlierWrittenFiles() != null) {
			entries.addAll(context.getEarlierWrittenFiles());
		}
		final int current = currentRequest(context);
		for (GConversationFile file : writtenInThisRequest(context)) {
			entries.add(new GConversationFilesText.Entry(current, file));
		}
		return entries;
	}

	/** The index of the request being answered: past every earlier written file's when it is not known. */
	static int currentRequest(IChatRequestContext context) {
		if (context.getCurrentRequestIndex() >= 0) {
			return context.getCurrentRequestIndex();
		}
		int after = 0;
		if (context.getEarlierWrittenFiles() != null) {
			for (GConversationFilesText.Entry entry : context.getEarlierWrittenFiles()) {
				after = Math.max(after, entry.request() + 1);
			}
		}
		return after;
	}

	/**
	 * The text telling the agent of the conversation's written files, within the
	 * share of its budget; null when there are none, none is readable now or the
	 * agents are not told of them.
	 */
	public String render(IChatRequestContext context, int agentBudget) {
		final int maxTokens = config.maxTokens(agentBudget);
		if (context == null || maxTokens <= 0) {
			return null;
		}
		final List<GConversationFilesText.Entry> entries = entries(context);
		if (entries.isEmpty()) {
			return null;
		}
		final IGAILibraryFilesService service = files.getIfAvailable();
		final String session = context.getSessionID();
		final Function<String, IGFilesLocation> locations = id -> {
			if (service == null || session == null) {
				return null;
			}
			try {
				return service.locationOf(session, id);
			} catch (RuntimeException none) {
				return null;
			}
		};
		final String text = GConversationFilesText.render(entries, currentRequest(context), locations,
				outliners.orderedStream().toList(), config.getMaxTokensPerFile(), maxTokens);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The conversation:" + session + " has " + entries.size() + " written file(s) to tell the"
					+ " agent of, within " + maxTokens + " (tok): " + (text != null ? text.length() + " chars" : "none"));
		}
		return text;
	}
}
