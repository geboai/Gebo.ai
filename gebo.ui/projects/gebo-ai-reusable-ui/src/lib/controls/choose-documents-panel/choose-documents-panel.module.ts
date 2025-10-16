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

import { PanelModule } from "primeng/panel";
import { GeboAIChooseDocumentsPanelComponent } from "./choose-documents-panel.component";
import { TabsModule } from 'primeng/tabs';
import { DialogModule } from "primeng/dialog";
import { GeboAISearchDocumentsComponent } from "./search-documents.component";
import { BlockUIModule } from "primeng/blockui";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { GeboAIDocumentsListPanelComponent } from "./documents-list-panel.component";
import { CheckboxModule } from "primeng/checkbox";
import { PickListModule } from 'primeng/picklist';
import { DragDropModule } from "@angular/cdk/drag-drop";
import { GeboAIUserspaceFilesModule } from "../userspace-files-component/userspace-files.module";
import { VFilesystemSelectorModule } from "../vfilesystem-selector/vfilesystem-selector.module";
import { GeboAIContentViewerModule } from "../content-viewer/gebo-ai-content-viewer.module";
import { GeboAIFieldContainerModule } from "../field-container/field-container.module";

/**
 * AI generated comments
 * 
 * @NgModule GeboAIChooseDocumentsPanelModule
 * This module organizes and exports components related to document selection, searching, and display
 * functionality within the Gebo.ai application. It bundles together multiple UI components that 
 * handle document selection, searching, and listing operations.
 * 
 * The module imports various Angular and PrimeNG modules that provide UI components and functionality:
 * - Angular core modules (CommonModule, ReactiveFormsModule, DragDropModule)
 * - PrimeNG UI components (PanelModule, TabsModule, DialogModule, etc.)
 * - Gebo.ai custom modules (VFilesystemSelectorModule, GeboAIContentViewerModule, etc.)
 * 
 * It declares and exports three main components:
 * - GeboAIChooseDocumentsPanelComponent: Main component for document selection
 * - GeboAISearchDocumentsComponent: Provides document search functionality
 * - GeboAIDocumentsListPanelComponent: Displays lists of selected documents
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, VFilesystemSelectorModule, PanelModule,  DialogModule, BlockUIModule, ButtonModule,TabsModule, GeboAIContentViewerModule, InputTextModule, CheckboxModule, PickListModule, DragDropModule, GeboAIUserspaceFilesModule,GeboAIFieldContainerModule],
    declarations: [GeboAIChooseDocumentsPanelComponent,GeboAISearchDocumentsComponent,GeboAIDocumentsListPanelComponent],
    exports: [GeboAIChooseDocumentsPanelComponent,GeboAISearchDocumentsComponent,GeboAIDocumentsListPanelComponent]
    
})
export class GeboAIChooseDocumentsPanelModule {

}