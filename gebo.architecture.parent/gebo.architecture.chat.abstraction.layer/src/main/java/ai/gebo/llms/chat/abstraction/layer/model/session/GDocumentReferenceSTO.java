package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.ReferenceType;
import ai.gebo.model.base.GeboComponentInfo;
import lombok.Data;

@Data
public class GDocumentReferenceSTO {
	private String code = null;
	private String description = null;
	/**
	 * The code of the parent virtual folder.
	 */

	private String parentVirtualFolderCode = null;

	/**
	 * The absolute path of the filesystem object.
	 */

	private String absolutePath = null;

	/**
	 * The code of the parent project associated with this filesystem object. This
	 * is a reference to a GProject.
	 */

	private String parentProjectCode = null;

	/**
	 * The code of the root knowledgebase associated with this filesystem object.
	 * This is a reference to a GKnowledgeBase.
	 */

	private String rootKnowledgebaseCode = null;

	/**
	 * The URI of the filesystem object.
	 */
	private String uri = null;

	/**
	 * The relative path of the filesystem object.
	 */
	private String relativePath = null;

	/**
	 * The name of the filesystem object.
	 */

	private String name = null;

	/**
	 * Flag indicating whether the filesystem object is marked as deleted.
	 */
	private Boolean deleted = false;

	/**
	 * ID of the messaging module associated with this filesystem object.
	 */

	private String messagingModuleId = null;

	/**
	 * Flag indicating whether the filesystem object is nested in an archive.
	 */
	private Boolean nestedInArchive = null;

	/**
	 * The absolute path of the archive containing this filesystem object.
	 */
	private String absoluteArchivePath = null;

	/**
	 * The internal path within the archive.
	 */
	private String archiveInternalPath = null;

	/**
	 * Custom metadata information associated with this filesystem object.
	 */
	private Map<String, Object> customMetaInfos = null;

	/**
	 * The ID of the latest job associated with this filesystem object.
	 */

	private String lastesJobId = null;

	/**
	 * Unique identifier for synchronization purposes.
	 */
	private String synchronizationUUID = null;

	/**
	 * File extension (e.g., 'pdf', 'docx'). Indexed with a hash for fast lookup.
	 */

	private String extension = null;

	/**
	 * Content type of the file (e.g., 'text/plain', 'application/pdf'). Indexed
	 * with a hash for fast lookup.
	 */

	private String contentType = null;

	/**
	 * Identifier for the archetype of the file. Indexed with a hash for fast
	 * lookup.
	 */

	private String geboFileArchetypeId = null;

	/**
	 * Size of the file in bytes.
	 */
	private Long fileSize = null;

	/**
	 * Flag indicating whether the content type is unmanaged.
	 */
	private Boolean unmanagedContentType = null;

	/**
	 * Type of the reference (FILE or WEB).
	 */
	private ReferenceType referenceType = null;

	/**
	 * Flag indicating whether vectorization of the content was skipped.
	 */
	private Boolean skippedVectorizationContent = null;

	/**
	 * Artificially generated content of the document.
	 */
	private String artificiallyGeneratedContent = null;

	private GeboComponentInfo originComponent = null;

	public static GDocumentReferenceSTO of(GDocumentReference reference) {
		GDocumentReferenceSTO out = new GDocumentReferenceSTO();
		try {
			BeanUtils.copyProperties(out, reference);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(
					"Exception copying properties, let's refactor this point with old fashioned set/get", e);
		}
		return out;
	}

}
