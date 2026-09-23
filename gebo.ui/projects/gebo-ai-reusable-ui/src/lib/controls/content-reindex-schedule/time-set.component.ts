/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, RequiredValidator } from "@angular/forms";
import { ReindexTimeComponentMetaInfo } from "@Gebo.ai/gebo-ai-rest-api";
/**
 * AI generated comments
 * 
 * Interface representing configuration for choice values in time component.
 * Defines the structure for configuring min/max values or a list of predefined choices.
 */
interface ChoiceCfg {
    minValue?: number;
    maxValue?: number;
    choices?: { id: number, label: string }[];
}

/**
 * AI generated comments
 * 
 * A reusable Angular component for configuring time-related values.
 * This component can be used to select time values from predefined choices or date values.
 * It implements ControlValueAccessor to work seamlessly with Angular forms.
 * Can handle multiple time dimensions based on provided metadata.
 */
@Component({
    selector: "time-set-component",
    templateUrl: "time-set.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => TimeSetComponent),
            multi: true
        }
    ],
    standalone: false
})
export class TimeSetComponent implements OnInit, OnChanges, ControlValueAccessor {

    /**
     * Configuration metadata for time component, defines the time units and available options
     */
    @Input() metaInfo?: ReindexTimeComponentMetaInfo[];
    
    /**
     * Flag indicating if the component should display date selection (true) or numeric selection (false)
     */
    public dateComponent: boolean = false;
    
    /**
     * Array of selected values for each time unit
     */
    public values: (number | undefined)[] = [];
    
    /**
     * Configuration for each choice field in the component
     */
    public choices: ChoiceCfg[] = [];
    
    /**
     * Form controls array for each time selection field
     */
    public formControl: FormControl[] = [];
    
    /**
     * Form control for created timestamp
     */
    public createdTime: FormControl = new FormControl();
    
    /**
     * Event emitter to notify parent when this component should be removed
     */
    @Output() remove: EventEmitter<boolean> = new EventEmitter();
    
    /**
     * Component initialization logic
     */
    ngOnInit(): void {

    }
    
    /**
     * Collects and emits the current component values when changes occur
     * Converts date values to timestamps if in date mode
     */
    private emitChange(): void {

        const outValue: number[] = [];

        let allValids: boolean = true;
        this.formControl.forEach(x => {
            allValids = allValids && x.valid;
            if (this.dateComponent) {
                outValue.push(this.date2int(x.value));
            } else {
                outValue.push(x.value);
            }
        });
        if (allValids) {
            this.onChange(outValue);
        }

    }
    
    /**
     * Syncs the form controls with the current values
     * Handles conversion between timestamp and date objects if in date mode
     */
    private setControls(): void {

        if (this.formControl && this.formControl.length) {
            if (this.values && this.values.length  && this.formControl.length === this.values.length) {
                for (let i: number = 0; i < this.values.length; i++) {
                    if (this.dateComponent === true) {
                        this.formControl[i].setValue(this.int2date(this.values[i]));
                    } else {
                        this.formControl[i].setValue(this.values[i]);
                    }
                }
            }else {
                for (let i: number = 0; i < this.formControl.length; i++) {
                    if (this.dateComponent !== true) {
                        this.formControl[i].setValue(0);
                    } 
                }
            }
        }
    }
    
    /**
     * Utility function to convert Date object to timestamp (milliseconds)
     * @param d Date object to convert
     * @returns Timestamp in milliseconds or 0 if no date provided
     */
    private date2int(d?: Date): number {
        if (!d) return 0;

        return d.getTime();
    }
    
    /**
     * Utility function to convert timestamp to Date object
     * @param i Timestamp in milliseconds
     * @returns Date object or undefined if no timestamp provided
     */
    private int2date(i?: number): Date | undefined {
        if (!i)
            return undefined;
        return new Date(i);

    }
    
    /**
     * Handles component input changes, particularly when metaInfo changes
     * Sets up the component based on the new metadata including form controls and choice configurations
     * @param changes Angular SimpleChanges object containing changed inputs
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.metaInfo && changes["metaInfo"] && this.metaInfo.length) {
            if (this.metaInfo.length === 1 && this.metaInfo[0].timeUnity === 'DATE') {
                this.dateComponent = true;
            }

            const choices: ChoiceCfg[] = [];
            const fc: FormControl[] = [];
            this.metaInfo.forEach(x => {
                const control: FormControl = new FormControl(RequiredValidator);
                control.valueChanges.subscribe(x => {
                    this.emitChange();
                });
                fc.push(control);
                const cfg: ChoiceCfg = {
                    minValue: 0,
                    maxValue: x.maxValue,
                    choices: x.choosableOptions?.map((y, i) => { return { id: i, label: y } })
                };
                choices.push(cfg);
            });
            this.choices = choices;
            this.formControl = fc;
            this.setControls();

        }
    }
    
    /**
     * ControlValueAccessor implementation: Sets the component's value from form model
     * @param obj The value from the form model
     */
    writeValue(obj: any): void {
        this.values = obj;
        this.setControls();
    }
    
    /**
     * Default onChange handler for ControlValueAccessor
     */
    private onChange: (v: any) => void = (v: any) => { };
    
    /**
     * ControlValueAccessor implementation: Registers onChange handler
     * @param fn Function to call when the value changes
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    
    /**
     * Default onTouch handler for ControlValueAccessor
     */
    private onTouch: (v: any) => void = (v: any) => { };
    
    /**
     * ControlValueAccessor implementation: Registers onTouched handler
     * @param fn Function to call when the control is touched
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    
    /**
     * ControlValueAccessor implementation: Sets the disabled state
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }
    
    /**
     * Emits remove event to notify parent that this component should be removed
     */
    removeThis(): void {
        this.remove.emit(true);
    }
}