/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { formatDateTime, formatHourMinute, formatMinuteOfHour, weekdayNames } from "./schedule-format";
import {
    HourMinute, RuleProblem, SCHEDULABLE_FREQUENCIES, SchedulableFrequency, ScheduleRule,
    changeRuleKind, ruleProblem, tomorrowAt
} from "./schedule-rules.model";

/**
 * One scheduling rule, shown as a card that reads like a sentence when collapsed and opens into
 * its editor in place.
 *
 * The card owns no copy of the rule: every edit emits a new ScheduleRule and the parent keeps the
 * single list. That is deliberate - the control this replaced kept the same times in a FormGroup,
 * in an editedValue array and in two nested ControlValueAccessors, and the three went out of sync.
 */
@Component({
    selector: "gebo-ai-schedule-rule-card",
    templateUrl: "schedule-rule-card.component.html",
    standalone: false
})
export class GeboAIScheduleRuleCardComponent implements OnChanges {

    /** The rule this card shows. */
    @Input({ required: true }) rule!: ScheduleRule;

    /** The language used to render day names, clock times and dates. */
    @Input() locale: string = "en";

    /** Whether the editor is open. */
    @Input() expanded: boolean = false;

    /** Emits the rule as edited. */
    @Output() ruleChange: EventEmitter<ScheduleRule> = new EventEmitter();

    /** Asks the parent to open or close this card. */
    @Output() expandedChange: EventEmitter<boolean> = new EventEmitter();

    /** Asks the parent to drop this rule. */
    @Output() remove: EventEmitter<void> = new EventEmitter();

    /** Asks the parent to add a copy of this rule. */
    @Output() duplicate: EventEmitter<void> = new EventEmitter();

    /** The frequencies the kind switcher offers. */
    public readonly kinds: SchedulableFrequency[] = SCHEDULABLE_FREQUENCIES;

    /** Quick choices for the minute of the hour of an hourly rule. */
    public readonly quickMinutes: number[] = [0, 15, 30, 45];

    /** Short weekday names of the current language, Monday first. */
    public dayNames: string[] = [];

    /** Long weekday names, used for the day toggles' accessible names. */
    public dayNamesLong: string[] = [];

    /**
     * The times and dates as the pickers bind to them.
     *
     * They are kept here rather than converted inside the template: a Date built in a binding is
     * a new reference on every change detection pass, which has the picker write the value back
     * into its own input again and again, under the cursor of whoever is typing in it.
     */
    public timeValues: Date[] = [];

    /** The absolute dates as the pickers bind to them, for the same reason. */
    public dateValues: Date[] = [];

    /** Why the rule is not saveable yet, when it is not. */
    public problem?: RuleProblem;

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["rule"] || changes["locale"]) {
            this.refresh();
        }
    }

    /** Recomputes everything the template renders from the rule. */
    private refresh(): void {
        this.dayNames = weekdayNames(this.locale, "short");
        this.dayNamesLong = weekdayNames(this.locale, "long");
        this.problem = ruleProblem(this.rule);
        // a value that did not change keeps the very Date object the picker already holds, so
        // typing in the input is not interrupted by a write-back of what was just typed
        this.timeValues = (this.rule.times || []).map((t, i) => {
            const held = this.timeValues[i];
            if (held && held.getHours() === t.hour && held.getMinutes() === t.minute) {
                return held;
            }
            const d = new Date();
            d.setHours(t.hour, t.minute, 0, 0);
            return d;
        });
        this.dateValues = (this.rule.dates || []).map((ms, i) => {
            const held = this.dateValues[i];
            return held && held.getTime() === ms ? held : new Date(ms);
        });
    }

    /** The icon that stands for the rule's frequency. */
    public get kindIcon(): string {
        switch (this.rule.kind) {
            case "HOURLY": return "pi-stopwatch";
            case "DAILY": return "pi-sun";
            case "WEEKLY": return "pi-calendar";
            case "DATES": return "pi-calendar-clock";
        }
    }

    /** Opens or closes the editor. */
    public toggle(): void {
        this.expandedChange.emit(!this.expanded);
    }

    /** Emits an edited copy of the rule. */
    private emit(changed: Partial<ScheduleRule>): void {
        this.ruleChange.emit(Object.assign({}, this.rule, changed));
    }

    /** Re-types the rule, keeping what the new frequency can still use. */
    public chooseKind(kind: SchedulableFrequency): void {
        if (kind !== this.rule.kind) {
            this.ruleChange.emit(changeRuleKind(this.rule, kind));
        }
    }

    /** True when the day is part of a weekly rule. */
    public hasDay(day: number): boolean {
        return !!this.rule.days && this.rule.days.indexOf(day) >= 0;
    }

    /** Adds or removes a day of a weekly rule. */
    public toggleDay(day: number): void {
        const days: number[] = this.rule.days ? this.rule.days.slice() : [];
        const at = days.indexOf(day);
        if (at >= 0) {
            days.splice(at, 1);
        } else {
            days.push(day);
        }
        this.emit({ days: days.sort((a, b) => a - b) });
    }

    /** Selects the five working days in one go. */
    public selectWeekdays(): void {
        this.emit({ days: [0, 1, 2, 3, 4] });
    }

    /** Selects all seven days. */
    public selectEveryDay(): void {
        this.emit({ days: [0, 1, 2, 3, 4, 5, 6] });
    }

    /** Writes back a time the user picked. */
    public setTime(index: number, value: Date | null): void {
        if (!value) {
            return;
        }
        const times: HourMinute[] = (this.rule.times || []).slice();
        times[index] = { hour: value.getHours(), minute: value.getMinutes() };
        this.emit({ times: times });
    }

    /** Adds a time of day, an hour after the last one so it never duplicates it. */
    public addTime(): void {
        const times: HourMinute[] = (this.rule.times || []).slice();
        const last: HourMinute | undefined = times.length ? times[times.length - 1] : undefined;
        times.push(last ? { hour: (last.hour + 1) % 24, minute: last.minute } : { hour: 2, minute: 0 });
        this.emit({ times: times });
    }

    /** Drops a time of day. */
    public removeTime(index: number): void {
        const times: HourMinute[] = (this.rule.times || []).slice();
        times.splice(index, 1);
        this.emit({ times: times });
    }

    /** A quick minute choice as it is labelled, for instance ":05". */
    public minuteLabel(minute: number): string {
        return formatMinuteOfHour(minute);
    }

    /** Sets the minute of the hour of an hourly rule. */
    public setMinute(minute: number | null): void {
        if (minute === null || minute === undefined) {
            return;
        }
        this.emit({ minute: Math.max(0, Math.min(59, Math.round(minute))) });
    }

    /** Writes back a date the user picked. */
    public setDate(index: number, value: Date | null): void {
        if (!value) {
            return;
        }
        const dates: number[] = (this.rule.dates || []).slice();
        dates[index] = value.getTime();
        this.emit({ dates: dates });
    }

    /** Adds another absolute date. */
    public addDate(): void {
        const dates: number[] = (this.rule.dates || []).slice();
        const last: number | undefined = dates.length ? dates[dates.length - 1] : undefined;
        dates.push(last ? last + 24 * 60 * 60 * 1000 : tomorrowAt(2, 0));
        this.emit({ dates: dates });
    }

    /** Drops an absolute date. */
    public removeDate(index: number): void {
        const dates: number[] = (this.rule.dates || []).slice();
        dates.splice(index, 1);
        this.emit({ dates: dates });
    }

    /** True when the date has already gone by and will therefore never fire. */
    public isPast(epochMillis: number): boolean {
        return epochMillis < new Date().getTime();
    }

    /** The accessible name of a time chip. */
    public timeLabel(time: HourMinute): string {
        return formatHourMinute(time, this.locale);
    }

    /** The accessible name of a date chip. */
    public dateLabel(epochMillis: number): string {
        return formatDateTime(epochMillis, this.locale);
    }
}
