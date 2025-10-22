/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, forwardRef, Input, OnInit } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { BuildSystemRef, BuildSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { forkJoin, map, Observable, of } from "rxjs";
/**
 * AI generated comments
 * Extends the BuildSystemRef interface with additional properties for UI representation
 */
interface ExtendedBuildSystemRef extends BuildSystemRef {
  code?: string,      // Unique identifier combining type and config codes
  description?: string; // Description text for UI display
};

/**
 * Component that provides a UI for selecting build systems
 * Implements ControlValueAccessor to integrate with Angular Forms
 * Allows users to choose from available build systems and configurations
 */
@Component({
  selector: "gebo-ai-build-systems-chooser", templateUrl: "build-systems-chooser.component.html",
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => BuildSystemsChooserComponent),
      multi: true
    },
    { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("BuildSystemsChooserComponent") }
  ],
  standalone: false
})
export class BuildSystemsChooserComponent implements OnInit, ControlValueAccessor {
  /** Form group containing the chooser control */
  formGroup: FormGroup = new FormGroup({ chooser: new FormControl() });

  /** Observable that emits the list of available build systems */
  buildSystemsObservable: Observable<ExtendedBuildSystemRef[]> = of([]);

  /** Currently selected build system references */
  value: BuildSystemRef[] = [];

  /** Cached data of available build systems */
  buildSystemsData: ExtendedBuildSystemRef[] = [];

  /** Flag to control whether the component is in read-only mode */
  @Input() readonly: boolean = false;

  /**
   * Constructor that injects the build system controller service
   * @param buildSystemController Service to fetch build system data
   */
  constructor(private buildSystemController: BuildSystemsControllerService) {

  }

  /**
   * Initializes the component, sets up form control subscriptions,
   * and fetches available build systems from the API
   */
  ngOnInit(): void {
    // Subscribe to value changes in the chooser control
    this.formGroup.controls["chooser"].valueChanges.subscribe((x: string[]) => {
      const outValue: BuildSystemRef[] = [];
      if (x) {
        x.forEach(xEntry => {
          const entry = this.buildSystemsData.find(w => w.code === xEntry);
          if (entry) {
            outValue.push({ buildSystemTypeCode: entry.buildSystemTypeCode, buildSystemConfigCode: entry.buildSystemConfigCode });
          }
        });
      }
      this.value = outValue;
      if (this.onChange) this.onChange(this.value);

    });

    // Fetch build system types and their configurations
    this.buildSystemController.getBuildSystemTypes().subscribe(buildSystems => {
      const obsevables = buildSystems?.map(x => x.code ? this.buildSystemController.getBuildSystemConfigs(x.code) : of([]));
      if (obsevables) {
        this.buildSystemsObservable = forkJoin(obsevables).pipe(map(results => {
          const entries: ExtendedBuildSystemRef[] = [];
          if (results.length) {
            results.forEach(vect => {
              vect.forEach(entry => {
                entries.push({
                  code: "[" + entry.buildSystemTypeCode + "]-[" + entry.code + "]",
                  description: entry.description,
                  buildSystemConfigCode: entry.code,
                  buildSystemTypeCode: entry.buildSystemTypeCode
                });
              });
            });
          }

          return entries;
        }));

        this.buildSystemsObservable.subscribe(v => {
          this.buildSystemsData = v;
        });
      }
    });
  }

  /**
   * ControlValueAccessor implementation: Updates the component value from the form model
   * @param obj Array of BuildSystemRef objects to set as the selected value
   */
  writeValue(obj: any): void {
    this.value = obj;
    const chooserValue: string[] = [];
    if (this.value && this.value.length) {
      this.value.forEach(entry => {
        const thisEntry: string = "[" + entry.buildSystemTypeCode + "]-[" + entry.buildSystemConfigCode + "]";
        chooserValue.push(thisEntry);
      });
    }
    this.formGroup.controls["chooser"].setValue(chooserValue);
  }

  /** Function to call when the value changes */
  onChange?: (v: any) => void;

  /**
   * ControlValueAccessor implementation: Registers the onChange callback
   * @param fn Function to call when the value changes
   */
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  /** Function to call when the control is touched */
  onTouch?: (v: any) => void;

  /**
   * ControlValueAccessor implementation: Registers the onTouched callback
   * @param fn Function to call when the control is touched
   */
  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  /**
   * ControlValueAccessor implementation: Sets the disabled state of the control
   * @param isDisabled Whether the control should be disabled
   */
  setDisabledState?(isDisabled: boolean): void {

  }
}