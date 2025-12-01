/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import lombok.Data;

/**
 * Gebo.ai comment agent
 *
 * Configuration class for Gebo chat prompts. This class is used to load and
 * manage configuration properties related to chat prompts.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.chat")
@Data
public class GeboChatPromptsConfigs {
	// List of default prompt configurations
	private List<GPromptConfig> promptDefaults = new ArrayList<GPromptConfig>();

	// List of prompt template wizard configurations
	private List<GPromptConfig> promptTemplateWizardConfigs = new ArrayList<GPromptConfig>();

	// Default configuration for the prompt template wizard
	private GPromptConfig defaultPromptTemplateWizardConfig = new GPromptConfig();
	// Summarize chat description prompt
	private GPromptConfig summarizeChatDescriptionPrompt = new GPromptConfig();

	private GPromptConfig historyConsolidationPrompt = new GPromptConfig();
	private int leaveLastInteractionsOnHistoryConsolidation = 4;

	/**
	 * Constructor for GeboChatPromptsConfigs. Initializes the default prompt
	 * template wizard configuration.
	 */
	public GeboChatPromptsConfigs() {
		// Initialize the default prompt template with a code and prompt message
		defaultPromptTemplateWizardConfig.setCode("prompt-template-wizard-default");
		defaultPromptTemplateWizardConfig.setPrompt("Write a chat prompt to assist the user on its tasks ");
		summarizeChatDescriptionPrompt.setCode("summarize-chat-description");
		summarizeChatDescriptionPrompt.setPrompt("You are a summarization assistant.\n"
				+ "Given a single user request sent to a chatbot, generate a short, meaningful description of the chat.\n"
				+ "The description must capture the core intent of the user request, without adding details that are not explicitly stated.\n"
				+ "Write it in one short sentence, in a neutral and professional tone.\n"
				+ "Avoid questions: produce a concise descriptive label.\n"
				+ "Do not include any system or assistant messages.\n"
				+ "Output only the final description, nothing else.");
		historyConsolidationPrompt.setCode("history-consolidation-prompt");
		historyConsolidationPrompt.setPrompt("You are a chat history consolidation system.\r\n" + "\r\n"
				+ "Your task:\r\n"
				+ "Given an existing summary of a chat session and a batch of new dialogue messages, produce an updated, concise summary that reflects the entire conversation so far.\r\n"
				+ "\r\n" + "Inputs:\r\n" + "- An EXISTING SUMMARY that may be empty or partially complete.\r\n"
				+ "- NEW MESSAGES: a sequence of turns between \"user\" and \"assistant\" in chronological order.\r\n"
				+ "\r\n" + "You must:\r\n"
				+ "1. Merge the EXISTING SUMMARY and the NEW MESSAGES into a single, coherent session summary.\r\n"
				+ "2. Preserve all important information from the existing summary that is still relevant.\r\n"
				+ "3. Incorporate any new important information from the latest messages.\r\n"
				+ "4. Remove obsolete, contradicted, or no longer relevant details if the conversation clearly superseded them.\r\n"
				+ "\r\n" + "Focus on:\r\n" + "- The user’s goals, questions, and problems.\r\n"
				+ "- Key facts the user provided (context, constraints, domain details).\r\n"
				+ "- Important explanations, decisions, and conclusions from the assistant.\r\n"
				+ "- Plans, TODOs, and next steps that were agreed upon or clearly implied.\r\n"
				+ "- Stable user preferences that may matter for future turns.\r\n" + "\r\n" + "Ignore or compress:\r\n"
				+ "- Greetings, small talk, filler phrases, and apologies.\r\n"
				+ "- Repeated information, verbose reasoning, and step-by-step derivations unless crucial to understand decisions.\r\n"
				+ "- Low-level implementation minutiae that the user is unlikely to need later if higher-level conclusions are present.\r\n"
				+ "\r\n" + "Style requirements:\r\n"
				+ "- Write in a concise, neutral, third-person style (e.g., \"The user is building...\", \"The assistant suggested...\").\r\n"
				+ "- Organize the text into 1–3 short paragraphs.\r\n"
				+ "- The summary must be self-contained and understandable without seeing the raw chat.\r\n"
				+ "- Aim to keep the length brief but complete (ideally under 400–500 words).\r\n" + "\r\n"
				+ "Input format:\r\n" + "[EXISTING_SUMMARY]\r\n" + "{existing_summary}\r\n" + "[/EXISTING_SUMMARY]\r\n"
				+ "\r\n" + "[NEW_MESSAGES]\r\n" + "{new_messages}\r\n" + "[/NEW_MESSAGES]\r\n" + "\r\n" + "Where:\r\n"
				+ "- EXISTING_SUMMARY is the current stored summary text (possibly empty).\r\n"
				+ "- NEW_MESSAGES is the new dialogue turns formatted as lines like:\r\n" + "  user: ...\r\n"
				+ "  assistant: ...\r\n" + "  user: ...\r\n" + "  ...\r\n" + "\r\n" + "Your output:\r\n"
				+ "- Produce ONLY the new consolidated session summary as plain text.\r\n"
				+ "- Do NOT include headings, labels, JSON, bullet points, or any extra commentary.\r\n"
				+ "- Just return the updated summary text.\r\n" + "- Generate a maximum of {historySizeTarget} tokens"
				+ "");
	}

}