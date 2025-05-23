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
 * A component that allows users to manage a list of strings with specific validation rules.
 * This component implements ControlValueAccessor to integrate with Angular's form controls.
 * It supports different types of string validation like URLs and hostnames with protocol options.
 */
import { FormatWidth } from "@angular/common";
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormArray, FormBuilder, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
//TODO: COMPLETE THIS COMPONENT TO LET USER ENTER URLS/HOSTS ecc...
@Component({
    selector: "gebo-ai-strings-list-component",
    templateUrl: "string-list.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIStringListComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAIStringListComponent implements OnInit, OnChanges, ControlValueAccessor {

    /** Title to display for the string list */
    @Input() title?: string;
    /** Types of strings allowed in the list (url or hostname) */
    @Input() allowedTypes: ("url" | "hostname")[] = ["url"];
    /** Allowed protocols for the URLs (none, http, https, ftp) */
    @Input() allowedProtocols: ("none" | "http" | "https" | "ftp")[] = ["http", "https"];
    /** Whether the component is in read-only mode */
    @Input() readonly: boolean = false;
    /** Form array to manage the string entries */
    public formArray?: FormArray;
    /** Internal storage for the string values */
    public internalValue: string[] = [];
    /** Flag to control the visibility of the edit window */
    public openEditWindow: boolean = false;

    /**
     * Initializes the component with an empty form array
     * @param fb FormBuilder service for creating form controls
     */
    constructor(private fb: FormBuilder) {
        this.formArray = fb.array([]);
    }

    /**
     * Creates an array of protocol prefixes based on the allowed protocols
     * @returns Array of protocol prefixes (e.g., "http://", "https://")
     */
    private allowedPrefixes(): string[] {
        const values: string[] = [];
        this.allowedProtocols.forEach(x => {

            if (x !== "none") {
                values.push(x + "://");
            }
        });
        return values;
    }

    /**
     * Adds a new row to the form array
     * @param value Optional initial value for the new row
     * @param options Optional configuration options, including whether to emit an event
     */
    public addRow(value?: string, options?: { emitEvent?: boolean; }) {
        const fg: FormGroup = this.fb.group({
            value: new FormControl()
        });
        if (value) {
            fg.patchValue({
                value: value
            });
        }
        this.formArray?.push(fg, options);
    }

    /**
     * Removes a row from the form array at the specified index
     * @param index The index of the row to remove
     * @param options Optional configuration options, including whether to emit an event
     */
    public removeRow(index: number, options?: { emitEvent?: boolean; }) {
        this.formArray?.removeAt(index, options);
    }

    /**
     * Clears all data from the form array
     * @param options Optional configuration options, including whether to emit an event
     */
    public clearData(options?: { emitEvent?: boolean; }): void {
        while (this.formArray?.length && this.formArray?.length > 0) {
            this.formArray.removeAt(0, options);
        }
    }

    /**
     * Updates the form array with a new set of values
     * @param newValue Array of string values to populate the form
     */
    private editValue(newValue: string[]) {
        this.clearData({ emitEvent: false });
        let realBindValues: string[] = [""];
        if (newValue && newValue.length > 0) {
            realBindValues = newValue;
        }
        realBindValues.forEach(x => {
            this.addRow(x, { emitEvent: false });
        });
        this.formArray?.updateValueAndValidity();

    }

    /**
     * Gets a FormGroup from the form array at the specified index
     * @param index The index of the FormGroup to retrieve
     * @returns The FormGroup at the specified index
     */
    getFg(index: number): FormGroup<any> {
        return this.formArray?.at(index) as FormGroup;
    }

    /**
     * Opens the edit mode by populating the form array with the current values
     */
    openEditMode(): void {
        this.editValue(this.internalValue);
        this.openEditWindow = true;
    }

    /**
     * Removes an item from the main panel at the specified index
     * @param i The index of the item to remove
     */
    removeFromMainPanel(i: number) {
        const newValue: string[] = [];
        this.internalValue?.forEach((x, index) => {
            if (i !== index) {
                newValue.push(x);
            }
        });
        this.internalValue = newValue;
        this.onChange(this.internalValue && this.internalValue.length ? this.internalValue : undefined);
    }

    /**
     * Confirms the current values in the form array
     * This method appears to be incomplete as it only retrieves the values but doesn't update anything
     */
    confirmValue(): void {
        const entries: { value: string }[] = this.formArray?.value;

    }

    /**
     * Angular lifecycle hook that is called after component initialization
     */
    ngOnInit(): void {

    }

    /**
     * Angular lifecycle hook that is called when input properties change
     * @param changes SimpleChanges object containing the changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * ControlValueAccessor method to write a value to the component
     * @param obj The value to write
     */
    writeValue(obj: any): void {
        this.internalValue = obj;
    }

    /**
     * Function to call when the value changes
     */
    private onChange: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor method to register a callback function for change events
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * Function to call when the control is touched
     */
    private onTouched: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor method to register a callback function for touched events
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /**
     * ControlValueAccessor method to set the disabled state of the component
     * @param isDisabled Whether the component should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }

}