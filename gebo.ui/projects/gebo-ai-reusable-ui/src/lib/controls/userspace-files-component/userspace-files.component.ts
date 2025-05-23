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
 * This component manages the selection and display of userspace files in the Gebo.ai application.
 * It implements ControlValueAccessor to integrate with Angular forms, allowing it to be used in form controls.
 * The component provides functionality to browse, select, and display files from the user's workspace.
 */
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { UserspaceControllerService, UserspaceFileDto } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIUserspaceFolderComponent } from "./userspace-folder.component";
import { GeboAIUserspaceKnowledgebaseComponent } from "./user-knowledgebase.component";
import { GeboAIUserspaceFilesUploadComponent } from "./userspace-files-upload.component";

/**
 * Component that handles userspace files functionality in the Gebo.ai application.
 * Implements ControlValueAccessor to work with Angular forms, allowing for selection and management
 * of files from user's workspace. It can display files, allow browsing, and handle selection changes.
 */
@Component({
    selector: "gebo-ai-userspace-files-component",
    templateUrl: "userspace-files.component.html", providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIUserpaceFilesComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAIUserpaceFilesComponent implements OnInit, OnChanges, ControlValueAccessor {
    /** Array of knowledge base codes that can be used for filtering or contextual display */
    @Input() knowledgeBaseCodes: string[] = [];
    /** Flag to indicate if knowledge bases should not be displayed or used */
    @Input() noKnowledgeBases: boolean = false;
    /** Indicates if data is currently being loaded */
    public loading: boolean = false;
    /** Controls visibility of the userspace browse window */
    public openedUserspaceBrowseWindow: boolean = false;
    /** List of user files retrieved from the backend */
    public userFiles: UserspaceFileDto[] = [];
    /** Form group for editing file selection */
    editingFormGroup: FormGroup = new FormGroup({
        filesList: new FormControl()
    });
    /** Internal storage for the list of file codes */
    private codesList: string[] = [];
    /** Reference to the folder component for browsing */
    userspaceFolderComponent = GeboAIUserspaceFolderComponent;
    /** Reference to the knowledge base component */
    userspaceKnowledgebaseComponent = GeboAIUserspaceKnowledgebaseComponent;
    /** Reference to the file upload component */
    userspaceFilesUploadComponent = GeboAIUserspaceFilesUploadComponent;

    /**
     * Constructor that injects the userspace controller service for API interactions
     * @param userspaceControllerService Service to interact with userspace files API
     */
    constructor(private userspaceControllerService: UserspaceControllerService) {

    }

    /**
     * Lifecycle hook that initializes the component
     */
    ngOnInit(): void {

    }

    /**
     * Lifecycle hook that responds to changes in input properties
     * @param changes Simple changes object containing current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Synchronizes the displayed files with the selected codes
     * Fetches file details from the API based on the selected codes
     */
    private syncDisplay(): void {
        if (this.codesList && this.codesList.length) {
            this.loading = true;
            this.userspaceControllerService.findUserspaceFileByCodes(this.codesList).subscribe({
                next:(list)=>{
                    this.userFiles=list;
                    if (list) {
                        this.codesList=list.map(x=>x.code) as string[];
                    }
                },complete:()=>{
                    this.loading=false;
                }
            });

        } else {
            this.userFiles = [];
            this.codesList=[];
        }
    }

    /**
     * ControlValueAccessor method that updates the component's value when the form control value changes
     * @param obj The value received from the form model
     */
    writeValue(obj: any): void {
        this.codesList = obj ? Array.isArray(obj) ? Array.from(obj) : [obj] : [];
        this.syncDisplay();
    }

    /**
     * Handles the confirmation of file selection from the form
     * Updates the internal state and notifies form subscribers
     */
    public onConfirmed(): void {
        this.codesList=this.editingFormGroup.controls["filesList"].value;
        this.syncDisplay();
        this.onChange(this.codesList);
    }

    /** Function to call when the value changes */
    private onChange: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor method that registers a callback function for value changes
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /** Function to call when the control is touched */
    private onTouch: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor method that registers a callback function for touched events
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

    }

    /**
     * Opens the userspace browse window for file selection
     */
    public openUserSpaceBrowseWindow() {
        this.openedUserspaceBrowseWindow = true;
    }

    /**
     * Handles the removal of a file from the selected list
     * @param f The file to be removed
     */
    doRemoveFile(f: UserspaceFileDto) {

    }
}