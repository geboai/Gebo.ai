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
 * This module provides a component that adapts an editable listbox for use with object data.
 * It implements ControlValueAccessor to work with Angular forms, allowing binding to objects
 * with code and description properties.
 */

import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from "@angular/forms";
import { GeboUIActionRequest } from "@Gebo.ai/reusable-ui";
import { Observable } from "rxjs";

/**
 * A component that wraps an editable listbox component for binding to objects with code/description properties.
 * It serves as an adapter that handles the communication between form controls and the underlying editable listbox.
 * Implements ControlValueAccessor to work within Angular's form ecosystem.
 */
@Component({
    selector: "geboai-editable-listbox-bound-object-component",
    template: "<geboai-editable-listbox-component [loading]='loading' [formControl]='formControl' [onSingleOptionAutomaticallySelect]='onSingleOptionAutomaticallySelect' [data]='dataSource' [readonly]='readonly' [createNewRecordRequest]='createNewRecordRequest' [placeholder]='placeholder' (selectionChanged)='onSelectionChanged($event)'></geboai-editable-listbox-component>",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => EditableListboxBoundObjectAdapterComponent),
            multi: true
        }
    ],
    standalone: false
})
export class EditableListboxBoundObjectAdapterComponent implements ControlValueAccessor, OnInit, OnChanges {
    /** Indicates whether data is currently being loaded */
    loading: boolean = false;
    
    /** The currently selected object value with code and description */
    value?: { code?: string, description?: string };
    
    /** Controls whether a single option should be automatically selected */
    @Input() onSingleOptionAutomaticallySelect: boolean = true;
    
    /** Placeholder text to display when no value is selected */
    @Input() placeholder?: string;
    
    /** Controls whether the component is in read-only mode */
    @Input() readonly: boolean = false;
    
    /** Form control for managing the selected value */
    formControl: FormControl = new FormControl();
    
    /** Observable that provides the options data */
    @Input() optionsObservable?: Observable<{ code?: string, description?: string }[]>;
    
    /** Static data array for options */
    @Input() data?: { code?: string, description?: string }[] = [];
    
    /** Configuration for creating new records */
    @Input() createNewRecordRequest?: GeboUIActionRequest = undefined;
    
    /** The processed data source used by the component */
    dataSource?: { code?: string, description?: string }[];
    
    /** Event emitter for selection changes */
    @Output() selectionChanged: EventEmitter<any> = new EventEmitter();
    
    /**
     * Lifecycle hook that is called after data-bound properties are initialized
     */
    ngOnInit(): void {

    }
    
    /**
     * Lifecycle hook that is called when any data-bound property changes
     * Handles updates to data sources and subscribes to observables when provided
     * @param changes Object containing all changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["data"] && this.data) {
            this.dataSource = [...this.data];
        }
        if (changes["optionsObservable"] && this.optionsObservable) {
            this.loading = true;
            this.optionsObservable.subscribe({
                next: (values) => {
                    this.dataSource = values;
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }
    
    /** Function to call when the value changes */
    onChange?: (v: any) => void;
    
    /**
     * Registers a callback function that is called when the control's value changes in the UI
     * Part of the ControlValueAccessor interface
     * @param fn The callback function to register
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
        if (this.value && this.onChange) {
            // If a value already exists, communicate it
            this.onChange(this.value);
        }
    }
    
    /** Function to call when the control receives a touch event */
    onTouch?: (v: any) => void;
    
    /**
     * Registers a callback function that is called when the control receives a touch event
     * Part of the ControlValueAccessor interface
     * @param fn The callback function to register
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    
    /**
     * Used by the forms API to disable the control
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
        if (isDisabled===true) {
            this.formControl.disable();
        }else {
            this.formControl.enable();
        }
    }
    
    /**
     * Writes a new value to the element
     * Part of the ControlValueAccessor interface
     * @param obj The new value to write
     */
    writeValue(obj: any): void {
        this.value = obj;
        this.formControl.setValue(this.value?.code);
    }
    
    /**
     * Handles selection change events from the inner component
     * Updates the current value and notifies Angular forms system of changes
     * @param chaData The selected code value
     */
    onSelectionChanged(chaData?: any) {
        if (chaData) {
            this.value = this.dataSource?.find(x => x.code === chaData);
        } else {
            this.value = undefined;
        }
        if (this.onChange)
            this.onChange(this.value);
    }
}