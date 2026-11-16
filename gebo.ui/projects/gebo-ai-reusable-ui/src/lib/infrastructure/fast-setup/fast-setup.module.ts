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
 * It imports various Angular and Optimus UI modules needed for UI components and form handling.
 * The module registers FastSetupComponent route and declares both FastSetupComponent 
 * and GeboAILicenceComponent while only exporting FastSetupComponent for use in other modules.
 */
import { CommonModule } from "@angular/common";
import { NgModule, OnInit } from "@angular/core";
import { PasswordModule } from "@openng/optimus-ui/password";
import { PanelModule } from "@openng/optimus-ui/panel";
import { BlockUIModule } from "@openng/optimus-ui/blockui";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { ButtonModule } from "@openng/optimus-ui/button";
import { FastSetupComponent } from "./fast-setup.component";
import { SelectModule } from "@openng/optimus-ui/select";
import { CheckboxModule } from "@openng/optimus-ui/checkbox";
import { RadioButtonModule } from '@openng/optimus-ui/radiobutton';
import { GeboAILicenceComponent } from "./licence.component";
import { ScrollPanelModule } from "@openng/optimus-ui/scrollpanel";
import { SelectButtonModule } from '@openng/optimus-ui/selectbutton';
import { FieldsetModule } from "@openng/optimus-ui/fieldset";
import { EditableListboxModule } from "../../controls/editable-listbox-component/editable-listbox.module";
import { GeboAIFieldTranslationContainerModule } from "../../controls/field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";

// Routes configuration for the fast setup feature
const routes: Routes = [{ path: 'ui/setup', component: FastSetupComponent }];

/**
 * This Angular module encapsulates all the components and dependencies needed for the fast setup functionality.
 * It bundles UI components from Optimus UI library and custom components such as FastSetupComponent
 * and GeboAILicenceComponent to provide a complete setup interface.
 * The module handles routing to the setup page and provides all necessary form controls and UI elements.
 */
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, PanelModule, BlockUIModule,  InputTextModule, ButtonModule, PasswordModule, RouterModule.forRoot(routes), SelectModule, EditableListboxModule, CheckboxModule, RadioButtonModule, ScrollPanelModule, SelectButtonModule, FieldsetModule, GeboAIFieldTranslationContainerModule,GeboAINotificationsModule],
    declarations: [FastSetupComponent, GeboAILicenceComponent],
    exports: [FastSetupComponent],
    providers: [
    ]
})

export class FastSetupModule implements OnInit {
    /**
     * Lifecycle hook that is called after data-bound properties of a directive are initialized.
     * Currently empty but can be used for module-level initialization if needed.
     */
    ngOnInit(): void {

    }
}