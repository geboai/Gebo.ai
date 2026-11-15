/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.graphrag.extraction.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;

/**
 * The relations a model extracts reach the graph: the CSV reading keeps the ones
 * between the entities it found, whatever their case, and the cleaning keeps the
 * complete ones.
 */
class ExtractedRelationsKeptTest {

	private static final String CSV = "entity;person;Alice Rossi;The quality manager;0.9;CHUNK-1;;\r\n"
			+ "entity;company;acme corp;The supplier;0.8;CHUNK-1;;\r\n"
			+ "relation;works_for;works for;Alice Rossi works for Acme Corp;0.8;CHUNK-1;Alice Rossi,Acme Corp;\r\n"
			+ "event;meeting;Supplier audit;The yearly audit of the supplier;0.7;CHUNK-1;alice rossi,ACME CORP;\r\n";

	@Test
	void theRelationsBetweenTheEntitiesFoundAreKept() throws Exception {
		LLMExtractionResult read = CSVIEParser.parseCSV(CSV);

		assertEquals(2, read.getEntities().size());
		assertEquals(1, read.getRelations().size(), "the relation between two entities found");
		RelationObject relation = read.getRelations().get(0);
		assertSame(entity(read, "alice rossi"), relation.getFromEntity(), "the entity found, with its type");
		assertSame(entity(read, "acme corp"), relation.getToEntity());
		assertEquals(1, read.getEvents().size(), "an event with its participants found");
		assertEquals(2, read.getEvents().get(0).getParticipantEntities().size());

		LLMExtractionResult cleaned = GraphDataExtractionServiceImpl.clean(read);

		assertEquals(1, cleaned.getRelations().size(), "a complete relation survives the cleaning");
		assertEquals(1, cleaned.getEvents().size());
	}

	@Test
	void theCleaningDropsTheIncompleteRelationsOnly() {
		LLMExtractionResult extracted = new LLMExtractionResult();
		EntityObject alice = entity("Alice", "PERSON");
		EntityObject acme = entity("Acme", "COMPANY");
		List<RelationObject> relations = new ArrayList<>();
		relations.add(relation("WORKS_FOR", alice, acme));
		relations.add(relation(null, alice, acme));
		relations.add(relation("WORKS_FOR", alice, entity("Nameless", null)));
		relations.add(relation("WORKS_FOR", alice, null));
		relations.add(null);
		extracted.setEntities(List.of(alice, acme));
		extracted.setRelations(relations);

		LLMExtractionResult cleaned = GraphDataExtractionServiceImpl.clean(extracted);

		assertEquals(1, cleaned.getRelations().size());
		assertEquals("WORKS_FOR", cleaned.getRelations().get(0).getType());
		assertEquals(2, cleaned.getEntities().size());
	}

	private static EntityObject entity(LLMExtractionResult result, String name) {
		return result.getEntities().stream().filter(x -> x.getName().trim().equalsIgnoreCase(name)).findFirst()
				.orElseThrow();
	}

	private static EntityObject entity(String name, String type) {
		EntityObject entity = new EntityObject();
		entity.setName(name);
		entity.setType(type);
		return entity;
	}

	private static RelationObject relation(String type, EntityObject from, EntityObject to) {
		RelationObject relation = new RelationObject();
		relation.setType(type);
		relation.setFromEntity(from);
		relation.setToEntity(to);
		return relation;
	}
}
