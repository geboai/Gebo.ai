/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboUIActionRoutingService } from "./gebo-ui-action-routing.service";
import { GeboUIModalOpenerComponent } from "./gebo-ui-modal-opener.component";
import { DialogModule } from 'primeng/dialog';
import { GeboUIModalOpenerWrapperComponent } from "./gebo-ui-modal-wrapper.component";
import { GeboFormGroupsService } from "./gebo-form-groups.service";
import { ButtonModule } from "primeng/button";
import { StepsModule } from 'primeng/steps';
import { GeboUIEntityFormsLauncherComponent } from "./gebo-ui-entity-forms-launcher.component";
import { GeboUIModalComponent } from "./gebo-ui-modal.component";

/**
 * @NgModule GeboUIArchitectureModule
 * 
 * AI generated comments
 * 
 * This module provides a comprehensive architecture for UI components focused on modals, forms, and entity management.
 * It bundles together components for opening and managing modals, launching entity forms, and handling form configurations.
 * 
 * The module imports several Angular and PrimeNG modules for form handling, dialog management, and UI components:
 * - CommonModule: Angular's common directives and pipes
 * - ReactiveFormsModule & FormsModule: Angular's form handling modules
 * - DialogModule: PrimeNG's dialog component for modal windows
 * - ButtonModule: PrimeNG's button components
 * - StepsModule: PrimeNG's multi-step component
 * 
 * It provides services for:
 * - Action routing (GeboUIActionRoutingService)
 * - Form group management (GeboFormGroupsService)
 * 
 * And exports components for:
 * - Modal opening and management
 * - Entity form launching
 * - Modal display
 */
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,DialogModule,ButtonModule,StepsModule],
    providers:[GeboUIActionRoutingService,GeboFormGroupsService],
    declarations:[GeboUIModalOpenerComponent,GeboUIModalOpenerWrapperComponent,GeboUIEntityFormsLauncherComponent,GeboUIModalComponent],
    exports:[GeboUIModalOpenerComponent,GeboUIEntityFormsLauncherComponent,GeboUIModalComponent]

})
export class GeboUIArchitectureModule {}