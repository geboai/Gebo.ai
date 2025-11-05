/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { NgModule } from "@angular/core";
import { VFilesystemSelectorComponent } from "./vfilesystem-selector.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TreeSelectModule } from "primeng/treeselect";
import { TreeModule } from "primeng/tree";
import { DialogModule } from "primeng/dialog";
import { ButtonModule } from "primeng/button";
import { ChipModule } from 'primeng/chip';
import { BlockUIModule } from "primeng/blockui";
import { PanelModule } from "primeng/panel";
import { MessagesModule } from "primeng/messages";
import { RadioButtonModule } from "primeng/radiobutton";
import { CheckboxModule } from "primeng/checkbox";
import { FieldsetModule } from "primeng/fieldset";
import { GeboAIFieldTransationContainerModule } from "@Gebo.ai/reusable-ui";
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, TreeSelectModule, TreeModule, DialogModule, ChipModule, ButtonModule, BlockUIModule, PanelModule, MessagesModule, RadioButtonModule, CheckboxModule, FieldsetModule, GeboAIFieldTransationContainerModule],
    declarations:[VFilesystemSelectorComponent],
    exports:[VFilesystemSelectorComponent]
})
export class VFilesystemSelectorModule {}