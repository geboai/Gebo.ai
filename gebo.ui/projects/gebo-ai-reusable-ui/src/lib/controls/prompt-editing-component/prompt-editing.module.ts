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
import { TextareaModule } from '@openng/optimus-ui/textarea';
import { PanelModule } from "@openng/optimus-ui/panel";
import { DialogModule } from "@openng/optimus-ui/dialog";
import { ButtonModule } from "@openng/optimus-ui/button";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { SelectModule } from "@openng/optimus-ui/select";
import { CheckboxModule } from "@openng/optimus-ui/checkbox";
import { TableModule } from "@openng/optimus-ui/table";
import { MessageModule } from "@openng/optimus-ui/message";
import { TabsModule } from "@openng/optimus-ui/tabs";


import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { EditableListboxModule } from "../editable-listbox-component/editable-listbox.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, TextareaModule, PanelModule, DialogModule, ButtonModule, BlockUIModule, InputTextModule, SelectModule, CheckboxModule, TableModule, MessageModule, TabsModule, EditableListboxModule, MonacoEditorModule, GeboAIFieldTranslationContainerModule,GeboAINotificationsModule],
    declarations: [GeboAIPromptEditingComponent, GeboAIPromptWizardComponent],
    exports: [GeboAIPromptEditingComponent],
    providers: []
})
export class PromptEditingModule { }