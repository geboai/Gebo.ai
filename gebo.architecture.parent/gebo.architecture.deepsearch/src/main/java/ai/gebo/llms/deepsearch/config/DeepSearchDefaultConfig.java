package ai.gebo.llms.deepsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;

@Configuration
@ConfigurationProperties(value = "ai.gebo.deepsearch")
public class DeepSearchDefaultConfig extends DeepSearchConfig {
	static final String analisysDefaultPrompt = "SYSTEM:\r\n"
			+ "You are an assistant that extracts ONLY information relevant to the user question\r\n"
			+ "from a batch of document fragments. Ignore everything else.\r\n" + "\r\n" + "USER:\r\n"
			+ "User question:\r\n" + "<<<\r\n" + "{question}\r\n" + ">>>\r\n" + "\r\n"
			+ "Document batch (each fragment has an id and text):\r\n"
			+ "{documents}\"\r\n" + "\r\n" + "TASK:\r\n"
			+ "1. Read the question and the document fragments.\r\n"
			+ "2. Extract ONLY the information that is clearly relevant to the question.\r\n"
			+ "3. Organize the extracted information into:\r\n" + "   - a short natural-language summary,\r\n"
			+ "   - a list of key bullet points,\r\n"
			+ "   - a list of ids of the fragments that support the information.\r\n"
			+ "4. If this batch is completely irrelevant, say so.\r\n" + "\r\n"
			;
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
			+ "Current consolidated report (may be empty or null):\r\n" + "<<<\r\n" + "{consolidated}\r\n"
			+ ">>>\r\n" + "\r\n" + "New partial contribution:\r\n" + "<<<\r\n" + "{documents}\r\n"
			+ ">>>\r\n" + "\r\n" + "TASK:\r\n"
			+ "1. If the current report is empty, create a new report based on the partial contribution.\r\n"
			+ "2. Otherwise:\r\n" + "   - merge the new partial contribution into the existing report,\r\n"
			+ "   - integrate new key points that are not already present,\r\n"
			+ "   - refine or correct previous points if the new contribution has better or more precise information,\r\n"
			+ "   - update the list of evidence fragment ids (de-duplicated).\r\n"
			+ "3. Keep the report concise and avoid repeating the same points.\r\n"
			+ "4. The report must directly answer the user question as best as possible with the available information.\r\n"
			;

	public DeepSearchDefaultConfig() {
		this.chatModelConfiguration = null;
		this.analisysPrompt = analisysDefaultPrompt;
		this.consolidationPrompt = consolidationDefaultPrompt;
		this.ragQueryOptions = new RagQueryOptions(100000, CompletenessLevel.MAX_TOKENS);
		this.graphRagTopN=50;
	}

}
