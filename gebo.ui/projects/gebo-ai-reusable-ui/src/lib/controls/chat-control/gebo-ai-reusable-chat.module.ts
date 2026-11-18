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
import { GeboAIReusableChatComponent } from "./gebo-ai-reusable-chat.component";
import { ScrollPanelModule } from '@openng/optimus-ui/scrollpanel';
import { ButtonModule } from "@openng/optimus-ui/button";
import { PanelModule } from "@openng/optimus-ui/panel";
import { DialogModule } from "@openng/optimus-ui/dialog";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { TextareaModule } from '@openng/optimus-ui/textarea';

import { ChangeDescriptionComponent } from "./change-description.component";
import { GeboAIContentViewerModule } from "../content-viewer/gebo-ai-content-viewer.module";
import { DocumentRefComponent } from "./document-ref.component";
import { GeboAISelectedChatDocumentsComponent } from "./selected-chat-documents.component";


import { GeboAIViewTableModule } from "../view-table/view-table.module";
import { CLIPBOARD_OPTIONS, ClipboardButtonComponent, MarkdownModule, provideMarkdown, SANITIZE } from "ngx-markdown";
import { SecurityContext } from '@angular/core';
import { SkeletonModule } from '@openng/optimus-ui/skeleton';
import { OverlayModule } from '@openng/optimus-ui/overlay';
import { GeboChatUserInfoComponent } from "./chat-info.component";
import { GeboAIChatLibrariesPanelComponent } from "./chat-libraries-panel.component";
import { GeboAIChatWrittenFilesComponent } from "./chat-written-files.component";
import { TableModule } from "@openng/optimus-ui/table";
import { FieldsetModule } from "@openng/optimus-ui/fieldset";
import { ScrollTopModule } from '@openng/optimus-ui/scrolltop';
import { InputTextModule } from "@openng/optimus-ui/inputtext";
import { ReactiveRagChatService } from "./reactive-chat.service";
import { ProgressSpinnerModule } from '@openng/optimus-ui/progressspinner';

import { GeboAIAudioRecorderModule } from "../audio-control/audio-control.module";
import { GeboAIChooseDocumentsPanelModule } from "../choose-documents-panel/choose-documents-panel.module";
import { BrowseContentModule } from "../browse-content-component/browse-content.module";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIUploadChatDocumentModule } from "../choose-documents-panel/upload-chat-document.module";

import { GeboAIUploadedDocumentRefComponent } from "./uploaded-document-ref.component";
import { GeboAIChatInputShellComponent } from "./chat-input-shell.component";
import { GeboAIGeneratedDocumentRefComponent } from "./llm-generated-document-ref.component";
import { GeboAIDeepSearchModule } from "../deep-search-control/deep-search.module";
import { TieredMenuModule } from '@openng/optimus-ui/tieredmenu';
import { PopoverModule } from '@openng/optimus-ui/popover';
import { ContextMenuModule } from "@openng/optimus-ui/contextmenu";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboAIExtensiblePipelineRouteDisplayComponent } from "./extensible-pipeline-route-label.component";
import { GeboAIChatStreamEventsDisplayComponent } from "./chat-stream-events-display.component";
import { ProgressBarModule } from "@openng/optimus-ui/progressbar";
import { ChipModule } from "@openng/optimus-ui/chip";
import { RadioButtonModule } from "@openng/optimus-ui/radiobutton";
import { CheckboxModule } from "@openng/optimus-ui/checkbox";
import { GeboAIChatSuspensionAnswerModule } from "./chat-suspension-answer.module";
@NgModule({
  imports: [CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SkeletonModule,
    ScrollPanelModule,
    OverlayModule,
    TableModule,
    FieldsetModule,
    GeboAIAudioRecorderModule,
    ScrollTopModule,
    GeboAIChooseDocumentsPanelModule,
    InputTextModule,
    ProgressSpinnerModule,
    DialogModule,
    ButtonModule,
    PanelModule,
    BlockUIModule,
    TextareaModule,
    GeboAIContentViewerModule,
    BrowseContentModule,
    GeboAIViewTableModule,
    MarkdownModule.forChild(),
    GeboAIFieldTranslationContainerModule,
    GeboAIUploadChatDocumentModule,
    GeboAIDeepSearchModule, 
    PopoverModule, 
    TieredMenuModule, 
    ContextMenuModule, 
    ProgressBarModule,
    ChipModule,
    RadioButtonModule,
    CheckboxModule,
    GeboAIChatSuspensionAnswerModule,
    GeboAINotificationsModule],
  providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIReusableChatModel", multi: false },

  provideMarkdown({
    sanitize: { provide: SANITIZE, useValue: SecurityContext.NONE },
    clipboardOptions: {
      provide: CLIPBOARD_OPTIONS,
      useValue: {
        buttonComponent: ClipboardButtonComponent,
      }
    }
  }), ReactiveRagChatService],
  declarations: [GeboAIChatLibrariesPanelComponent, GeboAIChatWrittenFilesComponent, ChangeDescriptionComponent, DocumentRefComponent, GeboAISelectedChatDocumentsComponent, GeboChatUserInfoComponent, GeboAIUploadedDocumentRefComponent, GeboAIChatInputShellComponent, GeboAIGeneratedDocumentRefComponent, GeboAIReusableChatComponent,GeboAIExtensiblePipelineRouteDisplayComponent,GeboAIChatStreamEventsDisplayComponent],
  exports: [GeboAIChatLibrariesPanelComponent, GeboAIChatWrittenFilesComponent, GeboAIReusableChatComponent]
})
export class GeboAIReusableChatModule { }
