/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * @file GeboAiChatModule
 * AI generated comments
 * 
 * This module provides the necessary imports and declarations for the GeboAiChatSection functionality.
 * It serves as a feature module that encapsulates all components, directives, and pipes related to
 * the AI chat interface. This module bundles PrimeNG UI components along with Angular form modules
 * to create a comprehensive chat experience.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { GeboAiChatSectionComponent } from "./gebo-ai-rag-chat-section.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { BlockUIModule } from "primeng/blockui";
import { ListboxModule } from 'primeng/listbox';
import { ScrollerModule } from 'primeng/scroller';
import { TreeModule } from 'primeng/tree';
import { PaginatorModule } from 'primeng/paginator';
import { EditableListboxModule, GEBO_AI_MODULE, GeboAIReusableChatModel, GeboAIFieldTransationContainerModule } from "@Gebo.ai/reusable-ui";
import { SidebarModule } from 'primeng/sidebar';
import { PanelModule } from "primeng/panel";
import { LayoutComponent } from "./layout.component";
import { ScrollPanelModule } from "primeng/scrollpanel";
import { PopoverModule } from 'primeng/popover';

/**
 * The GeboAiChatModule provides a self-contained feature module for chat functionality.
 * 
 * This module imports various UI components from PrimeNG (buttons, spinners, listboxes, etc.) and
 * Angular modules (forms, common) to support the chat interface. It declares the GeboAiChatSectionComponent
 * which is the main component for rendering the chat interface, and exports it to make it available
 * to other modules that import this module.
 * 
 * The UI components include:
 * - Form handling (ReactiveFormsModule, FormsModule)
 * - UI components from PrimeNG (Button, ProgressSpinner, BlockUI, etc.)
 * - Custom Gebo.ai components (EditableListboxModule, GeboAIReusableChatModel)
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, PaginatorModule, FormsModule, ButtonModule, ProgressSpinnerModule, BlockUIModule, ListboxModule, ScrollerModule, TreeModule, GeboAIReusableChatModel, EditableListboxModule, SidebarModule, PanelModule, GeboAIFieldTransationContainerModule,  ScrollPanelModule,PopoverModule],
    declarations: [GeboAiChatSectionComponent,LayoutComponent],
    exports: [GeboAiChatSectionComponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiChatModule", multi: false }]
})
export class GeboAiChatModule { }