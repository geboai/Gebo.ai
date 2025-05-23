/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a virtual folder or a virtual file in virtual
 * filesystem representation.
 * AI generated comments
 */
public class PathInfo {
    // Absolute path of the file or directory
    public String absolutePath = null;
    
    // Name of the file or directory
    public String name = null;
    
    // Flag to determine if it's a folder; true if it is a folder, otherwise false
    public boolean folder = false;

    // Metadata type of the path
    public PathInfoMetaType metaType = PathInfoMetaType.UNKNOWN;

    /**
     * Factory method to create a PathInfo instance from a Path object.
     *
     * @param path the Path object to convert
     * @return a PathInfo object representing the given path
     */
    public static PathInfo of(Path path) {
        PathInfo info = new PathInfo();
        info.absolutePath = path.toAbsolutePath().toString();
        info.name = path.getFileName().toString();
        info.folder = Files.isDirectory(path);
        return info;
    }

    /**
     * Factory method to create a list of PathInfo instances from a File object.
     *
     * @param file the File object to convert
     * @return a list of PathInfo objects representing the files within
     *         the directory, or a single PathInfo object if it's a file
     */
    public static List<PathInfo> of(File file) {
        if (!file.exists())
            return List.of(); // Return an empty list if the file does not exist
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            List<PathInfo> out = new ArrayList<PathInfo>();
            if (files != null) {
                for (File _file : files) {
                    PathInfo _out = new PathInfo();
                    _out.absolutePath = _file.getAbsolutePath();
                    _out.name = _file.getName();
                    _out.folder = _file.isDirectory();
                    out.add(_out); // Add each file info to the list
                }
            }
            return out;
        } else {
            PathInfo out = new PathInfo();
            out.absolutePath = file.getAbsolutePath();
            out.name = file.getName();
            out.folder = false;
            return List.of(out); // Return a single-element list for a file
        }
    }
}