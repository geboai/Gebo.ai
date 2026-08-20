/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * View models for the compliance data-flow graph.
 *
 * Kept separate from the generated REST stubs so the diagram templates bind to a
 * flat, render-ready shape instead of walking the
 * report -> module -> component -> flow nesting on every change detection pass.
 */

/** How far data travels to reach an endpoint - mirrors the backend enum. */
export type DataFlowLocality = "LOCAL_DEPLOYMENT" | "SAME_NETWORK" | "EXTERNAL_PROVIDER";

/** A store or interface the installation reads from or writes to. */
export interface DataFlowEndpointNode {
    /** Qualified id: moduleId.componentId&lt;-&gt;endpointId. */
    qualifiedId: string;
    localId: string;
    description: string;
    product: string;
    /** Sanitized locator - the backend guarantees it carries no credentials. */
    endpoint: string;
    input: boolean;
    output: boolean;
    types: string[];
    locality?: DataFlowLocality;
    secretReference?: string;
    personalData: boolean;
    /** Component able to erase this endpoint's data, when one is wired. */
    disposer?: string;
    /** The messaging component that reported this endpoint. */
    ownerComponent: string;
    /** The node this endpoint was reported from. */
    nodeId?: string;
}

/** An engine applied between two endpoints. */
export interface DataFlowTransformationNode {
    qualifiedId: string;
    description: string;
    engineDescription: string;
    transformFrom: string[];
    transformInto: string[];
    sourceId: string;
    destinationId: string;
    ownerComponent: string;
}

/** Counts driving the summary strip above the graph. */
export interface DataFlowSummary {
    endpoints: number;
    transformations: number;
    components: number;
    externalEndpoints: number;
    personalDataEndpoints: number;
    /** Retaining endpoints (output === true) with no disposer component wired. */
    retainingWithoutErasure: number;
}
