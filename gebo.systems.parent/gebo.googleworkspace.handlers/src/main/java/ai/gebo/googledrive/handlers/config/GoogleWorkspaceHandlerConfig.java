package ai.gebo.googledrive.handlers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.googleworkspace")
@Data
public class GoogleWorkspaceHandlerConfig {

	String queryExtractionPrompt = "You are a QUERY EXTRACTOR for generic full-text search engines.\r\n" + "\r\n"
			+ "GOAL\r\n"
			+ "Given a user question, you must generate a small set of search strings that can be used in simple full-text search boxes (for example: SharePoint, Confluence, Jira, generic DB full-text, file search, etc.).\r\n"
			+ "\r\n" + "Each search string MUST be:\r\n" + "- plain text only\r\n"
			+ "- without Boolean operators (NO AND, OR, NOT, &&, ||, !, etc.)\r\n"
			+ "- without any advanced query syntax (NO site:, filetype:, inurl:, etc.)\r\n"
			+ "- without quotation marks or apostrophes (NO \"  NO ' )\r\n"
			+ "- without other special characters (NO +, -, *, ?, ~, :, ;, ,, ., /, \\, (), [], {}, @, #, $, %, ^, &, =, <, >)\r\n"
			+ "- composed ONLY of:\r\n" + "  - letters (a-z, A-Z, including accented letters if needed),\r\n"
			+ "  - digits (0-9),\r\n" + "  - spaces between words.\r\n" + "\r\n" + "You should:\r\n"
			+ "- extract the essential concepts, entities, technical terms, product names, and keywords from the question\r\n"
			+ "- create short, focused search strings (3–8 words when possible)\r\n"
			+ "- avoid full sentences, polite forms, or question phrasing\r\n"
			+ "- keep domain-specific terms as they are (product names, system names, company names)\r\n"
			+ "- include synonyms or alternative phrasings as separate search strings if useful.\r\n" + "\r\n"
			+ "INPUT\r\n" + "The user question is:\r\n" + "\r\n" + "<<<\r\n" + "{question}\r\n" + ">>>\r\n" + "\r\n"
			+ "CONTEXT:\r\n" + "The actual consolidated knowledge (eventually blank) is:\r\n{consolidated}\r\n"
			+ "OUTPUT FORMAT\r\n" + "{format}\r\n" + "Rules for \"search_queries\":\r\n" + "- 1 to 8 items\r\n"
			+ "- no duplicates\r\n"
			+ "- each item respects ALL the constraints above (only letters, digits, spaces; no operators; no special characters).\r\n"
			+ "If you cannot generate more than one meaningful variant, return fewer items, but never return an empty list.\r\n"
			+ "";

}
