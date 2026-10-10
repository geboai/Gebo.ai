/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { ReindexingProgrammedTable, ReindexingTime } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * The scheduling rules the UI can express. The backend ReindexingFrequency enum also carries
 * MONTHLY, YEARLY and ON_CHANGES: MONTHLY is rejected here because TimesCalculatorService leaves
 * "case WEEK_OF_MONTH" empty (a monthly table would never fire), while YEARLY and ON_CHANGES have
 * no ReindexTimeStructureMetaInfo at all, so the engine cannot resolve their slot layout.
 * Tables of those frequencies found on an endpoint are carried through untouched (see splitTables).
 */
export type SchedulableFrequency = "HOURLY" | "DAILY" | "WEEKLY" | "DATES";

/** The frequencies offered when adding a rule, in the order they are presented. */
export const SCHEDULABLE_FREQUENCIES: SchedulableFrequency[] = ["HOURLY", "DAILY", "WEEKLY", "DATES"];

/**
 * Number of slots each frequency's timeComponent array carries, mirroring
 * ReindexTimeStructureMetaInfo on the backend:
 * HOURLY [minute], DAILY [hour, minute], WEEKLY [dayOfWeek, hour, minute], DATES [epochMillis].
 */
const SLOT_COUNT: Record<SchedulableFrequency, number> = { HOURLY: 1, DAILY: 2, WEEKLY: 3, DATES: 1 };

/** A wall clock time, resolved by the backend in the server's own time zone. */
export interface HourMinute {
    hour: number;
    minute: number;
}

/**
 * One scheduling rule as the user states it: "every weekday at 06:30" is a single rule, even
 * though it expands to five ReindexingTime rows. This view model never leaves the browser.
 */
export interface ScheduleRule {
    /** Client side identity, used for list tracking only. */
    id: string;
    kind: SchedulableFrequency;
    /** HOURLY: the minute of every hour the check runs at. */
    minute?: number;
    /** WEEKLY: days of the week, 0 = Monday ... 6 = Sunday (the backend DAY_OF_WEEK_LIST order). */
    days?: number[];
    /** DAILY and WEEKLY: the times of day the check runs at. */
    times?: HourMinute[];
    /** DATES: absolute instants, epoch milliseconds. */
    dates?: number[];
}

/** Why a rule cannot be saved yet. */
export type RuleProblem = "NO_TIMES" | "NO_DAYS" | "NO_DATES";

/** A non blocking remark about the schedule as a whole. */
export interface ScheduleRemark {
    code: "HIGH_FREQUENCY" | "OVERLAP" | "PAST_DATES";
    /** Checks per day for HIGH_FREQUENCY, number of affected entries otherwise. */
    amount: number;
}

/** The ready made schedules offered above the rule list. */
export type SchedulePreset = "HOURLY" | "NIGHTLY" | "TWICE_DAILY" | "WEEKDAYS" | "WEEKLY";

/** The presets offered, in presentation order. */
export const SCHEDULE_PRESETS: SchedulePreset[] = ["HOURLY", "NIGHTLY", "TWICE_DAILY", "WEEKDAYS", "WEEKLY"];

let idCounter: number = 0;

/** Returns a fresh client side rule identity. */
function nextId(): string {
    idCounter = idCounter + 1;
    return "rule-" + idCounter;
}

/** True when the frequency is one the editor knows how to express. */
export function isSchedulable(frequency?: ReindexingProgrammedTable.FrequencyEnum): boolean {
    return !!frequency && (SCHEDULABLE_FREQUENCIES as string[]).indexOf(frequency) >= 0;
}

/**
 * Separates the tables the editor owns from the ones it has to leave alone, so that a MONTHLY or
 * ON_CHANGES table already stored on an endpoint survives a round trip through this control
 * instead of being silently dropped on save.
 */
export function splitTables(tables?: ReindexingProgrammedTable[]): {
    editable: ReindexingProgrammedTable[],
    passthrough: ReindexingProgrammedTable[]
} {
    const editable: ReindexingProgrammedTable[] = [];
    const passthrough: ReindexingProgrammedTable[] = [];
    (tables || []).forEach(table => {
        if (isSchedulable(table.frequency)) {
            editable.push(table);
        } else {
            passthrough.push(table);
        }
    });
    return { editable: editable, passthrough: passthrough };
}

/** Orders times of day ascending. */
function compareTimes(a: HourMinute, b: HourMinute): number {
    return a.hour !== b.hour ? a.hour - b.hour : a.minute - b.minute;
}

/** The canonical text of a time of day, used as a grouping key. */
function timeKey(t: HourMinute): string {
    return t.hour + ":" + t.minute;
}

/** Sorts and de-duplicates a list of times of day. */
function normalizeTimes(times?: HourMinute[]): HourMinute[] {
    const seen: Record<string, boolean> = {};
    const out: HourMinute[] = [];
    (times || []).forEach(t => {
        const key = timeKey(t);
        if (!seen[key]) {
            seen[key] = true;
            out.push({ hour: t.hour, minute: t.minute });
        }
    });
    return out.sort(compareTimes);
}

/** Sorts and de-duplicates a list of day indexes. */
function normalizeDays(days?: number[]): number[] {
    const out: number[] = [];
    (days || []).forEach(d => {
        if (d >= 0 && d <= 6 && out.indexOf(d) < 0) {
            out.push(d);
        }
    });
    return out.sort((a, b) => a - b);
}

/** The key under which a stored row's createdTime is looked up. */
function rowKey(frequency: string, timeComponent: number[]): string {
    return frequency + "|" + timeComponent.join(",");
}

/**
 * Indexes the createdTime of every row of the previously stored tables.
 *
 * createdTime is not decoration: TimesCalculatorService.readNearerPast uses it as the lower bound
 * of a missed occurrence, and AbstractCentralSchedulingService turns that into a catch-up run.
 * Re-stamping it on every save would quietly tell the engine that a schedule is brand new and
 * therefore never late, so rows that survive an edit must keep the timestamp they were born with.
 */
function indexCreatedTimes(previous?: ReindexingProgrammedTable[]): Record<string, number> {
    const index: Record<string, number> = {};
    (previous || []).forEach(table => {
        (table.times || []).forEach(time => {
            if (time.timeComponent && time.createdTime !== undefined && time.createdTime !== null) {
                index[rowKey(table.frequency, time.timeComponent)] = time.createdTime;
            }
        });
    });
    return index;
}

/**
 * Expands the rules into the flat ReindexingProgrammedTable list the backend persists: one table
 * per frequency, one ReindexingTime row per concrete occurrence.
 *
 * @param rules the rules as edited
 * @param previous the tables currently stored, used to carry createdTime over
 * @param passthrough tables of frequencies this editor does not own, appended unchanged
 */
export function rulesToTables(rules: ScheduleRule[], previous?: ReindexingProgrammedTable[],
    passthrough?: ReindexingProgrammedTable[]): ReindexingProgrammedTable[] {
    const createdTimes: Record<string, number> = indexCreatedTimes(previous);
    const now: number = new Date().getTime();
    const rowsByFrequency: Record<string, ReindexingTime[]> = {};
    const emitted: Record<string, boolean> = {};

    const emit = (frequency: SchedulableFrequency, timeComponent: number[]) => {
        const key = rowKey(frequency, timeComponent);
        if (emitted[key]) {
            return;
        }
        emitted[key] = true;
        if (!rowsByFrequency[frequency]) {
            rowsByFrequency[frequency] = [];
        }
        rowsByFrequency[frequency].push({
            createdTime: createdTimes[key] !== undefined ? createdTimes[key] : now,
            timeComponent: timeComponent
        });
    };

    rules.forEach(rule => {
        switch (rule.kind) {
            case "HOURLY": {
                if (rule.minute !== undefined && rule.minute !== null) {
                    emit("HOURLY", [rule.minute]);
                }
            } break;
            case "DAILY": {
                normalizeTimes(rule.times).forEach(t => emit("DAILY", [t.hour, t.minute]));
            } break;
            case "WEEKLY": {
                const days = normalizeDays(rule.days);
                const times = normalizeTimes(rule.times);
                days.forEach(day => times.forEach(t => emit("WEEKLY", [day, t.hour, t.minute])));
            } break;
            case "DATES": {
                (rule.dates || []).forEach(d => emit("DATES", [d]));
            } break;
        }
    });

    const out: ReindexingProgrammedTable[] = [];
    SCHEDULABLE_FREQUENCIES.forEach(frequency => {
        const rows = rowsByFrequency[frequency];
        if (rows && rows.length) {
            out.push({ frequency: frequency as ReindexingProgrammedTable.FrequencyEnum, times: rows });
        }
    });
    (passthrough || []).forEach(table => out.push(table));
    return out;
}

/** True when a stored row carries exactly the slots its frequency declares. */
function hasExpectedSlots(kind: SchedulableFrequency, time: ReindexingTime): boolean {
    return !!time.timeComponent && time.timeComponent.length === SLOT_COUNT[kind];
}

/**
 * Collapses the stored tables back into editable rules.
 *
 * The grouping is not persisted, so it is re-derived: weekly rows are gathered by the set of times
 * they share, which keeps the same firing instants while possibly presenting them as a different
 * set of cards than the one originally typed.
 */
export function tablesToRules(tables?: ReindexingProgrammedTable[]): ScheduleRule[] {
    const out: ScheduleRule[] = [];
    const editable = splitTables(tables).editable;

    editable.forEach(table => {
        const kind = table.frequency as SchedulableFrequency;
        const rows = (table.times || []).filter(t => hasExpectedSlots(kind, t));
        if (!rows.length) {
            return;
        }
        switch (kind) {
            case "HOURLY": {
                const minutes: number[] = [];
                rows.forEach(r => {
                    const minute = r.timeComponent![0];
                    if (minutes.indexOf(minute) < 0) {
                        minutes.push(minute);
                    }
                });
                minutes.sort((a, b) => a - b).forEach(minute => out.push({ id: nextId(), kind: "HOURLY", minute: minute }));
            } break;
            case "DAILY": {
                const times: HourMinute[] = rows.map(r => ({ hour: r.timeComponent![0], minute: r.timeComponent![1] }));
                out.push({ id: nextId(), kind: "DAILY", times: normalizeTimes(times) });
            } break;
            case "WEEKLY": {
                // day -> the times that day fires at
                const timesByDay: Record<number, HourMinute[]> = {};
                rows.forEach(r => {
                    const day = r.timeComponent![0];
                    if (day < 0 || day > 6) {
                        return;
                    }
                    if (!timesByDay[day]) {
                        timesByDay[day] = [];
                    }
                    timesByDay[day].push({ hour: r.timeComponent![1], minute: r.timeComponent![2] });
                });
                // days sharing the very same set of times become one rule
                const groups: { signature: string, days: number[], times: HourMinute[] }[] = [];
                Object.keys(timesByDay).map(k => Number(k)).sort((a, b) => a - b).forEach(day => {
                    const times = normalizeTimes(timesByDay[day]);
                    const signature = times.map(timeKey).join("|");
                    const existing = groups.find(g => g.signature === signature);
                    if (existing) {
                        existing.days.push(day);
                    } else {
                        groups.push({ signature: signature, days: [day], times: times });
                    }
                });
                groups.forEach(g => out.push({ id: nextId(), kind: "WEEKLY", days: g.days, times: g.times }));
            } break;
            case "DATES": {
                const dates: number[] = [];
                rows.forEach(r => {
                    const value = r.timeComponent![0];
                    if (dates.indexOf(value) < 0) {
                        dates.push(value);
                    }
                });
                out.push({ id: nextId(), kind: "DATES", dates: dates.sort((a, b) => a - b) });
            } break;
        }
    });
    return out;
}

/** A new rule of the given kind, pre-filled with a sensible default so it is immediately valid. */
export function newRule(kind: SchedulableFrequency): ScheduleRule {
    switch (kind) {
        case "HOURLY": return { id: nextId(), kind: "HOURLY", minute: 0 };
        case "DAILY": return { id: nextId(), kind: "DAILY", times: [{ hour: 2, minute: 0 }] };
        case "WEEKLY": return { id: nextId(), kind: "WEEKLY", days: [0], times: [{ hour: 6, minute: 30 }] };
        case "DATES": return { id: nextId(), kind: "DATES", dates: [tomorrowAt(2, 0)] };
    }
}

/** A copy of the rule under a fresh identity. */
export function copyRule(rule: ScheduleRule): ScheduleRule {
    return {
        id: nextId(),
        kind: rule.kind,
        minute: rule.minute,
        days: rule.days ? rule.days.slice() : undefined,
        times: rule.times ? rule.times.map(t => ({ hour: t.hour, minute: t.minute })) : undefined,
        dates: rule.dates ? rule.dates.slice() : undefined
    };
}

/** Epoch milliseconds of tomorrow at the given local wall clock time. */
export function tomorrowAt(hour: number, minute: number): number {
    const d = new Date();
    d.setDate(d.getDate() + 1);
    d.setHours(hour, minute, 0, 0);
    return d.getTime();
}

/** Re-types a rule, keeping whatever the new kind can still use. */
export function changeRuleKind(rule: ScheduleRule, kind: SchedulableFrequency): ScheduleRule {
    if (rule.kind === kind) {
        return rule;
    }
    const fresh = newRule(kind);
    fresh.id = rule.id;
    const times = normalizeTimes(rule.times);
    switch (kind) {
        case "HOURLY": {
            fresh.minute = times.length ? times[0].minute : 0;
        } break;
        case "DAILY": {
            if (times.length) {
                fresh.times = times;
            } else if (rule.minute !== undefined) {
                fresh.times = [{ hour: 0, minute: rule.minute }];
            }
        } break;
        case "WEEKLY": {
            if (times.length) {
                fresh.times = times;
            } else if (rule.minute !== undefined) {
                fresh.times = [{ hour: 0, minute: rule.minute }];
            }
            const days = normalizeDays(rule.days);
            fresh.days = days.length ? days : [0];
        } break;
    }
    return fresh;
}

/** The reason the rule cannot be saved, or undefined when it is complete. */
export function ruleProblem(rule: ScheduleRule): RuleProblem | undefined {
    switch (rule.kind) {
        case "HOURLY": return rule.minute === undefined || rule.minute === null ? "NO_TIMES" : undefined;
        case "DAILY": return !rule.times || !rule.times.length ? "NO_TIMES" : undefined;
        case "WEEKLY": {
            if (!rule.days || !rule.days.length) return "NO_DAYS";
            if (!rule.times || !rule.times.length) return "NO_TIMES";
            return undefined;
        }
        case "DATES": return !rule.dates || !rule.dates.length ? "NO_DATES" : undefined;
    }
}

/** True when every rule carries enough information to be persisted. */
export function rulesAreComplete(rules: ScheduleRule[]): boolean {
    return rules.every(r => ruleProblem(r) === undefined);
}

/** Every weekday and hour a rule touches, as day * 24 + hour. */
function ruleCells(rule: ScheduleRule): number[] {
    const cells: number[] = [];
    switch (rule.kind) {
        case "HOURLY": {
            for (let day = 0; day < 7; day++) {
                for (let hour = 0; hour < 24; hour++) {
                    cells.push(day * 24 + hour);
                }
            }
        } break;
        case "DAILY": {
            normalizeTimes(rule.times).forEach(t => {
                for (let day = 0; day < 7; day++) {
                    cells.push(day * 24 + t.hour);
                }
            });
        } break;
        case "WEEKLY": {
            normalizeDays(rule.days).forEach(day => {
                normalizeTimes(rule.times).forEach(t => cells.push(day * 24 + t.hour));
            });
        } break;
        case "DATES": {
            (rule.dates || []).forEach(ms => {
                const d = new Date(ms);
                // Date.getDay() is Sunday based, the backend day list is Monday based
                const day = (d.getDay() + 6) % 7;
                cells.push(day * 24 + d.getHours());
            });
        } break;
    }
    return cells;
}

/** How many times a recurring rule fires in a week; absolute dates do not recur. */
function ruleChecksPerWeek(rule: ScheduleRule): number {
    switch (rule.kind) {
        case "HOURLY": return 24 * 7;
        case "DAILY": return normalizeTimes(rule.times).length * 7;
        case "WEEKLY": return normalizeDays(rule.days).length * normalizeTimes(rule.times).length;
        case "DATES": return 0;
    }
}

/** What the week grid and the hour rail draw. */
export interface SchedulePicture {
    /** Touched cells, as day * 24 + hour. */
    cells: number[];
    /** Touched hours of the day, 0..23. */
    hours: number[];
    /** Recurring checks per week across every rule. */
    checksPerWeek: number;
}

/** Projects the rules onto the week grid and the hour rail. */
export function pictureOf(rules: ScheduleRule[]): SchedulePicture {
    const cells: number[] = [];
    const hours: number[] = [];
    let checksPerWeek: number = 0;
    rules.forEach(rule => {
        checksPerWeek = checksPerWeek + ruleChecksPerWeek(rule);
        ruleCells(rule).forEach(cell => {
            if (cells.indexOf(cell) < 0) {
                cells.push(cell);
            }
            const hour = cell % 24;
            if (hours.indexOf(hour) < 0) {
                hours.push(hour);
            }
        });
    });
    return { cells: cells, hours: hours.sort((a, b) => a - b), checksPerWeek: checksPerWeek };
}

/** Every concrete weekly instant a recurring rule fires at, as "day:hour:minute". */
function recurringInstants(rule: ScheduleRule): string[] {
    const out: string[] = [];
    const times = normalizeTimes(rule.times);
    switch (rule.kind) {
        case "HOURLY": {
            for (let day = 0; day < 7; day++) {
                for (let hour = 0; hour < 24; hour++) {
                    out.push(day + ":" + hour + ":" + rule.minute);
                }
            }
        } break;
        case "DAILY": {
            for (let day = 0; day < 7; day++) {
                times.forEach(t => out.push(day + ":" + t.hour + ":" + t.minute));
            }
        } break;
        case "WEEKLY": {
            normalizeDays(rule.days).forEach(day => times.forEach(t => out.push(day + ":" + t.hour + ":" + t.minute)));
        } break;
    }
    return out;
}

/** Non blocking remarks about the schedule: density, duplicated instants, dates already gone by. */
export function scheduleRemarks(rules: ScheduleRule[]): ScheduleRemark[] {
    const out: ScheduleRemark[] = [];
    const picture = pictureOf(rules);
    const perDay = Math.round(picture.checksPerWeek / 7);
    if (perDay >= 12) {
        out.push({ code: "HIGH_FREQUENCY", amount: perDay });
    }
    const seen: Record<string, boolean> = {};
    let overlaps: number = 0;
    rules.forEach(rule => recurringInstants(rule).forEach(instant => {
        if (seen[instant]) {
            overlaps = overlaps + 1;
        } else {
            seen[instant] = true;
        }
    }));
    if (overlaps > 0) {
        out.push({ code: "OVERLAP", amount: overlaps });
    }
    const now = new Date().getTime();
    let past: number = 0;
    rules.forEach(rule => (rule.dates || []).forEach(d => {
        if (d < now) {
            past = past + 1;
        }
    }));
    if (past > 0) {
        out.push({ code: "PAST_DATES", amount: past });
    }
    return out;
}

/** The rules a ready made preset stands for. */
export function presetRules(preset: SchedulePreset): ScheduleRule[] {
    switch (preset) {
        case "HOURLY": return [{ id: nextId(), kind: "HOURLY", minute: 0 }];
        case "NIGHTLY": return [{ id: nextId(), kind: "DAILY", times: [{ hour: 2, minute: 0 }] }];
        case "TWICE_DAILY": return [{ id: nextId(), kind: "DAILY", times: [{ hour: 2, minute: 0 }, { hour: 14, minute: 0 }] }];
        case "WEEKDAYS": return [{ id: nextId(), kind: "WEEKLY", days: [0, 1, 2, 3, 4], times: [{ hour: 6, minute: 30 }] }];
        case "WEEKLY": return [{ id: nextId(), kind: "WEEKLY", days: [0], times: [{ hour: 6, minute: 30 }] }];
    }
}

/** True when the rules are exactly what the preset stands for, so it can be shown as selected. */
export function matchesPreset(rules: ScheduleRule[], preset: SchedulePreset): boolean {
    const expected = presetRules(preset);
    if (rules.length !== expected.length) {
        return false;
    }
    const asTables = (list: ScheduleRule[]) => JSON.stringify(rulesToTables(list).map(t => ({
        frequency: t.frequency,
        times: (t.times || []).map(x => x.timeComponent)
    })));
    return asTables(rules) === asTables(expected);
}
