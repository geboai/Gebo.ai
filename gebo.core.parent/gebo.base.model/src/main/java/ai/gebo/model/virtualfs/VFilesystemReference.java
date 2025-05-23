/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Class that represents a "pointer" to a virtual file system location
 * including its root and its path from the root.
 */
public class VFilesystemReference {
    @NotNull
    public GVirtualFilesystemRoot root = null; // The root of the virtual file system.
    public PathInfo path = null; // Information about the path from the root.

    /**
     * Gets the absolute path of the given VFilesystemReference.
     * 
     * @param r The VFilesystemReference from which to retrieve the absolute path.
     * @return A String representing the absolute path, or null if not available.
     */
    public static String absolutePathOf(VFilesystemReference r) {
        if (r == null)
            return null;
        return r.path != null ? r.path.absolutePath : r.root != null ? r.root.getAbsolutePath() : null;
    }

    /**
     * Creates a VFilesystemReference object from a Path object.
     * 
     * @param of The Path from which to create the VFilesystemReference.
     * @return A VFilesystemReference object that represents the given Path.
     */
    public static VFilesystemReference from(Path of) {
        if (of == null)
            return null;
        VFilesystemReference reference = new VFilesystemReference();
        reference.root = GVirtualFilesystemRoot.from(of);
        reference.path = new PathInfo();
        reference.path.absolutePath = of.toAbsolutePath().toString();
        if (of.getFileName() != null) {
            reference.path.name = of.getFileName().toString();
        } else {
            reference.path.name = "";
        }
        reference.path.folder = Files.isDirectory(of); // Determines if the path is a directory.
        return reference;
    }

    /**
     * Converts the VFilesystemReference to a Path object.
     * 
     * @return A Path object representing the VFilesystemReference, or null if
     *         neither path nor root is available.
     */
    public Path toPath() {
        if (path != null && path.absolutePath != null)
            return Path.of(path.absolutePath);
        else if (root != null) {
            return Path.of(root.getAbsolutePath());
        }
        return null;
    }

    /**
     * Returns the string representation of the VFilesystemReference.
     * 
     * @return A String representing the absolute path, or null if not available.
     */
    public String toString() {
        if (path != null && path.absolutePath != null)
            return (path.absolutePath);
        else if (root != null) {
            return (root.getAbsolutePath());
        }
        return null;
    }
}
