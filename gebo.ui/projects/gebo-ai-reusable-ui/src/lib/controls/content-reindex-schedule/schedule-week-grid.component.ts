/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { formatHourLabel, weekdayNames } from "./schedule-format";

/** One cell of the week grid. */
interface GridCell {
    hour: number;
    /** The schedule checks this source during this hour. */
    on: boolean;
    /** The rule being edited is the one that covers this hour. */
    hot: boolean;
}

/** One row of the week grid. */
interface GridRow {
    name: string;
    cells: GridCell[];
}

/**
 * The week at a glance: seven rows of twenty four hours, lit where the schedule runs a check.
 *
 * It reads the schedule rather than editing it. One cell of "Monday and Thursday at 06:30" cannot
 * be switched off on its own without splitting the rule in two, so making the cells clickable
 * would promise something the rule model cannot keep: the rule cards stay the way in.
 */
@Component({
    selector: "gebo-ai-schedule-week-grid",
    templateUrl: "schedule-week-grid.component.html",
    standalone: false
})
export class GeboAIScheduleWeekGridComponent implements OnChanges {

    /** Every hour the schedule touches, as day * 24 + hour. */
    @Input() cells: number[] = [];

    /** The hours covered by the rule currently open, drawn stronger than the others. */
    @Input() highlight: number[] = [];

    /** The language the day names are rendered in. */
    @Input() locale: string = "en";

    /** The grid as the template draws it. */
    public rows: GridRow[] = [];

    /** The hours that get a printed label under the grid. */
    public readonly ticks: number[] = [0, 3, 6, 9, 12, 15, 18, 21];

    /** The twenty four hours of the day, for the axis row. */
    public readonly hours: number[] = Array.from({ length: 24 }, (unused, i) => i);

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["cells"] || changes["highlight"] || changes["locale"]) {
            this.build();
        }
    }

    /** The label printed under a tick. */
    public tickLabel(hour: number): string {
        return formatHourLabel(hour);
    }

    /** Rebuilds the seven rows from the covered hours. */
    private build(): void {
        const names = weekdayNames(this.locale, "short");
        const on: Record<number, boolean> = {};
        (this.cells || []).forEach(c => on[c] = true);
        const hot: Record<number, boolean> = {};
        (this.highlight || []).forEach(c => hot[c] = true);

        const rows: GridRow[] = [];
        for (let day = 0; day < 7; day++) {
            const cells: GridCell[] = [];
            for (let hour = 0; hour < 24; hour++) {
                const index = day * 24 + hour;
                cells.push({ hour: hour, on: on[index] === true, hot: hot[index] === true });
            }
            rows.push({ name: names[day], cells: cells });
        }
        this.rows = rows;
    }
}
