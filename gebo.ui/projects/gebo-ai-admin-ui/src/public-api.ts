/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/*
 * Public API Surface of gebo-ai-admin-ui
 * AI generated comments
 * This is the main barrel file for the gebo-ai-admin-ui library.
 * It exports all the public-facing components, modules, and services
 * that consumers of this library will need to access.
 */

// Export the admin UI routing module for handling navigation within the admin interface
export * from './lib/admin-ui/gebo-ai-admin-routing.module';

// Export the main admin module which contains all components and services for the admin UI
export * from './lib/admin-ui/gebo-ai-admin.module';

// Export the setup wizard service which provides functionality for guiding users through initial configuration
export * from "./lib/setup-wizard/gebo-setup-wizards.service";

// Export the setup wizards module containing all components related to the setup process
export * from "./lib/setup-wizard/setup-wizards.module";

// Export the setup wizards routing module for navigation within the setup wizard flow
export * from "./lib/setup-wizard/setup-wizards-routing.module";