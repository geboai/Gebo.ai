/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, forwardRef, OnDestroy, OnInit } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import { ReindexingProgrammedTable } from "@Gebo.ai/gebo-ai-rest-api";
import { Subscription } from "rxjs";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "../field-translation-container/gebo-translation.service";
import { browserTimeZone } from "./schedule-format";
import {
    SCHEDULE_PRESETS, SchedulePreset, ScheduleRemark, ScheduleRule,
    copyRule, matchesPreset, newRule, pictureOf, presetRules, rulesAreComplete, rulesToTables,
    scheduleRemarks, splitTables, tablesToRules
} from "./schedule-rules.model";

/**
 * Editor of the schedule on which a data source is re-checked for contents to ingest.
 *
 * The value exchanged with the host form stays exactly what the backend persists, a list of
 * ReindexingProgrammedTable: the rule list the user edits is a view model, mapped at this
 * boundary by rulesToTables / tablesToRules and never sent anywhere.
 *
 * Frequencies the engine cannot honour are not offered (see SchedulableFrequency), and tables of
 * those frequencies already stored on an endpoint are carried through untouched rather than being
 * dropped by a save made here.
 */
@Component({
    selector: "gebo-ai-content-reindex-scheduler-component",
    templateUrl: "content-reindex-schedule.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIContentReindexScheduleComponent),
            multi: true
        }, { provide: GEBO_AI_MODULE, useValue: "GeboAIContentReindexModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIContentReindexScheduleComponent"), multi: false
        }
    ],
    standalone: false
})
export class GeboAIContentReindexScheduleComponent implements OnInit, OnDestroy, ControlValueAccessor {

    /** Whether the editor dialog is open. */
    public mode: "DISPLAY" | "EDITING" = "DISPLAY";

    /** The saved schedule, as rules. */
    public rules: ScheduleRule[] = [];

    /** The rules being edited; discarded as a whole when the editor is cancelled. */
    public draftRules: ScheduleRule[] = [];

    /** The rule whose editor is open, if any. */
    public expandedRuleId?: string;

    /** Whether the host form disabled this control. */
    public disabled: boolean = false;

    /** The language day names, clock times and dates are rendered in. */
    public locale: string = "en";

    /** The browser time zone, named next to the absolute dates. */
    public readonly timeZone: string = browserTimeZone();

    /** The ready made schedules offered above the rule list. */
    public readonly presets: SchedulePreset[] = SCHEDULE_PRESETS;

    /** Hours of the day touched by the saved schedule, for the collapsed rail. */
    public litHours: number[] = [];

    /** The twenty four hours of the day, for the collapsed rail. */
    public readonly hours: number[] = Array.from({ length: 24 }, (unused, i) => i);

    /** Hours of the week touched by the rules being edited, for the week grid. */
    public editedCells: number[] = [];

    /** Hours of the week touched by the rule being edited, drawn stronger in the grid. */
    public highlightedCells: number[] = [];

    /** What is worth pointing out about the schedule being edited. */
    public remarks: ScheduleRemark[] = [];

    /**
     * The tables as last received or last emitted. Kept so that rows surviving an edit keep the
     * createdTime they were born with: the scheduling engine reads it to decide whether a run was
     * missed, so re-stamping it on save would quietly cancel the catch-up of a late schedule.
     */
    private storedTables: ReindexingProgrammedTable[] = [];

    /** Tables of frequencies this editor does not own, re-emitted unchanged. */
    private passthroughTables: ReindexingProgrammedTable[] = [];

    /** Subscription to the language chooser. */
    private languageSubscription?: Subscription;

    constructor(private translationService: GeboAITranslationService) {

    }

    async ngOnInit() {
        await this.translationService.tryInit();
        this.locale = this.translationService.actualLanguageCode;
        this.languageSubscription = this.translationService.languageChanges.subscribe({
            next: () => {
                this.locale = this.translationService.actualLanguageCode;
            }
        });
    }

    ngOnDestroy(): void {
        if (this.languageSubscription) {
            this.languageSubscription.unsubscribe();
        }
    }

    /** True when there is nothing scheduled at all. */
    public get unscheduled(): boolean {
        return !this.rules.length;
    }

    /** True when every rule being edited is complete enough to be saved. */
    public get canConfirm(): boolean {
        return rulesAreComplete(this.draftRules);
    }

    /** The preset the rules being edited correspond to, or undefined when they are bespoke. */
    public selectedPreset?: SchedulePreset;

    /** True when nothing is scheduled in the editor, which is the "never" choice. */
    public get draftIsEmpty(): boolean {
        return !this.draftRules.length;
    }

    /** Opens the editor on a copy of the saved rules. */
    public enterEdit(): void {
        if (this.disabled) {
            return;
        }
        this.draftRules = this.rules.map(r => cloneRule(r));
        this.expandedRuleId = this.draftRules.length === 1 ? this.draftRules[0].id : undefined;
        this.refreshDraft();
        this.mode = "EDITING";
    }

    /**
     * Closes the editor without keeping anything. The draft is a separate array, so there is no
     * residue left behind to be picked up by the next save.
     */
    public cancelEdit(): void {
        this.draftRules = [];
        this.expandedRuleId = undefined;
        this.remarks = [];
        this.mode = "DISPLAY";
    }

    /** Reacts to the dialog being dismissed by its own close control. */
    public onDialogVisibleChange(visible: boolean): void {
        if (visible === false) {
            this.cancelEdit();
        }
    }

    /** Keeps the edited rules and reports them to the host form. */
    public confirmEdit(): void {
        if (!this.canConfirm) {
            return;
        }
        this.rules = this.draftRules.map(r => cloneRule(r));
        const tables: ReindexingProgrammedTable[] = rulesToTables(this.rules, this.storedTables, this.passthroughTables);
        this.storedTables = tables;
        this.refreshDisplay();
        this.onTouched();
        this.onChange(tables);
        this.draftRules = [];
        this.expandedRuleId = undefined;
        this.mode = "DISPLAY";
    }

    /** Replaces the whole schedule with a ready made one. */
    public applyPreset(preset: SchedulePreset): void {
        this.draftRules = presetRules(preset);
        this.expandedRuleId = undefined;
        this.refreshDraft();
    }

    /** Clears the schedule, so the source is only re-checked when it is published by hand. */
    public applyNever(): void {
        this.draftRules = [];
        this.expandedRuleId = undefined;
        this.refreshDraft();
    }

    /** Appends a rule and opens it. */
    public addRule(): void {
        const rule = newRule("DAILY");
        this.draftRules = [...this.draftRules, rule];
        this.expandedRuleId = rule.id;
        this.refreshDraft();
    }

    /** Takes an edit coming from a rule card. */
    public updateRule(index: number, rule: ScheduleRule): void {
        const rules = this.draftRules.slice();
        rules[index] = rule;
        this.draftRules = rules;
        this.refreshDraft();
    }

    /** Drops a rule. */
    public removeRule(index: number): void {
        const rules = this.draftRules.slice();
        const [dropped] = rules.splice(index, 1);
        this.draftRules = rules;
        if (dropped && this.expandedRuleId === dropped.id) {
            this.expandedRuleId = undefined;
        }
        this.refreshDraft();
    }

    /** Adds a copy of a rule right after it. */
    public duplicateRule(index: number): void {
        const rules = this.draftRules.slice();
        const copy = copyRule(rules[index]);
        rules.splice(index + 1, 0, copy);
        this.draftRules = rules;
        this.expandedRuleId = copy.id;
        this.refreshDraft();
    }

    /** Opens one rule's editor at a time. */
    public setExpanded(rule: ScheduleRule, expanded: boolean): void {
        this.expandedRuleId = expanded ? rule.id : undefined;
        this.refreshHighlight();
    }

    /** True when this rule's editor is the open one. */
    public isExpanded(rule: ScheduleRule): boolean {
        return this.expandedRuleId === rule.id;
    }

    /** Recomputes the week grid, the remarks and the selected preset. */
    private refreshDraft(): void {
        this.editedCells = pictureOf(this.draftRules).cells;
        this.remarks = scheduleRemarks(this.draftRules);
        this.selectedPreset = this.draftRules.length
            ? SCHEDULE_PRESETS.find(p => matchesPreset(this.draftRules, p))
            : undefined;
        this.refreshHighlight();
    }

    /** Recomputes the hours the open rule covers. */
    private refreshHighlight(): void {
        const open = this.draftRules.find(r => r.id === this.expandedRuleId);
        this.highlightedCells = open ? pictureOf([open]).cells : [];
    }

    /** Recomputes what the collapsed summary draws. */
    private refreshDisplay(): void {
        this.litHours = pictureOf(this.rules).hours;
    }

    /** True when the hour carries at least one check, for the collapsed rail. */
    public isLit(hour: number): boolean {
        return this.litHours.indexOf(hour) >= 0;
    }

    /** True when the hour gets a printed label under the collapsed rail. */
    public isRailTick(hour: number): boolean {
        return hour % 6 === 0;
    }

    /** The label printed under the collapsed rail. */
    public railLabel(hour: number): string {
        return (hour < 10 ? "0" : "") + hour;
    }

    /**
     * ControlValueAccessor: takes the tables stored on the endpoint and derives the rules from
     * them, without reporting anything back to the host form.
     */
    writeValue(obj: any): void {
        const tables: ReindexingProgrammedTable[] = Array.isArray(obj) ? obj : [];
        const split = splitTables(tables);
        this.storedTables = tables;
        this.passthroughTables = split.passthrough;
        this.rules = tablesToRules(split.editable);
        this.refreshDisplay();
    }

    /** Reports a new value to the host form. */
    private onChange: (v: any) => void = () => { };

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /** Tells the host form the control was used. */
    private onTouched: () => void = () => { };

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    setDisabledState?(isDisabled: boolean): void {
        this.disabled = isDisabled;
        if (isDisabled && this.mode === "EDITING") {
            this.cancelEdit();
        }
    }
}

/** A deep copy of a rule that keeps its identity, so the open card stays open across an edit. */
function cloneRule(rule: ScheduleRule): ScheduleRule {
    return {
        id: rule.id,
        kind: rule.kind,
        minute: rule.minute,
        days: rule.days ? rule.days.slice() : undefined,
        times: rule.times ? rule.times.map(t => ({ hour: t.hour, minute: t.minute })) : undefined,
        dates: rule.dates ? rule.dates.slice() : undefined
    };
}
