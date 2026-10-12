/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { RuleSummary, summarizeRule } from "./schedule-format";
import { RuleProblem, ScheduleRule, ruleProblem } from "./schedule-rules.model";

/**
 * A rule said in words: the structural words come from the UI text resources, the day names,
 * clock times and dates from Intl on the language in use.
 *
 * The words are separate elements on purpose. A single translatable sentence with the data
 * interpolated into it ("Every week on {days} at {times}") cannot be reordered by a translator,
 * and the gebo-ai-text directive only substitutes static content anyway.
 */
@Component({
    selector: "gebo-ai-schedule-sentence",
    templateUrl: "schedule-sentence.component.html",
    standalone: false
})
export class GeboAIScheduleSentenceComponent implements OnChanges {

    /** The rule to say. */
    @Input({ required: true }) rule!: ScheduleRule;

    /** The language the data words are rendered in. */
    @Input() locale: string = "en";

    /** Whether to point out that the rule is still incomplete. */
    @Input() showProblem: boolean = true;

    /** The localized data of the rule. */
    public summary: RuleSummary = { days: [], times: [], dates: [], minute: ":00" };

    /** Why the rule is not saveable yet, when it is not. */
    public problem?: RuleProblem;

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["rule"] || changes["locale"]) {
            this.summary = summarizeRule(this.rule, this.locale);
            this.problem = ruleProblem(this.rule);
        }
    }
}
