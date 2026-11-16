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
 * the AI chat interface. This module bundles Optimus UI UI components along with Angular form modules
 * to create a comprehensive chat experience.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { GeboAiChatSectionComponent } from "./gebo-ai-rag-chat-section.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ButtonModule } from '@openng/optimus-ui/button';
import { ProgressSpinnerModule } from '@openng/optimus-ui/progressspinner';
import { BlockUIModule } from "@openng/optimus-ui/blockui";
import { ListboxModule } from '@openng/optimus-ui/listbox';
import { ScrollerModule } from '@openng/optimus-ui/scroller';
import { TreeModule } from '@openng/optimus-ui/tree';
import { PaginatorModule } from '@openng/optimus-ui/paginator';
import { EditableListboxModule, GEBO_AI_MODULE, GeboAIReusableChatModule, GeboAIFieldTranslationContainerModule, GeboAINotificationsModule, GeboBlockableContainerDirective } from "@Gebo.ai/reusable-ui";
import { DrawerModule } from '@openng/optimus-ui/drawer';
import { PanelModule } from "@openng/optimus-ui/panel";
import { ScrollPanelModule } from "@openng/optimus-ui/scrollpanel";
import { PopoverModule } from '@openng/optimus-ui/popover';
import { MessageModule } from '@openng/optimus-ui/message';
import { RouterLinkWithHref } from "@angular/router";

/**
 * The GeboAiChatModule provides a self-contained feature module for chat functionality.
 * 
 * This module imports various UI components from Optimus UI (buttons, spinners, listboxes, etc.) and
 * Angular modules (forms, common) to support the chat interface. It declares the GeboAiChatSectionComponent
 * which is the main component for rendering the chat interface, and exports it to make it available
 * to other modules that import this module.
 * 
 * The UI components include:
 * - Form handling (ReactiveFormsModule, FormsModule)
 * - UI components from Optimus UI (Button, ProgressSpinner, BlockUI, etc.)
 * - Custom Gebo.ai components (EditableListboxModule, GeboAIReusableChatModel)
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, PaginatorModule, FormsModule, ButtonModule, ProgressSpinnerModule, BlockUIModule, ListboxModule, ScrollerModule, TreeModule, GeboAIReusableChatModule, EditableListboxModule, DrawerModule, PanelModule, GeboAIFieldTranslationContainerModule, ScrollPanelModule, PopoverModule, MessageModule, RouterLinkWithHref,GeboAINotificationsModule, GeboBlockableContainerDirective],
    declarations: [GeboAiChatSectionComponent],
    exports: [GeboAiChatSectionComponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiChatModule", multi: false }]
})
export class GeboAiChatModule { }