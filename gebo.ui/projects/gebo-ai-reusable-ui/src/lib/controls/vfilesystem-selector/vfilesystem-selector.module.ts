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
import { TreeSelectModule } from "@openng/optimus-ui/treeselect";
import { TreeModule } from "@openng/optimus-ui/tree";
import { DialogModule } from "@openng/optimus-ui/dialog";
import { ButtonModule } from "@openng/optimus-ui/button";
import { ChipModule } from '@openng/optimus-ui/chip';
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { PanelModule } from "@openng/optimus-ui/panel";

import { RadioButtonModule } from "@openng/optimus-ui/radiobutton";
import { CheckboxModule } from "@openng/optimus-ui/checkbox";
import { FieldsetModule } from "@openng/optimus-ui/fieldset";
import { TooltipModule } from "@openng/optimus-ui/tooltip";
import {  GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, TreeSelectModule, TreeModule, DialogModule, ChipModule, ButtonModule, BlockUIModule, PanelModule, RadioButtonModule, CheckboxModule, FieldsetModule, TooltipModule, GeboAIFieldTranslationContainerModule,GeboAINotificationsModule],
    declarations:[VFilesystemSelectorComponent],
    exports:[VFilesystemSelectorComponent]
})
export class VFilesystemSelectorModule {}