/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { CommonModule } from "@angular/common";
import { ModuleWithProviders, NgModule } from "@angular/core";
import { GeboAIModulesService } from "./gebo-ai-modules.service";
import { GeboAIPluggableModulesConfigService, GeboAIPluggableProjectEndpointsService } from "./pluggable-project-endpoint";
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { GeboAIPluggableKnowledgeAdminBaseTreeSearchService } from "./pluggable-knowledge-base-admin-tree-search.service";

/**
 * AI generated comments
 * 
 * This is the main module for Gebo AI functionality. It provides services for 
 * module configuration, project endpoints, and knowledge base administration.
 * The module uses Angular's modular architecture to expose services that can be 
 * consumed by other parts of the application.
 */
@NgModule({
    imports: [CommonModule],
    providers: [GeboAIPluggableModulesConfigService, GeboAIPluggableProjectEndpointsService,GeboAIPluggableKnowledgeAdminBaseTreeSearchService, provideHttpClient(withInterceptorsFromDi())]
})
export class GeboAIModulesModule {
    /**
     * Static method that provides the module with its required providers.
     * This follows the Angular pattern for feature modules that need to be imported
     * only once in the application. The forRoot pattern ensures singleton services.
     * 
     * @returns ModuleWithProviders configuration for the GeboAIModulesModule
     */
    static forRoot(): ModuleWithProviders<GeboAIModulesModule> {
        const modWithProd: ModuleWithProviders<GeboAIModulesModule> = {
            ngModule: GeboAIModulesModule,
            providers: [GeboAIPluggableModulesConfigService, GeboAIPluggableProjectEndpointsService,GeboAIPluggableKnowledgeAdminBaseTreeSearchService, provideHttpClient(withInterceptorsFromDi())]
        }
        return modWithProd;
    }
}