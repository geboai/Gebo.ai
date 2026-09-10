/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, InjectionToken } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import {
  GEBO_DEFAULT_SERVICE_ID,
  GeboClientsTopologyInfo,
  GeboMicroservicesClientsOptions,
} from './gebo-clients-topology.model';

/** The options {@link MicroservicesClientsModule.forRoot} was given. */
export const GEBO_MICROSERVICES_CLIENTS_OPTIONS = new InjectionToken<GeboMicroservicesClientsOptions>(
  'GEBO_MICROSERVICES_CLIENTS_OPTIONS'
);

/** Where the topology is published, in every installation shape alike. */
export const GEBO_CLIENTS_TOPOLOGY_PATH = '/public/ClientsTopologyProviderController';

/**
 * Reads the clients topology of the installation once and turns it into the
 * `BASE_PATH` each generated `ApiModule` needs.
 *
 * `load()` is run by the app initializer {@link MicroservicesClientsModule.forRoot}
 * registers, so by the time Angular constructs the first generated service —
 * every one of which reads its base path in its constructor and never again —
 * {@link basePathFor} already has the answer.
 */
@Injectable()
export class GeboClientsTopologyService {
  private readonly options: GeboMicroservicesClientsOptions;
  private readonly commonBaseUrl: string;
  private readonly topologyUrl: string;

  private topology: GeboClientsTopologyInfo | null = null;
  private warnedAboutEarlyAccess = false;

  constructor(
    private readonly http: HttpClient,
    @Inject(GEBO_MICROSERVICES_CLIENTS_OPTIONS) options: GeboMicroservicesClientsOptions
  ) {
    this.options = options;
    this.commonBaseUrl = GeboClientsTopologyService.stripTrailingSlash(options.baseUrl ?? '');
    this.topologyUrl = this.commonBaseUrl + (options.topologyPath ?? GEBO_CLIENTS_TOPOLOGY_PATH);
  }

  /** The common base url every client of this installation is built from, with no trailing slash. */
  get baseUrl(): string {
    return this.commonBaseUrl;
  }

  /** The url the topology is read from. */
  get url(): string {
    return this.topologyUrl;
  }

  /** The topology this installation published, or `null` while {@link load} has not completed. */
  get info(): GeboClientsTopologyInfo | null {
    return this.topology;
  }

  /**
   * Reads the topology. Called by the app initializer; calling it again re-reads
   * it, but note that services already constructed keep the base path they were
   * given — a re-read only affects services created afterwards.
   *
   * A failed call is NOT an error: it degrades to the monolithic shape (every
   * client points at the base url) and warns. The endpoint was added after the
   * stubs existed, so a Gebo.ai server predating it answers 404 — and those
   * servers were all monoliths, which is exactly what the fallback assumes. A
   * microservices gateway always publishes it.
   */
  load(): Observable<GeboClientsTopologyInfo> {
    return this.http.get<GeboClientsTopologyInfo>(this.topologyUrl).pipe(
      map((fetched) => {
        if (!fetched || !fetched.architectureType || !fetched.services?.length) {
          console.warn(
            `[gebo] clients topology at ${this.topologyUrl} answered an empty payload; falling back to the` +
              ` monolithic shape (every client points at ${this.commonBaseUrl || 'the app origin'}).`
          );
          return GeboClientsTopologyService.monolithicFallback();
        }
        return fetched;
      }),
      catchError((error: unknown) => {
        console.warn(
          `[gebo] clients topology could not be read from ${this.topologyUrl}; falling back to the monolithic` +
            ` shape (every client points at ${this.commonBaseUrl || 'the app origin'}). Expected against a server` +
            ` predating the endpoint, which is always a monolith — and wrong against a microservices gateway,` +
            ` where the per-service web contexts would then be missing.`,
          error
        );
        return of(GeboClientsTopologyService.monolithicFallback());
      }),
      map((resolved) => {
        this.topology = resolved;
        return resolved;
      })
    );
  }

  /**
   * The complete base path of one service — the value handed to that client
   * library's own `BASE_PATH` token.
   *
   * @param serviceId a service id in either the dotted or the underscore form
   * @throws when a microservices installation publishes no entry for the
   *         service: it has no address behind this base url, and a guessed one
   *         would only turn into a puzzling 404 later. The throw is lazy — it
   *         happens the first time that particular client is injected, so an app
   *         that never touches the service never sees it.
   */
  basePathFor(serviceId: string): string {
    return this.commonBaseUrl + this.relativeContextUrlFor(serviceId);
  }

  /**
   * The relative web context url published for a service, with the
   * {@link GEBO_DEFAULT_SERVICE_ID} fallback applied.
   *
   * @param serviceId a service id in either form
   */
  relativeContextUrlFor(serviceId: string): string {
    const canonical = GeboClientsTopologyService.normalizeServiceId(serviceId);
    if (!canonical) {
      throw new Error('[gebo] serviceId must not be empty');
    }
    if (!this.topology) {
      // Only reachable when a generated service is injected BEFORE the app
      // initializer has run — e.g. from another initializer registered earlier.
      // Falling back keeps the app alive; the warning names the fix.
      if (!this.warnedAboutEarlyAccess) {
        this.warnedAboutEarlyAccess = true;
        console.warn(
          `[gebo] a Gebo.ai client for '${canonical}' was injected before the clients topology finished loading,` +
            ` so it was given the bare base url ${this.commonBaseUrl || 'the app origin'}. Inject it from a` +
            ` component or a service resolved after bootstrap, not from an APP_INITIALIZER that runs before` +
            ` MicroservicesClientsModule's own.`
        );
      }
      return '';
    }

    const own = this.findRelativeContextUrl(canonical);
    if (own !== null) {
      return GeboClientsTopologyService.normalizeRelativeContextUrl(own);
    }
    const fallback = this.findRelativeContextUrl(GEBO_DEFAULT_SERVICE_ID);
    if (fallback !== null) {
      return GeboClientsTopologyService.normalizeRelativeContextUrl(fallback);
    }
    throw new Error(
      `[gebo] the installation at ${this.commonBaseUrl || 'the app origin'} publishes no clients-topology entry` +
        ` for service '${canonical}' (architecture ${this.topology.architectureType}), and no` +
        ` '${GEBO_DEFAULT_SERVICE_ID}' fallback entry. That service is not reachable through this base url.` +
        ` Published: ${this.topology.services.map((service) => service.serviceId).join(', ')}`
    );
  }

  private findRelativeContextUrl(canonicalServiceId: string): string | null {
    const match = this.topology?.services.find(
      (service) => GeboClientsTopologyService.normalizeServiceId(service.serviceId) === canonicalServiceId
    );
    return match ? match.relativeContextUrl : null;
  }

  /** Mirrors the backend's `GeboMicroservice.normalizeName`: `.` becomes `_`. */
  private static normalizeServiceId(serviceId: string | undefined | null): string | null {
    if (!serviceId) {
      return null;
    }
    const trimmed = serviceId.trim();
    return trimmed.length === 0 ? null : trimmed.replace(/\./g, '_');
  }

  /**
   * Brings a published value to the form the module concatenates blindly:
   * either `''` or a leading-slash path with no trailing slash, so
   * `baseUrl + relative` never grows a double or a dangling slash.
   */
  private static normalizeRelativeContextUrl(relativeContextUrl: string | undefined | null): string {
    const trimmed = (relativeContextUrl ?? '').trim();
    if (trimmed.length === 0 || trimmed === '/') {
      return '';
    }
    const withLeadingSlash = trimmed.startsWith('/') ? trimmed : '/' + trimmed;
    return GeboClientsTopologyService.stripTrailingSlash(withLeadingSlash);
  }

  private static stripTrailingSlash(url: string): string {
    let result = url.trim();
    while (result.length > 1 && result.endsWith('/')) {
      result = result.substring(0, result.length - 1);
    }
    return result === '/' ? '' : result;
  }

  private static monolithicFallback(): GeboClientsTopologyInfo {
    return {
      architectureType: 'MONOLITHIC',
      services: [{ serviceId: GEBO_DEFAULT_SERVICE_ID, relativeContextUrl: '' }],
    };
  }
}
