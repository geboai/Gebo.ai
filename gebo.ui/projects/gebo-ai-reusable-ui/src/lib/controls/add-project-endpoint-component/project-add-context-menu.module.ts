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
import { ReactiveFormsModule } from "@angular/forms";
import { ProjectAddContextMenuComponent } from "./project-add-context-menu.component";
import { ButtonModule } from "primeng/button";
import { ContextMenuModule } from 'primeng/contextmenu';

import { GeboAIChooseDataSourceTypeComponent } from "./choose-data-source-type.component";
import { PanelModule } from "primeng/panel";
import { RadioButtonModule } from 'primeng/radiobutton';
import { GeboUIArchitectureModule } from "../../architecture/gebo-ui-architecture.module";
import { GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
/**
 * @fileoverview This module provides components for project data source management in a Gebo.ai application.
 * AI generated comments
 */

/**
 * @NgModule ProjectAddContextMenuModule
 * 
 * A module that bundles components related to project addition and data source selection.
 * It provides context menu functionality for adding projects and a component for selecting
 * data source types.
 * 
 * This module imports common Angular modules like CommonModule and ReactiveFormsModule
 * along with PrimeNG UI components (Button, ContextMenu, Panel, RadioButton) and
 * Gebo's reusable UI architecture module.
 * 
 * The main components declared and exported are:
 * - ProjectAddContextMenuComponent: For displaying contextual options when adding projects
 * - GeboAIChooseDataSourceTypeComponent: For selecting between different data source types
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, ButtonModule, ContextMenuModule, GeboUIArchitectureModule, PanelModule, RadioButtonModule],
    declarations: [ProjectAddContextMenuComponent, GeboAIChooseDataSourceTypeComponent],
    exports: [ProjectAddContextMenuComponent, GeboAIChooseDataSourceTypeComponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "ProjectAddContextMenuModule", multi: true }]
})
export class ProjectAddContextMenuModule { }