/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GDocumentReferenceSnapshot;

/**
 * AI generated comments
 * This interface defines a repository for managing instances of {@link GDocumentReferenceSnapshot}.
 * It extends {@link IGBaseMongoDBRepository} to inherit common MongoDB repository functionalities.
 */
public interface DocumentReferenceSnapshotRepository extends IGBaseMongoDBRepository<GDocumentReferenceSnapshot> {

    /**
     * Provides the class type that this repository manages.
     * This is used by the base repository to perform operations specific to the type.
     *
     * @return The managed class type, {@link GDocumentReferenceSnapshot}.
     */
    @Override
    default Class<GDocumentReferenceSnapshot> getManagedType() {
        return GDocumentReferenceSnapshot.class;
    }
}