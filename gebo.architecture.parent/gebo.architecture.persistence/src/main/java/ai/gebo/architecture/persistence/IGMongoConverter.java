/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.persistence;

/**
 * A converter the Mongo mapping applies, contributed by the module that owns
 * the type: a bean implementing this and a Spring {@code Converter} (annotated
 * {@code @ReadingConverter} or {@code @WritingConverter}) is registered on the
 * mapping whichever way Mongo is configured, Gebo's own {@code MongoConfig} or
 * Spring Boot's. The conversions are assembled before the repositories, so such
 * a converter depends on no other bean.
 */
public interface IGMongoConverter {

}
