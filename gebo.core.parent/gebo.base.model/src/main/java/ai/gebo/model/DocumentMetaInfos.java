/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model;

import java.util.List;

/**
 * AI generated comments
 * The {@code DocumentMetaInfos} class contains a collection of constant string values
 * that represent various metadata keys associated with documents. These keys are
 * utilized to manage and organize document attributes effectively.
 */
public class DocumentMetaInfos {

    // Constants representing different metadata attributes
    public static final String CONTENT_CODE = "CONTENT_CODE";
    public static final String CONTENT_PAGE = "CONTENT_PAGE";
    public static final String SUBTITLE = "SUBTITLE";
    public static final String TITLE = "TITLE";
    public static final String CONTENT_EXTENSION = "CONTENT_EXTENSION";
    public static final String CONTENT_ORIGINAL_URL = "CONTENT_ORIGINAL_URL";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String PROJECT_CODE = "PROJECT_CODE";
    public static final String KNOWLEDGEBASE_CODE = "KNOWLEDGEBASE_CODE";
    public static final String PROJECT_ENDPOINT_CODE = "PROJECT_ENDPOINT_CODE";
    public static final String CONTENT_DESCRIPTION = "CONTENT_DESCRIPTION";
    public static final String GEBO_FILE_TYPE_ID = "GEBO_FILE_TYPE_ID";
    public static final String GEBO_FILE_TYPE_DESCRIPTION = "GEBO_FILE_TYPE_DESCRIPTION";
    public static final String GEBO_FILE_TREAT_AS = "GEBO_FILE_TREAT_AS";
    public static final String GEBO_FILE_NAME = "GEBO_FILE_NAME";
    public static final String GEBO_FILE_RELATIVE_PATH = "GEBO_FILE_RELATIVE_PATH";
    public static final String GEBO_FILE_FULLPATH = "GEBO_FILE_FULLPATH";
    public static final String GEBO_FILE_ARCHETYPEID = "GEBO_FILE_ARCHETYPEID";
    public static final String GEBO_ARCHIVE_FULLPATH = "GEBO_ARCHIVE_FULLPATH";
    public static final String GEBO_ARCHIVE_INTERNALPATH = "GEBO_ARCHIVE_INTERNALPATH";
    public static final String GEBO_TOKEN_LENGTH = "GEBO_TOKEN_LENGTH";
    public static final String GEBO_BYTES_LENGTH = "GEBO_BYTES_LENGTH";
    public static final String GEBO_EMBEDDING_METADATA = "GEBO_EMBEDDING_METADATA";

    /**
     * A list of all attribute constants defined in this class.
     * This list provides an easy way to access and iterate over all
     * available metadata attributes.
     */
    public static final List<String> ALL_ATTRIBUTES = List.of(
        CONTENT_CODE, CONTENT_PAGE, CONTENT_EXTENSION, CONTENT_ORIGINAL_URL,
        CONTENT_TYPE, PROJECT_CODE, KNOWLEDGEBASE_CODE, PROJECT_ENDPOINT_CODE,
        CONTENT_DESCRIPTION, GEBO_FILE_NAME, GEBO_FILE_TREAT_AS,
        GEBO_FILE_TYPE_DESCRIPTION, GEBO_FILE_TYPE_ID, GEBO_FILE_FULLPATH,
        GEBO_ARCHIVE_FULLPATH, GEBO_ARCHIVE_INTERNALPATH, GEBO_FILE_ARCHETYPEID,
        GEBO_FILE_RELATIVE_PATH, GEBO_TOKEN_LENGTH, GEBO_BYTES_LENGTH,
        GEBO_EMBEDDING_METADATA
    );
}