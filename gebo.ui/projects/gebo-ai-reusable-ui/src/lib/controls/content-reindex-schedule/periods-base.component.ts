/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * 
 * This file defines a component for managing periodic scheduling in the Gebo.ai application.
 * It implements Angular's ControlValueAccessor interface to integrate with reactive forms.
 */

import { Component, Directive, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormArray, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
import { ReindexingProgrammedTable, ReindexingTime, ReindexTimeStructureMetaInfo } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Component responsible for managing period scheduling.
 * This component provides a UI for creating, editing and removing time periods
 * and integrates with Angular's form system through ControlValueAccessor.
 * It handles transformation between the form model and the ReindexingTime data structure.
 */
@Component({
    selector: "gebo-ai-periods-component",
    templateUrl: "periods-base.component.html",
    styleUrls: ["periods-base.component.css"],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIPeriodsSchedulingBaseComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAIPeriodsSchedulingBaseComponent implements ControlValueAccessor, OnChanges, OnInit {
    
    /** Input array of time metadata information */
    @Input() public timeMetaInfos: ReindexTimeStructureMetaInfo[] = [];
    
    /** Frequency input to determine which metadata info to use */
    @Input() frequency?: ReindexingProgrammedTable.FrequencyEnum;
    
    /** The selected time metadata info based on the provided frequency */
    public controlTimeMetaInfos: ReindexTimeStructureMetaInfo | undefined;
    
    /** The internal value representation of reindexing times */
    value?: ReindexingTime[];
    
    /** FormArray used to manage the UI form controls */
    formArray: FormArray<any> = new FormArray<any>([]);
    
    /**
     * Responds to input changes by finding the appropriate time metadata 
     * for the current frequency setting
     * @param changes The SimpleChanges object containing current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.timeMetaInfos && this.frequency && (changes["timeMetaInfos"] || changes["frequency"])) {
            this.controlTimeMetaInfos = this.timeMetaInfos.find(x => x.frequency === this.frequency);
        }
    }
    
    /**
     * Initializes the component by subscribing to form value changes
     * and propagating those changes through the onChange handler
     */
    ngOnInit(): void {
      this.formArray.valueChanges.subscribe({
        next:(newValue) => {
            this.onChange(newValue);
        }
      });   
    }
    
    /**
     * ControlValueAccessor method that writes a new value to the component
     * @param obj The new value to write
     */
    writeValue(obj: any): void {
        this.value = obj;
        this.restructure();
    }
    
    /**
     * Gets a FormGroup at the specified index
     * @param i The index of the FormGroup to retrieve
     * @returns The FormGroup at the specified index
     */
    get(i: number): FormGroup {
        return this.formArray.at(i) as FormGroup;
    }
    
    /**
     * Adds a new entry to the value array and restructures the form
     */
    public addEntry(): void {
        if (this.value) this.value = [...this.value, {}];
        else this.value = [{}];
        this.restructure();
    }
    
    /**
     * Synchronizes the FormArray with the current value.
     * Adds or removes FormGroups as needed and updates values.
     */
    private restructure(): void {
        const length: number = this.value ? this.value.length : 0;
        while(this.formArray.length < length) {
            this.formArray.push(createNewFG());
        }
        while(length > this.formArray.length) {
            this.formArray.removeAt(0);
        }
        if (!this.value) this.value = [];
        this.formArray.patchValue(this.value);
    }
    
    /** Function to call when the value changes */
    onChange: (v: any) => void = (v: any) => {}
    
    /**
     * ControlValueAccessor method to register a callback function for value changes
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    
    /** Function to call when the control is touched */
    onTouch: (v: any) => void = (v: any) => {}
    
    /**
     * ControlValueAccessor method to register a callback function for touch events
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    
    /**
     * ControlValueAccessor method to set the disabled state of the component
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
        
    }
    
    /**
     * Removes a period at the specified index
     * @param i The index of the period to remove
     */
    remove(i: number) {
        this.formArray.removeAt(i);
    }
    
    /**
     * Adds a new period to the form array
     */
    addPeriod() {
        this.formArray.push(createNewFG());
    }
}

/**
 * Creates a new FormGroup for a time period entry
 * Includes controls for createdTime and timeComponent, with the timeComponent being required
 * Automatically sets the createdTime to the current timestamp
 * @returns A new FormGroup configured for a time period entry
 */
function createNewFG(): any {
    const fg = new FormGroup({
        createdTime: new FormControl(),
        timeComponent: new FormControl()
    });
    fg.controls["timeComponent"].addValidators(Validators.required);
    fg.controls["createdTime"].setValue(new Date().getTime());
    return fg; 
}