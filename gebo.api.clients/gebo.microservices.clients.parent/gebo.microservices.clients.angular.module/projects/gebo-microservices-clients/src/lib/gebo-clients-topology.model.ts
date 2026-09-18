/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/** Which Gebo.ai installation shape answered the clients-topology call. */
export type GeboArchitectureType = 'MONOLITHIC' | 'MICROSERVICES';

/** One entry of the clients topology: what to append to the common base url for one service. */
export interface GeboServiceWebContextInfo {
  /** The service this entry addresses, e.g. `brain_gebo_ai`, or `default`. */
  serviceId: string;
  /** The web context to append, e.g. `/brain`; `''` means the service answers at the base url itself. */
  relativeContextUrl: string;
}

/**
 * The answer of `GET <baseUrl>/public/ClientsTopologyProviderController` — how a
 * client completes the one base url it was configured with, per service.
 *
 * Declared here rather than imported from a generated client on purpose: this
 * library must be able to bootstrap against a monolith and a gateway alike,
 * before it knows which one it is talking to, so it cannot depend on either
 * one's stubs for the call that tells it.
 */
export interface GeboClientsTopologyInfo {
  architectureType: GeboArchitectureType;
  services: GeboServiceWebContextInfo[];
}

/**
 * The catch-all service id. It is the only entry a monolithic installation
 * publishes, and it carries the empty relative url — so every client of a
 * monolith resolves to the base url itself.
 */
export const GEBO_DEFAULT_SERVICE_ID = 'default';

/**
 * The service ids the Gebo.ai stubs address, in the canonical dot-free form the
 * clients topology keys its entries by (the deployables' `spring.application.name`
 * with `.` replaced by `_`).
 */
export const GeboMicroservices = {
  GATEWAY: 'gateway_gebo_ai',
  EUREKA: 'eureka_gebo_ai',
  HEIMDALL: 'heimdall_gebo_ai',
  BRAIN: 'brain_gebo_ai',
  VECTORIZATOR: 'vectorizator_gebo_ai',
  GRAPHICATOR: 'graphicator_gebo_ai',
  CHUNKER: 'chunker_gebo_ai',
  GIT: 'git_gebo_ai',
  FILESYSTEM: 'filesystem_gebo_ai',
  UPLOADS: 'uploads_gebo_ai',
  USERSPACE: 'userspace_gebo_ai',
  SHAREPOINT: 'sharepoint_gebo_ai',
  CONFLUENCE: 'confluence_gebo_ai',
  JIRA: 'jira_gebo_ai',
  AWS_S3: 'aws_s3_gebo_ai',
  GOOGLEDRIVE: 'googledrive_gebo_ai',
  MCPCLIENT: 'mcpclient_gebo_ai',
  WEBDAV: 'webdav_gebo_ai',
  INTEGRATION: 'integration_gebo_ai',
  FULLTEXTOR: 'fulltextor_gebo_ai',
  TYR: 'tyr_gebo_ai',
} as const;

/** Any of the {@link GeboMicroservices} ids. */
export type GeboMicroserviceId = (typeof GeboMicroservices)[keyof typeof GeboMicroservices];

/** How {@link MicroservicesClientsModule.forRoot} is configured. */
export interface GeboMicroservicesClientsOptions {
  /**
   * The ONE base url of the installation — the gateway's public url in a
   * microservices deployment, the server's url in a monolithic one. A trailing
   * slash is stripped. Pass `''` to address the origin the app itself is served
   * from, which is the usual case when Gebo.ai serves the SPA.
   */
  baseUrl: string;

  /**
   * Where the topology is published, appended to {@link baseUrl}. Defaults to
   * `/public/ClientsTopologyProviderController`; override only if the
   * installation is fronted by a proxy that remaps it.
   */
  topologyPath?: string;
}
