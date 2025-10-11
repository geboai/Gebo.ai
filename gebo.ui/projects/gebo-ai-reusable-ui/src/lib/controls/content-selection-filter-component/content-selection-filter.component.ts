import { Component, forwardRef, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { AbstractControl, ControlValueAccessor, FormArray, FormControl, FormGroup, NG_VALIDATORS, NG_VALUE_ACCESSOR, ValidationErrors, Validator } from "@angular/forms";
import { ContentMetaInfosControllerService, GContentSelectionFilter, GContentSelectionFilterCriteria, IngestionFileType, IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";

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
        }
    ]
})
export class GeboAIContentSelectionFilterComponent implements OnInit, OnChanges, ControlValueAccessor, Validator {

    protected internalValue?: GContentSelectionFilter;
    protected formGroup: FormGroup = new FormGroup({
        criterias: new FormArray([])
    });
    protected loading: boolean = false;
    protected fileTypes: IngestionFileType[]=[];
    protected createNewCriteria(): FormGroup {
        return new FormGroup({
            mimeContentTypes: new FormControl(),
            extensions: new FormControl(),
            nameFilter: new FormControl(),
            nameFilterCriteria: new FormControl(),
            maxFileSize: new FormControl(),
            maxTokenSize: new FormControl(),
            maxModificationAgeInDays: new FormControl()
        });
    }
    constructor(private fileTypeLibraryControllerService: IngestionFileTypesLibraryControllerService) {

    }
    private get criterias(): FormArray {
        return this.formGroup.controls["criterias"] as FormArray;
    }
    private adaptFormGroup(size: number): void {
        const actualValue = this.formGroup.value;
        const crit = this.criterias;
        while (crit.length > 0) {
            crit.removeAt(0);
        }
        for (let i: number = 0; i < size; i++) {
            crit.push(this.createNewCriteria());
        }
        crit.patchValue(actualValue);
    }
    ngOnInit(): void {
        this.loading = true;
        this.fileTypeLibraryControllerService.getAllFileTypes().subscribe({
            next: (fileTypes) => { 
                this.fileTypes=fileTypes;
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
        const adaptToSize: number = this.internalValue?.criterias?.length ? this.internalValue.criterias.length : 1;
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
        return null;
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