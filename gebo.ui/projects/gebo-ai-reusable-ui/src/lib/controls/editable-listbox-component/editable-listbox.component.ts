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
 * This file contains the EditableListboxComponent, which provides a customizable dropdown/listbox
 * that can display selectable options and optionally allow creating new entries.
 * The component implements ControlValueAccessor to integrate with Angular's form controls.
 */

import { ChangeDetectorRef, Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import { Observable } from "rxjs";
import { GeboActionPerformedEvent, GeboActionPerformedType, GeboUIActionRequest } from "../../architecture/actions.model";
import { GeboUIActionRoutingService } from "../../architecture/gebo-ui-action-routing.service";
import { SelectChangeEvent } from "primeng/select";

/**
 * EditableListboxComponent provides a dropdown/listbox component that can display a list of options and
 * optionally allow creation of new records.
 * 
 * Features:
 * - Integrates with Angular forms via ControlValueAccessor
 * - Can load options from an Observable or static data array
 * - Supports creating new items through a configurable action request
 * - Can automatically select an option when there's only one available
 * - Provides loading state indication
 */
@Component({
    selector: "geboai-editable-listbox-component",
    templateUrl: "editable-listbox.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => EditableListboxComponent),
            multi: true
        }
    ],
    standalone: false
})
export class EditableListboxComponent implements ControlValueAccessor, OnInit, OnChanges {
    /** Unique code to identify when "Create new" option is selected */
    public  createNewValueCodeConstant: string = "InsertNewRecord@@@@[Gebo.ai]";
    /** Object representing the "Create new" option */
    private newRecordSelectable: { code?: string, description?: string } = { code: this.createNewValueCodeConstant, description: "Create new...." };

    /** Flag indicating data is being loaded */
    @Input() public loading: boolean = false;
    /** Available options for the dropdown */
    public options?: { code?: string, description?: string }[] = [];
    /** Currently selected option */
    public selectedOption?: { code?: string, description?: string };
    /** Flag indicating if the component is disabled */
    public disabled:boolean=false;
    /** Whether to automatically select an option when there's only one available */
    @Input() onSingleOptionAutomaticallySelect: boolean = true;    
    /** Placeholder text to display when no option is selected */
    @Input() placeholder?: string;
    /** Flag indicating if the component is in readonly mode */
    @Input() readonly: boolean = false;

    @Input() required?:boolean;
    /** The current value of the component */
    public value?: string = undefined;
    /** Observable to fetch options dynamically */
    @Input() optionsObservable?: Observable<{ code?: string, description?: string }[]>;
    /** Static data array to populate options */
    @Input() data?: { code?: string, description?: string }[] = [];
    /** Configuration for creating new records */
    @Input() createNewRecordRequest?: GeboUIActionRequest = undefined;
    /** Event emitted when selection changes */
    @Output() selectionChanged:EventEmitter<any>=new EventEmitter();
    
    /**
     * Determines if the "Create new" option should be available
     * @returns True if the component is configured to support creating new records
     */
    private get canCreateRecord(): boolean {
        return this.createNewRecordRequest && this.actionsService ? true : false;
    }

    /**
     * Creates an instance of EditableListboxComponent
     * @param changeDetection Reference to Angular's change detection mechanism
     * @param actionsService Service for routing UI actions
     */
    constructor(private changeDetection: ChangeDetectorRef, private actionsService?: GeboUIActionRoutingService) {

    }

    /**
     * Angular lifecycle hook called after component initialization
     */
    ngOnInit(): void {

    }

    /**
     * Opens the edit window for creating a new record
     * Configures the action request and handles responses from the edit window
     */
    private doOpenEditWindow(): void {
        if (this.actionsService && this.createNewRecordRequest) {
            const action: GeboUIActionRequest = { ...this.createNewRecordRequest };
            action.onActionPerformed = (event: GeboActionPerformedEvent) => {
                switch (event.actionType) {
                    case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                    case GeboActionPerformedType.INSERTED:
                    case GeboActionPerformedType.SAVED: {
                        this.value = event?.target?.code;
                        if (this.options && (!this.options?.find(y=>y.code===this.value))) {
                            this.options=[...this.options,event.target];
                            if (this.data && !this.data?.find(y=>y.code===this.value)) {
                                this.data.push(event.target);
                            }
                        }
                        this.selectCorrectValue();
                        if (this.onChange) {
                            this.onChange(this.value);
                        }
                    }
                }
                this.reloadData();
            };
            this.actionsService.routeEvent(action);
        }

    }

    /**
     * Automatically selects an option if there's only one available and the auto-select flag is enabled
     */
    private checkSingleOptionPresent() {
        if (this.onSingleOptionAutomaticallySelect === true && !this.value && this.options && this.options.length === 1 && this.options[0].code !== this.createNewValueCodeConstant) {
            this.value = this.options[0].code;
            if (this.onChange)
                this.onChange(this.value);
        }
    }

    /**
     * Reloads the options data from either the observable or static data array
     * Adds the "Create new" option if enabled
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
                    this.checkSingleOptionPresent();
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
            this.checkSingleOptionPresent();
        } else {
            if (this.canCreateRecord) {
                this.options = [this.newRecordSelectable];
            } else {
                this.options = [];
            }
        }
    }

    /**
     * Angular lifecycle hook that responds to input changes
     * Reloads data when the options observable or data array changes
     * @param changes SimpleChanges object containing changed properties
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
     * ControlValueAccessor method to write a value to the component
     * @param obj The value to write
     */
    writeValue(obj: any): void {
        this.value = obj;
        this.selectCorrectValue();
    }

    /** Function to call when the value changes */
    onChange?: (v: any) => void;

    /**
     * ControlValueAccessor method to register a callback to be triggered when the value changes
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
        if (this.value && this.onChange) {
            //if i already have a value i comunicate it
            this.onChange(this.value);
        }
    }

    /** Function to call when the control is touched */
    onTouch?: (v: any) => void;

    /**
     * ControlValueAccessor method to register a callback to be triggered when the control is touched
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }

    /**
     * ControlValueAccessor method to enable/disable the component
     * @param isDisabled Whether the component should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
        this.disabled=isDisabled===true;
    }

    /**
     * Finds and selects the option that matches the current value
     * Updates the UI through change detection
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

        this.changeDetection.detectChanges();
    }

    /**
     * Handles value change events from the UI
     * Opens the edit window if "Create new" is selected
     * @param event The selection change event
     */
    changedValue(event: SelectChangeEvent) {
        this.value = event.value;
        if (this.canCreateRecord && this.value === this.createNewValueCodeConstant) {
            this.doOpenEditWindow();
        } else {
            this.selectCorrectValue();
            if (this.onChange) {
                this.onChange(this.value);
            }
            this.selectionChanged.emit(this.value);
        }
    }
}