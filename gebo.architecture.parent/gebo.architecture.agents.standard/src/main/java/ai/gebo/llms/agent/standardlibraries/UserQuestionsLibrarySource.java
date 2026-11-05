/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardlibraries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.tools.AskUserMultipleChoiceQuestionTool;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;

/**
 * User questions and clarifications: the library of the tools an agent asks the
 * user with, a question with one answer and a question with several. An agent
 * configuration naming the library type may wait for the user; one that does
 * not name it never suspends a run, since no tool of its can declare a condition
 * to wait for.
 * <p>
 * The library holds no resources: what a question waits for lives in the
 * suspension block of the run, not here. So an instance keeps nothing and is
 * rebuilt from its id, the id the conversation records being all there is of it.
 * That record is also what makes an instance a conversation's: with nothing
 * kept, there are no owners to check an id against, and nothing to protect.
 */
@Service
public class UserQuestionsLibrarySource implements IGAIToolsAndResourcesLibrarySource {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserQuestionsLibrarySource.class);
	public static final String SOURCE_ID = "user-questions";
	public static final String TYPE_ID = "user-questions-and-clarifications";
	private static final String TYPE_DESCRIPTION = "User questions and clarifications tool";
	private static final String INSTANCE_DESCRIPTION = "ask the user what you cannot decide without them; the work waits for the answer";

	private final List<ToolCallback> tools = List.of(AskUserSingleChoiceQuestionTool.declare(),
			AskUserMultipleChoiceQuestionTool.declare());
	private final AIToolsAndResourcesLibraryType type = declareType();

	/** The tools and no resources, declared once. */
	private AIToolsAndResourcesLibraryType declareType() {
		final AIToolsAndResourcesLibraryType declared = new AIToolsAndResourcesLibraryType();
		declared.setId(TYPE_ID);
		declared.setDescription(TYPE_DESCRIPTION);
		declared.setResourcesTypes(List.of());
		declared.setTools(List.of(reference(tools.get(0), "Ask the user a question with one answer"),
				reference(tools.get(1), "Ask the user a question with several answers")));
		return declared;
	}

	/** A tool reference read from the tool itself, so the stand-in offers what the tool takes. */
	private static ToolReference reference(ToolCallback tool, String shownAs) {
		final ToolReference reference = new ToolReference(tool);
		reference.setUserUIfunctionDescription(shownAs);
		return reference;
	}

	@Override
	public String getId() {
		return SOURCE_ID;
	}

	@Override
	public String getDescription() {
		return "Tools asking the user what the work waits for";
	}

	@Override
	public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
		return List.of(type);
	}

	@Override
	public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
		if (id == null || id.isBlank()) {
			return null;
		}
		try {
			if (!id.equals(UUID.fromString(id).toString())) {
				return null;
			}
		} catch (IllegalArgumentException notAnId) {
			LOGGER.warn("Not the id of a user questions library instance: " + id);
			return null;
		}
		return instanceOf(id, owner != null ? List.of(owner) : List.of());
	}

	@Override
	public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
			List<String> owners) {
		if (type == null || !TYPE_ID.equals(type.getId())) {
			throw new IllegalArgumentException(
					"Not the user questions library type: " + (type != null ? type.getId() : null));
		}
		final AIToolsAndResourcesLibraryInstance instance = instanceOf(UUID.randomUUID().toString(),
				owners != null ? owners : List.of());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created user questions library instance:" + instance.getId() + " for "
					+ instance.getOwnersId());
		}
		return instance;
	}

	/** Nothing to release: an instance keeps nothing. */
	@Override
	public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Dropped user questions library instance:" + (instance != null ? instance.getId() : null));
		}
	}

	private AIToolsAndResourcesLibraryInstance instanceOf(String id, List<String> owners) {
		final AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
		instance.setId(id);
		instance.setLibraryType(type);
		instance.setDescription(INSTANCE_DESCRIPTION);
		instance.setOwnersId(new ArrayList<>(owners));
		instance.setResources(List.of());
		instance.setTools(type.getTools());
		return instance;
	}

	/** The same tools for every instance: what they ask goes to the run's wait, found in the tool context. */
	@Override
	public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
		return tools;
	}
}
