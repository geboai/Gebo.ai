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
 * This file defines an editable multiselect component for Angular applications.
 * The component allows users to select multiple items from a dropdown list and
 * also provides functionality to create new entries through a popup window.
 */

import { ChangeDetectorRef, Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { Observable } from "rxjs";
import { GeboActionPerformedEvent, GeboActionPerformedType, GeboUIActionRequest } from "../../architecture/actions.model";
import { GeboUIActionRoutingService } from "../../architecture/gebo-ui-action-routing.service";
import { DropdownChangeEvent } from "primeng/dropdown";

/**
 * Constant string that serves as a marker for creating a new record
 * This special code is used to identify when the user selects the "Create new" option
 */
const createNewValueCodeConstant: string = "InsertNewRecord@@@@[Gebo.ai]";

/**
 * EditableMultiselectComponent provides a dropdown multiselect with the ability to create new options
 * 
 * This component implements ControlValueAccessor to integrate with Angular's form system,
 * and also implements OnInit and OnChanges for lifecycle hooks.
 * It supports loading options from either an Observable or a static data array,
 * and provides a "Create new" option that can trigger a popup window for record creation.
 */
@Component({
    selector: "geboai-editable-multiselect-component",
    templateUrl: "editable-multiselect.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => EditableMultiselectComponent),
            multi: true
        }
    ],
    standalone: false
})
export class EditableMultiselectComponent implements ControlValueAccessor, OnInit, OnChanges {

    /**
     * Represents the "Create new" option that can be selected from the dropdown
     * Contains a special code and display text for the user
     */
    private newRecordSelectable: { code?: string, description?: string } = { code: createNewValueCodeConstant, description: "Create new...." };

    /**
     * Indicates whether the component is currently loading data
     */
    public loading: boolean = false;
    
    /**
     * Available options to display in the dropdown
     */
    public options?: { code?: string, description?: string }[] = [];
    
    /**
     * Currently selected option(s) in the dropdown
     */
    public selectedOption?: { code?: string, description?: string }[];
    
    /**
     * Title to display on the new record creation window
     */
    @Input() newRecordWindowTitle: string = "New record";
    
    /**
     * Placeholder text for the dropdown
     */
    @Input() placeholder?: string;
    
    /**
     * Whether the component is in read-only mode
     */
    @Input() readonly: boolean = false;
    
    /**
     * The current value(s) selected in the dropdown (as an array of codes)
     */
    public value?: string [] = undefined;
    
    /**
     * Observable that provides the options for the dropdown
     */
    @Input() optionsObservable?: Observable<{ code?: string, description?: string }[]>;
    
    /**
     * Static data array that provides the options for the dropdown
     */
    @Input() data?: { code?: string, description?: string }[] = [];
    
    /**
     * Request configuration for creating a new record
     */
    @Input() createNewRecordRequest?: GeboUIActionRequest = undefined;
    
    /**
     * Determines whether the "Create new" functionality is available
     * based on the presence of required dependencies
     */
    private get canCreateRecord(): boolean {
        return this.createNewRecordRequest && this.actionsService ? true : false;
    }

    /**
     * Constructor for the component
     * 
     * @param changeDetection - Angular's change detector reference for triggering UI updates
     * @param actionsService - Service for routing UI actions (optional)
     */
    constructor(private changeDetection: ChangeDetectorRef, private actionsService?: GeboUIActionRoutingService) {

    }

    /**
     * Lifecycle hook that is called after component initialization
     */
    ngOnInit(): void {

    }

    /**
     * Opens the edit window for creating a new record
     * This is triggered when the user selects the "Create new" option
     */
    private doOpenEditWindow(): void {
        if (this.actionsService && this.createNewRecordRequest) {
            const action: GeboUIActionRequest = { ...this.createNewRecordRequest };
            action.onActionPerformed = (event: GeboActionPerformedEvent) => {
                switch (event.actionType) {
                    case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                    case GeboActionPerformedType.INSERTED:
                    case GeboActionPerformedType.SAVED: {
                        if (event?.target?.code) {
                          if (!this.value)
                            this.value = [event?.target?.code];
                          else {
                            this.value=[...this.value,event.target.code].filter(x=>x!==createNewValueCodeConstant);
                          }
                        }

                        this.selectCorrectValue();
                    }
                }
                this.reloadData();
            };
            this.actionsService.routeEvent(action);
        }

    }

    /**
     * Reloads the data for the dropdown options
     * This can be from either the Observable or the static data array
     */
    private reloadData(): void {
        if (this.optionsObservable) {
            this.loading = true;
            this.optionsObservable.subscribe({
                next: (elements: { code?: string, description?: string }[]) => {
                    if (this.canCreateRecord) {
                        const newVector: any[] = [];
                        newVector.push(this.newRecordSelectable);
                        if (elements) {
                            elements.forEach(entry => {
                                newVector.push(entry);
                            });
                        }
                        this.options = newVector;
                    } else {
                        this.options = elements;
                    }
                    this.selectCorrectValue();
                },
                complete: () => {
                    this.loading = false;
                }
            });
        } else if (this.data) {
            const vector: any = [] = [];
            this.data.forEach(entry => {
                vector.push(entry);
            });
            if (this.canCreateRecord) {
                vector.push(this.newRecordSelectable);
            }
            this.options = vector;
            this.selectCorrectValue();
        } else {
            if (this.canCreateRecord) {
                this.options = [this.newRecordSelectable];
            } else {
                this.options = [];
            }
        }
    }

    /**
     * Lifecycle hook that is called when component inputs change
     * Detects changes to the options sources and reloads data accordingly
     * 
     * @param changes - Object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.optionsObservable && changes["optionsObservable"]) {
            this.reloadData();
        } else
            if (this.data && changes["data"]) {
                this.reloadData();
            }
    }

    /**
     * ControlValueAccessor method that writes a new value to the component
     * 
     * @param obj - The new value to write
     */
    writeValue(obj: any): void {
        this.value = obj;
        this.selectCorrectValue();
    }

    /**
     * Change callback for the ControlValueAccessor interface
     */
    onChange?: (v: any) => void;
    
    /**
     * Registers the onChange callback
     * 
     * @param fn - The callback function to register
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    
    /**
     * Touch callback for the ControlValueAccessor interface
     */
    onTouch?: (v: any) => void;
    
    /**
     * Registers the onTouch callback
     * 
     * @param fn - The callback function to register
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }
    
    /**
     * ControlValueAccessor method to set the disabled state of the component
     * 
     * @param isDisabled - Whether the component should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }

    /**
     * Selects the correct value in the dropdown based on the current value
     * Updates the UI to reflect the selected value
     */
    private selectCorrectValue(): void {
        let outObject: any = null;
        if (this.value && this.options) {
            this.options.forEach(x => {
                if (x.code === this.value) {
                    outObject = x;
                }
            });
        }
        this.selectedOption = outObject;
        /*
                const formContent = {
                    ctrl: outObject?.code
                };
                this.formGroup.patchValue(formContent, { onlySelf: false, emitEvent: false });*/
        this.changeDetection.detectChanges();
    }

    /**
     * Handles the dropdown change event
     * Triggers the creation of a new record if the "Create new" option was selected
     * Otherwise, updates the component value and notifies the form
     * 
     * @param event - The dropdown change event containing the new value
     */
    changedValue(event: DropdownChangeEvent) {
        this.value = event.value;
        if (this.canCreateRecord && this.value && this.value.length && this.value[0] === createNewValueCodeConstant) {
            this.doOpenEditWindow();
        } else {
            this.selectCorrectValue();
            if (this.onChange) {
                this.onChange(this.value);
            }
        }
    }
}