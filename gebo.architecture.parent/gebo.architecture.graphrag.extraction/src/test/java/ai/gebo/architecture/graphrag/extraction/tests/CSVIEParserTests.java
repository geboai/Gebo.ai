package ai.gebo.architecture.graphrag.extraction.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.impl.CSVIEParser;

public class CSVIEParserTests {
	static final Logger LOGGER = LoggerFactory.getLogger(CSVIEParserTests.class);
	private static final String CAOTHIC_CSV_TEST = "entity;person;Samael Aun Weor;<Samael Aun Weor is the author of the book \"Pistis Sophia Svelato\";0.8;\"63a180d7-64cf-4927-b064-c598c96b82ac\";\"Samael Aun Weor\";,,\r\n"
			+ "entity;person;Gesù;<Gesù is the protagonist of the story>;0.9;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Gesù\";,,\r\n"
			+ "relation;part-of;Pistis Sophia Svelato;Pistis Sophia Svelato è un libro di Samael Aun Weor;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Samael Aun Weor\",\"Pistis Sophia Svelato\",\r\n"
			+ "entity_alias;alternative name;Samael Aun Weor;< alternative names for the author >;\"63a180d7-64cf-4927-b064-c598c96b82ac\";\"Samael Aun Weor\";,,\r\n"
			+ "event;process;risorsa dai morti;<Gesù risorse dai morti in questo evento>;0.9;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Gesù\",\"Samael Aun Weor\",\r\n"
			+ "event_alias;alternative expression;lavori mi dai Signore, ma con essi fortezza;< alternative expressions for the event >;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"lavori mi dai Signore, ma con essi fortezza\",,\r\n"
			+ "relation;part-of;Gesù;< Gesù è un personaggio di \"Pistis Sophia Svelato\" >;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Samael Aun Weor\",\"Gesù\",\r\n"
			+ "entity;person;Samael Aun Weor;<Samael Aun Weor is the author of the book \"Pistis Sophia Svelato\";0.8;\"63a180d7-64cf-4927-b064-c598c96b82ac\";\"Samael Aun Weor\";,,\r\n"
			+ "entity;person;Gesù;<Gesù is the protagonist of the story>;0.9;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Gesù\";,,\r\n"
			+ "relation;part-of;Pistis Sophia Svelato;Pistis Sophia Svelato è un libro di Samael Aun Weor;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Samael Aun Weor\",\"Pistis Sophia Svelato\",\r\n"
			+ "entity_alias;alternative name;Samael Aun Weor;< alternative names for the author >;\"63a180d7-64cf-4927-b064-c598c96b82ac\";\"Samael Aun Weor\";,,\r\n"
			+ "event;process;risorsa dai morti;<Gesù risorse dai morti in questo evento>;0.9;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Gesù\",\"Samael Aun Weor\",\r\n"
			+ "event_alias;alternative expression;lavori mi dai Signore, ma con essi fortezza;< alternative expressions for the event >;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"lavori mi dai Signore, ma con essi fortezza\",,\r\n"
			+ "relation;part-of;Gesù;< Gesù è un personaggio di \"Pistis Sophia Svelato\" >;\"1c2c7d3e-e890-4416-85d8-f4f2a0be892b\";\"Samael Aun Weor\",\"Gesù\",\r\n"
			+ "entity;person;Samael;A person, likely a figure of spiritual or mystical significance.;0.8;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";;;\r\n"
			+ "event;unknown;comprensione del Pentagramma magico;The understanding of the magical pentagram is key to two spaces.;0.5;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael\";\r\n"
			+ "entity_alias;product;Zolfo;A chemical element with the symbol S and atomic number 16.;0.9;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";;\"Pentagramma\";\r\n"
			+ "entity;person;Gabriel;A person, likely a figure of spiritual or mystical significance, often associated with divine messages and interventions.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael\";\r\n"
			+ "event;unknown;trascendenza sessuale;dissociation of semen from sex to give rise to electrical energies within the Tapestry of God.;0.9;\"c892ca09-d43a-47dd-ad6e-843acad1e233\";\"Aleph, Thau, Azoth, Vello d'Oro\";\r\n"
			+ "entity;organization;Libreria esoterica;An organization focused on esoteric knowledge and mysticism.;0.7;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";\"Samael Aun Weor\";\r\n"
			+ "entity_alias;product;Aloe;A type of plant, often used for its medicinal properties.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\";\r\n"
			+ "event;unknown;consacrazione del Pentagramma;censing the pentagram with four elements to invoke divine protection and powers.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael, Fuoco, Aria, Acqua, Terra\";\r\n"
			+ "entity_alias;product;Incenso;A type of aromatic plant or resin, often used in rituals and ceremonies.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\";\r\n"
			+ "event;unknown;rituale del Vello d'Oro;a mystical ritual to acquire the Golden Fleece, a symbol of enlightenment and spiritual awakening.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael Aun Weor\";\r\n"
			+ "entity_alias;product;Canfora;A type of aromatic plant or resin, often used in rituals and ceremonies.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\";\r\n"
			+ "2025-11-28 08:32:10,906 [pool-6-thread-2] DEBUG a.g.a.g.e.services.impl.CSVIEParser:54 - LLM CSV CONTENT:entity;person;Samael;A person, likely a figure of spiritual or mystical significance.;0.8;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";;;\r\n"
			+ "event;unknown;comprensione del Pentagramma magico;The understanding of the magical pentagram is key to two spaces.;0.5;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael\";\r\n"
			+ "entity_alias;product;Zolfo;A chemical element with the symbol S and atomic number 16.;0.9;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";;\"Pentagramma\";\r\n"
			+ "entity;person;Gabriel;A person, likely a figure of spiritual or mystical significance, often associated with divine messages and interventions.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael\";\r\n"
			+ "event;unknown;trascendenza sessuale;dissociation of semen from sex to give rise to electrical energies within the Tapestry of God.;0.9;\"c892ca09-d43a-47dd-ad6e-843acad1e233\";\"Aleph, Thau, Azoth, Vello d'Oro\";\r\n"
			+ "entity;organization;Libreria esoterica;An organization focused on esoteric knowledge and mysticism.;0.7;\"521f77c1-1b2f-44a2-98e2-7d419581c680\";\"Samael Aun Weor\";\r\n"
			+ "entity_alias;product;Aloe;A type of plant, often used for its medicinal properties.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\";\r\n"
			+ "event;unknown;consacrazione del Pentagramma;censing the pentagram with four elements to invoke divine protection and powers.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael, Fuoco, Aria, Acqua, Terra\";\r\n"
			+ "entity_alias;product;Incenso;A type of aromatic plant or resin, often used in rituals and ceremonies.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\";\r\n"
			+ "event;unknown;rituale del Vello d'Oro;a mystical ritual to acquire the Golden Fleece, a symbol of enlightenment and spiritual awakening.;0.8;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Samael Aun Weor\";\r\n"
			+ "entity_alias;product;Canfora;A type of aromatic plant or resin, often used in rituals and ceremonies.;0.9;\"6d5d2077-71cb-4eb7-a323-23a65340c643\";\"Stella Fiammeggiante\r\n";

	@Test
	public void executeParseCaothicCSV() throws IOException {
		LLMExtractionResult extraction = CSVIEParser.parseCSV(CAOTHIC_CSV_TEST);
		LOGGER.info(extraction.toString());
		assertFalse(extraction.getEntities().isEmpty(), "Must have at least an entity");
		assertFalse(extraction.getEvents().isEmpty(), "Must have at least an event");
		assertFalse(extraction.getRelations().isEmpty(), "Must have at least a relation");
		assertFalse(extraction.getEntityAliases().isEmpty(), "Must have at least an entity_alias");
		assertFalse(extraction.getEventAliases().isEmpty(), "Must have at least an event_alias");
	}

}
