/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import {
  InjectionToken,
  ModuleWithProviders,
  NgModule,
  Optional,
  Provider,
  SkipSelf,
  inject,
  provideAppInitializer,
} from '@angular/core';

import { ApiModule as GatewayApiModule, BASE_PATH as GATEWAY_BASE_PATH } from '@Gebo.ai/gateway';
import { ApiModule as EurekaApiModule, BASE_PATH as EUREKA_BASE_PATH } from '@Gebo.ai/eureka';
import { ApiModule as HeimdallApiModule, BASE_PATH as HEIMDALL_BASE_PATH } from '@Gebo.ai/heimdall';
import { ApiModule as BrainApiModule, BASE_PATH as BRAIN_BASE_PATH } from '@Gebo.ai/brain';
import { ApiModule as VectorizatorApiModule, BASE_PATH as VECTORIZATOR_BASE_PATH } from '@Gebo.ai/vectorizator';
import { ApiModule as GraphicatorApiModule, BASE_PATH as GRAPHICATOR_BASE_PATH } from '@Gebo.ai/graphicator';
import { ApiModule as ChunkerApiModule, BASE_PATH as CHUNKER_BASE_PATH } from '@Gebo.ai/chunker';
import { ApiModule as GitApiModule, BASE_PATH as GIT_BASE_PATH } from '@Gebo.ai/git';
import { ApiModule as FilesystemApiModule, BASE_PATH as FILESYSTEM_BASE_PATH } from '@Gebo.ai/filesystem';
import { ApiModule as UploadsApiModule, BASE_PATH as UPLOADS_BASE_PATH } from '@Gebo.ai/uploads';
import { ApiModule as UserspaceApiModule, BASE_PATH as USERSPACE_BASE_PATH } from '@Gebo.ai/userspace';
import { ApiModule as SharepointApiModule, BASE_PATH as SHAREPOINT_BASE_PATH } from '@Gebo.ai/sharepoint';
import { ApiModule as ConfluenceApiModule, BASE_PATH as CONFLUENCE_BASE_PATH } from '@Gebo.ai/confluence';
import { ApiModule as JiraApiModule, BASE_PATH as JIRA_BASE_PATH } from '@Gebo.ai/jira';
import { ApiModule as AwsS3ApiModule, BASE_PATH as AWS_S3_BASE_PATH } from '@Gebo.ai/awss3';
import { ApiModule as GoogledriveApiModule, BASE_PATH as GOOGLEDRIVE_BASE_PATH } from '@Gebo.ai/googledrive';
import { ApiModule as McpclientApiModule, BASE_PATH as MCPCLIENT_BASE_PATH } from '@Gebo.ai/mcpclient';
import { ApiModule as WebdavApiModule, BASE_PATH as WEBDAV_BASE_PATH } from '@Gebo.ai/webdav';
import { ApiModule as IntegrationApiModule, BASE_PATH as INTEGRATION_BASE_PATH } from '@Gebo.ai/integration';
import { ApiModule as FulltextorApiModule, BASE_PATH as FULLTEXTOR_BASE_PATH } from '@Gebo.ai/fulltextor';
import { ApiModule as TyrApiModule, BASE_PATH as TYR_BASE_PATH } from '@Gebo.ai/tyr';

import { GeboMicroservices, GeboMicroservicesClientsOptions } from './gebo-clients-topology.model';
import { GEBO_MICROSERVICES_CLIENTS_OPTIONS, GeboClientsTopologyService } from './gebo-clients-topology.service';

/**
 * Every generated client, paired with the service id the clients topology
 * publishes its web context under.
 *
 * Each library ships its OWN `BASE_PATH` injection token — 21 distinct
 * `InjectionToken` instances that merely share a description string — which is
 * exactly what makes per-service base paths possible in a single injector: they
 * never collide.
 */
const GEBO_CLIENT_BASE_PATH_TOKENS: ReadonlyArray<readonly [InjectionToken<string>, string]> = [
  [GATEWAY_BASE_PATH, GeboMicroservices.GATEWAY],
  [EUREKA_BASE_PATH, GeboMicroservices.EUREKA],
  [HEIMDALL_BASE_PATH, GeboMicroservices.HEIMDALL],
  [BRAIN_BASE_PATH, GeboMicroservices.BRAIN],
  [VECTORIZATOR_BASE_PATH, GeboMicroservices.VECTORIZATOR],
  [GRAPHICATOR_BASE_PATH, GeboMicroservices.GRAPHICATOR],
  [CHUNKER_BASE_PATH, GeboMicroservices.CHUNKER],
  [GIT_BASE_PATH, GeboMicroservices.GIT],
  [FILESYSTEM_BASE_PATH, GeboMicroservices.FILESYSTEM],
  [UPLOADS_BASE_PATH, GeboMicroservices.UPLOADS],
  [USERSPACE_BASE_PATH, GeboMicroservices.USERSPACE],
  [SHAREPOINT_BASE_PATH, GeboMicroservices.SHAREPOINT],
  [CONFLUENCE_BASE_PATH, GeboMicroservices.CONFLUENCE],
  [JIRA_BASE_PATH, GeboMicroservices.JIRA],
  [AWS_S3_BASE_PATH, GeboMicroservices.AWS_S3],
  [GOOGLEDRIVE_BASE_PATH, GeboMicroservices.GOOGLEDRIVE],
  [MCPCLIENT_BASE_PATH, GeboMicroservices.MCPCLIENT],
  [WEBDAV_BASE_PATH, GeboMicroservices.WEBDAV],
  [INTEGRATION_BASE_PATH, GeboMicroservices.INTEGRATION],
  [FULLTEXTOR_BASE_PATH, GeboMicroservices.FULLTEXTOR],
  [TYR_BASE_PATH, GeboMicroservices.TYR],
];

/**
 * The one import that connects an Angular app to a Gebo.ai installation,
 * whatever shape it has.
 *
 * ```ts
 * @NgModule({
 *   imports: [
 *     BrowserModule,
 *     HttpClientModule,
 *     MicroservicesClientsModule.forRoot({ baseUrl: environment.geboBaseUrl }),
 *   ],
 * })
 * export class AppModule {}
 * ```
 *
 * From there every generated service is injectable and already points at the
 * right address:
 *
 * ```ts
 * constructor(private chatModels: ChatModelsControllerService) {}   // @Gebo.ai/brain
 * ```
 *
 * ## What it does
 *
 * Each generated client hardcodes the address its spec was scraped from
 * (`http://localhost:13001/brain` and friends) and exposes a `BASE_PATH` token
 * to override it. Which suffix a service actually answers on depends on the
 * deployment: behind a gateway every service owns a web context (`/brain`,
 * `/heimdall`), on a monolith they all answer at the root. So this module asks
 * the installation itself — `/public/ClientsTopologyProviderController`, which
 * every shape publishes at the same relative url — and provides all 21
 * `BASE_PATH` tokens from that answer. The app knows ONE url and nothing else
 * changes between a monolithic and a microservices target.
 *
 * ## Requirements and caveats
 *
 * - `HttpClient` must be provided (`HttpClientModule` / `provideHttpClient()`),
 *   as every generated `ApiModule` already demands.
 * - Import it in the root module ONCE. The generated `ApiModule`s carry their own
 *   "already loaded" guard, and this one is a `forRoot`.
 * - The topology is read by an app initializer, so it is in place before Angular
 *   builds the first client. A client injected from an initializer registered
 *   BEFORE this module's is the one case that can miss it — it falls back to the
 *   bare base url and warns.
 * - The 21 `@Gebo.ai/*` client packages are peer dependencies: the consuming app
 *   installs them, this library only wires them.
 */
@NgModule({
  imports: [
    // Importing them here is what makes the app's own module a one-liner: every
    // generated service becomes injectable without the app listing 21 ApiModules
    // whose only purpose is to be given a base path.
    GatewayApiModule,
    EurekaApiModule,
    HeimdallApiModule,
    BrainApiModule,
    VectorizatorApiModule,
    GraphicatorApiModule,
    ChunkerApiModule,
    GitApiModule,
    FilesystemApiModule,
    UploadsApiModule,
    UserspaceApiModule,
    SharepointApiModule,
    ConfluenceApiModule,
    JiraApiModule,
    AwsS3ApiModule,
    GoogledriveApiModule,
    McpclientApiModule,
    WebdavApiModule,
    IntegrationApiModule,
    FulltextorApiModule,
    TyrApiModule,
  ],
})
export class MicroservicesClientsModule {
  /**
   * @param options the installation's base url, or the full options object. A
   *        bare string is the same as `{ baseUrl: options }`; `''` addresses the
   *        origin the app itself is served from, which is the usual case when
   *        Gebo.ai serves the SPA.
   */
  static forRoot(
    options: GeboMicroservicesClientsOptions | string
  ): ModuleWithProviders<MicroservicesClientsModule> {
    const resolved: GeboMicroservicesClientsOptions =
      typeof options === 'string' ? { baseUrl: options } : options;

    return {
      ngModule: MicroservicesClientsModule,
      providers: [
        { provide: GEBO_MICROSERVICES_CLIENTS_OPTIONS, useValue: resolved },
        GeboClientsTopologyService,
        // Runs before the app bootstraps, so the base-path factories below —
        // which are evaluated when a generated service is first injected —
        // always find the topology already resolved.
        provideAppInitializer(() => inject(GeboClientsTopologyService).load()),
        ...GEBO_CLIENT_BASE_PATH_TOKENS.map(([token, serviceId]) => basePathProvider(token, serviceId)),
      ],
    };
  }

  constructor(@Optional() @SkipSelf() parentModule?: MicroservicesClientsModule) {
    if (parentModule) {
      throw new Error(
        'MicroservicesClientsModule is already loaded. Import MicroservicesClientsModule.forRoot() in your' +
          ' AppModule only.'
      );
    }
  }
}

/**
 * One client library's `BASE_PATH`, resolved from the topology.
 *
 * A factory rather than a value: it is evaluated the first time that particular
 * client is injected, which is both after the app initializer has run and — for
 * a service the installation does not publish — the point at which throwing is
 * useful rather than fatal to the whole app.
 */
function basePathProvider(token: InjectionToken<string>, serviceId: string): Provider {
  return {
    provide: token,
    useFactory: (topology: GeboClientsTopologyService) => topology.basePathFor(serviceId),
    deps: [GeboClientsTopologyService],
  };
}
