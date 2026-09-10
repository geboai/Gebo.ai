/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/*
 * Public API Surface of gebo-microservices-clients
 *
 * Deliberately narrow: this library WIRES the generated @Gebo.ai/* clients, it
 * does not re-export them. Every one of the 21 ships its own ApiModule,
 * Configuration and BASE_PATH under those same names, so re-exporting would
 * collide 21 ways over; keep importing services from their own package.
 */

export * from './lib/gebo-clients-topology.model';
export * from './lib/gebo-clients-topology.service';
export * from './lib/microservices-clients.module';
