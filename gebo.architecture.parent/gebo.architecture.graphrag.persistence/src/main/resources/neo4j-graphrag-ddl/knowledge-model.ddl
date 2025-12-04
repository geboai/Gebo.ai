CREATE CONSTRAINT ux_entity_alias_id        IF NOT EXISTS FOR (n:entity_alias)        REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_event_alias_id         IF NOT EXISTS FOR (n:event_alias)         REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_relation_object_id     IF NOT EXISTS FOR (n:relation_object)     REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_entity_object_id       IF NOT EXISTS FOR (n:entity_object)       REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_event_object_id        IF NOT EXISTS FOR (n:event_object)        REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_document_chunk_id      IF NOT EXISTS FOR (n:document_chunk)      REQUIRE n.id IS UNIQUE;
CREATE CONSTRAINT ux_document_reference_id  IF NOT EXISTS FOR (n:document_reference)  REQUIRE n.id IS UNIQUE;
CREATE INDEX ix_document_reference_kb   IF NOT EXISTS FOR (n:document_reference) ON (n.knowledgebase_code);
CREATE INDEX ix_document_reference_kb1   IF NOT EXISTS FOR (n:document_reference) ON (n.project_code);
CREATE INDEX ix_document_reference_kb2   IF NOT EXISTS FOR (n:document_reference) ON (n.project_endpoint_class,n.project_endpoint_code);
CREATE CONSTRAINT ux_entity_object_type_name IF NOT EXISTS
FOR (n:entity_object)
REQUIRE (n.type, n.name) IS UNIQUE;

CREATE CONSTRAINT ux_event_object_type_name IF NOT EXISTS
FOR (n:event_object)
REQUIRE (n.type, n.name) IS UNIQUE;


MERGE (dc:document_chunk:__warmup__ {__name__:'docchunk'}) ;
MERGE (dr:document_reference:__warmup__ {__name__:'docreference'}) ;
MERGE (eo:entity_object:__warmup__ {__name__:'entity_object'}) ;
MERGE (ev:event_object:__warmup__ {__name__:'event_object'}) ;
MERGE (ro:relation_object:__warmup__ {__name__:'relation_object'}) ;
MERGE (ea:entity_alias:__warmup__ {__name__:'entity_alias'}) ;
MERGE (va:event_alias:__warmup__ {__name__:'event_alias'}) ;
MERGE (eac:entity_alias_chunk:__warmup__ {__name__:'entity_alias_chunk'}) ;
MERGE (vac:event_alias_chunk:__warmup__ {__name__:'event_alias_chunk'}) ;
MERGE (ric:relation_in_chunk:__warmup__ {__name__:'relation_in_chunk'}) ;
MERGE (dc)-[:chunk_of]->(dr) ;
MERGE (eac)-[:contained_in]->(dc) ;
MERGE (vac)-[:contained_in]->(dc) ;
MERGE (ric)-[:contained_in]->(dc) ;
MERGE (eac)-[:discovered_entity_alias]->(ea) ;
MERGE (vac)-[:discovered_event_alias]->(va) ;
MERGE (ric)-[:discovered_relation]->(ro) ;
MERGE (ea)-[:alias_of]->(eo) ;
MERGE (ea)-[:referred_on]->(eo) ;
MERGE (va)-[:alias_of]->(ev) ;
MERGE (va)-[:referred_on]->(ev) ;
MERGE (ro)-[:fromEntity]->(eo) ;
MERGE (ro)-[:toEntity]->(eo) ;
MERGE (ev)-[:participants]->(eo) ;
MATCH (n:__warmup__) DETACH DELETE n;



