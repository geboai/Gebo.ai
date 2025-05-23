/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/* AI generated comments */
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, ValidationErrors, ValidatorFn } from "@angular/forms";
import { ReindexingFrequencyOptionsControllerService, ReindexingProgrammedTable, ReindexingTime, ReindexTimeStructureMetaInfo } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Defines the time slots units based on the ReindexingTime array
 */
type timeSlotUnities = ReindexingTime[];

/**
 * Interface representing the internal structure for reindexing time periods
 * Contains separate arrays for different frequency types (daily, dates, hours, monthly, weekly)
 */
interface InternalRappr {
    daily: timeSlotUnities;
    dates: timeSlotUnities;
    hours: timeSlotUnities;
    monthly: timeSlotUnities;
    weekly: timeSlotUnities;
};

/**
 * Interface for period options used in the scheduling component
 * Contains a code representing the frequency type and a human-readable description
 */
interface PeriodOption {
    code: ReindexingProgrammedTable.FrequencyEnum;
    description: string;
}

/**
 * Available period options for scheduling
 * Defines the types of periods users can select for reindexing schedules
 */
const availPeriods: PeriodOption[] = [{ code: "DAILY", description: "Daily programming" }, { code: "WEEKLY", description: "Weekly programming" }, { code: "DATES", description: "Dates & times" }];

/**
 * Component responsible for managing content reindexing schedules
 * Provides a UI for setting, editing, and displaying reindexing schedules with various frequency options
 * Implements ControlValueAccessor to integrate with Angular forms
 */
@Component({
    selector: "gebo-ai-content-reindex-scheduler-component",
    templateUrl: "content-reindex-schedule.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIContentReindexScheduleComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAIContentReindexScheduleComponent implements OnChanges, OnInit, ControlValueAccessor {

    /**
     * Input property that allows specifying which frequency types should be available in the component
     */
    @Input() public frequencyTypes?: ReindexingProgrammedTable.FrequencyEnum[];
    
    /**
     * List of frequency periods that can be scheduled based on input configuration
     */
    public availableSchedulablePeriods?: ReindexingProgrammedTable.FrequencyEnum[];
    
    /**
     * Current value of the scheduled reindexing configurations
     */
    public value?: ReindexingProgrammedTable[];
    
    /**
     * Temporarily edited value during modification process
     */
    public editedValue?: ReindexingProgrammedTable[];
    
    /**
     * Current UI mode - either displaying schedules or editing them
     */
    public mode: "DISPLAY" | "EDITING" = "DISPLAY";
    
    /**
     * Human-readable representations of configured time schedules
     */
    public displayTimes: string[] = [];
    
    /**
     * Flag indicating if data is being loaded or processed
     */
    public loading: boolean = false;
    
    /**
     * Metadata about time structures for different frequency types
     */
    public timeMetaInfos: ReindexTimeStructureMetaInfo[] = [];
    
    /**
     * Controls whether value changes should be emitted or not
     */
    private emitValue: boolean = true;
    
    /**
     * Form group for managing the schedule editing UI
     */
    formGroup: FormGroup = new FormGroup({
        newFrequency: new FormControl(),
        daily: new FormControl(),
        dates: new FormControl(),
        hours: new FormControl(),
        monthly: new FormControl(),
        weekly: new FormControl()
    });

    /**
     * Returns periods that haven't been scheduled yet
     * Filters the available periods to only show ones that aren't already configured
     */
    public get unscheduledPeriods(): PeriodOption[] {
        const outPeriods: PeriodOption[] = availPeriods.filter(x => {
            return !this.editedValue || !this.editedValue.find(y => y.frequency === x.code);
        });
        return outPeriods;
    }

    /**
     * Constructor initializes the component with required services
     * @param reindexingFrequencyController Service to handle reindexing frequency operations
     */
    constructor(private reindexingFrequencyController: ReindexingFrequencyOptionsControllerService) {

    }

    /**
     * Lifecycle hook that responds to changes in input properties
     * @param changes SimpleChanges object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Validates a reindexing programmed table entry based on its frequency and values
     * Checks if the time components match the expected format for the given frequency
     * @param frequency The frequency type to validate
     * @param value The time slot values to validate
     * @returns Boolean indicating whether the values are valid for the given frequency
     */
    private validateReindexingProgrammedTable(frequency:ReindexingProgrammedTable.FrequencyEnum,value?: timeSlotUnities ): boolean {
        let validated: boolean = true;
        const format = this.timeMetaInfos.find(x => x.frequency === frequency);
        if (!value) {
            validated = true;
        } else if (value && value.length && format) {
            value?.forEach(x => {
                if (x.timeComponent && x.timeComponent?.length && x.timeComponent.length === format.periodComponents?.length) {
                    format.periodComponents.forEach((cFmt, i) => {
                        if (x.timeComponent) {
                            const tComponentValue = x.timeComponent[i];
                            if (tComponentValue===undefined) {
                                validated=false;    
                            }
                        }else {
                            validated=false;
                        }
                    });
                } else {
                    validated = false;
                }
            });
        }
        console.log("For time:"+frequency+" validation=>"+validated);
        return validated;
    }

    /**
     * Initializes the component and sets up form validation
     * Fetches time structure metadata required for validation
     */
    ngOnInit(): void {
        const validateFunction: ValidatorFn = (fg) => {
            const dataStructure: InternalRappr = fg.value;
            let out: ValidationErrors | null = null;
            if (this.validateReindexingProgrammedTable("DAILY", dataStructure?.daily) &&
                this.validateReindexingProgrammedTable("DATES",dataStructure?.dates) &&
                this.validateReindexingProgrammedTable("HOURLY",dataStructure?.hours) &&
                this.validateReindexingProgrammedTable("MONTHLY", dataStructure?.monthly) &&
                this.validateReindexingProgrammedTable("WEEKLY", dataStructure?.weekly)) {
                out = null;
                console.log("Scheduling validated");
            } else {
                console.log("Scheduling NOT validated");
                out = { invalidValue: "Invalid time expression" };
            };
            return out;
        };
        this.formGroup.addValidators(validateFunction);
        this.loading = true;
        this.reindexingFrequencyController.getAllTimeStructureMetaInfos().subscribe({
            next: (timeMetaInfos) => {
                this.timeMetaInfos = timeMetaInfos;
            },
            complete: () => {
                this.loading = false;
            }
        });

    }

    /**
     * Confirms changes made in the edit mode and updates the schedule values
     * Propagates changes through the ControlValueAccessor
     */
    confirmEditedValues(): void {
        const value: InternalRappr = this.formGroup.value;
        this.notifyValuesChanges(value);
        this.displayValue();
        this.mode = "DISPLAY";
    }

    /**
     * Adds a new scheduling entry based on the selected frequency
     * Prepares the new entry for editing
     */
    addScheduling(): void {
        const value = this.formGroup.value;
        const newEntry: ReindexingProgrammedTable = { frequency: value.newFrequency, times: [{ timeComponent: [] }] };
        if (this.editedValue) {
            this.editedValue = [...this.editedValue, newEntry];
        } else {
            this.editedValue = [newEntry];
        }
        this.showEdit();
        this.formGroup.controls["newFrequency"].setValue(undefined);
    }

    /**
     * Switches to edit mode and populates form with current values
     * Sets form controls based on the current schedule configuration
     */
    showEdit(): void {
        if (this.editedValue) {
            this.mode = "EDITING";
            this.editedValue.forEach(x => {
                switch (x.frequency) {
                    case "DAILY": {
                        this.formGroup.controls["daily"].setValue(x.times);
                    } break;
                    case "DATES": {
                        this.formGroup.controls["dates"].setValue(x.times);
                    } break;
                    case "HOURLY": {
                        this.formGroup.controls["hours"].setValue(x.times);
                    } break;
                    case "MONTHLY": {
                        this.formGroup.controls["monthly"].setValue(x.times);
                    } break;
                    case "WEEKLY": {
                        this.formGroup.controls["weekly"].setValue(x.times);
                    } break;
                    case "YEARLY": { } break;
                }
            });
        }
    }

    /**
     * Cancels the current editing operation and reverts to display mode
     * Discards any unsaved changes
     */
    abandonEdit(): void {
        this.mode = "DISPLAY";
        this.editedValue = undefined;
        this.formGroup.patchValue({});
    }

    /**
     * Enters edit mode and initializes edited value with current value
     * Creates a copy of the current schedule for editing
     */
    enterEdit(): void {
        if (this.value) {
            this.editedValue = [...this.value];
        } else {
            this.editedValue = [];
        }
        this.showEdit();
    }

    /**
     * Updates the component value and notifies the form
     * Called when schedule values have changed
     * @param newValue The new schedule configuration
     */
    private notifyValuesChanges(newValue: InternalRappr) {
        const value: ReindexingProgrammedTable[] = this.readDataInput(newValue);
        this.editedValue = value;
        this.value = value;
        this.onChange(value);
    }

    /**
     * Creates a ReindexingProgrammedTable array for a specific frequency
     * Constructs the data structure for a single frequency type
     * @param frequency The frequency type 
     * @param data The time slots for this frequency
     * @returns Array of ReindexingProgrammedTable entries
     */
    private readVector(frequency: ReindexingProgrammedTable.FrequencyEnum, data: timeSlotUnities): ReindexingProgrammedTable[] {
        const out: ReindexingProgrammedTable[] = [];
        if (data) {
            const pt: ReindexingProgrammedTable = {
                frequency: frequency,
                times: data
            };
            out.push(pt);
        }
        return out;
    }

    /**
     * Transforms the internal form representation to the API's expected format
     * Combines data from different frequency controls into a unified structure
     * @param newValue The form value in InternalRappr format
     * @returns Array of ReindexingProgrammedTable with valid configurations
     */
    private readDataInput(newValue: InternalRappr): ReindexingProgrammedTable[] {
        const out: ReindexingProgrammedTable[] = [
            ...this.readVector("HOURLY", newValue.hours),
            ...this.readVector("DAILY", newValue.daily),
            ...this.readVector("WEEKLY", newValue.weekly),
            ...this.readVector("MONTHLY", newValue.monthly),
            ...this.readVector("DATES", newValue.dates)
        ];
        const cleanout: ReindexingProgrammedTable[] = [];
        out.forEach(x => {
            if (x.times && x.times.length) {
                cleanout.push(x);
            }
        });
        return cleanout;
    }

    /**
     * ControlValueAccessor implementation: writes a value to the component
     * Updates the component's internal state with the new value
     * @param obj The value to write
     */
    writeValue(obj: any): void {
        this.value = obj;
        this.editedValue = obj;
        this.displayValue();

    }

    /**
     * Fetches human-readable representations of the schedule values
     * Converts technical time specifications to user-friendly display strings
     */
    private displayValue(): void {

        if (this.value && this.value.length) {
            this.emitValue = false;

            this.emitValue = true;
            this.loading = true;
            this.reindexingFrequencyController.displayTimeValues(this.value).subscribe({
                next: (dValue) => {
                    this.displayTimes = dValue;
                },
                complete: () => {
                    this.loading = false;
                }
            });
        } else {
            this.displayTimes = [];
            this.formGroup.patchValue({});
        }
    }

    /**
     * Function to call when value changes, used by ControlValueAccessor
     */
    private onChange: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor implementation: registers a callback for value changes
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * ControlValueAccessor implementation: registers a callback for touched state
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {

    }

    /**
     * ControlValueAccessor implementation: handles disabled state changes
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }
}