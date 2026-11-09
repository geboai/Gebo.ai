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
}

/**
 * The questions a suspended network of agents asks the user, under the answer that
 * asked them: one block per condition, a single or multiple choice among the options
 * the agents chose with an explanation of the user's own when allowed, a value for any other
 * kind of condition. Answering respawns the network with the reaction; a block whose
 * conditions may all be left unanswered can be skipped, the conversation going on.
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

    isAnswered(condition: AgentSuspendedWaitingConditionDto): boolean {
        const answer = this.answerOf(condition);
        if (this.isClarification(condition)) {
            return !!answer.chosenOption || !!answer.chosenOptions?.length
                || (this.customAllowed(condition) && !!answer.customExplanation?.trim());
        }
        return !!answer.value?.trim();
    }

    /** Every mandatory condition answered, and at least one answer given. */
    canSend(): boolean {
        if (this.disabled || this.conditions.length === 0) {
            return false;
        }
        const answered = this.conditions.filter(c => this.isAnswered(c));
        return answered.length > 0 && this.conditions.every(c => !this.isMandatory(c) || this.isAnswered(c));
    }

    send(): void {
        if (!this.canSend() || !this._state) {
            return;
        }
        const reactions: AgentSuspendedWaitingConditionReactionDto[] = [];
        const lines: string[] = [];
        for (const condition of this.conditions) {
            const answer = this.answerOf(condition);
            const reaction: AgentSuspendedWaitingConditionReactionDto = {
                conditionType: condition.conditionType,
                conditionId: condition.conditionId,
                attributes: {}
            };
            if (!this.isAnswered(condition)) {
                // a condition that is not mandatory, left unanswered: declined
                reaction.attributes!["declined"] = true;
                reactions.push(reaction);
                continue;
            }
            if (this.isMultipleChoice(condition)) {
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
}
