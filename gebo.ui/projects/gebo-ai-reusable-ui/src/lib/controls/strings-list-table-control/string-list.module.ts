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
import { GeboAIStringListComponent } from "./string-list.component";
import { ButtonModule } from "primeng/button";
import { ChipModule } from "primeng/chip";
import { DialogModule } from "primeng/dialog";
import { PanelModule } from "primeng/panel";
import { InputTextModule } from "primeng/inputtext";

@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,ButtonModule,ChipModule,ChipModule,DialogModule,PanelModule,InputTextModule],
    declarations:[GeboAIStringListComponent],
    exports:[GeboAIStringListComponent],
})
export class GeboAIStringListModule{}