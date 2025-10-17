package ai.gebo.architecture.graphrag.extraction.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import lombok.Data;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Configuration
@ConfigurationProperties(value = "ai.gebo.graphrag")
@Data
public class GraphRagExtractionStaticConfig {
	private static String defaultExtractionPrompt="You are an advanced Information Extraction system.\r\n"
			+ "\r\n"
			+ "Your task is to analyze the given INPUT TEXT and extract the following kinds of information:\r\n"
			+ "- Entities (people, companies, products, places, dates, etc.)\r\n"
			+ "- Relationships (links between entities, including type and attributes)\r\n"
			+ "- Events (things that happen, with participants, time, location, attributes)\r\n"
			+ "\r\n"
			+ "⚠️ IMPORTANT RULES:\r\n"
			+ "1. Always respond ONLY in the structured format described in ${format}.\r\n"
			+ "2. Do not include explanations, commentary, or extra text outside the structured output.\r\n"
			+ "3. If some field cannot be determined, leave it null/empty but preserve the schema.\r\n"
			+ "4. Use confidence scores between 0.0–1.0 when relevant.\r\n"
			+ "5. Ensure that IDs are consistent within the same output (e.g., entities referenced in relations/events must exist in the entities section).\r\n"
			+ "6. Normalize dates and times to ISO-8601 if possible.\r\n"
			+ "7. Do not invent facts not supported by the text.\r\n"
			+ "\r\n"
			+ "---\r\n"
			+ "\r\n"
			/*+ "INPUT TEXT:\r\n"
			+ "\"\"\"\r\n"
			+ "${text}\r\n"
			+ "\"\"\"\r\n"
			+ "\r\n"
			+ "---\r\n"*/
			+ "\r\n"
			+ "Now produce the output in exactly this format:\r\n"
			+ "${format}\r\n"
			+ "";
	GraphRagExtractionConfig extractionConfig = new GraphRagExtractionConfig();
	public GraphRagExtractionStaticConfig() {
		this.extractionConfig.setExtractionPrompt(defaultExtractionPrompt);
	}
	
}
