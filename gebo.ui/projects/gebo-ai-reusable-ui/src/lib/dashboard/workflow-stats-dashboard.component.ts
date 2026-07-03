/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Directive, OnInit } from "@angular/core";
import {
  WorkflowStatsDrillDownLevel,
  WorkflowStatsDrillDownResult,
  WorkflowStatsAggregationBucket
} from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";

@Directive()
export abstract class BaseWorkflowStatsDashboardComponent implements OnInit {
  loading: boolean = false;
  result?: WorkflowStatsDrillDownResult;
  hasNoStats: boolean = false;

  filterTab1: WorkflowStatsDrillDownLevel = {};
  filterTab2: WorkflowStatsDrillDownLevel = {};

  // Chart data
  documentsChartDataTab1: any;
  throughputChartDataTab1: any;
  documentsChartDataTab2: any;
  throughputChartDataTab2: any;

  chartOptions = {
    plugins: {
      legend: {
        labels: {
          color: "#495057"
        }
      },
      tooltip: {
        mode: "index",
        intersect: false
      }
    },
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        ticks: {
          color: "#495057"
        },
        grid: {
          color: "#ebedef"
        }
      },
      y: {
        beginAtZero: true,
        ticks: {
          color: "#495057"
        },
        grid: {
          color: "#ebedef"
        }
      }
    }
  };

  ngOnInit(): void {
    this.loadData({});
  }

  abstract executeDrillDown(filter: WorkflowStatsDrillDownLevel): Observable<WorkflowStatsDrillDownResult>;

  loadData(filter: WorkflowStatsDrillDownLevel): void {
    this.loading = true;
    this.executeDrillDown(filter).subscribe({
      next: (res) => {
        this.result = res;
        this.updateCharts();

        const dailyEmpty = !res.currentMonthDaily || res.currentMonthDaily.length === 0;
        const monthlyEmpty = !res.monthly || res.monthly.length === 0;

        if (Object.keys(filter).length === 0) {
          this.hasNoStats = dailyEmpty && monthlyEmpty;
        }

        this.loading = false;
      },
      error: (err) => {
        console.error("Error loading workflow stats data", err);
        this.loading = false;
      }
    });
  }

  applyFilter(tabIndex: number): void {
    if (tabIndex === 1) {
      this.loadData(this.filterTab1);
    } else {
      this.loadData(this.filterTab2);
    }
  }

  resetFilter(tabIndex: number): void {
    if (tabIndex === 1) {
      this.filterTab1 = {};
      this.loadData({});
    } else {
      this.filterTab2 = {};
      this.loadData({});
    }
  }

  /** Scalar sub-dimension options (workflow type/id/step, year, month). */
  getOptions(arr?: Array<any>): { label: string, value: any }[] {
    const options: { label: string, value: any }[] = [{ label: "All", value: undefined }];
    if (arr) {
      arr.forEach(val => {
        options.push({ label: String(val), value: val });
      });
    }
    return options;
  }

  /** Reference sub-dimension options (knowledge base, project, endpoint): the
   *  label is the human readable description/code, the value is the whole
   *  reference (matched server-side on className+code). */
  getRefOptions(arr?: Array<any>): { label: string, value: any }[] {
    const options: { label: string, value: any }[] = [{ label: "All", value: undefined }];
    if (arr) {
      arr.forEach(ref => {
        options.push({ label: ref?.description || ref?.code || String(ref), value: ref });
      });
    }
    return options;
  }

  private updateCharts(): void {
    if (!this.result) return;

    const currentMonthDaily = this.result.currentMonthDaily || [];
    const monthly = this.result.monthly || [];

    // Tab 1 (This month - Daily)
    this.documentsChartDataTab1 = this.buildDocumentsChartData(currentMonthDaily, true);
    this.throughputChartDataTab1 = this.buildThroughputChartData(currentMonthDaily, true);

    // Tab 2 (Monthly)
    this.documentsChartDataTab2 = this.buildDocumentsChartData(monthly, false);
    this.throughputChartDataTab2 = this.buildThroughputChartData(monthly, false);
  }

  private buildDocumentsChartData(buckets: WorkflowStatsAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    return {
      labels: labels,
      datasets: [
        {
          label: "Input",
          data: buckets.map(b => b.batchDocumentsInput || 0),
          backgroundColor: "rgba(66, 165, 245, 0.75)",
          borderColor: "#1E88E5",
          borderWidth: 1.5
        },
        {
          label: "Processed",
          data: buckets.map(b => b.batchDocumentsProcessed || 0),
          backgroundColor: "rgba(102, 187, 106, 0.75)",
          borderColor: "#43A047",
          borderWidth: 1.5
        },
        {
          label: "Sent to next step",
          data: buckets.map(b => b.batchSentToNextStep || 0),
          backgroundColor: "rgba(38, 166, 154, 0.75)",
          borderColor: "#00897B",
          borderWidth: 1.5
        },
        {
          label: "Discarded",
          data: buckets.map(b => b.batchDiscardedInput || 0),
          backgroundColor: "rgba(255, 167, 38, 0.75)",
          borderColor: "#FB8C00",
          borderWidth: 1.5
        },
        {
          label: "Errors",
          data: buckets.map(b => b.batchDocumentsProcessingErrors || 0),
          backgroundColor: "rgba(239, 83, 80, 0.75)",
          borderColor: "#E53935",
          borderWidth: 1.5
        }
      ]
    };
  }

  private buildThroughputChartData(buckets: WorkflowStatsAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    return {
      labels: labels,
      datasets: [
        {
          label: "Chunks",
          data: buckets.map(b => b.chunksProcessed || 0),
          backgroundColor: "rgba(126, 87, 194, 0.75)",
          borderColor: "#5E35B1",
          borderWidth: 1.5
        },
        {
          label: "Tokens (k)",
          data: buckets.map(b => (b.tokensProcessed || 0) / 1000),
          backgroundColor: "rgba(255, 167, 38, 0.75)",
          borderColor: "#FB8C00",
          borderWidth: 1.5
        }
      ]
    };
  }

  private formatTimeLabel(bucket: WorkflowStatsAggregationBucket, isDaily: boolean): string {
    if (isDaily) {
      const m = bucket.month ? String(bucket.month).padStart(2, "0") : "00";
      const d = bucket.day ? String(bucket.day).padStart(2, "0") : "00";
      return `${m}/${d}`;
    } else {
      const y = bucket.year ? String(bucket.year) : "0000";
      const m = bucket.month ? String(bucket.month).padStart(2, "0") : "00";
      return `${y}/${m}`;
    }
  }
}
