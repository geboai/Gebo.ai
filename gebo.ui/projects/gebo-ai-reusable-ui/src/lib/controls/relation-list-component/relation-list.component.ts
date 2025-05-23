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
 * This component provides a selectable and editable list of relations or items.
 * It implements ControlValueAccessor to integrate with Angular's form controls,
 * allowing it to be used in reactive forms. The component manages a list of
 * selectable items (objects with code and description properties) and tracks
 * both selected values and available options.
 */
import { ChangeDetectorRef, Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { Observable } from "rxjs";

/**
 * Component that displays and manages a list of selectable relation items.
 * It provides functionality to add and remove items from the list, with support
 * for loading options dynamically through Observables.
 */
@Component({
    selector: "gebo-ai-relation-list",
    templateUrl: "relation-list.component.html",
    styles: "p-chips {border: 0px solid white;}",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAiRelationListComponent),
            multi: true
        }
    ],
    standalone: false
})

export class GeboAiRelationListComponent implements ControlValueAccessor, OnInit, OnChanges {

    /** Flag indicating if all values are currently loading */
    private loadingAllValues: boolean = false;
    
    /** Flag indicating if remaining selectable values are currently loading */
    private loadingRemainingValues: boolean = false;
    
    /**
     * Returns whether any loading operation is in progress
     */
    get loading() {
        return this.loadingAllValues === true || this.loadingRemainingValues === true;
    }
    
    /** Array of selected value codes */
    selectedValues: string[] = [];
    
    /** Array of selected objects with code and description */
    selectedObjects: { code?: string, description?: string }[] = [];
    
    /** Array of objects that can be chosen/selected */
    choosableObjects: { code?: string, description?: string }[] = [];
    
    /** Complete collection of all available objects */
    private allObjects: { code?: string, description?: string }[] = [];
    
    /** Observable that provides all available options */
    @Input() allOptionsObservable?: Observable<{ code?: string, description?: string }[]>;
    
    /** Observable that provides remaining selectable options */
    @Input() remainingObjectsObservable?: Observable<{ code?: string, description?: string }[]>;
    
    /** Flag to set the component in read-only mode */
    @Input() readonly: boolean = false;
    
    /** Controls visibility of the add window/dialog */
    viewAddWindow: boolean = false;
    
    /** Form group for the selection list */
    formGroup: FormGroup = new FormGroup({
        list: new FormControl()
    });
    
    /** Form group for currently selected objects */
    selectedFormGroup:FormGroup=new FormGroup({
        selectedObjects:new FormControl()
    })
    
    /**
     * Creates an instance of the relation list component
     * @param changeDetection Reference to Angular's change detection mechanism
     */
    constructor(private changeDetection: ChangeDetectorRef) {

    }
    
    /**
     * Initializes the component and sets up form control subscriptions.
     * Listens for changes to the list control and updates selected values accordingly.
     */
    ngOnInit(): void {
        this.formGroup.controls["list"].valueChanges.subscribe(newValue => {
            if (!this.selectedValues.find(v => v === newValue)) {
                const newVector: string[] = [];
                if (this.selectedValues) {
                    this.selectedValues.forEach(v => {
                        newVector.push(v);
                    });
                }
                newVector.push(newValue);
                this.selectedValues = newVector;
                this.showCorrectValues();
                this.viewAddWindow = false;
                if (this.onChange) {
                    this.onChange(this.selectedValues);
                }
            }
        });
    }
    
    /**
     * Handles the removal of a chip/item from the selected items
     * @param event The event containing the value to be removed
     */
    removeChip(event: any) {
        const valueCode: string = event.value.code;
        this.selectedValues = this.selectedValues.filter(x => x !== valueCode);
        if (this.onChange) {
            this.onChange(this.selectedValues);
        }
        this.showCorrectValues();
    }

    /**
     * Responds to changes in component inputs by subscribing to observables
     * and updating the available and selected items.
     * @param changes Simple changes object containing changed input properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.allOptionsObservable && changes["allOptionsObservable"]) {
            this.loadingAllValues = true;
            this.allOptionsObservable.subscribe({
                next: vals => {
                    this.allObjects = vals
                    this.showCorrectValues();
                },
                error: (error) => { },
                complete: () => { this.loadingAllValues = false; }
            });
        }
        if (this.remainingObjectsObservable && changes["remainingObjectsObservable"]) {
            this.loadingRemainingValues = true;
            this.remainingObjectsObservable.subscribe({
                next: vals => {
                    this.choosableObjects = vals

                },
                error: (error) => { },
                complete: () => { this.loadingRemainingValues = false; }
            });
        }
    }
    
    /**
     * ControlValueAccessor implementation method that writes a new value to the component
     * @param obj The value to set for the component
     */
    writeValue(obj: any): void {
        this.selectedValues = obj;
        if (!obj) this.selectedValues = [];
        this.showCorrectValues();
    }
    
    /**
     * Updates the selectedObjects array based on the current selectedValues
     * and updates the form control value accordingly
     */
    private showCorrectValues(): void {
        if (this.selectedValues && this.allObjects?.length) {
            const selected: any[] = [];
            this.selectedValues.forEach(v => {
                const obj = this.allObjects.find(x => x.code === v);
                if (obj) {
                    selected.push(obj);
                }
            });
            this.selectedObjects = selected;
        }
        this.selectedFormGroup.patchValue({
            selectedObjects: this.selectedObjects
        });
        this.changeDetection.detectChanges();
    }
    
    /** Function to call when the value changes */
    onChange?: (v: any) => void;
    
    /**
     * ControlValueAccessor implementation method to register the onChange callback
     * @param fn The callback function to register
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    
    /** Function to call when the control is touched */
    onTouch?: (v: any) => void;
    
    /**
     * ControlValueAccessor implementation method to register the onTouched callback
     * @param fn The callback function to register
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    
    /**
     * ControlValueAccessor implementation method to handle disabled state
     * @param isDisabled Flag indicating if the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
    }

}