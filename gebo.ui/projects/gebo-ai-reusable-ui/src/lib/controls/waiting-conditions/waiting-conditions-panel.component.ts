/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { WaitingBlockView, WaitingConditionsActionAck, WaitingConditionsAdminControllerService, WaitingConditionsControllerService, WaitingConditionView } from "@Gebo.ai/gebo-ai-rest-api";
import { ConfirmationService } from "primeng/api";
import { catchError, Observable, of, Subscription, switchMap, timer } from "rxjs";
import { ExtendedConfirmation, GeboAITranslationService } from "../field-translation-container/gebo-translation.service";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

const moduleId: string = "GeboAIWaitingConditionsModule";
const entityId: string = "GeboAIWaitingConditionsPanelComponent";

/** What the last action did, shown above the list. */
export interface WaitingConditionsMessage {
    severity: "success" | "info" | "warn" | "error";
    summary: string;
    detail?: string;
}

/**
 * The runs of networks of agents waiting for a human in the loop or an external
 * condition: the user's own, or every run for an administrator. Each run shows
 * whose conversation it is, since when it waits, what it waits for and what is
 * already answered, and whether it is stuck past an expiry. Its owner, or an
 * administrator, may open the conversation, go on without the conditions still
 * waiting (all of them, or the ones ticked), or abandon the run. The list is
 * reloaded every {@link refreshSeconds} seconds.
 */
@Component({
    selector: "gebo-ai-waiting-conditions-panel",
    templateUrl: "waiting-conditions-panel.component.html",
    styleUrls: ["waiting-conditions-panel.component.scss"],
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: moduleId, multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName(entityId), multi: false }]
})
export class GeboAIWaitingConditionsPanelComponent implements OnInit, OnDestroy {
    /** Every run, with the administrators' actions; the user's own runs otherwise. */
    @Input() administrator: boolean = false;
    /** How often the list is reloaded. */
    @Input() refreshSeconds: number = 60;
    /** The chat page: a conversation opens at {@code <chatRoute>/<conversationCode>/load}. */
    @Input() chatRoute: string = "/ui/chat";
    public blocks: WaitingBlockView[] = [];
    public loading: boolean = false;
    public lastMessages: WaitingConditionsMessage[] = [];
    /** The conditions ticked to go on without, by block. */
    private ticked: { [blockCode: string]: string[] } = {};
    private subscription?: Subscription;

    constructor(private usersService: WaitingConditionsControllerService,
        private adminsService: WaitingConditionsAdminControllerService,
        private confirmService: ConfirmationService,
        private translationService: GeboAITranslationService) {
    }

    ngOnInit(): void {
        const periodMs = Math.max(this.refreshSeconds, 15) * 1000;
        this.subscription = timer(0, periodMs).pipe(
            switchMap(() => this.load().pipe(
                // a failed reload keeps the list shown: the next one tries again
                catchError(error => {
                    console.error("Cannot read the waiting conditions", error);
                    return of(null);
                })))
        ).subscribe(blocks => {
            if (blocks) {
                this.show(blocks);
            }
        });
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe();
    }

    /** Reloads the list now. */
    reload(): void {
        this.loading = true;
        this.load().subscribe({
            next: blocks => {
                this.show(blocks);
                this.loading = false;
            },
            error: error => {
                console.error("Cannot read the waiting conditions", error);
                this.loading = false;
            }
        });
    }

    private load(): Observable<WaitingBlockView[]> {
        return this.administrator ? this.adminsService.allWaitingConditions() : this.usersService.waitingConditions();
    }

    private show(blocks: WaitingBlockView[]): void {
        this.blocks = blocks ?? [];
        // the ticks of the runs no longer listed are forgotten
        const listed = new Set(this.blocks.map(b => b.blockCode ?? ""));
        for (const code of Object.keys(this.ticked)) {
            if (!listed.has(code)) {
                delete this.ticked[code];
            }
        }
    }

    chatLinkOf(block: WaitingBlockView): string[] {
        return [this.chatRoute, block.conversationCode ?? "", "load"];
    }

    /** The conditions of the run still waiting for an answer. */
    openConditionsOf(block: WaitingBlockView): WaitingConditionView[] {
        return (block.conditions ?? []).filter(c => c.solved !== true);
    }

    isTicked(block: WaitingBlockView, condition: WaitingConditionView): boolean {
        return this.tickedOf(block).includes(condition.conditionId ?? "");
    }

    tick(block: WaitingBlockView, condition: WaitingConditionView, ticked: boolean): void {
        const id = condition.conditionId ?? "";
        const current = this.tickedOf(block).filter(c => c !== id);
        if (ticked) {
            current.push(id);
        }
        this.ticked[block.blockCode ?? ""] = current;
    }

    tickedOf(block: WaitingBlockView): string[] {
        return this.ticked[block.blockCode ?? ""] ?? [];
    }

    /** Whether some, not all, of the open conditions are ticked: the action takes those only. */
    hasPartialTicks(block: WaitingBlockView): boolean {
        const ticked = this.tickedOf(block).length;
        return ticked > 0 && ticked < this.openConditionsOf(block).length;
    }

    trackByBlock(index: number, block: WaitingBlockView): string | undefined {
        return block.blockCode;
    }

    /**
     * Goes on without the conditions still waiting, mandatory or not: the ones ticked
     * when some are, every one otherwise. The run resumes once nothing mandatory is left.
     */
    goOnWithout(block: WaitingBlockView): void {
        const partial = this.hasPartialTicks(block);
        const confirmation: ExtendedConfirmation = {
            id: partial ? "GO_ON_WITHOUT_TICKED_ACTION" : "GO_ON_WITHOUT_ACTION",
            header: "Go on without an answer",
            message: partial
                ? "The agents will go on without the answers you ticked, told that you chose so. Go on?"
                : "The agents will go on without every answer still missing, told that you chose so. Go on?",
            accept: () => {
                const conditionIds = partial ? this.tickedOf(block) : undefined;
                this.perform(block, this.administrator
                    ? this.adminsService.goOnWithout1(block.blockCode ?? "", conditionIds)
                    : this.usersService.goOnWithout(block.blockCode ?? "", conditionIds));
            }
        };
        this.confirm(confirmation);
    }

    /** Drops the run where it stands, never to be resumed: the conversation is free. */
    abandon(block: WaitingBlockView): void {
        const confirmation: ExtendedConfirmation = {
            id: "ABANDON_WAITING_RUN_ACTION",
            header: "Abandon the waiting run",
            message: "The run will be abandoned, its questions never answered, and the conversation will be free. Abandon it?",
            accept: () => {
                this.perform(block, this.administrator
                    ? this.adminsService.abandon1(block.blockCode ?? "")
                    : this.usersService.abandon(block.blockCode ?? ""));
            }
        };
        this.confirm(confirmation);
    }

    private confirm(confirmation: ExtendedConfirmation): void {
        const translation = this.translationService.translateConfirmation(moduleId, entityId, confirmation).subscribe({
            next: translated => {
                this.confirmService.confirm(translated ? translated : confirmation);
                try {
                    translation.unsubscribe();
                } catch (e) {
                    // already closed
                }
            }
        });
    }

    private perform(block: WaitingBlockView, action: Observable<WaitingConditionsActionAck>): void {
        this.loading = true;
        action.subscribe({
            next: ack => {
                this.lastMessages = [this.messageOf(block, ack)];
                this.reload();
            },
            error: error => {
                console.error("The action on the waiting run failed", error);
                this.lastMessages = [{ severity: "error", summary: "The action failed", detail: String(error?.message ?? error) }];
                this.loading = false;
            }
        });
    }

    private messageOf(block: WaitingBlockView, ack: WaitingConditionsActionAck): WaitingConditionsMessage {
        switch (ack?.outcome) {
            case "DONE":
                return { severity: "success", summary: "Done", detail: ack.reason ?? undefined };
            case "NOT_FOUND":
                return { severity: "warn", summary: "The run no longer waits", detail: "It was answered, dropped or abandoned meanwhile." };
            default:
                return { severity: "warn", summary: "Not done", detail: ack?.reason ?? undefined };
        }
    }
}
