/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

/**
 * Gebo.ai comment agent
 * 
 * Exception class representing errors that occur while browsing a virtual filesystem.
 * This exception can be constructed with a message, a cause, or both.
 */
public class VirtualFilesystemBrowsingException extends Exception {

    /**
     * Default constructor that creates a new instance of VirtualFilesystemBrowsingException with no detail message or cause.
     */
    public VirtualFilesystemBrowsingException() {
        
    }

    /**
     * Constructs a new VirtualFilesystemBrowsingException with the specified detail message.
     *
     * @param message The detail message which can later be retrieved by the getMessage() method.
     */
    public VirtualFilesystemBrowsingException(String message) {
        super(message);
    }

    /**
     * Constructs a new VirtualFilesystemBrowsingException with the specified cause.
     *
     * @param cause The cause which can later be retrieved by the getCause() method.
     */
    public VirtualFilesystemBrowsingException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new VirtualFilesystemBrowsingException with the specified detail message and cause.
     *
     * @param message The detail message which can later be retrieved by the getMessage() method.
     * @param cause The cause which can later be retrieved by the getCause() method.
     */
    public VirtualFilesystemBrowsingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new VirtualFilesystemBrowsingException with the specified detail message, cause, 
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            The detail message which can later be retrieved by the getMessage() method.
     * @param cause              The cause which can later be retrieved by the getCause() method.
     * @param enableSuppression  Whether or not suppression is enabled or disabled.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public VirtualFilesystemBrowsingException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}