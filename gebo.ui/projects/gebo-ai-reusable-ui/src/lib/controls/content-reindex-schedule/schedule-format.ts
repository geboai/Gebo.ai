/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { HourMinute, ScheduleRule } from "./schedule-rules.model";

/**
 * Locale aware rendering of the scheduling data.
 *
 * Day names, clock times and dates are produced by Intl on the language the user picked, which
 * localizes them properly (and for free) instead of shipping seven day names per language through
 * the UI text resources. Only the structural words around them ("Repeat", "on", "at") go through
 * the gebo-ai-text / gebo-ai-label resources.
 */

/**
 * 1 January 2024 fell on a Monday: used as the anchor for naming weekdays, so that index 0 is
 * Monday exactly like the backend DAY_OF_WEEK_LIST.
 */
const MONDAY_ANCHOR_UTC_DAY: number = Date.UTC(2024, 0, 1);

/** One day in milliseconds. */
const DAY_MS: number = 24 * 60 * 60 * 1000;

/** The weekday names of the locale, Monday first. */
export function weekdayNames(locale: string, style: "short" | "long" | "narrow"): string[] {
    const out: string[] = [];
    try {
        const format = new Intl.DateTimeFormat(locale, { weekday: style, timeZone: "UTC" });
        for (let i = 0; i < 7; i++) {
            out.push(format.format(new Date(MONDAY_ANCHOR_UTC_DAY + i * DAY_MS)));
        }
    } catch (e) {
        const fallback = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
        for (let i = 0; i < 7; i++) {
            out.push(style === "long" ? fallback[i] : fallback[i].substring(0, style === "narrow" ? 1 : 3));
        }
    }
    return out;
}

/** A time of day on the locale's clock, for instance "06:30" or "6:30 AM". */
export function formatHourMinute(time: HourMinute, locale: string): string {
    try {
        const d = new Date(Date.UTC(2024, 0, 1, time.hour, time.minute));
        return new Intl.DateTimeFormat(locale, { hour: "2-digit", minute: "2-digit", timeZone: "UTC" }).format(d);
    } catch (e) {
        return pad(time.hour) + ":" + pad(time.minute);
    }
}

/** The minute of the hour an hourly check runs at, for instance ":05". */
export function formatMinuteOfHour(minute?: number): string {
    return ":" + pad(minute === undefined || minute === null ? 0 : minute);
}

/** An absolute instant on the locale's calendar and clock. */
export function formatDateTime(epochMillis: number, locale: string): string {
    try {
        return new Intl.DateTimeFormat(locale, {
            day: "2-digit", month: "short", year: "numeric", hour: "2-digit", minute: "2-digit"
        }).format(new Date(epochMillis));
    } catch (e) {
        return new Date(epochMillis).toISOString();
    }
}

/** An hour of the day as it labels the week grid and the hour rail, for instance "06". */
export function formatHourLabel(hour: number): string {
    return pad(hour);
}

/** Joins the parts the way the locale joins a list. */
export function formatList(parts: string[], locale: string): string {
    if (!parts.length) {
        return "";
    }
    const anyIntl: any = Intl as any;
    if (anyIntl && anyIntl.ListFormat) {
        try {
            return new anyIntl.ListFormat(locale, { style: "short", type: "conjunction" }).format(parts);
        } catch (e) {
            // falls through to the plain join
        }
    }
    return parts.join(", ");
}

/** Two digit padding, used wherever Intl is not involved. */
function pad(value: number): string {
    return (value < 10 ? "0" : "") + value;
}

/** The localized data words of a rule; the structural words live in the template. */
export interface RuleSummary {
    days: string[];
    times: string[];
    dates: string[];
    minute: string;
}

/** Renders the data of a rule for its card title and for the collapsed summary line. */
export function summarizeRule(rule: ScheduleRule, locale: string): RuleSummary {
    const names = weekdayNames(locale, "short");
    return {
        days: (rule.days || []).map(d => names[d]),
        times: (rule.times || []).map(t => formatHourMinute(t, locale)),
        dates: (rule.dates || []).map(d => formatDateTime(d, locale)),
        minute: formatMinuteOfHour(rule.minute)
    };
}

/** The time zone the server resolves wall clock schedules in cannot be known here, so the */
/** browser zone is named instead, with the wording in the template making the distinction. */
export function browserTimeZone(): string {
    try {
        return Intl.DateTimeFormat().resolvedOptions().timeZone || "";
    } catch (e) {
        return "";
    }
}
