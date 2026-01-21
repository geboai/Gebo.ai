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
	static final String defaultRatingPrompt = "SYSTEM\r\n"
			+ "You are a strict relevance-ranking engine. Your only job is to rank candidate items by how useful they are to answer the user query.\r\n"
			+ "You must be conservative: do not hallucinate facts that are not present in the candidate data.\r\n"
			+ "If information is missing, use metadata and snippet/title signals; never assume.\r\n" + "\r\n"
			+ "You must output ONLY valid JSON that conforms exactly to the schema provided. No markdown. No extra text.\r\n"
			+ "\r\nTASK\r\n"
			+ "Rank the candidate items by relevance to the USER_QUERY and return the sorted list with scores and short rationales grounded ONLY in the provided data.\r\n"
			+ "\r\n" + "DEFINITIONS\r\n" + "- \"Candidate item\" can be:\r\n"
			+ "  (A) a search result (document/page/file) with title + snippet/preview + metadata\r\n"
			+ "  (B) a reference extracted from another document (e.g., a citation, link, mentioned doc, or extracted \"document reference\") with partial info.\r\n"
			+ "- Relevance means: how likely the item contains information that directly answers the query, or is a strong supporting source.\r\n"
			+ "- Prefer items that likely contain explicit answers, procedures, definitions, or authoritative statements matching the query intent.\r\n"
			+ "- Penalize generic titles/snippets, off-topic items, and items with missing critical fields.\r\n"
			+ "- If the query requires recency, prefer newer items when dates are available.\r\n"
			+ "- If the query contains entities/codes/IDs/errors, prefer exact matches in title/snippet/path.\r\n"
			+ "\r\n" + "SCORING\r\n" + "Return a relevance_score in range 0..100 for each item.\r\n"
			+ "Use this rubric (apply best judgment):\r\n"
			+ "- 90-100: Almost certainly contains the answer (clear direct match in title/snippet or strong contextual cues)\r\n"
			+ "- 70-89: Very likely relevant, may require reading content to confirm\r\n"
			+ "- 40-69: Possibly relevant, weak/partial match or generic, could be supporting\r\n"
			+ "- 10-39: Unlikely relevant, mostly noise but not impossible\r\n"
			+ "- 0-9: Off-topic or clearly irrelevant\r\n" + "\r\n" + "OUTPUT RULES\r\n" + "- Output JSON only.\r\n"
			+ "- Sort by relevance_score desc, then by tie-breakers:\r\n"
			+ "  1) higher evidence_strength (stronger lexical/semantic match)\r\n"
			+ "  2) higher authority_boost (official/primary sources if known)\r\n"
			+ "  3) more recent (if query implies recency)\r\n" + "- Provide for each item:\r\n" + "  - rank (1..N)\r\n"
			+ "  - itemId (as given)\r\n" + "  - relevanceScore (0..100 integer)\r\n"
			+ "  - confidence (0..1 float, how confident you are in this ranking using only given data)\r\n" + "\r\n"
			+ "{format}\r\n" + "\r\n" + "INPUTS\r\n" + "USER_QUERY:\r\n" + "{question}\r\n" + "\r\n"
			+ "CANDIDATES (JSON data):\r\n" + "{documents}\r\n" + "\r\n"
			+ "IMPORTANT: Use ONLY these candidate fields; do not invent any additional information.\r\n" + "";
	static final String defaultKeywordGenerationPrompt = "You are a multilingual KEYWORD GENERATOR for fast chunk pre-filtering in large documents (PDFs).\r\n"
			+ "Your output will be used by a simple matcher (Unicode/diacritics folding, case folding, hyphenation fixes). No embeddings.\r\n"
			+ "\r\n" + "INPUTS\r\n" + "- question: {question}\r\n" + "- context (may be empty): {context}\r\n" + "\r\n"
			+ "TASK\r\n" + "Generate a SINGLE flat list of keywords/short phrases to match in text chunks.\r\n" + "\r\n"
			+ "LANGUAGE POLICY\r\n" + "- Detect the language(s) present in {question} and {context}.\r\n"
			+ "- Produce keywords primarily in the detected language(s), AND ALWAYS include English variants when plausible.\r\n"
			+ "- Keep keywords \"language-agnostic\" by favoring: acronyms, product/module names, protocol/API terms, standards, error codes, identifiers, version numbers, file formats, and proper nouns.\r\n"
			+ "- When a concept is expressed in Italian (or other language), include both: the native-language term and the English technical equivalent if common.\r\n"
			+ "\r\n" + "KEYWORD QUALITY RULES\r\n" + "1) Do NOT answer the question. Only output keywords.\r\n"
			+ "2) Prefer concrete, high-signal terms. Avoid generic words unless part of a specific short phrase.\r\n"
			+ "3) Keep phrases short (2–4 words). Avoid punctuation-heavy strings. Avoid quotes.\r\n"
			+ "4) Include variants and aliases:\r\n" + "   - acronyms ↔ expanded forms\r\n" + "   - common synonyms\r\n"
			+ "   - common brand/platform renames (e.g., \"Azure AD\" ↔ \"Entra ID\")\r\n"
			+ "5) Include identifiers if present/likely: numbers, versions, RFC/ISO names, CVE-like patterns, codes.\r\n"
			+ "6) Remove duplicates and near-duplicates.\r\n" + "7) Output size: 15 to 35 items.\r\n" + "\r\n"
			+ "OUTPUT FORMAT (STRICT)\r\n" + "{format}\r\n" + "\r\n" + "NOW GENERATE KEYWORDS FOR:\r\n"
			+ "question: {question}\r\n" + "context: {context}\r\n" + "";
	private String searchQueryExtractionPrompt = null;
	private int maxExternalSourcesSearchResults = 8;
	private boolean externalSourcesEnabled = false;
	private boolean deepSearchUIAllowChooseSources = false;
	private int perDataSourceMaxVisited = 25;
	private int perDataSourceMaxInputTokens = 5000000;
	private int perDataSourceMaxOutputTokens = 1000000;
	private DeepSearchVariant usedVariant = DeepSearchVariant.FULL_REACTIVE;

	public DeepSearchDefaultConfig() {
		this.setDescription("Default deep search configuration");
		this.setDefaultConfig(true);
		this.firstHopSimilarityThreashold = 0.5;
		this.secondHopSimilarityThreashold = 0.5;
		this.searchType = SearchType.MULTI_HOP;
		this.chatModelConfiguration = null;
		this.analisysPrompt = analisysDefaultPrompt;
		this.consolidationPrompt = consolidationDefaultPrompt;
		this.ratingPrompt = defaultRatingPrompt;
		this.keywordGenerationPrompt = defaultKeywordGenerationPrompt;
		this.ragQueryOptions = new RagQueryOptions(1000000, CompletenessLevel.STRICT_QUERY_RELATED);
		this.ragQueryOptions.setTopK(100);
		this.ragQueryOptions.setSimilarityThreashold(0.5);
		this.graphRagTopN = 50;
		this.searchQueryExtractionPrompt = defaultSearchQueryExtractionPrompt;
	}

}
