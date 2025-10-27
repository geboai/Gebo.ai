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
 * Component for quickly creating a new Confluence system integration.
 * This component provides a form for entering Confluence system configuration details
 * like base URI, credentials, and version type. It supports both cloud and on-premise
 * Confluence instances with appropriate validation for each type.
 */
import { Component, EventEmitter, forwardRef, OnInit, Output } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidationErrors } from "@angular/forms";
import { ConfluenceSystemsControllerService, FastConfluenceSystemInsertRequest, GConfluenceSystem, UserControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";


@Component({
    selector: "gebo-ai-confluence-system-fast-component",
    templateUrl: "gebo-ai-confluence-system-fast.component.html",

    standalone: false,
    providers:[ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAIConfluenceModule", multi: false },
        {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIConfluenceSystemFastComponent")
    }]
})
export class GeboAIConfluenceSystemFastComponent implements OnInit {
    /** Flag to indicate if the component is currently loading data */
    public loading: boolean = false;
    
    /** Form group containing all inputs for Confluence system configuration */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl(),
        baseUri: new FormControl(),
        username: new FormControl(),
        password: new FormControl(),
        token: new FormControl(),
        confluenceVersion: new FormControl()
    });
    
    /** Array to store messages that will be displayed to the user */
    userMessages: ToastMessageOptions[] = [];
    
    /** Available Confluence versions that can be selected */
    confluenceVersions: { code: GConfluenceSystem.ConfluenceVersionEnum, description: string }[] = [{ code: "ONPREMISE7X", description: "On premise 6.X/7.X versions" }, { code: "CLOUD", description: "Cloud version" }];
    
    /** Currently selected Confluence version */
    currentConfluenceVersion?: GConfluenceSystem.ConfluenceVersionEnum = "ONPREMISE7X";
    
    /** Event emitted when a new Confluence system is successfully created */
    @Output() newConfluenceSystemEvent: EventEmitter<GConfluenceSystem> = new EventEmitter();
    
    /** Event emitted when the user cancels the form */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();
    
    /**
     * Constructor initializes the component with necessary services and sets default form values
     * @param confluenceService Service for Confluence system operations
     * @param userControllerService Service for user operations
     */
    constructor(private confluenceService: ConfluenceSystemsControllerService, private userControllerService: UserControllerService) {


        this.formGroup.controls["description"].setValue("Confluence system");
        /*
        this.formGroup.controls["confluenceVersion"].valueChanges.subscribe({
            next:(value:GConfluenceSystem.ConfluenceVersionEnum)=>{
                this.currentConfluenceVersion=value;
                this.formGroup.controls["password"].clearValidators();
                this.formGroup.controls["token"].clearValidators();
                if (value){
                    switch(value){
                        case "CLOUD":{
                            this.formGroup.controls["token"].setValidators(Validators.required);
                        };break;
                        case "ONPREMISE7X":{
                            this.formGroup.controls["password"].setValidators(Validators.required);
                        };break;
                    }
                }
                this.formGroup.updateValueAndValidity();
            }   
        }); */
        this.formGroup.controls["confluenceVersion"].setValue(this.currentConfluenceVersion);
    }
    
    /**
     * Enables or disables a form control by name
     * @param ctrlName The name of the control to enable/disable
     * @param enabled Boolean indicating whether to enable (true) or disable (false) the control
     */
    setEnabled(ctrlName: string, enabled: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (enabled === ctrl.enabled) return;
        if (enabled===true) {
            ctrl.enable();
        } else {            
            ctrl.disable();
        }
    }
    
    /**
     * Custom validator function that validates the form based on the selected Confluence version
     * For cloud version, token is required; for on-premise, password is required
     * @param ctrl The AbstractControl to validate
     * @returns ValidationErrors or null if validation passes
     */
    private validate(ctrl: AbstractControl):ValidationErrors|null  {
        const value: FastConfluenceSystemInsertRequest = ctrl.value;
        const returnedError:ValidationErrors = { invalidForm: "Invalid" };
        let returned: null | any = returnedError;
        let validated: boolean;
        this.currentConfluenceVersion=value.confluenceVersion;
        if (value.description && value.baseUri && value.confluenceVersion && value.username) {
            
            switch (value.confluenceVersion) {
                case "CLOUD": {
                    this.setEnabled("password", false);
                    this.setEnabled("token", true);
                    if (value.token) {
                        returned = null;
                    }
                } break;
                case "ONPREMISE7X": {
                    this.setEnabled("password", true);
                    this.setEnabled("token", false);
                    if (value.password) {
                        returned = null;
                    }
                } break;
            }
        }
        return returned;
    }
    
    /**
     * Initializes the component by setting up form validation and loading current user data
     */
    ngOnInit(): void {
        
        this.formGroup.setValidators((ctrl: AbstractControl)=>this.validate(ctrl));
        this.loading = true;
        this.userControllerService.getCurrentUser().subscribe({
            next: (value) => {
                this.formGroup.controls["username"].setValue(value.username);
            },
            complete: () => {
                this.loading = false;
            }
        })
    }
    
    /**
     * Submits the form data to create a new Confluence system configuration
     * On success, emits the created system via newConfluenceSystemEvent
     */
    doInsert(): void {
        const data: FastConfluenceSystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.confluenceService.fastConfluenceConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages!==true) {
                    this.newConfluenceSystemEvent.emit(result.result);
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}