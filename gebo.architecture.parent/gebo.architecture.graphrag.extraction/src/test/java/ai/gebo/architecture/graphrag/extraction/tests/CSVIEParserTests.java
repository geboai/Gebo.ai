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
	private static final String CAOTHIC_CSV_TEST = "entity;person;Samael Aun Weor;Gnostic teacher and author;0.95;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;concept;Pentagramma;A five-pointed star used in gnostic rituals;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Vello d'Oro, Tesoro della Luce, Incisioni della Grande Luce;  \r\n"
			+ "entity;concept;Stella Fiammeggiante;Another name for the Pentagramma in gnostic tradition;0.90;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Pentagramma;  \r\n"
			+ "entity;concept;Vello d'Oro;The Treasure of Light, a key gnostic concept;0.80;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;  \r\n"
			+ "entity;concept;Tesoro della Luce;The Treasure of Light, same as Vello d'Oro;0.75;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;  \r\n"
			+ "entity;person;Gabriel;One of the five Geni mentioned in the text;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;person;Raphael;Another of the five Geni;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;person;Uriel;Third of the five Geni;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;person;Michael;Fourth of the five Geni;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;person;Samael;Fifth of the five Geni, also known as Samael Aun Weor;0.90;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;;  \r\n"
			+ "entity;concept;Incisioni della Grande Luce;Five carvings of the Great Light, part of the Stella Fiammeggiante;0.80;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;  \r\n"
			+ "event;ritual;Creazione del Pentagramma;Creation of the magical pentagram in gnostic rituals;0.90;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Pentagramma;  \r\n"
			+ "event;ritual;Invocazione degli Assistenti;Invocation of the five Geni during rituals;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Gabriel, Raphael, Uriel, Michael, Samael;  \r\n"
			+ "event;action;Uso degli Elementi per la Consecrazione;Use of the four elements (Fire, Air, Water, Earth) in consecrating the pentagram;0.80;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Fuoco, Aria, Acqua, Terra;  \r\n"
			+ "event;action;Soffiatura sul Pentagramma;Blowing five times on the magical pentagram;0.75;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;  \r\n"
			+ "relation;contains;Pentagramma-Tesoro della Luce;The Pentagram contains the Treasure of Light;0.90;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Tesoro della Luce;  \r\n"
			+ "relation;contains;Stella Fiammeggiante-Incisioni;The Stella Fiammeggiante contains the five carvings of the Great Light;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Incisioni della Grande Luce;  \r\n"
			+ "entity_alias;element;Vello d'Oro;Alternative name for Tesoro della Luce;0.95;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;Tesoro della Luce;  \r\n"
			+ "event_alias;action;Invocazione dei Geni;Alternative term for the invocation of the five Geni in rituals;0.85;7e4529e5-dbb2-4d35-8685-3e77c01a1b37;;\r\n"
			+ "entity;person;Giuseppe da Arimatea;\"Historical figure mentioned in the context of apocryphal gospels\";0.95;277bc148-455b-4a3f-b5f5-7e4da9fa3a72;;;;  \r\n"
			+ "entity;person;Ponzio Pilato;\"Notable figure mentioned in the context of apocryphal gospels\";0.95;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;  \r\n"
			+ "event;natività di Maria;\"Event describing the birth of Mary, a key figure in Christian tradition\";0.90;277bc148-455b-4a3f-b5f5-7e4da9fa3a72;;;;  \r\n"
			+ "event;dormizione di Maria;\"Event describing the assumed dormition or rest of Mary\";0.85;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;  \r\n"
			+ "relation;part-of;VANGELO DI TOMMASO;\"Relates to the福音of Thomas as part of the apocryphal gospels\";0.80;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;  \r\n"
			+ "relation;part-of;IL VANGELO DI GIUDA ISCARIOTA;\"Relates to the福音of Judas Iscariot as part of the apocryphal gospels\";0.80;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;  \r\n"
			+ "entity_alias;religious_text;\"VANGELO DI TOMMASO\";\"An alias for the Gospel of Thomas, an apocryphal text\";0.95;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;  \r\n"
			+ "entity_alias;religious_text;\"IL VANGELO DI GIUDA ISCARIOTA\";\"An alias for the Gospel of Judas Iscariot, an apocryphal text\";0.95;3dddb3b2-00e8-4159-a5ed-8ec9f306f62c;;;;\r\n"
			+ "entity;person;-;-;-;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "entity;concept;\"Telaio di Dio\";\"The divine loom mentioned in alchemical contexts.\";0.9;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "entity;concept;\"Vaso Ermetico\";\"A Hermetic vessel used in spiritual and alchemical work.\";0.9;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "entity;concept;\"lingam generatore\";\"The male sexual organ, symbolizing generative power in esoteric traditions.\";0.9;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "entity;concept;\"yoni femminile\";\"The female sexual organ, symbolizing receptivity in esoteric traditions.\";0.9;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "event;action;\"Generare elettricità sessuale trascendente\";\"The generation of transcendent sexual energy as part of alchemical work.\";0.8;78bfce20-d605-4ea3-bdf5-b21903e25659;-;-  \r\n"
			+ "relation;part-of;\"Vaso Ermetico e Telaio di Dio\";\"The Hermetic Vessel is part of the divine loom in alchemical work.\";0.8;78bfce20-d605-4ea3-bdf5-b21903e25659;\"Telaio di Dio,Vaso Ermetico\";-  \r\n"
			+ "";

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
