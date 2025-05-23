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
import { ReactiveFormsModule } from "@angular/forms";
import { GeboAIContentViewerComponent } from "./gebo-ai-content-viewer.component";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { BrowseContentModule, EnrichedDocumentReferenceViewRetrieveService } from "@Gebo.ai/reusable-ui";
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { DialogModule } from "primeng/dialog";
import { CodeEditorWrapperComponent } from "./code-editor-wrapper.component";

import { DownloadLinkViewerWrapperComponent } from "./download-link-wrapper.component";
import { PDFViewerWrapper2Component } from "./pdf-viewer-wrapper2.component";
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { GeboUIDocumentOpenerButton } from "./document-opener-button.component";
import { ButtonModule } from "primeng/button";
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,PanelModule,BlockUIModule,BrowseContentModule,MonacoEditorModule,DialogModule,NgxExtendedPdfViewerModule,ButtonModule],
    declarations:[GeboAIContentViewerComponent,CodeEditorWrapperComponent,DownloadLinkViewerWrapperComponent,PDFViewerWrapper2Component,GeboUIDocumentOpenerButton],
    exports:[GeboAIContentViewerComponent,CodeEditorWrapperComponent,GeboUIDocumentOpenerButton],
    providers:[EnrichedDocumentReferenceViewRetrieveService]
})
export class GeboAIContentViewerModule {

}
