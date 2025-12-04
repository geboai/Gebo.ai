package ai.gebo.architecture.graphrag.extraction.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionFormat;
import lombok.Data;

@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Configuration
@ConfigurationProperties(value = "ai.gebo.graphrag")
@Data
public class GraphRagExtractionStaticConfig {
	private static String jsonDefaultExtractionPrompt = "You are an advanced Information Extraction system.\r\n"
			+ "\r\n"
			+ "Your task is to analyze the given INPUT TEXT and extract the following kinds of information:\r\n"
			+ "- Entities (people, companies, products, places, dates, etc.)\r\n"
			+ "- Relationships (links between entities, including type and attributes)\r\n"
			+ "- Events (things that happen, with participants, time, location, attributes)\r\n" + "\r\n"
			+ "⚠️ IMPORTANT RULES:\r\n"
			+ "1. Always respond ONLY in the structured format described after \"Now produce the output in exactly this format:\" .\r\n"
			+ "2. Do not include explanations, commentary, or extra text outside the structured output.\r\n"
			+ "3. If some field cannot be determined, leave it null/empty but preserve the schema.\r\n"
			+ "4. Use confidence scores between 0.0–1.0 when relevant.\r\n"
			+ "5. Ensure that IDs are consistent within the same output (e.g., entities referenced in relations/events must exist in the entities section).\r\n"
			+ "6. Normalize dates and times to ISO-8601 if possible.\r\n"
			+ "7. Do not invent facts not supported by the text.\r\n"
			+ "8. document fragment id is inside the [CHUNK-ID]..[/CHUNK-ID] delimiters, you will receive a system-message for each document fragment.\r\n"
			+ "\r\n" + "---\r\n" + "\r\n" + "\r\n" + "Now produce the output in exactly this format:\r\n"
			+ "{format}\r\n" + "";
	private static String csvExtractionPrompt = "You are an information extraction system.\r\n"
			+ "Extract from the provided input text all the following object types: entities, events, relations, entity aliases, event aliases.\r\n"
			+ "\r\n" + "Produce the output strictly as CSV rows using the following format:\r\n"
			+ "<object type>;<object subtype>;<object name>;<object description>;<confidence score>;<document fragment ids>;<referred entities names>;<referred events names>\r\n"
			+ "\r\n" + "Where:\r\n" + "\r\n"
			+ "<object type> is one of: entity, event, relation, entity_alias, event_alias\r\n" + "\r\n"
			+ "<object subtype> if <object type> is entity is one of: person, company, product, place, nation, region, date  \r\n"
			+ "<object name> is the canonical name of the extracted object\r\n" 
			+ "<object description> is a short and clear description (1–2 sentences)\r\n"
			+ "<confidence score> is the confidence scores between 0.0–1.0 when relevant\r\n"
			+ "<document fragment ids> is a comma separated list of CHUNK-ID inside the [CHUNK-ID]..[/CHUNK-ID] delimiters before each text fragment. \r\n"
			+ "\r\n"
			+ "<referred entities names> can be applied only to event,relation and entity_alias object types, is a comma-separated list of of referenced entities names or empty if none, in case of relation, "
			+ "list the entities names that are in relation,participate the event or are entity_alias each other.\r\n"
			+ "\r\n"
			+ "<referred events names> can be applied only to event_alias object type, is a comma-separated list of referenced events names or empty if none\r\n"
			+ "\r\n" + "Instructions:\r\n" + "\r\n"
			+ "Identify all relevant entities (people, organizations, locations, products, concepts, etc.).\r\n"
			+ "\r\n" + "Identify all events (actions, processes, changes, activities, transactions, etc.).\r\n" + "\r\n"
			+ "Identify all relations between entities (cause, part-of, owned-by, participates-in, located-in, etc.). Relations must reference to two entities.\r\n"
			+ "\r\n" + "Identify aliases for entities (alternative names, abbreviations, acronyms, synonyms).\r\n"
			+ "\r\n" + "Identify aliases for events (alternative expressions describing the same event).\r\n" + "\r\n"
			+ "Always reference entities/events by their names inside <referred entities names> and <referred events names>.\r\n"
			+ "\r\n"
			+ "The output must be ONLY CSV lines, with no explanations, no headers, and no additional text.\r\n"
			+ "If an object has no references, leave the corresponding fields empty but still output all eight CSV columns.\r\n"
			+ "{format}\r\n" + "";
	List<GraphRagExtractionConfig> extractionConfigs = new ArrayList<>();

	public GraphRagExtractionStaticConfig() {
		GraphRagExtractionConfig jsonextractionConfig = new GraphRagExtractionConfig();
		jsonextractionConfig.setExtractionFormat(GraphRagExtractionFormat.JSON);
		jsonextractionConfig.setExtractionPrompt(jsonDefaultExtractionPrompt);
		GraphRagExtractionConfig csvextractionConfig = new GraphRagExtractionConfig();
		csvextractionConfig.setExtractionFormat(GraphRagExtractionFormat.CSV);
		csvextractionConfig.setExtractionPrompt(csvExtractionPrompt);
		extractionConfigs.add(jsonextractionConfig);
		extractionConfigs.add(csvextractionConfig);
	}

}
