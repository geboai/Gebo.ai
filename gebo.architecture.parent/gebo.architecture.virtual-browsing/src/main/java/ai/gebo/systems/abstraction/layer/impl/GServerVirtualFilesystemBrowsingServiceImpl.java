/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.IGServerVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ServerFileSystemContext;

/**
 * Service implementation for browsing virtual filesystem structures.
 * This class provides methods to navigate and retrieve information about files and directories in a virtual filesystem context.
 * AI generated comments
 */
@Service
public class GServerVirtualFilesystemBrowsingServiceImpl implements IGServerVirtualFilesystemBrowsingService {

    /**
     * Constructor for GServerVirtualFilesystemBrowsingServiceImpl.
     * Initializes any required resources or dependencies.
     */
    public GServerVirtualFilesystemBrowsingServiceImpl() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves the roots from the given server filesystem context.
     * Each root is represented as a GVirtualFilesystemRoot object.
     *
     * @param context The server filesystem context containing information about limiting roots.
     * @return OperationStatus containing a list of GVirtualFilesystemRoot objects.
     * @throws VirtualFilesystemBrowsingException if any error occurs during processing.
     */
    @Override
    public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(ServerFileSystemContext context)
            throws VirtualFilesystemBrowsingException {
        List<GVirtualFilesystemRoot> out = new ArrayList<GVirtualFilesystemRoot>();
        if (context != null && context.getLimitingRoots() != null) {
            for (Path c : context.getLimitingRoots()) {
                GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
                root.setAbsolutePath(c.toAbsolutePath().toString());
                root.setCode(c.toAbsolutePath().toString());
                String name = c.getFileName() != null ? c.getFileName().toString() : c.toAbsolutePath().toString();
                root.setDescription(name);
                out.add(root);
            }
        }
        return OperationStatus.of(out);
    }

    /**
     * Browses the filesystem based on the provided BrowseParam and server filesystem context.
     * Retrieves information about files and directories contained within the specified path.
     *
     * @param param   The browsing parameters, including root and path information.
     * @param context The server filesystem context.
     * @return OperationStatus containing a list of PathInfo objects representing the contents of the directory.
     * @throws VirtualFilesystemBrowsingException if the root or path is invalid or not within the context limiting roots.
     */
    @Override
    public OperationStatus<List<PathInfo>> browse(BrowseParam param, ServerFileSystemContext context)
            throws VirtualFilesystemBrowsingException {
        List<PathInfo> out = new ArrayList<PathInfo>();
        if (param.root == null)
            throw new VirtualFilesystemBrowsingException("Cannot accept null root BrowseParam");
        boolean foundRoot = false;
        Path rootPath = Path.of(param.root.getAbsolutePath());
        Path openingPath = rootPath;
        if (context != null && context.getLimitingRoots() != null) {
            for (Path c : context.getLimitingRoots()) {
                if (rootPath.equals(c)) {
                    foundRoot = true;
                }

            }
        }
        if (!foundRoot)
            throw new VirtualFilesystemBrowsingException("The expressed root is not in the context limiting roots");
        if (param.path != null) {
            Path nestedPath = Path.of(param.path.absolutePath);
            if (nestedPath.startsWith(rootPath)) {
                openingPath = nestedPath;
            } else {
                throw new VirtualFilesystemBrowsingException(
                        "The expressed path: " + nestedPath.toAbsolutePath().toString() + " is not contained in "
                                + rootPath.toAbsolutePath().toString());
            }
        }
        if (Files.isDirectory(openingPath) && Files.isReadable(openingPath)) {
            try {
                Stream<Path> stream = Files.list(openingPath);
                out = stream.map(PathInfo::of).toList();
            } catch (IOException e) {
                return OperationStatus.ofError("Error accessing " + openingPath.toString(),
                        "Message: " + e.getMessage());
            }
        }
        return OperationStatus.of(out);
    }

    /**
     * Indicates whether navigation status is supported by this service.
     *
     * @return true if navigation status is supported, false otherwise.
     */
    @Override
    public boolean isSupportsNavigationStatus() {
        return true;
    }

    /**
     * Retrieves the parent of the given virtual filesystem reference.
     *
     * @param reference The filesystem reference for which the parent is to be found.
     * @param context   The server filesystem context.
     * @return VFilesystemReference representing the parent directory or null if already at the root.
     * @throws VirtualFilesystemBrowsingException if the reference is invalid or not within the context limiting roots.
     */
    @Override
    public VFilesystemReference getParent(VFilesystemReference reference, ServerFileSystemContext context)
            throws VirtualFilesystemBrowsingException {
        if (reference.root == null)
            throw new VirtualFilesystemBrowsingException("Cannot accept null root BrowseParam");
        boolean foundRoot = false;
        Path rootPath = Path.of(reference.root.getAbsolutePath());
        Path openingPath = rootPath;
        if (context != null && context.getLimitingRoots() != null) {
            for (Path c : context.getLimitingRoots()) {
                if (rootPath.equals(c)) {
                    foundRoot = true;
                }

            }
        }
        if (!foundRoot)
            throw new VirtualFilesystemBrowsingException("The expressed root is not in the context limiting roots");
        if (reference.path != null) {
            Path nestedPath = Path.of(reference.path.absolutePath);
            if (nestedPath.startsWith(rootPath)) {
                openingPath = nestedPath;
            } else {
                throw new VirtualFilesystemBrowsingException(
                        "The expressed path: " + nestedPath.toAbsolutePath().toString() + " is not contained in "
                                + rootPath.toAbsolutePath().toString());
            }
        }
        if (openingPath.equals(rootPath)) {
            VFilesystemReference out = new VFilesystemReference();
            out.root = reference.root;
            out.path = null;
            return out;
        }
        openingPath = openingPath.getParent();
        if (openingPath.equals(rootPath)) {
            VFilesystemReference out = new VFilesystemReference();
            out.root = reference.root;
            out.path = null;
            return out;
        } else if (openingPath.startsWith(rootPath)) {
            VFilesystemReference out = new VFilesystemReference();
            out.root = reference.root;
            out.path = PathInfo.of(openingPath);
            return out;
        }
        throw new VirtualFilesystemBrowsingException("Browsing inconsistent state");
    }

}