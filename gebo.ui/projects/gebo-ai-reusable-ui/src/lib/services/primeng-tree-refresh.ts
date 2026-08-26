/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { TreeNode } from "primeng/api";

/**
 * Helpers to drive a lazily loaded PrimeNG `p-tree` correctly.
 *
 * `Tree` and `UITreeNode` both run with `ChangeDetectionStrategy.OnPush`, and a
 * `UITreeNode` is re-rendered only when its `node` input changes identity (or when
 * one of its own DOM listeners fires). Filling `node.children` from an http
 * callback therefore leaves the branch painted exactly as it was: the first click
 * flipped the toggler to "expanded" but no child ever appeared, and the second
 * click - being a DOM event inside the node - was the one that finally repainted
 * it. The same staleness froze `node.loading`, which with the default
 * `loadingMode="mask"` renders neither the chevron nor a spinner, leaving the
 * toggler blank.
 *
 * The fix is the one the PrimeNG lazy loading demo uses: after having changed a
 * node, hand the tree a new value array in which the changed branch is made of
 * new node objects.
 */

/**
 * Rebuilds the branch that leads to `node` with fresh node objects, so that the
 * PrimeNG tree repaints it, and returns the array to assign to `[value]`.
 *
 * Only the ancestors of `node` are cloned: the untouched siblings keep their
 * identity, so with {@link treeNodeTrackBy} in place their views are left alone
 * and only the changed path is re-rendered. `data`, `children` entries and every
 * other node property are shared with the original nodes, so lookups the caller
 * holds on them stay valid; `parent` back references are re-pointed to the clones.
 *
 * @param roots - The array currently bound to the tree `[value]`
 * @param node - The node whose contents (children, loading, expanded, label...) changed
 * @returns A new array to assign to the tree `[value]`
 */
export function refreshTreeBranch<T>(roots: TreeNode<T>[] | undefined, node: TreeNode<T>): TreeNode<T>[] {
    let clone: TreeNode<T> = cloneTreeNode(node);
    let original: TreeNode<T> = node;
    let parent: TreeNode<T> | undefined = node.parent;
    while (parent) {
        const parentClone: TreeNode<T> = { ...parent };
        parentClone.children = (parent.children ?? []).map(child => (child === original ? clone : child));
        parentClone.children.forEach(child => (child.parent = parentClone));
        original = parent;
        clone = parentClone;
        parent = parent.parent;
    }
    return (roots ?? []).map(root => (root === original ? clone : root));
}

/**
 * Rebuilds every loaded node of a tree, so that the whole of it is repainted, and
 * returns the array to assign to the tree `[value]`.
 *
 * Needed when what changed is not the contents of a single branch but something
 * the nodes read all over the tree - a selection propagated to the descendants of
 * an entry, a deletion mark, the state carried by `data`: PrimeNG would otherwise
 * keep showing every node that the user did not click on as it was.
 *
 * The `data` of the nodes and their other properties are shared with the original
 * nodes, only the node objects themselves and the `children`/`parent` links being
 * rebuilt.
 *
 * @param roots - The array currently bound to the tree `[value]`
 * @returns A new array to assign to the tree `[value]`
 */
export function refreshTreeNodes<T>(roots: TreeNode<T>[] | undefined): TreeNode<T>[] {
    return rebuildTreeNodes(roots ?? [], undefined);
}

/**
 * Rebuilds a list of nodes and, recursively, the ones they hold.
 * @param nodes - The nodes to rebuild
 * @param parent - The node the list belongs to, undefined for the roots
 * @returns The rebuilt list
 */
function rebuildTreeNodes<T>(nodes: TreeNode<T>[], parent: TreeNode<T> | undefined): TreeNode<T>[] {
    return nodes.map(node => {
        const clone: TreeNode<T> = { ...node, parent: parent };
        if (node.children) {
            clone.children = rebuildTreeNodes(node.children, clone);
        }
        return clone;
    });
}

/**
 * Returns a copy of a node holding the same contents, ready to replace it in the
 * array bound to the tree. The children are kept as they are, their `parent` back
 * reference being moved onto the copy.
 *
 * @param node - The node to copy
 * @returns The copy
 */
export function cloneTreeNode<T>(node: TreeNode<T>): TreeNode<T> {
    const clone: TreeNode<T> = { ...node };
    clone.children?.forEach(child => (child.parent = clone));
    return clone;
}

/**
 * `trackBy` for `p-tree`, to be bound as `[trackBy]="treeNodeTrackBy"`.
 *
 * PrimeNG tracks the nodes by object identity by default, which would throw away
 * and rebuild the whole DOM of a branch every time {@link refreshTreeBranch}
 * replaces its nodes. Tracking them by key keeps the existing views and only
 * updates the `node` input of the ones that actually changed. Nodes without a key
 * fall back to the default identity tracking.
 *
 * @param index - The index of the node among its siblings
 * @param node - The node being tracked
 * @returns The tracking identity of the node
 */
export function treeNodeTrackBy(index: number, node: TreeNode<any>): any {
    return node && node.key ? node.key : node;
}
