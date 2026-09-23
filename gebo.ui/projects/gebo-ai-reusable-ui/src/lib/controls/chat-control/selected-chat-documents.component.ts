/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, EventEmitter, forwardRef, Input, Output } from "@angular/core";
import { GResponseDocumentRef } from '@Gebo.ai/brain';
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * Displays the documents the user has chosen to "chat with", as
 * {@link GResponseDocumentRef}s regardless of whether they come from a knowledge
 * base (a GDocumentReference) or from an external search result. It owns only the
 * DISPLAY of the current selection plus removing one document or clearing the
 * whole list - the actual picking of knowledge-base documents stays in
 * {@code gebo-ai-choose-documents-panel-component}. The list layout is adapted
 * from that panel's own selected-documents area, but rendered through
 * {@code gebo-ai-chat-docref} so a KB document and an external search result look
 * and open the same way.
 */
@Component({
    selector: "gebo-ai-selected-chat-documents",
    templateUrl: "selected-chat-documents.component.html",
    styleUrl: "selected-chat-documents.component.scss",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("SelectedChatDocumentsComponent"), multi: false }
    ]
})
export class GeboAISelectedChatDocumentsComponent {

    /** The documents currently chosen to chat with (knowledge-base and/or external). */
    @Input() documents: GResponseDocumentRef[] = [];

    /** Maximum number of documents shown inline before the "..." (show all) button. */
    @Input() maxDisplayedDocuments?: number;

    /** Read-only mode: no remove/clear controls. */
    @Input() readonly: boolean = false;

    /** Disables the remove/clear interactions (e.g. while a response is streaming). */
    @Input() interactionDisabled: boolean = false;

    /** Emitted when the user removes a single document from the list. */
    @Output() removeDocument: EventEmitter<GResponseDocumentRef> = new EventEmitter();

    /** Emitted when the user clears the whole list. */
    @Output() clearDocuments: EventEmitter<void> = new EventEmitter();

    /** Controls the "show all selected documents" dialog. */
    public openedFullListWindow: boolean = false;

    getEntityName(): string {
        return "SelectedChatDocumentsComponent";
    }

    /** The documents shown inline (capped by {@link maxDisplayedDocuments}). */
    public get displayedDocuments(): GResponseDocumentRef[] {
        if (this.maxDisplayedDocuments !== undefined && this.maxDisplayedDocuments >= 0
            && this.documents.length > this.maxDisplayedDocuments) {
            return this.documents.slice(0, this.maxDisplayedDocuments);
        }
        return this.documents;
    }

    /** True when there are more documents than the inline cap. */
    public get hasMoreDocuments(): boolean {
        return this.maxDisplayedDocuments !== undefined && this.maxDisplayedDocuments >= 0
            && this.documents.length > this.maxDisplayedDocuments;
    }

    /** Number of documents hidden behind the "..." button. */
    public get hiddenDocumentsCount(): number {
        return Math.max(0, this.documents.length - (this.maxDisplayedDocuments || 0));
    }

    public get canInteract(): boolean {
        return this.readonly === false && this.interactionDisabled === false;
    }

    public remove(document: GResponseDocumentRef): void {
        this.removeDocument.emit(document);
    }

    public clearAll(): void {
        this.clearDocuments.emit();
    }
}
