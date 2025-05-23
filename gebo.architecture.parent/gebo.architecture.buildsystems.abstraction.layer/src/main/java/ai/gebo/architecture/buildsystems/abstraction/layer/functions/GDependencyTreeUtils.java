/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ai.gebo.knlowledgebase.model.contents.GDependencyTree;

/**
 * A utility class for handling operations on GDependencyTree objects.
 * Provides methods to reassemble and analyze dependency trees.
 * AI generated comments
 */
class GDependencyTreeUtils {
    
    /**
     * A utility class for wrapping an index and a GDependencyTree substitute.
     * Used internally to manage substitution operations within dependency lists.
     */
    static class sref {
        int index = 0;
        GDependencyTree substitute = null;
    }

    /**
     * Recursively assembles the child dependencies of the given root tree.
     * Selects the most complete dependency to substitute at each node.
     * 
     * @param root The root GDependencyTree node to start assembly.
     * @param goodTrees A list of potential trees to integrate into the main tree.
     * @return The fully assembled GDependencyTree root.
     */
    private static GDependencyTree assemblateChilds(GDependencyTree root, List<GDependencyTree> goodTrees) {
        // Attempt to assemble each tree by reference if it's already appended in the global tree.
        
        List<GDependencyTree> deps = root.getDependencies();
        if (deps != null) {
            List<sref> toSubstitute = new ArrayList<sref>();
            int i = 0;
            for (GDependencyTree item : deps) {
                final int j = i;
                goodTrees.stream().forEach(x -> {
                    if (item.isSameArtifact(x)) {
                        if (countCardinality(x) > countCardinality(item)) {
                            sref s = new sref();
                            s.index = j;
                            s.substitute = x;
                            toSubstitute.add(s);
                        }
                    }
                });
                i++;
            }
            for (sref sref : toSubstitute) {
                root.getDependencies().set(sref.index, sref.substitute);
            }
        }
        // Cycle through other levels of dependencies to check if there is something to append.
        for (GDependencyTree child : root.getDependencies()) {
            assemblateChilds(child, goodTrees);
        }

        return root;
    }

    /**
     * Counts the cardinality of the given GDependencyTree.
     * Cardinality is determined by the presence of certain identifiers and the number of dependencies.
     * 
     * @param root The GDependencyTree whose cardinality is to be counted.
     * @return The cardinality of the tree.
     */
    static int countCardinality(GDependencyTree root) {
        int cardinality = root.getPackageManager() != null && root.getModuleId() != null && root.getArtifactId() != null
                ? 1
                : 0;
        if (root.getDependencies() != null) {
            for (GDependencyTree child : root.getDependencies()) {
                cardinality += countCardinality(child);
            }
        }
        return cardinality;
    }

    /**
     * Determines the most complete GDependencyTree from a list by comparing their cardinality.
     * 
     * @param roots The list of GDependencyTree objects to compare.
     * @return The tree with the highest cardinality, or null if the list is empty.
     */
    static GDependencyTree getMoreComplete(List<GDependencyTree> roots) {
        if (roots.isEmpty())
            return null;
        TreeMap<Integer, GDependencyTree> nodes = new TreeMap<Integer, GDependencyTree>();
        for (GDependencyTree item : roots) {
            nodes.put(countCardinality(item), item);
        }

        return nodes.lastEntry().getValue();
    }

    /**
     * Reassembles a GDependencyTree structure using a list of dependency trees.
     * Assembles the structure by selecting the most complete sub-trees.
     * 
     * @param root The root GDependencyTree to start re-assembly.
     * @param list The list of potential dependency sub-trees.
     * @return The reassembled GDependencyTree.
     */
    static GDependencyTree reassembleTree(GDependencyTree root, List<GDependencyTree> list) {
        Map<String, List<GDependencyTree>> map = new HashMap<String, List<GDependencyTree>>();
        list.stream().forEach(x -> {
            StringBuffer sb = new StringBuffer();
            if (x.getModuleId() != null) {
                sb.append(x.getModuleId());

            }
            sb.append(".");
            if (x.getArtifactId() != null) {
                sb.append(x.getArtifactId());
                if (!map.containsKey(sb.toString())) {
                    map.put(sb.toString(), new ArrayList<GDependencyTree>());
                }
                map.get(sb.toString()).add(x);
            }
        });
        List<GDependencyTree> goodTrees = new ArrayList<GDependencyTree>();
        map.values().stream().forEach(x -> {
            goodTrees.add(getMoreComplete(x));
        });
        return assemblateChilds(root, goodTrees);
    }

}