/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.contenthandling.interfaces.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference.ReferenceType;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments Implementation of the IGDocumentReferenceFactory
 * interface, providing methods to create various types of GDocumentReference
 * instances.
 */
@Service
public class GDocumentReferenceFactoryImpl implements IGDocumentReferenceFactory {

	/** Logger instance for logging events */
	static Logger LOGGER = LoggerFactory.getLogger(GDocumentReferenceFactoryImpl.class);

	/**
	 * Extracts and returns the file extension from the given file path.
	 * 
	 * @param file the path of the file whose extension is to be retrieved.
	 * @return the file extension as a string, or null if no extension is found.
	 */
	protected String getExtension(Path file) {
		String name = file.getFileName().toString();
		String extension = null;
		int lastPointPosition = name.lastIndexOf(".");
		if (lastPointPosition >= 0) {
			extension = name.substring(lastPointPosition).toLowerCase();
		}
		return extension;
	}

	/**
	 * Creates and returns a GDocumentReference for a given file.
	 * 
	 * @param file            the path to the file being referenced.
	 * @param codePrefix      a prefix for the code of the document reference.
	 * @param uriPrefix       a prefix for the URI of the document reference.
	 * @param completeUrl     the complete URL for the document reference.
	 * @param projectEndpoint the project endpoint associated with the reference.
	 * @param virtualFolder   the virtual folder where the document resides.
	 * @param moduleId        the module ID associated with the reference.
	 * @return the created GDocumentReference.
	 * @throws GeboContentHandlerSystemException if an error occurs during the
	 *                                           creation process.
	 */
	@Override
	public GDocumentReference createReference(Path file, String codePrefix, String uriPrefix, String completeUrl,
			GProjectEndpoint projectEndpoint, GVirtualFolder virtualFolder, String moduleId)
			throws GeboContentHandlerSystemException {
		assert projectEndpoint != null;
		assert virtualFolder != null;
		try {
			String mimeType = Files.probeContentType(file);
			String extension = getExtension(file);
			GDocumentReference reference = new GDocumentReference();
			reference.setReferenceType(ReferenceType.FILE);
			reference.setAbsolutePath(file.toAbsolutePath().toString());
			reference.setCode(codePrefix + "/" + file.getFileName().toString());
			reference.setContentType(mimeType);
			reference.setExtension(extension);
			reference.setParentVirtualFolderCode(virtualFolder.getCode());
			FileTime lastModified = Files.getLastModifiedTime(file);
			if (lastModified != null) {
				reference.setModificationDate(new Date(lastModified.toMillis()));
			}
			reference.setFileSize(Files.size(file));
			reference.setDeleted(false);
			reference.setName(file.getFileName().toString());
			reference.setMessagingModuleId(moduleId);
			reference.setSkippedVectorizationContent(false);
			if (completeUrl != null) {
				reference.setUri(completeUrl);
			} else if (uriPrefix != null) {
				reference.setUri(uriPrefix + "/" + file.getFileName().toString());
			}

			if (projectEndpoint.getVectorizeOnlyExtensions() != null
					&& !projectEndpoint.getVectorizeOnlyExtensions().isEmpty() && reference.getExtension() != null) {
				reference.setSkippedVectorizationContent(
						!projectEndpoint.getVectorizeOnlyExtensions().contains(reference.getExtension().toLowerCase()));
			}
			reference.setParentProjectCode(projectEndpoint.getParentProjectCode());
			reference.setProjectEndpointReference(new GObjectRef<GProjectEndpoint>(projectEndpoint));
			String relativePath = file.getFileName().toString();
			if (virtualFolder.getRelativePath() != null) {
				relativePath = virtualFolder.getRelativePath() + "/" + relativePath;
			}
			reference.setRelativePath(relativePath);
			return reference;
		} catch (Throwable th) {
			throw new GeboContentHandlerSystemException("exception in extractContent(...)", th);
		}
	}

	/**
	 * Creates and returns a GDocumentReference for a deleted file.
	 * 
	 * @param <ProjectEndpointType> a generic type extending GProjectEndpoint.
	 * @param file                  the path to the deleted file.
	 * @param codePrefix            a prefix for the code of the document reference.
	 * @param uriPrefix             a prefix for the URI of the document reference.
	 * @param completeUrl           the complete URL for the document reference.
	 * @param projectEndpoint       the project endpoint associated with the
	 *                              reference.
	 * @param moduleId              the module ID associated with the reference.
	 * @return the created GDocumentReference.
	 * @throws GeboContentHandlerSystemException if an error occurs during the
	 *                                           creation process.
	 */
	@Override
	public <ProjectEndpointType extends GProjectEndpoint> GDocumentReference createDeletedReference(Path file,
			String codePrefix, String uriPrefix, String completeUrl, GProjectEndpoint projectEndpoint, String moduleId)
			throws GeboContentHandlerSystemException {
		assert projectEndpoint != null;

		try {
			String mimeType = Files.probeContentType(file);
			String extension = getExtension(file);
			GDocumentReference reference = new GDocumentReference();
			reference.setReferenceType(ReferenceType.FILE);
			reference.setAbsolutePath(file.toAbsolutePath().toString());
			reference.setCode(codePrefix + "/" + file.getFileName().toString());
			reference.setContentType(mimeType);
			reference.setExtension(extension);
			reference.setFileSize(0l);
			reference.setName(file.getFileName().toString());
			reference.setMessagingModuleId(moduleId);
			reference.setDeleted(true);
			reference.setSkippedVectorizationContent(false);
			if (completeUrl != null) {
				reference.setUri(completeUrl);
			} else if (uriPrefix != null) {
				reference.setUri(uriPrefix + "/" + file.getFileName().toString());
			}
			if (projectEndpoint.getVectorizeOnlyExtensions() != null
					&& !projectEndpoint.getVectorizeOnlyExtensions().isEmpty() && reference.getExtension() != null) {
				reference.setSkippedVectorizationContent(
						!projectEndpoint.getVectorizeOnlyExtensions().contains(reference.getExtension().toLowerCase()));
			}
			reference.setParentProjectCode(projectEndpoint.getParentProjectCode());
			reference.setProjectEndpointReference(new GObjectRef<GProjectEndpoint>(projectEndpoint));

			return reference;
		} catch (Throwable th) {
			throw new GeboContentHandlerSystemException("exception in extractContent(...)", th);
		}
	}

	/**
	 * Creates and returns a GDocumentReference for an archived file entry within a
	 * ZIP archive.
	 * 
	 * @param <ProjectEndpointType> a generic type extending GProjectEndpoint.
	 * @param originalFile          the path to the original ZIP file.
	 * @param zipFile               the ZIP file containing the entry.
	 * @param entry                 the specific ZIP entry to be referenced.
	 * @param codePrefix            a prefix for the code of the document reference.
	 * @param projectEndpoint       the project endpoint associated with the
	 *                              reference.
	 * @param virtualFolder         the virtual folder where the document resides.
	 * @param messagingModuleId     the module ID associated with the reference.
	 * @return the created GDocumentReference, or null if an IOException occurs.
	 */
	@Override
	public <ProjectEndpointType extends GProjectEndpoint> GDocumentReference createArchiveReference(Path originalFile,
			ZipFile zipFile, ZipEntry entry, String codePrefix, ProjectEndpointType projectEndpoint,
			GVirtualFolder virtualFolder, String messagingModuleId) {
		assert projectEndpoint != null;
		assert virtualFolder != null;
		try {
			int extIdx = entry.getName().lastIndexOf(".");

			String mimeType = Files.probeContentType(Path.of(entry.getName()));
			String extension = extIdx >= 0 ? entry.getName().substring(extIdx) : "";
			GDocumentReference reference = new GDocumentReference();
			reference.setReferenceType(ReferenceType.FILE);
			reference.setAbsolutePath(originalFile.toAbsolutePath().toString() + "#" + entry.getName());
			reference.setCode(codePrefix + "#" + entry.getName());
			reference.setContentType(mimeType);
			reference.setExtension(extension);
			reference.setModificationDate(new Date(entry.getTime()));
			reference.setFileSize(entry.getSize());
			reference.setDeleted(false);
			reference.setName(entry.getName());
			reference.setMessagingModuleId(messagingModuleId);
			reference.setAbsoluteArchivePath(originalFile.toAbsolutePath().toString());
			reference.setArchiveInternalPath(entry.getName());
			reference.setNestedInArchive(true);
			reference.setSkippedVectorizationContent(false);
			if (virtualFolder != null) {
				reference.setRelativePath(virtualFolder.getRelativePath() + "/" + originalFile.getFileName().toString()
						+ "/" + entry.getName());
			} else {
				reference.setRelativePath(originalFile.getFileName().toString() + "/" + entry.getName());
			}

			if (projectEndpoint.getVectorizeOnlyExtensions() != null
					&& !projectEndpoint.getVectorizeOnlyExtensions().isEmpty() && reference.getExtension() != null) {
				reference.setSkippedVectorizationContent(
						!projectEndpoint.getVectorizeOnlyExtensions().contains(reference.getExtension().toLowerCase()));
			}
			reference.setParentProjectCode(projectEndpoint.getParentProjectCode());
			reference.setProjectEndpointReference(new GObjectRef<GProjectEndpoint>(projectEndpoint));

			return reference;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates and returns a GDocumentReference for a web document, utilizing the
	 * provided metadata.
	 * 
	 * @param spaceFolder           the virtual space folder associated with the web
	 *                              document.
	 * @param projectEndpoint       the project endpoint associated with the
	 *                              reference.
	 * @param code                  the code of the web document reference.
	 * @param name                  the name of the web document.
	 * @param contentType           the content type of the web document.
	 * @param url                   the URL of the web document.
	 * @param modificationTimestamp the modification timestamp of the web document.
	 * @param meta                  custom metadata associated with the web
	 *                              document.
	 * @param moduleId              the module ID associated with the reference.
	 * @return the created GDocumentReference.
	 * @throws GeboContentHandlerSystemException if an error occurs during the
	 *                                           creation process.
	 */
	@Override
	public GDocumentReference createWebDocumentReference(GVirtualFolder spaceFolder, GProjectEndpoint projectEndpoint,
			String code, String name, String contentType, String url, Date modificationTimestamp,
			HashMap<String, Object> meta, String moduleId) throws GeboContentHandlerSystemException {
		assert projectEndpoint != null;
		assert spaceFolder != null;
		GDocumentReference reference = new GDocumentReference();
		reference.setReferenceType(ReferenceType.WEB);
		reference.setAbsolutePath(null);
		reference.setCode(spaceFolder.getCode() + "/" + code);
		reference.setContentType(contentType);
		reference.setExtension(null);
		reference.setFileSize(0l);
		reference.setName(name);
		reference.setRelativePath(spaceFolder.getRelativePath() + "/" + name);
		reference.setMessagingModuleId(moduleId);
		reference.setDeleted(false);
		reference.setUri(url);
		reference.setCustomMetaInfos(meta);
		reference.setModificationDate(modificationTimestamp);
		reference.setParentVirtualFolderCode(spaceFolder.getCode());
		reference.setParentProjectCode(projectEndpoint.getCode());
		reference.setSkippedVectorizationContent(false);

		if (projectEndpoint.getVectorizeOnlyExtensions() != null
				&& !projectEndpoint.getVectorizeOnlyExtensions().isEmpty() && reference.getExtension() != null) {
			reference.setSkippedVectorizationContent(
					!projectEndpoint.getVectorizeOnlyExtensions().contains(reference.getExtension().toLowerCase()));
		}
		reference.setParentProjectCode(projectEndpoint.getParentProjectCode());
		reference.setProjectEndpointReference(new GObjectRef<GProjectEndpoint>(projectEndpoint));

		return reference;
	}

	@Override
	public GDocumentReference createReference(Path file) throws GeboContentHandlerSystemException {
		{

			try {
				String mimeType = Files.probeContentType(file);
				String extension = getExtension(file);
				GDocumentReference reference = new GDocumentReference();
				reference.setReferenceType(ReferenceType.FILE);
				reference.setAbsolutePath(file.toAbsolutePath().toString());
				reference.setCode(file.getFileName().toString());
				reference.setContentType(mimeType);
				reference.setExtension(extension);
				
				FileTime lastModified = Files.getLastModifiedTime(file);
				if (lastModified != null) {
					reference.setModificationDate(new Date(lastModified.toMillis()));
				}
				reference.setFileSize(Files.size(file));
				reference.setDeleted(false);
				reference.setName(file.getFileName().toString());
				reference.setSkippedVectorizationContent(false);
				
				return reference;
			} catch (Throwable th) {
				throw new GeboContentHandlerSystemException("exception in extractContent(...)", th);
			}
		}
	}
}