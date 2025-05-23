/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This module provides the setup wizard functionality for the application, allowing users
 * to navigate through a guided setup process. It bundles all necessary components, services,
 * and UI elements required for the setup wizard experience.
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SetupWizardPanelComponent } from "./setup-wizard-panel.component";
import { RouterModule, Routes } from "@angular/router";
import { AlwaysTrueStatusService, WizardSectionWithNoUI } from "./setup-wizard-step";
import { PanelModule } from "primeng/panel";
import {BlockUIModule} from "primeng/blockui"
import { ButtonModule } from "primeng/button";
import { SetupWizardService } from "./setup-wizard.service";
import { MessagesModule } from "primeng/messages";
import { BreadcrumbModule } from 'primeng/breadcrumb';

/**
 * Route configuration for the setup wizard, mapping the 'setup-wizard' path to the
 * SetupWizardPanelComponent.
 */
const routes:Routes=[{path:"setup-wizard",component:SetupWizardPanelComponent}];

/**
 * The SetupWizardPanelModule encapsulates all required elements for the setup wizard
 * functionality in the application.
 * 
 * Imports:
 * - CommonModule: For common Angular directives
 * - ReactiveFormsModule/FormsModule: For form handling
 * - RouterModule: For navigation within the wizard
 * - PrimNG modules: For UI components (panels, buttons, messages, etc.)
 * 
 * Components:
 * - SetupWizardPanelComponent: Main panel for the wizard
 * - WizardSectionWithNoUI: Support component for wizard sections without UI
 * 
 * Services:
 * - AlwaysTrueStatusService: Service that always returns true for status checks
 * - SetupWizardService: Main service managing the wizard workflow
 */
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,RouterModule.forChild(routes),PanelModule,BlockUIModule,ButtonModule,MessagesModule,BreadcrumbModule],
    declarations:[SetupWizardPanelComponent,WizardSectionWithNoUI],
    exports:[SetupWizardPanelComponent,WizardSectionWithNoUI],
    providers:[AlwaysTrueStatusService,SetupWizardService]
})
export class SetupWizardPanelModule {}