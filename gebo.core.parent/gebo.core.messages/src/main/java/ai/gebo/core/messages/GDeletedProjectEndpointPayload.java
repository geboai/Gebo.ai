/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;

/**
 * AI generated comments
 * Represents payload data for a deleted project endpoint message.
 * This class extends GBaseMessagePayload and encapsulates details about a specific
 * project endpoint that has been deleted.
 * <p>
 * The endpoint travels as a shareable {@link GCentralizedProjectEndpoint} so the message can be
 * deserialized in every module, even where the concrete endpoint extension is not on the classpath.
 * Because the underlying object is already removed from persistence when this message is dispatched,
 * receivers cannot reconstruct it via {@code findByReference}; disposal therefore relies on the
 * carried {@link GCentralizedProjectEndpoint#getRemoteProjectReference() remote reference} (real
 * class name + code) and, when required to resolve the content management system, on
 * {@link #getContentManagementSystemCode()}.
 */
public class GDeletedProjectEndpointPayload extends GBaseMessagePayload {

    // The shareable centralized view of the project endpoint that has been deleted.
    private GCentralizedProjectEndpoint endpoint = null;

    // The originating content management system code (resolved before deletion), used to resolve
    // the content management system for disposal since the concrete endpoint no longer exists.
    private String contentManagementSystemCode = null;

    /**
     * Default constructor.
     * Initializes a new instance of the GDeletedProjectEndpointPayload class with no endpoint set.
     */
    public GDeletedProjectEndpointPayload() {

    }

    /**
     * Constructor with endpoint parameter.
     * Initializes a new instance of the GDeletedProjectEndpointPayload class with the specified deleted endpoint.
     *
     * @param endpoint the centralized project endpoint that has been deleted.
     */
    public GDeletedProjectEndpointPayload(GCentralizedProjectEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the deleted centralized project endpoint.
     *
     * @return the deleted centralized project endpoint.
     */
    public GCentralizedProjectEndpoint getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the centralized project endpoint that has been deleted.
     *
     * @param endpoint the centralized project endpoint to be set as deleted.
     */
    public void setEndpoint(GCentralizedProjectEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the originating content management system code, resolved before deletion.
     *
     * @return the content management system code, or {@code null} when not applicable.
     */
    public String getContentManagementSystemCode() {
        return contentManagementSystemCode;
    }

    /**
     * Sets the originating content management system code.
     *
     * @param contentManagementSystemCode the content management system code to set.
     */
    public void setContentManagementSystemCode(String contentManagementSystemCode) {
        this.contentManagementSystemCode = contentManagementSystemCode;
    }

}