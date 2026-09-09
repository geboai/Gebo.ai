/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { MarkdownModule } from 'ngx-markdown';
import { GeboAIReusableChatModule } from '../chat-control/gebo-ai-reusable-chat.module';
import { GeboAIOfficeAssistantComponent } from './gebo-ai-office-assistant.component';

/**
 * Standalone-consumable NgModule for {@link GeboAIOfficeAssistantComponent}. It
 * pulls in everything the assistant panel template needs — the reusable chat
 * control, the PrimeNG button for the Replace/Insert actions, markdown rendering
 * for suggestion parts, and CommonModule for the templated suggestion panel —
 * so a host only has to import this one module to use `<gebo-ai-office-assistant>`.
 */
@NgModule({
  imports: [CommonModule, ButtonModule, MarkdownModule.forChild(), GeboAIReusableChatModule],
  declarations: [GeboAIOfficeAssistantComponent],
  exports: [GeboAIOfficeAssistantComponent],
})
export class GeboAIOfficeAssistantModule {}
