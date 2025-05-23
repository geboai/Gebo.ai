/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AI generated comments
 *
 * Controller class for handling content-related requests.
 */
@Controller
@RequestMapping("/api/users/ContentController")
public class ContentController {

    // Logger for debugging and error messages
    static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private IGSecurityService securityService; // Service for handling security-related operations
    @Autowired
    private DocumentReferenceRepository documentRepository; // Repository for accessing document references
    @Autowired
    private IGPersistentObjectManager persistentObjectManager; // Manager for persistent objects
    @Autowired
    private IGContentManagementSystemHandlerRepositoryPattern contentsHandlersRepositoryPattern; // Repository pattern for content management system handlers

    /**
     * Default constructor.
     */
    public ContentController() {

    }

    /**
     * Handles the HTTP GET request to retrieve content.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException If an error occurs during request handling
     * @throws IOException If an I/O error occurs
     */
    @GetMapping()
    public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userid = securityService.getCurrentUser().getUsername(); // Get current user's username
            String contentCode = request.getParameter("code"); // Retrieve the 'code' parameter from the request
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("User:" + userid + " required content:" + contentCode);
            }
            if (contentCode == null || contentCode.trim().length() == 0) {
                response.setStatus(404); // Set response status to 404 if content code is missing or invalid
                return;
            }
            String encoding = request.getParameter("encoding"); // Retrieve the 'encoding' parameter
            boolean asDownload = request.getParameter("asDownload") != null
                    && request.getParameter("asDownload").equalsIgnoreCase("true"); // Check if content should be downloaded
            String knowledgeBasesIds[] = request.getParameterValues("knowledgeBasesIds"); // Retrieve the knowledge base IDs

            Optional<GDocumentReference> documentR = documentRepository.findById(contentCode); // Find the document reference by content code
            long sentBytes = 0l;
            if (documentR.isEmpty()) {
                response.setStatus(404); // Set response status to 404 if document not found
            } else {
                GDocumentReference document = documentR.get();
                if (document.getArtificiallyGeneratedContent() != null) {
                    response.setStatus(200); // Set response status to 200 if the document has generated content
                    response.setContentType(document.getContentType());
                    PrintWriter writer = response.getWriter();
                    writer.write(document.getArtificiallyGeneratedContent());
                    writer.flush();
                    return;
                }
                if (document.getProjectEndpointReference() == null) {
                    response.setStatus(404); // Set response status to 404 if no endpoint reference
                    return;
                }
                GProjectEndpoint endpoint = persistentObjectManager
                        .findByReference(document.getProjectEndpointReference(), GProjectEndpoint.class); // Find project endpoint
                IGContentManagementSystemHandler handler = contentsHandlersRepositoryPattern
                        .findByHandledEndpoint(endpoint); // Find content handler for the endpoint
                if (handler == null) {
                    throw new RuntimeException("Cannot find the content handler for the project endpoint => "
                            + document.getProjectEndpointReference().toString()); // Throw exception if handler not found
                }
                try (InputStream stream = handler.streamContent(document, new HashMap<>())) {
                    if (stream == null) {
                        throw new RuntimeException("Cannot stream the content for => " + document.getCode()); // Throw exception if content stream is null
                    }
                    String fileName = document.getName(); // Get the document's name
                    sentBytes = copyOutput(stream, response, encoding, document.getContentType(), fileName,
                            document.getFileSize()); // Copy output to response
                }
                // Additional code handling archived documents and absolute paths (commented out)
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("User:" + userid + " required content:" + contentCode + " served: " + response.getStatus()
                        + " content type: " + response.getContentType() + " bytes sent: " + sentBytes);
            }
        } catch (Throwable th) {
            LOGGER.error("Error serving content", th); // Log error if exception occurs
        }
    }

    /**
     * Copies the content of the input stream to the response output, handles encoding if necessary.
     *
     * @param is the InputStream containing the content
     * @param response the HttpServletResponse to which content is written
     * @param encoding the encoding format (e.g., base64)
     * @param contentType the MIME type of the content
     * @param fileName the name of the file to be downloaded
     * @param byteLength the length of the content in bytes
     * @return the total number of bytes written to the response
     * @throws IOException If an I/O error occurs
     */
    private long copyOutput(InputStream is, HttpServletResponse response, String encoding, String contentType,
                            String fileName, long byteLength) throws IOException {
        response.setStatus(200); // Set response status to 200 (OK)
        if (encoding != null)
            response.setHeader("Content-Transfer-Encoding", "binary");
        if (contentType != null)
            response.setContentType(contentType);
        if (fileName != null) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }
        ServletOutputStream os = response.getOutputStream();
        long _byteLength = 0l;
        
        if (encoding == null || encoding.trim().length() == 0) {
            response.setContentLengthLong(byteLength);
            _byteLength = copy(is, os); // Directly copy the input stream to output stream
            if (_byteLength != byteLength) {
                LOGGER.error("Sent size info whas " + byteLength + " but sent " + _byteLength);
            }
        } else if (encoding.equalsIgnoreCase("base64")) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            copy(is, bos);
            bos.close();
            response.setHeader("content-encoding", encoding);
            byte buffer[] = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.encodeBase64(buffer)); // Encode content in base64
            response.setContentLengthLong(buffer.length);
            _byteLength = copy(bis, os);
            if (_byteLength != buffer.length) {
                LOGGER.error("Sent size info whas " + buffer.length + " but sent " + _byteLength);
            }
        }

        os.flush();
        os.close();
        return _byteLength;
    }

    /**
     * Copies bytes from the input stream to the output stream.
     *
     * @param is the InputStream to read from
     * @param os the OutputStream to write to
     * @return the number of bytes copied
     * @throws IOException if an I/O error occurs
     */
    private long copy(InputStream is, OutputStream os) throws IOException {
        long nBytes = 0;
        byte buffer[] = new byte[8192];
        int nRead = 0;
        while ((nRead = is.read(buffer)) > 0) {
            os.write(buffer, 0, nRead);
            nBytes += (long) nRead;
        }
        os.flush();
        return nBytes;
    }
}