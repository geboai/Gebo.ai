/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * Relation List Module for Gebo.ai
 * 
 * AI generated comments
 * 
 * This module serves as a container for the GeboAiRelationListComponent, providing
 * all necessary imports and exports to make the component available for use in other modules.
 * It includes various Optimus UI UI components (Chip, BlockUI, Button, Dialog, Panel) for UI rendering,
 * forms modules for data binding, and the EditableListboxModule for displaying editable lists.
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { GeboAiRelationListComponent } from "./relation-list.component";
import { ChipModule } from '@openng/optimus-ui/chip';
import { BlockUIModule } from '@openng/optimus-ui/blockui';
import { ButtonModule } from '@openng/optimus-ui/button';
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DialogModule } from '@openng/optimus-ui/dialog';
import { PanelModule } from '@openng/optimus-ui/panel';

/**
 * Module that encapsulates the GeboAiRelationListComponent.
 * 
 * Imports necessary UI components from Optimus UI and Angular modules needed for forms handling.
 * The module makes the GeboAiRelationListComponent available to other modules through its exports.
 */
@NgModule({
    imports: [CommonModule, ChipModule, BlockUIModule, ButtonModule, EditableListboxModule, FormsModule, ReactiveFormsModule,DialogModule,PanelModule],
    declarations: [GeboAiRelationListComponent],
    exports: [GeboAiRelationListComponent]
})
export class GeboAiRelationListModule {

}