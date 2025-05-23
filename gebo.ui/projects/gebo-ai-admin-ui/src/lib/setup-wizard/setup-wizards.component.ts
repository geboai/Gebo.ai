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
 * This component manages the setup wizard functionality in the Gebo.ai application.
 * It handles displaying setup steps, tracking setup status, and managing the wizard's
 * visibility and completion state.
 */
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { SetupWizardPanelModule } from "../../../../gebo-ai-reusable-ui/src/lib/setup-wizard/setup-wizard-panel.module";
import { SetupStatus } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { ActivatedRoute } from "@angular/router";

@Component({
    selector: "gebo-setup-wizards",
    templateUrl: "setup-wizards.component.html",
    standalone: false
})
export class SetupWizardsComponent implements OnInit{
    /** Controls the visibility of the setup wizard */
    @Input() visible:boolean=false;
    
    /** Tracks the current setup status (complete, incomplete) */
    private actualSetupStatus?:SetupStatus;
    
    /** Current step ID from the route parameters */
    stepId?:string;
    
    /** Event emitted when the setup wizard is closed */
    @Output() closeSetup:EventEmitter<boolean>=new EventEmitter();
    
    /** Event emitted when the setup status changes */
    @Output() setupStatusRefresh:EventEmitter<SetupStatus>=new EventEmitter();
    
    /**
     * Initializes the component with required services
     * @param confirmService Service to handle confirmation dialogs
     * @param activatedRoute Service to access the current route parameters
     */
    constructor(private confirmService:ConfirmationService,private activatedRoute:ActivatedRoute) {

    }
    
    /**
     * Lifecycle hook that initializes the component
     * Subscribes to route parameters to get the current step ID
     */
    ngOnInit(): void {
           this.activatedRoute.params.subscribe(params=>{
                const stepId=params["stepId"];
                this.stepId=stepId;
           });
    }
    
    /**
     * Handles the setup status refresh event
     * Updates the current status and emits the updated status to parent components
     * @param data The updated setup status
     */
    public setupStatusRefreshed(data:SetupStatus) {
        this.actualSetupStatus=data;
        this.setupStatusRefresh.emit(data);
    }
    
    /**
     * Handles the close event for the setup wizard
     * If setup is incomplete, displays a confirmation dialog
     * Otherwise, immediately closes the wizard
     * @param data Event data from the close event
     */
    public onClose(data:any) {
        if (this.actualSetupStatus==="incomplete") {
            this.confirmService.confirm({
                header:"Close setup window",
                message:"You have some mandatory setup task to complete, do you really want to quit setup?",
                accept:()=>{
                    this.closeSetup.emit(true);
                }
            })
        }else {
            this.closeSetup.emit(true);
        }
    }
}