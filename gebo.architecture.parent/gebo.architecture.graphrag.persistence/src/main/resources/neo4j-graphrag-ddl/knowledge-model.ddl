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

CREATE CONSTRAINT ux_event_object_type_title IF NOT EXISTS
FOR (n:event_object)
REQUIRE (n.type, n.title) IS UNIQUE;


