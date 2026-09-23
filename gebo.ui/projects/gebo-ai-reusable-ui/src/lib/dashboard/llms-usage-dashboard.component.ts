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
  LLMUsageDrillDownLevel, 
  LLMUsageDrillDownResult, 
  LLMUsageAggregationBucket 
} from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";

@Directive()
export abstract class BaseLLMSUsageDashboardComponent implements OnInit {
  loading: boolean = false;
  result?: LLMUsageDrillDownResult;
  hasNoStats: boolean = false;

  filterTab1: LLMUsageDrillDownLevel = {};
  filterTab2: LLMUsageDrillDownLevel = {};

  // Chart data
  tokenChartDataTab1: any;
  latencyChartDataTab1: any;
  tokenChartDataTab2: any;
  latencyChartDataTab2: any;

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

  abstract executeDrillDown(filter: LLMUsageDrillDownLevel): Observable<LLMUsageDrillDownResult>;

  loadData(filter: LLMUsageDrillDownLevel): void {
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
        console.error("Error loading LLM usage data", err);
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

  getOptions(arr?: Array<any>): { label: string, value: any }[] {
    const options: { label: string, value: any }[] = [{ label: "All", value: undefined }];
    if (arr) {
      arr.forEach(val => {
        options.push({ label: String(val), value: val });
      });
    }
    return options;
  }

  private updateCharts(): void {
    if (!this.result) return;

    const currentMonthDaily = this.result.currentMonthDaily || [];
    const monthly = this.result.monthly || [];

    // Tab 1 (This month usage - Daily)
    this.tokenChartDataTab1 = this.buildTokenChartData(currentMonthDaily, true);
    this.latencyChartDataTab1 = this.buildLatencyChartData(currentMonthDaily, true);

    // Tab 2 (Monthly usage)
    this.tokenChartDataTab2 = this.buildTokenChartData(monthly, false);
    this.latencyChartDataTab2 = this.buildLatencyChartData(monthly, false);
  }

  private buildTokenChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    const inputData = buckets.map(b => (b.inputToken || 0) / 1000);
    const outputData = buckets.map(b => (b.outputToken || 0) / 1000);

    return {
      labels: labels,
      datasets: [
        {
          label: "Input Tokens (k)",
          data: inputData,
          backgroundColor: "rgba(66, 165, 245, 0.75)",
          borderColor: "#1E88E5",
          borderWidth: 1.5
        },
        {
          label: "Output Tokens (k)",
          data: outputData,
          backgroundColor: "rgba(255, 167, 38, 0.75)",
          borderColor: "#FB8C00",
          borderWidth: 1.5
        }
      ]
    };
  }

  private buildLatencyChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    const minData = buckets.map(b => (b.latencyMin || 0) / 1000);
    const avgData = buckets.map(b => (b.latencyAvg || 0) / 1000);
    const maxData = buckets.map(b => (b.latencyMax || 0) / 1000);

    return {
      labels: labels,
      datasets: [
        {
          label: "Min Latency (s)",
          data: minData,
          backgroundColor: "rgba(102, 187, 106, 0.75)",
          borderColor: "#43A047",
          borderWidth: 1.5
        },
        {
          label: "Avg Latency (s)",
          data: avgData,
          backgroundColor: "rgba(38, 166, 154, 0.75)",
          borderColor: "#00897B",
          borderWidth: 1.5
        },
        {
          label: "Max Latency (s)",
          data: maxData,
          backgroundColor: "rgba(239, 83, 80, 0.75)",
          borderColor: "#E53935",
          borderWidth: 1.5
        }
      ]
    };
  }

  private formatTimeLabel(bucket: LLMUsageAggregationBucket, isDaily: boolean): string {
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
