/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Directive, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { SetupWizardComunicationService } from "./setup-wizard-comunication.service";
import { ToastMessageOptions } from "primeng/api";

/**
 * AI generated comments
 * This directive serves as a base class for wizard section components in a setup wizard.
 * It implements common functionality such as data loading, user message handling, and
 * loading state management. All specific wizard sections should extend this class and
 * implement the required abstract methods.
 */
@Directive()
export abstract class BaseWizardSectionComponent implements OnInit, OnChanges {
     /**
      * Abstract method that must be implemented by subclasses to load or refresh
      * their specific data.
      */
     public abstract reloadData(): void;

     /**
      * Array to store toast messages that will be displayed to the user
      * during the wizard flow.
      */
     public userMessages:ToastMessageOptions[]=[];

     /**
      * Flag indicating whether data is currently being loaded.
      * Used to show loading indicators.
      */
     public loading:boolean=false;

     /**
      * Flag indicating whether the setup process for this section has been completed.
      */
     public isSetupCompleted:boolean=false;

     /**
      * Constructs the BaseWizardSectionComponent.
      * @param setupWizardComunicationService Service for communication between wizard components
      */
     constructor(private setupWizardComunicationService: SetupWizardComunicationService) {

     }

     /**
      * Lifecycle hook that is called after component initialization.
      * Calls reloadData to initialize the component with data.
      */
     ngOnInit(): void {
          this.reloadData();
     }

     /**
      * Lifecycle hook that is called when any component input properties change.
      * Can be overridden by subclasses to react to input changes.
      * @param changes Object containing the changed properties
      */
     ngOnChanges(changes: SimpleChanges): void {

     }

     /**
      * Closes the wizard by communicating with the setup wizard service.
      * This method can be called when the user wants to exit the wizard.
      */
     public closeWizard() {
          this.setupWizardComunicationService.closeWizard();
     }
}