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
import { RouterModule, Routes } from "@angular/router";
import { TableModule } from "primeng/table";
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";
import { CalendarModule } from "primeng/calendar";
import { InputTextModule } from "primeng/inputtext";
import { SelectModule } from "primeng/select";
import { TabViewModule } from "primeng/tabview";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { GeboAIFieldTranslationContainerModule } from "../../controls/field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboAIUserIntegrationsComponent } from "./user-integrations.component";

const routes: Routes = [{
    path: "ui/user-integrations",
    component: GeboAIUserIntegrationsComponent
}];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        TableModule,
        ButtonModule,
        DialogModule,
        CalendarModule,
        InputTextModule,
        SelectModule,
        TabViewModule,
        PanelModule,
        BlockUIModule,
        RouterModule.forRoot(routes),
        GeboAIFieldTranslationContainerModule,
        GeboAINotificationsModule
    ],
    declarations: [GeboAIUserIntegrationsComponent],
    exports: [GeboAIUserIntegrationsComponent]
})
export class GeboAIUserIntegrationsModule { }
