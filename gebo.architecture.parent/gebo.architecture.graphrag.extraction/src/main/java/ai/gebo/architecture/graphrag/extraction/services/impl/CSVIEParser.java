package ai.gebo.architecture.graphrag.extraction.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj.StreamObjectTypeHeaderEnd;

import ai.gebo.architecture.graphrag.extraction.model.AbstractAliasObject.EquivalenceType;
import ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;

public class CSVIEParser {

	private static final String REFERENCED_EVENT_NAMES = "referencedEventNames";
	private static final String REFERENCED_ENTITIES_NAMES = "referencedEntitiesNames";
	private static final String DOCUMENT_FRAGMENT_ID = "documentFragmentId";
	private static final String CONFIDENCE_SCORE = "confidenceScore";
	private static final String OBJECT_DESCRIPTION = "objectDescription";
	private static final String OBJECT_NAME = "objectName";
	private static final String OBJECT_SUBTYPE = "objectSubtype";
	private static final String OBJECT_TYPE = "objectType";

	private static enum ObjectTypes {
		entity, event, relation, entity_alias, event_alias
	}

	private CSVIEParser() {

	}

	private static String[] FIELDS_LIST = new String[] { OBJECT_TYPE, OBJECT_SUBTYPE, OBJECT_NAME, OBJECT_DESCRIPTION,
			CONFIDENCE_SCORE, DOCUMENT_FRAGMENT_ID, REFERENCED_ENTITIES_NAMES, REFERENCED_EVENT_NAMES };

	public static LLMExtractionResult parseCSV(String content) throws IOException {
		LLMExtractionResult output = new LLMExtractionResult();
		// identify valid csv lines
		// <object type>;<object subtype>;<object name>;<object description>;<confidence
		// score>;<document fragment id>;<referred entities names>;<referred events
		// names>
		// <object type> is one of: entity, event, relation, entity_alias, event_alias
		if (content != null) {
			ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
			BufferedReader dis = new BufferedReader(new InputStreamReader(is));
			String line = null;
			do {
				line = dis.readLine();
				if (line != null) {
					StringTokenizer tokenizer = new StringTokenizer(line, ";");
					Map<String, String> data = new HashedMap<>();
					for (String field : FIELDS_LIST) {
						if (tokenizer.hasMoreTokens()) {
							String value = tokenizer.nextToken();
							if (value == null)
								value = "";
							data.put(field, value.trim().toUpperCase());
						}
					}
					if (data.size() >= FIELDS_LIST.length - 2) {
						treatLineData(data, output);
					}
				}
			} while (line != null);
		}
		sanitize(output);
		return output;
	}

	private static void sanitize(LLMExtractionResult output) {
		Map<String, EntityObject> entitiesByName = new HashMap<>();
		Map<String, EventObject> eventsByName = new HashMap<>();
		output.getEntities().forEach(x -> {
			if (x.getName() != null) {
				entitiesByName.put(x.getName().trim().toLowerCase(), x);
			}
		});

		List<EventObject> eventObjects = new ArrayList<>();
		for (EventObject event : output.getEvents()) {
			if (event.getParticipantEntities() != null && !event.getParticipantEntities().isEmpty()) {
				List<EntityObject> completedEntities = new ArrayList<>();
				event.getParticipantEntities().forEach(x -> {
					EntityObject matching = entitiesByName.get(x.getName());
					if (matching != null) {
						completedEntities.add(matching);
					}
				});
				event.setParticipantEntities(completedEntities);
				if (!event.getParticipantEntities().isEmpty()) {
					eventObjects.add(event);
				}
			}
		}
		output.setEvents(eventObjects);
		output.getEvents().forEach(x -> {
			if (x.getTitle() != null) {
				eventsByName.put(x.getTitle().trim().toLowerCase(), x);
			}
		});
		List<RelationObject> relations = new ArrayList<>();
		// cleanup relations and complete them
		// relations without entities being discovered in the actual fragment are
		// removed
		for (RelationObject relation : output.getRelations()) {
			if (relation.getFromEntity() != null && relation.getFromEntity().getName() != null
					&& relation.getToEntity() != null && relation.getToEntity().getName() != null
					&& entitiesByName.containsKey(relation.getToEntity().getName())
					&& entitiesByName.containsKey(relation.getFromEntity().getName())) {
				relation.setFromEntity(entitiesByName.get(relation.getFromEntity().getName()));
				relation.setToEntity(entitiesByName.get(relation.getToEntity().getName()));
				relations.add(relation);
			}
		}
		output.setRelations(relations);
		List<EntityAliasObject> entitiesAliases = new ArrayList<>();

	}

	private static void treatLineData(Map<String, String> data, LLMExtractionResult output) {

		String objectType = data.get(OBJECT_TYPE);
		if (objectType != null) {
			Optional<ObjectTypes> foundType = Stream.of(ObjectTypes.values())
					.filter(x -> x.name().equalsIgnoreCase(objectType.trim())).findAny();
			if (foundType.isPresent()) {
				ObjectTypes type = foundType.get();
				String objectSubtype = data.get(OBJECT_SUBTYPE);
				String name = data.get(OBJECT_NAME);
				String description = data.get(OBJECT_DESCRIPTION);
				String confidence = data.get(CONFIDENCE_SCORE);
				String fragmentId = data.get(DOCUMENT_FRAGMENT_ID);
				String referencedEntitiesNames = data.get(REFERENCED_ENTITIES_NAMES);
				String referencedEventNames = data.get(REFERENCED_EVENT_NAMES);
				switch (type) {
				case entity: {
					addEntity(output, objectSubtype, name, description, confidence, fragmentId);
				}
					break;
				case event: {
					addEvent(output, objectSubtype, name, description, confidence, fragmentId, referencedEntitiesNames);
				}
					break;
				case relation: {
					addRelation(output, objectSubtype, name, description, confidence, fragmentId,
							referencedEntitiesNames);
				}
					break;
				case entity_alias: {
					addEntityAlias(output, objectSubtype, name, description, confidence, fragmentId,
							referencedEntitiesNames);
				}
					break;
				case event_alias: {
					addEventAlias(output, objectSubtype, name, description, confidence, fragmentId,
							referencedEventNames);
				}
					break;
				}
			}
		}
	}

	private static void addEventAlias(LLMExtractionResult output, String objectSubtype, String name, String description,
			String confidence, String fragmentId, String referencedEventNames) {
		EventAliasObject object = new EventAliasObject();
		if (objectSubtype != null) {
			object.setType(objectSubtype);
		} else {
			object.setType("EVENT");
		}
		object.setLongDescription(description);
		assignConfidence(object, confidence);
		object.setChunkIds(fragmentId != null && fragmentId.trim().length() > 0 ? List.of(fragmentId) : List.of());
		List<String> events = parseList(referencedEventNames);
		if (events != null && events.size() > 1) {
			List<EventObject> eventList = events.stream().map(x -> {
				EventObject event = new EventObject();
				event.setTitle(x);
				return event;
			}).toList();
			object.setEquivalenceType(EquivalenceType.ALIAS);
			object.setReferenceObject(eventList.get(0));
			object.setAliasObject(eventList.get(1));
			output.getEventAliases().add(object);

		}

	}

	static final double defaultConfidence = 0.4;

	private static void assignConfidence(AbstractGraphObject object, String confidence) {
		double confidenceD = defaultConfidence;
		if (confidence != null && confidence.trim().length() > 0) {
			try {
				confidenceD = Double.parseDouble(confidence.trim());
			} catch (Throwable t) {
			}
		}
		object.setConfidence(confidenceD);
	}

	private static void addEntityAlias(LLMExtractionResult output, String objectSubtype, String name,
			String description, String confidence, String fragmentId, String referencedEntitiesNames) {
		EntityAliasObject object = new EntityAliasObject();
		if (objectSubtype != null) {
			object.setType(objectSubtype);
		} else {
			object.setType("EVENT");
		}
		object.setLongDescription(description);
		assignConfidence(object, confidence);
		object.setChunkIds(fragmentId != null && fragmentId.trim().length() > 0 ? List.of(fragmentId) : List.of());
		List<String> entities = parseList(referencedEntitiesNames);
		if (entities != null && entities.size() > 1) {

			EntityObject entityFrom = new EntityObject();
			entityFrom.setName(entities.get(0));
			object.setAliasObject(entityFrom);
			EntityObject entityTo = new EntityObject();
			entityTo.setName(entities.get(1));
			object.setReferenceObject(entityTo);
			output.getEntityAliases().add(object);

		}

	}

	private static void addRelation(LLMExtractionResult output, String objectSubtype, String name, String description,
			String confidence, String fragmentId, String referencedEntitiesNames) {

		RelationObject object = new RelationObject();
		if (objectSubtype != null) {
			object.setType(objectSubtype);
		} else {
			object.setType("EVENT");
		}
		object.setLongDescription(description);
		assignConfidence(object, confidence);
		object.setChunkIds(fragmentId != null && fragmentId.trim().length() > 0 ? List.of(fragmentId) : List.of());
		List<String> entities = parseList(referencedEntitiesNames);
		if (entities != null && entities.size() > 1) {

			EntityObject entityFrom = new EntityObject();
			entityFrom.setName(entities.get(0));
			object.setFromEntity(entityFrom);
			EntityObject entityTo = new EntityObject();
			entityTo.setName(entities.get(1));
			object.setToEntity(entityTo);
			output.getRelations().add(object);

		}

	}

	private static List<String> parseList(String field) {
		if (field != null && field.trim().length() > 0) {
			List<String> list = new ArrayList<>();
			StringTokenizer tokenizer = new StringTokenizer(field, ",");
			while (tokenizer.hasMoreTokens()) {
				String tok = tokenizer.nextToken();
				if (tok != null && tok.trim().length() > 0) {
					list.add(tok.trim());
				}
			}
			return list;
		} else
			return List.of();
	}

	private static void addEvent(LLMExtractionResult output, String objectSubtype, String name, String description,
			String confidence, String fragmentId, String referencedEntitiesNames) {
		Optional<EventObject> entry = output.getEvents().stream()
				.filter(x -> name != null && x.getTitle().equalsIgnoreCase(name.trim())).findAny();
		if (entry.isPresent()) {
			if (fragmentId != null && fragmentId.trim().length() > 0) {
				List<String> chunks = entry.get().getChunkIds();
				List<String> newChunks = new ArrayList<>();
				if (chunks != null) {
					newChunks.addAll(chunks);
				}
				newChunks.add(fragmentId);
				entry.get().setChunkIds(newChunks);

			}

		} else {
			EventObject object = new EventObject();
			if (objectSubtype != null) {
				object.setType(objectSubtype);
			} else {
				object.setType("EVENT");
			}
			object.setTitle(name);
			assignConfidence(object, confidence);
			object.setLongDescription(description);
			object.setChunkIds(fragmentId != null && fragmentId.trim().length() > 0 ? List.of(fragmentId) : List.of());
			List<String> entities = parseList(referencedEntitiesNames);
			if (entities != null && !entities.isEmpty()) {
				entities.forEach(x -> {
					EntityObject entity = new EntityObject();
					entity.setName(x);
					object.getParticipantEntities().add(entity);
				});
				output.getEvents().add(object);

			}

		}

	}

	private static void addEntity(LLMExtractionResult output, String objectSubtype, String name, String description,
			String confidence, String fragmentId) {
		Optional<EntityObject> entry = output.getEntities().stream()
				.filter(x -> name != null && x.getName().equalsIgnoreCase(name.trim())).findAny();
		if (entry.isPresent()) {
			if (fragmentId != null && fragmentId.trim().length() > 0) {
				List<String> chunks = entry.get().getChunkIds();
				List<String> newChunks = new ArrayList<>();
				if (chunks != null) {
					newChunks.addAll(chunks);
				}
				newChunks.add(fragmentId);
				entry.get().setChunkIds(newChunks);
			}

		} else {
			EntityObject object = new EntityObject();
			if (objectSubtype != null) {
				object.setType(objectSubtype);
			} else {
				object.setType("ENTITY");
			}
			object.setName(name);
			object.setLongDescription(description);
			assignConfidence(object, confidence);
			object.setChunkIds(fragmentId != null && fragmentId.trim().length() > 0 ? List.of(fragmentId) : List.of());
			output.getEntities().add(object);
		}

	}

	public static void main(String[] args) throws IOException {
		String text = "entity;person;Gesù;The central figure in the text.;1.0;CHUNK-ID-1;;;\r\n"
				+ "entity;nation;Sesso;Sexual realm or concept;0.8;CHUNK-ID-2;;;\r\n"
				+ "event;action;risorse dai morti;Jesus is given resources by the dead.;0.9;CHUNK-ID-1;;Gesù\r\n"
				+ "relation;part-of;Primo Mistero;The Second Space of the First Mystery.;1.0;CHUNK-ID-3;;Kether, l’Anziano dei Giorni, tu lo sai.\r\n"
				+ "entity_alias;company;I re del fuoco sessuale;literally \"Fire Sexual Kings\";0.7;CHUNK-ID-2;;;\r\n"
				+ "entity;nation;Padre;Father, a concept or entity;0.9;CHUNK-ID-4;;;";
		System.out.println(parseCSV(text));
	}
}
