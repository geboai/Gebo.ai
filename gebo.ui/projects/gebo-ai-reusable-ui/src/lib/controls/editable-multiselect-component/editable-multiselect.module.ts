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
import { EditableMultiselectComponent } from "./editable-multiselect.component";

import { MultiSelectModule } from '@openng/optimus-ui/multiselect';
import { BlockUIModule } from '@openng/optimus-ui/blockui';
import { DialogModule } from '@openng/optimus-ui/dialog';
/**
 * Angular module for the editable multiselect component.
 * AI generated comments
 * 
 * This module packages the EditableMultiselectComponent along with its required dependencies.
 * It imports various Angular and Optimus UI modules needed for the component's functionality:
 * - CommonModule for common Angular directives
 * - ReactiveFormsModule/FormsModule for form handling
 * - MultiSelectModule from Optimus UI for the base multiselect functionality
 * - BlockUIModule for blocking UI interactions during operations
 * - DialogModule for dialog/modal functionality
 * 
 * The module declares the EditableMultiselectComponent and makes it available for use
 * in other modules by exporting it.
 */
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule,MultiSelectModule,BlockUIModule,DialogModule],
    declarations:[EditableMultiselectComponent],
    exports:[EditableMultiselectComponent]
})
export class EditableMultiselectModule {}