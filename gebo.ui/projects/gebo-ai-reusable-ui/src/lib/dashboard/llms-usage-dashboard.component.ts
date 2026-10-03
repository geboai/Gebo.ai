/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Directive, inject, OnInit } from "@angular/core";
import { 
  LLMUsageDrillDownLevel, 
  LLMUsageDrillDownResult, 
  LLMUsageAggregationBucket 
} from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../controls/field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";

/** One filter of the data shown, as the active filters bar presents it. */
export interface LLMUsageActiveFilter {
  key: keyof LLMUsageDrillDownLevel;
  label: string;
  value: string;
}

/** The order the active filters are presented in, from the widest to the narrowest. */
const FILTER_KEYS: (keyof LLMUsageDrillDownLevel)[] = ["providerId", "modelTypeCode", "modelType", "model", "username",
  "callerStack", "year", "month"];
/** The filters the daily tab ignores: it always shows the current month. */
const PERIOD_KEYS: (keyof LLMUsageDrillDownLevel)[] = ["year", "month"];

@Directive()
export abstract class BaseLLMSUsageDashboardComponent implements OnInit {
  loading: boolean = false;
  result?: LLMUsageDrillDownResult;
  hasNoStats: boolean = false;

  filterTab1: LLMUsageDrillDownLevel = {};
  filterTab2: LLMUsageDrillDownLevel = {};
  /**
   * The filter of the data shown. The two tabs share one result, so it is the filter
   * of both, and both filter panels are brought back to it after every load.
   */
  appliedFilter: LLMUsageDrillDownLevel = {};
  /** The applied filters as the bar above the charts shows them, per tab. */
  activeFiltersTab1: LLMUsageActiveFilter[] = [];
  activeFiltersTab2: LLMUsageActiveFilter[] = [];

  private translationService = inject(GeboAITranslationService);
  private moduleId = inject(GEBO_AI_MODULE, { optional: true });
  private fieldHost = inject(GEBO_AI_FIELD_HOST, { optional: true });

  /**
   * The texts of the options and chart series built here rather than in the template,
   * by resource id; translated in the current language on init.
   */
  protected texts: { [id: string]: string } = {
    AllOption: "All",
    ChatModelTypeOption: "Chat",
    EmbeddingModelTypeOption: "Embedding",
    RankingModelTypeOption: "Ranking",
    ImageModelTypeOption: "Image generation",
    TextToSpeechModelTypeOption: "Text to speech",
    TranscriptionModelTypeOption: "Transcription",
    InputTokensSeries: "Input Tokens (k)",
    OutputTokensSeries: "Output Tokens (k)",
    MinResponseTimeSeries: "Min Response Time (s)",
    AvgResponseTimeSeries: "Avg Response Time (s)",
    MaxResponseTimeSeries: "Max Response Time (s)",
    CostSeries: "Cost",
    MinTimeToFirstTokenSeries: "Min Time to First Token (s)",
    AvgTimeToFirstTokenSeries: "Avg Time to First Token (s)",
    MaxTimeToFirstTokenSeries: "Max Time to First Token (s)",
    ProviderIdFilterChip: "Provider",
    ModelTypeCodeFilterChip: "Provider service",
    ModelTypeFilterChip: "Model type",
    ModelFilterChip: "Model",
    UsernameFilterChip: "User",
    CallerStackFilterChip: "Caller stack",
    YearFilterChip: "Year",
    MonthFilterChip: "Month"
  };

  /**
   * Every model type usage is recorded for, not only the ones found in the current
   * result: the backend stops reporting model type as a sub-dimension once the filter
   * fixes it, so options built from the result would vanish after the first choice.
   */
  modelTypeOptions: { label: string, value?: LLMUsageDrillDownLevel.ModelTypeEnum }[] = this.buildModelTypeOptions();

  // Chart data
  tokenChartDataTab1: any;
  responseTimeChartDataTab1: any;
  timeToFirstTokenChartDataTab1: any;
  tokenChartDataTab2: any;
  responseTimeChartDataTab2: any;
  timeToFirstTokenChartDataTab2: any;
  costChartDataTab1: any;
  costChartDataTab2: any;
  /** True when some period's calls were priced in several currencies, which cannot be summed. */
  costMixedCurrenciesTab1: boolean = false;
  costMixedCurrenciesTab2: boolean = false;

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
    this.translateTexts();
    this.loadData({});
  }

  /** Translates the texts built here, then rebuilds the options and charts showing them. */
  private translateTexts(): void {
    const host = Array.isArray(this.fieldHost) ? this.fieldHost[0] : this.fieldHost;
    const moduleId = Array.isArray(this.moduleId) ? this.moduleId[0] : this.moduleId;
    const entityId = host?.getEntityName();
    if (!moduleId || !entityId) return;
    const items = Object.entries(this.texts).map(([id, label]) => ({ id, label }));
    this.translationService.translateMenuItems(moduleId, entityId, items).subscribe(translated => {
      const texts = { ...this.texts };
      translated.forEach(x => {
        if (x.id && x.label) texts[x.id] = x.label;
      });
      this.texts = texts;
      this.modelTypeOptions = this.buildModelTypeOptions();
      this.refreshActiveFilters();
      this.updateCharts();
    });
  }

  private buildModelTypeOptions(): { label: string, value?: LLMUsageDrillDownLevel.ModelTypeEnum }[] {
    return [
      { label: this.texts["AllOption"], value: undefined },
      { label: this.texts["ChatModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.CHAT },
      { label: this.texts["EmbeddingModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.EMBEDDING },
      { label: this.texts["RankingModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.RANKER },
      { label: this.texts["ImageModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.IMAGE },
      { label: this.texts["TextToSpeechModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.TTS },
      { label: this.texts["TranscriptionModelTypeOption"], value: LLMUsageDrillDownLevel.ModelTypeEnum.TRANSCRIPT }
    ];
  }

  abstract executeDrillDown(filter: LLMUsageDrillDownLevel): Observable<LLMUsageDrillDownResult>;

  loadData(filter: LLMUsageDrillDownLevel): void {
    const applied = BaseLLMSUsageDashboardComponent.normalized(filter);
    this.loading = true;
    this.executeDrillDown(applied).subscribe({
      next: (res) => {
        this.result = res;
        this.appliedFilter = applied;
        this.filterTab1 = { ...applied };
        this.filterTab2 = { ...applied };
        this.refreshActiveFilters();
        this.updateCharts();
        
        const dailyEmpty = !res.currentMonthDaily || res.currentMonthDaily.length === 0;
        const monthlyEmpty = !res.monthly || res.monthly.length === 0;
        
        if (Object.keys(applied).length === 0) {
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
    this.loadData({});
  }

  /** Drops one applied filter, from its chip in the active filters bar. */
  removeFilter(key: keyof LLMUsageDrillDownLevel): void {
    const next: LLMUsageDrillDownLevel = { ...this.appliedFilter };
    delete next[key];
    this.loadData(next);
  }

  /** True when the tab's filter panel was changed and not applied yet. */
  hasPendingChanges(tabIndex: number): boolean {
    const panel = BaseLLMSUsageDashboardComponent.normalized(tabIndex === 1 ? this.filterTab1 : this.filterTab2);
    const applied = this.appliedFilter;
    const keys = new Set([...Object.keys(panel), ...Object.keys(applied)]) as Set<keyof LLMUsageDrillDownLevel>;
    if (tabIndex === 1) {
      PERIOD_KEYS.forEach(key => keys.delete(key));
    }
    return [...keys].some(key => panel[key] !== applied[key]);
  }

  /**
   * The options of a filter: "All" and the values the result still offers, plus the
   * selected one, which the result stops offering once the filter fixes it.
   */
  getOptions(arr?: Array<any>, selected?: any): { label: string, value: any }[] {
    const options: { label: string, value: any }[] = [{ label: this.texts["AllOption"], value: undefined }];
    const values = arr ? [...arr] : [];
    if (selected !== undefined && selected !== null && !values.includes(selected)) {
      values.unshift(selected);
    }
    values.forEach(val => {
      options.push({ label: String(val), value: val });
    });
    return options;
  }

  private refreshActiveFilters(): void {
    const all = FILTER_KEYS.filter(key => this.appliedFilter[key] !== undefined)
      .map(key => ({ key, label: this.texts[key.charAt(0).toUpperCase() + key.slice(1) + "FilterChip"], value: this.filterValueLabel(key) }));
    this.activeFiltersTab2 = all;
    this.activeFiltersTab1 = all.filter(x => !PERIOD_KEYS.includes(x.key));
  }

  private filterValueLabel(key: keyof LLMUsageDrillDownLevel): string {
    const value: any = this.appliedFilter[key];
    if (key === "modelType") {
      return this.modelTypeOptions.find(x => x.value === value)?.label ?? String(value);
    }
    if (key === "month" && typeof value === "number") {
      return new Date(2000, value - 1, 1).toLocaleString(undefined, { month: "long" });
    }
    return String(value);
  }

  /** The filter without its unset fields: what the drill down actually applies. */
  private static normalized(filter: LLMUsageDrillDownLevel): LLMUsageDrillDownLevel {
    const result: any = {};
    Object.entries(filter || {}).forEach(([key, value]) => {
      if (value !== undefined && value !== null && value !== "") {
        result[key] = value;
      }
    });
    return result;
  }

  private updateCharts(): void {
    if (!this.result) return;

    const currentMonthDaily = this.result.currentMonthDaily || [];
    const monthly = this.result.monthly || [];

    // Tab 1 (This month usage - Daily)
    this.tokenChartDataTab1 = this.buildTokenChartData(currentMonthDaily, true);
    this.responseTimeChartDataTab1 = this.buildResponseTimeChartData(currentMonthDaily, true);
    this.timeToFirstTokenChartDataTab1 = this.buildTimeToFirstTokenChartData(currentMonthDaily, true);
    this.costChartDataTab1 = this.buildCostChartData(currentMonthDaily, true);
    this.costMixedCurrenciesTab1 = this.hasMixedCurrencies(currentMonthDaily);

    // Tab 2 (Monthly usage)
    this.tokenChartDataTab2 = this.buildTokenChartData(monthly, false);
    this.responseTimeChartDataTab2 = this.buildResponseTimeChartData(monthly, false);
    this.timeToFirstTokenChartDataTab2 = this.buildTimeToFirstTokenChartData(monthly, false);
    this.costChartDataTab2 = this.buildCostChartData(monthly, false);
    this.costMixedCurrenciesTab2 = this.hasMixedCurrencies(monthly);
  }

  private buildTokenChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    const inputData = buckets.map(b => (b.inputToken || 0) / 1000);
    const outputData = buckets.map(b => (b.outputToken || 0) / 1000);

    return {
      labels: labels,
      datasets: [
        {
          label: this.texts["InputTokensSeries"],
          data: inputData,
          backgroundColor: "rgba(66, 165, 245, 0.75)",
          borderColor: "#1E88E5",
          borderWidth: 1.5
        },
        {
          label: this.texts["OutputTokensSeries"],
          data: outputData,
          backgroundColor: "rgba(255, 167, 38, 0.75)",
          borderColor: "#FB8C00",
          borderWidth: 1.5
        }
      ]
    };
  }

  /**
   * Response time: from the request being issued to the response being complete,
   * for every call.
   */
  private buildResponseTimeChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    const minData = buckets.map(b => (b.responseTimeMin || 0) / 1000);
    const avgData = buckets.map(b => (b.responseTimeAvg || 0) / 1000);
    const maxData = buckets.map(b => (b.responseTimeMax || 0) / 1000);

    return {
      labels: labels,
      datasets: [
        {
          label: this.texts["MinResponseTimeSeries"],
          data: minData,
          backgroundColor: "rgba(102, 187, 106, 0.75)",
          borderColor: "#43A047",
          borderWidth: 1.5
        },
        {
          label: this.texts["AvgResponseTimeSeries"],
          data: avgData,
          backgroundColor: "rgba(38, 166, 154, 0.75)",
          borderColor: "#00897B",
          borderWidth: 1.5
        },
        {
          label: this.texts["MaxResponseTimeSeries"],
          data: maxData,
          backgroundColor: "rgba(239, 83, 80, 0.75)",
          borderColor: "#E53935",
          borderWidth: 1.5
        }
      ]
    };
  }

  /**
   * Cost of the priced calls per period, one series per currency: amounts in different
   * currencies are never summed. A period whose calls were priced in several
   * currencies reports no cost (the backend cannot sum it) and is a gap; narrowing the
   * drill down to a provider or model shows it. Not shown when no call was priced.
   */
  private buildCostChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    const currencies = Array.from(new Set(buckets
      .filter(b => b.cost !== undefined && b.cost !== null && !!b.currencyCode)
      .map(b => b.currencyCode as string)));
    if (currencies.length === 0) {
      return undefined;
    }
    const palette = [
      { backgroundColor: "rgba(255, 202, 40, 0.75)", borderColor: "#FFB300" },
      { backgroundColor: "rgba(141, 110, 99, 0.75)", borderColor: "#6D4C41" },
      { backgroundColor: "rgba(120, 144, 156, 0.75)", borderColor: "#546E7A" }
    ];
    return {
      labels: buckets.map(b => this.formatTimeLabel(b, isDaily)),
      datasets: currencies.map((currency, i) => ({
        label: `${this.texts["CostSeries"]} (${currency})`,
        data: buckets.map(b => b.currencyCode === currency && b.cost !== undefined && b.cost !== null ? b.cost : null),
        backgroundColor: palette[i % palette.length].backgroundColor,
        borderColor: palette[i % palette.length].borderColor,
        borderWidth: 1.5
      }))
    };
  }

  /** Whether some period had priced calls but no summable cost: several currencies. */
  private hasMixedCurrencies(buckets: LLMUsageAggregationBucket[]): boolean {
    return buckets.some(b => (b.costSamples || 0) > 0 && (b.cost === undefined || b.cost === null));
  }

  /**
   * Time to first token, the latency in the strict sense. Only streamed chat calls
   * measure it: a period without any is plotted as a gap (null), not as zero, and
   * when no period has one the chart is not shown at all.
   */
  private buildTimeToFirstTokenChartData(buckets: LLMUsageAggregationBucket[], isDaily: boolean): any {
    if (!buckets.some(b => (b.timeToFirstTokenSamples || 0) > 0)) {
      return undefined;
    }
    const labels = buckets.map(b => this.formatTimeLabel(b, isDaily));
    const timed = (b: LLMUsageAggregationBucket, value?: number) =>
      (b.timeToFirstTokenSamples || 0) > 0 && value !== undefined && value !== null ? value / 1000 : null;

    return {
      labels: labels,
      datasets: [
        {
          label: this.texts["MinTimeToFirstTokenSeries"],
          data: buckets.map(b => timed(b, b.timeToFirstTokenMin)),
          backgroundColor: "rgba(126, 87, 194, 0.75)",
          borderColor: "#5E35B1",
          borderWidth: 1.5
        },
        {
          label: this.texts["AvgTimeToFirstTokenSeries"],
          data: buckets.map(b => timed(b, b.timeToFirstTokenAvg)),
          backgroundColor: "rgba(92, 107, 192, 0.75)",
          borderColor: "#3949AB",
          borderWidth: 1.5
        },
        {
          label: this.texts["MaxTimeToFirstTokenSeries"],
          data: buckets.map(b => timed(b, b.timeToFirstTokenMax)),
          backgroundColor: "rgba(236, 64, 122, 0.75)",
          borderColor: "#D81B60",
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
