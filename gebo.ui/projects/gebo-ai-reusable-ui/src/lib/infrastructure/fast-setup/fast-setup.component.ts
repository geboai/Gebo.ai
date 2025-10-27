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
 * This module represents the Fast Setup component for initial Gebo.ai application configuration.
 * It allows new users to create their credentials, accept the license agreement,
 * and complete the installation process.
 */
import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { FastInstallationSetupData, GeboFastInstallationSetupControllerService, OperationStatusBoolean } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { LoginService } from "../login/login.service";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "../../controls/field-host-component-iface/field-host-component-iface";

/**
 * Component responsible for the initial fast setup process of the Gebo.ai application.
 * Provides a user interface for creating the first admin account and completing installation.
 * This component handles the setup form, validation, and API communication to finalize installation.
 */
@Component({
    selector: "gebo-ai-fast-setup",
    templateUrl: "fast-setup.component.html",
    providers: [LoginService,{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("FastSetupComponent")
    }],
    standalone: false

})
export class FastSetupComponent implements OnInit {
    /**
     * Form group containing all the user input fields required for setup.
     * Includes username, password, password confirmation, and license agreement.
     */
    formGroup: FormGroup = new FormGroup({
        username: new FormControl(),
        password: new FormControl(),
        passwordC: new FormControl(),
        licenceAgreement:new FormControl(),
        lang:new FormControl()
    });
    
    /** Flag indicating if a setup operation is in progress */
    loading: boolean = false;
    
    /** Flag indicating if a login operation is in progress */
    loggingin: boolean = false;
    
    /** User profile information */
    profile?: any;
    
    /** Status of the installation process */
    status?: OperationStatusBoolean;
    
    /** Username/email that will be used for the license */
    licencee:string="";
    
    /** Array of messages to display to the user */
    userMessages: ToastMessageOptions[] = [{ summary: "Welcome to gebo.ai setup", detail: "Enter your username (email) and password, wich LLMS to use and your api keys to setup", severity: "success" }];
    
    /**
     * Constructor initializes required services for installation, login, and navigation
     * @param geboFastSetupControllerService Service to interact with fast installation API
     * @param loginService Service to handle user authentication
     * @param router Angular router for navigation
     * @param activatedRoute Current activated route
     */
    constructor(private geboFastSetupControllerService: GeboFastInstallationSetupControllerService,
        private loginService: LoginService,
        private router: Router, private activatedRoute: ActivatedRoute) {

    }
    
    /**
     * Checks the system installation status.
     * If the system is already set up, redirects to login.
     */
    private checkSystemSetup() {
        this.geboFastSetupControllerService.getInstallationStatus().subscribe(status => {
            this.status = status;
            //this.userMessages=status.messages as Message[];
            if (status.result === true) {
                this.doLogin();
            }
        });
    }

    /**
     * Initializes the component by checking system status and setting up form validators.
     * Creates a validator to ensure passwords match and meet minimum requirements.
     */
    ngOnInit(): void {
        this.checkSystemSetup();
        const equalPasswordsValidator: ValidatorFn = (control: AbstractControl) => {
            const validationError: ValidationErrors = {};
            const formGroup = control as FormGroup;
            const v1: string = formGroup.controls["password"].value;
            const v2: string = formGroup.controls["passwordC"].value
            this.userMessages = [];
            if (v1 && v2 && v1 === v2 && v1.length>=8) return null;
            if (v1 && v2 && v1 !== v2) {
                const msgs: ToastMessageOptions[] = [];
                if (!this.status?.result == true) {

                }


                this.userMessages = msgs;
            }
            return {
                pwdNotMatching: true
            };
        };
        this.formGroup.addValidators(equalPasswordsValidator);
        this.formGroup.controls["username"].valueChanges.subscribe((username:string)=>{
            this.licencee=username;
        });
        
    }

    /**
     * Redirects the user to the reloader page after successful setup.
     * Passes state information indicating setup is complete.
     */
    public doLogin(): void {
        this.router.navigate(["..", "reloader"], { relativeTo: this.activatedRoute,state:{setupDone:true} });
    }

    /**
     * Performs the setup process with the provided form data.
     * Creates the initial admin account, completes installation,
     * and automatically logs in the user upon success.
     */
    public doSetup() {
        const param:FastInstallationSetupData = this.formGroup.value;
        this.loading = true;
        
        this.geboFastSetupControllerService.createSetup(param).subscribe({
            next: (result) => {
                this.status = result;
                this.userMessages = result?.messages as ToastMessageOptions[];
                if (this.status.result === true) {
                    const loginData = this.formGroup.value;
                    this.loggingin = true;
                    this.loginService.login({
                        username: loginData.username,
                        password: loginData.password
                    }).subscribe({
                        next: (v) => {
                            this.router.navigate(["..", "reloader"], { relativeTo: this.activatedRoute,state:{setupDone:true} });
                        },
                        complete: () => {
                            this.loggingin = false;
                        }
                    });
                }
            },
            error: (error) => { },
            complete: () => {
                this.loading = false;
            }
        })
    }
}