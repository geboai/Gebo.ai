/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, EventEmitter, Input, Output } from "@angular/core";
import { AgentSuspendedWaitingConditionDto, AgentSuspendedWaitingConditionReactionDto, NetworkOfAgentsSuspendedState, NetworkOfAgentsSuspendedStateReaction } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/** What the user answered, as the request that respawns the network carries it, and as the chat shows it. */
export interface SuspensionAnswer {
    reaction: NetworkOfAgentsSuspendedStateReaction;
    text: string;
}

/** An option of a question: what the user chooses, and what choosing it means. */
interface QuestionOption {
    label: string;
    description?: string;
}

/** The user's answer to one condition, while they write it. */
interface ConditionAnswer {
    chosenOption?: string;
    chosenOptions?: string[];
    customExplanation?: string;
    value?: string;
    /** No answer: the agents go on without it, mandatory or not, told that the user chose so. */
    goOnWithout?: boolean;
}

/** The attribute of an answer asking to go on without the condition (read by the chat only). */
const GO_ON_WITHOUT = "goOnWithout";
/** The line shown in the chat for a condition the user goes on without. */
const GONE_ON_WITHOUT_TEXT = "(no answer: go on without it)";

/**
 * The questions a suspended network of agents asks the user, under the answer that
 * asked them: one block per condition, a single or multiple choice among the options
 * the agents chose with an explanation of the user's own when allowed, a value for any other
 * kind of condition. Answering respawns the network with the reaction; a block whose
 * conditions may all be left unanswered can be skipped, the conversation going on.
 * Where the conditions are answered one at a time (the state says so) those another
 * source already solved are shown as answered, and an answer may leave others open:
 * the agents resume once every mandatory one has an answer, from whichever source.
 */
@Component({
    selector: "gebo-ai-chat-suspension-answer",
    templateUrl: "chat-suspension-answer.component.html",
    styleUrls: ["chat-suspension-answer.component.scss"],
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChatSuspensionAnswerComponent"), multi: false }]
})
export class GeboAIChatSuspensionAnswerComponent {
    private _state?: NetworkOfAgentsSuspendedState;
    @Input() disabled: boolean = false;
    @Output() answer: EventEmitter<SuspensionAnswer> = new EventEmitter<SuspensionAnswer>();
    @Output() skip: EventEmitter<void> = new EventEmitter<void>();
    /** The user abandons the run of the block whose id is given. */
    @Output() abandon: EventEmitter<string> = new EventEmitter<string>();
    public answers: { [conditionId: string]: ConditionAnswer } = {};

    @Input()
    set state(state: NetworkOfAgentsSuspendedState | undefined) {
        if (this._state?.networkOfAgentsBlockId !== state?.networkOfAgentsBlockId) {
            this.answers = {};
        }
        this._state = state;
    }
    get state(): NetworkOfAgentsSuspendedState | undefined {
        return this._state;
    }

    get conditions(): AgentSuspendedWaitingConditionDto[] {
        return this._state?.conditions ?? [];
    }

    /** Whether a condition of the block is mandatory: the conversation waits for the block. */
    get mandatory(): boolean {
        return this.conditions.some(c => this.isMandatory(c));
    }

    /** Whether the conditions are answered one at a time, next to other sources. */
    get oneByOne(): boolean {
        return this._state?.answersOneByOne === true;
    }

    /** Whether an answer of some source already solves the condition: it is not asked again. */
    isSolved(condition: AgentSuspendedWaitingConditionDto): boolean {
        return this.oneByOne && condition.attributes?.["solved"] === true;
    }

    /** The source of the answer that solves the condition. */
    solvedByOf(condition: AgentSuspendedWaitingConditionDto): string | undefined {
        return condition.attributes?.["solvedBy"];
    }

    /** The conditions still asked. */
    get openConditions(): AgentSuspendedWaitingConditionDto[] {
        return this.conditions.filter(c => !this.isSolved(c));
    }

    isClarification(condition: AgentSuspendedWaitingConditionDto): boolean {
        return condition.conditionType === "clarification";
    }

    isMandatory(condition: AgentSuspendedWaitingConditionDto): boolean {
        return condition.attributes?.["mandatory"] !== false;
    }

    statementOf(condition: AgentSuspendedWaitingConditionDto): string {
        return condition.attributes?.["question"] ?? condition.attributes?.["statement"] ?? "";
    }

    explanationOf(condition: AgentSuspendedWaitingConditionDto): string | undefined {
        return condition.attributes?.["explanation"];
    }

    /** The options as labels and descriptions; an answer saved before descriptions existed has plain texts. */
    optionsOf(condition: AgentSuspendedWaitingConditionDto): QuestionOption[] {
        const options = condition.attributes?.["options"];
        if (!Array.isArray(options)) {
            return [];
        }
        return options.map(option => typeof option === "string" ? { label: option } : option);
    }

    isMultipleChoice(condition: AgentSuspendedWaitingConditionDto): boolean {
        return condition.attributes?.["multipleChoice"] === true;
    }

    customAllowed(condition: AgentSuspendedWaitingConditionDto): boolean {
        return condition.attributes?.["customAnswerAllowed"] === true;
    }

    answerOf(condition: AgentSuspendedWaitingConditionDto): ConditionAnswer {
        const id = condition.conditionId ?? "";
        if (!this.answers[id]) {
            this.answers[id] = {};
        }
        return this.answers[id];
    }

    /** An option chosen clears an explanation, and the other way round: one answer per condition. */
    chooseOption(condition: AgentSuspendedWaitingConditionDto, option: string): void {
        const answer = this.answerOf(condition);
        answer.chosenOption = option;
        answer.customExplanation = undefined;
    }

    isChosen(condition: AgentSuspendedWaitingConditionDto, option: string): boolean {
        return this.answerOf(condition).chosenOptions?.includes(option) === true;
    }

    /** On a multiple choice the options chosen stay in the order they are shown, beside any explanation. */
    toggleOption(condition: AgentSuspendedWaitingConditionDto, option: string, chosen: boolean): void {
        const answer = this.answerOf(condition);
        answer.chosenOptions = this.optionsOf(condition).map(o => o.label)
            .filter(label => label === option ? chosen : this.isChosen(condition, label));
    }

    onExplanationChange(condition: AgentSuspendedWaitingConditionDto): void {
        if (this.isMultipleChoice(condition)) {
            return;
        }
        const answer = this.answerOf(condition);
        if (answer.customExplanation && answer.customExplanation.trim() !== "") {
            answer.chosenOption = undefined;
        }
    }

    /** Whether the user chose to go on without this condition's answer. */
    goesOnWithout(condition: AgentSuspendedWaitingConditionDto): boolean {
        return this.answerOf(condition).goOnWithout === true;
    }

    /** Going on without a condition drops whatever was answered to it, and the other way round. */
    toggleGoOnWithout(condition: AgentSuspendedWaitingConditionDto, goOnWithout: boolean): void {
        const answer = this.answerOf(condition);
        answer.goOnWithout = goOnWithout;
        if (goOnWithout) {
            answer.chosenOption = undefined;
            answer.chosenOptions = undefined;
            answer.customExplanation = undefined;
            answer.value = undefined;
        }
    }

    isAnswered(condition: AgentSuspendedWaitingConditionDto): boolean {
        const answer = this.answerOf(condition);
        if (answer.goOnWithout === true) {
            return true;
        }
        if (this.isClarification(condition)) {
            return !!answer.chosenOption || !!answer.chosenOptions?.length
                || (this.customAllowed(condition) && !!answer.customExplanation?.trim());
        }
        return !!answer.value?.trim();
    }

    /**
     * At least one answer given, and every mandatory condition answered; one at a time,
     * any answer to a condition still asked.
     */
    canSend(): boolean {
        if (this.disabled || this.conditions.length === 0) {
            return false;
        }
        const answered = this.openConditions.filter(c => this.isAnswered(c));
        return answered.length > 0 && (this.oneByOne || this.completes());
    }

    /** Whether the answers leave no mandatory condition still asked unanswered. */
    private completes(): boolean {
        return this.openConditions.every(c => !this.isMandatory(c) || this.isAnswered(c));
    }

    send(): void {
        if (!this.canSend() || !this._state) {
            return;
        }
        const reactions: AgentSuspendedWaitingConditionReactionDto[] = [];
        const lines: string[] = [];
        // answers that leave a mandatory condition open leave the optional ones open too
        const completes = this.completes();
        for (const condition of this.openConditions) {
            const answer = this.answerOf(condition);
            const reaction: AgentSuspendedWaitingConditionReactionDto = {
                conditionType: condition.conditionType,
                conditionId: condition.conditionId,
                attributes: {}
            };
            if (!this.isAnswered(condition)) {
                if (completes && !this.isMandatory(condition)) {
                    // a condition that is not mandatory, left unanswered: declined
                    reaction.attributes!["declined"] = true;
                    reactions.push(reaction);
                }
                continue;
            }
            if (answer.goOnWithout === true) {
                reaction.attributes![GO_ON_WITHOUT] = true;
                lines.push(GONE_ON_WITHOUT_TEXT);
            } else if (this.isMultipleChoice(condition)) {
                const chosen = answer.chosenOptions ?? [];
                const explanation = answer.customExplanation?.trim();
                reaction.attributes!["chosenOptions"] = chosen;
                if (explanation) {
                    reaction.attributes!["customExplanation"] = explanation;
                }
                lines.push(...chosen, ...(explanation ? [explanation] : []));
            } else if (this.isClarification(condition)) {
                if (answer.chosenOption) {
                    reaction.attributes!["chosenOption"] = answer.chosenOption;
                    lines.push(answer.chosenOption);
                } else {
                    reaction.attributes!["customExplanation"] = answer.customExplanation?.trim();
                    lines.push(answer.customExplanation?.trim() ?? "");
                }
            } else {
                reaction.attributes!["value"] = answer.value?.trim();
                lines.push(answer.value?.trim() ?? "");
            }
            reactions.push(reaction);
        }
        this.answer.emit({
            reaction: { networkOfAgentsBlockId: this._state.networkOfAgentsBlockId, reactions: reactions },
            text: lines.join("\n")
        });
    }

    doSkip(): void {
        if (!this.mandatory) {
            this.skip.emit();
        }
    }

    /** Whether some condition is still asked: the user may go on without every one, or abandon the run. */
    get hasOpenConditions(): boolean {
        return this.openConditions.length > 0;
    }

    /**
     * The user goes on without every answer still missing, mandatory or not: the agents
     * resume, told that the user chose so; what was already written is kept as answered.
     */
    goOnWithoutAll(): void {
        if (this.disabled || !this._state || !this.hasOpenConditions) {
            return;
        }
        for (const condition of this.openConditions) {
            if (!this.isAnswered(condition)) {
                this.toggleGoOnWithout(condition, true);
            }
        }
        this.send();
    }

    /** The user abandons the run: it is dropped where it stands, never to be resumed. */
    doAbandon(): void {
        if (!this.disabled && this._state?.networkOfAgentsBlockId) {
            this.abandon.emit(this._state.networkOfAgentsBlockId);
        }
    }
}
