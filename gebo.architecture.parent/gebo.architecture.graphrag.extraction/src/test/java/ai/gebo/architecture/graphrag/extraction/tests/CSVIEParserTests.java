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
	private static final String CAOTHIC_CSV_TEST = "entity;person;Samael;\"Samael is the name of one of the five Geni and also the author\";0.8;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b,00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Samael,AUN WEOR;event;none;\"The book 'Primo Libro' is being read\";\"54f417bb-beb2-4861-9c68-fe9713313e2b\"\r\n"
			+ "entity;company;\"Legge di Dio\";\"God's Law, the 22 commandments\";0.6;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b,00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Legge di Dio;none;relation;part-of;\"The Primo Mistero is part of the Legge di Dio\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\"\r\n"
			+ "entity;product;\"Pentagramma esoterico\";\"The esoteric pentagram\";0.5;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\";Pentagramma esoterico;none;event;none;\"The Pentagramma esoterico is being explained\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8\"\r\n"
			+ "entity;product;\"Vello d’Oro\";\"The golden fleece\";0.9;\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Vello d'Oro;none;relation;part-of;\"The Vello d'Oro is part of the Primo Mistero\";\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\"\r\n"
			+ "entity_alias;product;\"Stella Fiammeggiante\";\"The shining star\";0.7;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Stella Fiammeggiante;none;\"The Stella Fiammeggiante is an alias for the Pentagramma\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8,00c8ddb1-4f29-4a0f-9b6c-58d667453de3\"\r\n"
			+ "entity_alias;product;\"Pentagramma gnostico\";\"The gnostic pentagram\";0.5;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\";Pentagramma gnostico;none;\"The Pentagramma gnostico is an alias for the Stella Fiammeggiante\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\"\r\n"
			+ "entity_alias;product;\"Tesoro della Luce\";\"The treasure of light\";0.8;\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\";Tesoro della Luce;none;\"The Tesoro della Luce is an alias for the Vello d'Oro\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\"\r\n"
			+ "entity_alias;product;\"Telaio di Dio\";\"The loom of God\";0.9;\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Telaio di Dio;none;\"The Telaio di Dio is an alias for the Primo Mistero\";\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\"\r\n"
			+ "event;none;\"Primo Libro Ο Cap. 1-62\";\"The book 'Primo Libro' is being read\";0.8;\"54f417bb-beb2-4861-9c68-fe9713313e2b\";Primo Libro Ο Cap. 1-62;none;relation;part-of;\"The Primo Mistero is part of the Legge di Dio\";\"83d2d7d4-f23a-4326-8674-b0c7678608d8,54f417bb-beb2-4861-9c68-fe9713313e2b\"\r\n"
			+ "event;none;\"Il Pentagramma tracciato con il carbone\";\"The pentagram drawn with coal is being explained\";0.5;\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Il Pentagramma tracciato con il carbone;none;relation;part-of;\"The Primo Mistero si trova dentro il ventiquattresimo\";\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\"\r\n"
			+ "event_alias;product;\"Vello d’Oro\";\"The golden fleece\";0.9;\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\";Vello d'Oro;none;\"The Vello d'Oro is an alias for the Tesoro della Luce\";\"00c8ddb1-4f29-4a0f-9b6c-58d667453de3\"\r\n"
			+ "entity;person;Samael Aun Weor;<Samael Aun Weor is the author of the book \"Pistis Sophia Svelato\" and a spiritual teacher.>;0.9;\"848b99ff-1025-40d2-9fad-520c165d7204,e99b988e-0536-4056-a449-607c861e5243,c1b07cda-528e-489d-874c-545c008a3558,49c4403c-dbe9-4b78-85a3-84e46f715213\";;\r\n"
			+ "\r\n"
			+ "entity;person;Gesù;<Gesù is the central figure of Christianity and a key character in the text.>;0.8;\"c1b07cda-528e-489d-874c-545c008a3558,49c4403c-dbe9-4b78-85a3-84e46f715213\";;\r\n"
			+ "\r\n"
			+ "entity;product;Pistis Sophia;<Pistis Sophia is a spiritual text that is the basis for Samael Aun Weor's teachings.>;0.7;\"848b99ff-1025-40d2-9fad-520c165d7204,e99b988e-0536-4056-a449-607c861e5243\";;\r\n"
			+ "\r\n"
			+ "entity;product;Pistis Sophia Svelato;<Pistis Sophia Svelato is a translation of the Pistis Sophia text that was translated by Samael Aun Weor.>;0.8;\"e99b988e-0536-4056-a449-607c861e5243,c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "entity;product;Pistis Sophia Svelato Ο Samael Aun Weor;<Pistis Sophia Svelato Ο Samael Aun Weor is a book by Samael Aun Weor that translates and comments on the Pistis Sophia text.>;0.9;\"e99b988e-0536-4056-a449-607c861e5243,c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "event;process;risurrezione dai morti;<Gesù risuscitò dai morti e trascorse undici anni con i suoi discepoli.>;0.6;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "event;process;insegnamento dei discepoli;<Gesù istruì i suoi discepoli per undici anni, spiegando loro il primo comandamento e il primo mistero.>;0.7;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "relation;part-of;Primo Mistero;<Il Primo Mistero è parte del Telaio di Dio.>;0.5;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "relation;owned-by;Telaio di Dio;<Il Telaio di Dio possiede il Primo Mistero.>;0.4;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "event_alias;alternative expression;nascondere nel suo telaio,<nascondere nelle sue viscere;<These two phrases are alternative expressions for the event of hiding or concealing something, specifically in this context, it refers to the way the Primo Mistero is hidden within the Telaio di Dio.>;0.8;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "event_alias;alternative expression;nascondere nelle sue viscere;<This phrase is an alternative expression for hiding or concealing something, specifically in this context, it refers to the way the Primo Mistero is hidden within the Telaio di Dio.>;0.9;\"c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "entity_alias;alternative name;Pistis Sophia Svelato Ο Samael Aun Weor;<This is an alternative title for the book \"Pistis Sophia Svelato\" by Samael Aun Weor.>;0.6;\"e99b988e-0536-4056-a449-607c861e5243,c1b07cda-528e-489d-874c-545c008a3558\";;\r\n"
			+ "\r\n"
			+ "entity_alias;alternative name;Pistis Sophia;<This is an alternative title for the spiritual text that is the basis for Samael Aun Weor's teachings.>;0.7;\"848b99ff-1025-40d2-9fad-520c165d7204,e99b988e-0536-4056-a449-607c861e5243\";;";

	@Test
	public void executeParseCaothicCSV() throws IOException {
		LLMExtractionResult extraction = CSVIEParser.parseCSV(CAOTHIC_CSV_TEST);
		LOGGER.info(extraction.toString());
		assertFalse(extraction.getEntities().isEmpty(), "Must have at least an entity");
		//assertFalse(extraction.getEvents().isEmpty(), "Must have at least an event");
		//assertFalse(extraction.getRelations().isEmpty(), "Must have at least a relation");
		//assertFalse(extraction.getEntityAliases().isEmpty(), "Must have at least an entity_alias");
		//assertFalse(extraction.getEventAliases().isEmpty(), "Must have at least an event_alias");
	}

}
