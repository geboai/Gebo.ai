package ai.gebo.llms.deepsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.deepsearch")
@Data
public class DeepSearchDefaultConfig extends DeepSearchConfig {
	static final String analisysDefaultPrompt = "SYSTEM:\r\n"
			+ "You are an assistant that extracts ONLY information relevant to the user question\r\n"
			+ "from a batch of document fragments. Ignore everything else.\r\n" + "\r\n" + "USER:\r\n"
			+ "User question:\r\n" + "<<<\r\n" + "{question}\r\n" + ">>>\r\n" + "\r\n"
			+ "Document batch (each fragment has an id and text):\r\n" + "{documents}\"\r\n" + "\r\n" + "TASK:\r\n"
			+ "1. Read the question and the document fragments.\r\n"
			+ "2. Extract ONLY the information that is clearly relevant to the question.\r\n"
			+ "3. organize the extracted information into:\r\n"
			+ "   - a list of text blocks splitted by topics coherent with the question, each of them has a title, paragraphs with detailed informations, and includes references with hypertext links (if provided) that support the information at the bottom\r\n"
			+ ".\r\n" + "4. If this batch is completely irrelevant, say so.\r\n" + "\r\n";
	static final String consolidationDefaultPrompt = "SYSTEM:\r\n"
			+ "You are an assistant that maintains a consolidated report answering a user question.\r\n"
			+ "At each step you receive:\r\n" + "- the user question,\r\n"
			+ "- the current consolidated report (may be empty on first step),\r\n"
			+ "- a new partial contribution extracted from a batch of documents.\r\n" + "\r\n"
			+ "Your job is to update the consolidated report so that:\r\n"
			+ "- it is coherent, non-contradictory and well organized,\r\n"
			+ "- it integrates any truly new information from the partial contribution,\r\n"
			+ "- it keeps track of all evidence fragment ids.\r\n" + "\r\n" + "USER:\r\n" + "User question:\r\n"
			+ "<<<\r\n" + "{question}\r\n" + ">>>\r\n" + "\r\n"
			+ "Current consolidated report (may be empty or null):\r\n" + "<<<\r\n" + "{consolidated}\r\n" + ">>>\r\n"
			+ "\r\n" + "New partial contribution:\r\n" + "<<<\r\n" + "{documents}\r\n" + ">>>\r\n" + "\r\n"
			+ "TASK:\r\n"
			+ "1. If the current report is empty, create a new report based on the partial contribution.\r\n"
			+ "2. Otherwise:\r\n" + "   - merge the new partial contribution into the existing report,\r\n"
			+ "   - integrate new key points that are not already present,\r\n"
			+ "   - refine or correct previous points if the new contribution has better or more precise information,\r\n"
			+ "   - update the list of evidence fragment ids (de-duplicated).\r\n"
			+ "3. Keep the report concise and avoid repeating the same points.\r\n"
			+ "4. The report must directly answer the user question as best as possible with the available information.\r\n"
			+ "5. organize the extracted information into:\r\n"
			+ "   - a list of text blocks splitted by topics coherent with the question, each of them has a title, paragraphs with detailed informations, and includes references with hypertext links (if provided) that support the information at the bottom\r\n";

	static final String defaultSearchQueryExtractionPrompt = "You are “QuerySynth”, an expert at turning an information need into high-quality search queries for {dataSourceDescription}.\r\n"
			+ "\r\n" + "Your job:\r\n"
			+ "Given a user goal/question, generate a set of meaningful search queries to deepen the investigation. "
			+ "These queries will be executed by {dataSourceDescription}.\r\n" + "\r\n" + "INPUTS YOU WILL RECEIVE:\r\n"
			+ "- userGoal: the user question / investigation goal (natural language).\r\n" + "GUIDELINES:\r\n"
			+ "1) Derive search intents\r\n"
			+ "- Break userGoal into 3–8 intents (e.g., definitions/background, incidents, decisions, owners, requirements, risks, change history, evidence).\r\n"
			+ "- Each intent must produce 2–6 queries.\r\n" + "\r\n" + "2) Make queries “operator-aware”\r\n"
			+ "- If {dataSourceDescription} suggests support for boolean operators, include AND/OR/NOT, parentheses, phrase search (\"...\"), field scoping (title:, author:, path:, space:, project:, etc.) when appropriate.\r\n"
			+ "- If the target is Git or code search, include queries for filenames, extensions, class/function names, error strings, config keys.\r\n"
			+ "- If the target supports metadata filters (time, author, tags), populate filters; otherwise keep them null/empty.\r\n"
			+ "\r\n" + "3) Be robust to naming variation\r\n"
			+ "- Always include synonyms, acronyms, product names, internal codenames, alternative spellings, Italian/English variants when plausible.\r\n"
			+ "- Prefer concrete tokens that appear in documents: IDs, ticket keys, version numbers, error codes, API endpoints, class names.\r\n"
			+ "\r\n" + "4) Avoid low-value queries\r\n"
			+ "- Do NOT output generic queries like “info about X” unless you add specific constraints.\r\n"
			+ "- Avoid duplicates; each query must add new coverage (different angle, synonym set, timeframe, artifact type).\r\n"
			+ "\r\n" + "5) Include fallbacks\r\n"
			+ "- For each query, provide 1–3 fallbackQueries: simplified keyword query, alternative synonym set, narrower/wider version.\r\n"
			+ "\r\n" + "6) Language\r\n"
			+ "- Match the most likely language of the repository from {dataSourceDescription}; if uncertain, include bilingual query variants.\r\n"
			+ "- Do not translate proper nouns, IDs, code symbols.\r\n" + "\r\n" + "7) Safety and privacy\r\n"
			+ "- Do not request credentials, secrets, or personal data. If userGoal suggests searching for secrets, produce queries aimed at detecting accidental exposures (e.g., “password”, “secret”, “token”) but keep it defensive and compliance-oriented.\r\n"
			+ "\r\n" + "QUALITY BAR:\r\n" + "- Queries should be immediately executable.\r\n"
			+ "- They should maximize recall early, then add precision via filters/boolean structure.\r\n"
			+ "- Prefer short, surgical queries over long paragraphs.\r\n" + "\r\n"
			+ "If inputs are missing or vague:\r\n" + "- Infer reasonable intents from userGoal.\r\n"
			+ "- Keep filters null and include broader queries + fallbackQueries.\r\n"
			+ "\r\nUSER:\r\nUser question:\r\n\r\n{question}\r\n";

	private String searchQueryExtractionPrompt = null;
	private int maxExternalSourcesSearchResults = 8;
	private boolean externalSourcesEnabled = false;
	private boolean deepSearchUIAllowChooseSources = false;

	public DeepSearchDefaultConfig() {
		this.setDescription("Default deep search configuration");
		this.setDefaultConfig(true);
		this.firstHopSimilarityThreashold = 0.5;
		this.secondHopSimilarityThreashold = 0.5;
		this.searchType = SearchType.MULTI_HOP;
		this.chatModelConfiguration = null;
		this.analisysPrompt = analisysDefaultPrompt;
		this.consolidationPrompt = consolidationDefaultPrompt;
		this.ragQueryOptions = new RagQueryOptions(1000000, CompletenessLevel.STRICT_QUERY_RELATED);
		this.ragQueryOptions.setTopK(100);
		this.ragQueryOptions.setSimilarityThreashold(0.5);
		this.graphRagTopN = 50;
		this.searchQueryExtractionPrompt = defaultSearchQueryExtractionPrompt;
	}

}
