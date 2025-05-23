/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contentsystems.abstraction.layer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * This class contains unit tests for navigating a virtual filesystem's structure and verifying its integrity.
 */
public class VirtualFileSystemNavigationTests {
    // Logger for logging information
    static Logger LOGGER = LoggerFactory.getLogger(VirtualFileSystemNavigationTests.class);

    /**
     * Represents a generic navigable tree structure with a root and child nodes.
     * 
     * @param <RootType>  Type of the root node
     * @param <ChildType> Type of the child nodes
     */
    public static class NavigableTree<RootType, ChildType> {
        // Reference to the parent node
        public NavigableTree<?, ChildType> parent = null;
        // The root value of this tree node
        public RootType rootValue = null;
        // List of child nodes
        public List<NavigableTree<ChildType, ChildType>> childs = new ArrayList<NavigableTree<ChildType, ChildType>>();
    }

    // Array representing the roots of the navigable tree structure
    public static NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> root[] = new NavigableTree[2];

    // Static block to initialize the root array with test data
    static {
        for (int i = 0; i < root.length; i++) {
            root[i] = new NavigableTree<GVirtualFilesystemRoot, VFilesystemReference>();
            root[i].rootValue = new GVirtualFilesystemRoot();
            root[i].rootValue.setAbsolutePath("/" + i);
            root[i].rootValue.setCode("" + i);
            root[i].rootValue.setDescription("Root " + i);
            for (int j = 0; j < 10; j++) {
                NavigableTree<VFilesystemReference, VFilesystemReference> child = new NavigableTree<VFilesystemReference, VFilesystemReference>();
                child.parent = root[i];
                root[i].childs.add(child);
                child.rootValue = new VFilesystemReference();
                child.rootValue.root = root[i].rootValue;
                child.rootValue.path = new PathInfo();
                child.rootValue.path.absolutePath = root[i].rootValue.getAbsolutePath() + "/" + j;
                child.rootValue.path.name = "" + j;
                child.rootValue.path.folder = true;
                for (char ch = 'a'; ch <= 'e'; ch++) {
                    NavigableTree<VFilesystemReference, VFilesystemReference> childOfChild = new NavigableTree<VFilesystemReference, VFilesystemReference>();
                    child.childs.add(childOfChild);
                    childOfChild.rootValue = new VFilesystemReference();
                    childOfChild.rootValue.root = root[i].rootValue;
                    childOfChild.rootValue.path = new PathInfo();
                    childOfChild.rootValue.path.absolutePath = child.rootValue.path.absolutePath + "/" + ch;
                    childOfChild.rootValue.path.name = "" + ch;
                    childOfChild.rootValue.path.folder = false;
                    childOfChild.parent = child;
                }
            }
        }
    }

    /**
     * Returns a list of child nodes given a browsing parameter, searching within the root.
     * 
     * @param param The browsing parameters which include path and root info
     * @return List of child PathInfo objects
     */
    static List<PathInfo> getChilds(BrowseParam param) {
        for (int i = 0; i < root.length; i++) {
            if (param.root.getAbsolutePath().equals(root[i].rootValue.getAbsolutePath())) {
                return getChilds(param, root[i]);
            }
        }
        return List.of();
    }

    /**
     * Helper method to recursively get child nodes of a given root.
     * 
     * @param param The browsing parameters which include path and root info
     * @param root  The current root node
     * @return List of child PathInfo objects
     */
    private static List<PathInfo> getChilds(BrowseParam param,
            NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> root) {
        if (param.path == null) {
            return root.childs.stream().map(x -> x.rootValue.path).toList();
        } else {
            NavigableTree<VFilesystemReference, VFilesystemReference> node = findNode(root, param.path);
            if (node != null) {
                return node.childs.stream().map(x -> x.rootValue.path).toList();
            }
        }
        return List.of();
    }

    /**
     * Finds the node for a given path starting from the provided root.
     * 
     * @param root The root node of the tree to search
     * @param path The path information containing the absolute path
     * @return The NavigableTree node corresponding to the path if found, otherwise null
     */
    private static NavigableTree<VFilesystemReference, VFilesystemReference> findNode(
            NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> root, PathInfo path) {
        for (NavigableTree<VFilesystemReference, VFilesystemReference> child : root.childs) {
            NavigableTree<VFilesystemReference, VFilesystemReference> found = findNode(root, child, path);
            if (found != null)
                return found;
        }
        return null;
    }

    /**
     * Helper method to find a specific child node starting from a given node and path.
     * 
     * @param root  The root from where searching starts
     * @param child A specific child node to check against the path
     * @param path  The path information containing the absolute path
     * @return The NavigableTree node corresponding to the path if found, otherwise null
     */
    private static NavigableTree<VFilesystemReference, VFilesystemReference> findNode(
            NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> root,
            NavigableTree<VFilesystemReference, VFilesystemReference> child, PathInfo path) {
        if (child.rootValue.path != null) {
            if (child.rootValue.path.absolutePath.equals(path.absolutePath))
                return child;
            for (NavigableTree<VFilesystemReference, VFilesystemReference> childItem : child.childs) {
                NavigableTree<VFilesystemReference, VFilesystemReference> found = findNode(root, childItem, path);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Fake context implementation for IGVirtualFileSystemContext interface used for testing purposes.
     */
    static class FakeContext implements IGVirtualFileSystemContext {

    }

    /**
     * Test implementation of IGVirtualFilesystemBrowsingService to simulate browsing behaviors in a virtual filesystem.
     */
    static class TestVirtualFilesystemBrowsingService implements IGVirtualFilesystemBrowsingService<FakeContext> {
        static Logger LOGGER = LoggerFactory.getLogger(TestVirtualFilesystemBrowsingService.class);
        static ObjectMapper mapper = new ObjectMapper();
        static {

        }

        /**
         * Fetches the list of root directories available in the virtual filesystem context.
         * 
         * @param context The context within which the operation is performed
         * @return Operation status containing a list of root directories
         * @throws VirtualFilesystemBrowsingException if browsing fails
         */
        @Override
        public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(FakeContext context)
                throws VirtualFilesystemBrowsingException {
            LOGGER.info("Begin getRoots(..)");
            List<GVirtualFilesystemRoot> rootList = new ArrayList<GVirtualFilesystemRoot>();
            for (NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> r : root) {
                rootList.add(r.rootValue);
            }
            LOGGER.info("End getRoots(..)");
            return OperationStatus.of(rootList);
        }

        /**
         * Converts an object to a formatted JSON string representation.
         * 
         * @param o The object to convert
         * @return JSON string representation or an error message if conversion fails
         */
        private String o2s(Object o) {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } catch (Throwable t) {
                return "Cannot transform to JSON";
            }
        }

        /**
         * Browses the virtual file system based on provided parameters and returns the child paths.
         * 
         * @param param   The browsing parameters which include path and root info
         * @param context The context within which the operation is performed
         * @return Operation status containing a list of child paths
         * @throws VirtualFilesystemBrowsingException if browsing fails
         */
        @Override
        public OperationStatus<List<PathInfo>> browse(BrowseParam param, FakeContext context)
                throws VirtualFilesystemBrowsingException {
            LOGGER.info("Begin browse(" + o2s(param) + ",..)");
            OperationStatus<List<PathInfo>> status = OperationStatus.of(getChilds(param));
            LOGGER.info("End browse(" + o2s(param) + ",..) returning " + o2s(status));
            return status;
        }

        /**
         * Indicates if the virtual filesystem supports navigation status.
         * 
         * @return true if navigation status is supported, otherwise false
         */
        @Override
        public boolean isSupportsNavigationStatus() {

            return true;
        }

        /**
         * Gets the parent of a given filesystem reference within the specified context.
         * 
         * @param reference The reference to fetch the parent of
         * @param context   The context within which the operation is performed
         * @return The parent filesystem reference
         * @throws VirtualFilesystemBrowsingException if an error occurs
         */
        @Override
        public VFilesystemReference getParent(VFilesystemReference reference, FakeContext context)
                throws VirtualFilesystemBrowsingException {
            if (reference.root != null) {
                for (int i = 0; i < root.length; i++) {
                    if (root[i].rootValue.getAbsolutePath().equals(reference.root.getAbsolutePath())) {
                        NavigableTree<VFilesystemReference, VFilesystemReference> node = findNode(root[i],
                                reference.path);
                        NavigableTree<?, VFilesystemReference> parent = node.parent;
                        if (parent.rootValue instanceof VFilesystemReference) {
                            return (VFilesystemReference) parent.rootValue;
                        } else {
                            GVirtualFilesystemRoot root = (GVirtualFilesystemRoot) parent.rootValue;
                            VFilesystemReference rootReference = new VFilesystemReference();
                            rootReference.root = root;
                            return rootReference;
                        }
                    }
                }
            }
            return null;
        }

        /**
         * Retrieves the navigation status for a list of filesystem references within a specified context.
         * 
         * @param references The list of filesystem references to navigate
         * @param context    The context within which the operation is performed
         * @return The navigation status operation result
         * @throws VirtualFilesystemBrowsingException if an error occurs during navigation
         */
        @Override
        public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> navigationStatus(
                List<VFilesystemReference> references, FakeContext context) throws VirtualFilesystemBrowsingException {
            LOGGER.info("Begin navigationStatus(" + o2s(references) + ",..)");
            OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> nstatus = IGVirtualFilesystemBrowsingService.super.navigationStatus(
                    references, context);
            LOGGER.info("End navigationStatus(" + o2s(references) + ",..) returning " + o2s(nstatus));
            return nstatus;
        }
    }

    // Context object used for testing
    static FakeContext context = new FakeContext();
    // Service object used for testing navigation in the virtual filesystem
    static TestVirtualFilesystemBrowsingService testedService = new TestVirtualFilesystemBrowsingService();

    /**
     * Retrieves a filesystem reference from the tree for a given index path.
     * 
     * @param i Array of integers representing indices of tree nodes to traverse
     * @return The filesystem reference at the specified index path
     */
    static VFilesystemReference getByIndexes(int... i) {
        if (i == null)
            throw new RuntimeException("Pass an array!");
        if (i.length > 3)
            throw new RuntimeException("The test tree has 3 as maximum depth");
        if (i.length <= 0)
            throw new RuntimeException("The test tree has 1 as minimum depth");
        NavigableTree<GVirtualFilesystemRoot, VFilesystemReference> choosenRoot = root[i[0]];
        if (i.length == 1) {
            VFilesystemReference r = new VFilesystemReference();
            r.root = choosenRoot.rootValue;
            return r;
        }
        NavigableTree<VFilesystemReference, VFilesystemReference> chosenFirstStep = choosenRoot.childs.get(i[1]);
        if (i.length == 2)
            return chosenFirstStep.rootValue;
        return chosenFirstStep.childs.get(i[2]).rootValue;

    }

    /**
     * Test the structure of the virtual filesystem to ensure correct navigation through its hierarchical model.
     * 
     * @throws VirtualFilesystemBrowsingException if an error occurs during navigation
     */
    @Test
    public void testStructure() throws VirtualFilesystemBrowsingException {

        OperationStatus<List<GVirtualFilesystemRoot>> roots = testedService.getRoots(context);
        assertEquals(2, roots.getResult().size(), "Attended 2 roots");
        for (GVirtualFilesystemRoot root : roots.getResult()) {
            BrowseParam param = new BrowseParam();
            param.root = root;
            OperationStatus<List<PathInfo>> childs = testedService.browse(param, context);
            assertEquals(10, childs.getResult().size(), "Attended 10 childs");
            for (PathInfo child : childs.getResult()) {
                param = new BrowseParam();
                param.root = root;
                param.path = child;
                OperationStatus<List<PathInfo>> childsOfChilds = testedService.browse(param, context);
                assertEquals(5, childsOfChilds.getResult().size(), "Attended 10 childs");
            }
        }
    }

    /**
     * Test tree navigation and ensure the integrity of reconstructed paths in the virtual filesystem.
     * 
     * @throws VirtualFilesystemBrowsingException if an error occurs during navigation
     */
    @Test
    public void testTreeNavigationReconstruction() throws VirtualFilesystemBrowsingException {
        VFilesystemReference item = getByIndexes(new int[] { 0, 2, 1 });
        VFilesystemReference item2 = getByIndexes(new int[] { 0, 3, 1 });
        VFilesystemReference item3 = getByIndexes(new int[] { 1, 3, 1 });
        VFilesystemReference item4 = getByIndexes(new int[] { 1, 2, 1 });
        LOGGER.info(item.toString());
        LOGGER.info(item2.toString());
        LOGGER.info(item3.toString());
        LOGGER.info(item4.toString());
        OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> tree = testedService
                .navigationStatus(List.of(item), context);
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item),
                "The node must be in the set of opened and reachable nodes of the tree");

        tree = testedService.navigationStatus(List.of(item2), context);
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item2),
                "The node must be in the set of opened and reachable nodes of the tree");

        tree = testedService.navigationStatus(List.of(item3), context);
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item3),
                "The node must be in the set of opened and reachable nodes of the tree");
        tree = testedService.navigationStatus(List.of(item4), context);
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item4),
                "The node must be in the set of opened and reachable nodes of the tree");
        tree = testedService.navigationStatus(List.of(item, item2), context);
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertTrue(tree.getResult().get(0).isContained(item),
                "The node must be in the set of opened and reachable nodes of the tree");
        assertTrue(tree.getResult().get(0).isContained(item2),
                "The node must be in the set of opened and reachable nodes of the tree");
        tree = testedService.navigationStatus(List.of(item3, item4), context);
        assertEquals(1, tree.getResult().size(), "Only 1 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item3),
                "The node must be in the set of opened and reachable nodes of the tree");
        assertTrue(tree.getResult().get(0).isContained(item4),
                "The node must be in the set of opened and reachable nodes of the tree");
        tree = testedService.navigationStatus(List.of(item, item2, item3, item4), context);
        assertEquals(2, tree.getResult().size(), "Only 2 tree root found");
        assertFalse(tree.isHasErrorMessages(), "Navigation tree not with error");
        assertTrue(tree.getResult().get(0).isContained(item),
                "The node must be in the set of opened and reachable nodes of the tree");
        assertTrue(tree.getResult().get(0).isContained(item2),
                "The node must be in the set of opened and reachable nodes of the tree");
        assertTrue(tree.getResult().get(1).isContained(item3),
                "The node must be in the set of opened and reachable nodes of the tree");
        assertTrue(tree.getResult().get(1).isContained(item4),
                "The node must be in the set of opened and reachable nodes of the tree");
    }

}