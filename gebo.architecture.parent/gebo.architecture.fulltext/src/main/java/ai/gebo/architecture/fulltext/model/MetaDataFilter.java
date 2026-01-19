package ai.gebo.architecture.fulltext.model;

import java.util.List;

import lombok.Data;

@Data
public class MetaDataFilter {
	private List<String> knowledgebaseCodes; // DocumentMetaInfos.KNOWLEDGEBASE_CODE
    private String projectCode;           // DocumentMetaInfos.PROJECT_CODE
    private String projectEndpointCode;   // DocumentMetaInfos.PROJECT_ENDPOINT_CODE

    private String contentCode;           // DocumentMetaInfos.CONTENT_CODE
    private String contentExtension;      // DocumentMetaInfos.CONTENT_EXTENSION
    private String contentType;           // DocumentMetaInfos.CONTENT_TYPE
    private Integer contentPage;          // DocumentMetaInfos.CONTENT_PAGE

    private String language;              // DocumentMetaInfos.LANGUAGE
    private String fileTreatAs;           // DocumentMetaInfos.GEBO_FILE_TREAT_AS
    private String referenceType;         // DocumentMetaInfos.GEBO_REFERENCE_TYPE

    private String fileRelativePathPrefix; // DocumentMetaInfos.GEBO_FILE_RELATIVE_PATH (prefix filter)

    // Optional: restrict to specific documents (two-stage retrieval doc->chunk)
    private List<String> documentCodes;

    // Optional: avoid too many chunks from same doc
    private boolean collapseByDocument = true;
    private int perDocumentInnerHits = 3; // how many chunks per doc to return when collapsed
}
