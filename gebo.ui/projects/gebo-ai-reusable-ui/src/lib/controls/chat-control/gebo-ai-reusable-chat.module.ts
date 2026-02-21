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
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { ButtonModule } from "primeng/button";
import { PanelModule } from "primeng/panel";
import { DialogModule } from "primeng/dialog";
import { BlockUIModule } from "primeng/blockui";
import { TextareaModule } from 'primeng/textarea';

import { ChangeDescriptionComponent } from "./change-description.component";
import { GeboAIContentViewerModule } from "../content-viewer/gebo-ai-content-viewer.module";
import { DocumentRefComponent } from "./document-ref.component";


import { GeboAIViewTableModule } from "../view-table/view-table.module";
import { CLIPBOARD_OPTIONS, ClipboardButtonComponent, MarkdownModule, provideMarkdown } from "ngx-markdown";
import { SecurityContext } from '@angular/core';
import { SkeletonModule } from 'primeng/skeleton';
import { OverlayModule } from 'primeng/overlay';
import { GeboChatUserInfoComponent } from "./chat-info.component";
import { TableModule } from "primeng/table";
import { FieldsetModule } from "primeng/fieldset";
import { ScrollTopModule } from 'primeng/scrolltop';
import { InputTextModule } from "primeng/inputtext";
import { ReactiveRagChatService } from "./reactive-chat.service";
import { ProgressSpinnerModule } from 'primeng/progressspinner';

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
import { TieredMenuModule } from 'primeng/tieredmenu';
import { PopoverModule } from 'primeng/popover';
import { ContextMenuModule } from "primeng/contextmenu";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";
import { GeboAIExtensiblePipelineRouteDisplayComponent } from "./extensible-pipeline-route-label.component";
import { GeboAIChatStreamEventsDisplayComponent } from "./chat-stream-events-display.component";
import { ProgressBarModule } from "primeng/progressbar";
console.log(
  '[GeboAIReusableChatModule] declarations =',
  GeboAIReusableChatComponent,
  ChangeDescriptionComponent,
  DocumentRefComponent,  
  GeboChatUserInfoComponent
);
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
    GeboAINotificationsModule],
  providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIReusableChatModel", multi: false },

  provideMarkdown({
    sanitize: SecurityContext.NONE,
    clipboardOptions: {
      provide: CLIPBOARD_OPTIONS,
      useValue: {
        buttonComponent: ClipboardButtonComponent,
      }
    }
  }), ReactiveRagChatService],
  declarations: [ChangeDescriptionComponent, DocumentRefComponent,GeboChatUserInfoComponent, GeboAIUploadedDocumentRefComponent, GeboAIChatInputShellComponent, GeboAIGeneratedDocumentRefComponent, GeboAIReusableChatComponent,GeboAIExtensiblePipelineRouteDisplayComponent,GeboAIChatStreamEventsDisplayComponent],
  exports: [GeboAIReusableChatComponent]
})
export class GeboAIReusableChatModule { }
