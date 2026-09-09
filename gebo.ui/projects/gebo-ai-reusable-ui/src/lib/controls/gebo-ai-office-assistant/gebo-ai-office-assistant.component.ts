/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import {
  ChangeDetectionStrategy,
  Component,
  TemplateRef,
  computed,
  effect,
  inject,
  input,
  output,
  signal,
  untracked,
} from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import type {
  AdditionalContent,
  GChatProfileConfiguration,
  GUserChatInfo,
} from '@Gebo.ai/gebo-ai-rest-api';
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from '../field-host-component-iface/field-host-component-iface';

/** The two tabs the assistant panel exposes. */
export type AssistantTab = 'assistant' | 'suggestion';

/**
 * Context passed to a caller-provided suggestion-panel template (see
 * {@link GeboAIOfficeAssistantComponent.suggestionPanelTemplate}). The current
 * suggestion parts are provided both as `$implicit` and as `suggestion`.
 */
export interface SuggestionPanelContext {
  $implicit: AdditionalContent[];
  suggestion: AdditionalContent[];
}

/**
 * Pure presentational UI for the Gebo AI Assistant panel used by the office
 * (ONLYOFFICE / Euro-Office) plugin.
 *
 * It owns no host/plugin/auth logic: everything flows in through inputs and out
 * through outputs, so it can be hosted by an office container (the plugin's
 * AppComponent) or reused/tested on its own. It renders the two tabs — the
 * reusable chat control, and an "Assistant suggestion" tab with Replace/Insert
 * actions plus a renderer for the produced document parts. Tab state (including
 * auto-selecting the suggestion tab when one arrives) is owned here as pure UI
 * state, driven off the suggestion input.
 *
 * The suggestion panel below the two buttons renders the parts with a built-in
 * default renderer, unless the caller supplies {@link suggestionPanelTemplate},
 * in which case that template is projected instead (receiving the suggestion
 * parts in its context).
 */
@Component({
  selector: 'gebo-ai-office-assistant',
  standalone: false,
  templateUrl: './gebo-ai-office-assistant.component.html',
  styleUrl: './gebo-ai-office-assistant.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIOfficeAssistantModule", multi: false },
  {
    provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIOfficeAssistantComponent"),
    multi: false
  }]
})
export class GeboAIOfficeAssistantComponent {
  /** Bootstrap error to show in the assistant tab, if any. */
  readonly bootstrapError = input<string | null>(null);
  /** True once the assistant has authenticated against the backend. */
  readonly authReady = input(false);
  /** True while the assistant is still bootstrapping. */
  readonly bootstrapping = input(false);
  /** The resolved chat to host (null until resolved). */
  readonly chatInfo = input<GUserChatInfo | null>(null);
  /** Backend pipeline id the reusable chat control routes through. */
  readonly pipelineId = input('office-assistant');
  /** Live document selection, passed to the chat control as additional content. */
  readonly additionalContents = input<AdditionalContent[]>([]);
  /** Chat profiles to choose from when several exist and no chat is bound yet. */
  readonly profilesToChoose = input<GChatProfileConfiguration[]>([]);
  /** The assistant suggestion (document parts) shown in the second tab. */
  readonly suggestion = input<AdditionalContent[]>([]);
  /** True while the document has a text selection (enables the "Replace" action). */
  readonly hasSelectedText = input(false);
  /** True while connected to the host editor (drives the footer status LED). */
  readonly connected = input(false);
  /**
   * Optional templated renderer for the suggestion panel below the two buttons.
   * When provided it is projected instead of the built-in renderer, receiving the
   * current suggestion parts as `$implicit` (and as `suggestion`) in its context.
   */
  readonly suggestionPanelTemplate = input<TemplateRef<SuggestionPanelContext> | null>(null);

  /** Re-emitted from the chat control when the assistant produces a document suggestion. */
  readonly outputAdditionalContents = output<AdditionalContent[]>();
  /** The user asked to replace the current selection with the suggestion. */
  readonly replaceSelection = output<void>();
  /** The user asked to insert the suggestion at the caret. */
  readonly insertIntoDocument = output<void>();
  /** The user chose a chat profile to start from. */
  readonly startChatWithProfile = output<string>();

  private readonly sanitizer = inject(DomSanitizer);

  /** Which of the two tabs is active — pure UI state owned here. */
  readonly activeTab = signal<AssistantTab>('assistant');
  /** True while an assistant suggestion is available (drives the 2nd tab). */
  readonly hasSuggestion = computed(() => this.suggestion().length > 0);

  constructor() {
    // Auto-select the suggestion tab when a suggestion arrives, and fall back to
    // the assistant tab when it clears — matching the previous inline behaviour.
    // activeTab is read/written untracked so a manual tab switch (which does not
    // change the suggestion) never re-triggers this effect.
    effect(() => {
      const hasSuggestion = this.suggestion().length > 0;
      untracked(() => {
        if (hasSuggestion) this.activeTab.set('suggestion');
        else if (this.activeTab() === 'suggestion') this.activeTab.set('assistant');
      });
    });
  }

  selectTab(tab: AssistantTab): void {
    this.activeTab.set(tab);
  }

  /** True when the fragment is markdown. */
  isMarkdown(content: AdditionalContent): boolean {
    return (content.contentType ?? '').toLowerCase().includes('markdown');
  }

  /** True when the fragment is html. */
  isHtml(content: AdditionalContent): boolean {
    return (content.contentType ?? '').toLowerCase().includes('html');
  }

  /** Sanitized HTML for previewing an html fragment via [innerHTML]. */
  previewHtml(content: AdditionalContent): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(content.content ?? '');
  }
}
