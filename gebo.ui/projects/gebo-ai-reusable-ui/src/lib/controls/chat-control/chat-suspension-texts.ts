/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Injectable, inject } from "@angular/core";
import { MenuItem } from "@openng/optimus-ui/api";
import { take } from "rxjs";
import { AgentSuspendedWaitingConditionDto, NetworkOfAgentsSuspendedState } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "../field-translation-container/gebo-translation.service";

/** Where the texts of the questions of the agents live in the language resources. */
export const SUSPENSION_TEXTS_MODULE = "GeboAIChatControlModule";
export const SUSPENSION_TEXTS_ENTITY = "GeboAIChatSuspensionAnswerComponent";

/**
 * The options of a tool call put to a person, as the backend offers them and wants
 * them back (ToolCallConfirmationOptions): shown in the user's language, sent as they are.
 */
export const TOOL_CALL_ALLOW = "Allow";
export const TOOL_CALL_DENY = "Deny";

/**
 * The texts the chat builds around the questions of the agents, by resource id, in
 * English. The English ones are the backend's own (NetworkOfAgentsSuspensionTransport.render,
 * ConfirmingToolCallback): the answer of a suspended run is read back with them.
 */
export const DEFAULT_SUSPENSION_TEXTS = {
    ToolConfirmQuestion: "Allow the agent to run the tool",
    ToolConfirmWithArguments: "with these arguments?",
    ToolConfirmAllow: "Allow",
    ToolConfirmAllowDescription: "The tool runs with these arguments",
    ToolConfirmDeny: "Deny",
    ToolConfirmDenyDescription: "The tool does not run; you may tell the agent why or what to do instead",
    ToolConfirmMessagePlaceholder: "A message for the agent, with either choice (optional)",
    ToolConfirmMessageNote: "(You may add a message for the agent to either choice.)",
    WaitsForSomething: "The work waits for something from you.",
    MoreThanOneNote: "(You may choose more than one.)",
    OwnWordsLine: "or explain in your own words",
    MayLeaveUnansweredNote: "(You may leave this unanswered and go on.)"
};
export type SuspensionTexts = typeof DEFAULT_SUSPENSION_TEXTS;

/** Whether a condition is a tool call put to a person, rather than a question of the agents. */
export function isToolCallConfirmation(condition: AgentSuspendedWaitingConditionDto | undefined): boolean {
    return condition?.attributes?.["toolCallConfirmation"] === true;
}

/** The label of an option of a tool call put to a person, in the user's language. */
export function toolCallOptionLabel(option: string, texts: SuspensionTexts): string {
    return option === TOOL_CALL_ALLOW ? texts.ToolConfirmAllow : option === TOOL_CALL_DENY ? texts.ToolConfirmDeny : option;
}

function optionText(option: any, condition: AgentSuspendedWaitingConditionDto, texts: SuspensionTexts | undefined): string {
    if (option === null || typeof option !== "object") {
        return String(option);
    }
    let label = option.label;
    let description = option.description;
    if (texts && isToolCallConfirmation(condition)) {
        label = toolCallOptionLabel(label, texts);
        description = option.label === TOOL_CALL_ALLOW ? texts.ToolConfirmAllowDescription
            : option.label === TOOL_CALL_DENY ? texts.ToolConfirmDenyDescription : description;
    }
    return description !== undefined && description !== null ? label + ": " + description : String(label);
}

/**
 * The state as the backend renders it for a client that only shows the answer
 * (NetworkOfAgentsSuspensionTransport.render, followed line by line), or, given the
 * texts of a language, the same lines in it: the fixed lines, and a tool call put to a
 * person whole. What the agents wrote (their questions and options) is left as it is.
 */
export function renderSuspendedState(state: NetworkOfAgentsSuspendedState | undefined, texts?: SuspensionTexts): string {
    if (!state?.conditions?.length) {
        return "";
    }
    const fixed = texts ?? DEFAULT_SUSPENSION_TEXTS;
    let text = "";
    for (const condition of state.conditions) {
        if (text.length > 0) {
            text += "\n\n";
        }
        const attributes: { [key: string]: any } = condition.attributes ?? {};
        const statement = attributes["statement"];
        if (texts && isToolCallConfirmation(condition)) {
            text += texts.ToolConfirmQuestion + " \"" + attributes["toolName"] + "\" " + texts.ToolConfirmWithArguments
                + "\n" + (attributes["toolArguments"] ?? "{}");
        } else {
            text += statement !== undefined && statement !== null ? String(statement) : fixed.WaitsForSomething;
        }
        const options = attributes["options"];
        if (Array.isArray(options) && options.length > 0) {
            for (const option of options) {
                text += "\n- " + optionText(option, condition, texts);
            }
            if (attributes["multipleChoice"] === true) {
                text += "\n" + fixed.MoreThanOneNote;
            }
            if (attributes["toolCallConfirmation"] === true) {
                text += "\n" + fixed.ToolConfirmMessageNote;
            } else if (attributes["customAnswerAllowed"] === true) {
                text += "\n- " + fixed.OwnWordsLine;
            }
        }
        if (attributes["mandatory"] === false) {
            text += "\n" + fixed.MayLeaveUnansweredNote;
        }
    }
    return text;
}

/**
 * The answer of a run that suspended, its questions in the user's language: the backend
 * appends the questions, in English, to whatever the agents streamed; they are read back
 * and written again with the texts given. An answer not ending with them as expected is
 * shown as it is.
 */
export function localizedSuspendedAnswer(answer: string | undefined | null, state: NetworkOfAgentsSuspendedState | undefined,
    texts: SuspensionTexts): string | undefined | null {
    if (!answer || !state?.conditions?.length) {
        return answer;
    }
    const english = renderSuspendedState(state);
    if (!english || !answer.endsWith(english)) {
        return answer;
    }
    return answer.substring(0, answer.length - english.length) + renderSuspendedState(state, texts);
}

/**
 * The texts of the questions of the agents in the language the UI shows, kept up to
 * date when the user changes it.
 */
@Injectable({ providedIn: "root" })
export class GeboAISuspensionTextsService {
    private translationService = inject(GeboAITranslationService);
    private _texts: SuspensionTexts = { ...DEFAULT_SUSPENSION_TEXTS };

    constructor() {
        this.translate();
        this.translationService.languageChanges.subscribe(() => this.translate());
    }

    /** The texts in the current language; a new object whenever they change. */
    get texts(): SuspensionTexts {
        return this._texts;
    }

    private translate(): void {
        // fresh items each time: translateMenuItems writes the translations into them
        const items: MenuItem[] = Object.entries(DEFAULT_SUSPENSION_TEXTS).map(([id, label]) => ({ id, label }));
        this.translationService.translateMenuItems(SUSPENSION_TEXTS_MODULE, SUSPENSION_TEXTS_ENTITY, items).pipe(take(1))
            .subscribe(translated => {
                const texts: any = { ...DEFAULT_SUSPENSION_TEXTS };
                translated.forEach(item => {
                    if (item.id && item.label && item.id in texts) {
                        texts[item.id] = item.label;
                    }
                });
                this._texts = texts as SuspensionTexts;
            });
    }
}
