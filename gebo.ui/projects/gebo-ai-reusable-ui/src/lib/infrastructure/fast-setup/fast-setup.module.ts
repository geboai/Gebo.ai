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
 * This module provides a fast setup functionality for the application.
 * It imports various Angular and PrimeNG modules needed for UI components and form handling.
 * The module registers FastSetupComponent route and declares both FastSetupComponent 
 * and GeboAILicenceComponent while only exporting FastSetupComponent for use in other modules.
 */
import { CommonModule } from "@angular/common";
import { NgModule, OnInit } from "@angular/core";
import { PasswordModule } from "primeng/password";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { MessagesModule } from "primeng/messages";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { FastSetupComponent } from "./fast-setup.component";
import { DropdownModule } from "primeng/dropdown";
import { CheckboxModule } from "primeng/checkbox";
import { RadioButtonModule } from 'primeng/radiobutton';
import { GeboAILicenceComponent } from "./licence.component";
import { ScrollPanelModule } from "primeng/scrollpanel";
import { SelectButtonModule } from 'primeng/selectbutton';
import { FieldsetModule } from "primeng/fieldset";
import { EditableListboxModule } from "../../controls/editable-listbox-component/editable-listbox.module";

// Routes configuration for the fast setup feature
const routes: Routes = [{ path: 'ui/setup', component: FastSetupComponent }];

/**
 * This Angular module encapsulates all the components and dependencies needed for the fast setup functionality.
 * It bundles UI components from PrimeNG library and custom components such as FastSetupComponent
 * and GeboAILicenceComponent to provide a complete setup interface.
 * The module handles routing to the setup page and provides all necessary form controls and UI elements.
 */
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, PanelModule, BlockUIModule, MessagesModule, InputTextModule, ButtonModule, PasswordModule, RouterModule.forRoot(routes), DropdownModule, EditableListboxModule, CheckboxModule, RadioButtonModule, ScrollPanelModule, SelectButtonModule,FieldsetModule],
    declarations: [FastSetupComponent, GeboAILicenceComponent],
    exports: [FastSetupComponent]
})

export class FastSetupModule implements OnInit {
    /**
     * Lifecycle hook that is called after data-bound properties of a directive are initialized.
     * Currently empty but can be used for module-level initialization if needed.
     */
    ngOnInit(): void {

    }
}