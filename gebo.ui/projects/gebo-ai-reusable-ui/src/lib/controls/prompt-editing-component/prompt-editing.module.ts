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
import { GeboAIPromptEditingComponent } from "./prompt-editing.component";
import { GeboAIPromptWizardComponent } from "./prompt-wizard.component";
import { TextareaModule } from 'primeng/textarea';
import { PanelModule } from "primeng/panel";
import { DialogModule } from "primeng/dialog";
import { ButtonModule } from "primeng/button";
import { BlockUIModule } from "primeng/blockui";
import { MessagesModule } from "primeng/messages";

import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, TextareaModule, PanelModule, DialogModule, ButtonModule, BlockUIModule, MessagesModule, EditableListboxModule, MonacoEditorModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIPromptEditingComponent, GeboAIPromptWizardComponent],
    exports: [GeboAIPromptEditingComponent],
    providers: []
})
export class PromptEditingModule { }