import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { AbstractControl, ControlValueAccessor, FormArray, FormControl, FormGroup, NG_VALIDATORS, NG_VALUE_ACCESSOR, ValidationErrors, Validator, Validators } from "@angular/forms";
import { GContentSelectionFilter, IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { validateCriteria } from "./validate-content-selection-filter-criteria";
import { NonNullAssert } from "@angular/compiler";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-content-selection-filter-component",
    templateUrl: "content-selection-filter.component.html",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIContentSelectionFilterComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => GeboAIContentSelectionFilterComponent),
            multi: true
        },
        {
            provide: GEBO_AI_MODULE, useValue: "GeboAIContentSelectionFilterModule", multi: false
        },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIContentSelectionFilterComponent"),
            multi: false
        }
    ]
})
export class GeboAIContentSelectionFilterComponent implements OnInit, OnChanges, ControlValueAccessor, Validator {
    @Input() public title: string = "File selection criterias";
    protected internalValue?: GContentSelectionFilter;
    protected formGroup: FormGroup = new FormGroup({
        criterias: new FormArray([])
    });
    protected loading: boolean = false;
    protected fileTypes: { code: string, description: string }[] = [];
    protected createNewCriteria(): FormGroup {
        const formG: FormGroup = new FormGroup({
            mimeContentTypes: new FormControl(),
            extensions: new FormControl(),
            nameFilter: new FormControl(),
            nameFilterCriteria: new FormControl(),
            maxFileSize: new FormControl(),
            maxTokenSize: new FormControl(),
            maxModificationAgeInDays: new FormControl()
        });
        formG.addValidators((ctrl) => validateCriteria(ctrl.value));

        return formG;
    }
    constructor(private fileTypeLibraryControllerService: IngestionFileTypesLibraryControllerService) {
        this.adaptFormGroup(1);
        this.formGroup.valueChanges.subscribe({
            next: (data) => {
                this.changedValueNotification(data);
            }
        });
    }
    /*************************************************
     * All empty conditions notifies undefined and all validated notifies all conditions
     */
    private changedValueNotification(value: GContentSelectionFilter) {
        if (value?.criterias && value.criterias.length) {
            const validatedArray = value.criterias.map(x => validateCriteria(x) ? false : true).filter(y => y === true);
            if (validatedArray && validatedArray.length === value.criterias.length) {
                this.onChange(value);
            } else {
                //if all attributes of all objects are null undefined will be notified onChange
                const emptyObjects = value.criterias.filter(x => {
                    const attributes = Object.values(x);
                    return attributes && (attributes.length == 0 || attributes.filter(y => y ? true : false).length === 0);
                });
                if (value.criterias.length === emptyObjects.length) {
                    this.onChange(undefined);
                }
            }
        }

    }
    protected get criterias(): FormArray {
        return this.formGroup.controls["criterias"] as FormArray;
    }
    protected criteriaAt(index: number): FormGroup {
        return this.criterias.at(index) as FormGroup;
    }
    private adaptFormGroup(size: number): void {
        const noNotification = {
            emitEvent: false,
            onlySelf: true
        };
        const actualValue = this.formGroup.value;
        const crit = this.criterias;
        while (crit.length > 0) {
            crit.removeAt(0, noNotification);
        }

        for (let i: number = 0; i < size; i++) {

            crit.push(this.createNewCriteria(), noNotification);
        }
        if (actualValue && actualValue.criterias && actualValue.criterias.length)
            this.formGroup.patchValue(actualValue, noNotification);
        else
            this.formGroup.reset(noNotification);
        this.formGroup.updateValueAndValidity(noNotification);
    }
    ngOnInit(): void {
        this.loading = true;

        this.fileTypeLibraryControllerService.getAllFileTypes().subscribe({
            next: (fileTypes) => {
                const files: { code: string, description: string }[] = [];
                if (fileTypes) {
                    fileTypes.forEach(ft => {
                        ft.extensions?.forEach(ext => {
                            files.push({ code: ext, description: "(" + ext + ") " + ft.description });
                        })
                    });
                }
                this.fileTypes = files;
            },
            complete: () => {
                this.loading = false;
            }
        })
    }
    ngOnChanges(changes: SimpleChanges): void {

    }
    writeValue(obj: any): void {
        this.internalValue = obj;
        const adaptToSize: number = this.internalValue?.criterias?.length && this.internalValue.criterias.length > 0 ? this.internalValue.criterias.length : 1;
        this.adaptFormGroup(adaptToSize);
        if (this.internalValue)
            this.formGroup.patchValue(this.internalValue);
        else this.formGroup.reset();
    }
    private onChange: (fn: any) => void = (fn: any) => { }
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    private onTouched: (fn: any) => void = (fn: any) => { }
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {

    }
    validate(control: AbstractControl): ValidationErrors | null {
        const readValue: GContentSelectionFilter = control.value;
        let out: ValidationErrors | null = null;
        if (readValue?.criterias?.length) {
            readValue.criterias.forEach(x => {
                const validated = validateCriteria(x);
                if (validated) {
                    if (!out) out = {};
                    out = {
                        ...out,
                        ...validated
                    };
                }
            });
        }
        return out;
    }
    fn: () => void = () => { };
    registerOnValidatorChange?(fn: () => void): void {
        this.fn = fn;
    }
    addRow(): void {
        this.criterias.push(this.createNewCriteria());
    }
    removeRow(index: number) {
        this.criterias.removeAt(index);
    }
}