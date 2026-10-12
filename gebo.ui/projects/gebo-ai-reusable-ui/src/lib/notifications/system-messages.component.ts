/*
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { GSystemMessage, SystemMessagesControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { catchError, of, Subscription, switchMap, timer } from "rxjs";

/**
 * Banner with the system messages of the logged-in user, as routed by the backend
 * (administrators receive the administrators' and everybody's messages, the other
 * users the users' and everybody's). Place it once in an authenticated application
 * shell: it loads the messages at start and then every {@link refreshSeconds}
 * seconds, and lets the user hide the dismissible ones.
 */
@Component({
    selector: "gebo-ai-system-messages",
    templateUrl: "system-messages.component.html",
    styleUrls: ["system-messages.component.scss"],
    standalone: false
})
export class GeboAISystemMessagesComponent implements OnInit, OnDestroy {
    /** How often the messages are reloaded. */
    @Input() refreshSeconds: number = 300;
    /** The messages currently shown, most severe first. */
    messages: GSystemMessage[] = [];
    private subscription?: Subscription;

    constructor(private systemMessagesService: SystemMessagesControllerService) {
    }

    ngOnInit(): void {
        const periodMs = Math.max(this.refreshSeconds, 30) * 1000;
        this.subscription = timer(0, periodMs).pipe(
            switchMap(() => this.systemMessagesService.getMySystemMessages().pipe(
                // A failed poll keeps the messages already shown: the next one retries.
                catchError(() => of(null))))
        ).subscribe(messages => {
            if (messages) {
                this.messages = messages;
            }
        });
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe();
    }

    /**
     * Hides a message for the logged-in user until its content changes.
     *
     * @param message the message to hide
     */
    dismiss(message: GSystemMessage): void {
        if (!message.id || !message.dismissible) {
            return;
        }
        this.systemMessagesService.dismissSystemMessage({ messageId: message.id }).subscribe({
            next: (status) => {
                if (status?.result) {
                    this.messages = this.messages.filter(m => m.id !== message.id);
                }
            }
        });
    }

    trackById(index: number, message: GSystemMessage): string | undefined {
        return message.id;
    }
}
