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
	private GPromptConfig historyDocumentsConsolidationPrompt = new GPromptConfig();
	

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
				+ "- Aim to keep the length brief but complete (ideally under {historySizeTarget} tokens).\r\n" + "\r\n"
				+ "Input format:\r\n" + "[EXISTING_SUMMARY]\r\n" + "{consolidated}\r\n" + "[/EXISTING_SUMMARY]\r\n"
				+ "\r\n" + "[NEW_MESSAGES]\r\n" + "{documents}\r\n" 
				+ "{question}\r\n[/NEW_MESSAGES]\r\n" + "\r\n"
				+ "Where:\r\n" + "- EXISTING_SUMMARY is the current stored summary text (possibly empty).\r\n"
				+ "- NEW_MESSAGES is the new dialogue turns formatted as lines like:\r\n" + "  user: ...\r\n"
				+ "  assistant: ...\r\n" + "  user: ...\r\n" + "  ...\r\n" + "\r\n" + "Your output:\r\n"
				+ "- Produce ONLY the new consolidated session summary as plain text.\r\n"
				+ "- Do NOT include headings, labels, JSON, bullet points, or any extra commentary.\r\n"
				+ "- Just return the updated summary text.\r\n" + "- Generate a maximum of {historySizeTarget} tokens"
				+ "");
		historyDocumentsConsolidationPrompt
				.setPrompt("You are an expert “document condenser” for an enterprise RAG chat system.\r\n" + "\r\n"
						+ "TASK\r\n" + "Given:\r\n" + "(A) last user↔assistant turns (most recent first),\r\n" + "\r\n"
						+ "(B) one or more documents (text extracted from a file or retrieved via RAG/deep search) with its metadata,\r\n"
						+ "(C) chat history consolidated to illustrate the global meaning\r\n"
						+ "produce ONE JSON object of type CSSRelevantShrinkedDocument for each input documents that contains:\r\n"
						+ "\r\n" + "a faithful, compact summary of the document,\r\n" + "\r\n"
						+ "a relevancy score (Float) measuring how relevant the document is to the last 3 turns,\r\n"
						+ "\r\n" + "an estimated token length for the produced summary.\r\n" + "\r\n"
						+ "OUTPUT RULES (STRICT)\r\n" + "\r\n"
						+ "Output ONLY a single valid JSON object. No markdown. No extra keys. No comments.\r\n"
						+ "OUTPUT FORMAT\r\n{format}\r\n" + "\r\n"
						+ "Use these exact keys: documentReference, documentName, documentTitle, summarizedContent, relevancyRate, tokensLength.\r\n"
						+ "\r\n"
						+ "Keep summarizedContent concise but information-dense. Prefer factual bullet-like sentences separated by \"\\n\".\r\n"
						+ "\r\n"
						+ "Do NOT invent facts. If documents do not contain enough information, say so explicitly in the summary.\r\n"
						+ "\r\n" + "If some metadata fields are missing, set them to null.\r\n" + "\r\n"
						+ "relevancyRate must be a Float in [0.0, 1.0].\r\n" + "\r\n"
						+ "tokensLength must be an Integer estimating tokens of summarizedContent only.\r\n" + "\r\n"
						+ "Language of summarizedContent: same as the user’s language detected in last turns (default to English).\r\n"
						+ "\r\n" + "HOW TO SCORE RELEVANCY (relevancyRate)\r\n"
						+ "Compute relevance ONLY against the last 3 turns.\r\n" + "Use this rubric:\r\n" + "\r\n"
						+ "0.00–0.10: unrelated / generic\r\n" + "\r\n"
						+ "0.11–0.30: weakly related (shares broad topic only)\r\n" + "\r\n"
						+ "0.31–0.60: moderately useful (some direct overlap)\r\n" + "\r\n"
						+ "0.61–0.85: strongly useful (directly answers/grounds key parts)\r\n" + "\r\n"
						+ "0.86–1.00: critical (contains necessary details, constraints, or authoritative facts)\r\n"
						+ "\r\n" + "HOW TO SUMMARIZE (summarizedContent)\r\n" + "\r\n"
						+ "Capture: key claims, definitions, procedures, requirements, constraints, numbers, identifiers, and any “actionable” details.\r\n"
						+ "\r\n" + "Preserve important terminology and acronyms as-is.\r\n" + "\r\n"
						+ "If the document is long, prioritize parts most relevant to the last 3 turns.\r\n" + "\r\n"
						+ "If the document contains multiple sections, reflect that structure in the summary.\r\n"

						+ "\r\n" + "A: LAST_TURNS:\r\n" + "{question}\r\n" + "B: INPUT DOCUMENTS\r\n{documents}\r\n"
						+ "C: CONSOLIDATED HISTORY:\r\n{consolidated}\r\n");
	}

}